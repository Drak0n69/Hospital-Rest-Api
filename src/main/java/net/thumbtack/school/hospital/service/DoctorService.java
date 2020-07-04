package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.DoctorProfileResponseDto;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.model.enums.TimeSlotStatus;
import net.thumbtack.school.hospital.model.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DoctorService extends ServiceBase {

    private DoctorDao doctorDao;

    @Autowired
    public DoctorService(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao,
                         UserDao userDao, DoctorDao doctorDao1) {
        super(sessionDao, adminDao, doctorDao, patientDao, userDao);
        this.doctorDao = doctorDao1;
    }

    public DoctorProfileResponseDto registerDoctor(DoctorRegisterRequestDto requestDto, String cookie) throws ServerException {
        checkIfAdmin(cookie);
        User user = insertUserIntoDb(requestDto);
        List<DaySchedule> schedule = getScheduleModelFromDto(requestDto.getDateStart(), requestDto.getDateEnd(),
                requestDto.getDuration(), requestDto.getWeekSchedule(), requestDto.getDaySchedule());
        Doctor doctor = new Doctor(requestDto.getSpeciality(), requestDto.getRoom(), schedule);
        doctorDao.insert(user, doctor);
        return createDoctorProfileDto(doctor);
    }

    public DoctorProfileResponseDto getDoctorInfo(String cookie, int id, String schedule, String startDate, String endDate
    ) throws ServerException {
        User user = getSessionByCookie(cookie).getUser();
        Doctor doctor = getDoctorById(id);

        return getDoctorProfile(schedule, startDate, endDate, user, doctor);
    }

    public List<DoctorProfileResponseDto> getAllBySpeciality(String cookie, String schedule, String speciality,
                                                             String startDate, String endDate) throws ServerException {
        User user = getSessionByCookie(cookie).getUser();
        List<Doctor> doctors = getAllDoctors();
        List<DoctorProfileResponseDto> doctorsDto = new ArrayList<>();

        if (!speciality.equals("")) {
            for (Doctor doctor : doctors) {
                if (speciality.equalsIgnoreCase(doctor.getSpeciality())) {
                    doctorsDto.add(getDoctorProfile(schedule, startDate, endDate, user, doctor));
                }
            }
            return doctorsDto;
        }

        for (Doctor doctor : doctors) {
            doctorsDto.add(getDoctorProfile(schedule, startDate, endDate, user, doctor));
        }
        return doctorsDto;
    }

    public DoctorProfileResponseDto changeSchedule(String cookie, int id, DoctorChangeScheduleRequestDto requestDto
    ) throws ServerException {
        checkIfAdmin(cookie);
        Doctor doctor = doctorDao.getById(id);
        List<DaySchedule> newSchedule = getScheduleModelFromDto(requestDto.getDateStart(), requestDto.getDateEnd(),
                requestDto.getDuration(), requestDto.getWeekSchedule(), requestDto.getDaySchedule());
        doctor = doctorDao.changeSchedule(doctor, newSchedule);
        return createDoctorProfileDto(doctor);
    }

    public EmptyResponse deleteDoctor(String cookie, int id, DoctorDismissalRequestDto requestDto
    ) throws ServerException {
        checkIfAdmin(cookie);
        LocalDate disDate = getDateFromString(requestDto.getDismissalDate());
        doctorDao.deleteDoctor(id, disDate);
        return new EmptyResponse();
    }

    private DoctorProfileResponseDto getDoctorProfile(String schedule, String startDate,
                                                      String endDate, User user, Doctor doctor) throws ServerException {
        LocalDate dateStart = (startDate != null && !startDate.equals("")) ? getDateFromString(startDate)
                : LocalDate.now();
        LocalDate dateEnd = (endDate != null && !endDate.equals("")) ? getDateFromString(endDate)
                : LocalDate.now().plusMonths(2);

        if (schedule.equalsIgnoreCase("yes")) {
            if (user.getUserType() == UserType.PATIENT) {
                Patient patient = getPatientById(user.getId());
                return createDoctorProfileDtoWithParams(doctor, dateStart, dateEnd, patient);
            }
            return createDoctorProfileDtoWithParams(doctor, dateStart, dateEnd, null);
        }

        return new DoctorProfileResponseDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(),
                doctor.getPatronymic(), doctor.getSpeciality(), doctor.getRoom());
    }

    private List<DaySchedule> getScheduleModelFromDto(String dateStart, String dateEnd, int duration,
                 WeekScheduleRequestDto weekSchedule, List<DayScheduleRequestDto> daySchedule) throws ServerException {
        List<DaySchedule> daySchedules;
        List<LocalDate> workingDates;
        List<String> weekDays = new ArrayList<>();
        LocalDate startDate = getDateFromString(dateStart);
        LocalDate endDate = getDateFromString(dateEnd);

        if (weekSchedule != null) {
            if (weekSchedule.getWeekDays() != null) {
                workingDates = getWorkingWeekDays(startDate, endDate, weekSchedule.getWeekDays());
                daySchedules = getScheduleModelFromDtoForAllDays(workingDates, weekSchedule.getTimeStart(),
                        weekSchedule.getTimeEnd(), duration);
                return daySchedules;
            }
            workingDates = getWorkingWeekDays(startDate, endDate);
            daySchedules = getScheduleModelFromDtoForAllDays(workingDates, weekSchedule.getTimeStart(),
                    weekSchedule.getTimeEnd(), duration);
            return daySchedules;
        }

        if (daySchedule != null) {
            for (DayScheduleRequestDto dayScheduleRequestDto : daySchedule) {
                weekDays.add(dayScheduleRequestDto.getWeekDay());
            }
            workingDates = getWorkingWeekDays(startDate, endDate, weekDays);
            daySchedules = getScheduleModelFromDtoForEachDay(workingDates, daySchedule, weekSchedule.getTimeStart(),
                    weekSchedule.getTimeEnd(), duration);
            return daySchedules;
        }
        throw new ServerException(ServerError.INVALID_DOCTOR_SCHEDULE);
    }

    private List<LocalDate> getWorkingWeekDays(LocalDate dateStart, LocalDate dateEnd, List<String> weekDays) {
        EnumSet<DayOfWeek> dayOfWeeks = EnumSet.noneOf(DayOfWeek.class);
        final DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("E"))
                .toFormatter(Locale.US);

        for (String weekDay : weekDays) {
            dayOfWeeks.add(DayOfWeek.from(dtf.parse(weekDay)));
        }

        long numOfDaysBetween = ChronoUnit.DAYS.between(dateStart, dateEnd.plusDays(1));
        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                .mapToObj(dateStart::plusDays).filter(localDate -> dayOfWeeks.contains(localDate.getDayOfWeek()))
                .collect(Collectors.toList());
    }

    private List<LocalDate> getWorkingWeekDays(LocalDate dateStart, LocalDate dateEnd) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(dateStart, dateEnd.plusDays(1));
        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                .mapToObj(dateStart::plusDays).filter(localDate -> localDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        localDate.getDayOfWeek() != DayOfWeek.SUNDAY)
                .collect(Collectors.toList());
    }

    private List<DaySchedule> getScheduleModelFromDtoForAllDays(List<LocalDate> workingDates, String timeStart,
                                                                String timeEnd, int duration) {
        List<DaySchedule> schedule = new ArrayList<>();
        LocalTime startTime = getTimeFromString(timeStart);
        LocalTime endTime = getTimeFromString(timeEnd);
        List<Appointment> appointments = getAppointmentsForWeekDay(startTime, endTime, duration);

        for (LocalDate date : workingDates) {
            schedule.add(new DaySchedule(date, appointments));
        }
        return schedule;
    }

    private List<DaySchedule> getScheduleModelFromDtoForEachDay(List<LocalDate> workingDates,
                            List<DayScheduleRequestDto> daySchedule,  String timeStart, String timeEnd, int duration) {
        List<DaySchedule> schedule = new ArrayList<>();
        Map<String, List<Appointment>> appointmentsMap = new HashMap<>();
        LocalTime startTime = getTimeFromString(timeStart);
        LocalTime endTime = getTimeFromString(timeEnd);

        for (DayScheduleRequestDto daysDto : daySchedule) {
            List<Appointment> appointments = getAppointmentsForWeekDay(startTime, endTime, duration);
            appointmentsMap.put(daysDto.getWeekDay().toUpperCase(), appointments);
        }

        for (LocalDate date : workingDates) {
            String day = date.getDayOfWeek().toString().substring(0, 2);
            schedule.add(new DaySchedule(date, appointmentsMap.get(day)));
        }
        return schedule;
    }

    private List<Appointment> getAppointmentsForWeekDay(LocalTime startTime, LocalTime endTime, int duration) {
        List<Appointment> appointments = new ArrayList<>();
        long slotSize = ChronoUnit.MINUTES.between(startTime, endTime) / duration;

        for (int i = 0; i < slotSize; i++) {
            LocalTime nextSlot = startTime.plusMinutes(duration * i);
            appointments.add(new Appointment(nextSlot, duration, null, TimeSlotStatus.FREE));
        }
        return appointments;
    }

    private LocalDate getDateFromString(String date) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatDate);
    }

    private LocalTime getTimeFromString(String time) {
        DateTimeFormatter format24 = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, format24);
    }

}

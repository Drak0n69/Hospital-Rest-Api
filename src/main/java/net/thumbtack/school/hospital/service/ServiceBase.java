package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.request.UserRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.*;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.model.enums.UserType;
import net.thumbtack.school.hospital.model.Appointment;
import net.thumbtack.school.hospital.model.DaySchedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceBase {

    protected SessionDao sessionDao;
    protected AdminDao adminDao;
    protected DoctorDao doctorDao;
    protected PatientDao patientDao;
    protected UserDao userDao;

    public ServiceBase(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao
            , UserDao userDao) {
        this.sessionDao = sessionDao;
        this.adminDao = adminDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
        this.userDao = userDao;
    }

    protected Session getSessionByCookie(String cookie) throws ServerException {
        Session session = sessionDao.getByCookie(cookie);
        if (session == null) {
            throw new ServerException(ServerError.NO_VALID_SESSION);
        }
        return session;
    }

    protected void insertSession(String cookie, User user) throws ServerException {
        Session session = new Session(cookie, user);
        sessionDao.insertSession(session);
    }

    protected User getUserFromDb(String login, String password) throws ServerException {
        User userFromDb = userDao.getByLogin(login);
        if (userFromDb == null) {
            throw new ServerException(ServerError.INVALID_USER_LOGIN);
        }
        if (!password.equalsIgnoreCase(userFromDb.getPassword())) {
            throw new ServerException(ServerError.INVALID_USER_PASSWORD);
        }
        return userFromDb;
    }

    protected void checkIfAdmin(String cookie) throws ServerException {
        User user = getSessionByCookie(cookie).getUser();
        if (user.getUserType() != UserType.ADMIN) {
            throw new ServerException(ServerError.USER_NOT_ADMIN);
        }
    }

    protected UserProfileResponseDto getUserProfile(User user) throws ServerException {
        switch (user.getUserType()) {
            case ADMIN:
                Admin admin = getAdminById(user.getId());
                return createAdminProfileDto(admin);
            case DOCTOR:
                Doctor doctor = getDoctorByUser(user);
                return createDoctorProfileDto(doctor);
            case PATIENT:
                Patient patient = getPatientByUser(user);
                return createPatientProfileDto(patient);
            default:
                throw new ServerException(ServerError.INVALID_USER_TYPE);
        }
    }

    protected Admin getAdminById(int id) throws ServerException {
        Admin admin = adminDao.getById(id);
        if (admin == null) {
            throw new ServerException(ServerError.NO_SUCH_ADMIN);
        }
        return admin;
    }

    protected Doctor getDoctorByUser(User user) throws ServerException {
        Doctor doctor = doctorDao.getByUser(user);
        if (doctor == null) {
            throw new ServerException(ServerError.NO_SUCH_DOCTOR);
        }
        return doctor;
    }

    protected Doctor getDoctorById(int id) throws ServerException {
        Doctor doctor = doctorDao.getById(id);
        if (doctor == null) {
            throw new ServerException(ServerError.NO_SUCH_DOCTOR);
        }
        return doctor;
    }

    protected List<Doctor> getAllDoctors() throws ServerException {
        List<Doctor> doctors = doctorDao.getAll();
        if (doctors.isEmpty()) {
            throw new ServerException(ServerError.NO_SUCH_DOCTOR);
        }
        return doctors;
    }

    protected Patient getPatientByUser(User user) throws ServerException {
        Patient patient = patientDao.getByUser(user);
        if (patient == null) {
            throw new ServerException(ServerError.NO_SUCH_PATIENT);
        }
        return patient;
    }

    protected Patient getPatientById(int id) throws ServerException {
        Patient patient = patientDao.getById(id);
        if (patient == null) {
            throw new ServerException(ServerError.NO_SUCH_PATIENT);
        }
        return patient;
    }

    protected User insertUserIntoDb(UserRegisterRequestDto requestDto) throws ServerException {
        User user = new User(requestDto.getFirstName(), requestDto.getLastName(), requestDto.getPatronymic(),
                requestDto.getLogin(), requestDto.getPassword(), requestDto.getUserType());
        return userDao.insert(user);
    }

    protected AdminProfileResponseDto createAdminProfileDto(Admin admin) {
        return new AdminProfileResponseDto(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getPatronymic(),
                admin.getPosition());
    }

    protected PatientProfileResponseDto createPatientProfileDto(Patient patient) {
        return new PatientProfileResponseDto(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getPatronymic(), patient.getEmail(), patient.getAddress(), patient.getPhone());
    }

    protected DoctorProfileResponseDto createDoctorProfileDto(Doctor doctor) throws ServerException {
        List<DayScheduleResponseDto> scheduleDto = new ArrayList<>();
        List<DaySchedule> schedule = doctor.getDaySchedule();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (DaySchedule daySchedule : schedule) {
            List<AppointmentDto> appointmentsDto = createAppointmentsForResponseDto(daySchedule, null);
            scheduleDto.add(new DayScheduleResponseDto(daySchedule.getDate().format(formatDate), appointmentsDto));
        }
        return new DoctorProfileResponseDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(),
                doctor.getSpeciality(), doctor.getRoom(), scheduleDto);
    }

    protected DoctorProfileResponseDto createDoctorProfileDtoWithParams(Doctor doctor, LocalDate startDate, LocalDate endDate,
                                                              Patient patient) throws ServerException {
        List<DayScheduleResponseDto> scheduleDto = new ArrayList<>();
        List<DaySchedule> schedule = doctor.getDaySchedule();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (DaySchedule daySchedule : schedule) {
            if (daySchedule.getDate().isAfter(startDate.minusDays(1)) && daySchedule.getDate().isBefore(endDate)) {
                List<AppointmentDto> appointmentsDto = createAppointmentsForResponseDto(daySchedule, patient);
                scheduleDto.add(new DayScheduleResponseDto(daySchedule.getDate().format(formatDate), appointmentsDto));
            }
        }

        return new DoctorProfileResponseDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getPatronymic(),
                doctor.getSpeciality(), doctor.getRoom(), scheduleDto);
    }

    protected List<AppointmentDto> createAppointmentsForResponseDto(DaySchedule daySchedule, Patient patient) throws ServerException {
        List<AppointmentDto> appointmentsDto = new ArrayList<>();
        DateTimeFormatter format24 = DateTimeFormatter.ofPattern("HH:mm");

        for (Appointment appointment : daySchedule.getAppointments()) {
            if (appointment.getPatient() != null) {
                Patient patientFromDb = getPatientById(appointment.getPatient().getId());
                PatientProfileResponseDto patientDto = createPatientProfileDto(patientFromDb);
                appointmentsDto.add(new AppointmentDto(appointment.getSlot().format(format24), appointment.getStatus(),
                        patientDto));
            }
            if (appointment.getPatient() != null && appointment.getPatient().getId() == patient.getId()) {
                PatientProfileResponseDto patientDto = createPatientProfileDto(patient);
                appointmentsDto.add(new AppointmentDto(appointment.getSlot().format(format24), appointment.getStatus(),
                        patientDto));
            }
            appointmentsDto.add(new AppointmentDto(appointment.getSlot().format(format24), appointment.getStatus(), null));
        }

        return appointmentsDto;
    }
}

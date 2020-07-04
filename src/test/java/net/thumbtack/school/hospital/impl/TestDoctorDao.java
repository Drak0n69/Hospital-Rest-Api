package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.implementation.CommonDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.DoctorDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.PatientDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.UserDaoImpl;
import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.dao.intrface.DoctorDao;
import net.thumbtack.school.hospital.database.dao.intrface.PatientDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.model.enums.TimeSlotStatus;
import net.thumbtack.school.hospital.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, DoctorDaoImpl.class, UserDaoImpl.class, PatientDaoImpl.class})
public class TestDoctorDao extends TestBase {

    protected DoctorDao doctorDao;
    protected UserDao userDao;
    protected PatientDao patientDao;

    @Autowired
    public TestDoctorDao(CommonDao commonDao, DoctorDao doctorDao, UserDao userDao, PatientDao patientDao) {
        super(commonDao);
        this.doctorDao = doctorDao;
        this.userDao = userDao;
        this.patientDao = patientDao;
    }

    @Test
    void testInsert() throws ServerException {
        User user = userDao.insert(new User("Сергей", "Боткин", "Петрович", "klnern3evne1ln",
                "qwerty123456", UserType.DOCTOR));

        List<DaySchedule> schedule = new ArrayList<>();
        List<Appointment> todayApp = new ArrayList<>();
        todayApp.add(new Appointment(LocalTime.parse("08:00"), 15,  null, TimeSlotStatus.FREE));
        todayApp.add(new Appointment(LocalTime.parse("08:15"), 15,  null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now(), todayApp));
        List<Appointment> tomorrowApp = new ArrayList<>();
        tomorrowApp.add(new Appointment(LocalTime.parse("08:00"), 15,  null, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:15"), 15,  null, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:30"), 15,  null, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:45"), 15, null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now().plusDays(1), tomorrowApp));

        Doctor doctor = doctorDao.insert(user, new Doctor("Therapist", "103A", schedule));

        assertNotEquals(0, doctor.getId());
        for (DaySchedule schedule1 : doctor.getDaySchedule()) {
            assertNotEquals(0, schedule1.getId());
            for (Appointment appointment : schedule1.getAppointments()) {
                assertNotEquals(0, appointment.getId());
            }
        }
    }

    @Test
    void testFailedInsert() throws ServerException {
        User user = userDao.insert(new User("Сергей", "Боткин", "Петрович", "klnern3evne1ln",
                "qwerty123456", UserType.DOCTOR));
        List<DaySchedule> schedule = new ArrayList<>();
        List<Appointment> todayApp = new ArrayList<>();
        todayApp.add(new Appointment(LocalTime.parse("08:00"), 15, null, TimeSlotStatus.FREE));
        List<Appointment> tomorrowApp = new ArrayList<>();
        tomorrowApp.add(new Appointment(LocalTime.parse("09:00"), 15, null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now(), todayApp));
        schedule.add(new DaySchedule(LocalDate.now().plusDays(1), tomorrowApp));
        Doctor doctor = new Doctor("Therapist", "103A", schedule);
        doctorDao.insert(user, doctor);

        assertThrows(ServerException.class, () -> doctorDao.insert(user, doctor));
    }

    @Test
    void testGetFromDb() throws ServerException {
        User user = userDao.insert(new User("Сергей", "Боткин", "Петрович", "klnern3evne1ln",
                "qwerty123456", UserType.DOCTOR));

        List<DaySchedule> schedule = new ArrayList<>();
        List<Appointment> todayApp = new ArrayList<>();
        todayApp.add(new Appointment(LocalTime.parse("08:00"), 15, null, TimeSlotStatus.FREE));
        List<Appointment> tomorrowApp = new ArrayList<>();
        tomorrowApp.add(new Appointment(LocalTime.parse("09:00"), 15, null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now(), todayApp));
        schedule.add(new DaySchedule(LocalDate.now().plusDays(1), tomorrowApp));
        Doctor doctorToDb = doctorDao.insert(user, new Doctor("Therapist", "103A", schedule));

        Doctor doctorFromDb = doctorDao.getByUser(user);
        Doctor doctorFromDb2 = doctorDao.getById(doctorToDb.getId());

        System.out.println();
        assertAll(
                () -> assertEquals(doctorToDb.getId(), doctorFromDb.getId()),
                () -> assertEquals(doctorFromDb.getId(), doctorFromDb2.getId()),
                () -> assertEquals(doctorToDb.getRoom(), doctorFromDb.getRoom()),
                () -> assertEquals(doctorFromDb.getRoom(), doctorFromDb2.getRoom()),
                () -> assertEquals(doctorToDb.getSpeciality(), doctorFromDb.getSpeciality()),
                () -> assertEquals(doctorFromDb.getDaySchedule(), doctorFromDb2.getDaySchedule())
        );
    }

    @Test
    void testGetAll() throws ServerException {
        List<DaySchedule> schedule = new ArrayList<>();
        List<Appointment> todayApp = new ArrayList<>();
        todayApp.add(new Appointment(LocalTime.parse("08:00"), 15, null, TimeSlotStatus.FREE));
        List<Appointment> tomorrowApp = new ArrayList<>();
        tomorrowApp.add(new Appointment(LocalTime.parse("09:00"), 15, null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now(), todayApp));
        schedule.add(new DaySchedule(LocalDate.now().plusDays(1), tomorrowApp));

        User user1 = userDao.insert(new User("Имя", "Фамилия", "Отчество", "login1",
                "password1", UserType.DOCTOR));
        Doctor doctor1 = doctorDao.insert(user1, new Doctor("Therapist", "101", schedule));

        User user2 = userDao.insert(new User("Имя", "Фамилия", "Отчество", "login2",
                "password2", UserType.DOCTOR));
        Doctor doctor2 = doctorDao.insert(user2, new Doctor("Allergist", "102", schedule));

        User user3 = userDao.insert(new User("Имя", "Фамилия", "Отчество", "login3",
                "password3", UserType.DOCTOR));
        Doctor doctor3 = doctorDao.insert(user3, new Doctor("Gynecologist", "103", schedule));

        List<Doctor> doctors = doctorDao.getAll();

        for (Doctor doctor : doctors) {
            assertAll(
                    () -> assertNotNull(doctor.getRoom()),
                    () -> assertNotNull(doctor.getDaySchedule()),
                    () -> assertEquals(3, doctors.size())
            );
        }
    }

    @Test
    void testDeleteDoctor() throws ServerException {
        User user1 = userDao.insert(new User("Финеас", "Гейдж", "",
                "JKnenf1234fw1", "zzxcvbnm12335361", UserType.PATIENT));
        Patient patient1 = patientDao.insert(user1, new Patient("fenka1823@gmail.com", "San-Francisco",
                "+79991112233"));

        User user2 = userDao.insert(new User("Финеас", "Гейдж", "",
                "JKnenf1234fw2", "zzxcvbnm12335362", UserType.PATIENT));
        Patient patient2 = patientDao.insert(user2, new Patient("fenka1823@gmail.com", "San-Francisco",
                "+79991112234"));

        User user3 = userDao.insert(new User("Сергей", "Боткин", "Петрович", "klnern3evne1ln",
                "qwerty123456", UserType.DOCTOR));

        List<DaySchedule> schedule = new ArrayList<>();
        List<Appointment> todayApp = new ArrayList<>();
        todayApp.add(new Appointment(LocalTime.parse("08:00"), 15,  null, TimeSlotStatus.FREE));
        todayApp.add(new Appointment(LocalTime.parse("08:15"), 15,  patient1, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now(), todayApp));
        List<Appointment> tomorrowApp = new ArrayList<>();
        tomorrowApp.add(new Appointment(LocalTime.parse("08:00"), 15,  null, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:15"), 15,  null, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:30"), 15,  patient2, TimeSlotStatus.FREE));
        tomorrowApp.add(new Appointment(LocalTime.parse("08:45"), 15, null, TimeSlotStatus.FREE));
        schedule.add(new DaySchedule(LocalDate.now().plusDays(1), tomorrowApp));

        Doctor doctor = doctorDao.insert(user3, new Doctor("Therapist", "103A", schedule));

        List<Integer> list = doctorDao.deleteDoctor(doctor.getId(), LocalDate.now());

        System.out.println(list.size());
    }
}

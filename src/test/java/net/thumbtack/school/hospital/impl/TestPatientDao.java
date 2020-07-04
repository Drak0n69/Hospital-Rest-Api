package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.implementation.CommonDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.PatientDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.UserDaoImpl;
import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.dao.intrface.PatientDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, PatientDaoImpl.class, UserDaoImpl.class})
public class TestPatientDao extends TestBase {

    protected PatientDao patientDao;
    protected UserDao userDao;

    @Autowired
    public TestPatientDao(CommonDao commonDao, PatientDao patientDao, UserDao userDao) {
        super(commonDao);
        this.patientDao = patientDao;
        this.userDao = userDao;
    }

    @Test
    void testInsert() throws ServerException {
        User user = userDao.insert(new User("Финеас", "Гейдж", "",
                "JKnenf1234fw", "zzxcvbnm1233536", UserType.PATIENT));
        Patient patient = patientDao.insert(user, new Patient("fenka1823@gmail.com", "San-Francisco",
                "+79991112233"));
        assertNotEquals(0, patient.getId());
    }

    @Test
    void testFailedInert() throws ServerException {
        User user = userDao.insert(new User("Финеас", "Гейдж", "",
                "JKnenf1234fw", "zzxcvbnm1233536", UserType.PATIENT));
        Patient patient = new Patient("fenka1823@gmail.com", "San-Francisco",
                "+79991112233");
        patientDao.insert(user, patient);
        assertThrows(ServerException.class, () -> patientDao.insert(user, patient));
    }

    @Test
    void testGetFromDb() throws ServerException {
        User user = userDao.insert(new User("Финеас", "Гейдж", null,
                "JKnenf1234fw", "zzxcvbnm1233536", UserType.PATIENT));
        Patient patientToDb = patientDao.insert(user, new Patient("fenka1823@gmail.com", "San-Francisco",
                "+79991112233"));

        Patient patientFromDb = patientDao.getByUser(user);
        Patient patientFromDb2 = patientDao.getById(patientFromDb.getId());

        assertAll(
                () -> assertEquals(patientToDb.getId(), patientFromDb.getId()),
                () -> assertEquals(patientFromDb.getId(), patientFromDb2.getId()),
                () -> assertEquals(patientToDb.getEmail(), patientFromDb.getEmail()),
                () -> assertEquals(patientFromDb.getEmail(), patientFromDb2.getEmail()),
                () -> assertEquals(patientToDb.getTickets(), patientFromDb.getTickets()),
                () -> assertEquals(patientFromDb.getTickets(), patientFromDb2.getTickets())
        );
    }
}

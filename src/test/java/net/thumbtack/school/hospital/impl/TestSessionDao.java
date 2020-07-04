package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.implementation.CommonDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.SessionDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.UserDaoImpl;
import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.dao.intrface.SessionDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Session;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, SessionDaoImpl.class})
public class TestSessionDao extends TestBase {

    protected SessionDao sessionDao;
    protected UserDao userDao;
    protected final String cookie = UUID.randomUUID().toString();

    @Autowired
    public TestSessionDao(CommonDao commonDao, SessionDao sessionDao, UserDao userDao) {
        super(commonDao);
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    @Test
    void testInsert() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        sessionDao.insertSession(new Session(cookie, user));
    }

    @Test
    void testDelete() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        sessionDao.insertSession(new Session(cookie, user));
        sessionDao.deleteSession(cookie);

        assertNull(sessionDao.getByCookie(cookie));
    }

    @Test
    void testGetSession() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        sessionDao.insertSession(new Session(cookie, user));
        Session session = sessionDao.getByCookie(cookie);

        assertEquals(user.getFirstName(), session.getUser().getFirstName());
    }

    @Test
    void testUpdateSession() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        sessionDao.insertSession(new Session(cookie, user));
        String newCookie = UUID.randomUUID().toString();
        sessionDao.insertSession(new Session(newCookie, user));

        assertNotNull(sessionDao.getByCookie(newCookie));
        assertNull(sessionDao.getByCookie(cookie));
    }
}

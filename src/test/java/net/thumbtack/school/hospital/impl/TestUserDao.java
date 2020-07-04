package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.implementation.CommonDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.UserDaoImpl;
import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
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
@Import({CommonDaoImpl.class, UserDaoImpl.class})
public class TestUserDao extends TestBase {

    protected UserDao userDao;

    @Autowired
    public TestUserDao(CommonDao commonDao, UserDao userDao) {
        super(commonDao);
        this.userDao = userDao;
    }

    @Test
    void testInset() throws ServerException {
        User user = new User("Петр", "Петров", "Петрович",
                "login", "pass1", UserType.ADMIN);
        userDao.insert(user);

        assertNotEquals(0, user.getId());
    }

    @Test
    void testFailedInsert() {
        User user = new User(null, "Петров", "Петрович",
                "login", "pass1", UserType.ADMIN);
        try {
            userDao.insert(user);
            fail();
        } catch (ServerException ex) {
            assertEquals(ex.getError(), ServerError.SQL_SERVER_ERROR);
        }
    }

    @Test
    void testFailedInsert2() throws ServerException {
        userDao.insert(new User("Петр", "Петров", "Петрович",
                "login", "pass1", UserType.ADMIN));
        try {
            userDao.insert(new User("Семён", "Семёнов", "Семёнович",
                    "login", "pass2", UserType.DOCTOR));
            fail();
        } catch (ServerException ex) {
            assertEquals(ex.getError(), ServerError.USER_SAME_LOGIN);
        }
    }

    @Test
    void testGetById() throws ServerException {
        User userToDb = new User("Петр", "Петров", "Петрович",
                "login", "pass1", UserType.ADMIN);
        userDao.insert(userToDb);
        User userFromDb = userDao.getById(userToDb.getId());

        assertEquals(userToDb.getLogin(), userFromDb.getLogin());
    }

    @Test
    void testFailedGetById() {
        try {
            userDao.getById(3);
        } catch (ServerException ex) {
            assertEquals(ex.getError(), ServerError.SQL_SERVER_ERROR);
        }
    }

    @Test
    void testGetByLogin() throws ServerException {
        User userToDb = userDao.insert(new User("Петр", "Петров", "Петрович",
                "login", "pass1", UserType.ADMIN));
        User userFromDb = userDao.getByLogin("login");
        assertEquals(userToDb.getId(), userFromDb.getId());
    }

    @Test
    void testFailedGetByLogin() {
        try {
            userDao.getByLogin("notLogin");
        } catch (ServerException ex) {
            assertEquals(ex.getError(), ServerError.SQL_SERVER_ERROR);
        }
    }
}

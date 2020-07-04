package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.implementation.AdminDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.CommonDaoImpl;
import net.thumbtack.school.hospital.database.dao.implementation.UserDaoImpl;
import net.thumbtack.school.hospital.database.dao.intrface.AdminDao;
import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Admin;
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
@Import({CommonDaoImpl.class, AdminDaoImpl.class, UserDaoImpl.class})
public class TestAdminDao extends TestBase {

    protected AdminDao adminDao;
    protected UserDao userDao;

    @Autowired
    public TestAdminDao(CommonDao commonDao, AdminDao adminDao, UserDao userDao) {
        super(commonDao);
        this.adminDao = adminDao;
        this.userDao = userDao;
    }

    @Test
    void testInsert() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        Admin admin = adminDao.insert(user, new Admin("User's Admin"));

        assertNotEquals(0, admin.getId());
    }

    @Test
    void testFailedInsert() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        Admin admin = new Admin("User's Admin");
        adminDao.insert(user, admin);

        assertThrows(ServerException.class, () -> adminDao.insert(user, admin));
    }

    @Test
    void testGetById() throws ServerException {
        User user = userDao.insert(new User("Олег", "Петров", "Иванович", "nl2mfk2mlkmflk",
                "n2n3nlknlkn23ln4", UserType.ADMIN));
        Admin admin = adminDao.insert(user, new Admin("User's Admin"));

        Admin adminFromDb = adminDao.getByUser(user);

        assertAll(
                () -> assertEquals(admin.getPosition(), adminFromDb.getPosition()),
                () -> assertEquals(user.getLogin(), adminFromDb.getLogin())
        );
    }
}

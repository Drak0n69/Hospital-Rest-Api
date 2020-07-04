package net.thumbtack.school.hospital.database.dao.implementation;

import net.thumbtack.school.hospital.database.dao.intrface.AdminDao;
import net.thumbtack.school.hospital.database.mappers.AdminMapper;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class AdminDaoImpl implements AdminDao {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    private AdminMapper adminMapper;

    @Autowired
    public AdminDaoImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin insert(User user, Admin admin) throws ServerException {
        LOGGER.debug("DAO insert admin {} {}", user, admin);
        try {
            adminMapper.insert(user, admin);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert admin {} {}!", admin, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
        return admin;
    }

    @Override
    public Admin getById(int id) throws ServerException {
        LOGGER.debug("DAO get admin by id {}", id);
        try {
            return adminMapper.getById(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get admin by id {} {}!", id, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public Admin getByUser(User user) throws ServerException {
        LOGGER.debug("DAO get admin by user id {}", user.getId());
        try {
            return adminMapper.getByUserId(user.getId());
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get admin by user id {} {}!", user.getId(), ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }
}

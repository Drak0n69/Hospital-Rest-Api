package net.thumbtack.school.hospital.database.dao.implementation;

import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.User;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private UserMapper userMapper;

    @Autowired
    public UserDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User insert(User user) throws ServerException {
        LOGGER.debug("DAO insert user {}", user);
        try {
            userMapper.insert(user);
        } catch (DataAccessException ex) {
            if (ex.getClass() == DuplicateKeyException.class) {
                throw new ServerException(ServerError.USER_SAME_LOGIN, user.getLogin());
            }
            LOGGER.info("Can't insert user {} {}!", user, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
        return user;
    }

    @Override
    public User getByLogin(String login) throws ServerException {
        LOGGER.debug("DAO select user by login {}", login);
        try {
            return userMapper.getByLogin(login);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't select user by login {}!", login);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public User getById(int id) throws ServerException {
        LOGGER.debug("DAO select user by id {}", id);
        try {
            return userMapper.getById(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't select user by id {}!", id);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public void changePassword(User user) throws ServerException {
        LOGGER.debug("DAO change password for user {}", user);
        try {
            userMapper.changePassword(user);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't change password for user {}!", user);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }
}

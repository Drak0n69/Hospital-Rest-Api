package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.User;

public interface UserDao {

    User insert(User user) throws ServerException;

    User getByLogin(String login) throws ServerException;

    User getById(int id) throws ServerException;

    void changePassword(User user) throws ServerException;
}

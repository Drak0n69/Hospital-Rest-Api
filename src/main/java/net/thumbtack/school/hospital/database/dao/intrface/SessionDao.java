package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Session;

public interface SessionDao {

    void insertSession(Session session) throws ServerException;

    void deleteSession(String cookie) throws ServerException;

    Session getByCookie(String cookie) throws ServerException;
}

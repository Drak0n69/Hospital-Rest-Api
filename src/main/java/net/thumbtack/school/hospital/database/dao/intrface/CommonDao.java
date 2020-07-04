package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;

public interface CommonDao {

    void clearDB() throws ServerException;
}

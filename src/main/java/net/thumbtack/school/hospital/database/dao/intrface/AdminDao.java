
package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;

public interface AdminDao {

    Admin insert(User user, Admin admin) throws ServerException;

    Admin getById(int id) throws ServerException;

    Admin getByUser(User user) throws ServerException;
}

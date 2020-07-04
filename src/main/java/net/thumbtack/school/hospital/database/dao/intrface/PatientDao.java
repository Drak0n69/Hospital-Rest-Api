package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Ticket;
import net.thumbtack.school.hospital.model.User;

import java.util.List;

public interface PatientDao {

    Patient insert(User user, Patient patient) throws ServerException;

    Patient getById(int id) throws ServerException;

    Patient getByUser(User uer) throws ServerException;

    List<Ticket> getTicketsById(int id) throws ServerException;
}

package net.thumbtack.school.hospital.database.dao.intrface;

import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.DaySchedule;
import net.thumbtack.school.hospital.model.Doctor;
import net.thumbtack.school.hospital.model.User;

import java.time.LocalDate;
import java.util.List;

public interface DoctorDao {

    Doctor insert(User user, Doctor doctor) throws ServerException;

    Doctor getById(int id) throws ServerException;

    Doctor getByUser(User user) throws ServerException;

    List<Doctor> getAll() throws ServerException;

    Doctor changeSchedule(Doctor doctor, List<DaySchedule> newSchedule) throws ServerException;

    List<Integer> deleteDoctor(int id, LocalDate disDate) throws ServerException;
}

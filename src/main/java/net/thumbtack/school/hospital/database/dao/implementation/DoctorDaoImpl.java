package net.thumbtack.school.hospital.database.dao.implementation;

import net.thumbtack.school.hospital.database.dao.intrface.DoctorDao;
import net.thumbtack.school.hospital.database.dao.intrface.UserDao;
import net.thumbtack.school.hospital.database.mappers.DoctorMapper;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DoctorDaoImpl implements DoctorDao {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    private DoctorMapper doctorMapper;

    @Autowired
    public DoctorDaoImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public Doctor insert(User user, Doctor doctor) throws ServerException {
        LOGGER.debug("DAO insert doctor {} {}", user, doctor);
        try {
            doctorMapper.insertDoctor(user, doctor);
            for (DaySchedule daySchedule : doctor.getDaySchedule()) {
                doctorMapper.insertSchedule(doctor, daySchedule);
                for (Appointment appointment : daySchedule.getAppointments()) {
                    doctorMapper.insertAppointments(daySchedule, appointment);
                }
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert doctor {} {}!", doctor, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
        return doctor;
    }

    @Override
    public Doctor getById(int id) throws ServerException {
        LOGGER.debug("DAO get doctor by id {}", id);
        try {
            return doctorMapper.getById(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get doctor by id {} {}!", id, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public Doctor getByUser(User user) throws ServerException {
        LOGGER.debug("DAO get doctor by user id {}", user.getId());
        try {
            return doctorMapper.getByUserId(user.getId());
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get doctor by user id {} {}!", user.getId(), ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public List<Doctor> getAll() throws ServerException {
        LOGGER.debug("DAO get all doctors");
        try {
            return doctorMapper.getAll();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get all doctors!",  ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public Doctor changeSchedule(Doctor doctor, List<DaySchedule> newSchedule) throws ServerException {
        return null;
    }

    @Override
    public List<Integer> deleteDoctor(int id, LocalDate disDate) throws ServerException {
        LOGGER.debug("DAO delete doctor by id {}", id);
        try {
            List<Integer> patientsId = doctorMapper.getPatientsId(id, disDate);
            return patientsId;
        } catch (DataAccessException ex) {
            LOGGER.info("Can't delete doctor by id! {}, {}", id, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }
}

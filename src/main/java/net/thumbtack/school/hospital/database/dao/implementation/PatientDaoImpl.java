package net.thumbtack.school.hospital.database.dao.implementation;

import net.thumbtack.school.hospital.database.dao.intrface.PatientDao;
import net.thumbtack.school.hospital.database.mappers.PatientMapper;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.Ticket;
import net.thumbtack.school.hospital.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientDaoImpl implements PatientDao {

    private final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    private PatientMapper patientMapper;

    @Autowired
    public PatientDaoImpl(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient insert(User user, Patient patient) throws ServerException {
        LOGGER.debug("DAO insert patient {} {}", user, patient);
        try {
            patientMapper.insert(user, patient);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert patient {} {}!", patient, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
        return patient;
    }

    @Override
    public Patient getByUser(User user) throws ServerException {
        LOGGER.debug("DAO get patient by user id {}", user.getId());
        try {
            return patientMapper.getByUserId(user.getId());
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get patient by user id {} {}!", user.getId(), ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public Patient getById(int id) throws ServerException {
        LOGGER.debug("DAO get patient by id {}", id);
        try {
            return patientMapper.getById(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get patient by id {} {}!", id, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }

    @Override
    public List<Ticket> getTicketsById(int id) throws ServerException {
        LOGGER.debug("DAO get all patient's tickets by id {}", id);
        try {
            return patientMapper.getTicketsById(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get patient's tickets by id {} {}!", id, ex);
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }
}

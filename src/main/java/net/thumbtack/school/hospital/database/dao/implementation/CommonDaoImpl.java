package net.thumbtack.school.hospital.database.dao.implementation;

import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.database.mappers.*;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDaoImpl implements CommonDao {

    private final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    private UserMapper userMapper;
    private AdminMapper adminMapper;
    private DoctorMapper doctorMapper;
    private PatientMapper patientMapper;
    private SessionMapper sessionMapper;

    @Autowired
    public CommonDaoImpl(UserMapper userMapper, AdminMapper adminMapper, DoctorMapper doctorMapper, PatientMapper patientMapper,
                         SessionMapper sessionMapper) {
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.doctorMapper = doctorMapper;
        this.patientMapper = patientMapper;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public void clearDB() throws ServerException {
        LOGGER.debug("DAO clear DataBase!");
        try {
            userMapper.deleteAll();
            adminMapper.deleteAll();
            doctorMapper.deleteAll();
            patientMapper.deleteAll();
            sessionMapper.deleteAll();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't clear Database! Cause: {}", ExceptionUtils.getStackTrace(ex));
            throw new ServerException(ServerError.SQL_SERVER_ERROR);
        }
    }
}

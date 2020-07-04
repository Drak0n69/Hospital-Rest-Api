package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.request.PatientChangeProfileRequestDto;
import net.thumbtack.school.hospital.dto.request.PatientRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.PatientProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Patient;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class PatientService extends ServiceBase {

    private PatientDao patientDao;

    @Autowired
    public PatientService(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao,
                          UserDao userDao, PatientDao patientDao1) {
        super(sessionDao, adminDao, doctorDao, patientDao, userDao);
        this.patientDao = patientDao1;
    }

    public PatientProfileResponseDto registerPatient(PatientRegisterRequestDto requestDto, String cookie) throws ServerException {
        User user = insertUserIntoDb(requestDto);
        String phone = requestDto.getPhone().replaceAll("[\\-\\s]", "").trim();
        Patient patient = new Patient(requestDto.getEmail(), requestDto.getAddress(), phone);
        patientDao.insert(user, patient);
        return createPatientProfileDto(patient);
    }

    public PatientProfileResponseDto getPatientInfo(String cookie, int id) throws ServerException {
        User user = getSessionByCookie(cookie).getUser();
        if (user.getUserType() == UserType.PATIENT) {
            Patient patient = patientDao.getByUser(user);
            return createPatientProfileDto(patient);
        }
        Patient patient = patientDao.getById(id);
        return createPatientProfileDto(patient);
    }

    public PatientProfileResponseDto changePassword(String cookie, PatientChangeProfileRequestDto patientChangeProfileRequestDto) throws ServerException {
        User user = getSessionByCookie(cookie).getUser();
        if (!user.getPassword().equalsIgnoreCase(patientChangeProfileRequestDto.getPassword())) {
            throw new ServerException(ServerError.INVALID_USER_PASSWORD);
        }
        user.setPassword(patientChangeProfileRequestDto.getNewPassword());
        userDao.changePassword(user);
        Patient patient = patientDao.getByUser(user);
        return createPatientProfileDto(patient);
    }
}

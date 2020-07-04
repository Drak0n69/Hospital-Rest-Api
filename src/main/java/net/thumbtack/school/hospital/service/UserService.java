package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.request.LoginRequestDto;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.HospitalSettingsResponseDto;
import net.thumbtack.school.hospital.dto.response.UserProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.*;
import net.thumbtack.school.hospital.model.enums.UserType;
import net.thumbtack.school.hospital.util.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = DataAccessException.class)
public class UserService extends ServiceBase {

    private Settings settings;

    @Autowired
    public UserService(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao,
                       UserDao userDao, Settings settings) {
        super(sessionDao, adminDao, doctorDao, patientDao, userDao);
        this.settings = settings;
    }

    public UserProfileResponseDto login(LoginRequestDto loginDto, String newCookie) throws ServerException {
        User user = getUserFromDb(loginDto.getLogin(), loginDto.getPassword());
        insertSession(newCookie, user);
        return getUserProfile(user);
    }

    public EmptyResponse logout(String cookie) throws ServerException {
        sessionDao.deleteSession(cookie);
        return new EmptyResponse();
    }

    public HospitalSettingsResponseDto getServerSettings(String cookie) throws ServerException {
        if (cookie != null) {
            User user = getSessionByCookie(cookie).getUser();
            switch (user.getUserType()) {
                case ADMIN:
                case PATIENT:
                case DOCTOR:
                    return new HospitalSettingsResponseDto(settings.getMaxNameLength(), settings.getMinPasswordLength());
            }
        }
        return new HospitalSettingsResponseDto(settings.getMaxNameLength(), settings.getMinPasswordLength());
    }

    public UserProfileResponseDto getAccountInfo(String cookie) throws ServerException {
        if (cookie != null) {
            User user = getSessionByCookie(cookie).getUser();
            return getUserProfile(user);
        }
        throw new ServerException(ServerError.MISSING_COOKIE);
    }
}

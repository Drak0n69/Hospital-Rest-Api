package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.request.AdminChangeProfileRequestDto;
import net.thumbtack.school.hospital.dto.request.AdminRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.AdminProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.Admin;
import net.thumbtack.school.hospital.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class AdminService extends ServiceBase {

    private AdminDao adminDao;

    @Autowired
    public AdminService(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao,
                        UserDao userDao, AdminDao adminDao1) {
        super(sessionDao, adminDao, doctorDao, patientDao, userDao);
        this.adminDao = adminDao1;
    }

    public AdminProfileResponseDto registerAdmin(AdminRegisterRequestDto requestDto, String cookie) throws ServerException {
        checkIfAdmin(cookie);
        User user = insertUserIntoDb(requestDto);
        Admin admin = new Admin(requestDto.getPosition());
        adminDao.insert(user, admin);
        return createAdminProfileDto(admin);
    }

    public AdminProfileResponseDto changePassword(String cookie, AdminChangeProfileRequestDto adminChangeProfileRequestDto) throws ServerException {
        checkIfAdmin(cookie);
        User user = getSessionByCookie(cookie).getUser();
        if (!user.getPassword().equalsIgnoreCase(adminChangeProfileRequestDto.getPassword())) {
            throw new ServerException(ServerError.INVALID_USER_PASSWORD);
        }
        user.setPassword(adminChangeProfileRequestDto.getNewPassword());
        userDao.changePassword(user);
        Admin admin = adminDao.getByUser(user);
        return createAdminProfileDto(admin);
    }
}

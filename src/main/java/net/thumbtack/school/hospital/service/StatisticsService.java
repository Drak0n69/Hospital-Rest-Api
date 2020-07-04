package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.intrface.*;
import net.thumbtack.school.hospital.dto.response.HospitalSettingsResponseDto;
import net.thumbtack.school.hospital.dto.response.UserProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.model.User;
import net.thumbtack.school.hospital.model.enums.UserType;
import net.thumbtack.school.hospital.util.Settings;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService extends ServiceBase {

    private StatisticDao statisticDao;

    public StatisticsService(SessionDao sessionDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao,
                             UserDao userDao, Settings settings, StatisticDao statisticDao) {
        super(sessionDao, adminDao, doctorDao, patientDao, userDao);
        this.statisticDao = statisticDao;
    }
}

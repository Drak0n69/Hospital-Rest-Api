package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.HospitalSettingsResponseDto;
import net.thumbtack.school.hospital.dto.response.UserProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.service.StatisticsService;
import net.thumbtack.school.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class StatisticsController {
}

package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.HospitalSettingsResponseDto;
import net.thumbtack.school.hospital.dto.response.UserProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerException;
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
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public HospitalSettingsResponseDto getSettings(@CookieValue(name = "JAVASESSIONID", required = false)
                                                           String cookie) throws ServerException {
        return userService.getServerSettings(cookie);
    }

    @GetMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileResponseDto getAccountInfo(@CookieValue("JAVASESSIONID") String cookie) throws ServerException {
        return userService.getAccountInfo(cookie);
    }
}

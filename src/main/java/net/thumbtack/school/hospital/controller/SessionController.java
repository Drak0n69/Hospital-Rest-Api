package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.LoginRequestDto;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.dto.response.UserProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private UserService userService;

    @Autowired
    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileResponseDto loginUser(@RequestBody @Valid LoginRequestDto loginDto,
                                            HttpServletResponse response) throws ServerException {
        String cookie = UUID.randomUUID().toString();
        UserProfileResponseDto profileDto = userService.login(loginDto, cookie);
        response.addCookie(new Cookie("JAVASESSIONID", cookie));
        return profileDto;
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse logoutUser(@CookieValue("JAVASESSIONID") String cookie) throws ServerException {
        return userService.logout(cookie);
    }
}

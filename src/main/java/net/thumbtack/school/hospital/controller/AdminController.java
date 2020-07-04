package net.thumbtack.school.hospital.controller;


import net.thumbtack.school.hospital.dto.request.AdminChangeProfileRequestDto;
import net.thumbtack.school.hospital.dto.request.AdminRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.AdminProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
@Validated
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminProfileResponseDto registerAdmin(
            @CookieValue("JAVASESSIONID") String cookie, @RequestBody @Valid AdminRegisterRequestDto requestDto
    ) throws ServerException {
        return adminService.registerAdmin(requestDto, cookie);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminProfileResponseDto changePassword(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestBody @Valid AdminChangeProfileRequestDto adminChangeProfileRequestDto,
            HttpServletResponse response
    ) throws ServerException {
        AdminProfileResponseDto adminProfileResponseDto = adminService.changePassword(cookie, adminChangeProfileRequestDto);
        response.addCookie(new Cookie("JAVASESSIONID", cookie));
        return adminProfileResponseDto;
    }
}

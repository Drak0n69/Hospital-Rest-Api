package net.thumbtack.school.hospital.controller;


import net.thumbtack.school.hospital.dto.request.PatientChangeProfileRequestDto;
import net.thumbtack.school.hospital.dto.request.PatientRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.PatientProfileResponseDto;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@Validated
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientProfileResponseDto registerPatient(
            @RequestBody @Valid PatientRegisterRequestDto requestDto, HttpServletResponse response
    ) throws ServerException {
        String cookie = UUID.randomUUID().toString();
        PatientProfileResponseDto responseDto = patientService.registerPatient(requestDto, cookie);
        response.addCookie(new Cookie("JAVASESSIONID", cookie));
        return responseDto;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PatientProfileResponseDto getPatientInfo(
            @PathVariable("id") int id,
            @CookieValue("JAVASESSIONID") String cookie
    ) throws ServerException {
        return patientService.getPatientInfo(cookie, id);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PatientProfileResponseDto changePassword(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestBody @Valid PatientChangeProfileRequestDto patientChangeProfileRequestDto,
            HttpServletResponse response
    ) throws ServerException {
        PatientProfileResponseDto patientProfileResponseDto = patientService.changePassword(cookie, patientChangeProfileRequestDto);
        response.addCookie(new Cookie("JAVASESSIONID", cookie));
        return patientProfileResponseDto;
    }
}

package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.request.DoctorChangeScheduleRequestDto;
import net.thumbtack.school.hospital.dto.request.DoctorDismissalRequestDto;
import net.thumbtack.school.hospital.dto.request.DoctorRegisterRequestDto;
import net.thumbtack.school.hospital.dto.response.DoctorProfileResponseDto;
import net.thumbtack.school.hospital.dto.response.EmptyResponse;
import net.thumbtack.school.hospital.error.ServerException;
import net.thumbtack.school.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Validated
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorProfileResponseDto registerDoctor(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestBody @Valid DoctorRegisterRequestDto requestDto
    ) throws ServerException {
        return doctorService.registerDoctor(requestDto, cookie);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DoctorProfileResponseDto getDoctorInfo(
            @CookieValue("JAVASESSIONID") String cookie,
            @PathVariable("id") int id,
            @RequestParam(name = "schedule", defaultValue = "no") String schedule,
            @RequestParam(name = "startDate", defaultValue = "") String startDate,
            @RequestParam(name = "endDate", defaultValue = "") String endDate
    ) throws ServerException {
        return doctorService.getDoctorInfo(cookie, id, schedule, startDate, endDate);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DoctorProfileResponseDto> getAllDoctors(
            @CookieValue("JAVASESSIONID") String cookie,
            @RequestParam(name = "schedule", defaultValue = "no") String schedule,
            @RequestParam(name = "speciality", defaultValue = "") String speciality,
            @RequestParam(name = "startDate", defaultValue = "") String startDate,
            @RequestParam(name = "endDate", defaultValue = "") String endDate
    ) throws ServerException {
        return doctorService.getAllBySpeciality(cookie, schedule, speciality, startDate, endDate);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DoctorProfileResponseDto changeDoctorSchedule(
            @CookieValue("JAVASESSIONID") String cookie,
            @PathVariable("id") int id,
            @RequestBody @Valid DoctorChangeScheduleRequestDto requestDto
    ) throws ServerException {
        return doctorService.changeSchedule(cookie, id, requestDto);
    }

    @DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse deleteDoctor(
            @CookieValue("JAVASESSIONID") String cookie,
            @PathVariable("id") int id,
            @RequestBody @Valid DoctorDismissalRequestDto requestDto
    ) throws ServerException {
        return doctorService.deleteDoctor(cookie, id, requestDto);
    }
}

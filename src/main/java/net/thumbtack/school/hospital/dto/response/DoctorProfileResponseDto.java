package net.thumbtack.school.hospital.dto.response;

import java.util.List;

public class DoctorProfileResponseDto extends UserProfileResponseDto {

    private String speciality;
    private String room;
    private List<DayScheduleResponseDto> scheduleList;

    public DoctorProfileResponseDto(int id, String firstName, String lastName, String patronymic, String speciality,
                                    String room) {
        super(id, firstName, lastName, patronymic);
        this.speciality = speciality;
        this.room = room;
    }

    public DoctorProfileResponseDto(int id, String firstName, String lastName, String patronymic, String speciality,
                                    String room, List<DayScheduleResponseDto> scheduleList) {
        this(id, firstName, lastName, patronymic, speciality, room);
        this.scheduleList = scheduleList;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<DayScheduleResponseDto> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<DayScheduleResponseDto> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public String toString() {
        return "DoctorProfileResponseDto{" +
                "speciality='" + speciality + '\'' +
                ", room='" + room + '\'' +
                ", scheduleList=" + scheduleList +
                '}';
    }
}

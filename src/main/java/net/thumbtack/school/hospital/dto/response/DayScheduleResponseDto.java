package net.thumbtack.school.hospital.dto.response;

import java.util.List;


public class DayScheduleResponseDto {

    private String date;
    private List<AppointmentDto> appointments;

    public DayScheduleResponseDto(String date, List<AppointmentDto> appointments) {
        this.date = date;
        this.appointments = appointments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<AppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayScheduleResponseDto)) return false;

        DayScheduleResponseDto dto = (DayScheduleResponseDto) o;

        if (getDate() != null ? !getDate().equals(dto.getDate()) : dto.getDate() != null) return false;
        return getAppointments() != null ? getAppointments().equals(dto.getAppointments()) : dto.getAppointments() == null;
    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getAppointments() != null ? getAppointments().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DayScheduleResponseDto{" +
                "date=" + date +
                ", appointments=" + appointments +
                '}';
    }
}

package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Date;
import net.thumbtack.school.hospital.dto.validation.Time;
import net.thumbtack.school.hospital.model.enums.UserType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class DoctorRegisterRequestDto extends UserRegisterRequestDto {

    @NotNull
    private String speciality;
    @NotNull
    private String room;
    @Date
    private String dateStart;
    @Date
    private String dateEnd;
    @Time
    private int duration;
    private WeekScheduleRequestDto weekSchedule;
    private List<DayScheduleRequestDto> daySchedule;

    public DoctorRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password
            , UserType userType, String dateStart, String dateEnd) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public DoctorRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                    UserType userType, String dateStart, String dateEnd, String speciality, String room,
                                    int duration, WeekScheduleRequestDto weekSchedule) {
        this(firstName, lastName, patronymic, login, password, userType, dateStart, dateEnd);
        this.speciality = speciality;
        this.room = room;
        this.duration = duration;
        this.weekSchedule = weekSchedule;
    }

    public DoctorRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                    UserType userType, String dateStart, String dateEnd, String speciality, String room,
                                    int duration, List<DayScheduleRequestDto> daySchedule) {
        this(firstName, lastName, patronymic, login, password, userType, dateStart, dateEnd);
        this.speciality = speciality;
        this.room = room;
        this.duration = duration;
        this.daySchedule = daySchedule;
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public WeekScheduleRequestDto getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekScheduleRequestDto weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public List<DayScheduleRequestDto> getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(List<DayScheduleRequestDto> daySchedule) {
        this.daySchedule = daySchedule;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getSpeciality() != null ? getSpeciality().hashCode() : 0);
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        result = 31 * result + (getDateStart() != null ? getDateStart().hashCode() : 0);
        result = 31 * result + (getDateEnd() != null ? getDateEnd().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + (getWeekSchedule() != null ? getWeekSchedule().hashCode() : 0);
        result = 31 * result + (getDaySchedule() != null ? getDaySchedule().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DoctorRegisterRequestDto{" +
                "speciality='" + speciality + '\'' +
                ", room='" + room + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", duration=" + duration +
                ", weekSchedule=" + weekSchedule +
                ", daySchedule=" + daySchedule +
                '}';
    }
}

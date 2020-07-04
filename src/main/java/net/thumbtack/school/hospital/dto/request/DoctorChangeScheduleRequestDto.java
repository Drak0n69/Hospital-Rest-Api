package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Date;
import net.thumbtack.school.hospital.dto.validation.Time;

import java.util.List;

public class DoctorChangeScheduleRequestDto {

    @Date
    private String dateStart;
    @Date
    private String dateEnd;
    @Time
    private int duration;
    private WeekScheduleRequestDto weekSchedule;
    private List<DayScheduleRequestDto> daySchedule;

    public DoctorChangeScheduleRequestDto(String dateStart, String dateEnd, int duration, WeekScheduleRequestDto weekSchedule) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.weekSchedule = weekSchedule;
    }

    public DoctorChangeScheduleRequestDto(String dateStart, String dateEnd, int duration, List<DayScheduleRequestDto> daySchedule) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.daySchedule = daySchedule;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorChangeScheduleRequestDto)) return false;

        DoctorChangeScheduleRequestDto that = (DoctorChangeScheduleRequestDto) o;

        if (getDuration() != that.getDuration()) return false;
        if (getDateStart() != null ? !getDateStart().equals(that.getDateStart()) : that.getDateStart() != null)
            return false;
        if (getDateEnd() != null ? !getDateEnd().equals(that.getDateEnd()) : that.getDateEnd() != null) return false;
        if (getWeekSchedule() != null ? !getWeekSchedule().equals(that.getWeekSchedule()) : that.getWeekSchedule() != null)
            return false;
        return getDaySchedule() != null ? getDaySchedule().equals(that.getDaySchedule()) : that.getDaySchedule() == null;
    }

    @Override
    public int hashCode() {
        int result = getDateStart() != null ? getDateStart().hashCode() : 0;
        result = 31 * result + (getDateEnd() != null ? getDateEnd().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + (getWeekSchedule() != null ? getWeekSchedule().hashCode() : 0);
        result = 31 * result + (getDaySchedule() != null ? getDaySchedule().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DoctorChangeScheduleRequestDto{" +
                "dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", duration=" + duration +
                ", weekSchedule=" + weekSchedule +
                ", daySchedule=" + daySchedule +
                '}';
    }
}

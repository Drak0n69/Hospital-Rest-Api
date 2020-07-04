package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Time;
import net.thumbtack.school.hospital.dto.validation.WeekDay;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class DayScheduleRequestDto {

    @WeekDay
    private String weekDay;
    @Time
    private String timeStart;
    @Time
    private String timeEnd;

    public DayScheduleRequestDto(String weekDay, String timeStart, String timeEnd) {
        this.weekDay = weekDay;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayScheduleRequestDto)) return false;

        DayScheduleRequestDto that = (DayScheduleRequestDto) o;

        if (getWeekDay() != null ? !getWeekDay().equals(that.getWeekDay()) : that.getWeekDay() != null) return false;
        if (getTimeStart() != null ? !getTimeStart().equals(that.getTimeStart()) : that.getTimeStart() != null)
            return false;
        return getTimeEnd() != null ? getTimeEnd().equals(that.getTimeEnd()) : that.getTimeEnd() == null;
    }

    @Override
    public int hashCode() {
        int result = getWeekDay() != null ? getWeekDay().hashCode() : 0;
        result = 31 * result + (getTimeStart() != null ? getTimeStart().hashCode() : 0);
        result = 31 * result + (getTimeEnd() != null ? getTimeEnd().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DayScheduleRequestDto{" +
                "weekDay='" + weekDay + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}

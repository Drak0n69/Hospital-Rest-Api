package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Time;
import net.thumbtack.school.hospital.dto.validation.WeekDay;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

public class WeekScheduleRequestDto {

    @Time
    private String timeStart;
    @Time
    private String timeEnd;
    @WeekDay
    private List<String> weekDays;

    public WeekScheduleRequestDto(String timeStart, String timeEnd, List<String> weekDays) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.weekDays = weekDays;
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

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekScheduleRequestDto)) return false;

        WeekScheduleRequestDto that = (WeekScheduleRequestDto) o;

        if (getTimeStart() != null ? !getTimeStart().equals(that.getTimeStart()) : that.getTimeStart() != null)
            return false;
        if (getTimeEnd() != null ? !getTimeEnd().equals(that.getTimeEnd()) : that.getTimeEnd() != null) return false;
        return getWeekDays() != null ? getWeekDays().equals(that.getWeekDays()) : that.getWeekDays() == null;
    }

    @Override
    public int hashCode() {
        int result = getTimeStart() != null ? getTimeStart().hashCode() : 0;
        result = 31 * result + (getTimeEnd() != null ? getTimeEnd().hashCode() : 0);
        result = 31 * result + (getWeekDays() != null ? getWeekDays().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeekScheduleRequestDto{" +
                "timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", weekDays=" + weekDays +
                '}';
    }
}

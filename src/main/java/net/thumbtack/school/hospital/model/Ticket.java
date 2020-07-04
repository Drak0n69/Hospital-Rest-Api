package net.thumbtack.school.hospital.model;

import net.thumbtack.school.hospital.model.enums.TimeSlotStatus;

import java.time.LocalTime;

public class Ticket {

    private int id;
    private String number;
    private int dayScheduleId;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private int commissionId;
    private TimeSlotStatus status;

    public Ticket() {
    }

    public Ticket(int id, String number, LocalTime timeStart, LocalTime timeEnd, int commissionId, TimeSlotStatus status) {
        this.id = id;
        this.number = number;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.commissionId = commissionId;
        this.status = status;
    }

    public Ticket(int id, String number, int dayScheduleId, LocalTime timeStart, LocalTime timeEnd, TimeSlotStatus status) {
        this.id = id;
        this.number = number;
        this.dayScheduleId = dayScheduleId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDayScheduleId() {
        return dayScheduleId;
    }

    public void setDayScheduleId(int dayScheduleId) {
        this.dayScheduleId = dayScheduleId;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(int commissionId) {
        this.commissionId = commissionId;
    }

    public TimeSlotStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSlotStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (getId() != ticket.getId()) return false;
        if (getDayScheduleId() != ticket.getDayScheduleId()) return false;
        if (getCommissionId() != ticket.getCommissionId()) return false;
        if (getNumber() != null ? !getNumber().equals(ticket.getNumber()) : ticket.getNumber() != null) return false;
        if (getTimeStart() != null ? !getTimeStart().equals(ticket.getTimeStart()) : ticket.getTimeStart() != null)
            return false;
        if (getTimeEnd() != null ? !getTimeEnd().equals(ticket.getTimeEnd()) : ticket.getTimeEnd() != null)
            return false;
        return getStatus() == ticket.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + getDayScheduleId();
        result = 31 * result + (getTimeStart() != null ? getTimeStart().hashCode() : 0);
        result = 31 * result + (getTimeEnd() != null ? getTimeEnd().hashCode() : 0);
        result = 31 * result + getCommissionId();
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", dayScheduleId=" + dayScheduleId +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", commissionId=" + commissionId +
                ", status=" + status +
                '}';
    }
}

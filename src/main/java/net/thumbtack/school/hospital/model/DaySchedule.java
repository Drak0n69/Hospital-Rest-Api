package net.thumbtack.school.hospital.model;

import java.time.LocalDate;
import java.util.List;

public class DaySchedule {

    private int id;
    private LocalDate date;
    private List<Appointment> appointments;


    public DaySchedule() {
    }

    public DaySchedule(LocalDate date, List<Appointment> appointments) {
        this.date = date;
        this.appointments = appointments;
    }

    public DaySchedule(int id, LocalDate date, List<Appointment> appointments) {
        this(date, appointments);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaySchedule)) return false;

        DaySchedule daySchedule = (DaySchedule) o;

        if (getId() != daySchedule.getId()) return false;
        if (getAppointments() != null ? !getAppointments().equals(daySchedule.getAppointments()) : daySchedule.getAppointments() != null)
            return false;
        return getDate() != null ? getDate().equals(daySchedule.getDate()) : daySchedule.getDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getAppointments() != null ? getAppointments().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DayScheduleRequestDto{" +
                "id=" + id +
                ", date=" + date +
                ", appointments=" + appointments +
                '}';
    }
}

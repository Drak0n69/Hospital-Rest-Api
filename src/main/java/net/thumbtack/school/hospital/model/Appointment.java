package net.thumbtack.school.hospital.model;

import net.thumbtack.school.hospital.model.enums.TimeSlotStatus;

import java.time.LocalTime;

public class Appointment {

    private int id;
    private LocalTime slot;
    private int duration;
    private Patient patient;
    private TimeSlotStatus status;

    public Appointment() {
    }

    public Appointment(LocalTime slot, int duration, Patient patient, TimeSlotStatus status) {
        this.slot = slot;
        this.duration = duration;
        this.patient = patient;
        this.status = status;
    }

    public Appointment(int id, LocalTime slot, int duration, Patient patient, TimeSlotStatus status) {
        this(slot, duration, patient, status);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getSlot() {
        return slot;
    }

    public void setSlot(LocalTime slot) {
        this.slot = slot;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
        if (!(o instanceof Appointment)) return false;

        Appointment that = (Appointment) o;

        if (getId() != that.getId()) return false;
        if (getDuration() != that.getDuration()) return false;
        if (getSlot() != null ? !getSlot().equals(that.getSlot()) : that.getSlot() != null) return false;
        if (getPatient() != null ? !getPatient().equals(that.getPatient()) : that.getPatient() != null) return false;
        return getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getSlot() != null ? getSlot().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + (getPatient() != null ? getPatient().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}

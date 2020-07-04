package net.thumbtack.school.hospital.dto.response;

import net.thumbtack.school.hospital.model.enums.TimeSlotStatus;

public class AppointmentDto {

    private String slot;
    private PatientProfileResponseDto patient;
    private TimeSlotStatus status;

    public AppointmentDto(String slot, TimeSlotStatus status) {
        this.slot = slot;
        this.status = status;
    }

    public AppointmentDto(String slot, TimeSlotStatus status, PatientProfileResponseDto patient) {
        this(slot, status);
        this.patient = patient;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public PatientProfileResponseDto getPatient() {
        return patient;
    }

    public void setPatient(PatientProfileResponseDto patient) {
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
        if (!(o instanceof AppointmentDto)) return false;

        AppointmentDto that = (AppointmentDto) o;

        if (getSlot() != null ? !getSlot().equals(that.getSlot()) : that.getSlot() != null) return false;
        if (getPatient() != null ? !getPatient().equals(that.getPatient()) : that.getPatient() != null) return false;
        return getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getSlot() != null ? getSlot().hashCode() : 0;
        result = 31 * result + (getPatient() != null ? getPatient().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "slot='" + slot + '\'' +
                ", patient=" + patient +
                ", status=" + status +
                '}';
    }
}

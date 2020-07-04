package net.thumbtack.school.hospital.model;

import net.thumbtack.school.hospital.model.enums.UserType;

import java.util.List;


public class Doctor extends User {

    private String speciality;
    private String room;
    private List<DaySchedule> daySchedule;

    public Doctor() {
    }

    public Doctor(String speciality, String room, List<DaySchedule> daySchedule) {
        this.speciality = speciality;
        this.room = room;
        this.daySchedule = daySchedule;
    }

    public Doctor(String firstName, String lastName, String patronymic, String login, String password,
                  UserType userType, String speciality, String room, List<DaySchedule> daySchedule) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.speciality = speciality;
        this.room = room;
        this.daySchedule = daySchedule;
    }

    public Doctor(int id, String firstName, String lastName, String patronymic, String login, String password,
                  UserType userType, String speciality, String room, List<DaySchedule> daySchedule) {
        super(id, firstName, lastName, patronymic, login, password, userType);
        this.speciality = speciality;
        this.room = room;
        this.daySchedule = daySchedule;
    }

    public Doctor(int id, String firstName, String lastName, String patronymic, String login, String password,
                  UserType userType, String speciality, String room) {
        super(id, firstName, lastName, patronymic, login, password, userType);
        this.speciality = speciality;
        this.room = room;
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

    public List<DaySchedule> getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(List<DaySchedule> daySchedule) {
        this.daySchedule = daySchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        if (!super.equals(o)) return false;

        Doctor doctor = (Doctor) o;

        if (getSpeciality() != null ? !getSpeciality().equals(doctor.getSpeciality()) : doctor.getSpeciality() != null)
            return false;
        if (getRoom() != null ? !getRoom().equals(doctor.getRoom()) : doctor.getRoom() != null) return false;
        return getDaySchedule() != null ? getDaySchedule().equals(doctor.getDaySchedule()) : doctor.getDaySchedule() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getSpeciality() != null ? getSpeciality().hashCode() : 0);
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        result = 31 * result + (getDaySchedule() != null ? getDaySchedule().hashCode() : 0);
        return result;
    }
}

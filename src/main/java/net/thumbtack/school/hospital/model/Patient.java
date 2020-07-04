package net.thumbtack.school.hospital.model;

import net.thumbtack.school.hospital.model.enums.UserType;

import java.util.List;

public class Patient extends User {

    private String email;
    private String address;
    private String phone;
    private List<Ticket> tickets;

    public Patient(String email, String address, String phone) {
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Patient(int id, String firstName, String lastName, String patronymic, String login, String password,
                   UserType userType, String email, String address, String phone) {
        super(id, firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Patient(String firstName, String lastName, String patronymic, String login, String password,
                   UserType userType, String email, String address, String phone) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Patient(int id, String firstName, String lastName, String patronymic, String login, String password,
                   UserType userType, String email, String address, String phone, List<Ticket> tickets) {
        this(id, firstName, lastName, patronymic, login, password, userType, email, address, phone);
        this.tickets = tickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;

        Patient patient = (Patient) o;

        if (getEmail() != null ? !getEmail().equals(patient.getEmail()) : patient.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(patient.getAddress()) : patient.getAddress() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(patient.getPhone()) : patient.getPhone() != null) return false;
        return getTickets() != null ? getTickets().equals(patient.getTickets()) : patient.getTickets() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getTickets() != null ? getTickets().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}

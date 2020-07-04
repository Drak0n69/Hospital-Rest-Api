package net.thumbtack.school.hospital.model;

import net.thumbtack.school.hospital.model.enums.UserType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Admin extends User {

    private String position;

    public Admin() {
    }

    public Admin(String position) {
        this.position = position;
    }

    public Admin(String firstName, String lastName, String patronymic, String login, String password,
                 UserType userType, String position) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.position = position;
    }

    public Admin(int id, String firstName, String lastName, String patronymic, String login, String password,
                 UserType userType, String position) {
        super(id, firstName, lastName, patronymic, login, password, userType);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        if (!super.equals(o)) return false;

        Admin admin = (Admin) o;

        return getPosition() != null ? getPosition().equals(admin.getPosition()) : admin.getPosition() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "position='" + position + '\'' +
                '}';
    }
}

package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.dto.validation.Password;
import net.thumbtack.school.hospital.dto.validation.UserName;
import net.thumbtack.school.hospital.model.enums.UserType;

import javax.validation.constraints.NotNull;

public abstract class UserRegisterRequestDto {

    @UserName
    private String firstName;
    @UserName
    private String lastName;
    private String patronymic;
    @Login
    private String login;
    @Password
    private String password;
    @NotNull
    private UserType userType;

    public UserRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                  UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegisterRequestDto)) return false;

        UserRegisterRequestDto that = (UserRegisterRequestDto) o;

        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getPatronymic() != null ? !getPatronymic().equals(that.getPatronymic()) : that.getPatronymic() != null)
            return false;
        if (getLogin() != null ? !getLogin().equals(that.getLogin()) : that.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        return getUserType() == that.getUserType();
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPatronymic() != null ? getPatronymic().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRegisterRequestDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}

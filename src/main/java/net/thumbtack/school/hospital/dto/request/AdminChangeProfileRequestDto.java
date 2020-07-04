package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.dto.validation.Password;
import net.thumbtack.school.hospital.model.enums.UserType;

public class AdminChangeProfileRequestDto extends UserRegisterRequestDto{

    @Login
    private String position;
    @Password
    private String newPassword;

    public AdminChangeProfileRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                        UserType userType, String position, String newPassword) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.position = position;
        this.newPassword = newPassword;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminChangeProfileRequestDto)) return false;
        if (!super.equals(o)) return false;

        AdminChangeProfileRequestDto that = (AdminChangeProfileRequestDto) o;

        if (getPosition() != null ? !getPosition().equals(that.getPosition()) : that.getPosition() != null)
            return false;
        return getNewPassword() != null ? getNewPassword().equals(that.getNewPassword()) : that.getNewPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        result = 31 * result + (getNewPassword() != null ? getNewPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminChangeProfileRequestDto{" +
                "position='" + position + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}

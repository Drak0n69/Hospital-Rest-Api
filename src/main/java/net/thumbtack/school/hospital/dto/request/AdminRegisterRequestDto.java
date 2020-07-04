package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.model.enums.UserType;

public class AdminRegisterRequestDto extends UserRegisterRequestDto {

    @Login
    private String position;

    public AdminRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                   UserType userType, String position) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminRegisterRequestDto{" +
                "position='" + position + '\'' +
                '}';
    }
}

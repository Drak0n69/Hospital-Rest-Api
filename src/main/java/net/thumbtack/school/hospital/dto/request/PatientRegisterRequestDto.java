package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Email;
import net.thumbtack.school.hospital.dto.validation.Phone;
import net.thumbtack.school.hospital.dto.validation.UserName;
import net.thumbtack.school.hospital.model.enums.UserType;

public class PatientRegisterRequestDto extends UserRegisterRequestDto {

    @Email
    private String email;
    @UserName
    private String address;
    @Phone
    private String phone;

    public PatientRegisterRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                     UserType userType, String email, String address, String phone) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
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

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PatientRegisterRequestDto{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

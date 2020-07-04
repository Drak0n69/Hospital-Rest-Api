package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Email;
import net.thumbtack.school.hospital.dto.validation.Password;
import net.thumbtack.school.hospital.dto.validation.Phone;
import net.thumbtack.school.hospital.dto.validation.UserName;
import net.thumbtack.school.hospital.model.enums.UserType;

public class PatientChangeProfileRequestDto extends UserRegisterRequestDto{

    @Email
    private String email;
    @UserName
    private String address;
    @Phone
    private String phone;
    @Password
    private String newPassword;

    public PatientChangeProfileRequestDto(String firstName, String lastName, String patronymic, String login, String password,
                                          UserType userType, String email, String address, String phone, String newPassword) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.newPassword = newPassword;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientChangeProfileRequestDto)) return false;
        if (!super.equals(o)) return false;

        PatientChangeProfileRequestDto that = (PatientChangeProfileRequestDto) o;

        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getPhone() != null ? !getPhone().equals(that.getPhone()) : that.getPhone() != null) return false;
        return getNewPassword() != null ? getNewPassword().equals(that.getNewPassword()) : that.getNewPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getNewPassword() != null ? getNewPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PatientChangeProfileRequestDto{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}

package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Date;

public class DoctorDismissalRequestDto {

    @Date
    private String dismissalDate;

    public DoctorDismissalRequestDto(String dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public String getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(String dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorDismissalRequestDto)) return false;

        DoctorDismissalRequestDto that = (DoctorDismissalRequestDto) o;

        return getDismissalDate() != null ? getDismissalDate().equals(that.getDismissalDate()) : that.getDismissalDate() == null;
    }

    @Override
    public int hashCode() {
        return getDismissalDate() != null ? getDismissalDate().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DoctorDismissalRequestDto{" +
                "dismissalDate='" + dismissalDate + '\'' +
                '}';
    }
}

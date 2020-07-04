package net.thumbtack.school.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HospitalSettingsResponseDto {

    private int max_name_length;
    private int min_password_length;

    public HospitalSettingsResponseDto(int max_name_length, int min_password_length) {
        this.max_name_length = max_name_length;
        this.min_password_length = min_password_length;
    }

    public int getMax_name_length() {
        return max_name_length;
    }

    public int getMin_password_length() {
        return min_password_length;
    }

    @Override
    public String toString() {
        return "HospitalSettingsResponseDto{" +
                ", max_name_length=" + max_name_length +
                ", min_password_length=" + min_password_length +
                '}';
    }
}

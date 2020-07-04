package net.thumbtack.school.hospital.dto.response;

public class AdminProfileResponseDto extends UserProfileResponseDto {

    private String position;

    public AdminProfileResponseDto(int id, String firstName, String lastName, String patronymic, String position) {
        super(id, firstName, lastName, patronymic);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "AdminProfileResponseDto{" +
                "position='" + position + '\'' +
                '}';
    }
}

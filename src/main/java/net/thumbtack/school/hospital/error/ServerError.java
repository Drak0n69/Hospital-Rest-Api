package net.thumbtack.school.hospital.error;

import java.time.LocalDate;

public enum ServerError {

    INVALID_USER_NAME("name", "Invalid user name!"),
    INVALID_USER_EMAIL("email", "Invalid user email address!"),
    INVALID_USER_PHONE("phone", "Invalid user pone number!"),
    INVALID_USER_LOGIN("login", "Invalid user login!"),
    INVALID_USER_PASSWORD("password", "Invalid user password!"),
    INVALID_USER_TYPE("type", "Invalid user type!"),
    INVALID_DOCTOR_SCHEDULE("schedule", "Doctor’s schedule is missing or incorrect!"),
    NO_SUCH_ADMIN("id", "There is no admin with such id!"),
    NO_SUCH_DOCTOR("id", "There is no doctor with such id!"),
    NO_SUCH_PATIENT("id", "There is no patient with such id!"),
    USER_SAME_LOGIN("login", "User with login %s already exists!"),
    USER_NOT_ADMIN("", "You are not admin!"),
    MISSING_COOKIE("cookie", "Cookie required for this operation!"),
    INVALID_URL("URL", "Invalid URL variable!"),
    DATE_LESS_CURRENT("", "Date cannot be earlier than " + LocalDate.now().minusDays(1)),
    INVALID_DATE_FORMAT("", "Date format must be: DD.MM.YYYY"),
    INVALID_TIME_FORMAT("", "Time format must be: HH:MM"),
    INVALID_WEEK_DAY("", "Invalid day of the week!"),
    URL_NOT_FOUND("URL", "Invalid URL!"),
    INVALID_FIELD("", ""),
    INVALID_REQUEST_PARAM("", ""),
    INVALID_JSON("Request Body", ""),
    INVALID_METHOD("", "HTTP method is not supported for this URL!"),
    SQL_SERVER_ERROR("", "Error in SQL syntax!"),
    NO_VALID_SESSION("cookie", "User has no opened sessions!");

    private String field;
    private String message;

    ServerError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

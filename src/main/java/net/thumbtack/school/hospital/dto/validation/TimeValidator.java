package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator implements ConstraintValidator<Time, String> {

    public void initialize(Time constraint) {
    }

    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime.parse(s, dateFormatter);
        } catch (DateTimeParseException e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_TIME_FORMAT.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateValidator implements ConstraintValidator<Date, String> {

    @Override
    public void initialize(Date constraint) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
        LocalDate date;

        try {
            date = LocalDate.parse(s, dateFormatter);
        } catch (DateTimeParseException e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_DATE_FORMAT.getMessage())
                    .addConstraintViolation();
            return false;
        }

        if (date.isBefore(LocalDate.now().minusDays(1))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.DATE_LESS_CURRENT.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

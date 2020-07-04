package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class WeekDayValidator implements ConstraintValidator<WeekDay, String> {

    public void initialize(WeekDay constraint) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.equalsIgnoreCase("Mon") || !s.equalsIgnoreCase("Tue")
                || !s.equalsIgnoreCase("Wed") || !s.equalsIgnoreCase("Thu")
                || !s.equalsIgnoreCase("Fri") || StringUtils.isEmpty(s)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_WEEK_DAY.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

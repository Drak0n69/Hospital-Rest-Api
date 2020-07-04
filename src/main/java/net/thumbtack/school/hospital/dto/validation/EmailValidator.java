package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.util.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailValidator implements ConstraintValidator<Email, String> {

    private Settings settings;

    @Autowired
    public EmailValidator(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(s)
                || (s.length() > settings.getMaxNameLength())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_USER_EMAIL.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}

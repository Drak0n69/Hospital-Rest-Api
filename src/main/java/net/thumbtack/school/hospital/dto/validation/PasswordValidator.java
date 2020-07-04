package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.util.Settings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Settings settings;

    @Autowired
    public PasswordValidator(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if ((StringUtils.isEmpty(s)) || (s.length() < settings.getMinPasswordLength())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_USER_PASSWORD.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.util.Settings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LoginValidator implements ConstraintValidator<Login, String> {

    private Settings settings;

    @Autowired
    public LoginValidator(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(Login constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if ((StringUtils.isEmpty(s)) || (s.length() > settings.getMaxNameLength()) || (!s.matches("^[a-zA-Z0-9а-яА-Я]+$"))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_USER_LOGIN.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

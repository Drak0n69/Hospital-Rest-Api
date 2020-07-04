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
public class UserNameValidator implements ConstraintValidator<UserName, String> {

    private Settings settings;

    @Autowired
    public UserNameValidator(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(UserName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if ((StringUtils.isEmpty(s)) || (s.length() > settings.getMaxNameLength()) || (!s.matches("^[а-яА-Я\\s\\-]+$"))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_USER_NAME.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

package net.thumbtack.school.hospital.dto.validation;

import net.thumbtack.school.hospital.error.ServerError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if ((StringUtils.isEmpty(s)) || (!s.matches("^(\\+7|8)-*(\\d-*){9}+$"))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(ServerError.INVALID_USER_PHONE.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

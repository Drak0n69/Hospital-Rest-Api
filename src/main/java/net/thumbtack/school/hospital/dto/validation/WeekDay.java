package net.thumbtack.school.hospital.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WeekDayValidator.class)
public @interface WeekDay {
    String message() default "Invalid day of week!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.itk.kdp.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDate {

    String message() default "Дата закінчення відпустки не може бути меншою за дату початку";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


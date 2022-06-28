package com.itk.kdp.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventDateValidator implements ConstraintValidator<EventDate, VacationRequest > {

    @Override
    public boolean isValid(VacationRequest value, ConstraintValidatorContext context) {
        return value.getDateFrom().before(value.getDateBy());
    }
}

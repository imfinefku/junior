package com.base.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.base.util.annotation.CheckString;

public class CheckStringValidation implements
ConstraintValidator<CheckString, String> {
	
	private boolean haode;

	@Override
	public void initialize(CheckString constraintAnnotation) {
		haode=constraintAnnotation.haode();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (haode) {
			if (value !=null && value.contains("haode")) {
				return true;
			}
		}
		return false;
	}

}

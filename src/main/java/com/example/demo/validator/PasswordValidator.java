package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String>{

	@Override
	public boolean isValid(String s, ConstraintValidatorContext context) {
		boolean result = s.contains("@");
		return result;
	}

}

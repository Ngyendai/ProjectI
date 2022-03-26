package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.annotation.ValidEmail;
import com.example.demo.reposutory.LoginRepository;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		/*
			if(loginRepository == null)
			{
				return true;
			}
			return loginRepository.findbyemail(email) == null;
			*/
		return true;
	}
}
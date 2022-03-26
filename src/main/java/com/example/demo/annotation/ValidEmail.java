package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME) // Tồn tại trong lúc chạy chương trình
@Target({ ElementType.FIELD }) // Được sử dụng																																																																							
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

package com.example.demo.annotation;

import javax.validation.Payload;

public @interface Password {

	public String message() default "Chứa kí tự đặc biệt @";

	// represents group of constraints
	public Class<?>[] groups() default {};

//represents additional information about annotation  
	public Class<? extends Payload>[] payload() default {};
}

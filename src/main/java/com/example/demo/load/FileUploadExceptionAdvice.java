package com.example.demo.load;

import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.demo.mes.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends RepositoryNameSpaceHandler{
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MaxUploadSizeExceededException.class) 
	public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!", ""));
	}
}

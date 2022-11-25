package com.campass.demo.controller.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.campass.demo.dto.RestResponse;
import com.campass.demo.exception.JobFailException;


@RestControllerAdvice
public class BoardControllerAdvice {
	@ExceptionHandler(JobFailException.class)
	public ResponseEntity<RestResponse> JFExption(JobFailException e) {
		System.out.println(e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestResponse("FAIL", e.getMessage(), null));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<RestResponse> constraintViolationException() {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestResponse("FAIL", "데이터가 누락되었습니다", null));
	}
}

package com.addi.crm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.addi.crm.dto.ApiErrorDTO;

@RestControllerAdvice
public class Handler {

	Logger logger = LoggerFactory.getLogger(Handler.class);

	@ResponseBody
	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<ApiErrorDTO> handleException(CustomException exception) {
		logger.info("Upss");
		return new ResponseEntity<>(new ApiErrorDTO(exception.getErrorEnum().code, exception.getErrorEnum().title,
				exception.getErrorEnum().description), HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ApiErrorDTO> handleException(NotFoundException exception) {
		logger.info("NOT FOUND");
		return new ResponseEntity<>(new ApiErrorDTO(ErrorEnum.NOT_FOUND_EXCEPTION.code,
				ErrorEnum.NOT_FOUND_EXCEPTION.title, ErrorEnum.NOT_FOUND_EXCEPTION.description), HttpStatus.NOT_FOUND);
	}

}

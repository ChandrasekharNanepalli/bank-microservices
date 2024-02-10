package com.chandu.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chandu.dto.ErrorResponseVo;



@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Map<String,String> validationError=new HashMap<>();
		List<ObjectError> errorList=ex.getBindingResult().getAllErrors();
		
		errorList.forEach(e->{
			String field=((FieldError)e ).getField();
			String validationMsg=e.getDefaultMessage();
			validationError.put(field, validationMsg);
		});
		return new ResponseEntity<Object>(validationError,HttpStatus.BAD_REQUEST);
		
	
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseVo> handleGlobalException(Exception exception,
				WebRequest request){
		ErrorResponseVo errorResponseVo=new ErrorResponseVo(
				request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage(),
				LocalDateTime.now()
				);
		return new ResponseEntity<>(errorResponseVo,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(CustomerAlreadyPresentException.class)
	public ResponseEntity<ErrorResponseVo> handleCustomerAlreadyExistsException(CustomerAlreadyPresentException exception,
				WebRequest request){
		ErrorResponseVo errorResponseVo=new ErrorResponseVo(
				request.getDescription(false),
				HttpStatus.BAD_REQUEST,
				exception.getMessage(),
				LocalDateTime.now()
				);
		return new ResponseEntity<>(errorResponseVo,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseVo> handleResourceNotFoundException(ResourceNotFoundException exception,
				WebRequest request){
		ErrorResponseVo errorResponseVo=new ErrorResponseVo(
				request.getDescription(false),
				HttpStatus.NOT_FOUND,
				exception.getMessage(),
				LocalDateTime.now()
				);
		return new ResponseEntity<>(errorResponseVo,HttpStatus.BAD_REQUEST);
	}

}

package org.ifolks.commons.rest.exception;

import org.ifolks.commons.api.exception.ApplicationException;
import org.ifolks.commons.api.exception.TechnicalError;
import org.ifolks.commons.api.exception.repository.ObjectNotFoundException;
import org.ifolks.commons.api.exception.repository.ResourceNotFoundException;
import org.ifolks.commons.api.exception.state.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * How to handle exceptions : <li>If it is an {@link ApplicationException}, it
 * will be serialized to a {@link ProblemDetail} <li>Else, an unknown
 * {@link TechnicalError} will replace it for serialization.
 * 
 * @author Nicolas Thibault
 *
 */
@ControllerAdvice
public class RestExceptionHandler {	
	
	private static final Logger classLogger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	private boolean printErrorStackInRootLogger = true;
	
	public void setPrintErrorStackInRootLogger(boolean printErrorStackInRootLogger) {
		this.printErrorStackInRootLogger = printErrorStackInRootLogger;
	}

	private ProblemDetail createProblemDetail(HttpStatus status, String detail, String exceptionClassName) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		problemDetail.setTitle(status.getReasonPhrase());
		problemDetail.setProperty("exception", exceptionClassName);
		return problemDetail;
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public @ResponseBody ProblemDetail handleApplicationException(AccessDeniedException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.FORBIDDEN, "access.denied", "AccessDeniedException");
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public @ResponseBody ProblemDetail handleApplicationException(ObjectNotFoundException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.NOT_FOUND, e.getMessage(), e.getClass().getName());
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ProblemDetail handleApplicationException(ResourceNotFoundException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.NOT_FOUND, e.getMessage(), e.getClass().getName());
	}

	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(InvalidStateException.class)
	public @ResponseBody ProblemDetail handleApplicationException(InvalidStateException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.CONFLICT, e.getMessage(), e.getClass().getName());
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApplicationException.class)
	public @ResponseBody ProblemDetail handleApplicationException(ApplicationException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getClass().getName());
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody ProblemDetail handleException(Exception e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, TechnicalError.ERROR_UNKNOWN, TechnicalError.class.getName());
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody ProblemDetail handleException(MethodArgumentNotValidException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.BAD_REQUEST, "invalid.arguments", "InvalidArgumentException");
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public @ResponseBody ProblemDetail handleException(MethodArgumentTypeMismatchException e) {
		
		if (printErrorStackInRootLogger) classLogger.error(e.getMessage(),e);

		return createProblemDetail(HttpStatus.NOT_FOUND, "resource.not.found", e.getClass().getName());
	}
}

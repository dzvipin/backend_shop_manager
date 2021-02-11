package com.geninvo.backend.exception;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import com.geninvo.backend.exception.model.APIError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Handles translation of underlying application exceptions to HTTP status code.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static final List<Class<? extends Exception>> SUPRESSED_EXCEPTIONS = unmodifiableList(asList(
            HttpMediaTypeException.class,
            HttpMessageNotReadableException.class));

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleAll(Exception ex, WebRequest request) {
        logRestError(ex);
        HttpStatus status = INTERNAL_SERVER_ERROR;

        if (ex instanceof ErrorResponse) {
            status = HttpStatus.valueOf(((ErrorResponse) ex).getStatus());
        }

        return buildResponseEntity(new APIError(status, ex.getLocalizedMessage(), getRequestURI(request)));
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolation(Exception ex, WebRequest request) {
        final ConstraintViolationException cve = filter(ConstraintViolationException.class, ex);
        final List errors = new ArrayList<>();
        APIError apiError;

        if (cve != null) {
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                errors.add(new FieldValidationError(
                        violation
                                .getRootBeanClass()
                                .getName() + "." + violation.getPropertyPath(),
                        violation.getMessage()));
            }
            apiError = new APIError(UNPROCESSABLE_ENTITY, "Validation error", errors, getRequestURI(request));
        } else {
            final String message = ex != null ? ex.getLocalizedMessage() : "Validation error";
            apiError = new APIError(UNPROCESSABLE_ENTITY, message, errors, getRequestURI(request));
        }

        return buildResponseEntity(apiError);
    }

    /**
     * Work in conjunction with @{@link javax.validation.Valid} annotation based validations.
     */
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<FieldValidationError> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return buildResponseEntity(new APIError(BAD_REQUEST, "Validation error", errors, getRequestURI(request)));
    }

    @Override
    protected ResponseEntity handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        super.handleExceptionInternal(ex, body, headers, status, request);
        return buildResponseEntity(new APIError(status, ex.getLocalizedMessage(), getRequestURI(request)));
    }

    private <E extends Throwable> E filter(Class<E> target, Throwable exception) {
        if (target.isInstance(exception)) {
            return (E) exception;
        }
        return exception.getCause() == null ? null : filter(target, exception.getCause());
    }

    private ResponseEntity buildResponseEntity(APIError apiError) {
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private boolean isSuppressed(Exception ex) {
        return SUPRESSED_EXCEPTIONS
                .stream()
                .anyMatch(aClass -> aClass.isAssignableFrom(ex.getClass()));
    }

    private void logRestError(final Exception ex) {
        String msg;
        msg = ex.getLocalizedMessage();
        logger.error(msg, ex);

    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest hsr = ((ServletWebRequest) request).getRequest();
            return hsr.getRequestURI();
        } else {
            logger.warn("Failed to get URI for request: " + request.toString());
        }

        return null;
    }


}
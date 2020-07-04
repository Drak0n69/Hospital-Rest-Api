package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.dto.response.ErrorResponseDto;
import net.thumbtack.school.hospital.dto.response.ExceptionResponseDto;
import net.thumbtack.school.hospital.error.ServerError;
import net.thumbtack.school.hospital.error.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionResponseDto handleFieldValidation(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<FieldError> fields = ex.getBindingResult().getFieldErrors();
        List<ErrorResponseDto> errorResponseDtoList = new ArrayList<>();
        ServerError serverError = ServerError.INVALID_FIELD;
        for (int i = 0; i < errors.size(); i++) {
            String message = errors.get(i).getDefaultMessage();
            String field = fields.get(i).getField();
            errorResponseDtoList.add(new ErrorResponseDto(serverError.name(), field, message));
        }
        return new ExceptionResponseDto(errorResponseDtoList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseBody
    public ExceptionResponseDto handleMissingCookieException() {
        ServerError serverError = ServerError.MISSING_COOKIE;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(serverError.name(), serverError.getField(),
                serverError.getMessage());
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ExceptionResponseDto handleFieldValidation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        List<ErrorResponseDto> errorResponseDtoList = new ArrayList<>();
        ServerError serverError = ServerError.INVALID_REQUEST_PARAM;
        for (ConstraintViolation violation : errors) {
            String message = violation.getMessage();
            String field = violation.getPropertyPath().toString();
            field = field.substring(field.indexOf(".") + 1);
            errorResponseDtoList.add(new ErrorResponseDto(serverError.name(), field, message));
        }
        return new ExceptionResponseDto(errorResponseDtoList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ExceptionResponseDto handleFieldValidation(MethodArgumentTypeMismatchException ex) {
        ServerError serverError = ServerError.INVALID_URL;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(serverError.name(), serverError.getField(),
                serverError.getMessage());
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public ExceptionResponseDto handleServerException(ServerException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getError().name(),
                ex.getError().getField(), ex.getError().getMessage());
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ExceptionResponseDto handleNotFoundException() {
        ServerError serverError = ServerError.URL_NOT_FOUND;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(serverError.name(), serverError.getField(),
                serverError.getMessage());
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ExceptionResponseDto handleBadJsonException(HttpMessageNotReadableException ex) {
        String message = "No JSON found!";
        Throwable exception = ex.getCause();
        ServerError serverError = ServerError.INVALID_JSON;
        if (exception != null) {
            message = exception.getMessage();
        }
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(serverError.name(), serverError.getField(), message);
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ExceptionResponseDto handleMethodNotSupportedException() {
        ServerError serverError = ServerError.INVALID_METHOD;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(serverError.name(), serverError.getField(),
                serverError.getMessage());
        return new ExceptionResponseDto(Collections.singletonList(errorResponseDto));
    }
}

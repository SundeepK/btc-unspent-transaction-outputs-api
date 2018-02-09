package com.blockchain.api.controller;

import com.blockchain.api.domain.error.ErrorResponse;
import com.blockchain.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(HttpServletRequest request, Exception e) {
        logger.warn("Could not find url={}", getRequestUrl(request), e);
        return new ErrorResponse("Resource not found.");
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleHttpStatusCodeException(HttpServletRequest request, Exception e) {
        logger.warn("Internal error url={}", getRequestUrl(request), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(HttpServletRequest request, Exception e) {
        String requestUrl = getRequestUrl(request);
        logger.error("Caught unhandled exception from url={}", requestUrl, e);
        return new ErrorResponse(e.getMessage());
    }

    private static String getRequestUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

}
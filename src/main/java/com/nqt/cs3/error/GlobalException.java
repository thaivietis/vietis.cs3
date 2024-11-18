package com.nqt.cs3.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = EmailInvalidException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
    ModelAndView mav = new ModelAndView();
    mav.addObject("errorCode", HttpStatus.NOT_FOUND.value());
    mav.addObject("exception", exception);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("/error/404");
    return mav;
  }
}

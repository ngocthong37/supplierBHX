package com.supplierBHX.exception;

import com.supplierBHX.entity.ResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ResponseObject handleRuntimeException(RuntimeException e){
    return new ResponseObject("400",e.getMessage(),200);
  }
}

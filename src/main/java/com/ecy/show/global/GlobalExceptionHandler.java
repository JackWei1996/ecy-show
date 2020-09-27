package com.ecy.show.global;

import com.ecy.show.exception.BusinessException;
import com.ecy.show.util.TResult;
import com.ecy.show.util.TResultCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 天宇
 * 全局controller advice，捕捉异常，并包装结果
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public TResult page401(UnauthenticatedException e) {
        String eMsg = e.getMessage();
        LOGGER.error("UnauthenticatedException：{}",eMsg);
        return TResult.failure(TResultCode.USER_NOT_LOGGED_IN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public TResult page403(UnauthorizedException eMsg) {
        LOGGER.error("UnauthorizedException：{}",eMsg);
        String localizedMessage="Subject does not have permission";
        if(eMsg.getMessage().contains(localizedMessage)){
            return TResult.failure("您没有权限"+eMsg.getMessage().substring(localizedMessage.length()));
        }else{
            return TResult.failure(TResultCode.PERMISSION_NO_ACCESS);
        }
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public TResult handleBusinessException(HttpServletRequest req, BusinessException e) {
        return TResult.failure(e.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    public TResult handleNullPointerException(HttpServletRequest req, Exception e) {
        LOGGER.error("NullPointerException：{}",e);
        return TResult.failure(TResultCode.RESULE_DATA_NONE);
    }

    /**
     * 参数绑定错误时，将message传回给前台
     */
    @ExceptionHandler(BindException.class)
    public TResult handleBindException(BindException e) {
        return TResult.failure(Arrays.toString(e.getAllErrors().stream().map(ObjectError::getDefaultMessage).toArray()));
    }

    /**
     * 验证参数异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public TResult handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        LOGGER.info("MethodArgumentNotValidException",e);

        StringBuffer errorMesssage = new StringBuffer();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMesssage.append(fieldError.getDefaultMessage());
            errorMesssage.append(";");
        }
        return TResult.failure(errorMesssage.toString());
   }

    @ExceptionHandler(Exception.class)
    public TResult handleException(HttpServletRequest req, Exception e) {
        LOGGER.error("Exception：{}",e);
        return TResult.failure(e.getMessage());
    }
}
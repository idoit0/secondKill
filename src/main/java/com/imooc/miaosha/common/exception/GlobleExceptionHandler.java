package com.imooc.miaosha.common.exception;

import com.imooc.miaosha.common.CodeMsg;
import com.imooc.miaosha.common.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    //拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        //如果异常为数据绑定异常
        if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
            List<ObjectError> errors =  ex.getBindingResult().getAllErrors();
            String errorMsg = errors.get(0).getDefaultMessage();
           return Result.error(CodeMsg.BIND_ERROR.fillArgs(errorMsg));
        }
        //如果是其他异常，则抛出服务器异常g
        else{
            return Result.error(CodeMsg.SERVICE_EROOR);
        }
    }
}

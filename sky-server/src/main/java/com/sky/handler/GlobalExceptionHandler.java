package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {





    /**
     * 捕获业务异常
     */
    @ExceptionHandler
    public Result<String> baseExceptionHandler(BaseException ex) {

        return Result.error(ex.getMessage());
    }


    /**
     *  捕获添加用户名重复的异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> sQLIntegrityConstraintViolationException(DuplicateKeyException ex) throws ServletException, IOException {
        System.out.println("--------------------------------");
        String string = ex.getMessage();
        assert string != null;
        int entry = string.indexOf("entry ");
        int for1 = string.indexOf(" for");
        String username = string.substring(entry + 6, for1);
        return Result.error(username+"用户已经存在");
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public Result<String> dataIntegrityViolationException(){
//        return Result.error("身份证输入错误");
//    }

    /**
     * 全局默认异常处理
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(Exception ex) {
        log.error("异常信息：{}", ex.getMessage());
        ex.printStackTrace(); //输出到控制台
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }


}

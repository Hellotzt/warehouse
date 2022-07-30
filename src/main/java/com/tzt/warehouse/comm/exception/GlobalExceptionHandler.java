package com.tzt.warehouse.comm.exception;

import com.tzt.warehouse.comm.base.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 全局异常处理器
 *
 * @author：帅气的汤
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    // @ExceptionHandler(BusinessException.class)
    // public BaseResponse<String> handleBusinessException(BusinessException e) {
    //     log.error("BusinessException", e);
    //     return ResultUtils.error(e.getCode(), e.getMessage());
    // }
    //
    // /**
    //  * 运行时异常
    //  *
    //  * @param e 异常
    //  * @return 异常结果
    //  */
    // @ExceptionHandler(RuntimeException.class)
    // public BaseResponse<String> handleRuntimeException(RuntimeException e) {
    //     log.error("RuntimeException", e);
    //     return ResultUtils.error(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
    // }
    //
    // /**
    //  * 处理空指针的异常
    //  *
    //  * @param req
    //  * @param e
    //  * @return
    //  */
    // @ExceptionHandler(value = NullPointerException.class)
    // @ResponseBody
    // public BaseResponse<String> exceptionHandler(HttpServletRequest req, NullPointerException e) {
    //     log.error("发生空指针异常！原因是:", e);
    //     return ResultUtils.error(ErrorCodeEnum.NULL_POINTER.getCode(), ErrorCodeEnum.NULL_POINTER.getMessage());
    //
    // }

   /* @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public BaseResponse<String> DataIntegrityViolationException(HttpServletRequest req, NullPointerException e) {
        log.error("我爱你外键:", e);
        // return ResultUtils.error(ErrorCodeEnum.NULL_POINTER.getCode(), ErrorCodeEnum.NULL_POINTER.getMessage());
        return ResultUtils.error(50000000, "我爱你外键");
    }*/

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ResponseResult<HashMap<String, String>> handleRuntimeException(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError ->
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()))
        );
        return new ResponseResult(20000, errorMap);
    }
}

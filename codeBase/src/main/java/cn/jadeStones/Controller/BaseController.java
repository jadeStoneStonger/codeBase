package cn.jadeStones.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.jadeStones.base.response.MsgWrapper;
import cn.jadeStones.base.response.RespMessage;

/**
 * 统一处理异常行为
 * @author jadeStones
 *
 */
@ControllerAdvice
@ResponseBody
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	
	/** 
     * 400 - Bad Request 
     */  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    @ExceptionHandler(HttpMessageNotReadableException.class)  
    public RespMessage<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {  
        logger.error("参数解析失败", e);  
        return MsgWrapper.error(400);  
    }  
	
	/**
	 * 运行期异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value={RuntimeException.class})
	public RespMessage<?> handleRuntimeException(RuntimeException e){
		logger.error("异常：{}", e);
		return MsgWrapper.error(50000,e);
	}
	
	/**
	 * 异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value={Exception.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public RespMessage<?> handleException(Exception e){
		logger.error("异常：{}", e);
		return MsgWrapper.error(50000);
	}
	
	/**
	 * 异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value={SecurityException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public RespMessage<?> handleException(SecurityException e){
		logger.error("异常：{}", e);
		return MsgWrapper.error(10000);
	}
}

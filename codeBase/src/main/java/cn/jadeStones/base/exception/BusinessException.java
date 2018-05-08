package cn.jadeStones.base.exception;
/**
 * 业务异常
 * @author stephen
 *
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = 3492204302357819155L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}

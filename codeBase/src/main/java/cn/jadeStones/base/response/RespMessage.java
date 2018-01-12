package cn.jadeStones.base.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RespMessage<T> {

	/**
	 * 返回码
	 */
	private int code = 20000;
	/**
	 * 错误消息
	 */
	private String msg;
	/**
	 * 返回值内容
	 */
	private T content;
	
	public RespMessage() {
		super();
	}
	
	public RespMessage(int code, T t) {
		this(code, null, t);
	}

	public RespMessage(int code, String msg, T t) {
		super();
		this.code = code;
		this.msg = msg;
		this.content = t;
	}
	
	public int getCode() {
		return code;
	}

	public T getContent() {
		return content;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

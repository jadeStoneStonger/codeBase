package cn.jadeStones.base.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RespMessage<T> extends HashMap<String,Object> implements Serializable { /**
	 * 
	 */
	private static final long serialVersionUID = 4055259087400661458L;
	private static final String DEFAULT_STATUS_NAME="status";
    private static final String DEFAULT_MESSAGE_NAME="message";
    private static final String DEFAULT_VALUE_NAME="value";
    private static final String DEFAULT_TOTAL_NAME="total";
    private static final String DEFAULT_MAP_NAME="map";
    private static final String DEFAULT_LIST_NAME="list";
    private static final String DEFAULT_RES_CODE="code";
    
    public static final String TOKEN_SESSION="token";

    public RespMessage() {
        this.put(DEFAULT_STATUS_NAME,ResponseStatus.success);
    }
    
    public RespMessage(HttpServletRequest request){
    	this.put(DEFAULT_STATUS_NAME,ResponseStatus.success);
    	if(request!=null){
    		String token=(String)request.getSession().getAttribute(TOKEN_SESSION);
    		if(StringUtils.isNotBlank(token)){
    			this.put(TOKEN_SESSION,token);
    		}
    	}
    }
    
    public RespMessage(Integer code, String message){
    	setCode(code);
    	setMessage(message);
    }
    
    public RespMessage(T value){
    	setCode(20000);
    	setValue(value);
    }
    
    
    public Integer getCode(){
    	return (Integer)this.get(DEFAULT_RES_CODE);
    }
    
    public void setCode(Integer value){
    	this.put(DEFAULT_RES_CODE,value);
    }

    public ResponseStatus getStatus() {
        return (ResponseStatus)this.get(DEFAULT_STATUS_NAME);
    }

    public void setStatus(ResponseStatus status) {
        this.put(DEFAULT_STATUS_NAME,status);
    }

    public String getMessage() {
        return (String)this.get(DEFAULT_MESSAGE_NAME);
    }

    public void setMessage(String message) {
        this.put(DEFAULT_MESSAGE_NAME,message);
        this.put(DEFAULT_STATUS_NAME,ResponseStatus.error);
    }
    
    public String getToken(){
    	return (String)this.get(TOKEN_SESSION);
    }
    
    public void setToken(String value){
    	this.put(TOKEN_SESSION,value);
    }

    public T getValue() {
        return (T)this.get(DEFAULT_VALUE_NAME);
    }

    public void setValue(T value) {
        this.put(DEFAULT_VALUE_NAME,value);
    }

    public int getTotal() {
        return (Integer)this.get(DEFAULT_TOTAL_NAME);
    }

    public void setTotal(int total) {
        this.put(DEFAULT_TOTAL_NAME,total);
    }

    public Map<String, T> getMap() {
        return (Map<String, T>)this.get(DEFAULT_MAP_NAME);
    }

    public void setMap(Map<String, T> map) {
        this.put(DEFAULT_MAP_NAME,map);
    }

    public List<T> getList() {
        return (List<T>)this.get(DEFAULT_LIST_NAME);
    }

    public void setList(List<T> list) {
        this.put(DEFAULT_LIST_NAME,list);
    }}

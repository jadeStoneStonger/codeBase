package cn.jadeStones.base.security.Impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import cn.jadeStones.base.security.TokenManager;

public class DefaultTokenManager implements TokenManager{
	 private static Map<String, String> tokenMap = new ConcurrentHashMap<>();  
	  
	    @Override  
	    public String createToken(String username) {  
	        String token = UUID.randomUUID().toString().replaceAll("-", "");  
	        tokenMap.put(token, username);  
	        return token;  
	    }  
	  
	    @Override  
	    public boolean checkToken(String token) {  
	        return !StringUtils.isEmpty(token) && tokenMap.containsKey(token);  
	    }  
}

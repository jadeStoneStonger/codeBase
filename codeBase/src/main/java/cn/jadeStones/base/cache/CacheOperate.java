package cn.jadeStones.base.cache;


public interface CacheOperate {
	
	 String getString(String key);
	 
	 <T> T getObject(String key, Class<T> cls);
	 
	 boolean setString(String key, String value);
	 
	 boolean setObject(String key, Object value);
	 
	 boolean setString(String key, String value, int timeout);
	 
	 boolean setObject(String key, Object value, int timeout);
	 
	 void delPrefixKeys(final String prefix);
}

package cn.jadeStones.base.cache;

/**
 * 操作缓存的接口
 * @author 
 *
 */
public interface CacheOperator {
	
	 String getString(String key);
	 
	 <T> T getObject(String key, Class<T> cls);
	 
	 boolean setString(String key, String value);
	 
	 boolean setObject(String key, Object value);
	 
	 boolean setString(String key, String value, int timeout);
	 
	 boolean setObject(String key, Object value, int timeout);
	 
	 void delPrefixKeys(final String prefix);
}

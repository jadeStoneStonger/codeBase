package cn.jadeStones.base.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.jadeStones.base.cache.CacheOperator;

/*
 * 使用第三方缓存服务器，处理二级缓存
 */
@Component("redisOperater")
public class RedisOperator implements CacheOperator {
	
	private  final Logger log = LogManager.getLogger(RedisOperator.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redis;
	
	public  String getString(String key) {
		String str = null;
		Object val = redis.opsForValue().get(key);
		if(val!=null){
			str = val.toString();
		}
		return str;
	}

	public  <T> T getObject(String key, Class<T> cls) {
		T t = null;
		try {
			String str = getString(key);
			if(str!=null)
			t = JSONObject.parseObject(str, cls);
		} catch (Exception e) {
			log.error("redis get object value error", e);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public  boolean setString(String key, String value) {
		redis.opsForValue().set(key, value);
		return true;
	}

	public  boolean setObject(String key, Object value) {
		setString(key, JSONObject.toJSONString(value));
		return true;
	}

	@SuppressWarnings("unchecked")
	public  boolean setString(String key, String value, int timeout) {
		redis.opsForValue().set(key, value,timeout,TimeUnit.SECONDS);
		return true;
	}

	public  boolean setObject(String key, Object value, int timeout) {
		setString(key, JSONObject.toJSONString(value), timeout);
		return true;
	}
    
	@SuppressWarnings("unchecked")
	public  Long increment(String key, Long value) {
		return redis.opsForValue().increment(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public  Long hIncrement(String key, Object hashKey, Long value) {
		return redis.opsForHash().increment(key, hashKey, value);
	}
	
	@SuppressWarnings("unchecked")
	public  Long listRemove(String key, Long count, Object value) {
		return redis.opsForList().remove(key, count, value);
	}
	
	@SuppressWarnings("unchecked")
	public  Long listRightPushAll(String key, Object... values) {
		return redis.opsForList().rightPushAll(key, values);
	}
	
	/**
	 * 批量删除前缀的key
	 * 
	 * @param prefix 前缀，不包含*
	 */
	@SuppressWarnings("unchecked")
	public  void delPrefixKeys(final String prefix){
		redis.execute(new RedisCallback<Object>() {
			//不需要关闭conn
			@Override
			public Object doInRedis(final RedisConnection conn)	throws DataAccessException {
				//scan 选项
				final ScanOptions option = ScanOptions.scanOptions().match(prefix+"*").count(10000).build();
				Cursor<byte[]> res = conn.scan(option);
				while(res !=null && res.hasNext()){
					final List<byte []> list = new ArrayList<>();
					//遍历 cursor
					while(res.hasNext()) {
			        	list.add(res.next());
			        }
					//批量删除
					if(!list.isEmpty()){
						byte [][] args = new byte [list.size()][];
						list.toArray(args);
						conn.del(args);
					}else{
						break;
					}
					IOUtils.closeQuietly(res);
					res = conn.scan(option);		
				}
				IOUtils.closeQuietly(res);
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public  void delKey(final String key){
		redis.delete(key);
	}
	
	@SuppressWarnings("unchecked")
	public  Long getExpire(final String key){
		return redis.getExpire(key);
	}
}

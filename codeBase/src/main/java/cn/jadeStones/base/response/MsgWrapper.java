package cn.jadeStones.base.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MsgWrapper {

	private static final Logger log = LoggerFactory.getLogger(MsgWrapper.class);

	private static final Map<Integer, String> errors = new HashMap<>();

	public static final int OK = 20000;
	
	static {
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("error.properties"));
			for (Entry<Object, Object> ent : props.entrySet()) {
				String key = (String) ent.getKey();
				String value = (String) ent.getValue();
				errors.put(Integer.valueOf(key), value);
			}
		} catch (Exception e) {
			log.error("load error.properties exception",e);
		}
	}

	/**
	 * 返回成功
	 * @param t
	 * @return
	 */
	public static <T> RespMessage<T> success(final T t) {
		return new RespMessage<>(OK, t);
	}
	
	public static <T> RespMessage<T> success() {
		return success(null);
	}

	/**
	 * 失败
	 * @param errorCode
	 * @return
	 */
	public static <T> RespMessage<T> error(int errorCode) {
		if (errorCode == OK) {
			return success(null);
		}
		String msg = errors.get(errorCode);
		return new RespMessage<>(errorCode, msg, null);
	}
	
	/**
	 * 失败
	 * @param errorCode
	 * @return
	 */
	public static <T> RespMessage<T> error(int errorCode,String errmsg) {
		if (errorCode == OK) {
			return success(null);
		}
		String msg = errors.get(errorCode);
		StringBuilder sb = new StringBuilder();
		sb.append(msg)
		.append("\n")
		.append(errmsg);
		return new RespMessage<>(errorCode, sb.toString(), null);
	}
	
	/**
	 * 失败
	 * @param errorCode
	 * @param params
	 * @return
	 */
	public static <T> RespMessage<T> patternError(int errorCode,Object... params) {
		if (errorCode == OK) {
			return success(null);
		}
		String msg = errors.get(errorCode);
		return new RespMessage<>(errorCode, String.format(msg, params), null);
	}
	
	/**
	 * 失败
	 * @param errorCode
	 * @param e
	 * @return
	 */
	public static <T> RespMessage<T> error(int errorCode,Exception e) {
		if (errorCode == OK) {
			return success(null);
		}else if(errorCode == 50000){
//			if(e != null && "on".equals(System.getProperty(Constants.WEBSERVICE_DEBUG_MODE))){
//				StringBuilder sb = new StringBuilder();
//				sb.append(e.getLocalizedMessage());
//				sb.append("\n");
//				return new RespMessage<>(errorCode, sb.toString(), null);
//			}else{
				String msg = errors.get(errorCode);
				return new RespMessage<>(errorCode, msg, null);
//			}
		}else{
			String msg = errors.get(errorCode);
			return new RespMessage<>(errorCode, msg, null);
		}
	}

}

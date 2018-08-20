package cn.jadeStones.base.security;

/**
 * 管理token
 * @author jadeStones
 *
 */
public interface TokenManager {

	/**
	 * 生成token
	 * @param username
	 * @return
	 */
	String createToken(String username);  
	  
	/**
	 * 验证token
	 * @param token
	 * @return
	 */
    boolean checkToken(String token); 
}

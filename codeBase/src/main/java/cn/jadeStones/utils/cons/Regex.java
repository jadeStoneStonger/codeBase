package cn.jadeStones.utils.cons;

public class Regex {

	/**
	 * 验证邮箱的正则表达式
	 */
	public static final String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/**
	 * 验证手机号码的正则表达式
	 */
	public static final String PHONE="^1[3|4|5|6|7|8|9][0-9]{9}$";
}

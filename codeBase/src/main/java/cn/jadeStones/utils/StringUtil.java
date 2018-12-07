package cn.jadeStones.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils{
	
	/**
	 * 验证邮箱的正则表达式
	 */
	public static final String EMAIL_REGEX = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/**
	 * 验证手机号码的正则表达式
	 */
	public static final String PHONE_REGEX="^1[3|4|5|6|7|8|9][0-9]{9}$";
	/**
	 * 删除最后一个字节
	 * @param strBuilder
	 */
	public static void deleteLastChar(StringBuilder strBuilder) {
		deleteLastChar(strBuilder, 1);
	}

	/**
	 * 删除最后几个字节
	 * @param strBuilder
	 */
	public static void deleteLastChar(StringBuilder strBuilder, int count) {
		if (count < 1) {
			count = 1;
		}

		if (strBuilder == null || strBuilder.length() == 0) {
			strBuilder = null;
			return;
		}
		strBuilder.delete(strBuilder.length() - count, strBuilder.length());
	}
	
	
	/**
	 * 删除最后的字节
	 * @param str
	 * @param count
	 * @return
	 */
	public static String deleteLastChar(String str, int count) {
		if (count < 1) {
			count = 1;
		}

		if (str == null || str.length() == 0) {
			return null;
		}
		
		return str.substring(0, str.length()-count);
	}
	
	/**
	 * 将多个StringBuilder合并
	 * @param strBuilder
	 */
	public static String appendAll(StringBuilder... strBuilders) {
		StringBuilder all = new StringBuilder();
		for (StringBuilder stringBuilder : strBuilders) {
			all.append(stringBuilder);
		}
		return all.toString();
	}

	/**
	 * 将多个String合并
	 * @param strBuilder
	 */
	public static String appendAll(String... strings) {
		StringBuilder all = new StringBuilder();
		for (String string : strings) {
			all.append(string);
		}
		return all.toString();
	}

	/**
	 * 删除多余的空格
	 * @param strBuilder
	 */
	public static String deleteExcessBlank(String str) {
		StringBuilder sb = new StringBuilder(str = str.trim());
		int blankCount = 0;
		for (int i = 0; i < sb.length(); i++) {
			char ch = sb.charAt(i);
			if (ch == ' ') {
				if (blankCount == 0) {
					blankCount++;
				} else if (blankCount > 0) {
					sb.deleteCharAt(i);
					i--;
				}
			} else {
				blankCount = 0;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 分割字符串同时去掉多余的空格
	 * @param strBuilder
	 */
	public static String[] split(String str, String regex) {
		if (str == null) {
			return new String[0];
		}
		String[] strArray = str.split(regex);
		for (int i = 0; i < strArray.length; i++) {
			String string = strArray[i].trim();
			strArray[i] = string;
		}
		return strArray;
	}

	/**
	 * 判断str是否符合正则表达式
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	public static boolean match(String regex, String str) {
		if(str != null){
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		}
		return false;
	}
	
	/**
	 * 返回字段名的驼峰命名
	 * in： FIELD_NAME1, FIELD_NAME2, FIELD_NAME_NAME3
	 * out: fieldName1, fieldName2, fieldNameName3
	 * @param fieldName
	 * @return
	 */
	public static String convertFiedlName2Hump(String fieldName){
		return Arrays.asList(fieldName.split(",")).stream()
		.map((f)->{
			StringBuffer sb = new StringBuffer(f).append(" AS");
			//此句用于表别名
//			sb.insert(1,"t1.");  
			Matcher m = Pattern.compile("_[a-z]").matcher(f.toLowerCase());
			while (m.find()){ 
				m.appendReplacement(sb,  m.group().substring(1).toUpperCase());
			}
			m.appendTail(sb);
			return sb.toString();
			}
		).reduce((f,l)->{return f+","+l;}).get();
	}
	
	public static void main(String[] args) {
		String a = " NOS, PAYCODE, PAYNAME, WORDS, PAYMONEY, BZ2, CRT_TIME, REC_TIME";
		System.out.println(convertFiedlName2Hump(a));
	}
}

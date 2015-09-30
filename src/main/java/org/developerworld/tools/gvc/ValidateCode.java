package org.developerworld.tools.gvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 验证码类
 * @author Roy Huang
 * @version 20110111
 *
 *@deprecated
 *@see org.developerworld.commons.validatecode project
 */
public class ValidateCode {

	private static Log logger = LogFactory.getLog(ValidateCode.class);
	
	public final static String CODE_TYPE_NUMBER="number";
	public final static String CODE_TYPE_BIG_ENGLISH="bigEnglish";
	public final static String CODE_TYPE_SMALL_ENGLISH="smallEnglish";
	public final static String CODE_TYPE_ENGLISH="english";
	public final static String CODE_TYPE_NUMBER_AND_ENGLISH="numberAndEnglish";
	public final static String CODE_TYPE_CHINESE="chinese";
	public final static String CODE_TYPE_CHINESE_IDIOM_4="chineseIdiom4";

	private static Map<String, String[]> code = new HashMap<String, String[]>();

	private int Length;
	private String[] source;

	static {
		// 设置其他资源
		try {
			logger.info("The GVC code are loading code file!");
			Properties p = new Properties();
			p.load(ValidateCode.class.getResourceAsStream("code_source.properties"));
			Iterator<Entry<Object, Object>> i = p.entrySet().iterator();
			while (i.hasNext()) {
				Entry<Object, Object> e = i.next();
				code.put((String) e.getKey(),
						((String) e.getValue()).split(","));
			}
			logger.info("The GVC code have load!");
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public ValidateCode(String type) {
		this(type, 1);
	}

	public ValidateCode(String type, int length) {
		this.Length = length;
		if (code.containsKey(type))
			source = (String[]) code.get(type);
		else
			source = code.values().iterator().next();
	}

	/**
	 * 获取验证码字符串
	 * 
	 * @return
	 */
	public String getRandomCode() {
		return getRandomCode(this.Length);
	}

	/**
	 * 获取验证码字符串
	 * 
	 * @param length
	 * @return
	 */
	public String getRandomCode(int length) {
		return random(length, source);
	}
	
	/**
	 * 从数组中，随机选择指定个数元素
	 * @param count
	 * @param source
	 * @return
	 */
	private String random(int count, String[] source) {
		StringBuffer rst = new StringBuffer();
		if (source != null && count > 0) {
			for (int i = 0; i < count; i++) {
				rst.append(source[RandomUtils.nextInt(source.length)]);
			}
		}
		return rst.toString();
	}
}

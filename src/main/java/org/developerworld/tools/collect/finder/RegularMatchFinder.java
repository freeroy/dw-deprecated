package org.developerworld.tools.collect.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.developerworld.tools.collect.Finder;

/**
 * 正则表达式查找器
 * 
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class RegularMatchFinder implements Finder {

	private String regular;
	private String regularGroup;
	private int regularPatternFlags = Pattern.DOTALL// 启用 dotall 模式
			| Pattern.CASE_INSENSITIVE// 启用不区分大小写的匹配
			| Pattern.MULTILINE;// 启用多行模式;

	public RegularMatchFinder() {

	}

	public RegularMatchFinder(String regular) {
		this.regular = regular;
	}

	public RegularMatchFinder(String regular, String regularGroup) {
		this.regular = regular;
		this.regularGroup = regularGroup;
	}

	public RegularMatchFinder(String regular, String regularGroup,
			int regularPatternFlags) {
		this.regular = regular;
		this.regularGroup = regularGroup;
		this.regularPatternFlags = regularPatternFlags;
	}

	public void setRegular(String regular) {
		this.regular = regular;
	}

	public void setRegularGroup(String regularGroup) {
		this.regularGroup = regularGroup;
	}

	public void setRegularPatternFlags(int regularPatternFlags) {
		this.regularPatternFlags = regularPatternFlags;
	}

	public List<String> find(String content) {
		List<String> rst = new ArrayList<String>();
		Pattern rPattern = Pattern.compile(regular == null ? ".*" : regular,
				regularPatternFlags);
		Matcher matcher = rPattern.matcher(content);
		// 找到匹配内容
		while (matcher.find()) {
			// 获取匹配的值
			String matchContent = null;
			if (StringUtils.isNotBlank(regularGroup))
				matchContent = getGroupExpressionValue(regularGroup, matcher);
			else
				matchContent = matcher.group();
			rst.add(matchContent);
		}
		return rst;
	}

	/**
	 * 获取组合表达式的值
	 * 
	 * @param regularGroup
	 * @param matcher
	 * @return
	 */
	private String getGroupExpressionValue(String regularGroup,
			java.util.regex.Matcher matcher) {
		String rst = regularGroup;
		String bStr = "${";
		String eStr = "}";
		if (StringUtils.isNotBlank(rst)) {
			int bIndex = 0;
			int eIndex = 0;
			while (true) {
				bIndex = rst.indexOf(bStr, eIndex);
				if (bIndex < 0)
					break;
				eIndex = rst.indexOf(eStr, bIndex + bStr.length());
				if (eIndex < 0)
					break;
				String tmp = rst.substring(bIndex + bStr.length(), eIndex);
				int gI = Integer.parseInt(tmp.trim());
				String groupStr = null;
				if (gI >= 0 && gI <= matcher.groupCount())
					groupStr = matcher.group(gI);
				if (groupStr == null)
					groupStr = "";
				rst = rst.substring(0, bIndex) + groupStr
						+ rst.substring(eIndex + eStr.length(), rst.length());
				// 调整结束位置到修改内容后的位置
				eIndex = bIndex + groupStr.length();
			}
		}
		return rst;
	}

}

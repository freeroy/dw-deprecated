package org.developerworld.tools.collect.finder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.developerworld.tools.collect.Finder;

/**
 * 简单字符查找器
 * 
 * @author Roy Huang
 * @version 20121110
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class StringMatchFinder implements Finder {

	private String beginKeyWord;
	private String endKeyWord;

	public StringMatchFinder() {

	}

	public StringMatchFinder(String beginKeyWord, String endKeyWord) {
		this.beginKeyWord = beginKeyWord;
		this.endKeyWord = endKeyWord;
	}

	public void setBeginKeyWord(String beginKeyWord) {
		this.beginKeyWord = beginKeyWord;
	}

	public void setEndKeyWord(String endKeyWord) {
		this.endKeyWord = endKeyWord;
	}

	public List<String> find(String content) {
		List<String> rst = new ArrayList<String>();
		StringBuffer subContent = new StringBuffer(content);
		while (subContent.length() > 0) {
			int bIndex = 0;
			int eIndex = subContent.length();
			if (StringUtils.isNotBlank(beginKeyWord)) {
				bIndex = subContent.indexOf(beginKeyWord);
				// 去到结尾位置
				if (bIndex >= 0)
					bIndex += beginKeyWord.length();
			}
			if (StringUtils.isNotBlank(endKeyWord)) {
				eIndex = subContent.indexOf(endKeyWord);
				while (eIndex >= 0 && bIndex >= eIndex)
					eIndex = subContent.indexOf(endKeyWord,
							eIndex + endKeyWord.length());
			}
			// 若开始位置比结束位置还要前或相等，就退出
			if (bIndex < 0 || bIndex >= eIndex)
				break;
			// 截取开始、结束位置之间的内容
			rst.add(subContent.substring(bIndex, eIndex));
			subContent.delete(0, eIndex);
		}
		return rst;
	}

}

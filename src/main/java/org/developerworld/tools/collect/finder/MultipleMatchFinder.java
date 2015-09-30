package org.developerworld.tools.collect.finder;

import java.util.ArrayList;
import java.util.List;

import org.developerworld.tools.collect.Finder;

/**
 * 多重查找器查找器
 * 
 * @author Roy Huang
 * @version 20121210
 *@deprecated
 *@see org.developerworld.commons.collect project
 * 
 */
public class MultipleMatchFinder implements Finder {

	List<Finder> finders = new ArrayList<Finder>();

	public MultipleMatchFinder() {

	}

	public MultipleMatchFinder(List<Finder> finders) {
		this.finders = finders;
	}

	public void setFinders(List<Finder> finders) {
		this.finders = finders;
	}

	public MultipleMatchFinder addFinder(Finder finder) {
		finders.add(finder);
		return this;
	}

	public List<String> find(String content) {
		List<String> rst = new ArrayList<String>();
		if (finders == null || finders.size() <= 0)
			rst.add(content);
		else
			rst = findByFinder(content, 0);
		return rst;
	}

	/**
	 * 递归执行finder查找
	 * 
	 * @param content
	 * @param index
	 * @return
	 */
	private List<String> findByFinder(String content, int index) {
		List<String> rst = new ArrayList<String>();
		// 提取finder
		Finder finder = finders.get(index);
		if (finder != null) {
			// 根据finder获取匹配字符集合
			List<String> matchers = finder.find(content);
			// 若当前finder是最后一个，则直接返回得到的数据
			if (index + 1 >= finders.size())
				rst = matchers;
			// 遍历查到的数据向下继续执行
			else
				for (String matcher : matchers)
					rst.addAll(findByFinder(matcher, index + 1));
		} else
			rst.add(content);
		return rst;
	}

}

package org.developerworld.framework.spring.jdbc.core.simple;

import org.developerworld.framework.spring.jdbc.core.support.DwJdbcDaoSupport;

/**
 * 扩展原SimpleJdbcDaoSupport类
 * 
 * @author Roy Huang
 * @version 20101129
 * 
 * @deprecated
 * @see org.developerworld.frameworks.spring project
 */
@Deprecated
public class DwSimpleJdbcDaoSupport extends DwJdbcDaoSupport {

	private DwSimpleJdbcTemplate dwSimpleJdbcTemplate;

	/**
	 * 请参考原SimpleJdbcDaoSupport的描述
	 */
	@Override
	protected void initTemplateConfig() {
		this.dwSimpleJdbcTemplate = new DwSimpleJdbcTemplate(
				getDwJdbcTemplate());
	}

	/**
	 * 请参考原SimpleJdbcDaoSupport的描述
	 * 
	 * @return
	 */
	public DwSimpleJdbcTemplate getDwSimpleJdbcTemplate() {
		return this.dwSimpleJdbcTemplate;
	}
}

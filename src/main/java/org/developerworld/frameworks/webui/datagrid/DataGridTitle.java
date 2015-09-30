package org.developerworld.frameworks.webui.datagrid;

/**
 * 数据网格标题对象
 * @author Roy Huang
 * @version 20110415
 *@deprecated 不再维护
 */
public class DataGridTitle{
	
	public final static String ALIGN_LEFT="left";
	public final static String ALIGN_CENTER="center";
	public final static String ALIGN_RIGHT="right";
	public final static String VALIGN_TOP="top";
	public final static String VALIGN_MIDDLE="middle";
	public final static String VALIGN_BOTTOM="bottom";
	
	private String key;
	private String name;
	private String renderer;
	private String width;
	private String sort;
	private String align;
	private String valign;
	
	public String getAlign() {
		return align;
	}

	public DataGridTitle setAlign(String align) {
		this.align = align;
		return this;
	}

	public String getValign() {
		return valign;
	}

	public DataGridTitle setValign(String valign) {
		this.valign = valign;
		return this;
	}

	private boolean unordered;
	
	public String getKey() {
		return key;
	}

	public DataGridTitle setKey(String key) {
		this.key = key;
		return this;
	}

	public String getName() {
		return name;
	}

	public DataGridTitle setName(String name) {
		this.name = name;
		return this;
	}

	public String getRenderer() {
		return renderer;
	}

	public DataGridTitle setRenderer(String renderer) {
		this.renderer = renderer;
		return this;
	}

	public String getWidth() {
		return width;
	}

	public DataGridTitle setWidth(String width) {
		this.width = width;
		return this;
	}

	public String getSort() {
		return sort;
	}

	public DataGridTitle setSort(String sort) {
		this.sort = sort;
		return this;
	}

	public boolean isUnordered() {
		return unordered;
	}

	public DataGridTitle setUnordered(boolean unordered) {
		this.unordered = unordered;
		return this;
	}
	
}

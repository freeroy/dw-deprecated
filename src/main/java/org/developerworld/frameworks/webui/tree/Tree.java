package org.developerworld.frameworks.webui.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形组件java支持类
 * 
 * @author Roy Huang
 * @version 20120510
 * @deprecated 不再维护
 */
public class Tree {

	private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	/**
	 * 设置节点数据
	 * 
	 * @param datas
	 * @return
	 */
	public Tree datas(List<Map<String, Object>> datas) {
		setDatas(datas);
		return this;
	}

	/**
	 * 添加节点数据
	 * 
	 * @param id
	 * @param name
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name) {
		return addNode(id, name, null, null, false);
	}
	
	/**
	 * 添加节点数据
	 * @param id
	 * @param name
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name,Map<String,Object> otherDatas) {
		return addNode(id, name, null, null, false,otherDatas);
	}

	/**
	 * 添加节点数据
	 * 
	 * @param id
	 * @param name
	 * @param isChecked
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name, boolean isChecked) {
		return addNode(id, name, null, null, isChecked);
	}
	
	/**
	 * 添加节点数据
	 * @param id
	 * @param name
	 * @param isChecked
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name, boolean isChecked,Map<String,Object> otherDatas) {
		return addNode(id, name, null, null, isChecked,otherDatas);
	}

	/**
	 * 添加节点
	 * 
	 * @param id
	 * @param name
	 * @param handler
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name, String handler) {
		return addNode(id, name, handler, null, false);
	}
	
	/**
	 * 添加节点
	 * @param id
	 * @param name
	 * @param handler
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name, String handler,Map<String,Object> otherDatas) {
		return addNode(id, name, handler, null, false,otherDatas);
	}

	/**
	 * 添加节点
	 * @param id
	 * @param name
	 * @param handler
	 * @param isChecked
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name, String handler,
			boolean isChecked) {
		return addNode(id, name, handler, null, isChecked);
	}
	
	/**
	 * 添加节点
	 * @param id
	 * @param name
	 * @param handler
	 * @param isChecked
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name, String handler,
			boolean isChecked,Map<String,Object> otherDatas) {
		return addNode(id, name, handler, null, isChecked,otherDatas);
	}

	/**
	 * 添加节点
	 * 
	 * @param id
	 * @param name
	 * @param handler
	 * @param childs
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name, String handler, Tree childs) {
		return addNode(id, name, handler, childs, false);
	}
	
	/**
	 * 添加节点
	 * @param id
	 * @param name
	 * @param handler
	 * @param childs
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name, String handler, Tree childs,Map<String,Object> otherDatas) {
		return addNode(id, name, handler, childs, false,otherDatas);
	}

	/**
	 * 添加节点
	 * 
	 * @param id
	 * @param name
	 * @param handler
	 * @param childs
	 * @param isChecked
	 * @param args
	 * @return
	 */
	public Tree addNode(String id, String name, String handler, Tree childs,
			boolean isChecked) {
		return addNode(id,name,handler,childs,isChecked,null);
	}
	
	/**
	 * 添加节点
	 * @param id
	 * @param name
	 * @param handler
	 * @param childs
	 * @param isChecked
	 * @param otherDatas
	 * @return
	 */
	public Tree addNode(String id, String name, String handler, Tree childs,
			boolean isChecked,Map<String,Object> otherDatas) {
		Map<String, Object> data = new HashMap<String, Object>();
		if(otherDatas!=null)
			data.putAll(otherDatas);
		data.put("id", id);
		data.put("name", name);
		data.put("handler", handler);
		data.put("childs", childs == null ? null : childs.toList());
		data.put("isChecked", isChecked);
		datas.add(data);
		return this;
	}

	/**
	 * 返回节点列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> toList() {
		return datas;
	}

}

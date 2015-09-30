package org.developerworld.commons.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回命令对象
 * 
 * @author Roy Huang
 * @version 20111026
 *  @deprecated 不在提供支持
 */
public class ResultCommand<T> {

	public static final int STATUS_ERROR=0;
	public static final int STATUS_SUCCESS=1;
	
	public ResultCommand(){
		super();
	}
	
	public ResultCommand(int status){
		this.status=status;
	}
	
	public ResultCommand(String message){
		this.message=message;
	}
	
	public ResultCommand(T data){
		this.data=data;
	}
	
	public ResultCommand(int status,String message){
		this.status=status;
		this.message=message;
	}
	
	public ResultCommand(int status,T data){
		this.status=status;
		this.data=data;
	}
	
	public ResultCommand(int status,String message,T data){
		this.status=status;
		this.message=message;
		this.data=data;
	}

	private String message;

	private int status;

	private T data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultCommand<T> message(String message) {
		setMessage(message);
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ResultCommand<T> status(int status) {
		setStatus(status);
		return this;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultCommand<T> data(T data) {
		setData(data);
		return this;
	}
	
	/**
	 * 生成Map对象
	 * @return
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> rst = new HashMap<String, Object>();
		// 生成sort段
		rst.put("status", status);
		// 生成page段
		rst.put("message", message);
		// 生成title段
		rst.put("data", data);
		return rst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + status;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultCommand other = (ResultCommand) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResultCommand [message=" + message + ", status=" + status
				+ ", data=" + data + "]";
	}

}

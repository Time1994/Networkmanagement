package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 返回bean
 * 
 * @author amos
 * 
 */
public class CommonBean<T> implements Serializable {
	private String state;
	private String msg;
	private T data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

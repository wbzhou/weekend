package com.littleWeekend.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
@author :zhouwenbin
@time   :2018年12月15日
@comment:
**/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeMsgResp {

	private int code;
	private String msg;
	private Object data;
	private String success;
	private String desc;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}

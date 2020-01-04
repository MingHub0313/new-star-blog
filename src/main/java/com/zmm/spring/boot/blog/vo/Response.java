package com.zmm.spring.boot.blog.vo;

/**
 * @author 1805783671
 * @version Response-1.0
 * @time 2018年12月31日 下午7:20:38
 * @Desc 描述---返回对象
 */
public class Response {

	/**
	 * 处理是否成功
	 */
	private boolean success;
	/**
	 * 处理后消息提示
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private Object body;
	
	/** 响应处理是否成功 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/** 响应处理的消息 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/** 响应处理的返回内容 */
	public Object getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}
	
	public Response(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Response(boolean success, String message, Object body) {
		this.success = success;
		this.message = message;
		this.body = body;
	}
	
	
}

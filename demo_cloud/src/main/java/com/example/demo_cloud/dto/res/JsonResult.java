package com.example.demo_cloud.dto.res;

public class JsonResult<T> extends Result {

	private T data;

	public JsonResult() {
	}

	public JsonResult(T data, String message, boolean success, int statusCode) {
		this.data = data;
		super.setMessage(message);
		super.setSuccess(success);
		super.setStatusCode(statusCode);
	}

	public JsonResult(T data, String message, int statusCode) {
		this.data = data;
		super.setMessage(message);
		super.setSuccess(true);
		super.setStatusCode(statusCode);
	}

	public JsonResult(T data, String message, boolean success) {
		this(data, message, success, 200);
	}

	public JsonResult(T data, String message) {
		this(data, message, true);
	}

	public JsonResult(T data) {
		this(data, "成功");
	}

	public JsonResult(String message, boolean success) {
		super.setMessage(message);
		super.setSuccess(success);
		super.setStatusCode(500);
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

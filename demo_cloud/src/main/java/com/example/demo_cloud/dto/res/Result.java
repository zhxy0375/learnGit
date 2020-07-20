package com.example.demo_cloud.dto.res;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 7458484793731524074L;

	private String message;

	private int statusCode;

	private boolean success;

	public Result() {
	}

	public String getMessage() {
		return this.message;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}

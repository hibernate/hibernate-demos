package org.hibernate.demo.message.account.core.service.exception;

public class ExceptionMessage {

	private Integer code;
	private String exceptionClass;
	private String message;

	public ExceptionMessage(Integer code, String exceptionClass, String message) {
		this.code = code;
		this.exceptionClass = exceptionClass;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

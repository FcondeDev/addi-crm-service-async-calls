package com.addi.crm.dto;

public class JsonDTO<T> {

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public JsonDTO(T data) {
		this.data = data;
	}

	public JsonDTO() {
	}

}

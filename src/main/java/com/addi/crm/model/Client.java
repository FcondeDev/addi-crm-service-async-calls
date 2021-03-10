package com.addi.crm.model;

public class Client {

	private String clientId;
	private String name;
	private String lastName;
	private String email;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Client(String clientId, String name, String lastName, String email) {
		this.clientId = clientId;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

}

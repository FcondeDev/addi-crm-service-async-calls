package com.addi.crm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.addi.crm.model.Client;

@Component
public class CrmRepository {

	private List<Client> clientInformation;

	@PostConstruct
	public void init() {
		Client client1 = new Client("1", "pepito", "perez", "pepito@gmail.com");
		clientInformation = new ArrayList<>();
		clientInformation.add(client1);
	}

	public Optional<Client> getClientById(String clientId) {
		return clientInformation.stream().filter(client -> client.getClientId().equals(clientId)).findFirst();
	}
}

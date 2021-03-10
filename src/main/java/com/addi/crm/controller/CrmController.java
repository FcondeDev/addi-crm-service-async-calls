package com.addi.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.addi.crm.dto.CrmResponseDTO;
import com.addi.crm.dto.JsonDTO;
import com.addi.crm.service.CrmService;

@RestController
public class CrmController {

	@Autowired
	private CrmService crmService;

	@GetMapping("/crms/{clientId}")
	public ResponseEntity<JsonDTO<CrmResponseDTO>> getCrmResult(@PathVariable String clientId) {
		return new ResponseEntity<>(new JsonDTO<>(crmService.evaluateClient(clientId)), HttpStatus.OK);
	}

}

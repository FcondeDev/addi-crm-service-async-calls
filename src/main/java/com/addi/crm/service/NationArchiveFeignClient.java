package com.addi.crm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.addi.crm.dto.JsonDTO;
import com.addi.crm.dto.NationalArchivesSimulationResponseDTO;

@FeignClient(name = "national-archives-service", url = "localhost:9000")
public interface NationArchiveFeignClient {

	@GetMapping("national/archives/{clientId}")
	public ResponseEntity<JsonDTO<NationalArchivesSimulationResponseDTO>> getNationalArchivesInformation(
			@PathVariable("clientId") String clientId);

}

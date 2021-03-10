package com.addi.crm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.addi.crm.dto.JsonDTO;
import com.addi.crm.dto.NationalRegistrySimulationResponseDTO;

@FeignClient(name = "national-registry-service", url = "localhost:9000")
public interface NationalRegistryFeignClient {

	@GetMapping("national/registers/{clientId}")
	public ResponseEntity<JsonDTO<NationalRegistrySimulationResponseDTO>> getNationalRegistryInformation(
			@PathVariable("clientId") String clientId);

}

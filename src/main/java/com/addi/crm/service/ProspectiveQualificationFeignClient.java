package com.addi.crm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.addi.crm.dto.JsonDTO;
import com.addi.crm.dto.ProspectDTO;
import com.addi.crm.dto.ProspectQualificationSimulationResponseDTO;

@FeignClient(name = "prospect-qualification-service", url = "localhost:9000")
public interface ProspectiveQualificationFeignClient {

	@PostMapping("prospectives")
	public ResponseEntity<JsonDTO<ProspectQualificationSimulationResponseDTO>> getProspectiveQualificationInformation(
			@RequestBody ProspectDTO prospectDTO);

}

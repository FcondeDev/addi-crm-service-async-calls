package com.addi.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.addi.crm.async.AsyncCalls;
import com.addi.crm.dto.CrmResponseDTO;
import com.addi.crm.dto.NationalArchivesSimulationResponseDTO;
import com.addi.crm.dto.NationalRegistrySimulationResponseDTO;
import com.addi.crm.dto.ProspectDTO;
import com.addi.crm.dto.ProspectQualificationSimulationResponseDTO;
import com.addi.crm.exception.CustomException;
import com.addi.crm.exception.ErrorEnum;
import com.addi.crm.exception.NotFoundException;
import com.addi.crm.service.CrmService;
import com.addi.crm.service.ProspectiveQualificationFeignClient;

@Service
public class CrmServiceImpl implements CrmService {

	@Autowired
	private ProspectiveQualificationFeignClient prospectiveQualificationFeignClient;

	Logger logger = LoggerFactory.getLogger(CrmServiceImpl.class);

	private List<Map<String, String>> clientInformation;

	@Autowired
	private AsyncCalls asyncCalls;

	@PostConstruct
	public void init() {
		Map<String, String> client1Information = new HashMap<>();
		client1Information.put("clientId", "1");
		client1Information.put("name", "pepito");
		client1Information.put("lastname", "perez");
		client1Information.put("email", "pepito@gmail.com");
		clientInformation = new ArrayList<>();
		clientInformation.add(client1Information);
	}

	@Override
	public CrmResponseDTO evaluateClient(String clientId) {

		boolean successfulProcess = false;
		Optional<Map<String, String>> client1 = clientInformation.stream()
				.filter(client -> client.get("clientId").equals(clientId)).findFirst();

		if (!client1.isPresent())
			throw new NotFoundException();

		CompletableFuture<NationalRegistrySimulationResponseDTO> nationalRegistryResult = asyncCalls
				.getNationalRegistryResult(clientId);
		CompletableFuture<NationalArchivesSimulationResponseDTO> nationalArchivesResult = asyncCalls
				.getNationalArchivesResult(clientId);

		try {
			successfulProcess = getProspectiveQualificationResult(new ProspectDTO(clientId,
					nationalRegistryResult.get().isSuccess(), nationalArchivesResult.get().isSuccess())).isSuccess();
		} catch (Exception e) {
			logger.error("interrupted", e);
			throw new CustomException(ErrorEnum.ERROR_GETTING_INFORMATION_FROM_ASYNC, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return successfulProcess ? new CrmResponseDTO("Approved!!", true)
				: new CrmResponseDTO("Crm validation failed", false);
	}

	public ProspectQualificationSimulationResponseDTO getProspectiveQualificationResult(ProspectDTO prospectDTO) {
		try {
			logger.info("seding sycn call to prospective qualifications..");
			return prospectiveQualificationFeignClient.getProspectiveQualificationInformation(prospectDTO).getBody()
					.getData();
		} catch (Exception e) {
			throw new CustomException(ErrorEnum.FEIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

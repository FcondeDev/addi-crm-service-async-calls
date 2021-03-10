package com.addi.crm.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.addi.crm.async.AsyncCalls;
import com.addi.crm.dto.CrmResponseDTO;
import com.addi.crm.dto.JsonDTO;
import com.addi.crm.dto.NationalArchivesSimulationResponseDTO;
import com.addi.crm.dto.NationalRegistrySimulationResponseDTO;
import com.addi.crm.dto.ProspectDTO;
import com.addi.crm.dto.ProspectQualificationSimulationResponseDTO;
import com.addi.crm.exception.CustomException;
import com.addi.crm.exception.ErrorEnum;
import com.addi.crm.exception.NotFoundException;
import com.addi.crm.model.Client;
import com.addi.crm.repository.CrmRepository;
import com.addi.crm.service.CrmService;
import com.addi.crm.service.ProspectiveQualificationFeignClient;

@Service
public class CrmServiceImpl implements CrmService {

	@Autowired
	private AsyncCalls asyncCalls;

	@Autowired
	private ProspectiveQualificationFeignClient prospectiveQualificationFeignClient;

	@Autowired
	private CrmRepository crmRepository;

	Logger logger = LoggerFactory.getLogger(CrmServiceImpl.class);

	@Override
	public CrmResponseDTO evaluateClient(String clientId) {

		boolean successfulProcess = false;

		Optional<Client> client1 = crmRepository.getClientById(clientId);

		if (!client1.isPresent())
			throw new NotFoundException();

		CompletableFuture<NationalRegistrySimulationResponseDTO> nationalRegistryResult = asyncCalls
				.getNationalRegistryResult(clientId);
		CompletableFuture<NationalArchivesSimulationResponseDTO> nationalArchivesResult = asyncCalls
				.getNationalArchivesResult(clientId);
		try {
			successfulProcess = getProspectiveQualificationResult(new ProspectDTO(clientId,
					nationalRegistryResult.get().isSuccess(), nationalArchivesResult.get().isSuccess())).getBody()
							.getData().isSuccess();
		} catch (Exception e) {
			logger.error("interrupted", e);
			throw new CustomException(ErrorEnum.ERROR_GETTING_INFORMATION_FROM_ASYNC, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return successfulProcess ? new CrmResponseDTO("Approved!!", true)
				: new CrmResponseDTO("Crm validation failed", false);
	}

	public ResponseEntity<JsonDTO<ProspectQualificationSimulationResponseDTO>> getProspectiveQualificationResult(
			ProspectDTO prospectDTO) {
		try {
			logger.info("seding sycn call to prospective qualifications..");
			return prospectiveQualificationFeignClient.getProspectiveQualificationInformation(prospectDTO);
		} catch (Exception e) {
			throw new CustomException(ErrorEnum.FEIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

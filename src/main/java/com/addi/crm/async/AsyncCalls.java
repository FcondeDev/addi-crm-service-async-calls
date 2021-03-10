package com.addi.crm.async;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.addi.crm.dto.NationalArchivesSimulationResponseDTO;
import com.addi.crm.dto.NationalRegistrySimulationResponseDTO;
import com.addi.crm.exception.CustomException;
import com.addi.crm.exception.ErrorEnum;
import com.addi.crm.service.NationArchiveFeignClient;
import com.addi.crm.service.NationalRegistryFeignClient;

@Component
public class AsyncCalls {

	@Autowired
	private NationalRegistryFeignClient nationalRegistryFeignClient;

	@Autowired
	private NationArchiveFeignClient nationArchiveFeignClient;

	Logger logger = LoggerFactory.getLogger(AsyncCalls.class);

	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

	@Async
	public CompletableFuture<NationalRegistrySimulationResponseDTO> getNationalRegistryResult(String clientId) {
		try {
			logger.info("seding asycn call to national registry.. {}", format.format(new Date()));
			CompletableFuture<NationalRegistrySimulationResponseDTO> nationalRegistryResult = CompletableFuture
					.completedFuture(
							nationalRegistryFeignClient.getNationalRegistryInformation(clientId).getBody().getData());
			logger.info("nationalRegistryResult got it");
			return nationalRegistryResult;
		} catch (Exception e) {
			throw new CustomException(ErrorEnum.FEIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Async
	public CompletableFuture<NationalArchivesSimulationResponseDTO> getNationalArchivesResult(String clientId) {
		try {
			logger.info("seding asycn call to national archives.. {}", format.format(new Date()));
			CompletableFuture<NationalArchivesSimulationResponseDTO> nationalArchivesResult = CompletableFuture
					.completedFuture(
							nationArchiveFeignClient.getNationalArchivesInformation(clientId).getBody().getData());
			logger.info("nationalArchivesResult got it");
			return nationalArchivesResult;
		} catch (Exception e) {
			throw new CustomException(ErrorEnum.FEIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

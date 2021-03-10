package com.addi.crm;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.addi.crm.async.AsyncCalls;
import com.addi.crm.dto.JsonDTO;
import com.addi.crm.dto.NationalArchivesSimulationResponseDTO;
import com.addi.crm.dto.NationalRegistrySimulationResponseDTO;
import com.addi.crm.dto.ProspectDTO;
import com.addi.crm.dto.ProspectQualificationSimulationResponseDTO;
import com.addi.crm.exception.NotFoundException;
import com.addi.crm.service.NationArchiveFeignClient;
import com.addi.crm.service.NationalRegistryFeignClient;
import com.addi.crm.service.ProspectiveQualificationFeignClient;
import com.addi.crm.service.impl.CrmServiceImpl;

@SpringBootTest
class AddiCrmServiceApplicationTests {

	@Autowired
	private CrmServiceImpl crmService;

	@MockBean
	private NationalRegistryFeignClient nationalRegistryFeignClient;

	@MockBean
	private NationArchiveFeignClient nationArchiveFeignClient;

	@MockBean
	private ProspectiveQualificationFeignClient prospectiveQualificationFeignClient;

	@DisplayName("CLIENT NOT FOUND")
	@Test
	void clientNotFound() {
		assertThrows(NotFoundException.class, () -> crmService.evaluateClient("2"));
	}

	@DisplayName("ASYNC AND SYNC CALLS TEST")
	@Test
	void asyncAndsyncCallsTest() {

		ProspectQualificationSimulationResponseDTO prospectQualificationSimulationResponseDTO = new ProspectQualificationSimulationResponseDTO(
				false);

		doReturn(new ResponseEntity<>(new JsonDTO<>(new NationalRegistrySimulationResponseDTO(true, null)),
				HttpStatus.OK)).when(nationalRegistryFeignClient).getNationalRegistryInformation("1");

		doReturn(new ResponseEntity<>(new JsonDTO<>(new NationalArchivesSimulationResponseDTO(true, null)),
				HttpStatus.OK)).when(nationArchiveFeignClient).getNationalArchivesInformation("1");

		doReturn(new ResponseEntity<>(new JsonDTO<>(prospectQualificationSimulationResponseDTO), HttpStatus.OK))
				.when(prospectiveQualificationFeignClient)
				.getProspectiveQualificationInformation(Mockito.any(ProspectDTO.class));

		assertNotNull(crmService.evaluateClient("1"));
	}

	@DisplayName("SUCCESSFULY CALLS")
	@Test
	void successfulCall() {

		ProspectQualificationSimulationResponseDTO prospectQualificationSimulationResponseDTO = new ProspectQualificationSimulationResponseDTO(
				true);

		AsyncCalls asyncCallsMock = org.mockito.Mockito.mock(AsyncCalls.class);

		doReturn(new ResponseEntity<>(new JsonDTO<>(new NationalRegistrySimulationResponseDTO(true, null)),
				HttpStatus.OK)).when(nationalRegistryFeignClient).getNationalRegistryInformation("1");

		doReturn(new ResponseEntity<>(new JsonDTO<>(new NationalArchivesSimulationResponseDTO(true, null)),
				HttpStatus.OK)).when(nationArchiveFeignClient).getNationalArchivesInformation("1");

		doReturn(new ResponseEntity<>(new JsonDTO<>(prospectQualificationSimulationResponseDTO), HttpStatus.OK))
				.when(prospectiveQualificationFeignClient)
				.getProspectiveQualificationInformation(Mockito.any(ProspectDTO.class));

		when(asyncCallsMock.getNationalArchivesResult("1"))
				.thenReturn(CompletableFuture.completedFuture(new NationalArchivesSimulationResponseDTO(true, null)));

		when(asyncCallsMock.getNationalRegistryResult("1"))
				.thenReturn(CompletableFuture.completedFuture(new NationalRegistrySimulationResponseDTO(true, null)));

		assertNotNull(crmService.evaluateClient("1"));
		assertTrue(crmService.evaluateClient("1").isSuccess());
	}

}

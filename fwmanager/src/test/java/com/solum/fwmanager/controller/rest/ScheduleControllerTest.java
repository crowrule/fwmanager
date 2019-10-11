package com.solum.fwmanager.controller.rest;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solum.fwmanager.dto.OTAStationScheduleDTO;
import com.solum.fwmanager.service.AimsService;
import com.solum.fwmanager.service.OTAScheduleService;
import com.solum.fwmanager.service.ScheduleArrangeService;


//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	ScheduleArrangeService	scheduleArrangeServiceMock;
	
	@MockBean
	OTAScheduleService	otaScheduleService;
	
	@MockBean
	AimsService	aimsService;
	
    @BeforeEach
    public void init(){

        when(scheduleArrangeServiceMock.setAutoArrangeOTASchedule("1", "ST001"))
        .thenReturn(OTAStationScheduleDTO.builder()
				.code("ST001")
				.gwCount(1)
				.first(LocalDateTime.now().plusDays(1))
				.last(LocalDateTime.now().plusDays(1).plusHours(1))
				.build())
        .thenReturn(null);
    }
	
	
	@Test
	@Order(1)  
	public void whenWithValidFWId_thenReturns200and404() throws Exception {

		List<String>	stationList = new ArrayList<String>(Arrays.asList("ST001"));
		
		mockMvc.perform(post("/api/otaschedulebyfw/{packageId}", "1")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(stationList)))
		        .andExpect(status().isOk());
		
		mockMvc.perform(post("/api/otaschedulebyfw/{packageId}", "1")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(stationList)))
		        .andExpect(status().isNotFound());

		//ArgumentCaptor<OTAStationScheduleDTO> otaStationScheduleDTOCaptor = ArgumentCaptor.forClass(OTAStationScheduleDTO.class);

		//verify(scheduleArrangeService, times(1)).setAutoArrangeOTASchedule("1", "ST001");
		
		//assertThat(userCaptor.getValue().getName()).isEqualTo("Zaphod");
		//assertThat(userCaptor.getValue().getEmail()).isEqualTo("zaphod@galaxy.net");

	}

	@Test
	public void whenNoDisabledOTAScheduled_thenReturns204() throws Exception {
	   mockMvc.perform(get("/api/otaschedule/disabled"))
	        .andExpect(status().isNoContent());
	}
	
	@Test
	public void testCheckHeartBeat() {
		try {
			mockMvc.perform(get("/api/heartbeat")
				    .contentType("application/json"))
				    .andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("mockMvc is failed.");
		}

	}

}

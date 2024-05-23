package com.example.spaceship;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


import com.example.spaceship.controller.SpaceShipController;
import com.example.spaceship.model.SpaceshipDTO;
import com.example.spaceship.model.app.PageableInfoObject;
import com.example.spaceship.service.SpaceshipService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class SpaceshipApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private SpaceshipService spaceshipService;
	@InjectMocks
	private SpaceShipController spaceShipController;

	private MockMvc mockMvc;
	
	
	@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(spaceShipController).build();
    }

	

    @Test
    public void createSpaceShip_Success() throws Exception {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("name", "Millennium Falcon");

        when(spaceshipService.getSpaceShipExistenceStatus("Millennium Falcon")).thenReturn(false);

        mockMvc = MockMvcBuilders.standaloneSetup(spaceShipController).build();

        mockMvc.perform(post("/spaceships/create-spaceship")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Millennium Falcon\"}"))
                .andExpect(status().isOk())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    public void createSpaceShip_Conflict() throws Exception {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("name", "Millennium Falcon");

        when(spaceshipService.getSpaceShipExistenceStatus("Millennium Falcon")).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(spaceShipController).build();

        mockMvc.perform(post("/spaceships/create-spaceship")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Millennium Falcon\"}"))
                .andExpect(status().isConflict());
    }

	
	 @Test
	    public void testFindSpaceShipById_Found() throws Exception {
	        SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
	        spaceshipDTO.setId(1L);
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(spaceshipDTO);

	        mockMvc.perform(get("/spaceships/get-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1L));
	    }

	    @Test
	    public void testFindSpaceShipById_NotFound() throws Exception {
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(null);

	        mockMvc.perform(get("/spaceships/get-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound());
	    }

	    @Test
	    public void testUpdateSpaceshipName_Found() throws Exception {
	        SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
	        spaceshipDTO.setId(1L);
	        spaceshipDTO.setName("Old Name");
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(spaceshipDTO);

	        Map<String, String> nameMap = new HashMap<>();
	        nameMap.put("name", "New Name");
	        ObjectMapper objectMapper = new ObjectMapper();
	        String nameMapJson = objectMapper.writeValueAsString(nameMap);

	        mockMvc.perform(put("/spaceships/update-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(nameMapJson))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.name").value("New Name"));
	    }

	    @Test
	    public void testUpdateSpaceshipName_NotFound() throws Exception {
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(null);

	        Map<String, String> nameMap = new HashMap<>();
	        nameMap.put("name", "New Name");
	        ObjectMapper objectMapper = new ObjectMapper();
	        String nameMapJson = objectMapper.writeValueAsString(nameMap);

	        mockMvc.perform(put("/spaceships/update-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(nameMapJson))
	                .andExpect(status().isNotFound());
	    }

	    @Test
	    public void testFilterSpaceShipList() throws Exception {
	        SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
	        spaceshipDTO.setId(1L);
	        Page<SpaceshipDTO> page = new PageImpl<>(Collections.singletonList(spaceshipDTO), PageRequest.of(0, 10), 1);

	        when(spaceshipService.findAllSpaceshipsByName(anyString(), any(Pageable.class))).thenReturn(page);

	        PageableInfoObject<Map<String, String>> pageableInfoObjectMap = new PageableInfoObject<>();
	        Map<String, String> filterMap = new HashMap<>();
	        filterMap.put("filter", "test");
	        pageableInfoObjectMap.setObject(filterMap);
	        pageableInfoObjectMap.setDirection("ASC");
	        pageableInfoObjectMap.setSortBy("id");
	        pageableInfoObjectMap.setPage(0);
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        String pageableInfoObjectJson = objectMapper.writeValueAsString(pageableInfoObjectMap);

	        mockMvc.perform(post("/spaceships/filter-spaceships")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(pageableInfoObjectJson))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.content[0].id").value(1L));
	    }

	    @Test
	    public void testDeleteSpaceshipById_Found() throws Exception {
	        SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
	        spaceshipDTO.setId(1L);
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(spaceshipDTO);

	        mockMvc.perform(delete("/spaceships/delete-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNoContent());
	    }

	    @Test
	    public void testDeleteSpaceshipById_NotFound() throws Exception {
	        when(spaceshipService.findSpaceShipById(1L)).thenReturn(null);

	        mockMvc.perform(delete("/spaceships/delete-spaceship/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound());
	    }
	    
		@Test
		public void getsAllSSuccess() throws Exception {
			SpaceshipDTO spaceshipDTO1 = new SpaceshipDTO("spaceship1");
			spaceshipDTO1.setId(1L);
			SpaceshipDTO spaceshipDTO2 = new SpaceshipDTO("spaceship2");
			spaceshipDTO2.setId(2L);
			SpaceshipDTO spaceshipDTO3 = new SpaceshipDTO("spaceship3");
			spaceshipDTO3.setId(3L);


			List<SpaceshipDTO> allSpaceShips = new ArrayList<>();
			allSpaceShips.add(spaceshipDTO1);
			allSpaceShips.add(spaceshipDTO2);
			allSpaceShips.add(spaceshipDTO3);

			when(spaceshipService.findAllSpaceships()).thenReturn(allSpaceShips);

			mockMvc = MockMvcBuilders.standaloneSetup(spaceShipController)
		            .setMessageConverters(new MappingJackson2HttpMessageConverter())
		            .build();
		    // Preparing the JSON request body
		    String requestBody = "{ \"direction\": \"ASC\", \"sortBy\": \"id\", \"page\": 0 }";

		    mockMvc.perform(post("/spaceships/all-spaceships")
		            .contentType(MediaType.APPLICATION_JSON_VALUE)
		            .content(requestBody))
		            .andExpect(status().isOk());
		}

}

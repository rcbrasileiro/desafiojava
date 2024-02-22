package com.desafio.api.web.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.desafio.api.domain.Car;
import com.desafio.api.domain.User;
import com.desafio.api.repository.UserRepository;
import com.desafio.api.service.CarService;
import com.desafio.api.service.UserServiceImpl;
import com.desafio.api.web.controller.mock.CarMock;
import com.desafio.api.web.controller.mock.UserMock;
import com.desafio.api.web.dto.AuthRequestDTO;
import com.desafio.api.web.dto.CarFormDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserServiceImpl userService;


	@Test
	public void testSaveCar() throws Exception {

		User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);

		Car savedCar = CarMock.create();

		when(carService.save(any(Car.class))).thenReturn(savedCar);

		CarFormDTO carFormDTO = new CarFormDTO(2022, "XYZ-5679", "Ford Fusion", "Preto");

		String token = getToken();

		mockMvc.perform(post("/api/cars").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(carFormDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.year", equalTo(savedCar.getYear())))
				.andExpect(jsonPath("$.licensePlate", equalTo(savedCar.getLicensePlate())))
				.andExpect(jsonPath("$.model", equalTo(savedCar.getModel())))
				.andExpect(jsonPath("$.color", equalTo(savedCar.getColor())));
	}
	
	@Test
	public void testUpdateCar() throws Exception {

		User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);

		Car existingCar = CarMock.create();

		when(carService.update(any(Car.class))).thenReturn(existingCar);
		
		CarFormDTO carFormDTO = new CarFormDTO(2022, "XYZ-5679", "Ford Fusion", "Preto");
		
		String token = getToken();

		mockMvc.perform(put("/api/cars/{id}", existingCar.getId()).header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(carFormDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(existingCar.getId()))
				.andExpect(jsonPath("$.year").value(existingCar.getYear()))
				.andExpect(jsonPath("$.licensePlate").value(existingCar.getLicensePlate()))
				.andExpect(jsonPath("$.model").value(existingCar.getModel()))
				.andExpect(jsonPath("$.color").value(existingCar.getColor()));
	}
	
	@Test
    public void testDeleteCar() throws Exception {
		
		User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
		
		Car car = CarMock.create();
		
		String token = getToken();

		mockMvc.perform(delete("/api/cars/{id}", car.getId())
				.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
	
	@Test
    public void testFindById_ExistingCar() throws Exception {
		
		User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
     
        Car existingCar = CarMock.create();
        
        when(carService.findById(existingCar.getId())).thenReturn(Optional.of(existingCar));

        String token = getToken();
        
        mockMvc.perform(get("/api/cars/{id}", existingCar.getId())
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingCar.getId()))
                .andExpect(jsonPath("$.year").value(existingCar.getYear()))
                .andExpect(jsonPath("$.licensePlate").value(existingCar.getLicensePlate()))
                .andExpect(jsonPath("$.model").value(existingCar.getModel()))
                .andExpect(jsonPath("$.color").value(existingCar.getColor()));
       
        Mockito.verify(carService, Mockito.times(1)).findById(existingCar.getId());
    }
	
	@Test
    public void testFindById_NonExistingCar() throws Exception {
		
		User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
		
        when(carService.findById(anyLong())).thenReturn(Optional.empty());
        
        String token = getToken();

        mockMvc.perform(get("/api/cars/{id}", 999L)
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(carService, Mockito.times(1)).findById(999L);
    }
	
	@Test
    public void testListCars() throws Exception {
        List<Car> carList = new ArrayList<Car>();
        
        carList.add(CarMock.create());
        carList.add(CarMock.create2());
        
        Page<Car> carsPage = new PageImpl<>(carList);
        
        User user = UserMock.create();

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
        
        when(carService.findAll(any(Pageable.class))).thenReturn(carsPage);
        
        String token = getToken();

        mockMvc.perform(get("/api/cars")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(carList.size()));

        Mockito.verify(carService, Mockito.times(1)).findAll(any(Pageable.class));
    }

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getToken() throws Exception {
		
		AuthRequestDTO authRequestDTO = new AuthRequestDTO("joaosilva", "senha123");
		String authRequestJson = asJsonString(authRequestDTO);

		MvcResult result = mockMvc.perform(post("/api/signin").contentType(MediaType.APPLICATION_JSON)
				.content(authRequestJson).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());

		return jsonNode.get("token").asText();
	}

}

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

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
	
	private User user;
	
	private Car car;
	
	private CarFormDTO carFormDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		user = UserMock.create();
		car = CarMock.create();
		carFormDTO = new CarFormDTO(2022, "XYZ-5679", "Ford Fusion", "Preto");
		
	}


	@Test
	public void testSaveCar() throws Exception {


		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);

		when(carService.save(any(Car.class))).thenReturn(car);

		String token = getToken();

		mockMvc.perform(post("/api/cars").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(carFormDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.year", equalTo(car.getYear())))
				.andExpect(jsonPath("$.licensePlate", equalTo(car.getLicensePlate())))
				.andExpect(jsonPath("$.model", equalTo(car.getModel())))
				.andExpect(jsonPath("$.color", equalTo(car.getColor())));
	}
	
	@Test
	public void testUpdateCar() throws Exception {

		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);

		when(carService.update(any(Car.class))).thenReturn(car);
		
		String token = getToken();

		mockMvc.perform(put("/api/cars/{id}", car.getId()).header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(carFormDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(car.getId()))
				.andExpect(jsonPath("$.year").value(car.getYear()))
				.andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.color").value(car.getColor()));
	}
	
	@Test
    public void testDeleteCar() throws Exception {
		
		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
		
		String token = getToken();

		mockMvc.perform(delete("/api/cars/{id}", car.getId())
				.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
	
	@Test
    public void testFindById_ExistingCar() throws Exception {
		
		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
     
        when(carService.findById(car.getId())).thenReturn(Optional.of(car));

        String token = getToken();
        
        mockMvc.perform(get("/api/cars/{id}", car.getId())
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.year").value(car.getYear()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.color").value(car.getColor()));
       
        Mockito.verify(carService, Mockito.times(1)).findById(car.getId());
    }
	
	@Test
    public void testFindById_NonExistingCar() throws Exception {
		
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
        
        List<Car> carList = List.of(CarMock.create(), CarMock.create2());
        
        Page<Car> carsPage = new PageImpl<>(carList);
        
		when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
        
        when(carService.findAllByUserId(any(Pageable.class))).thenReturn(carsPage);
        
        String token = getToken();

        mockMvc.perform(get("/api/cars")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(carList.size()));

        Mockito.verify(carService, Mockito.times(1)).findAllByUserId(any(Pageable.class));
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

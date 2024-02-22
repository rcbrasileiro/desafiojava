package com.desafio.api.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
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

import com.desafio.api.domain.User;
import com.desafio.api.repository.UserRepository;
import com.desafio.api.service.CarService;
import com.desafio.api.service.UserServiceImpl;
import com.desafio.api.web.controller.mock.UserMock;
import com.desafio.api.web.dto.CarFormDTO;
import com.desafio.api.web.dto.UserFormDTO;
import com.desafio.api.web.dto.UserResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserServiceImpl userService;

	private User user;

	private UserFormDTO userFormDTO;

	@BeforeEach
	void setUp() throws Exception {
		user = UserMock.createUserCar();

		CarFormDTO carFormDTO = new CarFormDTO(2022, "XYZ-5679", "Ford Fusion", "Preto");
		CarFormDTO carFormDTO2 = new CarFormDTO(2022, "ABC-5679", "BMW 320", "Azul");

		List<CarFormDTO> carList = List.of(carFormDTO, carFormDTO2);

		LocalDate birthday = LocalDate.of(1990, Month.JANUARY, 1);
		Date dateBirthday = Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant());
		userFormDTO = new UserFormDTO("Joao", "Silva", "joao@example.com", dateBirthday, "joaosilva",
				"$2a$12$4AOuyX3CZVD8n7Bk9aP2FO/YNxSWKZB7BpUlum/h0QuPJ1ZU1hnmK", "(12) 3456-7890", carList);
	}

	@Test
	public void testSaveUser() throws Exception {

		when(userService.save(any(User.class))).thenReturn(user);				

		MvcResult result = mockMvc
				.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userFormDTO)))
				.andExpect(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString();
		UserResultDTO returnedUser = new ObjectMapper().readValue(content, UserResultDTO.class);

		assertEquals(user.getFirstName(), returnedUser.firstName());
		assertEquals(user.getLastName(), returnedUser.lastName());
		assertEquals(user.getEmail(), returnedUser.email());
		assertEquals(user.getLogin(), returnedUser.login());
		assertEquals(user.getPhone(), returnedUser.phone());
		assertEquals(user.getBirthday(), returnedUser.birthday());

		assertEquals(user.getCars().get(0).getId(), returnedUser.cars().get(0).id());
		assertEquals(user.getCars().get(1).getId(), returnedUser.cars().get(1).id());
	}

	@Test
	public void testUpdateUser() throws Exception {

		when(userService.update(any(User.class))).thenReturn(user);
		
		MvcResult result = mockMvc.perform(put("/api/users/{id}", user.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userFormDTO))).andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		UserResultDTO updatedUser = new ObjectMapper().readValue(content, UserResultDTO.class);
		
		assertEquals(user.getFirstName(), updatedUser.firstName());
		assertEquals(user.getLastName(), updatedUser.lastName());
		assertEquals(user.getEmail(), updatedUser.email());
		assertEquals(user.getLogin(), updatedUser.login());
		assertEquals(user.getPhone(), updatedUser.phone());
		assertEquals(user.getBirthday(), updatedUser.birthday());

		assertEquals(user.getCars().get(0).getId(), updatedUser.cars().get(0).id());
		assertEquals(user.getCars().get(1).getId(), updatedUser.cars().get(1).id());
	}
	
	@Test
    public void testDeleteUser() throws Exception {
		
		mockMvc.perform(delete("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
	
	@Test
    public void testFindById_ExistingUser() throws Exception {
		
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));

        MvcResult result = mockMvc.perform(get("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        
        String content = result.getResponse().getContentAsString();
		UserResultDTO findedUser = new ObjectMapper().readValue(content, UserResultDTO.class);
		
		assertEquals(user.getFirstName(), findedUser.firstName());
		assertEquals(user.getLastName(), findedUser.lastName());
		assertEquals(user.getEmail(), findedUser.email());
		assertEquals(user.getLogin(), findedUser.login());
		assertEquals(user.getPhone(), findedUser.phone());
		assertEquals(user.getBirthday(), findedUser.birthday());

		assertEquals(user.getCars().get(0).getId(), findedUser.cars().get(0).id());
		assertEquals(user.getCars().get(1).getId(), findedUser.cars().get(1).id());
       
        Mockito.verify(userService, Mockito.times(1)).findById(user.getId());
    }
	
	@Test
    public void testFindById_NonExistingUser() throws Exception {
		
        when(userService.findById(anyLong())).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/users/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(userService, Mockito.times(1)).findById(999L);
    }
	
	@Test
    public void testListCars() throws Exception {
        
        List<User> userList = List.of(UserMock.create(), UserMock.createUserCar());
        
        Page<User> userPage = new PageImpl<>(userList);
        
        when(userService.findAll(any(Pageable.class))).thenReturn(userPage);
        
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(userList.size()));

        Mockito.verify(userService, Mockito.times(1)).findAll(any(Pageable.class));
    }

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

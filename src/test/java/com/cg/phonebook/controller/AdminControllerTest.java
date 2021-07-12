
package com.cg.phonebook.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.repository.PhonebookUsersRepository;
import com.cg.phonebook.service.AdminService;
import com.cg.phonebook.service.PhonebookUsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

	@MockBean
	private AdminService adminService;
	@MockBean
	private PhonebookUsersRepository userRepo;
	@MockBean
	private PhonebookUsersService userService;
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void test1_findAllUSers() throws Exception {
		
		List<UserEntity> userList = new ArrayList<>();
		UserEntity user1 = new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null);
		userList.add(user1);
		Mockito.when(adminService.findAllUsers()).thenReturn(userList);
		this.mockMvc.perform(get("/api/phonebook/allUsers"))
		.andExpect(status().isOk());
	}
	
	@Test
	void test2_findByUserId() throws Exception {
		
		UserEntity user = new UserEntity();
		user.setFirstName("Sai");
		user.setLastName("krishna");
		user.setUserName("US09");
		user.setEmail("saikrishna@gmail.com");
		user.setPhoneNumber("9923456781");
		user.setPassword("Chaitanya@1");
		user.setConfirmPassword("Chaitanya@1");
		when(adminService.deleteUserByUserName(Mockito.any())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/api/phonebook/byUserIdentifier/US09")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String inputJson = this.mapToJson(user);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(inputJson).isEqualTo(outputInJson);
		assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
	}
	
	@Test
	void test3_findByUserId() throws Exception {
		
		Mockito.when(adminService.deleteUserByUserName(Mockito.any())).thenReturn(null);
		this.mockMvc.perform(get("/api/phonebook/byUserIdentifier/US01"))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	void test6_deleteByUserId() throws Exception{
		
		UserEntity user = new UserEntity();
		user.setFirstName("Sai");
		user.setLastName("krishna");
		user.setUserName("US09");
		user.setEmail("saikrishna@gmail.com");
		user.setPhoneNumber("9923456781");
		user.setPassword("Chaitanya@1");
		user.setConfirmPassword("Chaitanya@1");
		when(adminService.deleteUserByUserName(Mockito.any())).thenReturn(true);
		this.mockMvc.perform(delete("/api/phonebook/byIdentifier/US01"))
		.andExpect(status().isOk());
		
	}
	
	@Test
	void test7_deleteByUserId() throws Exception{
		
		Mockito.when(adminService.deleteUserByUserName(Mockito.any())).thenReturn(false);
		this.mockMvc.perform(delete("/api/phonebook/byIdentifier/US01"))
		.andExpect(status().isBadRequest());
		
	}
	
	
	

	@Test
	void test10_createNewAdmin() throws Exception {
		
		AdminEntity admin = new AdminEntity();
		admin.setAdminUserName("AD09");
		admin.setFirstName("Sai");
		admin.setLastName("Krishna");
		admin.setPhoneNumber("99786545674");
		admin.setEmail("saikrish@gmail.com");
		admin.setPassword("Chaitanya@1");
		admin.setConfirmPassword("Chaitanya@1");
		
		String inputJson = this.mapToJson(admin);
		Mockito.when(adminService.saveAdmin(Mockito.any(AdminEntity.class))).thenReturn(admin);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/phonebook/adminregister")
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputJson);
		assertEquals(HttpStatus.CREATED.value(),response.getStatus());
	}
	
	 private String mapToJson(Object object) throws JsonProcessingException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 return objectMapper.writeValueAsString(object);
	 }
	
}

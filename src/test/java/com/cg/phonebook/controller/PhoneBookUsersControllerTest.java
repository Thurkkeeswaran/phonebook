package com.cg.phonebook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.service.PhonebookUsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneBookUsersControllerTest {
	
	@MockBean
	private PhonebookUsersService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@Test
	void test1_findAllContacts() throws Exception {
		
		List<Contact> contactList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		contactList.add(contact);
		UserEntity user = new UserEntity();
		user.setFirstName("Sai");
		user.setLastName("krishna");
		user.setUserName("US09");
		user.setEmail("saikrishna@gmail.com");
		user.setPhoneNumber("9923456781");
		user.setAddress("address");
		user.setPassword("Chaitanya@1");
		user.setConfirmPassword("Chaitanya@1");
		user.setContactList(contactList);
		String inputJson = this.mapToJson(user);
		Mockito.when(userService.addNewUser(Mockito.any(UserEntity.class))).thenReturn(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/phonebook/newUserRegister")
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputJson);
		assertEquals(HttpStatus.CREATED.value(),response.getStatus());
	}
	
	@Test
	void test2_deleteContact() throws Exception{
		
		List<Contact> contactList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		contactList.add(contact);
		UserEntity user = new UserEntity();
		user.setFirstName("Sai");
		user.setLastName("krishna");
		user.setUserName("US09");
		user.setEmail("saikrishna@gmail.com");
		user.setPhoneNumber("9923456781");
		user.setPassword("Chaitanya@1");
		user.setConfirmPassword("Chaitanya@1");
		user.setContactList(contactList);
		Mockito.when(userService.deleteContact(1,"9951763425")).thenReturn(true);
		this.mockMvc.perform(delete("/api/phonebook/deleteContact/1/9951763425"))
		.andExpect(status().isOk());
		
	}
	
	@Test
	void test3_deleteContact() throws Exception{
		
		Mockito.when(userService.deleteContact(1,"9923456781")).thenReturn(false);
		this.mockMvc.perform(delete("/api/phonebook/deleteContact/1/9923456781"))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void test4_addContact() throws Exception {
		
		List<Contact> contactList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		contactList.add(contact);
		UserEntity user = new UserEntity();
		user.setFirstName("Sai");
		user.setLastName("krishna");
		user.setUserName("US09");
		user.setEmail("saikrishna@gmail.com");
		user.setPhoneNumber("9923456781");
		user.setPassword("Chaitanya@1");
		user.setConfirmPassword("Chaitanya@1");
		user.setContactList(contactList);
		String inputJson = this.mapToJson(user);
		//Mockito.when(userService.addContact(Mockito.any(), Mockito.any())).thenReturn(contact);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/phonebook/addContact/US09")
				.accept(MediaType.APPLICATION_JSON).content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputJson);
	}
	private String mapToJson(Object object) throws JsonProcessingException {
		 ObjectMapper objectMapper = new ObjectMapper();
		 return objectMapper.writeValueAsString(object);
	 }
	
}

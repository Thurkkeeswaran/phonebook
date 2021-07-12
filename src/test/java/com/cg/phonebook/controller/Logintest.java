package com.cg.phonebook.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cg.phonebook.PhoneBookApplication;
import com.cg.phonebook.controller.AdminController;
import com.cg.phonebook.controller.PhonebookUsersController;
import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.service.AdminService;
import com.cg.phonebook.service.MapValidationErrorService;
import com.cg.phonebook.repository.AdminRepository;
import com.cg.phonebook.repository.ContactRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;
 
import com.cg.phonebook.service.PhonebookUsersService;

import junit.framework.Assert;
 

@SuppressWarnings("deprecation")
@ContextConfiguration(classes=PhoneBookApplication.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = { AdminController.class, AdminService.class,PhonebookUsersController.class,PhonebookUsersService.class})
 
class Logintest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Autowired
	private	MockMvc mockMvc;
	@MockBean
	private	AdminService adminService;
	@MockBean
	private	PhonebookUsersService phonebookservice;
	@MockBean
	private MapValidationErrorService mapValidationErrorService;
	@InjectMocks
	private	AdminController adminController;
	@InjectMocks
	private PhonebookUsersController usercontroller;
	@MockBean
	private	PhonebookUsersRepository userRepo;
@MockBean
private ContactRepository contactrepo;
	@MockBean
	private AdminRepository adminrepo;
	 

	@Test
	void testLogin() throws Exception {
		String URI = "/adminLogin/{email}/{password}";

		AdminEntity a = new AdminEntity();
		a.setEmail("ruhi123@gmail.com");
        a.setPassword("ruhi123");
		 

		Mockito.when(adminrepo.loginAdmin("ruhi123@gmail.com","ruhi123")).thenReturn(a);
		 

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URI, "ruhi123@gmail.com", "ruhi123").accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		assertThat("Login successfull"== jsonOutput);
	}
	@Test
	void testLoginUser() throws Exception {
		String URI = "/userLogin/{email}/{password}";

		UserEntity a1 = new UserEntity();
		a1.setEmail("megha123@gmail.com");
        a1.setPassword("megha123");
		 

		Mockito.when(contactrepo.loginUser("megha123@gmail.com","megha123")).thenReturn(a1);
		 

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URI, "megha123@gmail.com", "megha123").accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String jsonOutput = mockHttpServletResponse.getContentAsString();

		assertThat("Login successfull"== jsonOutput);
	}
}

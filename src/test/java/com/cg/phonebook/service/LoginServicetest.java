package com.cg.phonebook.service;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
 
import org.springframework.boot.test.context.SpringBootTest;
 
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.phonebook.PhoneBookApplication;
import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.repository.AdminRepository;
import com.cg.phonebook.repository.ContactRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;
import com.cg.phonebook.service.AdminService;
import com.cg.phonebook.service.PhonebookUsersService;

import junit.framework.Assert;
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=PhoneBookApplication.class)
public class LoginServicetest {

	@Mock
	AdminRepository adminRepo;
	@Mock
	PhonebookUsersRepository userRepo;
	@Mock
	ContactRepository contactrepo;
	@InjectMocks
	AdminService adminService;
	@InjectMocks
	PhonebookUsersService phonebookservice;
	
    @Test
	void testfindByMailId(){
		AdminEntity a = new AdminEntity();
		a.setEmail("ruhi123@gmail.com");
	 
		Mockito.when(adminRepo.findByMailID("ruhi123@gmail.com")).thenReturn(a);
		AdminEntity a1 = adminService.findByMailId("ruhi123@gmail.com");
 	 
Assert.assertEquals(a.getEmail(),(a1.getEmail()));
	} 
    @Test
    void testUserLogin() {
    	UserEntity a = new UserEntity();
		a.setEmail("megha123@gmail.com");
		 
		Mockito.when(contactrepo.findByMailID(Mockito.anyString())).thenReturn(a);
		UserEntity a1 = phonebookservice.findByMailId("megha123@gmail.com");
 	 
Assert.assertEquals(a.getEmail(),(a1.getEmail()));	
    }
	 
}

package com.cg.phonebook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.repository.ContactRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;

@ExtendWith(SpringExtension.class)
public class PhonebookUsersServiceTest {

	@Mock
	PhonebookUsersRepository userRepo;
	@InjectMocks
	PhonebookUsersService userService;
	@Mock
	ContactRepository contactRepo;
     
	@Test
	void test1_addUser() {
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",null);
		Mockito.when(userRepo.save(user)).thenReturn(user);
		UserEntity a = userService.addNewUser(user);
		assertNotNull(a);
		assertEquals("Sai", a.getFirstName());
		assertEquals("krish", a.getLastName());
	    assertEquals("saikrish@gmail.com", a.getEmail());
		assertEquals("9978654321", a.getPhoneNumber());
		
	}
	
	@Test
	void test2_addContact() {
		
		List<Contact> conList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",conList);
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(user);
		UserEntity ue = userRepo.findByUserName("US01");
		ue.getContactList().add(contact);
		assertEquals(1,ue.getContactList().size());
	}
	
	@Test
	void test3_allContacts() {
		
		List<Contact> conList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",conList);
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(user);
		UserEntity ue = userRepo.findByUserName("US01");
		ue.getContactList().add(contact);
		Mockito.when(contactRepo.findAll()).thenReturn(ue.getContactList());
		List<Contact> clist = userService.viewAllContacts("US01");
		assertNotNull(clist);
	}
	
	@Test
	void test4_allContacts_return_null() {
		
		List<Contact> conList = new ArrayList<>();
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",conList);
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(user);
		UserEntity ue = userRepo.findByUserName("US01");
		Mockito.when(contactRepo.findAll()).thenReturn(ue.getContactList());
		List<Contact> clist = userService.viewAllContacts("US01");
		assertTrue(clist.isEmpty());
		
	}
	
	@Test
	void test5_searchByPhoneNumber() {
		
		List<Contact> conList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",conList);
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(user);
		UserEntity ue = userRepo.findByUserName("US01");
		ue.getContactList().add(contact);
		Mockito.when(contactRepo.findByContactNumber("9951763425")).thenReturn(contact);
		Contact clist = userService.findByContactNumber("9951763425");
		assertNotNull(clist);
		
	}
	
	@Test
	void test6_deleteContact() {
		
		List<Contact> conList = new ArrayList<>();
		Contact contact = new Contact("9951763425","Thanusha","thanusha@gmail.com");
		UserEntity user = new UserEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1","address",conList);
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(user);
		UserEntity ue = userRepo.findByUserName("US01");
		ue.getContactList().add(contact);
		for(Contact c:ue.getContactList()) {
			if(c.getContactNumber().equals("9951763425")) {
				System.out.println(c);
				boolean cd = userService.deleteContact(c.getUserId(), "9951763425");
				assertFalse(cd);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
//	@Test
//	void validateUserData() throws ResourceNotFoundException
//	{
//		try {
//		BDDMockito.given(userRepo.findByMailID("smilyeesonuu@gmail.com")).willReturn(new UserEntity("Vishnu@228","smilyeesonuu@gmail.com","Vishnu@228","Swapna","850019666","vardhan", null));
//		UserEntity user= userService.findByMailId("smilyeesonuu@gmail.com");
//		assertNull(user);
//		}
//		catch(NullPointerException e) {
//			assertTrue(true);
//		}
//	}
//
//	@Test
//	 void validateUserDataSave() throws ResourceNotFoundException
//	{
//		try {
//		UserEntity data=new UserEntity();
//		data.setPassword("Vishnu@228");
//		data.setEmail("smilyeesonuuu@gmail.com");
//		data.setConfirmPassword("Vishnu@228");
//		data.setFirstName("Swapna");
//		data.setPhoneNumber("850019666");
//		data.setLastName("vardhan");
//		
//    BDDMockito.given(userRepo.save(data)).willReturn(data);
//     assertThat(userService.addNewUser(data)).isEqualTo(data);
//		}
//		catch(NullPointerException e) {
//			assertTrue(true);
//		}
//	}
}

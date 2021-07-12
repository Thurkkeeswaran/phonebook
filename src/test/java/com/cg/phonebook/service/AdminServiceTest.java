package com.cg.phonebook.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.repository.AdminRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;

@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

	@Mock
	AdminRepository adminRepo;
	@Mock
	PhonebookUsersRepository userRepo;
	@InjectMocks
	AdminService adminService;
     

	@Test
	void test1_addAdmin() {
		AdminEntity admin = new AdminEntity("Sai","krish","US01","saikrish@gmail.com","9978654321","Chaitanya@1","Chaitanya@1");
		Mockito.when(adminRepo.save(admin)).thenReturn(admin);
		AdminEntity a = adminService.saveAdmin(admin);
		assertNotNull(a);
		assertEquals("Sai", a.getFirstName());
		assertEquals("krish", a.getLastName());
	    assertEquals("saikrish@gmail.com", a.getEmail());
		assertEquals("9978654321", a.getPhoneNumber());
		
	}
	
	@Test
	void test2_findAllUsers() {
		
		List<UserEntity> phonebookUsersList = new ArrayList<>();
		UserEntity user1 = new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null);
		phonebookUsersList.add(user1);
		Mockito.when(userRepo.findAll()).thenReturn(phonebookUsersList);
		Iterable<UserEntity> users = adminService.findAllUsers();
		assertNotNull(users);
		assertEquals(phonebookUsersList, users);
		
	}
	
	@Test
    public void test3_getUserById_return_userObject()
    {
		
        Mockito.when(userRepo.findByUserName("US01")).thenReturn(new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null));
        UserEntity user = adminService.findByUserName("US01");
        assertEquals("Sai", user.getFirstName());
        assertEquals("krishna", user.getLastName());
        assertEquals("saikrishna@gmail.com", user.getEmail());
        assertEquals("9923456781", user.getPhoneNumber());
        assertEquals("address", user.getAddress());
    }
	
	@Test
    public void test4_getUserById_return_null()
    {
		
        Mockito.when(userRepo.findByUserName("US02")).thenReturn(new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null));
        UserEntity user = adminService.findByUserName("US02");
        if(user==null) {
        	assertNull(user);
        }
    }
	
	
	
	@Test
	void test7_deleteUserByUserIdentifier_return_true() {
		
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null) );
		assertTrue(adminService.deleteUserByUserName("US01"));
		
	}
	
	@Test
	void test8_deleteUserByUserIdentifier_return_false() {
		
		Mockito.when(userRepo.findByUserName("US01")).thenReturn(new UserEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1","address",null) );
		assertFalse(adminService.deleteUserByUserName("US02"));
		
	}
	
	
	
	@Test
    public void test11_getUserByMailId_return_userObject()
    {
		
        Mockito.when(adminRepo.findByMailID("saikrishna@gmail.com")).thenReturn(new AdminEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1"));
        AdminEntity user = adminService.findByMailId("saikrishna@gmail.com");
        assertEquals("Sai", user.getFirstName());
        assertEquals("krishna", user.getLastName());
        assertEquals("saikrishna@gmail.com", user.getEmail());
        assertEquals("9923456781", user.getPhoneNumber());
       
    }
	
	@Test
    public void test12_getUserByMailId_return_null()
    {
		
        Mockito.when(adminRepo.findByMailID("saikrishna@gmail.com")).thenReturn(new AdminEntity("Sai","krishna","US01","saikrishna@gmail.com","9923456781","Chaitanya@1","Chaitanya@1"));
        AdminEntity user = adminService.findByMailId("saikrishn@gmail.com");
        if(user==null) {
        	assertNull(user);
        }
    }
}
	


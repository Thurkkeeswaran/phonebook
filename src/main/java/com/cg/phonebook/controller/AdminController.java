package com.cg.phonebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.exception.AdminException;
import com.cg.phonebook.exception.ResourceNotFoundException;
import com.cg.phonebook.exception.UserValidate;
import com.cg.phonebook.repository.AdminRepository;
import com.cg.phonebook.service.AdminService;
import com.cg.phonebook.service.MapValidationErrorService;

@CrossOrigin
@RestController
@RequestMapping("/api/phonebook")
public class AdminController {

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	AdminService adminService;
	@Autowired
	MapValidationErrorService mapValidationErrorService;

	/*
	 * View all the users in database
	 */
	@GetMapping("/allUsers")
	public Iterable<UserEntity> findAll() {
		Iterable<UserEntity> userList = adminService.findAllUsers();
		if (userList != null) {
			return adminService.findAllUsers();
		} else {
			throw new AdminException("No Contacts available");
		}
	}

	/*
	 * View User by identifier
	 */
	/*
	 * @GetMapping("/byUserName/{userName}") public ResponseEntity<?>
	 * getUserByUserName(@PathVariable String userName){ UserEntity user =
	 * adminService.findUserByUserName(userName); if(user !=null) { return new
	 * ResponseEntity<UserEntity>( user,HttpStatus.OK); } else { return new
	 * ResponseEntity<String>( userName+" is not available",HttpStatus.BAD_REQUEST);
	 * } }
	 */

	@GetMapping("/byUsername/{userName}")
	public ResponseEntity<?> findUserByUserName(@PathVariable String userName) {
		UserEntity contact = adminService.findByUserName(userName);
		if (contact == null) {
			return new ResponseEntity<String>(userName + " is not available", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<UserEntity>(contact, HttpStatus.OK);
		}
	}
	
	 /*
	  * 	@PutMapping("/updateContact/{contactId}")
	public ResponseEntity<Contact> updateContact(@PathVariable Long contactId, @RequestBody Contact contactDetails)
			throws ResourceNotFoundException {
			
		Contact contact = contactrepo.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not exist with id:" + contactId));
		
		contact.setContactName(contactDetails.getContactName());
		contact.setContactNumber(contactDetails.getContactNumber());
		contact.setEmail(contactDetails.getEmail());
		
		Contact updatedContact = contactrepo.save(contact);
		return ResponseEntity.ok(updatedContact);
	}
	  */
	@PutMapping("/updateAdmin/{adminId}")
	public ResponseEntity<AdminEntity> updateAdmin(@PathVariable Long adminId,
			@RequestBody AdminEntity adminDetails) throws ResourceNotFoundException {
		//logger.info("in updateEmail");
		AdminEntity adminEntity = adminRepo.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("No Admin found with this Id:" + adminId));
		
		adminEntity.setFirstName(adminDetails.getFirstName());
		adminEntity.setLastName(adminDetails.getLastName());
		adminEntity.setConfirmPassword(adminDetails.getConfirmPassword());
		adminEntity.setPassword(adminDetails.getPassword());
		adminEntity.setPhoneNumber(adminDetails.getPhoneNumber());
		adminEntity.setEmail(adminDetails.getEmail());
		adminEntity.setAdminUserName(adminDetails.getAdminUserName());
		
		
		AdminEntity updatedAdmin = adminRepo.save(adminEntity);

		return ResponseEntity.ok(updatedAdmin);
	}
	 
	//=======================================================
	/*
	 * @DeleteMapping("/{phoneNumber}") public ResponseEntity<?>
	 * removeUserByPhoneNumber(@PathVariable String phoneNumber){ boolean deleteUser
	 * = adminService.removeUserByPhoneNumber(phoneNumber); if(deleteUser == true) {
	 * return new ResponseEntity<String>(phoneNumber+" is deleted", HttpStatus.OK);
	 * } else { return new ResponseEntity<String>(phoneNumber+" is not available",
	 * HttpStatus.BAD_REQUEST); } }
	 */
	
	/*
	 * Delete User by User ID
	 */
	@DeleteMapping("/byUserName/{userName}")
	public ResponseEntity<?> deleteUserByUserName(@PathVariable String userName) {
		boolean deleteUser = adminService.deleteUserByUserName(userName.toUpperCase());
		if (deleteUser == true) {
			return new ResponseEntity<String>(userName + " is deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(userName + " is not available", HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * Admin login
	 */
	@GetMapping("/adminLogin/{email}/{password}")
	public String login(@PathVariable("email") String email, @PathVariable("password") String password) {
		Object obj = null;
		obj = adminRepo.loginAdmin(email, password);

		if (obj != null)
			return "Login successfull";
		else
			return "Id or password is incorrect";
	}

	/*
	 * New admin register
	 */
	@PostMapping("/adminregister")
	public ResponseEntity<?> addAdmin(@RequestBody AdminEntity data) throws ResourceNotFoundException {
		System.out.println(data);
		AdminEntity v = adminService.findByMailId(data.getEmail());
		UserValidate validateUser = new UserValidate();
		boolean user = validateUser.validateUser(data.getEmail());
		boolean password1 = validateUser.validatePassword(data.getPassword(), data.getEmail());

		for (AdminEntity e : adminRepo.findAll()) {
			if (e.getPhoneNumber().equalsIgnoreCase(data.getPhoneNumber())) {
				throw new AdminException("Phone Number already exist");
			}
			if (e.getAdminUserName().equalsIgnoreCase(data.getAdminUserName())) {
				throw new AdminException("Admin Id already exist!! Please Try another");
			}
		}
		if (user && password1) {
			if (v == null) {
				if (data.getPassword().equals(data.getConfirmPassword())) {
					AdminEntity admin = adminService.saveAdmin(data);
					return new ResponseEntity<AdminEntity>(admin, HttpStatus.CREATED);
				} else {
					throw new ResourceNotFoundException("confirm password must match with password");
				}
			} else
				throw new ResourceNotFoundException("Mail Id already exist!!");
		} else {
			return new ResponseEntity<String>("Enter mailId or password in specified format", HttpStatus.BAD_REQUEST);
		}
	}

}

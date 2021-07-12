package com.cg.phonebook.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.cg.phonebook.exception.ContactException;
import com.cg.phonebook.exception.ResourceNotFoundException;
import com.cg.phonebook.exception.UserValidate;
import com.cg.phonebook.repository.ContactRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;
import com.cg.phonebook.service.PhonebookUsersService;
import com.cg.phonebook.service.MapValidationErrorService;

@CrossOrigin
@RestController
@RequestMapping("/api/phonebook")
public class PhonebookUsersController {

	@Autowired
	PhonebookUsersRepository userRepo;
	@Autowired
	ContactRepository contactrepo;
	@Autowired
	PhonebookUsersService contactService;
	@Autowired
	MapValidationErrorService mapValidationErrorService;

	/*
	 * New user registration
	 */
	@PostMapping("/newUserRegister")
	public ResponseEntity<?> addUser(@RequestBody UserEntity data) throws ResourceNotFoundException {
		UserEntity v = contactService.findByMailId(data.getEmail());
		UserValidate validateUser = new UserValidate();
		boolean user = validateUser.validateUser(data.getEmail());
		boolean password1 = validateUser.validatePassword(data.getPassword(), data.getEmail());
		for (UserEntity e : userRepo.findAll()) {
			if (e.getPhoneNumber().equalsIgnoreCase(data.getPhoneNumber())) {
				throw new AdminException("Phone Number already exist");
			}
			if (e.getUserName().equalsIgnoreCase(data.getUserName())) {
				throw new AdminException("User Id already exist!! Please try another");
			}
		}
		if (user && password1) {
			if (v == null) {
				if (data.getPassword().equals(data.getConfirmPassword())) {
					UserEntity us = contactService.addNewUser(data);
					return new ResponseEntity<UserEntity>(us, HttpStatus.CREATED);
				} else {
					throw new AdminException("confirm password must match with password");
				}
			} else
				throw new AdminException("Mail Id already exist!!");
		} else {
			return new ResponseEntity<String>("Enter mailId or password in specified format", HttpStatus.BAD_REQUEST);
		}

	}

	/*
	 * User login
	 */
	@GetMapping("/userLogin/{email}/{password}")

	public String login(@PathVariable("email") String email, @PathVariable("password") String password) {
		Object obj = null;
		obj = contactrepo.loginUser(email, password);

		if (obj != null)
			return "Login Successfull";
		else
			return "Login Failed!! Please Check Your Credentials";
	}

	@PutMapping("/user/update")
	public UserEntity updateUser(@RequestBody UserEntity user) {
		return contactService.updateUser(user);
	}

	/*
	 * Add Contact
	 */
	@PostMapping("/addContact/{email}")
	public ResponseEntity<?> createContact(@Valid @PathVariable String email, @RequestBody Contact contact) {
		String userName = contactService.findByMailId(email).getUserName();
		UserEntity cont = contactService.addContact(userName, contact);
		if (contact != null) {
			return new ResponseEntity<>(cont, HttpStatus.OK);
		} else {
			throw new ContactException(userName + " does not exist");
		}
	}

	/*
	 * All Contact
	 */
	@GetMapping("/allContacts/{userName}")
	public ResponseEntity<?> viewAllContacts(@PathVariable String userName) {
		boolean flag = false;
		for (UserEntity e : userRepo.findAll()) {
			if (e.getUserName().equals(userName)) {
				flag = true;
				break;
			}
		}
		if (flag == true) {
			List<Contact> contactList = contactService.viewAllContacts(userName);
			if (contactList.isEmpty()) {
				return new ResponseEntity<String>("No Contacts Available", HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>(userName + " not found", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/byContactname/{contactName}/{useremail}")
	public ResponseEntity<?> findContactByContactName(@PathVariable String contactName,
			@PathVariable String useremail) {

		Contact contact = contactService.findByContactName(contactName);
		if (contact == null) {
			return new ResponseEntity<String>(contactName + " is not found in any contact List",
					HttpStatus.BAD_REQUEST);
		} else {

			long id = contact.getUserId();
			System.out.println(id);
			UserEntity user = contactService.findByMailId(useremail);
			List<Contact> cl = user.getContactList();
			System.out.println(cl);
			Contact c = cl.stream().filter(Contact -> contactName.equals(Contact.getContactName())).findAny()
					.orElse(null);
			System.out.println(c);
			if (c == null) {
				return new ResponseEntity<String>(contactName + " is not found in your contact List",
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Contact>(contact, HttpStatus.OK);
			}
		}
	}

	@GetMapping("/byContactnum/{contactNum}/{useremail}")
	public ResponseEntity<?> findContactByContactNumber(@PathVariable String contactNum,
			@PathVariable String useremail) {

		Contact contact = contactService.findByContactNumber(contactNum);
		System.out.println(contact);
		if (contact == null) {
			System.out.println(contact);

			return new ResponseEntity<String>(contactNum + " is not found in any contact List", HttpStatus.BAD_REQUEST);
		} else {

			long id = contact.getUserId();
			UserEntity user = contactService.findByMailId(useremail);
			List<Contact> cl = user.getContactList();
			Contact c = cl.stream().filter(Contact -> contactNum.equals(Contact.getContactNumber())).findAny()
					.orElse(null);

			if (c == null) {
				return new ResponseEntity<String>(contactNum + " is not found in your contact List",
						HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Contact>(contact, HttpStatus.OK);
			}
		}
	}

	@PutMapping("/updateContact/{contactId}")
	public ResponseEntity<Contact> updateContact(@PathVariable long contactId, @RequestBody Contact contactDetails)
			throws ResourceNotFoundException {

		Contact contact = contactrepo.findById(contactId).get();

		contact.setContactName(contactDetails.getContactName());
		contact.setContactNumber(contactDetails.getContactNumber());
		contact.setEmail(contactDetails.getEmail());

		Contact updatedContact = contactrepo.save(contact);

		return new ResponseEntity<>(updatedContact, HttpStatus.OK);

	}

	/*
	 * Delete contact by phone number
	 */
	@DeleteMapping("/deleteContact/{userId}/{phoneNumber}")
	public ResponseEntity<?> deleteContact(@PathVariable long userId, @PathVariable String phoneNumber) {
		boolean contact = contactService.deleteContact(userId, phoneNumber);
		if (contact == true) {
			return new ResponseEntity<String>("Contact Deleted!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Not deleted", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getContacts/{email}")
	public ResponseEntity<?> viewContactList(@PathVariable String email) {

		UserEntity user = contactService.findByMailId(email);
		List<Contact> contactList = user.getContactList();
		return new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);

	}

//	/*
//	 * Update the contact
//	 */
//	@PutMapping("/updateContact/{userIdentifier}")
//	public ResponseEntity<Contact> updateContact(@RequestBody Contact contact ,@PathVariable String userIdentifier ) {
//		Contact updatedContact=contactService.updateContact(contact, phoneNumber);
//		return new ResponseEntity<Contact>(updatedContact,HttpStatus.OK);
//		
//	}

}
package com.cg.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.exception.ContactException;
import com.cg.phonebook.exception.PhonebookUsersException;
import com.cg.phonebook.repository.ContactRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;

@Service
public class PhonebookUsersService {
	
	private static final Logger logger = LoggerFactory.getLogger(PhonebookUsersService.class);

	@Autowired
	PhonebookUsersRepository repo;
	@Autowired
	private ContactRepository contactRepository;
	
	 
	public UserEntity getUser(long id) {
		return repo.findById(id).get();
	}
	
	/*
	 * To register the new User
	 */
	public UserEntity addNewUser(UserEntity user) {
			return repo.save(user);
		
	}
	
    public UserEntity updateUser(UserEntity user) {
    	return repo.save(user);
		
	}
	
	
//	public Contact addContact(String userName, Contact contact) {
//		
//		UserEntity user = repo.findByUserName(userName);
//        if(user !=null) {
//                Contact con = new Contact();
//                for(Contact d : contactRepository.findAll()) {
//                    if(user.getUserId() == d.getUserId()) {
//                        if(d.getContactName().equalsIgnoreCase(contact.getContactName()))
//                        {
//                            logger.error("Name already exist");
//                            throw new ContactException("Name already exist");
//                        }
//                        if(d.getContactNumber().equals(contact.getContactNumber())) {
//                            logger.error("Number already exist");
//                            throw new ContactException("Number already exist");
//                        }
//                    }
//                }
//                con.setContactName(contact.getContactName());
//                con.setContactNumber(contact.getContactNumber());
//                con.setEmail(contact.getEmail());
//                con.setUserId(user.getUserId());
//                //user.getContactList().add(con);
//                logger.info("Contact details added");
//                return repo.save(contact);
//        }
//        return null;
//}

	
	/*
	 * View all the contacts
	 */
	public List<Contact> viewAllContacts(String userName){
		List<Contact> contactList = new ArrayList<Contact>();
		UserEntity user = repo.findByUserName(userName);
		
		long id = user.getUserId();
		for(Contact c : contactRepository.findAll()) {
			if(c.getUserId()==id) {
				contactList.add(c);
			}
		}
		return contactList;
	}
	public Contact findByContactName(String contactName) {
		Contact contact = null;
		for(Contact contacts:contactRepository.findAll()) {
			if(contacts.getContactName().equalsIgnoreCase(contactName)) {
				logger.info("Details found with contact name : "+contactName);
				contact=contacts;
			}
		}
		if(contact==null) {
			logger.error("Details not found");
		}
		return contact;
		
	}
	
	/*
	 * public Contact findContactByName(String contactName) { Contact contact =
	 * contactRepository.findByName(contactName); if (contact == null) {
	 * logger.error("Contact Name "+contactName+" not available"); throw new
	 * ContactException("Contact Name " + contactName + " not available");
	 * 
	 * } logger.info(contactName+" is found"); return contact;
	 * 
	 * }
	 */
	
	
	/*
	 * Delete contact by phone number
	 */
	public boolean deleteContact(long userId, String phoneNumber) {
		
		for(Contact c : contactRepository.findAll()) {
			if(c.getUserId()==userId && c.getContactNumber().equals(phoneNumber)) {
				contactRepository.delete(c);
				logger.info("Contact details deleted");
				return true;
				}
		}
		return false;
	}
	
	/*
	 * Find user by mailID
	 */
	public UserEntity findByMailId(String mailId) {
		return contactRepository.findByMailID(mailId);
	}
	
	public Contact findByContactNumber(String contactNumber) {
	
	Contact contact = contactRepository.findByContactNumber(contactNumber);
	if (contact == null) {
		logger.error(contactNumber+ " is not available");
		return null;
		//throw new ContactException(contactNumber + " not available");
	}else {
	return contact;}
}
public UserEntity addContact(String userName, Contact contact) {
		
		UserEntity user = repo.findByUserName(userName);
        if(user !=null) {
                Contact con = new Contact();
                for(Contact d : contactRepository.findAll()) {
                    if(user.getUserId() == d.getUserId()) {
                        if(d.getContactName().equalsIgnoreCase(contact.getContactName()))
                        {
                            logger.error("Name already exist");
                            throw new ContactException("Name already exist");
                        }
                        if(d.getContactNumber().equals(contact.getContactNumber())) {
                            logger.error("Number already exist");
                            throw new ContactException("Number already exist");
                        }
                    }
                }
                con.setContactName(contact.getContactName());
                con.setContactNumber(contact.getContactNumber());
                con.setEmail(contact.getEmail());
                con.setUserId(user.getUserId());
                user.getContactList().add(con);
                logger.info("Contact details added");
                return repo.save(user);
        }
        return null;
}


}

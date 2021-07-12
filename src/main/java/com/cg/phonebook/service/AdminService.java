package com.cg.phonebook.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.UserEntity;
import com.cg.phonebook.repository.AdminRepository;
import com.cg.phonebook.repository.PhonebookUsersRepository;

@Service
public class AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	PhonebookUsersRepository userRepo;

	/*
	 * Saving Admin details in repository
	 */
	public AdminEntity saveAdmin(AdminEntity data) {
		return adminRepo.save(data);
	}

	/*
	 * Fetching all users in the repository
	 */
	public Iterable<UserEntity> findAllUsers() {
		return userRepo.findAll();
	}



	/*
	 * Method to search the user by his ID
	 */
	/*
	 * public UserEntity findUserByUserName(String userName) { UserEntity user =
	 * userRepo.findByUserName(userName.toUpperCase()); if (user == null) {
	 * logger.error("User Name "+userName+" not available"); throw new
	 * PhonebookUsersException("User Name " + userName + " not available");
	 * 
	 * } logger.info(userName+" is found"); return user;
	 * 
	 * }
	 */
	/*
	 * public boolean removeUserByPhoneNumber(String phoneNumber) { boolean
	 * flag=false; for(UserEntity c:userRepo.findAll()) {
	 * if((c.getPhoneNumber()).equalsIgnoreCase(phoneNumber)){
	 * logger.info(phoneNumber+" is deleted"); userRepo.delete(c); flag=true; } }
	 * if(flag == false) { logger.error(phoneNumber+" is not available"); } return
	 * flag; }
	 */

	/*
	 * Delete the user by his ID
	 */
	public boolean deleteUserByUserName(String userName) {
		boolean flag = false;
		for (UserEntity c : userRepo.findAll()) {
			if ((c.getUserName()).equalsIgnoreCase(userName)) {
				logger.info(userName + " is deleted");
				userRepo.delete(c);
				flag = true;
			}
		}
		if (flag == false) {
			logger.error(userName + " is not available");
		}
		return flag;
	}

	public UserEntity searchByPhoneNumber(String phoneNumber) {
		UserEntity contact = null;
		for (UserEntity contacts : userRepo.findAll()) {
			if (contacts.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
				logger.info("Details found with phone number : " + phoneNumber);
				contact = contacts;
			}
		}
		if (contact == null) {
			logger.error("Details not found");
		}
		return contact;
	}

	public UserEntity findByUserName(String userName) {
		UserEntity contact = null;
		for (UserEntity contacts : userRepo.findAll()) {
			if (contacts.getUserName().equalsIgnoreCase(userName)) {
				logger.info("Details found with user name : " + userName);
				contact = contacts;
			}
		}
		if (contact == null) {
			logger.error("Details not found");
		}
		return contact;

	}

	/*
	 * Find the admin by mail ID
	 */

	public AdminEntity findByMailId(String mailId) {
		return adminRepo.findByMailID(mailId);
	}

	public Optional<AdminEntity> getAdminById(Long adminId) {

		return adminRepo.findById(adminId);
	}

}

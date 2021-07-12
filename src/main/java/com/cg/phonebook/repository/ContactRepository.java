package com.cg.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.phonebook.entity.AdminEntity;
import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;

@Repository 
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
    
	@Query(value="select t from Contact t where t.contactNumber=:contactNumber")
	public Contact findByContactNumber(@Param("contactNumber")String contactNumber);
	
	@Query(value="select t from UserEntity t where t.email=:email")
	UserEntity findByMailID(@Param("email") String email);
	
	@Query(value="select t from UserEntity t where t.email=:email and t.password=:password")
	UserEntity loginUser(String email,String password);

	public Contact findByContactName(String contactName);
	
	//Contact findByName(String contactName);
}
  
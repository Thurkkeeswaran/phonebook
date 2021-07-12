package com.cg.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.phonebook.entity.Contact;
import com.cg.phonebook.entity.UserEntity;

@Repository
public interface PhonebookUsersRepository extends JpaRepository<UserEntity, Long>{
	
	UserEntity findByUserName(String userName);

	Contact save(Contact contact);
	
	@Query(value="select t from UserEntity t where t.email=:email")
	UserEntity findByMailID(@Param("email") String email);
}

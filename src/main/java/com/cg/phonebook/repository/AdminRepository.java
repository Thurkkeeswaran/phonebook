package com.cg.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.phonebook.entity.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
	@Query(value="select t from AdminEntity t where t.email=:email")
	AdminEntity findByMailID(@Param("email") String email);
	
	@Query(value="select t from AdminEntity t where t.adminId=:adminId")
	AdminEntity findByadminID(@Param("adminId") String email);
	@Query("SELECT a FROM AdminEntity a where a.email=:email and a.password=:password")
	public AdminEntity loginAdmin(String email, String password);
}

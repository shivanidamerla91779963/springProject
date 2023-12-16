//UserDao is like UserJpaRepository
package com.inn.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.inn.cafe.model.User;
import com.inn.cafe.wrapper.UserWrapper;

import jakarta.transaction.Transactional;

public interface UserDao extends JpaRepository<User,Integer> {

	User findByEmailId(@Param("email") String email);
	List<UserWrapper> getAllUser();
	
	List<String> getAllAdmin();
	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status") String status,@Param("id") Integer id);
	
	//jpa automatically generate methods for fields in User like id,name,email....
	User findByEmail(String email);
}



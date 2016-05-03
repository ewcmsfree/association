package com.ewcms.security.user.service;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.BaseIT;
import com.ewcms.security.user.entity.User;

/**
 * 
 * @author wu_zhijun
 *
 */
public abstract class BaseUserIT extends BaseIT{

	@Autowired
	protected UserService userService;
	@Autowired
	protected PasswordService passwordService;
	
	protected int maxRetryCount = 10;
	
	protected String username = "wu_zhijun_1980";
	protected String email = "wu_zhijun_abc@msn.com";
	protected String mobilePhoneNumber = "18912345678";
	protected String password = "123456";
	
	@Before
	public void setUp(){
		userService.setPasswordService(passwordService);
		passwordService.setMaxRetryCount(maxRetryCount);

		User user = userService.findByUsername(username);
		if (user != null){
			userService.delete(user);
			delete(user);
		}
		user = userService.findByEmail(email);
		if (user != null){
			userService.delete(user);
			delete(user);
		}
		user = userService.findByMobilePhoneNumber(mobilePhoneNumber);
		if (user != null){
			userService.delete(user);
			delete(user);
		}
		clear();
	}
	
	@After
	public void tearDown(){
		passwordService.clearLoginRecordCache(username);
		passwordService.clearLoginRecordCache(email);
		passwordService.clearLoginRecordCache(mobilePhoneNumber);
	}
	
	protected User createUser(String username, String email, String mobilePhoneNumber, String password){
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setMobilePhoneNumber(mobilePhoneNumber);
		user.setPassword(password);
		userService.saveAndFlush(user);
		return user;
	}
	
	protected User createDefaultUser(){
		return createUser(username, email, mobilePhoneNumber, password);
	}
}

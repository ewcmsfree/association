package com.ewcms.security.user.service;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.exception.UserBlockedException;
import com.ewcms.security.user.exception.UserNotExistsException;
import com.ewcms.security.user.exception.UserPasswordNotMatchException;
import com.ewcms.security.user.exception.UserPasswordRetryLimitExceedException;

/**
 * 
 * @author wu_zhijun
 *
 */
public class UserServiceIT extends BaseUserIT {
	
	@Test
	public void testLoginSuccessWithUsername(){
		User user = createDefaultUser();
		Assert.assertNotNull(userService.login(user.getUsername(), password));
	}
	
	@Test
	public void testLoginSuccessWithEmail(){
		User user = createDefaultUser();
		Assert.assertNotNull(userService.login(user.getEmail(), password));
	}
	
	@Test
	public void testLoginSuccessWithMobilePhoneNumber(){
		User user = createDefaultUser();
		Assert.assertNotNull(userService.login(user.getMobilePhoneNumber(), password));
	}
	
	@Test(expected = UserNotExistsException.class)
	public void testLoginFailWithUserNotExists(){
		User user = createDefaultUser();
		userService.login(user.getUsername() + "x", password);
	}
	
	@Test(expected = UserNotExistsException.class)
	public void testLoginFailWithUserDeleted(){
		User user = createDefaultUser();
		userService.delete(user);
		clear();
		userService.login(username, password);
	}
	
	@Test(expected = UserPasswordNotMatchException.class)
	public void testLoginFailWithUserPasswordNotMatch(){
		User user = createDefaultUser();
		userService.login(user.getUsername(), password + "x");
	}
	
	@Test(expected = UserBlockedException.class)
	public void testLoginFailWithStatusBlocked(){
		User user = createDefaultUser();
		userService.changeStatus(user, user, UserStatus.blocked, "sql");
		userService.login(username, password);
	}
	
	@Test(expected = UserPasswordRetryLimitExceedException.class)
	public void testLoginFailWithRetryLimitExceed(){
		User user = createDefaultUser();
		for (int i = 0; i < maxRetryCount; i++){
			try{
				userService.login(user.getUsername(), password + "x");
			} catch (UserPasswordNotMatchException e){}
		}
		userService.login(user.getUsername(), password+ "x");
		passwordService.clearLoginRecordCache(user.getUsername());
	}
}

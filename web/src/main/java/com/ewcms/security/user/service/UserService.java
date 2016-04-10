package com.ewcms.security.user.service;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.PatternUtils;
import com.ewcms.personnel.archive.service.ArchiveService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.exception.UserBlockedException;
import com.ewcms.security.user.exception.UserNotExistsException;
import com.ewcms.security.user.exception.UserPasswordNotMatchException;
import com.ewcms.security.user.repository.UserRepository;
import com.ewcms.security.user.util.UserLogUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
@Service
public class UserService extends BaseService<User, Long> {

    private UserRepository getUserRepository() {
        return (UserRepository) baseRepository;
    }

    @Autowired
    private UserStatusHistoryService userStatusHistoryService;

    @Autowired
    private PasswordService passwordService;
    
    @Autowired
    private ArchiveService archiveService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public User save(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));

        return super.save(user);
    }

    @Override
    public User update(User user) {
    	User dbUser = findOne(user.getId());
    	user.setOrganizationJobs(dbUser.getOrganizationJobs());
    	
    	return super.update(user);
    }
    
    public User findByUsername(String username) {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        return getUserRepository().findByUsername(username);
    }

    public User findByEmail(String email) {
        if(StringUtils.isEmpty(email)) {
            return null;
        }
        return getUserRepository().findByEmail(email);
    }

    public User findByMobilePhoneNumber(String mobilePhoneNumber) {
        if(StringUtils.isEmpty(mobilePhoneNumber)) {
            return null;
        }
        return getUserRepository().findByMobilePhoneNumber(mobilePhoneNumber);
    }

    public User changePassword(User user, String newPassword) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        update(user);
        return user;
    }

    public User changeStatus(User opUser, User user, UserStatus newStatus, String reason) {
        user.setStatus(newStatus);
        update(user);
        userStatusHistoryService.log(opUser, user, newStatus, reason);
        return user;
    }

    public User login(String username, String password) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "username is empty");
            throw new UserNotExistsException();
        }
        //密码如果不在指定范围内 肯定错误
        if (password.length() < PatternUtils.PASSWORD_MIN_LENGTH || password.length() > PatternUtils.PASSWORD_MAX_LENGTH) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "password length error! password is between {} and {}",
                    PatternUtils.PASSWORD_MIN_LENGTH, PatternUtils.PASSWORD_MAX_LENGTH);

            throw new UserPasswordNotMatchException();
        }

        User user = null;

        //此处需要走代理对象，目的是能走缓存切面
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        if (maybeUsername(username)) {
            user = proxyUserService.findByUsername(username);
        }

        if (user == null && maybeEmail(username)) {
            user = proxyUserService.findByEmail(username);
        }

        if (user == null && maybeMobilePhoneNumber(username)) {
            user = proxyUserService.findByMobilePhoneNumber(username);
        }

        if (user == null || Boolean.TRUE.equals(user.getDeleted())) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "user is not exists!");

            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if (user.getStatus() == UserStatus.blocked) {
            UserLogUtils.log(
                    username,
                    "loginError",
                    "user is blocked!");
            throw new UserBlockedException(userStatusHistoryService.getLastReason(user));
        }

        UserLogUtils.log(
                username,
                "loginSuccess",
                "");
        return user;
    }


    private boolean maybeUsername(String username) {
        if (!username.matches(PatternUtils.USERNAME_PATTERN)) {
            return false;
        }
        //如果用户名不在指定范围内也是错误的
        if (username.length() < PatternUtils.USERNAME_MIN_LENGTH || username.length() > PatternUtils.USERNAME_MAX_LENGTH) {
            return false;
        }

        return true;
    }

    private boolean maybeEmail(String username) {
        if (!username.matches(PatternUtils.EMAIL_PATTERN)) {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username) {
        if (!username.matches(PatternUtils.MOBILE_PHONE_NUMBER_PATTERN)) {
            return false;
        }
        return true;
    }

    public void changePassword(User opUser, List<Long> ids, String newPassword) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changePassword(user, newPassword);
            UserLogUtils.log(
                    user.getUsername(),
                    "changePassword",
                    "admin user {} change password!", opUser.getUsername());
        }
    }

    public void changeStatus(User opUser, List<Long> ids, UserStatus newStatus, String reason) {
        UserService proxyUserService = (UserService) AopContext.currentProxy();
        for (Long id : ids) {
            User user = findOne(id);
            proxyUserService.changeStatus(opUser, user, newStatus, reason);
            UserLogUtils.log(
                    user.getUsername(),
                    "changeStatus",
                    "admin user {} change status!", opUser.getUsername());
        }
    }

    public Set<Map<String, Object>> findIdAndNames(Searchable searchable, String usernme) {

        searchable.addSearchFilter("username", SearchOperator.LIKE, usernme);
        searchable.addSearchFilter("deleted", SearchOperator.EQ, false);

        return Sets.newHashSet(
                Lists.transform(
                        findAll(searchable).getContent(),
                        new Function<User, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> apply(User input) {
                                Map<String, Object> data = Maps.newHashMap();
                                data.put("label", input.getUsername());
                                data.put("value", input.getId());
                                return data;
                            }
                        }
                )
        );
    }
    
    public Set<User> findUserDisplay(Set<Long> userIds){
    	if (EmptyUtil.isCollectionEmpty(userIds)){
    		return Sets.newHashSet();
    	} else {
    		return getUserRepository().findUserDisplay(userIds);
    	}
    }
}

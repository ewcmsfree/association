package com.ewcms.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.LogicDeleteable;
import com.ewcms.common.utils.EmptyUtil;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * 用户
 * 
 * @author wu_zhijun
 */

public class User extends BaseSequenceEntity<Long> implements LogicDeleteable {
	
	private static final long serialVersionUID = -6104610983204668263L;
	
    private String username;
    private String email;
    private String mobilePhoneNumber;
    private String password;
    private String salt;
    private Date createDate;
    private UserStatus status = UserStatus.normal;
    private Boolean admin = false;
    private Boolean deleted = Boolean.FALSE;
	private Boolean isRegister = true; 
    private String realname;
    
    public User() {
    }

    public User(Long id) {
        setId(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public String getStatusInfo(){
    	return status == null ? "" : status.getInfo();
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

	public String getRealname() {
		return realname;
	}

	public String getUsernameAndRealname(){
		return this.username + (EmptyUtil.isStringNotEmpty(getRealname()) ? "(" + getRealname() + ")" : ""); 
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}
}
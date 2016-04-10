package com.ewcms.system.externalds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * JDBC数据源
 * 
 * <ul>
 * <li>driver:驱动名</li>
 * <li>connUrl:数据库连接URL</li>
 * <li>userName:用户名</li>
 * <li>passWord:密码</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_ds_jdbc")
@PrimaryKeyJoinColumn(name = "ds_id")
public class JdbcDs extends BaseDs {

    private static final long serialVersionUID = 7009275702505946488L;
    
    @Column(name = "driver", nullable = false)
    private String driver;
    @Column(name = "connurl", nullable = false)
    private String connUrl;
    @Column(name = "username", nullable = false)
    private String userName;
    @Column(name = "password")
    private String passWord;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getConnUrl() {
        return connUrl;
    }

    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

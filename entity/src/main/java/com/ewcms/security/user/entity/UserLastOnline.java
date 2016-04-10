package com.ewcms.security.user.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

import javax.persistence.*;

import java.util.Date;

/**
 * 在线用户最后一次在线信息()
 * 此表对于分析活跃用户有用
 *
 * <ul>
 * <li>userId:用户编号</li>
 * <li>username:用户名</li>
 * <li>uid:最后退出时的UID</li>
 * <li>host:用户主机地址</li>
 * <li>userAgent:用户浏览器类型</li>
 * <li>systemHost:用户登录系统IP</li>
 * <li>lastLoginTimestamp:最后登录时间</li>
 * <li>lastStopTimestamp:最后退出时间</li>
 * <li>loginCount:登录次数</li>
 * <li>totalOnlineTime:总在线时长(以秒为单位)</li>
 * </ul>
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_user_last_online",
		uniqueConstraints = {
			@UniqueConstraint(name = "unique_sec_user_last_online_user_id", columnNames = "user_id")
		},
		indexes = {
			@Index(name = "idx_sec_user_last_online_user_id", columnList = "user_id"),
			@Index(name = "idx_sec_user_last_online_host", columnList = "host"),
			@Index(name = "idx_sec_user_last_online_system_host", columnList = "system_host"),
			@Index(name = "idx_sec_user_last_online_last_login_timestamp", columnList = "last_login_timestamp"),
			@Index(name = "idx_sec_user_last_online_last_stop_timestamp", columnList = "last_stop_timestamp"),
			@Index(name = "idx_sec_user_last_online_user_agent", columnList = "user_agent")
		}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_user_last_online_id", allocationSize = 1)
public class UserLastOnline extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 2563371548507277997L;

    @Column(name = "user_id")
    private Long userId;
	@Formula(value = "(select s_o.username || case when s_p.name is not null then ('(' || s_p.name || ')') else '' end from sec_user s_o left join pel_archive s_p on s_o.id = s_p.user_id where s_o.id=user_id)")
	private String userName;
    @Column(name = "uid")
    private String uid;
    @Column(name = "host")
    private String host;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "system_host")
    private String systemHost;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTimestamp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_stop_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStopTimestamp;
    @Column(name = "login_count")
    private Integer loginCount = 0;
    @Column(name = "total_online_time")
    private Long totalOnlineTime = 0L;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(Date lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getLastStopTimestamp() {
        return lastStopTimestamp;
    }

    public void setLastStopTimestamp(Date lastStopTimestamp) {
        this.lastStopTimestamp = lastStopTimestamp;
    }

    public void incLoginCount() {
        setLoginCount(getLoginCount() + 1);
    }

    public void incTotalOnlineTime() {
        long onlineTime = getLastStopTimestamp().getTime() - getLastLoginTimestamp().getTime();
        setTotalOnlineTime(getTotalOnlineTime() + onlineTime / 1000);
    }


    public static final UserLastOnline fromUserOnline(UserOnline online) {
        UserLastOnline lastOnline = new UserLastOnline();
        lastOnline.setHost(online.getHost());
        lastOnline.setUserId(online.getUserId());
//        lastOnline.setUsername(online.getUsername());
        lastOnline.setUserAgent(online.getUserAgent());
        lastOnline.setSystemHost(online.getSystemHost());
        lastOnline.setUid(String.valueOf(online.getId()));
        lastOnline.setLastLoginTimestamp(online.getStartTimestamp());
        lastOnline.setLastStopTimestamp(online.getLastAccessTime());
        return lastOnline;
    }

    public static final void merge(UserLastOnline from, UserLastOnline to) {
        to.setHost(from.getHost());
        to.setUserId(from.getUserId());
//        to.setUsername(from.getUserName());
        to.setUserAgent(from.getUserAgent());
        to.setSystemHost(from.getSystemHost());
        to.setUid(String.valueOf(from.getUid()));
        to.setLastLoginTimestamp(from.getLastLoginTimestamp());
        to.setLastStopTimestamp(from.getLastStopTimestamp());
    }
}
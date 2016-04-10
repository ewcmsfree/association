package com.ewcms.security.user.entity;

import org.apache.shiro.session.mgt.OnlineSession;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.AbstractEntity;

import javax.persistence.*;

import java.util.Date;

/**
 * 当前在线会话
 * 
 * <ul>
 * <li>id:用户会话id(UID)</li>
 * <li>userId:用户编号</li>
 * <li>username:用户名</li>
 * <li>host:用户主机地址</li>
 * <li>systemHost:用户登录时系统IP</li>
 * <li>userAgent:用户浏览器类型</li>
 * <li>status:在线状态</li>
 * <li>startTimestamp:session创建时间</li>
 * <li>lastAccessTime:session最后访问时间</li>
 * <li>timeout:超时时间</li>
 * <li>session:备份的当前用户会话</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_user_online",
		indexes = {
			@Index(name = "idx_sec_user_online_user_id", columnList = "user_id"),
			@Index(name = "idx_sec_user_online_host", columnList = "host"),
			@Index(name = "idx_sec_user_online_system_host", columnList = "system_host"),
			@Index(name = "idx_sec_user_online_start_timestamp", columnList = "start_timestamp"),
			@Index(name = "idx_sec_user_online_last_access_time", columnList = "last_access_time"),
			@Index(name = "idx_sec_user_online_user_agent", columnList = "user_agent")
		}
)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOnline extends AbstractEntity<String> {

	private static final long serialVersionUID = 8115765202368326404L;

    @Id
    @GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
    private String id;
    @Column(name = "user_id")
    private Long userId = 0L;
	@Formula(value = "(select s_o.username || case when s_p.name is not null then ('(' || s_p.name || ')') else '' end from sec_user s_o left join pel_archive s_p on s_o.id = s_p.user_id where s_o.id=user_id)")
	private String userName;
    @Column(name = "host")
    private String host;
    @Column(name = "system_host")
    private String systemHost;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OnlineSession.OnlineStatus status = OnlineSession.OnlineStatus.on_line;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTimestamp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_access_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column(name = "timeout")
    private Long timeout;
    @Column(name = "session", columnDefinition = "text")
    @Type(type = "com.ewcms.common.repository.hibernate.type.ObjectSerializeUserType")
    private OnlineSession session;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }


    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public OnlineSession.OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineSession.OnlineStatus status) {
        this.status = status;
    }
    
    public String getStatusInfo(){
    	return status == null ? "" : status.getInfo();
    }

    public OnlineSession getSession() {
        return session;
    }

    public void setSession(OnlineSession session) {
        this.session = session;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public static final UserOnline fromOnlineSession(OnlineSession session) {
        UserOnline online = new UserOnline();
        online.setId(String.valueOf(session.getId()));
        online.setUserId(session.getUserId());
//        online.setUsername(session.getUsername());
        online.setStartTimestamp(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setTimeout(session.getTimeout());
        online.setHost(session.getHost());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);

        return online;
    }
}
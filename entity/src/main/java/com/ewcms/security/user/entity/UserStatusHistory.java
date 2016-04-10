package com.ewcms.security.user.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.utils.EmptyUtil;

import javax.persistence.*;

import java.util.Date;

/**
 * 用户状态改变历史记录
 * 
 * <ul>
 * <li>user:用户对象</li>
 * <li>reason:状态改变原因</li>
 * <li>status:操作时的状态</li>
 * <li>opUser:操作员对象</li>
 * <li>opDate:操作时间</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_user_status_history",
		indexes = {
			@Index(name = "idx_sec_user_block_history_user_id_block_date", columnList = "user_id, op_date"),
			@Index(name = "idx_sec_user_block_history_op_user_id_op_date", columnList = "op_user_id, op_date")
		}
)
@SequenceGenerator(name="seq", sequenceName="seq_sec_user_status_history_id", allocationSize = 1)
public class UserStatusHistory extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 4214332634043833217L;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private User user;
    @Column(name = "reason")
    private String reason;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "op_user_id")
    private User opUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "op_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opDate;

    @JSONField(serialize = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName(){
    	return this.user.getUsername() + (EmptyUtil.isStringNotEmpty(this.user.getRealname()) ? "(" + this.user.getRealname() + ")" : ""); 
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @JSONField(serialize = false)
    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }
    
    public String getOpUserName(){
    	return this.opUser.getUsername() + (EmptyUtil.isStringNotEmpty(this.opUser.getRealname()) ? "(" + this.opUser.getRealname() + ")" : ""); 
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}

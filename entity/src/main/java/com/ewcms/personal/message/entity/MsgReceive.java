package com.ewcms.personal.message.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 
 * 信息接收
 * 
 * <ul>
 * <li>userName:用户</li>
 * <li>read:读取标志</li>
 * <li>status:状态</li>
 * <li>readTime:读取时间</li>
 * <li>msgContent:消息内容对象</li>
 * <li>sendUserName:发送者</li>
 * <li>subscription:订阅标志</li>
 * </ul>
 * 
 * @author wu_zhijun
 * 
 */
@Entity
@Table(name = "per_message_receive")
@SequenceGenerator(name = "seq", sequenceName = "seq_per_message_receive_id", allocationSize = 1)
public class MsgReceive extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -8925782877706243408L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Column(name = "read")
	private Boolean read;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MsgStatus status;
	@Column(name = "readtime")
	private Date readTime;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = MsgContent.class)
	@JoinColumn(name="receive_content_id")
	private MsgContent msgContent = new MsgContent();
	@Column(name = "send_user_id")
	private Long sendUserId;
	@Column(name = "subscription")
	private Boolean subscription;

	public MsgReceive(){
		read = false;
		subscription = false;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public MsgStatus getStatus() {
		return status;
	}

	public void setStatus(MsgStatus status) {
		this.status = status;
	}
	
	public String getStatusInfo(){
		return status.getInfo();
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public MsgContent getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public Boolean getSubscription() {
		return subscription;
	}

	public void setSubscription(Boolean subscription) {
		this.subscription = subscription;
	}
}

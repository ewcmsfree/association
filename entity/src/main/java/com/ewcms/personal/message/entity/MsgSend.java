package com.ewcms.personal.message.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.repository.hibernate.type.CollectionToStringUserType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 消息发送
 * 
 * <ul>
 * <li>userName:用户</li>
 * <li>title:标题</li>
 * <li>sendTime:发送时间</li>
 * <li>type:类型</li>
 * <li>status:状态</li>
 * <li>msgContents:内容对象集合</li>
 * <li>receiveUserNames:接收者</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@TypeDef(name = "SetToStringUserType", typeClass = CollectionToStringUserType.class, parameters = {
	@Parameter(name = "separator", value = ","),
	@Parameter(name = "collectionType", value = "java.util.HashSet"),
	@Parameter(name = "elementType", value = "java.lang.Long") })
@Entity
@Table(name = "per_message_send")
@SequenceGenerator(name = "seq", sequenceName = "seq_per_message_send_id", allocationSize = 1)
public class MsgSend extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 8470154441934582608L;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Transient
	private String userName;
	@Column(name = "title", length = 200)
	private String title;
	@Column(name = "sendtime")
	private Date sendTime;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private MsgType type;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private MsgStatus status;
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY, targetEntity = MsgContent.class)
	@JoinColumn(name = "send_id")
	@OrderBy(value = "id Desc")
	private List<MsgContent> msgContents = Lists.newArrayList();
	@Type(type = "SetToStringUserType")
	@Column(name = "user_ids")
	private Set<Long> userIds = Sets.newHashSet();
	@Transient
	private String userNames;

	public MsgSend(){
		sendTime = new Date(Calendar.getInstance().getTime().getTime());
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}
	
	public String getTypeInfo(){
		return type.getInfo();
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
	
	public List<MsgContent> getMsgContents() {
		return msgContents;
	}

	public void setMsgContents(List<MsgContent> msgContents) {
		this.msgContents = msgContents;
	}

	@JSONField(serialize = false)
	public Set<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<Long> userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
}

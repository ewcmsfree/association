package com.ewcms.personal.memoranda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 备忘录
 * 
 * <ul>
 * <li>title:标题</li>
 * <li>content:内容</li>
 * <li>noteDate:日期</li>
 * <li>userName:用户名</li>
 * <li>warn:是否提醒</li>
 * <li>warnTime:提醒时间</li>
 * <li>frequency:提醒频率</li>
 * <li>before:提前时间</li>
 * <li>fireTime:触发时间</li>
 * <li>missRemind:错过是否提醒(true:是,false:否)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "per_calendar")
@SequenceGenerator(name = "seq", sequenceName = "seq_per_calendar_id", allocationSize = 1)
public class Memoranda extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = -6072138684705920059L;
	
	/**
	 * 提前时间段枚举
	 * @author wuzhijun
	 */
	public enum BeforeStatus {
		NONE("正点"), 
		ONE("1分钟"), 
		FIVE("5分钟"), 
		TEN("10分钟"), 
		FIFTEEN("15分钟"), 
		TWENTY("20分钟"), 
		TWENTYFIVE("25分钟"), 
		THIRTY("30分钟"), 
		FORTYFIVE("45分钟"), 
		ONEHOUR("1小时"), 
		TWOHOUR("2小时"), 
		THREEHOUR("3小时"), 
		TWELVEHOUR("12小时"), 
		TWENTYFOUR("24小时"), 
		TWODAY("2天"), 
		ONEWEEK("1周");

		private String info;

		private BeforeStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}

	}

	/**
	 * 提醒频率枚举
	 * @author wuzhijun
	 */
	public enum FrequencyStatus {
		SINGLE("单次"), 
		EVERYDAY("每天"), 
		EVERYWEEK("每周"), 
		EVERYMONTHWEEK("每月(星期)"), 
		EVERYMONTH("每月(日)"), 
		EVERYYEAR("每年");

		private String info;

		private FrequencyStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}
	
	@Column(name = "title", nullable = false, length = 50)
	private String title;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notedate", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date noteDate;
	@Column(name = "user_id", nullable = false)
	private Long userId;
//	@Column(name = "username", nullable = false)
//	private String userName;
	@Column(name = "warn", nullable = false)
	private Boolean warn;
	@Temporal(TemporalType.TIME)
	@Column(name = "warntime")
	@DateTimeFormat(pattern = "HH:mm")
	private Date warnTime;
	@Column(name = "frequency")
	@Enumerated(EnumType.STRING)
	private FrequencyStatus frequency;
	@Column(name = "remind")
	@Enumerated(EnumType.STRING)
	private BeforeStatus before;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "firetime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fireTime;
	@Column(name = "missremind")
	private Boolean missRemind;

	public Memoranda(){
		warn = false;
		missRemind = false;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSONField(format = "yyyy-MM-dd")
	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public Boolean getWarn() {
		return warn;
	}

	public void setWarn(Boolean warn) {
		this.warn = warn;
	}

	@JSONField(format = "HH:mm")
	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	public FrequencyStatus getFrequency() {
		return frequency;
	}

	public void setFrequency(FrequencyStatus frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyInfo(){
		return (frequency != null) ? frequency.getInfo() : FrequencyStatus.SINGLE.getInfo();
	}
	
	public BeforeStatus getBefore() {
		return before;
	}

	public void setBefore(BeforeStatus before) {
		this.before = before;
	}

	public String getBeforeDescription(){
		return (before != null) ? before.getInfo() : BeforeStatus.NONE.getInfo();
	}
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getFireTime() {
		return fireTime;
	}

	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}

	public Boolean getMissRemind() {
		return missRemind;
	}

	public void setMissRemind(Boolean missRemind) {
		this.missRemind = missRemind;
	}
}

package com.ewcms.system.report.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.system.externalds.entity.BaseDs;
import com.google.common.collect.Sets;

/**
 * 报表对象
 * 
 * <ul>
 * <li>id:报表ID</li>
 * <li>name:报表名</li>
 * <li>type:报表类型</li>
 * <li>textEntity:报表实体</li>
 * <li>createDate:创建时间</li>
 * <li>updateDate:更新时间</li>
 * <li>hidden:隐藏</li>
 * <li>remarks:备注</li>
 * <li>parameters:参数列表（与Parameters对象一对多关联）</li>
 * <li>alqcDataSource:连接数据源对象（与AlqcDataSource对象一对一关联）</li>
 * <li>Category:分类列表(与Category对象多对多关联)</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_text")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_report_text_id", allocationSize = 1)
public class TextReport extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = 2289611908936617074L;
    
    /**
     * 文字报表类型枚举
     * @author wuzhijun
     */
    public enum Type {

        HTML("HTML"), PDF("PDF"), XLS("XLS"), RTF("RTF"), XML("XML");
        
        private String description;

        private Type(String description) {
            this.description = description;
        }

        /**
         * 描述状态
         *
         * @return
         */
        public String getDescription() {
            return this.description;
        }
    }
    
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
    @Column(name = "textentity")
    private byte[] textEntity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdate", columnDefinition = "Timestamp default CURRENT_DATE", insertable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    @Column(name = "hidden", nullable = false)
    private Boolean hidden = false;
    @Column(name = "remarks",columnDefinition = "text")
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Parameter.class)
    @JoinColumn(name="text_id")
    @OrderBy("id")
    private Set<Parameter> parameters = Sets.newLinkedHashSet();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = BaseDs.class)
    @JoinColumn(name = "base_ds_id")
    private BaseDs baseDs = new BaseDs();
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(serialize = false)
    public byte[] getTextEntity() {
        return textEntity;
    }

    public void setTextEntity(byte[] textEntity) {
        this.textEntity = textEntity;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
		return createDate;
	}

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JSONField(serialize = false)
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    @JSONField(serialize = false)
    public BaseDs getBaseDs() {
        return baseDs;
    }

    public void setBaseDs(BaseDs baseDs) {
        this.baseDs = baseDs;
    }
}

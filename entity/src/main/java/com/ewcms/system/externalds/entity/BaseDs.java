package com.ewcms.system.externalds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 数据源父类
 * 
 * <ul>
 * <li>name:名称</li>
 * <li>timeZone:时区偏移量</li>
 * <li>remarks:备注</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_ds_base")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_ds_base_id", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseDs extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = 3846532575959285040L;
    
    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;
    @Column(name = "timezone")
    private String timeZone = "Asia/Shanghai";
    @Column(name = "remarks",columnDefinition = "text")
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

package com.ewcms.system.report.entity.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 组件视图父类
 * 
 * <ul>
 * <li>id:组件视图编号</li>
 * <li>isMandatory:是否强制</li>
 * <li>isReadOnly:是否是读</li>
 * <li>isVisible:是否可见</li>
 * <ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_view")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq", sequenceName = "seq_plugin_report_view_id", allocationSize = 1)
public class ComponentView extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = 6470965538592594049L;
    
    @Column(name = "mandatory")
    private Boolean isMandatory;
    @Column(name = "readonly")
    private Boolean isReadOnly;
    @Column(name = "visible")
    private Boolean isVisible;

    public ComponentView(){
    	isMandatory = false;
    	isReadOnly = false;
    	isVisible = true;
    }
    
    public Boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(Boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public Boolean isVisible() {
        return isVisible;
    }

    public void setVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
}

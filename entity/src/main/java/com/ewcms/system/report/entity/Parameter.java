package com.ewcms.system.report.entity;

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

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.system.report.entity.data.Data;
import com.ewcms.system.report.entity.view.ComponentView;

/**
 * 报表参数
 * 
 * <ul>
 * <li>enName:参数名</li>
 * <li>className:参数类型</li>
 * <li>description:参数描述</li>
 * <li>chName:中文名</li>
 * <li>defaultValue:默认值</li>
 * <li>value:设置值</li>
 * <li>componentView:控件视图</li>
 * <li>data:数据</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_parameter")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_report_parameter_id", allocationSize = 1)
public class Parameter extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = 2283573904816876354L;
    
    /**
     * 参数类型
     * @author wuzhijun
     */
    public enum Type{
        BOOLEAN("布尔型"), TEXT("文本型"), LIST("列表型"), CHECK("选择型"), DATE("日期型"), SESSION("登录用户型"), SQL("SQL语句");
        
        private String description;

        private Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
    
    @Column(name = "enname", nullable = false)
    private String enName;
    @Column(name = "classname", nullable = false)
    private String className;
    @Column(name = "description",columnDefinition = "text")
    private String description;
    @Column(name = "cnname")
    private String cnName;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "defaultvalue")
    private String defaultValue;
    @Column(name = "value",columnDefinition = "text")
    private String value;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ComponentView.class)
    @JoinColumn(name = "componentview_id")
    private ComponentView componentView = new ComponentView();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Data.class)
    @JoinColumn(name = "data_id")
    private Data data = new Data();

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCnName() {
        return cnName == null ? "" : cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public Type getType() {
        return type;
    }

	public String getTypeDescription(){
		if (type != null){
			return type.getDescription();
		}else{
			return Type.TEXT.getDescription();
		}
	}
	
    public void setType(Type type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ComponentView getComponentView() {
        return componentView;
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setComponentView(ComponentView componentView) {
        this.componentView = componentView;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

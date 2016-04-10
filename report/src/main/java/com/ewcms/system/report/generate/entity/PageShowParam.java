package com.ewcms.system.report.generate.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ewcms.system.report.entity.Parameter.Type;

/**
 * 参数对象（由页面调用）
 * 
 * <ul>
 * <li>id:编号
 * <li>enName:英文名称
 * <li>cnName:中文名称
 * <li>defaultValue:默认值
 * <li>type:参数类型
 * <li>value:值
 * </ul>
 * 
 * @author 吴智俊
 */
public class PageShowParam implements Serializable {

    private static final long serialVersionUID = -5508964970148147799L;
    private Long id = null;
    private String enName = null;
    private String cnName = null;
    private String defaultValue = null;
    private Type type = Type.TEXT;
    private Map<String, String> paramMap = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> value) {
        this.paramMap = value;
    }

    public void setParamMapList(List<String> valueList) {
        String valueString = "";
        for (String str : valueList) {
            valueString += "'" + str + "',";
        }
        setDefaultValue(valueString.substring(0, valueString.length() - 1));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enName == null) ? 0 : enName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PageShowParam other = (PageShowParam) obj;
        if (enName == null) {
            if (other.enName != null) {
                return false;
            }
        } else if (!enName.equals(other.enName)) {
            return false;
        }
        return true;
    }
}

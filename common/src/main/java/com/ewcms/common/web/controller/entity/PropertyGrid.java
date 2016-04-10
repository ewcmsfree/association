package com.ewcms.common.web.controller.entity;

import java.io.Serializable;
import java.util.Map;

public class PropertyGrid implements Serializable{

	private static final long serialVersionUID = -7573244306517205481L;
	
	private Long id;
	private Object name;
    private Object value;
    private String group;
    private String description;
    private String className;
    private Map<String,Object> editor;
    
    public PropertyGrid(Object name, Object value, String group){
    	this.name = name;
    	this.value = value;
    	this.group = group;
    }
    
    public PropertyGrid(Long id, Object name, Object value, String group, String description, String className, Map<String, Object> editor){
    	this.id = id;
        this.name = name;
        this.value = value;
        this.group = group;
        this.description = description;
        this.className = className;
        this.editor = editor;
    }
    
    public Long getId(){
    	return id;
    }
    
    public Object getName() {
        return name;
    }
    
    public Object getValue() {
        return value;
    }
    
    public String getGroup() {
        return group;
    }
    
    public String getDescription(){
    	return description;
    }

    public String getClassName(){
    	return className;
    }
    
	public Map<String, Object> getEditor() {
		return editor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyGrid other = (PropertyGrid) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

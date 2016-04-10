package com.ewcms.common.web.controller.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 树型类
 *
 * @param <ID>
 */
public class TreeNode<ID extends Serializable> implements Serializable {
    
	private static final long serialVersionUID = 2714472198558173574L;
	
	private ID id;
    private String text;
    private String iconCls;
    private boolean checked = false;
    private List<TreeNode<ID>> children;
	private String state;
	private Map<String, Object> attributes = Maps.newHashMap();
	private Object data;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

	public List<TreeNode<ID>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<ID>> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}

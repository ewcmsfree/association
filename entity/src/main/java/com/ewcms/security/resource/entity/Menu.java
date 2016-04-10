package com.ewcms.security.resource.entity;

import com.ewcms.common.utils.EmptyUtil;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 界面菜单对象
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>oneLevelId:根节点下所属的子节点编号</li>
 * <li>oneLevelStyle:根节点下所属的子节点显示风格</li>
 * <li>name:名称</li>
 * <li>icon:图标</li>
 * <li>url:路径</li>
 * <li>children:子节点集合</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1180504895311552005L;
	
	private Long id;
	private Long oneLevelId;
	private ResourceStyle oneLevelStyle;
    private String name;
    private String icon;
    private String url;
    private List<Menu> children;

    public Menu(Long id, Long oneLevelId, ResourceStyle oneLevelStyle, String name, String icon, String url) {
        this.id = id;
        this.oneLevelId = oneLevelId;
        this.oneLevelStyle = oneLevelStyle;
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOneLevelId() {
		return oneLevelId;
	}

	public void setOneLevelId(Long oneLevelId) {
		this.oneLevelId = oneLevelId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getChildren() {
        if (children == null) {
            children = Lists.newArrayList();
        }
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public ResourceStyle getOneLevelStyle() {
		return oneLevelStyle;
	}

	public void setOneLevelStyle(ResourceStyle oneLevelStyle) {
		this.oneLevelStyle = oneLevelStyle;
	}

	/**
     * @return
     */
    public boolean isHasChildren() {
        return !getChildren().isEmpty();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", oneLevelId=" + oneLevelId + 
                ", oneLevelStyle='" + ((EmptyUtil.isNotNull(oneLevelStyle)) ? oneLevelStyle.getInfo() : "") + "\'" +
                ", name='" + name + "\'" +
                ", icon='" + icon + "\'" +
                ", url='" + url + "\'" +
                ", children=" + children +
                "}";
    }
}
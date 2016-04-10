package com.ewcms.security.resource.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Treeable;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.PostUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资源表
 * 
 * <ul>
 * <li>name:名称</li>
 * <li>identity:资源标识符</li>
 * <li>url:地址(供菜单使用)</li>
 * <li>parentId:父节点编号</li>
 * <li>parentName:父节点名称(不作为数据字段)</li>
 * <li>parentIds:父节点路径</li>
 * <li>weight:权重(排序)</li>
 * <li>icon:图标</li>
 * <li>hasChildren:是否有叶节点(不作为数据字段)</li>
 * <li>show:是否显示</li>
 * <li>style:显示风格(只有根节点下的子节点使用)</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_resource",
		indexes = {
			@Index(name = "idx_sec_resource_name", columnList = "name"),
			@Index(name = "idx_sec_resource_identity", columnList = "identity"),
			@Index(name = "idx_sec_resource_url", columnList = "url"),
			@Index(name = "idx_sec_resource_parent_id", columnList = "parent_id"),
			@Index(name = "idx_sec_resource_parent_ids_weight", columnList = "parent_ids, weight")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_resource_id", allocationSize = 1)
public class Resource extends BaseSequenceEntity<Long> implements Treeable<Long> {

	private static final long serialVersionUID = -8087263678903245340L;

	@Column(name = "name")
    private String name;
	@Column(name = "identity")
    private String identity;
	@Column(name = "url")
    private String url;
    @NotNull(message = "{not.null}")
    @Column(name = "parent_id")
    private Long parentId;
    @Formula(value = "(select s_o.name from sec_resource s_o where s_o.id = parent_id)")
    private String parentName;
    @Column(name = "parent_ids")
    private String parentIds;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "icon")
    private String icon;
    @Formula(value = "(select count(*) from sec_resource f_t where f_t.parent_id = id)")
    private int hasChildren;
    @Column(name = "is_show")
    private Boolean show = Boolean.TRUE;
    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private ResourceStyle style;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
		return parentName;
	}

	public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public String makeSelfAsNewParentIds() {
        return getParentIds() + getId() + getSeparator();
    }

    public String getTreetableIds() {
        String selfId = makeSelfAsNewParentIds().replace("/", "-");
        return selfId.substring(0, selfId.length() - 1);
    }

    public String getTreetableParentIds() {
        String parentIds = getParentIds().replace("/", "-");
        return parentIds.substring(0, parentIds.length() - 1);
    }

    @Override
    public String getSeparator() {
        return "/";
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getIcon() {
        if (!StringUtils.isEmpty(icon)) {
            return icon;
        }
        if (isRoot()) {
            return getRootDefaultIcon();
        }
        if (isLeaf()) {
            return getLeafDefaultIcon();
        }
        return getBranchDefaultIcon();
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    @Override
    public boolean isRoot() {
        if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isLeaf() {
        if (isRoot()) {
            return false;
        }
        if (isHasChildren()) {
            return false;
        }

        return true;
    }

    public boolean isHasChildren() {
    	return this.hasChildren > 0;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }


    public ResourceStyle getStyle() {
		return style;
	}

	public void setStyle(ResourceStyle style) {
		this.style = style;
	}
	
    public String getStyleInfo(){
    	return style == null ? "" : style.getInfo();
    }

	/**
     * 根节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getRootDefaultIcon() {
        return "ztree_setting";
    }

    /**
     * 树枝节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getBranchDefaultIcon() {
        return "ztree_folder";
    }

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getLeafDefaultIcon() {
        return "ztree_file";
    }

	@PostUpdate
	public void afterPersist() {
		String[] parentIdArray = StringUtils.split(parentIds, "/");
		
		if (parentIdArray == null){
			style = ResourceStyle.accordion;
		}else{
			if (parentIdArray.length != 2){
				style = null;
			} else {
				if (style == null) style = ResourceStyle.accordion;
			}
		}
	}
}
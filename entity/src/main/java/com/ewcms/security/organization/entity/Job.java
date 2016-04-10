package com.ewcms.security.organization.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.plugin.entity.Treeable;
import com.ewcms.common.repository.support.annotation.EnableQueryCache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 工作职务树
 * 
 * <ul>
 * <li>name:名称</li>
 * <li>parentId:父节点编号</li>
 * <li>parentIds:父节点路径</li>
 * <li>weight:权重(排序)</li>
 * <li>icon:图标</li>
 * <li>hasChildren:是否有叶节点(不作为数据字段)</li>
 * <li>show:是否显示</li>
 * </ul>
 * 
 * @author wu_zhijun
 */
@Entity
@Table(name = "sec_job",
		indexes = {
			@Index(name = "idx_sec_job_name", columnList = "name"),
			@Index(name = "idx_sec_job_parentId", columnList = "parent_id"),
			@Index(name = "idx_sec_job_parentIds_weight", columnList = "parent_ids, weight")
		}
)
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name="seq", sequenceName="seq_sec_job_id", allocationSize = 1)
public class Job extends BaseSequenceEntity<Long> implements Treeable<Long> {

	private static final long serialVersionUID = 2182691935057217199L;
	
	@Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "parent_ids")
    private String parentIds;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "icon")
    private String icon;
    @Formula(value = "(select count(*) from sec_job f_t where f_t.parent_id = id)")
    private int hasChildren;
    @Column(name = "is_show")
    private Boolean show = Boolean.TRUE;

    public Job() {}

    public Job(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    /**
     * 根节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getRootDefaultIcon() {
        return "icon-channel-site";
    }

    /**
     * 树枝节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getBranchDefaultIcon() {
        return "ztree_branch";
    }

    /**
     * 树叶节点默认图标 如果没有默认 空即可
     *
     * @return
     */
    @Override
    public String getLeafDefaultIcon() {
        return "ztree_leaf";
    }
}
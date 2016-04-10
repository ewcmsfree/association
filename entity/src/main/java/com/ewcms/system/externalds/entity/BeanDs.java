package com.ewcms.system.externalds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Bean数据源
 *
 * <ul>
 * <li>beanName:Bean名称</li>
 * <li>beanMethod:Bean方法</li>
 * </ul>
 *
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_ds_bean")
@PrimaryKeyJoinColumn(name = "ds_id")
public class BeanDs extends BaseDs {

    private static final long serialVersionUID = -4508392875313490729L;
    
    @Column(name = "beanname", length = 100, nullable = true)
    private String beanName;
    @Column(name = "beanmethod", length = 100)
    private String beanMethod;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanMethod() {
        return beanMethod;
    }

    public void setBeanMethod(String beanMethod) {
        this.beanMethod = beanMethod;
    }
}

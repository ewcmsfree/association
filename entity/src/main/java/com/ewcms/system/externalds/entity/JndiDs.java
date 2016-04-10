package com.ewcms.system.externalds.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Jndi数据源
 * 
 * <ul>
 * <li>jndiName:Jndi名</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_ds_jndi")
@PrimaryKeyJoinColumn(name = "ds_id")
public class JndiDs extends BaseDs {

    private static final long serialVersionUID = 7204174514804533685L;
    
    @Column(name = "jndiname", nullable = false)
    private String jndiName;

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
}

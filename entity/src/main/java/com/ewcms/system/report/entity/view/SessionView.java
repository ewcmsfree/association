package com.ewcms.system.report.entity.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Session视图
 * 
 * <ul>
 * <li>name:名称</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_view_session")
@PrimaryKeyJoinColumn(name = "view_id")
public class SessionView extends ComponentView {

    private static final long serialVersionUID = 8091206135913046057L;
    
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

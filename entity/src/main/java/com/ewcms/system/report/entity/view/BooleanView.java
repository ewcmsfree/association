package com.ewcms.system.report.entity.view;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 布尔视图
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_view_boolean")
@PrimaryKeyJoinColumn(name = "view_id")
public class BooleanView extends ComponentView {

    private static final long serialVersionUID = 3751586581957193886L;
}

package com.ewcms.system.report.entity.view;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 日期视图
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_view_date")
@PrimaryKeyJoinColumn(name = "view_id")
public class DateView extends ComponentView {

    private static final long serialVersionUID = 8034121813285141934L;
}

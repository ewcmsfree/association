package com.ewcms.system.report.entity.view;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 多选项视图
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_view_check")
@PrimaryKeyJoinColumn(name = "view_id")
public class CheckView extends ComponentView {

    private static final long serialVersionUID = 9207930888243599387L;
}

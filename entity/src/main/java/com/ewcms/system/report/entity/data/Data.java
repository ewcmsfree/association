package com.ewcms.system.report.entity.data;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 数据父类
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_report_data")
@SequenceGenerator(name = "seq", sequenceName = "seq_sys_report_data_id", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Data extends BaseSequenceEntity<Long> {

    private static final long serialVersionUID = -4261767148281294408L;
}

package com.ewcms.system.report.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.report.entity.CategoryReport;
import com.ewcms.system.report.entity.TextReport;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface TextReportRepository extends BaseRepository<TextReport, Long> {
	
	@Query("Select c From CategoryReport As c Left Join c.texts As t Where t.id=?1")
	Set<CategoryReport> findCategoryReportByTextReportId(Long id);
	
//	@Override
//	public List<EwcmsJobReport> findEwcmsJobReportByTextReportId(final Long textReportId){
//		String hql = "Select e From EwcmsJobReport As e Where e.textReport.id=:textReportId";
//		
//		TypedQuery<EwcmsJobReport> query = em.createQuery(hql, EwcmsJobReport.class);
//		query.setParameter("textReportId", textReportId);
//		
//		return query.getResultList();
//	}
}

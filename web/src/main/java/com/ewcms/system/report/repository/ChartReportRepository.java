package com.ewcms.system.report.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.report.entity.CategoryReport;
import com.ewcms.system.report.entity.ChartReport;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface ChartReportRepository extends BaseRepository<ChartReport, Long>{
	
	@Query("Select c From CategoryReport As c Left Join c.charts As t Where t.id=?1")
	Set<CategoryReport> findCategoryReportByChartReportId(Long id);
	
//	@Override
//	public List<EwcmsJobReport> findEwcmsJobReportByChartReportId(final Long chartReportId){
//		String hql = "Select e From EwcmsJobReport As e Where e.chartReport.id=:chartReportId";
//		
//		TypedQuery<EwcmsJobReport> query = em.createQuery(hql, EwcmsJobReport.class);
//		query.setParameter("chartReportId", chartReportId);
//		
//		return query.getResultList();
//	}
}

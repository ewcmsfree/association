package com.ewcms.system.report.repository;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.report.entity.CategoryReport;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface CategoryReportRepository extends BaseRepository<CategoryReport, Long> {

	Long countByTextsIdAndId(Long textReportId, Long id);
	Long countByChartsIdAndId(Long charReportId, Long id);
}

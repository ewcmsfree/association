package com.ewcms.system.report.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.system.report.entity.CategoryReport;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.repository.CategoryReportRepository;

/**
 * 
 * @author wu_zhijun
 * 
 */
@Service
public class CategoryReportService extends BaseService<CategoryReport, Long>{

	private CategoryReportRepository getCategoryReportRepository(){
		return (CategoryReportRepository) baseRepository;
	}

	@Autowired
	private TextReportService textReportService;
	@Autowired
	private ChartReportService chartReportService;

	@Override
	public CategoryReport save(CategoryReport categoryReport){
		return addOrUpdCategoryReport(categoryReport);
	}
	
	@Override
	public CategoryReport update(CategoryReport categoryReport){
		return addOrUpdCategoryReport(categoryReport);
	}
	
	private CategoryReport addOrUpdCategoryReport(CategoryReport categoryReport){
		if (categoryReport.getId() != null){
			Long categoryReportId = categoryReport.getId();
			CategoryReport source = findOne(categoryReportId);
			if (EmptyUtil.isCollectionNotEmpty(source.getCharts())){
				categoryReport.setCharts(source.getCharts());
			}
			if (EmptyUtil.isCollectionNotEmpty(source.getCharts())){
				categoryReport.setTexts(source.getTexts());
			}
		}
		super.save(categoryReport);
		return categoryReport;
	}

	public void addReportToCategories(Long categoryReportId, List<Long> textReportIds, List<Long> chartReportIds){
		CategoryReport categoryReport = findOne(categoryReportId);
		Set<TextReport> textReports = new HashSet<TextReport>();
		if (textReportIds != null && !textReportIds.isEmpty()){
			for (Long textReportId : textReportIds){
				TextReport text = textReportService.findOne(textReportId);
				textReports.add(text);
			}
		}
		categoryReport.setTexts(textReports);
		
		Set<ChartReport> chartList = new HashSet<ChartReport>();
		if (chartReportIds != null && !chartReportIds.isEmpty()){
			for (Long chartReportId : chartReportIds){
				ChartReport chart = chartReportService.findOne(chartReportId);
				chartList.add(chart);
			}
		}
		categoryReport.setCharts(chartList);
		
		save(categoryReport);
	}

	public Boolean findTextIsEntityByTextAndCategory(Long textId, Long categoryId) {
		return getCategoryReportRepository().countByTextsIdAndId(textId, categoryId) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	public Boolean findChartIsEntityByChartAndCategory(Long chartId, Long categoryId) {
		return getCategoryReportRepository().countByChartsIdAndId(chartId, categoryId) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
}

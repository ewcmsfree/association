package com.ewcms.system.report.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.service.BaseDsService;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.exception.ReportException;
import com.ewcms.system.report.generate.factory.ChartFactory;
import com.ewcms.system.report.util.ChartAnalysisUtil;
import com.ewcms.system.report.util.ParameterSetValueUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class ChartReportService extends BaseService<ChartReport, Long>{

	@Autowired
	private CategoryReportService categorReportService;
//	@Autowired
//	private EwcmsJobReportDao ewcmsJobReportDao;
	@Autowired
	private BaseDsService baseDsService;
	@Autowired
	private ChartFactory chartFactory;

	@Override
	public ChartReport save(ChartReport chartReport){
		Assert.notNull(chartReport);
		Assert.hasLength(chartReport.getChartSql());

		Set<Parameter> parameters = ChartAnalysisUtil.analysisSql(chartReport.getChartSql());
		chartReport.setParameters(parameters);
		
		if (chartReport.getBaseDs() == null || chartReport.getBaseDs().getId() == 0L){
			chartReport.setBaseDs(null);
		}else{
			BaseDs baseDs = baseDsService.findOne(chartReport.getBaseDs().getId());
			chartReport.setBaseDs(baseDs);
		}
		super.save(chartReport);
		return chartReport;
	}

	@Override
	public ChartReport update(ChartReport chartReport){
		Assert.notNull(chartReport);
		Assert.hasLength(chartReport.getChartSql());
		
		ChartReport entity = findOne(chartReport.getId());
		
		entity.setName(chartReport.getName());
		if (chartReport.getBaseDs().getId() == 0L){
			entity.setBaseDs(null);
		}else{
			BaseDs baseDs = baseDsService.findOne(chartReport.getBaseDs().getId());
			entity.setBaseDs(baseDs);
		}
		entity.setType(chartReport.getType());
		entity.setShowTooltips(chartReport.getShowTooltips());
		entity.setChartTitle(chartReport.getChartTitle());
		entity.setFontName(chartReport.getFontName());
		entity.setFontSize(chartReport.getFontSize());
		entity.setFontStyle(chartReport.getFontStyle());
		entity.setHorizAxisLabel(chartReport.getHorizAxisLabel());
		entity.setVertAxisLabel(chartReport.getVertAxisLabel());
		entity.setDataFontName(chartReport.getDataFontName());
		entity.setDataFontSize(chartReport.getDataFontSize());
		entity.setDataFontStyle(chartReport.getDataFontStyle());
		entity.setAxisFontName(chartReport.getAxisFontName());
		entity.setAxisFontSize(chartReport.getAxisFontSize());
		entity.setAxisFontStyle(chartReport.getAxisFontStyle());
		entity.setAxisTickFontName(chartReport.getAxisTickFontName());
		entity.setAxisTickFontSize(chartReport.getAxisTickFontSize());
		entity.setAxisTickFontStyle(chartReport.getAxisTickFontStyle());
		entity.setTickLabelRotate(chartReport.getTickLabelRotate());
		entity.setShowLegend(chartReport.getShowLegend());
		entity.setLegendPosition(chartReport.getLegendPosition());
		entity.setLegendFontName(chartReport.getLegendFontName());
		entity.setLegendFontSize(chartReport.getLegendFontSize());
		entity.setLegendFontStyle(chartReport.getLegendFontStyle());
		entity.setChartHeight(chartReport.getChartHeight());
		entity.setChartWidth(chartReport.getChartWidth());
		entity.setBgColorB(chartReport.getBgColorB());
		entity.setBgColorG(chartReport.getBgColorG());
		entity.setBgColorR(chartReport.getBgColorR());
		entity.setRemarks(chartReport.getRemarks());
		entity.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		
		if (!entity.getChartSql().equals(chartReport.getChartSql())) {
			entity.setChartSql(chartReport.getChartSql());
			Set<Parameter> icNewList = new LinkedHashSet<Parameter>();
			
			Set<Parameter> oldParameters = entity.getParameters();
			Set<Parameter> newParameters = ChartAnalysisUtil.analysisSql(chartReport.getChartSql());
			for (Parameter newParameter : newParameters){
				Parameter ic = findListEntity(oldParameters,newParameter);
				if (ic == null){
					ic = newParameter;
				}
				icNewList.add(ic);
			}
			entity.setParameters(icNewList);
		}
		super.update(entity);
		return entity;
	}

//	public void delChartReport(Long chartReportId){
//		ChartReport chartReport = findOne(chartReportId);
//		Assert.notNull(chartReport);
//		Set<CategoryReport> categories = getChartReportRepository().findCategoryReportByChartReportId(chartReportId);
//		if (categories != null && !categories.isEmpty()){
//			for (CategoryReport categoryReport : categories){
//				Set<ChartReport> chartReports = categoryReport.getCharts();
//				if (chartReports.isEmpty()) continue;
//				chartReports.remove(chartReport);
//				categoryReport.setCharts(chartReports);
//				categorReportService.save(categoryReport);
//			}
//		}
//		List<EwcmsJobReport> ewcmsJobReports = chartReportDao.findEwcmsJobReportByChartReportId(chartReportId);
//		if (ewcmsJobReports != null && !ewcmsJobReports.isEmpty()){
//			for (EwcmsJobReport ewcmsJobReport : ewcmsJobReports){
//				if (ewcmsJobReport.getTextReport() == null) {
//					ewcmsJobReportDao.delete(ewcmsJobReport);
//				}else{
//					ewcmsJobReport.setChartReport(null);
//					ewcmsJobReportDao.save(ewcmsJobReport);
//				}
//			}
//		}
//		super.delete(chartReportId);
//	}

	public Long updChartReportParameter(Long chartReportId, Parameter parameter) {
		if (chartReportId == null || chartReportId.intValue() == 0)
			throw new ReportException("report.chart.id.null", null);
		ChartReport chart = findOne(chartReportId);
		if (chart == null)
			throw new ReportException("report.chart.null", null);
		
		parameter = ParameterSetValueUtil.setParametersValue(parameter);
		
		Set<Parameter> parameters = chart.getParameters();
		parameters.remove(parameter);
		parameters.add(parameter);
		chart.setParameters(parameters);
		
		update(chart);	
		
		return parameter.getId();
	}
	
//	public Map<String, Object> search(QueryParameter params){
//		return SearchMain.search(params, "IN_id", Long.class, chartReportDao, ChartReport.class);
//	}

	
	/**
	 * 根据参数名查询数据库中的参数集合
	 * 
	 * @param oldParameters
	 *            数据库中的报表参数集合
	 * @param newParameter 新参数
	 *            
	 * @return Parameter
	 */
	private Parameter findListEntity(Set<Parameter> oldParameters, Parameter newParameter) {
		for (Parameter parameter : oldParameters) {
			String rpEnName = parameter.getEnName();
			if (newParameter.getEnName().trim().equals(rpEnName.trim())) {
				parameter.setClassName(newParameter.getClassName());
				parameter.setDefaultValue(newParameter.getDefaultValue());
				parameter.setDescription(newParameter.getDescription());
				return parameter;
			}
		}
		return null;
	}
	
	public void buildChart(Map<String, String> paramMap, Long reportId, HttpServletResponse response) {
        response.setContentType("image/png");
        
        BufferedInputStream is = null;
        OutputStream os = null;
        try {
        	ChartReport chart = findOne(reportId);
        	os = response.getOutputStream();
            is = new BufferedInputStream(new ByteArrayInputStream(chartFactory.export(chart, paramMap)));
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
	}

}

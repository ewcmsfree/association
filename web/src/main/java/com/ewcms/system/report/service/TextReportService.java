package com.ewcms.system.report.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.service.BaseDsService;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.exception.ReportException;
import com.ewcms.system.report.generate.factory.TextFactory;
import com.ewcms.system.report.util.ParameterSetValueUtil;
import com.ewcms.system.report.util.TextDesignUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class TextReportService extends BaseService<TextReport, Long>{

	@Autowired
	private CategoryReportService categorReportService;
//	@Autowired
//	private EwcmsJobReportDao ewcmsJobReportDao;
	@Autowired
	private BaseDsService baseDsService;
	@Autowired
	private TextFactory textFactory;
	
	@Override
	public TextReport save(TextReport textReport) {
		byte[] reportFile = textReport.getTextEntity();

		if (reportFile != null && reportFile.length > 0) {
			InputStream in = new ByteArrayInputStream(reportFile);
			TextDesignUtil rd = new TextDesignUtil(in);
			List<JRParameter> paramList = rd.getParameters();
			
			Set<Parameter> icSet = new LinkedHashSet<Parameter>();
			if (!paramList.isEmpty()) {
				for (JRParameter param : paramList) {
					Parameter ic = getParameterValue(param);
					icSet.add(ic);
				}
				textReport.setParameters(icSet);
			}
		}
		
		if (textReport.getBaseDs().getId() == 0L){
			textReport.setBaseDs(null);
		}else{
			BaseDs baseDs = baseDsService.findOne(textReport.getBaseDs().getId());
			textReport.setBaseDs(baseDs);
		}
		super.save(textReport);
		return textReport;
	}

	@Override
	public TextReport update(TextReport textReport) {
		TextReport entity = findOne(textReport.getId());
		
		if (textReport.getBaseDs().getId() == 0L){
			entity.setBaseDs(null);
		}else{
			BaseDs baseDs = baseDsService.findOne(textReport.getBaseDs().getId());
			entity.setBaseDs(baseDs);
		}
		entity.setName(textReport.getName());
		entity.setHidden(textReport.getHidden());
		entity.setRemarks(textReport.getRemarks());
		
		entity.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		
		byte[] reportFile = textReport.getTextEntity();
		if (reportFile != null && reportFile.length > 0) {
			entity.setTextEntity(textReport.getTextEntity());
			
			InputStream in = new ByteArrayInputStream(reportFile);
			TextDesignUtil rd = new TextDesignUtil(in);

			List<JRParameter> paramList = rd.getParameters();
			Set<Parameter> icSet = entity.getParameters();

			Set<Parameter> icNewList = new LinkedHashSet<Parameter>();
			for (JRParameter param : paramList) {
				Parameter ic = findListEntity(icSet, param);
				if (ic == null) {
					ic = getParameterValue(param);
				}
				icNewList.add(ic);
			}
			entity.setParameters(icNewList);
		}
		
		super.update(entity);
		return entity;
	}

//	public void delTextReport(Long textReportId){
//		TextReport textReport = textReportDao.findOne(textReportId);
//		Assert.notNull(textReport);
//		List<CategoryReport> categories = textReportDao.findCategoryReportByTextReportId(textReportId);
//		if (categories != null && !categories.isEmpty()){
//			for (CategoryReport categoryReport : categories){
//				Set<TextReport> textReports = categoryReport.getTexts();
//				if (textReports.isEmpty()) continue;
//				textReports.remove(textReport);
//				categoryReport.setTexts(textReports);
//				categorReportDao.save(categoryReport);
//			}
//		}
//		List<EwcmsJobReport> ewcmsJobReports = textReportDao.findEwcmsJobReportByTextReportId(textReportId);
//		if (ewcmsJobReports != null && !ewcmsJobReports.isEmpty()){
//			for (EwcmsJobReport ewcmsJobReport : ewcmsJobReports){
//				if (ewcmsJobReport.getChartReport() == null){
//					ewcmsJobReportDao.delete(ewcmsJobReport);
//				}else{
//					ewcmsJobReport.setTextReport(null);
//					ewcmsJobReportDao.save(ewcmsJobReport);
//				}
//			}
//		}
//		textReportDao.delete(textReportId);
//	}

	public Long updTextReportParameter(Long textReportId, Parameter parameter) {
		if (textReportId == null || textReportId.intValue() == 0)
			throw new ReportException("report.text.id.null", null);
		TextReport text = findOne(textReportId);
		if (text == null)
			throw new ReportException("report.text.null", null);
		
		parameter = ParameterSetValueUtil.setParametersValue(parameter);
		
		Set<Parameter> parameters = text.getParameters();
		parameters.remove(parameter);
		parameters.add(parameter);
		text.setParameters(parameters);
		
		update(text);
		
		return parameter.getId();
	}
	
//	public Map<String, Object> search(QueryParameter params){
//		return SearchMain.search(params, "IN_id", Long.class, textReportDao, TextReport.class);
//	}
	
	/**
	 * 把报表文件里的参数转换数据参数
	 * 
	 * @param jrParameter
	 *            报表参数对象
	 * @return Parameters
	 */
	private Parameter getParameterValue(JRParameter jrParameter) {
		Parameter ic = new Parameter();

		ic.setEnName(jrParameter.getName());
		ic.setClassName(jrParameter.getValueClassName());
		if (jrParameter.getDefaultValueExpression() == null){
			ic.setDefaultValue("");
		}else{
			ic.setDefaultValue(jrParameter.getDefaultValueExpression().getText());
		}
		ic.setDescription(jrParameter.getDescription());
		ic.setType(Conversion(jrParameter.getValueClassName()));

		return ic;
	}

	/**
	 * 根据报表参数名查询数据库中的报表参数集合
	 * 
	 * @param icSet
	 *            数据库中的报表参数集合
	 * @param JRParameter
	 *            报表参数
	 * @return ReportParameter
	 */
	private Parameter findListEntity(Set<Parameter> icSet, JRParameter param) {
		for (Parameter ic : icSet) {
			String rpEnName = ic.getEnName();
			String jrEnName = param.getName();
			if (jrEnName.trim().equals(rpEnName.trim())) {
				return ic;
			}
		}
		return null;
	}

	/**
	 * 把类型名转换成枚举
	 * 
	 * @param className
	 *            类型名
	 * @return InputControlEnum 枚举
	 */
	private Parameter.Type Conversion(String className) {
		if (className.toLowerCase().indexOf("boolean") > -1) {
			return Parameter.Type.BOOLEAN;
		}
		return Parameter.Type.TEXT;
	}

	public void buildText(Map<String, String> paramMap, Long reportId, TextReport.Type textReportTypeMap, HttpServletResponse response) {
        BufferedInputStream is = null;
        OutputStream os = null;
        try {
        	TextReport report = findOne(reportId);
        	os = response.getOutputStream();
            is = new BufferedInputStream(new ByteArrayInputStream(textFactory.export(paramMap, report, textReportTypeMap, response)));
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
	}

}

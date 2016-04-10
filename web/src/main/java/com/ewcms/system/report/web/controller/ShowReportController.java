package com.ewcms.system.report.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.generate.entity.PageShowParam;
import com.ewcms.system.report.generate.entity.ParameterBuilder;
import com.ewcms.system.report.generate.util.ParamConversionPage;
import com.ewcms.system.report.service.CategoryReportService;
import com.ewcms.system.report.service.ChartReportService;
import com.ewcms.system.report.service.TextReportService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/show")
public class ShowReportController extends BaseController<Parameter, Long>{
	
	@Autowired
	private CategoryReportService categoryReportService;
	@Autowired
	private TextReportService textReportService;
	@Autowired
	private ChartReportService chartReportService;

	@Override
	protected void setCommonData(Model model) {
		model.addAttribute("categoryReportList", categoryReportService.findAll());
		model.addAttribute("textReportTypeMap", TextReport.Type.values());
		super.setCommonData(model);

	}
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		setCommonData(model);
		return viewName("index");
	}

	@RequestMapping(value = "{reportType}/{reportId}/paraset")
	public String parameterSet(@PathVariable("reportType") String reportType, @PathVariable("reportId") Long reportId, Model model) {
		setCommonData(model);
		List<PageShowParam> pageShowParams = new ArrayList<PageShowParam>();
		if (reportType.toLowerCase().trim().equals("text")) {
			pageShowParams = ParamConversionPage.conversion(textReportService.findOne(reportId).getParameters());
		} else if (reportType.toLowerCase().trim().equals("chart")) {
			pageShowParams = ParamConversionPage.conversion(chartReportService.findOne(reportId).getParameters());
		}
		model.addAttribute("pageShowParams", pageShowParams);
		model.addAttribute("m", new ParameterBuilder());
		return viewName("paraset");
	}

	@RequestMapping(value = "{reportType}/{reportId}/build")
	public void build(@PathVariable("reportType") String reportType, @PathVariable("reportId") Long reportId, @ModelAttribute("parameterBuilder") ParameterBuilder parameterBuilder, HttpServletResponse response) {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        
		if (reportType.equals("text")){
			textReportService.buildText(parameterBuilder.getParamMap(), reportId, parameterBuilder.getTextType(), response);
		}else if (reportType.equals("chart")){
			chartReportService.buildChart(parameterBuilder.getParamMap(), reportId, response);
		}
	}
	

}

package com.ewcms.system.report.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.common.web.controller.entity.PropertyGrid;
import com.ewcms.common.web.controller.permission.PermissionList;
import com.ewcms.system.report.entity.CategoryReport;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.service.CategoryReportService;
import com.ewcms.system.report.service.ChartReportService;
import com.ewcms.system.report.service.TextReportService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/category/detail")
public class CategoryReportDetailController extends BaseController<CategoryReport, Long>{

	private PermissionList permissionList = PermissionList.newPermissionList("system:categoryreport");
	
	private static final String TEXT_GROUP_TITLE = "文字报表";
	private static final String CHART_GROUP_TITLE = "图型报表";

	@Autowired
	private CategoryReportService categoryReportService;
	@Autowired
	private TextReportService textReportService;
	@Autowired
	private ChartReportService chartReportService;

	@RequestMapping(value = "{categoryId}/index")
	public String index(@PathVariable("categoryId") Long categoryId) {
		permissionList.assertHasViewPermission();
		return viewName("index");
	}

	@RequestMapping(value = "{categoryId}/query")
	@ResponseBody
	public Map<String, Object> query(@PathVariable("categoryId") CategoryReport categoryReport) {
		List<PropertyGrid> propertyGrids = new ArrayList<PropertyGrid>();
		if (categoryReport != null) {
			Set<TextReport> textReports = categoryReport.getTexts();
			for (TextReport textReport : textReports) {
				PropertyGrid propertyGrid = new PropertyGrid(textReport.getName(), textReport.getRemarks(), TEXT_GROUP_TITLE);
				propertyGrids.add(propertyGrid);
			}
			Set<ChartReport> chartReports = categoryReport.getCharts();
			for (ChartReport chartReport : chartReports) {
				PropertyGrid propertyGrid = new PropertyGrid(chartReport.getName(), chartReport.getRemarks(), CHART_GROUP_TITLE);
				propertyGrids.add(propertyGrid);
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", propertyGrids.size());
		resultMap.put("rows", propertyGrids);
		return resultMap;
	}

	@RequestMapping(value = "{categoryId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable("categoryId") Long categoryId) {
		permissionList.assertHasCreatePermission();
		return viewName("edit");
	}

	@RequestMapping(value = "{categoryId}/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = false) List<Long> textReportIds, @RequestParam(required = false) List<Long> chartReportIds,
			@PathVariable("categoryId") Long categoryId, Model model) {
		permissionList.assertHasCreatePermission();
		try {
			categoryReportService.addReportToCategories(categoryId, textReportIds, chartReportIds);
			model.addAttribute("close", Boolean.TRUE);
		} catch (IllegalStateException e) {
		}
		return viewName("edit");
	}
	
	@RequestMapping(value = "{categoryId}/text")
	public @ResponseBody List<ComboBox> findTextReport(@PathVariable("categoryId")Long categoryId){
		Iterable<TextReport> texts = textReportService.findAll();
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		if (texts != null) {
			ComboBox comboBox = null;
			for (TextReport text : texts) {
				comboBox = new ComboBox();
				comboBox.setId(text.getId());
				comboBox.setText(text.getName());
				Boolean isEntity = categoryReportService.findTextIsEntityByTextAndCategory(text.getId(),categoryId);
				if (isEntity)comboBox.setSelected(true);
				comboBoxs.add(comboBox);
			}
		}
		return comboBoxs;
	}
	
	@RequestMapping(value = "{categoryId}/chart")
	public @ResponseBody List<ComboBox> findChartReport(@PathVariable("categoryId") Long categoryId) {
		Iterable<ChartReport> charts = chartReportService.findAll();
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		if (charts != null) {
			ComboBox comboBox = null;
			for (ChartReport chart : charts) {
				comboBox = new ComboBox();
				comboBox.setId(chart.getId());
				comboBox.setText(chart.getName());
				Boolean isEntity = categoryReportService.findChartIsEntityByChartAndCategory(chart.getId(), categoryId);
				if (isEntity) comboBox.setSelected(true);
				comboBoxs.add(comboBox);
			}
		}
		return comboBoxs;
	}
}

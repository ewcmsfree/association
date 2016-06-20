package com.ewcms.system.report.web.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.service.ChartReportService;
import com.ewcms.system.report.service.ParameterService;
import com.ewcms.system.report.service.TextReportService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/parameter")
public class ParameterController extends BaseCRUDController<Parameter, Long>{

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private TextReportService textReportService;
	@Autowired
	private ChartReportService chartReportService;
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("typeMap",Parameter.Type.values());
	}
	
	@RequestMapping(value = "{reportType}/{reportId}/index")
	public String index(@PathVariable(value = "reportType") String reportType, @PathVariable(value = "reportId") Long reportId, Model model){
		return super.index(model);
	}
	
	@RequestMapping(value = "{reportType}/{reportId}/save",method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "reportType") String reportType, @PathVariable(value = "reportId") Long reportId, Model model, @RequestParam(required = false) List<Long> selections){
		return super.showSaveForm(model, selections);
	}	
	
	@RequestMapping(value = "{reportType}/{reportId}/save", method = RequestMethod.POST)
	public String save(@CurrentUser User user, @PathVariable(value = "reportType") String reportType, @PathVariable(value = "reportId") Long reportId,Model model, @Valid @ModelAttribute("m") Parameter m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }

		setCommonData(model);
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        if (m.getType() == Parameter.Type.SESSION){
				m.setDefaultValue(user.getUsername());
			}
	        
	        Long parameterId = null;
			if ("text".equals(reportType)){
				parameterId = textReportService.updTextReportParameter(reportId, m);
			} else if ("chart".equals(reportType)){
				parameterId = chartReportService.updChartReportParameter(reportId, m);
			}
			
	        Parameter lastM = parameterService.findOne(parameterId);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		
		return showSaveForm(model, selections);
	}
	
	@RequestMapping(value = "{reportType}/{reportId}/query")
	public @ResponseBody Map<String, Object> query(@PathVariable(value = "reportType")String reportType, @PathVariable(value = "reportId")Long reportId){
		Map<String, Object> resultMap = Maps.newHashMap();
		Long count = 0L;
		Set<Parameter> parameters = Sets.newLinkedHashSet();
		if (reportType.toLowerCase().trim().equals("text")){
			TextReport textReport = textReportService.findOne(reportId);
			parameters = textReport.getParameters();
		}else if (reportType.toLowerCase().trim().equals("chart")){
			ChartReport chartReport = chartReportService.findOne(reportId);
			parameters = chartReport.getParameters();
		}
		resultMap.put("total", count);
		resultMap.put("rows", parameters);
		return resultMap;
	}
}

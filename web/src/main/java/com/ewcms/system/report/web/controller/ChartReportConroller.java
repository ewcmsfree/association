package com.ewcms.system.report.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.service.BaseDsService;
import com.ewcms.system.report.entity.ChartReport;
import com.ewcms.system.report.util.ChartUtil;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/chart")
public class ChartReportConroller extends BaseCRUDController<ChartReport, Long>{
	
	@Autowired
	private BaseDsService baseDsService;

	public ChartReportConroller() {
		setResourceIdentity("system:chartreport");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("typeMap", ChartReport.Type.values());
		model.addAttribute("baseDsList", baseDsService.findAllBaseDs());
		model.addAttribute("fontNameMap", ChartUtil.getFontNameMap());
		model.addAttribute("fontStyleMap", ChartUtil.getFontStyleMap());
		model.addAttribute("fontSizeMap", ChartUtil.getFontSizeMap());
		model.addAttribute("rotateMap", ChartUtil.getRotateMap());
		model.addAttribute("positionMap", ChartUtil.getPositionMap());
		model.addAttribute("alignmentMap", ChartUtil.getAlignmentMap());
	}
	
	@RequestMapping(value = "sqlHelp")
	public String sqlHelp(){
		return viewName("sql_help");
	}
}

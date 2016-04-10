package com.ewcms.system.report.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.report.entity.CategoryReport;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/category")
public class CategoryReportController extends BaseCRUDController<CategoryReport, Long>{
	
    public CategoryReportController() {
        setResourceIdentity("system:categoryreport");
    }
}

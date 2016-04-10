package com.ewcms.system.externalds.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.entity.JdbcDs;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/externalds/jdbc")
public class JdbcDsController extends BaseCRUDController<JdbcDs, Long>{
	
	public JdbcDsController() {
		setResourceIdentity("system:externalds");
	}
}

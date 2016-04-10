package com.ewcms.system.externalds.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.entity.CustomDs;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/externalds/custom")
public class CustomDsController extends BaseCRUDController<CustomDs, Long>{
	
	public CustomDsController() {
		setResourceIdentity("system:externalds");
	}
}

package com.ewcms.system.externalds.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.entity.BeanDs;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/externalds/bean")
public class BeanDsController extends BaseCRUDController<BeanDs, Long>{
	
	public BeanDsController() {
		setResourceIdentity("system:externalds");
	}
}

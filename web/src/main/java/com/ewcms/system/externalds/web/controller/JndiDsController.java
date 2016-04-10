package com.ewcms.system.externalds.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.system.externalds.entity.JndiDs;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/externalds/jndi")
public class JndiDsController extends BaseCRUDController<JndiDs, Long>{
	
	public JndiDsController() {
		setResourceIdentity("system:externalds");
	}
}

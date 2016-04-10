package com.ewcms.personal.message.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message")
public class MsgTabsController {

	@RequestMapping(value = "/index")
	public String index(){
		return "personal/message/tabs";
	}
}
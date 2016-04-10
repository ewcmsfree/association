package com.ewcms.personal.message.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.entity.MsgType;
import com.ewcms.personal.message.service.MsgSendService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message/more")
public class MsgMoreController extends BaseCRUDController<MsgSend, Long>{
	
	@Autowired
	private MsgSendService msgSendService;
	
	@RequestMapping(value = "index")
	public String index(){
		return "personal/message/more/index";
	}
	
	@RequestMapping(value = "query/discard")
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "/query")
	@ResponseBody
	public Map<String, Object> query(@RequestParam(value = "type") MsgType type, @ModelAttribute SearchParameter<Long> searchParameter){
		return msgSendService.queryMore(searchParameter, type);
	}
}

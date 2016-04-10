package com.ewcms.personal.message.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.personal.message.entity.MsgContent;
import com.ewcms.personal.message.entity.MsgReceive;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.service.MsgReceiveService;
import com.ewcms.personal.message.service.MsgSendService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message/detail")
public class MsgDetailController {
	
	@Autowired
	private MsgReceiveService msgRecieveService;
	@Autowired
	private MsgSendService msgSendService;

	@RequestMapping(value = "{id}/{type}/index")
	public String index(@CurrentUser User user,
			@PathVariable(value = "id") Long id, 
			@PathVariable(value = "type") String type, 
			Model model){
		if (type.toLowerCase().equals("notice")){
			MsgSend msgSend = msgSendService.findOne(id);
			model.addAttribute("title", msgSend.getTitle());
			model.addAttribute("detail", msgSend.getMsgContents().get(0).getDetail());
			model.addAttribute("results", null);
		}else if (type.toLowerCase().equals("subscription")){
			MsgSend msgSend = msgSendService.findOne(id);
			model.addAttribute("title", msgSend.getTitle());
			List<MsgContent> msgContents = msgSend.getMsgContents();
			List<String> details = new ArrayList<String>();
			for (MsgContent msgContent : msgContents){
				details.add(msgContent.getDetail());
			}
			model.addAttribute("results", details);
		}else if (type.toLowerCase().equals("message")){
			MsgReceive msgReceive = msgRecieveService.findOne(id);
			model.addAttribute("title", msgReceive.getMsgContent().getTitle());
			model.addAttribute("detail", msgReceive.getMsgContent().getDetail());
			model.addAttribute("results", null);
			msgRecieveService.readMsgReceive(user.getId(), id);
		}
		return "personal/message/detail/index";
	}
}

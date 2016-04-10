package com.ewcms.personal.message.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personal.message.entity.MsgContent;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.service.MsgSendService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message/content")
public class MsgContentController extends BaseCRUDController<MsgContent, Long>{
	
	@Autowired
	private MsgSendService msgSendService;
	
	@Override
	public AjaxResponse delete(@RequestParam(value = "selections") List<Long> selections){
        AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
        
        try{
	        if (selections != null && !selections.isEmpty()){
	        	msgSendService.delSubscription(selections.get(0));
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{msgSendId}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value="msgSendId")Long msgSendId, Model model) {
		model.addAttribute("msgContent", new MsgContent());
		return viewName("edit");
	}

	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") MsgContent m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{msgSendId}/save", method = RequestMethod.POST)
	public String save(@CurrentUser User user,
			@PathVariable(value = "msgSendId")Long msgSendId, @RequestParam(required = false) List<Long> selections, @RequestParam(value = "title") String title, @RequestParam(value = "detail") String detail, Model model) {
		msgSendService.addSubscription(user.getId(), msgSendId, title, detail);
		selections = selections == null ? new ArrayList<Long>() : selections;
		selections.add(0, msgSendId);
		model.addAttribute("msgSend", new MsgSend());
		model.addAttribute("selections", selections);
		return viewName("edit");
	}
}

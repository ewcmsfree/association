package com.ewcms.personal.message.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personal.message.entity.MsgReceive;
import com.ewcms.personal.message.service.MsgReceiveService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message/receive")
public class MsgReceiveController extends BaseCRUDController<MsgReceive, Long>{
	
	private MsgReceiveService getMsgReceiveService(){
		return (MsgReceiveService) baseService;
	}
	
	public MsgReceiveController() {
		setListAlsoSetCommonData(true);
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		return getMsgReceiveService().query(user.getId(), searchParameter);
	}
	
	@RequestMapping(value = "delete/discard")
	@Override
	public AjaxResponse delete(@RequestParam(value = "selections") List<Long> selections){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public AjaxResponse delete(@CurrentUser User user, @RequestParam(value = "selections") List<Long> selections){
        AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getMsgReceiveService().delete(user.getId(), selections);
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "changeStatus/{status}")
	@ResponseBody
	public AjaxResponse markRead(@CurrentUser User user, 
			@PathVariable(value = "status") Boolean status,
			@RequestParam(value = "selections") List<Long> selections){
        AjaxResponse ajaxResponse = new AjaxResponse("标记成功！");
        try{
	        if (selections != null && !selections.isEmpty()){
	        	getMsgReceiveService().markReadMsgReceive(user.getId(), selections.get(0), status);
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("标记失败了！");
        }
		return ajaxResponse;
	}
	
	@RequestMapping(value = "unread")
	@ResponseBody
	public List<MsgReceive> unRead(@CurrentUser User user){
		return getMsgReceiveService().findMsgReceiveByUnRead(user.getId());
	}
}

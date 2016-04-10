package com.ewcms.security.user.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.eis.OnlineSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.MessageUtils;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.security.user.entity.UserOnline;
import com.ewcms.security.user.service.UserService;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/online")
public class UserOnlineController extends BaseCRUDController<UserOnline, String> {

	@Autowired
	private OnlineSessionDAO onlineSessionDAO;
	@Autowired
	private UserService userService;
	
	public UserOnlineController(){
		setListAlsoSetCommonData(true);
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("userList", userService.findAll());
	}
	
    @Override
    public Map<String, Object> query(@ModelAttribute SearchParameter<String> searchParameter, Model model) {
        if (!SecurityUtils.getSubject().isPermitted("security:userOnline:view or monitor:userOnline:view")) {
            throw new UnauthorizedException(MessageUtils.message("no.view.permission", "security:userOnline:view或monitor:userOnline:view"));
        }
        Map<String, Direction> sorts = searchParameter.getSorts();
        
        sorts.put("startTimestamp", Direction.DESC);
        sorts.put("lastAccessTime", Direction.DESC);
        
        searchParameter.setSorts(sorts);
        
        return super.query(searchParameter, model);
    }
	
	@RequestMapping(value = "forceLogout")
	@ResponseBody
	public AjaxResponse forceLogout(@RequestParam(required = false) List<String> selections){
        if (!SecurityUtils.getSubject().isPermitted("security:userOnline or monitor:userOnline")) {
            throw new UnauthorizedException(MessageUtils.message("no.view.permission", "security:userOnline或monitor:userOnline"));
        }

        AjaxResponse ajaxResponse = new AjaxResponse("强制退出成功！");
        try{
	        for (String id : selections){
				UserOnline online = baseService.findOne(id);
				if (online == null) continue;
				OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
				if (onlineSession == null) continue;
				onlineSession.setStatus(OnlineSession.OnlineStatus.force_logout);
				online.setStatus(OnlineSession.OnlineStatus.force_logout);
				baseService.update(online);
			}
        } catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
        	ajaxResponse.setMessage("强制退出失败了！");
        }
		return ajaxResponse;
	}
	
}

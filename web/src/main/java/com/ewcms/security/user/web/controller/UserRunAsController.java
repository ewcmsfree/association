package com.ewcms.security.user.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.search.SearchHelper;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserRunAs;
import com.ewcms.security.user.entity.UserRunAsPk;
import com.ewcms.security.user.entity.UserStatus;
import com.ewcms.security.user.service.UserRunAsService;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.security.user.web.controller.entity.SwitchUser;
import com.google.common.collect.Lists;

/**
 *
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/security/user/runAs")
public class UserRunAsController extends BaseCRUDController<UserRunAs, UserRunAsPk> {

	private UserRunAsService getUserRunAsService() {
		return (UserRunAsService) baseService;
	}

	@Autowired
	private UserService userService;

	public UserRunAsController() {
		setListAlsoSetCommonData(true);
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			model.addAttribute("previousUsername", previousUsername);
		}
	}

	@RequestMapping(value = "query/discard")
	@ResponseBody
	@Override
	public Map<String, Object> query(
			@ModelAttribute SearchParameter<UserRunAsPk> searchParameter,
			Model model) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "queryCanSwith")
	@ResponseBody
	public Map<String, Object> queryCanSwith(@CurrentUser User user, @ModelAttribute SearchParameter<UserRunAsPk> searchParameter) {
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, UserRunAs.class);
		searchable.addSearchFilter("id.fromUserId", SearchOperator.EQ, user.getId());
		searchable.addSort(Direction.ASC, "id");

		Page<UserRunAs> userRunAss = getUserRunAsService().findAll(searchable);
		
		String switchToTemplate = "<a href='switchTo/%1$s'>切换到该身份</a>";
		List<SwitchUser> switchUsers = Lists.newArrayList();
		SwitchUser switchUser = null;
		for (UserRunAs userRunAs : userRunAss.getContent()){
			Long toUserId = userRunAs.getId().getToUserId();
			User toUser = userService.findOne(toUserId);
			if (EmptyUtil.isNull(toUser)) continue;
			if (toUser.getDeleted()){
				switchUser = new SwitchUser(toUser.getUsername(), toUser.getRealname(), "用户已被删除了，不能切换到该身份");
			} else if (toUser.getStatus() == UserStatus.blocked){
				switchUser = new SwitchUser(toUser.getUsername(), toUser.getRealname(), "用户处于" + UserStatus.blocked.getInfo() + "，不能切换到该身份");
			} else {
				switchUser = new SwitchUser(toUser.getUsername(), toUser.getRealname(), String.format(switchToTemplate, toUser.getId()));
			}
			switchUsers.add(switchUser);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", switchUsers.size());
		resultMap.put("rows", switchUsers);
		return resultMap;
	}

	@RequestMapping(value = "queryAllUser")
	@ResponseBody
	public Map<String, Object> queryAllUser(@CurrentUser User user, @ModelAttribute SearchParameter<UserRunAsPk> searchParameter) {
		Searchable searchable = SearchHelper.parameterConverSearchable(searchParameter, User.class);
		searchable.addSearchFilter("id", SearchOperator.NE, user.getId());
		searchable.addSort(Direction.ASC, "id");

		Page<User> users = userService.findAll(searchable);
		
		String revokeUrlTemplate = "<a href='revoke/%1$s'>回收身份</a>";
		String grantUrlTemplate = "<a href='grant/%1$s'>授予身份</a>";
		
		List<Long> fromUserIds = getUserRunAsService().findByToUserIds(user.getId());
		List<SwitchUser> switchUsers = Lists.newArrayList();
		SwitchUser switchUser = null;
		for (User toUser : users.getContent()){
			String opMethod = String.format(grantUrlTemplate, toUser.getId());
			if (EmptyUtil.isCollectionNotEmpty(fromUserIds) && CollectionUtils.contains(fromUserIds.iterator(), toUser.getId())) {
				opMethod = String.format(revokeUrlTemplate, toUser.getId());
			}
			switchUser = new SwitchUser(toUser.getUsername(), toUser.getRealname(), opMethod);
			switchUsers.add(switchUser);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", switchUsers.size());
		resultMap.put("rows", switchUsers);
		return resultMap;
	}

	@RequestMapping("/grant/{toUserId}")
	public String grant(@CurrentUser User user,
			@PathVariable("toUserId") Long toUserId,
			RedirectAttributes redirectAttributes) {
		if (user.getId().equals(toUserId)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "自己不能切换到自己的身份");
			return redirectToUrl(viewName("index"));
		}
		getUserRunAsService().grantRunAs(toUserId, user.getId());
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "授予身份操作成功");
		return redirectToUrl(viewName("index"));
	}

	@RequestMapping("/revoke/{toUserId}")
	public String revoke(@CurrentUser User user,
			@PathVariable("toUserId") Long toUserId,
			RedirectAttributes redirectAttributes) {
		getUserRunAsService().revokeRunAs(toUserId, user.getId());
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "回收身份操作成功");
		return redirectToUrl(viewName("index"));
	}

	@RequestMapping("/switchTo/{switchToUserId}")
	public String switchTo(@CurrentUser User user,
			@PathVariable("switchToUserId") Long switchToUserId,
			RedirectAttributes redirectAttributes) {
		Subject subject = SecurityUtils.getSubject();
		User switchToUser = userService.findOne(switchToUserId);
		if (user.equals(switchToUser)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "自己不能切换到自己的身份");
			return redirectToUrl(viewName("index"));
		}
		if (switchToUser == null || !getUserRunAsService().exists(user.getId(), switchToUserId)) {
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "对方没有授予您身份，不能切换");
			return redirectToUrl(viewName("index"));
		}
		if (switchToUser.getDeleted()){
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "对方已被删除了，不能切换");
			return redirectToUrl(viewName("index"));
		}
		if (switchToUser.getStatus() == UserStatus.blocked){
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "用户处于" + UserStatus.blocked.getInfo() + "，不能切换");
			return redirectToUrl(viewName("index"));
		}
		subject.runAs(new SimplePrincipalCollection(switchToUser.getUsername(),""));
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "切换身份操作成功！");
		redirectAttributes.addFlashAttribute("needRefresh", "true");
		return redirectToUrl(viewName("index"));
	}

	@RequestMapping("/switchBack")
	public String switchBack(RedirectAttributes redirectAttributes) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isRunAs()) {
			subject.releaseRunAs();
		}
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "切换身份操作成功！");
		redirectAttributes.addFlashAttribute("needRefresh", "true");
		return redirectToUrl(viewName("index"));
	}
}

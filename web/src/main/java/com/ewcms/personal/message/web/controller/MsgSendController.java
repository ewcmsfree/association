package com.ewcms.personal.message.web.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.controller.entity.ComboBox;
import com.ewcms.personal.message.entity.MsgSend;
import com.ewcms.personal.message.entity.MsgType;
import com.ewcms.personal.message.service.MsgSendService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/message/send")
public class MsgSendController extends BaseCRUDController<MsgSend, Long>{

	private MsgSendService getMsgSendService(){
		return (MsgSendService) baseService;
	}
	
	@Autowired
	private UserService userService;
	
	public MsgSendController() {
		setListAlsoSetCommonData(true);
	}
	
	protected void setCommonData(Model model, User user) {
		super.setCommonData(model);
		if (user.getAdmin()){
			model.addAttribute("sendTypeList", MsgType.values());
		} else {
			MsgType[] msgTypes = new MsgType[1];
			msgTypes[0] = MsgType.GENERAL;
			model.addAttribute("sendTypeList", msgTypes);
		}
	}
	
	@RequestMapping(value = "index/discard", method = RequestMethod.GET)
	@Override
	public String index(Model model){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model, @CurrentUser User user) {
		setCommonData(model, user);
		return viewName("index");
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@CurrentUser User user, SearchParameter<Long> searchParameter, Model model) {
		return getMsgSendService().query(user.getId(), searchParameter);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections, @CurrentUser User user) {
		setCommonData(model, user);

        if (!model.containsAttribute("close")){
	        if (selections == null || selections.isEmpty()) {
	        	model.addAttribute("operate", "add");
		        
		        if (!model.containsAttribute("m")) {
		            model.addAttribute("m", newModel());
		        }
			} else {
				model.addAttribute("operate", "update");
				
				MsgSend m = baseService.findOne(selections.get(0));
				model.addAttribute("m", m);
				model.addAttribute("selections", selections);
			}
        }
        
		return viewName("edit");
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") MsgSend m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@CurrentUser User user, @Valid @ModelAttribute(value = "m") MsgSend m, BindingResult result, @RequestParam(required = false) List<Long> selections, @RequestParam(value="content", required = false) String content, @RequestParam(value="userIds", required = false) Set<Long> userIds, Model model) {
		if (hasError(m, result)) {
            return showSaveForm(model, selections, user);
        }
		
		setCommonData(model, user);
			
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
			
		} else {
			Long id = getMsgSendService().addMsgSend(user.getId(), m, content, userIds);
			
			MsgSend lastM = baseService.findOne(id);
			
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(lastM));
		}
		return showSaveForm(model, selections, user);
	}
	
	@RequestMapping(value = "/user")
	public @ResponseBody List<ComboBox> user(@RequestParam(value = "msgSendId", defaultValue = "-1")Long msgSendId){
		Set<Long> userIds = Sets.newHashSet();
		
		MsgSend msgSend = getMsgSendService().findOne(msgSendId);
		if (EmptyUtil.isNotNull(msgSend)){
			userIds = msgSend.getUserIds();
		}
		
		List<ComboBox> comboBoxStrings = Lists.newArrayList();
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("deleted", SearchOperator.EQ, Boolean.FALSE);
		searchable.addSort(Direction.ASC, "id");

		List<User> users = userService.findAllWithSort(searchable);
		ComboBox comboBox = null;
		for (User user : users){
			comboBox = new ComboBox();
			comboBox.setId(user.getId());
			comboBox.setText(user.getUsername() + "(" + (EmptyUtil.isStringNotEmpty(user.getRealname()) ? user.getRealname() : "") + ")");
			if (userIds.contains(user.getId())){
				comboBox.setSelected(true);
			}
			comboBoxStrings.add(comboBox);
		}
		return comboBoxStrings;
	}
}

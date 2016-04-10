package com.ewcms.personal.memoranda.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personal.memoranda.entity.Memoranda;
import com.ewcms.personal.memoranda.entity.Memoranda.BeforeStatus;
import com.ewcms.personal.memoranda.entity.Memoranda.FrequencyStatus;
import com.ewcms.personal.memoranda.service.MemorandaService;
import com.ewcms.personal.memoranda.util.Lunar;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/personal/memoranda")
public class MemorandaConroller extends BaseCRUDController<Memoranda, Long>{
	
	private MemorandaService getMemorandaService(){
		return (MemorandaService) baseService;
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		model.addAttribute("beforeList", BeforeStatus.values());
		model.addAttribute("frequencyList", FrequencyStatus.values());
		model.addAttribute("booleanList", BooleanEnum.values());
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "index")
	public String index(@CurrentUser User user,
			@RequestParam(value = "year", required = false)Integer year, 
			@RequestParam(value = "month", required = false)Integer month, Model model){
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DATE);
		
		Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("currentMonth", currentMonth);
		
		model.addAttribute("toDayLunar",Lunar.getLunar("" +  currentYear, "" + currentMonth, "" + currentDay));
		
		if (year == null || month == null) {
			year = currentYear;
			month = currentMonth;
			model.addAttribute("year", year).addAttribute("month", month);
		}
//		String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		model.addAttribute("result", getMemorandaService().getInitCalendarToHtml(user.getId(), year, month));
		
		return viewName("index");
	}
	
	@RequestMapping(value = "{year}/{month}/changeDate")
	@ResponseBody
	public AjaxResponse changeDate(@CurrentUser User user,
			@PathVariable(value = "year") Integer year, 
			@PathVariable(value = "month") Integer month){
//		String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			ajaxResponse.setMessage(getMemorandaService().getInitCalendarToHtml(user.getId(), year, month).toString());
		}catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "{currentYear}/{currentMonth}/toDay")
	@ResponseBody
	public AjaxResponse toDay(@CurrentUser User user,
			@PathVariable(value = "currentYear") Integer currentYear, 
			@PathVariable(value = "currentMonth")Integer currentMonth){
//		String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			ajaxResponse.setMessage(getMemorandaService().getInitCalendarToHtml(user.getId(), currentYear, currentMonth).toString());
		}catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
		
	@RequestMapping(value = "{memorandaId}/{year}/{month}/{dropDay}/drop")
	@ResponseBody
	public AjaxResponse drop(@PathVariable(value = "memorandaId")Long memorandaId, 
			@PathVariable(value = "year") Integer year, 
			@PathVariable(value = "month")Integer month, 
			@PathVariable(value = "dropDay") Integer dropDay){
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			getMemorandaService().update(memorandaId, year, month, dropDay);
		}catch(Exception e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "{year}/{month}/{day}/save", method = RequestMethod.GET)
	public String showSaveForm(@PathVariable(value = "year")Integer year, 
			@PathVariable(value = "month")Integer month, 
			@PathVariable(value = "day")Integer day, Model model){
		Memoranda memoranda = new Memoranda();
		model.addAttribute("year", year).addAttribute("month", month).addAttribute("day", day).addAttribute("m", memoranda);
		return super.showSaveForm(model, null);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") Memoranda m, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public String save(@CurrentUser User user,
			@RequestParam(value = "year")Integer year, 
			@RequestParam(value = "month") Integer month, 
			@RequestParam(value = "day") Integer day, 
			@Valid @ModelAttribute(value = "m") Memoranda m, BindingResult result, 
			@RequestParam(required = false) List<Long> selections, 
			Model model){
		if (hasError(m, result)) {
            return showSaveForm(year, month, day, model);
        }
		
		setCommonData(model);
		
		//Boolean close = Boolean.FALSE;
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())){
			getMemorandaService().update(m);
			selections.remove(0);
			if(selections == null || selections.isEmpty()){
				//close = Boolean.TRUE;
			}else{
				m = getMemorandaService().findOne(selections.get(0));
				model.addAttribute("m", m);
				model.addAttribute("selections", selections);
			}
		}else{
			getMemorandaService().save(user.getId(), m, year, month, day);
			selections = selections == null ? new ArrayList<Long>() : selections;
			selections.add(0, m.getId());
			model.addAttribute("m", new Memoranda());
			model.addAttribute("selections", selections);
		}
		model.addAttribute("close", Boolean.TRUE).addAttribute("year", year).addAttribute("month", month).addAttribute("day", day);
		return viewName("edit");
	}
	
	@RequestMapping(value = "list")
	public String list(Model model){
		setCommonData(model);
		return viewName("list");
	}
}

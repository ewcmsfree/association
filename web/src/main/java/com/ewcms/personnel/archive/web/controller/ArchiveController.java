package com.ewcms.personnel.archive.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewcms.common.Constants;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.personnel.acadcategory.service.AcadCategoryService;
import com.ewcms.personnel.allowance.service.AllowanceService;
import com.ewcms.personnel.archive.entity.AllowanceEnum;
import com.ewcms.personnel.archive.entity.Archive;
import com.ewcms.personnel.archive.entity.ArchiveHistory;
import com.ewcms.personnel.archive.entity.ArchiveStatus;
import com.ewcms.personnel.archive.entity.Sex;
import com.ewcms.personnel.archive.service.ArchiveHistoryService;
import com.ewcms.personnel.archive.service.ArchiveService;
import com.ewcms.personnel.currentstate.service.CurrentStateService;
import com.ewcms.personnel.photo.service.PhotoService;
import com.ewcms.security.organization.service.OrganizationService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.entity.UserOrganizationJob;
import com.ewcms.security.user.service.UserOrganizationJobService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.service.TextReportService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/personnel/archive")
public class ArchiveController extends BaseCRUDController<Archive, Long>{

	@Autowired
	private PhotoService attachmentService;
	@Autowired
	private CurrentStateService currentStateService;
	@Autowired
	private ArchiveHistoryService archiveHistoryService;
	@Autowired
	private AllowanceService allowanceService;
	@Autowired
	private UserOrganizationJobService userOrganizationJobService;
	@Autowired
	private AcadCategoryService acadCategoryService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private TextReportService textReportService;
	
	private ArchiveService getArchiveService(){
		return (ArchiveService) baseService;
	}
	
	public ArchiveController(){
		setListAlsoSetCommonData(true);
		setResourceIdentity("personnel:archive");
	}
	
	@Override
	protected void setCommonData(Model model) {
		super.setCommonData(model);
		
		model.addAttribute("sexList", Sex.values());
		model.addAttribute("booleanList", BooleanEnum.values());
		model.addAttribute("allowanceEnumList", AllowanceEnum.values());
		model.addAttribute("statusList", ArchiveStatus.values());

		Sort sort = new Sort(Sort.Direction.ASC, "id");
		model.addAttribute("currentStateList", currentStateService.findAll(sort));
		model.addAttribute("allowanceList", allowanceService.findAll(sort));
		model.addAttribute("categoryList", acadCategoryService.findAll(sort));
	}
	
	@Override
	@RequestMapping(value = "index/discard")
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "index")
	public String index(@CurrentUser User user, Model model){
		model.addAttribute("isAdmin", user.getAdmin() && !user.getIsRegister());
		return super.index(model);
	}

	@Override
	@RequestMapping(value = "query/discard")
	public Map<String, Object> query(SearchParameter<Long> searchParameter, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query")
	@ResponseBody
	public Map<String, Object> query(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		getArchiveService().covertSearchParameter(user, searchParameter);
		searchParameter.getSorts().put("createTime", Direction.DESC);
		return super.query(searchParameter, model);
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{archive}/view", method = RequestMethod.GET)
	public String viewForm(Model model, @PathVariable(value = "archive") Archive archive){
		setCommonData(model);
		
		archive.setDeleted(true);
		model.addAttribute("m", archive);
		model.addAttribute("view", Boolean.TRUE);

		return viewName("edit");
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String showSaveForm(Model model, @CurrentUser User user) {
        if (!user.getIsRegister()) return viewName("description");
		
		setCommonData(model);

        Archive archive = getArchiveService().findByUserId(user.getId());
        if (archive == null || !archive.getUserId().equals(user.getId())) {
        	archive = newModel();
            
        	archive.setUserId(user.getId());
        	archive.setMobilePhoneNumber(user.getMobilePhoneNumber());
        	archive.setEmail(user.getEmail());
        }
        
    	model.addAttribute("m", archive);
    	
		return viewName("edit");
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, Archive m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") Archive m, BindingResult result, @CurrentUser User user, RedirectAttributes redirectAttributes) {
		if (hasError(m, result)) {
            return showSaveForm(model, user);
        }

		if (!m.getIsVoyage()) m.setVoyageYear(null);
		
		m.setUserId(user.getId());
		m.setStatus(ArchiveStatus.useredit);
		if (m.getId() != null) {
	        baseService.update(m);
		} else {
	        baseService.save(m);
		}
		
		ArchiveHistory ah = new ArchiveHistory();
		ah.setArchiveId(m.getId());
		ah.setStatus(ArchiveStatus.useredit);
		ah.setReason("本人修改信息");
		archiveHistoryService.save(ah);	
		
		redirectAttributes.addFlashAttribute(Constants.MESSAGE, "专家信息保存成功");
		return redirectToUrl(viewName("edit"));
	}
	
	@RequestMapping(value = "through")
	@ResponseBody
	public AjaxResponse through(@CurrentUser User user){
		AjaxResponse ajaxResponse = new AjaxResponse(Boolean.FALSE, "信息必须先保存才能提交");
		
		try{
			Archive archive = getArchiveService().findByUserId(user.getId());
			if (EmptyUtil.isNotNull(archive)){
				archive.setStatus(ArchiveStatus.submitthrough);
				baseService.update(archive);
				
				ArchiveHistory ah = new ArchiveHistory();
				ah.setArchiveId(archive.getId());
				ah.setStatus(ArchiveStatus.submitthrough);
				ah.setReason("信息已提交后台进行审核");
				archiveHistoryService.save(ah);
				
				ajaxResponse.setSuccess(Boolean.TRUE);
				ajaxResponse.setMessage("你的信息已提交后台进行审核");
			}
		} catch (IllegalStateException e){
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "changeStatus/{isAudit}")
	@ResponseBody
	public AjaxResponse audit(@PathVariable("isAudit")Boolean isAudit, @RequestParam(value = "reason", required=false) String reason, @RequestParam("selections") List<Long> selections){
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			ArchiveHistory ah = null;
			for (Long id : selections) {
				Archive archive = getArchiveService().findOne(id);
				
				ah = new ArchiveHistory();
				ah.setArchiveId(id);
				if (isAudit){
					archive.setStatus(ArchiveStatus.through);
					ah.setStatus(ArchiveStatus.through);
					ah.setReason(ArchiveStatus.through.getInfo());
				} else {
					archive.setStatus(ArchiveStatus.nothrough);
					ah.setStatus(ArchiveStatus.nothrough);
					ah.setReason(reason);
				}
				baseService.update(archive);
				archiveHistoryService.save(ah);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "recycle")
	@ResponseBody
	public AjaxResponse recycle(@RequestParam("selections") List<Long> selections) {
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		AjaxResponse ajaxResponse = new AjaxResponse("还原成功！");
		try{
			for (Long id : selections) {
				Archive archive = getArchiveService().findOne(id);
				archive.setDeleted(Boolean.FALSE);
				getArchiveService().update(archive);
			}
		} catch (IllegalStateException e){
			ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("还原失败了！");
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
	public List<Archive> canUseUser() {
		if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilter("deleted", SearchOperator.EQ, Boolean.FALSE);
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
	}

	/**
	 * 个人打印
	 * 
	 * @param user
	 * @param response
	 */
	@RequestMapping(value = "printCurrentUser")
	public void printCurrentUser(@CurrentUser User user, HttpServletResponse response){
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        
        Long userId = user.getId();
        
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("userId", String.valueOf(userId));
        
        textReportService.buildText(paramMap, 1L, TextReport.Type.PDF, response);
	}
	
	@RequestMapping(value = "querySummary")
	@ResponseBody
	public Map<String, Object> querySummary(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter) {
		return getArchiveService().findTopRowSubmitThroughArchive(user, searchParameter);
	}

	@RequestMapping(value = "printSummary")
	public void printSummary(@CurrentUser User user, HttpServletResponse response){
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        List<UserOrganizationJob> userOrganizationJobs = user.getOrganizationJobs();
        if (!userOrganizationJobs.isEmpty()){
        	for (UserOrganizationJob userOrganizationJob : userOrganizationJobs){
        		Long organizatoinId = userOrganizationJob.getOrganizationId();
        		
        		Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("organization_id", String.valueOf(organizatoinId));
                
                textReportService.buildText(paramMap, 3L, TextReport.Type.PDF, response);
        	}
        }
	}
	
	@RequestMapping(value = "printArchive")
	public void printArchive(@CurrentUser User user, HttpServletResponse response){
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        List<UserOrganizationJob> userOrganizationJobs = user.getOrganizationJobs();
        if (!userOrganizationJobs.isEmpty()){
        	for (UserOrganizationJob userOrganizationJob : userOrganizationJobs){
        		Long organizatoinId = userOrganizationJob.getOrganizationId();
        		
        		Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("organization_id", String.valueOf(organizatoinId));
                
                textReportService.buildText(paramMap, 4L, TextReport.Type.PDF, response);
        	}
        }
	}

	
//	/**
//	 * 根据查询条件打印多个条件
//	 * 
//	 * @param response
//	 */
//	@RequestMapping(value = "printUsers")
//	public void printUsers(@CurrentUser User user, @ModelAttribute SearchParameter<Long> searchParameter, HttpServletResponse response){
//        getArchiveService().covertSearchParameter(user, searchParameter);
//        List<Archive> archives = getArchiveService().findAllWithNoPageNoSort(SearchHelper.parameterConverSearchable(searchParameter, Archive.class));
//        
//        List<Long> archiveIds = Collections3.extractToList(archives, "id");
//        
//		response.setDateHeader("Expires", 0L);
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
//        response.setHeader("Pragma", "no-cache");
//        
//        Map<String, String> paramMap = Maps.newHashMap();
//        paramMap.put("userId", String.valueOf(userId));
//        
//        textReportService.buildText(paramMap, 1L, TextReport.Type.PDF, response);
//	}
}

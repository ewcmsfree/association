package com.ewcms.common.plugin.web.controller;

import com.alibaba.fastjson.JSON;
import com.ewcms.common.Constants;
import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.entity.enums.BooleanEnum;
import com.ewcms.common.plugin.entity.Treeable;
import com.ewcms.common.plugin.service.BaseTreeableService;
import com.ewcms.common.utils.MessageUtils;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.entity.TreeNode;
import com.ewcms.common.web.controller.permission.PermissionList;
import com.ewcms.common.web.validate.AjaxResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wu_zhijun
 */
public abstract class BaseTreeableController<M extends BaseSequenceEntity<ID> & Treeable<ID>, ID extends Serializable> extends BaseController<M, ID> {

    protected BaseTreeableService<M, ID> baseService;

    protected PermissionList permissionList = null;

    @Autowired
    public void setBaseService(BaseTreeableService<M, ID> baseService) {
        this.baseService = baseService;
    }

    /**
     * 权限前缀：如sys:user
     * 则生成的新增权限为 sys:user:create
     */
    public void setResourceIdentity(String resourceIdentity) {
        if (!StringUtils.isEmpty(resourceIdentity)) {
            permissionList = PermissionList.newPermissionList(resourceIdentity);
        }
    }

    protected void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        if (permissionList != null) {
            permissionList.assertHasViewPermission();
        }

        return viewName("index");
    }
    
    @RequestMapping(value = "tree/{checkId}/singleChecked")
    @ResponseBody
    public List<TreeNode<ID>> treeSingleChecked(@RequestParam(value = "id", required = false) ID parentId, @PathVariable(value = "checkId") ID checkId){
    	return baseService.treeSingleChecked(parentId, checkId, null);
    }
    
    @RequestMapping(value = "tree/[{checkIds}]/multipleChecked")
    @ResponseBody
    public List<TreeNode<ID>> treeMultipleChecked(@RequestParam(value = "id", required = false) ID parentId, @PathVariable(value = "checkIds") ID[] checkIds){
    	return baseService.treeMultipleChecked(parentId, checkIds, null);
    }
    
    @RequestMapping(value = "tree")
    @ResponseBody
    public List<TreeNode<ID>> tree(@RequestParam(value = "id", required = false) ID parentId){
    	return baseService.tree(parentId, null, false);
    }
    
    @RequestMapping(value = "table")
    @ResponseBody
    public List<TreeNode<ID>> table(){
    	return baseService.table(null);
    }
    
    @RequestMapping(value = "{parentId}/appendChild")
    @ResponseBody
    public M appendChild(@PathVariable("parentId") M parent, @RequestParam("name") String name) {
        if (permissionList != null) {
            permissionList.assertHasCreatePermission();
        }

        M child = newModel();
        try {
            child.setName(name);
        	baseService.appendChild(parent, child);
        } catch (IllegalStateException e){
        	child = null;
        }
        return child;
    }
    
    //********************树型使用以下几个方法开始*******************************
    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String showSaveForm(@PathVariable("id") M m, Model model, RedirectAttributes redirectAttributes){
    	if (permissionList != null) {
            permissionList.assertHasUpdatePermission();
        }

        if (m == null) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "您修改的数据不存在！");
            return redirectToUrl(viewName("edit"));
        }

        setCommonData(model);
        model.addAttribute("m", m);
        return viewName("edit");
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") M m, BindingResult result, RedirectAttributes redirectAttributes) {
	
	      if (permissionList != null) {
	          permissionList.assertHasUpdatePermission();
	      }
	
	      if (result.hasErrors()) {
	          return showSaveForm(m, model, redirectAttributes);
	      }
	
	      baseService.update(m);
	      redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
	      return redirectToUrl(viewName(m.getId() + "/edit"));
	}    
    //********************树型使用以下几个方法结束*******************************

    //********************树型表格使用以下几个方法开始*******************************
    @RequestMapping(value = "table/save", method = RequestMethod.GET)
	public String showSaveForm(Model model, @RequestParam(required = false) List<ID> selections) {
        setCommonData(model);

        if (!model.containsAttribute("close")){
	        if (selections == null || selections.isEmpty()) {
		        if (permissionList != null) {
		            this.permissionList.assertHasCreatePermission();
		        }
	        	model.addAttribute("operate", "add");
		        
		        if (!model.containsAttribute("m")) {
		            model.addAttribute("m", newModel());
		        }
			} else {
				if (permissionList != null) {
		            this.permissionList.assertHasUpdatePermission();
		        }
				model.addAttribute("operate", "update");
				
				M m = baseService.findOne(selections.get(0));
				model.addAttribute("m", m);
				model.addAttribute("selections", selections);
			}
        }
		return viewName("edit");
	}

	@RequestMapping(value = "table/save", method = RequestMethod.POST)
	public String save(Model model, @Valid @ModelAttribute("m") M m, BindingResult result, @RequestParam(required = false) List<ID> selections) {
		setCommonData(model);

		if (hasError(m, result)) {
            return showSaveForm(model, selections);
        }
		
		if (m.getId() != null && StringUtils.hasText(m.getId().toString())) {
	        if (permissionList != null) {
	            this.permissionList.assertHasUpdatePermission();
	        }
			
	        M lastM = baseService.update(m);
			
			selections.remove(0);
			if (selections == null || selections.isEmpty()) {
				model.addAttribute("close", true);
			}
			model.addAttribute("lastM", JSON.toJSONString(baseService.covertToTreeNode(lastM)));
		} else {
	        if (permissionList != null) {
	            this.permissionList.assertHasCreatePermission();
	        }
			
	        M parent = baseService.findOne(m.getParentId());
			
			M lastM = baseService.appendChild(parent, m);
	        
			model.addAttribute("m", newModel());
			model.addAttribute("lastM", JSON.toJSONString(baseService.covertToTreeNode(lastM)));
		}
		
		return showSaveForm(model, selections);
	}
	
    //********************树型表格使用以下几个方法结束*******************************
    
    @RequestMapping(value = "{id}/delete")
    @ResponseBody
    public AjaxResponse deletedSelfAndChildren(@PathVariable("id") M m){
    	if (permissionList != null) {
            permissionList.assertHasDeletePermission();
        }
    	AjaxResponse ajaxResponse = new AjaxResponse("删除节点成功");
    	
	    try{
	    	baseService.deleteSelfAndChild(m);
	    } catch (IllegalStateException e) {
	    	ajaxResponse.setSuccess(Boolean.FALSE);
	        ajaxResponse.setMessage("删除节点失败");
	    }
    	return ajaxResponse;
    }
    
    @RequestMapping(value = "{id}/rename")
    @ResponseBody
    public AjaxResponse renameSelf(@PathVariable("id") M m, @RequestParam("name") String name){
        if (permissionList != null) {
            permissionList.assertHasUpdatePermission();
        }
        
        AjaxResponse ajaxResponse = new AjaxResponse("");
        
        try{
	    	m.setName(name);
	    	baseService.update(m);
	    	
	    	ajaxResponse.setMessage(m.getName());
        } catch (IllegalStateException e) {
    		ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("重命名节点失败");
    	}
    	return ajaxResponse;
    }
    
    @RequestMapping(value = "{sourceId}/{targetId}/{moveType}/move")
    @ResponseBody
    public AjaxResponse move(@PathVariable("sourceId") M source, @PathVariable("targetId") M target, @PathVariable("moveType") String moveType) {
    	if (this.permissionList != null) {
	    	this.permissionList.assertHasEditPermission();
    	}
	        
	    AjaxResponse ajaxResponse = new AjaxResponse("移动位置成功");
	    
        if (target.isRoot() && !moveType.equals("append")) {
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("不能移动到根节点之前或之后");
        } else {
		    try{
		        baseService.move(source, target, moveType);
	    	} catch (IllegalStateException e){
	    		ajaxResponse.setSuccess(Boolean.FALSE);
	            ajaxResponse.setMessage(MessageUtils.message("move.not.enough"));
	    	}
        }
	    return ajaxResponse;
    }

    @RequestMapping(value = "reweight")
    @ResponseBody
    public AjaxResponse reweight() {

        if (this.permissionList != null) {
            this.permissionList.assertHasEditPermission();
        }

        AjaxResponse ajaxResponse = new AjaxResponse("优化排序成功！");
        try {
        	baseService.reweight();
        } catch (IllegalStateException e) {
            ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("优化排序失败了！");
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value = "description")
    public String description(){
    	return viewName("description");
    }
}
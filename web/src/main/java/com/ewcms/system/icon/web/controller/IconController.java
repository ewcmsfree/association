package com.ewcms.system.icon.web.controller;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.LogUtils;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.controller.entity.TreeIconCls;
import com.ewcms.common.web.upload.FileUploadUtils;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.system.icon.entity.Icon;
import com.ewcms.system.icon.entity.IconType;
import com.ewcms.system.icon.service.IconService;
import com.google.common.collect.Lists;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * @author wu_zhijun
 */
@Controller
@RequestMapping(value = "/system/icon")
public class IconController extends BaseCRUDController<Icon, Long> {

    private IconService getIconService() {
        return (IconService) baseService;
    }

    @Value("${icon.css.file.src}")
    private String iconClassFile;

    public IconController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("system:icon");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", IconType.values());
    }
    
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model) {
		Map<String, Object> parameters = searchParameter.getParameters();
		parameters.put("SUFFIXNOTLIKE_identity", TreeIconCls.tree_suffix);
		searchParameter.setParameters(parameters);
		
		Map<String, Direction> sorts = searchParameter.getSorts();
		sorts.put("identity", Direction.DESC);
		searchParameter.setSorts(sorts);
		
		return super.query(searchParameter, model);
	}
	
		
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, @Valid @ModelAttribute("m") Icon icon, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}
    
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,  @RequestParam(value = "file", required = false) MultipartFile file, Model model, @Valid @ModelAttribute("m") Icon icon, BindingResult result, @RequestParam(required = false) List<Long> selections) {
		if (file != null && !file.isEmpty()) {
            icon.setImgSrc(FileUploadUtils.upload(request, file, result));
        }
		String view = super.save(model, icon, result, selections);
		
		genIconCssFile(request);
		return view;
	}

	@RequestMapping(value = "delete/discard")
	@ResponseBody
	@Override
	public AjaxResponse delete(@RequestParam(required = false) List<Long> selections){
		throw new RuntimeException("discarded method");
	}	
	
    @RequestMapping(value = "delete")
    @ResponseBody
	public AjaxResponse delete(HttpServletRequest request, @RequestParam(required = false) List<Long> selections){
    	AjaxResponse ajaxResponse = new AjaxResponse("删除成功！");
    	
    	try{
	    	List<Long> treeIconIds = Lists.newArrayList();
	    	for (Long selection : selections){
	    		Icon icon = getIconService().findOne(selection);
	    		Icon treeIcon = getIconService().findByIdentity(icon.getIdentity() + TreeIconCls.tree_suffix);
	    		if (treeIcon == null) continue;
	    		treeIconIds.add(treeIcon.getId());
	    	}
	    	selections.addAll(treeIconIds);
	    	
			super.delete(selections);
			genIconCssFile(request);
    	} catch (IllegalStateException e){
        	ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("删除失败了！");
        }
        return ajaxResponse;
	}

    /**
     * 击生成CSS文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/genCssFile")
    @ResponseBody
    public AjaxResponse genIconCssFile(HttpServletRequest request) {
        this.permissionList.assertHasEditPermission();

        String uploadFileTemplate = ".%1$s{background:url('%2$s/%3$s');width:%4$spx;height:%5$spx;display:inline-block;vertical-align: middle;%6$s}";
        String cssSpriteTemplate = ".%1$s{background:url('%2$s/%3$s') no-repeat -%4$spx -%5$spx;width:%6$spx;height:%7$spx;display:inline-block;vertical-align: middle;%8$s}";

        ServletContext sc = request.getServletContext();
        String ctx = sc.getContextPath();

        List<String> cssList = Lists.newArrayList();

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("iconType", SearchOperator.NE, IconType.css_class);
        
        List<Icon> iconList = baseService.findAllWithNoPageNoSort(searchable);

        for (Icon icon : iconList) {
            if (icon.getIconType() == IconType.upload_file) {
                cssList.add(String.format(
                        uploadFileTemplate,
                        icon.getIdentity(),
                        ctx, icon.getImgSrc(),
                        icon.getWidth(), icon.getHeight(),
                        icon.getStyle()));
                
                continue;
            }

            if (icon.getIconType() == IconType.css_sprite) {
                cssList.add(String.format(
                        cssSpriteTemplate,
                        icon.getIdentity(),
                        ctx, icon.getSpriteSrc(),
                        icon.getLeft(), icon.getTop(),
                        icon.getWidth(), icon.getHeight(),
                        icon.getStyle()));
                
                continue;
            }
        }

        AjaxResponse ajaxResponse = new AjaxResponse("生成成功！");
        try {
            ServletContextResource resource = new ServletContextResource(sc, iconClassFile);
            FileUtils.writeLines(resource.getFile(), cssList);
        } catch (Exception e) {
            LogUtils.logError("gen icon error", e);
            ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("生成失败！");
        }

        return ajaxResponse;
    }


    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {
        ValidateResponse response = ValidateResponse.newInstance();

        if ("identity".equals(fieldId)) {
            Icon icon = getIconService().findByIdentity(fieldValue);
            if (icon == null || (icon.getId().equals(id) && icon.getIdentity().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "该标识符已被其他人使用");
            }
        }
        return response.result();
    }
    
    @RequestMapping(value = "treeUseIcon", method = RequestMethod.GET)
    @ResponseBody
    public List<Icon> treeUseIcon(){
    	Searchable searchable = Searchable.newSearchable();
    	
    	searchable.addSearchFilter("identity", SearchOperator.SUFFIXLIKE, TreeIconCls.tree_suffix);
    	
    	Sort.Order idAsc = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(idAsc);
        searchable.addSort(sort);
        
    	return getIconService().findAllWithSort(searchable);
    }
}

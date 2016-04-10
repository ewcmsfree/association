package com.ewcms.personnel.photo.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.ImageUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.personnel.photo.entity.Photo;
import com.ewcms.personnel.photo.service.PhotoService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping(value = "/personnel/photo")
public class PhotoController extends BaseCRUDController<Photo, Long>{
	
	private PhotoService getPhotoService(){
		return (PhotoService) baseService;
	}
	
	@RequestMapping(value = "index/discard")
	@Override
	public String index(Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "query/discard")
	@Override
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, Model model){
		throw new RuntimeException("discarded method");
	}
		
	@RequestMapping(value = "save/discard", method = RequestMethod.GET)
	@Override
	public String showSaveForm(Model model, @RequestParam(required = false) List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "personal/save", method = RequestMethod.GET)
	public String showSaveForm(@CurrentUser User user, Model model){
        setCommonData(model);
        
		Photo photo = getPhotoService().findByUserId(user.getId());
		if (EmptyUtil.isNull(photo)){
			photo = newModel();
			photo.setUserId(user.getId());
		}
        model.addAttribute("m", photo);
        
        return viewName("edit");
	}
	
	@RequestMapping(value = "save/discard", method = RequestMethod.POST)
	@Override
	public String save(Model model, Photo m, BindingResult result, List<Long> selections) {
		throw new RuntimeException("discarded method");
	}

	@RequestMapping(value = "personal/save", method = RequestMethod.POST)
	public String save(@CurrentUser User user, @RequestParam(value = "myUpload") MultipartFile myUpload, Model model, @Valid @ModelAttribute("m") Photo m, BindingResult result) {
		if (hasError(m, result)) {
            return showSaveForm(user, model);
        }
		
		setCommonData(model);
		
	    try {
	    	m.setFormatName(ImageUtil.getFormatName(myUpload));
	    	m.setUserId(user.getId());
			m.setReal(myUpload.getBytes());
			
			if (m.getId() != null && StringUtils.hasText(m.getId().toString())){
				baseService.save(m);
			} else {
				baseService.update(m);
			}
		} catch (IOException e) {
		}
		
	    model.addAttribute("close", Boolean.TRUE);
		return showSaveForm(user, model);
	}
}

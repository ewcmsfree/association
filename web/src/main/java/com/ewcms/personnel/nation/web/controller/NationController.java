package com.ewcms.personnel.nation.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.personnel.nation.entity.Nation;
import com.ewcms.personnel.nation.service.NationService;

@Controller
@RequestMapping(value = "/personnel/nation")
public class NationController extends BaseCRUDController<Nation, Long>{

	private NationService getNationService(){
		return (NationService) baseService;
	}
	
	public NationController() {
		setResourceIdentity("personnel:nation");
	}
	
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();


        if ("name".equals(fieldId)) {
            Nation nation = getNationService().findByName(fieldValue);
            if (nation == null|| (nation.getId().equals(id) && nation.getName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "民族名称已存在");
            }
        }
        return response.result();
    }
    
    @RequestMapping(value = "canUse", method = RequestMethod.GET)
	@ResponseBody
    public List<Nation> canUse(){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(Direction.ASC, "id");

		return baseService.findAllWithSort(searchable);
    }

}

package com.ewcms.personnel.allowance.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.personnel.allowance.entity.Allowance;
import com.ewcms.personnel.allowance.service.AllowanceService;

@Controller
@RequestMapping(value = "/personnel/allowance")
public class AllowanceController extends BaseCRUDController<Allowance, Long>{

	private AllowanceService getAllowanceService(){
		return (AllowanceService) baseService;
	}
	
	public AllowanceController() {
		setResourceIdentity("personnel:allowance");
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();


        if ("name".equals(fieldId)) {
            Allowance allowance = getAllowanceService().findByName(fieldValue);
            if (allowance == null|| (allowance.getId().equals(id) && allowance.getName().equals(fieldValue))) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "津贴名称已存在");
            }
        }

        return response.result();
    }
}

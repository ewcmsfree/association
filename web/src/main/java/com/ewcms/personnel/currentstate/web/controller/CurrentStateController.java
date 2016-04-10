package com.ewcms.personnel.currentstate.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.validate.ValidateResponse;
import com.ewcms.personnel.currentstate.entity.CurrentState;
import com.ewcms.personnel.currentstate.service.CurrentStateService;

@Controller
@RequestMapping(value = "/personnel/currentState")
public class CurrentStateController extends BaseCRUDController<CurrentState, Long>{

	private CurrentStateService getCurrentStateService(){
		return (CurrentStateService) baseService;
	}
	
	public CurrentStateController() {
		setResourceIdentity("personnel:currentState");
	}
	
	@RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {

        ValidateResponse response = ValidateResponse.newInstance();


        if ("name".equals(fieldId)) {
            CurrentState currentState = getCurrentStateService().findByName(fieldValue);
            if (currentState == null|| (currentState.getId().equals(id) && currentState.getName().equals(fieldValue))) {
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "情况名称已存在");
            }
        }

        return response.result();
    }
}

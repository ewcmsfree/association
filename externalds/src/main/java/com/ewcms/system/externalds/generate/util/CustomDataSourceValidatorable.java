package com.ewcms.system.externalds.generate.util;

import org.springframework.validation.Errors;

import com.ewcms.system.externalds.entity.CustomDs;

public interface CustomDataSourceValidatorable {
	void validatePropertyValues(CustomDs ds, Errors errors);
}

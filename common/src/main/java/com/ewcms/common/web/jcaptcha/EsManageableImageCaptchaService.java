package com.ewcms.common.web.jcaptcha;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * @author wu_zhijun
 */
public class EsManageableImageCaptchaService extends DefaultManageableImageCaptchaService {

    public EsManageableImageCaptchaService(com.octo.captcha.service.captchastore.CaptchaStore captchaStore, com.octo.captcha.engine.CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCapcha(String id, String userCaptchaResponse) {
    	try{
    		Captcha captcha = store.getCaptcha(id);
    		return captcha.validateResponse(userCaptchaResponse);
    	} catch (Exception e){
    		return false;
    	}
    }
}

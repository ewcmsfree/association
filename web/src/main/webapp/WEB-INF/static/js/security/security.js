$.security = {
    user : {
        initValidator : function(form) {
            var ajaxCall = {
                "url": ctx + "/security/user/user/validate",
                extraDataDynamic: ['#id'],
                "alertTextLoad": "* 正在验证，请稍等。。。"
            };
            //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
            //不能合并到一个 否则提交表单时有个黑屏阶段
            $.validationEngineLanguage.allRules.ajaxCall = ajaxCall;
            $.validationEngineLanguage.allRules.username = {
                "regex": /^[\u4E00-\u9FA5\uf900-\ufa2d_a-zA-Z][\u4E00-\u9FA5\uf900-\ufa2d\w]{1,19}$/,
                "alertText": "* 2到20个汉字、字母、数字或下划线组成，且必须以非数字开头"
            };
            $.validationEngineLanguage.allRules.mobilePhoneNumber = {
                "regex": /^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/,
                "alertText": "* 手机号错误"
            };
            var validationEngine = form.validationEngine({
    			promptPosition:'bottomRight',
    			showOneMessage: true
    		});
            return validationEngine;
        }
    }
};
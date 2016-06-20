package com.ewcms.system.report.generate.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.data.StaticData;
import com.ewcms.system.report.entity.view.ComponentView;
import com.ewcms.system.report.entity.view.SessionView;
import com.ewcms.system.report.generate.entity.PageShowParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 数据库中的参数值转换成页面对象
 *
 * @author 吴智俊
 */
public class ParamConversionPage {

    /**
     * 把数据库里的参数集合转换成页面显示的记录集
     *
     * @param paramSet 数据库参数集合
     * @return 页面显示记录集
     */
    public static List<PageShowParam> conversion(Set<Parameter> paramSet) {
        List<PageShowParam> pspList = Lists.newArrayList();
        if (paramSet != null && paramSet.size() > 0) {
            for (Parameter param : paramSet) {
                Map<String, String> showValue = Maps.newHashMap();
                if (param.getType() == Parameter.Type.SESSION) {
                    ComponentView componentView = param.getComponentView();
                    if (componentView != null) {
                        SessionView sessionView = (SessionView) componentView;
                        showValue.put("0", sessionView.getName());
                    }
                } else if (param.getType() == Parameter.Type.LIST || param.getType() == Parameter.Type.CHECK) {
                    StaticData staticData = (StaticData) param.getData();
                    if (staticData != null) {
                        showValue = AnalysisUtil.analysis(staticData.getValue());
                    }
                } else if (param.getType() == Parameter.Type.BOOLEAN) {
                    showValue.put("true", "true");
                    showValue.put("false", "false");
                }
                PageShowParam psp = new PageShowParam();

                psp.setParamMap(showValue);
                psp.setCnName(param.getCnName());
                psp.setDefaultValue(param.getDefaultValue());
                psp.setEnName(param.getEnName());
                psp.setId(param.getId());
                psp.setType(param.getType());

                pspList.add(psp);
            }
        }
        return pspList;
    }
}

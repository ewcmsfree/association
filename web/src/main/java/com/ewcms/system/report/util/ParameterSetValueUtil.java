package com.ewcms.system.report.util;

import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.data.SqlData;
import com.ewcms.system.report.entity.data.StaticData;
import com.ewcms.system.report.entity.view.BooleanView;
import com.ewcms.system.report.entity.view.CheckView;
import com.ewcms.system.report.entity.view.DateView;
import com.ewcms.system.report.entity.view.ListView;
import com.ewcms.system.report.entity.view.SessionView;
import com.ewcms.system.report.entity.view.TextView;

public class ParameterSetValueUtil {
	/**
	 * 把页面传入的对象转换成参数对象
	 * 
	 * @param parameter
	 *            参数对象
	 * @param pagesParam
	 *            页面参数对象
	 * @return
	 */
	public static Parameter setParametersValue(Parameter parameter) {
		if (parameter.getType() == Parameter.Type.TEXT) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			TextView tv = new TextView();
			parameter.setData(sd);
			parameter.setComponentView(tv);
		} else if (parameter.getType() == Parameter.Type.BOOLEAN) {
			BooleanView bv = new BooleanView();
			parameter.setData(null);
			parameter.setComponentView(bv);
		} else if (parameter.getType() == Parameter.Type.CHECK) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			CheckView cv = new CheckView();
			parameter.setData(sd);
			parameter.setComponentView(cv);
		} else if (parameter.getType() == Parameter.Type.DATE) {
			DateView dv = new DateView();
			parameter.setData(null);
			parameter.setComponentView(dv);
		} else if (parameter.getType() == Parameter.Type.LIST) {
			StaticData sd = new StaticData();
			sd.setValue(parameter.getValue());
			ListView lv = new ListView();
			parameter.setData(sd);
			parameter.setComponentView(lv);
		} else if (parameter.getType() == Parameter.Type.SESSION) {
			SessionView sv = new SessionView();
			sv.setName(parameter.getValue());
			parameter.setData(null);
			parameter.setComponentView(sv);
		} else if (parameter.getType() == Parameter.Type.SQL) {
			SqlData sd = new SqlData();
			sd.setSqlQuery(parameter.getValue());
			parameter.setData(sd);
			parameter.setComponentView(null);
		}
		return parameter;
	}

}

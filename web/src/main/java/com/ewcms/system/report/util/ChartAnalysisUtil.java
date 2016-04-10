package com.ewcms.system.report.util;

import java.util.Set;
import java.util.StringTokenizer;

import com.ewcms.system.report.entity.Parameter;
import com.google.common.collect.Sets;

/**
 * @author 吴智俊
 */
public class ChartAnalysisUtil {
	
	/**
	 * @param sql
	 * @return
	 */
	public static Set<Parameter> analysisSql(String sql) {
		Set<Parameter> set = Sets.newLinkedHashSet();

		int beginIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < sql.length(); i++) {
			String p = sql.substring(i, i + 1);
			if (p.equals("$")) {
				beginIndex = i;
				continue;
			}
			if (p.equals("}")) {
				endIndex = i;
			}
			if (endIndex > beginIndex + 3) {
				String temp = sql.substring(beginIndex + 3, endIndex);
				StringTokenizer tokenizer = new StringTokenizer(temp, "|", false);
				
				Parameter parameter = new Parameter();
				while (tokenizer.hasMoreElements()) {
					String[] splitValue = tokenizer.nextToken().split("=");
					if (splitValue.length > 0){
						String name = splitValue[0];
						String value = "";
						if (splitValue.length == 2)
							value = splitValue[1];
						
						if (name.toLowerCase().equals("name")){
							parameter.setEnName(value);
						}else if (name.equals("class")){
							parameter.setClassName(value);
						}else if (name.equals("des")){
							parameter.setDescription(value);
						}else if (name.equals("dv")){
							parameter.setDefaultValue(value);
						}
						parameter.setType(Parameter.Type.TEXT);
					}
				}
				beginIndex = endIndex = 0;
				set.add(parameter);
			}
		}
		return set;
	}

	public static void main(String[] args) {
	}
}

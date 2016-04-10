package com.ewcms.system.report.util;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 图形报表工具集
 * 
 * @author 吴智俊
 */
public class ChartUtil {
	public static Map<String, String> getFontNameMap() {
		Map<String, String> fontNameMap = Maps.newTreeMap();

		fontNameMap.put("Monospaced", "Monospaced");
		fontNameMap.put("SansSerif", "SansSerif");
		fontNameMap.put("Serif", "Serif");
		fontNameMap.put("宋体", "宋体");

		return fontNameMap;
	}

	public static Map<Integer, String> getFontStyleMap() {
		Map<Integer, String> fontStyleMap = Maps.newHashMap();

		fontStyleMap.put(java.awt.Font.BOLD, "BOLD");
		fontStyleMap.put(java.awt.Font.ITALIC, "ITALIC");
		fontStyleMap.put(java.awt.Font.PLAIN, "PLAIN");

		return fontStyleMap;
	}

	public static Map<Integer, Integer> getFontSizeMap() {
		Map<Integer, Integer> fontSizeMap = Maps.newTreeMap();

		for (int i = 5; i < 13; i++) {
			fontSizeMap.put(i, i);
		}
		for (int i = 14; i < 30; i+=2){
			fontSizeMap.put(i,i);
		}
		fontSizeMap.put(36, 36);
		fontSizeMap.put(48, 48);
		fontSizeMap.put(72, 72);
		
		return fontSizeMap;
	}

	public static Map<Integer, String> getRotateMap() {
		Map<Integer, String> rotateMap = Maps.newTreeMap();

		rotateMap.put(0, "0°");
		rotateMap.put(30, "30°");
		rotateMap.put(60, "60°");
		rotateMap.put(90, "90°");

		return rotateMap;
	}

	public static Map<Integer, String> getPositionMap() {
		Map<Integer, String> positionMap = Maps.newTreeMap();

		positionMap.put(0, "LEFT");
		positionMap.put(1, "TOP");
		positionMap.put(2, "RIGHT");
		positionMap.put(3, "BOTTOM");

		return positionMap;
	}

	public static Map<Integer, String> getAlignmentMap() {
		Map<Integer, String> alignmentMap = Maps.newHashMap();

		alignmentMap.put(2, "RIGHT");
		alignmentMap.put(3, "LEFT");
		alignmentMap.put(4, "CENTER");

		return alignmentMap;
	}

}

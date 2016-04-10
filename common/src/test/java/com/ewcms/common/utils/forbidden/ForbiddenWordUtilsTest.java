package com.ewcms.common.utils.forbidden;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author wu_zhijun
 *
 */
public class ForbiddenWordUtilsTest {

	@Test
	public void testReplaceWithDefaultMask(){
		String input = "2016年职称英语买答案确保真实！";
		String expected = "2016年***确保真实！";
		String actual = ForbiddenWordUtils.replace(input);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testReplaceWithDefaultMask2(){
		String input = "1964.*学生运动abc";
		String expected = "19***abc";
		String actual = ForbiddenWordUtils.replace(input);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testReplaceWithDefaultMask3(){
		String input = "freenetabc";
		String expected = "***abc";
		String actual = ForbiddenWordUtils.replace(input);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testReplaceWidthDefaultMask4(){
		String input = " 海峰 ";
		String expected = "***";
		String actual = ForbiddenWordUtils.replace(input);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testReplaceWithCustomMask(){
		String input = "2016年职称英语买答案确保真实！";
		String expected = "2016年###确保真实！";
		String actual = ForbiddenWordUtils.replace(input, "###");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testContainsForbiddenWord(){
		String input = "2016年职称英语买答案确保真实！";
		Assert.assertTrue(ForbiddenWordUtils.containsForbiddenWord(input));
	}
	
	
}

package com.ewcms.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ewcms.common.Constants;

/**
 * 通过Shiro Session,根据属性名获取参数值
 * @author 吴智俊
 */
public final class ShiroSessionUtil {
	
	private static Subject getShiroSubject(){
		return SecurityUtils.getSubject();
	}
	
	private static Session getShiroSession(){
		return getShiroSubject().getSession();
	}
	
	public static Long getCurrentSiteId(){
		Session session = getShiroSession();
		Object object = null;
		try{
			object = session.getAttribute(Constants.CURRENT_SITE_ID);
		} catch (NullPointerException e){
			return null;
		} catch (InvalidSessionException e){
			return null;
		}
		if (EmptyUtil.isNull(object)) return null;
		
		Long siteId = null;
		try{
			siteId = (Long) object;
		} catch (ClassCastException e){
		}
		return siteId;
	}
	
	public static String getCurrentUsername(){
		return (String)getShiroSubject().getPrincipal();
	}
}

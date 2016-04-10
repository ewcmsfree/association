package com.ewcms.personnel.photo.web.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.SpringUtils;
import com.ewcms.personnel.photo.entity.Photo;
import com.ewcms.personnel.photo.service.PhotoService;

import freemarker.template.utility.StringUtil;


@WebServlet(urlPatterns = "/archive/photo/*", asyncSupported = true)
public class PhotoServlet extends HttpServlet {

	private static final long serialVersionUID = -8426294600664215583L;

	private static final String DOWNLOAD_TYPE = "application/x-msdownload";
	
	private PhotoService photoService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		photoService = SpringUtils.getBean("photoService");
	}
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.reset();
		response.setHeader("Content-Language", "zh-CN");
		response.setHeader("Cache-Control", "no-store");
		
		// set on cache
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType(DOWNLOAD_TYPE);
		

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		
		if (EmptyUtil.isStringNotEmpty(contextPath)){
			uri = StringUtil.replace(request.getRequestURI(), request.getContextPath(), "", false, true);
		}
		
		String userIdStr = uri.replace("/archive/photo/", "");
		
		Long userId = 0L;
		try{
			userId = Long.parseLong(userIdStr);
		} catch (NumberFormatException e){
		}
		
		ServletOutputStream out = response.getOutputStream();
		try{
			Photo photo = photoService.findByUserId(userId);
			
			if (EmptyUtil.isNotNull(photo) && EmptyUtil.isNotNull(photo.getReal())){
				IOUtils.copy(new ByteArrayInputStream(photo.getReal()), out);
			} else {
				IOUtils.copy(new FileInputStream(request.getServletContext().getRealPath("/WEB-INF/static/image/nopicture.jpg")), out);
			}
	        out.flush();
		} catch (Exception e){
		} finally{
			IOUtils.closeQuietly(out);
		}
	}
}

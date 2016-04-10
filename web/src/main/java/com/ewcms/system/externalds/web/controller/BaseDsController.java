package com.ewcms.system.externalds.web.controller;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.web.controller.BaseController;
import com.ewcms.common.web.controller.permission.PermissionList;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.generate.factory.DataSourceFactoryable;
import com.ewcms.system.externalds.generate.factory.init.EwcmsDataSourceFactory;
import com.ewcms.system.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.system.externalds.service.BaseDsService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/externalds")
public class BaseDsController extends BaseController<BaseDs, Long>{
	
	private PermissionList permissionList = PermissionList.newPermissionList("system:externalds");

	@Autowired
	private BaseDsService baseDsService;
	@Autowired
	private EwcmsDataSourceFactory ewcmsDataSourceFactory;
	
	@RequestMapping(value = "index")
	public String index(){
		permissionList.assertHasViewPermission();
		return viewName("index");
	}
	
	@RequestMapping(value = "isConnect/{id}")
	@ResponseBody
	public AjaxResponse isConnect(@PathVariable("id") Long id){
		EwcmsDataSourceServiceable service = null;
		Connection con = null;
		
		AjaxResponse ajaxResponse = new AjaxResponse(Boolean.FALSE, "测试数据库连接失败,请确认填写的内容正确!");
		
		try{
			BaseDs baseDs = baseDsService.findOne(id);

			DataSourceFactoryable factory = (DataSourceFactoryable) ewcmsDataSourceFactory.getBean(baseDs.getClass());
			service = factory.createService(baseDs);
			con = service.openConnection();
			
			if (!con.isClosed()) {
				ajaxResponse.setSuccess(Boolean.TRUE);
				ajaxResponse.setMessage("测试数据库连接正确,您可以在以后的程序中使用!");
			}
		}catch(Exception e){
		}finally{
			try{
				if (con != null){
					con.close();
					con = null;
				}
			}catch(Exception e){
			}
			try{
				if (service != null){
					service.closeConnection();
					service = null;
				}
			}catch(Exception e){
			}
		}
		return ajaxResponse;
	}
}

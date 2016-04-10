package com.ewcms.system.report.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseCRUDController;
import com.ewcms.common.web.utils.DownloadUtils;
import com.ewcms.system.report.entity.Repository;
import com.ewcms.system.report.service.RepositoryService;

/**
 * @author 吴智俊
 */
@Controller
@RequestMapping(value = "/system/report/repository")
public class RepositoryController extends BaseCRUDController<Repository, Long>{

	@Autowired
	private RepositoryService repositoryService;
	
	public RepositoryController() {
		setResourceIdentity("system:categoryreport");
	}
	
	@RequestMapping(value = "{id}/download")
	public void download(@PathVariable(value = "id") Repository repository ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
		if (EmptyUtil.isNull(repository.getEntity())) {
			response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("下载的文件不存在！");
		} else {
			String type = repository.getType();
			if (type.toLowerCase().equals("pdf")) {
				response.setContentType("application/pdf");
			} else if (type.toLowerCase().equals("png")) {
				response.setContentType("image/png");
			}
			DownloadUtils.download(request, response, repository.getName(), repository.getEntity());
		}
	}

//	@RequestMapping(value = "publish")
//	public @ResponseBody Boolean publish(@RequestParam(value = "selections") List<Long> selections, @CurrentSite Site site) {
//		Boolean result = false;
//		if (selections != null && !selections.isEmpty()) {
//			if (site != null && site.getId() != null){
//				repositoryService.publishRepository(selections, site);
//				result = true;
//			}
//		}
//		return result;
//	}
}

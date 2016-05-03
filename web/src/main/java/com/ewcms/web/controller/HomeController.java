package com.ewcms.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.utils.XMLUtil;
import com.ewcms.extra.push.PushService;
import com.ewcms.personnel.archive.service.ArchiveService;
import com.ewcms.security.resource.entity.Menu;
import com.ewcms.security.resource.service.ResourceService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
public class HomeController {
	
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private PushService pushApiService;
    @Autowired
    private ArchiveService archiveService;

	@RequestMapping(value = "home")
	public String home(@CurrentUser User user, Model model){
        List<Menu> menus = resourceService.findMenus(user);
        model.addAttribute("menus", menus);
        
        pushApiService.offline(user.getId());
        
		Subject subject = SecurityUtils.getSubject();
		model.addAttribute("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername = (String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			model.addAttribute("previousUsername", previousUsername);
		}

		return "home";
	}
	
	@RequestMapping(value = "archiveChart")
	@ResponseBody
	public String archiveChart(HttpServletResponse resp){
		XMLUtil xml = new XMLUtil();
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		Map<String, Long> map = archiveService.archiveChart();
		Iterator<Entry<String, Long>> it = map.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, Long> m = (Map.Entry<String, Long>)it.next();
			String key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", key);
			set.addAttribute("value", total.toString());
			set.addAttribute("color", Integer.toHexString((int) (Math.random() * 255 * 255 * 255)).toUpperCase());
		}
		
		return xml.getXML();
	}
}

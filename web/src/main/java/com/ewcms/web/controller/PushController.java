package com.ewcms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.extra.push.BasePushService;
import com.ewcms.personal.memoranda.service.MemorandaService;
import com.ewcms.personal.message.entity.MsgType;
import com.ewcms.personal.message.service.MsgReceiveService;
import com.ewcms.personal.message.service.MsgSendService;
import com.ewcms.personnel.archive.service.ArchiveService;
import com.ewcms.security.user.entity.User;
import com.ewcms.security.user.service.UserOnlineService;
import com.ewcms.security.user.web.bind.annotation.CurrentUser;

@Controller
public class PushController {

	@Autowired
	private MsgReceiveService msgReceiveService;
	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private MemorandaService memorandaService;
	@Autowired
	private ArchiveService archiveService;
	@Autowired
	private UserOnlineService userOnlineService;
    @Autowired
    private BasePushService basePushService;

    /**
     * 获取页面的提示信息
     * @return
     */
    @RequestMapping(value = "/polling")
    @ResponseBody
    public Object polling(@CurrentUser User user, HttpServletResponse resp) {
        resp.setHeader("Connection", "Keep-Alive");
        resp.addHeader("Cache-Control", "private");
        resp.addHeader("Pragma", "no-cache");

        Long userId = user.getId();
        if(userId == null) {
            return null;
        }
        
        //如果用户第一次来 立即返回
        if(!basePushService.isOnline(userId)) {
            Long unreadMessageCount = msgReceiveService.findUnReadMessageCountByUserId(userId);
            List<Map<String, Object>> notices = msgSendService.findTopRowNoticesOrSubscription(MsgType.NOTICE, 10);
            Long totalArchive = archiveService.totalArchive();
            Integer onlineCount = userOnlineService.countOnline();
            //List<Map<String, Object>> subscriptions = msgSendService.findTopRowNoticesOrSubscription(MsgType.SUBSCRIPTION, 10);
            //List<Map<String, Object>> todos = articleMainService.findBeApprovalArticleMain(userId);
            //List<Map<String, Object>> pops = memorandaService.getMemorandaFireTime(userId, new Date(Calendar.getInstance().getTime().getTime()));
            
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("unreadMessageCount", unreadMessageCount);
            data.put("notices", notices);
            data.put("totalArchive", totalArchive);
            data.put("onlineCount", onlineCount);
            //data.put("subscriptions", subscriptions);
            //data.put("todos", todos);
            //data.put("pops", pops);
            basePushService.online(userId);
            return data;
        } else {
            //长轮询
            return basePushService.newDeferredResult(userId);
        }
    }
}

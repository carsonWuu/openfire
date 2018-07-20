package org.jivesoftware.openfire.plugin.control;

import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.session.Session;

public class PresenceControl {
private PushServer pushServer;
	
	public PresenceControl(PushServer pushServer){
		this.pushServer = pushServer;
	}
	
	
	// 更新客户端出席消息
	public int updatePresenceCache(ReqBean reqBean,Session session,List<GroupBean> grpList){
		// 这里修改为推送到所有的群,并非活动群
		// 直接返回
		
		RecvBean ret = new RecvBean(0,"ok");
		pushServer.recvClientMsg("grp_client_pre", reqBean, ret, session);
		return 0;
	}
}

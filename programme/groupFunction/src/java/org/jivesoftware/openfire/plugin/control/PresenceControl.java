package org.jivesoftware.openfire.plugin.control;

import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.RespBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;

import com.sp.affair.data.analysis.function.factoryInter.Factory;
import com.sp.affair.data.analysis.function.presence.inPresence;

public class PresenceControl {
	private PushServer pushServer;
	
	public PresenceControl(PushServer pushServer){
		this.pushServer = pushServer;
	}
	
	
	// 更新客户端出席消息
	public int updatePresenceCache(ReqBean reqBean,Session session,List<GroupBean> grpList){
		// 这里修改为推送到所有的群,并非活动群
		// 直接返回
		
		Factory presence = new inPresence(reqBean,grpList);
		RecvBean ret  = presence.doAction();
		pushServer.recvClientMsg("grp_client_pre", reqBean, ret, session);
		pushPresenceGroup(reqBean,presence.storeGroup,session);
		return 0;
	}
	public void pushPresenceGroup(ReqBean reqBean,GroupBean groupBean,Session session){
		try {// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setU_id(reqBean.getU_id());
			respBean.setGrp_id(reqBean.getGrp_id());
			
			for(int i = 0;i<groupBean.getUserList().size();i++){
				if(!groupBean.getUserList().get(i).getU_id().equals(reqBean.getU_id())){
					pushServer.pushDetail(groupBean.getUserList().get(i).getU_id(), "grp_server_pre", GsonUtil.gson.toJson(respBean));
				}
			}
			
				
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
	public int userINgroup(String u_id,GroupBean groupBean){//判断用户是否在群组及返回实际位置
		int index = -1;
		List<UserBean> userList = groupBean.getUserList();
		for(int i= 0 ;i <userList.size();i++){
			if(u_id.equals(userList.get(i).getU_id())){
				index = i ;
				return index;
			}
		}
		return index;
	}

}

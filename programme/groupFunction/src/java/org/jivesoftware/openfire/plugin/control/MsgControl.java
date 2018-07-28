package org.jivesoftware.openfire.plugin.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.openfire.plugin.GroupPlugin;
import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.RespBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.db.DBHelper;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;

public class MsgControl {
private PushServer pushServer;
	
	public MsgControl(PushServer pushServer){
		this.pushServer = pushServer;
	}
	
	
	public int pushMsg(ReqBean reqBean,Session session,List<GroupBean> grpList){
		
		RecvBean ret = new RecvBean(0,"ok");
		int ACT = reqBean.getAct();
		
		if(ACT !=1 && ACT !=2)return -1;
		
		
		
		if (1 == ACT) {//群聊
			// 先判断群是否存在
			boolean flag = false;
			GroupBean grpBean = new GroupBean();
			for (int i = 0; i < grpList.size(); i++) {
				if (reqBean.getGrp_id().equals(grpList.get(i).getGrpId())) {
					grpBean = grpList.get(i);
					flag = true;
					break;
				}
			}
			if (flag) {//群组存在，发送消息至群组
				System.out.println(reqBean.getFrom());
				if(userINgroup(reqBean.getFrom(),grpBean)==-1){
					System.out.println("不在组内");
					ret = new RecvBean(8,"该成员不在组内");
					pushServer.recvClientMsg("grp_client_msg", reqBean, ret, session);
					
					return -1;
				}
				pushServer.recvClientMsg("grp_client_msg", reqBean, ret, session);
				pushMsgGroup(reqBean, grpBean,"grp_server_msg");//发送至群组其他人
//				pushServer.recvClientMsg("grp_client_msg", reqBean, grpBean, session,2,"操作群/组ID不存在");
				
			}
			else{//群组不存在
				return 2;
			}
			
			
			
		}
		else if (2 == ACT) {//单聊
			pushServer.recvClientMsg("grp_client_msg", reqBean, ret, session);
			//	单聊,直接推送到个人
			pushMsgSingle(reqBean, "grp_server_msg");
		}
		
		
		return 0;
	}
	
	
	/**
	 * 推送消息到指定人
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushMsgSingle(ReqBean reqBean,String retSubject){
		try {
			// 插入数据库,获取linkid
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("c_user_from", reqBean.getFrom());
			params.put("c_user_to", reqBean.getAct());
			params.put("n_type", reqBean.getType());
			params.put("c_msg", reqBean.getMsg());
			params.put("t_create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			Map<String, Object> msgMap = DBHelper.insertAndReturnID("grp_message_record", params);
			
			String linkId = msgMap.get("id").toString();
			
			// 通知指定用户
			RespBean respBean = new RespBean();
			respBean.setAct(reqBean.getAct());
			respBean.setU_id(reqBean.getU_id());
			respBean.setMaster(reqBean.getMaster());
			respBean.setType(reqBean.getType());
			respBean.setLinkid(Integer.valueOf(linkId));
			respBean.setMsg(reqBean.getMsg());
			respBean.setFrom(reqBean.getFrom());
			respBean.setAlias("");
			respBean.setGrp_id("");
			pushServer.push(reqBean.getU_id(), retSubject, GsonUtil.gson.toJson(respBean));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
	
	
	/**
	 * 推送消息到指定群
	 * @param reqBean 
	 * @param grpBean
	 * @param retSubject
	 */
	public void pushMsgGroup(ReqBean reqBean,GroupBean grpBean,String retSubject){
		try {
			
			// 插入数据库,获取linkid
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("c_grp_id", reqBean.getGrp_id());
			params.put("c_user_from", reqBean.getFrom());
			params.put("c_user_to", reqBean.getU_id());
			params.put("n_type", reqBean.getType());
			params.put("c_msg", reqBean.getMsg());
			params.put("t_create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Map<String, Object> msgMap = DBHelper.insertAndReturnID("grp_message_record", params);
			
			String linkId = msgMap.get("id").toString();
			
			
			// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setAct(reqBean.getAct());
			respBean.setU_id("");
			respBean.setFrom(reqBean.getFrom());
			respBean.setMaster(reqBean.getMaster());
			respBean.setType(reqBean.getType());
			respBean.setLinkid(Integer.valueOf(linkId));
			respBean.setMsg(reqBean.getMsg());
			
			for (UserBean userBean : grpBean.getUserList()) {
				if (!reqBean.getFrom().equals(userBean.getU_id())) {
					pushServer.pushDetail(userBean.getU_id(), retSubject, GsonUtil.gson.toJson(respBean));
				}
			}
//			if (!reqBean.getFrom().equals(grpBean.getMasterId())) {
//				pushServer.pushDetail(grpBean.getMasterId(), "grp_server_msg", GsonUtil.gson.toJson(respBean));
//			}
			
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

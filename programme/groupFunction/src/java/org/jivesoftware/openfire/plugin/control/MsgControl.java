package org.jivesoftware.openfire.plugin.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.openfire.plugin.GroupPlugin;
import org.jivesoftware.openfire.plugin.bean.GroupBean;
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
		if (1 == reqBean.getAct()) {
			// 先判断群是否存在
			boolean flag = true;
			GroupBean grpBean = new GroupBean();
			for (int i = 0; i < grpList.size(); i++) {
				if (reqBean.getGrp_id().equals(grpList.get(i).getGrpId())) {
					grpBean = grpList.get(i);
					flag = false;
					break;
				}
			}
			if (flag) {
//				pushServer.recvClientMsg("grp_client_msg", reqBean, grpBean, session,2,"操作群/组ID不存在");
				return 2;
			}
			
			
			//	群聊
			pushMsgGroup(reqBean, grpBean);
		}else if (2 == reqBean.getAct()) {
			
			//	单聊,直接推送到个人
			pushMsgSingle(reqBean, null);
		}
		
		pushServer.recvClientMsg("grp_client_msg", reqBean, null, session);
		return 0;
	}
	
	
	/**
	 * 推送消息到指定人
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushMsgSingle(ReqBean reqBean,GroupBean grpBean){
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
			pushServer.pushDetail(reqBean.getU_id(), "grp_server_msg", GsonUtil.gson.toJson(respBean));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
	
	
	/**
	 * 推送消息到指定群
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushMsgGroup(ReqBean reqBean,GroupBean grpBean){
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
					pushServer.pushDetail(userBean.getU_id(), "grp_server_msg", GsonUtil.gson.toJson(respBean));
				}
			}
			if (!reqBean.getFrom().equals(grpBean.getMasterId())) {
				pushServer.pushDetail(grpBean.getMasterId(), "grp_server_msg", GsonUtil.gson.toJson(respBean));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
}

package org.jivesoftware.openfire.plugin.control;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.openfire.plugin.GroupPlugin;
import org.jivesoftware.openfire.plugin.bean.GpsBean;
import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.RespBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.db.DBHelper;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;

public class GpsControl {
private PushServer pushServer;
	
	public GpsControl(PushServer pushServer){
		this.pushServer = pushServer;
	}
	
	
	public int getGps(ReqBean reqBean,Session session,List<GroupBean> grpList){
		// 插入数据库
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//反馈给发送方
		RecvBean ret = new RecvBean(0,"ok");
		
		this.pushServer.recvClientMsg("grp_client_gps", reqBean, ret, session);
		// 直接推送到所有有该用户的群/组
		pushGpsGroup(reqBean, grpList,session);
		return 0;
	}
	
	
	
	/**
	 * 推送GPS消息到该活动群
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushGpsGroup(ReqBean reqBean,List<GroupBean> grpList,Session session){
		try {// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setU_id(reqBean.getU_id());
			respBean.setFrom(reqBean.getFrom().split("@")[0]);
			respBean.setGps_list(reqBean.getGps_list());
			
			for(int i = 0;i<grpList.size();i++){
				GroupBean groupBean = grpList.get(i);
				int index = userINgroup(reqBean.getU_id(),groupBean);//用户的位置
				if(index > -1){//存在
					for(int j = 0 ; j< index ;j++){
						pushServer.pushDetail(groupBean.getUserList().get(j).getU_id(), "grp_server_gps", GsonUtil.gson.toJson(respBean));
					}
					
					for(int j = index+1 ; j< groupBean.getUserList().size() ;j++){
						pushServer.pushDetail(groupBean.getUserList().get(j).getU_id(), "grp_server_gps", GsonUtil.gson.toJson(respBean));
					}
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

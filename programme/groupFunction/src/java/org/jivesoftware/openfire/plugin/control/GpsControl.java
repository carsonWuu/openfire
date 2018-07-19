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
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
}

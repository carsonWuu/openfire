package com.sp.affair.data.analysis.function.factoryInter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;

import com.sp.data.data.cacheDATA;
import com.sp.message.returnMessage.returnMessage;

public abstract class Factory {
	protected int act;
	protected String grp_id;
	protected String master;
	protected List<UserBean> u_list = new ArrayList();
	protected cacheDATA data;
	
	protected int type;
	protected int linkid;
	protected String msg;
	protected String u_id;
	
	public static GroupBean storeGroup;//用来保存需要删除的群组信息
	
	public abstract RecvBean doAction();//行为
	
	public void cout(){
		
		int size = this.data.groupandmember.list.size();
		System.out.println("现在成员群组数量："+size);
		System.out.println(this.data.groupandmember.tostring());
		
		
	}
	public static String getTime(){
		String GE="";
		StringBuffer ret=new StringBuffer();
		Calendar now = Calendar.getInstance();
		ret.append(now.get(Calendar.YEAR));
		int temp = now.get(Calendar.MONTH)+1;
		GE= temp>9?String.valueOf(temp):String.valueOf("0"+temp);
		ret.append(GE);
		
		temp = now.get(Calendar.DAY_OF_MONTH);
		GE= temp>9?String.valueOf(temp):String.valueOf("0"+temp);
		ret.append(GE);
		
		temp = now.get(Calendar.HOUR_OF_DAY);
		GE= temp>9?String.valueOf(temp):String.valueOf("0"+temp);
		ret.append(GE);
	
		temp = now.get(Calendar.MINUTE);
		GE= temp>9?String.valueOf(temp):String.valueOf("0"+temp);
		ret.append(GE);
		
		temp = now.get(Calendar.SECOND);
		GE= temp>9?String.valueOf(temp):String.valueOf("0"+temp);
		ret.append(GE);
		
		return ret.toString();
	}
}

package com.sp.data.groupAndmember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupA{
	/*{ID：xxx,userlist:[{type,u_id}]}
	 * 群组信息，ID为群号码，userlist为群成员，其中userlist(type,u_id)type表示群主或是群员，u_id成员id
	 * ID : id9	
	 * userlist : [{type=0, u_id=a10}]
	 */
	public String ID = new String();
	public List<member> userlist = new ArrayList();
	public GroupA(String id,List<member> list){
		this.ID=id;
		this.userlist=list;
	}
	public GroupA addMember(member m){

		/*向群组中添加群组成员userlist
		 * userlist:[{member1},{member2},...]
		 */
	
		this.userlist.add(m);
		
		return this;
	}
}
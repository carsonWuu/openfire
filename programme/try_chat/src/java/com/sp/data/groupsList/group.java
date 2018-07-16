package com.sp.data.groupsList;

public class group {
	public String groupID;
	public String owerID;
	public group(String groupid,String owerid){
		this.groupID = groupid;
		this.owerID = owerid;
		
	}
	@Override 
	public String toString(){
		return "{groupID:"+groupID+", owerID: "+owerID+"}";
	}
}

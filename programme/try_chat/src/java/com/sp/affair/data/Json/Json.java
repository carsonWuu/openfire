package com.sp.affair.data.Json;
import net.sf.json.*;
public class Json {
	private JSONObject json;
	public Json(JSONObject json){
		this.json = json;
	}
	public JSONObject getJson(){
		return this.json;
	}
	
}

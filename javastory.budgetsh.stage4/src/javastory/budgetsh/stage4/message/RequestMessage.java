package javastory.budgetsh.stage4.message;

import com.google.gson.Gson;

public class RequestMessage {
	//
	private String serviceName; 
	private String[] value;
	private String[] remark;
	private String type;
	
	public RequestMessage() {
		//
		value = new String[7];
		remark = new String[2];
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RequestMessage(String serviceName, String value) {
		//
		this();
		this.serviceName = serviceName;
		this.value[0] = value;
	}
	
	public RequestMessage(String serviceName, String value1, String value2) {
		//
		this();
		this.serviceName = serviceName;
		this.value[0] = value1;
		this.value[1] = value2;
	}
	
	public RequestMessage(String serviceName, String value1, String value2,
			int value3, String value4, String value5, String value6, String value7) {
		//
		this();
		this.serviceName = serviceName;
		this.value[0] = value1;
		this.value[1] = value2;
		this.value[2] = String.valueOf(value3);
		this.value[3] = value4;
		this.value[4] = value5;
		this.value[5] = value6;
		this.value[6] = value7;
	}
	
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder();
		
		builder.append("Service name:").append(serviceName);
		builder.append(", vlaue:").append(value);
		builder.append(", remark:").append(remark);
		
		return builder.toString();
	}
	
	public String toJson() {
		// 
		return (new Gson()).toJson(this); 
	}
	
	public static RequestMessage fromJson(String json) {
		// 
		return (new Gson()).fromJson(json, RequestMessage.class); 
	}
	
	public static RequestMessage getSample() {
		// 
		return new RequestMessage("setBaseTime", "10"); 
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getValue() {
		return value[0];
	}
	
	public String[] getValues() {
		return value;
	}

	public void setValue(String value) {
		this.value[0] = value;
	}
	
	public void setValues(String value1, String value2) {
		this.value[0] = value1;
		this.value[1] = value2;
	}

	public String getRemark() {
		return remark[0];
	}
	
	public String[] getRemarks() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark[0] = remark;
	}
	
	public void setRemarks(String remark1, String remark2) {
		this.remark[0] = remark1;
		this.remark[1] = remark2;
	}
}
class ResponseMessage{
	constructor(serviceName, value, success){
		this.serviceName = serviceName;
		this.value = value;
		this.success = success;
		this.reason = '';
	}
	
	toJson(){
		return JSON.stringify(this);
	}
	
	fromJson(json){
		return JSON.parse(json);
	}
}

module.exports = ResponseMessage;
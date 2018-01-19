class RequestMessage{
	constructor(operation, serviceName){
		this.operation = operation;
		this.value = [];
		this.remark = [];
		this.serviceName = serviceName;
	}
	
	toJson(){
		return JSON.stringify(this);
	}
	
	fromJson(json){
		return JSON.parse(json);
	}
}
 
module.exports = RequestMessage;
var localDate = require('js-joda').LocalDate;

class IdName{
	constructor(id, name){
	this.id = id;
	this.name = name;
	}
	idName(){
		console.log('Id: ' + this.id + ', Name: ' + this.name);
	}
}

module.exports = IdName;
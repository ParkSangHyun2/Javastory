const UUID = require('uuid/v4');

class Entity{
	constructor(id){
		if(arguments.length == 1){
			this.id = id;
		}else{
			this.id = UUID();
		}
	}
}
module.exports = Entity;
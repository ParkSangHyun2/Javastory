const UUID = require('uuid/v4');

class Entity{
	constructor(){
		this.id = UUID();
	}
}
module.exports = Entity;
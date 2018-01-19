const Entity = require('../share/Entity.js');

class Travel extends Entity{
	constructor(title){
		super();
		this.club = null;
		this.title = title;
		this.leader = null;
		this.term = null;
		this.participantsCount = null;
		this.budgetGoal = null;
		this.memo = '';
		this.time = '';
	}
	
	travel(){
		console.log('TravelTitle: '+this.title);
	}
}

module.exports = Travel;
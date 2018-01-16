class Item{
	//
	constructor(type, amount){
		this.type = type;
		this.amount = amount;
	}
	
	item(){
		console.log('Type: ' + this.type + ', Amount: ' + this.amount);
	}
}

module.exports = Item;
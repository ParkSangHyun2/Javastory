const MapStorage = require('./MapStorage.js');
const mapStorage = new MapStorage();

class AccountStore{
	constructor(){
	}
	
	exist(account){
		if(mapStorage.accountMap.has(account)){
			return true;
		}
		return false;
	}
	
	regist(yearlyDue){
		if(yearlyDue == null){
			return;
		}
		var id = yearlyDue.member.id;
		
		mapStorage.accountMap.set(id, yearlyDue);
	}
	
	retrieve(account){
		return mapStorage.accountMap.get(account);
	}
	
	update(yearlyDue){
		var id = yearlyDue.member.id;
		mapStorage.accountMap.delete(id);
		mapStorage.accountMap.set(id, yearlyDue);
	}
	
	remove(account){
		mapStorage.accountMap.delete(account);
	}
}

module.exports = AccountStore;
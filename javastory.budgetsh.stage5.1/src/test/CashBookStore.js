const MapStorage = require('./MapStorage.js');
const mapStorage = new MapStorage();

class CashBookStore{
	//
	constructor(){
		
	}
	exist(bankAccount){
		if(mapStorage.cashbookMap.has(bankAccount)){
			return true;
		}else{
			return false;
		}
	}
	
	retrieve(bankAccount){
		return mapStorage.cashbookMap.get(bankAccount);
	}
	
	update(cashbook){
		mapStorage.cashbookMap.delete(cashbook.bankAccount);
		mapStorage.cashbookMap.set(cashbook.bankAccount, cashbook);
	}
	
	remove(cashbook){
		mapStorage.cashbookMap.delete(cashbook.bankAccount);
	}
	
	regist(cashbook){
		mapStorage.cashbookMap.set(cashbook.bankAccount, cashbook);
	}
	
}

module.exports = CashBookStore;
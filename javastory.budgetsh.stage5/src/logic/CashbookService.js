const CashBookStore = require('../store/CashBookStore.js');
const cashBookStore = new CashBookStore();

class CashBookServiceLogic{
	constructor(){
		this.cashbook = null;
	}
	
	exist(bankAccount){
		
		if(cashBookStore.exist(bankAccount)){
			return true;
		}
		return false;
	}
	
	regist(cashbook){
		//
		if(cashbook == null){
			new Error('regist parameter Error');
		}
		cashBookStore.regist(cashbook);
		if(this.exist(cashbook.bankAccount)){
			new Error('regist Error');
		}
	}
	
	retrieve(account){
		//
		this.cashbook = cashBookStore.retrieve(account);
		return this.cashbook;
	}
	
	update(cashbook){
		//
		if(cashbook == null){
			new Error('CashBook update Error');
		}
		cashBookStore.update(cashbook);
	}
	
	remove(cashbook){
		//
		if(cashbook == null){
			new Error('No such CashBook');
		}
		cashBookStore.remove(cashbook);
		if(this.exist(cashbook.bankAccount)){
			new Error('cashbook remove Error');
		}
	}
}

module.exports = CashBookServiceLogic;
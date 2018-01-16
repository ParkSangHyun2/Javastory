class DatePair{
	constructor(startDate, endDate){
		this.startDate = startDate;
		this.endtDate = endDate;
	}
	
	datePair(){
		console.log('StartDate: ' + this.startDate + ', EndDate: '+ this.endtDate);
	}
}
module.exports = DatePair;
class AccountYearlyDue{
	
	constructor(member, year){
		this.member = member;
		this.year = year;
		this.monthlyDues = new Array();
	}
	
	addMonthlyDue(monthlyDue){
		this.monthlyDues.push(monthlyDue);
	}
	
	accountYearlyDue(){
		console.log('MemberId: ' + this.member.id + ', MemberName: ' + this.member.name+', Year: ' + this.year);
	}
}

module.exports = AccountYearlyDue;
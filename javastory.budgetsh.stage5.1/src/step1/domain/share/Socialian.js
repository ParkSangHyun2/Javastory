class Socialian{
	constructor(socialId, firstName, familyName, email){
		this.locale = 'KR';
		this.socialId = socialId;
		this.firstName = firstName;
		this.familyName = familyName;
		this.email = email;
		this.phone = '';
	}
	socialian(){
		console.log('Locale: ' + this.locale + ', socialId: ' +
				this.socialId + ', Name: ' + this.firstName+this.familyName+
				', Email/Phone: ' + this.email + ' / ' + this.phone);
	}
}
module.exports = Socialian;
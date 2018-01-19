//const FileReader = require('fs');
const co = require('co');
const prompt = require('co-prompt');
const FileDbWrapper = require('./FileDbWrapper.js');
const AccountYearlyDue = require('../../../../share/domain/account/AccountYearlyDue.js');
//const path = require('path');
const defaultDelimiter = '||';

const accountFile = new FileDbWrapper('Resource', 'YearlyDue', defaultDelimiter);
const accountTempFile = new FileDbWrapper('Resource', 'YearlyDueTemp', defaultDelimiter);
const keyIndexMap = new Map();
keyIndexMap.set('bankAccount', 0);
keyIndexMap.set('year', 1);
accountFile.keyIndexMap = keyIndexMap;
accountTempFile.keyIndexMap = keyIndexMap;

exports.exist = function(account){
	//
		let found = false;

		let lines = [];
		lines = accountFile.readFile();
		
		let lineSize = lines.length;
		//console.log('values'+ lines[0] + ', Length: ' + lineSize);
		for(var i = 0; i<lineSize; i++){
			if(accountFile.hasValueOf('bankAccount', account, lines[i])){
				found = true;
				break;
			}
		}
		//console.log('!!!!!! : ' + lines[0]);
		return found;
}

exports.regist = function(yearlyDue){
	//
	let data = JSON.stringify(yearlyDue);
	console.log(data);
	accountFile.writeFile(yearlyDue.member.id);
	accountFile.writeFile(defaultDelimiter);
	accountFile.writeFile(yearlyDue.year);
	accountFile.writeFile(defaultDelimiter);
	accountFile.writeFile(data);
	accountFile.writeFile('\r\n');
}

exports.retrieve = function(account){
	let yearlyDue = new AccountYearlyDue();
	let lines = [];
	let json = '';
	lines = accountFile.readFile();
	
	let lineSize = lines.length;
	//console.log('values'+ lines[0] + ', Length: ' + lineSize);
	for(var i = 0; i<lineSize; i++){
		if(accountFile.hasValueOf('bankAccount', account, lines[i])){
			json = lines[i].split(defaultDelimiter)[2]
			yearlyDue = JSON.parse(json);
			break;
		}
	}
	
	return yearlyDue;
}

exports.update = function(yearlyDue){
	let account = yearlyDue.member.id;
	let lines = [];
	let json = '';
	lines = accountFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(accountFile.hasValueOf('bankAccount', account, lines[i])){
			json = JSON.stringify(yearlyDue);	
			accountTempFile.writeFile(yearlyDue.member.id);
			accountTempFile.writeFile(defaultDelimiter);
			accountTempFile.writeFile(yearlyDue.year);
			accountTempFile.writeFile(defaultDelimiter);
			accountTempFile.writeFile(json);
			accountTempFile.writeFile('\r\n');
		}else{
			accountTempFile.writeFile(lines[i]);
			accountTempFile.writeFile('\r\n');
		}
	}
	accountTempFile.writeFile('\r\n');
	accountTempFile.deleteFile(accountFile.getFilePath());
	accountTempFile.rename(accountFile.getFilePath());
}

exports.remove = function(account){
	let yearlyDue = null;
	let lines = [];
	let json = '';
	lines = accountFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(!accountFile.hasValueOf('bankAccount', account, lines[i])){
			accountTempFile.writeFile(lines[i]);
			accountTempFile.writeFile('\r\n');
		}
	}
	accountTempFile.writeFile('\r\n');
	accountTempFile.deleteFile(accountFile.getFilePath());
	accountTempFile.rename(accountFile.getFilePath());
}
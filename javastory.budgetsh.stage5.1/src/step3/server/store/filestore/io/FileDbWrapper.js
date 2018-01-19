const fileStream = require('fs');
const path = require('path');

class FileDbWrapper {
	//
	constructor(folderName, fileName, delimiter) {
		this.rootName = 'resource';
		this.folderName = folderName;
		this.fileName = fileName;
		this.delimiter = delimiter;
		
		this.keyIndexMap = new Map();
		this.lines = [];

	}

	getDirectory() {
		let canonicalPath = path.resolve('../../');
		return canonicalPath + '/' + this.folderName;
	}

	getFilePath() {
		return this.getDirectory() + '/' + this.fileName + '.db';
	}

	readFile() {
		let lines = [];
		let line = '';
		let directory = this.getDirectory();
		let filePath = this.getFilePath();
		
		if(!fileStream.existsSync(directory)){
			fileStream.mkdirSync(directory);
		}
		fileStream.openSync(filePath, 'a+');
		
		line = fileStream.readFileSync(filePath, 'utf8');
		fileStream.closeSync(0);
		
		return lines=line.split('\r\n');
	}

	hasValueOf(key, compare, line) {
		let found = false;
		let values = [];
		values = line.split(this.delimiter);
		if (values[this.keyIndexMap.get(key)] === compare) {
			found = true;
		}
		return found;
	}

	writeFile(data) {
		fileStream.openSync(this.getFilePath(), 'a+');
		fileStream.appendFileSync(this.getFilePath(), data, 'utf8');
		fileStream.closeSync(0);
	}
	
	deleteFile(deletePath){
		fileStream.unlinkSync(deletePath);
	}
	
	rename(newPath){
		fileStream.renameSync(this.getFilePath(), newPath);
	}
}

module.exports = FileDbWrapper;
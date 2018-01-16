const fileStream = require('fs');
const path = require('path');

let data = 'good for you\r\n';
fileStream.openSync('./go', 'a+');
fileStream.appendFileSync('./go', data,'utf8');
fileStream.closeSync(0);
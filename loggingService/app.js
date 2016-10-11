/************************ LOGGINGSERVICE ********************/

var express = require('express');
var app = express();
var http = require('http');
var fs = require('fs');

//maybe for auth???
var jwt = require("express-jwt");

var request = require("request");

//Calls below
app.get('/write-log', function(req, res) {
    var logLevel = req.query.loglevel;
    var message = req.query.message;
    console.log('We have a connection');
    try {
	writeToLog(logLevel, message);
    } catch(err) {
	throw err;
    }
    res.sendStatus(200);
	
    //res.send("Welcome to a palace!");
});

app.get('/read-log', function(req, res) {
    var logLevel = req.query.loglevel;
   
    
    var resultArray = readLog(logLevel);
    console.log('We have a connection');
    res.send(resultArray.toString());
});




app.set('port', process.env.PORT || 8090);
var server = http.createServer(app);
server.listen(app.get('port'), function() {
    fs.writeFile('logfile.log', "", function(err) {
	if(err)
	    throw err;
	console.log("logfile is now empty");
    });
    console.log("Express server listening on port " + app.get('port'));
});




function writeToLog(loglevel, message) {
    fs.appendFile('logfile.log', '[' + loglevel + '] - ' + message + '\n', function(err) {
	if(err) {
	    console.log(err);
	} else {
	    console.log("Wrote loglevel: " + loglevel);
	    console.log("Wrote message: " + message);
	}
    });
}

function readLog(loglevel) {
    var resultArray = [];
    var array = fs.readFileSync('logfile.log').toString();
/*    for(i in array) {
	i += "<br />";
    }*/
    console.log(array);
    return array;
}

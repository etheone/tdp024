/************************ BANKSERVICE ********************/

var express = require('express');
var app = express();
var http = require('http');
var fs = require('fs');

//maybe for auth???
var jwt = require("express-jwt");

var request = require("request");

//Calls below
app.get('/', function(req, res) {
    console.log('We have a connection');
    res.send("Welcome to a place!");
});


app.get('/bank', function(req, res) {
    console.log('Connection to /bank');
    res.send('Banks loaded');
});


app.get('/bank/list', function(req, res) {
    console.log('/bank/list called');
    var obj = JSON.parse(fs.readFileSync('banks.json', 'utf8'));
    res.send(obj);
});






app.get('/bank/find.name', function(req, res) {
    console.log('bank/find.name called');
    var name = req.query.name;
    console.log("The name is: " + name);
    var result = findByName(name);
    res.send(result);
});



app.get('/bank/find.key', function(req, res) {
    console.log('bank/find.key called');
    var key = req.query.key;
    console.log("The key is: " + key);
    var result = findByKey(key);
    res.send(result);
});






app.get('*', function(req, res) {
    console.log('Wildcard caught a connection');
    res.sendStatus(404);
});



app.set('port', process.env.PORT || 8070);
var server = http.createServer(app);
server.listen(app.get('port'), function() {
    console.log("Express server listening on port " + app.get('port'));
});








function findByName(name) {
    console.log("\nfindByName called with: " + name);
    var jsonBanks = JSON.parse(fs.readFileSync('banks.json', 'utf8'));
    var result = [];
    for(var bank in jsonBanks) {
	console.log(jsonBanks[bank]);
	if(jsonBanks[bank].name == name) {
	    result.push(jsonBanks[bank]);
	}
    }
    
    if(result.length == 0) {
	result = 'null';
    }
    
    return result;
}


function findByKey(key) {
    console.log("\nfindByKey called with: " + key);
    var jsonBanks = JSON.parse(fs.readFileSync('banks.json', 'utf8'));
    var result = [];
    for(var bank in jsonBanks) {
	console.log(jsonBanks[bank]);
	if(jsonBanks[bank].key == key) {
	    result.push(jsonBanks[bank]);
	}
    }
    
    if(result.length == 0) {
	result = 'null';
    }
    
    return result;
}

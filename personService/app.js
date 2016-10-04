/************************ PERSONSERVICE ********************/

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


app.get('/person', function(req, res) {
    console.log('Connection to /person');
    res.send('Persons loaded');
});

app.get('/person/list', function(req, res) {
    console.log('/person/list called');
    var obj = JSON.parse(fs.readFileSync('persons.json', 'utf8'));
    res.send(obj);
});

app.get('/person/find.name', function(req, res) {
    console.log('person/find.name called');
    var name = req.query.name;
    console.log("The name is: " + name);
    var result = findByName(name);
    if (result === 'null')
	res.send(result);
    else
	res.send(result[0]);
});



app.get('/person/find.key', function(req, res) {
    console.log('person/find.key called');
    var key = req.query.key;
    console.log("The key is: " + key);
    var result = findByKey(key);
    if (result === 'null')
	res.send(result);
    else
	res.send(result[0]);
});

app.get('*', function(req, res) {
    console.log('Wildcard caught a connection');
    res.sendStatus(404);
});



app.set('port', process.env.PORT || 8060);
var server = http.createServer(app);
server.listen(app.get('port'), function() {
    console.log("Express server listening on port " + app.get('port'));
});



function findByName(name) {
    console.log("\nfindByName called with: " + name);
    var jsonPersons = JSON.parse(fs.readFileSync('persons.json', 'utf8'));
    var result = [];
    for(var person in jsonPersons) {
	console.log(jsonPersons[person]);
	if(jsonPersons[person].fullname == name) {
	    result.push(jsonPersons[person]);
	}
    }
    
    if(result.length == 0) {;
	result = 'null';
    }
    
    return result;
}


function findByKey(key) {
    console.log("\nfindByKey called with: " + key);
    var jsonPersons = JSON.parse(fs.readFileSync('persons.json', 'utf8'));
    var result = [];
    for(var person in jsonPersons) {
	console.log(jsonPersons[person]);
	if(jsonPersons[person].key == key) {
	    result.push(jsonPersons[person]);
	}
    }
    
    if(result.length == 0) {
	result = 'null';
    }
    
    return result;
}


/* var exjson = {'key':'...abc...', 'key2':'...xyz...'};
for(var exKey in exjson) {
      console.log("key:"+exKey+", value:"+exjson[exKey]);
 } */

/* var exjson = {'key':'...abc...', 'key2':'...xyz...'};
if(exjson.hasOwnProperty('key2')){
          //define here
 }
 */


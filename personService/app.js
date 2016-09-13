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

app.get('person/find.name', function(req, res) {
    console.log('person/find.name called');
    
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




function findPersons() {

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


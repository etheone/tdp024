/************************ BANKSERVICE ********************/

var express = require('express');
var app = express();
var http = require('http');

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










app.get('*', function(req, res) {
    console.log('Wildcard caught a connection');
    res.sendStatus(404);
});



app.set('port', process.env.PORT || 8070);
var server = http.createServer(app);
server.listen(app.get('port'), function() {
    console.log("Express server listening on port " + app.get('port'));
});

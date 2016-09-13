/************************ LOGGINGSERVICE ********************/

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




app.set('port', process.env.PORT || 3000);
var server = http.createServer(app);
server.listen(app.get('port'), function() {
    console.log("Express server listening on port " + app.get('port'));
});

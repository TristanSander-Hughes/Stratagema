var express=require("express");
var app=express();
var server=require("http").createServer(app);
var http=require('http');
var os=require("os");
var bodyParser = require('body-parser');
var XMLHttpRequest=require("xmlhttprequest").XMLHttpRequest;
var rp = require('request-promise');
var fs = require('fs') 
var limit=0;
var arr = {};
var gameOver=false;
var migrators=new Array();
var migratorNumber=new Array();
var numMigrants=0;
const MYPORT=4000;
//const SENDPORT=4500;
var timeKeeper;//=setInterval(testFunc,30000);
var numInitialized=0;
var players=[];
var MaxPlayers=2;
var MaxRounds=5;
var RoundLength=20000;
var roundsPlayed=1;
var hostAddress=os.hostname();
var syncReturns;
var conditionCheck;
console.log("Startup successful.")
console.log("Listening on port "+MYPORT);
server.listen(process.env.PORT||MYPORT);


app.use(bodyParser.json()); 
app.post("/",async function(req,res){
	console.log("request received");
	console.log(req.body);
	
	res.json(await handle(req));
	res.end;
});
process.on('SIGTERM',()=>{
	app.close(()=>{
		console.log('Process terminated')
	})
});


function timerFunc()
{
	console.log("Entering timerfunc");
	clearInterval(timeKeeper);
	//console.log("Starting request");
if(roundsPlayed<MaxRounds){

	roundsPlayed++;
  var ip;
  var data="Reproduce";
  syncReturns=0;
  for(i=0; i<players.length; i++)
  {
  	//console.log("entered for branch");
     var options={
    host: players[i].PlayerIP,
    path: '/inbound',
    port: players[i].Port,
    method: 'POST',
    headers:{
    	'Content-Type':'application/text',
    	'Content-Length':Buffer.byteLength(data)
    }
    };
    callback=function(response){
    //respone.on('complete',function(returnData){
      console.log("callback complete");
      syncReturns++;
      if(syncReturns==MaxPlayers)
      {
      	migrate();
  		//timeKeeper=setInterval(timerFunc,RoundLength);
      }
   // });
  }
  console.log(options);
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  
}
	
	//migrate();
  //timeKeeper=setInterval(timerFunc,RoundLength);
}else{
	var maxFitness=players[0].MaxFitness;
	var maxIndex=0;
	for(i=1;i<players.length;i++)
	{
		if(players[i].MaxFitness>maxFitness)
		{
			maxFitness=players[i].MaxFitness;
			maxIndex=i;
		}
	}
	endStateReached(players[maxIndex].PlayerID);
  
  return;

  }

  

}

async function handle(req)
{
  if(req.body.type=="initialize")
  {
  	var ip=(req.headers['x-forwarded-for']||'').split(',').pop().trim()||
  	req.connection.remoteAddress||
  	req.socket.remoteAddress||
  	req.connection.socket.remoteAddress;
    return initialize(req.body,ip);
  }
  if(req.body.type=="lowerTemperature")
  {
    return lowerOpponentTemperature(req.body);
  }
    if(req.body.type=="raiseTemperature")
  {
    return raiseOpponentTemperature(req.body);
  }
    if(req.body.type=="lowerPh")
  {
    return lowerOpponentPh(req.body);
  }
    if(req.body.type=="raisePh")
  {
    return raiseOpponentPh(req.body);
  }
    if(req.body.type=="alterRadiation")
  {
    return alterOpponentRadiation(req.body);
  }
  if(req.body.type=="UpdateOpponentData")
  {
  	return returnOpponentData(req.body);
  }
  if(req.body.type=="UpdateOpponentFitness")
  {
  	return returnOpponentFitness(req.body);
  }
  if(req.body.type=="migrate")
  {
  	return updateMigrators(req.body);
  }
  
}

function initialize(body,ip)
{

  for(i=0; i<players.length; i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      players[i]={
         PlayerID: body.PlayerID,
    PlayerIP: ip,
    PopulationSize: body.PopulationSize,
    MaxFitness: body.MaxFitness,
    MostFitGenotype: body.MostFitGenotype,
    Temperature: body.Temperature,
    pH: body.pH,
    Radiation: body.Radiation,
    Port: body.Port
      };
      console.log(players);
       numInitialized++;
  if(numInitialized==MaxPlayers)
  {	
  		//let migrators=new Map();
  		console.log("Sending request");
		var data="Begin";
		syncReturns=0;
		for(j=0;j<MaxPlayers; j++)
		{
     		var options={
  	  			host: players[j].PlayerIP,
  	  			path: '/inbound',
  	  			port: players[j].Port,
 	   			method: 'POST',
 	   			headers:{
   		 			'Content-Type':'application/text',
   		 			'Content-Length':Buffer.byteLength(data)
  	  				}
  	  			};
  	  		callback=function(response){
  	  		//response.on('complete',function(returnData){
  	    	console.log("callback complete");
  	    	syncReturns++;
  	    	if(syncReturns==MaxPlayers)
  	    	{
  	    		timeKeeper=setInterval(timerFunc,RoundLength);
  	    	}
   	 		//});
  			}
  			console.log(options);
  			var req=http.request(options,callback);
  			req.write(data);
  			req.end();
  		}
  	numInitialized=0;
  	
  	
  }
  
      return "Completed";
    }
  }
  var newPlayer={
    PlayerID: body.PlayerID,
    PlayerIP: ip,
    PopulationSize: body.PopulationSize,
    MaxFitness: body.MaxFitness,
    MostFitGenotype: body.MostFitGenotype,
    Temperature: body.Temperature,
    pH: body.pH,
    Radiation: body.Radiation,
    Port: body.Port
  };
  players.push(newPlayer);
  console.log(players);
  numInitialized++;
  if(numInitialized==MaxPlayers)
  {
  	//let migrators=new Map();
  	syncReturns=0;
  	for(j=0; j<MaxPlayers;j++)
  	{


  		console.log("Sending request");
		var data="Begin";
     	var options={
  	 		 host: players[j].PlayerIP,
  	  		path: '/inbound',
  	  		port: players[j].Port,
 	   		method: 'POST',
 	   		headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  	  		};
  	 		 callback=function(response){
  	 		 //response.on('complete',function(returnData){
  	    	console.log("callback complete");
  	    	syncReturns++;
  	    	if(syncReturns==MaxPlayers)
  	    	{
  	    		timeKeeper=setInterval(timerFunc,RoundLength);
   	 			//});
  	    	}
  		}
  		console.log(options);
  		var req=http.request(options,callback);
  		req.write(data);
  		req.end();
  	}
  	numInitialized=0;
  	
  	
  }
  
  return "Completed";
}
function updateTemperature(body,newTemp)
{
  for(i=0; i<players.length; i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      players[i].Temperature=newTemp;
      return ;
    }
  }
}
function updatepH(body,newpH)
{
  for(i=0;i<players.length; i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      players[i].pH=newpH;
      return;
    }
  }
}
function updateRadiation(body,newRadiation)
{
  for(i=0; i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      players[i].Radiation=newRadiation;
      return;
    }
  }
}
function lowerOpponentTemperature(body)
{
  var ip;
  var port;
  var data="lowerTemperature";
  for(i=0; i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      ip=players[i].PlayerIP;
      port=players[i].Port;
    }
  }
  var options={
    host: ip,
    path: '/inbound',
    port: port,
    method: 'POST',
    headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  };
  callback=function(response){
    //respone.on('complete',function(returnData){
      console.log("LowerTemp complete");
    //});
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  //console.log(body);
  return "Completed";
}
function raiseOpponentTemperature(body)
{
  var ip;
  var PORT;
  var data="raiseTemperature";
  for(i=0; i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      ip=players[i].PlayerIP;
      PORT=players[i].Port;
    }
  }
  var options={
    host: ip,
    path: '/inbound',
    port: PORT,
    method: 'POST',
    headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  };
  callback=function(response){
    //response.on('complete',function(returnData){
      console.log("raiseTemp complete");
    //});
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  return "Completed";
}
function lowerOpponentPh(body)
{
  var ip;
  var PORT; 
  var data="lowerPh";
  for(i=0; i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      ip=players[i].PlayerIP;
      PORT=players[i].Port;
    }
  }
  var options={
    host: ip,
    path: '/inbound',
    port: PORT,
    method: 'POST',
     headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  };
  callback=function(response){
   //response.on('complete',function(returnData){
      console.log("lowerph complete");
    //});
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  return "Completed";
}
function raiseOpponentPh(body)
{
  var ip;
  var PORT;
  var data="raisePh";
  for(i=0;i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      ip=players[i].PlayerIP;
      PORT=players[i].Port;
    }
  }
  var options={
    host: ip,
    path: '/inbound',
    port: PORT,
    method: 'POST',
     headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  };
  callback=function(response){
    //response.on('complete',function(returnData){
      console.log("raiseph complete");
    //});
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  return "Completed";
}
function alterOpponentRadiation(body)
{
  var ip;
  var PORT;
  var data="alterRadiation";
  for(i=0;i<players.length;i++)
  {
    if(players[i].PlayerID==body.PlayerID)
    {
      ip=players[i].PlayerIP;
      PORT=players[i].Port;
    }
  }
  var options={
    host: ip,
    path: '/inbound',
    port: PORT,
    method: 'POST',
     headers:{
   		 		'Content-Type':'application/text',
   		 		'Content-Length':Buffer.byteLength(data)
  	  			}
  };
  callback=function(response){
    //response.on('complete',function(returnData){
      console.log("alterrad complete");
   // });
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();
  return "Completed";
}
function returnOpponentData(body)
{
	var responseObject={
		id:[],
		mostFit:[]
	};
	for(i=0; i<players.length;i++)
	{
		if(players[i].PlayerID!=body.PlayerID)
		{
			responseObject.id.push(players[i].PlayerID);
			responseObject.mostFit.push(players[i].MostFitGenotype);
		}
	}
	return JSON.stringify(responseObject);
}
function returnOpponentFitness(body)
{
	var responseObject={
		id:[],
		fitness:[]
	};
	for(i=0; i<players.length;i++)
	{
		if(players[i].PlayerID!=body.PlayerID)
		{
			responseObject.id.push(players[i].PlayerID);
			responseObject.fitness.push(players[i].MaxFitness);
		}
	}
	console.log(responseObject);
	return JSON.stringify(responseObject);
}
function updateMigrators(body)
{
	for(var i in body.migrantTypes)
	{
		//console.log(i);
		//console.log(Object.keys(body.migrants[i]));
		
		var currType=body.migrantTypes[i];
		var currValue=body.migrantNumbers[i];
		if (migrators.includes(currType))
		{
			migratorNumber[migrators.indexOf(currType)]=migratorNumber[migrators.indexOf(currType)]+currValue;
		}
		else{
			migrators.push(currType);
			migratorNumber.push(currValue);
		}
		numMigrants=numMigrants+currValue;
	}
}

function migrate()
{
	var migratorsPer=numMigrants/MaxPlayers;
	var ip;
	var num;
	syncReturns=0;
  for(i=0;i<players.length;i++)
  {
    
      ip=players[i].PlayerIP;
      PORT=players[i].Port;
    num=0;
    index=0;
    targetIndex=-1;
  

  var data={
  	types: [],
  	number: []
  }
  while(num<migratorsPer)
  {
  	if(migratorNumber[index]!=0)
  	{
  		if(data.types.includes(migrators[index]))
  		{
  			data.number[targetIndex]=data.number[targetIndex]+1;
  			migratorNumber[index]=migratorNumber[index]-1;
  		}else{
  			targetIndex++;
  			data.types[targetIndex]=migrators[index];
  			data.number[targetIndex]=1;
  			migratorNumber[index]=migratorNumber[index]-1;
  		}
  		num++;
  		numMigrants--;

  	}else
  	{
  		index++;
  	}
  }
  var dataString=JSON.stringify(data);
  var options={
    host: ip,
    path: '/inbound',
    port: PORT,
    method: 'POST',
    headers:{
    	'Content-Type':'application/json',
    	'Content-Length':Buffer.byteLength(dataString)
    }
  };

  callback=function(response){
    //response.on('complete',function(returnData){
      console.log("migrate callback complete");
      syncReturns++;
      if(syncReturns==MaxPlayers)
      {
      	timeKeeper=setInterval(timerFunc,RoundLength);
      }
    //});
  }
  
  var req=http.request(options,callback);
  req.write(dataString);
  req.end();
  
}
return "Completed";
}


function endStateReached(winner)
{
  //clearInterval(timeKeeper);
  var ip;
  var data ="GameOver "+winner;
  syncReturns=0;
  for(i=0;i<players.length;i++)
  {
     var options={
    host: players[i].PlayerIP,
    path: '/inbound',
    port: players[i].Port,
    method: 'POST',
    headers:{
    	'Content-Type':'application/json',
    	'Content-Length':Buffer.byteLength(data)
    }

    };
    callback=function(response){
    //response.on('complete',function(returnData){
      console.log("callback complete");
      syncReturns++;
      if(syncReturns==MaxPlayers)
      	endServer();
    //});
  }
  var req=http.request(options,callback);
  req.write(data);
  req.end();

  }
  
  //endServer();
  //process.kill(process.pid,'SIGTERM');

}

function endServer()
{
	 process.kill(process.pid,'SIGTERM');
}
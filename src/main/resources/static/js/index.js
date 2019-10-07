'use strict';

var divhome = document.getElementById("home");
var divcreate = document.getElementById("creategame");
var divjoin = document.getElementById("joingame");
var divplay = document.getElementById("playgame");

divhome.style.display = 'block';
divcreate.style.display = 'none';
divjoin.style.display = 'none';
divplay.style.display = 'none';

function createGame() {
    divhome.style.display = 'none';
    divjoin.style.display = 'none';
    divplay.style.display = 'none';
    divcreate.style.display = 'block';
}

function joinGame2() {
    divhome.style.display = 'none';
    divcreate.style.display = 'none';
    divplay.style.display = 'none';
    divjoin.style.display = 'block';
}

function playGame() {
    divhome.style.display = 'none';
    divcreate.style.display = 'none';
    divplay.style.display = 'block';
    divjoin.style.display = 'none';
}

var word = document.getElementById("word");
var number = document.getElementById("number");
var code = document.getElementById("code");
var username = document.getElementById("username");
var letter = document.getElementById("letter");

var stompClient = null;
var connected = false;

document.getElementById("connect").addEventListener('click', connect, true);
document.getElementById("join").addEventListener('click', joinGame, true);


// this function is called after the click on button "Create game"
function connect(event) {
  if (event) {
    event.preventDefault();
  }
  console.log("nesto");
  if (word.value && number.value) {
      console.log("rijec");
    var socket = new SockJS('/ws');
    stompClient = window.Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, onConnected, onError);
  }
}

// after the connection is established 
function onConnected() {
  connected = true;
  console.log("rijecnova");

  // subscribe to the Games topic
  stompClient.subscribe('/topic/new', onGameCreated);

  // we are telling the server our hangman word and number of players
  stompClient.send("/app/hangman/addGame",
      {},
      JSON.stringify({word: word.value, numberOfPlayers: number.value})
      );

  // logArea.classList.add('hidden');
}

function onGameCreated(payload) {
    console.log("uslo ovdje");
    var json = JSON.parse(payload.body);
    console.log(json);
    divcreate.style.display = 'none';
    divjoin.style.display = 'none';
    divplay.style.display = 'none';
    divhome.style.display = 'block';
}


function onError(error) {
  console.log(error);
  connected = false;
  setTimeout(connect, 1000);

  console.log('Not connected. Connecting ...');
}

function joinGame(event) {
  if (event) {
    event.preventDefault();
  }
  
  if (username.value && code.value) {
    console.log("uslo u joinGame");
    var socket = new SockJS('/ws');
    stompClient = window.Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, onConnectedJoinGame, onError);
  }
}

function onConnectedJoinGame() {
  connected = true;
  
  console.log("uslo u onconnectedjoingame");
  
  //Ovdje stavimo key koji player ukuca da saznamo kojoj igri pripada
  stompClient.subscribe('/topic/player'+code.value, onPlayerJoined);

  // Tell your username to the server
  stompClient.send("/app/hangman/findGame",
      {},
      JSON.stringify({username:username.value, playerCode: code.value})
      );
}

function onPlayerJoined(payload) {
    
  divcreate.style.display = 'none';
  divjoin.style.display = 'none';
  divplay.style.display = 'block';
  divhome.style.display = 'none';
  
  var user = JSON.parse(payload.body);
  console.log(user);
  
  var object = {'username': user.username, 'gameCode': user.gameCode, 'playerCode': user.playerCode};
  
  var gameCode = user.gameCode;
  
  localStorage.setItem('user', object);
  
  // subscribing on room with received game code
  stompClient.subscribe('/topic/join'+gameCode, onGameEntered);

  // Tell your username to the server
  stompClient.send("/app/hangman/joinGame",
      {},
      JSON.stringify({username:username.value, playerCode: code.value, gameCode: gameCode})
      );
}

function onGameEntered(payload) {
    var game = JSON.parse(payload.body);
    
    console.log("game joined");
    console.log(game);
}
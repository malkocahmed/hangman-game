'use strict';

var word = document.getElementById("word");
var number = document.getElementById("number");

var stompClient = null;
var username = null;
var connected = false;

document.getElementById("connect").addEventListener('click', connect, true);

// this function is called after the click on button "Create game"
function connect(event) {
  if (event) {
    event.preventDefault();
  }
  console.log("nesto");
  if (true) {
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
    window.location.href = "index.html";
}


function onError(error) {
  console.log(error);
  connected = false;
  setTimeout(connect, 1000);

  logArea.textContent = 'Not connected. Connecting ...';
  logArea.classList.remove('hidden');
}

function joinGame(event) {
  if (event) {
    event.preventDefault();
  }
  username = document.querySelector('#name').value.trim();
  if (username) {
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    // var socket = new SockJS('/ws');
    stompClient = window.Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, onConnectedJoinGame, onError);
  }
}

//Ovo se poziva na click new game
function onConnectedJoinGame() {
  connected = true;
  var word = document.getElementById("word");
  var username = document.getElementById("username");
  //Subscribe to the Public Topic
  //Ovdje stavimo key koji player ukuca da saznamo kojoj igri pripada
  stompClient.subscribe('/topic/player'+word.value, onPlayerJoined);

  // Tell your username to the server
  stompClient.send("/app/memory/findGame",
      {},
      JSON.stringify({userCode:word.value, username: username.value})
      );
  logArea.classList.add('hidden');
}

function onPlayerJoined(payload) {
  var user = JSON.parse(payload.body);
  console.log(user);
  
  var gameCode = user.gameCode;
  
  // subscribing on room with received game code
  stompClient.subscribe('/topic/join/'+gameCode, onGameEntered);

  // Tell your username to the server
  stompClient.send("/app/memory/joinGame",
      {},
      JSON.stringify({userCode:word.value, username: username.value})
      );
  logArea.classList.add('hidden');
}

function onGameEntered(payload) {
    var game = payload.body;
    
    console.log("game joined");
    console.log(game);
}



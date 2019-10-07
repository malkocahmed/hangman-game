/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';

var word = document.getElementById("word");
var number = document.getElementById("number");

var stompClient = null;
var username = null;
var connected = false;

document.getElementById("join").addEventListener('click', join, true);


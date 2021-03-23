var stompClient = null;
var uuid = "";
var lastMessage = null;

window.addEventListener("beforeunload", function(e){
    deleteAll();
}, false);

window.onbeforeunload = function(){
    deleteAll();
}

document.addEventListener("DOMContentLoaded", function(){
    connect();
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    uuid = uuidv4();
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showGreeting(message.body);
        });
        sendConnected();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendConnected() {
    stompClient.send("/app/connect", {}, JSON.stringify({
        'id': 0,
        'quantity': 0,
        'name': ""}));
}

function sendName() {
    stompClient.send("/app/item", {}, JSON.stringify({
        'id': uuid,
        'quantity': parseInt($("#quantity").val()),
        'name': $("#message").val()}));
}

function deleteAll() {
    stompClient.send("/app/item", {}, JSON.stringify({
        'id': uuid,
        'quantity': 0,
        'name': "deleteall"}));
}

function showGreeting(message) {

    if(lastMessage !== message) {
        lastMessage  = message;
        $("#objText").text(message);
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
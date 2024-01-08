'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
let avatars = {};
var colors = [
    `#FFD1DC`, `#92A8D1`, `#FED7C3`, `#D7BEF8`, `#98DDCA`,
    `#FFF9B0`, `#CD5B45`, `#D3D3D3`, `#00CED1`, `#CD853F`
];

let button1 = document.getElementById("changeAvatar1");
let button2 = document.getElementById("changeAvatar2");
let button3 = document.getElementById("changeAvatar3");

var a;
var b;

button1.addEventListener('click', function () {
    a= "https://img.freepik.com/free-psd/3d-render-avatar-character_23-2150611737.jpg?w=740&t=st=1704632816~exp=1704633416~hmac=ac10f5d88ae056154dabd4e6c29d3c3d88daee7c9287f868e7b610ecb9b3b2d5"

});
button2.addEventListener('click', function () {
    a="https://img.freepik.com/free-psd/3d-render-avatar-character_23-2150611731.jpg?w=740&t=st=1704632784~exp=1704633384~hmac=4bcd8f694bcd04039f823343ed42549b90ac056e9367740677e9d223ecabf9d0"
});
button3.addEventListener('click', function () {
a = "https://img.freepik.com/free-psd/3d-render-avatar-character_23-2150611716.jpg?size=626&ext=jpg&ga=GA1.1.1675606772.1704632662"
});


function changeBackground1() {
    document.body.className = "";
    document.body.classList.add("changeBG1");
    console.log(document.querySelector(".changeBG1").style.background);
}

function changeBackground2() {
    document.body.className = "";
    document.body.classList.add("changeBG2");

}

function changeBackground3() {
    document.body.className = "";
    document.body.classList.add("changeBG3");
}

function connect(event) {
    username = document.querySelector('#name').value.trim();
    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
    return false;
}


function onConnected() {
    setAvatar(username, a)
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({
        sender: username,
        type: 'JOIN'
    }))
    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var censoredContent = messageContent.replace(/kurwa/gi, '*****');
        var formattedContent = censoredContent.replace(/(.{1,40}\b)/g, '$1\n');
        var chatMessage = {
            sender: username,
            content: formattedContent,
            type: 'CHAT',
            avatarUrl: a
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if(message.type !== null) {
        var messageElement = document.createElement('li');
        if (message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined the chat!';

    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left the chat!';
    } else {
        messageElement.classList.add('chat-message');
        var avatarElement = document.createElement('i');
        avatarElement.style.width = "50px";
        avatarElement.style.height = "50px";
        if(message.avatarUrl===null) {
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style.display = 'flex';
            avatarElement.style.justifyContent = 'center';
            avatarElement.style.alignItems = 'center';
            avatarElement.style['background-color'] = getAvatarColor(message.sender);

        } else{
            avatarElement.style.backgroundSize = 'cover';
            avatarElement.style.backgroundImage = 'url(' + message.avatarUrl + ')';
        }
        messageElement.appendChild(avatarElement);
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function setAvatar(username, url) {
    stompClient.send("/app/avatar.sendAvatar", {}, JSON.stringify({
        sender: username,
        avatarUrl: url
    }));
}



usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

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
    a = "https://img.freepik.com/premium-vector/woman-avatar-profile-round-icon_24640-14047.jpg?size=338&ext=jpg&ga=GA1.1.1546980028.1703808000&semt=ais";
});
button2.addEventListener('click', function () {
    a = "data:image/png;base64,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";
});
button3.addEventListener('click', function () {

    a = "data:image/jpeg;base64,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";
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
            message.content = message.sender + ' joined!';

    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');
        var avatarElement = document.createElement('i');
        if(message.avatarUrl===null) {
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
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

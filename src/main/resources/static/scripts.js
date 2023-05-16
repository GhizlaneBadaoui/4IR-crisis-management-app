var stompClient = null;
var notificationCount = 0;

$(document).ready(function() {
    console.log("app page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

//    $("#send-private").click(function() {
//        sendPrivateMessage();
//    });
//
//    $("#notifications").click(function() {
//        resetNotificationCount();
//    });
});

function getInfo(id){

    let Info = document.getElementById(id).value;

    console.log("from get info j'ai récupéré : "+Info);

    return Info;

}


/*function checkPassword( emailUser,givenPassword){


    console.log("On va faire le fetch avec "+emailUser);
    return fetch("http:/connexion/${password}/${email}/localhost:8080", {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    })
        .then(response => response.json())
        .then(data => {
            console.log(data); // Afficher la réponse du serveur dans la console
            return data;
        })
        .catch(error => {
            console.error(error);
            return false;
        });


}*/

function connect() {
   /* var socket = new SockJS('/our-websocket');

    let Username = getInfo("email");
    let Password = getInfo("password");


    if(Username =="" || Username == null || Password == "" || Password == null){
        alert("No Username or Password has been write");
        return;

    }

    if(!(checkPassword(Username,Password))){
        alert("L'identifiant ou le mot de passe est incorrect");
        return;
    }*/

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/topic/global-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });

        stompClient.subscribe('/user/topic/private-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
    });
    window.location='alert-interface.html';

}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    updateNotificationDisplay();
}

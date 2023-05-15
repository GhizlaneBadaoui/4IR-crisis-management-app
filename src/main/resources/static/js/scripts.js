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

    return Info;

}


function checkPassword( emailUser,givenPassword){
 let API_URL = "http://localhost:8080";

    fetch(API_URL+"/connexion",{
        method: 'GET',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({                 // Ici on crée un objet anonyme car avec le fetch on ne peut envoyer qu'une seule chose dans le body
            Mail: emailUser,
            Password: givenPassword


        }),
    }).then(function(response){

        if(response.status == 200){
            let myStorage = localStorage;
            let Token = JSON.parse(response.body);
            console.log(Token.Token);
            myStorage.setItem(Token);
            document.location.assign("http://127.0.0.1:5500/src/acceuilAdmin.html");

        }
        else if(response.status == 500){
            alert("Internal serveur error");
            return;

        }
        else if(response.status == 404){
            alert("Wrong mail or password");
            return;

        }
        else if(response.status == 401){
            alert("Wrong mail or password");
            return;

        }

    }).catch(function(error){
        console.log(error);
        alert("Internal servor issue");
    })

}

function connect() {
    var socket = new SockJS('/our-websocket');

    let Mail= getInfo("email");
    let Password = getInfo("password");


    if(Username =="" || Username == null || Password == "" || Password == null){
        alert("No Username or Password has been write");
        return;

    }













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

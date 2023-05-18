var stompClient = null;
var notificationCount = 0;

$(document).ready(function() {
    console.log("app page is ready");
   // connect();

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

    let responseFunction;
    console.log("On va faire le fetch avec "+emailUser);
    fetch("http:/connexion/${password}/${email}/localhost:8080", {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    })
        .then(function(response){

            if(response.status == 200){
                console.log("on a une réponse du serveur ");
                response.json().then(function(responseJSON){
                    responseFunction = responseJSON;
                })

            }

            if(response.status == 404){
                console.log("Aucune réponse du serveur");
                responseFunction = false;

            }

        })
        .catch(error => {
            console.log("On est dans erreur du fetch");
            console.error(error);
            responseFunction = false;
        });

    return responseFunction;


}
 */

function checkPassword(emailUser, givenPassword) {
    console.log("On va faire le fetch avec " + emailUser);

    return new Promise((resolve, reject) => {
        fetch(`http:/connexion/${givenPassword}/${emailUser}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        })
            .then(response => {
                if (response.status == 200) {
                    console.log("On a une réponse du serveur");
                    response.json().then(responseJSON => {
                        resolve(responseJSON);
                    });
                }
                if (response.status == 404) {
                    console.log("Aucune réponse du serveur");
                    resolve(false);
                }
                if (response.status == 500) {
                    console.log("Problème du serveur");
                    resolve(false);
                }
            })
            .catch(error => {
                console.log("On est dans erreur du fetch");
                console.error(error);
                resolve(false);
            });
    });
}

async function login(){

    let Username = getInfo("email");
    let Password = getInfo("password");


    if(Username =="" || Username == null || Password == "" || Password == null){
        alert("No Username or Password has been write");
        return;

    }

    let response = await checkPassword(Username,Password);
    console.log("La reponse du fetch est : "+response);
    if(response){
        alert("Connexion");
        window.location='alert-interface.html';
        connect();
        return;
    }
    else{
       alert("Mauvais identifiant ou mot de passe ")
        return;
    }


}

function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            console.log("Rock and roll baby");
            console.log("message received-->"+message.body);
            showMessage(JSON.parse(message.body).content);
        });

 //       stompClient.subscribe('/user/topic/private-messages', function (message) {
//         showMessage(JSON.parse(message.body).content);
//        });
//
 //       stompClient.subscribe('/topic/global-notifications', function (message) {
 //           console.log("Received it from global-notifications:"+message.body);
 //           notificationCount = notificationCount + 1;
 //           updateNotificationDisplay();
  //      });
//
//        stompClient.subscribe('/user/topic/private-notifications', function (message) {
//            notificationCount = notificationCount + 1;
//            updateNotificationDisplay();
//        });
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
    if ("Notification" in window) {
      console.log("Notification pris en compte");
      if(Notification.permission!=="denied"){
        //permission n'a pas été garantie
        Notification.requestPermission().then(function (permission){
            if(permission=="granted"){
            //permission accordée
                afficherNotification("Alerte!",{body:message});
            }
        });
      }
      else if(Notification.permission==="granted"){
        afficherNotification("Alerte!",{body:message});
      }

    } else {
      console.log("Les notifications ne sont pas prises en charge par ce navigateur.");
    }
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
function afficherNotification(titre,options){
    var notification=new Notification(titre,options);
}
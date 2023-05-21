var stompClient = null;
var responseCount = 0;


$(document).ready(function() {
    console.log("app page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

  $("#send-private").click(function() {
       sendPrivateMessage();
  });

    $("#notifications").click(function() {
        resetNotificationCount();
   });
});

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

function connect() {
    console.log("lets connect to web socket")
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
    console.log("end of connection function")
}


function showMessage(message) {
    $("#messages").append("<tr>"
                           + "<td>" + message.alertType + "</td>"
                           + "<td>" + (new Date).getDate()+'/'+(new Date).getMonth()+'/'+(new Date).getFullYear()+ ' ' +(new Date).getHours() + ':' +(new Date).getMinutes()+ "</td>"
                           + "<td>" + message.location + "</td>"
                           + "<td>" + responseCount + "</td>"
                           + "</tr>");

    if ("Notification" in window) {
      console.log("Notification pris en compte");
      if(Notification.permission!=="denied"){
        //permission n'a pas été garantie
        Notification.requestPermission().then(function (permission){
            if(permission=="granted"){
                //permission accordée
                afficherNotification("Alerte !! ", {body:"Type: "+message.alertType+ "\nDétail: " +message.content+ " \nLieu: " + message.location});
            }
        });
      }
      else if(Notification.permission==="granted"){
        afficherNotification("🚨 Alerte ! ", {body:"Type: "+message.alertType+ "\nDétail: " +message.content+ " \nLieu: " + message.location});
      }

    } else {
      console.log("Les notifications ne sont pas prises en charge par ce navigateur.");
    }
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#content").val(),
                                                         'location': $('#location :selected').text(),
                                                         'alertType': $('#alertType :selected').text()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}

function afficherNotification(titre,options){
    var notification = new Notification(titre,options);
}
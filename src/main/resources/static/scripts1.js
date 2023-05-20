var stompClient = null;
var responseCount = 0;


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

//function sendPrivateMessage() {
//    console.log("sending private message");
//    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
//}

function afficherNotification(titre,options){
    var notification = new Notification(titre,options);
}
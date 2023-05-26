var stompClient = null;

$(document).ready(function() {
    console.log("app page is ready");
});

function getInfo(id){
    let Info = document.getElementById(id).value;
    console.log("from get info j'ai récupéré : "+Info);
    return Info;
}

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
    });
}
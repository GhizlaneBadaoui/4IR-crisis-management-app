document.addEventListener("DOMContentLoaded", function() {
  var table = document.getElementById("table-messages");
  var rows = table.getElementsByTagName("tr");
  var notificationDiv = document.getElementById("notification");
  var safe = document.getElementById("safe");
  var notSafe = document.getElementById("notSafe");

  for (var i = 0; i < rows.length; i++) {
    rows[i].addEventListener("click", function() {
      var rowData = [];
      var cells = this.getElementsByTagName("td");
      for (var j = 0; j < cells.length; j++) {
        rowData.push(cells[j].textContent);
      }

      var alertDiv = document.createElement("div");
      alertDiv.textContent = "Détail de l'alerte: " + rowData[1];
      var locationDiv = document.createElement("div");
      locationDiv.textContent = "Lieu: " + rowData[2];
      notificationDiv.innerHTML = "";
      notificationDiv.appendChild(alertDiv);
      notificationDiv.appendChild(locationDiv);

      safe.disabled = false;
      notSafe.disabled = false;
      selectedRow = Array.prototype.indexOf.call(rows, this);


      safe.addEventListener("click", function() {
         updateResponse(selectedRow, "Pas en danger");
         safe.disabled = true;
         notSafe.disabled = true;
         notificationDiv.textContent = "";
      });

      notSafe.addEventListener("click", function() {
        updateResponse(selectedRow, "En danger");
        safe.disabled = true;
        notSafe.disabled = true;
        notificationDiv.textContent = "";
      });
    });
  }

  function updateResponse(rowNumber, response) {
      var responseCell = rows[rowNumber].getElementsByTagName("td")[3];
      responseCell.textContent = response;
  }

});
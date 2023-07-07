let tg = window.Telegram.WebApp;

let table = document.createElement("table");

let tHead = document.createElement("thead");
let tBody = document.createElement("tbody");

let tableDiv = document.getElementById("table_div");

let data = tg.initDataUnsafe.user.id;


tableDiv.appendChild(table);

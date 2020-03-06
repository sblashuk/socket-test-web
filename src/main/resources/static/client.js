var ws = null;
var url = "ws://127.0.0.1:8080/client";

function connect() {
    ws = new WebSocket(url);
    ws.onopen = () => {

    };

    ws.onmessage = event => {
        console.log(event);
    };

    ws.onclose = event => {

    };
}

function send(guid) {
    if (ws != null) {
        var message = document.getElementById('message-' + guid).value;
        addHistoryItem(guid, message);
        ws.send(message);
    } else {
        alert('connection not established, please connect.');
    }
}

function addHistoryItem(guid, message) {
    const historyRecord = document.createElement('a');
    historyRecord.appendChild(document.createTextNode(message));
    historyRecord.className = 'list-group-item';

    document.getElementById('history-' + guid).after(historyRecord);
}
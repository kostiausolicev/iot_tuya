function drawCreateRoom() {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="room_name" name="room_name" required><br>
    `;
}

function parseRoomToJson() {
    const name = document.getElementById('room_name').value;
    const formData = {
        name: name
    };
    return JSON.stringify(formData);
}
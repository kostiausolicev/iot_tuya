function drawCreateDevice() {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="home_name" name="home_name" required><br>

            <label for="home_address">Address:</label>
            <input type="text" id="home_address" name="home_address" required><br>
    `;
}

function parseDeviceToJson() {
    const name = document.getElementById('home_name').value;
    const address = document.getElementById('home_address').value;
    const formData = {
        name: name,
        address: address
    };
    return JSON.stringify(formData);
}
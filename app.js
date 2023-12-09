let tg = window.Telegram.WebApp;

tg.expand(); //расширяем на все окно

tg.MainButton.setText("Отправить данные");
tg.MainButton.enable()
tg.MainButton.show()

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

let formType = getParameterByName('formType');
let obj = getParameterByName('obj');

if (formType === 'auth') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
    `;
}
else if (formType === 'register') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br>

            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <label for="confirm_password">Repeat password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required><br>
    `;
}
else if (formType === 'create' && obj === 'home') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="home_name" name="home_name" required><br>

            <label for="home_address">Address:</label>
            <input type="text" id="home_address" name="home_address" required><br>
    `;
}
else if (formType === 'create' && obj === 'room') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="name">Name:</label>
            <input type="text" id="room_name" name="room_name" required><br>
    `;
}
else if (formType === 'create' && obj === 'device') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="tuya_id">TuyaID:</label>
            <input type="text" id="tuya_id" name="tuya_id" required><br>

            <label for="device_name">Name:</label>
            <input type="text" id="device_name" name="device_name" required><br>
            
            <label for="home_id">HomeID:</label>
            <input type="text" id="home_id" name="name" required><br>
            
            <label for="room_id">RoomID:</label>
            <input type="text" id="room_id" name="room_id" required><br>
    `;
}
else {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Ты не сюда попал</h1>
    `
}

Telegram.WebApp.onEvent('mainButtonClicked', function(){

    let formDataJSON;
    if (formType === 'register') {
        const name = document.getElementById('name').value;
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirm_password = document.getElementById('confirm_password').value;
        const formData = {
            name: name,
            username: username,
            password: password,
            confirmPassword: confirm_password
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'auth') {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const formData = {
            username: username,
            password: password
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'create' && obj === 'home') {
        const name = document.getElementById('home_name').value;
        const address = document.getElementById('home_address').value;
        const formData = {
            name: name,
            address: address
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'create' && obj === 'room') {
        const name = document.getElementById('room_name').value;
        const formData = {
            name: name
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'create' && obj === 'device') {
        const name = document.getElementById('device_name').value;
        const tuya_id = document.getElementById('tuya_id').value;
        const home_id = document.getElementById('home_id').value;
        const room_id = document.getElementById('room_id').value;
        const formData = {
            tuya_id: tuya_id,
            name: name != null ? name : null,
            home_id: home_id,
            room_id: room_id,
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'update' && obj === 'home') {
        const name = document.getElementById('home_name').value;
        const address = document.getElementById('home_address').value;
        const formData = {
            name: name,
            address: address
        };
        formDataJSON = JSON.stringify(formData);
    }
    tg.sendData(formDataJSON);
    tg.close();
});

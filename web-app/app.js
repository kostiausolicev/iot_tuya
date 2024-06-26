let tg = window.Telegram.WebApp;

tg.expand();

tg.MainButton.setText("Отправить данные");
tg.MainButton.enable()
tg.MainButton.show()

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    const value = decodeURIComponent(results[2].replace(/\+/g, " "));
    // Если значение является строкой в формате JSON, то парсим его
    try {
        return JSON.parse(value);
    } catch (error) {
        return value;
    }
}

let formType = getParameterByName('formType');
let obj = getParameterByName('obj');
let id = parseInt(getParameterByName('id'));

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
            <label for="room_name">Name:</label>
            <input type="text" id="room_name" name="room_name" required><br>
    `;
}
else if (formType === 'create' && obj === 'device') {
    document.getElementById('dynamicFormContainer').innerHTML = `
        <h1>Введите данные:</h1>
        <label for="tuya_id">TuyaID:</label>
        <input type="text" id="tuya_id" name="tuya_id"><br>

        <label for="device_name">Name:</label>
        <input type="text" id="device_name" name="device_name"><br>
    `;
}

else if (formType === 'send_command') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
                        
            <label for="switch_led">SWITCH_LED:</label>
            <input type="checkbox" id="switch_led_check" name="switch_led_check">
            <input type="text" placeholder="true or false" id="switch_led" name="switch_led"><br>
            
            <input type="checkbox" id="temperature_check" name="temperature_check">
            <label for="temperature">TEMPERATURE:</label>
            <input type="text" placeholder="0-1000" id="temperature" name="temperature"><br>
            
            <input type="checkbox" id="brightness_check" name="brightness_check">
            <label for="brightness">BRIGHTNESS:</label>
            <input type="text" placeholder="0-500" id="brightness" name="brightness"><br>
            
            <input type="checkbox" id="color_check" name="color_check">
            <label for="color">COLOR:</label>
            <input type="color" id="color" name="color"><br>
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
            home_id: id,
            name: name
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'create' && obj === 'device') {
        const name = document.getElementById('device_name').value;
        const tuya_id = document.getElementById('tuya_id').value;
        const formData = {
            tuya_id: tuya_id,
            name: name,
        };
        formDataJSON = JSON.stringify(formData);
    }
    else if (formType === 'send_command') {
        const switch_led = document.getElementById('switch_led').value;
        const temperature = document.getElementById('temperature').value;
        const brightness = document.getElementById('brightness').value;
        const color = document.getElementById('color').value;
        let formData = []
        if (document.getElementById('switch_led_check').checked) {
            formData.push({
                code: 'SWITCH_LED',
                value: switch_led
            });
        }

        if (document.getElementById('temperature_check').checked) {
            formData.push({
                code: 'TEMPERATURE',
                value: temperature
            });
        }

        if (document.getElementById('brightness_check').checked) {
            formData.push({
                code: 'BRIGHTNESS',
                value: brightness
            });
        }

        if (document.getElementById('color_check').checked) {
            formData.push({
                code: 'COLOR',
                value: {
                    h: tinycolor(color).toHsv().h,
                    s: tinycolor(color).toHsv().s * 1000,
                    v: tinycolor(color).toHsv().v * 1000,
                }
            });
        }

        formDataJSON = JSON.stringify({
            deviceId: id,
            commands: formData
        });
    }
    tg.sendData(formDataJSON);
    tg.close();
});

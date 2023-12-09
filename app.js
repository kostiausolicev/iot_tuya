let tg = window.Telegram.WebApp; //получаем объект webapp телеграма

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

let dynamicFormContainer = document.getElementById('dynamicFormContainer');

dynamicFormContainer.style.backgroundColor = tg.ThemeParams.bg_color;

let labels = dynamicFormContainer.querySelectorAll('label');
for (let label of labels) {
    label.style.color = tg.ThemeParams.text_color;
    label.style.fontFamily = 'Arial, sans-serif';
    label.style.textAlign = 'center';
}

let h1 = dynamicFormContainer.querySelector('h1');
if (h1) {
    h1.style.color = tg.ThemeParams.text_color;
    h1.style.fontFamily = 'Arial, sans-serif';
    h1.style.textAlign = 'center';
}

let formType = getParameterByName('formType');

if (formType === 'register') {
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
} else if (formType === 'auth') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <h1>Введите данные:</h1>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
    `;
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
    } else if (formType === 'auth') {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const formData = {
            username: username,
            password: password
        };
        formDataJSON = JSON.stringify(formData);
    }
    tg.sendData(formDataJSON);
    tg.close();
});

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

let formType = getParameterByName('formType');

if (formType === 'register') {
    document.getElementById('dynamicFormContainer').innerHTML = `
            <label for="name">Username:</label>
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
        const confirm_password = document.getElementById('confirm password').value;
        const formData = {
            name: name,
            username: username,
            password: password,
            confirm_password: confirm_password
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

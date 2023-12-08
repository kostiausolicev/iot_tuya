let tg = window.Telegram.WebApp; //получаем объект webapp телеграма

tg.expand(); //расширяем на все окно

tg.MainButton.setText("Отправить данные");
tg.MainButton.enable()
tg.MainButton.show()

Telegram.WebApp.onEvent('mainButtonClicked', function(){
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var confirm_password = document.getElementById('confirm password').value;

    var formData = {
        username: username,
        password: password,
        confirm_password: confirm_password
    };

    // Получение JSON-представления объекта
    var formDataJSON = JSON.stringify(formData);
    tg.sendData("formDataJSON");
    tg.close();
});

let tg = window.Telegram.WebApp; //получаем объект webapp телеграма

tg.expand(); //расширяем на все окно

tg.MainButton.text = "Отправить данные"; //изменяем текст кнопки
tg.MainButton.show()
tg.MainButton.enable() //показываем

Telegram.WebApp.onEvent('mainButtonClicked', function(){
    tg.sendData("some string that we need to send");
});

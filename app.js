import drawAuth from 'templates/auth.js'
import parseAuthToJson from 'templates/auth.js'
import drawRegister from 'templates/register.js'
import parseRegisterToJson from 'templates/register.js'
import drawCreateDevice from 'templates/createDevice.js'
import parseDeviceToJson from 'templates/createDevice.js'
import drawCreateHome from 'templates/createHome.js'
import parseHomeToJson from 'templates/createHome.js'
import drawCreateRoom from 'templates/createRoom.js'
import parseRoomToJson from 'templates/createRoom.js'
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

if (formType === 'register') {
    drawRegister()
} else if (formType === 'auth') {
    drawAuth()
}
// else if (formType === 'create' && obj === 'home') {
//     drawCreateHome()
//         } else if (formType === 'create' && obj === 'room') {
//     drawCreateRoom()
//         } else if (formType === 'create' && obj === 'device') {
//     drawCreateDevice()
//         } else if (formType === 'update' && obj === 'home') {
//
// } else if (formType === 'update' && obj === 'room') {
//
// } else if (formType === 'update' && obj === 'device') {
//
// } else if(formType === 'change') {
//
// }

Telegram.WebApp.onEvent('mainButtonClicked', function(){

    let formDataJSON;
    if (formType === 'register') {
        formDataJSON = parseRegisterToJson()
    } else if (formType === 'auth') {
        formDataJSON = parseAuthToJson()
    }
    // else if (formType === 'create' && obj === 'home') {
    //     formDataJSON = parseHomeToJson()
    // } else if (formType === 'create' && obj === 'room') {
    //     formDataJSON = parseRoomToJson()
    // } else if (formType === 'create' && obj === 'device') {
    //     formDataJSON = parseDeviceToJson()
    // } else if (formType === 'update' && obj === 'home') {
    //     formDataJSON = parseAuthToJson()
    // } else if (formType === 'update' && obj === 'room') {
    //     formDataJSON = parseAuthToJson()
    // } else if (formType === 'update' && obj === 'device') {
    //     formDataJSON = parseAuthToJson()
    // } else if(formType === 'change') {
    //     formDataJSON = parseAuthToJson()
    // }
    tg.sendData(formDataJSON);
    tg.close();
});

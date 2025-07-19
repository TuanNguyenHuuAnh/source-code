import { Component } from 'react';
// import ReactHtmlParser from 'react-html-parser';
import parse from 'html-react-parser';
import { refreshCaptcha } from '../util/APIUtils';

let captcha_value = '';
let captcha_number = '';
let backgroundColor_value = '';
let fontColor_value = '';
let LoadCanvasTemplate_HTML = "<div><canvas id=\"canv\"></canvas><div>"
    // + "<a id=\"reload_href\"  style=\"cursor: pointer; color: blue\">Reload Captcha</a>"
    + "</div></div>";
let LoadCanvasTemplateNoReload_HTML = "<div><canvas id=\"canv\"></canvas><div></div></div>";


export const loadCaptchaEnginge = (numberOfCharacters, backgroundColor = 'white', fontColor = 'black', retVal = '') => {
    backgroundColor_value = backgroundColor;
    fontColor_value = fontColor;
    captcha_number = numberOfCharacters;
    let length = parseInt(numberOfCharacters);

    let captcha = retVal;

    captcha_value = captcha;



    let canvas = document.getElementById('canv'),
        ctx = canvas.getContext('2d');
    let text = captcha;
    let lineheight = 30;

    let lines = text.split('\n');
    ctx.canvas.width = parseInt(length) * 24;
    ctx.canvas.height = (lines.length * lineheight);

    ctx.fillStyle = backgroundColor;
    ctx.fillRect(10, 0, canvas.width, canvas.height);


    ctx.textBaseline = "middle";
    ctx.font = "italic 20px Arial";
    ctx.fillStyle = fontColor;




    let num = 0;
    for (let i = 0; i < parseInt(length); i++) {
        num = parseInt(num) + 1;
        let heigt_num = 20 * num;
        ctx.fillText(retVal[i], heigt_num, Math.round(Math.random() * (15 - 12) + 14));
    }

    // document.getElementById("reload_href").onclick = function () {
    //     reloadCaptchaEnginge(captcha_number, backgroundColor, fontColor);
    // }

};

export const validateCaptcha = (userValue, reload = true) => {
    if (userValue !== captcha_value) {
        if (reload === true) {
            reloadCaptchaEnginge(captcha_number, backgroundColor_value, fontColor_value);
        }

        return false;
    }

    else {
        return true;
    }
};

export const reloadCaptchaEnginge = (numberOfCharacters, backgroundColor = 'white', fontColor = 'black', userLogin = '') => {
    let captchaRequest = {
        jsonDataInput: {
            UserLogin: userLogin
        }
    };
    refreshCaptcha(captchaRequest).then(res => {
        let Response = res.Response;
        if (Response.Result === 'true' && Response.ErrLog === 'RECACTCHA_REQUIRED_OTP' && Response.Captcha) {
            loadCaptchaEnginge(captcha_number, backgroundColor_value, fontColor_value, Response.Captcha);
        } 
    }).catch(error => {

    });

}

export class LoadCanvasTemplate extends Component {

    render() {
        let reload_text = "";
        let reload_color = "";
        LoadCanvasTemplate_HTML = "<div><canvas id=\"canv\" style=\"background-color: blue;\"></canvas><div>"
            // + "<a id=\"reload_href\"  style=\"cursor: pointer; color: blue\">Reload Captcha</a>"
            + "</div></div>";


        if (this.props.reloadText) {
            reload_text = this.props.reloadText;


        }

        if (this.props.reloadColor) {
            reload_color = this.props.reloadColor;
        }

        if (reload_text === "") {
            reload_text = "Reload Captcha";
        }

        if (reload_color === "") {
            reload_color = "blue";
        }


        LoadCanvasTemplate_HTML = "<div><canvas id=\"canv\"></canvas><div>"
            // + "<a id=\"reload_href\"  style=\"cursor: pointer; color: " + reload_color + "\">" + reload_text + "</a>"
            + "</div></div>";

        return (parse(LoadCanvasTemplate_HTML));
    }

};

export class LoadCanvasTemplateNoReload extends Component {

    render() {
        return (parse(LoadCanvasTemplateNoReload_HTML));
    }

};

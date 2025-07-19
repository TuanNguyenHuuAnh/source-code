import {USER_LOGIN, COMPANY_KEY2} from "../constants";
import toast, {Toaster} from 'react-hot-toast';
import {
    CMS_CATEGORY_LIST_DATA,
    CMS_SUB_CATEGORY_MAP,
    FROM_APP,
    HISTORY_LOCATION_PATH,
    FE_BASE_URL,
    CMS_APP_ALIASLINK_CATEGORY_CODE_MAP,
    CMS_APP_ALIASLINK_CATEGORY_NAME_MAP,
    CLAIM_TYPE,
    IRACE_ID,
    DCID,
    PAGE_IRACE_BASE,
    IRACE_SECRET_KEY,
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLIENT_ID,
    WEB_BROWSER_VERSION,
    DEVICE_ID,
    EDOCTOR_ID,
    API_BASE_URL,
    PARTNER_ID_MAP, EDOCTOR_CODE,
    AKTIVOLABS_ID,
    AKTIVO_ACCESS_TOKEN,
    AKTIVO_MEMBER_ID,
    DEVICE_KEY,
    TranslatedStatus
} from '../constants';
import {GetConfiguration, getAktivoAuthenticate, CPSaveLog, CPConsentConfirmation} from './APIUtils';
import AES256 from 'aes-everywhere';
import {db} from './db';
import md5 from 'md5';
import {v4 as uuidv4} from 'uuid';
import {useHistory} from 'react-router-dom';
import ReactGA from "react-ga4";

export function checkIsNumber(value) {
    if (value === '' || value === '.' || value === ',') {
        return false;
    }
    if (value.indexOf('.') === value.length - 1) {
        return false;
    }
    if ((value.split(',').length - 1 > 1) || (value.split('.').length - 1 > 1)) {
        return false;
    }
    if ((value.indexOf(',') >= 0) && (value.indexOf('.') > value.indexOf(','))) {
        return false;
    }
    if (value.indexOf('.,') >= 0) {
        return false;
    }
    if (value.indexOf(',') > 0 && (value.length - value.indexOf(',') > 2)) {
        return false;
    }
    return /^[0-9.,]+$/.test(value);
};

export function isInteger(value) {
    return /^[0-9]+$/.test(value);
};

export function checkRegisterPassword(value) {
    return /(?=.{8,})((?=.*\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])).*/.test(value)
}

export function onlyLettersAndSpaces(str) {
    return !/\d/.test(str);
}

export function isLoggedIn() {
    let sessionUserLogin = getSession(USER_LOGIN);
    return sessionUserLogin !== null && sessionUserLogin !== undefined && sessionUserLogin && !!sessionUserLogin;
};

export function checkValue(value) {
    return !value ? '' : value;
};

export function formatDate(date) {
    if (!date) {
        return date;
    }
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('/');
}


export function formatDateFull(d) {
    var date = new Date(d);
    const pad = (num) => num.toString().padStart(2, '0');

    const day = pad(date.getDate());
    const month = pad(date.getMonth() + 1); // Tháng bắt đầu từ 0
    const year = date.getFullYear().toString().slice(-2); // Lấy 2 chữ số cuối của năm

    let hours = date.getHours();
    const minutes = pad(date.getMinutes());
    const seconds = pad(date.getSeconds());
    const ampm = hours >= 12 ? 'PM' : 'AM';

    hours = hours % 12;
    hours = hours ? hours : 12; // Nếu giờ là 0 thì đổi thành 12
    const formattedHours = pad(hours);

    return `${day}/${month}/${year} ${formattedHours}:${minutes}:${seconds} ${ampm}`;
}


export const trackingEvent = (category, action, label, from) => {
    // ReactGA.event({
    //     category: category,
    //     action: action + (getSession(DCID)?('_' + getSession(DCID)):''),
    //     label: label + (getSession(DCID)?('_' + getSession(DCID)):''),
    //     DCID:  (getSession(DCID)?getSession(DCID):''),
    //     Device_ID: (getSession(DEVICE_ID)?getSession(DEVICE_ID):''),
    //     Section_ID: `Web_${(getSession(DEVICE_ID)?getSession(DEVICE_ID):'')}`
    // });

    let event_name = action;
    let data = {
        DCID:  (getSession(DCID)?getSession(DCID):''),
        Device_ID: (getSession(DEVICE_ID)?getSession(DEVICE_ID):''),
        Section_ID: `${from?from:'Web'}_${(getSession(DEVICE_ID)?getSession(DEVICE_ID):'')}`,
        platform: from?from:'Web',
        custom_user_id: (getSession(DCID)?getSession(DCID):'')
    }
    let _label= label + (getSession(DCID)?('_' + getSession(DCID)):'');
    let event_params = {
        category,
        _label,
        ...data
    };
    // Send GA4 Event
    ReactGA.event(event_name, event_params);

    window.dataLayer.push({
        event: 'event',
        eventProps: {
            category: category,
            action: action + (getSession(DCID)?('_' + getSession(DCID)):''),
            label: label + (getSession(DCID)?('_' + getSession(DCID)):''),
            DCID:  (getSession(DCID)?getSession(DCID):''),
            Device_ID: (getSession(DEVICE_ID)?getSession(DEVICE_ID):''),
            Section_ID: `${from?from:'Web'}_${(getSession(DEVICE_ID)?getSession(DEVICE_ID):'')}`,
            platform: from?from:'Web',
            custom_user_id: (getSession(DCID)?getSession(DCID):'')
        }
    });
}

export const trackingEventAsTemplate = (eventName, screenName, platform, flow, cusType, dcid) => {
    let data = '';
    if (dcid) {
        window.dataLayer.push({
            event: eventName,
            eventProps: {
                screen_name: screenName,
                platform: platform,
                flow: flow,
                cus_type: cusType,
                dcid:  dcid,
                custom_user_id: dcid
            }
        });

        data = {
            screen_name: screenName,
            platform: platform,
            flow: flow,
            cus_type: cusType,
            dcid:  dcid,
            custom_user_id: dcid
        }
    } else {
        window.dataLayer.push({
            event: eventName,
            eventProps: {
                screen_name: screenName,
                platform: platform,
                flow: flow,
                cus_type: cusType
            }
        });

        data = {
            screen_name: screenName,
            platform: platform,
            flow: flow,
            cus_type: cusType
        }
    }
    let event_params = {
        screenName,
        ...data
    };
    // Send GA4 Event
    ReactGA.event(screenName, event_params);

}

export function formatDateMMddyy(date) {
    if (!date) {
        return date;
    }
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [month, day, year].join('/');
}

export function shortFormatDate(date) {
    if (!date) {
        return date;
    }
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month].join('/');
}

export function formatDate2(date) {
    if (!date) {
        return date;
    }
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('-');
}

export function formatDate3(date) {
    if (!date) {
        return date;
    }
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

export function getCurrentDate() {
    var d = new Date,
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hour = '' + d.getHours(),
        min = '' + d.getMinutes(),
        sec = '' + d.getSeconds();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;
    if (hour.length < 2)
        hour = '0' + hour
    if (min.length < 2)
        min = '0' + min
    if (sec.length < 2)
        sec = '0' + sec
    return [day, month, year].join('/') + ' ' +
        [hour, min, sec].join(':');
}

export const VALID_EMAIL_REGEX =
    RegExp(/^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i);

export const base64ArrayBuffer = (arrayBuffer) => {
    var base64 = ''
    var encodings = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'

    var bytes = new Uint8Array(arrayBuffer)
    var byteLength = bytes.byteLength
    var byteRemainder = byteLength % 3
    var mainLength = byteLength - byteRemainder

    var a, b, c, d
    var chunk

    // Main loop deals with bytes in chunks of 3
    for (var i = 0; i < mainLength; i = i + 3) {
        // Combine the three bytes into a single integer
        chunk = (bytes[i] << 16) | (bytes[i + 1] << 8) | bytes[i + 2]

        // Use bitmasks to extract 6-bit segments from the triplet
        a = (chunk & 16515072) >> 18 // 16515072 = (2^6 - 1) << 18
        b = (chunk & 258048) >> 12 // 258048   = (2^6 - 1) << 12
        c = (chunk & 4032) >> 6 // 4032     = (2^6 - 1) << 6
        d = chunk & 63               // 63       = 2^6 - 1

        // Convert the raw binary segments to the appropriate ASCII encoding
        base64 += encodings[a] + encodings[b] + encodings[c] + encodings[d]
    }

    // Deal with the remaining bytes and padding
    if (byteRemainder === 1) {
        chunk = bytes[mainLength]

        a = (chunk & 252) >> 2 // 252 = (2^6 - 1) << 2

        // Set the 4 least significant bits to zero
        b = (chunk & 3) << 4 // 3   = 2^2 - 1

        base64 += encodings[a] + encodings[b] + '=='
    } else if (byteRemainder === 2) {
        chunk = (bytes[mainLength] << 8) | bytes[mainLength + 1]

        a = (chunk & 64512) >> 10 // 64512 = (2^6 - 1) << 10
        b = (chunk & 1008) >> 4 // 1008  = (2^6 - 1) << 4

        // Set the 2 least significant bits to zero
        c = (chunk & 15) << 2 // 15    = 2^4 - 1

        base64 += encodings[a] + encodings[b] + encodings[c] + '='
    }

    return base64
}

export function formatMoney(amount, decimalCount = 1, decimal = ",", thousands = ".") {
    try {
        if (parseFloat(amount) === parseInt(amount)) {
            decimalCount = 0;
        }
        decimalCount = Math.abs(decimalCount);
        decimalCount = isNaN(decimalCount) ? 1 : decimalCount;

        const negativeSign = amount < 0 ? "-" : "";

        let i = parseInt(amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)).toString();
        let j = (i.length > 3) ? i.length % 3 : 0;
        let va = decimalCount ? decimal + Math.abs(amount - i).toFixed(decimalCount).slice(2) : "";
        let value = (j ? i.substr(0, j) + thousands : '') +
            i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousands) + va;

        return negativeSign + value;

    } catch (e) {
        //console.log(e)
    }
}

export function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

export function formatStrDetail(strDetail, subDetail) {
    let detail = strDetail;
    let indexStart = strDetail.indexOf('[');
    let indexEnd = strDetail.indexOf(']');
    let typeSup = 'Không có';
    if (indexStart >= 0 && indexEnd > 0) {
        typeSup = strDetail.substring(indexStart + 1, indexEnd);
        if (subDetail != '') {
            var position = detail.indexOf(subDetail);
            detail = [detail.slice(detail.indexOf(subDetail), position + subDetail.length + 1), typeSup, detail.slice(position + subDetail.length)].join('');
        } else {
            var position = detail.indexOf(subDetail);
            detail = [typeSup, detail.slice(indexEnd + 1)].join('');
        }
    }
    return detail;
}

export function getHistoryIcon(description) {
    let img = 'img/icon/9.1/9.1-menulist-icon-thamgiahd2.svg';
    if (description.indexOf('Hủy đơn hàng') >= 0) {
        img = 'img/icon/9.1/9.1-menulist-icon-thamgiahd2.svg';
    } else if (description.indexOf('Thưởng theo phương thức nộp phí') >= 0) {
        img = 'img/icon/9.1/9.1-menulist-icon-nopphibh.svg';
    } else if (description.indexOf('Sinh nhật của Bên mua Bảo Hiểm') >= 0) {
        img = 'img/icon/9.1/9.1-menulist-icon-sinhnhat.svg';
    } else if (description.indexOf('Thưởng trên thời gian duy trì hợp đồng') >= 0) {
        img = 'img/icon/9.1/9.1-menulist-icon-knhd.svg';
    }
    return img;
}

export function formatFullName(name) {
    let formatedName = '';
    if (!name || (name.trim() === '')) {
        formatedName = name;
    } else {
        let nameArr = name.toLowerCase().split(' ');
        for (let i = 0; i < nameArr.length; i++) {
            if (formatedName === '') {
                formatedName += nameArr[i].charAt(0).toUpperCase() + nameArr[i].slice(1);
            } else {
                formatedName += ' ' + nameArr[i].charAt(0).toUpperCase() + nameArr[i].slice(1);
            }

        }
    }
    return formatedName;
}

export function roundUp(number) {
    let rounded = Math.ceil(number * 10) / 10;
    return rounded;
}

export function roundDown(number) {
    let rounded = Math.floor(number * 10) / 10;
    return rounded;
}

export function genPassword(n) {
    var length = n,
        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789*#$",
        retVal = "";
    for (var i = 0, n = charset.length; i < length; ++i) {
        retVal += charset.charAt(Math.floor(Math.random() * n));
    }
    return retVal;
}

export function showQuantricsSurvey(clsName) {
    if (document.getElementsByClassName(clsName)[0]) {
        document.getElementsByClassName(clsName)[0].className = document.getElementsByClassName(clsName)[0].className.replaceAll(' hideDiv', '');
    }

}

export function isLoadedQuantricsSurvey(clsName) {
    return document.getElementsByClassName(clsName)[0];
}

export function showMessage(msg) {
    return toast(
        <div>
            <p>{msg}</p>
        </div>,
        {
            style: {
                borderRadius: '10px',
                background: '#ffffff',
                color: '#000000',
                fontFamily: 'Inter, sans-serif',
            },
        });
}

export function toDate(d) {
    const [day, month, year] = d.split("/");
    return new Date(year, month - 1, day);
}

export function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(window.location.search);
    return results === null ? '' : decodeURIComponent(results[1]);
}

export function maskPhoneNumber(phoneNumber) {
    // Check if the phoneNumber is a string and has at least 7 characters
    if (typeof phoneNumber === 'string' && phoneNumber.length >= 7) {
        // Keep the first 3 characters, replace the middle characters with '*',
        // and keep the last characters (3 characters after the last '*')
        return phoneNumber.slice(0, 3) + '*'.repeat(phoneNumber.length - 6) + phoneNumber.slice(-3);
    } else {
        // Return the original value if it doesn't meet the criteria
        return phoneNumber;
    }
}

export function removeTrailingZeros(number) {
    // Convert to string, parse to float, and format without trailing zeros
    return parseFloat(number).toFixed(10).replace(/\.?0+$/, '');
}

export function maskEmailFormat(email) {
    // Check if the email is a string and contains an '@' symbol
    if (typeof email === 'string' && email.includes('@')) {
        const atIndex = email.indexOf('@');
        const domain = email.slice(atIndex); // Extract the domain part
        const maskedPart = email.slice(0, Math.min(4, atIndex)) + '*'.repeat(atIndex - Math.min(4, atIndex));
        return maskedPart + domain;
    } else {
        // Return the original value if it doesn't meet the criteria
        return email;
    }
}

export function getUrlParameterNoDecode(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(window.location.search);
    return results === null ? '' : results[1];
}

export function filterValidEpolicy(polList) {

    const pol = polList.filter(item => {
        if ((item.PolicyStatusCode === '1' || item.PolicyStatusCode === '2' || item.PolicyStatusCode === '3' || item.PolicyStatusCode === '5')) {
            return true;
        }

        return false;
    });
    if (pol !== undefined) {
        return pol;
    }
    return null;
}

export function filterValidILEpolicy(polList) {

    const pol = polList.filter(item => {
        if (item.PolicyClassCD === 'IL') {
            return true;
        }

        return false;
    });
    if (pol !== undefined) {
        return pol;
    }
    return null;
}

export function maskPhone(phone) {
    if (!phone) {
        return '';
    }
    let strLength = phone.length;
    let last4Characters = phone.substring(strLength - 4);
    let maskStr = makeStr(strLength - 4);
    return maskStr + last4Characters;
}

// export function maskEmail(email) {
//     if (!email || email.indexOf('@') < 0) {
//         // Empty or no '@' character found, return the original email
//         return '';
//     }

//     let [sub, pre] = email.split('@');
//     let chars = sub.split('');
//     let count = 0;
//     for (let i = chars.length - 1; (i >= 0) && (count++ < 3); i--) {
//         if (!/\s/.test(chars[i])) {
//             chars[i] = '*';
//         }
//     }

//     return chars.join('') + '@' + pre;
// }

export function maskEmail(email) {
    if (!email || email.indexOf('@') < 0) {
        // Empty or no '@' character found, return the original email
        return '';
    }
    let [localPart, domain] = email.split('@');
    let maskedLocalPart = localPart.slice(0, 3) + '*'.repeat(localPart.length - 3);
    return maskedLocalPart + '@' + domain;
}

export function makeStr(len, char) {
    return Array.prototype.join.call({length: (len || -1) + 1}, char || 'x');
}

export function formatDateNew(inputDateString) {
    const [datePart, timePart] = inputDateString.split(' '); // Split date and time parts
    const [day, month, year] = datePart.split('/').map(Number);

    if (isNaN(day) || isNaN(month) || isNaN(year)) {
        // Handle invalid input date string
        return "Invalid Date";
    }

    const formattedDate = `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
    return formattedDate;
}

export function removeAccents(str) {
    if (!str) {
        return str;
    }
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    return str;
}

export function findIndexInStr(str, keyword) {
    return removeAccents(str).indexOf(removeAccents(keyword));
}

export function StringToAliasLink(str) {
    if (str === null || (str === undefined)) return str;
    try {
        str = (str.toLowerCase()).replaceAll('& ', '');
        str = removeAccents(str);
        str = str.replaceAll(' ', '-')
        return str;
    } catch (err) {

    }
    return str;
}

export function getMapSize(x) {
    var len = 0;
    for (var count in x) {
        len++;
    }

    return len;
}

export function checkMapHaveTrue(x) {
    for (var count in x) {
        if (x[count]) {
            return true;
        }
    }
    return false;
}

export function is18Plus(DOB) {
    let birthday = new Date(DOB);
    var ageDifMs = Date.now() - birthday.getTime();
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
    return Math.abs(ageDate.getUTCFullYear() - 1970) >= 18;
}

export function findAliasLinkInCategoryList(aliasLink, categoyListData) {
    let data = categoyListData;
    if (!data) {
        data = getSession(CMS_CATEGORY_LIST_DATA) ? JSON.parse(getSession(CMS_CATEGORY_LIST_DATA)) : getSession(CMS_CATEGORY_LIST_DATA);
    }
    if (data) {
        for (let i = 0; i < data.length; i++) {
            if (aliasLink === data[i].linkAlias) {
                return [data[i].label, data[i].code];
            }
        }
    }
    return null;
}

export function findAliasLinkInSubCategoryList(aliasLink, code, subCategoryMap) {
    let map = subCategoryMap;
    if (!map) {
        map = getSession(CMS_SUB_CATEGORY_MAP) ? JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)) : getSession(CMS_SUB_CATEGORY_MAP);
    }
    if (map && getMapSize(map) > 0) {
        let data = map[code];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                if (aliasLink === StringToAliasLink(data[i].label)) {
                    return [data[i].label, data[i].id];
                }
            }
        }

    }
    return null;
}

export function findSubCategoryIdInSubCategoryList(aliasLink, code, subCategoryMap) {
    let map = null;
    if (subCategoryMap) {
        map = subCategoryMap;
    } else {
        map = getSession(CMS_SUB_CATEGORY_MAP) ? JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)) : getSession(CMS_SUB_CATEGORY_MAP);
    }
    if (map && getMapSize(map) > 0) {
        let data = map[code];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                if (aliasLink === StringToAliasLink(data[i].label)) {
                    return data[i].id;
                }
            }
        }

    }
    return null;
}

export function findSubCategoryNameInSubCategoryList(aliasLink, code, subCategoryMap) {
    let map = null;
    if (subCategoryMap) {
        map = subCategoryMap;
    } else {
        map = getSession(CMS_SUB_CATEGORY_MAP) ? JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)) : getSession(CMS_SUB_CATEGORY_MAP);
    }
    if (map && getMapSize(map) > 0) {
        let data = map[code];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                if (aliasLink === StringToAliasLink(data[i].label)) {
                    return data[i].label;
                }
            }
        }

    }
    return null;
}

export function findCategoryBySub(subAlias, subCatMap) {
    let subCategoryMap = null;
    if (subCatMap) {
        subCategoryMap = subCatMap;
    } else {
        subCategoryMap = JSON.parse(getSession(CMS_SUB_CATEGORY_MAP));
    }

    for (let catCode in subCategoryMap) {
        let data = subCategoryMap[catCode];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                if (StringToAliasLink(data[i].label) === subAlias) {
                    return catCode;
                }
            }

        }
    }
    return null;
}

export function findCategoryNameBySubCatName(subCatName) {
    let subCategoryMap = JSON.parse(getSession(CMS_SUB_CATEGORY_MAP));
    for (let catCode in subCategoryMap) {
        let data = subCategoryMap[catCode];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                // alert(data[i].label + "|" + subCatName);
                if (data[i].label === subCatName) {
                    return findCategoryByCode(catCode);
                }
            }

        }
    }
    return null;
}

export function findCategoryBySubCategoryId(subId) {
    let subCategoryMap = JSON.parse(getSession(CMS_SUB_CATEGORY_MAP));
    for (let catCode in subCategoryMap) {
        let data = subCategoryMap[catCode];
        if (data) {
            for (let i = 0; i < data.length; i++) {
                if (data[i].id === subId) {
                    return catCode;
                }
            }

        }
    }
    return null;
}

export function findCategoryByCode(code) {
    let categoryList = JSON.parse(getSession(CMS_CATEGORY_LIST_DATA));
    if (categoryList) {
        for (let i = 0; i < categoryList.length; i++) {
            if (categoryList[i].code === code) {
                return categoryList[i].label;
            }
        }
    }
    return null;
}

export function findCategoryCodeByLinkAlias(aliasLink) {
    let categoryList = JSON.parse(getSession(CMS_CATEGORY_LIST_DATA));
    if (categoryList) {
        for (let i = 0; i < categoryList.length; i++) {
            if (categoryList[i].linkAlias === aliasLink) {
                return categoryList[i].code;
            }
        }
    }
    return CMS_APP_ALIASLINK_CATEGORY_CODE_MAP[aliasLink];
}

export function findCategoryNameByLinkAlias(aliasLink) {
    let categoryList = JSON.parse(getSession(CMS_CATEGORY_LIST_DATA));
    if (categoryList) {
        for (let i = 0; i < categoryList.length; i++) {
            if (categoryList[i].linkAlias === aliasLink) {
                return categoryList[i].label;
            }
        }
    }
    return CMS_APP_ALIASLINK_CATEGORY_NAME_MAP[aliasLink];
}

export function findCategoryLinkAliasByCode(code, categoryListData) {
    let categoryList = null;
    if (categoryListData) {
        categoryList = categoryListData;
    } else {
        categoryList = JSON.parse(getSession(CMS_CATEGORY_LIST_DATA));
    }

    if (categoryList) {
        for (let i = 0; i < categoryList.length; i++) {
            if (categoryList[i].code === code) {
                return categoryList[i].linkAlias;
            }
        }
    }
    return null;
}


export function findItemInNewsList(item, list) {
    if (item && list) {
        for (let i = 0; i < list.length; i++) {
            if (list[i].id === item.id) {
                return true;
            }
        }
    }
    return false;
}

export function findItemIndexInNewsList(item, list) {
    if (item && list) {
        for (let i = 0; i < list.length; i++) {
            if (list[i].id === item.id) {
                return i;
            }
        }
    }
    return -1;
}

export function findRelateInNewsList(item, list) {
    let result = [];
    if (item && list) {
        for (let i = 0; i < list.length; i++) {
            if ((list[i].id !== item.id) && findTagRelative(list[i].keyWord, item.keyWord)) {
                result.push(list[i]);
                if (result.length === 3) {
                    return result;
                }
            }
        }
    }
    return result;
}

export function findTagRelative(tag1, tag2) {
    if (!tag1 || !tag2) {
        return false;
    }
    let tag1Arr = tag1.split(',');
    let tag2Arr = tag2.split(',');
    for (let i = 0; i < tag1Arr.length; i++) {
        for (let j = 0; j < tag2Arr.length; j++) {
            if (tag1Arr[i].trim() === tag2Arr[j].trim()) {
                return true;
            }
        }
    }
    return false;
}

export function findLatestPublicInNewsList(item, list, relateList) {
    let result = [];
    if (item && list) {
        for (let i = 0; i < list.length; i++) {
            if ((list[i].id !== item.id) && !findItemInNewsList(list[i], relateList)) {
                result.push(list[i]);
                if (result.length === 3) {
                    return result;
                }
            }
        }
    }
    return result;
}

export function pushHistory(fromApp, forceReset) {
    if (getSession(FROM_APP) || fromApp) {
        let historyArr = getSession(HISTORY_LOCATION_PATH);
        if (historyArr && !forceReset) {
            historyArr = JSON.parse(historyArr);
            let idx = historyArr.indexOf(window.location.href);
            if (idx < 0) {
                historyArr.push(window.location.href);
                setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyArr));
            } else {
                historyArr.splice(idx, 1);
                historyArr.push(window.location.href);
                setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyArr));
            }

        } else {
            historyArr = [];
            historyArr.push(window.location.href);
            setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyArr));
        }
        return true;
    }
    return false;
}

export function setSession(key, value) {
    if (!key) {
        return;
    }
    value = value + '';
    sessionStorage.setItem(key, AES256.encrypt(value, COMPANY_KEY2));
    //localStorage.setItem(key, AES256.encrypt(value, COMPANY_KEY2));
}

export function getSession(key) {
    if (!key) {
        return null;
    }
    let value = sessionStorage.getItem(key);
    //let value = localStorage.getItem(key);
    if (!value) {
        return value;
    }
    return AES256.decrypt(value, COMPANY_KEY2);
}


// export function setSession(key, value) {
//   // value = value + '';
//   if (!key || !value) {
//     return;
//   }
//   sessionStorage.setItem(key, value);
// }

// export function getSession(key) {
//   if (!key) {
//     return null;
//   }
//   let value = sessionStorage.getItem(key);
//   return value;
// }

export function setDeviceKey(value) {
    if (!value) {
        return;
    }
    localStorage.setItem(DEVICE_KEY, value);
}

export function getDeviceKey() {
      let value = localStorage.getItem(DEVICE_KEY);
      return value;
}

export function removeSession(key) {
    if (!key) {
        return;
    }
    sessionStorage.removeItem(key);
    //localStorage.removeItem(key);
}

export function clearSession() {
    sessionStorage.clear();
}

export function clearLocalStorage() {
    localStorage.clear();
}

export function setLocal(key, value) {
    value = value + '';
    // console.log(key, value)
    if (value === 'undefined') {
        return;
    }
    if (!key || !value) {
        return;
    }
    try {
        const v = AES256.encrypt(value, COMPANY_KEY2);
        db.claims.put({v, key: key});
        // localStorage.setItem(key, AES256.encrypt(value, COMPANY_KEY2));
    } catch (e) {
        // console.log(`setLocal Error: ${e}`);
    }
}

export function getLocal(key) {
    if (!key) {
        return null;
    }
    let value = null;
    try {
        value = db.claims.get({key});
    } catch (e) {
        // console.log(`getLocal Error: ${e}`);
    }
    if (!value) {
        return value;
    }
    // return AES256.decrypt(value, COMPANY_KEY2);
    return value;
}

// export function setLocal(key, value) {
//   value = value + '';
//   if (!key || !value) {
//     return;
//   }
//   localStorage.setItem(key, value);
// }

// export function getLocal(key) {
//   if (!key) {
//     return null;
//   }
//   return localStorage.getItem(key);
// }

export function removeLocal(key) {
    if (!key) {
        return;
    }
    try {
        db.claims.delete(key);
    } catch (e) {
        console.log('removeLocal error:', e);
    }
    
}

export function appendScript(addSript) {
    const script = document.createElement("script");

    script.src = FE_BASE_URL + addSript;
    script.async = true;
    document.body.appendChild(script);
}

export function removeScript(scriptToremove) {
    let allsuspects = document.getElementsByTagName("script");
    for (let i = allsuspects.length; i >= 0; i--) {
        if (allsuspects[i] && allsuspects[i].getAttribute("src") !== null
            && allsuspects[i].getAttribute("src").indexOf(`${scriptToremove}`) !== -1) {
            allsuspects[i].parentNode.removeChild(allsuspects[i])
        }
    }
}

export function removeIframe() {
    var elements = document.getElementsByTagName("iframe");
    while (elements.length) {
        elements[0].parentNode.removeChild(elements[0]);
    }
}

export function createScriptPrerenderWait() {
    var head = document.getElementsByTagName('head')[0];
    var tag = document.createElement('script');
    tag.id = 'prerendertagid';
    tag.textContent = 'window.prerenderReady = false;';
    var script = document.getElementById('prerendertagid');
    if (script != null) {
      head.removeChild(script);
    }
    head.insertBefore(tag, document.head.firstElementChild);
}

export function createScriptPrerenderForceReady() {
    var head = document.getElementsByTagName('head')[0];
    var tag = document.createElement('script');
    tag.id = 'prerendertagid';
    tag.textContent = 'window.prerenderReady = true;';
    var script = document.getElementById('prerendertagid');
    if (script != null) {
      head.removeChild(script);
    }
    head.insertBefore(tag, document.head.firstElementChild);
}

export function postMessageSubNativeIOS(fromApp) {
    let from = fromApp;
    if (!from) {
        from = getSession(FROM_APP);
    }
    if (from && (from === "IOS")) {
        let obj = {
            kind_of_page: "SUB_PAGE"
        };
        if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.callbackNavigateToPage !== undefined) {
            window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
        }
    }
}

export function postMessageMainNativeIOS(fromApp) {
    let from = fromApp;
    if (!from) {
        from = getSession(FROM_APP);
    }
    if (from && (from === "IOS")) {
        let obj = {
            kind_of_page: "MAIN_PAGE"
        };
        if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.callbackNavigateToPage !== undefined) {
            window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
        }
    }
}

export function postMessageExternalNativeIOS() {
    let from = getSession(FROM_APP);
    if (from && (from === "IOS")) {
        let obj = {
            kind_of_page: "EXTERNAL_PAGE_RUN"
        };
        if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.callbackNavigateToPage !== undefined) {
            window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
        }
    }
}

export function compareDate(startDate, endDate) {
    let start = new Date(startDate);
    let end = new Date(endDate);
    start.setHours(0, 0, 0, 0);
    end.setHours(0, 0, 0, 0);
    return end - start;
}

export function numOfDates(startDate, endDate) {
    let date1 = new Date(startDate);
    let date2 = new Date(endDate);

    // To calculate the time difference of two dates
    let Difference_In_Time = date2.getTime() - date1.getTime();

    // To calculate the no. of days between two dates
    let Difference_In_Days =
        Math.round(Difference_In_Time / (1000 * 3600 * 24));

    // To display the final no. of days (result)

        return Difference_In_Days;
}

export function getMineType(base64) {
    if (base64.indexEnd("image/png" >= 0)) {
        return "image/png";
    }
    if (base64.indexEnd("image/jpg" >= 0)) {
        return "image/jpg";
    }
    if (base64.indexEnd("image/jpeg" >= 0)) {
        return "image/jpeg";
    }
    if (base64.indexEnd("application/pdf" >= 0)) {
        return "application/pdf";
    }
    return "image/png";
}

export function isMobile() {
    let check = false;
    (function (a) {
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) check = true;
    })(navigator.userAgent || navigator.vendor || window.opera);
    return check;
}

export function sumInvoice(invoiceList) {
    if (invoiceList && invoiceList.length > 0) {
        let total = 0;
        for (let i = 0; i < invoiceList.length; i++) {
            if (invoiceList[i].RequestAmount) {
                // Chuyển chuỗi số thành số và loại bỏ dấu chấm
                const amount = parseFloat(invoiceList[i].RequestAmount.replace(/\./g, '').replace(',', '.'));
                total += amount;
            }
        }
        // Định dạng kết quả theo đúng tiền tệ
        const formattedTotal = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(total);
        return formattedTotal;
    }
    return 0;
}

export function sumInvoiceNew(invoiceList) {
    if (invoiceList && invoiceList.length > 0) {
        let total = 0;
        for (let i = 0; i < invoiceList.length; i++) {
            if (invoiceList[i].invoiceAmount) {
                // Chuyển chuỗi số thành số và loại bỏ dấu phẩy nếu có
                const amount = parseFloat(invoiceList[i].invoiceAmount.replace(/\./g, '').replace(',', '.'));
                total += amount;
            }
        }
        // Trả về tổng số dưới dạng chuỗi
        return total.toString();
    }
    return "0";
}



export function countIndefofItem(array, str) {
    if (!array || !str) {
        return 0;
    }
    let count = 0;
    for (let i = 0; i < array.length; i++) {
        if (array[i].indexOf(str) >= 0) {
            count++;
        }
    }
    return count;
}

export function iOS() {
    return [
            'iPad Simulator',
            'iPhone Simulator',
            'iPod Simulator',
            'iPad',
            'iPhone',
            'iPod'
        ].includes(navigator.platform)
        // iPad on iOS 13 detection
        || (navigator.userAgent.includes("Mac") && "ontouchend" in document)
}

//chi tu vong
export function isCheckedOnlyDeadth(claimCheckedMap) {
    let haveDeadType = false;
    let count = 0;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.DEATH) {
            haveDeadType = true;
        }
        if (claimCheckedMap[type]) {
            count++;
        }
    }
    if (haveDeadType && count === 1) {
        return true;
    } else {
        return false;
    }
}

//chi tai nan
export function isCheckedOnlyAccident(claimCheckedMap) {
    let haveAccidentType = false;
    let count = 0;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.ACCIDENT) {
            haveAccidentType = true;
        }
        if (claimCheckedMap[type]) {
            count++;
        }
    }
    if (haveAccidentType && count === 1) {
        return true;
    } else {
        return false;
    }
}

//chi benh hiem ngheo
export function isCheckedOnlyIllness(claimCheckedMap) {
    let haveIllnessType = false;
    let count = 0;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.ILLNESS) {
            haveIllnessType = true;
        }
        if (claimCheckedMap[type]) {
            count++;
        }
    }
    if (haveIllnessType && count === 1) {
        return true;
    } else {
        return false;
    }
}

//chi HC/HS
export function isCheckedOnlyHC_HS(claimCheckedMap) {
    let haveHCHSType = false;
    let count = 0;
    let countHCHS = 0;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.HEALTH_CARE) {
            haveHCHSType = true;
            countHCHS++;
        }
        if (claimCheckedMap[type] && type === CLAIM_TYPE.HS) {
            haveHCHSType = true;
            countHCHS++;
        }
        if (claimCheckedMap[type]) {
            count++;
        }
    }
    if (haveHCHSType && count === countHCHS) {
        return true;
    } else {
        return false;
    }
}

//have HC/HS
export function haveHC_HS(claimCheckedMap) {
    let haveHCHSType = false;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.HEALTH_CARE) {
            haveHCHSType = true;
            break;
        }
        if (claimCheckedMap[type] && type === CLAIM_TYPE.HS) {
            haveHCHSType = true;
            break;
        }
    }
    if (haveHCHSType) {
        return true;
    } else {
        return false;
    }
}
//co tu vong
export function haveCheckedDeadth(claimCheckedMap) {
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && type === CLAIM_TYPE.DEATH) {
            return true;
        }
    }
    return false;
}

//no any chosen
export function noCheckedAny(claimCheckedMap) {
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type]) {
            return false;
        }
    }
    return true;
}

//facility tab
export function validFacilityDate(pFacilityDetail, fDate, sickInfo, accidentInfo, deathInfo) {
    const validFacilityVsSick = validFacilityWithSick(fDate, sickInfo);
    const validFacilityVsAccident = validFacilityWithAccident(fDate, accidentInfo);
    const validFacilityVsDeath = validFacilityWithDeath(fDate, deathInfo);
    let todayDate = new Date();
    if (pFacilityDetail.treatmentType === 'IN') {
        if (pFacilityDetail.startDate === null || pFacilityDetail.startDate === undefined || pFacilityDetail.startDate === ""
            || pFacilityDetail.endDate === null || pFacilityDetail.endDate === undefined || pFacilityDetail.endDate === "") {
            pFacilityDetail.errors.dateValid = "Quý khách cần nhập đầy đủ ngày nhập viện và ngày xuất viện hợp lệ.";
        } else if (new Date(formatDateMMddyy(fDate)) > new Date(formatDateMMddyy(todayDate))) {
            pFacilityDetail.errors.dateValid = "Ngày nhập viện/ngày xuất viện không được lớn hơn ngày hiện tại.";
        } else if (new Date(formatDateMMddyy(pFacilityDetail.startDate)) > new Date(formatDateMMddyy(pFacilityDetail.endDate))) {
            pFacilityDetail.errors.dateValid = "Ngày nhập viện không được lớn hơn ngày xuất viện";
        } else if (validFacilityVsDeath) {
            pFacilityDetail.errors.dateValid = validFacilityVsDeath;
        } else if (validFacilityVsSick) {
            pFacilityDetail.errors.dateValid = validFacilityVsSick;
        } else if (validFacilityVsAccident) {
            pFacilityDetail.errors.dateValid = validFacilityVsAccident;
        } else {
            pFacilityDetail.errors.dateValid = "";
        }
    } else {
        if (pFacilityDetail.startDate === null || pFacilityDetail.startDate === undefined || pFacilityDetail.startDate === "") {
            pFacilityDetail.errors.dateValid = "Quý khách cần nhập ngày khám/điều trị.";
        } else if (new Date(formatDateMMddyy(pFacilityDetail.startDate)) > new Date(formatDateMMddyy(todayDate)) || new Date(formatDateMMddyy(pFacilityDetail.startDate)) > new Date(formatDateMMddyy(todayDate))) {
            pFacilityDetail.errors.dateValid = "Ngày khám/điều trị không được lớn hơn ngày hiện tại.";
        } else if (validFacilityVsDeath) {
            pFacilityDetail.errors.dateValid = validFacilityVsDeath;
        } else if (validFacilityVsSick) {
            pFacilityDetail.errors.dateValid = validFacilityVsSick;
        } else if (validFacilityVsAccident) {
            pFacilityDetail.errors.dateValid = validFacilityVsAccident;
        } else {
            pFacilityDetail.errors.dateValid = "";
        }
    }
    return pFacilityDetail;
}

export function validFacilityWithSick(date, sickInfo) {
    if (sickInfo.sickFoundTime && (new Date(formatDateMMddyy(date)) < new Date(formatDateMMddyy(sickInfo.sickFoundTime)))) {
        return "Ngày khám và điều trị phải sau ngày khởi phát Bệnh";
    }
    return "";
}

export function validFacilityWithAccident(date, accidentInfo) {
    if (accidentInfo.date && (new Date(formatDateMMddyy(date)) < new Date(formatDateMMddyy(accidentInfo.date)))) {
        return "Ngày khám và điều trị phải sau ngày Tai nạn";
    }
    return "";
}

export function validFacilityWithDeath(date, deathInfo) {
    if (deathInfo.date && (new Date(formatDateMMddyy(date)) > new Date(formatDateMMddyy(deathInfo.date)))) {
        return "Ngày khám và điều trị phải trước ngày Tử vong";
    }
    return "";
}

//death tab
export function validDeathDateWithFacilityDate(deathDate, facilityList) {
    if (!facilityList || facilityList.length === 0) {
        return "";
    }
    for (let i = 0; i < facilityList.length; i++) {
        if (facilityList[i].startDate && new Date(formatDateMMddyy(facilityList[i].startDate)) > new Date(formatDateMMddyy(deathDate))) {
            return "Ngày Tử vong phải sau ngày khám và điều trị";
        } else if (facilityList[i].endDate && new Date(formatDateMMddyy(facilityList[i].endDate)) > new Date(formatDateMMddyy(deathDate))) {
            return "Ngày Tử vong phải sau ngày khám và điều trị";
        }
    }
    return "";
}

export function validDeathWithSick(deathDate, sickInfo) {
    if (sickInfo.sickFoundTime && new Date(formatDateMMddyy(sickInfo.sickFoundTime)) > new Date(formatDateMMddyy(deathDate))) {
        return "Ngày Tử vong phải sau Ngày khởi phát bệnh ";
    }
    return "";
}

export function validDeathWithAccident(deathDate, accidentInfo) {
    if (accidentInfo.date && new Date(formatDateMMddyy(accidentInfo.date)) > new Date(formatDateMMddyy(deathDate))) {
        return "Ngày Tử vong phải sau ngày xảy ra Tai nạn";
    }
    return "";
}

export function validDeathDate(value, pDeathInfo, sickInfo, accidentInfo, facilityList) {
    let validWithSick = validDeathWithSick(value, sickInfo);
    let validWithAccident = validDeathWithAccident(value, accidentInfo);
    let deathVSFacility = validDeathDateWithFacilityDate(value, facilityList);

    if (!value) {
        pDeathInfo.errors.dateValid = "Quý khách cần nhập ngày giờ tử vong hợp lệ.";
        // } else if (moment(pDeathInfo.date, "yyyy-MM-DD").add(12, "M") < moment(todayDate)) {
        //   pDeathInfo.errors.dateValid = "Ngày tử vong không quá 12 tháng kể từ ngày hiện tại tạo yêu cầu.";
    } else if (validWithSick) {
        pDeathInfo.errors.dateValid = validWithSick;
    } else if (validWithAccident) {
        pDeathInfo.errors.dateValid = validWithAccident;
    } else if (deathVSFacility) {
        pDeathInfo.errors.dateValid = deathVSFacility;
    } else {
        pDeathInfo.errors.dateValid = "";
    }
    return pDeathInfo;
}

//sick tab
export function validSickDateWithFacilityDate(sickDate, facilityList) {
    if (!facilityList || facilityList.length === 0) {
        return "";
    }
    for (let i = 0; i < facilityList.length; i++) {
        if (facilityList[i].startDate && (new Date(formatDateMMddyy(facilityList[i].startDate)) < new Date(formatDateMMddyy(sickDate)))) {
            return "Ngày khởi phát Bệnh phải trước ngày Khám và điều trị";
        } else if (facilityList[i].endDate && new Date(formatDateMMddyy(facilityList[i].endDate)) < new Date(formatDateMMddyy(sickDate))) {
            return "Ngày khởi phát Bệnh phải trước ngày Khám và điều trị";
        }
    }
    return "";
}

export function validSickWithDeath(sickDate, deathInfo) {
    if (deathInfo.date && new Date(formatDateMMddyy(sickDate)) > new Date(formatDateMMddyy(deathInfo.date))) {
        return "Ngày khởi phát Bệnh phải trước ngày Tử vong";
    }
    return "";
}

export function validSickDate(value, pSickInfo, facilityList, deathInfo) {
    const validSickWithDate = validSickDateWithFacilityDate(value, facilityList);
    let validWithDeath = validSickWithDeath(value, deathInfo);
    let todayDate = new Date();
    if (!value) {
        pSickInfo.errors.dateValid = "Quý khách cần nhập thời điểm khởi phát bệnh";
    } else if (new Date(formatDateMMddyy(pSickInfo.sickFoundTime)) > new Date(formatDateMMddyy(todayDate))) {
        pSickInfo.errors.dateValid = "Thời điểm khởi phát bệnh không được sau ngày hiện tại";
    } else if (validWithDeath) {
        pSickInfo.errors.dateValid = validWithDeath;
    } else if (validSickWithDate) {
        pSickInfo.errors.dateValid = validSickWithDate;
    } else {
        pSickInfo.errors.dateValid = "";
    }
    return pSickInfo;
}

//accident tab
export function validAccidentDateWithFacilityDate(accidentDate, facilityList) {
    if (!facilityList || facilityList.length === 0) {
        return "";
    }
    for (let i = 0; i < facilityList.length; i++) {
        if (facilityList[i].startDate && (new Date(formatDateMMddyy(facilityList[i].startDate)) < new Date(formatDateMMddyy(accidentDate)))) {
            return "Ngày xảy ra tai nạn phải trước ngày Khám và điều trị";
        } else if (facilityList[i].endDate && new Date(formatDateMMddyy(facilityList[i].endDate)) < new Date(formatDateMMddyy(accidentDate))) {
            return "Ngày xảy ra tai nạn phải trước ngày Khám và điều trị";
        }
    }
    return "";
}

export function validAccidentWithDeath(accidentDate, deathInfo) {
    if (deathInfo.date && new Date(formatDateMMddyy(accidentDate)) > new Date(formatDateMMddyy(deathInfo.date))) {
        return "Ngày xảy ra tai nạn phải trước ngày Tử vong";
    }
    return "";
}

export function validAccidentDate(value, pAccidentInfo, facilityList, deathInfo) {
    const validAccidentWithfDate = validAccidentDateWithFacilityDate(value, facilityList);
    const validWithDeath = validAccidentWithDeath(value, deathInfo);
    let todayDate = new Date();
    if (!value) {
        pAccidentInfo.errors.dateValid = "Quý khách cần nhập ngày giờ xảy ra tai nạn hợp lệ.";
    } else if (new Date(formatDateMMddyy(pAccidentInfo.date)) > new Date(formatDateMMddyy(todayDate))) {
        pAccidentInfo.errors.dateValid = "Ngày tai nạn không được sau ngày hiện tại";
    } else if (validWithDeath) {
        pAccidentInfo.errors.dateValid = validWithDeath;
    } else if (validAccidentWithfDate) {
        pAccidentInfo.errors.dateValid = validAccidentWithfDate;
    } else {
        pAccidentInfo.errors.dateValid = "";
    }
    return pAccidentInfo;
}

//Kiem tra list require object có chứa item không
export function checkListRequireContain(list, item) {
    if (!list || !item) {
        return false;
    }
    for (let i = 0; i < list.length; i++) {
        if (list[i].DocID === item.DocID) {
            return true;
        }
    }
    return false;
}

//get browser version
export function getBrowserVersion() {
    var ua = navigator.userAgent;
    var tem;
    var M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
    if (/trident/i.test(M[1])) {
        tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
        return 'IE ' + (tem[1] || '');
    }
    if (M[1] === 'Chrome') {
        tem = ua.match(/\b(OPR|Edge)\/(\d+)/);
        if (tem != null) return tem.slice(1).join(' ').replace('OPR', 'Opera');
    }
    M = M[2] ? [M[1], M[2]] : [navigator.appName, navigator.appVersion, '-?'];
    if ((tem = ua.match(/version\/(\d+)/i)) != null) M.splice(1, 1, tem[1]);
    return M.join('-');
}

export function getLinkId() {
    if (getSession(IRACE_ID)) {
        let dlvn_id = getSession(DCID);
        let cdyt_id = getSession(IRACE_ID);
        let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id + '|' + cdyt_id);
        let href = PAGE_IRACE_BASE + '?dlvn_id=' + dlvn_id + '&cdyt_id=' + cdyt_id + '&checksum=' + checksum;
        window.open(href);
    } else {
        let request = {
            jsonDataInput: {
                OS: WEB_BROWSER_VERSION,
                Platform: 'Web_mCP',
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'GetLinkIDByFunc3P',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getSession(DEVICE_ID),
                Project: 'mcp',
                DCID: getSession(DCID),
                FunctionID: '27',
                UserLogin: getSession(USER_LOGIN)
            }
        }
        GetConfiguration(request).then(res => {
            let Response = res.Response;
            if (Response.ErrLog === 'Successfull' && Response.Result === 'true' && Response.ClientProfile) {
                setSession(IRACE_ID, Response.ClientProfile[0].LinkID ? Response.ClientProfile[0].LinkID : '');
                if (!Response.ClientProfile[0].LinkID) {
                    document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
                } else {
                    let dlvn_id = getSession(DCID);
                    let cdyt_id = Response.ClientProfile[0].LinkID;
                    let checksum = md5(IRACE_SECRET_KEY + '|' + dlvn_id + '|' + cdyt_id);
                    let href = PAGE_IRACE_BASE + '?dlvn_id=' + dlvn_id + '&cdyt_id=' + cdyt_id + '&checksum=' + checksum;
                    window.open(href);
                }
            } else {
                document.getElementById('popup-irace-option-id').className = 'popup option-popup show';
            }
        }).catch(error => {
        });

    }

}

export function getEnsc(request, path) {

    fetch(API_BASE_URL + "/v1/partner/ensc", {
        method: "POST",
        headers: {"Content-Type": "text/plain"},
        body: AES256.encrypt(JSON.stringify(request), COMPANY_KEY2)
    })
        .then(response =>

            response.json().then(res => {
                // console.log("res", res);
                // console.log("path", path);

                if (!response.ok) {
                    window.location.href = path;
                }
                // history.push({
                //   pathname: path,
                //   search: `?data=${res.data}&signature=${res.signature}`,
                // });
                window.location.href = `${path}${(path.indexOf('?') >= 0)? '&': '/?'}data=${res.data}&signature=${res.signature}`;
            })
                .catch((error) => {
                    console.error(error);
                    window.location.href = `/${path}`;
                })
        );
}

export function getLinkPartner(partnerId, baseUrl) {
    if (getSession(partnerId)) {
        let request = {
            company: "DLVN",
            partner_code: EDOCTOR_CODE,
            partnerid: getSession(partnerId),
            deviceid: getDeviceId(),
            dcid: getSession(DCID),
            token: getSession(ACCESS_TOKEN),
            having_partnerid: "YES",
            role: getSession(CLIENT_ID)?"EXIST":"PUBLIC",
            timeinit: new Date().getTime()
        }
        if (partnerId === EDOCTOR_ID) {
            getEnsc(request, baseUrl);
        } else {
            // console.log('to do another partner');
        }
    } else {
        let request = {
            jsonDataInput: {
                OS: WEB_BROWSER_VERSION,
                Platform: 'Web_mCP',
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'GetLinkIDByFunc3P',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Project: 'mcp',
                DCID: getSession(DCID),
                FunctionID: PARTNER_ID_MAP[partnerId],
                UserLogin: getSession(USER_LOGIN)
            }
        }
        GetConfiguration(request).then(res => {
            let Response = res.Response;
            if (Response.ErrLog === 'Successfull' && Response.Result === 'true' && Response.ClientProfile) {
                setSession(partnerId, Response.ClientProfile[0].LinkID ? Response.ClientProfile[0].LinkID : '');
                request = {
                    company: "DLVN",
                    partner_code: EDOCTOR_CODE,
                    partnerid: '',
                    deviceid: getDeviceId(),
                    dcid: getSession(DCID),
                    token: getSession(ACCESS_TOKEN),
                    having_partnerid: "NO",
                    role: getSession(CLIENT_ID)?"EXIST":"PUBLIC",
                    timeinit: new Date().getTime()
                }
                if (Response.ClientProfile[0].LinkID) {
                    request.partnerid = Response.ClientProfile[0].LinkID;
                    request.having_partnerid = 'YES';
                }
                if (partnerId === EDOCTOR_ID) {
                    getEnsc(request, baseUrl);
                } else if (partnerId === AKTIVOLABS_ID) {
                    request = {
                        jsonDataInput:{
                           APIToken:getSession(ACCESS_TOKEN),
                           Action:"GetAuthenPartner",
                           Authentication:AUTHENTICATION,
                           ClientID:getSession(CLIENT_ID),
                           DCID:getSession(DCID),
                           DeviceId:getDeviceId(),
                           OS:WEB_BROWSER_VERSION,
                           PartnerCode:"AKTIVOLABS",
                           Project:"mcp",
                           UserLogin:getSession(USER_LOGIN)
                        }
                     }
                     getAktivoAuthenticate(request).then(r => {
                        let Resp = r.Response;
                        console.log(Resp);
                        if (Resp.ErrLog === 'Successfull' && Resp.Result === 'true' && Resp.ClientProfile) {
                            if (Resp.ClientProfile[0].access_token) {
                                setSession(AKTIVO_ACCESS_TOKEN, Resp.ClientProfile[0].access_token);
                            }
                            if (Resp.ClientProfile[0].member_id) {
                                setSession(AKTIVO_MEMBER_ID, Resp.ClientProfile[0].member_id);
                            }
                        } else {
                            console.log('get authenticate fail');
                        }
                    }).catch(error => {
                        console.log('get authenticate have error: ');
                        console.log(error);
                    });
                } else {
                    // console.log('to do another partner 2');
                }
            } else {
                console.log('get linkid have fail');
            }
        }).catch(error => {
            console.log('get linkid have error: ' + error);
        });

    }

}

export function getLinkAktivoPartner(partnerId) {
        let request = {
            jsonDataInput: {
                OS: WEB_BROWSER_VERSION,
                Platform: 'Web_mCP',
                APIToken: getSession(ACCESS_TOKEN),
                Action: 'GetLinkIDByFunc3P',
                Authentication: AUTHENTICATION,
                ClientID: getSession(CLIENT_ID),
                DeviceId: getDeviceId(),
                Project: 'mcp',
                DCID: getSession(DCID),
                FunctionID: PARTNER_ID_MAP[partnerId],
                UserLogin: getSession(USER_LOGIN)
            }
        }
        GetConfiguration(request).then(res => {
            request = {
                jsonDataInput:{
                    APIToken:getSession(ACCESS_TOKEN),
                    Action:"GetAuthenPartner",
                    Authentication:AUTHENTICATION,
                    ClientID:getSession(CLIENT_ID),
                    DCID:getSession(DCID),
                    DeviceId:getDeviceId(),
                    OS:WEB_BROWSER_VERSION,
                    PartnerCode:"AKTIVOLABS",
                    Project:"mcp",
                    UserLogin:getSession(USER_LOGIN),
                    ForceNewToken: "1"
                }
                }
                getAktivoAuthenticate(request).then(r => {
                let Resp = r.Response;
                if (Resp.ErrLog === 'Successfull' && Resp.Result === 'true' && Resp.ClientProfile) {
                    if (Resp.ClientProfile[0].access_token) {
                        setSession(AKTIVO_ACCESS_TOKEN, Resp.ClientProfile[0].access_token);
                    }
                    if (Resp.ClientProfile[0].member_id) {
                        setSession(AKTIVO_MEMBER_ID, Resp.ClientProfile[0].member_id);
                    }
                } else {
                    console.log('get authenticate fail');
                }
            }).catch(error => {
                console.log('get authenticate have error: ');
                console.log(error);
            });
 
        }).catch(error => {
            console.log('get linkid have error: ' + error);
        });


}

export function sessionStorageSpace() {
    var data = '';

    console.log('Current session storage: ');

    for (var key in window.sessionStorage) {

        if (window.sessionStorage.hasOwnProperty(key)) {
            data += window.sessionStorage[key];
            console.log(key + " = " + ((window.sessionStorage[key].length * 16) / (8 * 1024)).toFixed(2) + ' KB');
        }

    }

    console.log(data ? '\n' + 'Total space used: ' + ((data.length * 16) / (8 * 1024)).toFixed(2) + ' KB' : 'Empty (0 KB)');
    console.log(data ? 'Approx. space remaining: ' + (5120 - ((data.length * 16) / (8 * 1024)).toFixed(2)) + ' KB' : '5 MB');
}

export function convertImageUuidToAlt(imageUuid, data) {
    const item = data?.find(item => item?.imageUuid === imageUuid);
    if (item) {
        return item?.alt;
    } else {
        return "Banner";
    }
}


export function getDeviceId() {
    if (getSession(DEVICE_ID)) {
        return getSession(DEVICE_ID);
    } else {
        const deviceId = uuidv4().replaceAll('-', '');
        setSession(DEVICE_ID, deviceId);
        return deviceId;
    }
}

export function hasQueryParams(url) {
    return url.includes('?');
}

export function buildParamsAndRedirect(path) {
    let request = '';
    if (!getSession(ACCESS_TOKEN)) {
        request = {
            company: "DLVN",
            partner_code: EDOCTOR_CODE,
            deviceid: getDeviceId(),
            role: getSession(CLIENT_ID)?"EXIST":"PUBLIC",
            timeinit: new Date().getTime()
        }
        getEnsc(request, path);
    } else {
        request = {
            company: "DLVN",
            partner_code: EDOCTOR_CODE,
            partnerid: "",
            deviceid: getDeviceId(),
            dcid: getSession(DCID),
            token: getSession(ACCESS_TOKEN),
            having_partnerid: "NO",
            timeinit: new Date().getTime()
        }
        getLinkPartner (EDOCTOR_ID, path);

    }


}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
export async function pauseMe(ms) {
    await sleep(ms);
}

export function isOlderThan18(dob) {
    const birthDate = new Date(dob);
    const currentDate = new Date();

    // Calculate the age
    let age = currentDate.getFullYear() - birthDate.getFullYear();
    const monthDiff = currentDate.getMonth() - birthDate.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < birthDate.getDate())) {
        age--;
    }
    // Check if the person is older than 18
    return age >= 18;
}

export function  filterLI(clientProfile) {
    let clientProfileFiltered = clientProfile.filter(item => (item.Role === 'LI'));
    return clientProfileFiltered;
}

export function cpSaveLog(functionName) {
    const masterRequest = {
        jsonDataInput: {
            OS: "Web",
            APIToken: getSession(ACCESS_TOKEN),
            Authentication: AUTHENTICATION,
            ClientID: getSession(CLIENT_ID),
            DeviceId: getDeviceId(),
            DeviceToken: "",
            function: functionName,
            Project: "mcp",
            UserLogin: getSession(USER_LOGIN)
        }
    }
    CPSaveLog(masterRequest).then(res => {
    }).catch(error => {

    });
}

export function cpSaveLogSDK(functionName, apiToken, deviceId, clientId) {
    const masterRequest = {
        jsonDataInput: {
            OS: "Web",
            APIToken: apiToken,
            Authentication: AUTHENTICATION,
            ClientID: clientId,
            DeviceId: deviceId,
            DeviceToken: "",
            function: functionName,
            Project: "mcp",
            UserLogin: clientId
        }
    }
    CPSaveLog(masterRequest).then(res => {
    }).catch(error => {
    });
}

export function formatTime(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
}

export function deleteND13DataTemp(clientID, dKey, apiToken, deviceId) {
    let request = {
        jsonDataInput: {
            Action: "DeleteND13Data",
            APIToken: apiToken,
            Authentication: AUTHENTICATION,
            DKey: dKey,
            DeviceId: deviceId,
            OS: WEB_BROWSER_VERSION,
            Project: "mcp",
            UserLogin: clientID
        }
    };
    
    CPConsentConfirmation(request)
        .then(res => {
            const Response = res.Response;
            if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true') {
                console.log("DeleteND13Data success!");
            } else {
                console.log("DeleteND13Data error!");
            }
        })
        .catch(error => {
            console.log(error);
        });
}

export function mapConsentStatusToTranslatedStatus(consentStatus) {
    switch (consentStatus) {
        case 'NULL_OR_EMPTY':
            return TranslatedStatus.NULL_OR_EMPTY;
        case 'WaitConfirm':
            return TranslatedStatus.WAIT_CONFIRM;
        case 'Declined':
            return TranslatedStatus.DECLINED;
        case 'Agreed':
            return TranslatedStatus.AGREED;
        case 'Expired':
            return TranslatedStatus.EXPIRED;
        default:
            return 'Unknown Status';
    }
}

export function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

export function isOnlyNormalText(str) {
    if(!str) {
        return true;
    }
    // const regex = /^[a-zA-Z0-9\s]*$/;
    const regex = /^[a-zA-Z0-9]+$/;
    return regex.test(str);
}

export function setViewportScale(scale) {
    let viewportMeta = document.querySelector('meta[name="viewport"]');
    if (!viewportMeta) {
        viewportMeta = document.createElement('meta');
        viewportMeta.setAttribute('name', 'viewport');
        document.head.appendChild(viewportMeta);
    }
    viewportMeta.setAttribute('content', `width=device-width, initial-scale=1.0, maximum-scale=${scale}, user-scalable=no`);
}

export function removeSpecialCharacters(text) {
    // Chỉ giữ lại các ký tự chữ cái, số, khoảng trắng, @ và .
    return text.replace(/[^a-zA-Z0-9\s@.]/g, '');
}

export function removeSpecialCharactersAndSpace(text) {
    // Chỉ giữ lại các ký tự chữ cái, số, @ và .
    return text.replace(/[^a-zA-Z0-9@.]/g, '');
}

// Hàm lấy ngày từ chuỗi yyyy-mm-dd
export function getDay(dateString) {
    return new Date(dateString).getDate();
}

export function getMonth(dateString) {
    return new Date(dateString).getMonth();
}

export function getOperatingSystem() {
    let userAgent = window.navigator.userAgent;
    let platform = window.navigator.platform;
    let os = null;
    if (platform.indexOf('Win') !== -1) {
        os = 'Windows';
    } else if (platform.indexOf('Mac') !== -1) {
        os = 'MacOS';
    } else if (platform.indexOf('Linux') !== -1) {
        os = 'Linux';
    } else if (/Android/.test(userAgent)) {
        os = 'Android';
    } else if (/iPhone|iPad|iPod/.test(userAgent)) {
        os = 'iOS';
    } else {
        os = 'Unknown';
    }
    return os;
}

export function getOSVersion() {
    let userAgent = navigator.userAgent;
    let osVersion = "Unknown OS Version";

    if (/android/i.test(userAgent)) {
        let match = userAgent.match(/Android\s([0-9\.]+)/);
        if (match) osVersion = "Android " + match[1];
    } else if (/iPad|iPhone|iPod/.test(userAgent) && !window.MSStream) {
        let match = userAgent.match(/OS\s([0-9_]+)/);
        if (match) osVersion = "iOS " + match[1].replace(/_/g, '.');
    } else if (userAgent.indexOf("Windows NT 10.0; Win64; x64") !== -1) {
        osVersion = "Windows 11";
    } else if (userAgent.indexOf("Windows NT 10.0") !== -1) {
        osVersion = "Windows 10";
    } else if (userAgent.indexOf("Windows NT 6.3") !== -1) {
        osVersion = "Windows 8.1";
    } else if (userAgent.indexOf("Windows NT 6.2") !== -1) {
        osVersion = "Windows 8";
    } else if (userAgent.indexOf("Windows NT 6.1") !== -1) {
        osVersion = "Windows 7";
    } else if (userAgent.indexOf("Mac OS X") !== -1) {
        osVersion = "Mac OS X";
    }

    return osVersion;
}
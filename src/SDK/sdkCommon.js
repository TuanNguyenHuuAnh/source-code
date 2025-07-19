import {DCID, COMPANY_KEY2, DEVICE_ID, ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, USER_LOGIN, GENDER_MAP, CLAIM_TYPE} from "./sdkConstant";
import {CPSaveLog, CPConsentConfirmation} from './sdkAPI';
import AES256 from 'aes-everywhere';
import ReactGA from "react-ga4";
import {v4 as uuidv4} from 'uuid';
import {db} from './db';
import dayjs from 'dayjs';
import parse from 'html-react-parser';
import toast, {Toaster} from 'react-hot-toast';
import moment from "moment";
import { gzipSync, gunzipSync, strToU8, strFromU8 } from 'fflate';

export function getDeviceId() {
    if (getSession(DEVICE_ID)) {
        return getSession(DEVICE_ID);
    } else {
        const deviceId = uuidv4().replaceAll('-', '');
        setSession(DEVICE_ID, deviceId);
        return deviceId;
    }
}

export function setDeviceId(deviceId) {
    if (deviceId) {
        setSession(DEVICE_ID, deviceId);
    }
}

export function setSession(key, value) {
    if (!key || !value) {
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

export const trackingEvent = (category, action, label, from, error, errorDesc) => {
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
        custom_user_id: (getSession(DCID)?getSession(DCID):''),
        Crash_log: error? error: '',
        MessageDesc: errorDesc? errorDesc: ''
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

export function genPassword(n) {
    var length = n,
        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789*#$",
        retVal = "";
    for (var i = 0, n = charset.length; i < length; ++i) {
        retVal += charset.charAt(Math.floor(Math.random() * n));
    }
    return retVal;
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

export function ddMMyyyyToDate(dateString) {
    var dateParts = dateString.split("/");
    // month is 0-based, that's why we need dataParts[1] - 1
    var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
    return dateObject;
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

export function cpSaveLog(functionName, Description, Exception) {
    const masterRequest = {
        jsonDataInput: {
            OS: "Web",
            APIToken: getSession(ACCESS_TOKEN),
            Authentication: AUTHENTICATION,
            ClientID: getSession(CLIENT_ID),
            DeviceId: getDeviceId(),
            DeviceToken: "",
            function: functionName?functionName: '',
            Project: "mcp",
            UserLogin: getSession(USER_LOGIN),
            Description: Description? Description: '',
            Exception: Exception? Exception: ''
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

export function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(window.location.search);
    return results === null ? '' : decodeURIComponent(results[1]);
}

export function getUrlParameterNoDecode(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(window.location.search);
    return results === null ? '' : results[1];
}

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

export function formatDateFromDate(date) {
    if (!date) {
        return date;
    }
    var d = date,
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
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

export function callbackAppOpenLink(url, from) {
    let obj = {
        Action: "LINK_OPEN",
        Link: url
    };
    if (from && (from === "Android")) {//for Android
        if (window.AndroidAppCallback) {
            window.AndroidAppCallback.postMessage(JSON.stringify(obj));
        }
    } else if (from && (from === "IOS")) {//for IOS
        if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
            window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
        }
    }
}

export function generateConsentResults(data) {
    const result = {};
    data.forEach((item, index) => {
        const role = item.Role;
        let key;
        if (role === 'PO') {
            key = 'ConsentResultPO';
        } else {
            // key = `ConsentResultLI_${index + 1}`;
            key = 'ConsentResultLI';
        }
        if (item.ConsentRuleID === 'ND13') {
            result[key] = item.ConsentResult;
        }
        
    });
    return result;
}

export function getMapSize(x) {
    if (!x) {
        return 0;
    }
    var len = 0;
    for (var count in x) {
        len++;
    }

    return len;
}

export function makeStr(len, char) {
    return Array.prototype.join.call({length: (len || -1) + 1}, char || 'x');
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

export function maskEmail(email) {
    if (!email || email.indexOf('@') < 0) {
        // Empty or no '@' character found, return the original email
        return '';
    }

    let [sub, pre] = email.split('@');
    let chars = sub.split('');

    for (let i = chars.length - 1; i >= 4; i--) {
        if (!/\s/.test(chars[i])) {
            chars[i] = '*';
        }
    }

    return chars.join('') + '@' + pre;
}

/**sdk claim */
export function checkMapHaveTrue(x) {
    for (var count in x) {
        if (x[count]) {
            return true;
        }
    }
    return false;
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
        console.log(`setLocal Error: ${e}`);
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
export function haveHC_HS(claimCheckedMap, claimTypeList) {
    let haveHCHSType = false;
    for (var type in claimCheckedMap) {
        if (claimCheckedMap[type] && (type === CLAIM_TYPE.HEALTH_CARE)) {
            if (claimTypeList) {
                let claimType = claimTypeList.find(item=>item.claimType === type);
                if (claimType?.isHc3 === '0') {
                    console.log('claimType=', claimType);
                    console.log('isHc3', claimType?.isHc3)
                    haveHCHSType = true;
                    // break;
                }
            } else {
                haveHCHSType = true;
                // break;
            } 

        }
        if (claimCheckedMap[type] && (type === CLAIM_TYPE.HS)) {
            haveHCHSType = true;
            // break;
        }
    }
    if (haveHCHSType) {
        return true;
    } else {
        return false;
    }
}

export function isHC3(claimCheckedMap, claimTypeList) {
    let isHC3 = false;
    if (claimCheckedMap[CLAIM_TYPE.HEALTH_CARE]) {
        if (claimTypeList) {
            let claimType = claimTypeList.find(item=>item.claimType === CLAIM_TYPE.HEALTH_CARE);
            if (claimType?.isHc3 === '1') {
                console.log('claimType=', claimType);
                console.log('isHc3', claimType?.isHc3)
                isHC3 = true;
            }
        } 

    }
    return isHC3;
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

export const VALID_EMAIL_REGEX =
    RegExp(/^(([^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i);

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

export function onlyLettersAndSpaces(str) {
    return !/\d/.test(str);
}

export function is18Plus(DOB) {
    let birthday = new Date(DOB);
    var ageDifMs = Date.now() - birthday.getTime();
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
    return Math.abs(ageDate.getUTCFullYear() - 1970) >= 18;
}

export function checkValue(value) {
    return !value ? '' : value;
};

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

export function sumInvoiceMicro(invoiceList) {
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

export function removeSession(key) {
    if (!key) {
        return;
    }
    sessionStorage.removeItem(key);
    //localStorage.removeItem(key);
}

export function isInteger(value) {
    return /^[0-9]+$/.test(value);
};

export function getOSAndBrowserInfo() {
    var userAgent = navigator.userAgent;
    var platform = navigator.platform;
    var os = "Unknown OS";
    var browser = "Unknown Browser";

    // Detect OS
    if (platform.indexOf('Win') !== -1) os = "Web Windows";
    else if (platform.indexOf('Mac') !== -1) os = "Web MacOS";
    else if (platform.indexOf('Linux') !== -1) os = "Linux";
    else if (/Android/.test(userAgent)) os = "Android";
    else if (/iPhone|iPad|iPod/.test(userAgent)) os = "iOS";

    // Detect Browser
    if (/Chrome/.test(userAgent) && /Google Inc/.test(navigator.vendor)) browser = "Google Chrome";
    else if (/Safari/.test(userAgent) && /Apple Computer/.test(navigator.vendor)) browser = "Safari";
    else if (/Firefox/.test(userAgent)) browser = "Mozilla Firefox";
    else if (/MSIE|Trident/.test(userAgent)) browser = "Internet Explorer";
    else if (/Edge/.test(userAgent)) browser = "Microsoft Edge";

    return os + ' ' + browser;
}

export function deleteND13DataTemp(clientID, dKey, apiToken, deviceId) {
    let request = {
        jsonDataInput: {
            Action: "DeleteND13Data",
            APIToken: apiToken,
            Authentication: AUTHENTICATION,
            DKey: dKey,
            DeviceId: deviceId,
            OS: getOSAndBrowserInfo(),
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

export function getGenderCode(gender) {
    for (let genderCode in GENDER_MAP) {
        if (GENDER_MAP[genderCode] === gender) {
            return genderCode;
        }
    }
    return '';
}

export function capitalizeFirstLetter(str) {
    return str.split(' ').map(word => {
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    }).join(' ');
}

export function isFloat(str) {
    return !isNaN(str) && !isNaN(parseFloat(str));
}

export function openBase64(data) {
    var image = new Image();
    image.src = data;
    var w = window.open("");
    w.document.write(image.outerHTML);
}

export function disabledFutureDate(current) {
    console.log('current=', current);
    console.log('endofday=', dayjs().endOf('day'));
    console.log('booleean endofday=', current && (current > dayjs().endOf('day')));
    return current && (current > dayjs().endOf('day'));
}

export function decodeHtmlEntity(str) {
    const txt = document.createElement("textarea");
    txt.innerHTML = str;
    return txt.value;
}
export function clearSession() {
    sessionStorage.clear();
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

export function parseHtml(str) {
    return parse(str);
}

export function buildMicroRequest(metadata, data) {
    let request = {
        requestId: genRequestId(),
        metadata: metadata,
        data: data
    }
    return request;
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

export function genRequestId() {
    return uuidv4().replaceAll('-', '');
}

export function toLowerCase(value) {
    if (!value) {
        return value;
    }
    return value.toLowerCase();
}

export function toUpperCase(value) {
    if (!value) {
        return value;
    }
    return value.toUpperCase();
}

export function removeSpecialCharactersAndSpace(text) {
    // Chỉ giữ lại các ký tự chữ cái, số, @ và .
    return text.replace(/[^a-zA-Z0-9]/g, '');
}


export function convertObjectToArray(obj) {
    let result = [];
    for (let key in obj) {
        if (obj.hasOwnProperty(key)) {
            result = result.concat(obj[key].map(item => item.imgData));
        }
    }
    return result;
}
    
export function removeEmptyEntries(map) {
    map.forEach((value, key) => {
        if (value === null || value === undefined || value === '') {
            map.delete(key);
        }
    });
    return map;
}

export function getBenifits(claimCheckedMap) {
    let benefits = '';
    for (const it in claimCheckedMap) {
        if (claimCheckedMap[it]) {
            if (!benefits) {
                benefits = claimCheckedMap[it];
            } else {
                benefits = benefits + ', ' + claimCheckedMap[it];
            }
        }
    }
    return benefits;
}

export function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

export function isMobile() {
    let check = false;
    (function (a) {
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) check = true;
    })(navigator.userAgent || navigator.vendor || window.opera);
    return check;
}



export function convertDateToISO(dateString) {
    if (!dateString) {
        return dateString;
    }
    try {
        if (moment.isMoment(dateString)) {
            dateString = dateString.format("YYYY-MM-DD");
        } 
        let parts;
        console.log('dateString=', dateString);
        if (dateString.indexOf('/') > 0) {
            // Format dd/MM/yyyy
            parts = dateString.split('/');
            return formatDate3(`${parts[2]}-${parts[1].padStart(2, '0')}-${parts[0].padStart(2, '0')}`);
        } else if (dateString.indexOf('-') > 0) {
            // Format yyyy-MM-dd
            return formatDate3(dateString);
        } else {
            return dateString;
        }
    } catch (error) {
        return dateString;
    }

}

/***
 * Convert dd/MM/yyyy sang MM/dd/yyyy
 */
export function convertDateToUSFormat(dateString) {
    let parts = dateString.split('/');
    if (parts.length !== 3) {
        return dateString;
    }
    return `${parts[1].padStart(2, '0')}/${parts[0].padStart(2, '0')}/${parts[2]}`;
}

/**
 * Convert dateString to dd/mm/yyyy hh:mm
 * @param {*} dateTimeStr 
 * @returns 
 */
export function convertDateTime(dateTimeStr) {
    const dateTime = new Date(dateTimeStr);
    const day = String(dateTime.getDate()).padStart(2, '0');
    const month = String(dateTime.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    const year = dateTime.getFullYear();
    const hours = String(dateTime.getHours()).padStart(2, '0');
    const minutes = String(dateTime.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
}

export function formatDateString(dateString) {
    if (!dateString) {
        return dateString;
    }

    // Kiểm tra nếu đối tượng là Moment
    if (moment.isMoment(dateString)) {
        return dateString.format('DD/MM/YYYY');
    }

    let parts;
    if (dateString.includes('/')) {
        // Format dd/MM/yyyy
        parts = dateString.split('/');
        if (dateString.length > 10) {
            dateString = dateString.substring(0, 10);
        }
        return `${parts[0]}/${parts[1]}/${parts[2]}`;
    } else if (dateString.includes('-')) {
        // Format yyyy-MM-dd
        if (dateString.length > 10) {
            dateString = dateString.substring(0, 10);
        }
        parts = dateString.split('-');
        return `${parts[2]}/${parts[1]}/${parts[0]}`;
    } else {
        return dateString;
    }
}

// Hàm lấy ngày từ chuỗi yyyy-mm-dd
export function getDay(dateString) {
    return new Date(dateString).getDate();
}

export function getMonth(dateString) {
    return new Date(dateString).getMonth();
}

/**
 * Chuyển Uint8Array thành chuỗi base64 an toàn (không dùng spread)
 */
function uint8ArrayToBase64(uint8Array) {
    let binary = '';
    for (let i = 0; i < uint8Array.length; i++) {
        binary += String.fromCharCode(uint8Array[i]);
    }
    return btoa(binary);
}

/**
 * Chuyển chuỗi base64 thành Uint8Array
 */
function base64ToUint8Array(base64) {
    const binary = atob(base64);
    const len = binary.length;
    const bytes = new Uint8Array(len);
    for (let i = 0; i < len; i++) {
        bytes[i] = binary.charCodeAt(i);
    }
    return bytes;
}

/**
 * Nén JSON thành chuỗi base64 bằng fflate (gzip)
 * @param {Object} json - Đối tượng JSON cần nén
 * @returns {string} - Chuỗi base64 sau khi nén
 */
export function compressJson(json) {
    const jsonStr = JSON.stringify(json);
    console.log('The json input size=', jsonStr.length);
    const compressed = gzipSync(strToU8(jsonStr));
    return uint8ArrayToBase64(compressed);
}

/**
 * Giải nén chuỗi base64 thành JSON bằng fflate (gunzip)
 * @param {string} base64Str - Chuỗi base64 đã nén
 * @returns {Object} - Đối tượng JSON sau khi giải nén
 */
export function decompressJson(base64Str) {
    if (!base64Str || (base64Str.trim() === "")) {
        console.warn("Input is empty. Returning null.");
        return null;
    }

    try {
        const compressedBytes = base64ToUint8Array(base64Str);
        const decompressed = gunzipSync(compressedBytes);
        return JSON.parse(strFromU8(decompressed));
    } catch (error) {
        console.error("Decompression failed:", error);
        return null;
    }
}


export function scrollUpFraction() {
    console.log('scrollUpFraction.........');
    const screenHeight = window.innerHeight;
    const scrollAmount = screenHeight / 5;
    console.log('scrollAmount=', scrollAmount);
    window.scrollBy({ top: -scrollAmount, behavior: 'smooth' });
    console.log('End scrollUpFraction.........');
}

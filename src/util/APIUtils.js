import {
    ACCESS_TOKEN,
    API_BASE_URL,
    AUTHENTICATION,
    COMPANY_KEY,
    COMPANY_KEY2,
    LOG_OUTED,
    USER_LOGIN,
    OAUTH2_BASE_URL,
    COMPANY_KEY3 
} from '../constants';
import {trackPromise} from 'react-promise-tracker';
import AES256 from 'aes-everywhere';
import {getDeviceId, getSession, setSession, clearSession, clearOriginalSession, clearLocalStorage} from './common';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    if (options.key) {
        headers.append('key', options.key)
    }
    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response =>

            response.json().then(json => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return JSON.parse(AES256.decrypt(json.JsonData, COMPANY_KEY2));
            })
        );
};

const request2 = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return trackPromise(
        fetch(options.url, options)
            .then(response =>
                response.json().then(json => {
                    if (!response.ok) {
                        return Promise.reject(json);
                    }
                    return json;
                })), 'request-loading-area');
};

const request3 = (options, contentType) => {
    const headers = new Headers({
        'Content-Type': contentType,
    })

    if (options.key) {
        headers.append('key', options.key)
    }
    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response =>

            response.json().then(json => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                if (json.JsonData) {
                    return JSON.parse(AES256.decrypt(json.JsonData, COMPANY_KEY3));
                } else {
                    return json;
                }
                
            })
        );
};

const requestWithLoading = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })

    if (options.key) {
        headers.append('key', options.key)
    }

    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return trackPromise(
        fetch(options.url, options)
            .then(response =>
                response.json().then(json => {
                    if (!response.ok) {
                        return Promise.reject(json);
                    }
                    return JSON.parse(AES256.decrypt(json.JsonData, COMPANY_KEY2));
                })), JSON.parse(options.body).loadingArea);
};

export function loginSocial(path) {
    return request({
        url: API_BASE_URL + path,
        method: 'GET'
    });
}

export function downloadFile(fileName) {
    if (!getSession(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/v1/download/" + fileName,
        method: 'GET'
    });
}
export function uploadImage(submitRequest) {
    submitRequest['loadingArea'] = "upload-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/image",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function checkAvatar(submitRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/check",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function enscryptData(submitRequest) {
    return request2({
        url: API_BASE_URL + "/v1/user/enscryptData",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function fetchTagsData(submitRequest) {
    return request({
        url: API_BASE_URL + "/v1/partner/tags",
        method: 'POST',
        body:  AES256.encrypt(JSON.stringify(submitRequest), COMPANY_KEY2)
    });
}

export function fetchDoctorsData(submitRequest) {
    return request({
        url: API_BASE_URL + "/v1/partner/doctors",
        method: 'POST',
        body:  AES256.encrypt(JSON.stringify(submitRequest), COMPANY_KEY2)
    });
}
export function synchronizedNotiAPIService(submitRequest) {
    return request({
        url: API_BASE_URL + "/v1/partner/notify",
        method: 'POST',
        body:  AES256.encrypt(JSON.stringify(submitRequest), COMPANY_KEY2)
    });
}

export function getFormList() {
    if (!getSession(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/v1/download/list",
        method: 'GET'
    });
}

export function login(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function logout(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/logout",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function checkAuthZalo(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/checkAuthZalo",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function checkCCTokenExpire(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPProposalConfirm",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function checkZoomToken(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/mAGPCheckToken",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function login2(loginRequest, isByPass) {
    let vlogin = "/v1/user/login2";
    if (isByPass) {
        vlogin = "/v1/user/login";
    }
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + vlogin,
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    signupRequest['loadingArea'] = "signup-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/registration",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function logoutSession() {
    if (document.getElementById('header-id')) {
        document.getElementById('header-id').className = '';
    }
    const submitRequest = {
        jsonDataInput: {
            Company: COMPANY_KEY,
            Action: 'LogOut',
            APIToken: getSession(ACCESS_TOKEN),
            Authentication: AUTHENTICATION,
            DeviceId: getDeviceId(),
            DeviceToken: '',
            UserLogin: getSession(USER_LOGIN),
            OS: '',
            Project: 'mcp',
            Password: ''
        }
    };
    logout(submitRequest)
        .then(response => {
            if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
                //console.log('Logout successful!');
            } else {
                //console.log('logout fail!');
            }
        }).catch(error => {
        });

    clearSession();
    // clearLocalStorage();
    setSession(LOG_OUTED, LOG_OUTED);
}

export function CPGetPolicyListByCLIID(CPGetPolicyListByCLIIDRequest) {
    CPGetPolicyListByCLIIDRequest['loadingArea'] = "policyList-by-cliID";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPGetPolicyListByCLIID",
        method: 'POST',
        body: JSON.stringify(CPGetPolicyListByCLIIDRequest)
    });
}

export function CPServicesPSPaymentProcess(CPGetPolicyListByCLIIDRequest) {
    CPGetPolicyListByCLIIDRequest['loadingArea'] = "policyList-by-cliID";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPServicesPSPaymentProcess",
        method: 'POST',
        body: JSON.stringify(CPGetPolicyListByCLIIDRequest)
    });
}

export function CPServicesPSPaymentProcessList(CPGetPolicyListByCLIIDRequest) {
    CPGetPolicyListByCLIIDRequest['loadingArea'] = "policyList-by-cliID";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPServicesPSPaymentProcessList",
        method: 'POST',
        body: JSON.stringify(CPGetPolicyListByCLIIDRequest)
    });
}

export function Pay_HistoryPaymentDetail(Pay_HistoryPaymentDetailRequest) {
    Pay_HistoryPaymentDetailRequest['loadingArea'] = "dtProposal-info";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/Pay_HistoryPaymentDetail",
        method: 'POST',
        body: JSON.stringify(Pay_HistoryPaymentDetailRequest)
    });
}

export function cms(cmsRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/cms",
        method: 'POST',
        body: JSON.stringify(cmsRequest)
    });
}

export function checkPolicyInfo(policyInfoRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/check-policy-info",
        method: 'POST',
        body: JSON.stringify(policyInfoRequest)
    });
}

export function genOTP(genOTPRequest) {
    genOTPRequest['loadingArea'] = "verify-otp";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/gen-otp",
        method: 'POST',
        body: JSON.stringify(genOTPRequest)
    });
}

export function CPGetClientProfileByCLIID(CPGetClientProfileByCLIIDRequest) {
    CPGetClientProfileByCLIIDRequest['loadingArea'] = "claim-li-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPGetClientProfileByCLIID",
        method: 'POST',
        body: JSON.stringify(CPGetClientProfileByCLIIDRequest)
    });

}
export function CPGetPolicyInfoByPOLID(CPGetPolicyInfoByPOLIDRequest) {
    CPGetPolicyInfoByPOLIDRequest['loadingArea'] = "policy-info";
    return request({
        url: API_BASE_URL + "/v1/user/CPGetPolicyInfoByPOLID",
        method: 'POST',
        body: JSON.stringify(CPGetPolicyInfoByPOLIDRequest)
    });
}
export function CPGetProductListByPOLID(CPGetProductListByPOLIDRequest) {
    CPGetProductListByPOLIDRequest['loadingArea'] = "policy-info";
    return request({
        url: API_BASE_URL + "/v1/user/CPGetProductListByPOLID",
        method: 'POST',
        body: JSON.stringify(CPGetProductListByPOLIDRequest)
    });
}

export function getClientInfo(clientInfoRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/get-client-info",
        method: 'POST',
        body: JSON.stringify(clientInfoRequest)
    });
}
export function getContactInfo(clientInfoRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/get-contact-info",
        method: 'POST',
        body: JSON.stringify(clientInfoRequest)
    });
}

export function verifyOTP(verifyOTPRequest) {
    verifyOTPRequest['loadingArea'] = "verify-otp";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/verify-otp",
        method: 'POST',
        body: JSON.stringify(verifyOTPRequest)
    });
}

export function verifyTokenOTP(verifyOTPRequest) {
    verifyOTPRequest['loadingArea'] = "verify-otp";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/verify-token-otp",
        method: 'POST',
        body: JSON.stringify(verifyOTPRequest)
    });
}

export function changePass(changePassRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/change-pass",
        method: 'POST',
        body: JSON.stringify(changePassRequest)
    });
}

export function checkPass(checkPassRequest) {
    checkPassRequest['loadingArea'] = "check-pass";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/check-pass",
        method: 'POST',
        body: JSON.stringify(checkPassRequest)
    });
}

export function checkAccount(checkAccountRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/check-account",
        method: 'POST',
        body: JSON.stringify(checkAccountRequest)
    });
}

export function switchTwoFA(twoFARequest) {
    return request({
        url: API_BASE_URL + "/v1/user/switch-two-fa",
        method: 'POST',
        body: JSON.stringify(twoFARequest)
    });
}

export function forgotPass(forgotPassRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/forgot-pass",
        method: 'POST',
        body: JSON.stringify(forgotPassRequest)
    });
}

export function getPointByClientID(pointRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/get-point",
        method: 'POST',
        body: JSON.stringify(pointRequest)
    });
}

export function searchPolicyHolder(policyHolderRequest) {
    policyHolderRequest['loadingArea'] = "give-point-info";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/point/CPSearchPolicyHolderByPolicyID",
        method: 'POST',
        body: JSON.stringify(policyHolderRequest)
    });
}

export function CPSubmitForm(submitRequest) {
    submitRequest['loadingArea'] = "claim-li-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/point/CPSubmitForm",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function CPSubmitAnswer(submitRequest) {
    submitRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPSubmitForm",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

// export function CPLoyaltyPointConfirmation(submitRequest) {
//     return request({
//         url: API_BASE_URL + "/v1/point/CPLoyaltyPointConfirmation",
//         method: 'POST',
//         body: JSON.stringify(submitRequest)
//     });
// }
export function CPLoyaltyPointConfirmation(submitRequest) {
    submitRequest['loadingArea'] = "submit-loyalty-point";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/point/CPLoyaltyPointConfirmation",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function getProductByCategory(productRequest) {
    productRequest['loadingArea'] = "point-product-info";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/point/CPGetProductByCategory",
        method: 'POST',
        body: JSON.stringify(productRequest)
    });
}

export function getPointAccountHistory(historyRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/CPGetPointAccountByCLIID",
        method: 'POST',
        body: JSON.stringify(historyRequest)
    });
}

export function iibGetMasterDataByType(typeRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/GetMasterDataByType",
        method: 'POST',
        body: JSON.stringify(typeRequest)
    });
}

export function iibGetShippingAddress(addressRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/CPProcessShippingAddress",
        method: 'POST',
        body: JSON.stringify(addressRequest)
    });
}

export function CPCalculateShippingFee(addressRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/CPCalculateShippingFee",
        method: 'POST',
        body: JSON.stringify(addressRequest)
    });
}

export function getEPolicyPdf(getEPolicyPdfRequest) {
    getEPolicyPdfRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/edocument/ePolicyPdf",
        method: 'POST',
        body: JSON.stringify(getEPolicyPdfRequest)
    });
}

export function GetTaxInvoice(taxInvoiceRequest) {
    taxInvoiceRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/edocument/GetTaxInvoice",
        method: 'POST',
        body: JSON.stringify(taxInvoiceRequest)
    });
}

export function getUserInfo(getUserInfoRequest) {
    getUserInfoRequest['loadingArea'] = "epolicy-user-info";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/edocument/getUserInfo",
        method: 'POST',
        body: JSON.stringify(getUserInfoRequest)
    });
}

export function getHCCard(hcCardRequest) {
    hcCardRequest['loadingArea'] = "HCCard-loading";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPGetHCCard",
        method: 'POST',
        body: JSON.stringify(hcCardRequest)
    });
}

export function refreshCaptcha(captchaRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/captcha",
        method: 'POST',
        body: JSON.stringify(captchaRequest)
    });
}

export function onlineRequestSubmit(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmit",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function onlineRequestSubmitUserInfo(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-loading";//add loading
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitUserInfo",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function onlineRequestSubmitESubmission(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitESubmission",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function onlineRequestSubmitESubmissionCSA(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitESubmissionCSA",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function onlineRequestSubmitConfirm(jsonRequest) {
    jsonRequest['loadingArea'] = "verify-otp";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitConfirm",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function CPSaveLog(jsonRequest) {
    jsonRequest['loadingArea'] = "online-save-log";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPSaveLog",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function GetConfiguration(jsonRequest) {
    jsonRequest['loadingArea'] = "link-id-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/GetConfiguration",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function CPConsentConfirmation(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPConsentConfirmation",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function iibGetSurveyInfo(typeRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/GetSurveyInfo",
        method: 'POST',
        body: JSON.stringify(typeRequest)
    });
}

export function iibSurveyInfoSubmit(typeRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/SurveyInfoSubmition",
        method: 'POST',
        body: JSON.stringify(typeRequest)
    });
}

export const removeUndefinedFields = (obj) => {
    const cleanedObj = {};
    for (const key in obj) {
        if (obj.hasOwnProperty(key) && obj[key] !== "undefined" && key !== "loadingArea") {
            if (typeof obj[key] === "object") {
                cleanedObj[key] = removeUndefinedFields(obj[key]);
            } else {
                cleanedObj[key] = obj[key];
            }
        }
    }
    return cleanedObj;
}



// Claim Utils - START
export function getZipCodeMasterData(getZipCodeMasterDataRequest) {
    getZipCodeMasterDataRequest['loadingArea'] = "zip-code-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getZipCodeMasterData",
        method: 'POST',
        body: JSON.stringify(getZipCodeMasterDataRequest)
    });
}
export function getBankMasterData(getBankMasterDataRequest) {
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getBankMasterData",
        method: 'POST',
        body: JSON.stringify(getBankMasterDataRequest)
    });
}
export function getLIListForClaim(getLIListForClaimRequest) {
    getLIListForClaimRequest['loadingArea'] = "claim-li-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getLIListForClaim",
        method: 'POST',
        body: JSON.stringify(getLIListForClaimRequest)
    });
}
export function getSelectedLIPolList(getSelectedLIPolListRequest) {
    getSelectedLIPolListRequest['loadingArea'] = "claim-selectedli-pol-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getSelectedLIPolList",
        method: 'POST',
        body: JSON.stringify(getSelectedLIPolListRequest)
    });
}
export function getLiBenefit(getLiBenefitRequest) {
    getLiBenefitRequest['loadingArea'] = "claim-li-benefit-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getLiBenefit",
        method: 'POST',
        body: JSON.stringify(getLiBenefitRequest)
    });
}
export function getDocType(getDocTypeRequest) {
    getDocTypeRequest['loadingArea'] = "info-required-claim-initial";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getDocType",
        method: 'POST',
        body: JSON.stringify(getDocTypeRequest)
    });
}
export function postClaimInfo(postClaimInfoRequest) {
    postClaimInfoRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/postClaimInfo",
        method: 'POST',
        body: JSON.stringify(postClaimInfoRequest)
    });
}
export function postClaimImage(postClaimImageRequest) {
    postClaimImageRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/postClaimImage",
        method: 'POST',
        body: JSON.stringify(postClaimImageRequest)
    });
}
// Claim Utils - END

// Utilities Utils - START
export function postContactForm(postContactFormRequest) {
    postContactFormRequest['loadingArea'] = "contact-form-submit";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/utilities/postContactForm",
        method: 'POST',
        body: JSON.stringify(postContactFormRequest)
    });
}
export function getFaq(getFaqRequest) {
    getFaqRequest['loadingArea'] = "faq-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/utilities/getFaq",
        method: 'POST',
        body: JSON.stringify(getFaqRequest)
    });
}
export function getOccupationList(getOccupationListRequest) {
    getOccupationListRequest['loadingArea'] = "occup-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/get-occupation",
        method: 'POST',
        body: JSON.stringify(getOccupationListRequest)
    });
}
export function getDoc(getDocRequest) {
    getDocRequest['loadingArea'] = "doc-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/utilities/getDoc",
        method: 'POST',
        body: JSON.stringify(getDocRequest)
    });
}

export function FeedbackSubmit(submitRequest) {
    submitRequest['loadingArea'] = "feedback-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/utilities/FeedbackSubmit",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}
// AccountInfo
export function checkAccountInfo(checkAccountInfoRequest) {
    checkAccountInfoRequest['loadingArea'] = "update-account-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/checkAccountInfo",
        method: 'POST',
        body: JSON.stringify(checkAccountInfoRequest)
    });
}
export function submitAccountUpdate(submitAccountUpdateRequest) {
    submitAccountUpdateRequest['loadingArea'] = "update-account-loading-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/submitAccountUpdate",
        method: 'POST',
        body: JSON.stringify(submitAccountUpdateRequest)
    });
}
// Utilities Utils - END

export function getNotification(clientInfoRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/getNotification",
        method: 'POST',
        body: JSON.stringify(clientInfoRequest)
    });
}

export function CPSubmit(submitRequest) {
    submitRequest['loadingArea'] = "submit-loading";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/point/CPSubmitForm",
        method: 'POST',
        body: JSON.stringify(submitRequest)
    });
}

export function cpCallMAGP(jsoninput) {
    jsoninput['loadingArea'] = "network-loading";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CallMAGP",
        method: 'POST',
        body: JSON.stringify(jsoninput)
    });
}

export function getClaimCheckhold(jsoninput) {
    jsoninput['loadingArea'] = "claim-check-hold";
    return request({
        url: API_BASE_URL + "/v1/user/claimCheckHold",
        method: 'POST',
        body: JSON.stringify(jsoninput)
    });
}
// export function getOccupationList(occupationRequest) {
//     return request({
//         url: API_BASE_URL + "/v1/user/get-occupation",
//         method: 'POST',
//         body: JSON.stringify(occupationRequest)
//     });
// }

// health cms content
export function getListByPath(path) {
    return request2({
        url: API_BASE_URL + "/v1/healthcms" + path,
        method: 'GET'
    });
}

export function loadLogin(devideId) {
    return request({
        url: OAUTH2_BASE_URL + "/getprofilebyid",
        method: 'POST',
        body: AES256.encrypt(devideId, COMPANY_KEY2)
    });
}

export function getChallenges(challengesRequest) {
    return request2({
        url: API_BASE_URL + "/v1/aktivo/challenges",
        method: 'POST',
        body: JSON.stringify(challengesRequest)
    });
}

export function getChallengeDetail(challengeRequest) {
    return request2({
        url: API_BASE_URL + "/v1/aktivo/challenge-detail",
        method: 'POST',
        body: JSON.stringify(challengeRequest)
    });
}

export function getAktivoAuthenticate(authenticateRequest) {
    return request({
        url: API_BASE_URL + "/v1/aktivo/get-aktivo-authenticate",
        method: 'POST',
        body: JSON.stringify(authenticateRequest)
    });
}

//START SDK edoc viewer premium letter
export function GetDocumentPremium(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetDocumentPremium",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getDocumentList(getEdocumentRequest) {
    return request3({
        url: API_BASE_URL + "/v1/sdk/getDocumentList",
        method: 'POST',
        body: AES256.encrypt(JSON.stringify(getEdocumentRequest), COMPANY_KEY3)
    }, 'text/plain');
}

export function getDocumentDetail(getEdocumentRequest) {
    return request3({
        url: API_BASE_URL + "/v1/sdk/getDetails",
        method: 'POST',
        body: AES256.encrypt(JSON.stringify(getEdocumentRequest), COMPANY_KEY3)
    }, 'text/plain');
}

//END SDK edoc viewer premium letter
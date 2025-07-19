import {
    ACCESS_TOKEN,
    API_BASE_URL,
    COMPANY_KEY2,
    COMPANY_KEY,
    AUTHENTICATION,
    USER_LOGIN,
    LOG_OUTED,
    COMPANY_KEY3
} from './sdkConstant';
import {trackPromise} from 'react-promise-tracker';
import AES256 from 'aes-everywhere';
import {getSession, setSession, clearSession, getDeviceId} from './sdkCommon';

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

const requestWithLoading2 = (options) => {
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
                    return json;
                })), JSON.parse(options.body).loadingArea);
};

export function CPSaveLog(jsonRequest) {
    jsonRequest['loadingArea'] = "online-save-log";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPSaveLog",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
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

export function getEdocument(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetToken",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getTokenPO(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetTokenPO",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getMircoToken(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/getMircoToken",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getOmiDocs(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading2({
        url: API_BASE_URL + "/v1/sdk/GetOmiDocs",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getIBPSDocuments(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetIBPSDocument",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getIBPSDocumentList(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetIBPSDocumentList",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function GetDocumentDetails(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetDocumentDetails",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getDocTypePermission(getEdocumentRequest, key) {
    getEdocumentRequest['epolicy-pdf'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetDocTypePermission",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}

export function getRecordingToken(submitRequest) {
    return request({
        url: API_BASE_URL + "/v1/edocument/getRecordingToken",
        method: 'POST',
        body:  AES256.encrypt(submitRequest, COMPANY_KEY2)
    });
}

//esubmision
export function getLIListForClaim(getLIListForClaimRequest) {
    getLIListForClaimRequest['loadingArea'] = "claim-li-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getLIListForClaim",
        method: 'POST',
        body: JSON.stringify(getLIListForClaimRequest)
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

export function getDocType(getDocTypeRequest) {
    getDocTypeRequest['loadingArea'] = "info-required-claim-initial";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getDocType",
        method: 'POST',
        body: JSON.stringify(getDocTypeRequest)
    });
}

export function iibGetMasterDataByType(typeRequest) {
    return request({
        url: API_BASE_URL + "/v1/point/GetMasterDataByType",
        method: 'POST',
        body: JSON.stringify(typeRequest)
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

export function onlineRequestSubmitESubmissionCSA(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitESubmissionCSA",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function onlineRequestSubmitESubmissionCCI(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitESubmissionCCI",
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

export function genOTP(genOTPRequest) {
    genOTPRequest['loadingArea'] = "verify-otp";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/gen-otp",
        method: 'POST',
        body: JSON.stringify(genOTPRequest)
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

export function logout(loginRequest) {
    loginRequest['loadingArea'] = "login-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/logout",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function CPGetPolicyListByCLIID(CPGetPolicyListByCLIIDRequest) {
    CPGetPolicyListByCLIIDRequest['loadingArea'] = "policyList-by-cliID";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/CPGetPolicyListByCLIID",
        method: 'POST',
        body: JSON.stringify(CPGetPolicyListByCLIIDRequest)
    });
}

export function getBankMasterData(getBankMasterDataRequest) {
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getBankMasterData",
        method: 'POST',
        body: JSON.stringify(getBankMasterDataRequest)
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

export function getZipCodeMasterData(getZipCodeMasterDataRequest) {
    getZipCodeMasterDataRequest['loadingArea'] = "zip-code-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getZipCodeMasterData",
        method: 'POST',
        body: JSON.stringify(getZipCodeMasterDataRequest)
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

export function getLiBenefit(getLiBenefitRequest) {
    getLiBenefitRequest['loadingArea'] = "claim-li-benefit-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getLiBenefit",
        method: 'POST',
        body: JSON.stringify(getLiBenefitRequest)
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

export function getSelectedLIPolList(getSelectedLIPolListRequest) {
    getSelectedLIPolListRequest['loadingArea'] = "claim-selectedli-pol-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claim/getSelectedLIPolList",
        method: 'POST',
        body: JSON.stringify(getSelectedLIPolListRequest)
    });
}

export function enscryptData(submitRequest) {
    return request2({
        url: API_BASE_URL + "/v1/user/enscryptData",
        method: 'POST',
        body: JSON.stringify(submitRequest)
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

export function onlineRequestSubmitESubmission(jsonRequest) {
    jsonRequest['loadingArea'] = "online-submit-area";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/user/OnlineRequestSubmitESubmission",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function checkAccount(checkAccountRequest) {
    return request({
        url: API_BASE_URL + "/v1/user/check-account",
        method: 'POST',
        body: JSON.stringify(checkAccountRequest)
    });
}

//SDK Claim
export function getPermissionByGroupAndProccessType(getEdocumentRequest, key) {
    getEdocumentRequest['loadingArea'] = "epolicy-pdf";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/GetPermissionByGroupAndProccessType",
        method: 'POST',
        body: JSON.stringify(getEdocumentRequest),
        key: key
    });
}


export function authorizationRecordInfor(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/authorizationRecordInfor",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getCustomerListByAgentCode(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getCustomerListByAgentCode",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function authorizationUpdateInfor(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/authorizationUpdateInfor",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}


export function getAgentServiceListByClientId(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getAgentServiceListByClientId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getLifeInsuredListByClientId(jsonRequest) {
    jsonRequest['loadingArea'] = "claim-li-list";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getLifeInsuredListByClientId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getPolicyProductInfoByInduredId(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getPolicyProductInfoByInduredId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getClaimBenefitByInsuredId(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getClaimBenefitByInsuredId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getPolicyListEligibleRequestClaimByClientId(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getPolicyListEligibleRequestClaimByClientId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getDocTypeListForRequestClaim(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getDocTypeListForRequestClaim",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function authorizationGetInfor(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/authorizationGetInfor",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}


export function requestClaimRecordInfor(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/requestClaimRecordInfor",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function requestClaimGetInfor(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/requestClaimGetInfor",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function requestClaimGetList(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/requestClaimGetList",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getCustomerProfile(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getCustomerProfile",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function getPolicyListByAgentServiceId(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/getPolicyListByAgentServiceId",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function saveClaimRequest(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/saveClaimRequest",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function saveImageClaim(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/saveImageClaim",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function completeClaimRequest(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/completeClaimRequest",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}

export function checkData(jsonRequest, key) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/sdk/checkData",
        method: 'POST',
        body: JSON.stringify(jsonRequest),
        key: key
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

export function requestClaimGetTempInfo(jsonRequest) {
    jsonRequest['loadingArea'] = "submit-init-claim";
    return requestWithLoading({
        url: API_BASE_URL + "/v1/claimsdk/requestClaimGetTempInfo",
        method: 'POST',
        body: JSON.stringify(jsonRequest)
    });
}
//END SDK edoc viewer premium letter
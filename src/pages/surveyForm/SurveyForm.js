import React, {useEffect, useState} from "react";
import './SurveyForm.css';
import {getDeviceId, getSession, getUrlParameter, getUrlParameterNoDecode} from "../../util/common";
import {ACCESS_TOKEN, CLIENT_ID, COMPANY_KEY2, DCID, FE_BASE_URL, FROM_APP, IS_MOBILE} from "../../constants";
import SurveyComponent from "./SurveyComponent";
import {iibGetSurveyInfo, iibSurveyInfoSubmit} from "../../util/APIUtils";
import {cloneDeep} from "lodash/lang";
import {isEmpty} from "lodash";
import AES256 from "aes-everywhere";
import PopupSurvey from "../../popup/PopupSurvey";
import LoadingIndicatorSpinner from "../../common/LoadingIndicatorSpinner";
import HTMLReactParser from "html-react-parser";

const SurveyForm = () => {
    const [surveyInfo, setSurveyInfo] = useState(null);
    const [isButtonEnabled, setIsButtonEnabled] = useState(false);
    const [isShowPopupSurvey, setIsShowPopupSurvey] = useState(false);
    const [msgPopupSurvey, setMsgPopupSurvey] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [isLock, setIsLock] = useState(false);

    const getSurveyInfo = () => {
        setIsLoading(true);
        let TemplateID = getUrlParameter("TemplateID");
        let TrackingID = getUrlParameter("TrackingID");
        let AppParamData = getUrlParameterNoDecode("Data");

        const request = {
            TemplateID: TemplateID,
            ParamData: AES256.encrypt(`TrackingID=${TrackingID}&ClientID=${getSession(CLIENT_ID)}&APIToken=${getSession(ACCESS_TOKEN)}&DeviceID=${getDeviceId()}`, COMPANY_KEY2),
            AppParamData: AppParamData ? AppParamData : "",
        };

        iibGetSurveyInfo(request).then((Res) => {
            const Response = Res.Response;

            // console.log("iibGetSurveyInfo", Response);

            if (Response?.Result === 'True' && Response?.Data && Response?.Data.length > 0) {
                if (Response.isLock) {
                    setIsShowPopupSurvey(true);
                    setMsgPopupSurvey(renderCustomMessage(Response?.LockMessage));
                    setIsLoading(false);
                } else {
                    let result = cloneDeep(Response);
                    result.Data = Response.Data[0];
                    result.ParamData = AES256.encrypt(`TrackingID=${TrackingID}&ClientID=${getSession(CLIENT_ID)}&APIToken=${getSession(ACCESS_TOKEN)}&DeviceID=${getDeviceId()}&SubmitFrom=${/Android/.test(navigator.userAgent) ? 'Android' : /iPhone|iPad|iPod/.test(navigator.userAgent) ? 'IOS' : 'Web'}`, COMPANY_KEY2);

                    setSurveyInfo(
                        {
                            Data: result.Data,
                            ParamData: result.ParamData,
                            DCID: result.DCID || getSession(DCID),
                            AppParamData,
                        }
                    );
                    setIsLoading(false);
                }
                setIsLock(Response.isLock);
            } else {
                setIsShowPopupSurvey(true);
                setMsgPopupSurvey(renderCustomMessage(Response?.Message));
                setIsLoading(false);
                setIsLock(true);
            }
        }).catch((error) => {
            console.log(error);
            setIsLoading(false);
        });
    }

    const renderCustomMessage = (msg) => {
        const isValidHTML = (htmlString) => {
            try {
                new DOMParser().parseFromString(htmlString, 'text/html');
                return true;
            } catch (e) {
                return false;
            }
        };

        const parsedHTML = (content) => {
            return HTMLReactParser(content, {
                replace: (node) => {
                    return node;
                },
            });
        }
        return (
            <div>
                {isValidHTML(msg) ? parsedHTML(msg) : msg}
            </div>
        );
    }

    useEffect(() => {
        if (!isEmpty(surveyInfo)) {
            const isAnySelected = surveyInfo.Data.Questions.some((question) => {
                const isAnswerProvided = question.QAnswer !== undefined && question.QAnswer !== null;
                return (
                    (isAnswerProvided ? isAnswerProvided : true) &&
                    question.QDataList.some((option) => option.SSelectedDefault === true)
                );
            });

            setIsButtonEnabled(isAnySelected);
        }
    }, [surveyInfo]);


    const handleSurveyInfoSubmit = (event) => {
        event.preventDefault();
        setIsLoading(true);
        let TemplateID = getUrlParameter("TemplateID");

        const surveyRequest = {
            TemplateID,
            DCID: surveyInfo.DCID,
            ParamData: surveyInfo.ParamData,
            Data: AES256.encrypt(JSON.stringify(convertSurveyDataToSubmitData(surveyInfo)), COMPANY_KEY2),
            AppParamData: surveyInfo.AppParamData ? surveyInfo.AppParamData : "",
        };

        iibSurveyInfoSubmit(surveyRequest).then((Res) => {
            const Response = Res.Response;
            if (Response.ErrLog === 'SUCCESS' && Response.Result === 'true') {
                setIsShowPopupSurvey(true);
                setIsLoading(false);
            } else {
                setIsShowPopupSurvey(true);
                setMsgPopupSurvey(renderCustomMessage(Response?.Message));
                setIsLoading(false);
            }
        }).catch((error) => {
            console.log(error);
            setIsLoading(false);
        });
    };

    const callBackLoyaltyPoint = (url, action) => {
        const mobile = getSession(IS_MOBILE);
        try {
            // console.log("callBackLoyaltyPoint", mobile);
            if (mobile === 'Android') {
                // console.log("AndroidAppCallback", action);
                window.AndroidAppCallback.postMessage(JSON.stringify({ Action: action, url }));
            } else if (mobile === 'iOS') {
                // console.log("IOSAppCallback", action);
                const handler = action === 'CallToBackHome' ? 'callBackNavigateToHomePage' : 'callBackNavigateToLoyatyPage';
                window.webkit.messageHandlers[handler].postMessage(url);
            } else {
                window.location.href = url;
            }
        } catch (error) {
            console.error('Error in callBackLoyaltyPoint:', error);
        }

        return false;
    };


    const handleUpdateSurveySubmit = (value) => {
        const updatedSurveyInfo = JSON.parse(JSON.stringify(surveyInfo));

        updatedSurveyInfo.Data.Questions.forEach((question, questionIndex) => {
            const matchingCode = Object.keys(value)[0];
            const matchingValue = value[matchingCode];

            if (question.QCode === matchingCode) {
                // Cập nhật QCode
                question.QCode = matchingCode;

                // Cập nhật SSelectedDefault dựa trên giá trị mới của QCode và SCode hoặc SOptionCode
                question.QDataList.forEach(option => {
                    option.SSelectedDefault = option.SCode === matchingValue;
                });
            }
        });
        setSurveyInfo(updatedSurveyInfo);
    };

    const convertSurveyDataToSubmitData = (surveyData) => {
        const AnswerList = [];

        if (surveyData && surveyData.Data.Questions && surveyData.Data.Questions.length > 0) {
            surveyData.Data.Questions.forEach((question) => {
                const answer = {
                    QCode: question.QCode,
                    QType: question.QType,
                    Answers: [],
                };

                if (question.QType === "RadioBox" || question.QType === "CheckBox") {
                    const selectedOption = question.QDataList.find(
                        (option) => option.SSelectedDefault
                    );

                    if (selectedOption) {
                        answer.Answers.push({
                            AnswerValue: selectedOption.SDisplayText,
                            AnswerCode: selectedOption.SCode,
                        });
                    }
                } else if (question.QType === "InputField") {
                    answer.Answers.push({
                        AnswerValue: question.QAnswer,
                        AnswerCode: "",
                    });
                }

                if (answer.Answers.length > 0) {
                    AnswerList.push(answer);
                }
            });
        }

        return AnswerList;
    };

    const handleSurveyCompletion = () => {
        setIsShowPopupSurvey(false);

        if (isLock) {
            // console.log("handleSurveyCompletion_Lock");
            callBackLoyaltyPoint('', 'CallToBackHome');
        } else if (surveyInfo?.Data?.URLRedirect && surveyInfo?.Data?.ActionAfterSubmit) {
            callBackLoyaltyPoint(surveyInfo.Data.URLRedirect, surveyInfo.Data.ActionAfterSubmit);
        } else {
            window.location.href = `${FE_BASE_URL}/point`;
        }
    };

    useEffect(() => {
        getSurveyInfo();
    }, []);


    return (
        <main className={getSession(ACCESS_TOKEN) ? "logined" : ""}>
            <div className="scpointchange">
                <section className="survey-form-main-wrapper">
                    <div className="container">
                        {isLoading && <LoadingIndicatorSpinner/>}
                        {!isEmpty(surveyInfo) && <form onSubmit={handleSurveyInfoSubmit}>
                            <div className="survey-form-content">
                                <div className="survey-form-container">
                                    <div className="survey-form-body">
                                        <SurveyComponent data={surveyInfo ? surveyInfo : null}
                                                         getDataSubmit={(data) => handleUpdateSurveySubmit(data)}/>
                                    </div>

                                    <img className="decor-clip" src="../../img/mock.svg" alt=""/>
                                    <img className="decor-person" src="../../img/person.png" alt=""/>
                                </div>
                                <div className="bottom-btn">
                                    <button
                                        className={`btn btn-primary ${(!isButtonEnabled || isLoading) && 'disabled'}`}
                                        disabled={!isButtonEnabled || isLoading}
                                        id="btn-survey-submit">Gửi yêu cầu
                                    </button>
                                </div>
                            </div>
                        </form>}
                    </div>
                </section>
            </div>
            {isShowPopupSurvey &&
                <PopupSurvey onClosePopup={handleSurveyCompletion} message={msgPopupSurvey}/>}
        </main>
    );
};
export default SurveyForm;
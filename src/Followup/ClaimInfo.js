import 'antd/dist/antd.min.css';
import React, {Component} from 'react';
import {
    ACCESS_TOKEN,
    AUTHENTICATION,
    CLAIM_TYPE,
    CLIENT_ID,
    COMPANY_KEY,
    EXPIRED_MESSAGE,
    FE_BASE_URL,
    PageScreen,
    USER_LOGIN,
    WEB_BROWSER_VERSION
} from '../constants';
import {
    formatDate, formatDateNew,
    getDeviceId,
    getSession,
    removeTrailingZeros,
    showMessage,
    sumInvoice,
    trackingEvent
} from '../util/common';
import {
    CPGetClientProfileByCLIID,
    CPSaveLog,
    CPSubmitForm,
    getClaimCheckhold,
    getLiBenefit,
    iibGetMasterDataByType,
    logoutSession,
    postClaimImage
} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator2';
import {Helmet} from "react-helmet";
import {Redirect, withRouter} from 'react-router-dom';
import NumberFormat from "react-number-format";
import ImageViewer from "./ImageViewer";
import {isEmpty} from "lodash";
import {TREAMENT_TYPE} from "../Claim/CreateClaim/CreateClaim";
import {cloneDeep} from "lodash/lang";
import LoadingIndicatorBasic from "../common/LoadingIndicatorBasic";
import moment from "moment";
import NoticePopup from "../components/NoticePopup";
import parse from "html-react-parser";

let totalAmount = 0;

class ClaimInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            jsonReqSynClaim: {
                jsonDataInput: {
                    Action: 'SyncClaim',
                    Company: COMPANY_KEY,
                    OS: WEB_BROWSER_VERSION,
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Project: 'mcp',
                    ClaimID: '',
                    SubmissionID: '',
                    UserLogin: getSession(USER_LOGIN),
                }
            },
            jsonInput: {
                jsonDataInput: {
                    Action: 'ListClaimFilter',
                    Project: 'mcp',
                    Authentication: AUTHENTICATION,
                    DeviceId: getDeviceId(),
                    UserLogin: getSession(USER_LOGIN),
                    ClientID: getSession(CLIENT_ID),
                    APIToken: getSession(ACCESS_TOKEN),
                    FilterStatus: '',
                    FilterPolicy: ''
                }
            },
            jsonInput2: {
                jsonDataInput: {
                    SubmissionID: '',
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'CheckHold',
                    Authentication: AUTHENTICATION,
                    ClaimID: '',
                    ClaimType: '',
                    DeviceId: getDeviceId(),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN)
                }
            },
            jsonInput3: {
                jsonDataInput: {
                    Project: 'mcp',
                    Company: COMPANY_KEY,
                    NumberOfPage: '',
                    APIToken: getSession(ACCESS_TOKEN),
                    Authentication: AUTHENTICATION,
                    ClaimID: '',
                    ClaimType: 'Additional',
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    DocNumber: '',
                    DocProcessID: '',
                    DocTypeID: '',
                    DocTypeName: '',
                    Image: ''
                }
            },
            jsonInput4: {
                jsonDataInput: {
                    SubmissionID: '',
                    APIToken: getSession(ACCESS_TOKEN),
                    Action: 'ClaimHold',
                    Authentication: AUTHENTICATION,
                    ClaimID: '',
                    ClaimType: 'Additional',
                    ClientID: getSession(CLIENT_ID),
                    DeviceId: getDeviceId(),
                    Project: 'mcp',
                    UserLogin: getSession(USER_LOGIN),
                    DocTypeComments: [
                        {
                            Status: '',
                            ClientComment: '',
                            DocTypeID: '',
                            SubDocID: '',
                            WFAddDocID: ''
                        },
                    ],
                }
            },
            jsonResponse: null,
            clientProfile: null,

            clientProfileDetails: null,
            claimRequester: null,
            claimDeath: null,
            claimTypeList: null,
            paymentList: null,
            claimSub: null,
            treatmentHistorys: null,
            lstDocType: null,
            othersInsurance: null,

            checkHoldList: null,
            filter: 'All',
            isEmpty: true,
            nodata: false,
            isCompleted: false,
            claimList: null,
            attachmentState: {
                previewVisible: false,
                previewImage: "",
                previewTitle: "",

                claimAttachment: [
                    {
                        DocTypeID: "",
                        attachmentList: [],
                    },
                ],
                disabledButton: true,
            },
            claimID: '',
            claimType: '',
            SubmissionID: '',
            nextScreen: false,
            isWAITHC: false,
            isWAITWF: false,
            isViewClaimDetails: false,
            renderMeta: false,
            claimDetails: null,
            index: '',
            isExtendMethod: false,
            isExtendMethod2: false,
            isExtendLossEvent: false,
            isExtendAccidentEvent: false,
            isExtendIllnessEvent: false,
            isLoss: false,
            isAccident: false,
            isIllness: false,
            isTreatmentHistory: false,
            spinning: true,
        }
        this.handlerUpdateMainState = this.updateMainState.bind(this);
        this.handlerUploadAttachment = this.uploadAttachment.bind(this);
        this.handlerDragOver = this.dragFileOver.bind(this);
        this.handlerDragLeave = this.dragFileLeave.bind(this);
        this.handlerDrop = this.dropFile.bind(this);
        this.handlerUpdateAttachmentList = this.updateAttachmentList.bind(this);
        this.handlerDeleteAttachment = this.deleteAttachment.bind(this);
        this.handlerChangeInputComment = this.onChangeInputComment.bind(this);
        this.clickOnCardDetails = this.clickOnCardDetails.bind(this);
    }


    onChangeInputComment(index4, event) {
        const target = event.target;
        const inputValue = target.value.trim();
        const jsonState = this.state;
        jsonState.attachmentState.disabledButton = false;
        jsonState.jsonInput4.jsonDataInput.DocTypeComments[index4].ClientComment = inputValue;
        this.setState(jsonState);

    }

    updateMainState(subStateName, editedState) {
        const jsonState = this.state;
        jsonState[subStateName] = editedState;
        this.setState(jsonState);
    }

    updateState(jsonState) {
        const c1Attachments = jsonState.attachmentState.claimAttachment.find(attachment => attachment.attachmentList.length > 0);
        jsonState.attachmentState.disabledButton = !(c1Attachments);
        this.setState(jsonState);
        this.handlerUpdateMainState("attachmentState", this.state.attachmentState);
    }

    dragFileOver(htmlId, event) {
        event.preventDefault();
        document.getElementById(htmlId).className = "img-upload active";
    }

    dragFileLeave(htmlId, event) {
        event.preventDefault();
        document.getElementById(htmlId).className = "img-upload";
    }

    dropFile(index4, event, updateAttachmentListByDrag) {
        event.preventDefault();
        const validateImage = ["image/jpeg", "image/jpg", "image/png"];
        const files = Object.values(event.dataTransfer.files);
        let promisedFiles = [];
        for (let file of files) {
            let filePromise = new Promise(resolve => {
                if (validateImage.includes(file.type)) {
                    let reader = new FileReader();
                    reader.readAsDataURL(file);
                    reader.onloadend = () => resolve(reader.result);
                }
            });
            promisedFiles.push(filePromise);
        }
        Promise.all(promisedFiles).then(promisedFiles => {
            // fileContents will be an array containing
            // the contents of the files, perform the
            // character replacements and other transformations
            // here as needed
            const jsonState = this.state;
            const arrAttData = promisedFiles.map(base64 => {
                return {imgData: base64}
            });
            const attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;
            if (attachments !== null && attachments !== undefined) {
                attachments.push(...arrAttData);
                this.updateState(jsonState);
            }
            event.target.value = null;
        });
    }

    loadClientBenifit(LifeInsuredID) {
        // Check benefits
        const apiRequest = {
            jsonDataInput: {
                Company: COMPANY_KEY,
                Authentication: AUTHENTICATION,
                DeviceId: getDeviceId(),
                APIToken: getSession(ACCESS_TOKEN),
                Project: 'mcp',
                Action: 'LifeInsuredBenefit',
                LifeInsuredID: LifeInsuredID,
                UserLogin: getSession(USER_LOGIN),
                ClientID: getSession(CLIENT_ID),
            }
        }
        getLiBenefit(apiRequest).then(Res => {
            let Response = Res.Response;

            if (Response.Result === 'true' && Response.ClientProfile !== null && Response.ClientProfile !== undefined) {
                this.setState({claimTypeList: Response.ClientProfile});
            }
        }).catch(error => {
        });
    }

    uploadAttachment(index4, event, updateAttachmentList) {
        const validateImage = ["image/jpeg", "image/jpg", "image/png"];
        let promisedFiles = [];
        for (const processingFile of event.target.files) {
            let filePromise = new Promise(resolve => {
                if (validateImage.includes(processingFile.type)) {
                    let reader = new FileReader();
                    reader.readAsDataURL(processingFile);
                    reader.onloadend = () => resolve(reader.result);
                }
            });
            promisedFiles.push(filePromise);
        }
        Promise.all(promisedFiles).then(promisedFiles => {
            const arrAttData = promisedFiles.map(base64 => {
                return {imgData: base64}
            });
            const jsonState = this.state;
            const attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;

            if (attachments !== null && attachments !== undefined) {
                attachments.push(...arrAttData);
                this.updateState(jsonState);
            }
            event.target.value = null;
        });
    }

    updateAttachmentList(index4, event, val) {
        const jsonState = this.state;
        const attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;

        if (attachments !== null && attachments !== undefined) {
            attachments.push({imgData: val});
            this.updateState(jsonState);
        }
        event.target.value = null;
    }

    deleteAttachment(index4, attachmentIndex) {
        const jsonState = this.state;
        const attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;
        attachments.splice(attachmentIndex, 1);
        this.updateState(jsonState);
    }

    async clickOnCardDetails(event, item, index) {
        try {
            this.setState({
                spinning: true,
            });

            await this.fetchCPGetClientProfileByCLIID(item);
            await this.getHospitalList();

        } catch (error) {
            console.error('Error fetching data:', error);
        }

        const cloneClaimDetails = cloneDeep(item);
        cloneClaimDetails.claimDetailsIndex = index;

        this.setState({
            isViewClaimDetails: true,
            isEmpty: false,
            claimDetails: cloneClaimDetails,
        });

        //-------------------------------------------------------------
        const jsonState = this.state;
        const bottomCard = document.getElementsByClassName('card__bottom-info')[0];

        jsonState.clientProfile.forEach((cliID, i) => {
            const cardElement = document.getElementById('card' + i);

            if (cardElement) {
                const isChosen = i === index;

                cardElement.className = `card ${isChosen && 'choosen card-border-no-rad'}`;

                if (bottomCard) {
                    if (isChosen) {
                        bottomCard.style.borderColor = '#de181f';
                        bottomCard.style.filter = 'drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.1))';
                    } else {
                        bottomCard.style.borderColor = '#e0dedc';
                        bottomCard.style.filter = 'none';
                    }
                }
            }
        });
        //-------------------------------------------------------------
    };

    componentDidMount() {
        const {match} = this.props;
        const cliID = match.params.cliID;

        const apiRequest = Object.assign({}, this.state.jsonInput);
        CPGetClientProfileByCLIID(apiRequest).then(Res => {
            let jsonState;
            let Response1 = Res.Response;

            if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
                const foundIndex = Response1.ClientProfile.findIndex(item => item.ClaimID === cliID);
                if (foundIndex !== -1) {
                    const foundItem = Response1.ClientProfile[foundIndex];
                    this.clickOnCardDetails(null, foundItem, foundIndex);
                } else {
                    console.log("Item not found");
                }

                jsonState = this.state;
                jsonState.jsonResponse = Response1;
                jsonState.clientProfile = Response1.ClientProfile;

                this.setState(jsonState);

            } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                })
            } else {
                jsonState = this.state;
                jsonState.nodata = true;
                this.setState(jsonState);
            }
        }).catch(error => {
            this.props.history.push('/maintainence');
        });

        this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_REQUEST_PAGE}`);
        trackingEvent(
            "Theo dõi yêu cầu",
            `Web_Open_${PageScreen.CLAIM_REQUEST_PAGE}`,
            `Web_Open_${PageScreen.CLAIM_REQUEST_PAGE}`,
        );
    }


    componentWillUnmount() {
        this.cpSaveLog(`Web_Close_${PageScreen.CLAIM_REQUEST_PAGE}`);
        trackingEvent(
            "Theo dõi yêu cầu",
            `Web_Close_${PageScreen.CLAIM_REQUEST_PAGE}`,
            `Web_Close_${PageScreen.CLAIM_REQUEST_PAGE}`,
        );
    }


    fetchCPGetClientProfileByCLIID(element) {
        let reqBody = {...this.state.jsonReqSynClaim};
        reqBody.jsonDataInput.ClaimID = element?.ClaimID;
        reqBody.jsonDataInput.SubmissionID = element?.lstClaimSubmission[0]?.SubmissionID;

        const apiRequest = Object.assign({}, reqBody);
        CPGetClientProfileByCLIID(apiRequest).then(Res => {

            let jsonState;
            let Response = Res.Response;

            console.log("CPGetClientProfileByCLIID", Response);
            if (Response.ErrLog === 'SUCCESSFUL' && Response.ClientProfile !== null) {
                jsonState = this.state;
                jsonState.jsonResponse = Response;
                jsonState.clientProfileDetails = Response.ClientProfile;
                jsonState.claimRequester = Response.ClientProfile[0]?.claimRequester;
                jsonState.paymentList = Response.ClientProfile[0]?.PaymentList;
                jsonState.claimDeath = Response.ClientProfile[0]?.claimDeath;
                jsonState.claimSub = Response.ClientProfile[0]?.claimSub;
                jsonState.treatmentHistorys = Response.ClientProfile[0]?.TreatmentHistorys;
                jsonState.lstDocType = Response.ClientProfile[0]?.lstDocType;
                jsonState.othersInsurance = Response.ClientProfile[0]?.OthersInsurance;

                jsonState.isTreatmentHistory = !isEmpty(jsonState.treatmentHistorys);

                this.loadClientBenifit(Response.ClientProfile[0]?.claimRequester?.LifeInsuredID);


                // Check for claimDeath fields
                if (jsonState.claimDeath) {
                    const claimDeathData = jsonState.claimDeath;

                    // Check for Loss fields in claimDeathData
                    const isLoss = ["LossDate", "LossPlace", "LossDistrict", "LossProvince", "LossProgression"]
                        .some(key => claimDeathData.hasOwnProperty(key) && claimDeathData[key] !== "");

                    jsonState.isLoss = isLoss;

                    // Check for Accident fields in claimDeathData
                    const isAccident = ["AccidentReason", "AccidentDate", "AccidentPlace", "AccidentDistrict", "AccidentProvince", "AccidentIllnessDesc"]
                        .some(key => claimDeathData.hasOwnProperty(key) && claimDeathData[key] !== "");

                    jsonState.isAccident = isAccident;

                    // Check for Illness fields in claimDeathData
                    const isIllness = ["IllnessProgression", "IllnessDate", "IllnessPlace"]
                        .some(key => claimDeathData.hasOwnProperty(key) && claimDeathData[key] !== "");

                    jsonState.isIllness = isIllness;
                }

                this.setState(jsonState);

            } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                    pathname: '/home',
                    state: {authenticated: false, hideMain: false}
                })
            } else {
                jsonState = this.state;
                jsonState.isEmpty = true;

                this.setState(jsonState);
            }
        }).catch(error => {
            this.props.history.push('/maintainence');
        });
    }

    getHospitalList() {
        let request = {
            Action: "GetHospitalMedic",
            Project: "mcp"
        }
        iibGetMasterDataByType(request).then(Res => {
            let Response = Res.GetMasterDataByTypeResult;
            if (Response.Result === 'true' && Response.ClientProfile) {
                this.setState({hospitalList: Response.ClientProfile, spinning: false,});
            }
        }).catch(error => {
        });
    }

    cpSaveLog(functionName) {
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
            this.setState({renderMeta: true});
        }).catch(error => {
            this.setState({renderMeta: true});
        });
    }

    render() {
        totalAmount = 0;
        const closeNotice = () => {
            this.setState({showNotice: false});
        }
        const showNotice = () => {
            this.setState({showNotice: true});
        }
        const {
            clientProfileDetails,
            claimTypeList,
            claimRequester,
            paymentList,
            claimSub,
            claimDeath,
            treatmentHistorys,
            lstDocType,
            othersInsurance
        } = this.state;

        const mapValueToDescription = (value, data) => {
            const mappedValue = Object.values(data).find(item => item.submitType === value);
            return mappedValue ? mappedValue.desc : null;
        };

        const callCheckHoldAPI = (item) => {
            const jsonState = this.state;
            jsonState.jsonInput2.jsonDataInput.ClaimID = (item.lstClaimSubmission[0].ClaimID === '' ? item.lstClaimSubmission[0].SubmissionID : item.lstClaimSubmission[0].ClaimID);
            jsonState.jsonInput2.jsonDataInput.ClaimType = item.lstClaimSubmission[0].ClaimType;
            jsonState.jsonInput2.jsonDataInput.SubmissionID = item.lstClaimSubmission[0].SubmissionID;
            jsonState.jsonInput4.jsonDataInput.DocTypeComments = [];
            jsonState.attachmentState.claimAttachment = [];
            jsonState.checkHoldList = null;
            const apiRequest = Object.assign({}, this.state.jsonInput2);
            getClaimCheckhold(apiRequest).then(Res => {
                let Response1 = Res.Response;

                if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
                    var jsonState = this.state;
                    jsonState.checkHoldList = Response1.ClientProfile;
                    for (let i = 0; i < jsonState.checkHoldList.length; i++) {
                        jsonState.attachmentState.claimAttachment[i] = jsonState.checkHoldList[i];
                        jsonState.attachmentState.claimAttachment[i]['attachmentList'] = []
                        jsonState.jsonInput4.jsonDataInput.DocTypeComments[i] = jsonState.checkHoldList[i];
                    }
                    this.setState(jsonState);
                } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}

                    })

                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };

        const callHistoryClaimAPI = (filter) => {
            const jsonState = this.state;
            jsonState.clientProfile = null;
            jsonState.jsonInput.jsonDataInput.FilterStatus = filter;
            this.setState(jsonState);
            const apiRequest = Object.assign({}, this.state.jsonInput);
            CPGetClientProfileByCLIID(apiRequest).then(Res => {
                let Response = Res.Response;
                if (Response.ErrLog === "SUCCESSFUL" && Response.ClientProfile !== null) {
                    const jsonState = this.state;
                    jsonState.jsonResponse = Response;
                    jsonState.clientProfile = Response.ClientProfile;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}
                    })
                }
            }).catch(error => {
                this.props.history.push('/maintainence');
            });
        };
        const showFilter = () => {
            const jsonState = this.state;
            document.getElementById(jsonState.filter).className = "filter-pop-tick ticked";

            if (document.getElementById("filter").className === "specialfilter show") {
                document.getElementById("filter").className = "specialfilter";
            } else {
                document.getElementById("filter").className = "specialfilter show";
            }
        };
        const showAll = () => {
            document.getElementById("All").className = "filter-pop-tick ticked";
            document.getElementById("TEMPSAVED").className = "filter-pop-tick";
            document.getElementById("WAITWF").className = "filter-pop-tick";
            document.getElementById("WFHOLD").className = "filter-pop-tick";
            document.getElementById("WAITHC").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.FilterStatus = '';
            jsonState.filter = 'All';
            this.setState(jsonState);
            callHistoryClaimAPI('');
        };
        const showSubmited = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("TEMPSAVED").className = "filter-pop-tick ticked";
            document.getElementById("WAITWF").className = "filter-pop-tick";
            document.getElementById("WFHOLD").className = "filter-pop-tick";
            document.getElementById("WAITHC").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.FilterStatus = 'TEMPSAVED';
            jsonState.filter = 'TEMPSAVED';
            this.setState(jsonState);
            callHistoryClaimAPI('TEMPSAVED');
        };
        const showProcessing = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("TEMPSAVED").className = "filter-pop-tick";
            document.getElementById("WAITWF").className = "filter-pop-tick ticked";
            document.getElementById("WFHOLD").className = "filter-pop-tick";
            document.getElementById("WAITHC").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.FilterStatus = 'WAITWF';
            jsonState.filter = 'WAITWF';
            this.setState(jsonState);
            callHistoryClaimAPI('WAITWF');
        };
        const showWfHold = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("TEMPSAVED").className = "filter-pop-tick";
            document.getElementById("WAITWF").className = "filter-pop-tick";
            document.getElementById("WFHOLD").className = "filter-pop-tick ticked";
            document.getElementById("WAITHC").className = "filter-pop-tick";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.FilterStatus = 'WFHOLD';
            jsonState.filter = 'WFHOLD';
            this.setState(jsonState);
            callHistoryClaimAPI('WFHOLD');
        };
        const showWaitHC = () => {
            document.getElementById("All").className = "filter-pop-tick";
            document.getElementById("TEMPSAVED").className = "filter-pop-tick";
            document.getElementById("WAITWF").className = "filter-pop-tick";
            document.getElementById("WFHOLD").className = "filter-pop-tick";
            document.getElementById("WAITHC").className = "filter-pop-tick ticked";
            document.getElementById("filter").className = "specialfilter";
            const jsonState = this.state;
            jsonState.jsonInput.jsonDataInput.FilterStatus = 'WAITHC';
            jsonState.filter = 'WAITHC';
            this.setState(jsonState);
            callHistoryClaimAPI('WAITHC');
        };
        const dropdownContent = (index) => {
            if (document.getElementById('dropdownContent' + index).className === "dropdown show") {
                document.getElementById('dropdownContent' + index).className = "dropdown";
            } else {
                document.getElementById('dropdownContent' + index).className = "dropdown show";
            }
        };

        const clickOnCard = (event, item, index) => {
            event.preventDefault();
            const target = event.target;

            // Check if the click happened on the container, button, or text
            if (target.classList.contains('card__bottom-info-btn') || target.tagName === 'P') {

                const jsonState = this.state;
                jsonState.isViewClaimDetails = false;
                jsonState.claimDetails = null;
                jsonState.isCompleted = false;
                jsonState.isWAITHC = false;
                jsonState.isWAITWF = false;
                jsonState.claimList = item;
                jsonState.index = index;

                jsonState.isEmpty = false;
                if (item.Status !== "WFHOLD") {
                    jsonState.isWAITWF = true;
                    jsonState.isCompleted = true;
                    if (item.Status === "WAITHC") {
                        jsonState.isWAITHC = true;
                        jsonState.isWAITWF = false;
                    }
                } else {
                    jsonState.claimID = (item.lstClaimSubmission[0].ClaimID === '' ? item.lstClaimSubmission[0].SubmissionID : item.lstClaimSubmission[0].ClaimID);
                    jsonState.claimType = item.lstClaimSubmission[0].ClaimType;
                    jsonState.SubmissionID = item.lstClaimSubmission[0].SubmissionID;
                    callCheckHoldAPI(item);

                }
                this.updateState(jsonState);
                // this.setState(jsonState);
                const bottomCard = document.getElementsByClassName('card__bottom-info')[0];

                jsonState.clientProfile.forEach((cliID, i) => {
                    const cardElement = document.getElementById('card' + i);

                    if (cardElement) {
                        const isChosen = i === index;
                        const isWFHOLD = item.Status === 'WFHOLD';

                        cardElement.className = `card ${isChosen && isWFHOLD && 'choosen card-border-no-rad'}`;


                        if (isChosen) {
                            bottomCard.style.borderColor = '#de181f';
                            bottomCard.style.filter = 'drop-shadow(0px 3px 6px rgba(0, 0, 0, 0.1))';
                        } else {
                            bottomCard.style.borderColor = '#e0dedc';
                            bottomCard.style.filter = 'none';
                        }
                    }
                });
            }
        };

        const submitClaimHold = () => {
            const jsonState = this.state;
            for (let i = 0; i < jsonState.jsonInput4.jsonDataInput.DocTypeComments.length; i++) {
                jsonState.jsonInput4.jsonDataInput.SubmissionID = this.state.SubmissionID;
                jsonState.jsonInput4.jsonDataInput.ClaimID = jsonState.claimID;
                jsonState.jsonInput4.jsonDataInput.ClaimType = jsonState.claimType;
                jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].DocTypeID = (jsonState.attachmentState.claimAttachment[i].DocTypeID !== undefined ? jsonState.attachmentState.claimAttachment[i].DocTypeID : '');
                jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].SubDocID = (jsonState.attachmentState.claimAttachment[i].SubDocId !== undefined ? jsonState.attachmentState.claimAttachment[i].SubDocId : '');
                jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].WFAddDocID = (jsonState.attachmentState.claimAttachment[i].WFAddDocID !== undefined ? jsonState.attachmentState.claimAttachment[i].WFAddDocID : '');
                if (jsonState.attachmentState.claimAttachment[i].attachmentList.length === 0 && jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].ClientComment !== '') {
                    jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].Status = 'Missing';
                } else {
                    jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].Status = '';
                }
                this.setState(jsonState);
            }
            const apiRequest = Object.assign({}, jsonState.jsonInput4);
            CPSubmitForm(apiRequest).then(Res => {
                let Response = Res.Response;
                if (Response.Result === 'true') {
                    for (let i = 0; i < jsonState.attachmentState.claimAttachment.length; i++) {
                        for (let j = 0; j < jsonState.attachmentState.claimAttachment[i].attachmentList.length; j++) {
                            let docProcId = '';
                            if ((jsonState.claimType.indexOf(CLAIM_TYPE.HEALTH_CARE) >= 0) || (jsonState.claimType.indexOf(CLAIM_TYPE.HS) >= 0)) {
                                docProcId = 'CHA';
                            } else {
                                docProcId = 'CLA';
                            }
                            jsonState.jsonInput3.jsonDataInput.ClaimID = jsonState.claimID;
                            jsonState.jsonInput3.jsonDataInput.ClaimType = 'Additional';
                            jsonState.jsonInput3.jsonDataInput.DocProcessID = docProcId;
                            jsonState.jsonInput3.jsonDataInput.DocTypeID = jsonState.attachmentState.claimAttachment[i].DocTypeID;
                            jsonState.jsonInput3.jsonDataInput.DocTypeName = jsonState.attachmentState.claimAttachment[i].DocTypeName;
                            jsonState.jsonInput3.jsonDataInput.NumberOfPage = j + '';
                            jsonState.jsonInput3.jsonDataInput.DocNumber = j + '';
                            var matches = jsonState.attachmentState.claimAttachment[i].attachmentList[j].imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
                            jsonState.jsonInput3.jsonDataInput.Image = matches.length === 3 ? matches[2] : '';
                            this.setState(jsonState);
                            const apiRequest = Object.assign({}, jsonState.jsonInput3);
                            postClaimImage(apiRequest);
                        }
                    }
                    jsonState.nextScreen = true;
                    this.setState(jsonState);
                } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                    showMessage(EXPIRED_MESSAGE);
                    logoutSession();
                    this.props.history.push({
                        pathname: '/home',
                        state: {authenticated: false, hideMain: false}
                    })
                }
            }).catch(error => {
                //this.props.history.push('/maintainence');
            });

        };
        const closePopup = () => {
            const jsonState = this.state;
            document.getElementById('popup').className = "popup envelop";
            jsonState.nextScreen = false;
            jsonState.isCompleted = true;
            this.setState(jsonState);
            callHistoryClaimAPI('');

        };

        const goBack = () => {
            const main = document.getElementById("main-id");
            if (main) {
                main.classList.toggle("nodata");
            }
        }

        const mapClaimTypeToClaimName = (inputValue, data) => {
            const claimTypes = inputValue.split(';');
            const mappedNames = claimTypes.map(type => {
                const foundType = data?.find(item => item.ClaimType === type);
                return foundType ? foundType.ClaimName : null;
            });
            const filteredNames = mappedNames.filter(name => name !== null);
            return filteredNames.join(';');
        };

        const claimAttachment = this.state.attachmentState.claimAttachment;
        if (!getSession(CLIENT_ID)) {
            return <Redirect to={{
                pathname: '/home'
            }}/>;
        }

        const redirectRequestHistory = () => {
            this.props.history.push('/claim-history');
        };

        const containerStyle = {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            flexDirection: 'column',
        };

        const textContainerStyle = {
            display: 'flex',
            alignItems: 'center',
            flexDirection: 'column',
        };

        const textCommonStyle = {
            fontSize: '1.4rem',
            fontWeight: '500',
            lineHeight: '20px',
        };

        const linkStyle = {
            color: '#985801',
            cursor: 'pointer',
        };

        // console.log("clientProfile", this.state.clientProfile);
        return (
            <div>
                {this.state.renderMeta &&
                    <Helmet>
                        <title>Giải quyết quyền lợi – Dai-ichi Life Việt Nam</title>
                        <meta name="description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta name="robots" content="noindex, nofollow"/>
                        <meta property="og:type" content="website"/>
                        <meta property="og:url" content={FE_BASE_URL + "/followup-claim-info"}/>
                        <meta property="og:title" content="Giải quyết quyền lợi Dai-ichi Life Việt Nam"/>
                        <meta property="og:description"
                              content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam"/>
                        <meta property="og:image"
                              content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
                        <link rel="canonical" href={FE_BASE_URL + "/followup-claim-info"}/>
                    </Helmet>
                }

                {this.state.nodata === true ? (
                    <main className="logined nodata nodata2">
                        <div className="main-warpper basic-mainflex">
                            <section className="sccontract-warpper no-data nodata2-fullwidth">
                                <div className="breadcrums" style={{backgroundColor: '#ffffff'}}>
                                    <div className="breadcrums__item">
                                        <p>Theo dõi yêu cầu</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                    <div className="breadcrums__item">
                                        <p>Giải quyết quyền lợi</p>
                                        <p className='breadcrums__item_arrow'>></p>
                                    </div>
                                </div>
                                <div className="sccontract-container" style={{background: '#f5f3f2'}}>
                                    <div className="insurance">
                                        <div className="empty">
                                            <div className="icon">
                                                <img src="img/2.2-nodata-image.png" alt=""/>
                                            </div>
                                            <div style={containerStyle}>
                                                <p style={textCommonStyle}>Hiện tại không có yêu cầu nào đang xử lý</p>
                                                <div style={textContainerStyle}>
                                                    <p style={textCommonStyle}>
                                                        Quý khách có thể xem lại các yêu cầu đã &nbsp;
                                                    </p>
                                                    <div style={{display: 'flex', alignItems: 'center'}}>
                                                        <p style={textCommonStyle}>
                                                            hoàn tất tại <span style={linkStyle}
                                                                               onClick={redirectRequestHistory}>Lịch sử yêu cầu</span>
                                                        </p>
                                                        <span className="arrow"
                                                              style={{marginTop: 2, marginLeft: 2, cursor: 'pointer'}}>
            <img src="img/icon/arrow-down-bronw.svg" alt="" style={{transform: 'rotate(-90deg)'}}
                 onClick={redirectRequestHistory}/>
          </span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </main>
                ) : (
                    <main className="logined" id="main-id">
                        <div className="main-warpper basic-mainflex">
                            <section className="sccard-warpper hopdong-sccard-wrapper">
                                <div className="title">
                                    <h4 className="basic-bold">Vui lòng chọn yêu cầu cần xử lý:</h4>
                                    <div className="specialfilter" id="filter">
                                        <div className="filter_button" onClick={() => showFilter()}>
                                            <div className="icon-left">
                                                <img src="img/icon/filter-icon.svg" alt="filter-icon"/>
                                            </div>
                                            <div className="text">
                                                <p style={{fontWeight: '600'}}>Bộ lọc</p>
                                            </div>
                                            <div className="icon-right">
                                                <img src="img/icon/arrow-white-down.svg" alt="arrow-down-icon"/>
                                            </div>
                                        </div>
                                        <div className="filter_content">
                                            <div className="filter-pop">
                                                <div className="filter-pop-tick" onClick={() => showAll()} id="All">
                                                    <p className="content">Tất cả</p>
                                                    <div className="img-wrapper">
                                                        <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                    </div>
                                                </div>
                                                <div className="filter-pop-tick" onClick={() => showSubmited()}
                                                     id="TEMPSAVED">
                                                    <p className="content">Đã gửi</p>
                                                    <div className="img-wrapper">
                                                        <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                    </div>
                                                </div>
                                                <div className="filter-pop-tick" onClick={() => showWfHold()}
                                                     id="WFHOLD">
                                                    <p className="content">Bổ sung chứng từ</p>
                                                    <div className="img-wrapper">
                                                        <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                    </div>
                                                </div>
                                                <div className="filter-pop-tick" onClick={() => showProcessing()}
                                                     id="WAITWF">
                                                    <p className="content">Đang xử lý</p>
                                                    <div className="img-wrapper">
                                                        <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                    </div>
                                                </div>
                                                <div className="filter-pop-tick" onClick={() => showWaitHC()}
                                                     id="WAITHC">
                                                    <p className="content">Bổ sung chứng từ gốc</p>
                                                    <div className="img-wrapper">
                                                        <img src="img/icon/green-ticked.svg" alt="ticked"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="bg"></div>
                                    </div>
                                </div>
                                <LoadingIndicator area="claim-li-list"/>
                                {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                                        (item.lstClaimSubmission[0] !== null
                                            && item.lstClaimSubmission[0] !== ''
                                            && item.lstClaimSubmission[0] !== undefined) &&
                                        <>
                                            <div className="card-warpper">
                                                <div className="item"
                                                    // onClick={(event) => clickOnCard(event, item, index)}
                                                >
                                                    <div className={`card ${(item.lstClaimSubmission[0] !== null
                                                        && item.lstClaimSubmission[0] !== ''
                                                        && item.lstClaimSubmission[0] !== undefined) && item.Status === "WFHOLD" && "card-border-no-rad"}`}
                                                         id={"card" + index}
                                                         style={{cursor: 'initial'}}>
                                                        <div className="card__header">
                                                            <div className="calendar-wrapper">
                                                                <img src="img/icon/Calendar.svg" alt=""/>
                                                                <p>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].DateSubmission : ''}</p>
                                                            </div>
                                                            {['WFHOLD', 'DECLINED', 'WAITHC'].includes(item.Status) ? (
                                                                <div>
                                                                    <div className="dcstatus">
                                                                        <p><br/></p>
                                                                        <p className="wfhold">{item.StatusVN}</p>
                                                                    </div>
                                                                </div>
                                                            ) : (
                                                                <div className="dcstatus">
                                                                    {(item.Status === "TEMPSAVED" || item.Status === "CANCEL") ? (
                                                                        <p className="cancel">{item.StatusVN}</p>
                                                                    ) : (
                                                                        (item.Status === "WAITWF")?(
                                                                            <p className="waitwf" style={{
                                                                                fontFamily: 'Inter',
                                                                                fontSize: '14px',
                                                                                fontWeight: '600'
                                                                            }}>{item.StatusVN}</p>
                                                                        ):(
                                                                            <p className="claimStatus" style={{
                                                                                fontFamily: 'Inter',
                                                                                fontSize: '14px',
                                                                                fontWeight: '600'
                                                                            }}>{item.StatusVN}</p>
                                                                        )

                                                                    )
                                                                    }
                                                                </div>
                                                            )}
                                                        </div>
                                                        <div className="card__footer_Claim_Info">
                                                            <div className="card__footer-item">
                                                                <p>Số hồ sơ</p>
                                                                <p>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].ClaimWKID : ''}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Mã yêu cầu</p>
                                                                <p>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) && (item.lstClaimSubmission[0].ClaimID !== item.lstClaimSubmission[0].ClaimWKID) ? item.lstClaimSubmission[0].ClaimID : ''}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Hợp đồng</p>
                                                                <p>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].PolicyNo : ''}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Người được bảo hiểm</p>
                                                                <p style={{textAlign: 'right'}}>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].LIFullName : ''}</p>
                                                            </div>
                                                            <div className="card__footer-item">
                                                                <p>Sự kiện bảo hiểm</p>
                                                                {((item.lstClaimSubmission[0] !== null
                                                                        && item.lstClaimSubmission[0] !== ''
                                                                        && item.lstClaimSubmission[0] !== undefined)) &&
                                                                        <p>{item.lstClaimSubmission[0]?.ClaimTypeVN?item.lstClaimSubmission[0]?.ClaimTypeVN: ''}</p>}
                                                            </div>

                                                            <div className="card__footer-item">
                                                                {item.Channel === 'mCP' &&
                                                                    <div
                                                                        className="flex-center card__footer-claim-details-wrapper"
                                                                        onClick={(e) => this.clickOnCardDetails(e, item, index)}>
                                                                        <p className="primary-text basic-semibold card__footer-claim-details-label">Xem
                                                                            chi tiết hồ sơ</p>
                                                                        <span className="arrow">
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                                                    </div>}
                                                                <p>{(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].ClaimWKID : ''}</p>
                                                            </div>

                                                            {(item.lstClaimSubmission[0] !== null
                                                                    && item.lstClaimSubmission[0] !== ''
                                                                    && item.lstClaimSubmission[0] !== undefined) && item.Status === "WFHOLD" &&
                                                                <div className="top-dash-line-short-margin"
                                                                     style={{marginBottom: 16}}></div>}

                                                            {(item.lstClaimSubmission[0] !== null
                                                                && item.lstClaimSubmission[0] !== ''
                                                                && item.lstClaimSubmission[0] !== undefined) && item.Status === "WFHOLD" && (
                                                                <div className="card__footer-item">
                                                                    <div className="dropdown"
                                                                         id={"dropdownContent" + index}>
                                                                        <div className="dropdown__content"
                                                                             onClick={(e) => {
                                                                                 e.stopPropagation();
                                                                                 dropdownContent(index);
                                                                             }}>
                                                                            <p className="primary-text basic-semibold">Xem
                                                                                chi
                                                                                tiết cần bổ sung</p>
                                                                            <p className="alternative-text basic-semibold">Thu
                                                                                gọn</p>
                                                                            <div className="arrow">
                                                                                <img src="img/icon/arrow-down-bronw.svg"
                                                                                     alt=""/>
                                                                            </div>
                                                                        </div>

                                                                        {(item.lstClaimSubmission[0] !== null
                                                                            && item.lstClaimSubmission[0] !== ''
                                                                            && item.lstClaimSubmission[0] !== undefined) && item.lstClaimSubmission[0].lstDocTypeHold.map((desc, index2) => (
                                                                            <div className="dropdown__items">
                                                                                <div className="content-wrapper">
                                                                                    <div className="item">
                                                                                        <div className="list-style"></div>
                                                                                        <div className="content">
                                                                                            <p>{desc.DocTypeName}</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        ))}
                                                                    </div>
                                                                </div>
                                                            )}
                                                        </div>
                                                    </div>
                                                    {(item.lstClaimSubmission[0] !== null
                                                            && item.lstClaimSubmission[0] !== ''
                                                            && item.lstClaimSubmission[0] !== undefined) && item.Status === "WFHOLD" &&
                                                        <div className="card__bottom">
                                                            <div className="card__bottom-info">
                                                                <p className="card__bottom-info-content">
                                                                    Vui lòng bổ sung chứng từ trước ngày&nbsp;
                                                                    <span
                                                                        className="card__bottom-info-red-text">{item.lstClaimSubmission[0]?.lstDocTypeHold[0]?.ExpiredDate}</span>
                                                                </p>
                                                                <button className="card__bottom-info-btn"
                                                                        onClick={(event) => clickOnCard(event, item, index)}>
                                                                    <p className="card__bottom-info-btn-label">Bổ sung thông
                                                                        tin</p>
                                                                </button>
                                                            </div>
                                                        </div>}
                                                </div>
                                            </div>

                                        </>


                                    )
                                )}
                                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                    <p>Tiếp tục</p>
                                    <i><img src="img/icon/arrow-left.svg" alt=""/></i>
                                </div>
                            </section>
                            <section className="sccontract-warpper">
                                {this.state.isEmpty || this.state.isWAITHC === true || this.state.isWAITWF === true
                                || this.state.isCompleted === false ? (
                                    <div className="breadcrums"
                                         style={{backgroundColor: this.state.claimDetails === null ? '#ffffff' : '#f5f3f2'}}>
                                        <div className="breadcrums__item">
                                            <p>Theo dõi yêu cầu</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                        <div className="breadcrums__item">
                                            <p>Giải quyết quyền lợi</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                    </div>) : (
                                    <div className="breadcrums" style={{backgroundColor: '#f1f1f1'}}>
                                        <div className="breadcrums__item">
                                            <p>Theo dõi yêu cầu</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                        <div className="breadcrums__item">
                                            <p>Giải quyết quyền lợi</p>
                                            <p className='breadcrums__item_arrow'>></p>
                                        </div>
                                    </div>
                                )}
                                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                                    <p>Chọn thông tin</p>
                                    <i><img src="img/icon/return_option.svg" alt=""/></i>
                                </div>
                                {this.state.isEmpty ? (
                                    <div className="sccontract-container">
                                        <div className="insurance">
                                            <div className="empty">
                                                <div className="icon">
                                                    <img src="img/icon/empty.svg" alt=""/>
                                                </div>
                                                <p style={{paddingTop: '20px'}}>Bạn hãy chọn thông tin ở phía bên trái
                                                    nhé!</p>
                                            </div>
                                        </div>
                                    </div>
                                ) : clientProfileDetails !== null && this.state.isViewClaimDetails ? (
                                        //Chi tiết theo dõi yêu cầu bổ sung
                                        this.state.spinning ? <LoadingIndicatorBasic/> :
                                            <div className="insurance">
                                                {(this.state.claimDetails.lstClaimSubmission[0] !== null
                                                    && this.state.claimDetails.lstClaimSubmission[0] !== ''
                                                    && this.state.claimDetails.lstClaimSubmission[0] !== undefined) &&
                                                this.state.claimDetails.Status === "WFHOLD" ?
                                                    <div
                                                        className="warning-wrapper">
                                                        <div className="flexColumn">
                                                            <div className="icon-wrapper">
                                                                <img src="img/icon/2.2/2.2-icon-signal.svg" alt=""/>
                                                            </div>

                                                            <div className="content">
                                                                <p>
                                                                    Yêu cầu này đang cần bổ sung Giấy tờ/Chứng. Vui lòng xem
                                                                    danh
                                                                    sách hồ sơ bồi thường để biết thêm chi tiết.
                                                                </p>
                                                            </div>
                                                        </div>
                                                        {this.state.claimDetails?.Status === 'WFHOLD' ?
                                                            <button className="button-profile"
                                                                    onClick={(event) => clickOnCard(event, this.state.claimDetails, this.state.claimDetails.claimDetailsIndex)}>Bổ
                                                                sung thông tin</button> :
                                                            <button className="button-profile">Xem danh sách hồ sơ</button>}
                                                    </div> : <p style={{
                                                        fontSize: '14px',
                                                        fontWeight: 700
                                                    }}>{`Yêu cầu ${this.state.claimDetails?.lstClaimSubmission[0]?.ClaimID}`}</p>}

                                                <div className="claimInfoForm">
                                                    <div className="claimInfoForm__head" style={{paddingBottom: 0}}>
                                                        {/* QUYỀN LỢI YÊU CẦU bẢO HIỂM */}
                                                        <h5 className="claimInfoForm__head-title">QUYỀN LỢI YÊU CẦU BẢO
                                                            HIỂM</h5>
                                                        <div className="claimInfoForm__head-content">
                                                            <div className="item">
                                                                <p className="item-label">Người được bảo hiểm</p>
                                                                <p className="item-content basic-bigx">{claimRequester?.LIFullName}</p>
                                                            </div>
                                                            <div className="item">
                                                                <p className="item-label">Ngày sinh</p>
                                                                <p className="item-content basic-bigx">{claimRequester?.LIBirthDate ? formatDateNew(claimRequester?.LIBirthDate) : ''}</p>
                                                            </div>
                                                            <div className="item">
                                                                <p className="item-label">Số giấy tờ tùy thân</p>
                                                                <p className="item-content basic-bigx">{claimRequester?.LIIDNum}</p>
                                                            </div>
                                                            <div className="item">
                                                                <p className="item-label">Nghề nghiệp</p>
                                                                <p className="item-content basic-bigx">{claimRequester?.Occupation}</p>
                                                            </div>
                                                            <div className="item">
                                                                <p className="item-label basic-big"
                                                                   style={{lineHeight: '22px'}}>Quyền lợi được chọn</p>
                                                                <p className="item-content basic-big"
                                                                   style={{textAlign: 'right', lineHeight: '22px'}}>
                                                                    {!isEmpty(claimTypeList) && mapClaimTypeToClaimName(claimSub?.ClaimType, claimTypeList)}
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div className="top-dash-line-short-margin"
                                                             style={{marginBottom: 16}}></div>
                                                    </div>
                                                    <div className="claimInfoForm__body">
                                                        {/* Thông tin sự kiện bảo hiểm */}
                                                        <div className="info">
                                                            <div className="info__title">THÔNG TIN SỰ KIỆN BẢO HIỂM
                                                            </div>
                                                            {/* Sự kiện bảo hiểm 1 */}
                                                            {this.state.isIllness && <div className="info__content">
                                                                <div className="info__content-item">
                                                                    <div className="input disabled">
                                                                        <div className="input__content">
                                                                            <label>Sự kiện bảo hiểm</label>
                                                                            <input value="Bệnh" disabled type="search"/>
                                                                        </div>
                                                                    </div>
                                                                    <div className="tab__content">
                                                                        <div className="illness-dropdown dropdown"
                                                                             id="sickDetailDropdown">
                                                                            <div className="dropdown__content"
                                                                                 style={{pading: 0, marginTop: 8}}
                                                                                 onClick={(e) => {
                                                                                     this.setState({
                                                                                         isExtendIllnessEvent: !this.state.isExtendIllnessEvent,
                                                                                     });
                                                                                     document.getElementById('sickDetailDropdown').className === 'illness-dropdown dropdown' ?
                                                                                         document.getElementById('sickDetailDropdown').className = 'illness-dropdown dropdown show' :
                                                                                         document.getElementById('sickDetailDropdown').className = 'illness-dropdown dropdown'
                                                                                 }}>
                                                                                <p className="simple-brown basic-semibold">
                                                                                    {!this.state.isExtendIllnessEvent ? 'Chi tiết' : 'Thu gọn'}
                                                                                </p>
                                                                                {!this.state.isExtendIllnessEvent ?
                                                                                    <span style={{
                                                                                        transform: 'rotate(-90deg)',
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 2,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span> : <span style={{
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 6,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                                                                }
                                                                            </div>
                                                                            <div className="dropdown__items">
                                                                                <div className="yellowtab">
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Thời điểm khởi phát bệnh</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.IllnessDate ? formatDateNew(claimDeath?.IllnessDate) : ''}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Triệu chứng/ chẩn đoán đầu
                                                                                            tiên</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.IllnessProgression}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Cơ sở y tế thăm khám đầu tiên</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.IllnessPlace}</p>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>}

                                                            <br/>
                                                            <br/>
                                                            {/* Sự kiện bảo hiểm 2 */}
                                                            {this.state.isAccident && <div className="info__content">
                                                                <div className="info__content-item">
                                                                    <div className="input disabled">
                                                                        <div className="input__content">
                                                                            <label>Sự kiện bảo hiểm</label>
                                                                            <input value="Tai nạn" disabled type="search"/>
                                                                        </div>
                                                                    </div>
                                                                    <div className="tab__content">
                                                                        <div className="accident-dropdown dropdown"
                                                                             id="accidentDetailDropdown">
                                                                            <div className="dropdown__content"
                                                                                 style={{pading: 0, marginTop: 8}}
                                                                                 onClick={(e) => {
                                                                                     this.setState({
                                                                                         isExtendAccidentEvent: !this.state.isExtendAccidentEvent,
                                                                                     });
                                                                                     document.getElementById('accidentDetailDropdown').className === 'accident-dropdown dropdown' ?
                                                                                         document.getElementById('accidentDetailDropdown').className = 'accident-dropdown dropdown show' :
                                                                                         document.getElementById('accidentDetailDropdown').className = 'accident-dropdown dropdown'
                                                                                 }}>
                                                                                <p className="simple-brown basic-semibold">
                                                                                    {!this.state.isExtendAccidentEvent ? 'Chi tiết' : 'Thu gọn'}
                                                                                </p>
                                                                                {/*<span*/}
                                                                                {/*    className="icon simple-brown basic-semibold"*/}
                                                                                {/*    style={{marginTop: 0}}>&gt;</span>*/}
                                                                                {!this.state.isExtendAccidentEvent ?
                                                                                    <span style={{
                                                                                        transform: 'rotate(-90deg)',
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 2,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span> : <span style={{
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 6,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                                                                }
                                                                            </div>
                                                                            <div className="dropdown__items">
                                                                                <div className="yellowtab">
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Diễn biến tai nạn</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.AccidentReason}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Thời gian</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.AccidentDate ? claimDeath?.AccidentDate : ''}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Nơi xảy ra tai nạn</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.AccidentPlace}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Tỉnh/Thành phố</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.AccidentProvince}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Quận/Huyện</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.AccidentDistrict}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Địa chỉ cụ thể</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>  {claimDeath.AccidentPlace +
                                                                                            (claimDeath.AccidentDistrict !== null
                                                                                            && claimDeath.AccidentDistrict !== undefined
                                                                                            && claimDeath.AccidentDistrict !== "" ? ", " + claimDeath.AccidentDistrict : "") +
                                                                                            (claimDeath.AccidentProvince !== null
                                                                                            && claimDeath.AccidentProvince !== undefined
                                                                                            && claimDeath.AccidentProvince !== "" ? ", " + claimDeath.AccidentProvince : "")}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left",
                                                                                            lineHeight: '22px',
                                                                                        }}>Sau tai nạn, người được bảo hiểm
                                                                                            có được khám/ điều trị tại cơ sở
                                                                                            y tế?</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>Có</p>
                                                                                    </div>
                                                                                    {claimDeath.AccidentIllnessDesc &&
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown" style={{ lineHeight: '22px' }}>Tình
                                                                                                trạng thương tật/sức khỏe
                                                                                                hiện tại của người được bảo
                                                                                                hiểm</p>
                                                                                            <p className="simple-brown"
                                                                                               style={{
                                                                                                   maxWidth: "300px",
                                                                                                   textAlign: "right",
                                                                                                   width: '50%'
                                                                                               }}>{claimDeath.AccidentIllnessDesc}</p>
                                                                                        </div>
                                                                                    }
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>}

                                                            <br/>
                                                            <br/>
                                                            {/* Sự kiện bảo hiểm 3 */}
                                                            {this.state.isLoss && <div className="info__content">
                                                                <div className="info__content-item">
                                                                    <div className="input disabled">
                                                                        <div className="input__content">
                                                                            <label>Sự kiện bảo hiểm</label>
                                                                            <input value="Tử vong" disabled type="search"/>
                                                                        </div>
                                                                    </div>
                                                                    <div className="tab__content">
                                                                        <div className="loss-dropdown dropdown"
                                                                             id="lossDetailDropdown">
                                                                            <div className="dropdown__content"
                                                                                 style={{pading: 0, marginTop: 8}}
                                                                                 onClick={(e) => {
                                                                                     this.setState({
                                                                                         isExtendLossEvent: !this.state.isExtendLossEvent,
                                                                                     });
                                                                                     document.getElementById('lossDetailDropdown').className === 'loss-dropdown dropdown' ?
                                                                                         document.getElementById('lossDetailDropdown').className = 'loss-dropdown dropdown show' :
                                                                                         document.getElementById('lossDetailDropdown').className = 'loss-dropdown dropdown'
                                                                                 }}>
                                                                                <p className="simple-brown basic-semibold">
                                                                                    {!this.state.isExtendLossEvent ? 'Chi tiết' : 'Thu gọn'}
                                                                                </p>
                                                                                {/*<span*/}
                                                                                {/*    className="icon simple-brown basic-semibold"*/}
                                                                                {/*    style={{marginTop: 0}}>&gt;</span>*/}
                                                                                {!this.state.isExtendLossEvent ?
                                                                                    <span style={{
                                                                                        transform: 'rotate(-90deg)',
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 2,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span> : <span style={{
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 6,
                                                                                    }}>
                                                                    <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                                                                </span>
                                                                                }
                                                                            </div>
                                                                            <div className="dropdown__items">
                                                                                <div className="yellowtab">
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Thời gian</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.LossDate ?
                                                                                            moment(claimDeath?.LossDate, "DD/MM/YYYY HH:mm:ss.SSS").format("DD/MM/YYYY hh:mm:ss") : ""}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Nơi xãy ra tử vong</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.LossPlace}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Tỉnh/Thành phố</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.LossProvince}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Quận/Huyện</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.LossDistrict}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Địa chỉ cụ thể</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>  {claimDeath.LossPlace +
                                                                                            (claimDeath.LossDistrict !== null
                                                                                            && claimDeath.LossDistrict !== undefined
                                                                                            && claimDeath.LossDistrict !== "" ? ", " + claimDeath.LossDistrict : "") +
                                                                                            (claimDeath.LossProvince !== null
                                                                                            && claimDeath.LossProvince !== undefined
                                                                                            && claimDeath.LossProvince !== "" ? ", " + claimDeath.LossProvince : "")}</p>
                                                                                    </div>
                                                                                    <div className="tab">
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "left"
                                                                                        }}>Nguyên nhân tử vong</p>
                                                                                        <p className="simple-brown" style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{claimDeath?.LossProgression}</p>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>}


                                                        </div>
                                                        {this.state.isTreatmentHistory &&
                                                            <div className="top-dash-line-short-margin"
                                                                 style={{marginBottom: 16}}></div>}
                                                        {/* Thông tin khám điều trị tại cơ sở y tế */}
                                                        {this.state.isTreatmentHistory && <div className="info">
                                                            {treatmentHistorys?.map((item, index) => (
                                                                <div className="info__content">
                                                                    <div className="info__title">THÔNG TIN KHÁM/ ĐIỀU TRỊ
                                                                    </div>
                                                                    <div className="info__content-item">
                                                                        <div className="tabflex-wrapper">
                                                                            <div className="claimInfoLabel">
                                                                                <h5>Cơ sở y tế</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right",
                                                                                    fontWeight: '700',
                                                                                }}>{item.TreatmentHospital ? item.TreatmentHospital : ''}</p>
                                                                            </div>
                                                                            <div className="claimInfoLabel">
                                                                                <h5>Tỉnh/ Thành phố</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right"
                                                                                }}>{item.TreatmentProvince ? item.TreatmentProvince : ''}</p>
                                                                            </div>
                                                                            <div className="claimInfoLabel">
                                                                                <h5>Quận/ Huyện</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right"
                                                                                }}>{item.TreatmentDistrict ? item.TreatmentDistrict : ''}</p>
                                                                            </div>

                                                                            {item.TreatmentDateFrom && item.TreatmentDateTo ? (
                                                                                formatDateNew(item.TreatmentDateFrom) === formatDateNew(item.TreatmentDateTo) ? (
                                                                                    <div className="claimInfoLabel">
                                                                                        <h5>Ngày khám/ điều trị</h5>
                                                                                        <p style={{
                                                                                            maxWidth: "300px",
                                                                                            textAlign: "right"
                                                                                        }}>{formatDateNew(item.TreatmentDateFrom)}</p>
                                                                                    </div>
                                                                                ) : (
                                                                                    <>
                                                                                        <div className="claimInfoLabel">
                                                                                            <h5>Ngày khám/ điều trị</h5>
                                                                                            <p style={{
                                                                                                maxWidth: "300px",
                                                                                                textAlign: "right"
                                                                                            }}>{formatDateNew(item.TreatmentDateFrom) + '-' + formatDateNew(item.TreatmentDateTo)}</p>
                                                                                        </div>
                                                                                    </>
                                                                                )
                                                                            ) : (
                                                                                ''
                                                                            )}

                                                                            <div className="claimInfoLabel">
                                                                                <h5>Hình thức điều trị</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right"
                                                                                }}>{item?.PatientType ? mapValueToDescription(item?.PatientType, TREAMENT_TYPE) : ''}</p>
                                                                            </div>
                                                                            <div className="claimInfoLabel">
                                                                                <h5>Chẩn đoán</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right",
                                                                                    lineHeight: '22px',
                                                                                }}>{item?.Diagnostic ? item?.Diagnostic : ' '}</p>
                                                                            </div>

                                                                            {!isEmpty(item.lsTaxInvoice) && <div className="claimInfoLabel">
                                                                                <span style={{ display: 'flex' }}><h5>Tổng chi
                                                                                    phí cho lần điều trị này</h5>
                                                                                    {/*<i className="info-icon"*/}
                                                                                    {/*   onClick={() => showNotice()}*/}
                                                                                    {/*   style={{ marginLeft: 6, cursor: 'pointer' }}*/}
                                                                                    {/*><img*/}
                                                                                    {/*    src="../../img/icon/Information-step.svg"*/}
                                                                                    {/*    alt="information-icon"*/}
                                                                                    {/*    className="info-icon"*/}
                                                                                    {/*    style={{ width: 18, height: 18 }}*/}
                                                                                    {/*/></i>*/}
                                                                                </span>
                                                                                <p className="claim-submission-text"
                                                                                   style={{
                                                                                       maxWidth: "300px",
                                                                                       textAlign: "right"
                                                                                   }}>
                                                                                    <NumberFormat displayType="search"
                                                                                                  type="search"
                                                                                                  value={sumInvoice(item.lsTaxInvoice)}
                                                                                                  thousandsGroupStyle="thousand"
                                                                                                  thousandSeparator={'.'}
                                                                                                  suffix={' VNĐ'}
                                                                                                  decimalSeparator=","
                                                                                                  decimalScale="0"
                                                                                                  style={{padding: '0px'}}
                                                                                    />
                                                                                </p>
                                                                            </div>}
                                                                            {/*<div className="item">*/}
                                                                            {/*    <div className="optionalform__title">*/}
                                                                            {/*        <p className="--item__title">Tổng chi*/}
                                                                            {/*            phí cho lần điều trị này</p>*/}
                                                                            {/*        <i className="info-icon"*/}
                                                                            {/*           onClick={() => showNotice()}*/}
                                                                            {/*        ><img*/}
                                                                            {/*            src="../../img/icon/Information-step.svg"*/}
                                                                            {/*            alt="information-icon"*/}
                                                                            {/*            className="info-icon"*/}
                                                                            {/*        /></i>*/}
                                                                            {/*    </div>*/}
                                                                            {/*    <div className="item__content">*/}
                                                                            {/*        <div className="tab">*/}

                                                                            {/*            <div className="tab__content">*/}
                                                                            {/*                <div className="input disabled">*/}
                                                                            {/*                    <div*/}
                                                                            {/*                        className="input__content">*/}
                                                                            {/*                        <label>Số tiền</label>*/}
                                                                            {/*                        <NumberFormat*/}
                                                                            {/*                            displayType="input"*/}
                                                                            {/*                            type="search"*/}
                                                                            {/*                            prefix=""*/}
                                                                            {/*                            value={totalAmount}*/}
                                                                            {/*                            thousandsGroupStyle="thousand"*/}
                                                                            {/*                            thousandSeparator={'.'}*/}
                                                                            {/*                            decimalSeparator=","*/}
                                                                            {/*                            decimalScale="0"*/}
                                                                            {/*                            suffix={' VNĐ'}*/}
                                                                            {/*                            allowNegative={false}*/}
                                                                            {/*                            disabled*/}
                                                                            {/*                        />*/}
                                                                            {/*                    </div>*/}
                                                                            {/*                </div>*/}
                                                                            {/*            </div>*/}
                                                                            {/*        </div>*/}
                                                                            {/*    </div>*/}
                                                                            {/*</div>*/}
                                                                            {item.lsTaxInvoice && item.lsTaxInvoice.map((it, idx) => {
                                                                                if (it.RequestAmount > 0) {
                                                                                    totalAmount = totalAmount + parseInt(it.RequestAmount);
                                                                                }
                                                                                if (it.InvoiceNo) {
                                                                                    return (
                                                                                        <>
                                                                                            <div className="tabflex"
                                                                                                 key={'sub-faci-invoice-' + idx}>
                                                                                                {/*<h5>Số hoá*/}
                                                                                                {/*    đơn {item.lsTaxInvoice.length > 1 ? idx + 1 : ''}</h5>*/}
                                                                                                <h5>Số hóa đơn</h5>
                                                                                                <p style={{
                                                                                                    maxWidth: "300px",
                                                                                                    textAlign: "right"
                                                                                                }}>
                                                                                                    {it.InvoiceNo}
                                                                                                </p>
                                                                                            </div>
                                                                                            {item?.RequestAmount > 0 && <div className="tabflex">
                                                                                                <h5>Số tiền được chi trả</h5>
                                                                                                <p className="claim-submission-text"
                                                                                                   style={{
                                                                                                       maxWidth: "300px",
                                                                                                       textAlign: "right"
                                                                                                   }}>
                                                                                                    <NumberFormat
                                                                                                        displayType="search"
                                                                                                        type="search"
                                                                                                        value={it.RequestAmount}
                                                                                                        thousandsGroupStyle="thousand"
                                                                                                        thousandSeparator={'.'}
                                                                                                        suffix={' VNĐ'}
                                                                                                        decimalSeparator=","
                                                                                                        decimalScale="0"
                                                                                                        style={{padding: '0px'}}
                                                                                                    />
                                                                                                </p>
                                                                                            </div>}
                                                                                        </>
                                                                                    )
                                                                                }
                                                                            })}
                                                                            {item.InsuranceCompany &&
                                                                                <div className="claimInfoLabel">
                                                                                    <h5>Đã được chi trả tại công ty bảo hiểm
                                                                                        khác</h5>
                                                                                    <p style={{
                                                                                        maxWidth: "300px",
                                                                                        textAlign: "right",
                                                                                        fontWeight: '700'
                                                                                    }}>Có</p>
                                                                                </div>}
                                                                            {item.InsuranceCompany && <div className="claimInfoLabel">
                                                                                <h5>Công ty bảo hiểm đã chi trả</h5>
                                                                                <p style={{
                                                                                    maxWidth: "300px",
                                                                                    textAlign: "right",
                                                                                    fontWeight: '700'
                                                                                }}>{item.InsuranceCompany ? item.InsuranceCompany : ''}</p>
                                                                            </div>}
                                                                            {item?.InsuranceAmount > 0 && <div className="claimInfoLabel">
                                                                                <h5>Số tiền được chi trả</h5>
                                                                                <p className="claim-submission-text"
                                                                                   style={{
                                                                                       maxWidth: "300px",
                                                                                       textAlign: "right"
                                                                                   }}>
                                                                                    <NumberFormat displayType="search"
                                                                                                  type="search"
                                                                                                  value={item?.InsuranceAmount ? removeTrailingZeros(item?.InsuranceAmount) : ''}
                                                                                                  thousandsGroupStyle="thousand"
                                                                                                  thousandSeparator={'.'}
                                                                                                  suffix={' VNĐ'}
                                                                                                  decimalSeparator=","
                                                                                                  decimalScale="0"
                                                                                                  style={{padding: '0px'}}
                                                                                    />
                                                                                </p>
                                                                            </div>}
                                                                        </div>
                                                                    </div>
                                                                    {this.state.isTreatmentHistory && treatmentHistorys.length - 1 !== index &&
                                                                        <div className="top-dash-line-short-margin"
                                                                             style={{marginBottom: 16}}></div>}
                                                                </div>

                                                            ))}
                                                        </div>}
                                                        {!isEmpty(othersInsurance) &&
                                                            <div className="top-dash-line-short-margin"
                                                                 style={{marginBottom: 16}}></div>}
                                                        <img className="decor-clip" src="img/mock.svg" alt=""/>
                                                        <img className="decor-person" src="img/person.png" alt=""/>
                                                    </div>
                                                </div>

                                                {!isEmpty(paymentList) && <div className="claimInfoForm claimInfoFormFrame">
                                                    <div className="claimInfoForm__body">
                                                        {/* Thông tin thanh toán */}
                                                        <div className="info">
                                                            <div className="info__title">THÔNG TIN THANH TOÁN
                                                            </div>
                                                            {paymentList?.map((item, index) => (
                                                                <div className="info__content" key={index}>
                                                                    <div className="info__content-item">
                                                                        <div className="input disabled">
                                                                            <div className="input__content">
                                                                                <label>Phương thức thanh toán</label>
                                                                                <input
                                                                                    // value={item?.PaymentMethod}
                                                                                    value={item.PaymentMethodID === 'P1' ? 'Nhận tiền mặt tại ngân hàng' : 'Chuyển khoản' }
                                                                                    disabled
                                                                                    type="search"
                                                                                />
                                                                            </div>
                                                                        </div>
                                                                        <div className="tab__content">
                                                                            <div className="yellow-dropdown dropdown" id={`lifeBenPaymentDetailDropdown-${index}`}>
                                                                                <div className="dropdown__content" style={{ padding: 0, marginTop: 8 }} onClick={(e) => {
                                                                                    const dropdownId = `lifeBenPaymentDetailDropdown-${index}`;
                                                                                    this.setState({
                                                                                        [`isExtendMethod${index}`]: !this.state[`isExtendMethod${index}`],
                                                                                    });
                                                                                    document.getElementById(dropdownId).classList.toggle('show');
                                                                                }}>
                                                                                    <p className="simple-brown basic-semibold">
                                                                                        {this.state[`isExtendMethod${index}`] ? 'Thu gọn' : 'Chi tiết'}
                                                                                    </p>
                                                                                    <span style={{
                                                                                        transform: !this.state[`isExtendMethod${index}`] ? 'rotate(-90deg)' : '',
                                                                                        transition: 'transform 0.3s ease',
                                                                                        width: 14,
                                                                                        height: 14,
                                                                                        marginLeft: 8,
                                                                                        marginTop: 2,
                                                                                    }}>
                            <img src="img/icon/arrow-down-bronw.svg" alt=""/>
                        </span>
                                                                                </div>
                                                                                <div className={`dropdown__items ${this.state[`isExtendMethod${index}`] ? 'show' : ''}`}>
                                                                                    <div className="yellowtab">
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown">Tỉnh/Thành phố</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.BankProvince}</p>
                                                                                        </div>
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown">Ngân hàng</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.BankName}</p>
                                                                                        </div>
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown">Chi nhánh</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.BankBranch}</p>
                                                                                        </div>
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown">Phòng giao dịch</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.BankDealingRoom}</p>
                                                                                        </div>
                                                                                        {item.PaymentMethodID === 'P2' && <div className="tab">
                                                                                            <p className="simple-brown">Số tài khoản</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.AccountNumber}</p>
                                                                                        </div>}
                                                                                        <div className="tab">
                                                                                            <p className="simple-brown">{`${item.PaymentMethodID === 'P1' ? 'Họ tên người nhận' : 'Tên chủ tài khoản'}`}</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.AccountName}</p>
                                                                                        </div>
                                                                                        {item.PaymentMethodID === 'P1' && <div className="tab">
                                                                                            <p className="simple-brown">CMND/CCCD</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.PayIssueID}</p>
                                                                                        </div>}
                                                                                        {item.PaymentMethodID === 'P1' && <div className="tab">
                                                                                            <p className="simple-brown">Ngày cấp</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.PayIssueDate ? formatDateNew(item?.PayIssueDate) : ''}</p>
                                                                                        </div>}
                                                                                        {item.PaymentMethodID === 'P1' && <div className="tab">
                                                                                            <p className="simple-brown">Nơi cấp</p>
                                                                                            <p className="simple-brown" style={{ maxWidth: "300px", textAlign: "right" }}>{item?.PayIssuePlace}</p>
                                                                                        </div>}
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            ))}

                                                        </div>
                                                    </div>
                                                </div>}

                                                <div className="claimInfoForm claimInfoFormFrame">
                                                    <div className="claimInfoForm__body">
                                                        {/* Thông tin liên hệ */}
                                                        <div className="info">
                                                            <div className="info__title">THÔNG TIN LIÊN HỆ
                                                            </div>
                                                            <div className="info__content">
                                                                <div className="info__content-item">
                                                                    <div className="tabflex-wrapper">
                                                                        <div className="claimInfoLabel">
                                                                            <h5>Họ và tên</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right",
                                                                                fontWeight: '700'
                                                                            }}>{claimRequester?.FullName ? claimRequester?.FullName : claimRequester?.FullName}</p>
                                                                        </div>
                                                                        <div className="claimInfoLabel">
                                                                            <h5>Ngày sinh</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right"
                                                                            }}>{claimRequester?.DOB ? formatDateNew(claimRequester?.DOB) : ''}</p>
                                                                        </div>
                                                                        <div className="claimInfoLabel">
                                                                            <h5>CMND/CCCD</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right"
                                                                            }}>{claimRequester?.IDNum ? claimRequester?.IDNum : claimRequester?.IDNum}</p>
                                                                        </div>
                                                                        <div className="claimInfoLabel">
                                                                            <h5>Điện thoại</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right"
                                                                            }}>{claimRequester?.CellPhone}</p>
                                                                        </div>
                                                                        <div className="claimInfoLabel">
                                                                            <h5>Email</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right"
                                                                            }}>{claimRequester?.Email}</p>
                                                                        </div>
                                                                        {/*<div className="claimInfoLabel">*/}
                                                                        {/*    <h5>Quan hệ với bên mua bảo hiểm</h5>*/}
                                                                        {/*    <p style={{*/}
                                                                        {/*        maxWidth: "300px",*/}
                                                                        {/*        textAlign: "right"*/}
                                                                        {/*    }}>{claimRequester?.Relationship}</p>*/}
                                                                        {/*</div>*/}
                                                                        <div className="claimInfoLabel">
                                                                            <h5>Địa chỉ</h5>
                                                                            <p style={{
                                                                                maxWidth: "300px",
                                                                                textAlign: "right"
                                                                            }}>{claimRequester?.Address}</p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div className="claimInfoForm claimInfoFormFrame">
                                                    <div className="claimInfoForm__body">
                                                        {/* GIẤY TỜ/ CHỨNG TỪ ĐÍNH KÈM */}
                                                        <div className="info">
                                                            <div className="info__title">
                                                                <div className="info__title">GIẤY TỜ/ CHỨNG TỪ ĐÍNH KÈM
                                                                </div>
                                                            </div>
                                                            {lstDocType?.map((item, index) => {
                                                                const baseUrl = "https://magpcmsuat2.dai-ichi-life.com.vn/iibmobile/v1/imagebyname?filename=";
                                                                const modifiedData = item.lstURL?.map(filename => baseUrl + filename);

                                                                return (
                                                                    <div className="info__content" key={index}>
                                                                        <div className="info__content-item">
                                                                            <div className="tabflex-wrapper">
                                                                                <div className="claimInfoLabel">
                                                                                    <h3>{item?.DocTypeName}</h3>
                                                                                </div>
                                                                                <div className="claimInfo-image-wrapper">
                                                                                    <ImageViewer imageList={modifiedData}/>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                );
                                                            })}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                    ) :
                                    (
                                        <div className="sccontract-container">
                                            <LoadingIndicator area="claim-check-hold"/>
                                            <br/>
                                            {this.state.isCompleted === false ? (
                                                <div>
                                                    <div className="warning-wrapper">
                                                        <div className="icon-wrapper">
                                                            <img src="img/icon/2.2/2.2-icon-signal.svg" alt=""/>
                                                        </div>

                                                        <div className="content">
                                                            <p>
                                                                Vui lòng bổ sung Giấy tờ/Chứng từ còn thiếu/chưa hợp lệ
                                                                trước ngày
                                                                <span
                                                                    className="light-brown-text"> {this.state.claimList?.lstClaimSubmission[0].lstDocTypeHold[0] ? this.state.claimList?.lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate : ''}</span> để
                                                                chúng tôi thẩm định yêu cầu quyền lợi này và phản hồi
                                                                kết
                                                                quả đến Quý khách.
                                                            </p>
                                                        </div>
                                                    </div>

                                                    <div className="stepform form-wrapper"
                                                         style={{marginTop: '120px!important'}}>
                                                        <div className="stepform__body">
                                                            {this.state.checkHoldList !== null && this.state.checkHoldList.map((holdData, index4) =>
                                                                <div className="info">
                                                                    {/* DocName */}
                                                                    <div className="info__subtitle">
                                                                        <p className="basic-semibold">{holdData.DocName}</p>
                                                                    </div>
                                                                    <div className="info__body">
                                                                        {/* Upload image area */}
                                                                        <div className="item">
                                                                            <div className="item__content">
                                                                                {(claimAttachment[index4].attachmentList !== null
                                                                                    && claimAttachment[index4].attachmentList !== undefined
                                                                                    && !!claimAttachment[index4].attachmentList
                                                                                    && claimAttachment[index4].attachmentList.length > 0) ?
                                                                                    <div
                                                                                        className="img-upload-wrapper not-empty">
                                                                                        {claimAttachment[index4].attachmentList.map((att, attIdx) => (
                                                                                            <div
                                                                                                className="img-upload-item"
                                                                                                key={attIdx}>
                                                                                                <div className="file">
                                                                                                    <div
                                                                                                        className="img-wrapper">
                                                                                                        <img
                                                                                                            src={att.imgData}
                                                                                                            alt=""/>
                                                                                                    </div>
                                                                                                    <div
                                                                                                        className="deletebtn"
                                                                                                        onClick={() => this.handlerDeleteAttachment(index4, attIdx)}>X
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        ))}
                                                                                        <div
                                                                                            className="img-upload-item">
                                                                                            <div className="img-upload"
                                                                                                 id={"img-upload-claimAttachment_" + index4}
                                                                                                 onClick={() => {
                                                                                                     document.getElementById("claimAttInput_" + index4).click()
                                                                                                 }}
                                                                                                 onDragOver={(event) => this.handlerDragOver("img-upload-claimAttachment_" + index4, event)}
                                                                                                 onDragLeave={(event) => this.handlerDragLeave("img-upload-claimAttachment_" + index4, event)}
                                                                                                 onDrop={(event) => this.handlerDrop(index4, event, this.handlerUpdateAttachmentList)}>
                                                                                                <button
                                                                                                    className="circle-plus">
                                                                                                    <img
                                                                                                        src="img/icon/plus.svg"
                                                                                                        alt="circle-plus"
                                                                                                        className="plus-sign"/>
                                                                                                </button>
                                                                                                <p className="basic-grey">
                                                                                                    Kéo & thả tệp tin
                                                                                                    hoặc
                                                                                                    <span
                                                                                                        className="basic-red basic-semibold">chọn tệp</span>
                                                                                                </p>
                                                                                                <input
                                                                                                    id={"claimAttInput_" + index4}
                                                                                                    className="inputfile"
                                                                                                    type="file"
                                                                                                    multiple={true}
                                                                                                    hidden={true}
                                                                                                    accept="image/*"
                                                                                                    onChange={(e) => this.handlerUploadAttachment(index4, e, this.handlerUpdateAttachmentList)}/>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div> :
                                                                                    <div className="img-upload-wrapper">
                                                                                        <div
                                                                                            className="img-upload-item">
                                                                                            <div className="img-upload"
                                                                                                 id={"img-upload-empty-" + index4}
                                                                                                 onClick={() => {
                                                                                                     document.getElementById("claimAttEmptyInput_" + index4).click()
                                                                                                 }}
                                                                                                 onDragOver={(event) => this.handlerDragOver("img-upload-empty-" + index4, event)}
                                                                                                 onDragLeave={(event) => this.handlerDragLeave("img-upload-empty-" + index4, event)}
                                                                                                 onDrop={(event) => this.handlerDrop(index4, event, this.handlerUpdateAttachmentList)}>
                                                                                                <button
                                                                                                    className="circle-plus">
                                                                                                    <img
                                                                                                        src="img/icon/plus.svg"
                                                                                                        alt="circle-plus"
                                                                                                        className="plus-sign"/>
                                                                                                </button>
                                                                                                <p className="basic-grey">
                                                                                                    Kéo & thả tệp tin
                                                                                                    hoặc&nbsp;
                                                                                                    <span
                                                                                                        className="basic-red basic-semibold">chọn tệp</span>
                                                                                                </p>
                                                                                                <input
                                                                                                    id={"claimAttEmptyInput_" + index4}
                                                                                                    className="inputfile"
                                                                                                    type="file"
                                                                                                    multiple={true}
                                                                                                    hidden={true}
                                                                                                    accept="image/*"
                                                                                                    onChange={(e) => this.handlerUploadAttachment(index4, e, this.handlerUpdateAttachmentList)}/>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                }
                                                                            </div>
                                                                        </div>
                                                                        {/* TODO ifelse comment area */}
                                                                        {/* Comment area */}
                                                                        <div className="item">
                                                                            <h5 className="item__title">* Phản hồi từ
                                                                                Dai-ichi Việt Nam</h5>
                                                                            <h5 className="item__title">{holdData.ClaimAdminComment}</h5>
                                                                            {/* User response */}
                                                                            <div className="item__content">
                                                                                <div className="tab">
                                                                                    <div className="tab__content">
                                                                                        <div className="input textarea">
                                                                                            <div
                                                                                                className="input__content">
                                                                                                <label>Câu trả
                                                                                                    lời</label>
                                                                                                <textarea
                                                                                                    className='eclaim-text-area'
                                                                                                    name={"comment" + index4}
                                                                                                    onChange={(event) => this.handlerChangeInputComment(index4, event)}
                                                                                                    placeholder="Ví dụ: Mất chứng từ ghi nhận sự kiện bảo hiểm"
                                                                                                    rows="4"
                                                                                                ></textarea>
                                                                                            </div>
                                                                                            <i><img
                                                                                                src="img/icon/edit.svg"
                                                                                                alt=""/></i>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            )}
                                                            <img className="decor-clip" src="img/mock.svg" alt=""/>
                                                            <img className="decor-person" src="img/person.png" alt=""/>
                                                        </div>
                                                    </div>
                                                    <div className="bottom-btn">
                                                        {this.state.attachmentState.disabledButton ?
                                                            <button className="btn btn-primary disabled">Gửi bổ
                                                                sung</button> :
                                                            <button className="btn btn-primary"
                                                                    onClick={() => submitClaimHold()}>Gửi bổ
                                                                sung</button>}
                                                    </div>
                                                </div>
                                            ) : (
                                                this.state.spinning ? <LoadingIndicatorBasic/> :
                                                    <div className="insurance">
                                                        {this.state.isWAITHC === true ? (
                                                            <div className="empty">
                                                                <div className="icon">
                                                                    <img src="img/2.2-nodata-image.png" alt="no-data"/>
                                                                </div>
                                                                <h4 className="basic-semibold">Yêu cầu chờ bổ sung
                                                                    chứng
                                                                    từ
                                                                    gốc</h4>
                                                                <span>Vui lòng gửi chứng từ gốc đến văn phòng Dai-ichi Life Việt Nam</span>
                                                                <span style={{paddingTop: '4px'}}>Thông tin sẽ được cập nhật đến bạn sớm nhất</span>
                                                            </div>
                                                        ) : (
                                                            <div className="empty">
                                                                <div className="icon">
                                                                    <img src="img/2.2-nodata-image.png" alt="no-data"/>
                                                                </div>
                                                                <h4 className="basic-semibold">Yêu cầu đang được xử
                                                                    lý</h4>
                                                                <span>Dai-ichi life Việt Nam sẽ cập nhật thông tin chi tiết sớm nhất</span>
                                                            </div>
                                                        )}
                                                    </div>
                                            )}
                                        </div>
                                    )
                                }
                            </section>
                        </div>
                        {
                            this.state.nextScreen === true && (
                                <div className="popup envelop show" id="popup">
                                    <div className="popup__card envelop-infomation">
                                        <button className="envelop-infomation__close-button" onClick={() => closePopup()}>
                                            <img src="img/icon/close-icon.svg" alt=""/>
                                        </button>
                                        <div className="envelop-infomation__content">
                                            <h5 className="basic-bold">gửi yêu cầu thành công</h5>

                                            <p>Chúng tôi sẽ xử lý và thông báo đến quý khách tình trạng cập nhật
                                                trong thời gian sớm nhất</p>
                                        </div>
                                    </div>
                                    <div className="popupbg"></div>
                                </div>
                            )
                        }
                    </main>
                )}
                {this.state.showNotice &&
                    <NoticePopup closePopup={closeNotice} title='Tổng chi phí cho lần điều trị này'
                                 msg={parse('Tổng chi phí được tính dựa vào số tiền của <br/> các hóa đơn cho một lần khám và điều trị')}
                                 imgPath={FE_BASE_URL + '/img/popup/total-claim-fee.png'}/>
                }
            </div>
        )
            ;
    }
}


export default withRouter(ClaimInfo);
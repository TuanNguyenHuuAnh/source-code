import React, { useState, useEffect } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, FE_BASE_URL, PERSONAL_INFO_STATE, PageScreen, CELL_PHONE, TWOFA, EMAIL, DCID, COMPANY_KEY2, COMPANY_KEY, AUTHENTICATION, FULL_NAME, WEB_BROWSER_VERSION, CCCD_QR_CODE, SCREENS, OCR_UNIDENTIFIED, IDDOCUMENTS_FRONT, IDDOCUMENTS_BACK, IDDOCUMENTS_BC, IDDOCUMENTS_PP, CLIENT_CLASS, CCCD_FRONTS, CCCD_BACKS, PERSONAL_INFO_SAVE_LOCAL, DOCUMENTS_ORDER, ConsentStatus, GENDER_MAP, IS_MOBILE, GENDER, OTHERS_NC, OTHERS_CV, CACHE_TRACKING_ID, CACHE_PERSONALINFO_KEY   } from '../sdkConstant';
import { getDeviceId, getSession, cpSaveLog, getUrlParameter, trackingEvent, getUrlParameterNoDecode, formatFullName, generateConsentResults, getMapSize, setLocal, getLocal, getGenderCode, capitalizeFirstLetter, removeLocal, parseHtml, toLowerCase, formatDate, removeSpecialCharactersAndSpace, toUpperCase, setSession, removeSession } from '../sdkCommon';
import { onlineRequestSubmitUserInfo, onlineRequestSubmitESubmissionCCI, CPConsentConfirmation, logoutSession, onlineRequestSubmit } from '../sdkAPI';
import { Helmet } from "react-helmet";
import PersonalInfoList from './PersonalInfoList';
import { useParams, useHistory } from 'react-router-dom';
import AES256 from 'aes-everywhere';
import InputPersonalInfo from './InputPersonalInfo';
import AdditionalAttachment from './AdditionalAttachment';
import ReviewPersonalInfo from './ReviewPersonalInfo';
import AlertPopupPhone from '../../components/AlertPopupPhone';
import AlertPopupCustom from '../../components/AlertPopupCustom';
import AuthenticationPopup from '../../components/AuthenticationPopup';
import NationalityPopup from '../components/NationalityPopup';
import ConfirmChangePopup from '../components/ConfirmChangePopup';
import POWarningND13 from '../ModuleND13/ND13Modal/POWarningND13/POWarningND13';
import AlertPopupError from '../components/AlertPopupError';
import imageCompression from 'browser-image-compression';
import moment from 'moment';
import ND13 from "../ND13";
import {parse, stringify} from 'flatted';
import AgreePopup from '../components/AgreePopup';
import NoticeHouseHoldRegistration from '../components/NoticeHouseHoldRegistration';
import ND13Expire from '../ND13Expire';
import ExpirePopup from '../components/ExpirePopup';

let fromNative = '';
let popupImgPath = '';
const UpdatePersonalInfo = (props) => {
  const history = useHistory();
  // const [stepName, setStepName] = useState(PERSONAL_INFO_STATE.CHOOSE_LI);
  const { param } = useParams();
  const [state, setState] = useState({
    personalInfoChecked: false,
    residenceCheck: false,
    occupationChecked: false,
    trackingId: '', 
    clientListStr: '', 
    clientId: getSession(CLIENT_ID),
    proccessType: 'CCI',
    appType: 'CLOSE',
    deviceId: getDeviceId(),
    apiToken: getSession(ACCESS_TOKEN),
    phone: getSession(CELL_PHONE),
    twoFA: getSession(TWOFA),
    email: getSession(EMAIL),
    DCID: getSession(DCID),
    clientClass: getSession(CLIENT_CLASS), 
    clientName: getSession(FULL_NAME),
    // DOB: formatDate(getSession(DOB)),
    // Gender: GENDER_MAP[getSession(GENDER)],
    // POID: getSession(POID),
    noPhone: false,
    noTwofa: false,
    stepName: PERSONAL_INFO_STATE.INIT,
    selectedLI: null,
    personalInfo: {
      DocTypeNameSelected: undefined,
      attachmentState: {
        previewVisible: false,
        previewImage: "",
        previewTitle: "",
        attachmentList: [],
        attachmentMap: {},
        disabledButton: true,
      },
      ocrSuccess: false,
      isQRCode: false,
      QRCodeFrontOrBack: '',
      personalInfoChange: [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          OldClientIDNumber: '',
          ppChecked: false,
          NewClientPassportNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderS',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ]
    },
    residenceInfo: {
      nationSelected: undefined,
      EntryDateSelected: undefined,
      ProcessTypeCode: 'ResidenceS',
      NewCountryOfResidenceCode: '',
      NewCountryOfResidence: '',
      EntryDate: '',
      PurposeOfResidence: undefined,
      PurposeOfResidenceDescription: '',
      Duration: undefined,
      USNationality: 'No',
      USPermanent: 'No',
      USTaxDeclared: 'No',
      occupationSelected: undefined,
      NewOccClassCode: '',
      NewOccClass: '',
      NewOccNameCode: '',
      NewOccName: '',
      OccDescription: ''
    },
    occupationInfo: {
      occupationSelected: undefined,
      ProcessTypeCode: 'OccupationS',
      NewOccClassCode: '',
      NewOccClass: '',
      NewOccNameCode: '',
      NewOccName: '',
      OccDescription: '',
      IsChangeFromResidence: ''
    },
    attachmentState: {
      previewVisible: false,
      previewImage: "",
      previewTitle: "",
      attachmentList: [],
      attachmentMap: {},
      disabledButton: true,
    },
    docTypeProfile: null,
    docTypeRebuild: null,
    occupationList: null,
    nationList: null,
    minutes: 0,
    seconds: 0,
    showOtp: false,
    errorMessage: '',
    isSubmitting: false,
    ocrError: false,
    SubDocIdErr: '', 
    attIndexErr: '',
    localUploading: false,
    errorUpload: '',
    identityMessage: '',
    theSame: false,
    additionalDocRequire: null,
    validInputText: true,
    confirmGoToChooseLI: false,
    saveAndQuit: false,
    haveCreatingData: false,
    changeSelectLIItem: null,
    showConfirmResetUpload: false,
    docTypeConfirm: '',
    errorInfoChange: '',
    openPopupWarningDecree13: false,
    consentDisabled: false,
    showMaxImageOnSectionError: '',
    noValidPolicy: false,
    showConfirm: false,
    deleteAttachmentIndex: '',
    deleteSubDocId: '',
    showConfirmOthers: false,
    deleteAttachmentOthersIndex: '',
    deleteOthersSubDocId: '',
    showOthersConfirm: false,
    isCompany: false,
    showNotice: false,
    gender: '',
    validResidence: true,
    validOccupation: true,
    linkExpired: false,
    fromNoti: false
});

  const handlerBackToPrevStep = () => {
    if (state.stepName === 2) {
      setState(prevState => ({ ...prevState, confirmGoToChooseLI: true }));
    } else if (state.stepName > 1) {
      // setStepName(stepName - 1);
      setState(prevState => ({ ...prevState, stepName: state.stepName - 1 }));
    } else {
        if ((state.stepName === PERSONAL_INFO_STATE.CHOOSE_LI) && (state.appType !== 'CLOSE')) {
          let from = fromNative;
          let obj = {
              Action: "BACK_ND13_" + state.proccessType,
              ClientID: state.clientId,
              PolicyNo: state.policyNo,
              TrackingID: state.trackingId
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
      } /*else {
          this.setState({stepName: this.state.stepName - 1, enabled: false});
          if (state.stepName === PERSONAL_INFO_STATE.CHOOSE_LI) {
              // window.location.href = '/update-policy-info';
              this.props.handlerResetState();
          }
      }*/
    }

  };

  const goBack = () => {
    const main = document.getElementById("main-id");
    if (main) {
      main.classList.toggle("nodata");
    }
  };

  const haveChangePersonalInfo=()=> {
    if ((state.personalInfo.attachmentState.attachmentList.length > 0)
      || state.personalInfo.personalInfoChange[0].NewFamilyName 
      || state.personalInfo.personalInfoChange[0].NewGivenName 
      || state.personalInfo.personalInfoChange[1].NewClientIDNumber
      || state.personalInfo.personalInfoChange[1].NewClientPassportNumber
      || state.personalInfo.personalInfoChange[2].NewDOB
      || state.personalInfo.personalInfoChange[3].NewGender) {
      return true;
    }
    return false;
  }

  const togglePersonalInfo=() => {
    if (state.personalInfoChecked && haveChangePersonalInfo()) {
      showConfirmUntickPersonalInfo();
    } else {
      setState(prevState => ({ ...prevState, personalInfoChecked: !state.personalInfoChecked }));
    }
  }
  const toggleResidence=() => {
    let occupationChecked = state.occupationChecked;
    if (!state.residenceCheck) {
      occupationChecked = false;
    }
    setState(prevState => ({ ...prevState, residenceCheck: !state.residenceCheck, occupationChecked: occupationChecked }));
  }
  const toggleOccupation=() => {
    setState(prevState => ({ ...prevState, occupationChecked: !state.occupationChecked }));
  }

  const updateSelectedLI = (li) => {
    // getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + li?.LifeInsuredID)
    // .then(Res => {
    //   if (Res?.v) {
    //     setState(prevState => ({
    //       ...prevState,
    //       selectedLI: li,
    //       haveCreatingData: true
    //     }));
    //     return;
    //   } else {
    //     setState(prevState => ({ ...prevState, selectedLI: li }));
    //   }
    // })
    // .catch(error => {
    //   console.log(error);
    // });
    setState(prevState => ({ ...prevState, selectedLI: li }));
  }

  const checkHaveSavingData = () => {
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID)
    .then(Res => {
      if (Res?.v) {
        setState(prevState => ({
          ...prevState,
          haveCreatingData: true
        }));
        return true;
      } else {
        return false;
      }
    })
    .catch(error => {
      return false;
      console.log(error);
    });
  }

  const chosenLISubmit=() => {
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID)
    .then(Res => {
      if (Res?.v) {
        setState(prevState => ({
          ...prevState,
          haveCreatingData: true
        }));
      } else {
        updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
        fetchCPConsentConfirmationRefresh();
        saveLocal();
      }
    })
    .catch(error => {
      console.log(error);
    });
  }

  const updateDocTypeProfile=(profile) => {
    setState(prevState => ({ ...prevState, docTypeProfile: profile }));
  }

  const updateDocTypeRebuild=(profile) => {
    setState(prevState => ({ ...prevState, docTypeRebuild: profile }));
  }

  const updateOccupationList=(list) => {
    setState(prevState => ({ ...prevState, occupationList: list }));
  }

  const updateNationList=(list) => {
    setState(prevState => ({ ...prevState, nationList: list }));
  }

  const confirmGoToChooseLI=() => {
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID).then(Res => {
      if (Res.v) {
        removeLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID);
      }
    }).catch(error => {
      // alert(error);
    });
    resetState();
  //   setTimeout(() => {
  //     window.location.href = '/update-personal-info'
  // }, 100);
    if (!fromNative) {
      window.location.href = '/update-personal-info';
    }
  }
  const closeConfirmGoToChooseLI=() => {
    setState(prevState => ({ ...prevState, confirmGoToChooseLI: false }));
  }

  const closeConfirmResetUpload=() => {
    setState(prevState => ({ ...prevState, showConfirmResetUpload: false }));
  }

  const closeConfirmResetPersonalInfo=() => {
    setState(prevState => ({ ...prevState, showConfirmResetPersonalInfo: false }));
  }

  const showConfirmResetUpload=(DocTypeName) => {
    setState(prevState => ({ ...prevState, 
      showConfirmResetUpload: true,
      docTypeConfirm: DocTypeName 
    }));
  }

  const showConfirmUntickPersonalInfo=() => {
    setState(prevState => ({ ...prevState, 
      showConfirmResetPersonalInfo: true
    }));
  }
  
  
  const confirmResetUpload=() => {
    let personalInfo = {
      DocTypeNameSelected: undefined,
      attachmentState: {
        previewVisible: false,
        previewImage: "",
        previewTitle: "",
        attachmentList: [],
        attachmentMap: {},
        disabledButton: true,
      },
      ocrSuccess: false,
      isQRCode: false,
      QRCodeFrontOrBack: '',
      personalInfoChange: [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          NewClientPassportNumber: '',
          OldClientIDNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderS',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ]
    };
    setState(prevState => ({ ...prevState, 
      showConfirmResetUpload: false,
      personalInfo: personalInfo
    }));
    updateDocTypeNameSelected(state.docTypeConfirm);
  }

  const confirmResetPersonalInfo=() => {
    let personalInfo = {
      DocTypeNameSelected: undefined,
      attachmentState: {
        previewVisible: false,
        previewImage: "",
        previewTitle: "",
        attachmentList: [],
        attachmentMap: {},
        disabledButton: true,
      },
      ocrSuccess: false,
      isQRCode: false,
      QRCodeFrontOrBack: '',
      personalInfoChange: [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          OldClientIDNumber: '',
          NewClientPassportNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderS',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ]
    };
    setState(prevState => ({ ...prevState, 
      showConfirmResetUpload: false,
      personalInfo: personalInfo
    }));
    setState(prevState => ({ ...prevState, personalInfoChecked: !state.personalInfoChecked }));
  }
  
  const resetState=()=> {
    let personalInfo = {
      DocTypeNameSelected: undefined,
      attachmentState: {
        previewVisible: false,
        previewImage: "",
        previewTitle: "",
        attachmentList: [],
        attachmentMap: {},
        disabledButton: true,
      },
      ocrSuccess: false,
      isQRCode: false,
      QRCodeFrontOrBack: '',
      personalInfoChange: [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          NewClientPassportNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderS',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ]
    };
    let residenceInfo = {
      nationSelected: undefined,
      EntryDateSelected: undefined,
      ProcessTypeCode: 'ResidenceS',
      NewCountryOfResidenceCode: '',
      NewCountryOfResidence: '',
      EntryDate: '',
      PurposeOfResidence: undefined,
      Duration: undefined,
      USNationality: 'No',
      USPermanent: 'No',
      USTaxDeclared: 'No',
      NewOccClassCode: '',
      NewOccClass: '',
      NewOccNameCode: '',
      NewOccName: '',
      OccDescription: ''
    };
    let occupationInfo = {
      occupationSelected: undefined,
      ProcessTypeCode: 'OccupationS',
      NewOccClassCode: '',
      NewOccClass: '',
      NewOccNameCode: '',
      NewOccName: '',
      OccDescription: ''
    };
    setState(prevState => ({
       ...prevState, 
          stepName: PERSONAL_INFO_STATE.CHOOSE_LI,
          selectedLI: null,
          personalInfoChecked: false,
          residenceCheck: false,
          occupationChecked: false,
          personalInfo: personalInfo,
          residenceInfo: residenceInfo,
          occupationInfo: occupationInfo
      }));
  }

  const replacer = () => {
    const seen = new WeakSet();
    return (key, value) => {
        if (typeof value === "object" && value !== null) {
            if (seen.has(value)) {
                return;
            }
            seen.add(value);
        }
        return value;
    };
};

  const saveLocal=()=> {
    if (state.selectedLI?.LifeInsuredID && (state.stepName > PERSONAL_INFO_STATE.CHOOSE_LI)) {
      setLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID, stringify(state));
      setSession(CACHE_PERSONALINFO_KEY, PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID);
    }
  }

  const deleteLocal=()=> {
    if (state.selectedLI?.LifeInsuredID) {
      removeLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID);
      removeSession(CACHE_PERSONALINFO_KEY);
    } else if (getSession(CACHE_PERSONALINFO_KEY)) {
      removeLocal(getSession(CACHE_PERSONALINFO_KEY));
      removeSession(CACHE_PERSONALINFO_KEY);
    }
  }

  const saveLocalAndQuit = () => {
    console.log('......saveLocalAndQuit');
    // setState(prevState => ({
    //   ...prevState,
    //   saveAndQuit: true
    // }));
    saveLocal();
    setTimeout(1000, GoHome());
  }

  const saveAndQuit = () => {
    setState(prevState => ({
      ...prevState,
      saveAndQuit: false
    }));
    saveLocal();
    setTimeout(25, GoHome());
  };

  const Quit = () => {
    setState(prevState => ({
      ...prevState,
      saveAndQuit: false
    }));
    setTimeout(25, GoHome());
  };

  const GoHome = () => {
    let from = fromNative;
    if (from) {
      let obj = {
        Action: "EXIT_ND13_" + state.proccessType,
        ClientID: state.clientId,
        PolicyNo: state.policyNo,
        TrackingID: state.trackingId
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
    } else {
      history.push('/');
    }
  };

  const closeSaveAndQuit = () => {
    setState(prevState => ({
      ...prevState,
      saveAndQuit: false
    }));
  };

  const closeHaveCreatingData = () => {
    setState(prevState => ({
      ...prevState,
      haveCreatingData: false
    }));
  };

  const agreeChangeSelectLi = () => {
    clickOnLIChange(state.changeSelectLIItem);
    setState(prevState => ({
      ...prevState,
      haveCreatingData: false
    }));
  };

  const agreeLoadCreatingLI = () => {
    clickOnLIGo(state.selectedLI);
  };
  
  const clickOnLIGo = (item) => {
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + item?.LifeInsuredID).then(Res => {
      if (Res.v) {
        let savedState = parse(AES256.decrypt(Res.v, COMPANY_KEY2));
        console.log('savedState=', savedState);
        // savedState
        // savedState.personalInfo.personalInfoChange[2].NewDOB = formatDate(savedState.personalInfo.personalInfoChange[2].NewDOB);
        let currentAPIToken = state.apiToken;
        let currentDeviceId = state.deviceId;
        setState(savedState);
        setState(prevState => ({
          ...prevState,
          apiToken: currentAPIToken,
          deviceId: currentDeviceId,
          haveCreatingData: false
        }));
        if (savedState.stepName < PERSONAL_INFO_STATE.UPDATE_INFO) {
          updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
        }
        let clientIds = state.clientId;
        if (state.clientId !== item?.LifeInsuredID) {
          clientIds = clientIds + ',' + item?.LifeInsuredID;
        }
        // fetchCPConsentConfirmationRefresh();
      } else {
        updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
      }
    }).catch(error => {
      console.log(error);
    });
  }

  const agreeLoadCreatingLINew = () => {
    clickOnLINew(state.selectedLI);
  };

  const deleteND13DataTemp = (clientID, dKey, apiToken, deviceId) => {
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

  const clickOnLINew = (item) => {
    let trackingID = state.trackingId || getSession(CACHE_TRACKING_ID);
    if (trackingID) {
      deleteND13DataTemp(state.clientId, trackingID, state.apiToken, state.deviceId);
      removeSession(CACHE_TRACKING_ID);
    }
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + item?.LifeInsuredID).then(Res => {
      if (Res.v) {
        removeLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + item?.LifeInsuredID);
      }
    }).catch(error => {
    });
    // Reset state
    resetState();
    setState(prevState => ({
      ...prevState,
      selectedLI: item
    }));
    updateStepName(PERSONAL_INFO_STATE.UPDATE_INFO);
    let clientIds = state.clientId;
    if (state.clientId !== item?.LifeInsuredID) {
      clientIds = clientIds + ',' + item?.LifeInsuredID;
    }
    saveLocal();
  }
  
  const clickOnLIChange = (item) => {
    getLocal(PERSONAL_INFO_SAVE_LOCAL + FE_BASE_URL + state.clientId + state.selectedLI?.LifeInsuredID)
      .then(Res => {
        if (Res.v) {
          setState(prevState => ({
            ...prevState,
            selectedLI: item,
            haveCreatingData: true
          }));
          return;
        }
      })
      .catch(error => {
      });
    // Reset state

  };

  const onChangeDocType=(DocTypeName)=> {
    if (state?.personalInfo?.DocTypeNameSelected) {
      showConfirmResetUpload(DocTypeName);
    } else {
      updateDocTypeNameSelected(DocTypeName);
    }
  }

  const updateDocTypeNameSelected = (newDocTypeID) => {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        DocTypeNameSelected: newDocTypeID
      }
    }));
  };

  const onChangeNation = (code, name) => {
    console.log('code=' + code + ',name=' + name);
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        nationSelected: code,
        NewCountryOfResidenceCode: code,
        NewCountryOfResidence: name
      }
    }));

  };

  const onChangeEntryDate = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        EntryDateSelected: value,
        EntryDate: value?.format('DD/MM/YYYY')
      }
    }));

  };
  
  const onChangePurposeOfResidence = (value) => {
    if (value !== 'Khác') {
      setState(prevState => ({
        ...prevState,
        residenceInfo: {
          ...prevState.residenceInfo,
          PurposeOfResidence: value,
          PurposeOfResidenceDescription: ''
        }
      }));
    } else {
      setState(prevState => ({
        ...prevState,
        residenceInfo: {
          ...prevState.residenceInfo,
          PurposeOfResidence: value
        }
      }));
    }
  };

  const onChangeDuration = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        Duration: value
      }
    }));

  };

  const onChangeUSNationality = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        USNationality: value? 'Yes': 'No'
      }
    }));

  };

  const onChangeUSPermanent = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        USPermanent: value? 'Yes': 'No'
      }
    }));

  };

  const onChangeUSTaxDeclared = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        USTaxDeclared: value? 'Yes': 'No'
      }
    }));

  };
  
  const onLocalUploading = () => {
    setState(prevState => ({
      ...prevState,
      localUploading: true
    }));

  };

  const onCloseLocalUploading = () => {
    setState(prevState => ({
      ...prevState,
      localUploading: false
    }));

  };
  
  const onChangeOccupation = (code, occupation) => {
    setState(prevState => ({
      ...prevState,
      occupationInfo: {
        ...prevState.occupationInfo,
        occupationSelected: code,
        NewOccClass: occupation?.type,
        NewOccNameCode: code,
        NewOccName: occupation?.nameVN
      }
    }));
  };

  const onChangeResidenceOccupation = (code, occupation) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        occupationSelected: code,
        NewOccClass: occupation?.type,
        NewOccNameCode: code,
        NewOccName: occupation?.nameVN
      }
    }));
    onChangeOccupation(code, occupation);
  };

  const onChangeOccDescription = (value) => {
    setState(prevState => ({
      ...prevState,
      occupationInfo: {
        ...prevState.occupationInfo,
        OccDescription: value,
        IsChangeFromResidence: ''
      }
    }));
  };

  const onChangeResidenceOccDescription = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        OccDescription: value
      }
    }));
    onChangeOccDescription(value);
    setState(prevState => ({
      ...prevState,
      occupationInfo: {
        ...prevState.occupationInfo,
        IsChangeFromResidence: 'Yes'
      }
    }));
    
  };

  const onChangePurposeOfResidenceDescription = (value) => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        PurposeOfResidenceDescription: value
      }
    }));
  };

  const dragFileOver=(htmlId, event)=> {
    event.preventDefault();
    document.getElementById(htmlId).className = "img-upload active";
  }
  
  const dragFileLeave=(htmlId, event)=> {
    event.preventDefault();
    document.getElementById(htmlId).className = "img-upload";
  }
  
  const dropFile = async (event, SubDocId) => {
    if (state.isSubmitting || state.localUploading) {
      return;
    }
    event.preventDefault();
    onLocalUploading();
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
	const files = Object.values(event.dataTransfer.files);
    const attachments = state.personalInfo.attachmentState;
    if (((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) && files && (files.length > 1)) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách chỉ có thể đính kèm 1 chứng từ cho mỗi mặt.',
        localUploading: false
      }));
      return;
    }
  
    if (files && files.length > 20) {
      event.dataTransfer.clearData();
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)) {
      event.dataTransfer.clearData();
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    }
  
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        if (validateImage.includes(file.type)) {
          try {
            let options = {
              maxSizeMB: 3,
              maxWidthOrHeight: 1920,
              useWebWorker: true,
              initialQuality: 0.2
            };
            if ((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) {
              options = {
                maxSizeMB: 3,
                maxWidthOrHeight: 1920,
                useWebWorker: true
              };
            }
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error('Error compressing file:', error);
          }
          setState(prevState => ({
            ...prevState,
            errorUpload: '',
            localUploading: false
          }));
        } else {
          setState(prevState => ({
            ...prevState,
            errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png',
            localUploading: false
          }));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, SubDocId, event) => {
      let index = 0;
      const processNextBatch = async () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        const batchResults = await Promise.all(promises);
        const arrAttData = batchResults.map(base64 => ({ imgData: base64 }));
        const attachments = state.personalInfo.attachmentState.attachmentMap;
        const attList = state.personalInfo.attachmentState.attachmentList;
  
        if (attachments !== null && attachments !== undefined) {
          let att = attachments[SubDocId];
          if (!att) {
            att = [];
          }
          if ((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) {
            const matches = arrAttData[0].imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
            console.log((matches.length === 3) ? matches[2] : '');
            ocrImage((matches.length === 3) ? matches[2] : '', SubDocId, att.length);
          } else {
            setState(prevState => ({
              ...prevState,
              localUploading: false
            }));
          }
          attList.push(...arrAttData);
          att.push(...arrAttData);
          attachments[SubDocId] = att;
          console.log('attachments=', attachments);
          setState(prevState => ({
            ...prevState,
            personalInfo: {
              ...prevState.personalInfo,
              attachmentState: {
                ...prevState.personalInfo.attachmentState,
                attachmentList: attList,
                attachmentMap: attachments
              }
            }
          }));
        }
  
        index += batchSize;
        if (index < files.length) {
          processNextBatch();
        } else {
          event.dataTransfer.clearData();
          setState(prevState => ({
            ...prevState,
            localUploading: false
          }));
          saveLocal();
        }
      };
  
      processNextBatch();
    };
  
    processFilesInBatches(files, 5, processFile, SubDocId, event);
  }

  const uploadAttachment = async (event, SubDocId) => {
    if (state.isSubmitting || state.localUploading) {
      return;
    }
    event.preventDefault();
    onLocalUploading();
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    const files = Array.from(event.target.files);
    const attachments = state.personalInfo.attachmentState;
    if (((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) && files && (files.length > 1)) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách chỉ có thể đính kèm 1 chứng từ cho mỗi mặt.',
        localUploading: false
      }));
      return;
    }
  
    if (files && files.length > 20) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    }
  
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        if (validateImage.includes(file.type)) {
          try {
            let options = {
              maxSizeMB: 3,
              maxWidthOrHeight: 1920,
              useWebWorker: true,
              initialQuality: 0.2
            };
            if ((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) {
              options = {
                maxSizeMB: 3,
                maxWidthOrHeight: 1920,
                useWebWorker: true
              };
            }
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error('Error compressing file:', error);
          }
          setState(prevState => ({
            ...prevState,
            errorUpload: '',
            localUploading: false
          }));
        } else {
          setState(prevState => ({
            ...prevState,
            errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png',
            localUploading: false
          }));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, SubDocId, event) => {
      let index = 0;
      const processNextBatch = async () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        const batchResults = await Promise.all(promises);
        const arrAttData = batchResults.map(base64 => ({ imgData: base64 }));
        const attachments = state.personalInfo.attachmentState.attachmentMap;
        const attList = state.personalInfo.attachmentState.attachmentList;
  
        if (attachments !== null && attachments !== undefined) {
          let att = attachments[SubDocId];
          if (!att) {
            att = [];
          }
          if ((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) {
            const matches = arrAttData[0].imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
            console.log((matches.length === 3) ? matches[2] : '');
            ocrImage((matches.length === 3) ? matches[2] : '', SubDocId, att.length);
          } else {
            setState(prevState => ({
              ...prevState,
              localUploading: false
            }));
          }
          attList.push(...arrAttData);
          att.push(...arrAttData);
          attachments[SubDocId] = att;
          console.log('attachments=', attachments);
          setState(prevState => ({
            ...prevState,
            personalInfo: {
              ...prevState.personalInfo,
              attachmentState: {
                ...prevState.personalInfo.attachmentState,
                attachmentList: attList,
                attachmentMap: attachments
              }
            }
          }));
        }
  
        index += batchSize;
        if (index < files.length) {
          processNextBatch();
        } else {
          event.target.value = null;
          setState(prevState => ({
            ...prevState,
            localUploading: false
          }));
          saveLocal();
        }
      };
  
      processNextBatch();
    };
  
    processFilesInBatches(files, 5, processFile, SubDocId, event);
    
  }

  const ocrImage = (base64, SubDocId, attIndex) => {
    setState(prevState => ({
      ...prevState,
      isSubmitting: true
    }));
    let jsonRequest = {
      jsonDataInput: {
        APIToken: state.apiToken,
        Action: 'ChangePersonalInfo',
        Authentication: AUTHENTICATION,
        ContactEmail: state.email,
        ClientClass: state.clientClass,
        ClientID: state.clientId,
        ClientName: formatFullName(state.clientName),
        Company: COMPANY_KEY,
        DeviceId: state.deviceId,
        FromSystem: "DCW",
        Project: 'mcp',
        UserLogin: state.clientId,
        sContent: base64
      }

    }
    console.log(jsonRequest);
    onlineRequestSubmitUserInfo(jsonRequest).then(Res => {
      let Response = Res.Response;
      console.log('ocr response1=', Response);
      if (Response.Result === 'true' && Response.ErrLog === 'Success' && Response.Data) {
        // let idType = Response?.Data[0]?.idType;
        const {idType, fullNameConf, dobConf, genderConf, idNumConf, nationalityConf, fullName, idNum, dob, gender, issueDate, idNum_9} = Response.Data[0];
        if (SubDocId === IDDOCUMENTS_FRONT) {
          if ((idType !== OCR_UNIDENTIFIED) && !CCCD_FRONTS.includes(idType) && !CCCD_BACKS.includes(idType)) {
            popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
            setState(prevState => ({
              ...prevState,
              errorMessage: '<p>Chưa đúng loại giấy tờ! Vui lòng sử dụng hình ảnh của Căn cước công dân hoặc Thẻ căn cước để tiếp tục.</p>',
              SubDocIdErr: SubDocId,
              attIndexErr: attIndex,
              localUploading: false,
              isSubmitting: false
            }));
            return;
          } else if (!CCCD_FRONTS.includes(idType)) {
            popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
            setState(prevState => ({
              ...prevState,
              errorMessage: '<div class="cccdnotice"><p>Hình ảnh chưa hợp lệ!</p><div class="popup_content"><p>Vui lòng đảm bảo hình ảnh của giấy tờ thỏa các điều kiện sau:</p><ul><li>Dễ đọc, không bị mờ, mất góc, hay lóa sáng</li><li>Tải lên đúng mặt trước hoặc mặt sau theo hướng dẫn</li><li>Dung lượng hình ảnh không vượt quá 5 MB và độ phân giải tối thiểu là 640x480</li><li>Tỷ lệ diện tích của giấy tờ phải chiếm ít nhất 1/2 tổng diện tích của hình ảnh</li></ul></div></div>',
              SubDocIdErr: SubDocId,
              attIndexErr: attIndex,
              localUploading: false,
              isSubmitting: false
            }));
            return;
          }
        } else if (SubDocId === IDDOCUMENTS_BACK) {
          if ((idType !== OCR_UNIDENTIFIED) && !CCCD_FRONTS.includes(idType) && !CCCD_BACKS.includes(idType)) {
            popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
            setState(prevState => ({
              ...prevState,
              errorMessage: '<p>Chưa đúng loại giấy tờ! Vui lòng sử dụng hình ảnh của Căn cước công dân hoặc Thẻ căn cước để tiếp tục.</p>',
              SubDocIdErr: SubDocId,
              attIndexErr: attIndex,
              localUploading: false,
              isSubmitting: false
            }));
            return;
          } else if (!CCCD_BACKS.includes(idType)) {
            popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
            setState(prevState => ({
              ...prevState,
              errorMessage: '<div class="cccdnotice"><p>Hình ảnh chưa hợp lệ!</p><div class="popup_content"><p>Vui lòng đảm bảo hình ảnh của giấy tờ thỏa các điều kiện sau:</p><ul><li>Dễ đọc, không bị mờ, mất góc, hay lóa sáng</li><li>Tải lên đúng mặt trước hoặc mặt sau theo hướng dẫn</li><li>Dung lượng hình ảnh không vượt quá 5 MB và độ phân giải tối thiểu là 640x480</li><li>Tỷ lệ diện tích của giấy tờ phải chiếm ít nhất 1/2 tổng diện tích của hình ảnh</li></ul></div></div>',
              SubDocIdErr: SubDocId,
              attIndexErr: attIndex,
              localUploading: false,
              isSubmitting: false
            }));
            return;
          }
        }
        let hadLockInfo = state.personalInfo.isQRCode;
        if (fullName && issueDate) {
          console.log('bang');
          setState(prevState => ({
            ...prevState,
            personalInfo: {
              ...prevState.personalInfo,
              isQRCode: true,
              QRCodeFrontOrBack: SubDocId,
              localUploading: false,
              isSubmitting: false
            }
          }));
        } else if (idType === OCR_UNIDENTIFIED) {
          popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
          setState(prevState => ({
            ...prevState,
            errorMessage: '<p>Chưa đúng loại giấy tờ! Vui lòng sử dụng hình ảnh của Căn cước công dân hoặc Thẻ căn cước để tiếp tục.</p>',
            SubDocIdErr: SubDocId,
            attIndexErr: attIndex,
            localUploading: false,
            isSubmitting: false
          }));
          return;
        } else {
          console.log('k bang');
          if (!state.personalInfo.QRCodeFrontOrBack || (state.personalInfo.QRCodeFrontOrBack && (SubDocId === state.personalInfo.QRCodeFrontOrBack))) {
            setState(prevState => ({
              ...prevState,
              personalInfo: {
                ...prevState.personalInfo,
                QRCodeFrontOrBack: SubDocId,
                localUploading: false,
                isSubmitting: false
                // isQRCode: false
              }
            }));
          }
        }
        

        // if (((fullNameConf < 0.7) || (dobConf < 0.7) || (genderConf < 0.7) || (idNumConf < 0.7) || (nationalityConf < 0.7)) && !issueDate) {
        //   popupImgPath =  FE_BASE_URL + '/img/popup/incorrect-doctype.svg';
        //   setState(prevState => ({
        //     ...prevState,
        //     errorMessage: '<div class="cccdnotice"><p>Hình ảnh chưa hợp lệ!</p><div class="popup_content"><p>Vui lòng đảm bảo hình ảnh của giấy tờ thỏa các điều kiện sau:</p><ul><li>Dễ đọc, không bị mờ, mất góc, hay lóa sáng</li><li>Tải lên đúng mặt trước hoặc mặt sau theo hướng dẫn</li><li>Dung lượng hình ảnh không vượt quá 5 MB và độ phân giải tối thiểu là 640x480</li><li>Tỷ lệ diện tích của giấy tờ phải chiếm ít nhất 1/2 tổng diện tích của hình ảnh</li></ul></div></div>',
        //     SubDocIdErr: SubDocId,
        //     attIndexErr: attIndex
        //   }));
        // }
        if (hadLockInfo) {
          popupImgPath =  FE_BASE_URL + '/img/popup/ok.svg';
          setState(prevState => ({
            ...prevState,
            localUploading: false,
            errorMessage: '', 
            identityMessage: '<p>Đọc dữ liệu thành công! Vui lòng kiểm tra lại thông tin cần điều chỉnh.</p>',
            SubDocIdErr: SubDocId,
            attIndexErr: attIndex,
            localUploading: false,
            isSubmitting: false
          }));
          return;
        }

        let changeFullName = false;
        if (fullName) {
          const nameParts = fullName.trim().split(' ');
          const familyName = nameParts.slice(0, -1).join(' ');
          const givenName = nameParts.slice(-1).join(' ');
          onChangeFamilyName(familyName);
          onChangeGivenName(givenName);
          if (toLowerCase(fullName) !== toLowerCase(state.selectedLI?.FullName)) {
            changeFullName = true;
            updateClientProperties(0, true, changeFullName);
          } else {
            updateClientProperties(0, false, false);
          }
          
        }
        let chageIdNum = false;
        if (idNum) {
          onChangeClientIDNumber(idNum);
          if (idNum !== state.selectedLI?.LifeInsuredIDNum) {
            chageIdNum = true;
            updateClientProperties(1, true, chageIdNum);
          } else {
            updateClientProperties(1, false, false);
          }
          
        }
        if (idNum_9) {
          onChangeOldClientIDNumber(idNum_9);
        }
        let changeDOB = false;
        if (dob) {
          onChangeClientDOB(moment(dob, 'DD/MM/YYYY'));
          if (dob !== formatDate(state.selectedLI?.DOB)) {
            changeDOB = true;
            updateClientProperties(2, true, changeDOB);
          } else {
            updateClientProperties(2, false, false);
          }
        }
        let changeGender = false;
        if (gender) {
          onChangeClientGender(getGenderCode(gender));
          console.log('gender code=', getGenderCode(gender));
          console.log('gender=', state.selectedLI?.Gender);
          if (getGenderCode(gender) && (state.selectedLI?.Gender !== getGenderCode(gender))) {
            changeGender = true;
            updateClientProperties(3, true, changeGender);
          } else {
            updateClientProperties(3, false, false);
          }
          
        }
        
        if (fullName && !changeFullName && idNum && !chageIdNum && dob && !changeDOB && gender && !changeGender) {
          popupImgPath =  FE_BASE_URL + '/img/popup/ok.svg';
          setState(prevState => ({
            ...prevState,
            errorMessage: '', 
            identityMessage: '<p>Thông tin nhân thân của Quý khách cung cấp trùng khớp với thông tin lưu trữ tại Dai-ichi Life Việt Nam. Quý khách vui lòng kiểm tra và chọn loại thông tin khác cần điều chỉnh (nếu có).</p>',
            SubDocIdErr: SubDocId,
            attIndexErr: attIndex,
            theSame: true,
            localUploading: false,
            isSubmitting: false
          }));
        } else { //if ((fullName && changeFullName) || (idNum && chageIdNum) || (dob && changeDOB) || (gender && changeGender)) {
          popupImgPath =  FE_BASE_URL + '/img/popup/ok.svg';
          setState(prevState => ({
            ...prevState,
            errorMessage: '', 
            identityMessage: '<p>Đọc dữ liệu thành công! Vui lòng kiểm tra lại thông tin cần điều chỉnh.</p>',
            SubDocIdErr: SubDocId,
            attIndexErr: attIndex,
            localUploading: false,
            isSubmitting: false
          }));
        }
        console.log('state=', state);
      } else {
        setState(prevState => ({
          ...prevState,
          ocrError: true,
          localUploading: false,
          isSubmitting: false
        }));
      }
      setState(prevState => ({
        ...prevState,
        localUploading: false,
        isSubmitting: false
      }));
    }).catch(error => {
      setState(prevState => ({
        ...prevState,
        localUploading: false,
        isSubmitting: false
      }));
      console.log(JSON.stringify(error));
    });
  }

  // const updateAttachmentList=(event, val)=> {
  //   const attachments = state.personalInfo.attachmentState.attachmentList;

  //   if (attachments !== null && attachments !== undefined) {
  //     attachments.push({ imgData: val });
  //     setState(prevState => ({
  //       ...prevState,
  //       personalInfo: {
  //         ...prevState.personalInfo,
  //         attachmentState: {
  //           ...prevState.personalInfo.attachmentState,
  //           attachmentList: attachments
  //         }
  //       }
  //     }));
  //   }
  //   event.target.value = null;
  // }
  const deleteAttachment=(attachmentIndex, SubDocId, force)=> {
    if (state.isSubmitting) {
      return;
    }
    if (force) {
      deleteAttachmentGo(attachmentIndex, SubDocId);
    } else {
      setState(prevState => ({
        ...prevState,
        showConfirm: true,
        deleteAttachmentIndex: attachmentIndex,
        deleteSubDocId: SubDocId
      }));
    }
  }

  const AgreeDelete = () => {
    deleteAttachmentGo(state.deleteAttachmentIndex, state.deleteSubDocId);
  }

  const deleteAttachmentGo=(attachmentIndex, SubDocId)=> {
    const attachments = state.personalInfo.attachmentState.attachmentList;
    const attachmentsMap = state.personalInfo.attachmentState.attachmentMap;
    attachments.splice(attachmentIndex, 1);
    attachmentsMap[SubDocId].splice(attachmentIndex, 1);
    setState(prevState => ({
      ...prevState,
      showConfirm: false,
      personalInfo: {
        ...prevState.personalInfo,
        attachmentState: {
          ...prevState.personalInfo.attachmentState,
          attachmentList: attachments,
          attachmentMap: attachmentsMap
        }
      }
    }));
    if (state.personalInfo.QRCodeFrontOrBack === SubDocId) {
      let personalInfoChangeReset = [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          OldClientIDNumber: '',
          NewClientPassportNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderSID',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ];
      // onChangeFamilyName('');
      // onChangeGivenName('');
      // toggleClientProperties(0);
      // onChangeClientIDNumber('');
      // toggleClientProperties(1);
      // onChangeClientDOB('');
      // toggleClientProperties(2);
      // onChangeClientGender('');
      // toggleClientProperties(3);
      setState(prevState => ({
        ...prevState,
        personalInfo: {
          ...prevState.personalInfo,
          personalInfoChange: personalInfoChangeReset,
          isQRCode: false
        }
      }));
    } 
    saveLocal();
  }
  
  const toggleClientProperties=(idx)=> {
    const checked = state.personalInfo.personalInfoChange[idx]?.Checked;
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === idx ? { ...item, Checked: !checked } : item
        )
      }
    }));
  }

  const toggleClientPropertiesPassport=(idx)=> {
    const checked = state.personalInfo.personalInfoChange[idx]?.ppChecked;
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === idx ? { ...item, ppChecked: !checked } : item
        )
      }
    }));
  }

  const updateClientProperties=(idx, value, change)=> {
    // const checked = state.personalInfo.personalInfoChange[idx]?.Checked;
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === idx ? { ...item, Checked: value, isChange: change } : item
        )
      }
    }));
  }

  const updateClientPropertiesPassport=(idx, value, change)=> {
    // const checked = state.personalInfo.personalInfoChange[idx]?.Checked;
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === idx ? { ...item, ppChecked: value, isChange: change } : item
        )
      }
    }));
  }

  const onChangeFamilyName=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 0 ? { ...item, NewFamilyName: capitalizeFirstLetter(value) } : item
        )
      }
    }));
  }

  const onChangeGivenName=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 0 ? { ...item, NewGivenName: capitalizeFirstLetter(value) } : item
        )
      }
    }));
    console.log(state);
  }
  
  const onChangeClientIDNumber=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 1 ? { ...item, NewClientIDNumber: toUpperCase(removeSpecialCharactersAndSpace(value)) } : item
        )
      }
    }));

  }

  const onChangeOldClientIDNumber=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 1 ? { ...item, OldClientIDNumber: value } : item
        )
      }
    }));
  }

  const onChangeClientPassportNumber=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 1 ? { ...item, NewClientPassportNumber: toUpperCase(removeSpecialCharactersAndSpace(value)) } : item
        )
      }
    }));
  }

  const onChangeClientDOB=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 2 ? { ...item, NewDOBSelected: value, NewDOB: value?value.format('DD/MM/YYYY'): '' } : item
        )
      }
    }));
  }

  const onChangeClientGender=(value)=> {
    setState(prevState => ({
      ...prevState,
      personalInfo: {
        ...prevState.personalInfo,
        personalInfoChange: prevState.personalInfo.personalInfoChange.map((item, index) =>
          index === 3 ? { ...item, NewGenderCode: value, NewGender: GENDER_MAP[value] } : item
        )
      }
    }));
  }
  
  const updateAllLIAgree=(value)=> {
      // this.setState({allLIAgree: value});
  }

  const updateExistLINotAgree=(value)=> {
    // this.setState({allLIAgree: value});
  }
  

  // let personalInfoDocRequire = [];
  // let remainList = [];
  let uploadList = [];
  if (state.docTypeProfile) {
    // remainList = state.docTypeProfile.filter(item => 
    //   !state.personalInfo.attachmentState.attachmentMap[item?.SubDocId]);
    uploadList = state.docTypeProfile.filter(item => 
      state.personalInfo.attachmentState.attachmentMap[item?.SubDocId] && (state.personalInfo.attachmentState.attachmentMap[item?.SubDocId].length > 0) );
      console.log('uploadList=', uploadList);
  }
  // if (state.personalInfoChecked) {
  //   let front = remainList.find(item => item?.SubDocId === IDDOCUMENTS_FRONT);
  //   if (front) {
  //     personalInfoDocRequire.push(IDDOCUMENTS_FRONT);
  //   }

  //   let back = remainList.find(item => item?.SubDocId === IDDOCUMENTS_BACK);
  //   if (back) {
  //     personalInfoDocRequire.push(IDDOCUMENTS_BACK);
  //   }
  // }

  const validateAddtionalAtt = (uploadList) => {
    if (state.personalInfoChecked) {
      if (!uploadList || (uploadList.length === 0)) {
        return false;
      } else {
        for (let SubDocId in state.personalInfo.attachmentState.attachmentMap) {
          if ((SubDocId === IDDOCUMENTS_PP) || (SubDocId === IDDOCUMENTS_BC)) {
            return true;
          } else if ((SubDocId === IDDOCUMENTS_FRONT) || (SubDocId === IDDOCUMENTS_BACK)) {
            if (!state.personalInfo.attachmentState.attachmentMap[IDDOCUMENTS_FRONT] || !state.personalInfo.attachmentState.attachmentMap[IDDOCUMENTS_BACK]) {
              return false;
            } else if ((state.personalInfo.attachmentState.attachmentMap[IDDOCUMENTS_FRONT].length === 0) || (state.personalInfo.attachmentState.attachmentMap[IDDOCUMENTS_BACK].length === 0)) {
              return false;
            }
            return true;
          }
        }
      }
    }
    return true;
  }

  const validateInfoChange = () => {
    if (!state.personalInfoChecked) {
      return true;
    }
    let haveCheckChangePersonalInfo = state.personalInfo.personalInfoChange.filter(item => ((item.Checked === true) || (item.ppChecked === true)));
    console.log('haveCheckChangePersonalInfo=', haveCheckChangePersonalInfo);
    if (!haveCheckChangePersonalInfo || (haveCheckChangePersonalInfo.length === 0)) {
      return false;
    }
    if (state.personalInfo.personalInfoChange[0].Checked) {
      if (!state.personalInfo.personalInfoChange[0].NewFamilyName || !state.personalInfo.personalInfoChange[0].NewGivenName) {
        return false;
      }
    }
    if (state.personalInfo.personalInfoChange[1].Checked) {
      if (!state.personalInfo.personalInfoChange[1].NewClientIDNumber) {
        return false;
      }
    }
    if (state.personalInfo.personalInfoChange[1].ppChecked) {
      if (!state.personalInfo.personalInfoChange[1].NewClientPassportNumber) {
        return false;
      }
    }
    if (state.personalInfo.personalInfoChange[2].Checked) {
      if (!state.personalInfo.personalInfoChange[2].NewDOB) {
        return false;
      }
    }
    if (state.personalInfo.personalInfoChange[3].Checked) {
      if (!state.personalInfo.personalInfoChange[3].NewGender) {
        return false;
      }
    }

    return true;
  }

  const validateResidenceChange = () => {
    if (!state.residenceCheck) {
      return true;
    }
    if (!state.residenceInfo.NewCountryOfResidence 
      || !state.residenceInfo.EntryDate 
      || !state.residenceInfo.PurposeOfResidence
      || !state.residenceInfo.Duration) {
      return false;
    }
    if (!state.occupationInfo.NewOccName &&  state.occupationInfo.OccDescription
      || state.occupationInfo.NewOccName &&  !state.occupationInfo.OccDescription) {
      return false;
    }
    
    if((['Du học', 'Công tác', 'Định cư', 'Xuất khẩu lao động'].indexOf(state.residenceInfo?.PurposeOfResidence) >= 0 ) && (!state.residenceInfo.NewOccNameCode || !state.residenceInfo.OccDescription)) {
      return false;
    }

    if (state.residenceInfo?.PurposeOfResidence && (state.residenceInfo?.PurposeOfResidence === 'Khác') && !state.residenceInfo.PurposeOfResidenceDescription) {
      return false;
    }

    return true;
  }

  const validateOccupationChange = () => {
    if (!state.occupationChecked) {
      return true;
    }
    if (!state.occupationInfo.NewOccName ||  !state.occupationInfo.OccDescription) {
      return false;
    }
    return true;
  }

  const validateInputText = () => {
    let validInfo =  validateInfoChange();
    if (validInfo) {
      setState(prevState => ({
        ...prevState,
        errorInfoChange: ''
      }));
    } else {
      setState(prevState => ({
        ...prevState,
        errorInfoChange: 'Vui lòng thay đổi ít nhất 1 thông tin'
      }));
    }

    let vResidence = validateResidenceChange();
    console.log('vResidence=', vResidence);
    let vOccupation = validateOccupationChange();
    console.log('vOccupation=', vOccupation);
    let valid =  validInfo && vResidence && vOccupation;
    console.log('oke valid=', valid);
      setState(prevState => ({
        ...prevState,
        validInputText: valid,
        validResidence: vResidence,
        validOccupation: vOccupation
      }));
    return valid;
  }

  const validateMininum = () => {
    if (!state.personalInfoChecked && !state.residenceCheck && !state.occupationChecked) {
      return false;
    }
    let validPersonalInfo = validateMininumPersonalInfoChange();
    console.log('validPersonalInfo=' + validPersonalInfo);
    let valid = validPersonalInfo || ((state.residenceCheck || state.occupationChecked) && validPersonalInfo);
    console.log('valid=' + valid);
    return valid;
  }

  const validateMininumPersonalInfoChange = () => {
    if (!state.personalInfoChecked) {
      return true;
    }
    console.log('state.personalInfo.personalInfoChange=', state.personalInfo.personalInfoChange);
    let haveCheckChangePersonalInfo = state.personalInfo.personalInfoChange.filter(item => ((item.Checked === true) || (item.ppChecked === true)));
    console.log('haveCheckChangePersonalInfo...=', haveCheckChangePersonalInfo);
    if (!haveCheckChangePersonalInfo || (haveCheckChangePersonalInfo.length === 0)) {
      return false;
    }
    return true;
  }

  let validAttacthment = validateAddtionalAtt(uploadList);
  let validateMinimum = validateMininum();
  
  console.log('validAttacthment=' + validAttacthment);
  const changePolicyInfoSubmit = () => {
      // if (!this.state.isValidInput) {
      //     return;
      // }
      if (state.isSubmitting) {
          return;
      }
      console.log('changePolicyInfoSubmit=', state);
      setState(prevState => ({
        ...prevState,
        isSubmitting: true
      }));
      let changeList = [];
      if (state.personalInfoChecked) {
        changeList = state.personalInfo.personalInfoChange.filter(item => (item.Checked === true) || (item.ppChecked === true));
        console.log('changeList=', changeList);
        if (changeList) {
          for (let i=0; i < changeList.length; i++ ) {
            if (changeList[i]?.ProcessTypeCode === 'IDNumberS') {
              if (!changeList[i]?.Checked) {
                changeList[i].NewClientIDNumber = '';
              }
              if (!changeList[i]?.ppChecked) {
                changeList[i].NewClientPassportNumber = '';
              }
            }
          }
        }
      }
      if (state.residenceCheck) {
        changeList.push(state.residenceInfo);
        if(state.residenceInfo.NewOccNameCode) {
          changeList.push(state.occupationInfo);
        }
      } else if (state.occupationChecked) {
        changeList.push(state.occupationInfo);
      }
      
      console.log('changeList=', changeList);

      let submitRequest = {
          jsonDataInput: {
              APIToken: state.apiToken,
              Action: "SubmitChangePersonalInfo",
              Authentication: AUTHENTICATION,
              ClientClass: state.clientClass?state.clientClass: 'NOVIP',
              ClientID: state.clientId,
              ConsentList: [
                  {
                      ConsentRuleID: state.proccessType + '_Agreement',
                      ConsentResult: 'Y'
                  }
              ],
              ClientName: formatFullName(state.clientName),
              Company: COMPANY_KEY,
              ContactEmail: state.email,
              DeviceId: state.deviceId,
              FromSystem: fromNative ? "DCA" : "DCW",
              OS: fromNative?fromNative: WEB_BROWSER_VERSION,
              Project: "mcp",
              RequestTypeID: state.proccessType,
              UserLogin: state.clientId,
              Alteration: {
                ClientID: state.selectedLI?.LifeInsuredID,
                ClientName: formatFullName(state.selectedLI.FullName),
                NewValue: changeList,
              }
          }
      }
        console.log(submitRequest);
        onlineRequestSubmitESubmissionCCI(submitRequest)
          .then(res => {
              if (res.Response.Result === 'true' && res.Response.Message === 'Success') {
                  const trackingID = res.Response.Data[0]?.TransID;
                  setSession(CACHE_TRACKING_ID, trackingID);
                  setState(prevState => ({
                    ...prevState,
                    trackingId: trackingID
                    // trackingId: state.proccessType + state.clientId //yêu cầu ND13 theo client không theo proccess

                  }));
                  if ((state.personalInfo?.attachmentState?.attachmentList && (state.personalInfo?.attachmentState?.attachmentList?.length > 0)) || (state.attachmentState?.attachmentList && (state.attachmentState?.attachmentList?.length > 0)) ) {
                    uploadImages(trackingID);
                  } else {
                    setState(prevState => ({
                      ...prevState,
                      trackingId: trackingID,
                      stepName: PERSONAL_INFO_STATE.SDK,
                      isSubmitting: false
                    }));
                  }
                  
                  // fetchCPConsentConfirmation(res.Response.Message);
              } else {
                setState(prevState => ({
                  ...prevState,
                  isSubmitting: false
                }));
              }
              saveLocal();
          }).catch(error => {
            setState(prevState => ({
              ...prevState,
              isSubmitting: false
            }));
          });
  }

  const getNumOfPage=(SubDocID)=> {
    for (let i = 0; i < DOCUMENTS_ORDER.length; i++) {
      if (DOCUMENTS_ORDER[i] === SubDocID) {
        return i + 1;
      }
    }
    return 0;
  }

  const uploadImages = (transactionId) => {
      if ((state.personalInfo.attachmentState.attachmentList.length === 0) && (state.attachmentState.attachmentList.length === 0)) {
          setState(prevState => ({
            ...prevState,
            isSubmitting: false
          }));
          return;
      }
      let i = 0;
      let personalAttachmentMap = state.personalInfo.attachmentState.attachmentMap;
      let attachmentMap = state.attachmentState.attachmentMap;
      let copiedMap = new Map(Object.entries(attachmentMap));
      if (!state.residenceCheck) {
        copiedMap.delete(IDDOCUMENTS_PP);
        copiedMap.delete(OTHERS_NC);
      }
      if (!state.personalInfoChecked || !state.personalInfo?.personalInfoChange[0]?.Checked && (!state.personalInfo?.personalInfoChange[2]?.Checked || !state.personalInfo?.personalInfoChange[1]?.Checked)) {
        copiedMap.delete(OTHERS_CV);
      }
      let updatedAdditionalObj = Object.fromEntries(copiedMap);
  
      let mergeMap = {...personalAttachmentMap, ...updatedAdditionalObj};
      let attachmentMapSize = getMapSize(mergeMap);
      if (attachmentMapSize > 0) {
        let total = 0;
        Object.entries(mergeMap).forEach(([SubDocId, value]) => {
          if (state.docTypeProfile) {
            let DocTypeSelected = state.docTypeProfile.find(item => item.SubDocId === SubDocId);
            console.log('value=', value);
            total = total + value.length;
            for (let j = 0; j < value.length; j++) {
              i++;
              const matches = value[j]?.imgData.match(/^data:([A-Za-z-+/]+);base64,(.+)$/);
              let uploadRequest = {
                  jsonDataInput: {
                      Action: "SubmitDocumentImage",
                      APIToken: state.apiToken,
                      Authentication: AUTHENTICATION,
                      ClientID: state.clientId,
                      Company: COMPANY_KEY,
                      DeviceId: state.deviceId,
                      OS: fromNative?fromNative: WEB_BROWSER_VERSION,
                      Project: "mcp",
                      UserLogin: state.clientId,
                      TransactionID: transactionId,
                      DocProcessID: state.proccessType,
                      DocTypeID: DocTypeSelected.DocTypeID,
                      DocTypeName: DocTypeSelected.DocTypeName,
                      SubDocID: DocTypeSelected.SubDocId,
                      NumberOfPage: getNumOfPage(SubDocId),
                      Image: (matches.length === 3) ? matches[2] : ''
                  }
              }
              onlineRequestSubmit(uploadRequest)
                  .then(res => {
                      if ((res.Response.Result === 'true') && (res.Response.ErrLog === 'Successfull') && res.Response.Message) {
                          if (i === total) {
                              setState(prevState => ({
                                ...prevState,
                                trackingId: transactionId,
                                stepName: PERSONAL_INFO_STATE.SDK,
                                isSubmitting: false
                              }));
                          }
                      } else {
                          setState(prevState => ({
                            ...prevState,
                            apiError: true,
                            isSubmitting: false
                          }));
                      }
                      saveLocal();
                  }).catch(error => {
                   console.log(error);
              });
            }

  
          }
        });
      } else {
        setState(prevState => ({
          ...prevState,
          // clientProfile: clientProfile,
          // configClientProfile: configClientProfile,
          trackingId: transactionId,
          stepName: PERSONAL_INFO_STATE.SDK,
          isSubmitting: false
        }));
      }


      // for (let i = 0; i < state.personalInfo.attachmentState.attachmentList.length; i++) {

      // }
  }
  
  const fetchCPConsentConfirmation = (TrackingID) => {
      console.log('fetchCPConsentConfirmation', TrackingID);
      let request = {
          jsonDataInput: {
              Action: "CheckCustomerConsent",
              APIToken: state.apiToken,
              Authentication: AUTHENTICATION,
              ClientID: state.clientId,
              Company: COMPANY_KEY,
              ClientList: state.clientId,
              ProcessType: state.proccessType,
              DeviceId: state.deviceId,
              OS: fromNative?fromNative: WEB_BROWSER_VERSION,
              Project: "mcp",
              UserLogin: state.clientId,
              TrackingID: TrackingID,
          }
      };
      console.log('fetchCPConsentConfirmation request', request)

      CPConsentConfirmation(request)
          .then(res => {
              console.log('res=', res);
              const Response = res.Response;
              if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
                  // const { DOB } = this.state.clientProfile;
                  const clientProfile = Response.ClientProfile;
                  const configClientProfile = Response.Config;
                  const consentResultPO = generateConsentResults(clientProfile)?.ConsentResultPO;
                  // alert('consentResultPO=' + consentResultPO);
                  const trackingID = TrackingID;
                  if (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED ) {
                      // let state = this.state;
                      // state.clientProfile = clientProfile;
                      // state.configClientProfile = configClientProfile;
                      // state.trackingId = trackingID;
                      // state.clientListStr = this.props.clientId;

                      // // state.appType = 'CLOSE';
                      // state.appType = this.props.appType;
                      // // state.proccessType = 'CSA';
                      // state.stepName = FUND_STATE.SDK
                      // this.setState(state);
                      // this.props.setStepName(FUND_STATE.SDK);
                      // this.setState({isSubmitting: false});
                      // alert('done check consent');
                      setState(prevState => ({
                        ...prevState,
                        clientProfile: clientProfile,
                        configClientProfile: configClientProfile,
                        trackingId: trackingID,
                        stepName: PERSONAL_INFO_STATE.SDK,
                        isSubmitting: false
                      }));
                  } else {
                      genOtpV2();
                  }
              } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
                  this.setState({
                      openPopupWarningDecree13: false,
                  });
                  genOtpV2();
              }
          })
          .catch(error => {
              console.log(error);
              setState(prevState => ({
                ...prevState,
                isSubmitting: false
              }));
          });
  }

  const handleSetStepName = (step) => {
    setState(prevState => ({
      ...prevState,
      stepName: step,
      localUploading: false,
      isSubmitting: false
    }));
  }

  const genOtpV2 = () => {
    // alert('genOtpV2');
      //gen otp, email/phone get at backend
      // const genOTPRequest = {
      //     jsonDataInput: {
      //         Company: COMPANY_KEY,
      //         Action: 'GenOTPV2',
      //         APIToken: state.apiToken,
      //         Authentication: AUTHENTICATION,
      //         ClientID: state.clientId,
      //         DeviceId: state.deviceId,
      //         Note: state.proccessType + 'ProcessConfirm',
      //         OS: fromNative?fromNative: WEB_BROWSER_VERSION,
      //         Project: 'mcp',
      //         UserLogin: state.clientId,
      //         DCID : state.DCID,
      //     }

      // }
      // genOTP(genOTPRequest)
      //     .then(response => {
      //         if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
      //             setState(prevState => ({
      //               ...prevState,
      //               showOtp: true,
      //               transactionId: response.Response.Message,
      //               minutes: 5,
      //               seconds: 0
      //             }));
      //             startTimer();
      //         } else if (response.Response.ErrLog === 'OTP Exceed') {
      //             document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
      //         } else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
      //             document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
      //         } else {
      //             document.getElementById("popup-exception").className = "popup special point-error-popup show";
      //         }
      //         setState(prevState => ({
      //           ...prevState,
      //           isSubmitting: false
      //         }));
      //     }).catch(error => {
      //         setState(prevState => ({
      //           ...prevState,
      //           isSubmitting: false
      //         }));
      //         document.getElementById("popup-exception").className = "popup special point-error-popup show";
      //     });

  }

  // startTimer = () => {
  //   let myInterval = setInterval(() => {
  //     if (state.seconds > 0) {
  //       this.setState({ seconds: state.seconds - 1 });
  //     }
  //     if (state.seconds === 0) {
  //       if (state.minutes === 0) {
  //         clearInterval(myInterval)
  //       } else {
  //         this.setState({ minutes: state.minutes - 1, seconds: 59 });
  //       }
  //     }
  //   }, 1000)
  //   return () => {
  //     clearInterval(myInterval);
  //   };

  // }

  //Upload others
  const dragFileOverOthers=(htmlId, event)=> {
    event.preventDefault();
    document.getElementById(htmlId).className = "img-upload active";
  }
  
  const dropFileOthers = async (event, SubDocId) => {
    if (state.isSubmitting || state.localUploading) {
      return;
    }
    event.preventDefault();
    onLocalUploading();
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    const files = Object.values(event.dataTransfer.files);
    const attachments = state.attachmentState;

     if (files && files.length > 20) {
      event.dataTransfer.clearData();
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)) {
      event.dataTransfer.clearData();
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    }
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        if (validateImage.includes(file.type)) {
          try {
            const options = {
              maxSizeMB: 3,
              maxWidthOrHeight: 1920,
              useWebWorker: true,
              initialQuality: 0.2
            };
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error('Error compressing file:', error);
          }
          setState(prevState => ({
            ...prevState,
            errorUpload: '',
            localUploading: false
          }));
        } else {
          setState(prevState => ({
            ...prevState,
            errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png',
            localUploading: false
          }));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, SubDocId, event) => {
      let index = 0;
      const processNextBatch = async () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        const batchResults = await Promise.all(promises);
        const arrAttData = batchResults.map(base64 => ({ imgData: base64 }));
        const attachments = state.attachmentState.attachmentMap;
        const attList = state.attachmentState.attachmentList;
  
        if (attachments !== null && attachments !== undefined) {
          let att = attachments[SubDocId];
          if (!att) {
            att = [];
          }

          attList.push(...arrAttData);
          att.push(...arrAttData);
          attachments[SubDocId] = att;
          setState(prevState => ({
            ...prevState,
              attachmentState: {
                ...prevState.attachmentState,
                attachmentList: attList,
                attachmentMap: attachments
              }
          }));
        }
  
        index += batchSize;
        if (index < files.length) {
          processNextBatch();
        } else {
          event.dataTransfer.clearData();
		    setState(prevState => ({
              ...prevState,
              localUploading: false
            }));
          saveLocal();
        }
      };
  
      processNextBatch();
    };
  
    processFilesInBatches(files, 5, processFile, SubDocId, event);
  }

  const uploadAttachmentOthers = async (event, SubDocId) => {
    if (state.isSubmitting || state.localUploading) {
      return;
    }
    event.preventDefault();
    onLocalUploading();
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    const files = Array.from(event.target.files);
    const attachments = state.attachmentState;

     if (files && files.length > 20) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    } else if (files && attachments && attachments.attachmentList && (files.length + attachments.attachmentList.length > 20)) {
      event.target.value = null;
      setState(prevState => ({
        ...prevState,
        showMaxImageOnSectionError: 'Quý khách có thể đính kèm tối đa 20 chứng từ.',
        localUploading: false
      }));
      return;
    }
    const processFile = (file) => {
      return new Promise(async (resolve, reject) => {
        if (validateImage.includes(file.type)) {
          try {
            const options = {
              maxSizeMB: 3,
              maxWidthOrHeight: 1920,
              useWebWorker: true,
              initialQuality: 0.2
            };
            const compressedFile = await imageCompression(file, options);
            let reader = new FileReader();
            reader.readAsDataURL(compressedFile);
            reader.onloadend = () => resolve(reader.result);
          } catch (error) {
            console.error('Error compressing file:', error);
          }
          setState(prevState => ({
            ...prevState,
            errorUpload: '',
            localUploading: false
          }));
        } else {
          setState(prevState => ({
            ...prevState,
            errorUpload: 'Chỉ hỗ trợ định dạng jpeg/jpg/png',
            localUploading: false
          }));
        }
      });
    };
  
    const processFilesInBatches = (files, batchSize, processFile, SubDocId, event) => {
      let index = 0;
      const processNextBatch = async () => {
        const batch = files.slice(index, index + batchSize);
        const promises = batch.map(file => processFile(file));
        const batchResults = await Promise.all(promises);
        const arrAttData = batchResults.map(base64 => ({ imgData: base64 }));
		const attachments = state.attachmentState.attachmentMap;
		const attList = state.attachmentState.attachmentList;
  
        if (attachments !== null && attachments !== undefined) {
          let att = attachments[SubDocId];
          if (!att) {
            att = [];
          }

          attList.push(...arrAttData);
          att.push(...arrAttData);
          attachments[SubDocId] = att;
          console.log('attachments=', attachments);
          console.log('attList=', attList);
          setState(prevState => ({
            ...prevState,
              attachmentState: {
                ...prevState.attachmentState,
                attachmentList: attList,
                attachmentMap: attachments
              }
          }));
        }
  
        index += batchSize;
        if (index < files.length) {
          processNextBatch();
        } else {
          event.target.value = null;
          setState(prevState => ({
            ...prevState,
            localUploading: false
          }));
          saveLocal();
        }
      };
  
      processNextBatch();
    };
  
    processFilesInBatches(files, 5, processFile, SubDocId, event);
  }

  const deleteAttachmentOthersGo = (attachmentIndex, SubDocId) => {
    const attachments = state.attachmentState.attachmentList;
    const attachmentsMap = state.attachmentState.attachmentMap;
    attachments.splice(attachmentIndex, 1);
    // attachmentsMap[SubDocId].splice(attachmentIndex, 1);

    let copiedMap = new Map(Object.entries(attachmentsMap));
    copiedMap.delete(SubDocId);

    console.log('attachments=', copiedMap);
    console.log('attachmentsMap=', copiedMap);
    setState(prevState => ({
      ...prevState,
      attachmentState: {
        ...prevState.attachmentState,
        attachmentList: attachments,
        attachmentMap: copiedMap
      }
    }));
  }

  const deleteAttachmentOthers=(attachmentIndex, SubDocId)=> {
    // setState(prevState => ({
    //   ...prevState,
    //   showOthersConfirm: true,
    //   deleteAttachmentOthersIndex: attachmentIndex,
    //   deleteOthersSubDocId: SubDocId
    // }));
    deleteAttachmentOthersGo(attachmentIndex, SubDocId);
  }

  const AgreeOthersDelete = () => {
    setState(prevState => ({
      ...prevState,
      showOthersConfirm: false,
    }));
    deleteAttachmentOthersGo(state.deleteAttachmentOthersIndex, state.deleteOthersSubDocId);
  }

  useEffect(() => {
    // This code will run whenever 'param' changes
    console.log('localUploading=', state.localUploading);
    console.log('URL parameter changed:', param);
    console.log('state=', state);
    // Add your logic here
    fromNative = getUrlParameter("fromApp");
    let AppType = getUrlParameter("AppType");

    if (AppType === "Internal") {
      if (!AppType) {
          alert("Invalid Url");
          return;
      }
      // setState(prevState => ({ ...prevState, appType: AppType }));
      // let ParamData = getUrlParameter("Data");
      let ParamData = getUrlParameterNoDecode("Data");

      // ParamData = "OF8YGHE10%2BySX1KwBFarUPukEU7wCUPUho1PlEtym5iWF5xoh4IXOzLPF8XN33aGgX48IfQIdSF9X87PMl0CPNlCi%2FE2M15PJuZ5tuII%2FC3h43qQnVF1QtlnaoVBFQ%2FtLA9u%2BXZI8vdP%2FWW3CEmdprlLRZ16oloPiw7A2PsEJMIG%2FEPWQmkRGQngyZXmEVVfMlWbqL9NeeGbzZcayRqQLEralQz1jsKlsXAz9XbrYYN%2FlyWOR82%2BNi16zrZd6dpmefL9eK%2BlnB%2BXntxLvYl3eH77CEQUIT3QUtNjeXe0ecJiWDqrXYvjjuahEZ7lvx4G0YZLTo1jmHiAB52V%2BjWsrw%3D%3D";
      if (ParamData) {
          let ParamObject = JSON.parse(AES256.decrypt(ParamData, COMPANY_KEY2));
          if (ParamObject) {
              let trackingId = ParamObject?.TrackingID;
              let clientListStr = ParamObject?.ClientList;
              let clientId = ParamObject?.ClientID;
              let proccessType = ParamObject?.ProcessType;
              let deviceId = ParamObject?.DeviceId;
              let apiToken = ParamObject?.APIToken;
              let policyNo = ParamObject?.policyNo;
              let cphone = ParamObject?.phone;
              let twoFA = ParamObject?.twoFA;
              let gender = ParamObject?.gender;
              if (!cphone) {
                  //yêu cầu cập nhật số dt
                  setState(prevState => ({ ...prevState, noPhone: true }));
                  return;
              }
              if (!twoFA || (twoFA === '0') ||twoFA === 'undefined') {
                  //yêu cầu bật 2fa
                  setState(prevState => ({ ...prevState, noTwofa: true }));
                  return;
              }
              if (gender === 'C') {
                  //không dành cho khách hàng doanh nghiệp
                  setState(prevState => ({ ...prevState, isCompany: true }));
                  return;
              }
            let clientClass = ParamObject?.clientClass;
            let clientName = ParamObject?.clientName;
            let email = ParamObject?.email;
            let DCID = ParamObject?.DCID;
            setState(prevState => ({ ...prevState, trackingId: trackingId, clientListStr: clientListStr, clientId: clientId, proccessType: proccessType, deviceId: deviceId, apiToken: apiToken, policyNo: policyNo, appType: AppType, phone: cphone, twoFA: twoFA, clientClass: clientClass, clientName: clientName, email: email, DCID: DCID, gender: gender, stepName: PERSONAL_INFO_STATE.CHOOSE_LI }));
            appGoByTrackingId(trackingId, apiToken, deviceId, clientId, policyNo);
          }
      }
        
    } else {
      if (!state.phone) {
        //yêu cầu cập nhật số dt
        setState(prevState => ({ ...prevState, noPhone: true }));
        return;
      }
      if (!state.twoFA || (state.twoFA === '0') || state.twoFA === 'undefined') {
        //yêu cầu bật 2fa
        setState(prevState => ({ ...prevState, noTwofa: true }));
        return;
      }
      if (getSession(GENDER) === 'C') {
          //không dành cho khách hàng doanh nghiệp
          setState(prevState => ({ ...prevState, isCompany: true }));
          return;
      }
      setState(prevState => ({ ...prevState, stepName: PERSONAL_INFO_STATE.CHOOSE_LI }));
      // this.loadND13DataTemp(state.clientId, state.trackingId, state.apiToken, state.deviceId, state.clientListStr, state.proccessType);
      goByTrackingId(props.match.params.id);
    }

    // document.addEventListener("keydown", this.handleClosePopupEsc, false);
    cpSaveLog(`Web_Open_${PageScreen.POL_TRANS_CHANGE_PERSONAL_INFO}`);
    trackingEvent(
        "Thay đổi thông tin cá nhân",
        `Web_Open_${PageScreen.POL_TRANS_CHANGE_PERSONAL_INFO}`,
        `Web_Open_${PageScreen.POL_TRANS_CHANGE_PERSONAL_INFO}`,
    );
  }, []);

  useEffect(() => {
    saveLocal();
  }, [state.personalInfo?.attachmentState?.attachmentList?.length, state.attachmentState?.attachmentList?.length]);

  const goByTrackingId = (trackingID) => {
    let flowNoti = false;
    if (trackingID) {
      flowNoti = true;
    }
    let request = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: "CheckPSRequestStatus",
        APIToken: state.apiToken,
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        OS: fromNative ? fromNative : WEB_BROWSER_VERSION,
        Project: "mcp",
        ClientID: state.clientId,
        UserLogin: state.clientId,
        FromSystem: fromNative ? "DCA" : "DCW",
        TrackingID: trackingID
      }
    }
    onlineRequestSubmit(request)
      .then(Res => {
        const Response = Res.Response;
        if (Response.Result === 'true' && Response.ErrLog && Response.Message) {
          if (Response.ErrLog !== 'DRAFT') {
            setState(prevState => ({ ...prevState, linkExpired: true}));
            return;
          }
          setState(prevState => ({ ...prevState, trackingId: trackingID, fromNoti: flowNoti }));
          updateStepName(PERSONAL_INFO_STATE.SDK);
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          let from = fromNative;
          if (from) {
            let obj = {
              Action: "BACK_ND13_" + state.proccessType,
              ClientID: state.clientId,
              PolicyNo: state.policyNo,
              TrackingID: state.trackingId
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
          } else {
            window.location.href = '/';
          }
        }
      })
      .catch(error => {
        let from = fromNative;
        if (from) {
          let obj = {
            Action: "BACK_ND13_" + state.proccessType,
            ClientID: state.clientId,
            PolicyNo: state.policyNo,
            TrackingID: state.trackingId
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
        } else {
          window.location.href = '/';
        }
      });
  }

  const appGoByTrackingId = (trackingID, apiToken, deviceId, clientId, policyNo) => {
    let flowNoti = false;
    if (trackingID) {
      flowNoti = true;
    }
    let request = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: "CheckPSRequestStatus",
        APIToken: apiToken,
        Authentication: AUTHENTICATION,
        DeviceId: deviceId,
        OS: fromNative ? fromNative : WEB_BROWSER_VERSION,
        Project: "mcp",
        ClientID: clientId,
        UserLogin: clientId,
        FromSystem: fromNative ? "DCA" : "DCW",
        TrackingID: trackingID
      }
    }
    onlineRequestSubmit(request)
      .then(Res => {
        const Response = Res.Response;
        if (Response.Result === 'true' && Response.ErrLog && Response.Message) {
          if (Response.ErrLog !== 'DRAFT') {
            setState(prevState => ({ ...prevState, linkExpired: true }));
            return;
          }
          setState(prevState => ({ ...prevState, trackingId: trackingID, fromNoti: flowNoti}));
          updateStepName(PERSONAL_INFO_STATE.SDK);
        } else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          let from = fromNative;
          if (from) {
            let obj = {
              Action: "BACK_ND13_" + state.proccessType,
              ClientID: clientId,
              PolicyNo: policyNo,
              TrackingID: trackingID
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
          } else {
            window.location.href = '/';
          }
        }
      })
      .catch(error => {
        let from = fromNative;
        if (from) {
          let obj = {
            Action: "BACK_ND13_" + state.proccessType,
            ClientID: state.clientId,
            PolicyNo: state.policyNo,
            TrackingID: state.trackingId
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
        } else {
          window.location.href = '/';
        }
      });
  }

  const updateStepName = (stepName) => {
    setState(prevState => ({ ...prevState, stepName: stepName }));
    saveLocal();
  }

  const closePopupError = () => {
    setState(prevState => ({
      ...prevState, 
      noPhone: false,
      noTwofa: false,
      noValidPolicy: false,
      isCompany: false,
      linkExpired: false 
     }));
    let from = fromNative;
    if (from) {
      let obj = {
        Action: "CANCEL_ND13_" + state.proccessType,
        ClientID: state.clientId,
        PolicyNo: state.policyNo,
        TrackingID: state.trackingId
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
  }
  
  const closeMessageError = () => {
    setState(prevState => ({
      ...prevState,
      errorMessage: '',
      localUploading: false,
      isSubmitting: false
    }));
    deleteAttachment(state.attIndexErr, state.SubDocIdErr, true);
  }

  const closeIdentityMessage = () => {
    setState(prevState => ({
      ...prevState,
      identityMessage: '',
      localUploading: false,
      isSubmitting: false
    }));
  }

  const closeTheSame = () => {
    setState(prevState => ({
      ...prevState,
      identityMessage: '',
      theSame: false,
      personalInfoChecked: false,
      residenceCheck: false,
      occupationChecked: false,
      localUploading: false,
      isSubmitting: false
    }));
    let personalInfo = {
      DocTypeNameSelected: undefined,
      attachmentState: {
        previewVisible: false,
        previewImage: "",
        previewTitle: "",
        attachmentList: [],
        attachmentMap: {},
        disabledButton: true,
      },
      ocrSuccess: false,
      isQRCode: false,
      QRCodeFrontOrBack: '',
      personalInfoChange: [
        {
          Checked: false,
          ProcessTypeCode: 'ClientNameS',
          NewFamilyName: '',
          NewGivenName: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          ProcessTypeCode: 'IDNumberS',
          NewClientIDNumber: '',
          NewClientPassportNumber: '',
          OldClientIDNumber: '',
          isChange: false,
          error: '',
          error2: ''
        },
        {
          Checked: false,
          NewDOBSelected: undefined,
          ProcessTypeCode: 'DayofBirthS',
          NewDOB: '',
          isChange: false,
          error: '',
        },
        {
          Checked: false,
          ProcessTypeCode: 'GenderS',
          NewGenderCode: undefined,
          NewGender: undefined,
          isChange: false,
          error: '',
        }
      ]
    };
    setState(prevState => ({ ...prevState, 
      showConfirmResetUpload: false,
      personalInfo: personalInfo
    }));
  }

  const closeFatca = () => {
    setState(prevState => ({
      ...prevState,
      showFatca: false
    }));
  }

  const showFatca = () => {
    setState(prevState => ({
      ...prevState,
      showFatca: true
    }));
  }

  const clearFatca = () => {
    setState(prevState => ({
      ...prevState,
      residenceInfo: {
        ...prevState.residenceInfo,
        USNationality: 'No',
        USPermanent: 'No',
        USTaxDeclared: 'No'
      }
    }));
  }

  const onConfirmFatca = () => {
    setState(prevState => ({
      ...prevState,
      confirmFatca: true,
      showFatca: false
    }));
    updateStepName(state.stepName + 1);
  }

  //ND13
  const generateConsentResults=(data)=> {
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

  const haveLIStillNotAgree=(data)=> {
    let result = false;
    console.log('haveLIStillNotAgree=', data);
    data.forEach((item, index) => {
      const role = item.Role;
      if ((role === 'LI') && (item.ConsentRuleID === 'ND13') && (item.ConsentResult !== ConsentStatus.AGREED)) {
        console.log('haveLIStillNotAgree true');
        result = true;
        return result;
      }

    });
    console.log('haveLIStillNotAgree =', result);
    return result;
  }

  const fetchCPConsentConfirmationRefresh=()=> {
    let clientIds = state.clientId;
    if (state.clientId !== state.selectedLI?.LifeInsuredID) {
      clientIds = clientIds + ',' + state.selectedLI?.LifeInsuredID;
    }
    let request = {
      jsonDataInput: {
        Action: "CheckCustomerConsent",
        APIToken: state.apiToken,
        Authentication: AUTHENTICATION,
        ClientID: state.clientId,
        Company: COMPANY_KEY,
        ClientList: clientIds,
        ProcessType: state.proccessType,
        DeviceId: state.deviceId,
        OS: fromNative?fromNative: WEB_BROWSER_VERSION,
        Project: "mcp",
        UserLogin: state.clientId,
        TrackingID: state.trackingId,
      }
    };

    CPConsentConfirmation(request)
      .then(res => {
        const Response = res.Response;
        if (Response.ErrLog === 'SUCCESSFUL' && Response.Result === 'true' && Response.ClientProfile) {
          const clientProfile = Response.ClientProfile;
          const consentResultPO = generateConsentResults(clientProfile)?.ConsentResultPO;
          const isOpenPopupWarning = (consentResultPO === ConsentStatus.WAIT_CONFIRM || consentResultPO === ConsentStatus.EXPIRED || consentResultPO === ConsentStatus.DECLINED) || haveLIStillNotAgree(clientProfile);
          setState(prevState => ({
            ...prevState,
            openPopupWarningDecree13: isOpenPopupWarning
          }));
        } else if (Response.ErrLog === 'CONSENT DISABLE' && Response.Result === 'true') {
          setState(prevState => ({
            ...prevState,
            consentDisabled: true
          }));
        } else {
          console.log("System error!");
        }
        saveLocal();
      })
      .catch(error => {
        console.log(error);
      });
  }
  //End ND13

  const closeMaxImageOnSectionError = () => {
    setState(prevState => ({
      ...prevState,
      showMaxImageOnSectionError: ''
    }));
  }

  const closeShowConfirm = () => {
    setState(prevState => ({
      ...prevState,
      showConfirm: false
    }));
  }

  const closeShowOthersConfirm = () => {
    setState(prevState => ({
      ...prevState,
      showOthersConfirm: false
    }));
  }
  

  const Agree = () => {

  }
  
  const showNotice = () => {
    setState(prevState => ({
      ...prevState,
      showNotice: true
    }));
  }

  const closeNotice = () => {
    setState(prevState => ({
      ...prevState,
      showNotice: false
    }));
  }

  const closeToHome = () => {
      let from = getUrlParameter("fromApp");
      if (from) {
          let obj = {
              Action: "END_ND13_" + state.proccessType,
              ClientID: state.clientId,
              TrackingID: state.trackingId
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
      } else {
          window.location.href = '/update-personal-info';
      }
  }
  const padding = getSession(IS_MOBILE)? ' padding-top0': '';
  let clsName = getSession(ACCESS_TOKEN) ? "logined" : "";
  clsName = clsName + padding;

  // if (!state.apiToken) {
  //   return <Redirect to={{
  //     pathname: "/"
  // }}/>;
  // }
  return (
    <>
      <Helmet>
        <title>Điều chỉnh thông tin cá nhân – Dai-ichi Life Việt Nam</title>
        <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
        <meta name="robots" content="noindex, nofollow" />
        <meta property="og:type" content="website" />
        <meta property="og:url" content={FE_BASE_URL + "/update-personal-info"} />
        <meta property="og:title" content="Điều chỉnh thông tin hợp cá nhân - Dai-ichi Life Việt Nam" />
        <meta property="og:description"
          content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
        <meta property="og:image"
          content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
        <link rel="canonical" href={FE_BASE_URL + "/update-personal-info"} />
      </Helmet>
      <main className={clsName} style={{background: '#f5f3f2'}} id="main-id">

        {state.linkExpired ?
          (fromNative ? (
            <ExpirePopup closePopup={()=>closePopupError()}
              msg={'Liên kết với thông báo này không còn hiệu lực.'}
              imgPath={FE_BASE_URL + '/img/icon/iconAlertTimeExpired.svg'}
            />
          ) : (<ND13Expire />)
          ) : (
            <div className='main-warpper basic-mainflex e-claim personal-info'>
              <div className='insurance' style={{ padding: '0', paddingTop: '60px' }}>
                <div className={getSession(IS_MOBILE) ? "heading2 heading-mobile" : (state.stepName === PERSONAL_INFO_STATE.CHOOSE_LI)?"heading2 heading-web-step1": "heading2 heading-web"}>
                  {!getSession(IS_MOBILE) &&
                    <div className="breadcrums">
                      <div className="breadcrums__item">
                        <p>Giao dịch hợp đồng</p>
                        <span>&gt;</span>
                      </div>
                      <div className="breadcrums__item">
                        <p>Điều chỉnh thông tin cá nhân</p>
                        <span>&gt;</span>
                      </div>
                    </div>
                  }
                  {/* <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                <p>Chọn thông tin</p>
                <i><img src="../../img/icon/return_option.svg" alt="" /></i>
              </div> */}
                  {(state.stepName < PERSONAL_INFO_STATE.SDK) &&
                    <div className={getSession(IS_MOBILE) ? "heading__tab mobile" : "heading__tab"}>
                      <div className="step-container">
                        <div className="step-wrapper" style={{ background: '#f5f3f2' }}>
                          {(state.stepName > PERSONAL_INFO_STATE.INIT) &&
                            <div className="step-btn-wrapper">
                              <div className="back-btn" style={{ marginLeft: '-96px', lineHeight: '16px' }}>
                                <button>
                                  <div className="svg-wrapper" onClick={() => handlerBackToPrevStep()}>
                                    <svg
                                      width="11"
                                      height="15"
                                      viewBox="0 0 11 15"
                                      fill="none"
                                      xmlns="http://www.w3.org/2000/svg"
                                    >
                                      <path
                                        d="M9.31149 14.0086C9.13651 14.011 8.96586 13.9566 8.82712 13.8541L1.29402 8.1712C1.20363 8.10293 1.13031 8.01604 1.07943 7.91689C1.02856 7.81774 1.00144 7.70887 1.00005 7.59827C0.998661 7.48766 1.02305 7.37814 1.07141 7.27775C1.11978 7.17736 1.1909 7.08865 1.27955 7.01814L8.63636 1.17893C8.71445 1.1171 8.80442 1.07066 8.90112 1.04227C8.99783 1.01387 9.09938 1.00408 9.19998 1.01344C9.40316 1.03235 9.59013 1.12816 9.71976 1.27978C9.84939 1.4314 9.91106 1.62642 9.89121 1.82193C9.87135 2.01745 9.7716 2.19744 9.6139 2.32231L2.99589 7.57403L9.77733 12.6865C9.90265 12.7809 9.99438 12.9104 10.0398 13.0572C10.0853 13.204 10.0823 13.3608 10.0311 13.506C9.97999 13.6511 9.88328 13.7774 9.75437 13.8675C9.62546 13.9575 9.4707 14.0068 9.31149 14.0086Z"
                                        fill="#985801"
                                        stroke="#985801"
                                        strokeWidth="0.2"
                                      />
                                    </svg>
                                  </div>

                                  {!getSession(IS_MOBILE) ? (
                                    <span className="simple-brown" onClick={() => handlerBackToPrevStep()}>Quay lại</span>
                                  ) : (
                                    <p style={{textAlign: 'center', paddingLeft: '20px', minWidth: '230%', fontWeight: '700', verticalAlign: '18px', verticalAlign: 'bottom'}}>{'Tạo mới yêu cầu'}</p>
                                  )}
                                </button>
                              </div>
                              {/* <div className="save-wrap">
                            <button className="back-text">Lưu</button>
                          </div> */}
                            </div>
                          }
                          <div className="progress-bar" style={{ background: '#f5f3f2' }}>
                            <div className={(state.stepName >= PERSONAL_INFO_STATE.CHOOSE_LI) ? "step active" : "step"}>
                              <div className="bullet">
                                <span>1</span>
                              </div>
                              <p>Chọn <br /> khách hàng</p>
                            </div>
                            <div className={(state.stepName >= PERSONAL_INFO_STATE.UPDATE_INFO) ? "step active" : "step"}>
                              <div className="bullet">
                                <span>2</span>
                              </div>
                              <p>Nhập <br /> thông tin</p>
                            </div>
                            <div className={(state.stepName >= PERSONAL_INFO_STATE.ATTACHMENT) ? "step active" : "step"}>
                              <div className="bullet">
                                <span>3</span>
                              </div>
                              <p>Kèm <br />chứng từ</p>
                            </div>
                            <div className={(state.stepName >= PERSONAL_INFO_STATE.VERIFICATION) ? "step active" : "step"}>
                              <div className="bullet">
                                <span>4</span>
                              </div>
                              <p>Xác <br /> nhận</p>
                            </div>
                          </div>
                          {state.stepName > PERSONAL_INFO_STATE.CHOOSE_LI &&
                            <div className="step-btn-save-quit">
                              <div className='svg-wrapper' style={{ cursor: 'pointer', lineHeight: '16px' }}>
                                <button onClick={() => saveLocalAndQuit()} >
                                  <span className="simple-brown"
                                  >Lưu & Thoát</span>
                                </button>
                              </div>
                            </div>
                          }
                        </div>
                      </div>
                    </div>
                  }
                </div>
                {state.stepName === PERSONAL_INFO_STATE.CHOOSE_LI &&
                  <PersonalInfoList
                    stepName={state.stepName}
                    appType={state.appType}
                    trackingId={state.trackingId}
                    clientId={state.clientId}
                    proccessType={state.proccessType}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    phone={state.phone}
                    twoFA={state.twoFA}
                    email={state.email}
                    DCID={state.DCID}
                    selectedLI={state.selectedLI}
                    updateStepName={updateStepName}
                    updateSelectedLI={updateSelectedLI}
                    handlerBackToPrevStep={handlerBackToPrevStep}
                    fetchCPConsentConfirmationRefresh={fetchCPConsentConfirmationRefresh}
                    saveLocal={saveLocal}
                    checkHaveSavingData={checkHaveSavingData}
                    chosenLISubmit={chosenLISubmit}
                  />
                }
                {state.stepName === PERSONAL_INFO_STATE.UPDATE_INFO &&
                  <InputPersonalInfo
                    stepName={state.stepName}
                    appType={state.appType}
                    trackingId={state.trackingId}
                    clientId={state.clientId}
                    proccessType={state.proccessType}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    phone={state.phone}
                    twoFA={state.twoFA}
                    email={state.email}
                    DCID={state.DCID}
                    // DOB={state.DOB}
                    // Gender={state.Gender}
                    // POID={state.POID}
                    selectedLI={state.selectedLI}
                    personalInfo={state.personalInfo}
                    residenceInfo={state.residenceInfo}
                    occupationInfo={state.occupationInfo}
                    docTypeProfile={state.docTypeProfile}
                    docTypeRebuild={state.docTypeRebuild}
                    occupationList={state.occupationList}
                    nationList={state.nationList}
                    localUploading={state.localUploading}
                    errorUpload={state.errorUpload}
                    personalInfoChecked={state.personalInfoChecked}
                    residenceCheck={state.residenceCheck}
                    occupationChecked={state.occupationChecked}
                    validAttacthment={validAttacthment}
                    validateMinimum={validateMinimum}
                    validInputText={state.validInputText}
                    errorInfoChange={state.errorInfoChange}
                    validResidence={state.validResidence}
                    validOccupation={state.validOccupation}
                    isSubmitting={state.isSubmitting}
                    onChangeDocType={onChangeDocType}
                    onChangeNation={onChangeNation}
                    onChangeOccupation={onChangeOccupation}
                    updateStepName={updateStepName}
                    handlerBackToPrevStep={handlerBackToPrevStep}
                    dragFileOver={dragFileOver}
                    dragFileLeave={dragFileLeave}
                    dropFile={dropFile}
                    uploadAttachment={uploadAttachment}
                    // updateAttachmentList={updateAttachmentList}
                    deleteAttachment={deleteAttachment}
                    toggleClientProperties={toggleClientProperties}
                    onChangeFamilyName={onChangeFamilyName}
                    onChangeGivenName={onChangeGivenName}
                    onChangeClientIDNumber={onChangeClientIDNumber}
                    onChangeOldClientIDNumber={onChangeOldClientIDNumber}
                    onChangeClientPassportNumber={onChangeClientPassportNumber}
                    onChangeClientDOB={onChangeClientDOB}
                    onChangeClientGender={onChangeClientGender}
                    onChangeEntryDate={onChangeEntryDate}
                    onChangePurposeOfResidence={onChangePurposeOfResidence}
                    onChangeDuration={onChangeDuration}
                    updateDocTypeProfile={updateDocTypeProfile}
                    updateDocTypeRebuild={updateDocTypeRebuild}
                    updateOccupationList={updateOccupationList}
                    updateNationList={updateNationList}
                    onLocalUploading={onLocalUploading}
                    togglePersonalInfo={togglePersonalInfo}
                    toggleResidence={toggleResidence}
                    toggleOccupation={toggleOccupation}
                    onChangeOccDescription={onChangeOccDescription}
                    validateInputText={validateInputText}
                    showFatca={showFatca}
                    clearFatca={clearFatca}
                    onChangeResidenceOccupation={onChangeResidenceOccupation}
                    onChangeResidenceOccDescription={onChangeResidenceOccDescription}
                    onChangePurposeOfResidenceDescription={onChangePurposeOfResidenceDescription}
                    toggleClientPropertiesPassport={toggleClientPropertiesPassport}
                    updateClientPropertiesPassport={updateClientPropertiesPassport}
                    saveLocal={saveLocal}
                  />
                }

                {state.stepName === PERSONAL_INFO_STATE.ATTACHMENT &&
                  <AdditionalAttachment
                    stepName={state.stepName}
                    appType={state.appType}
                    trackingId={state.trackingId}
                    clientId={state.clientId}
                    proccessType={state.proccessType}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    phone={state.phone}
                    twoFA={state.twoFA}
                    email={state.email}
                    DCID={state.DCID}
                    selectedLI={state.selectedLI}
                    personalInfo={state.personalInfo}
                    residenceInfo={state.residenceInfo}
                    occupationInfo={state.occupationInfo}
                    docTypeProfile={state.docTypeProfile}
                    residenceCheck={state.residenceCheck}
                    personalInfoChecked={state.personalInfoChecked}
                    occupationChecked={state.occupationChecked}
                    attachmentState={state.attachmentState}
                    localUploading={state.localUploading}
                    errorUpload={state.errorUpload}
                    onChangeDocType={onChangeDocType}
                    onChangeNation={onChangeNation}
                    onChangeOccupation={onChangeOccupation}
                    updateStepName={updateStepName}
                    handlerBackToPrevStep={handlerBackToPrevStep}
                    dragFileOver={dragFileOverOthers}
                    dragFileLeave={dragFileLeave}
                    dropFile={dropFileOthers}
                    uploadAttachment={uploadAttachmentOthers}
                    deleteAttachment={deleteAttachmentOthers}
                    showNotice={showNotice}
                    saveLocal={saveLocal}
                  />
                }

                {state.stepName === PERSONAL_INFO_STATE.VERIFICATION &&
                  <ReviewPersonalInfo
                    stepName={state.stepName}
                    appType={state.appType}
                    trackingId={state.trackingId}
                    clientId={state.clientId}
                    proccessType={state.proccessType}
                    deviceId={state.deviceId}
                    apiToken={state.apiToken}
                    phone={state.phone}
                    twoFA={state.twoFA}
                    email={state.email}
                    DCID={state.DCID}
                    // DOB={state.DOB}
                    // Gender={state.Gender}
                    // POID={state.POID}
                    selectedLI={state.selectedLI}
                    personalInfo={state.personalInfo}
                    residenceInfo={state.residenceInfo}
                    occupationInfo={state.occupationInfo}
                    docTypeProfile={state.docTypeProfile}
                    docTypeRebuild={state.docTypeRebuild}
                    occupationList={state.occupationList}
                    nationList={state.nationList}
                    localUploading={state.localUploading}
                    errorUpload={state.errorUpload}
                    personalInfoChecked={state.personalInfoChecked}
                    residenceCheck={state.residenceCheck}
                    occupationChecked={state.occupationChecked}
                    USNationality={state.residenceInfo.USNationality}
                    USPermanent={state.residenceInfo.USPermanent}
                    USTaxDeclared={state.residenceInfo.USTaxDeclared}
                    attachmentState={state.attachmentState}
                    isSubmitting={state.isSubmitting}
                    changePolicyInfoSubmit={changePolicyInfoSubmit}
                    updateStepName={updateStepName}
                    saveLocal={saveLocal}
                  />
                }

                {/*START ND13*/}
                {state.appType && state.trackingId && (state.stepName === PERSONAL_INFO_STATE.SDK) && <ND13
                  appType={state.appType}
                  trackingId={state.trackingId}
                  clientListStr={state.clientId !== state.selectedLI?.LifeInsuredID ? state.clientId + ',' + state.selectedLI?.LifeInsuredID : state.clientId}
                  clientId={state.clientId}
                  proccessType={state.proccessType}
                  deviceId={state.deviceId}
                  apiToken={state.apiToken}
                  PolicyNo={state.polID}
                  phone={state.phone}
                  clientName={state.clientName ? state.clientName : getSession(FULL_NAME)}
                  stepName={state.stepName}
                  // updateInfoState={state.updateInfoState}
                  selectedCliInfo={state?.selectedLI}
                  LifeInsuredID={state.selectedLI?.LifeInsuredID}
                  updateAllLIAgree={updateAllLIAgree}
                  updateExistLINotAgree={updateExistLINotAgree}
                  saveLocalAndQuit={() => saveLocalAndQuit()}
                  handleSetStepName={(step) => handleSetStepName(step)}
                  saveLocal={saveLocal}
                  deleteLocal={deleteLocal}
                  closeToHome={closeToHome}
                  fromNoti={state.fromNoti}
                />}
                {/* {state.showOtp &&
              <DOTPInput minutes={state.minutes} seconds={state.seconds} startTimer={startTimer()}
                          closeOtp={closeOtp} errorMessage={state.errorMessage}
                          popupOtpSubmit={popupOtpSubmit} reGenOtp={genOtpV2}
                          maskPhone={maskPhone(this.props.phone)}
                          />
            }  */}
                {state.errorMessage &&
                  <AlertPopupCustom closePopup={closeMessageError} msg={state.errorMessage} imgPath={popupImgPath} />
                }
                {state.identityMessage &&
                  <AlertPopupCustom closePopup={closeIdentityMessage} msg={state.identityMessage} imgPath={popupImgPath} />
                }
                {state.identityMessage && state.theSame &&
                  <AlertPopupCustom closePopup={closeTheSame} msg={state.identityMessage} imgPath={popupImgPath} />
                }

                {state.noPhone &&
                  <AlertPopupPhone closePopup={closePopupError} msg={'Quý khách chưa có Số điện thoại di động để nhận mã xác thực. Vui lòng liên hệ văn phòng Dai-ichi Life gần nhất để cập nhật'} imgPath={FE_BASE_URL + '/img/popup/no-phone.svg'}
                    screen={SCREENS.UPDATE_PERSONAL_INFO} />
                }

                {state.noTwofa &&
                  <AuthenticationPopup closePopup={() => closePopupError()}
                    msg={'Quý khách chưa xác thực nhận mã OTP qua SMS. Vui lòng xác thực để thực hiện giao dịch trực tuyến.'}
                    screen={SCREENS.UPDATE_PERSONAL_INFO} />
                }
                {state.showFatca &&
                  <NationalityPopup closePopup={closeFatca} ConfirmGo={onConfirmFatca} isSubmitting={state.isSubmitting}
                    USNationality={state.residenceInfo.USNationality} USPermanent={state.residenceInfo.USPermanent}
                    USTaxDeclared={state.residenceInfo.USTaxDeclared}
                    onChangeUSNationality={onChangeUSNationality} onChangeUSPermanent={onChangeUSPermanent}
                    onChangeUSTaxDeclared={onChangeUSTaxDeclared} />
                }
                {state.confirmGoToChooseLI &&
                  <ConfirmChangePopup closePopup={() => closeConfirmGoToChooseLI()} go={() => confirmGoToChooseLI()}
                    msg='Dữ liệu đã điền sẽ bị mất nếu quay lại bước chọn Khách hàng, Quý khách có đồng ý?'
                    agreeText='Đồng ý' notAgreeText='Không đồng ý' />
                }
                {state.saveAndQuit &&
                  <ConfirmChangePopup closePopup={() => closeSaveAndQuit()} go={() => saveAndQuit()}
                    msg={parseHtml('Quý khách có muốn Lưu lại yêu cầu này trước khi Thoát hay không?')}
                    agreeText='Lưu và Thoát' notAgreeText='Thoát' notAgreeFunc={() => Quit()} />
                }
                {state.haveCreatingData &&
                  <ConfirmChangePopup closePopup={() => closeHaveCreatingData()} go={() => agreeLoadCreatingLI()}
                    msg={parseHtml('Quý khách <span classname="basic-bold">' + (state.selectedLI ? state.selectedLI.FullName : '') + '</span> có một yêu cầu đang tạo. Quý khách có muốn tiếp tục với yêu cầu này?')}
                    agreeText='Tiếp tục' notAgreeText='Xóa và tạo mới'
                    notAgreeFunc={() => agreeLoadCreatingLINew()} />}
                {state.showConfirmResetUpload &&
                  <ConfirmChangePopup closePopup={() => closeConfirmResetUpload()} go={() => confirmResetUpload()}
                    msg='Dữ liệu đã điền sẽ bị mất, Quý khách có đồng ý tiếp tục?'
                    agreeText='Đồng ý' notAgreeText='Không đồng ý' />
                }
                {state.showConfirmResetPersonalInfo &&
                  <ConfirmChangePopup closePopup={() => closeConfirmResetPersonalInfo()} go={() => confirmResetPersonalInfo()}
                    msg='Dữ liệu đã điền sẽ bị mất, Quý khách có đồng ý tiếp tục?'
                    agreeText='Đồng ý' notAgreeText='Không đồng ý' />
                }
                {/* {state.localUploading &&
                <InfoPopup closePopup={()=>onCloseLocalUploading()}
                                      msg={'<p>Thông tin chứng từ đang được xử lý.<br/>Quý khách vui lòng đợi trong giây lát.</p>'} 
                                      imgPath={'https://localhost:3000' + '/img/popup/proccessing.svg'}
                                      />
            } */}
                {state.openPopupWarningDecree13 &&
                  <POWarningND13 proccessType={state.proccessType}
                    onClickExtendBtn={() => setState(prevState => ({
                      ...prevState,
                      openPopupWarningDecree13: false
                    }))}
                  />
                }
                {state.showMaxImageOnSectionError &&
                  <AlertPopupError closePopup={() => closeMaxImageOnSectionError()} msg={state.showMaxImageOnSectionError} imgPath={FE_BASE_URL + '/img/popup/quyenloi-popup.svg'} />
                }
                {state.showConfirm &&
                  <AgreePopup closePopup={() => closeShowConfirm()} agreeFunc={() => AgreeDelete()}
                    msg={'Quý khách có muốn xóa thông tin được chọn không?'}
                    agreeText='Đồng ý' />}
                {state.showOthersConfirm &&
                  <AgreePopup closePopup={() => closeShowOthersConfirm()} agreeFunc={() => AgreeOthersDelete()}
                    msg={'Quý khách có muốn xóa thông tin được chọn không?'}
                    agreeText='Đồng ý' />}
                {state.isCompany &&
                  <AlertPopupError closePopup={closePopupError} msg={'Chức năng chỉ dành cho khách hàng cá nhân. Quý khách vui lòng liên hệ văn phòng Dai-ichi Life Việt Nam để được hướng dẫn.'} imgPath={FE_BASE_URL + '/img/popup/notvalid.svg'}
                    screen={SCREENS.HOME} />
                }
                {state.showNotice &&
                  (
                    <NoticeHouseHoldRegistration closePopup={closeNotice} imgPath={FE_BASE_URL + '/img/popup/hotich-notice.svg'} />
                  )}
              </div>
            </div>
          )
        }
      </main>
    </>
  );
};

export default UpdatePersonalInfo;

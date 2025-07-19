import 'antd/dist/antd.min.css';
import React, { Component } from 'react';
import {
  ACCESS_TOKEN,
  CLIENT_ID,
  USER_LOGIN,
  EXPIRED_MESSAGE,
  AUTHENTICATION,
  COMPANY_KEY,
  FE_BASE_URL,
  CLAIM_TYPE,
  PageScreen
} from '../../constants';
import {showMessage, getSession, getDeviceId, trackingEvent} from '../../util/common';
import {
  CPGetClientProfileByCLIID,
  getClaimCheckhold,
  logoutSession,
  postClaimImage,
  CPSubmitForm,
  CPSaveLog
} from '../../util/APIUtils';
import LoadingIndicator from '../../../src/common/LoadingIndicator2';
import { Helmet } from "react-helmet";
import { Redirect } from 'react-router-dom';

class AdditionalClaim extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Action: 'ListClaimHold',
          Project: 'mcp',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
          APIToken: getSession(ACCESS_TOKEN)
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
      clientProfileListAll: [],
      checkHoldList: null,
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
      isWAITHC: false

    }
    this.handlerUpdateMainState = this.updateMainState.bind(this);
    this.handlerUploadAttachment = this.uploadAttachment.bind(this);
    this.handlerDragOver = this.dragFileOver.bind(this);
    this.handlerDragLeave = this.dragFileLeave.bind(this);
    this.handlerDrop = this.dropFile.bind(this);
    this.handlerUpdateAttachmentList = this.updateAttachmentList.bind(this);
    this.handlerDeleteAttachment = this.deleteAttachment.bind(this);
    this.handlerChangeInputComment = this.onChangeInputComment.bind(this);
  }
  onChangeInputComment(index4, event) {
    const target = event.target;
    const inputValue = target.value.trim();
    //console.log(inputValue);
    var jsonState = this.state;
    jsonState.attachmentState.disabledButton = false;
    jsonState.jsonInput4.jsonDataInput.DocTypeComments[index4].ClientComment = inputValue;
    this.setState(jsonState);
    //console.log(jsonState.jsonInput4.jsonDataInput.DocTypeComments);

  }
  updateMainState(subStateName, editedState) {
    var jsonState = this.state;
    jsonState[subStateName] = editedState;
    this.setState(jsonState);
    //console.log("CreateClaim - Updated main state:");
    //console.log(this.state.attachmentState);
  }
  updateState(jsonState) {
    jsonState.attachmentState.disabledButton = !(jsonState.attachmentState.claimAttachment.length > 0);
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
      var jsonState = this.state;
      var arrAttData = promisedFiles.map(base64 => {
        return { imgData: base64 }
      })
      //console.log(jsonState.attachmentState.claimAttachment[index4].attachmentList);
      var attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;
      if (attachments !== null && attachments !== undefined) {
        attachments.push(...arrAttData);
        this.updateState(jsonState);
      }
      // jsonState.attachmentState.claimAttachment[index4].attachmentList = arrAttData;
      event.target.value = null;
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
      var arrAttData = promisedFiles.map(base64 => {
        return { imgData: base64 }
      })
      var jsonState = this.state;
      var attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;

      if (attachments !== null && attachments !== undefined) {
        attachments.push(...arrAttData);
        this.updateState(jsonState);
      }
      event.target.value = null;
    });
  }
  updateAttachmentList(index4, event, val) {
    var jsonState = this.state;
    //console.log(jsonState);
    var attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;

    if (attachments !== null && attachments !== undefined) {
      // attachments.splice(0, 0, { imgData: val });
      attachments.push({ imgData: val });
      this.updateState(jsonState);
    }
    event.target.value = null;
  }
  deleteAttachment(index4, attachmentIndex) {
    var jsonState = this.state;
    var attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;
    attachments.splice(attachmentIndex, 1);
    this.updateState(jsonState);
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
      this.setState({ renderMeta: true });
    }).catch(error => {
      this.setState({ renderMeta: true });
    });
  }
  componentWillUnmount() {
    this.cpSaveLog(`Web_Close_${PageScreen.CLAIM_LIST_HOLD_PAGE}`);
    trackingEvent(
        "Theo dõi yêu cầu",
        `Web_Close_${PageScreen.CLAIM_LIST_HOLD_PAGE}`,
        `Web_Close_${PageScreen.CLAIM_LIST_HOLD_PAGE}`,
    );
  }

  componentDidMount() {
    this.cpSaveLog(`Web_Open_${PageScreen.CLAIM_LIST_HOLD_PAGE}`);
    trackingEvent(
        "Theo dõi yêu cầu",
        `Web_Open_${PageScreen.CLAIM_LIST_HOLD_PAGE}`,
        `Web_Open_${PageScreen.CLAIM_LIST_HOLD_PAGE}`,
    );
    const apiRequest = Object.assign({}, this.state.jsonInput);
    //console.log(apiRequest);
    var jsonState = this.state;
    CPGetClientProfileByCLIID(apiRequest).then(Res => {
      //console.log(Res);
      let Response1 = Res.Response;
      if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
        jsonState.jsonResponse = Response1;
        jsonState.clientProfile = Response1.ClientProfile;
      } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
        showMessage(EXPIRED_MESSAGE);
        logoutSession();
        this.props.history.push({
          pathname: '/home',
          state: { authenticated: false, hideMain: false }
        })
      } else {
        // var jsonState = this.state;
        jsonState.nodata = true;
        this.setState(jsonState);
      }
      this.setState(jsonState);
    }).catch(error => {
      this.props.history.push('/maintainence');
    });

  }
  render() {
    var callHistoryClaimAPI = () => {
      var jsonState = this.state;
      jsonState.clientProfile = null;
      const apiRequest = Object.assign({}, this.state.jsonInput);
      //console.log(apiRequest);
      CPGetClientProfileByCLIID(apiRequest).then(Res => {
        //console.log(Res);
        let Response1 = Res.Response;
        if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
          jsonState.jsonResponse = Response1;
          jsonState.clientProfile = Response1.ClientProfile;
        } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }
          })
        } else {
          // var jsonState = this.state;
          jsonState.nodata = true;
          this.setState(jsonState);
        }
        this.setState(jsonState);
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
    }
    var dropdownContent = (index) => {
      if (document.getElementById('dropdownContent' + index).className === "dropdown show") {
        document.getElementById('dropdownContent' + index).className = "dropdown";
      } else {
        document.getElementById('dropdownContent' + index).className = "dropdown show";
      }
    }
    var clickOnCard = (item, index) => {
      const jsonState = this.state;
      jsonState.isCompleted = false;
      jsonState.isWAITHC = false;
      jsonState.claimList = item;
      this.setState(jsonState);
      //console.log(item);

      jsonState.isEmpty = false;
      if (item.Status !== "WFHOLD") {
        jsonState.isCompleted = true;
        if (item.Status === "WAITHC") {
          jsonState.isWAITHC = true;
        }
      } else {
        jsonState.claimID = (item.lstClaimSubmission[0].ClaimID === '' ? item.lstClaimSubmission[0].SubmissionID : item.lstClaimSubmission[0].ClaimID);
        jsonState.claimType = item.lstClaimSubmission[0].ClaimType;
        jsonState.SubmissionID = item.lstClaimSubmission[0].SubmissionID;
        //console.log(item.ClaimID);
        //console.log(item.lstClaimSubmission[0].ClaimType);
        callCheckHoldAPI(item);
      }
      this.setState(jsonState);
      jsonState.clientProfile.forEach((cliID, i) => {
        if (i === index) {
          if (document.getElementById('card' + i)) {
            document.getElementById('card' + i).className = "card choosen";
          }

        } else {
          if (document.getElementById('card' + i)) {
            document.getElementById('card' + i).className = "card";
          }

        }
      });

      //console.log(jsonState.claimList);
    }
    var callCheckHoldAPI = (item) => {
      const jsonState = this.state;
      jsonState.jsonInput2.jsonDataInput.ClaimID = (item.lstClaimSubmission[0].ClaimID === '' ? item.lstClaimSubmission[0].SubmissionID : item.lstClaimSubmission[0].ClaimID);
      jsonState.jsonInput2.jsonDataInput.ClaimType = item.lstClaimSubmission[0].ClaimType;
      jsonState.jsonInput2.jsonDataInput.SubmissionID = item.lstClaimSubmission[0].SubmissionID;
      jsonState.jsonInput4.jsonDataInput.DocTypeComments = [];
      jsonState.attachmentState.claimAttachment = [];
      jsonState.checkHoldList = null;
      const apiRequest = Object.assign({}, this.state.jsonInput2);
      //console.log(apiRequest);
      getClaimCheckhold(apiRequest).then(Res => {
        //console.log(Res);
        let Response1 = Res.Response;
        if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
          var jsonState = this.state;
          jsonState.checkHoldList = Response1.ClientProfile;
          //jsonState.attachmentState = jsonState.checkHoldList;
          //console.log(jsonState.checkHoldList);
          for (let i = 0; i < jsonState.checkHoldList.length; i++) {
            jsonState.attachmentState.claimAttachment[i] = jsonState.checkHoldList[i];
            jsonState.attachmentState.claimAttachment[i]['attachmentList'] = []
            jsonState.jsonInput4.jsonDataInput.DocTypeComments[i] = jsonState.checkHoldList[i];

          }
          //console.log(jsonState.jsonInput4.jsonDataInput.DocTypeComments);
          this.setState(jsonState);
          //Alert.success("Welcome To NDBH Screen!");
          //console.log("attachmentState"); //console.log(jsonState.attachmentState);
          //console.log("DocTypeComments"); //console.log(jsonState.jsonInput3.jsonDataInput.DocTypeComments);
        } else if (Response1.NewAPIToken === 'invalidtoken' || Response1.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }

          })

        }
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
    }

    var submitClaimHold = () => {
      const jsonState = this.state;
      for (let i = 0; i < jsonState.jsonInput4.jsonDataInput.DocTypeComments.length; i++) {
        //console.log(jsonState.jsonInput4.jsonDataInput.DocTypeComments);
        jsonState.jsonInput4.jsonDataInput.SubmissionID = this.state.SubmissionID;
        jsonState.jsonInput4.jsonDataInput.ClaimID = jsonState.claimID;
        jsonState.jsonInput4.jsonDataInput.ClaimType = jsonState.claimType;
        //console.log(jsonState.attachmentState.claimAttachment[i]);
        jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].DocTypeID = (jsonState.attachmentState.claimAttachment[i].DocTypeID !== undefined ? jsonState.attachmentState.claimAttachment[i].DocTypeID : '');
        jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].SubDocID = (jsonState.attachmentState.claimAttachment[i].SubDocId !== undefined ? jsonState.attachmentState.claimAttachment[i].SubDocId : '');
        jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].WFAddDocID = (jsonState.attachmentState.claimAttachment[i].WFAddDocID !== undefined ? jsonState.attachmentState.claimAttachment[i].WFAddDocID : '');
        //console.log(jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].ClientComment);
        //console.log(jsonState.attachmentState.claimAttachment[i].attachmentList[0].imgData);
        if (jsonState.attachmentState.claimAttachment[i].attachmentList.length == 0 && jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].ClientComment !== '') {
          jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].Status = 'Missing';
        } else {
          jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].Status = '';
          if (!jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].ClientComment) {
            jsonState.jsonInput4.jsonDataInput.DocTypeComments[i].ClientComment = ' ';
          }
        }
        this.setState(jsonState);
      }
      const apiRequest = Object.assign({}, jsonState.jsonInput4);
      CPSubmitForm(apiRequest).then(Res => {
        //console.log(Res);
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
          //document.getElementById('session-expired-popup').className = 'popup option-popup show';
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }
          })
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });

    }
    var closePopup = () => {
      const jsonState = this.state;
      document.getElementById('popup').className = "popup envelop";
      jsonState.nextScreen = false;
      jsonState.isCompleted = true;
      this.setState(jsonState);
      callHistoryClaimAPI();

    }
    const goBack = () => {
      const main = document.getElementById("main-id");
      if (main) {
        main.classList.toggle("nodata");
      }
    }

    const claimAttachment = this.state.attachmentState.claimAttachment;
    if (!getSession(CLIENT_ID)) {
      return <Redirect to={{
        pathname: '/home'
      }} />;
    }
    return (
      <div>
        <Helmet>
          <title>Danh sách yêu cầu cần bổ sung  – Dai-ichi Life Việt Nam</title>
          <meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
          <meta name="robots" content="noindex, nofollow" />
          <meta property="og:type" content="website" />
          <meta property="og:url" content={FE_BASE_URL + "/info-required-claim"} />
          <meta property="og:title" content="Danh sách yêu cầu cần bổ sung  - Dai-ichi Life Việt Nam" />
          <meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
          <meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
          <link rel="canonical" href={FE_BASE_URL + "/info-required-claim"} />
        </Helmet>
        {this.state.nodata === true ? (
          <main className="logined nodata nodata2">
            <div className="main-warpper basic-mainflex">
              <section className="sccontract-warpper no-data nodata2-fullwidth">
                <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                  <div className="breadcrums__item">
                    <p>Theo dõi yêu cầu</p>
                    <p className='breadcrums__item_arrow'>></p>
                  </div>
                  <div className="breadcrums__item">
                    <p>Giải quyết quyền lợi</p>
                    <p className='breadcrums__item_arrow'>></p>
                  </div>
                </div>
                <div className="sccontract-container">
                  <div className="insurance">
                    <div className="empty">
                      <div className="icon">
                        <img src="img/2.2-nodata-image.png" alt="" />
                      </div>
                      <h4>Hiện tại không có yêu cầu nào đang xử lý</h4>
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

                </div>
                <LoadingIndicator area="claim-li-list" />
                {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                  (!!item.lstClaimSubmission && item.lstClaimSubmission[0] !== null
                    && item.lstClaimSubmission[0] !== ''
                    && item.lstClaimSubmission[0] !== undefined) &&
                  <div className="card-warpper">
                    <div className="item" onClick={() => clickOnCard(item, index)}>

                      <div className="card" id={"card" + index}>
                        <div className="card__header">
                          <div className="calendar-wrapper">
                            <img src="img/icon/Calendar.svg" alt="" />
                            <p>{(item.lstClaimSubmission[0] !== null
                                && item.lstClaimSubmission[0] !== ''
                                && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].DateSubmission : ''}</p>
                          </div>

                          <div className="dcstatus">
                            <p><br /></p>
                            <p className={['WFHOLD', 'DECLINED', 'WAITHC'].includes(item.Status) ? "wfhold" : (['APPROVAL', 'SUBMITTED', 'APPROVED'].includes(item.Status))
                                ? "claimStatus": ((item.Status === "WAITWF")?"waitwf": "cancel")}>
                              {item.StatusVN}
                            </p>
                          </div>


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
                            <p style={{ textAlign: 'right' }}>{(item.lstClaimSubmission[0] !== null
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
                          {(item.lstClaimSubmission[0] !== null
                            && item.lstClaimSubmission[0] !== ''
                            && item.lstClaimSubmission[0] !== undefined) && item.Status === "WFHOLD" && (
                              <div className="card__footer-item">
                                <div className="dropdown" id={"dropdownContent" + index}>
                                  <div className="dropdown__content" onClick={() => dropdownContent(index)}>
                                    <p className="primary-text basic-semibold">Xem chi tiết cần bổ sung</p>
                                    <p className="alternative-text basic-semibold">Thu gọn</p>
                                    <div className="arrow">
                                      <img src="img/icon/arrow-down-bronw.svg" alt="" />
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

                    </div>
                  </div>
                )
                )}

                <div className="other_option" id="other-option-toggle" onClick={() => goBack()}>
                  <p>Tiếp tục</p>
                  <i><img src="img/icon/arrow-left.svg" alt="" /></i>
                </div>
              </section>
              <section className="sccontract-warpper">
                {this.state.isEmpty || this.state.isWAITHC === true
                  || this.state.isCompleted === false ? (
                  <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                    <div className="breadcrums__item">
                      <p>Theo dõi yêu cầu</p>
                      <p className='breadcrums__item_arrow'>></p>
                    </div>
                    <div className="breadcrums__item">
                      <p>Giải quyết quyền lợi</p>
                      <p className='breadcrums__item_arrow'>></p>
                    </div>
                  </div>) : (
                  <div className="breadcrums" style={{ backgroundColor: '#f1f1f1' }}>
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
                  <i><img src="img/icon/return_option.svg" alt="" /></i>
                </div>
                {this.state.isEmpty ? (
                  <div className="sccontract-container">
                    <div className="insurance">
                      <div className="empty">
                        <div className="icon">
                          <img src="img/icon/empty.svg" alt="" />
                        </div>
                        <p style={{ paddingTop: '20px' }}>Bạn hãy chọn thông tin ở phía bên trái nhé!</p>
                      </div>
                    </div>
                  </div>
                ) : (

                  <div className="sccontract-container">
                    {this.state.isCompleted === false ? (
                      <div>
                        <div className="warning-wrapper">
                          <div className="icon-wrapper">
                            <img src="img/icon/2.2/2.2-icon-signal.svg" alt="" />
                          </div>
                          <div className="content">
                            <p>
                              Vui lòng bổ sung Giấy tờ/Chứng từ còn thiếu/chưa hợp lệ trước ngày
                              <span className="light-brown-text"> {this.state.claimList.lstClaimSubmission[0].lstDocTypeHold[0]?this.state.claimList.lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate:''}</span> để chúng tôi thẩm định yêu cầu quyền lợi này và phản hồi kết quả đến Quý khách.
                            </p>
                          </div>
                        </div>

                        <div className="stepform form-wrapper">

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
                                        <div className="img-upload-wrapper not-empty">
                                          {claimAttachment[index4].attachmentList.map((att, attIdx) => (
                                            <div className="img-upload-item" key={attIdx}>
                                              <div className="file">
                                                <div className="img-wrapper">
                                                  <img src={att.imgData} alt="" />
                                                </div>
                                                <div className="deletebtn" onClick={() => this.handlerDeleteAttachment(index4, attIdx)}>X</div>
                                              </div>
                                            </div>
                                          ))}
                                          <div className="img-upload-item">
                                            <div className="img-upload" id={"img-upload-claimAttachment_" + index4}
                                              onClick={() => { document.getElementById("claimAttInput_" + index4).click() }}
                                              onDragOver={(event) => this.handlerDragOver("img-upload-claimAttachment_" + index4, event)}
                                              onDragLeave={(event) => this.handlerDragLeave("img-upload-claimAttachment_" + index4, event)}
                                              onDrop={(event) => this.handlerDrop(index4, event, this.handlerUpdateAttachmentList)}>
                                              <button className="circle-plus">
                                                <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                                              </button>
                                              <p className="basic-grey">
                                                Kéo & thả tệp tin hoặc
                                                <span className="basic-red basic-semibold">chọn tệp</span>
                                              </p>
                                              <input id={"claimAttInput_" + index4} className="inputfile"
                                                type="file" multiple={true} hidden={true} accept="image/*"
                                                onChange={(e) => this.handlerUploadAttachment(index4, e, this.handlerUpdateAttachmentList)} />
                                            </div>
                                          </div>
                                        </div> :
                                        <div className="img-upload-wrapper">
                                          <div className="img-upload-item">
                                            <div className="img-upload" id={"img-upload-empty-" + index4}
                                              onClick={() => { document.getElementById("claimAttEmptyInput_" + index4).click() }}
                                              onDragOver={(event) => this.handlerDragOver("img-upload-empty-" + index4, event)}
                                              onDragLeave={(event) => this.handlerDragLeave("img-upload-empty-" + index4, event)}
                                              onDrop={(event) => this.handlerDrop(index4, event, this.handlerUpdateAttachmentList)}>
                                              <button className="circle-plus">
                                                <img src="img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                                              </button>
                                              <p className="basic-grey">
                                                Kéo & thả tệp tin hoặc&nbsp;
                                                <span className="basic-red basic-semibold">chọn tệp</span>
                                              </p>
                                              <input id={"claimAttEmptyInput_" + index4} className="inputfile"
                                                type="file" multiple={true} hidden={true} accept="image/*"
                                                onChange={(e) => this.handlerUploadAttachment(index4, e, this.handlerUpdateAttachmentList)} />
                                            </div>
                                          </div>
                                        </div>
                                      }
                                    </div>
                                  </div>
                                  {/* TODO ifelse comment area */}
                                  {/* Comment area */}
                                  <div className="item">
                                    <h5 className="item__title">* Phản hồi từ Dai-ichi Việt Nam</h5>
                                    <h5 className="item__title">{holdData.ClaimAdminComment}</h5>
                                    {/* User response */}
                                    <div className="item__content">
                                      <div className="tab">
                                        <div className="tab__content">
                                          <div className="input textarea">
                                            <div className="input__content">
                                              <label>Câu trả lời</label>
                                              <textarea className='eclaim-text-area'
                                                name={"comment" + index4}
                                                onChange={(event) => this.handlerChangeInputComment(index4, event)}
                                                placeholder="Ví dụ: Mất chứng từ ghi nhận sự kiện bảo hiểm"
                                                rows="4"
                                              ></textarea>
                                            </div>
                                            <i><img src="img/icon/edit.svg" alt="" /></i>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            )}

                            <img className="decor-clip" src="img/mock.svg" alt="" />
                            <img className="decor-person" src="img/person.png" alt="" />
                          </div>

                        </div>

                        {/*<div className="bottom-text">
                          <p>Bạn có thắc mắc? <span>Liên hệ tư vấn</span></p>
                                    </div>*/}

                        <div className="bottom-btn" >
                          {this.state.attachmentState.disabledButton ?
                            <button className="btn btn-primary disabled">Gửi bổ sung</button> :
                            <button className="btn btn-primary" onClick={() => submitClaimHold()}>Gửi bổ sung</button>}
                        </div>
                      </div>

                    ) : (
                      <div className="insurance">
                        {this.state.isWAITHC === true ? (
                          <div className="empty">
                            <div className="icon">
                              <img src="img/2.2-nodata-image.png" alt="no-data" />
                            </div>
                            <h4 className="basic-semibold">Yêu cầu chờ bổ sung chứng từ gốc</h4>
                            <span>Vui lòng gửi chứng từ gốc đến văn phòng Dai-ichi Life Việt Nam</span>
                            <span style={{ paddingTop: '4px' }}>Thông tin sẽ được cập nhật đến bạn sớm nhất</span>
                          </div>
                        ) : (
                          <div className="empty">
                            <div className="icon">
                              <img src="img/2.2-nodata-image.png" alt="no-data" />
                            </div>
                            <h4 className="basic-semibold">Yêu cầu đang được xử lý</h4>
                            <span>Dai-ichi life Việt Nam sẽ cập nhật thông tin chi tiết sớm nhất</span>
                          </div>
                        )}
                      </div>
                    )}
                  </div>
                )}
              </section>
            </div>
            {this.state.nextScreen === true && (
              <div className="popup envelop show" id="popup">
                <div className="popup__card envelop-infomation">
                  <button className="envelop-infomation__close-button" onClick={() => closePopup()}>
                    <img src="img/icon/close-icon.svg" alt="" />
                  </button>
                  <div className="envelop-infomation__content">
                    <h5 className="basic-bold">gửi yêu cầu thành công</h5>

                    <p>Chúng tôi sẽ xử lý và thông báo đến quý khách tình trạng cập nhật trong thời gian sớm nhất</p>
                  </div>
                </div>
                <div className="popupbg"></div>
              </div>
            )}
          </main>

        )}
      </div>
    );
  }
}


export default AdditionalClaim;
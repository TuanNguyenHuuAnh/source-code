import 'antd/dist/antd.min.css';
import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, AUTHENTICATION, COMPANY_KEY, USER_DEVICE_TOKEN, ZALO_LOGINED, CLAIM_ID, CLAIM_TYPE } from '../../constants';
import { showMessage, isInteger, setSession, getSession, getDeviceId } from '../../util/common';
import { CPGetClientProfileByCLIID, logoutSession, CPSubmitForm, postClaimImage, getClaimCheckhold, checkAuthZalo } from '../../util/APIUtils';
import LoadingIndicator from '../../../src/common/LoadingIndicator2';

class AdditionalClaimPublic extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Action: 'ListClaimHold',
          Project: 'mcp',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          UserLogin: '',
          ClientID: '',
          APIToken: ''
        }
      },
      jsonInput2: {
        jsonDataInput: {
          SubmissionID: '',
          APIToken: '',
          Action: 'CheckHold',
          Authentication: AUTHENTICATION,
          ClaimID: '',
          ClaimType: '',
          DeviceId: getDeviceId(),
          Project: 'mcp',
          UserLogin: ''
        }
      },
      jsonInput3: {
        jsonDataInput: {
          Project: 'mcp',
          Company: COMPANY_KEY,
          NumberOfPage: '',
          APIToken: '',
          Authentication: AUTHENTICATION,
          ClaimID: '',
          ClaimType: 'Additional',
          ClientID: '',
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
          APIToken: '',
          Action: 'ClaimHold',
          Authentication: AUTHENTICATION,
          ClaimID: '',
          ClaimType: 'Additional',
          ClientID: '',
          DeviceId: getDeviceId(),
          Project: 'mcp',
          UserLogin: '',
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
      isWAITHC: false,
      password: '',
      token: '',
      ignored: false,
      isAdditional: false,
      errorMessage: '',
      userLogin: '',
      loading: false

    }
    this.handlerUpdateMainState = this.updateMainState.bind(this);
    this.handlerUploadAttachment = this.uploadAttachment.bind(this);
    this.handlerDragOver = this.dragFileOver.bind(this);
    this.handlerDragLeave = this.dragFileLeave.bind(this);
    this.handlerDrop = this.dropFile.bind(this);
    this.handlerUpdateAttachmentList = this.updateAttachmentList.bind(this);
    this.handlerDeleteAttachment = this.deleteAttachment.bind(this);
    this.handlerChangeInputComment = this.onChangeInputComment.bind(this);
    this.handleInputPass = this.handleInputPass.bind(this);
    this.popupLaPassSubmit = this.popupLaPassSubmit.bind(this);

  }

  handleInputPass(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value.trim();

    this.setState({
      [inputName]: inputValue
    });
    if (inputValue.length === 6) {
      document.getElementById('btn-la-pass').className = "btn btn-primary";
    } else {
      document.getElementById('btn-la-pass').className = "btn btn-primary disabled";
    }
  }
  popupLaPassSubmit(event) {
    event.preventDefault();
    if (document.getElementById('btn-la-pass').className === "btn btn-primary disabled") {
      return;
    }
    if (!this.props.match.params.id) {
      return;
    }
    let loginRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'CheckAuthenZalo',
        APIToken: '',
        Authentication: AUTHENTICATION,
        DeviceId: getDeviceId(),
        DeviceToken: getSession(USER_DEVICE_TOKEN) ? getSession(USER_DEVICE_TOKEN) : '',
        UserLogin: '0000000001',
        OS: '',
        Project: 'mcp',
        Password: '000000000',
        AuthenID: this.props.match.params.id,
        AuthenDOB: this.state.password && isInteger(this.state.password) ? parseInt(this.state.password) + '' : this.state.password
      }
    };
    checkAuthZalo(loginRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'Successful') {
          const myArray = response.Response.Message.split("@");
          //setSession(USER_LOGIN, response.Response.Message);
          //setSession(CLIENT_ID, response.Response.Message);
          const client_id = (myArray.length > 0 && myArray[0] !== undefined) ? myArray[0] : "";
          const claim_id = (myArray.length > 0 && myArray[2] !== undefined) ? myArray[2] : "";

          // setSession(USER_LOGIN, client_id);
          // setSession(CLIENT_ID, client_id);
          // setSession(CLAIM_ID, claim_id);
          // setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
          // setSession(ZALO_LOGINED, ZALO_LOGINED);

          var jsonState = this.state;
          jsonState.token = response.Response.NewAPIToken;
          jsonState.ignored = true;
          jsonState.userLogin = client_id;//response.Response.Message;
          const apiRequest = {
            jsonDataInput: {
              Action: 'ListClaimHold',
              Project: 'mcp',
              Authentication: AUTHENTICATION,
              DeviceId: getDeviceId(),
              UserLogin: client_id,//response.Response.Message,
              ClientID: client_id,//response.Response.Message,
              ClaimID: claim_id,
              APIToken: response.Response.NewAPIToken
            }
          }
          //console.log("checkAuthZalo_" + JSON.stringify(apiRequest));
          CPGetClientProfileByCLIID(apiRequest).then(Res => {
            //console.log(Res);
            let Response1 = Res.Response;
            //console.log("CPGetClientProfileByCLIID_" + Response1.ErrLog);
            if (Response1.ErrLog === 'SUCCESSFUL' && Response1.ClientProfile !== null) {
              jsonState.jsonResponse = Response1;
              jsonState.clientProfile = Response1.ClientProfile;
              jsonState.ignored = false;
              jsonState.nodata = false;
              this.setState(jsonState);
              this.clickOnCard(Response1.ClientProfile[0], 0);
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
              jsonState.ignored = false;
              this.setState(jsonState);
            }
            // this.setState(jsonState);
          }).catch(error => {
            // alert(error);
            //this.props.history.push('/maintainence');
          });

          this.setState(jsonState);
        } else if (response.Response.ErrLog === 'WRONGPASSEXCEED') {
          this.setState({ errorMessage: 'Mật khẩu không chính xác. Quý khách đã nhập sai thông tin quá 5 lần. Vui lòng thử lại sau 30 phút.' });
        } else if (response.Response.ErrLog === 'ACCOUNTINVALID') {
          this.setState({ errorMessage: 'Mật khẩu không chính xác. Vui lòng nhập lại.' });
        }

      }).catch(error => {
        // alert(error);
      });


    // this.setState({ token: getSession(ACCESS_TOKEN) });
  }

  onChangeInputComment(index4, event) {
    const target = event.target;
    const inputValue = target.value.trim();
    //console.log(inputValue);
    var jsonState = this.state;
    if (inputValue === "")
      jsonState.attachmentState.disabledButton = true;
    else
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
  updateState(jsonState, index) {
    jsonState.attachmentState.disabledButton = !(jsonState.attachmentState.claimAttachment[index].attachmentList && (jsonState.attachmentState.claimAttachment[index].attachmentList.length > 0));
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
        this.updateState(jsonState, index4);
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
        this.updateState(jsonState, index4);
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
      this.updateState(jsonState, index4);
    }
    event.target.value = null;
  }
  deleteAttachment(index4, attachmentIndex) {
    var jsonState = this.state;
    var attachments = jsonState.attachmentState.claimAttachment[index4].attachmentList;
    attachments.splice(attachmentIndex, 1);
    jsonState.attachmentState.claimAttachment[index4].attachmentList = attachments;
    this.updateState(jsonState, index4);
  }
  clickOnCard = (item, index) => {
    let jsonState = this.state;
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
      this.callCheckHoldAPI(item);
    }
    this.setState(jsonState);

    //console.log(jsonState.claimList);
  }

  callCheckHoldAPI = (item) => {
    const jsonState = this.state;
    let claimID = (item.lstClaimSubmission[0].ClaimID === '' ? item.lstClaimSubmission[0].SubmissionID : item.lstClaimSubmission[0].ClaimID);
    let claimType = item.lstClaimSubmission[0].ClaimType;
    let submissionID = item.lstClaimSubmission[0].SubmissionID;
    jsonState.jsonInput4.jsonDataInput.DocTypeComments = [];
    jsonState.attachmentState.claimAttachment = [];
    jsonState.checkHoldList = null;
    // const apiRequest = Object.assign({}, this.state.jsonInput2);
    const apiRequest = {
      jsonDataInput: {
        SubmissionID: submissionID,
        APIToken: this.state.token,
        Action: 'CheckHold',
        Authentication: AUTHENTICATION,
        ClaimID: claimID,
        ClaimType: claimType,
        DeviceId: getDeviceId(),
        Project: 'mcp',
        UserLogin: this.state.userLogin
      }
    }
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

  render() {
    var callHistoryClaimAPI = () => {
      var jsonState = this.state;
      jsonState.clientProfile = null;
      // const apiRequest = Object.assign({}, this.state.jsonInput);
      const apiRequest = {
        jsonDataInput: {
          Action: 'ListClaimHold',
          Project: 'mcp',
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          UserLogin: jsonState.userLogin,
          ClientID: jsonState.userLogin,
          APIToken: jsonState.token
        }
      }
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

    const showHidePass = () => {
      var x = document.getElementById("la-passsword");
      if (x.type === "password") {
        x.type = "text";
        document.getElementById('la-password-input').className = "input special-input-extend password-input show";
      } else {
        x.type = "password";
        document.getElementById('la-password-input').className = "input special-input-extend password-input";
      }

    }

    const closeLaPass = () => {
      this.setState({ ignored: true });
    }
    const closePopup = () => {
      const jsonState = this.state;
      document.getElementById('popup').className = "popup envelop";
      jsonState.nextScreen = false;
      jsonState.isCompleted = true;
      this.setState(jsonState);
      callHistoryClaimAPI();


    }
    var submitClaimHold = () => {
      const jsonState = this.state;
      jsonState.loading = true;
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
      jsonState.jsonInput4.jsonDataInput.UserLogin = jsonState.userLogin;
      jsonState.jsonInput4.jsonDataInput.ClientID = jsonState.userLogin;
      jsonState.jsonInput4.jsonDataInput.APIToken = jsonState.token;
      jsonState.jsonInput4.jsonDataInput.DeviceId = getDeviceId();
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
              jsonState.jsonInput3.jsonDataInput.ClientID = jsonState.userLogin;
              jsonState.jsonInput3.jsonDataInput.APIToken = jsonState.token;
              jsonState.jsonInput3.jsonDataInput.DeviceId = getDeviceId();
              this.setState(jsonState);
              const apiRequest = Object.assign({}, jsonState.jsonInput3);
              postClaimImage(apiRequest);
            }
          }
          jsonState.nextScreen = true;
          jsonState.loading = false;
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
        this.setState({ loading: false });
      });

    }

    const additionalDocuments = (item, index) => {
      this.clickOnCard(item, index);
      this.setState({ isAdditional: true });
    }

    const clearClaimSelected = () => {
      let jsonState = this.state;
      jsonState.isCompleted = false;
      jsonState.isWAITHC = false;
      jsonState.claimList = null;

      jsonState.claimID = '';
      jsonState.claimType = '';
      jsonState.SubmissionID = '';
      jsonState.isAdditional = false;
      this.setState(jsonState);

    }
    const claimAttachment = this.state.attachmentState.claimAttachment;
    return (
      <div>
        {this.state.ignored ? (
          <main className="logined">
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
                        <img src="../../../img/empty-state.svg" alt="" />
                      </div>
                      <h4 className="basic-semibold" style={{ textAlign: 'center' }}>Để xem chứng từ cần bổ sung, <br /> Quý khách vui lòng bấm vào thông báo <br /> trong tin nhắn đã nhận được.</h4>
                    </div>
                  </div>
                </div>
              </section>
            </div>
          </main>
        ) : (
          this.state.token ? (
            this.state.nodata === true ? (
              <main className="logined">
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
                            <img src="../../../img/2.2-nodata-image.png" alt="" />
                          </div>
                          <h4 className="basic-semibold" style={{ textAlign: 'center' }}>Yêu cầu bổ sung chứng từ này đã <br /> hoàn thành hoặc hết hạn bổ sung. <br /> Vui lòng liên hệ tổng đài Dai-ichi Life <br /> Việt Nam để biết thêm chi tiết</h4>
                        </div>
                      </div>
                    </div>
                  </section>
                </div>
              </main>
            ) : (
              <main className="logined nodata">
                <div className="main-warpper basic-mainflex">
                  {this.state.isAdditional ? (
                    <section className="sccontract-warpper-la">
                      <div className="breadcrums" style={{ backgroundColor: '#ffffff' }}>
                        <div className="breadcrums__item">
                          <p onClick={() => clearClaimSelected()} style={{ cursor: 'pointer' }}>Yêu cầu quyền lợi</p>
                          <p className='breadcrums__item_arrow'>></p>
                        </div>
                        <div className="breadcrums__item">
                          <p>Danh sách yêu cầu cần bổ sung</p>
                          <p className='breadcrums__item_arrow'>></p>
                        </div>
                      </div>
                      <div className="sccontract-container">
                        {this.state.isCompleted === false ? (
                          <div>
                            <div className="warning-wrapper">
                              <div className="icon-wrapper">
                                <img src="../../../img/icon/2.2/2.2-icon-signal.svg" alt="" />
                              </div>
                              <div className="content">
                                <p>
                                  Vui lòng bổ sung Giấy tờ/Chứng từ còn thiếu/chưa hợp lệ trước ngày
                                  <span className="light-brown-text"> {this.state.claimList && this.state.claimList.lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate}</span> để chúng tôi thẩm định yêu cầu quyền lợi này và phản hồi kết quả đến Quý khách.
                                </p>
                              </div>
                            </div>
                            <div className="stepform-la form-wrapper">

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
                                                  <button className="circle-plus" style={{ padding: '0' }}>
                                                    <img style={{ width: '24px', height: '24px' }} src="../../../img/icon/zl_plug.svg" alt="circle-plus" />
                                                  </button>
                                                  <p className="basic-grey">
                                                    Bấm để
                                                    <span className="basic-red basic-semibold">chọn hình ảnh</span>
                                                  </p>
                                                  {/* <p className='basic-textsmall-padding-bottom'>Hỗ trợ định dạng hình ảnh JPGE, JPG, PNG</p> */}
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
                                                  <button className="circle-plus" style={{ padding: '0' }}>
                                                    <img style={{ width: '24px', height: '24px' }} src="../../../img/icon/zl_plug.svg" alt="circle-plus" />
                                                  </button>
                                                  <p className="basic-grey">
                                                    Bấm để&nbsp;
                                                    <span className="basic-red basic-semibold">chọn hình ảnh</span>
                                                  </p>
                                                  {/* <p className='basic-textsmall-padding-bottom'>Hỗ trợ định dạng hình ảnh JPGE, JPG, PNG</p> */}
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
                                                <i><img src="../../../img/icon/edit.svg" alt="" /></i>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                )}

                                <img className="decor-clip" src="../../../img/mock.svg" alt="" />
                                <img className="decor-person" src="../../../img/person.png" alt="" />
                              </div>

                            </div>

                            {/*<div className="bottom-text"><p>Bạn có thắc mắc? <span>Liên hệ tư vấn</span></p></div>*/}
                            <LoadingIndicator area="claim-li-list" />
                            <div className="bottom-btn-la" >
                              {(this.state.attachmentState.disabledButton || this.state.loading) ?
                                <button className="btn btn-primary disabled diplay-init">Gửi bổ sung</button> :
                                <button className="btn btn-primary diplay-init" onClick={() => submitClaimHold()}>Gửi bổ sung</button>}
                            </div>
                          </div>

                        ) : (
                          <div className="insurance">
                            {this.state.isWAITHC === true ? (
                              <div className="empty">
                                <div className="icon">
                                  <img src="../../../img/2.2-nodata-image.png" alt="no-data" />
                                </div>
                                <h4 className="basic-semibold">Yêu cầu chờ bổ sung chứng từ gốc</h4>
                                <span>Vui lòng gửi chứng từ gốc đến văn phòng Dai-ichi Life Việt Nam</span>
                                <span style={{ paddingTop: '4px' }}>Thông tin sẽ được cập nhật đến bạn sớm nhất</span>
                              </div>
                            ) : (
                              <div className="empty">
                                <div className="icon">
                                  <img src="../../../img/2.2-nodata-image.png" alt="no-data" />
                                </div>
                                <h4 className="basic-semibold">Yêu cầu đang được xử lý</h4>
                                <span>Dai-ichi life Việt Nam sẽ cập nhật thông tin chi tiết sớm nhất</span>
                              </div>
                            )}
                          </div>
                        )}
                      </div>
                    </section>
                  ) : (
                    <section className="sccard-warpper-la hopdong-sccard-wrapper">
                      <div className="title">
                        <h4 className="basic-bold" style={{ paddingLeft: '10px' }}>Xem chứng từ cần bổ sung</h4>

                      </div>
                      <LoadingIndicator area="claim-li-list" />
                      {this.state.clientProfile !== null && this.state.clientProfile.map((item, index) => (
                        (!!item.lstClaimSubmission && item.lstClaimSubmission[0] !== null
                          && item.lstClaimSubmission[0] !== ''
                          && item.lstClaimSubmission[0] !== undefined) &&
                        <div className="card-warpper">
                          <div className="item">

                            <div className="card" id={"card" + index} style={{ cursor: 'default' }}>
                              <div className="card__header">
                                <div className="calendar-wrapper">
                                  <img src="../../../img/icon/Calendar.svg" alt="" />
                                  <p>{(item.lstClaimSubmission[0] !== null
                                      && item.lstClaimSubmission[0] !== ''
                                      && item.lstClaimSubmission[0] !== undefined) ? item.lstClaimSubmission[0].DateSubmission : ''}</p>
                                </div>
                                {['WFHOLD', 'DECLINED', 'WAITHC'].includes(item.Status) ? (
                                  <div>
                                    {/*<div className="choose">
                                    <div className="dot" ></div>
                                  </div>*/}
                                    <div className="dcstatus">
                                      <p><br /></p>
                                      <p className="wfhold" >{item.StatusVN}</p>
                                    </div>
                                  </div>
                                ) : (
                                  <div className="dcstatus">
                                    {(item.Status === "TEMPSAVED" || item.Status === "CANCEL") &&
                                      <p className="cancel">{item.StatusVN}</p>}
                                    {(item.Status === "WAITWF") &&
                                      <p className="waitwf" style={{ fontFamily: 'Inter', fontSize: '14px', fontWeight: '600' }}>{item.StatusVN}</p>}
                                    {(['APPROVAL', 'SUBMITTED', 'APPROVED'].includes(item.Status)) &&
                                      <p className="claimStatus" style={{ fontFamily: 'Inter', fontSize: '14px', fontWeight: '600' }}>{item.StatusVN}</p>}
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
                                <div className='dash-line'></div>
                                {(item.lstClaimSubmission[0] !== null
                                  && item.lstClaimSubmission[0] !== ''
                                  && item.lstClaimSubmission[0] !== undefined) && (
                                    <>
                                      <div className="card__footer-item">
                                        <div className="dropdown show" id={"dropdownContent" + index}>
                                          <div className="dropdown__content" style={{ cursor: 'default' }}>
                                            <p className="alternative-text basic-semibold">Chi tiết chứng từ cần bổ sung</p>
                                          </div>

                                          {(item.lstClaimSubmission[0] !== null
                                            && item.lstClaimSubmission[0] !== ''
                                            && item.lstClaimSubmission[0] !== undefined) && item.lstClaimSubmission[0].lstDocTypeHold.map((desc, index2) => (
                                              <div className="dropdown__items">
                                                <div className="content-wrapper" style={{ cursor: 'default' }}>
                                                  <div className="item">
                                                    {desc.DocTypeName && ((desc.DocTypeName === 'Chứng từ khác')) ? (
                                                      <div style={{ width: '5px', height: '7px', marginRight: '6px', fontWeight: '700' }}></div>
                                                    ) : (
                                                      (desc.DocTypeName !== 'Câu hỏi mở rộng') &&
                                                      <div className="list-style"></div>
                                                    )}

                                                    <div className="content">

                                                      {desc.DocTypeName && ((desc.DocTypeName === 'Chứng từ khác')) ? (
                                                        <p style={{ fontWeight: '700' }}>Câu hỏi mở rộng</p>
                                                      ) : (
                                                        (desc.DocTypeName !== 'Câu hỏi mở rộng') &&
                                                        <p>{desc.DocTypeName}</p>
                                                      )}
                                                    </div>

                                                  </div>
                                                  {desc.DocTypeName && ((desc.DocTypeName === 'Câu hỏi mở rộng') || (desc.DocTypeName === 'Chứng từ khác')) &&

                                                    <div className="item" style={{ marginTop: '-10px' }}>
                                                      <div className="list-style" style={{ marginRight: '-6px' }}></div>
                                                      <p style={{ lineHeight: '19px', size: '14px' }}>{desc.ClaimAdminComment}</p>
                                                    </div>

                                                  }
                                                </div>
                                              </div>
                                            ))}
                                        </div>
                                      </div>

                                    </>
                                  )}
                              </div>
                              {this.state.clientProfile &&
                                <div className="card__footer-item">
                                  <div style={{ background: '#F5F3F2', height: '43px', width: '100%', alignItems: 'center', paddingTop: '12px', marginBottom: '-8px', paddingLeft: '12px' }}>
                                    <p>
                                      Vui lòng bổ sung chứng từ trước ngày<span className="basic-red"> {this.state.clientProfile[0].lstClaimSubmission[0].lstDocTypeHold[0].ExpiredDate}</span>
                                    </p>
                                  </div>
                                </div>
                              }

                            </div>

                          </div>
                          <div className="bottom-btn-la" onClick={() => additionalDocuments(item, index)}>
                            {item.Status === "WFHOLD" &&
                              <button className="btn btn-primary diplay-init">Bổ sung ngay</button>
                            }
                          </div>
                        </div>
                      )
                      )}





                    </section>
                  )}



                </div>
                {this.state.nextScreen === true && (
                  <div className="popup envelop show" id="popup">
                    <div className="popup__card envelop-infomation">
                      <button className="envelop-infomation__close-button" onClick={() => closePopup()}>
                        <img src="../../../img/icon/close-icon.svg" alt="" />
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
            )
          ) : (
            <div className="popup special new-password-popup show" id="claim-la-popup">
              <form onSubmit={this.popupLaPassSubmit}>
                <div className="popup__card">
                  <div className="new-password-card" style={{ maxWidth: '360px' }}>
                    <div className="header">
                      <h4>Yêu cầu mật khẩu</h4>
                      <i className="closebtn"><img src="../../../img/icon/close.svg" alt="" onClick={() => closeLaPass()} /></i>
                    </div>
                    <div className="body">
                      {(this.state.errorMessage.length > 0) &&
                        <div className="error-message validate">
                          <i className="icon">
                            <img src="../../../img/icon/warning_sign.svg" alt="" />
                          </i>
                          <p style={{ 'lineHeight': '20px' }}>{this.state.errorMessage}</p>
                        </div>
                      }
                      <h5 className='basic-text2 bigheight' style={{ textAlign: 'left' }}>Để xem chứng từ cần bổ sung, Quý khách vui lòng nhập Mật khẩu với 6 ký tự của Tháng và năm sinh Người được bảo hiểm.</h5>
                      <div className="input-wrapper">
                        <div className="input-wrapper-item" style={{ paddingTop: '0px' }}>
                          <div className="input special-input-extend password-input" id="la-password-input">
                            <div className="input__content">
                              <label htmlFor="">Mật khẩu (6 ký tự)</label>
                              <input type="password" name="password" id="la-passsword" required placeholder='MMYYYY' onChange={this.handleInputPass} maxLength='6' style={{ color: '#727272' }} />
                            </div>
                            <i className="password-toggle" onClick={() => showHidePass()}></i>
                          </div>
                        </div>
                        <div className="input-wrapper-item">
                          <p>
                            Ví dụ: Quý khách có Tháng và năm sinh là 02/1999 <br /> Mật khẩu sẽ là: 021999
                          </p>
                        </div>
                      </div>
                      <div className="btn-wrapper">
                        <button className="btn btn-primary disabled" id="btn-la-pass" style={{ fontWeight: '600' }}>Xác nhận</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="popupbg"></div>
              </form>
            </div>
          )

        )}

      </div>
    );
  }
}


export default AdditionalClaimPublic;
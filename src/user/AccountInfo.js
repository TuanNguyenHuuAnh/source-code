import React, {Component} from 'react';
import moment from 'moment'
import {
  ACCESS_TOKEN,
  ADDRESS,
  API_BASE_URL,
  AUTHENTICATION,
  AVATAR_USER,
  CELL_PHONE,
  CLIENT_ID,
  CLIENT_PROFILE,
  COMPANY_KEY,
  DOB,
  EMAIL,
  EXPIRED_MESSAGE,
  FORGOT_PASSWORD,
  FULL_NAME,
  GENDER,
  HAVE_HC_CARD,
  LINK_FB,
  LINK_GMAIL,
  OTP_EXPIRED,
  OTP_INCORRECT,
  TWOFA,
  USER_LOGIN,
  VERIFY_CELL_PHONE,
  VERIFY_EMAIL
} from '../constants';
import './AccountInfo.css';
import {
  checkAvatar,
  checkPass,
  genOTP,
  getHCCard,
  logoutSession,
  switchTwoFA,
  uploadImage,
  verifyOTP
} from '../util/APIUtils';
import {formatDate, getDeviceId, getSession, maskEmail, maskPhone, setSession, showMessage} from '../util/common';
import {formatDateString} from '../SDK/sdkCommon';
import LoadingIndicator from '../common/LoadingIndicator2';
import AccountUpdateForm from './AccountUpdateForm';
import {Link} from "react-router-dom";
import '../common/Common.css';
import DOTPInput from '../components/DOTPInput';
import AlertPopupInvalidUser from '../components/AlertPopupInvalidUser';
import ChangeUserNameAndPassword from './ChangeUserNameAndPassword';

class AccountInfoNoCard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      checkPass: false,
      checkOtp: false,
      checkOtpChangePass: false,
      checkOtpEmail: false,
      checkOtpEmailChangePass: false,
      twoFA: (getSession(TWOFA) && (getSession(TWOFA) !== 'undefined')) ? getSession(TWOFA) : '0',
      verifyPhone: (getSession(VERIFY_CELL_PHONE) && (getSession(VERIFY_CELL_PHONE) !== 'undefined')) ? getSession(VERIFY_CELL_PHONE) : '0',
      verifyEmail: (getSession(VERIFY_EMAIL) && (getSession(VERIFY_EMAIL) !== 'undefined')) ? getSession(VERIFY_EMAIL) : '0',
      password: '',
      OTP: '',
      TransactionId: '',
      showNoPhone: false,
      showNoEmail: false,
      minutes: 0,
      seconds: 0,
      uploadSelected: false,
      selectedFile: '',
      avatarPath: null,
      haveHCCard: (getSession(HAVE_HC_CARD) && (getSession(HAVE_HC_CARD) === '1')) ? '1' : '0',
      showNoAnyAuthentication: false,
      proccesing: false,
      isdisplayChangeScreen: "",
      errorUpload: '',
      errorMessage: '',
      invalidTypeUser: false,
      ChangeUserNameAndPasswordScreen: false,
    };
    this.handleInputPassChange = this.handleInputPassChange.bind(this);
    this.popupCheckPassSubmit = this.popupCheckPassSubmit.bind(this);
    this.handleInputAccOtpChange = this.handleInputAccOtpChange.bind(this);
    this.popupAccOtpSubmit = this.popupAccOtpSubmit.bind(this);
    this.reGenOtp = this.reGenOtp.bind(this);

    this.handlerChangePotentialUserInfo = this.changePotentialUserInfo.bind(this);
    this.handlerCloseAccountUpdateForm = this.closeUpdateForm.bind(this);
    this.closeCheckPassEsc = this.closeCheckPassEsc.bind(this);
    this.handleUploadChange = this.handleUploadChange.bind(this);
    this.handDragFileOver = this.handDragFileOver.bind(this);
    this.handleDragFileLeave = this.handleDragFileLeave.bind(this);
    this.handleDropFile = this.handleDropFile.bind(this);
    this.handleUploadSubmit = this.handleUploadSubmit.bind(this);
  }

  //Reset status 2fa, verifyPhone after update Phone number
  resetTwoFAPhone = () => {
    this.setState({ twoFA: '0', verifyPhone: '0' });
    setSession(TWOFA, '0');
    setSession(VERIFY_CELL_PHONE, '0');
  }

  //Handle OTP start
  startTimer = () => {
    let myInterval = setInterval(() => {
      if (this.state.seconds > 0) {
        this.setState({ seconds: this.state.seconds - 1 });
      }
      else if (this.state.seconds === 0) {
        if (this.state.minutes === 0) {
          clearInterval(myInterval)
        } else {
          this.setState({ minutes: this.state.minutes - 1, seconds: 59 });
        }
      }
    }, 1000)
    return () => {
      clearInterval(myInterval);
    };

  }

  handleInputAccOtpChange(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value.trim();
    this.setState({
      [inputName]: inputValue
    });
    if (inputValue !== '') {
      document.getElementById('btn-verify-otp').className = "btn btn-primary";
    } else {
      document.getElementById('btn-verify-otp').className = "btn btn-primary disabled";
    }
  }
  popupAccOtpSubmit(OTP) {
    const verifyOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'CheckOTP',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),//this.state.POID
        DeviceId: getDeviceId(),
        Note: (this.state.checkOtp || this.state.checkOtpChangePass) ? 'VALID_OTP_PHONE' : 'VALID_OTP_EMAIL',
        OS: '',
        OTP: OTP,
        Project: 'mcp',
        TransactionID: this.state.TransactionId,
        UserLogin: getSession(USER_LOGIN)
      }

    }

    verifyOTP(verifyOTPRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
          if (this.state.checkOtp === true) {
            this.verifyPhone();
            this.setState({ checkOtp: false, minutes: 0, seconds: 0 });
          } else if (this.state.checkOtpEmail === true) {
            this.verifyEmail();
            this.setState({ checkOtpEmail: false, minutes: 0, seconds: 0 });
          } else if (this.state.checkOtpChangePass === true) {
            this.setState({ checkOtpChangePass: false, minutes: 0, seconds: 0 });
            document.getElementById('new-password-popup').className = "popup special new-password-popup show";
          } else if (this.state.checkOtpEmailChangePass === true) {
            this.setState({ checkOtpEmailChangePass: false, minutes: 0, seconds: 0 });
            document.getElementById('new-password-popup').className = "popup special new-password-popup show";
          }
        } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        } else if (response.Response.ErrLog === 'OTPEXPIRY') {
          this.setState({ errorMessage: OTP_EXPIRED });
        }
        else if (response.Response.ErrLog === 'OTPLOCK' || response.Response.ErrLog === 'OTP Wrong 3 times') {
          this.setState({ checkOtp: false, checkOtpEmail: false, checkOtpChangePass: false, checkOtpEmailChangePass: false, minutes: 0, seconds: 0 });
          document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        }
        else {
          this.setState({ errorMessage: OTP_INCORRECT });
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });
  }

  verifyPhone = () => {
    const verifyOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'VerifyCellPhone',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        CellPhone: getSession(CELL_PHONE),
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        DeviceToken: '',
        Note: 'VALID_OTP_PHONE',
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }

    verifyOTP(verifyOTPRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGESSUCC') {
          this.switchTwoFA();
        } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        } else {
          // document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });

  }

  verifyEmail = () => {
    const verifyOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'VerifyEmail',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        DeviceToken: '',
        Email: getSession(EMAIL),
        Note: 'VALID_OTP_EMAIL',
        OS: 'Samsung_SM-A125F-Android-11',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }

    verifyOTP(verifyOTPRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGESSUCC') {
          document.getElementById('popup-verify-successful').className = "popup option-popup show";
          this.setState({ verifyEmail: '1' });
          setSession(VERIFY_EMAIL, '1');
        } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        } else {
        }
      }).catch(error => {
      });

  }

  reGenOtp(event) {
    if (this.state.checkOtp || this.state.checkOtpChangePass) {
      this.genOtpPhone();
    } else {
      this.genOtpEmail();
    }
  }

  genOtpPhone = () => {
    const genOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'GenOTP',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        CellPhone: getSession(CELL_PHONE),
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Note: 'VALID_OTP_PHONE',
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }
    genOTP(genOTPRequest)
      .then(resGen => {
        if (resGen.Response.Result === 'true' && resGen.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ checkOtp: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
          this.startTimer();
          //this.setState({checkOtp: true});
        } else if (resGen.Response.NewAPIToken === 'invalidtoken' || resGen.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);

        } else if (resGen.Response.ErrLog === 'OTP Exceed') {
          document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
        } else if (resGen.Response.ErrLog === 'OTPLOCK' || resGen.Response.ErrLog === 'OTP Wrong 3 times') {
          document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        } else {
          document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }

      }).catch(error => {
        //this.props.history.push('/maintainence');
      });
  }

  genOtpPhoneChangePass = () => {
    const genOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'GenOTP',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        CellPhone: getSession(CELL_PHONE),
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Note: 'VALID_OTP_PHONE',
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }
    //console.log(genOTPRequest);
    genOTP(genOTPRequest)
      .then(resGen => {
        if (resGen.Response.Result === 'true' && resGen.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ checkOtpChangePass: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
          this.startTimer();
          //this.setState({checkOtp: true});
        } else if (resGen.Response.NewAPIToken === 'invalidtoken' || resGen.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);

        } else if (resGen.Response.ErrLog === 'OTP Exceed') {
          document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
        } else if (resGen.Response.ErrLog === 'OTPLOCK' || resGen.Response.ErrLog === 'OTP Wrong 3 times') {
          document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        } else {
          document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });
  }

  genOtpEmail = () => {
    const genOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'GenOTP',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Email: getSession(EMAIL),
        Note: 'VALID_OTP_EMAIL',
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }
    genOTP(genOTPRequest)
      .then(resGen => {
        if (resGen.Response.Result === 'true' && resGen.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ checkOtpEmail: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
          this.startTimer();
        } else if (resGen.Response.NewAPIToken === 'invalidtoken' || resGen.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
        } else if (resGen.Response.ErrLog === 'OTPLOCK' || resGen.Response.ErrLog === 'OTP Exceed') {
          document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
        } else if (resGen.Response.ErrLog === 'OTP Wrong 3 times') {
          document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        } else {
          document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }
      }).catch(error => {
      });
  }

  genOtpEmailChangePass = () => {
    const genOTPRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'GenOTP',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Email: getSession(EMAIL),
        Note: 'VALID_OTP_EMAIL',
        OS: '',
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN)
      }

    }
    //console.log(genOTPRequest);
    genOTP(genOTPRequest)
      .then(resGen => {
        if (resGen.Response.Result === 'true' && resGen.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ checkOtpEmailChangePass: true, TransactionId: resGen.Response.Message, minutes: 5, seconds: 0 });
          this.startTimer();
        } else if (resGen.Response.NewAPIToken === 'invalidtoken' || resGen.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);

        } else if (resGen.Response.ErrLog === 'OTPLOCK' || resGen.Response.ErrLog === 'OTP Exceed') {
          document.getElementById('option-popup-account-exceed-otp').className = "popup special point-error-popup show";
        } else if (resGen.Response.ErrLog === 'OTP Wrong 3 times') {
          document.getElementById('option-popup-account-exceed-otp-3-times').className = "popup special point-error-popup show";
        } else {
          document.getElementById("popup-exception").className = "popup special point-error-popup show";
        }
      }).catch(error => {
        //this.props.history.push('/maintainence');
      });
  }
  //Handle OTP end
  handleInputPassChange(event) {
    const target = event.target;
    const inputName = target.name;
    const inputValue = target.value.trim();

    this.setState({
      [inputName]: inputValue
    });
    if (inputValue !== '' && inputValue.length >= 8) {
      document.getElementById('btn-check-pass').className = "btn btn-primary";
    } else {
      document.getElementById('btn-check-pass').className = "btn btn-primary disabled";
    }
  }
  popupCheckPassSubmit(event) {
    event.preventDefault();
    if (document.getElementById('btn-check-pass').className === "btn btn-primary disabled") {
      return;
    }
    document.getElementById('btn-check-pass').className = "btn btn-primary disabled";
    const checkPassRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'CheckPassword',
        UserLogin: getSession(USER_LOGIN),
        ClientID: getSession(CLIENT_ID),
        Password: this.state.password,
        DeviceId: getDeviceId(),
        APIToken: getSession(ACCESS_TOKEN),
        OS: '',
        Project: 'mcp',
        Authentication: AUTHENTICATION,
      }

    }

    checkPass(checkPassRequest)
      .then(response => {
        document.getElementById('btn-check-pass').className = "btn btn-primary";
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'Login successfull') {
          //update 2fa 
          this.switchTwoFA();
          this.setState({ checkPass: false });
          //document.getElementById('popup-thanks').className = "popup option-popup show";
        } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
          showMessage(EXPIRED_MESSAGE);
          logoutSession();
          this.props.history.push({
            pathname: '/home',
            state: { authenticated: false, hideMain: false }

          })
        } else if (response.Response.ErrLog === 'WRONGPASS') {
          document.getElementById('check-pass-id').className = 'error-message validate';
        }
      }).catch(error => {
        document.getElementById('btn-check-pass').className = "btn btn-primary";
        this.props.history.push('/maintainence');
      });

  }

  componentDidMount() {
    if (this.state.avatarPath === null) {
      this.checkAvatarExist();
    }
    document.addEventListener("keydown", this.closeCheckPassEsc, false);
    if (!getSession(HAVE_HC_CARD)) {
      this.checkHaveHCCard();
    }
  }
  componentWillUnmount() {
    document.removeEventListener("keydown", this.closeCheckPassEsc, false);
  }
  closeCheckPassEsc = (event) => {
    if (event.keyCode === 27) {
      if (this.state.checkPass) {
        this.closeCheckPass();
      } else if (this.state.checkOtp || this.state.checkOtpChangePass || this.state.checkOtpEmail || this.state.checkOtpEmailChangePass) {
        this.closeAccOtpPopup();
      } else if (this.state.showNoPhone) {
        this.closeNoPhone();
      } else if (this.state.showNoEmail) {
        this.closeNoEmail();
      } else if (this.state.showNoAnyAuthentication) {
        this.closeNoAnyAuthen();
      }

    }

  }
  closeNoPhone = () => {
    this.setState({ twoFA: '0', showNoPhone: false });
  }
  closeNoEmail = () => {
    this.setState({ verifyEmail: '0', showNoEmail: false });
  }
  closeNoAnyAuthen = () => {
    this.setState({ showNoAnyAuthentication: false });
  }
  closeCheckPass = () => {
    let twofa = '0';
    if (this.state.twoFA === '0') {
      twofa = '1';
    } else {
      twofa = '0';
    }
    this.setState({ checkPass: false, twoFA: twofa });
  }

  closeAccOtpPopup = () => {
    if (this.state.checkOtp === true) {
      let twofa = '0';
      if (this.state.twoFA === '0') {
        twofa = '1';
      } else {
        twofa = '0';
      }
      this.setState({ checkOtp: false, twoFA: twofa, minutes: 0, seconds: 0 });
    } else if (this.state.checkOtpEmail === true) {
      this.setState({ checkOtpEmail: false, verifyEmail: '0', minutes: 0, seconds: 0 });
      setSession(VERIFY_EMAIL, '0');
    } else if (this.state.checkOtpChangePass === true) {
      this.setState({ checkOtpChangePass: false, minutes: 0, seconds: 0 });
    } else if (this.state.checkOtpEmailChangePass) {
      this.setState({ checkOtpEmailChangePass: false, minutes: 0, seconds: 0 });
    }

  }


  switchSMS = () => {
    if (!getSession(CELL_PHONE)) {
      // yêu cầu cập nhật số dt
      this.setState({ twoFA: '1', showNoPhone: true });
      return;
    }

    const twoFA = this.state.twoFA === '0' ? '1' : '0';

    if (this.state.verifyPhone === '1') {
      this.setState({ checkPass: true, twoFA });
    } else {
      this.genOtpPhone();
      this.setState({ twoFA });
    }
  }

  switchEmail = () => {
    if (!getSession(EMAIL) || (getSession(EMAIL) === 'undefined')) {
      this.setState({ verifyEmail: '1', showNoEmail: true });
      setSession(VERIFY_EMAIL, '1');
      return;
    }
    if (this.state.verifyEmail === '0') {
      this.genOtpEmail();
      // this.setState({ verifyEmail: '1' });
      // setSession(VERIFY_EMAIL, '1');
    }
  }

  switchTwoFA = () => {
    const twoFARequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'UpdateTwoFA',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        FullName: getSession(FULL_NAME),
        LinkFB: getSession(LINK_FB),
        LinkGmail: getSession(LINK_GMAIL),
        OS: '',
        Project: 'mcp',
        '2FA': this.state.twoFA,
        UserLogin: getSession(USER_LOGIN),
      }
    }


    switchTwoFA(twoFARequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'CHANGESSUCC') {
          setSession(TWOFA, this.state.twoFA);
          let verifyPhone = '1';
          if (this.state.twoFA === '0') {
            verifyPhone = '0';
          }
          setSession(VERIFY_CELL_PHONE, verifyPhone);
          this.setState({ verifyPhone: verifyPhone });
          if (this.state.twoFA === '1') {
            document.getElementById('popup-verify-successful').className = "popup option-popup show";
          }
        } else if (response.Response.NewAPIToken === 'invalidtoken' || response.Response.ErrLog === 'APIToken is invalid') {
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


  changePass = () => {
    if (this.state.verifyPhone === '0' && this.state.verifyEmail === '0') {
      this.setState({ showNoAnyAuthentication: true });
    } else {
      setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
      if (this.state.verifyPhone === '1') {
        //Gen otp phone
        this.genOtpPhoneChangePass();
        //this.setState({ checkOtpChangePass: true });
      } else if (this.state.verifyEmail === '1') {
        //Gen otp email
        this.genOtpEmailChangePass();
        //this.setState({checkOtpEmailChangePass: true});
      }
    }
    //document.getElementById('new-password-popup').className = "popup special new-password-popup show";
  }

  showChangeUserNameAndPassword = () => {
    if (this.state.verifyPhone === '0' && this.state.verifyEmail === '0') {
      this.setState({ showNoAnyAuthentication: true });
    } else {
      this.setState({ChangeUserNameAndPasswordScreen : true});
    }
  }

  closeChangeUserNameAndPasswordScreen = () => {
    this.setState({ChangeUserNameAndPasswordScreen : false});

  }

  requestRemoveAccount = () => {
    this.props.history.push({
      pathname: '/utilities/contact/reason=1',
    });
  }

  //Upload start
  handleUploadChange(event) {
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    let files = event.target.files;
    let fileSize = ((files[0].size / 1024) / 1024).toFixed(4);
    if (validateImage.includes(files[0].type) && fileSize <= 10) {
      let reader = new FileReader();
      reader.readAsDataURL(files[0]);

      reader.onload = (e) => {

        this.setState({
          selectedFile: e.target.result, errorUpload: ''
        })
      }
    } else {
      this.setState({ errorUpload: 'Chỉ được upload file jpeg/jpg/png không quá 10MB' });
    }

  }
  handDragFileOver(event) {
    event.preventDefault();
    document.getElementById('img-upload-id').className = "img-upload active";
  }
  handleDragFileLeave(event) {
    event.preventDefault();
    document.getElementById('img-upload-id').className = "img-upload";
  }

  handleDropFile(event) {
    event.preventDefault();
    const validateImage = ["image/jpeg", "image/jpg", "image/png"];
    let files = Object.values(event.dataTransfer.files);
    let fileSize = ((files[0].size / 1024) / 1024).toFixed(4);
    if (validateImage.includes(files[0].type) && fileSize <= 10) {
      let reader = new FileReader();
      reader.readAsDataURL(files[0]);

      reader.onload = (e) => {

        this.setState({
          selectedFile: e.target.result, errorUpload: ''
        })
      }
    } else {
      this.setState({ errorUpload: 'Chỉ được upload file jpeg/jpg/png không quá 10MB' });
    }
  }

  handleUploadSubmit = (event) => {
    event.preventDefault();
    const submitRequest = {
      imageBase64: this.state.selectedFile,
      imageName: getSession(USER_LOGIN),
      APIToken: getSession(ACCESS_TOKEN)
    }

    uploadImage(submitRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
          this.toggleUpload();
          this.checkAvatarExist();
          sessionStorage.removeItem(AVATAR_USER);
        }

      }).catch(error => {
        this.props.history.push('/maintainence');
      });
  }

  checkAvatarExist = () => {
    const submitRequest = {
      imageName: getSession(USER_LOGIN),
      APIToken: getSession(ACCESS_TOKEN)
    }
    checkAvatar(submitRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
          this.setState({ avatarPath: response.Response.avatarPath });
        } else {
          this.setState({ avatarPath: '' });
        }
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
  }
  //Upload end
  //HC Card start

  checkHaveHCCard = () => {
    const submitRequest = {
      jsonDataInput: {
        Company: COMPANY_KEY,
        Action: 'CheckHCCard',
        APIToken: getSession(ACCESS_TOKEN),
        Authentication: AUTHENTICATION,
        ClientID: getSession(CLIENT_ID),
        DeviceId: getDeviceId(),
        Project: 'mcp',
        UserLogin: getSession(USER_LOGIN),
      }

    }

    getHCCard(submitRequest)
      .then(response => {
        if (response.Response.Result === 'true' && response.Response.ErrLog === 'HCCard') {
          this.setState({ haveHCCard: '1' });
          setSession(HAVE_HC_CARD, '1');
        } else {
          this.setState({ haveHCCard: '0' });
          setSession(HAVE_HC_CARD, '0');
        }
      }).catch(error => {
        this.props.history.push('/maintainence');
      });
  }
  //HC Card end

  // Change potential user info - START
  changePotentialUserInfo() {
    const jsonState = this.state;
    jsonState.isdisplayChangeScreen = "Y";
    this.setState(jsonState);
  }

  closeUpdateForm(formState, changePhone, changeEmail) {
    if (formState !== null && formState !== undefined) {
      const sessionClientProfile = JSON.parse(getSession(CLIENT_PROFILE));
      sessionClientProfile[0].FullName = formState.familyName + ' ' + formState.givenName;
      sessionClientProfile[0].Gender = formState.gender;
      sessionClientProfile[0].DOB = moment(formState.dob, "yyyy-MM-DD").format("DD/MM/yyyy");
      sessionClientProfile[0].CellPhone = formState.phone;
      sessionClientProfile[0].Email = formState.email;
      setSession(CLIENT_PROFILE, JSON.stringify(sessionClientProfile));

      setSession(FULL_NAME, formState.familyName + ' ' + formState.givenName);
      setSession(GENDER, formState.gender);
      setSession(DOB, moment(formState.dob, "yyyy-MM-DD").format("DD/MM/yyyy"));
      setSession(CELL_PHONE, formState.phone);
      setSession(EMAIL, formState.email);

      // if (changePhone) {
      //   sessionClientProfile[0].TwoFA = '0';
      //   sessionClientProfile[0].VerifyCellPhone = '0';
      //   this.resetTwoFAPhone();
      // }
      // if (changeEmail) {
      //   sessionClientProfile[0].VerifyEmail = '0';
      //   this.resetVerifyEmail();
      // }

    }

    const jsonState = this.state;
    jsonState.isdisplayChangeScreen = "";
    this.setState(jsonState);
  }
  // Change potential user info - END
  toggleUpload = () => {
    this.setState({ uploadSelected: !this.state.uploadSelected });
  }
  render() {
    let clientId = '';
    if (getSession(CLIENT_ID)) {
      clientId = getSession(CLIENT_ID);
    }
    let gender = '';
    if (getSession(GENDER)) {
      gender = getSession(GENDER) === 'F' ? 'Nữ' : (getSession(GENDER) === 'M' ? 'Nam' : '---');
    }
    const logoutAccount = () => {
      logoutSession();
      this.props.history.push({
        pathname: '/home',
        state: { authenticated: false, hideMain: false, clientProfile: null }
      });

    }
    let nameArr = '';
    let name = '';
    let fullName = '';
    let clientProfile = null;
    if (getSession(CLIENT_PROFILE)) {
      clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
      if (clientProfile && clientProfile[0].FullName.trim() !== '') {
        fullName = clientProfile[0].FullName;
        nameArr = fullName.split(' ');
        if (nameArr.length >= 2) {
          name = nameArr[nameArr.length - 2].substring(0, 1) + nameArr[nameArr.length - 1].substring(0, 1);
        } else if (nameArr.length === 1) {
          name = nameArr[nameArr.length - 1].substring(0, 1);
        }
      }
    }

    const showHidePass = () => {
      const x = document.getElementById("check-passsword");
      if (x.type === "password") {
        x.type = "text";
        document.getElementById('check-password-input').className = "input password-input show";
      } else {
        x.type = "password";
        document.getElementById('check-password-input').className = "input password-input";
      }
    }

    const jsonState = this.state;
    const toggleUpload = () => {
      this.setState({ uploadSelected: !this.state.uploadSelected });
    }

    const showCanvas = () => {
      document.getElementById('input-file-id').click();
    }

    let msgPopup = '';
    let popupImgPath = '';
    if (this.state.invalidTypeUser) {
      msgPopup = 'Chức năng chỉ dành cho khách hàng cá nhân. Quý khách vui lòng liên hệ văn phòng Dai-ichi Life Việt Nam để được hướng dẫn.';
      popupImgPath = '../../img/popup/no-policy.svg';
    }
    const closeInvalidUserType = () => {
      this.setState({ invalidTypeUser: false });
    }
    return (
      <>
        <main className="logined nodata">
          <div className="main-warpper insurancepage page-eleven">
            <div className="profile-user">
              <div className="profile-user__left">
                <div className="left-qt" onClick={() => toggleUpload()}>
                  {this.state.avatarPath ? (
                    <div className="avatar_small">
                      <img src={API_BASE_URL + this.state.avatarPath} alt="avatar" />
                    </div>
                  ) : (
                    <div className="avatar_medium">
                      <img src="" alt="" />
                      <p className="text basic-bold">{name}</p>
                    </div>
                  )}

                  <div className="img-camera">
                    <img src="../../img/icon/camera-red.svg" alt="camera" />
                  </div>
                </div>
                <div className="left-text">
                  <div className="text">{fullName}</div>
                  {this.state.haveHCCard === '1' &&
                    <Link to="/hccard">
                      <p className="hc-card">Thẻ bảo hiểm sức khỏe ></p>
                    </Link>
                  }
                </div>
              </div>
              <a>
                <div className="profile-user__right" onClick={() => logoutAccount()}>
                  <div className="profile-user__right-sign-out">
                    <button>Đăng xuất</button>
                  </div>
                </div>
              </a>
            </div>
            {this.state.uploadSelected &&
              <div className="info__body">
                <div className="item">
                  <div className="item__content">
                    {this.state.selectedFile &&
                      <>
                        <div className="img-upload-wrapper not-empty">
                          <div className="img-upload-item">
                            <div className="file">
                              <div className="img-wrapper">
                                <img src={this.state.selectedFile} alt="" />
                              </div>

                            </div>
                          </div>
                        </div>
                        <div className="btn-wrapper">
                          <button className="btn btn-primary" id="btn-upload-avatar-id" onClick={this.handleUploadSubmit}>Upload</button>
                        </div>
                      </>
                    }
                    <div className="img-upload-wrapper">
                      <div className="img-upload-item">
                        <div className="img-upload" id="img-upload-id" onClick={() => showCanvas()}
                          onDragOver={this.handDragFileOver} onDragLeave={this.handleDragFileLeave} onDrop={this.handleDropFile}>
                          <button className="circle-plus">
                            <img src="../../img/icon/plus.svg" alt="circle-plus" className="plus-sign" />
                          </button>
                          <p className="basic-grey">
                            Kéo & thả tệp tin hoặc
                            <span className="basic-red basic-semibold">&nbsp;chọn tệp</span>
                          </p>

                          <input className="inputfile" type="file" id="input-file-id" accept="image/jpg,image/jpeg,image/png" onChange={this.handleUploadChange} />
                          {(this.state.errorUpload.length > 0) && <span style={{ color: 'red', margin: '8px 0 8px 0' }}>{this.state.errorUpload}</span>}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            }

            <div className="customer-information">
              <div className="customer-infomation consulting-service">
                {this.state.ChangeUserNameAndPasswordScreen == true ? (
                  <>
                    <ChangeUserNameAndPassword currentClientInfo={JSON.parse(getSession(CLIENT_PROFILE))[0]} 
                    close = {this.closeChangeUserNameAndPasswordScreen}
                    success = {() => {this.setState({ChangeUserNameAndPasswordScreen : false})}}
                    />
                  </>)
                : ( <>
                {jsonState.isdisplayChangeScreen === "Y" ?
                  <AccountUpdateForm
                    currentClientInfo={JSON.parse(getSession(CLIENT_PROFILE))[0]}
                    handlerCloseAccountUpdateForm={this.handlerCloseAccountUpdateForm}
                  /> :
                  <>
                  <div className='account_potential_info'> {/* for overwidth field in form__item */}
                    <div className="form">
                      <div className="form__item form__item-header">
                        <div className="above">
                          <p className="basic-black">Thông tin khách hàng</p>
                          {clientId !== '' ? (
                            <span className="update-btn simple-brown"><Link className="update-btn simple-brown" to="/update-contact-info">Cập nhật</Link></span>
                          ) : (
                            <p>
                              <a className="simple-brown2" onClick={this.handlerChangePotentialUserInfo}>Cập nhật</a>
                            </p>
                          )
                          }
                        </div>
                        <div className="below">
                          <p style={{ fontSize: '1.6rem' }}>Mã khách hàng</p>
                          <p style={{ fontSize: '1.6rem' }}>{clientId != '' ? clientId : '---'}</p>
                        </div>
                      </div>
                      <div className="form__item">
                        <p style={{ fontSize: '1.6rem' }}>Giới tính</p>
                        <p style={{ fontSize: '1.6rem' }}>{gender !== '' ? gender : '---'}</p>
                      </div>
                      <div className="form__item">
                        <p style={{ fontSize: '1.6rem' }}> Ngày sinh</p>
                        {getSession(CLIENT_ID) ? (
                          <p style={{ fontSize: '1.6rem' }}>{getSession(DOB) && (getSession(DOB) !== '01/01/1900') ? formatDateString(getSession(DOB)) : '---'}</p>
                        ) : (
                          <p style={{ fontSize: '1.6rem' }}>{getSession(DOB) && (getSession(DOB) !== '01/01/1900') ? getSession(DOB) : '---'}</p>
                        )}
                      </div>
                      <div className="form__item">
                        <p style={{ fontSize: '1.6rem' }}>Địa chỉ email</p>
                        <p style={{ fontSize: '1.6rem' }}>{(getSession(EMAIL) && getSession(EMAIL) !== 'undefined') ? getSession(EMAIL) : '---'}</p>
                      </div>

                      <div className="form__item">
                        <p style={{ fontSize: '1.6rem' }}>Điện thoại di động</p>
                        <p style={{ fontSize: '1.6rem' }}>{getSession(CELL_PHONE) ? getSession(CELL_PHONE) : '---'}</p>
                      </div>
                      <div className="form__item">
                        <p style={{ fontSize: '1.6rem' }}>Địa chỉ liên hệ</p>
                        <p className="basic-text-ali" style={{ fontSize: '1.6rem' }}>{getSession(ADDRESS) ? getSession(ADDRESS) : '---'}</p>
                      </div>
                    </div>
                    <LoadingIndicator area="verify-otp" />
                    <div className="authentication-form">
                      <h3>Phương thức nhận mã xác thực</h3>

                      <div className="content-wrapper">
                        <div className="authentication-form__item">
                          <p>Xác thực bằng SMS</p>
                          <div className="button-switch" onClick={this.switchSMS}>
                            {this.state.twoFA === '1' &&
                              <input type="checkbox" id="switch-orange" className="switch" checked />
                            }
                            {this.state.twoFA === '0' &&
                              <input type="checkbox" id="switch-orange" className="switch" />
                            }
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="authentication-sms bottom-expand">
                      <h3>Cài đặt ứng dụng</h3>

                      <div className="content-wrapper">
                        <div className="authentication-sms__item">
                          <p>Xác thực email</p>
                          <div className="button-switch" onClick={this.switchEmail}>
                            {this.state.verifyEmail === '1' &&
                              <input type="checkbox" id="switch-orange" className="switch" checked/>
                            }
                            {this.state.verifyEmail === '0' &&
                              <input type="checkbox" id="switch-orange" className="switch" />
                            }
                          </div>
                        </div>
                        <div className="change-password dropdown" onClick={this.showChangeUserNameAndPassword} /*style={{
                          borderBottom: '1px solid #e6e6e6',
                          marginLeft: '-16px',
                          marginRight: '-16px',
                          padding: '20px 16px',
                          borderRadius: '0px',
                          width: 'initial',
                        }}*/>
                          <div className="dropdown__content">
                            <p className="basic-semibold">Đổi tên đăng nhập & mật khẩu</p>
                            <p className="arrow">></p>
                          </div>
                          <div className="dropdown__items">
                            <p>chưa có design</p>
                          </div>
                        </div>
                        {/* <div className="change-password dropdown" onClick={this.requestRemoveAccount}>
                          <div className="dropdown__content">
                            <p className="basic-semibold">Yêu cầu xóa tài khoản</p>
                            <p className="arrow">></p>
                          </div>
                        </div> */}
                      </div>
                    </div>
                  </div>
                  </>
                }</>)}
              </div>
            </div>
          </div>
        </main>
        {this.state.checkPass &&
          <div className="popup special new-password-popup show" id="check-password-popup">
            <form onSubmit={this.popupCheckPassSubmit}>
              <div className="popup__card">
                <div className="new-password-card">
                  <div className="header">
                    <h4>Xin vui lòng nhập mật khẩu để xác nhận</h4>
                    <i className="closebtn"><img src="../../img/icon/close.svg" alt="" onClick={this.closeCheckPass} /></i>
                  </div>
                  <div className="body">
                    <div className="error-message" id="check-pass-id">
                      <i className="icon">
                        <img src="../../img/icon/warning_sign.svg" alt="" />
                      </i>
                      <p>Mật khẩu không chính xác. Vui lòng nhập lại.</p>
                    </div>
                    <div className="input-wrapper">
                      <div className="input-wrapper-item">
                        <div className="input password-input" id="check-password-input">
                          <div className="input__content">
                            <label htmlFor="">Mật khẩu</label>
                            <input type="password" name="password" id="check-passsword" onChange={this.handleInputPassChange} required />
                          </div>
                          <i className="password-toggle" onClick={() => showHidePass()}></i>
                        </div>
                      </div>
                    </div>
                    <LoadingIndicator area="check-pass" />
                    <div className="btn-wrapper">
                      <button className="btn btn-primary disabled" id="btn-check-pass">Tiếp tục</button>
                    </div>
                  </div>
                </div>
              </div>
              <div className="popupbg"></div>
            </form>
          </div>
        }
        {(this.state.checkOtp || this.state.checkOtpChangePass) &&
          <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer} closeOtp={this.closeAccOtpPopup} errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupAccOtpSubmit} reGenOtp={this.reGenOtp} maskPhone={maskPhone(getSession(CELL_PHONE))} />
        }
        {(this.state.checkOtpEmail || this.state.checkOtpEmailChangePass) &&
          <DOTPInput minutes={this.state.minutes} seconds={this.state.seconds} startTimer={this.startTimer} closeOtp={this.closeAccOtpPopup} errorMessage={this.state.errorMessage} popupOtpSubmit={this.popupAccOtpSubmit} reGenOtp={this.reGenOtp} maskEmail={maskEmail(getSession(EMAIL))} />
        }
        {this.state.showNoPhone &&
          <div className="popup special point-error-popup show" id="point-error-popup-sorry-no-cell-phone">
            <div className="popup__card">
              <div className="point-error-card">
                <div className="close-btn">
                  <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeNoPhone} />
                </div>
                <div className="picture-wrapper">
                  <div className="picture">
                    <img src="../../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                  </div>
                </div>
                <div className="content">
                  <h4>Rất tiếc!</h4>
                  <p>Không có thông tin số điện thoại, vui lòng cập nhật tài khoản</p>
                </div>
              </div>
            </div>
            <div className="popupbg"></div>
          </div>
        }
        {this.state.showNoEmail &&
          <div className="popup special point-error-popup show" id="point-error-popup-sorry-no-cell-phone">
            <div className="popup__card">
              <div className="point-error-card">
                <div className="close-btn">
                  <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeNoEmail} />
                </div>
                <div className="picture-wrapper">
                  <div className="picture">
                    <img src="../../img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                  </div>
                </div>
                <div className="content">
                  <h4>Rất tiếc!</h4>
                  <p>Không có thông tin email, vui lòng cập nhật tài khoản</p>
                </div>
              </div>
            </div>
            <div className="popupbg"></div>
          </div>
        }
        {this.state.showNoAnyAuthentication &&
          <div className="popup special point-error-popup show" id="point-error-popup-sorry-no-cell-phone">
            <div className="popup__card">
              <div className="phone-confirm-card">
                <div className="close-btn">
                  <img src="../../img/icon/close-icon.svg" alt="closebtn" className="btn" onClick={this.closeNoAnyAuthen} />
                </div>
                <div className="picture-wrapper">
                  <div className="picture">
                    <img src="../../img/icon/9.2/9.2-shield.svg" alt="popup-hosobg" />
                  </div>
                </div>
                <div className="content">
                  <p>Quý khách vui lòng xác thực số điện thoại/Email để nhận OTP</p>
                </div>
              </div>
            </div>
            <div className="popupbg"></div>
          </div>
        }
        {this.state.invalidTypeUser &&
          <AlertPopupInvalidUser closePopup={() => closeInvalidUserType()} msg={msgPopup} imgPath={popupImgPath} />
        }
      </>
    )
  }
}

export default AccountInfoNoCard;

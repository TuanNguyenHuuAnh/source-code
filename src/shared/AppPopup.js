import React, {Component} from 'react';
import {USER_LOGIN} from '../constants';
// refresh token
import PopupExisted from '../popup/PopupExisted';
import PopupRestorePass from '../popup/PopupRestorePass';
import PopupVerifyOtp from '../popup/PopupVerifyOtp';
import PopupNewPass from '../popup/PopupNewPass';
import PopupThanks from '../popup/PopupThanks';
import PopupOption from '../popup/PopupOption';
import PopupNonExisted from '../popup/PopupNonExisted';
import PopupVerifyOtpEmail from '../popup/PopupVerifyOtpEmail';
import PopupUserRule from '../popup/PopupUserRule';
import PopupExistedEmail from '../popup/PopupExistedEmail';
import PopupPointExChangeRule from '../popup/PopupPointExChangeRule';
import PopupNoClaimBenefit from '../popup/PopupNoClaimBenefit';
import PopupErrorOnlyForExisted from '../popup/PopupErrorOnlyForExisted';
import PopupErrorNotFoundPolicyHolder from '../popup/PopupErrorNotFoundPolicyHolder';
import PopupExchangeDoctorAddress from '../popup/PopupExchangeDoctorAddress';
import PopupSorry from '../popup/PopupSorry';
import PopupVerifyOtpEmailSocial from '../popup/PopupVerifyOtpEmailSocial';
import PopupException from '../popup/PopupException';
import PopupSessionExpired from '../popup/PopupSessionExpired';
import PopupOnlyOneFeeRefundOneTime from '../popup/PopupOnlyOneFeeRefundOneTime';
import PopupPhoneNotVerified from '../popup/PopupPhoneNotVerified';
import PopupVerifySuccessfull from '../popup/PopupVerifySuccessfull';
import PopupChangePassSucc from '../popup/PopupChangePassSucc';
import PopupOnlyOneGivePointOneTime from '../popup/PopupOnlyOneGivePointOneTime';
import PopupNoOrder from '../popup/PopupNoOrder';
import PopupAccountExceed from '../popup/PopupAccountExceed';
import PopupThanksEmail from '../popup/PopupThanksEmail';
import PopupVerifyOtpEmailForgotPass from '../popup/PopupVerifyOtpEmailForgotPass';
import PopupOtpAccountExceed from '../popup/PopupOtpAccountExceed';
import PopupOtpAccountExceed3Times from '../popup/PopupOtpAccountExceed3Times';
import PopupHappyNewYear from '../popup/PopupHappyNewYear';
import PopupThanksFeedback from '../popup/PopupThanksFeedback';
import PopupChooseAccountWarning from '../popup/PopupChooseAccountWarning';
import PopupErrorPotentialFeedback from '../popup/PopupErrorPotentialFeedback';
import PopupAskForgotPass from '../popup/PopupAskForgotPass';
import PopupNoXML from '../popup/PopupNoXML';
import PopupErrorNoHavePhoneToExchange from '../popup/PopupErrorNoHavePhoneToExchange';
import PopupIraceOption from '../popup/PopupIraceOption';
import PopupQRCnButton from '../popup/PopupQRCnButton';
import PopupDownloadnExperience from '../popup/PopupDownloadnExperience';
import {getSession} from '../util/common';
import PopupNewPassAfterLogin from "../popup/PopupNewPassAfterLogin ";
import PopupNewPassAccountRegistration from "../popup/PopupNewPassAccountRegistration";
import PopupForgetPassOption from "../popup/AccountManagementFlow/Popup/PopupForgetPassOption/PopupForgetPassOption";

class AppPopup extends Component { //({ parentCallback, parentCallbackReloadSidebar}) {
  constructor(props) {
    super(props);
    this.state = {
      ruleChecked: false,
      minutes: 0,
      seconds: 0,
      email: getSession(USER_LOGIN) ? getSession(USER_LOGIN) : ''
    };

  }

  callbackAppPopup = (isLogined, profile, po) => {
    this.props.parentCallback(isLogined, profile, po);
  }

  callbackAppReloadSidebar = (reload) => {
    this.props.parentCallbackReloadSidebar(reload);
  }

  callbackAppSetUserRule = (isChecked) => {
    this.setState({ ruleChecked: isChecked });
  }

  callbackStartTimer = () => {
    this.setState({ minutes: 5, seconds: 0 });
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

  callbackResetTimer = () => {
    this.setState({ minutes: 0, seconds: 0 });
  }
  callbackSetEmail = (ema) => {
    this.setState({ email: ema });
  }
  render() {
    return (
      <div>
        <PopupRestorePass callbackStartTimer={this.callbackStartTimer} />
        <PopupOption callbackShowLogin={this.props.callbackShowLogin} />
        <PopupForgetPassOption />
        <PopupNonExisted callbackStartTimer={this.callbackStartTimer} callbackSetEmail={this.callbackSetEmail} email={this.state.email} ruleChecked={this.state.ruleChecked} parentCallback={this.callbackAppSetUserRule} callbackShowLogin={this.props.callbackShowLogin} />
        <PopupUserRule ruleChecked={this.state.ruleChecked} parentCallback={this.callbackAppSetUserRule} />
        <PopupExisted parentCallback={this.callbackAppSetUserRule}  callbackStartTimer={this.callbackStartTimer} />
        <PopupExistedEmail parentCallback={this.callbackAppSetUserRule}  callbackStartTimer={this.callbackStartTimer} callbackSetEmail={this.callbackSetEmail} email={this.state.email} callbackShowLogin={this.props.callbackShowLogin}   />
        <PopupNewPass />
        <PopupNewPassAfterLogin  parentCallback={this.callbackAppPopup} path={this.props.path}/>
        <PopupNewPassAccountRegistration  parentCallback={this.callbackAppPopup} path={this.props.path}/>
        <PopupVerifyOtp minutes={this.state.minutes} seconds={this.state.seconds} callbackResetTimer={this.callbackResetTimer} />
        <PopupVerifyOtpEmail minutes={this.state.minutes} seconds={this.state.seconds} callbackStartTimer={this.callbackStartTimer} callbackResetTimer={this.callbackResetTimer} parentCallback={this.callbackAppPopup} path={this.props.path}/>
        <PopupVerifyOtpEmailSocial parentCallback={this.callbackAppPopup} path={this.props.path}/>
        <PopupThanks />
        <PopupPointExChangeRule />
        <PopupNoClaimBenefit />
        <PopupErrorOnlyForExisted />
        <PopupErrorNotFoundPolicyHolder />
        <PopupExchangeDoctorAddress />
        <PopupSorry />
        <PopupException />
        {/*<PopupSuccessfulND13 />*/}
        <PopupSessionExpired />
        <PopupOnlyOneFeeRefundOneTime />
        <PopupPhoneNotVerified />
        <PopupVerifySuccessfull />
        <PopupChangePassSucc />
        <PopupOnlyOneGivePointOneTime />
        <PopupNoOrder />
        <PopupAccountExceed />
        <PopupThanksEmail />
        <PopupVerifyOtpEmailForgotPass minutes={this.state.minutes} seconds={this.state.seconds} callbackStartTimer={this.callbackStartTimer} callbackResetTimer={this.callbackResetTimer} callbackSetEmail={this.callbackSetEmail} email={this.state.email} />
        <PopupOtpAccountExceed />
        <PopupOtpAccountExceed3Times />
        <PopupHappyNewYear />
        <PopupThanksFeedback />
        <PopupChooseAccountWarning />
        <PopupErrorPotentialFeedback />
        <PopupAskForgotPass callbackStartTimer={this.callbackStartTimer} callbackSetEmail={this.callbackSetEmail} email={this.state.email} />
        <PopupNoXML />
        <PopupErrorNoHavePhoneToExchange />
        <PopupIraceOption firstFunc={() => this.props.iraceNotYet()} secondFunc={()=>this.props.iraceExisted()} />
        <PopupQRCnButton/>
        <PopupDownloadnExperience/>
      </div>
    )
  }
}

export default AppPopup;

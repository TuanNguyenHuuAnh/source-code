import React, { Component } from 'react';
import { FORGOT_PASSWORD, USER_LOGIN, COMPANY_KEY, AUTHENTICATION, ACCESS_TOKEN } from '../constants';
import { forgotPass } from '../util/APIUtils';
import {setSession, getSession, getDeviceId} from '../util/common';

class PopupAskForgotPass extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false
        };
        this.closeOptionPopupEsc = this.closeOptionPopupEsc.bind(this);
    }

    componentDidMount(){
        document.addEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    componentWillUnmount(){
        document.removeEventListener("keydown", this.closeOptionPopupEsc, false);
    }
    closeOptionPopupEsc=(event)=> {
        if (event.keyCode === 27) {
            this.closeOptionPopup();
        }
    
    } 
    closeOptionPopup() {
        if (getSession(FORGOT_PASSWORD)) {
            sessionStorage.removeItem(FORGOT_PASSWORD);
        }
        document.getElementById('popup-ask-forgot-pass').className = "popup option-popup";
    }

    render() {
        const forgotPassword = (event) => {
            event.preventDefault();
            this.setState({loading: true});
            var agent = window.btoa(navigator.userAgent);
            setSession(FORGOT_PASSWORD, FORGOT_PASSWORD);
            const forgotPassRequest = {jsonDataInput:{
                Company:COMPANY_KEY,
                Action:'ForgotPassword',
                Authentication:AUTHENTICATION,
                DeviceId: getDeviceId(),
                OS: '',
                Project:'mcp',
                UserLogin: getSession(USER_LOGIN)
                }
    
            }
            forgotPass(forgotPassRequest) //call signup, but json is for case forgotpassword
            .then(response => {
                if (response.Response.Result === 'true') {
                this.props.callbackStartTimer();
                document.getElementById('popup-ask-forgot-pass').className = "popup option-popup";
                document.getElementById('otp-email-forgot-popup').className = "popup special otp-popup show";
                document.getElementById('otp-email-forgot-id').value = '';

                setSession(ACCESS_TOKEN, response.Response.NewAPIToken);
                    
                }
                this.setState({loading: false});
            }).catch(error => {
                this.setState({loading: false});
            });
        }
        return (
            <div className="popup option-popup" id="popup-ask-forgot-pass">
                <div className="popup__card">
                    <div className="optioncard" style={{lineHeight:'20px',paddingBottom:'0px'}}>
                        <p>Quý khách đã có tài khoản, vui lòng <br/>thực hiện khôi phục mật khẩu</p>
                    <div className="btn-wrapper">
                        {this.state.loading?(
                            <button className="btn btn-primary disabled">Khôi phục mật khẩu</button>
                        ):(
                            <button className="btn btn-primary" onClick={(event) => forgotPassword(event)}>Khôi phục mật khẩu</button>
                        )}
                        <button className="btn btn-nobg" onClick={this.closeOptionPopup}>Đăng nhập</button>
                    </div>
                    <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt="" onClick={this.closeOptionPopup}/></i>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}
export default PopupAskForgotPass;
import React, { useState, useEffect } from 'react';
import OTPInput from "otp-input-react";
// import '../common/Common.css';
import LoadingIndicator from '../LoadingIndicator2';
import { isMobile } from '../sdkCommon';

function DOTPInput({length, width, minutes, seconds, errorMessage, startTimer, closeOtp, popupOtpSubmit, reGenOtp, maskPhone, maskEmail, isSubmitting }) {
    const [OTP, setOTP] = useState('');
    const [otpLength, setOtpLength] = useState(length?length: 6);
    const [otpWidth, setOtpWidth] = useState(width?width: (isMobile()?'27px':'54.5px'))

    const otpSubmit=(event)=> {
        if (isSubmitting) {
            return;
        }
        event.preventDefault();
        if (document.getElementById('btn-verify-otp-id').className === "btn btn-primary disabled") {
            return;
        }
        popupOtpSubmit(OTP);
    }

    const closeOtpPopupEsc = (event) => {
        if (event.keyCode === 27) {
            closeOtpPopup();
        }

    }
    const closeOtpPopup = () => {
        closeOtp();
    }


    useEffect(() => {
        if ((OTP.length >= 6) && !isSubmitting) {
            document.getElementById('btn-verify-otp-id').className = 'btn btn-primary';
            document.getElementById('btn-verify-otp-id').disabled = false;
        } else {
            document.getElementById('btn-verify-otp-id').className = 'btn btn-primary disabled';
            document.getElementById('btn-verify-otp-id').disabled = true;
        }
        document.addEventListener("keydown", closeOtpPopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closeOtpPopupEsc, false);
        }

    }, [OTP, minutes, seconds, errorMessage]);

    return (

            <div className="popup special otp-popup show" id="d-otp-popup">
                <form onSubmit={(event) => otpSubmit(event)}>
                    <div className="popup__card">
                        <div className="popup-otp-card">
                            <div className="header">
                                <h4>Nhập mã OTP</h4>
                                <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={()=>closeOtpPopup()} /></i>
                            </div>
                            <div className="body">
                                {errorMessage&&
                                    <div className="error-message validate">
                                        <i className="icon">
                                            <img src="../img/icon/warning_sign.svg" alt="" />
                                        </i>
                                        <p>{errorMessage}</p>
                                    </div>
                                }

                                <div className="otp-picture-wrapper">
                                    <div className="picture" style={{marginLeft: '34%'}}>
                                    <img src="../img/otp-verify.svg" />
                                    </div>
                                </div>
                                <h5 className="basic-text2 bigheight">
                                    Mã <span className="basic-text2 basic-bold">OTP</span> đã được gửi đến
                                    <span className="basic-text2 basic-bold">&nbsp;{maskPhone?'Số điện thoại ' + maskPhone: (maskEmail?'email ' + maskEmail:'email')}</span> của Quý khách. Vui lòng nhập
                                    <span className="basic-text2 basic-bold">&nbsp;OTP</span> để xác nhận.
                                </h5>
                                <div className="otp-focus">
                                <OTPInput value={OTP} onChange={setOTP} autoFocus OTPLength={otpLength} otpType="number" disabled={false} 
                                                inputStyles={{
                                                    borderBottom: '2px solid #C4C4C4',
                                                    borderTop: '0px solid #ffffff',
                                                    borderLeft: '0px solid #ffffff',
                                                    borderRight: '0px solid #ffffff',
                                                    width: otpWidth
                                                }} 
                                                required />

                                </div>
                                {minutes === 0 && seconds === 0
                                    ? (
                                        <p className="tag">Bạn chưa nhận được mã OTP? <br/><span className="simple-brown" onClick={() => reGenOtp()}>Gửi lại</span></p>
                                    ) : (<p className="tag">Bạn chưa nhận được mã OTP?
                                        <br/><span className="tag" onClick={() => reGenOtp()}>Gửi lại mã mới sau</span>&nbsp;<span style={{ fontWeight: 700 }}>{`${minutes * 60 + seconds}s`}</span>
                                    </p>)
                                }
                                <br/>
                                <br/>
                                <LoadingIndicator area="verify-otp" />
                                <div className="btn-wrapper">
                                    <button className={`btn btn-primary ${(OTP.length >= 6) && !isSubmitting ? '' : 'disabled'}`} id="btn-verify-otp-id"  disabled={((OTP.length < 6) || isSubmitting)}>Xác nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </form>
            </div>
            

    )
}

export default DOTPInput;
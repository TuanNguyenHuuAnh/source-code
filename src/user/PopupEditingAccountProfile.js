import React, {useEffect} from 'react';
import {FE_BASE_URL} from '../constants';
import LoadingIndicator from '../common/LoadingIndicator2';


    const PopupEditingAccountProfile = ({jsonState, setState, onSubmitInput, onClose, isValidEmail, isValidPhone, isSubmitEditing}) => {

    useEffect(() => {
        const closeEsc = (event) => {
            if (event.keyCode === 27) {
                close();
            }
        };

        document.addEventListener("keydown", closeEsc, false);

        return () => {
            document.removeEventListener("keydown", closeEsc, false);
        };
    }, []);

    const close = () => {
        // document.getElementById('popup-edit-cellphone').className = "popup special edit-cell-phone-popup";
        onClose();
    };

    const onChangeInput = (inputName, inputValue) => {
        switch (inputName) {
            case "phone":
                jsonState.phone = inputValue;
                jsonState.errors.phone = "";
                break;
            case "email":
                jsonState.email = inputValue;
                jsonState.errors.email = "";
                break;
            default:
                break;
        }
        setState(jsonState);
    }

    return (
        <div className={`popup special edit-cell-phone-popup show ${isValidPhone ? 'edit-cellphone' : 'edit-email'}`} id="popup-edit-cellphone">
            <div className="popup__card">
                <div className="point-error-card" style={{ maxWidth: '460px', padding: '24px 16px' }}>
                    <p style={{
                        textAlign: 'center',
                        marginBottom: '20px',
                        fontSize: 18,
                        fontWeight: 700,
                        lineHeight: '24px',
                    }}>{isValidPhone ? 'Số điện thoại' : 'Email'}</p>
                    <LoadingIndicator area="update-account-loading-area" />
                    <div className="close-btn">
                        <img src={`${FE_BASE_URL}/img/icon/close-icon.svg`} alt="closebtn" className="btn"
                             onClick={close} style={{ marginTop: '-6px' }} />
                    </div>
                    <div className="content" style={{ padding: '0px' }}>
                        {isValidPhone &&
                            <>
                                <div className="input basic-expand-margin1">
                                    <div className="input__content">
                                        <label htmlFor="phone">Số điện thoại<span style={{color: "red"}}>*</span></label>
                                        <input type="search" className="basic-black" id="phone" value={jsonState.phone} maxLength="10"
                                               onChange={(event) => onChangeInput("phone", event.target.value)} />
                                    </div>
                                    <i><img src="img/icon/phone-grey.svg" alt="calender" /></i>
                                </div>
                                {jsonState.errors.phone && <span style={{
                                    color: '#DE181F',
                                    fontSize: 12,
                                    lineHeight: '14.52px',
                                    fontWeight: 500,
                                }}>{jsonState.errors.phone}</span>}
                                <div style={{
                                    fontSize: 12,
                                    lineHeight: '14.52px',
                                    fontWeight: 500,
                                    color: '#727272',
                                    marginTop: '12px'
                                }}>Số điện thoại này sẽ được dùng để xác thực tài khoản, nhận các thông báo về các dịch vụ sức khỏe, chương trình ưu đãi khác từ Dai-ichi Life Việt Nam.</div>
                            </>
                        }
                        {isValidEmail &&
                            <>
                                <div className="input basic-expand-margin1">
                                    <div className="input__content">
                                        <label htmlFor="email">Email<span style={{color: "red"}}>*</span></label>
                                        <input type="search" className="basic-black" id="email" value={jsonState.email} maxLength="100"
                                               onChange={(event) => onChangeInput("email", event.target.value)} />
                                    </div>
                                    <i><img src="img/icon/email.svg" alt="email" /></i>
                                </div>
                                {jsonState.errors.email && <span style={{
                                    color: '#DE181F',
                                    fontSize: 12,
                                    lineHeight: '14.52px',
                                    fontWeight: 500,
                                }}>{jsonState.errors.email}</span>}
                                <div style={{
                                    fontSize: 12,
                                    lineHeight: '14.52px',
                                    fontWeight: 500,
                                    color: '#727272',
                                    marginTop: '12px'
                                }}>Email này sẽ được dùng để xác thực tài khoản, nhận các thông báo về các dịch vụ sức khỏe, chương trình ưu đãi khác từ Dai-ichi Life Việt Nam.</div>
                            </>
                        }
                        <button className={`btn btn-primary ${isSubmitEditing ? 'disabled' : ''}`}
                                style={{
                                    width: '100%',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    marginTop: '32px',
                                }}
                                disabled={isSubmitEditing}
                                onClick={onSubmitInput}>Lưu</button>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    );
};

export default PopupEditingAccountProfile;

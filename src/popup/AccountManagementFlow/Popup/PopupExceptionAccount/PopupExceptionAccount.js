import React, {useEffect} from 'react';

const PopupExceptionAccount = ({ onCallBack }) => {
    useEffect(() => {
        const closeThanksEsc = (event) => {
            if (event.keyCode === 27) {
                closeThanks();
            }
        };

        document.addEventListener("keydown", closeThanksEsc, false);

        return () => {
            document.removeEventListener("keydown", closeThanksEsc, false);
        };
    }, []);

    const closeThanks = () => {
        onCallBack();
    };

    return (
        <div className="popup special exception-account-popup show" id="popup-exception-account">
            <div className="popup__card">
                <div className="point-error-card">
                    <div className="close-btn">
                        <img src="/img/icon/close-icon.svg" alt="closebtn" className="closebtn" onClick={closeThanks}/>
                    </div>
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src="/img/popup/quyenloi-popup.svg" alt="popup-hosobg" />
                        </div>
                    </div>
                    <div className="content">
                        <p>Rất tiếc!</p>
                        <p>Có lỗi xảy ra, Quý khách vui lòng thử lại sau.</p>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    );
};
export default PopupExceptionAccount;
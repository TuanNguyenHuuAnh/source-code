import React, {useEffect} from 'react';
import LoadingIndicatorBasic from "../../../../common/LoadingIndicatorBasic";

const PopupSuccessfulAccount = ({ onCallBack, loading }) => {
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
        <div className="popup special confirm-account-popup show" id="popup-successful-account">
            <div className="popup__card">
                <div className="envelop-confirm-card">
                    <div className="envelopcard">
                        <div className="envelop-content">
                            <div className="envelop-content__header">
                                <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={closeThanks} /></i>
                            </div>
                            <div className="envelop-content__body">
                                <p>
                                    Thông tin đăng nhập của Quý khách đã được cập nhật thành công.
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="envelopcard_bg">
                        <img src="../img/envelop_nowhite.png" alt="" />
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>
    );
};
export default PopupSuccessfulAccount;
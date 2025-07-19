import React, {useEffect} from 'react';

const PopupSuccessfulSignUp = ({ onCallBack }) => {
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
        <div className="popup special envelop-confirm-popup show" id="popup-thanks-email">
            <div className="popup__card">
              <div className="envelop-confirm-card">
                <div className="envelopcard">
                  <div className="envelop-content">
                    <div className="envelop-content__header">
                      <i className="closebtn"><img src="../img/icon/close.svg" alt="" onClick={closeThanks}/></i>
                    </div>
                    <div className="envelop-content__body">
                      <p className="basic-bold">ĐĂNG KÝ THÀNH CÔNG</p>
                      <p>&nbsp;</p>
                      <p>
                        Cảm ơn bạn đã lựa chọn Dai-ichi Life Việt Nam.
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
export default PopupSuccessfulSignUp;
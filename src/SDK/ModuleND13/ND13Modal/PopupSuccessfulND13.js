import React, { useEffect } from 'react';

const PopupSuccessfulND13 = (props) => {
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
    // const popupThanks = document.getElementById('popup-confirm-follow-up');
    // if (popupThanks) {
    //   popupThanks.className = "popup special popup-confirm-follow-up";
    //   window.location.href = '/';
    // }

    props.onClose();
  };

  return (
      <div className="popup special popup-confirm-follow-up show" id="popup-confirm-follow-up">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn"><img src="../../img/icon/close.svg" alt="" onClick={closeThanks} /></i>
                </div>
                <div className="envelop-content__body">
                  <p style={{ textAlign: 'center', marginBottom: 12, fontWeight: 700 }}>GỬI YÊU CẦU THÀNH CÔNG</p>
                  <p>
                    Vui lòng theo dõi tình trạng hồ sơ tại chức năng <br /><span style={{ fontWeight: 700 }}>Theo dõi yêu cầu.</span>
                  </p>
                </div>
              </div>
            </div>
            <div className="envelopcard_bg">
              <img src="../../img/envelop_nowhite.png" alt="" />
            </div>
          </div>
        </div>
        <div className="popupbg"></div>
      </div>
  );
};

export default PopupSuccessfulND13;

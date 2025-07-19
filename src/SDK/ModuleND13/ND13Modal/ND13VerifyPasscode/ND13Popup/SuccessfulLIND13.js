import React, { useEffect } from 'react';

const SuccessfulLIND13 = () => {
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
    const popupThanks = document.getElementById('popup-thanks');
    if (popupThanks) {
      popupThanks.className = "popup special envelop-confirm-popup";
      // alert('close');
      window.location.href = '/';
      window.location.reload();
    }
  };



  return (
      <div className="popup special envelop-confirm-popup show" id="popup-thanks">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn"><img src="../../../../../img/icon/close.svg" alt="" onClick={closeThanks} /></i>
                </div>
                <div className="envelop-content__body">
                  <p>
                   Cảm ơn Quý khách đã xác nhận thông tin.
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

export default SuccessfulLIND13;

import React, {useEffect} from 'react';

const PopupSurvey = ({ message, onClosePopup }) => {

  useEffect(() => {
    const closeThanksEsc = (event) => {
      if (event.keyCode === 27) {
        closeThanks();
      }
    };

    document.addEventListener('keydown', closeThanksEsc, false);

    return () => {
      document.removeEventListener('keydown', closeThanksEsc, false);
    };
  }, []);

  const closeThanks = () => {
    document.getElementById('popup-thanks-survey').className = 'popup special envelop-confirm-popup';
    onClosePopup && onClosePopup(); // Call the callback to close the popup
  };

  return (
      <div className="popup special envelop-confirm-popup show" id="popup-thanks-survey">
        <div className="popup__card">
          <div className="envelop-confirm-card">
            <div className="envelopcard">
              <div className="envelop-content">
                <div className="envelop-content__header">
                  <i className="closebtn">
                    <img src="../img/icon/close.svg" alt="" onClick={closeThanks} />
                  </i>
                </div>
                <div className="envelop-content__body">
                  <p>{message || 'Cảm ơn Quý khách đã gửi khảo sát'}</p>
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

export default PopupSurvey;

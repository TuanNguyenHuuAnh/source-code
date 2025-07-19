import React, { useState, useEffect } from 'react';
import './styles.css';

const PopupConfirmContainer = () => {
  const [isLyricsVisible, setIsLyricsVisible] = useState(true);
  return (
    <>
      {isLyricsVisible && (
        <div className="popup-container">
          <div className="popup-content">
            <h4 style={{ margin: '20px 0' }}>Record hoàn thành</h4>
            <button className="toggle-button" onClick={() => setIsLyricsVisible(false)}>
              Đóng
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default PopupConfirmContainer;

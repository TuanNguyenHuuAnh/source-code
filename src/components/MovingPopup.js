import React from 'react';
import '../common/Common.css';
import LoadingIndicatorBasic from '../common/LoadingIndicatorBasic';
import AppFooter from '../shared/AppFooter';

function MovingPopup() {

    return (
        <div className="popup special popup__moving show">
            <div className="popup__moving">
                <div className='content'>
                <LoadingIndicatorBasic />
                <p>Những thử thách thú vị của Cung Đường Yêu Thương <br/> đang được chuẩn bị...</p>
                </div>
            </div>
            <div className="popupbg_ext"></div>
        </div>
    )
}

export default MovingPopup;
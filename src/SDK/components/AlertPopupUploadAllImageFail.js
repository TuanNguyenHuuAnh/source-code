import React, { useEffect } from 'react';
// import '../common/Common.css';
import {SCREENS, FE_BASE_URL} from '../sdkConstant';
import { Link } from "react-router-dom";
import parse from "html-react-parser";

function AlertPopupUploadAllImageFail({ closePopup, msg, imgPath, subMsg = "" }) {


    const close = () => {
        closePopup();
    }


    return (

        <div className="alert popup special point-error-popup show">
            <div className="popup__card">
                <div className="point-error-card" style={{paddingTop: '29px'}}>
                    {/* <div className="close-btn" style={{marginTop: '-16px', marginRight: '-1px'}}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />  
                        
                    </div> */}
                    <div className="picture-wrapper">
                        <div className="picture">
                            <img src={imgPath} />
                        </div>
                    </div>
                    <div className="content" style={{marginTop: '0px', marginLeft: '16px', marginRight: '16px'}}>
                        {msg ? parse(msg) : ''}
                        <br/>
                        {subMsg ? parse(subMsg) : ''}
                    </div>
                    <div className="btn-wrapper" style={{marginTop: '16px'}}>
                        <button className="btn btn-primary" onClick={() => close()}>Đồng ý</button>
                    </div>
                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default AlertPopupUploadAllImageFail;
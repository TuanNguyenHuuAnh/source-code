import React, {useEffect} from 'react';

import Address from './Address';
import {FE_BASE_URL} from '../constants';

    const AddressPopup = ({onClose, handlerOnChangeAddress}) => {

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

    return (
        <div className={`popup special edit-cell-phone-popup show edit-cellphone`} id="popup-edit-cellphone">
            <div className="popup__card">
                <div className="point-error-card" style={{ maxWidth: '460px', padding: '24px 16px' }}>
                    <div className="close-btn" style={{marginTop: '-17px', marginRight: '-1px'}}>
                        <img src={FE_BASE_URL + "/img/icon/close-icon.svg"} alt="closebtn" className="btn" onClick={() => close()} />
                    </div>
                    <Address handlerOnChangeAddress={handlerOnChangeAddress}/>
                </div>
            </div>
            <div className="popupbg"></div>
            {/* <button className={`btn btn-primary ${isSubmitEditing ? 'disabled' : ''}`}
                                style={{
                                    width: '100%',
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'center',
                                    marginTop: '32px',
                                }}
                                disabled={isSubmitEditing}
                                onClick={onSubmitInput}>Lưu</button> */}
        </div>
    );
};

export default AddressPopup;

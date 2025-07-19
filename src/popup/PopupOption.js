import React, {Component} from 'react';
import {FORGOT_PASSWORD, FORGOT_PASSWORD_PO, FORGOT_PASSWORD_POTENTIAL} from '../constants';
import {getSession, setSession} from '../util/common';
import iconContract from '../img/icon/iconContract.svg';
import iconContractWhite from '../img/icon/iconContractWhite.svg';
import iconAccount from '../img/icon/iconAccount.svg';
import iconAccountWhite from '../img/icon/iconAccountWhite.svg';


class PopupOption extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isExistedClicked: false,
            isNonExistedClicked: false,
        };
        // this.closeOptionPopupEsc = this.closeOptionPopupEsc.bind(this);
        this.hadPolicy = this.hadPolicy.bind(this);
        this.noHavePolicy = this.noHavePolicy.bind(this);

        this.iconContractWhite = new Image();
        this.iconContractWhite.src = iconContractWhite;
        this.iconContract = new Image();
        this.iconContract.src = iconContract;
        this.iconAccountWhite = new Image();
        this.iconAccountWhite.src = iconAccountWhite;
        this.iconAccount = new Image();
        this.iconAccount.src = iconAccount;

    }

    // componentDidMount() {
    //     document.addEventListener("keydown", this.closeOptionPopupEsc, false);
    // }

    // componentWillUnmount() {
    //     document.removeEventListener("keydown", this.closeOptionPopupEsc, false);
    // }

    // closeOptionPopupEsc (event) {
    //     if (event.keyCode === 27) {
    //         this.closeOptionPopup();
    //     }
    //     this.setState({
    //         isExistedClicked: false,
    //         isNonExistedClicked: false,
    //     });
    // }

    hadPolicy = () => {
        this.setState({
            isExistedClicked: true,
            isNonExistedClicked: false,
        }, () => {
            setTimeout(() => {
                document.getElementById('option-popup').className = "popup option-popup";
                if (getSession(FORGOT_PASSWORD)) {
                    document.getElementById('popup_forget-pass').className = "popup popup_forget-pass show";
                    setSession(FORGOT_PASSWORD_PO);
                    sessionStorage.removeItem(FORGOT_PASSWORD_POTENTIAL);
                } else {
                    document.getElementById('signup-popup-existed').className = "popup special signup-popup-existed show";
                }
                this.setState({
                    isExistedClicked: false,
                    isNonExistedClicked: false,
                });
            }, 300);
        });
    };

    noHavePolicy = () => {
        this.setState({
            isExistedClicked: false,
            isNonExistedClicked: true,
        }, () => {
            setTimeout(() => {
                document.getElementById('option-popup').className = "popup option-popup";
                // document.getElementById('popup_forget-pass').className = "popup popup_forget-pass show";

                if (getSession(FORGOT_PASSWORD)) {
                    document.getElementById('popup_forget-pass').className = "popup popup_forget-pass show";
                    setSession(FORGOT_PASSWORD_POTENTIAL);
                    sessionStorage.removeItem(FORGOT_PASSWORD_PO);
                } else {
                    document.getElementById('signup-popup-nonexisted').className = "popup special signup-popup-nonexisted show";
                }

                this.setState({
                    isExistedClicked: false,
                    isNonExistedClicked: false,
                });
            }, 300);
        });
    };

    preloadImages() {
        const imagesToPreload = [
            iconContractWhite,
            iconContract,
            iconAccountWhite,
            iconAccount,
        ];

        imagesToPreload.forEach((image) => {
            const img = new Image();
            img.src = image;
        });
    }

    render() {
        const closeOptionPopup = () => {
            this.props.callbackShowLogin(true);
            if (getSession(FORGOT_PASSWORD)) {
                sessionStorage.removeItem(FORGOT_PASSWORD);
            }
            document.getElementById('option-popup').className = "popup option-popup";
        }
        return (
            <div className="popup option-popup" id="option-popup">
                <div className="popup__card">
                    <div className="optioncard">
                        <p>Quý khách có Hợp đồng Bảo hiểm<br/>với Dai-ichi Life Việt Nam?​</p>
                        <div className="btn-frame-wrapper">
                            <button className={`btn-frame ${this.state.isExistedClicked ? 'clicked' : ''}`} id="existed" onClick={this.hadPolicy} style={{ marginRight: '4px'}}>
                                <img src={this.state.isExistedClicked ? this.iconContractWhite.src : this.iconContract.src} alt="iconContract" />
                                
                                <span className={`btn-frame-text ${this.state.isExistedClicked ? 'btn-text-color-clicked' : 'btn-text-color'}`}>
                                    <div className='temp_container_PopupOption'>Đã có <br></br> hợp đồng</div>
                                </span>
                                
                            </button>
                            <button className={`btn-frame ${this.state.isNonExistedClicked ? 'clicked' : ''}`}
                                    id="nonexisted" onClick={this.noHavePolicy} style={{ marginLeft: '4px'}}>
                                <img src={this.state.isNonExistedClicked ? this.iconAccountWhite.src : this.iconAccount.src} alt="iconAccount" />

                                <span className={`btn-frame-text ${this.state.isNonExistedClicked ? 'btn-text-color-clicked' : 'btn-text-color'}`}>
                                    <div className='temp_container_PopupOption'>Chưa có <br></br> hợp đồng</div>
                                </span>

                            </button>
                        </div>
                        <i className="closebtn option-closebtn"><img src="../img/icon/close.svg" alt=""
                                                                     onClick={()=>closeOptionPopup()}/></i>
                    </div>
                </div>
                <div className="popupbg"></div>
            </div>

        )
    }
}

export default PopupOption;
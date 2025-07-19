import React, {useState, useEffect} from 'react';
import {PAGE_POLICY_PAYMENT, PAGE_REGULATIONS_PAYMENT, FE_BASE_URL} from '../sdkConstant';

function UserAgree({closePopup, popupSubmit}) {
    const [agree, setAgree] = useState(false);

    const agreeSubmit = (event) => {
        event.preventDefault();
        popupSubmit();
    }

    const closePopupEsc = (event) => {
        if (event.keyCode === 27) {
            closeUserRule();
        }

    }
    const closeUserRule = () => {
        closePopup();
    }

    const toggleAgree = () => {
        setAgree(!agree);
    }

    useEffect(() => {
        document.addEventListener("keydown", closePopupEsc, false);
        return () => {
            document.removeEventListener("keydown", closePopupEsc, false);
        }
    }, [agree]);

    return (

        <div className="popup special user-rule-popup show" id="user-agree_popup_id">
            <div className="popup__card">

                <div className="user-rule-card">
                    <div className="header" style={{padding: '26px'}}>
                        <i className="closebtn"><img src="img/icon/close.svg" alt=""
                                                     onClick={() => closeUserRule()}/></i>
                    </div>
                    <div className="body">

                        <div className="content-wrapper popup-security">
                            <div className={agree ? "bottom-text choosen" : "bottom-text"}
                                 style={{'maxWidth': '594px', paddingLeft: '3px', marginTop: '10px'}}
                                 onClick={() => toggleAgree()}>
                                <div className={agree ? "square-choose fill-red" : "square-choose"}
                                     style={{height: '20px', cursor: 'pointer'}}>
                                    <div className="checkmark">
                                        <img src={FE_BASE_URL + "/img/icon/check.svg"} alt=""/>
                                    </div>
                                </div>
                                <p className="popup-security__title" style={{
                                    textAlign: 'justify',
                                    paddingLeft: '28px',
                                    marginTop: '-20px',
                                    paddingTop: '0'
                                }}>
                                    <span style={{color: '#727272', fontSize: '12px', fontWeight: '600'}}>
                                        Tôi đồng ý và xác nhận:
                                    </span>
                                </p>
                            </div>
                            {/* <p>Tôi cam kết và đồng ý:</p> */}
                            <p className="popup-security__text">
                                - Tất cả thông tin trên là đầy đủ, đúng sự thật và là cơ sở
                                hợp pháp để Dai-ichi Life Việt Nam (DLVN) thẩm định yêu
                                cầu khôi phục hiệu lực Hợp đồng này. Tôi/Chúng tôi có
                                trách nhiệm thông báo cho DLVN nếu có thay đổi thông
                                tin sức khỏe và các thông tin khác đã khai báo với DLVN
                                của (những) NĐBH trước khi HĐBH được chấp thuận khôi
                                phục hiệu lực. Việc vi phạm nghĩa vụ cung cấp thông tin,
                                cung cấp thông tin không đầy đủ, không chính xác khi yêu
                                cầu khôi phục hiệu lực HĐBH hoặc không thông báo cho
                                DLVN thay đổi thông tin sức khỏe trước khi HĐBH được
                                chấp thuận khôi phục hiệu lực thì DLVN sẽ: (1) Đối với
                                HĐBH phát hành trước ngày 01/01/2023: sẽ đình chỉ/chấm
                                dứt khôi phục hiệu lực HĐBH, DLVN chỉ chi trả giá trị hoàn
                                lại (nếu có) và không chi trả bất kỳ Quyền lợi bảo hiểm
                                nào; (2) Đối với HĐBH phát hành từ 01/01/2023: sẽ hủy bỏ
                                khôi phục hiệu lực Hợp đồng, DLVN không có trách nhiệm
                                chi trả bất kỳ quyền lợi bảo hiểm nào. Đồng thời DLVN sẽ
                                hoàn lại phí bảo hiểm đã nộp để khôi phục hiệu lực HĐBH
                                sau khi trừ đi các chi phí hợp lý.
                            </p>
                            <p className="popup-security__text">
                                - Hiểu rằng yêu cầu này chỉ có hiệu lực kể từ ngày được DLVN chấp nhận.
                            </p>
                            <p className="popup-security__text">
                            - Với xác nhận hoàn tất giao dịch, Tôi/Chúng tôi đồng ý với
                            <span className="rule-red basic-bold"><a
                                style={{display: 'inline'}}
                                className="rule-red basic-bold"
                                href={PAGE_POLICY_PAYMENT} target='_blank'> Điều khoản Dịch vụ và Giao dịch điện tử</a></span> và
                                <span className="rule-red basic-bold"><a style={{display: 'inline'}}
                                                                              className="rule-red basic-bold"
                                                                              href={PAGE_REGULATIONS_PAYMENT} target='_blank'> Quy định bảo vệ và xử lý dữ liệu của Dai-ichi Life Việt Nam</a></span>.
                            </p>
                        </div>


                        <div className="btn-wrapper">
                            {agree ? (
                                <button className="btn btn-primary" id="btn-user-agree"
                                        onClick={(event) => agreeSubmit(event)}>Hoàn thành</button>
                            ) : (
                                <button className="btn btn-primary disabled" id="btn-user-agree">Hoàn thành</button>
                            )
                            }

                        </div>


                    </div>

                </div>
            </div>
            <div className="popupbg"></div>
        </div>


    )
}

export default UserAgree;
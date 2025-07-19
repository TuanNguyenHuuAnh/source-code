import React, {useEffect, useRef, useState} from 'react';
import iconClose from '../../../../img/icon/close-icon.svg';
import arrowDown from '../../../../img/icon/dropdown-arrow.svg';
import {DatePicker, Select} from 'antd';
import dayjs from "dayjs";
import moment from "moment";
import './styles.css';
import {removeAccents, VALID_EMAIL_REGEX} from "../../../../util/common";
import {iibGetMasterDataByType} from "../../../../util/APIUtils";
import {SearchOutlined} from "@ant-design/icons";

const PaymentPOPersonalInfo = (props) => {
        const {Option} = Select;
        const guardianNameRef = useRef(null);
        const cellPhoneRef = useRef(null);
        const emailRef = useRef(null);
        const identityCardRef = useRef(null);
        const {
            onClickConfirmBtn = () => {
            },
            onClickCallBack = () => {
            },
        } = props;
        const [isOpenPopup, setOpenPopup] = useState(true);
        const [relationShipList, setRelationShipList] = useState([]);
        const [relateModalData, setRelateModalData] = useState({
            guardianName: {
                value: '',
                error: '',
            },
            dateOfBirth: {
                value: '',
                error: '',
            },
            cellPhone: {
                value: '',
                error: '',
            },
            email: {
                value: '',
                error: '',
            },
            relationship: {
                value: '',
                error: '',
            },
            poRelation: {
                value: '',
                error: '',
            },
            poRelationCode: {
                value: '',
                error: '',
            },
            refPurpose: {
                value: '',
                error: '',
            },
            refOtherPurpose: {
                value: '',
                error: '',
            },
            identityCard: {
                value: '',
                error: '',
            },
        });
        const [isActiveRelate, setIsActiveRelate] = useState(false);
        const [acceptPolicy, setAcceptPolicy] = useState(false);


        const getRelationShips = () => {
            let request = {
                Action: "RelationShipConsent",
                Project: "mcp"
            }
            iibGetMasterDataByType(request).then(Res => {
                let Response = Res.GetMasterDataByTypeResult;
                if (Response.Result === 'true' && Response.ClientProfile) {
                    setRelationShipList(Response.ClientProfile);
                }
            }).catch(error => {
            });
        };

        const handlePurposeChange = (name, value) => {
            setRelateModalData({
                ...relateModalData,
                [name]: {
                    ...relateModalData[name],
                    value: value,
                },
            });
        };

        const handleRadioChange = (event) => {
            setRelateModalData({
                ...relateModalData,
                relationship: {
                    ...relateModalData.relationship,
                    value: event.target.value,
                }
            });
        };

        const validateInput = (event) => {
            const {name, value} = event.target;
            switch (name) {
                case "guardianName":
                    setRelateModalData({
                        ...relateModalData,
                        guardianName: {
                            ...relateModalData.guardianName,
                            error: !value ? "Vui lòng không được để trống" : "",
                        }
                    });
                    if (!value) {
                        guardianNameRef.current.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start',
                        });
                    }
                    break;
                case "phone":
                    setRelateModalData({
                        ...relateModalData,
                        cellPhone: {
                            ...relateModalData.cellPhone,
                            error: !/^0\d{9}$/.test(value) ? "Số điện thoại phải bắt đầu bằng 0 có độ dài 10 chữ số" : "",
                        }
                    });
                    if (!/^0\d{9}$/.test(value)) {
                        cellPhoneRef.current.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start',
                        });
                    }
                    break;
                case "email":
                    setRelateModalData({
                        ...relateModalData,
                        email: {
                            ...relateModalData.email,
                            error: value.length > 0 && !VALID_EMAIL_REGEX.test(value) ? "Email không hợp lệ" : "",
                        }
                    });
                    if (value.length > 0 && !VALID_EMAIL_REGEX.test(value)) {
                        emailRef.current.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start',
                        });
                    }
                    break;
                case "identityCard":
                    setRelateModalData({
                        ...relateModalData,
                        identityCard: {
                            ...relateModalData.identityCard,
                            value: value,
                            error: value.length > 14 ? "CMND/CCCD không được quá 14 chữ số" : "",
                        },
                    });
                    if (value.length > 14) {
                        identityCardRef.current.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start',
                        });
                    }
                    break;
                default:
                    break;
            }
        };

        const disabledDate = (current) => {
            return current && (current > dayjs().endOf('day'));
        }

        const toggleCollapsibleRelate = () => {
            setIsActiveRelate(!isActiveRelate);
        };

        const closeOptionPopupEsc = (event) => {
            if (event.keyCode === 27) {
                closeOptionPopup();
            }
        };

        const closeOptionPopup = () => {
            setOpenPopup(false);
        };

        const onChangePoRelation = (value) => {
            setRelateModalData(prevState => ({
                ...prevState,
                poRelation: {
                    error: '',
                    value: value,
                },
            }));

            const relationCode = relationShipList.find(result => result.RelationName === value);

            if (relationCode) {
                setRelateModalData(prevState => ({
                    ...prevState,
                    poRelationCode: {
                        ...prevState.poRelationCode,
                        value: relationCode.RelationCode,
                    },
                }));
            }
        };


        useEffect(() => {
            getRelationShips();
            document.addEventListener("keydown", closeOptionPopupEsc, false);

            return () => {
                document.removeEventListener("keydown", closeOptionPopupEsc, false);
            };
        }, []);

        return (
            <>
                {isOpenPopup && <div className="popup special show" id="modal-common">
                    <div className="popup__card">
                        <div className="relate-modal-card">
                            <div className="modal-header">
                                <p className="modal-header-title">Thanh toán Quyền lợi bảo hiểm</p>
                                <i className="close-btn"> <img src={iconClose} alt="closebtn" className="btn"
                                                               onClick={closeOptionPopup}/></i>
                            </div>
                            <div className="relate-modal-content">
                                <p className="modal-body-content">Quyền lợi Bảo hiểm Hỗ trợ viện phí/Chăm sóc sức khỏe của
                                    NĐBH dưới 18 tuổi, nếu được chấp thuận, sẽ được chi trả cho Cha/Mẹ/Người giám hộ
                                    của trẻ.</p>
                                <div className="card-border"></div>
                                <p className="modal-body-sub-content">Quý khách vui lòng xác nhận mối quan hệ của
                                    BMBH với NĐBH <span style={{color: '#985801', fontSize: '16px'}}>[tên NĐBH]:</span></p>
                                <div className={`collapsible ${isActiveRelate ? 'active' : ''}`}>
                                    <div className="collapsible-header" onClick={toggleCollapsibleRelate}>
                                        <p className={`modal-body-sub-content ${isActiveRelate ? 'bold-text' : ''}`}>Họ và
                                            tên Người được bảo hiểm</p>
                                        {isActiveRelate ? <img src={arrowDown} alt="arrow-down"/> :
                                            <img src={arrowDown} alt="arrow-left"/>}
                                    </div>
                                    <div className="collapsible-content">
                                        <div className="checkbox-warpper">
                                            <div className="checkbox-item">
                                                <div className="round-checkbox">
                                                    <label className="customradio">
                                                        <input
                                                            type="radio"
                                                            value="relateFamily"
                                                            checked={relateModalData.relationship.value === 'relateFamily'}
                                                            onChange={handleRadioChange}
                                                        />
                                                        <div className="checkmark"></div>
                                                        <p className="text" style={{fontSize:'15px'}}>Cha, mẹ/ Người giám hộ</p>
                                                    </label>
                                                </div>
                                            </div>
                                            <div className="checkbox-item">
                                                <div className="round-checkbox">
                                                    <label className="customradio">
                                                        <input
                                                            type="radio"
                                                            value="relateOther"
                                                            checked={relateModalData.relationship.value === 'relateOther'}
                                                            onChange={handleRadioChange}
                                                        />
                                                        <div className="checkmark"></div>
                                                        <p className="text" style={{fontSize:'15px'}}>Khác</p>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        {relateModalData.relationship.value === 'relateFamily' &&
                                            <div className="body-frame">
                                                <p className="body-frame-title">Bên mua bảo hiểm (BMBH) <span
                                                    className="bold-text">&lt;tên BMBH&gt;</span> đồng ý và cam kết:</p>
                                                <p className="body-frame-content">
                                                    Trong quá trình tư vấn, giao kết, thực hiện HĐBH, giải quyết Quyền lợi
                                                    bảo
                                                    hiểm và
                                                    các công việc khác liên quan, DLVN có trách nhiệm xử lý Dữ liệu cá nhân
                                                    (DLCN) của
                                                    Tôi/Chúng tôi đúng mục đích và tuân thủ Nghị định số 13/2023/NĐ-CP
                                                    ngày
                                                    17/04/2023
                                                    về bảo vệ DLCN. Tôi/Chúng tôi xác nhận đã được DLVN thông báo về việc
                                                    xử lý
                                                    DLCN,
                                                    đã đọc và đồng ý cho phép DLVN được quyền xử lý DLCN bao gồm DLCN cơ
                                                    bản và
                                                    DLCN
                                                    nhạy cảm phù hợp với các mục đích <span style={{fontSize: '12px', fontWeight: '700'}}>(Mục đích: 1- Định danh và nhận biết
                                                    Khách
                                                    hàng;
                                                    2- Giao kết HĐBH; 3- Thực hiện HĐBH; 4 - Quản lý, đánh giá hoạt động
                                                    kinh
                                                    doanh và
                                                    tuân thủ của DLVN)</span> cụ thể tại phần xác nhận bên dưới và toàn bộ nội dung
                                                    của
                                                    Quy
                                                    định bảo vệ và xử lý DLCN được đăng tải trên trang chủ của DLVN:
                                                    https://www.dai-ichi-life.com.vn (“Quy định BV&XLDLCN”).
                                                    Chúng tôi đồng ý ủy quyền cho BMBH sẽ thay mặt Chúng tôi đưa ra yêu cầu/thực hiện các thủ tục về xử lý DLCN của Chúng tôi liên quan đến HĐBH với Dai-ichi Life Việt Nam. 
                                                </p>
                                            </div>}

                                        {relateModalData.relationship.value === 'relateOther' && <div>
                                            <p className="modal-body-content" style={{fontWeight: 500}}>Quý khách vui lòng
                                                cung cấp thông tin liên hệ của
                                                Cha/Mẹ/Người giám hộ của NĐBH để nhận tin về khoản thanh toán, nếu
                                                có:</p>
                                            <div
                                                ref={guardianNameRef}
                                                className={`${relateModalData.guardianName.error ? 'validate' : ''} input mt12`}>
                                                <div className="input__content">
                                                    <label>Họ và tên Cha, mẹ, người giám hộ</label>
                                                    <input
                                                        type="search"
                                                        // placeholder="Họ và tên Cha, mẹ, người giám hộ"
                                                        value={relateModalData.guardianName.value}
                                                        name="guardianName"
                                                        onChange={(e) => setRelateModalData({
                                                            ...relateModalData,
                                                            guardianName: {
                                                                error: '',
                                                                value: e.target.value,
                                                            }
                                                        })}
                                                        onBlur={(event) => validateInput(event)}
                                                    />
                                                </div>
                                                <i><img src="../../../img/icon/edit.svg" alt=""/></i>
                                            </div>
                                            <div
                                                className={`${relateModalData.poRelation.error ? 'validate mb6' : ''} input mt12 relation-select-wrapper`}
                                                style={{
                                                    padding: '8px 12px 8px 16px',
                                                    height: '56px',
                                                    flexDirection: 'column'
                                                }}>
                                                <label style={{width: '100%',}}>Mối quan hệ Cha/Mẹ/Người giám hộ</label>
                                                <Select
                                                    suffixIcon={<SearchOutlined/>}
                                                    showSearch
                                                    size='large'
                                                    style={{
                                                        width: '100%',
                                                        height: '26px',
                                                        display: 'flex',
                                                        alignItems: 'center'
                                                    }}
                                                    bordered={false}
                                                    // placeholder="Mối quan hệ Cha/Mẹ/Người giám hộ"
                                                    optionFilterProp="name"
                                                    onChange={(value) => onChangePoRelation(value)}
                                                    onBlur={() => {
                                                        setRelateModalData({
                                                            ...relateModalData,
                                                            poRelation: {
                                                                ...relateModalData.poRelation,
                                                                error: !relateModalData.poRelation.value ? "Vui lòng không được để trống" : "",
                                                            }
                                                        });

                                                    }}
                                                    value={relateModalData.poRelation.value}
                                                    filterOption={(input, option) =>
                                                        removeAccents(option.name.toLowerCase().replace(/  +/g, ' ')).indexOf(removeAccents(input.toLowerCase().replace(/  +/g, ' '))) >= 0
                                                    }
                                                >
                                                    {relationShipList.map((relation) => (
                                                        <Option key={relation.RelationName}
                                                                name={relation.RelationName}>{relation.RelationName}</Option>
                                                    ))}
                                                </Select>
                                            </div>
                                            {relateModalData.poRelation.error &&
                                                <span style={{color: 'red'}}>{relateModalData.poRelation.error}</span>}
                                            <div
                                                ref={identityCardRef}
                                                className={`${relateModalData.identityCard.error ? 'validate mb6' : ''} input mt12`}>
                                                <div className="input__content">
                                                    <label>CMND/CCCD</label>
                                                    <input
                                                        type="search"
                                                        // placeholder="CMND/CCCD"
                                                        maxLength="14"
                                                        value={relateModalData.identityCard.value}
                                                        name="identityCard"
                                                        onChange={(e) => setRelateModalData({
                                                            ...relateModalData,
                                                            identityCard: {
                                                                ...relateModalData.identityCard,
                                                                value: e.target.value,
                                                            }
                                                        })}
                                                        onBlur={(event) => validateInput(event)}
                                                    />
                                                </div>
                                                <i><img src="../../../img/icon/edit.svg" alt=""/></i>
                                            </div>
                                            {relateModalData.identityCard.error &&
                                                <span style={{color: 'red'}}>{relateModalData.identityCard.error}</span>}
                                            <div
                                                className={`${relateModalData.dateOfBirth.error ? 'validate mb6' : ''} input mt12`}>
                                                <div className="input__content" style={{width: '100%'}}>
                                                    <label>Ngày tháng năm sinh</label>
                                                    <DatePicker placeholder="Ví dụ: 21/07/2019"
                                                                id="relateDateOfBirth" disabledDate={disabledDate}
                                                                value={relateModalData.dateOfBirth.value ? moment(relateModalData.dateOfBirth.value) : ""}
                                                                onChange={(value) => setRelateModalData({
                                                                    ...relateModalData,
                                                                    dateOfBirth: {
                                                                        ...relateModalData.dateOfBirth,
                                                                        value,
                                                                    }
                                                                })}
                                                                format="DD/MM/YYYY" style={{
                                                        width: '100%',
                                                        margin: '0px',
                                                        padding: '0px',
                                                        fontSize: '1.4rem',
                                                        border: '0'
                                                    }}/>
                                                </div>
                                            </div>
                                            <div
                                                className={`${relateModalData.cellPhone.error ? 'validate mb6' : ''} input mt12`}>
                                                <div className="input__content">
                                                    <label>Số điện thoại người được bảo hiểm</label>
                                                    <input value={relateModalData.cellPhone.value?relateModalData.cellPhone.value.trim():''} name="phone"
                                                           id="relateCellPhone"
                                                           onChange={(e) => setRelateModalData({
                                                               ...relateModalData,
                                                               cellPhone: {
                                                                   ...relateModalData.cellPhone,
                                                                   value: e.target.value,
                                                               }
                                                           })}
                                                           onBlur={(event) => validateInput(event)}
                                                           type="search"/>
                                                </div>
                                                <i><img src="../../../img/icon/input_phone.svg"
                                                        alt=""/></i>
                                            </div>
                                            {relateModalData.cellPhone.error &&
                                                <span style={{color: 'red'}}>{relateModalData.cellPhone.error}</span>}
                                            <div
                                                className={`${relateModalData.email.error ? 'validate mb6' : ''} input mt12`}>
                                                <div className="input__content">
                                                    <label>Email Người được bảo hiểm</label>
                                                    <input value={relateModalData.email.value} name="email"
                                                           id="relateEmail"
                                                           onChange={(e) => setRelateModalData({
                                                               ...relateModalData,
                                                               email: {
                                                                   ...relateModalData.email,
                                                                   value: e.target.value,
                                                               }
                                                           })}
                                                           onBlur={(event) => validateInput(event)}
                                                           type="search"/>
                                                </div>
                                                <i><img src="../../../img/icon/input_mail.svg" alt=""
                                                /></i>
                                            </div>
                                            {relateModalData.email.error &&
                                                <span style={{color: 'red'}}>{relateModalData.email.error}</span>}
                                        </div>}
                                    </div>
                                </div>
                                {/*----------------------------------------PO khai báo thông tin liên hệ Cha mẹ/NGH của LI------------------------------------------------------*/}
                                <div className="relate-po-wrapper">
                                    <p className="relate-po-policy-content">
                                        Số tiền yêu cầu &lt; [X], nếu được DLVN chấp thuận chi trả và Quý
                                        khách
                                        chọn Người nhận tiền là Bên mua bảo hiểm, vui lòng xác nhận và
                                        điền
                                        thông tin dưới đây:
                                    </p>
                                    <div
                                        className={acceptPolicy ? "bottom-text choosen" : "bottom-text"}
                                        style={{
                                            'maxWidth': '600px',
                                            display: 'flex'
                                        }}>
                                        <div className={acceptPolicy ? "square-choose fill-red" : "square-choose"}
                                             style={{
                                                 flex: '0 0 auto',
                                                 height: '20px',
                                                 cursor: 'pointer',
                                                 margin: 0
                                             }}
                                             onClick={() => setAcceptPolicy(!acceptPolicy)}>
                                            <div className="checkmark">
                                                <img src="img/icon/check.svg" alt=""/>
                                            </div>
                                        </div>
                                        <div className="accept-policy-wrapper" style={{
                                            paddingLeft: '12px'
                                        }}>
                                            <p>Tôi, Bên mua bảo hiểm, đồng ý và cam kết: Nếu được DLVN chấp
                                                thuận chi
                                                trả
                                                quyền lợi bảo hiểm cho yêu cầu này, Tôi là Bên mua bảo
                                                hiểm, sẽ nhận
                                                số
                                                tiền do DLVN thanh toán theo phương thức thanh toán mà Tôi đã
                                                cung cấp.
                                                Tôi
                                                cam kết chịu trách nhiệm trước pháp luật về việc nhận
                                                tiền này và
                                                miễn
                                                trừ DLVN khỏi các khiếu nại, tranh chấp về việc thanh toán,
                                                nếu
                                                có. </p>
                                        </div>
                                    </div>
                                </div>
                                {/*---------------------------------------------------------Footer------------------------------------------------------------*/}
                                <div className="btn-wrapper">
                                    <button className="btn btn-confirm" onClick={onClickConfirmBtn}>Tiếp tục</button>
                                    <button className="btn btn-callback" onClick={onClickCallBack}>Tôi không đủ thông tin
                                        cung cấp
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="popupbg"></div>
                </div>}
            </>
        )
            ;
    }
;

export default PaymentPOPersonalInfo;

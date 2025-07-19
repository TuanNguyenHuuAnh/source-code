import React, {useState, useEffect} from 'react';
import Slider from 'react-slick';
import './CustomSwiper.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import {Link} from "react-router-dom";
import {compareDate, formatDate, isMobile, shortFormatDate,getLinkPartner, getSession} from '../../util/common';
import {isEmpty} from "lodash";
import iconGreenCheck from '../../img/icon/iconGreenCheck.svg';

import '../../pages/DoctorList/DoctorPage.css';
import iconStar from '../../pages/DoctorList/iconStar.svg';
import {ACCESS_TOKEN, EDOCTOR_ID, FE_BASE_URL, SCREENS} from "../../constants";
import GeneralPopup from "../GeneralPopup";


const NextArrow = (props) => {
    const {onClick, currentIndex, totalSlides, slidesToShow} = props;
    const isDisabled = currentIndex + slidesToShow >= totalSlides;

    return (<div
        className='nextArrow'
        onClick={!isDisabled ? onClick : null}
        style={{marginLeft: '8px'}} // Add the extra spacing here
    >
            <span className="nav-next-icon"><i
                className={`ico ${isDisabled ? 'icon-arrow-right-disabled' : 'icon-arrow-right'} ico__large`}></i></span>
    </div>);
};


const PrevArrow = (props) => {
    const {onClick, currentIndex, totalSlides, slidesToShow} = props;
    const isDisabled = currentIndex === 0;
    return (<div
        className='prevArrow'
        onClick={!isDisabled ? onClick : null}
        style={{marginRight: '8px'}} // Add the extra spacing here
    >
            <span className="nav-prev-icon"><i
                className={`ico ${isDisabled ? 'icon-arrow-left-disabled' : 'icon-arrow-left'} ico__large`}></i></span>
    </div>);
};

const DoctorList2 = (props) => {
    const cardArray = Array.from({length: 3}, (_, index) => index + 1);

    const cardStyle = {
        width: '347px',
        height: '313px',
        padding: '0',
        border: '1px solid #F0F0F0',
        borderRadius: '8px',
        overflow: 'hidden',
    };

    const cardWrapperStyle = {
        padding: '8px 8px 16px 8px',
    };

    const imageSkeletonStyle = {
        width: '100%', height: '165px', backgroundColor: '#F0F0F0',
    };

    const textSkeletonStyle1 = {
        width: '80%', height: '16px', backgroundColor: '#F0F0F0', marginBottom: '8px',
    };

    const textSkeletonStyle2 = {
        width: '60%', height: '16px', backgroundColor: '#F0F0F0', marginBottom: '4px',
    };


    const buttonStyle = {
        width: '100%', height: '40px', marginTop: '12px', backgroundColor: '#F0F0F0', borderRadius: '6px',
    };

    const containerStyle = {
        display: 'flex', gap: '16px',
    };


    const {data} = props;
    const [currentIndex, setCurrentIndex] = useState(0);
    const [sliderRef, setSliderRef] = useState(null);


    const [expandedIndexes, setExpandedIndexes] = useState([]);
    const [showRequireLogin, setShowRequireLogin] = useState(false);
    const [doctorId, setDoctorId] = useState('');
    
    const mapSpecialtyToVietnamese = (specialty) => {
        const specialties = {
            tieu_hoa: 'Tiêu hóa',
            tim_mach: 'Tim mạch',
            phuc_hoi_chuc_nang: 'Phục hồi chức năng',
            noi_tiet: 'Nội tiết',
            rang_ham_mat: 'Răng hàm mặt',
            dinh_duong: 'Dinh dưỡng',
            tiet_nieu: 'Tiết niệu',
            nam_khoa: 'Nam khoa',
            mat: 'Mắt',
            ung_thu: 'Ung thư',
            da_lieu: 'Da liễu',
            nhi_khoa: 'Nhi khoa',
            y_hoc_co_truyen: 'Y học cổ truyền',
            noi_khoa: 'Nội khoa',
            ho_hap: 'Hô hấp',
            tai_mui_hong: 'Tai mũi họng',
            di_ung: 'Dị ứng',
            than_kinh_co_xuong_khop: 'Thần kinh cơ xương khớp',
            truyen_nhiem: 'Truyền nhiễm',
            ngoai_khoa: 'Ngoại khoa',
            y_hoc_the_thao: 'Y học thể thao',
            san_phu_khoa: 'Sản phụ khoa',
            tam_ly_tam_than: 'Tâm lý tâm thần',
            tam_ly: 'Tâm lý',
            tham_my: 'Thẩm mỹ',
            lao: 'Lao',
            lao_khoa: 'Lão khoa',
            ngoai_than_kinh: 'Ngoại thần kinh',
            ngoai_long_nguc: 'Ngoại lòng ngực',
            chan_thuong_chinh_hinh: 'Chấn thương chỉnh hình',
            bong: 'Bỏng',
            phau_thuat_gay_me_hoi_suc: 'Phẫu thuật gây mê hồi sức',
            hoi_suc_cap_cuu: 'Hồi sức cấp cứu',
            huyet_hoc: 'Huyết học',
            chan_doan_hinh_anh: 'Chẩn đoán hình ảnh',
            chong_doc: 'Chống độc',
            bac_si_gia_dinh: 'Bác sĩ gia đình',
            da_khoa: 'Đa khoa',
        };

        return specialties[specialty] || specialty;
    };

    const settings = {
        ref: (slider) => setSliderRef(slider),
        centerMode: false,
        infinite: false,
        dots: false,
        autoplay: false,
        // speed: 50,
        slidesToShow: 1,//(data && data.length < 3) ? data.length : 3,
        slidesToScroll: 1,
        centerPadding: '0',
        swipeToSlide: true,
        // focusOnSelect: true,
        // centerPadding: "200px",
        initialSlide: 0,
        nextArrow: !isMobile() ? (<NextArrow currentIndex={currentIndex} totalSlides={data.length}
                                             slidesToShow={(data && data.length < 3) ? data.length : 1}/>) : null,
        prevArrow: !isMobile() ? (<PrevArrow currentIndex={currentIndex} totalSlides={data.length}
                                             slidesToShow={(data && data.length < 3) ? data.length : 1}/>) : null,
        initialSlide: 0,
        afterChange: (current) => {
            setCurrentIndex(current);
        },
        responsive: [{
            breakpoint: 300, settings: {
                slidesToShow: 1.423, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
                initialSlide: 1,
            },
        },{
            breakpoint: 400, settings: {
                slidesToShow: 1.6, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
                initialSlide: 1,
            },
        },{
            breakpoint: 415, settings: {
                slidesToShow: 1.7, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
                initialSlide: 1,
            },
        },{
            breakpoint: 450, settings: {
                slidesToShow: 1.75, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
                initialSlide: 1,
            },
        },{
            breakpoint: 768, settings: {
                slidesToShow: 1.7, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
                initialSlide: 1,
            },
        }, {
            breakpoint: 820, settings: {
                slidesToShow: 1,  // Adjust the number of slides as needed
                slidesToScroll: 1,
            },
        }, {
            breakpoint: 1180, settings: {
                slidesToShow: (data && data.length < 3) ? data.length : 3, slidesToScroll: 1,
            },
        }, {
            breakpoint: 1200, settings: {
                slidesToShow: (data && data.length < 4) ? data.length : 4, slidesToScroll: 1,
            },
        },],
    };

    const handleToggleExpand = (index) => {
        console.log("expandedIndexes", expandedIndexes);
        if (expandedIndexes.includes(index)) {
            // Nếu index đã được mở rộng, thì đóng tất cả các chuyên môn của index đó
            setExpandedIndexes(expandedIndexes.filter((item) => item !== index));
        } else {
            // Nếu index chưa được mở rộng, thì mở rộng tất cả các chuyên môn của index đó và đóng tất cả các chuyên môn khác
            setExpandedIndexes([index]);
        }
    };

    function convertToSlug(text) {
        return text.toLowerCase().trim()
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "") // Loại bỏ dấu thanh
            .replace(/đ/g, "d") // Thay thế d đặc biệt
            .replace(/[^a-z0-9\s-]/g, "") // Loại bỏ các ký tự đặc biệt, trừ dấu gạch ngang, dấu cách và số
            .replace(/\s+/g, "-") // Thay thế khoảng trắng bằng dấu gạch ngang
            .replace(/-+/g, "-"); // Loại bỏ các dấu gạch ngang liên tiếp
    }

    const handleRedirectSpecialist = (value) => {
        console.log(value);
        // setTimeout(() => {
        //     window.location.href = `${FE_BASE_URL + '/tu-van-suc-khoe/bac-si/' + convertToSlug(value.fullName) + '-' + value.doctorId}`;
        // }, 500);
        // console.log(value);
        // setTimeout(() => {
        //     window.location.href = `${FE_BASE_URL + '/tu-van-suc-khoe/tu-van-tu-xa?id=' + value}`;
        // }, 500);
    };

    const clickExternal = (doctorId) => {
        console.log("clickExternal");
        if (!getSession(ACCESS_TOKEN)) {
            setDoctorId(doctorId);
            setShowRequireLogin(true);
        } else {
            setTimeout(() => {
                getLinkPartner(EDOCTOR_ID, FE_BASE_URL + `/tu-van-suc-khoe/tu-van-tu-xa?iddr=${doctorId}`);
            }, 500);
        }
    }
    const closeRequireLogin = () => {
        setShowRequireLogin(false);
    }

    const goToLink = (url) => {
        window.location.href = url;
    }

return (<div
    className={`custom-slider-wrapper padding-slider-challenge ${data.length === 1 ? 'padding-slider-challenge-mobile-1' : 'padding-slider-doctor-mobile'}`}>
    {!isEmpty(data) ? <>
            {!isMobile() ? <div className="doctor-slider">
                {data && data.map((item, index) => {
                    return (<div className="article-item doctor-item__border" key={index}>
                        <div className="doctor-item-right">
                            <div className="doctor-item-content" onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                <a
                                    href={FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`}
                                    className="doctor-thumb" onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)} id="doctor-info">
                                    <img src={item.avatar} alt="" className=""/>
                                </a>
                                <div style={{width: '100%', 
                                            justifyContent : 'center',
                                            display : 'flex',
                                            position : 'relative',
                                            top : '-15px'}}>
                                <div className="doctor-rating">
                                    <img src={iconStar} alt="iconStar"/>
                                    <span>{item.rating && item.rating.length === 3?item.rating:item.rating + '  '}</span>
                                </div>
                                </div>
                                <p className="doctor-title">{item?.title ? item?.title : 'BS.'}</p>
                                <a
                                    href={FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`}
                                    className="doctor-fullname" onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                    <p className="doctor-fullname">{item.fullName}</p>
                                </a>
                                <p className="doctor-working">{item?.workingYear ? item?.workingYear : '0'} năm kinh nghiệm</p>
                                <div className="doctor-specialist-wrapper">
                                    {item.medicalSpecialties && (expandedIndexes.includes(index) ? (<>
                                        {item.medicalSpecialties.map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                               /*onClick={() => handleRedirectSpecialist(specialty)}*/>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 2 && (<div className="doctor-circle"
                                                                                     onClick={() => handleToggleExpand(index)}>
                                            ...
                                        </div>)}
                                    </>) : (<>
                                        {item.medicalSpecialties.slice(0, 2).map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                               /*onClick={() => handleRedirectSpecialist(specialty)}*/>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 2 && (<div className="doctor-circle"
                                                                                     onClick={() => handleToggleExpand(index)}>
                                            ...
                                        </div>)}
                                    </>))}
                                </div>
                            </div>
                            <div style={{ marginTop: 8 }}></div>
                            <button className="btn btn-primary make-appointment"
                                    onClick={() => clickExternal(item.doctorId)}>
                                Đặt hẹn tư vấn
                            </button>
                        </div>
                    </div>);
                })}
            </div> : <Slider {...settings} className="custom-slider-content slider-public-challenge">
                {data && data.map((item, index) => {
                    return (<div className="article-item doctor-item__border" key={index}>
                        <div className="doctor-item-right">
                            <div className="doctor-item-content">
                                <a
                                    href={FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`}
                                    className="doctor-thumb">
                                    <img src={item.avatar} alt="" className=""/>
                                </a>
                                <div style={{width: '100%', 
                                            justifyContent : 'center',
                                            display : 'flex',
                                            position : 'relative',
                                            top : '-15px'}}>
                                <div className="doctor-rating">
                                    <img src={iconStar} alt="iconStar"/>
                                    <span>{item.rating && item.rating.length === 3?item.rating:item.rating + '  '}</span>
                                </div>
                                </div>
                                <p className="doctor-title">{item?.title ? item?.title : 'BS. Chuyên khoa 2'}</p>
                                <a
                                    href={FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`}
                                    className="doctor-fullname" onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                    <p className="doctor-fullname">{item.fullName}</p>
                                </a>
                                <p className="doctor-working">{item?.workingYear ? item?.workingYear : '0'} năm kinh nghiệm</p>
                                <div className="doctor-specialist-wrapper">
                                    {item.medicalSpecialties && (expandedIndexes.includes(index) ? (<>
                                        {item.medicalSpecialties.map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                            onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 1 && (<div className="doctor-circle"
                                                                                     >
                                            ...
                                        </div>)}
                                    </>) : (<>
                                        {item.medicalSpecialties.slice(0, 1).map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                            onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 1 && (<div className="doctor-circle"
                                                                                     >
                                            ...
                                        </div>)}
                                    </>))}
                                </div>
                            </div>
                            <button className="btn btn-primary make-appointment"
                                    onClick={() => clickExternal(item.doctorId)}>
                                Đặt hẹn tư vấn
                            </button>
                        </div>
                    </div>);
                })}
            </Slider>}
        </> : <div style={containerStyle}>
        {cardArray.map((cardNumber) => (<div key={cardNumber} style={cardStyle}>
            {/* Hình ảnh skeleton */}
            <div style={imageSkeletonStyle}></div>

            <div style={cardWrapperStyle}>
                {/* Dòng văn bản 1 */}
                <div style={textSkeletonStyle1}></div>

                {/* Dòng văn bản 2 */}
                <div style={textSkeletonStyle2}></div>

                {/* Dòng văn bản 3 */}
                <div style={textSkeletonStyle2}></div>

                {/* Button */}
                <button style={buttonStyle}></button>
            </div>
        </div>))}
    </div>}
    {showRequireLogin && <GeneralPopup closePopup={closeRequireLogin}
                                           msg={'Quý khách vui lòng đăng nhập<br/>Dai-ichi Connect để sử dụng tính năng này.'}
                                           imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'}
                                           buttonName={'Đăng nhập'}
                                           linkToGo={SCREENS.HOME} screenPath={SCREENS.HEALTH}
                                           doctorId = {doctorId}/>}
</div>);
};

export default DoctorList2;

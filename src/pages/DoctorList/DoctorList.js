import React, {useState, useEffect} from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './DoctorPage.css';
import {Link} from 'react-router-dom';
import iconStar from './iconStar.svg';
import {ACCESS_TOKEN, EDOCTOR_ID, FE_BASE_URL, SCREENS} from "../../constants";
import GeneralPopup from "../../components/GeneralPopup";
import {getLinkPartner, getSession} from "../../util/common";
import {isEmpty} from "lodash";

const DoctorList = (props) => {
    const {data} = props;
    const [expandedIndexes, setExpandedIndexes] = useState([]);
    const [showRequireLogin, setShowRequireLogin] = useState(false);
    const [sliderRef, setSliderRef] = useState(null);
    const [currentIndex, setCurrentIndex] = useState(0);
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

    const isMobile = () => {
        const userAgent = window.navigator.userAgent;
        return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini|Mobile|mobile/i.test(userAgent);
    };

    const settings = {
        ref: (slider) => setSliderRef(slider),
        centerMode: false,
        infinite: false,
        dots: false,
        autoplay: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        centerPadding: '0',
        swipeToSlide: true,
        focusOnSelect: true,
        initialSlide: 0,
        afterChange: (current) => {
            setCurrentIndex(current);
        },
        responsive: [{
            breakpoint: 300, settings: {
                slidesToShow: 1, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        },{
            breakpoint: 400, settings: {
                slidesToShow: 1, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        },{
            breakpoint: 768, settings: {
                slidesToShow: 1, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        }, {
            breakpoint: 820, settings: {
                slidesToShow: 1, slidesToScroll: 1
            },
        }, {
            breakpoint: 1180, settings: {
                slidesToShow: data && data.length < 3 ? data.length : 3, slidesToScroll: 1,
                infinite: true
            },
        }, {
            breakpoint: 1200, settings: {
                slidesToShow: data && data.length < 3 ? data.length : 3, slidesToScroll: 1,
                infinite: true
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
    // useEffect(() => {

    // }, []);

    const skeletonItems = Array.from({length: 4}).map((_, index) => (
        <div className="article-item doctor-item__border" key={index}>
            <div className="doctor-item-right">
                <div className="doctor-item-content">
                    <div className="doctor-thumb skeleton">
                        <div className="doctor-skeleton-image"></div>
                    </div>
                    <div className="doctor-title skeleton-text skeleton"></div>
                    <div className="doctor-fullname skeleton-text skeleton"></div>
                    <div className="doctor-working skeleton-text skeleton"></div>
                    <div className="doctor-specialist-wrapper">
                        <div className="doctor-specialist skeleton"></div>
                        <div className="doctor-specialist skeleton"></div>
                        <div className="doctor-specialist skeleton"></div>
                    </div>
                </div>
                <div className="btn btn-primary make-appointment skeleton" style={{
                    background: '#f0f0f0',
                }}></div>
            </div>
        </div>));


    return (<div className={`custom-slider-wrapper padding-slider-challenge ${data.length === 1 ? 'padding-slider-challenge-mobile-1' : 'padding-slider-doctor-mobile'}`}>
        {isEmpty(data) ? <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
        }}>{skeletonItems}</div> : <>
            {!isMobile() ? <div className="doctor-slider">
                {data && data.slice(0, 4).map((item, index) => {
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
                                    <span>{item.rating}</span>
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
            </div> : <Slider {...settings} className="doctor-slider">
                {data && data.slice(0, 4).map((item, index) => {
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
                                    <span>{item.rating}</span>
                                </div>
                                </div>
                                <p className="doctor-title">{item?.title ? item?.title : 'BS. Chuyên khoa 2'}</p>
                                <a
                                    href={FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`}
                                    className="doctor-fullname" onClick={()=>goToLink(FE_BASE_URL + `${'/tu-van-suc-khoe/bac-si/' + convertToSlug(item.fullName) + '-' + item.doctorId}`)}>
                                    <p className="doctor-fullname">{item.fullName}</p>
                                </a>
                                <p className="doctor-working">{item?.working ? item?.working : 'BV Đại học Y dược TP.HCM'}</p>
                                <div className="doctor-specialist-wrapper">
                                    {item.medicalSpecialties && (expandedIndexes.includes(index) ? (<>
                                        {item.medicalSpecialties.map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                               onClick={() => handleRedirectSpecialist(specialty)}>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 2 && (<div className="doctor-circle"
                                                                                     onClick={() => handleToggleExpand(index)}>
                                            ...
                                        </div>)}
                                    </>) : (<>
                                        {item.medicalSpecialties.slice(0, 2).map((specialty, specialtyIndex) => (
                                            <p className="doctor-specialist" key={specialtyIndex}
                                               onClick={() => handleRedirectSpecialist(specialty)}>
                                                {mapSpecialtyToVietnamese(specialty)}
                                            </p>))}
                                        {item.medicalSpecialties.length > 2 && (<div className="doctor-circle"
                                                                                     onClick={() => handleToggleExpand(index)}>
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
        </>}

        {showRequireLogin && <GeneralPopup closePopup={closeRequireLogin}
                                           msg={'Quý khách vui lòng đăng nhập<br/>Dai-ichi Connect để sử dụng tính năng này.'}
                                           imgPath={FE_BASE_URL + '/img/popup/health-require-login.svg'}
                                           buttonName={'Đăng nhập'}
                                           linkToGo={SCREENS.HOME} screenPath={SCREENS.HEALTH}
                                           doctorId = {doctorId}/>}
    </div>);
};

export default DoctorList;

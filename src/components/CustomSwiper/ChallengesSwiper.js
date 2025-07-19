import React, {useState, useEffect} from 'react';
import Slider from 'react-slick';
import './CustomSwiper.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import {Link} from "react-router-dom";
import {compareDate, formatDate, isMobile, shortFormatDate,} from '../../util/common';
import {isEmpty} from "lodash";
import iconGreenCheck from '../../img/icon/iconGreenCheck.svg';

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

const ChallengesSwiper = (props) => {
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

    const settings = {
        ref: (slider) => setSliderRef(slider),
        centerMode: false,
        infinite: false,
        dots: false,
        autoplay: false,
        speed: 500,
        slidesToShow: 1,//(data && data.length < 3) ? data.length : 3,
        slidesToScroll: 1,
        centerPadding: '0',
        swipeToSlide: true,
        focusOnSelect: true,
        nextArrow: !isMobile() ? (<NextArrow currentIndex={currentIndex} totalSlides={data.length}
                                             slidesToShow={(data && data.length < 3) ? data.length : 3}/>) : null,
        prevArrow: !isMobile() ? (<PrevArrow currentIndex={currentIndex} totalSlides={data.length}
                                             slidesToShow={(data && data.length < 3) ? data.length : 3}/>) : null,
        initialSlide: 0,
        afterChange: (current) => {
            setCurrentIndex(current);
        },
        responsive: [{
            breakpoint: 300, settings: {
                slidesToShow: 1, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        },{
            breakpoint: 768, settings: {
                slidesToShow: 1, // slidesToShow: 1,
                slidesToScroll: 1, nextArrow: null, prevArrow: null,
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
                slidesToShow: (data && data.length < 3) ? data.length : 3, slidesToScroll: 1,
            },
        },],
    };

    const showQRC = (event) => {
        event.preventDefault(); // Ngăn chặn hành động mặc định của nút

        if (isMobile()) {
            document.getElementById('popup-DownloadnExperience').className = "popup special point-error-popup show";
        } else {
            document.getElementById('popup-QRC').className = "popup special point-error-popup show";
        }

    }

    function daysBetween(startDate, endDate) {
        // Chuyển đổi các ngày thành số mili giây
        const start = new Date(startDate).getTime();
        const end = new Date(endDate).getTime();

        // Tính số mili giây giữa hai ngày và chia cho số mili giây trong một ngày
        const difference = Math.abs(end - start);
        const oneDay = 1000 * 60 * 60 * 24;

        // Trả về số ngày
        return Math.round(difference / oneDay);
    }

    // useEffect(() => {
    //     console.log('currentIndex=' + currentIndex);
    //     console.log('sliderRef=', sliderRef);
    //     if (currentIndex > 3) {
    //         sliderRef.slickPrev();
    //         setCurrentIndex(3);
    //     }
    // }, [currentIndex, sliderRef]);
    return (<div
        className={`custom-slider-wrapper padding-slider-challenge ${data.length === 1 ? 'padding-slider-challenge-mobile-1' : 'padding-slider-challenge-mobile'}`}>
        {!isEmpty(data) ? <Slider {...settings} className="custom-slider-content slider-public-challenge">
            {!isEmpty(data) && data.map((item, index) => {
                let path = '/song-vui-khoe/thu-thach-song-khoe/' + item._id;

                return (
                    <div className="article-item article-item__border" key={index}>
                        <Link to={path} className="article-thumb">
                            <div style={{position: 'relative'}}>
                                <img src={item.imageUrl} alt=""/>
                                <p className="article-item-tag">Cá nhân</p>
                            </div>
                            <div className="article-item-right">
                                <p><Link to={path}
                                         className={"article-title line-clamp-challenge"}>{item.title}</Link>
                                </p>
                                <div className="article-nav">

                                    <span className="article-nav-item grid-event"
                                          style={{}}>
                                        <i className="ico ico-num-persons" style={{marginBottom: 4}}></i> <span
                                        className='margin-event'>{item.numberOfParticipants ? item.numberOfParticipants + ' người tham gia ' : '0 người tham gia'}</span>
                                        <i className="ico ico-event-date"></i>
                                        {item.end_date ? (compareDate(item.start_date, new Date()) > 0 ? (
                                            <span className='margin-event'>
        {item.start_date ? shortFormatDate(item.start_date) : ''}{' - ' + formatDate(item.end_date)}
                                                {' (còn lại ' + daysBetween(new Date(), item.end_date) + ' ngày)'}
    </span>) : (<span className='margin-event'>
            {item.start_date ? shortFormatDate(item.start_date) : ''}{' - ' + formatDate(item.end_date)}
                                            {' (bắt đầu sau ' + daysBetween(item.start_date, new Date()) + ' ngày nữa)'}
        </span>)) : (<span className='margin-event'>
        {item.start_date ? formatDate(item.start_date) : ''}
    </span>)}
                                    </span>

                                    <a href="#"
                                       className="article-nav-item">{/*<i className="ico ico-bookmark-g"></i> <span>Lưu</span>*/}</a>
                                </div>
                                <button
                                    className={`btn btn-primary challenge-btn-join ${item.isEnrolled ? 'enrolled' : ''}`}
                                    onClick={showQRC}
                                >
                                    {item.isEnrolled && <img src={iconGreenCheck} alt="iconGreenCheck"
                                         style={{
                                             width: '18px',
                                             height: '18px',
                                             flexShrink: 0,
                                             border: 'none',
                                             marginRight: 8,
                                         }}/>}
                                    {item.isEnrolled ? 'Đã tham gia' : 'Tham gia'}
                                </button>
                            </div>
                        </Link>
                    </div>
                )
            })}
        </Slider> : <div style={containerStyle}>
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
    </div>);
};

export default ChallengesSwiper;

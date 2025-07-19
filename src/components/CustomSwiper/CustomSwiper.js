import React, {useEffect, useState} from 'react';
import Slider from 'react-slick';
import './CustomSwiper.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import {Link} from "react-router-dom";

const NextArrow = (props) => {
    const { onClick, currentIndex, totalSlides,slidesToShow } = props;
    const isDisabled = currentIndex + slidesToShow >= totalSlides;

    return (
        <div
            className='nextArrow'
            onClick={!isDisabled ? onClick : null}
        >
            <span className="nav-next-icon"><i className={`ico ${isDisabled ? 'icon-arrow-right-disabled' : 'icon-arrow-right'} ico__large`}></i></span>
        </div>
    );
};


const PrevArrow = (props) => {
    const { onClick, currentIndex, totalSlides, slidesToShow } = props;
    const isDisabled = currentIndex === 0;
    return (
        <div
            className='prevArrow'
            onClick={!isDisabled ? onClick : null}
        >
            <span className="nav-prev-icon"><i className={`ico ${isDisabled ? 'icon-arrow-left-disabled' : 'icon-arrow-left'} ico__large`}></i></span>
        </div>
    );
};

const CustomSwiper = (props) => {
    const {data} = props;
    const [currentIndex, setCurrentIndex] = useState(0);
    const [isMobile, setIsMobile] = useState(0);

    // const listItems = [
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Cơ xương khớp',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Da liễu',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Tim mạch',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Vật lý trị liệu',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Thần kinh',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Ngoại tổng hợp',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Cơ xương khớp 1',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Da liễu 1',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Tim mạch 1',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Vật lý trị liệu 1',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Thần kinh 1',
    //     },
    //     {
    //         icon: 'ico ico-heartbeat ico__large',
    //         name: 'Ngoại tổng hợp 1',
    //     },
    // ];

    const settings = {
        centerMode: false,
        infinite: false,
        dots: true,
        autoplay: false,
        speed: 500,
        slidesToShow: 6,
        slidesToScroll: 1,
        centerPadding: '0',
        swipeToSlide: true,
        focusOnSelect: true,
        nextArrow: !isMobile ? (
            <NextArrow currentIndex={currentIndex} totalSlides={data.length} slidesToShow={6} />
        ) : null,
        prevArrow: !isMobile ? (
            <PrevArrow currentIndex={currentIndex} totalSlides={data.length} slidesToShow={6} />
        ) : null,
        initialSlide: 0,
        afterChange: (current) => {
            setCurrentIndex(current);
        },
        responsive: [
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                    nextArrow: null,
                    prevArrow: null,
                },
            },
            {
                breakpoint: 1200,
                settings: {
                    slidesToShow: 6,
                    slidesToScroll: 1,
                },
            },
        ],
    };

    useEffect(() => {
        const handleResize = () => {
            const mobile = window.innerWidth <= 768;
            if (mobile !== isMobile) {
                setIsMobile(mobile);
            }
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, [isMobile]);

    return (
        <div className="custom-slider-wrapper">
            <Slider {...settings} className="custom-slider-content">
                {data?.map((itemSlider, index) => (
                    <div key={index} className="custom-slider-item">
                        <Link to={`/tu-van-suc-khoe/hoi-dap/chu-de/${itemSlider?.slug}`} className="swiper-slide nav-cate-item">
              <span className="nav-icon">
                {itemSlider.avantar ? <i className={`ico ico-${itemSlider.avantar} ico__large`}></i> : <i className='ico ico-ico ico-heartbeat ico__large'></i>}
              </span>
                            <h2 className="nav-label">{itemSlider?.name}</h2>
                        </Link>
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default CustomSwiper;

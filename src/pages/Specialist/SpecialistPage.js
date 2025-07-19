import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './SpecialistPage.css';
import {isEmpty} from "lodash";
import {FE_BASE_URL} from '../../constants';

const NextArrow = (props) => {
    const {onClick, currentIndex, totalSlides, slidesToShow} = props;
    // const isDisabled = currentIndex + 3 >= specialist.length;
    const isDisabled = currentIndex + slidesToShow >= totalSlides;

    return (<div className={`nextArrow ${isDisabled ? 'swiper-button-disabled' : ''}`}
                 onClick={!isDisabled ? onClick : null}>
                <span className="nav-next-icon"><i
                    className={`ico ${isDisabled ? 'icon-arrow-right-disabled' : 'icon-arrow-right'} ico__large`}></i></span>
    </div>);
};

const PrevArrow = (props) => {
    const {onClick, currentIndex, totalSlides, slidesToShow} = props;

    const isDisabled = currentIndex === 0;

    return (<div className={`prevArrow ${isDisabled ? 'swiper-button-disabled' : ''}`}
                 onClick={!isDisabled ? onClick : null}>
                <span className="nav-prev-icon"><i
                    className={`ico ${isDisabled ? 'icon-arrow-left-disabled' : 'icon-arrow-left'} ico__large`}></i></span>
    </div>);
};


const SpecialistPage = (props) => {
    const {specialist} = props;
    const [currentIndex, setCurrentIndex] = useState(0);
    const [sliderRef, setSliderRef] = useState(null);
    const [isMobile, setIsMobile] = useState(0);

    const settings = {
        ref: (slider) => setSliderRef(slider),
        centerMode: false,
        infinite: true,
        dots: false,
        autoplay: false,
        speed: 500,
        slidesToShow: 6,
        slidesToScroll: 1,
        centerPadding: '0',
        swipeToSlide: true,
        focusOnSelect: true,
        nextArrow: <NextArrow currentIndex={currentIndex} totalSlides={specialist.length} slidesToShow={6}/>,
        prevArrow: <PrevArrow currentIndex={currentIndex} totalSlides={specialist.length} slidesToShow={6}/>,
        initialSlide: 0,
        afterChange: (current) => {
            setCurrentIndex(current);
        },
        responsive: [{
            breakpoint: 768, settings: {
                slidesToShow: 4, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        }, {
            breakpoint: 820, settings: {
                slidesToShow: 4, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        }, {
            breakpoint: 1180, settings: {
                slidesToShow: 6, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        }, {
            breakpoint: 1200, settings: {
                slidesToShow: 6, slidesToScroll: 1, nextArrow: null, prevArrow: null,
            },
        },],
    };

    useEffect(() => {
        console.log('--------------ssss-------------');
        console.log(specialist);
        if (sliderRef) {
            let recordsPerPage;
            if (window.innerWidth <= 768) {
                recordsPerPage = 4;
            } else {
                recordsPerPage = 6;
            }
            const totalRecords = specialist.length;

            let progressPercentage;
            progressPercentage = 30;
            let progressMargin = 0;
            if (isMobile) {
                if (currentIndex === totalRecords - recordsPerPage) {
                    progressMargin = 25;
                } else {
                    progressMargin = ((currentIndex + 1) / (totalRecords - recordsPerPage + 1)) * 25;
                }
                if (progressMargin > 25) {
                    progressMargin = 25;
                }
                if (progressMargin < 3) {
                    progressMargin = 0;
                }
            } else {
                if (currentIndex === totalRecords - recordsPerPage) {
                    progressMargin = 30;
                } else {
                    progressMargin = ((currentIndex + 1) / (totalRecords - recordsPerPage + 1)) * 25;
                }
                if (progressMargin > 32) {
                    progressMargin = 32;
                }
                if (progressMargin <= 6.25) {
                    progressMargin = 0;
                }
            }

            const dragElement = document.querySelector('.custom-scrollbar-drag');

            dragElement.style.width = `${progressPercentage}%`;
            dragElement.style.marginLeft = `${progressMargin}px`;
        }
    }, [currentIndex, specialist.length, sliderRef]);

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

    const skeletonItems = Array.from({length: 6}).map((_, index) => (
        <div className="tag-item tag-item__border" key={index}>
            <div className="tag-thumb">
                <div className="nav-icon skeleton">
                    <div className="specialist-skeleton-image skeleton"></div>
                </div>
            </div>
            <div className="specialist-skeleton-text skeleton" style={{ margin: '0 auto', width: '130px', }}></div>
        </div>));

    return (<>
            <div
                className='padding-slider-tag'>
                {isEmpty(specialist) ? <Slider {...settings} className="custom-slider-content">
                    {skeletonItems}
                </Slider> :
                    <Slider {...settings} className="custom-slider-content">
                    {specialist.map(item => (<div className="tag-item tag-item__border" key={item.tagId}>
                        <a className="tag-thumb"
                              href={FE_BASE_URL + `${`/tu-van-suc-khoe/tu-van-tu-xa?id=${item.tag}`}`}>
                            <span className="nav-icon"><img src={item.avatar} alt={item.tagId}/></span>
                            <span className="nav-label">{item.name}</span>
                        </a>
                    </div>))}
                </Slider>}

            </div>
            <div className="custom-scrollbar custom-scrollbar-horizontal">
                <div className="custom-scrollbar-drag"></div>
            </div>
            {/*{!isEmpty(specialist) && specialist.length > 6 &&*/}
            {/*    <div className="custom-scrollbar custom-scrollbar-horizontal">*/}
            {/*        <div className="custom-scrollbar-drag"></div>*/}
            {/*    </div>}*/}
        </>

    );
};

export default SpecialistPage;


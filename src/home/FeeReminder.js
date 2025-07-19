import React from 'react';
import {Link} from 'react-router-dom';
import {Swiper, SwiperSlide} from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {Autoplay, Navigation, Pagination} from 'swiper';
import './FeeReminder.css';

// Import Swiper styles


SwiperCore.use([Autoplay, Pagination, Navigation]);

class FeeReminder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentIndex: 0,
            isMobile: window.innerWidth <= 768,
        };
    }


    handleResize = () => {
        this.setState({
            isMobile: window.innerWidth <= 768,
        });
    };

    componentDidMount() {
        window.addEventListener('resize', this.handleResize);
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.handleResize);
    }

    render() {
        const {currentIndex} = this.state;

        const callbackApp = (hideMain) => {
            this.props.parentCallback(hideMain);
        }

        const handleSlideChange = (swiper) => {
            this.setState({currentIndex: swiper.activeIndex});
        };
        //console.log("ClientProfile", this.props.cmdfeereminder.ClientProfile);
        let itemcount = 0;
        let itemList = [];
        if (!this.props.cmdfeereminder) {
            return "";
        } else if (this.props.cmdfeereminder.ClientProfile) {
            this.props.cmdfeereminder.ClientProfile.forEach((item, index, array) => {
                itemcount = index;
                if (item?.PolicyClassCD && item?.PolicyClassCD === "ePolicy") {
                    itemList.push(<SwiperSlide>
                        <div className="folder__item swiper-slide">
                            <div className="greycard">
                                <div className="greycard__content">
                                    <h4 className="basic-bold">{item.ProductName}</h4>
                                    <p>
                                        Dành cho <span
                                        className="basic-black">{item.PolicyLIName}</span>{", với hợp đồng "}
                                        <span className="basic-red">{item.PolicyID}</span> {"phát hành ngày "}
                                        <span className="basic-red">{item.PolIssEffDate}</span>
                                    </p>
                                </div>
                                <div className="greycard__item">
                                    <i style={{marginTop: '1px'}}><img src="img/icon/img_policy.png" alt=""/></i>
                                    <p className="basic-black basic-semibold"></p>
                                    <Link to={"/epolicy"} onClick={() => callbackApp(true)}>
                                        <button className="btn btn-primary">Xem hợp đồng</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </SwiperSlide>);
                } else {
                    if (item.PaidToDate && item.PaidToDate !== "" && item.PolExpiryDate && item.PolExpiryDate !== "") {
                        itemList.push(<SwiperSlide>
                            <div className="folder__item swiper-slide">
                                <div className="greycard">
                                    <div className="greycard__content">
                                        <h4 className="basic-bold">{item.ProductName}</h4>
                                        <p>
                                            Dành cho <span
                                            className="basic-black">{item.PolicyLIName}</span>{", với hợp đồng "}
                                            <span
                                                className="basic-red">{item.PolicyID}</span> {"sắp hết hạn đóng phí. Vui lòng đóng trước ngày "}
                                            <span className="basic-red">{item.PolExpiryDate}</span>
                                        </p>
                                    </div>
                                    <div className="greycard__item">
                                        <i><img src="img/icon/contract_limit.svg" alt=""/></i>
                                        <p className="basic-black basic-semibold">{item.PolSndryAmt} đ</p>
                                        <Link to={"/mypayment/:id".replace(":id", item.PolicyID)}
                                              onClick={() => callbackApp(true)}>
                                            <button className="btn btn-primary">Đóng Phí</button>
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        </SwiperSlide>);
                    } else {
                        itemList.push(<SwiperSlide>
                            <div className="folder__item swiper-slide">
                                <div className="greycard">
                                    <div className="greycard__content">
                                        <h4 className="basic-bold">{item.ProductName}</h4>
                                        <p>
                                            Dành cho <span
                                            className="basic-black">{item.PolicyLIName}</span>{", với hợp đồng "}
                                            <span className="basic-red">{item.PolicyID}</span> {"có kỳ đóng phí "}
                                            <span className="basic-red">{item.PaidToDate}</span>
                                        </p>
                                    </div>
                                    <div className="greycard__item">
                                        <i><img src="img/icon/contract_limit.svg" alt=""/></i>
                                        <p className="basic-black basic-semibold">{item.PolSndryAmt} đ</p>
                                        <Link to={"/mypayment/:id".replace(":id", item.PolicyID)}
                                              onClick={() => callbackApp(true)}>
                                            <button className="btn btn-primary">Đóng Phí</button>
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        </SwiperSlide>);
                    }
                }

            });
            let props = {
                spaceBetween: 0,
                navigation: false,
                className: "mySwiper mySwiperSingle",
                slidesPerView: 1
            };
            if (itemcount > 0) {
                props = {
                    spaceBetween: 28,
                    // navigation: true,
                    navigation: {
                        nextEl: '.custom-next-button',
                        prevEl: '.custom-prev-button',
                    },
                    className: "mySwiper",
                    slidesPerView: window.innerWidth <= 768 ? 1 : 2

                }
            }

            return (
                <section className="scfolder fee-reminder-container">
                    <div className="container">
                        <div className="folder swiper">
                            <div className="folderwarpper ">
                                <Swiper  {...props}
                                         onSlideChange={handleSlideChange}

                                >

                                    {itemList}
                                </Swiper>
                                <div className="custom-navigation">
                                    <div className="custom-prev-button">
                                        <div
                                            className='prevArrow'
                                        >
                                            <span className="nav-prev-icon"><i
                                                className={`ico ${(currentIndex === 0) ? 'icon-arrow-left-disabled' : 'icon-arrow-left'} ico__large`}></i></span>
                                        </div>
                                    </div>
                                    <div className="custom-next-button">
                                        <div
                                            className='nextArrow'
                                        >
                                            <span className="nav-next-icon"><i
                                                className={`ico ${(currentIndex + 2 >= itemList.length) ? 'icon-arrow-right-disabled' : 'icon-arrow-right'} ico__large`}></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            )
        } else {
            return "";

        }
    }
}

export default FeeReminder;
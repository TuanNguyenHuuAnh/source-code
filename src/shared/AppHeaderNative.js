import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { SUB_MENU_NAME, CMS_APP_CATEGORY_ICON_MAP, CMS_CATEGORY_CODE, HISTORY_LOCATION_PATH, CMS_SUB_CATEGORY_ID, FROM_APP, CMS_CATEGORY_CODE_SET, CMS_SUB_CATEGORY_MAP, FROM_SCREEN_APP, FE_BASE_URL } from '../constants';
import { Swiper, SwiperSlide } from 'swiper/react';
import '../dest/swiper-bundle.min.css';
import SwiperCore, {
	Autoplay, Pagination, Navigation, Scrollbar
} from 'swiper';
import { StringToAliasLink, findCategoryLinkAliasByCode, findCategoryCodeByLinkAlias, findSubCategoryIdInSubCategoryList, findSubCategoryNameInSubCategoryList, findCategoryNameByLinkAlias, setSession, getSession, countIndefofItem } from '../util/common';

SwiperCore.use([Autoplay, Pagination, Navigation, Scrollbar]);

class AppHeaderNative extends Component {
	constructor(props) {
		var today = new Date(),
			date = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
		super(props);
		this.state = {
			isShowNoti: false,
			jsonResponse: null,
			ClientProfile: null,
			sessionClientProfile: this.props.clientProfile,
			clientID: '',
			activeIndex: this.props.activeIndex,
			countNoti: 0,
			currentDate: date,
			hideNativeHeader: false,
			hide: false,
			isBackHomeMobile: false,
			toggle: false
		}

	}

	render() {
		const cacheCategoryCode = (categoryCode) => {
			setSession(CMS_CATEGORY_CODE, categoryCode);
		}
		const cacheSubCategoryId = (linkAlias, code) => {
			let subCategoryId = findSubCategoryIdInSubCategoryList(linkAlias, code);
			setSession(CMS_CATEGORY_CODE, code);
			setSession(CMS_SUB_CATEGORY_ID, subCategoryId);
		}
		let nativeCls = 'native';
		let appHeadCls = 'hw-app-mobile-header-parent';
		let pathArr = window.location.pathname.split('/');
		let isSubCatHeader = false;
		if ((window.location.pathname === '/timkiem/') || (window.location.pathname.indexOf('/tags') >= 0)) {
			nativeCls = 'native-sub';
			appHeadCls = 'hw-app-mobile-header-sub';
			isSubCatHeader = true;
		}
		if ((window.location.pathname.indexOf('/song-vui-khoe') >= 0) && (pathArr.length >= 5)) {
			nativeCls = 'native-sub';
			appHeadCls = 'hw-app-mobile-header-sub';
			isSubCatHeader = true;
		}
		let headIcon = "../../../../../img/ico-app-head-trang-chu.svg";
		//swiper
		let basePath = '/song-vui-khoe/bi-quyet/';
		let props = {};
		let itemList = [];
		if ((window.location.pathname === '/song-vui-khoe/bi-quyet') || (window.location.pathname === '/song-vui-khoe/bi-quyet/')) {
			headIcon = "../../../../../img/ico-app-head-bi-quyet-song-khoe.svg";
			//swiper
			itemList.push(
				<SwiperSlide className='margin-swiper-slice-left'>
					<Link to={basePath + StringToAliasLink('Bệnh và thuốc')} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
						<span className="nav-icon"><i className="ico ico-sick-and-drug ico__large"></i></span>
						<span className="nav-label">Bệnh và <br /> thuốc</span>
					</Link>
				</SwiperSlide>);
			itemList.push(
				<SwiperSlide>
					<Link to={basePath + StringToAliasLink('Thực phẩm và dinh dưỡng')} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
						<span className="nav-icon"><i className="ico ico-food-and-nutrition ico__large"></i></span>
						<span className="nav-label">Thực phẩm và <br /> dinh dưỡng</span>
					</Link>
				</SwiperSlide>);
			itemList.push(
				<SwiperSlide>
					<Link to={basePath + StringToAliasLink('Thói quen sống khỏe')} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
						<span className="nav-icon"><i className="ico ico-habit ico__large"></i></span>
						<span className="nav-label">Thói quen <br /> sống khỏe</span>
					</Link>
				</SwiperSlide>);
			itemList.push(
				<SwiperSlide>
					<Link to={basePath + StringToAliasLink('Bảo hiểm và Đầu tư tích lũy')} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
						<span className="nav-icon"><i className="ico ico-insurance-and-save ico__large"></i></span>
						<span className="nav-label">Bảo hiểm và <br /> Đầu tư tích&nbsp;lũy</span>
					</Link>
				</SwiperSlide>);
			itemList.push(
				<SwiperSlide className='margin-swiper-slice-right'>
					<Link to={basePath + StringToAliasLink('Tin tức và Sự kiện')} onClick={() => cacheCategoryCode(CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
						<span className="nav-icon"><i className="ico ico-news-and-event ico__large"></i></span>
						<span className="nav-label">Tin tức và <br /> Sự kiện</span>
					</Link>
				</SwiperSlide>);
			props = {
				// spaceBetween: 40,
				// navigation: false,
				className: "nav-cate mySwiper z-index-under",
				slidesPerView: 3,
				scrollbar: {
					draggable: true,
					dragSize: 19.2
				},
				// centeredSlides: true,
				breakpoints: {
					270: {
						slidesPerView: 2,
						// spaceBetween: 40
					},
					300: {
						slidesPerView: 2.7,
						// spaceBetween: 40
					},
					320: {
						slidesPerView: 2.8,
						// spaceBetween: 40
					},
					340: {
						slidesPerView: 3,
						spaceBetween: 10
					},
					360: {
						slidesPerView: 3,
						spaceBetween: 10
					},
					372: {
						slidesPerView: 3.2,
						spaceBetween: 10
					},
					388: {
						slidesPerView: 3.3,
						spaceBetween: 10
					},
					412: {
						slidesPerView: 3.5,
						spaceBetween: 10
					},
					500: {
						slidesPerView: 4,
						spaceBetween: 10
					},
					550: {
						slidesPerView: 4.5,
						spaceBetween: 10
					},
					600: {
						slidesPerView: 5,
						spaceBetween: 10
					},
					768: {
						slidesPerView: 5,
						// spaceBetween: 40
					},
					1024: {
						slidesPerView: 6,
						// spaceBetween: 40
					}
				}
			};
		} else if (CMS_APP_CATEGORY_ICON_MAP[getSession(CMS_CATEGORY_CODE)]) {
			headIcon = "../../../../../img/" + CMS_APP_CATEGORY_ICON_MAP[getSession(CMS_CATEGORY_CODE)];
			let categoryCode = getSession(CMS_CATEGORY_CODE);
			if (!categoryCode) {
				categoryCode = findCategoryCodeByLinkAlias(this.props.match.params.category);
			}
			let swipLength = 2;
			if (categoryCode === CMS_CATEGORY_CODE_SET.MEDICINE) {
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.MEDICINE) + "/" + StringToAliasLink('Bệnh thường gặp')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bệnh thường gặp'), CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-sick ico__large"></i></span>
							<span className="nav-label">Bệnh <br /> thường gặp</span>
						</Link>
					</SwiperSlide>);
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.MEDICINE) + "/" + StringToAliasLink('Thuốc & Thực phẩm chức năng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Thuốc & Thực phẩm chức năng'), CMS_CATEGORY_CODE_SET.MEDICINE)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-drug ico__large"></i></span>
							<span className="nav-label">Thuốc & <br /> Thực phẩm chức năng</span>
						</Link>
					</SwiperSlide>);

			} else if (categoryCode === CMS_CATEGORY_CODE_SET.FOODS) {
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.FOODS) + "/" + StringToAliasLink('Thực phẩm')} onClick={() => cacheSubCategoryId(StringToAliasLink('Thực phẩm'), CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-food ico__large"></i></span>
							<span className="nav-label">Thực phẩm</span>
						</Link>
					</SwiperSlide>);
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.FOODS) + "/" + StringToAliasLink('Dinh dưỡng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Dinh dưỡng'), CMS_CATEGORY_CODE_SET.FOODS)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-nutrition ico__large"></i></span>
							<span className="nav-label">Dinh dưỡng</span>
						</Link>
					</SwiperSlide>);
			} else if (categoryCode === CMS_CATEGORY_CODE_SET.HABIT) {
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.HABIT) + "/" + StringToAliasLink('Sức khỏe thể chất')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sức khỏe thể chất'), CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
							<span className="nav-icon" style={{ padding: '5px' }}><i className="ico ico-health ico__large" style={{ width: '50px', height: '50px' }}></i></span>
							<span className="nav-label">Sức khỏe <br /> thể chất</span>
						</Link>
					</SwiperSlide>);
				itemList.push(
					<SwiperSlide>

						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.HABIT) + "/" + StringToAliasLink('Sức khỏe tinh thần')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sức khỏe tinh thần'), CMS_CATEGORY_CODE_SET.HABIT)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-morale ico__large"></i></span>
							<span className="nav-label">Sức khỏe <br /> tinh thần</span>
						</Link>
					</SwiperSlide>);

			} else if (categoryCode === CMS_CATEGORY_CODE_SET.INVESTMENT) {
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.INVESTMENT) + "/" + StringToAliasLink('Bảo hiểm')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bảo hiểm'), CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-insurance ico__large"></i></span>
							<span className="nav-label">Bảo hiểm</span>
						</Link>
					</SwiperSlide>);

				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.INVESTMENT) + "/" + StringToAliasLink('Đầu tư & Tích lũy')} onClick={() => cacheSubCategoryId(StringToAliasLink('Đầu tư & Tích lũy'), CMS_CATEGORY_CODE_SET.INVESTMENT)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-waller ico__large"></i></span>
							<span className="nav-label">Đầu tư & <br /> Tích lũy</span>
						</Link>
					</SwiperSlide>);
			} else if (categoryCode === CMS_CATEGORY_CODE_SET.NEWS) {
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Tin tức')} onClick={() => cacheSubCategoryId(StringToAliasLink('Tin tức'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-news ico__large"></i></span>
							<span className="nav-label">Tin tức</span>
						</Link>
					</SwiperSlide>);
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Sự kiện')} onClick={() => cacheSubCategoryId(StringToAliasLink('Sự kiện'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
							<span className="nav-icon"><i className="ico ico-event ico__large"></i></span>
							<span className="nav-label">Sự kiện</span>
						</Link>
					</SwiperSlide>);
				itemList.push(
					<SwiperSlide>
						<Link to={basePath + findCategoryLinkAliasByCode(CMS_CATEGORY_CODE_SET.NEWS) + "/" + StringToAliasLink('Bản tin khách hàng')} onClick={() => cacheSubCategoryId(StringToAliasLink('Bản tin khách hàng'), CMS_CATEGORY_CODE_SET.NEWS)} className="nav-cate-item">
							<span className="nav-icon" style={{ padding: '10px' }}><i className="ico ico-customer-news ico__large" style={{ width: '35px', height: '35px' }}></i></span>
							<span className="nav-label">Bản tin <br /> khách hàng</span>
						</Link>
					</SwiperSlide>);
				swipLength = 2.8;
			}

			props = {
				// spaceBetween: 100,
				// navigation: false,
				className: "nav-cate mySwiper z-index-under artical",
				slidesPerView: swipLength,
				scrollbar: {
					draggable: true,
					dragSize: 0
				},
				breakpoints: {
					270: {
						slidesPerView: swipLength,
						// spaceBetween: 40
					},
					340: {
						slidesPerView: swipLength,
						// spaceBetween: 100
					},
					640: {
						slidesPerView: swipLength,
						// spaceBetween: 100
					},
					768: {
						slidesPerView: swipLength,
						// spaceBetween: 100
					},
					1024: {
						slidesPerView: swipLength,
						// spaceBetween: 100
					}
				}
			};

		}

		let titleName = '';
		let hide = true;
		if (window.location.pathname === '/timkiem/') {
			hide = false;
			titleName = 'Tìm kiếm';
		}
		else if ((window.location.pathname === '/song-vui-khoe/bi-quyet') || (window.location.pathname === '/song-vui-khoe/bi-quyet/')) {
			hide = false;
			if ((window.location.pathname === '/song-vui-khoe/bi-quyet') || (window.location.pathname === '/song-vui-khoe/bi-quyet/')) {
				titleName = 'Bí quyết sống vui khỏe';
			}
		}

		else if (pathArr.length >= 3) {
			titleName = this.props.subMenuName;

			if ((pathArr.length === 3) && !titleName) {
				titleName = findCategoryNameByLinkAlias(pathArr[2]);
			}
			if (pathArr.length === 7) {
				titleName = findSubCategoryNameInSubCategoryList(pathArr[4], findCategoryCodeByLinkAlias(pathArr[3]), JSON.parse(getSession(CMS_SUB_CATEGORY_MAP)));
			}
		}

		let historyPathArr = JSON.parse(getSession(HISTORY_LOCATION_PATH));
		if ((!historyPathArr && !this.state.isBackHomeMobile) || (historyPathArr && (historyPathArr.length === 1) && !this.state.isBackHomeMobile)) {
			this.setState({ isBackHomeMobile: true });
		} else if (historyPathArr && (historyPathArr.length > 1) && this.state.isBackHomeMobile) {
			this.setState({ isBackHomeMobile: false });
		}
		if (historyPathArr && (historyPathArr.length > 0) && (this.state.hide === true)) {
			this.setState({ hide: false });
		}



		const removeList = () => {
			if (getSession(FROM_APP)) {
				let historyPathArr = JSON.parse(getSession(HISTORY_LOCATION_PATH));
				// alert(JSON.stringify(historyPathArr));
				if (getSession(HISTORY_LOCATION_PATH)) {

					if (historyPathArr.length > 1) {
						historyPathArr.splice(-1);
						if ((historyPathArr.length === 1) && (historyPathArr[0].indexOf("/song-vui-khoe/bi-quyet") >= 0)) {
							this.setState({ isBackHomeMobile: true });
						}
						setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyPathArr));
					}
				} else if ((historyPathArr.length === 1) && (historyPathArr[0].indexOf("/song-vui-khoe/bi-quyet") >= 0)) {
					// alert('no history cache');
					historyPathArr.splice(-1);
				}
			}
		}

		const removeRemain = () => {
			let isBackHomeMobile = this.state.isBackHomeMobile;
			let historyPathArr = JSON.parse(getSession(HISTORY_LOCATION_PATH));
			if (historyPathArr.length === 1) {
				isBackHomeMobile = true;
				historyPathArr.splice(-1);
			}
			hide = true;
			let from = getSession(FROM_APP);
			if (from && (from === "IOS")) {
				let obj = {
					kind_of_page: "MAIN_PAGE"
				};
				if (window.webkit && window.webkit.messageHandlers && (window.webkit.messageHandlers.callbackNavigateToPage !== undefined)) {
					window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
				}
			}
			this.setState({ hide: hide, isBackHomeMobile: isBackHomeMobile });

		}

		const goBack = () => {
			if (window.location.pathname === '/timkiem/') {
				let count = countIndefofItem(historyPathArr, '/timkiem/');
				if (!getSession(FROM_APP)) {
					if (count > 0) {
						let index = historyPathArr.indexOf('/timkiem/');
						historyPathArr.splice(index, count);
						setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyPathArr));
						window.history.go(-count);
					}
					return false;

				} else {
					if (count === 2) {
						let index = historyPathArr.indexOf('/timkiem/');
						historyPathArr.splice(index, count);
						setSession(HISTORY_LOCATION_PATH, JSON.stringify(historyPathArr));
						window.history.go(-count);
						return false;
					}

				}
			}

			removeList();
			window.history.back();
			return false;
		}

		const callBackShare = (url) => {
			if (url && (url.indexOf('?') > 0)) {
				let index = url.indexOf('?');
				url = url.substring(0, index);
			}
			// console.log('callBackShare url = ' + url);
			try {
				let from = getSession(FROM_APP);
				if (from && (from === "Android")) {
					let obj = {
						Action: "Shared_Article",
						url: url,
					};
					if (window.AndroidAppCallback !== undefined) {
						window.AndroidAppCallback.postMessage(JSON.stringify(obj));
					}
				} else if (window.webkit?.messageHandlers?.callBackToSharedArticle) {
					window.webkit.messageHandlers.callBackToSharedArticle.postMessage(url);
				}
				let data = {
					type: 'shared-article',
					url: url,
				}
				if (window.webkit?.messageHandlers?.aktivoEventHandler) {
					console.log("share window.webkit.messageHandlers.aktivoEventHandler....");
					window.webkit.messageHandlers.aktivoEventHandler.postMessage(JSON.stringify(data));
				}
				if (window.AndroidAktivoCallback) {
					console.log("share window.AndroidAktivoCallback....");
					window.AndroidAktivoCallback.receiveMessage(JSON.stringify(data));
				}
				if (window.webkit?.messageHandlers?.edoctorEventHandler) {
					console.log("share window.webkit.messageHandlers.edoctorEventHandler....");
					window.webkit.messageHandlers.edoctorEventHandler.postMessage(JSON.stringify(data));
				}
				if (window.AndroidEdoctorCallback) {
					console.log("share window.AndroidEdoctorCallback....");
					window.AndroidEdoctorCallback.receiveMessage(JSON.stringify(data));
				}
			} catch (error) {
			}
			return false;
		}

		const callBackHomeApp = () => {
			try {
				let from = getSession(FROM_APP);
				if (from && (from === "Android")) {
					let obj = {
						Action: "Back_To_Home"
					};
					if (window.AndroidAppCallback !== undefined) {
						console.log("call window.AndroidAppCallback....");
						window.AndroidAppCallback.postMessage(JSON.stringify(obj));
					}
				} else if (window.webkit?.messageHandlers?.callBackToHomePage) {
					console.log("call window.webkit.messageHandlers.callBackToHomePage....");
					window.webkit.messageHandlers.callBackToHomePage.postMessage("");
				}
				let screen = getSession(FROM_SCREEN_APP);
				// if (screen && window.AndroidAktivoCallback) {
				// 	let screenObj = {
				// 		Action: screen
				// 	};
				// 	console.log("call window.AndroidAktivoCallback....");
				// 	window.AndroidAktivoCallback.backToScreen(JSON.stringify(screenObj));
				// }
				let data = {
					type: 'go-back',
					screen: screen
				}
				if (window.webkit?.messageHandlers?.aktivoEventHandler) {
					console.log("call window.webkit.messageHandlers.aktivoEventHandler....");
					window.webkit.messageHandlers.aktivoEventHandler.postMessage(JSON.stringify(data));
				}
				if (window.AndroidAktivoCallback) {
					console.log("call window.AndroidAktivoCallback....");
					window.AndroidAktivoCallback.receiveMessage(JSON.stringify(data));
				}
				if (window.webkit?.messageHandlers?.edoctorEventHandler) {
					console.log("call window.webkit.messageHandlers.edoctorEventHandler....");
					window.webkit.messageHandlers.edoctorEventHandler.postMessage(JSON.stringify(data));
				}
				if (window.AndroidEdoctorCallback) {
					console.log("call window.AndroidEdoctorCallback....");
					window.AndroidEdoctorCallback.receiveMessage(JSON.stringify(data));
				}
				
			} catch (error) {
			}
			return false;
		}
		return (
			!isSubCatHeader ? (
				((window.location.pathname !== '/song-vui-khoe') && (window.location.pathname !== '/song-vui-khoe/')) && (window.location.pathname !== '/timkiem/') && (window.location.pathname !== '/timkiem') && (window.location.pathname.indexOf('/tags') < 0) && (!this.state.hideNativeHeader) &&
					!this.state.hide ? (
					<header className='hw-app' style={{ position: 'absolute' }}>
						<div className="hwapp-head-wrap">
							<img src="../../../../../img/new/bg-header.svg" className="hwapp-head-bg" />
							<div className="container">
								{this.state.isBackHomeMobile ? (
									getSession(FROM_SCREEN_APP) ? (
										<div className="hwapp-head-nav">
											<a onClick={() => callBackHomeApp()} className="hwapp-nav-btn"><i className="ico ico-back"></i></a>
											<h1 className="hwapp-head-title">{titleName}</h1>
										</div>
									) : (
										<div className="hwapp-head-nav">
											<Link to='/song-vui-khoe/?ignore=yes' onClick={() => removeRemain()} className="hwapp-nav-btn"><i className="ico ico-back"></i></Link>
											<h1 className="hwapp-head-title">{titleName}</h1>
										</div>
									)

								) : (
									<div className="hwapp-head-nav">
										<a onClick={() => goBack()} className="hwapp-nav-btn"><i className="ico ico-back"></i></a>
										<h1 className="hwapp-head-title">{titleName}</h1>
									</div>
								)}

								{((window.location.pathname.indexOf('/song-vui-khoe/bi-quyet') >= 0) && (pathArr.length <= 5)) &&
									<Link to='/timkiem/'><i className='right-icons-ext'><img src={FE_BASE_URL + "/img/icon/search-look.svg"} alt="" /></i></Link>
								}
								<img src={headIcon} className="hwapp-head-photo" />
								<div className="swiper-wrapper z-index-under">
									<Swiper {...props} >
										{itemList}
									</Swiper>
								</div>
							</div>
						</div>
					</header>
				) : (
					<></>
				)




			) : (
				<div>
					{((window.location.pathname !== '/song-vui-khoe') && (window.location.pathname !== '/song-vui-khoe/')) && (!this.state.hideNativeHeader || !hide) &&

						<header className={nativeCls}>

							<div className={appHeadCls}>

								{this.state.isBackHomeMobile ? (
									getSession(FROM_SCREEN_APP) ? (
										<div className="native-back">
											<a onClick={() => callBackHomeApp()}><i className="ico ico-back"></i></a>

										</div>
									) : (
										<div className="native-back">
											<Link to='/song-vui-khoe/?ignore=yes' onClick={() => removeRemain()}><i className="ico ico-back"></i></Link>

										</div>
									)

								) : (
									<div className="native-back">
										<a onClick={() => goBack()}><i className="ico ico-back"></i></a>

									</div>
								)}
								{(pathArr.length < 5) && (window.location.pathname !== '/timkiem/') && (window.location.pathname !== '/timkiem') && (window.location.pathname.indexOf('/tags') < 0) &&
									<div className='native-sub-menu-picture'>

										<img src={headIcon} alt="" />
									</div>

								}
								{((window.location.pathname.indexOf('/song-vui-khoe/bi-quyet') >= 0) && (pathArr.length <= 5)) &&
									<Link to='/timkiem/'><i className='right-icons'><img src={FE_BASE_URL + "/img/icon/search-look.svg"} alt="" /></i></Link>
								}
								{((pathArr.length >= 6)) &&
									<i className='right-icons' onClick={() => callBackShare(window.location.href)}><img src="../../../../../img/icon/Out.svg" alt="" /></i>

								}
								<p>{titleName}</p>
							</div>

						</header>

					}

					{/* <div className='basic-buffer-20'></div> */}
				</div>
			)

		)

	}
}

export default AppHeaderNative;
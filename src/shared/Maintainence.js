import React, { Component } from 'react';
//import { Online, Offline } from "react-detect-offline";
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, FE_BASE_URL, PageScreen, USER_LOGIN} from '../constants';
import './Maintainence.css';
import {getDeviceId, getSession, trackingEvent} from '../util/common';
import { Helmet } from "react-helmet";
import {CPSaveLog} from "../util/APIUtils";

class Maintainence extends Component {
	constructor(props) {
		super(props);
		this.state = {
			noti: '',
			readed: false

		}
	}
	readNoti = () => {
		var setState = this.setState.bind(this);
		fetch("../../notification.txt").then(function (response) {
			return response;
		}).then(function (data) {
			return data.text();
		}).then(function (Normal) {
			setState({ noti: Normal, readed: true });
		}).catch(function (err) {
			//console.log('Fetch problem show: ' + err.message);
		});
	}
	componentDidMount() {
		if (!this.state.readed) {
			this.readNoti();
		}
		this.cpSaveLog(`Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`);
		trackingEvent(
			"Bảo trì",
			`Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`,
			`Web_Open_${PageScreen.SUBMIT_FORM_PAGE}`,
		);
	}

	componentWillUnmount() {
		this.cpSaveLog(`Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`);
		trackingEvent(
			"Bảo trì",
			`Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`,
			`Web_Close_${PageScreen.SUBMIT_FORM_PAGE}`,
		);
	}

	cpSaveLog(functionName) {
		const masterRequest = {
			jsonDataInput: {
				OS: "Web",
				APIToken: getSession(ACCESS_TOKEN),
				Authentication: AUTHENTICATION,
				ClientID: getSession(CLIENT_ID),
				DeviceId: getDeviceId(),
				DeviceToken: "",
				function: functionName,
				Project: "mcp",
				UserLogin: getSession(USER_LOGIN)
			}
		}
		CPSaveLog(masterRequest).then(res => {
			this.setState({renderMeta: true});
		}).catch(error => {
			this.setState({renderMeta: true});
		});
	}

	render() {

		var logined = false;
		if (getSession(ACCESS_TOKEN)) {
			logined = true;
		}
		var mailaddress = "customer.services@dai-ichi-life.com.vn";
		return (
			<main className={logined ? "logined" : ""}>
				<Helmet>
					<title>Trang đang bảo trì – Dai-ichi Life Việt Nam</title>
					<meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
					<meta name="robots" content="noindex, nofollow" />
					<meta property="og:type" content="website" />
					<meta property="og:url" content={FE_BASE_URL + "/maintainence"} />
					<meta property="og:title" content="Trang đang bảo trì - Dai-ichi Life Việt Nam" />
					<meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
					<meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
					<link rel="canonical" href={FE_BASE_URL + "/maintainence"} />
				</Helmet>
				<div className="main-warpper customer-feedback-page">
					<section className="scbreadcrums basic-white">
						<div className="container">
							<div className="breadcrums">
								<div className="breadcrums__item">
									<p>Trang chủ</p>
									<p className='breadcrums__item_arrow'>></p>
								</div>
								<div className="breadcrums__item">
									<p>Bảo trì</p>
									<p className='breadcrums__item_arrow'>></p>
								</div>
							</div>
						</div>
					</section>

					<div className="scdieukhoan">
						<div className="container">
							<p>{this.state.noti}</p>
						</div>
					</div>

					<section className="sc-contact">
						<div className="container">
							<div className="contact-page">
								<div className="lien-he-form">
									<div className="lien-he-form__body">
										<div className="lien-he-form-wrapper">
											<div className="lien-heform">
												<div className="lien-heform-left">
													<p><span className="red">Hệ thống đang bảo trì. Quý khách vui lòng Liên hệ đường dây nóng nếu có thắc mắc hoặc cần hỗ trợ!</span></p>
													<br />
													<div className="lien-heform-left-content">
														<h5>Liên hệ trực tiếp</h5>
														<div className="hot-line">
															<div className="hot-line-tit">Đường dây nóng:</div>
															<div className="hot-line-content">
																<img src="img/icon/phone-nobg.svg" alt="phone" />
																<p>(028) 3810 0888 - Bấm phím 1</p>
															</div>
															<div className="hot-line-content">
																<img src="img/icon/phone-nobg.svg" alt="phone" />
																<p>(028) 7308 8880 - Bấm phím 1</p>
															</div>
															<div className="hot-line-content">
																<p>Thứ Hai đến Chủ Nhật: từ 08:00 - 17:30</p>
															</div>
															<div className="hot-line-content">
																<p style={{ fontSize: 16 }}>(trừ các ngày nghỉ lễ, Tết)</p>
															</div>
														</div>
														<div className="email-list">
															<div className="email-list-tit">
																<img src="img/icon/email-nobg.svg" alt="email" />
																<h5>Email</h5>
															</div>
														</div>
														<div className="email-list">

															<div className="address-list-content">
																<ul>
																	<li>
																		Thông tin liên quan sản phẩm và dịch vụ chăm sóc khách hàng hoặc quyền lợi khi tham
																		gia bảo hiểm:
																		<a href={"mailto:" + mailaddress}>
																			<span className="simple-brown2">customer.services@dai-ichi-life.com.vn</span>
																		</a>
																	</li>
																	<li>
																		Các vấn đề khác:
																		<a href="mailto:info@dai-ichi-life.com.vn">
																			<span className="simple-brown2">info@dai-ichi-life.com.vn</span>
																		</a>
																	</li>
																</ul>
															</div>
														</div>
													</div>
												</div>
												<div className="border-column"></div>
												<div className="lien-heform-right">
													<div className="lien-heform-left-content">


														<div className="address-list">
															<div className="address-list-tit">
																<img src="img/icon/location-nobg.svg" alt="address" />
																<h5>Địa chỉ</h5>
															</div>
															<div className="address-list-content">
																<ul>
																	<li>
																		Trụ sở chính: Tòa nhà DAI-ICHI LIFE, 149-151 Nguyễn Văn Trỗi, Phường 11, Quận Phú
																		Nhuận, TP. Hồ Chí Minh
																	</li>
																	<li>
																		Hoặc
																		<span className="simple-brown2"
																		>Tìm kiếm Văn phòng Tổng đại lý, Trung tâm dịch vụ khách hàng gần nhất</span
																		>
																	</li>
																	<li>
																		Thời gian làm việc:<br />
																		+ Thứ Hai đến Thứ Sáu: từ 08:00 - 17:30
																		<br />+ Thứ Bảy: từ 08:00 - 12:00 <br/>
																		<p>(trừ các ngày nghỉ lễ, Tết)</p>
																	</li>
																</ul>
															</div>
														</div>


													</div>

												</div>
											</div>
										</div>
									</div>
									<img className="decor-clip1" src="img/icon/clip-small-single.svg" alt="" />
									<img className="decor-clip2" src="img/icon/clip-small-single.svg" alt="" />

									<img className="decor-person" src="img/person.png" alt="" />
								</div>
							</div>
						</div>
					</section>
				</div>
			</main>
		)
	}
}

export default Maintainence;
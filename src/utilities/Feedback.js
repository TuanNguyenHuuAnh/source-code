import React, { Component } from 'react';
import {ACCESS_TOKEN, COMPANY_KEY, USER_LOGIN, AUTHENTICATION, FE_BASE_URL, CLIENT_ID, PageScreen} from '../constants';
import { Link } from "react-router-dom";
import {CPSaveLog, FeedbackSubmit} from "../util/APIUtils";
import LoadingIndicator from '../common/LoadingIndicator2';
import b64toBlob from 'b64-to-blob';
import {getDeviceId, getSession, trackingEvent} from "../util/common";
import { Helmet } from "react-helmet";

const MAX_MESSAGE = 2500;
class Feedback extends Component {
	constructor(props) {
		super(props);
		this.state = {
			showFeedback: false,
			reqId: '',
			message: '',
			clientProfile: null,
			renderMeta: false
		};
		this.handleMessage = this.handleMessage.bind(this);
	}

	handleMessage = (event) => {
		if (event.target.value.length > MAX_MESSAGE) {
			document.getElementById('question-area-id').value = this.state.message;
			return;
		}
		this.setState({ message: event.target.value });
	}
	getFeedbackList = () => {
		const feedbackRequest = {
			jsonDataInput: {
				Project: "mcp",
				Company: COMPANY_KEY,
				UserLogin: getSession(USER_LOGIN),
				APIToken: getSession(ACCESS_TOKEN),
				Authentication: AUTHENTICATION,
				DeviceId: getDeviceId(),
				Action: "GetFeedbackList",
				FromSystem: "Web"
			}
		}
		FeedbackSubmit(feedbackRequest).then(response => {
			if (response.Response.Result === 'true' && response.Response.ErrLog === 'Successful') {
				this.setState({ clientProfile: this.sortListByDate(response.Response.ClientProfile) });
			}
			this.setState({ renderMeta: true });
		}).catch(error => {
			// console.log(error);
			this.setState({ renderMeta: true });

		});
	}
	sortListByDate(obj) {
		obj.sort((a, b) => {
			if (a.AskDate < b.AskDate) return 1;
			else if (a.AskDate > b.AskDate) return -1;
			else return 0;
		});
		return obj;
	}
	componentDidMount() {
		this.getFeedbackList();
		this.cpSaveLog(`Web_Open_${PageScreen.FEEDBACK}`);
		trackingEvent(
			"Tiện ích",
			`Web_Open_${PageScreen.FEEDBACK}`,
			`Web_Open_${PageScreen.FEEDBACK}`,
		);
	}
	componentWillUnmount() {
		this.cpSaveLog(`Web_Close_${PageScreen.FEEDBACK}`);
		trackingEvent(
			"Tiện ích",
			`Web_Close_${PageScreen.FEEDBACK}`,
			`Web_Close_${PageScreen.FEEDBACK}`,
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
			this.setState({ renderMeta: true });
		}).catch(error => {
			this.setState({ renderMeta: true });
		});
	}
	render() {
		const submitFeedback = () => {
			const feedbackRequest = {
				jsonDataInput: {
					Project: "mcp",
					Company: COMPANY_KEY,
					UserLogin: getSession(USER_LOGIN),
					APIToken: getSession(ACCESS_TOKEN),
					Authentication: AUTHENTICATION,
					DeviceId: getDeviceId(),
					AskContent: this.state.message,
					Action: "SubmitFeedback",
					FromSystem: "Web"
				}
			}
			FeedbackSubmit(feedbackRequest).then(response => {
				if (response.Response.Result === 'true' && response.Response.ErrLog === 'Submit successfull.') {
					document.getElementById('popup-thanks-feedback').className = "popup special envelop-confirm-popup show";
					this.setState({ message: '' });
					this.getFeedbackList();
				} else {
					document.getElementById("popup-exception").className = "popup special point-error-popup show";
				}
			}).catch(error => {
				console.log(error);
				document.getElementById("popup-exception").className = "popup special point-error-popup show";
			});
		}
		const toggleShow = () => {
			this.setState({ showFeedback: !this.state.showFeedback });
		}
		const toggleResponse = (id) => {
			this.setState({ reqId: id });
		}
		var logined = false;
		if (getSession(ACCESS_TOKEN)) {
			logined = true;
		}

		const downloadAttach = (event, url) => {
			event.preventDefault();
			const downloadRequest = {
				jsonDataInput: {
					Project: "mcp",
					Company: COMPANY_KEY,
					UserLogin: getSession(USER_LOGIN),
					APIToken: getSession(ACCESS_TOKEN),
					Authentication: AUTHENTICATION,
					DeviceId: getDeviceId(),
					URL: url,
					Action: "GetImage"
				}
			}
			FeedbackSubmit(downloadRequest).then(response => {
				// console.log(response.Response);
				if (response.Response.Result === 'true' && response.Response.ErrLog === 'SUCCESSFUL') {
					let arrUrl = url.split('\\');
					let fileName = arrUrl[arrUrl.length - 1];
					if (fileName.indexOf('.pdf') >= 0) {
						downloadPdf(response.Response.Message, fileName);
					} else {
						var element = document.createElement('a');
						element.setAttribute('href', 'data:application/octet-stream;base64,' + encodeURIComponent(response.Response.Message));
						element.setAttribute('download', fileName);
						element.style.display = 'none';
						document.body.appendChild(element);
						element.click();
						document.body.removeChild(element);
					}

				}
			}).catch(error => {
				console.log(error);
				document.getElementById("popup-exception").className = "popup special point-error-popup show";
			});
		}
		const downloadPdf = (response, fileName) => {
			var contentType = 'application/pdf';
			var sliceSize = 512;
			var byteCharacters = atob(response);
			var byteArrays = [];
			for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
				var slice = byteCharacters.slice(offset, offset + sliceSize);
				var byteNumbers = new Array(slice.length);
				for (var i = 0; i < slice.length; i++) {
					byteNumbers[i] = slice.charCodeAt(i);
				}
				var byteArray = new Uint8Array(byteNumbers);
				byteArrays.push(byteArray);
			}

			var blob = new Blob(byteArrays, { type: contentType });
			blob = b64toBlob(response, contentType);
			// var blobUrl = URL.createObjectURL(blob);

			// window.open(blobUrl);


			let url = window.URL.createObjectURL(blob);
			let a = document.createElement('a');
			a.href = url;
			a.download = fileName;
			a.click();
		}
		return (
			<main className={logined ? "logined" : ""}>
				{this.state.renderMeta &&
					<Helmet>
						<title>Góp ý – Dai-ichi Life Việt Nam</title>
						<meta name="description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
						<meta name="robots" content="noindex, nofollow" />
						<meta property="og:type" content="website" />
						<meta property="og:url" content={FE_BASE_URL + "/utilities/feedback"} />
						<meta property="og:title" content="Góp ý - Dai-ichi Life Việt Nam" />
						<meta property="og:description" content="Cổng thông tin dành cho Khách hàng của Dai-ichi Life Việt Nam" />
						<meta property="og:image" content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg" />
						<link rel="canonical" href={FE_BASE_URL + "/utilities/feedback"} />
					</Helmet>
				}
				<div className="main-warpper customer-feedback-page">
					<section className="scbreadcrums basic-white">
						<div className="container">
							<div className="breadcrums">
								<div className="breadcrums__item">
									<p>Trang chủ</p>
									<p className='breadcrums__item_arrow'>></p>
								</div>
								<div className="breadcrums__item">
									<p>Tiện ích</p>
									<p className='breadcrums__item_arrow'>></p>
								</div>
								<div className="breadcrums__item">
									<p>Góp ý</p>
									<p className='breadcrums__item_arrow'>></p>
								</div>
							</div>
						</div>
					</section>

					<div className="scdieukhoan">
						<div className="container">
							<p>Góp ý</p>
						</div>
					</div>
					{logined ? (
						<section className="sccustomer-feedback">
							<div className="container">
								<div className="customer-feedback">
									<div className="y-kien-form">
										<div className="y-kien-form-wrapper">
											<div className="y-kienform__title">
												<h5 style={{ marginTop: '32px' }}>Ý kiến của quý khách</h5>
											</div>
											<div className='y-kienform__body-text'>
												<textarea placeholder="Nhập nội dung*" onChange={this.handleMessage} id='question-area-id' value={this.state.message}>
												</textarea>
												<p style={{ marginBottom: '8px', paddingBottom: '8px' }}>*{this.state.message.length}/2500 kí tự</p>
											</div>

										</div>
										<img className="decor-clip1" src="/img/icon/clip-small-single.svg" alt="" />
										<img className="decor-clip2" src="/img/icon/clip-small-single.svg" alt="" />
										<img className="decor-person" src="/img/person.png" alt="" />


									</div>
									{(this.state.message.length >= 3) ? (
										<div className='button-send' onClick={() => submitFeedback()}>
											<button>Gửi</button>
										</div>
									) : (
										<div className='button-send-disabled'>
											<button disabled>Gửi</button>
										</div>
									)}
									<LoadingIndicator area="feedback-loading-area" />
									<div className="y-kien-form" style={{ marginTop: '4px' }}>
										{this.state.clientProfile && (
											!this.state.showFeedback ? (
												<div className="content-item">
													<div className="dropdown__content" onClick={() => toggleShow()} style={{ borderRadius: '6px' }}>
														<p className='basic-bold'>Nội dung đã góp ý</p>
														<div className="arrow">
															<img src="../img/icon/arrow-left-brown.svg" alt="" />
														</div>
													</div>
												</div>
											) : (
												<div className="content-item">
													<div className="dropdown__content" onClick={() => toggleShow()} style={{ borderTopLeftRadius: '6px', borderTopRightRadius: '6px' }}>
														<p className='basic-bold'>Nội dung đã góp ý</p>
														<div className="arrow">
															<img src="../img/icon/arrow-down-bronw.svg" alt="" />
														</div>
													</div>
													<div>
														<table className='feedbacklist'>
															<tbody>
																{this.state.clientProfile && this.state.clientProfile.length > 0 ? (
																	this.state.clientProfile.map((item, index) => {
																		return (
																			<>
																				<tr>
																					<td>{item.AskDate}</td>
																					<td>
																						{item.Reply === "1" ? (


																							this.state.reqId === 'req-id-' + item.FeedbackID ? (
																								<div className='feedback_' id={'req-id-' + item.FeedbackID}><p style={{ whiteSpace: 'pre-line' }}>{item.AskContent}</p>
																									<span className='basic-green' onClick={() => toggleResponse('')}>Đã phản hồi<p className='arrow-down'></p></span>

																								</div>
																							) : (
																								<div className='feedback_' id={'req-id-' + item.FeedbackID}><p style={{ whiteSpace: 'pre-line' }}>{item.AskContent.length > 210 ? item.AskContent.substring(0, 210) + ' ...' : item.AskContent}</p>
																									<span className='basic-green' onClick={() => toggleResponse('req-id-' + item.FeedbackID)}>Đã phản hồi<p className='arrow-left'></p></span>
																								</div>
																							)

																						) : (
																							<div className='feedback_'><p style={{ whiteSpace: 'pre-line' }}>{item.AskContent}</p><span className='basic-gray'>Chờ phản hồi</span></div>
																						)}

																					</td>
																				</tr>
																				{(item.Reply === "1" && this.state.reqId === 'req-id-' + item.FeedbackID) &&
																					<tr>
																						<td colSpan={2} style={{ padding: '0px', margin: '0px' }}>
																							<table className='feedbacklist' style={{ padding: '0px', border: '0px solid #ddd' }}>
																								<tr style={{ backgroundColor: '#F5F3F2' }}>
																									<td>{item.AnswerDate}</td>
																									<td>

																										<div className='feedback_' id={'req-id-' + item.FeedbackID}><p style={{ whiteSpace: 'pre-line' }}>{item.AnswerContent.replaceAll('\\n', '\n')}</p>
																											{item.URL &&
																												<span className='basic-gray-underline' onClick={(e) => downloadAttach(e, item.URL)}>Tải trả lời</span>
																											}
																										</div>

																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																				}
																			</>
																		)
																	})
																) : (
																	<tr>
																		<td style={{ width: '597px' }}>
																			Quý khách chưa gửi góp ý
																		</td>
																	</tr>
																)}
															</tbody>
														</table>
													</div>
												</div>

											))}

									</div>
								</div>
							</div>
						</section>
					) : (
						<section className="sccustomer-feedback">
							<div className="container">
								<div className="customer-feedback">
									<div className="y-kien-form">
										<div className="y-kien-form-wrapper">
											<div className="y-kienform__title">
												<h5 style={{ textAlign: 'center', margin: 'auto' }}>Vui lòng đăng nhập để sử dụng tính năng này</h5>
											</div>
										</div>
									</div>
									<div className='button-send'>
										<Link to='/' className='button-send'><button>Đăng nhập</button></Link>
									</div>
								</div>
							</div>
						</section>
					)}
				</div>
			</main>
		)
	}
}

export default Feedback;
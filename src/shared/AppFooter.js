import React from 'react';
import {FROM_HOME} from '../constants';
import {getSession} from '../util/common';
import './AppFooter.css';
import {useHistory} from "react-router-dom";

const AppFooter = () => {

	const handleAgreeClick = () => {
		window.open('/terms-of-use', '_blank');

	};

	const handleInfoSecurityClick = () => {
		window.open('/privacy-policy', '_blank');
	};

	const  getCurrentYear = () => {
		const currentYear = new Date().getFullYear();
		return currentYear;
	};
	const currentYear = getCurrentYear();

	return (
		<footer style={getSession(FROM_HOME)?{marginTop: '0px'}:{}} id="main-footer-id">
			<div className="footer-container">
				<div className="footer-left-container">
					<p className="footer-pointer" onClick={() =>  handleAgreeClick()}>Điều khoản sử dụng</p>
					<p className="divide-line">|</p>
					<p className="footer-pointer" onClick={() =>  handleInfoSecurityClick()}>Chính sách bảo mật</p>
				</div>
				<div className="footer-right-container footer-right-invisible-mobile">
					<p>
						© {currentYear} Bản quyền thuộc về Công ty TNHH Bảo hiểm Nhân thọ&nbsp;
					</p>
					<a className="text-bold" href="www.dai-ichi-life.com.vn">Dai-ichi Việt Nam</a>
				</div>
				<div className="footer-right-container footer-right-visible-mobile">
					<p>
						© {currentYear} Bản quyền thuộc về
					</p>
					<div className="footer-flex-center">
						<p>
							Công ty TNHH Bảo hiểm Nhân thọ&nbsp;
						</p>
						<a className="text-bold" href="www.dai-ichi-life.com.vn">Dai-ichi Việt Nam</a>
					</div>
				</div>

			</div>
		</footer>
	);
}

export default AppFooter;
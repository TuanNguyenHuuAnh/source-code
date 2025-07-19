import React, {useEffect} from 'react';
import {withRouter} from 'react-router-dom';
import ReactGA from 'react-ga4';
import {FROM_APP, IS_MOBILE} from '../constants';
import {findAliasLinkInCategoryList, findAliasLinkInSubCategoryList, getSession, trackingEvent} from '../util/common';
import {HEALTH_CONSULTING} from "./dummyData";

const RouteChangeTracker = ({
                                history,
                                callbackMenu,
                                callbackSubMenu,
                                callbackHideMain,
                                callbackCategory,
                                callbackSubCategory,
                                callbackShowLogin
                            }) => {
    useEffect(() => {
        history.listen((location, action) => {

            const gtmScriptTag = document.querySelectorAll("[id^='gtmScript']");
            const seenIds = new Set();

            for (let i = gtmScriptTag.length - 1; i >= 0; i--) {
                const currentTag = gtmScriptTag[i];
                const currentId = currentTag.getAttribute('id');
                if (seenIds.has(currentId)) {
                    currentTag.remove();
                } else {
                    seenIds.add(currentId);
                }
            }

            setTimeout(() => {
                ReactGA.send({hitType: 'pageview', page: location.pathname});
                window.dataLayer.push({
                    event: 'pageview',
                    page: {
                        url: location.pathname,
                        title: document.title
                    }
                });
            }, 500);
            boldLinkPage(location);
        });
    }, []);

    useEffect(() => {
        const checkIfMobile = () => {
            if (window.innerWidth <= 768 && !getSession(FROM_APP) && !getSession(IS_MOBILE)) {
                const mainContainer = document.getElementsByTagName('main')[0];
                if (mainContainer) {
                    mainContainer.style.marginTop = '62px';
                }

                const headerContainer = document.getElementsByTagName('header')[0];
                if (headerContainer) {
                    headerContainer.style.marginTop = '62px';
                }
                const popupSearch = document.getElementsByClassName('popup__search')[0];
                if (popupSearch) {
                    popupSearch.style.marginTop = '86px';
                }
            } else {
                const mainContainer = document.getElementsByTagName('main')[0];
                if (mainContainer) {
                    mainContainer.style.marginTop = '0px';
                }

                const headerContainer = document.getElementsByTagName('header')[0];
                if (headerContainer) {
                    headerContainer.style.marginTop = '0px';
                }

                const popupSearch = document.getElementsByClassName('popup__search')[0];
                if (popupSearch) {
                    popupSearch.style.marginTop = '0px';
                }
            }
        };
        checkIfMobile();
        window.addEventListener('resize', checkIfMobile);
        return () => {
            window.removeEventListener('resize', checkIfMobile);
        };
    }, [getSession(FROM_APP), getSession(IS_MOBILE)]);

    const boldLinkPage = (location) => {
        callbackCategory('', '');
        callbackSubCategory('', '');

        if (location.pathname === '/mypolicyinfo') {
            trackingEvent(
                "Thông tin hợp đồng",
                "Danh sách hợp đồng",
                "Danh sách hợp đồng",
            );
            callbackMenu('Thông tin hợp đồng', 'sidebar-item12');
            callbackSubMenu('Danh sách hợp đồng', 'item-12-0');
        } else if (location.pathname === '/lifeassured') {
            trackingEvent(
                "Thông tin hợp đồng",
                "Người được bảo hiểm",
                "Người được bảo hiểm",
            );
            callbackMenu('Thông tin hợp đồng', 'sidebar-item12');
            callbackSubMenu('Người được bảo hiểm', 'item-12-1');
        } else if (location.pathname === '/policyowner') {
            trackingEvent(
                "Thông tin hợp đồng",
                "Bên mua bảo hiểm",
                "Bên mua bảo hiểm",
            );
            callbackMenu('Thông tin hợp đồng', 'sidebar-item12');
            callbackSubMenu('Bên mua bảo hiểm', 'item-12-2');
        } else if (location.pathname.indexOf('/mypayment') >= 0) {
            trackingEvent(
                "Đóng phí bảo hiểm",
                "Hợp đồng của tôi",
                "Hợp đồng của tôi",
            );
            callbackMenu('Đóng phí bảo hiểm', 'sidebar-item13');
            callbackSubMenu('Hợp đồng của tôi', 'item-13-0');
        } else if (location.pathname === '/familypayment') {
            trackingEvent(
                "Đóng phí bảo hiểm",
                "Hợp đồng của người thân",
                "Hợp đồng của người thân",
            );
            callbackMenu('Đóng phí bảo hiểm', 'sidebar-item13');
            callbackSubMenu('Hợp đồng của người thân', 'item-13-1');
        } else if (location.pathname === '/myclaim') {
            trackingEvent(
                "Yêu cầu quyền lợi",
                "Tạo mới yêu cầu",
                "Tạo mới yêu cầu",
            );
            callbackMenu('Yêu cầu quyền lợi', 'sidebar-item14');
            callbackSubMenu('Tạo mới yêu cầu', 'item-14-0');
        } else if (location.pathname === '/info-required-claim') {
            trackingEvent(
                "Yêu cầu quyền lợi",
                "Danh sách yêu cầu cần bổ sung",
                "Danh sách yêu cầu cần bổ sung",
            );
            callbackMenu('Yêu cầu quyền lợi', 'sidebar-item14');
            callbackSubMenu('Danh sách yêu cầu cần bổ sung', 'item-14-1');
        } else if (location.pathname === '/followup-claim-info') {
            trackingEvent(
                "Theo dõi yêu cầu",
                "Giải quyết quyền lợi",
                "Giải quyết quyền lợi",
            );
            callbackMenu('Theo dõi yêu cầu', 'sidebar-item1');
            callbackSubMenu('Giải quyết quyền lợi', 'item-1-0');
        } else if (location.pathname === '/update-contact-info') {
            trackingEvent(
                "Giao dịch hợp đồng",
                "Điều chỉnh thông tin liên hệ",
                "Điều chỉnh thông tin liên hệ",
            );
            callbackMenu('Giao dịch hợp đồng', 'sidebar-item2');
            callbackSubMenu('Điều chỉnh thông tin liên hệ', 'item-2-0');
        } else if (location.pathname === '/update-personal-info') {
            trackingEvent(
                "Giao dịch hợp đồng",
                "Điều chỉnh thông tin cá nhân",
                "Điều chỉnh thông tin cá nhân",
            );
            callbackMenu('Giao dịch hợp đồng', 'sidebar-item2');
            callbackSubMenu('Điều chỉnh thông tin cá nhân', 'item-2-1');
        } else if (location.pathname === '/update-policy-info') {
            trackingEvent(
                "Giao dịch hợp đồng",
                "Điều chỉnh thông tin hợp đồng",
                "Điều chỉnh thông tin hợp đồng",
            );
            callbackMenu('Giao dịch hợp đồng', 'sidebar-item2');
            callbackSubMenu('Điều chỉnh thông tin hợp đồng', 'item-2-2');
        } else if (location.pathname === '/reinstatement') {
            trackingEvent(
                "Giao dịch hợp đồng",
                "Khôi phục hiệu lực",
                "Khôi phục hiệu lực",
            );
            callbackMenu('Giao dịch hợp đồng', 'sidebar-item2');
            callbackSubMenu('Khôi phục hiệu lực', 'item-2-3');
        } else if (location.pathname === '/payment-contract') {
            trackingEvent(
                "Giao dịch hợp đồng",
                "Thanh toán giá trị hợp đồng",
                "Thanh toán giá trị hợp đồng",
            );
            callbackMenu('Giao dịch hợp đồng', 'sidebar-item2');
            callbackSubMenu('Thanh toán giá trị hợp đồng', 'item-2-4');
        } 
        
        else if (location.pathname === '/payment-history') {
            trackingEvent(
                "Lịch sử hợp đồng",
                "Đóng phí bảo hiểm",
                "Đóng phí bảo hiểm",
            );
            callbackMenu('Lịch sử hợp đồng', 'sidebar-item3');
            callbackSubMenu('Đóng phí bảo hiểm', 'item-3-0');
        } else if (location.pathname === '/claim-history') {
            trackingEvent(
                "Lịch sử hợp đồng",
                "Giải quyết quyền lợi",
                "Giải quyết quyền lợi",
            );
            callbackMenu('Lịch sử hợp đồng', 'sidebar-item3');
            callbackSubMenu('Giải quyết quyền lợi', 'item-3-1');
        } else if (location.pathname === '/epolicy') {
            trackingEvent(
                "Thư viện tài liệu",
                "Bộ hợp đồng",
                "Bộ hợp đồng",
            );
            callbackMenu('Thư viện tài liệu', 'sidebar-item4');
            callbackSubMenu('Bộ hợp đồng', 'item-4-0');
        } else if (location.pathname === '/manage-epolicy') {
            trackingEvent(
                "Thư viện tài liệu",
                "Quản lý hợp đồng",
                "Quản lý hợp đồng",
            );
            callbackMenu('Thư viện tài liệu', 'sidebar-item4');
            callbackSubMenu('Quản lý hợp đồng', 'item-4-1');
        } else if (location.pathname === '/account') { //End sidebar
            trackingEvent(
                "Thông tin tài khoản",
                "Thông tin tài khoản",
                "Thông tin tài khoản",
            );
            callbackMenu('Thông tin tài khoản', 'account-page-id');
            callbackSubMenu(' ', 'account-page-id');
        } else if (location.pathname === '/') { //Start header menu
            trackingEvent(
                "Trang chủ",
                "Trang chủ",
                "Trang chủ",
            );
            callbackMenu('Trang chủ', 'ah1');
            callbackSubMenu(' ', 'ah1');
            callbackHideMain(false);
        } else if (location.pathname === '/healthnews') {
            trackingEvent(
                "Sống khoẻ",
                "Bản tin sống khỏe",
                "Bản tin sống khỏe",
            );
            callbackMenu('Sống khoẻ', 'ah4');
            callbackSubMenu('Bản tin sống khỏe', 'h3');
        } else if (location.pathname === '/point') {
            trackingEvent(
                "Điểm thưởng",
                "Điểm thưởng",
                "Điểm thưởng",
            );
            callbackMenu('Trang chủ', 'ah3');
            callbackSubMenu('Điểm thưởng', 'ah3');
        } else if (location.pathname === '/point-exchange') {
            trackingEvent(
                "Điểm thưởng",
                "Đổi điểm thưởng",
                "Đổi điểm thưởng",
            );
            callbackMenu('Điểm thưởng', 'ah3');
            callbackSubMenu('Đổi điểm thưởng', 'p1');
        } else if (location.pathname === '/gift-cart') {
            trackingEvent(
                "Điểm thưởng",
                "Giỏ quà của tôi",
                "Giỏ quà của tôi",
            );
            callbackMenu('Điểm thưởng', 'ah3');
            callbackSubMenu('Giỏ quà của tôi', 'p2');
        } else if (location.pathname === '/point-history') {
            trackingEvent(
                "Điểm thưởng",
                "Lịch sử điểm thưởng",
                "Lịch sử điểm thưởng",
            );
            callbackMenu('Điểm thưởng', 'ah3');
            callbackSubMenu('Lịch sử điểm thưởng', 'p3');
        } else if (location.pathname === '/utilities') {
            trackingEvent(
                "Tiện ích",
                "Tiện ích",
                "Tiện ích",
            );
            callbackMenu('Trang chủ', 'ah5');
            callbackSubMenu('Tiện ích', 'ah5');
        } else if ((location.pathname === '/utilities/network') || (location.pathname === '/utilities/network/Hospital')) {
            trackingEvent(
                "Tiện ích",
                "Mạng lưới",
                "Mạng lưới",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Mạng lưới', 'u1');
        } else if (location.pathname === '/utilities/participate') {
            trackingEvent(
                "Tiện ích",
                "Tham gia bảo hiểm",
                "Tham gia bảo hiểm",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Tham gia bảo hiểm', 'u2');
        } else if (location.pathname === '/utilities/policy-payment') {
            trackingEvent(
                "Tiện ích",
                "Đóng phí bảo hiểm",
                "Đóng phí bảo hiểm",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Đóng phí bảo hiểm', 'u3');
        } else if (location.pathname === '/utilities/claim-guide') {
            trackingEvent(
                "Tiện ích",
                "Giải quyết quyền lợi bảo hiểm",
                "Giải quyết quyền lợi bảo hiểm",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Giải quyết quyền lợi bảo hiểm', 'u4');
        } else if (location.pathname === '/utilities/policy-trans') {
            trackingEvent(
                "Tiện ích",
                "Giao dịch hợp đồng",
                "Giao dịch hợp đồng",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Giao dịch hợp đồng', 'u5');
        } else if (location.pathname === '/utilities/document') {
            trackingEvent(
                "Tiện ích",
                "Biểu mẫu",
                "Biểu mẫu",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Biểu mẫu', 'u7');
        } else if (location.pathname === '/utilities/occupation') {
            trackingEvent(
                "Tiện ích",
                "Phân nhóm nghề",
                "Phân nhóm nghề",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Phân nhóm nghề', 'u11');
        } else if (location.pathname === '/utilities/faq') {
            trackingEvent(
                "Tiện ích",
                "Câu hỏi thường gặp",
                "Câu hỏi thường gặp",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Câu hỏi thường gặp', 'u8');
        } else if (location.pathname === '/faq') {
            trackingEvent(
                "Tiện ích",
                "Câu hỏi thường gặp",
                "Câu hỏi thường gặp",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Câu hỏi thường gặp', 'u8');
        } else if (location.pathname === '/utilities/clauses') {
            trackingEvent(
                "Tiện ích",
                "Điều khoản sử dụng",
                "Điều khoản sử dụng",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Điều khoản sử dụng', 'u9');
        } else if (location.pathname === '/terms-of-use') {
            trackingEvent(
                "Tiện ích",
                "Điều khoản sử dụng",
                "Điều khoản sử dụng",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Điều khoản sử dụng', 'u9');
        } else if (location.pathname === '/privacy-policy') {
            trackingEvent(
                "Tiện ích",
                "Chính sách bảo mật",
                "Chính sách bảo mật",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Chính sách bảo mật', 'u9_');
        } else if (location.pathname === '/utilities/contact') {
            trackingEvent(
                "Tiện ích",
                "Liên hệ",
                "Liên hệ",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Liên hệ', 'u12');
        } else if (location.pathname === '/companypolicyinvoiceinfo') {
            trackingEvent(
                "Thư viện tài liệu",
                "Hóa đơn điện tử",
                "Hóa đơn điện tử",
            );
            callbackMenu('Thư viện tài liệu', 'sidebar-item4');
            callbackSubMenu('Hóa đơn điện tử', 'item-4-1');
        } else if (location.pathname === '/companypolicyinfo') {
            trackingEvent(
                "Thông tin hợp đồng",
                "Danh sách hợp đồng theo công ty",
                "Danh sách hợp đồng theo công ty",
            );
            callbackMenu('Thông tin hợp đồng', 'content-12');
            callbackSubMenu('Danh sách hợp đồng theo công ty', 'item-12-0');
        } else if (location.pathname === '/utilities/feedback') {
            trackingEvent(
                "Tiện ích",
                "Góp ý",
                "Góp ý",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Góp ý', 'u13');
        } else if (location.pathname === '/khach-hang-can-biet') {
            trackingEvent(
                "Tiện ích",
                "Khách hàng cần biết",
                "Khách hàng cần biết",
            );
            callbackMenu('Tiện ích', 'ah5');
            callbackSubMenu('Khách hàng cần biết', 'u0');
        } else if (location.pathname.indexOf('/song-vui-khoe') >= 0) {

            let from = getSession(FROM_APP);
            let delta = 0;
            if (from) {
                from = '[' + from + ']';
                delta = 1;
            } else {
                from = '';
            }
            let locationPath = from + location.pathname;
            let pathArr = location.pathname.split('/');
            trackingEvent(
                locationPath,
                locationPath,
                locationPath,
            );
            if (delta === 1) {
                if (pathArr.length === 2) {
                    callbackMenu('Trang chủ', 'ah4');
                    callbackSubMenu('Sống vui khoẻ', 'ah4');
                } else if (pathArr.length === 3) {
                    if (pathArr[2] === 'bi-quyet') {
                        callbackMenu('Sống vui khoẻ', 'ah4');
                        callbackSubMenu('Bí quyết Sống vui khỏe', 'h4');
                    } else if (pathArr[2] === 'thu-thach-song-khoe') {
                        callbackMenu('Sống vui khoẻ', 'ah4');
                        callbackSubMenu('Thử thách sống khỏe', 'h3');
                    }
                } else if (pathArr.length === 4) {
                    if (location.pathname.indexOf('/bi-quyet') >= 0) {

                        let info = findAliasLinkInCategoryList(pathArr[3]);
                        if (info !== null) {
                            callbackMenu('Bí quyết Sống vui khỏe', 'ah4');
                            callbackSubMenu(info[0], 'h4');
                            callbackCategory(info[0], info[1]);
                        }
                    }
                } else if (pathArr.length >= 5) {
                    if (location.pathname.indexOf('/bi-quyet') >= 0) {

                        let info = findAliasLinkInCategoryList(pathArr[3]);
                        if (info !== null) {
                            let subInfo = findAliasLinkInSubCategoryList(pathArr[4], info[1])
                            if (subInfo !== null) {
                                callbackMenu(info[0], 'ah4');
                                callbackSubMenu(subInfo[0], 'h4');
                                callbackCategory(info[0], info[1]);
                                callbackSubCategory(subInfo[0], 'sub' + subInfo[1]);
                            }

                        }
                    }
                }
            } else {
                if (pathArr.length === 2) {
                    callbackMenu('Trang chủ', 'ah4');
                    callbackSubMenu('Sống vui khoẻ', 'ah4');
                } else if (pathArr.length === 3) {
                    if (pathArr[2] === 'bi-quyet') {
                        callbackMenu('Sống vui khoẻ', 'ah4');
                        callbackSubMenu('Bí quyết Sống vui khỏe', 'h4');
                    } else if (pathArr[2] === 'thu-thach-song-khoe') {
                        callbackMenu('Sống vui khoẻ', 'ah4');
                        callbackSubMenu('Thử thách sống khỏe', 'h3');
                    }
                } else if (pathArr.length === 4) {
                    if (location.pathname.indexOf('/bi-quyet') >= 0) {

                        let info = findAliasLinkInCategoryList(pathArr[3]);
                        if (info !== null) {
                            callbackMenu('Bí quyết Sống vui khỏe', 'ah4');
                            callbackSubMenu(info[0], 'h4');
                            callbackCategory(info[0], info[1]);
                            // callbackSubCategory('', '0');
                        }
                    }
                } else if (pathArr.length === 5 || pathArr.length === 6) {
                    if (location.pathname.indexOf('/bi-quyet') >= 0) {

                        let info = findAliasLinkInCategoryList(pathArr[3]);
                        if (info !== null) {
                            let subInfo = findAliasLinkInSubCategoryList(pathArr[4], info[1])
                            if (subInfo !== null) {
                                callbackMenu(info[0], 'ah4');
                                callbackSubMenu(subInfo[0], 'h4');
                                callbackCategory(info[0], info[1]);
                                callbackSubCategory(subInfo[0], 'sub' + subInfo[1]);
                            }

                        }
                    }
                }

            }
        } else  if (location.pathname.indexOf('/tu-van-suc-khoe') >= 0) {
            let pathArr = location.pathname.split('/');

         if (pathArr.length === 2) {
             if (pathArr[1] === 'tu-van-suc-khoe') {
                 callbackMenu('Sống vui khoẻ', 'ah4');
                 callbackSubMenu('Tư vấn sức khỏe', 'h5');
             }
         } else if (pathArr.length === 3) {
             let info = findAliasLinkInCategoryList(pathArr[2], HEALTH_CONSULTING);
             if (info !== null) {
                 callbackMenu('Tư vấn sức khỏe', 'ah4');
                 callbackSubMenu(info[0], 'h5');
                 callbackCategory(info[0], info[1]);
             }
         }
        } else if (location.pathname.indexOf('/timkiem') >= 0) {
            trackingEvent(
                "Bí quyết Sống vui khỏe",
                "Tìm kiếm",
                "Tìm kiếm",
            );

        }

        if (location.pathname !== '/') {
            callbackShowLogin(false);
        } else {
            callbackShowLogin(true);
        }
    }

    return <div></div>;
};

export default withRouter(RouteChangeTracker);
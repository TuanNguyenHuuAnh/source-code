import React, {useEffect, useRef, useState} from "react";
import {CPSaveLog} from '../util/APIUtils';
import {getDeviceId, getSession, getUrlParameter, isLoggedIn, trackingEvent} from "../util/common";
import {ACCESS_TOKEN, AUTHENTICATION, CLIENT_ID, FE_BASE_URL, IS_MOBILE, PageScreen, USER_LOGIN} from '../constants';
import {Helmet} from "react-helmet";
import Breadcrumb from "./Breadcrumb";
import TermsOfUse from "./TermsOfUse";
import {useLocation} from "react-router-dom";
import { Link } from "react-router-dom";

export const TABS = Object.freeze({
    AGREEMENT: 0, COMMITMENT: 1,
});

const breadcrumbItems = [{label: 'Trang chủ'}, {label: 'Tiện ích'}, {label: 'Điều khoản sử dụng'}, {label: 'Thoả thuận sử dụng ứng dụng'},];

const Clauses = () => {
    const location = useLocation();
    const [isLoaded, setIsLoaded] = useState(false);
    const isMobile = getUrlParameter(IS_MOBILE);

    const tabPaneRef = useRef(null);

    const [state, setState] = useState({
        currentTab: TABS.AGREEMENT, renderMeta: false,
    });

    useEffect(() => {
        import('./Clauses.css').then(() => {
            setIsLoaded(true);
        });

        const urlParams = new URLSearchParams(location.search);
        const tabFromUrl = urlParams.get('currentTab');
        const parsedTab = tabFromUrl ? parseInt(tabFromUrl, 10) : TABS.AGREEMENT;

        setState((prev) => ({...prev, currentTab: parsedTab}));

        cpSaveLog(`Web_Open_${PageScreen.TERM_OF_USE}`);
        trackingEvent("Tiện ích", `Web_Open_${PageScreen.TERM_OF_USE}`, `Web_Open_${PageScreen.TERM_OF_USE}`);

        return () => {
            cpSaveLog(`Web_Close_${PageScreen.TERM_OF_USE}`);
            trackingEvent("Tiện ích", `Web_Close_${PageScreen.TERM_OF_USE}`, `Web_Close_${PageScreen.TERM_OF_USE}`);
        };
    }, [location.search]); // Empty dependency array means this effect will only run once (equivalent to componentDidMount)

    useEffect(() => {

        window.scrollTo(0, 0);

        if (tabPaneRef.current) {
            tabPaneRef.current.scrollIntoView({behavior: 'smooth'});
        }
    }, [location.pathname]);

    useEffect(() => {
        if (isMobile) {

            document.querySelectorAll(".no-padding-app").forEach((noPadding) => {
                noPadding.style.padding = 0;
            });

            document.querySelectorAll(".no-margin-app").forEach((noMargin) => {
                noMargin.style.margin = 0;
            });
        }
    }, [isMobile]);

    const cpSaveLog = (functionName) => {
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
                UserLogin: getSession(USER_LOGIN),
            },
        };

        CPSaveLog(masterRequest)
            .then((res) => {
                setState((prev) => ({...prev, renderMeta: true}));
            })
            .catch((error) => {
                setState((prev) => ({...prev, renderMeta: true}));
            });
    };

    const onChangeTab = (tabId) => {
        setState((prev) => ({...prev, currentTab: tabId}));
        // history.push(`/utilities/clauses?currentTab=${tabId}`);

    };
    if (!isLoaded) {
        return null; // Hoặc có thể render một loading indicator
    }
    return (<main className={`${isLoggedIn() ? "logined" : ""} no-padding-app`}>
        {state.renderMeta && <Helmet>
            <title>Điều khoản sử dụng – Dai-ichi Life Việt Nam</title>
            <meta name="description"
                  content="Trang thông tin về thỏa thuận điều khoản sử dụng ứng dụng Dai-ichi Connect và bảo mật thông tin khách hàng từ Dai-ichi Life Việt Nam."/>
            <meta name="robots" content="noindex, nofollow"/>
            <meta property="og:type" content="website"/>
            <meta property="og:url" content={FE_BASE_URL + "/utilities/clauses"}/>
            <meta property="og:title" content="Liên hệ – Dai-ichi Life Việt Nam"/>
            <meta property="og:description"
                  content="Trang thông tin về thỏa thuận điều khoản sử dụng ứng dụng Dai-ichi Connect và bảo mật thông tin khách hàng từ Dai-ichi Life Việt Nam."/>
            <meta property="og:image"
                  content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
            <link rel="canonical" href={FE_BASE_URL + "/utilities/clauses"}/>
        </Helmet>}
        <div className="main-warpper">
            {/* Route display */}
            {/* <Breadcrumb items={breadcrumbItems} isMobile={getUrlParameter(IS_MOBILE)}/> */}
            <section className="scbreadcrums">
                <div className="container">
                    <div className="breadcrums basic-white">
                        <Link to="/" className="breadcrums__item">
                            <p>Trang chủ</p>
                            <p className='breadcrums__item_arrow'>></p>
                        </Link>
                        <Link to="/utilities" className="breadcrums__item">
                            <p>Tiện ích</p>
                            <p className='breadcrums__item_arrow'>></p>
                        </Link>
                        <div className="breadcrums__item">
                            <p>Điều khoản sử dụng</p>
                            <p className='breadcrums__item_arrow'>></p>
                        </div>
                    </div>
                </div> 
            </section>
            {/* Banner */}
            <TermsOfUse isMobile={getUrlParameter(IS_MOBILE)} label="Điều khoản sử dụng"/>
            {/* Main page */}
            <section className="scterm-of-use custom-margin-top-app"style={{ marginTop: isMobile && '130px' }}>
                <div className="tab-pane-container">
                    {/* Tabs */}
                    <section className="policy-menu">
                        <button
                            className={state.currentTab === TABS.AGREEMENT ? "policy-menu__item active" : "policy-menu__item"}
                            onClick={() => onChangeTab(TABS.AGREEMENT)}>
                            <h2>THOẢ THUẬN SỬ DỤNG ỨNG DỤNG</h2>
                        </button>
                        <button
                            className={state.currentTab === TABS.COMMITMENT ? "policy-menu__item active" : "policy-menu__item"}
                            onClick={() => onChangeTab(TABS.COMMITMENT)}>
                            <h2>CHÍNH SÁCH BẢO MẬT THÔNG TIN</h2>
                        </button>
                    </section>

                    {/* Contents */}
                    <div className="tab-pane-container-content" ref={tabPaneRef}>
                        <div id="thoathuan"
                             className={state.currentTab === TABS.AGREEMENT ? "tabpane-tab active" : "tabpane-tab"}>
                            <div className="dieukhoan-page">
                                <div className="dieukhoan-form">
                                    <div className="dieukhoan-form__body">
                                        <div className="dieukhoanform-wrapper">
                                            <div className="dieukhoanform">
                                                <div className="dieukhoanform__title no-margin-app"
                                                     style={{flexDirection: 'column'}}>
                                                    <p className="basic-bold">Thoả thuận sử dụng ứng dụng</p>
                                                    <p className="basic-bold" style={{marginTop: 8}}>PHẦN I:</p>
                                                    <p className="basic-bold" style={{marginTop: 8}}>Quy định về điều
                                                        kiện và điều khoản sử dụng ứng dụng</p>
                                                </div>
                                                <div className="dieukhoanform__body">
                                                    <div className="dieukhoanform__body-text">
                                                        <p>
                                                            Chào mừng bạn đến với ứng dụng Dai-ichi Connect của
                                                            Công ty TNHH Bảo hiểm Nhân thọ
                                                            Dai-ichi Việt Nam (“Dai-ichi Life Việt Nam”).
                                                            <br/>
                                                            Trước khi truy cập, cung cấp, đăng tải các thông tin và sử
                                                            dụng các dịch vụ trên ứng dụng Dai-ichi Connect, Quý khách
                                                            vui lòng đọc kỹ và tuân thủ đầy đủ các điều khoản tại Quy
                                                            định về điều kiện và điều khoản sử dụng ứng dụng này (“Quy
                                                            định”). Trường hợp Quý khách không đồng ý với Quy định này,
                                                            vui lòng ngừng việc truy cập và sử dụng ứng dụng Dai-ichi
                                                            Connect.
                                                            <br/>
                                                            Dai-ichi Life Việt Nam có quyền hủy bỏ, chấm dứt, thay đổi
                                                            hoặc cập nhật Quy định này bất kỳ lúc nào mà không cần được
                                                            sự đồng ý của Quý khách. Việc hủy bỏ, chấm dứt, thay đổi
                                                            hoặc cập nhật Quy định này sẽ được Dai-ichi Life Việt Nam
                                                            thông báo cho Quý khách qua địa chỉ thư điện tử (email) hoặc
                                                            số điện thoại của Quý khách hoặc qua ứng dụng Dai-ichi
                                                            Connect.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 1.Nội dung của ứng dụng
                                                            Dai-ichi Connect và Quyền sở hữu trí tuệ</p>
                                                        <p>
                                                            <span>1.1</span> Tất cả nội dung do Dai-ichi Life Việt
                                                            Nam đăng tải trên ứng dụng Dai-ichi Connect hoặc các bộ phận
                                                            cấu thành ứng dụng Dai-ichi Connect này (bao gồm nhưng không
                                                            giới hạn bất kỳ thông tin, dữ liệu, tài liệu, thiết kế, đồ
                                                            họa, giao diện, hình ảnh, nhãn hiệu, tên thương mại, sản
                                                            phẩm, dịch vụ, sau đây gọi chung là “Thông tin”) là thuộc
                                                            quyền sở hữu hợp pháp duy nhất và toàn bộ của Dai-ichi Life
                                                            Việt Nam hoặc được bên thứ ba cho phép Dai-ichi Life Việt
                                                            Nam sử dụng, quản lý và xử lý.
                                                        </p>
                                                        <p>
                                                            <span>1.2</span> Nếu không được đồng ý trước bằng văn
                                                            bản của Dai-ichi Life Việt Nam, Quý khách không được thay
                                                            đổi, xóa, hủy, sao chép, mô phỏng, chuyển giao, xuất bản,
                                                            phân phối hoặc thực hiện bất kỳ hành vi xâm phạm quyền sở
                                                            hữu trí tuệ nào khác đối với ứng dụng Dai-ichi Connect theo
                                                            quy định của pháp luật về sở hữu trí tuệ và pháp luật có
                                                            liên quan.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 2.Đăng ký thành viên, truy
                                                            cập và hủy truy cập ứng dụng Dai-ichi Connect</p>
                                                        <p>
                                                            <span>2.1.</span> Quý khách sẽ trở thành thành viên
                                                            trên
                                                            ứng dụng Dai-ichi Connect khi Quý khách đồng ý với Quy định
                                                            này và đồng ý đăng ký làm thành viên trên ứng dụng Dai-ichi
                                                            Connect (sau đây gọi là “Người dùng”), khi đó, Người dùng sẽ
                                                            có quyền sử dụng ứng dụng Dai-ichi Connect để khởi tạo tài
                                                            khoản, đăng nhập và liên kết với (các) tài khoản mạng xã hội
                                                            cá nhân như Google, Facebook hoặc bất kỳ loại tài khoản nào
                                                            khác được Dai-ichi Life Việt Nam cho phép thể hiện bằng chức
                                                            năng có sẵn trên ứng dụng Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>2.2.</span> Trong suốt quá trình sử dụng ứng dụng
                                                            Dai-ichi Connect, Người dùng phải tuân thủ theo đúng hướng
                                                            dẫn và khuyến cáo sử dụng của Dai-ichi Life Việt Nam được
                                                            công bố trong cẩm nang hướng dẫn sử dụng ứng dụng Dai-ichi
                                                            Connect và bất kỳ hướng dẫn, khuyến cáo nào khác được
                                                            Dai-ichi Life Việt Nam công bố tại từng thời điểm.
                                                        </p>
                                                        <p>
                                                            <span>2.3.</span> Dai-ichi Life Việt Nam có quyền tạm
                                                            ngừng hoặc chấm dứt việc truy cập, sử dụng ứng dụng Dai-ichi
                                                            Connect của Người dùng vào bất kỳ thời điểm nào mà không cần
                                                            được sự đồng ý của Người dùng hay phải thông báo trước cho
                                                            Người dùng.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 3. Phạm vi sử dụng</p>
                                                        <p>
                                                            Phạm vi sử dụng Người dùng chỉ được phép truy cập, sử dụng
                                                            các dịch vụ và thông tin cung cấp bởi ứng dụng Dai-ichi
                                                            Connect này (“Thông tin”) để phục vụ cho hoạt động kinh
                                                            doanh bảo hiểm nhân thọ và phục vụ khách hàng của Dai-ichi
                                                            Life Việt Nam.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 4. Bảo mật thông tin đăng
                                                            nhập, giao dịch</p>
                                                        <p>
                                                            <span>4.1.</span> Người dùng phải giữ bí mật các thông
                                                            tin đăng nhập, giao dịch của ứng dụng Dai-ichi Connect (tên
                                                            đăng nhập, mật khẩu, mã xác thực, mã OTP (nếu có)), đồng
                                                            thời cam kết không chia sẻ hay để lộ thông tin đăng nhập,
                                                            giao dịch của mình cho bất kỳ bên thứ ba nào khác. Người
                                                            dùng sẽ hoàn toàn chịu mọi trách nhiệm trong trường hợp để
                                                            lộ thông tin đăng nhập, giao dịch hoặc cung cấp thông tin
                                                            đăng nhập, giao dịch dẫn đến việc bên thứ ba đăng nhập, giao
                                                            dịch trái phép/ không có sự đồng ý của Người dùng tại ứng
                                                            dụng Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>4.2.</span> Người dùng phải kịp thời thông báo cho
                                                            Dai-ichi Life Việt Nam ngay khi thông tin đăng nhập, giao
                                                            dịch bị mất, bị lộ, hoặc nghi ngờ bị mất, bị lộ.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 5. Không phá hoại</p>
                                                        <p>
                                                            <span>5.1. </span>Người dùng không được thực hiện bất kỳ
                                                            hành động nào nhằm làm ngưng trệ hoặc tê liệt hoạt động của
                                                            ứng dụng Dai-ichi Connect hoặc phá hoại một phần hay toàn bộ
                                                            các dịch vụ, chức năng của ứng dụng Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>5.2. </span>Người dùng không sử dụng hoặc hướng dẫn
                                                            người khác sử dụng công cụ hỗ trợ để gửi, lan truyền, phát
                                                            tán vi rút tin học, chương trình phần mềm có tính năng lấy
                                                            trộm thông tin, phá huỷ dữ liệu máy tính lên mạng Internet;
                                                            làm rối loạn, cản trở hoạt động của ứng dụng Dai-ichi
                                                            Connect và hoạt động kinh doanh của Dai-ichi Life Việt Nam.
                                                            Người dùng không được đánh cắp và sử dụng mật khẩu, khoá mật
                                                            mã, thông tin riêng của tổ chức, cá nhân hoặc phổ biến cho
                                                            người khác sử dụng.
                                                        </p>
                                                        <p>
                                                            <span>5.3. </span>Người dùng sẽ phải chịu trách nhiệm
                                                            trước pháp luật và với Dai-ichi Life Việt Nam nếu vi phạm
                                                            bất kỳ quy định nào được nêu trong Quy định này.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 6. Miễn trừ trách nhiệm đối
                                                            với Dai-ichi Life Việt Nam</p>
                                                        <p>
                                                            <span>6.1. </span>Người dùng đồng ý rằng với việc truy cập
                                                            vào ứng dụng Dai-ichi Connect, Người dùng sẽ tự chịu mọi rủi
                                                            ro đối với việc Người dùng truy cập, kết nối và sử dụng ứng
                                                            dụng Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>6.2. </span>Ngoài các quy định về miễn trừ trách
                                                            nhiệm của Dai-ichi Life Việt Nam theo quy định của Điều kiện
                                                            và điều khoản giao dịch điện tử , Dai-ichi Life Việt Nam sẽ
                                                            không chịu trách nhiệm đối với bất kỳ những thiệt hại, mất
                                                            mát nào dù là trực tiếp hay gián tiếp của Người dùng phát
                                                            sinh do (i) việc truy cập và/hoặc sử dụng ứng dụng Dai-ichi
                                                            Connect gây ra; hoặc (ii) hành vi tiết lộ thông tin đăng
                                                            nhập, mật khẩu, mã OTP (nếu có) hoặc thông tin bảo mật khác
                                                            của Người dùng cho bên thứ ba hoặc; (iii) Người dùng truy
                                                            cập vào đường dẫn, liên kết không do Dai-ichi Life Việt Nam
                                                            phát hành và quản lý.
                                                        </p>
                                                        <p>
                                                            <span>6.3. </span>Với bất kỳ thông tin, tài liệu mà
                                                            Người dùng cung cấp, đăng tải lên ứng dụng Dai-ichi Connect,
                                                            Người dùng phải đảm bảo tính chính xác, trung thực và hợp
                                                            pháp của thông tin, tài liệu đó. Dai-ichi Life Việt Nam được
                                                            miễn trừ mọi khiếu nại, tranh chấp phát sinh, trách nhiệm
                                                            bồi thường giữa Người dùng và bên thứ ba (nếu có) liên quan
                                                            đến việc cung cấp các thông tin, tài liệu này.
                                                        </p>
                                                        <p>
                                                            <span>6.4. </span>Trong quá trình truyền tín hiệu và
                                                            việc tải thông tin, có thể phát sinh những vấn đề truyền tải
                                                            qua mạng internet, ứng dụng Dai-ichi Connect có thể bị treo,
                                                            bị ngừng, bị trì hoãn hoặc có lỗi dữ liệu; hiển thị kết quả
                                                            tham gia có thể không chính xác, lỗi về bảo mật có thể xảy
                                                            ra; tạm ngừng cung cấp dịch vụ để nâng cấp hệ thống; những
                                                            vấn đề khác sẽ được Dai-ichi Life Việt Nam cập nhật và công
                                                            bố nếu cần thiết.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 7. Trách nhiệm và nghĩa vụ
                                                            khác của Người dùng</p>
                                                        <p>Ngoài việc tuân thủ theo quy định tại Điều kiện và điều khoản
                                                            giao dịch điện tử , Người dùng có trách nhiệm tuân thủ các
                                                            quy định sau:</p>
                                                        <p>
                                                            <span>7.1. </span>Người dùng cam kết được quyền hoặc đã
                                                            được sự đồng ý rõ ràng và hợp lệ của bất kỳ cá nhân có liên
                                                            quan nào về việc Người dùng được cung cấp tất cả thông tin
                                                            của cá nhân đó cho Dai-ichi Life Việt Nam (nếu có).
                                                        </p>
                                                        <p>
                                                            <span>7.2. </span>Cung cấp đầy đủ, chính xác tất cả các
                                                            thông
                                                            tin theo yêu cầu của Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <p>
                                                            <span>7.3. </span>Người dùng có nghĩa vụ, bằng chi phí
                                                            của mình, trang bị đầy đủ, bảo dưỡng thường xuyên nhằm đảm
                                                            bảo chất lượng cho các loại máy móc, thiết bị kết nối, phần
                                                            mềm hệ thống, phần mềm ứng dụng,... để có thể kết nối, đăng
                                                            nhập an toàn vào ứng dụng Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>7.4. </span>Người dùng cam kết tuân thủ các thủ tục
                                                            đăng ký, trình tự giao dịch và các hướng dẫn khác của
                                                            Dai-ichi Life Việt Nam liên quan đến việc sử dụng ứng dụng
                                                            Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>7.5. </span>Chịu trách nhiệm trước pháp luật và bồi
                                                            thường thiệt hại phát sinh cho Dai-ichi Life Việt Nam và bất
                                                            kỳ bên thứ ba có liên quan nào trong trường hợp Người dùng
                                                            vi phạm bất kỳ quy định nào của Quy định này và pháp luật
                                                            Việt Nam.
                                                        </p>
                                                        <p>
                                                            <span>7.6. </span>Ngay cả khi không còn là Người dùng của
                                                            ứng dụng Dai-ichi Connect, Người dùng vẫn bị ràng buộc bởi
                                                            các điều khoản của Quy định này và chịu trách nhiệm đối với
                                                            các vi phạm đã thực hiện trong thời gian Người dùng được
                                                            quyền sử dụng dịch vụ và truy cập thông tin của ứng dụng
                                                            Dai-ichi Connect.
                                                        </p>
                                                        <p>
                                                            <span>7.7. </span>Khi truy cập vào ứng dụng Dai-ichi
                                                            Connect, Người dùng đã đồng ý miễn trừ Dai-ichi Life Việt
                                                            Nam quy định tại Điều 6 nêu trên và miễn trừ cho Dai-ichi
                                                            Life Việt Nam khỏi bất kỳ khiếu nại, tranh chấp nào dưới bất
                                                            kỳ hình thức nào từ Người dùng hoặc bất kỳ bên thứ ba nào
                                                            xuất phát từ việc truy cập, sử dụng ứng dụng Dai-ichi
                                                            Connect của Người dùng đối với bất kỳ thiệt hại, phí tổn,
                                                            hành động, khiếu kiện, khiếu nại, bồi thường, chi phí (bao
                                                            gồm cả chi phí pháp lý) hoặc trách nhiệm nào mà Dai-ichi
                                                            Life Việt Nam phải gánh chịu do hậu quả của việc truy cập,
                                                            sử dụng ứng dụng Dai-ichi Connect nói trên hoặc do Quý khách
                                                            vi phạm hoặc không tuân thủ bất kỳ nội dung nào trong bản
                                                            Quy định này.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 8. Tính ràng buộc và việc
                                                            điều chỉnh, sửa đổi Quy định</p>
                                                        <p>
                                                            <span>8.1. </span>Đây là Quy định mang tính ràng buộc giữa
                                                            Người dùng và Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <p>
                                                            <span>8.2. </span>Quy định này có thể được Dai-ichi Life
                                                            Việt Nam đơn phương điều chỉnh hay sửa đổi bất kỳ lúc nào
                                                            nếu Dai-ichi Life Việt Nam xét thấy là cần thiết và phù hợp.
                                                            Nội dung điều chỉnh hoặc sửa đổi sẽ được công bố tại ứng
                                                            dụng Dai-ichi Connect và có hiệu lực ngay từ thời điểm được
                                                            công bố.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                </div>
                                                <div className="dieukhoanform__title no-margin-app"
                                                     style={{flexDirection: 'column'}}>
                                                    <p className="basic-bold" style={{marginTop: 8}}>PHẦN II:</p>
                                                    <p className="basic-bold" style={{marginTop: 8}}>Quy định về điều
                                                        kiện và điều khoản giao dịch điện tử (ĐK&ĐKGDĐT)</p>
                                                </div>
                                                <div className="dieukhoanform__body">
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 1.Định nghĩa</p>
                                                        <p>
                                                            <span>1.1.</span> <span style={{fontWeight: '700'}}>“Giao Dịch Điện Tử (GDĐT)”</span> là
                                                            các giao
                                                            dịch được thực hiện trực tiếp bởi Người Dùng (ND) hoặc bởi
                                                            Dai-ichi Life Việt Nam hay các lệnh được gửi đến Dai-ichi
                                                            Life Việt Nam hoặc ND thông qua Phương Tiện Điện Tử.
                                                        </p>
                                                        <p>
                                                            <span>1.2.</span> <span style={{fontWeight: '700'}}>“Người Dùng (ND)”</span> là
                                                            Bên mua bảo hiểm
                                                            cá nhân hoặc người đại diện theo pháp luật/đại diện theo ủy
                                                            quyền của Bên mua bảo hiểm là tổ chức hoặc Người được bảo
                                                            hiểm hoặc Cha, mẹ/Người giám hộ của Người được bảo hiểm dưới
                                                            18 tuổi hoặc Người thụ hưởng hoặc bất cứ cá nhân nào đăng ký
                                                            làm thành viên để truy cập/sử dụng trên các trang thông tin
                                                            điện tử/ứng dụng/nền tảng trực tuyến của Dai-ichi Life Việt
                                                            Nam để thực hiện GDĐT.
                                                        </p>
                                                        <p>
                                                            <span>1.3.</span> <span style={{fontWeight: '700'}}>“Dịch Vụ Giao Dịch Điện Tử (DVGDĐT)”</span> là
                                                            các dịch vụ được cung cấp bởi Dai-ichi Life Việt Nam cho ND
                                                            để thực hiện GDĐT.
                                                        </p>
                                                        <p>
                                                            <span>1.4.</span> <span style={{fontWeight: '700'}}>“Phương Tiện Điện Tử (PTĐT)”</span> là
                                                            phương tiện hoạt động dựa trên công nghệ điện, điện tử, kỹ
                                                            thuật số, từ tính, truyền dẫn không dây, quang học, điện từ
                                                            hoặc công nghệ tương tự (bao gồm nhưng không giới hạn như
                                                            internet, phần mềm hệ thống, phần mềm ứng dụng, thư điện tử,
                                                            nhắn tin qua điện thoại di động và các thiết bị điện tử
                                                            khác).
                                                        </p>
                                                        <p>
                                                            <span>1.5.</span> <span style={{fontWeight: '700'}}>“Hệ Thống Thông Tin (HTTT)”</span> là
                                                            hệ thống được tạo lập để gửi, nhận, lưu trữ, hiển thị hoặc
                                                            thực hiện các xử lý khác đối với Thông Điệp Dữ Liệu (bao gồm
                                                            nhưng không giới hạn các chương trình, ứng dụng phần mềm
                                                            được thiết lập tại địa chỉ https://kh.dai-ichi-life.com.vn
                                                            và/hoặc tại các máy chủ; điện thoại; máy fax; thiết bị
                                                            truyền dữ liệu, ghi âm hay các thiết bị khác).
                                                        </p>
                                                        <p>
                                                            <span>1.6.</span> <span style={{fontWeight: '700'}}>“Thông Điệp Dữ Liệu (TĐDL)”</span> là
                                                            thông tin được tạo ra, được gửi đi, được nhận và được lưu
                                                            trữ bằng PTĐT.
                                                        </p>
                                                        <p>
                                                            <span>1.7.</span> <span style={{fontWeight: '700'}}>“Mã Xác Thực”</span> là
                                                            chuỗi ký tự (chữ số, chữ cái, dấu, ký tự đặc biệt) được HTTT
                                                            tạo ra hoặc ghi nhận để gắn với người thực hiện GDĐT tại mỗi
                                                            lần thực hiện GDĐT nhằm phục vụ việc xác thực.
                                                        </p>
                                                        <p>
                                                            <span>1.8.</span> <span style={{fontWeight: '700'}}>“Chứng Từ Điện Tử (CTĐT)”</span> là
                                                            các dữ liệu được tạo ra, gửi đi, nhận và lưu trữ bằng PTĐT
                                                            khi thực hiện GDĐT; bao gồm chứng từ, báo cáo, hợp đồng,
                                                            thỏa thuận, thông tin giao dịch và các loại thông tin, dữ
                                                            liệu khác theo quy định của pháp luật.
                                                        </p>
                                                        <p>
                                                            <span>1.9.</span> <span style={{fontWeight: '700'}}>“Thông Tin Đăng Nhập (TTĐN)”</span> bao
                                                            gồm Mã Truy Cập và Mật Khẩu Truy Cập.
                                                        </p>
                                                        <p>
                                                            <span>1.10.</span> <span style={{fontWeight: '700'}}>“Mật Khẩu Truy Cập (MKTC)”</span> bao
                                                            gồm tất cả cụm từ, mật mã, chữ số, ký hiệu hoặc các biện
                                                            pháp xác thực khác được bảo mật, và được cung cấp cho ND
                                                            hoặc được ND đăng ký với Dai-ichi Life Việt Nam để xác thực
                                                            việc truy cập vào HTTT.
                                                        </p>
                                                        <p>
                                                            <span>1.11.</span> <span style={{fontWeight: '700'}}>“Rủi Ro Của GDĐT”</span> là
                                                            những rủi ro có thể xảy ra trong quá trình thực hiện GDĐT
                                                            khiến cho GDĐT không được thực hiện hoặc thực hiện không
                                                            đúng với yêu cầu ban đầu của ND hoặc Dai-ichi Life Việt Nam,
                                                            bao gồm nhưng không giới hạn các rủi ro:
                                                        </p>
                                                        <div>
                                                            <p>
                                                                <span style={{
                                                                    marginRight: 8, marginLeft: 16
                                                                }}>a.</span> Rủi ro hệ
                                                                thống phần cứng hoặc hệ thống phần mềm không hoạt động
                                                                hoặc hoạt động không đúng chức năng được thiết kế, xuất
                                                                phát từ các nguyên nhân lũ lụt, hỏa hoạn, thiên tai,
                                                                chập điện, hư hỏng tự nhiên, hành vi phá hoại hoặc gian
                                                                lận của con người,…;
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>b.</span> Rủi ro đường truyền điện thoại,
                                                                internet, thư điện tử,
                                                                tin nhắn điện thoại di động và các thiết bị điện tử khác
                                                                của nhà cung cấp dịch vụ gặp sự cố kỹ thuật như đứt
                                                                đường truyền, quá tải hoặc những sự cố tương tự có thể
                                                                ảnh hưởng đến việc thực hiện GDĐT;
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>c.</span> Rủi ro hệ thống máy tính, PTĐT của ND
                                                                hoặc Dai-ichi Life
                                                                Việt Nam bị mất nguồn điện, bị tấn công, nhiễm virus, bị
                                                                tiết lộ hoặc bị đánh cắp các thông tin về GDĐT (bao gồm
                                                                Thông Tin Đăng Nhập, Mã Xác Thực, số điện thoại, thư
                                                                điện tử,…)
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>d.</span> Rủi ro tài khoản/PTĐT của ND hoặc
                                                                Dai-ichi Life Việt Nam bị sử dụng một cách trái phép bởi
                                                                một bên thứ ba;
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>e.</span> Rủi ro có liên quan đến hành vi tiết lộ
                                                                mật khẩu, mã OTP hoặc thông tin bảo mật khác của ND cho
                                                                bên thứ ba, hoặc ND truy cập vào đường dẫn, liên kết
                                                                không phải do Dai-ichi Life Việt Nam quản lý.
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>f.</span> Bất kỳ GDĐT nào thực hiện ngoài ý
                                                                muốn của ND do ND chưa hiểu rõ cách thức thực hiện giao
                                                                dịch mà bản thân ND đã đăng ký với Dai-ichi Life Việt
                                                                Nam, do sai sót trong quá trình ND thao tác với các PTĐT
                                                                hoặc các lý do chủ quan khác.
                                                            </p>
                                                            <p>
                                                                <span
                                                                    style={{
                                                                        marginRight: 8, marginLeft: 16
                                                                    }}>g.</span> Các rủi ro khác liên quan đến sự kiện
                                                                bất khả kháng.
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 2.Trách nhiệm của Người
                                                            Dùng</p>
                                                        <p>
                                                            <span>2.1.</span> Tự mình chịu mọi thiệt hại phát sinh
                                                            từ hoặc có liên quan đến (các) Rủi Ro Của GDĐT do lỗi của ND
                                                            gây ra và/hoặc được xác định là do lỗi của ND theo quy định
                                                            pháp luật có liên quan. Ngoài ra, ND chịu toàn bộ thiệt hại
                                                            phát sinh từ hoặc có liên quan đến hành vi tiết lộ Thông Tin
                                                            Đăng Nhập hoặc Mã Xác Thực hoặc thông tin của PTĐT hoặc
                                                            thông tin bảo mật khác của ND cho bên thứ ba, hoặc ND truy
                                                            cập vào đường dẫn, liên kết không phải do Dai-ichi Life Việt
                                                            Nam quản lý và/hoặc các trường hợp khác theo quy định pháp
                                                            luật có liên quan.
                                                        </p>
                                                        <p>
                                                            <span>2.2.</span> Chịu trách nhiệm bảo mật toàn bộ các
                                                            thông tin, bảo quản các PTĐT liên quan đến GDĐT và thực hiện
                                                            tất cả biện pháp cần thiết ở mức độ cao nhất để ngăn ngừa sự
                                                            sử dụng trái phép các thông tin, Mã Xác Thực, TTĐN và PTĐT.
                                                            Cho mục đích này, ND cam kết không bao giờ tiết lộ thông
                                                            tin, Mã Xác Thực và TTĐN hoặc đưa PTĐT cho bất kỳ ai bằng
                                                            bất cứ hình thức nào, dù vô tình hay cố ý, tại bất kỳ nơi
                                                            nào và vào bất kỳ thời điểm nào. ND đồng thời tự mình chịu
                                                            trách nhiệm, thiệt hại, tổn thất đối với mọi GDĐT được thực
                                                            hiện thông qua Mã Xác Thực và TTĐN.
                                                        </p>
                                                        <p>
                                                            <span>2.3.</span> Thông báo ngay lập tức cho Dai-ichi Life
                                                            Việt Nam theo cách thức quy định tại Mục 3.3 sau đây nếu
                                                            tính bảo mật của Mã Xác Thực và TTĐN bị vi phạm.
                                                        </p>
                                                        <p>
                                                            <span>2.4.</span> Thay đổi MKTC ban đầu trong thời hạn 01
                                                            (một) ngày làm việc kể từ ngày được cung cấp.
                                                            Dai-ichi Life Việt Nam được miễn trừ trách nhiệm với mọi
                                                            thiệt hại phát sinh từ hoặc có liên quan đến việc không đổi
                                                            MKTC ban đầu theo Điều 2.4 này.
                                                        </p>
                                                        <p>
                                                            <span>2.5.</span> Thực hiện tất cả biện pháp hợp lý nhằm
                                                            đảm bảo tính an toàn, tính bảo mật, tính tương thích cho
                                                            PTĐT mà ND sử dụng để kết nối truy cập vào HTTT, bao gồm
                                                            không giới hạn cài đặt và thường xuyên cập nhật chương trình
                                                            diệt virus, cập nhật phiên bản ứng dụng mới nhất, thông báo
                                                            cho Dai-ichi Life Việt Nam khi phát hiện hoặc nghi ngờ có
                                                            hiện tượng truy cập trái phép vào HTTT, hoặc nghi ngờ TTĐN
                                                            không còn bảo mật.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 3.Trách nhiệm của Dai-ichi
                                                            Life Việt Nam</p>
                                                        <p>
                                                            <span>3.1.</span> Nỗ lực tối đa để bảo đảm sự an toàn về mặt
                                                            kỹ thuật nhằm bảo mật các dữ liệu được truyền tải trong
                                                            GDĐT.
                                                        </p>
                                                        <p>
                                                            <span>3.2.</span> Dai-ichi Life Việt Nam sẽ được miễn
                                                            trừ trách nhiệm đối với các trường hợp, bao gồm trường hợp
                                                            bị nhiễm vi-rút, hành vi phá hoại ác ý từ bên thứ ba gây ra
                                                            đối với PTĐT và/hoặc HTTT cũng như các trang web liên kết
                                                            của Dai-ichi Life Việt Nam; lỗi, sai sót, gián đoạn, chậm
                                                            trễ trong hoạt động hay truyền dữ liệu, lỗi hệ thống hay
                                                            đường truyền, mất điện; Sự kiện bất khả kháng.
                                                        </p>
                                                        <p>
                                                            <span>3.3.</span> Khóa quyền sử dụng của ND ngay khi
                                                            Dai-ichi Life Việt Nam nhận được thông báo trực tiếp thông
                                                            qua số Hotline (028) 3810 0888 của Dai-ichi Life Việt Nam
                                                            (sau khi đã hoàn tất các bước xác minh thông tin khách hàng)
                                                            hoặc bằng văn bản của ND đến địa chỉ: Tòa nhà Dai-ichi Life,
                                                            149-151 Nguyễn Văn Trỗi, Phường 11, Quận Phú Nhuận, TP.HCM,
                                                            tùy hình thức được Dai-ichi Life Việt Nam áp dụng.
                                                        </p>
                                                        <p>
                                                            <span>3.4.</span> Chấm dứt việc sử dụng DVGDĐT trong vòng
                                                            tối đa 07 (bảy) ngày kể từ ngày nhận được yêu cầu bằng văn
                                                            bản của ND về việc chấm dứt sử dụng DVGDĐT.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 4. Nguyên tắc xác định lỗi
                                                            và trách nhiệm</p>
                                                        <p>
                                                            Trừ trường hợp được miễn trừ trách nhiệm theo quy định tại
                                                            ĐK&ĐKGDĐT này và/hoặc trường hợp bất khả kháng và/hoặc theo
                                                            quy định pháp luật có liên quan, Bên được xác định có lỗi
                                                            trong việc vi phạm bất kỳ nghĩa vụ nào được quy định tại
                                                            ĐK&ĐKGDĐT này và/hoặc quy định pháp luật có liên quan thì sẽ
                                                            phải chịu toàn bộ trách nhiệm giải quyết các vấn đề phát
                                                            sinh và bồi thường thiệt hại tương ứng với phạm vi lỗi đó
                                                            (nếu có).
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 5. Sửa đổi, bổ sung nội dung
                                                            Điều khoản</p>
                                                        <p>
                                                            Dai-ichi Life Việt Nam có quyền sửa đổi, bổ sung ĐK&ĐKGDĐT
                                                            này bất cứ lúc nào nhưng phải thông báo trước khi có hiệu
                                                            lực cho ND bằng các hình thức như: gửi thư thông qua địa chỉ
                                                            thư điện tử (email), số điện thoại mà ND cung cấp. Theo đó,
                                                            ĐK&ĐKGDĐT được sửa đổi, bổ sung sẽ được công bố trên website
                                                            của Dai-ichi Life Việt Nam và/hoặc HTTT, và/hoặc niêm yết
                                                            tại các văn phòng của Dai-ichi Life Việt Nam, và/hoặc bất cứ
                                                            phương thức nào phù hợp với quy định pháp luật.
                                                        </p>
                                                        <p>
                                                            ĐK&ĐKGDĐT sửa đổi, bổ sung sẽ có giá trị ràng buộc nếu ND
                                                            tiếp tục thực hiện GDĐT sau khi ĐK&ĐKGDĐT sửa đổi, bổ sung
                                                            có hiệu lực.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                    <div className="dieukhoanform__body-text">
                                                        <p style={{fontWeight: '600'}}>Điều 6. Thời gian hiệu lực</p>
                                                        <p>
                                                            ND đồng ý với toàn bộ nội dung của ĐK&ĐKGDĐT và (các)
                                                            ĐK&ĐKGDĐT sửa đổi, bổ sung (nếu có) kể từ thời điểm truy
                                                            cập, sử dụng các trang thông tin điện tử/ứng dụng/nền tảng
                                                            trực tuyến của Dai-ichi Life Việt Nam để thực hiện GDĐT.
                                                            ĐK&ĐKGDĐT sẽ được duy trì hiệu lực cho đến khi có sự kiện
                                                            pháp lý làm chấm dứt việc sử dụng DVGDĐT.
                                                        </p>
                                                    </div>
                                                    <div className="dash-border"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="camket"
                             className={state.currentTab === TABS.COMMITMENT ? "tabpane-tab active" : "tabpane-tab"}>
                            <div className="dieukhoan-page">
                                <div className="dieukhoan-form">
                                    <div className="dieukhoan-form__body">
                                        <div className="dieukhoanform-wrapper">
                                            <div className="dieukhoanform">
                                                <div className="dieukhoanform__title no-margin-app">
                                                    <p className="basic-bold">Chính sách bảo mật thông tin</p>
                                                </div>
                                                <div className="dieukhoanform__body">
                                                    <div className="dieukhoanform__body-text">
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Công ty Bảo hiểm nhân thọ Dai-ichi Việt Nam  </span>(sau
                                                            đây
                                                            gọi là <span>“Dai-ichi Life Việt Nam”</span>) luôn nỗ
                                                            lực cung cấp cho Bạn những giải pháp tài chính vượt trội
                                                            và đáng tin cậy nhằm thực hiện sứ mệnh bảo vệ an toàn
                                                            tài chính cho Bạn và gia đình. Đồng thời, Dai-ichi Life
                                                            Việt Nam luôn đề cao và tôn trọng sự riêng tư, bảo mật
                                                            và bảo vệ dữ liệu cá nhân của Bạn.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Trong quá trình xử lý thông tin của Bạn, Dai-ichi Life Việt Nam cam kết:</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent">
                                                            Bảo vệ an toàn thông tin, dữ liệu cá nhân của Bạn bằng
                                                            cách tuân thủ các quy định pháp luật Việt Nam hiện hành
                                                            về bảo vệ dữ liệu và bảo mật thông tin cá nhân, đồng
                                                            thời đảm bảo sự tuân thủ của đội ngũ nhân viên với các
                                                            chuẩn mực nghiêm ngặt về sự an toàn và tính bảo mật.
                                                        </p>
                                                        <p className="dieukhoanform__subContent">
                                                            Quản lý thông tin cá nhân dưới sự kiểm soát thận trọng.
                                                            Dai-ichi Life Việt Nam có đội ngũ nhân viên và phương
                                                            pháp bảo mật thích hợp để bảo vệ thông tin cá nhân và
                                                            tiến hành những biện pháp đề phòng để ngăn chặn và giảm
                                                            thiểu tối đa các rủi ro có thể xảy ra.
                                                        </p>
                                                        <p className="dieukhoanform__subContent">
                                                            Thu thập và xử lý các loại dữ liệu cá nhân (bao gồm các
                                                            dữ liệu cơ bản và dữ liệu nhạy cảm) theo đúng các mục
                                                            đích và tuân thủ các quy định tại:
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - <a style={{
                                                            display: 'inline',
                                                            textDecoration: 'underline',
                                                            fontSize: '14px',
                                                        }}
                                                                 href={`${FE_BASE_URL + '/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf'}`}
                                                                 className="simple-brown2" target='_blank'>Quy
                                                            định bảo vệ và xử lý dữ liệu cá nhân đối với
                                                            khách hàng</a> đã được Dai-ichi Life Việt Nam đăng tải
                                                            công khai trên trang thông tin điện tử, ứng dụng, nền
                                                            tảng bán hàng trực tuyến của Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - Và/hoặc các mẫu Quy định bảo vệ và xử lý dữ liệu cá
                                                            nhân gửi riêng cho từng nhóm đối tượng là đại lý, quản
                                                            lý, ứng viên, người lao động,… của Dai-ichi Life Việt
                                                            Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            (Các quy định bảo vệ và xử lý dữ liệu cá nhân nêu trên
                                                            sau đây được gọi chung là “Quy định bảo vệ và xử lý dữ
                                                            liệu cá nhân”).
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Khi Dai-ichi Life Việt Nam sử dụng từ <span>“Bạn”</span> tại
                                                            Chính Sách
                                                            Bảo Mật Thông Tin này, nghĩa là Dai-ichi Life Việt Nam muốn
                                                            nhắc đến tất cả: (i) Các khách hàng tiềm năng (như khách
                                                            hàng quan tâm đến dịch vụ, sản phẩm của Dai-ichi Life Việt
                                                            Nam, khách hàng ghé thăm, đăng nhập trang thông tin điện tử,
                                                            các ứng dụng bán hàng trực tuyến, kênh truyền thông, mạng xã
                                                            hội, fanpage… của Dai-ichi Life Việt Nam), khách hàng hiện
                                                            hữu (như bên mua bảo hiểm, người được bảo hiểm, người thụ
                                                            hưởng và những bên liên quan khác), (ii) đại lý (kể cả đại
                                                            lý tiềm năng và hiện hữu), (iii) người lao động, ứng viên,
                                                            người thử việc/học việc, (iv) đối tác, nhà thầu và các nhà
                                                            cung cấp dịch vụ và những bên liên quan khác.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Bằng việc sử dụng các sản phẩm, dịch vụ của Dai-ichi
                                                            Life Việt Nam/tham gia các giao dịch/cung cấp thông tin
                                                            cho Dai-ichi Life Việt Nam/tiếp tục sử dụng trang thông
                                                            tin điện tử, ứng dụng, nền tảng bán hàng trực tuyến, Bạn
                                                            đã hiểu và đồng ý với toàn bộ nội dung của <span>Chính Sách
                                                                Bảo Mật Thông Tin</span> này (bao gồm cả các Quy định
                                                            bảo vệ và
                                                            xử lý dữ liệu cá nhân được đề cập đến tại Chính Sách Bảo
                                                            Mật này) của Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Dai-ichi Life Việt Nam thu thập và xử lý thông tin của Bạn như thế nào?</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Để Dai-ichi Life Việt Nam có thể cung cấp các sản phẩm,
                                                            dịch vụ cho Bạn và/hoặc hợp tác và/hoặc xử lý các yêu
                                                            cầu của Bạn, Dai-ichi Life Việt Nam có thể cần phải
                                                            và/hoặc được yêu cầu phải thu thập dữ liệu cá nhân có
                                                            liên quan đến Bạn và các cá nhân có liên quan của Bạn.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam có thể thu thập trực tiếp hoặc gián
                                                            tiếp những dữ liệu cá nhân này các nguồn khác nhau thông qua
                                                            những cách thức minh bạch và hợp pháp được quy định cụ thể
                                                            trong <span>Quy định bảo vệ và xử lý dữ liệu cá nhân</span>,
                                                            chẳng hạn
                                                            như: (i) Trực tiếp từ Bạn khi Bạn cung cấp thông tin cho
                                                            Dai-ichi Life Việt Nam hoặc gián tiếp thông qua việc Bạn sử
                                                            dụng các trang thông tin điện tử, ứng dụng, nền tảng bán
                                                            hàng trực tuyến hoặc nền tảng truyền thông xã hội của
                                                            Dai-ichi Life Việt Nam hoặc bất kỳ phương tiện, cách thức
                                                            nào khác; (ii) Thông qua quan hệ được thiết lập giữa
                                                            Dai-ichi Life Việt Nam và Bạn khi Bạn sử dụng các sản phẩm,
                                                            dịch vụ của Dai-ichi Life Việt Nam hoặc tham gia vào các
                                                            chương trình, hoạt động hợp pháp của Dai-ichi Life Việt Nam
                                                            hoặc khi Bạn có hợp tác, ký hợp đồng với Dai-ichi Life Việt
                                                            Nam; (iii) Từ các bên thứ ba có quan hệ với Bạn, như người
                                                            sử dụng lao động, các thành viên gia đình, người giám hộ hợp
                                                            pháp, cộng sự hoặc người thụ hưởng sản phẩm và dịch vụ,…;
                                                            (iv) Từ nhà môi giới hoặc một bên trung gian khác (ví dụ:
                                                            đại lý, nhà phân phối, môi giới, đối tác kinh doanh), các
                                                            bên mà Dai-ichi Life Việt Nam có hợp tác để cung cấp sản
                                                            phẩm hoặc dịch vụ hoặc cung cấp báo giá cho Bạn; hoặc các
                                                            bên thứ ba cung cấp hợp pháp các thông tin nhân khẩu học,
                                                            chi tiết các phương tiện đi lại, lịch sử yêu cầu bồi thường,
                                                            thông tin về gian lận, danh sách quảng cáo tiếp thị và thông
                                                            tin khác để giúp cải thiện sản phẩm và dịch vụ của Dai-ichi
                                                            Life Việt Nam; (v) Từ hoạt động phân tích các hoạt động của
                                                            Bạn trong quá trình sử dụng các sản phẩm, dịch vụ,… tại
                                                            Dai-ichi Life Việt Nam; (vi) Thông qua các tập tin được tạo
                                                            ra (cookie), dịch vụ định vị và/hoặc địa chỉ IP, khi Bạn
                                                            truy cập hoặc khi Bạn điền vào biểu mẫu liên hệ với Dai-ichi
                                                            Life Việt Nam trên trang thông tin điện tử hoặc ứng dụng
                                                            hoặc hoặc nền tảng bán hàng trực tuyến của Dai-ichi Life
                                                            Việt Nam; (vii) Từ CCTV để ghi âm, ghi hình tại nơi Dai-ichi
                                                            Life Việt Nam thực hiện hoạt động kinh doanh và làm việc;
                                                            (viii) Thông qua Bảng câu hỏi và thông tin liên lạc chi tiết
                                                            khi Bạn tham gia khảo sát, hội nghị nhà đầu tư, các buổi hội
                                                            thảo hoặc khi Bạn cập nhật thông tin liên lạc/thông tin cá
                                                            nhân/cư trú của Bạn với Dai-ichi Life Việt Nam trên trang
                                                            thông tin điện tử hoặc ứng dụng hoặc nền tảng bán hàng trực
                                                            tuyến của Dai-ichi Life Việt Nam; (ix) Từ các cơ quan có
                                                            thẩm quyền tại Việt Nam như Ngân hàng Nhà Nước Việt Nam, Bộ
                                                            Tài chính, Cơ quan Công an, các cơ quan có thẩm quyền khác
                                                            theo quy định pháp luật Việt Nam; (x) Từ Công ty mẹ, công ty
                                                            liên kết, các Công ty thành viên trong tập đoàn mà Dai-ichi
                                                            Việt Nam là thành viên; (xi) Từ các nguồn khác như Công ty
                                                            bảo hiểm, Cơ quan phòng chống gian lận, tổ chức tham chiếu
                                                            tín dụng, người cho vay khác và các thông tin đã được công
                                                            bố công khai (ví dụ: danh bạ điện thoại, phương tiện truyền
                                                            thông xã hội, các trang thông tin điện tử, mạng xã hội, các
                                                            bài báo); các tổ chức thu hồi nợ; các tổ chức khác để hỗ trợ
                                                            phòng ngừa và phát hiện tội phạm, cảnh sát và các cơ quan
                                                            thực thi pháp luật; (xii) Từ những nguồn của bên thứ ba
                                                            khác, mà Bạn đồng ý việc chia sẻ/cung cấp dữ liệu cá nhân,
                                                            hoặc từ những nguồn mà việc thu thập được pháp luật yêu cầu
                                                            hoặc cho phép; (xi) các nguồn khác được quy định cụ thể
                                                            trong <span>Quy định bảo vệ và xử lý dữ liệu cá nhân</span>.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Đồng thời, Dai-ichi Life Việt Nam sẽ sử dụng thông tin cá
                                                            nhân của Bạn phù hợp với mục đích theo <span>Quy
                                                                    định bảo vệ và xử lý dữ liệu cá nhân</span> và <span>Chính Sách
                                                                    Bảo Mật Thông Tin</span> này.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Đối với khách hàng, thì phạm vi loại dữ liệu cá nhân mà
                                                            Dai-ichi Life Việt Nam thu thập và xử lý sẽ cho các mục đích
                                                            sau đây:
                                                        </p>
                                                        <div className="table-container">
                                                            <div className="table-clauses-wrapper">
                                                                <table border="1">
                                                                    <thead>
                                                                    <tr>
                                                                        <th rowSpan="2">STT</th>
                                                                        <th rowSpan="2">Mục đích xử lý Dữ liệu cá nhân
                                                                        </th>
                                                                        <th colSpan="2">Loại dữ liệu cá nhân</th>
                                                                        <th rowSpan="2">
                                                                            Bên có liên quan đến Mục đích Xử lý dữ liệu
                                                                            cá nhân
                                                                        </th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Dữ liệu cá nhân cơ bản</th>
                                                                        <th>Dữ liệu cá nhân nhạy cảm</th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <tr>
                                                                        <td>1</td>
                                                                        <td>
                                                                            <span>Xác minh định danh và nhận biết Khách hàng, </span>bao
                                                                            gồm:
                                                                            <ul>
                                                                                <li className="no-bullet">
                                                                                    1. Xác minh tính chính xác, đầy
                                                                                    đủ của các thông tin được
                                                                                    Khách hàng cung cấp;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    2. Xác định hoặc xác thực danh
                                                                                    tính của Khách hàng và thực
                                                                                    hiện quy trình xác thực Khách hàng.
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    (Gọi chung là <span>“Mục đích
                                                                            1”</span>)
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            <ul>
                                                                                <li>Họ, chữ đệm và tên khai sinh, tên
                                                                                    gọi khác (nếu có);
                                                                                </li>
                                                                                <li>
                                                                                    Ngày, tháng, năm sinh; ngày, tháng,
                                                                                    năm chết hoặc mất tích;
                                                                                    Giới tính; Quốc tịch;
                                                                                </li>
                                                                                <li>
                                                                                    Nơi sinh, nơi đăng ký khai sinh, nơi
                                                                                    thường trú, nơi tạm trú,
                                                                                    nơi ở hiện tại, quê quán, địa chỉ
                                                                                    liên hệ;{" "}
                                                                                </li>
                                                                                <li>
                                                                                    Hình ảnh của cá nhân; Số điện thoại;
                                                                                    Email; số căn cước công
                                                                                    dân/chứng minh nhân dân/số định danh
                                                                                    cá nhân/số hộ chiếu, số
                                                                                    mã số thuế cá nhân, số bảo hiểm xã
                                                                                    hội, số thẻ bảo hiểm y tế;
                                                                                    Bản sao căn cước công dân/chứng minh
                                                                                    nhân dân/mã định danh cá
                                                                                    nhân/giấy khai sinh/trích lục khai
                                                                                    tử/giấy báo tử;
                                                                                </li>
                                                                                <li>
                                                                                    Tình trạng hôn nhân; Thông tin về
                                                                                    mối quan hệ gia đình (cha
                                                                                    mẹ, con cái và các thành viên khác
                                                                                    trong gia đình có liên quan
                                                                                    đến sản phẩm, dịch vụ);
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin về tài khoản số của cá
                                                                                    nhân; Chức vụ, Nghề nghiệp;
                                                                                    Thu nhập bình quân/Thu nhập gia
                                                                                    đình;
                                                                                </li>
                                                                                <li>Chữ ký, chữ viết, bao gồm cả chữ ký
                                                                                    điện tử, chữ ký số;
                                                                                </li>
                                                                                <li>Thông tin liên quan đến HĐBH mà
                                                                                    Khách hàng tham gia.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            <ul>
                                                                                <li>Quan điểm tôn giáo;</li>
                                                                                <li>Thông tin liên quan đến nguồn gốc
                                                                                    dân tộc;
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin về thuộc tính vật lý, đặc
                                                                                    điểm sinh học riêng của cá
                                                                                    nhân; hình ảnh, dữ liệu sinh trắc
                                                                                    học (dấu vân tay, mống mắt,
                                                                                    gương mặt,...);
                                                                                </li>
                                                                                <li>
                                                                                    Bản ghi âm, ghi hình, bao gồm cả các
                                                                                    cuộc trò chuyện của với
                                                                                    Dai-ichi Life Việt Nam thông qua các
                                                                                    kênh liên lạc của
                                                                                    Dai-ichi Life Việt Nam và/hoặc do
                                                                                    Dai-ichi Life Việt Nam thực
                                                                                    hiện;
                                                                                </li>
                                                                                <li>
                                                                                    Dữ liệu về tội phạm, hành vi phạm
                                                                                    tội được thu thập, lưu trữ
                                                                                    bởi các cơ quan thực thi pháp luật;
                                                                                </li>
                                                                                <li>Thông tin định danh theo quy định
                                                                                    của pháp luật.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Dai-ichi Life Việt Nam có thể sẽ tiết lộ,
                                                                            chia sẻ những dữ liệu cá
                                                                            nhân của Khách hàng hoặc của các bên thứ ba
                                                                            có liên quan đến Khách
                                                                            hàng, cho một hoặc các bên dưới đây:
                                                                            <ul>
                                                                                <li>
                                                                                    Nhân viên, người lao động của
                                                                                    Dai-ichi Life Việt Nam; Công ty
                                                                                    mẹ, Công ty con, Công ty liên kết
                                                                                    của Dai-ichi Life Việt Nam,
                                                                                    Công ty thành viên trong tập đoàn mà
                                                                                    Dai-ichi Life Việt Nam là
                                                                                    thành viên;
                                                                                </li>
                                                                                <li>
                                                                                    Đại lý bảo hiểm, môi giới bảo hiểm,
                                                                                    Doanh nghiệp bảo hiểm;
                                                                                </li>
                                                                                <li>
                                                                                    Các công ty và/hoặc tổ chức đóng vai
                                                                                    trò là các bên cung cấp
                                                                                    dịch vụ, đối tác, hoặc/và các cố
                                                                                    vấn, tư vấn chuyên nghiệp của
                                                                                    Dai-ichi Life Việt Nam liên quan đến
                                                                                    việc thực hiện định danh,
                                                                                    xác minh, nhận biết Khách hàng;
                                                                                </li>
                                                                                <li>
                                                                                    Bộ Tài chính, Hiệp hội Bảo hiểm Việt
                                                                                    Nam, Ngân hàng Nhà nước
                                                                                    Việt Nam, Tòa án, Cơ quan Công an và
                                                                                    Cơ quan nhà nước khác có
                                                                                    thẩm quyền tại Việt Nam hoặc bất kỳ
                                                                                    cá nhân, cơ quan có thẩm
                                                                                    quyền hoặc cơ quan quản lý hoặc bên
                                                                                    thứ ba mà Dai-ichi Life
                                                                                    Việt Nam được phép hoặc bắt buộc
                                                                                    phải tiết lộ theo quy định
                                                                                    pháp luật, kể cả quy định pháp luật
                                                                                    của bất kỳ quốc gia nào.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>2</td>
                                                                        <td>
                                                                            <span>Giao kết Hợp đồng bảo hiểm (“HĐBH”), </span>bao
                                                                            gồm:
                                                                            <ul>
                                                                                <li className="no-bullet">
                                                                                    1. Giới thiệu, chào bán sản phẩm
                                                                                    bảo hiểm trước khi Khách hàng mua
                                                                                    sản phẩm hoặc dịch vụ của Dai-ichi
                                                                                    Life Việt Nam;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    2. Thiết kế và cung cấp cho Khách
                                                                                    hàng các sản phẩm và dịch vụ bảo
                                                                                    hiểm;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    3. Thẩm định Hồ sơ yêu cầu tham
                                                                                    gia bảo hiểm của HĐBH, nghiên cứu
                                                                                    đánh giá tình hình tài chính, khả
                                                                                    năng thanh toán, điều kiện tham gia
                                                                                    bảo hiểm;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    4. Tư vấn, giải thích về quy tắc
                                                                                    và điều khoản sản phẩm bảo hiểm, bàn
                                                                                    giao HĐBH;
                                                                                </li>
                                                                                <li className="no-bullet">5. Tái bảo
                                                                                    hiểm;
                                                                                </li>
                                                                                <li className="no-bullet">6. Tính toán
                                                                                    phí bảo hiểm, thiết
                                                                                    lập bảng minh họa bảo hiểm;
                                                                                </li>
                                                                                <li className="no-bullet">7. Phát hành
                                                                                    HĐBH;
                                                                                </li>
                                                                                <li className="no-bullet">8. Bàn giao bộ
                                                                                    HĐBH cho Khách
                                                                                    hàng.
                                                                                </li>
                                                                                <li className="no-bullet">(Gọi chung
                                                                                    là <span>“Mục đích 2”</span>)
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            <ul>
                                                                                <li>Các dữ liệu cá nhân cơ bản được
                                                                                    Dai-ichi Life Việt Nam xử lý tương
                                                                                    ứng với <span>Mục đích 1</span> nêu
                                                                                    trên.
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin lịch sử tham gia bảo hiểm,
                                                                                    yêu cầu bồi thường/chi trả quyền lợi
                                                                                    bảo hiểm (bao gồm tại Dai-ichi Life
                                                                                    Việt Nam và tại các doanh nghiệp/tổ
                                                                                    chức khác).
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các dữ liệu cá nhân nhạy cảm được xử
                                                                            lý tương ứng với <span>Mục đích 1</span> nêu
                                                                            trên, Dai-ichi Life Việt Nam thực hiện Xử lý
                                                                            thêm các dữ liệu cá nhân nhạy cảm sau:
                                                                            <ul>
                                                                                <li>Tình trạng sức khỏe và đời tư được
                                                                                    ghi trong hồ sơ bệnh án (không bao
                                                                                    gồm nhóm máu);
                                                                                </li>
                                                                                <li>Thông tin về bệnh sử, thương tật
                                                                                    (nằm ngoài hồ sơ bệnh án);
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin về đặc điểm di truyền được
                                                                                    thừa hưởng hoặc có được của cá nhân;
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin về thẻ/tài khoản, thông
                                                                                    tin về tiền gửi, thông tin về tài
                                                                                    sản gửi, thông tin về giao dịch liên
                                                                                    quan đến HĐBH (bao gồm cả thông
                                                                                    tin/hình ảnh giao dịch đóng phí),
                                                                                    thông tin về tổ chức, cá nhân là bên
                                                                                    bảo đảm tại tổ chức tín dụng, chi
                                                                                    nhánh ngân hàng, tổ chức cung ứng
                                                                                    dịch vụ trung gian thanh toán;
                                                                                </li>
                                                                                <li>
                                                                                    Thông tin chi tiết về tất cả các
                                                                                    khoản vay, khoản nợ và khoản vay
                                                                                    hiện tại của Khách hàng;
                                                                                </li>
                                                                                <li>
                                                                                    Dữ liệu về vị trí của cá nhân được
                                                                                    xác định qua dịch vụ định vị.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các Bên có liên quan đến Xử lý dữ liệu
                                                                            cá nhân tương ứng
                                                                            với <span>Mục đích 1</span> nêu trên,
                                                                            Dai-ichi Life Việt Nam có thể sẽ tiết lộ,
                                                                            chia sẻ những dữ liệu cá nhân tại mục 2 này
                                                                            của Khách hàng hoặc dữ liệu cá nhân của các
                                                                            bên thứ ba có liên quan đến Khách hàng, cho
                                                                            một hoặc các bên dưới đây:
                                                                            <ul>
                                                                                <li>
                                                                                    Cơ sở y tế, Tổ chức tái bảo hiểm, Tổ
                                                                                    chức cung ứng dịch vụ công nghệ sức
                                                                                    khỏe, công nghệ tài chính
                                                                                    (Healthtech, Fintech);
                                                                                </li>
                                                                                <li>
                                                                                    Tổ chức tín dụng, Tổ chức trung gian
                                                                                    thanh toán; Tổ chức thẻ quốc tế; Tổ
                                                                                    chức chuyển mạch thẻ; Tổ chức bù trừ
                                                                                    điện tử giao dịch thẻ;
                                                                                </li>
                                                                                <li>
                                                                                    Các công ty và/hoặc tổ chức
                                                                                    và/hoặc cá nhân đóng vai trò là các
                                                                                    bên cung cấp, đối tác, hoặc/và các
                                                                                    cố vấn, tư vấn chuyên nghiệp của
                                                                                    Dai-ichi Life Việt Nam, bao gồm các
                                                                                    công ty/tổ chức/cá nhân cung cấp
                                                                                    dịch vụ kiểm toán; hành chính; bưu
                                                                                    chính; bưu điện; tiếp thị; viễn
                                                                                    thông; kết nối mạng; điện thoại, in
                                                                                    ấn; phương tiện truyền thông xã hội;
                                                                                    dịch vụ xúc tiến thương mại, quản
                                                                                    trị nhân sự; hội thảo; xử lý dữ
                                                                                    liệu; công nghệ thông tin; máy tính;
                                                                                    thanh toán/thu hộ; nghiên cứu thị
                                                                                    trường; mô hình hóa dữ liệu; lưu trữ
                                                                                    và quản lý hồ sơ; pháp lý; các đối
                                                                                    tác kinh doanh, nhà cung cấp phần
                                                                                    thưởng; nhà cung cấp quà tặng;
                                                                                    chương trình khách hàng thân thiết;
                                                                                    các nhà quảng cáo; tổ chức từ thiện
                                                                                    hoặc tổ chức phi lợi nhuận;
                                                                                </li>
                                                                                <li>
                                                                                    Cơ quan truyền thông, báo chí;
                                                                                </li>
                                                                                <li>
                                                                                    Bất kỳ cá nhân có liên quan đến việc
                                                                                    thực thi hoặc duy trì bất kỳ quyền
                                                                                    hoặc nghĩa vụ nào theo (các) thỏa
                                                                                    thuận giữa Khách hàng với Dai-ichi
                                                                                    Life Việt Nam;
                                                                                </li>
                                                                                <li>
                                                                                    Các bên thứ ba mà Khách hàng đồng ý
                                                                                    hoặc Dai-ichi Life Việt Nam có cơ sở
                                                                                    pháp lý để chia sẻ dữ liệu cá nhân
                                                                                    của Khách hàng.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>3</td>
                                                                        <td>
                                                                            <span>Thực hiện HĐBH, </span>bao
                                                                            gồm:
                                                                            <ul>
                                                                                <li className="no-bullet">
                                                                                    1. Thu phí bảo hiểm;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    2. In ấn, gửi các biểu mẫu/thông
                                                                                    báo/quyết định liên quan đến HĐBH;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    3. Thẩm định/tái thẩm định, thực
                                                                                    hiện thủ tục khôi phục hiệu lực
                                                                                    HĐBH, tham gia thêm quyền lợi bảo
                                                                                    hiểm, yêu cầu thay đổi thông tin chi
                                                                                    tiết HĐBH;
                                                                                </li>
                                                                                <li className="no-bullet">4. Thực hiện
                                                                                    chương trình khuyến
                                                                                    mại, chương trình chăm sóc, tri ân
                                                                                    Khách hàng, các hoạt động xúc tiến
                                                                                    thương mại khác;
                                                                                </li>
                                                                                <li className="no-bullet">5. Thực hiện
                                                                                    giải quyết quyền lợi
                                                                                    bảo hiểm, bao gồm:
                                                                                </li>
                                                                                <ul style={{marginLeft: '2rem'}}>
                                                                                    <li className="no-bullet">
                                                                                        - Xác minh lịch sử khám, chữa
                                                                                        bệnh, thông tin khác có liên
                                                                                        quan của Khách hàng;
                                                                                    </li>
                                                                                    <li className="no-bullet">
                                                                                        - Xác minh nguyên nhân tai nạn,
                                                                                        hồ sơ bệnh án, khám chữa bệnh,
                                                                                        nguyên nhân tử vong của Khách
                                                                                        hàng;
                                                                                    </li>
                                                                                    <li className="no-bullet">- Thu thập
                                                                                        hồ sơ phục vụ cho
                                                                                        việc giải quyết quyền lợi bảo
                                                                                        hiểm;
                                                                                    </li>
                                                                                    <li className="no-bullet">- Thực
                                                                                        hiện chi trả quyền lợi
                                                                                        bảo hiểm theo quy định HĐBH;
                                                                                    </li>
                                                                                    <li className="no-bullet"> Phòng,
                                                                                        chống trục lợi bảo
                                                                                        hiểm;
                                                                                    </li>
                                                                                    <li className="no-bullet">- Công bố
                                                                                        danh sách HĐBH có quyền
                                                                                        lợi bảo hiểm đáo hạn nhưng chưa
                                                                                        nhận quyền lợi bảo hiểm trên báo
                                                                                        (giấy và điện tử), trang thông
                                                                                        tin điện tử, trụ sở văn phòng
                                                                                        của Dai-ichi Life Việt Nam.
                                                                                    </li>
                                                                                </ul>
                                                                                <li className="no-bullet">6. Thực hiện
                                                                                    đổi điểm thưởng mà
                                                                                    Khách hàng tích lũy được để nhận
                                                                                    quà, dịch vụ, đầu tư/tích lũy vào
                                                                                    giá trị tài khoản HĐBH trên Ứng dụng
                                                                                    Dai-chi Connect và/hoặc ứng dụng/nền
                                                                                    tảng bán hàng trực tuyến của
                                                                                    Dai-ichi Life Việt Nam và/hoặc đầu
                                                                                    tư vào quỹ mở do Công ty TNHH MTV
                                                                                    Quản lý quỹ Dai-ichi Life Việt Nam
                                                                                    đang quản lý;
                                                                                </li>
                                                                                <li className="no-bullet">7. Giải đáp
                                                                                    thắc mắc, khiếu nại,
                                                                                    tố cáo, khiếu kiện, tranh chấp
                                                                                    trước, trong và sau quá trình thực
                                                                                    hiện HĐBH;
                                                                                </li>
                                                                                <li className="no-bullet">8. Thực hiện
                                                                                    các chương trình,
                                                                                    dịch vụ chăm sóc khách hàng;
                                                                                </li>
                                                                                <li className="no-bullet">(ix) Lập các
                                                                                    báo cáo tài chính, thực
                                                                                    hiện tổng hợp, báo cáo hoạt động
                                                                                    kinh doanh hoặc các loại báo cáo
                                                                                    liên quan khác liên quan đến việc
                                                                                    thực hiện HĐBH và quy định pháp
                                                                                    luật.
                                                                                </li>
                                                                                <li className="no-bullet">10. Trích lập
                                                                                    dự phòng nghiệp vụ
                                                                                    theo quy định của pháp luật.
                                                                                </li>
                                                                                <li className="no-bullet">11. Nghiên
                                                                                    cứu, đánh giá tình hình
                                                                                    tài chính, khả năng thanh toán, mức
                                                                                    độ đầy đủ vốn, yêu cầu vốn của
                                                                                    Dai-ichi Life Việt Nam.
                                                                                </li>
                                                                                <li className="no-bullet">(Gọi chung
                                                                                    là <span>“Mục đích 3”</span>)
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Các dữ liệu cá nhân cơ bản được được
                                                                            Dai-ichi Life Việt Nam xử lý tương ứng
                                                                            với <span>Mục đích 2</span> nêu trên.
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các dữ liệu cá nhân nhạy cảm được xử
                                                                            lý tương ứng với <span>Mục đích 2</span> nêu
                                                                            trên, Dai-ichi Life Việt Nam thực hiện Xử lý
                                                                            thêm các dữ liệu cá nhân nhạy cảm sau:
                                                                            <ul>
                                                                                <li>Thông tin hồ sơ phục vụ cho việc
                                                                                    giải quyết bồi thường bảo hiểm: các
                                                                                    tài liệu xác minh nguyên nhân/quá
                                                                                    trình/diễn biến xảy ra tai nạn, tử
                                                                                    vong, thương tật (nằm ngoài hồ sơ
                                                                                    bệnh án).
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các Bên có liên quan đến Xử lý dữ liệu
                                                                            cá nhân tương ứng
                                                                            với <span>Mục đích 2</span> nêu trên,
                                                                            Dai-ichi Life Việt Nam có thể sẽ tiết lộ,
                                                                            chia sẻ những dữ liệu cá nhân tại mục 4 này
                                                                            của Khách hàng hoặc dữ liệu cá nhân của các
                                                                            bên thứ ba có liên quan đến Khách hàng, cho
                                                                            một hoặc các bên dưới đây:
                                                                            <ul>
                                                                                <li>
                                                                                    Các cơ quan nhà nước, tổ chức, cá
                                                                                    nhân liên quan đến việc thu thập hồ
                                                                                    sơ giải quyết quyền lợi bảo hiểm.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>4</td>
                                                                        <td>
                                                                            <span>Quản lý, đánh giá hoạt động kinh doanh và tuân thủ của Dai-ichi Life Việt Nam, </span>bao
                                                                            gồm:
                                                                            <ul>
                                                                                <li className="no-bullet">
                                                                                    1. Quản lý và đánh giá các hoạt
                                                                                    động kinh doanh bao gồm thiết kế,
                                                                                    cải tiến và nâng cao chất lượng các
                                                                                    sản phẩm, dịch vụ của Dai-ichi Life
                                                                                    Việt Nam hoặc thực hiện các hoạt
                                                                                    động truyền thông tiếp thị;
                                                                                </li>
                                                                                <li className="no-bullet">
                                                                                    2. Thực hiện nghiên cứu thị
                                                                                    trường, khảo sát và phân tích dữ
                                                                                    liệu liên quan đến bất kỳ các sản
                                                                                    phẩm, dịch vụ nào do Dai-ichi Life
                                                                                    Việt Nam cung cấp (dù được thực hiện
                                                                                    bởi Dai-ichi Life Việt Nam hay một
                                                                                    bên thứ ba khác mà Dai-ichi Life
                                                                                    Việt Nam hợp tác) mà có thể liên
                                                                                    quan đến Khách hàng.
                                                                                </li>
                                                                                <li className="no-bullet">(Gọi chung
                                                                                    là <span>“Mục đích 4”</span>)
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Các dữ liệu cá nhân cơ bản được được
                                                                            Dai-ichi Life Việt Nam xử lý tương ứng
                                                                            với <span>Mục đích 2</span> nêu trên.
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các dữ liệu cá nhân nhạy cảm được xử
                                                                            lý tương ứng với <span>Mục đích 1</span> nêu
                                                                            trên, Dai-ichi Life Việt Nam thực hiện Xử lý
                                                                            thêm các dữ liệu cá nhân nhạy cảm sau:
                                                                            <ul>
                                                                                <li>Tình trạng sức khỏe và đời tư được
                                                                                    ghi trong hồ sơ bệnh án (không bao
                                                                                    gồm nhóm máu);
                                                                                </li>
                                                                                <li>Thông tin về đặc điểm di truyền được
                                                                                    thừa hưởng hoặc có được của cá nhân.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                        <td>
                                                                            Ngoài các Bên có liên quan đến Xử lý dữ liệu
                                                                            cá nhân tương ứng
                                                                            với <span>Mục đích 1</span> nêu trên,
                                                                            Dai-ichi Life Việt Nam có thể sẽ tiết lộ,
                                                                            chia sẻ những dữ liệu cá nhân tại mục 4 này
                                                                            của Khách hàng hoặc dữ liệu cá nhân của các
                                                                            bên thứ ba có liên quan đến Khách hàng, cho
                                                                            một hoặc các bên dưới đây:
                                                                            <ul>
                                                                                <li>
                                                                                    Các công ty và/hoặc tổ chức
                                                                                    và/hoặc cá nhân đóng vai trò là các
                                                                                    bên cung cấp, đối tác, hoặc/và các
                                                                                    cố vấn, tư vấn chuyên nghiệp của
                                                                                    Dai-ichi Life Việt Nam, bao gồm các
                                                                                    công ty/tổ chức/cá nhân cung cấp
                                                                                    dịch vụ kiểm toán; nâng cao/cải tiến
                                                                                    sản phẩm/dịch vụ; nghiên cứu thị
                                                                                    trường; nghiên cứu/ đánh giá tình
                                                                                    hình tài chính, khả năng thanh toán,
                                                                                    mức độ đầy đủ vốn, yêu cầu vốn của
                                                                                    Dai-ichi Life Việt Nam; hành chính;
                                                                                    bưu chính; bưu điện; tiếp thị; viễn
                                                                                    thông; kết nối mạng; điện thoại; in
                                                                                    ấn; phương tiện truyền thông xã hội;
                                                                                    xử lý dữ liệu; công nghệ thông tin;
                                                                                    máy tính; thanh toán; mô hình hóa dữ
                                                                                    liệu; lưu trữ và quản lý hồ sơ; pháp
                                                                                    lý; các đối tác kinh doanh; nhà cung
                                                                                    cấp phần thưởng; nhà cung cấp quà
                                                                                    tặng; chương trình khách hàng thân
                                                                                    thiết; các nhà quảng cáo; tổ chức từ
                                                                                    thiện hoặc tổ chức phi lợi nhuận.
                                                                                </li>
                                                                            </ul>
                                                                        </td>
                                                                    </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Quyền và nghĩa vụ của khách hàng liên quan đến hoạt động xử lý dữ liệu cá nhân</span>
                                                        </p>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>1. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Các quyền của khách hàng: (i) Quyền được biết; (ii)
                                                                Quyền
                                                                đồng ý; (ii) Quyền truy cập; (iv) Quyền rút lại sự đồng
                                                                ý;
                                                                (v) Quyền xóa dữ liệu; (vi) Quyền hạn chế xử lý dữ liệu;
                                                                (vii) Quyền cung cấp dữ liệu; (viii) Quyền phản đối xử
                                                                lý dữ
                                                                liệu; (ix) Quyền khiếu nại, tố cáo, khởi kiện; (x) Quyền
                                                                yêu
                                                                cầu bồi thường thiệt hại; (xi) Quyền tự bảo vệ và các
                                                                quyền
                                                                có liên quan khác theo quy định của pháp luật.
                                                            </p>
                                                        </div>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>2. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Trong phạm vi pháp luật cho phép, khách hàng có thể thực
                                                                hiện các quyền của mình bằng cách liên hệ với Dai-ichi
                                                                Life
                                                                Việt Nam. Dai-ichi Life Việt Nam, bằng sự nỗ lực hợp lý,
                                                                sẽ
                                                                thực hiện yêu cầu hợp pháp và hợp lệ từ khách hàng trong
                                                                khoảng thời gian luật định kể từ khi nhận được yêu cầu
                                                                hoàn
                                                                chỉnh và hợp lệ và phí xử lý liên quan (nếu có) từ khách
                                                                hàng, tùy thuộc vào quyền của Dai-ichi Life Việt Nam
                                                                được
                                                                viện dẫn đến bất kỳ sự miễn trừ và/hoặc ngoại lệ nào
                                                                theo
                                                                quy định pháp luật.
                                                            </p>
                                                        </div>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>3. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Khi khách hàng thực hiện các quyền nêu trên của mình
                                                                đối
                                                                với việc Dai-ichi Life Việt Nam tiến hành xử lý dữ liệu
                                                                cá
                                                                nhân của khách hàng cho Mục đích 4 (được đề cập tại phần
                                                                “Dai-ichi Life Việt Nam thu thập và xử lý thông tin của
                                                                Bạn
                                                                như thế nào?"), trừ các trường hợp xử lý dữ liệu cá nhân
                                                                không cần sự đồng ý của khách hàng phù hợp với quy định
                                                                tại
                                                                Điều 5 của <a style={{
                                                                display: 'inline',
                                                                textDecoration: 'underline',
                                                                fontSize: '14px',
                                                            }}
                                                                              href={`${FE_BASE_URL + '/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf'}`}
                                                                              className="simple-brown2" target='_blank'>Quy
                                                                định bảo vệ và xử lý dữ liệu cá nhân đối với
                                                                khách hàng</a> và quy định pháp luật hiện hành, Daiichi
                                                                Life
                                                                Việt Nam sẽ thực hiện theo (các) yêu cầu hợp pháp đó của
                                                                khách hàng.
                                                            </p>
                                                        </div>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>4. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Nếu Khách hàng rút lại sự đồng ý của mình, yêu cầu xóa
                                                                dữ
                                                                liệu cá nhân, hạn chế xử lý dữ liệu cá nhân, phản đối xử
                                                                lý
                                                                dữ liệu cá nhân và/hoặc thực hiện các quyền khác đối với
                                                                bất
                                                                kỳ hoặc tất cả dữ liệu cá nhân mà:
                                                            </p>
                                                        </div>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: 20}}>
                                                            a) dẫn đến việc hạn chế hoặc không thể xử lý dữ liệu cá nhân
                                                            theo các Mục đích 1, 2 và 3 (được đề cập tại phần “Dai-ichi
                                                            Life Việt Nam thu thập và xử lý thông tin của Bạn như thế
                                                            nào?") và/ hoặc dẫn đến việc hạn chế hoặc không thể xử lý dữ
                                                            liệu cá nhân trong các trường hợp tại Điều 5 của <a style={{
                                                            display: 'inline',
                                                            textDecoration: 'underline',
                                                            fontSize: '14px',
                                                        }}
                                                                                                                href={`${FE_BASE_URL + '/documents/10156/ab395221-e32a-4cbb-9104-10c64b61685e/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf'}`}
                                                                                                                className="simple-brown2"
                                                                                                                target='_blank'>Quy
                                                            định bảo vệ và xử lý dữ liệu cá nhân đối với
                                                            khách hàng</a> ,
                                                            Dai-ichi Life Việt Nam sẽ thông báo cho khách hàng các hậu
                                                            quả và thiệt hại có thể phát sinh đối với (các) yêu cầu nêu
                                                            trên của khách hàng. Theo đó, Dai-ichi Life Việt Nam có thể
                                                            sẽ xem xét và quyết định về việc từ chối cung cấp hoặc không
                                                            tiếp tục cung cấp dịch vụ cho khách hàng. Việc khách hàng
                                                            thực hiện các quyền đã được liệt kê này có thể sẽ được xem
                                                            là sự đơn phương chấm dứt từ phía khách hàng cho bất kỳ mối
                                                            quan hệ nào giữa khách hàng với Dai-ichi Life Việt Nam (kể
                                                            cả HĐBH), và hoàn toàn có thể dẫn đến sự vi phạm nghĩa vụ
                                                            hoặc các cam kết theo HĐBH giữa khách hàng với Dai-ichi Life
                                                            Việt Nam. Đồng thời, Dai-ichi Life Việt Nam bảo lưu các
                                                            quyền và biện pháp khắc phục hợp pháp của Dai-ichi Life Việt
                                                            Nam trong những trường hợp đó. Theo đó, Dai-ichi Life Việt
                                                            Nam sẽ không chịu trách nhiệm đối với khách hàng cho bất kỳ
                                                            tổn thất nào phát sinh, và các quyền hợp pháp của Dai-ichi
                                                            Life Việt Nam sẽ được bảo lưu một cách rõ ràng đối với việc
                                                            giới hạn, hạn chế, tạm ngừng, hủy bỏ, ngăn cản, hoặc cấm
                                                            đoán đó.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: 20}}>
                                                            b) không thuộc các trường hợp nêu tại mục a nêu trên thì
                                                            Dai-ichi Life Việt Nam sẽ thực hiện theo (các) yêu cầu hợp
                                                            pháp đó của khách hàng.
                                                        </p>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>5. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Vì mục đích bảo mật, khách hàng có thể cần phải đưa ra
                                                                yêu cầu của mình bằng văn bản hoặc sử dụng phương pháp
                                                                khác
                                                                để chứng minh và xác thực danh tính của khách hàng.
                                                                Dai-ichi
                                                                Life Việt Nam có thể yêu cầu khách hàng xác minh danh
                                                                tính
                                                                trước khi xử lý yêu cầu của khách hàng.
                                                            </p>
                                                        </div>
                                                        <div style={{
                                                            display: 'flex',
                                                            alignItems: 'flex-start',
                                                            flexDirection: 'row'
                                                        }}>
                                                            <p style={{marginRight: 12}}>6. </p>
                                                            <p className="dieukhoanform__subContent no-dot">
                                                                Khách hàng có nghĩa vụ: 1. Tự bảo vệ dữ liệu cá nhân của
                                                                mình; 2. Cung cấp đầy đủ, chính xác dữ liệu cá nhân cho
                                                                Dai-ichi Life Việt Nam; 3. Thực hiện quy định của pháp
                                                                luật về bảo vệ dữ liệu cá nhân và tham gia phòng, chống
                                                                các
                                                                hành vi vi phạm quy định về bảo vệ dữ liệu cá nhân.
                                                            </p>
                                                        </div>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Thời gian xử lý dữ liệu cá nhân của khách hàng</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span style={{
                                                                textDecoration: 'underline', fontSize: '14px', fontWeight: 500
                                                            }}>Thời điểm bắt đầu:</span> Dai-ichi Life Việt Nam sẽ bắt
                                                            đầu xử lý dữ liệu cá nhân của khách hàng khi nhận được dữ
                                                            liệu với sự đồng ý của khách hàng.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span style={{
                                                                textDecoration: 'underline', fontSize: '14px', fontWeight: 500
                                                            }}>Thời điểm kết thúc:</span> Dai-ichi Life Việt Nam sẽ dừng
                                                            xử lý dữ liệu của khách hàng khi (tùy theo thời điểm nào đến
                                                            sau cùng):
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - Theo yêu cầu của khách hàng bằng văn bản;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - HĐBH chấm dứt hiệu lực/giải quyết xong quyền lợi bảo
                                                            hiểm/đáo hạn;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - Kết thúc tranh chấp, khiếu nại bằng thỏa thuận/bản
                                                            án/quyết định có hiệu lực pháp luật;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - Thời điểm khác phù hợp với Mục đích đã được khách hàng
                                                            đồng ý;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}>
                                                            - Theo quy định pháp luật.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Chính sách Cookie</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Trang thông tin điện tử hoặc ứng dụng hoặc nền tảng bán hàng
                                                            trực tuyến này sử dụng cookie để phân biệt Bạn với những
                                                            người dùng khác, đồng thời giúp Dai-ichi Life Việt Nam cung
                                                            cấp cho Bạn trải nghiệm trực tuyến dễ dàng hơn và cũng cho
                                                            phép Dai-ichi Life Việt Nam cải thiện trang thông tin điện
                                                            tử hoặc ứng dụng hoặc nền tảng bán hàng trực tuyến của
                                                            Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Cookie là một tệp nhỏ gồm các chữ cái và số mà Dai-ichi Việt
                                                            Nam lưu trữ trên trình duyệt hoặc ổ cứng máy tính của Bạn.
                                                            Bạn có khả năng chấp nhận hoặc từ chối cookie bằng cách sửa
                                                            đổi cài đặt trong trình duyệt của Bạn. Nếu Bạn muốn làm điều
                                                            này, xin vui lòng xem mục trợ giúp trong trình duyệt của
                                                            Bạn.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Những công nghệ khác, bao gồm dữ liệu lưu trữ trên trình
                                                            duyệt hoặc thiết bị, cũng có thể được sử dụng cho mục đích
                                                            tương tự. Dai-ichi Life Việt Nam gọi tất cả các công nghệ
                                                            này là cookie.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span style={{ fontWeight: 500 }}>Các loại cookie được sử dụng bao gồm:</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}
                                                        >
                                                            - Cookie cần thiết cho trang thông tin điện tử, ứng
                                                            dụng, nền tảng bán hàng trực tuyến: là các cookie
                                                            cần
                                                            phải có để phục vụ hoạt động của trang thông tin
                                                            điện
                                                            tử, ứng dụng, nền tảng bán hàng trực tuyến. Ví dụ,
                                                            các
                                                            cookie cho phép Bạn đăng nhập vào trang thông tin
                                                            điện
                                                            tử, ứng dụng, nền tảng bán hàng trực tuyến của
                                                            Dai-ichi
                                                            Life Việt Nam một cách an toàn;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}
                                                        >
                                                            - Cookie phân tích/quản lý hoạt động: là các cookie
                                                            cho
                                                            phép Dai-ichi Life Việt Nam nhận ra và đếm số lượng
                                                            người dùng truy cập trang thông tin điện tử, ứng
                                                            dụng,
                                                            nền tảng bán hàng trực tuyến và theo dõi việc truy
                                                            cập
                                                            xung quanh trang thông tin điện tử, ứng dụng, nền
                                                            tảng
                                                            bán hàng trực tuyến của Dai-ichi Life Việt Nam. Điều
                                                            này
                                                            giúp Dai-ichi Life Việt Nam cải thiện cách thức hoạt
                                                            động của trang thông tin điện tử, ứng dụng, nền tảng
                                                            bán
                                                            hàng trực tuyến;
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot"
                                                           style={{marginLeft: '2rem'}}
                                                        >
                                                            - Cookie chức năng: được sử dụng để nhận ra Bạn khi
                                                            Bạn
                                                            quay lại trang thông tin điện tử, ứng dụng, nền tảng
                                                            bán
                                                            hàng trực tuyến của Dai-ichi Life Việt Nam và cho
                                                            phép
                                                            Dai-ichi Life Việt Nam cá nhân hóa nội dung hiển thị
                                                            cho
                                                            Bạn, chào Bạn bằng tên và ghi nhớ sở thích của Bạn.
                                                            Ví
                                                            dụ: lựa chọn ngôn ngữ hoặc khu vực của Bạn.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Bằng cách tiếp tục sử dụng trang thông tin điện tử,
                                                            ứng
                                                            dụng, nền tảng bán hàng trực tuyến của Dai-ichi Life
                                                            Việt Nam, Bạn chấp nhận việc sử dụng các loại cookie
                                                            như
                                                            đã nêu ở trên.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>An ninh thông tin</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Thông tin cá nhân của Bạn được truy cập một cách giới hạn
                                                            bởi nhân viên của Dai-ichi Life Việt Nam, đại lý và nhà cung
                                                            cấp dịch vụ có thẩm quyền, những người cần thông tin của Bạn
                                                            để thực hiện công việc của họ.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam đã phát triển và tiếp tục nâng cấp hệ
                                                            thống bảo mật để bảo vệ thông tin cá nhân của Bạn, sử dụng
                                                            những phương thức bảo vệ an ninh phù hợp với mức độ quan
                                                            trọng của thông tin, ngăn chặn việc mất cắp hoặc ngăn chặn
                                                            hành vi truy cập, công bố, sửa đổi, tiết lộ, hoặc sử dụng
                                                            thông tin trái phép.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam duy trì các biện pháp bảo vệ và các
                                                            quy trình an ninh thích hợp với từng loại tài liệu thông tin
                                                            khác nhau, bao gồm cả hồ sơ điện tử hoặc hồ sơ bằng giấy,
                                                            biện pháp bảo vệ thông tin trong nội bộ Dai-ichi Life Việt
                                                            Nam, và các biện pháp bảo vệ thông tin qua công nghệ chẳng
                                                            hạn như việc sử dụng các mật mã và mã hóa.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam chịu trách nhiệm đối với thông tin cá
                                                            nhân thuộc quyền sở hữu của Dai-ichi Life Việt Nam, bao gồm
                                                            các thông tin được chuyển đến các nhà cung cấp dịch vụ hiện
                                                            đang thực hiện nhiệm vụ thay mặt Dai-ichi Life Việt Nam. Khi
                                                            chia sẻ thông tin cá nhân với các nhà cung cấp dịch vụ, họ
                                                            có trách nhiệm bảo vệ thông tin bằng những biện pháp phù hợp
                                                            với thực tiễn
                                                            và <span>Chính Sách Bảo Mật Thông Tin</span> của Dai-ichi
                                                            Life Việt Nam.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Tuyên bố loại trừ </span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Mặc dù Dai-ichi Life Việt Nam có những biện pháp bảo mật
                                                            thích hợp để bảo vệ thông tin cá nhân và nỗ lực tiến hành
                                                            những biện pháp đề phòng để ngăn chặn và giảm thiểu tối đa
                                                            các rủi ro có thể khi Bạn sử dụng trang thông tin điện tử,
                                                            ứng dụng, nền tảng bán hàng trực tuyến, tuy nhiên Internet
                                                            là một hệ thống mở và không hệ thống trực tuyến nào là an
                                                            toàn tuyệt đối, bất kỳ hệ thống nào cũng có thể bị lỗi bởi
                                                            con người hoặc hoạt động.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Khi Bạn nhận thấy, nghi ngờ hoặc biết được thông tin cá nhân
                                                            của Bạn bị sửa đổi, sử dụng hay chiếm đoạt trái phép bởi một
                                                            bên thứ ba để đăng nhập vào trang thông tin điện tử, ứng
                                                            dụng, nền tảng bán hàng trực tuyến hoặc cho bất kỳ mục đích
                                                            nào khác, xin vui lòng liên hệ ngay lập tức với Dai-ichi
                                                            Life Việt Nam để được hỗ trợ xử lý.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Công cụ xử lý dữ liệu </span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Công cụ xử lý dữ liệu là công cụ dò tìm duy nhất trên máy
                                                            tính của Bạn hoặc trên các thiết bị khác thông qua một máy
                                                            chủ của trang web, có chứa những thông tin mà về sau có thể
                                                            đọc được từ máy chủ đã cung cấp cho Bạn công cụ này.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam có thể sử dụng nhiều công cụ xử lý
                                                            trên các trang web, ứng dụng, công cụ khác nhau do Dai-ichi
                                                            Life Việt Nam quản lý. Thông tin được thu thập bao gồm nhưng
                                                            không giới hạn địa chỉ IP của Bạn, tên domain, phần mềm lướt
                                                            web, các loại và cấu hình của công cụ lướt web của Bạn, các
                                                            thiết lập về ngôn ngữ, vị trí địa lý, hệ điều hành, trang
                                                            web tham khảo, các trang web và nội dung đã xem, cùng với
                                                            thời gian truy cập,… nhằm thống kê, phân tích, tổng hợp, cải
                                                            thiện chất lượng trang web, ứng dụng và phục vụ cho các hoạt
                                                            động nâng cao chất lượng dịch vụ cung cấp cho Bạn.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Các công cụ xử lý này cũng có thể làm cho trang web, ứng
                                                            dụng, nền tảng bán hàng trực tuyến của Dai-ichi Life Việt
                                                            Nam ghi nhận lại phần truy cập của Bạn cũng như các thứ tự
                                                            ưu tiên, đồng thời điều chỉnh trang web, ứng dụng, nền tảng
                                                            bán hàng trực tuyến cho phù hợp với nhu cầu của Bạn. Các
                                                            công cụ xử lý dữ liệu cho mục đích quảng cáo cho phép
                                                            Dai-ichi Life Việt Nam cung cấp những mẫu quảng cáo trên các
                                                            trang web, ứng dụng, nền tảng bán hàng trực tuyến của mình
                                                            phù hợp hơn, ví dụ như bằng việc chọn mẫu quảng cáo dựa vào
                                                            sự quan tâm dành cho Bạn, hoặc chặn lại các mẫu quảng cáo có
                                                            cùng nội dung liên tục gửi đến Bạn.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Hầu hết những công cụ lướt web được thiết kế trước tiên là
                                                            nhận được sự chấp thuận của Bạn cho phép xử lý dữ liệu và
                                                            Bạn có thể tạm khóa hoặc từ chối hoặc thiết lập công cụ lướt
                                                            web của mình. Tuy nhiên, Bạn sẽ không được hưởng đầy đủ mọi
                                                            quyền lợi từ các trang web, ứng dụng hoặc nền tảng bán hàng
                                                            trực tuyến của Dai-ichi Life Việt Nam cũng như một số chức
                                                            năng có thể hoạt động không chính xác.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Ngoài ra, Dai-ichi Life Việt Nam có thể áp dụng phương thức
                                                            xử lý dữ liệu cá nhân tự động thông qua các thuật toán để xử
                                                            lý dữ liệu của Bạn.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Kết nối bên ngoài</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Nếu bất kỳ phần nào của trang thông tin điện tử, ứng dụng,
                                                            nền tảng bán hàng trực tuyến này có chứa các đường dẫn kết
                                                            nối với trang thông tin điện tử và/hoặc ứng dụng khác, những
                                                            trang thông tin điện tử và/hoặc ứng dụng đó có thể không
                                                            chịu sự quản lý
                                                            của <span>Chính Sách Bảo Mật Thông Tin</span> này.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Bạn nên kiểm tra phần quy định về bảo mật trên các trang
                                                            thông tin điện tử và/hoặc, ứng dụng đó để hiểu rõ về chính
                                                            sách của họ khi thu thập, sử dụng, chuyển giao và tiết lộ
                                                            thông tin cá nhân.
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Biện pháp bảo vệ dữ liệu cá nhân</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam sẽ bảo vệ dữ liệu cá nhân của
                                                            Bạn
                                                            ngay từ khi bắt đầu và trong suốt quá trình xử lý dữ
                                                            liệu cá nhân.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam sẽ bảo vệ thông tin cá nhân
                                                            của
                                                            Bạn bằng những phương thức bảo vệ an ninh phù hợp
                                                            với
                                                            mức độ quan trọng của thông tin, ngăn chặn hành vi
                                                            truy
                                                            cập, công bố, sửa đổi hoặc sử dụng thông tin trái
                                                            phép.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Khi chia sẻ thông tin cá nhân của Bạn với các nhà
                                                            cung
                                                            cấp dịch vụ, họ có trách nhiệm bảo vệ thông tin bằng
                                                            những biện pháp phù hợp với thực tiễn và chính sách
                                                            bảo
                                                            mật của Dai-ichi Life Việt Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam tuân thủ các chính sách sử
                                                            dụng dữ liệu của Google. Theo đó, mọi việc sử dụng
                                                            và chuyển giao thông tin nhận được từ các API của
                                                            Google đến bất kỳ ứng dụng nào khác sẽ tuân theo
                                                            Chính sách Dữ liệu Người dùng Dịch vụ API của Google (
                                                            <a style={{
                                                                display: 'inline', fontSize: '14px',
                                                            }}
                                                               href='https://developers.google.com/terms/api-services-user-data-policy'
                                                               className="simple-brown2" target='_blank'>Google
                                                                API Services User Data Policy</a>), bao gồm các
                                                            yêu cầu Vận dụng Hạn chế (<a style={{
                                                            display: 'inline', fontSize: '14px',
                                                        }}
                                                                                         href='https://developers.google.com/terms/api-services-user-data-policy#additional_requirements_for_specific_api_scopes'
                                                                                         className="simple-brown2"
                                                                                         target='_blank'>Limited
                                                            Use Policy</a>).
                                                        </p>
                                                        <div className="dash-border"></div>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            <span>Sửa đổi, bổ sung</span>
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Bất kỳ thời điểm nào và không cần thông báo,
                                                            Dai-ichi
                                                            Life Việt Nam có quyền sửa đổi, cập nhật hoặc điều
                                                            chỉnh
                                                            các nội dung
                                                            trong <span>Chính Sách Bảo Mật Thông Tin</span> này,
                                                            và
                                                            phiên bản mới nhất
                                                            của <span>Chính Sách Bảo Mật Thông Tin</span> sẽ
                                                            được đăng tải công khai trên trang thông tin điện
                                                            tử,
                                                            ứng dụng, nền tảng bán hàng trực tuyến của Dai-ichi
                                                            Life
                                                            Việt Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Dai-ichi Life Việt Nam khuyến nghị Bạn nên định kỳ
                                                            kiểm
                                                            tra thông điệp bảo mật trên trang thông tin điện tử,
                                                            ứng
                                                            dụng, nền tảng bán hàng trực tuyến của Dai-ichi Life
                                                            Việt Nam.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Những sửa đổi, cập nhật hoặc điều chỉnh đó sẽ có
                                                            hiệu
                                                            lực ngay khi được Dai-ichi Việt Nam cập nhật trên
                                                            trang
                                                            thông tin điện tử, ứng dụng hoặc nền tảng bán hàng
                                                            trực
                                                            tuyến.
                                                        </p>
                                                        <p className="dieukhoanform__subContent no-dot">
                                                            Bằng việc tiếp tục sử dụng trang thông tin điện tử,
                                                            ứng
                                                            dụng, nền tảng bán hàng trực tuyến này, Dai-ichi
                                                            Life
                                                            Việt Nam hiểu rằng Bạn đã đọc, hiểu và chấp
                                                            nhận <span>Chính
                                                                Sách Bảo Mật Thông Tin</span> đã được sửa đổi, cập nhật
                                                            hoặc
                                                            điều chỉnh đó.
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </main>);

}

export default Clauses;

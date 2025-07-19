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

const Terms = () => {
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
    let mainClass = "";
    if (isLoggedIn()) {
        mainClass = "logined";
    } 
    if (isMobile) {
        mainClass = mainClass + " no-padding-app";
    }
    return (<main className={mainClass}>
        {state.renderMeta && <Helmet>
            <title>Thỏa thuận sử dụng ứng dụng – Dai-ichi Life Việt Nam</title>
            <meta name="description"
                  content="Trang thông tin về thỏa thuận điều khoản sử dụng ứng dụng Dai-ichi Connect từ Dai-ichi Life Việt Nam."/>
            <meta name="keywords" content="thỏa thuận sử dụng, điều khoản, bảo hiểm, bảo hiểm nhân thọ, dai-ichi life, bí quyết sống vui khỏe, chạy bộ, tập luyện thể thao, hợp đồng bảo hiểm, yêu cầu hỗ trợ, giải quyết quyền lợi, thay đổi thông tin, người được bảo hiểm, chủ hợp đồng" />
            <meta name="robots" content="noindex, nofollow"/>
            <meta property="og:type" content="website"/>
            <meta property="og:url" content={FE_BASE_URL + "/terms-of-use"}/>
            <meta property="og:title" content="Thỏa thuận sử dụng ứng dụng – Dai-ichi Life Việt Nam"/>
            <meta property="og:description"
                  content="Trang thông tin về thỏa thuận điều khoản sử dụng ứng dụng Dai-ichi Connect từ Dai-ichi Life Việt Nam."/>
            <meta property="og:image"
                  content="https://api-healthcontent.dai-ichi-life.com.vn/api/api/v1/app/downloadFile?fileName=/data/editor/news%2F%40%40%40Daiichilifethumbnailog_1670556986788.jpg"/>
            <link rel="canonical" href={FE_BASE_URL + "/terms-of-use"}/>
        </Helmet>}
        <div className="main-warpper">
            {/* Route display */}
            {/* <Breadcrumb items={breadcrumbItems} isMobile={getUrlParameter(IS_MOBILE)}/> */}
            {!isMobile &&
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
            }
            {/* Banner */}
            <TermsOfUse isMobile={getUrlParameter(IS_MOBILE)}/>
            {/* Main page */}
            <section className="scterm-of-use custom-margin-top-app"style={{ marginTop: isMobile && '130px' }}>
                <div className="tab-pane-container">
                    {/* Contents */}
                    <div className="tab-pane-container-content" ref={tabPaneRef}>
                        <div id="thoathuan"
                             className="tabpane-tab active">
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
                    </div>
                </div>
            </section>
        </div>
    </main>);

}

export default Terms;

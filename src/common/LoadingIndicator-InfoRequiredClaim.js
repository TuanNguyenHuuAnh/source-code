import React from 'react';
import Loader from 'react-loader-spinner';
import { usePromiseTracker } from "react-promise-tracker";

export default function LoadingIndicator2(props) {
    const { promiseInProgress } = usePromiseTracker({ area: props.area });
    return (
        promiseInProgress &&
        <main className="logined additonal-information-nodata">
            <div className="main-warpper basic-mainflex">
                <section className="sccontract-warpper additional-information-sccontract-warpper">
                    <div className="breadcrums">
                        <div className="breadcrums__item">
                            <p>Yêu cầu quyền lợi</p>
                            <span>&gt;</span>
                        </div>
                        <div className="breadcrums__item">
                            <p>Bổ sung thông tin</p>
                            <span>&gt;</span>
                        </div>
                    </div>
                    <div className="insurance">
                        <div className="empty">
                            {/* <div className="icon">
                                <img src="img/2.2-nodata-image.png" alt="" />
                            </div>
                            <h4>Hiện tại không có yêu cầu nào đang xử lý</h4> */}
                            <div style={{ width: "100%", height: "100", display: "flex", justifyContent: "center", alignItems: "center" }}>
                                <Loader type="ThreeDots" color="#ea6544" secondaryColor="d12a1e" height="50" width="50" />
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    );
}
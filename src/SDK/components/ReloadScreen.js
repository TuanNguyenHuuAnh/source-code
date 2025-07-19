
import React from 'react';


function ReloadScreen ({getPolicyList, isNonePaymentList, isReloadPaymentList, isRetrying, msg}){

    const retry = () => {
        getPolicyList(true);
    }

    return (
        <div className="sccontract-container" style={{backgroundColor: '#ffffff'}}>
            <div className="insurance">
                <div className="empty">
                {isNonePaymentList ? (
                    <>
                        <div className="icon">
                            <img src="../img/icon/timeout.svg" alt=""/>
                        </div>
                        {isReloadPaymentList ? (
                            <>
                                <p style={{paddingTop: '20px'}}><b style={{fontWeight: 1000}}>Hệ thống đang bận​</b></p>
                                <p style={{paddingTop: '10px'}}>Vui lòng nhấn nút 'Thử lại' bên dưới để tải lại thông tin hợp đồng​</p>
                                <p> 
                                    <div className="btn-wrapper center-all">
                                        <button className="btn btn-primary center-all"  style={{justifyContent: 'center'}} 
                                            onClick={()=> retry()}>Thử lại</button>
                                    </div>
                                </p>
                                                 
                            </>
                            ) : (
                            <>
                                <p style={{paddingTop: '20px'}}><b style={{fontWeight: 1000}}>Hệ thống đang bị gián đoạn​</b></p>
                                <p style={{paddingTop: '10px'}}>Quý khách vui lòng thử lại sau​</p>
                            </>
                            )
                        }
                    </>
                    ) : (
                    <>
                        <div className="icon">
                            <img src="../img/icon/empty.svg" alt=""/>
                        </div>
                        {isRetrying ? (
                            <p style={{paddingTop: '20px'}}>
                                <>
                                    <p style={{paddingTop: '10px'}}>Đang cập nhật dữ liệu</p>
                                    <p style={{paddingTop: '10px'}}>Vui lòng đợi trong giây lát</p>
                                </>
                            </p> 
                            ) : (
                            <p style={{paddingTop: '20px'}}>{msg}</p>)
                        }
                    </>
                    )
                }
                </div>
            </div>
        </div>

    )
}

export default ReloadScreen;


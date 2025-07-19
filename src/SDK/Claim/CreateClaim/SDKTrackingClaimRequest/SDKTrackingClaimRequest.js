import { Component } from 'react';
import Pagination from '../../../Paging';
import {
    ACCESS_TOKEN, FE_BASE_URL, CLAIM_STATUS_TITLE_MAPPING, PAGE_SIZE_CARD,
    IS_MOBILE
} from '../../../sdkConstant';
import LoadingIndicator from '../../../LoadingIndicator2';
import { getSession, removeAccents, clearSession, convertDateTime, getUrlParameter } from '../../../sdkCommon';
import { isEmpty } from "lodash";
import AgreeCancelPopup from '../../../components/AgreeCancelPopup';
import AlertPopupHight from '../../../components/AlertPopupHight';
// import LazyLoad from 'react-lazyload';


class SDKTrackingClaimRequest extends Component {
    constructor(props) {
        super(props)
        this.state = {
            clientProfile: null,
            pageOfItems: [],
            searchInput: '',
            clientProfileSearch: [],
            loading: false,
            totalRecords: 0,
            offsetSize: 200,
            offset: 1,
            requestId: '',
            data: this.props.trackingClaimData,
            deleteRequestId: '',
            showConfirm: false,
            claimCofirmedMsg: '',
            customerId: '',
            searched: false
        };
        this.onChangePageCom = this.onChangePageCom.bind(this);
        this.handlerOnChangeSearchInput = this.onChangeSearchInput.bind(this);
        this.handleSearch = this.search.bind(this);
        // this.loadMoreCards = this.loadMoreCards.bind(this);
        // this.handleScroll = this.handleScroll.bind(this);
    }

    onChangePageCom(pageOfItems) {
        // update state with new page of items
        this.setState({ pageOfItems: pageOfItems });
    }

    onChangeSearchInput(event) {
        this.setState({ searchInput: event.target.value, enable: false });
    }

    componentDidMount() {
        console.log('this.props.trackingClaimData=', this.props.trackingClaimData);
        if (this.props.trackingClaimData) {
            // let profile = this.props.trackingClaimData;
            // if (profile.length > PAGE_SIZE_CARD) {
            //     profile = profile.slice(0, PAGE_SIZE_CARD);
            // }
            this.setState({ data: this.props.trackingClaimData, clientProfileSearch: this.props.trackingClaimData, pageOfItems: this.props.trackingClaimData });
        }
        
        // const scrollableDiv = document.getElementById('scrollableDiv');
        // scrollableDiv.addEventListener('scroll', this.handleScroll);

    }

    // loadMoreCards() {
    //     if (this.state.pageOfItems.length + PAGE_SIZE_CARD <= this.state.clientProfileSearch?.length) {
    //         this.setState({pageOfItems: this.state.clientProfileSearch?.slice(0, this.state.pageOfItems.length + PAGE_SIZE_CARD)});
    //     } else {
    //         this.setState({pageOfItems: this.state.clientProfileSearch});
    //     }
    // }

    // handleScroll() {
    //     if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
    //         this.loadMoreCards();
    //     }
    // }

    componentDidUpdate(prevProps) {
        // Typical usage (don't forget to compare props):
        if (this.props.trackingClaimData !== prevProps.trackingClaimData) {
            if (this.props.trackingClaimData) {
                let profile = this.props.trackingClaimData;
                if (profile.length > PAGE_SIZE_CARD) {
                    profile = profile.slice(0, PAGE_SIZE_CARD);
                }
                this.setState({ data: this.props.trackingClaimData, clientProfileSearch: this.props.trackingClaimData, pageOfItems: this.props.trackingClaimData });
            }
        }
    }

    // componentWillUnmount() {
    //     const scrollableDiv = document.getElementById('scrollableDiv');
    //     scrollableDiv.removeEventListener('scroll', this.handleScroll);
    // }

    search(event) {
        event.preventDefault();

        if (this.state.loading || isEmpty(this.state.data)) return;
        let clientProfile = [];
        if (!this.state.searchInput) {
            // let profile = this.state.data;
            // if (profile.length > PAGE_SIZE_CARD) {
            //     profile = profile.slice(0, PAGE_SIZE_CARD);
            // }
            this.setState({ clientProfileSearch: this.state.data, pageOfItems: this.state.data });
        } else {
            clientProfile = this.state.data.filter(item => {
                return (
                    (removeAccents(item?.requestId?.toLowerCase()?.replace(/  +/g, ' '))?.indexOf(removeAccents(this.state.searchInput?.toLowerCase()?.replace(/  +/g, ' '))) >= 0) 
                    || removeAccents(item?.customerName?.toLowerCase()?.replace(/  +/g, ' '))?.indexOf(removeAccents(this.state.searchInput?.toLowerCase()?.replace(/  +/g, ' '))) >= 0)
                    || (removeAccents(item?.lifeInsuredName?.toLowerCase()?.replace(/  +/g, ' '))?.indexOf(removeAccents(this.state.searchInput?.toLowerCase()?.replace(/  +/g, ' '))) >= 0)
                    || (removeAccents(item?.claimTypeVN?.toLowerCase()?.replace(/  +/g, ' '))?.indexOf(removeAccents(this.state.searchInput?.toLowerCase()?.replace(/  +/g, ' '))) >= 0)
                    || (removeAccents(item?.statusVN?.toLowerCase()?.replace(/  +/g, ' '))?.indexOf(removeAccents(this.state.searchInput?.toLowerCase()?.replace(/  +/g, ' '))) >= 0)
                    || (convertDateTime(item?.effectiveDate)?.indexOf(this.state.searchInput) >= 0)
                    || (convertDateTime(item?.updateDate)?.indexOf(this.state.searchInput) >= 0) 
            });
            // let profile = clientProfile;
            // if (profile.length > PAGE_SIZE_CARD) {
            //     profile = profile.slice(0, PAGE_SIZE_CARD);
            // }
            this.setState({ clientProfileSearch: clientProfile, pageOfItems: clientProfile, searched: true});
        }
    }

    
    searchValue(value) {
        const normalizedValue = removeAccents(value)?.toLowerCase()?.replace(/  +/g, ' ');
        for (const [key, val] of Object.entries(CLAIM_STATUS_TITLE_MAPPING)) {
            if (removeAccents(val)?.toLowerCase()?.replace(/  +/g, ' ') === normalizedValue) {
                return key;
            }
        }
        return '';
    }

    render() {
        console.log('this.state.noValidPolicy=', this.state.noValidPolicy);
        var logined = false;
        if (getSession(ACCESS_TOKEN)) {
            logined = true;
        }

        const loadClaimByRequestId = (requestId, status) => {
            this.props.requestClaimGetInforAgent(requestId, this.props.dataRequest, status);
            // this.props.getPermissionOnly(this.props.dataRequest);
        }

        const clearSearch = () => {
            this.setState({ clientProfileSearch: this.state.data, pageOfItems: this.state.data, searchInput: '', enable: false });
        }

        const setShowConfirm = (value) => {
            this.setState({ showConfirm: value });
        }

        const cancelConfirm = () => {
            this.setState({ showConfirm: false });
            let data = this.props.dataDescrypt;
            if (data && data?.platform === 'Web') {
                window.location.href = '/';
            } else if (data) {
                let obj = {
                    Action: "CANCEL_ND13_" + data?.proccessType,
                    ClientID: this.props.clientId
                    // PolicyNo: this.props.polID,
                    // TrackingID: this.state.trackingId
                };
                if (from && (from === "Android")) {//for Android
                    if (window.AndroidAppCallback) {
                        window.AndroidAppCallback.postMessage(JSON.stringify(obj));
                    }
                } else if (from && (from === "IOS")) {//for IOS
                    if (window.webkit?.messageHandlers?.callbackNavigateToPage) {
                        window.webkit.messageHandlers.callbackNavigateToPage.postMessage(JSON.stringify(obj));
                    }
                }
            }

        }


        const closeNoValidPolicy = () => {
            this.setState({ noValidPolicy: false });
        }

        const closePhoneEmailPopup = () => {
            this.setState({ noPhone: false, noEmail: false, noVerifyPhone: false, noVerifyEmail: false });
        }

        const openConfirm = (customerId) => {
            clearSession();
            this.props.clearState();
            this.setState({ showConfirm: true, customerId: customerId });
            this.props.handlerSetClientId(customerId);
        }

        const showConfirmDelete = (requestId, customerId) => {
            this.setState({ deleteRequestId: requestId, customerId: customerId, showConfirm: true });
        }

        const closeConfirmDelete = () => {
            this.setState({ showConfirm: false });
        }

        const deleteClaim = () => {
            this.props.markDeleteClaimRequest(this.state.deleteRequestId, this.state.customerId);//Truyền status empty sẽ keep current status
            this.setState({ showConfirm: false });
        }

        const popupClaimCofirmed = (status) => {
            let msg = '';
            if (status === 'CONFIRM') {
                msg = 'Yêu cầu Giải quyết quyền lợi này đã nộp, vui lòng theo dõi ở mục “Danh sách yêu cầu quyền lợi bảo hiểm”';
            } else if (["CANCEL", "REJECT_CLAIM", "REJECT_DLCN", "REJECT_AUTHORIZED"].includes(status)) {
                msg = 'Thông tin Yêu cầu Giải quyết quyền lợi này đã bị hủy, do bị từ chối hoặc không được hoàn tất trong 24 giờ.';
            }
            this.setState({ claimCofirmedMsg: msg });
        }

        const closePopupClaimCofirmed = () => {
            this.setState({ claimCofirmedMsg: '' });
        }

        console.log('this.state.pageOfItems=', this.state.pageOfItems);
        return (
            <>
                {this.props.systemGroupPermission?.[0]?.Role === 'PO' ? (
                    <main className={getSession(ACCESS_TOKEN) ? 'logined' : ''}>
                        <div className='main-warpper basic-mainflex e-claim'>
                            <div className="breadcrums">
                                <div className="breadcrums__item">
                                    <p>Yêu cầu quyền lợi</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>
                                <div className="breadcrums__item">
                                    <p>Tạo mới yêu cầu</p>
                                    <p className='breadcrums__item_arrow'>></p>
                                </div>

                            </div>
                        </div>
                    </main>
                ) : (
                    <main className={getUrlParameter("fromApp")?this.props.cssSystem + ' padding-top0': this.props.cssSystem} id="scrollableDiv" style={getSession(IS_MOBILE) ? { height: '696px', overflow: "auto", paddingTop: '250px' } : { height: '625px', overflow: "scroll" }}>
                        <div className='dconnect-sdk'>
                            {getUrlParameter("fromApp") &&
                                <div className='agent-back'>
                                    <i onClick={() => this.props.closeToHome()}><img src={`${FE_BASE_URL}/img/btn-back-agent.svg`} alt="Quay lại" className='viewer-back-title' style={{ paddingLeft: '4px' }} /></i>
                                    <p className='viewer-back-title'>Danh sách yêu cầu quyền lợi</p>
                                </div>
                            }
                            {this.props.tabStatus && (this.props.tabStatus === 'ALL') ? (
                                <h4 style={{ marginBottom: '12px', marginTop: '24px', fontSize: '13px' }}>
                                    Danh sách {CLAIM_STATUS_TITLE_MAPPING[this.props.tabStatus]} hồ sơ yêu cầu quyền lợi: {this.state.clientProfileSearch.length}
                                </h4>
                            ) : (
                                this.props.tabStatus &&
                                <h4 style={{ marginBottom: '12px',marginTop: '24px', fontSize: '13px' }}>
                                    Danh sách hồ sơ yêu cầu quyền lợi {CLAIM_STATUS_TITLE_MAPPING[this.props.tabStatus]}: {this.state.clientProfileSearch.length}
                                </h4>
                            )
                            }


                            <form onSubmit={this.handleSearch}>
                                <section className='com-search' style={{ paddingRight: '8px' }}>
                                    <div className="common-searchbar">
                                        <div className="search-bar">
                                            <div className="input">
                                                <div className="input__content">
                                                    <input style={{ fontFamily: 'Inter', fontWeight: '500', fontSize: '16px' }} placeholder="Tìm kiếm" type="search" maxLength="200"
                                                        value={this.state.searchInput} onChange={(event) => this.handlerOnChangeSearchInput(event)} />
                                                </div>
                                                <i className="icon" onClick={this.handleSearch} style={{ cursor: 'pointer' }}><img src={FE_BASE_URL + "/img/icon/Search.svg"} alt="search" /></i>
                                            </div>

                                        </div>
                                    </div>
                                </section>
                            </form>
                            <div><LoadingIndicator area="submit-init-claim" /></div>
                            <section className='card-claim-section'>
                                <div>
                                    {!this.state.loading && isEmpty(this.state.pageOfItems)  && this.state.searched ? (
                                        <div className="insurance" style={{ marginTop: '-33px', paddingLeft: '0px', marginLeft: '-6px' }}>
                                            <div className="empty">
                                                <div className="icon">
                                                    <img src={FE_BASE_URL + "/img/empty-result.svg"} alt="no-data" />
                                                </div>
                                                <p style={{ paddingTop: '20px' }}>Chưa tìm thấy kết quả phù hợp.<br /> Vui lòng thử lại từ khoá khác!</p>
                                            </div>
                                        </div>
                                    ) : (

                                        <div class="card-claim-container">
                                            {this.state.pageOfItems && this.state.pageOfItems?.map((item, index) => (
                                                // <LazyLoad key={'card-' + index} offset={8} once>
                                                <div class="card-claim" style={{ display: 'flex' }}>
                                                    <div className='card-claim-left'>
                                                        {["CONFIRM", "CANCEL", "REJECT_CLAIM", "REJECT_DLCN", "REJECT_AUTHORIZED"].includes(item?.status) ? (
                                                            <div onClick={() => popupClaimCofirmed(item?.status)}>
                                                                <div class="row">
                                                                    <div class="label title basic-bold">Mã Yêu cầu:</div>
                                                                    <div class="value title">{item?.requestId}</div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">Tình trạng:</div>
                                                                    {item?.status === 'CONFIRM' ? (
                                                                        <div class="value confirm-status margin-left2">{item?.statusVN}</div>
                                                                    ) : (
                                                                        <div class="value reject-status">{item?.statusVN}</div>
                                                                    )}
                                                                    
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">NĐBH: <b>{item?.lifeInsuredName?.toUpperCase()}</b></div>
                                                                    {/* <div class="value">{item?.lifeInsuredName?.toUpperCase()}</div> */}
                                                                </div>
                                                                
                                                                    <div class="row">                                                                   
                                                                        <div class="label">Sự kiện bảo hiểm: <b>{item?.claimTypeVN}</b></div>
                                                                   
                                                                        {/* <div class="value">{item?.claimTypeVN}</div> */}
                                                                    </div>

                                                                <div class="row">
                                                                    <div class="label">BMBH:</div>
                                                                    <div class="value basic-bold">{item?.customerName?.toUpperCase()}</div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">Ngày gửi BMBH:</div>
                                                                    <div class="value">{convertDateTime(item?.effectiveDate)}</div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">Ngày cập nhật:</div>
                                                                    <div class="value">{convertDateTime(item?.updateDate)}</div>
                                                                </div>
                                                            </div>
                                                        ) : (
                                                            <div onClick={() => loadClaimByRequestId(item?.requestId, item?.status)}>
                                                                {/* {!["request", "initial", "edit"].includes(item?.status?.toLowerCase()) && */}
                                                                <div class="row">
                                                                    <div class="label title basic-bold">Mã Yêu cầu: </div>
                                                                    <div class="value title">{item?.requestId}</div>
                                                                </div>
                                                                {/* } */}
                                                                <div class="row">
                                                                    <div class="label">Tình trạng: </div>
                                                                    {["request", "initial", "edit"].includes(item?.status?.toLowerCase())?(
                                                                        <div class="value initial-status">{item?.statusVN}</div>
                                                                    ):(
                                                                        (item?.status === 'CONFIRM')?(
                                                                            <div class="value confirm-status">{item?.statusVN}</div>
                                                                        ):(
                                                                            <div class="value review-status">{item?.statusVN}</div>
                                                                        )
                                                                    )}
                                                                    
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">NĐBH: <b>{item?.lifeInsuredName?.toUpperCase()}</b></div>
                                                                    {/* <div class="value">{item?.lifeInsuredName?.toUpperCase()}</div> */}
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">Sự kiện bảo hiểm: <b>{item?.claimTypeVN}</b></div>
                                                                    {/* <div class="value">{item?.claimTypeVN}</div> */}
                                                                </div>

                                                                <div class="row">
                                                                    <div class="label">BMBH:</div>
                                                                    <div class="value">{item?.customerName?.toUpperCase()}</div>
                                                                </div>
                                                                {item?.effectiveDate &&
                                                                <div class="row">
                                                                    <div class="label">Ngày gửi BMBH:</div>
                                                                    <div class="value">{convertDateTime(item?.effectiveDate)}</div>
                                                                </div>
                                                                }
                                                                <div class="row">
                                                                    <div class="label">Ngày tạo:</div>
                                                                    <div class="value">{item?.createDate?convertDateTime(item?.createDate): ''}</div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="label">Ngày cập nhật:</div>
                                                                    <div class="value">{convertDateTime(item?.updateDate)}</div>
                                                                </div>
                                                                    {['WAITINGCONFIRMDLCN', 'POREVIEW', 'REEDIT'].includes(item?.status) && item?.expiredDate &&
                                                                        <div class="row">
                                                                            <div class="label">Yêu cầu này bị hủy sau:</div>
                                                                            <div class="value review-status" style={{marginLeft:'2px'}}>{convertDateTime(item?.expiredDate)}</div>
                                                                        </div>
                                                                    }
                                                            </div>
                                                        )}

                                                    </div>
                                                    {["request", "initial", "edit"].includes(item?.status?.toLowerCase()) &&
                                                        <div className='card-claim-right'>
                                                            <img src={FE_BASE_URL + "/img/icon/delete-claim.svg"} alt="delete" className="btn" style={{ height: '16px' }} onClick={() => showConfirmDelete(item?.requestId, item?.customerId)} />
                                                        </div>
                                                    }
                                                </div>
                                                // </LazyLoad>
                                            ))}

                                        </div>
                                    )

                                    }

                                </div>
                                {/* <div className="paging-container" id="paging-container-com-id">
                                    {this.state.clientProfileSearch !== null && (
                                        <Pagination items={this.state.clientProfileSearch} onChangePage={this.onChangePageCom} pageSize={8} />
                                    )}
                                </div> */}
                            </section>
                        </div>
                        {this.state.showConfirm &&
                            <AgreeCancelPopup closePopup={() => closeConfirmDelete()} agreeFunc={(event) => deleteClaim()}
                                title={'Yêu cầu của Quý khách sẽ bị xóa'}
                                msg={'<p>Hồ sơ yêu cầu quyền lợi đang trong quá trình khởi tạo. Quý khách chắc chắn muốn xóa yêu cầu này?</p>'}
                                imgPath={FE_BASE_URL + '/img/popup/reject-confirm.svg'} agreeText='Xác nhận xóa' notAgreeText='Quay lại' notAgreeFunc={() => closeConfirmDelete()} />}

                        {this.state.claimCofirmedMsg &&
                            <AlertPopupHight closePopup={() => closePopupClaimCofirmed()}
                                msg={this.state.claimCofirmedMsg}
                                imgPath={FE_BASE_URL + '/img/popup/confirm-claim.svg'}
                            />
                        }
                    </main>

                    // )
                )}
            </>
        )
    }
}

export default SDKTrackingClaimRequest;
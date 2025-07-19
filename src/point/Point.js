import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { ACCESS_TOKEN, CLIENT_ID, LINK_SUB_MENU_NAME, LINK_SUB_MENU_NAME_ID, CLIENT_PROFILE, TOTAL_CART_POINT, EXPIRED_MESSAGE, CLASSPO, POINT } from '../constants';
import { formatMoney, getSession } from '../util/common';

class Point extends Component {
  constructor(props) {
    super(props);
    this.state = {
      classPO: getSession(CLASSPO),
      hdpoint: '',
      point: getSession(POINT)
    }
  };

  render() {
    const selectedSubMenu = (id, subMenuName) => {
      // setSession(LINK_SUB_MENU_NAME, subMenuName);
      // setSession(LINK_SUB_MENU_NAME_ID, id);
    }
    let clientProfile = null;
    if (getSession(CLIENT_PROFILE)) {
      clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
    }
    let fullName = '';
    if (clientProfile !== null) {
      fullName = clientProfile[0].FullName;
    }

    var usePoint = '0';
    if (getSession(TOTAL_CART_POINT)) {
      usePoint = getSession(TOTAL_CART_POINT);
    }

    var logined = false;
    if (getSession(ACCESS_TOKEN)) {
      logined = true;
    }
    return (
      <main className={logined ? "logined" : ""}>
        <div className="breadcrums">
          <div className="breadcrums__item">
            <p>Trang chủ</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
          <div className="breadcrums__item">
            <p>Điểm thưởng</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
        </div>

        <section className="scpromotionpointcard">
          <div className="container">
            <div className="promotionpointcard-wrapper">
              <div className="promotionpointcard">
                {(getSession(CLIENT_ID) && (getSession(CLIENT_ID) != '')) &&
                  <div className="card">
                      <div className="card__header">
                        <h4 className="basic-bold">{fullName}</h4>
                        <div className="ranktag"><p className="basic-semibold">{this.state.classPO}</p></div>
                      </div>
                    <div className="card__footer">
                      <div className="card__footer-item">
                        <div className="cardfootertab">
                          <p>Điểm thưởng: <span className="basic-red">{formatMoney(this.state.point)}</span></p>
                          <Link to="/point-history" onClick={() => selectedSubMenu('p3', 'Lịch sử điểm thưởng')}><a className="basic-semibold simple-brown">Lịch sử điểm &gt;</a></Link>
                        </div>
                        <span className="line"></span>
                        <div className="cardfootertab">
                          <p>Giỏ quà của tôi: <span className="basic-red">{formatMoney(usePoint)}</span></p>
                          <Link to="/gift-cart" onClick={() => selectedSubMenu('p2', 'Giỏ quà của tôi')}><a className="basic-semibold simple-brown">Xem chi tiết &gt;</a></Link>
                        </div>
                      </div>
                    </div>
                  </div>
                }
              </div>
            </div>
          </div>
        </section>

        <section className="scspecialprogram">
          <div className="container">
            <h2>Chương trình đặc biệt</h2>
            <div className="program-field special-program">
              <div className="program-field__image"></div>
              <div className="program-field__content">
                <h3 className="basic-semibold">Trải nghiệm ngay - Điểm thưởng liền tay</h3>
                <p>01/03/2021 - 01/05/2021</p>
                <Link to={"/special"}>
                  <button className="btn btn-primary btn-point basic-semibold">Tìm hiểu thêm</button>
                </Link>
              </div>
            </div>
          </div>
        </section>

        <section className="scspecialprogram">
          <div className="container">
            <h2>Chương trình thường xuyên</h2>
            <div className="program-field special-program reverse">
              <div className="program-field__image"></div>
              <div className="program-field__content">
                <h3 className="basic-semibold basic-red">Gắn bó dài lâu</h3>
                <p>
                  <span>“Gắn bó dài lâu" là chương trình tích lũy điểm thưởng</span> ra đời với phương châm Khách hàng là
                  trên hết” của Dai-ichi Life Việt Nam.
                </p><span />
                <Link to={"/normal"}>
                  <button className="btn btn-primary btn-point basic-semibold">Tìm hiểu thêm</button>
                </Link>
              </div>
            </div>
          </div>
        </section>

        <section className="scpromotion-point">
          <div className="container">
            <div className="promotion-warpper">
              <Link to={"/point-exchange"} onClick={() => selectedSubMenu('p1', 'Đổi điểm thưởng')}>
                
                {getSession(CLIENT_ID) ? (
                  <button className="promotionvideo2">
                    <p>Đổi điểm</p>
                    <div className="icon-warpper">
                      <i><img src="img/icon/playbtn.svg" alt="" /></i>
                    </div>
                  </button>
                ) : (
                  <button className="promotionvideo">
                    <p>Xem kho quà tặng</p>
                    <div className="icon-warpper">
                      <i><img src="img/icon/playbtn.svg" alt="" /></i>
                    </div>
                  </button>
                )}


              </Link>
            </div>
          </div>
        </section>
      </main>

    )
  }
}

export default Point;

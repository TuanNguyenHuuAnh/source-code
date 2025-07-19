import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../constants';
import './SpecialProgramPoint.css'
import { Link } from "react-router-dom";
import { getSession } from '../util/common';

class SpecialProgramPoint extends Component {
  constructor(props) {
    super(props);
  };


  render() {
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
          <div className="breadcrums__item">
            <p>Chương trình đặc biệt</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
        </div>

        <div className="scprogram">
          <div className="container">
            <div className="program-field special-program reverse">
              <div className="program-field__image"></div>
              <div className="program-field__content">
                <div>
                  <p />
                  <h3 className="basic-bold basic-red">TRẢI NGHIỆM NGAY ĐIỂM THƯỞNG LIỀN TAY</h3>
                  <p>Trong thời gian từ 01/04/2021 đến 30/06/2021:</p>
                  <p>
                    <span> Bạn đã sẵn sàng trải nghiệm ứng dụng Dai-ichi Connect – All in one?</span>
                  </p>

                  <ul>
                    <li>Thưởng ngay 50 điểm khi đăng ký hoặc kích hoạt tài khoản mới trên ứng dụng Dai-ichi Connect</li>
                    <li>
                      <span className="basic-black-bold">ĐẶC BIỆT:</span> Thưởng thêm <span className="basic-black-bold">50 điểm</span> hoàn
                      chỉnh thông tin cá nhân trên ứng dụng Dai-ichi Connect
                    </li>
                  </ul>

                  <Link to={"/special-new"}><button className="btn btn-primary btn-point basic-semibold">Tìm hiểu thêm</button></Link>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="scprogram">
          <div className="container">
            <div className="program-field special-program">
              <div className="program-field__image"></div>
              <div className="program-field__content">
                <div>
                  <br />
                  <h3 className="basic-bold basic-red">TRẢI NGHIỆM NGAY ĐIỂM THƯỞNG LIỀN TAY</h3>

                  <p>Trong thời gian từ 01/04/2021 đến 30/06/2021:</p>
                  <p>
                    <span className="basic-bold"> Bạn đã sẵn sàng trải nghiệm ứng dụng Dai-ichi Connect – All in one?</span>
                  </p>

                  <ul>
                    <li>Thưởng ngay 50 điểm khi đăng ký hoặc kích hoạt tài khoản mới trên ứng dụng Dai-ichi Connect</li>
                    <li>
                      <span className="basic-black-bold">ĐẶC BIỆT:</span> Thưởng thêm <span className="basic-black-bold">50 điểm</span> hoàn
                      chỉnh thông tin cá nhân trên ứng dụng Dai-ichi Connect
                    </li>
                  </ul>
                  <Link to={"/special-new"}><button className="btn btn-primary btn-point basic-semibold">Tìm hiểu thêm</button></Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    )
  }
}

export default SpecialProgramPoint;

import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../constants';
import { Link } from "react-router-dom";
import { getSession} from '../util/common';

class ExchangePointSpecialNew extends Component {

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
          <div className="breadcrums__item">
            <p>Chi tiết</p>
            <p className='breadcrums__item_arrow'>></p>
          </div>
        </div>

        <div className="bg"></div>

        <section className="screading">
          <div className="container">
            <div className="reading-wrapper">
              <div className="reading">
                <div className="reading__title">
                  <h2 className="basic-bold">TRẢI NGHIỆM NGAY ĐIỂM THƯỞNG LIỀN TAY</h2>
                  <p className="basic-semibold">Bạn đã sẵn sàng trải nghiệm ứng dụng Dai-ichi Connect – All in one?</p>
                </div>
                <div className="reading__content">
                  <p>Kính thưa Quý khách hàng,</p>
                  <p>
                    Với phương châm hoạt động “Khách hàng là trên hết”, nhằm tri ân tất cả Quý khách hàng đã tín nhiệm lựa
                    chọn tham gia bảo hiểm với Dai-ichi Life Việt Nam và hơn thế nữa là mang đến cho Quý khách hàng những
                    quyền lợi cộng thêm bên cạnh những quyền lợi bảo hiểm, Dai-ichi Life Việt Nam đã triển khai Chương
                    trình chăm sóc Khách hàng đặc biệt, có tính chất toàn diện, lâu dài và duy nhất hiện nay trong ngành
                    bảo hiểm nhân thọ tại Việt Nam: Chương trình tích lũy điểm thưởng “Gắn bó dài lâu”.
                  </p>
                  <p>
                    Chương trình chăm sóc Khách hàng đặc biệt này dành cho Quý khách hàng có hợp đồng bảo hiểm đang duy
                    trì hiệu lực. Tất cả Quý khách hàng đều có thể dễ dàng tích lũy điểm thưởng với các tiêu chí thưởng
                    điểm được áp dụng kể từ ngày 09/01/2018 <Link to="/normal">(xem chi tiết)</Link>.
                  </p>
                  <p>
                    Điểm thưởng sẽ được cập nhật tự động vào tài khoản điểm thưởng của Quý khách sau khi đáp ứng các tiêu
                    chí thưởng điểm của Chương trình, trong đó 1 (một) điểm thưởng tương đương với 1.000 (một nghìn) đồng.
                  </p>
                  <p>
                    Quý khách có thể sử dụng ngay điểm thưởng tích lũy của mình để hưởng các quyền lợi hấp dẫn sau đây:
                  </p>
                  <p>
                    <span>
                      - Nhận Mã mua hàng TIKI để sử dụng mua hàng trăm mặt hàng tại trang thương mại điện tử TIKI;</span
                    >
                    <span>- Nộp phí bảo hiểm định kỳ hoặc hoàn trả các khoản tạm ứng của hợp đồng bảo hiểm;</span>
                    <span>- Nạp tiền điện thoại (là thẻ điện thoại của Vinaphone, MobiFone, Viettel);</span>
                    <span>- Nhận phiếu mua hàng siêu thị (hệ thống siêu thị Coop-mart và Big C);</span>
                    <span>- Nhận thẻ quà tặng (là thẻ trả trước đồng thương hiệu DLVN&HDBank);</span>
                    <span>- Nhận quà tặng của Dai-ichi Life Việt Nam phân phối;</span>
                    <span>- Nhận phiếu kiểm tra sức khỏe;</span>
                    <span>- Tặng cho khách hàng khác có hợp đồng bảo hiểm với Dai-ichi Life Việt Nam.</span>
                    <span>- Đầu tư vào Quỹ mở DFVN.</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>

        <section className="scprogramreadingnews">
          <div className="container">
            <h2>Xem thêm các chương trình</h2>

            <div className="programreadingnews-wrapper">
              <div className="programreadingnews">
                <div className="programreadingnews-item">
                  <div className="programreadingnews-card">
                    <div className="img-wrapper"><img src="img/9.1-background-special-1.png" alt="" /></div>
                    <div className="content">
                      <p className="simple-brown basic-semibold">Chạy đi chờ chi</p>
                      <p className="basic-blacksecondary">01/03/2021 - 01/05/2021</p>
                    </div>
                  </div>
                </div>
                <div className="programreadingnews-item">
                  <div className="programreadingnews-card">
                    <div className="img-wrapper"><img src="img/9.1-background-special-1.png" alt="" /></div>
                    <div className="content">
                      <p className="simple-brown basic-semibold">Chạy đi chờ chi</p>
                      <p className="basic-blacksecondary">01/03/2021 - 01/05/2021</p>
                    </div>
                  </div>
                </div>
                <div className="programreadingnews-item">
                  <div className="programreadingnews-card">
                    <div className="img-wrapper"><img src="img/9.1-background-special-1.png" alt="" /></div>
                    <div className="content">
                      <p className="simple-brown basic-semibold">Chạy đi chờ chi</p>
                      <p className="basic-blacksecondary">01/03/2021 - 01/05/2021</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>)
  }
}

export default ExchangePointSpecialNew;

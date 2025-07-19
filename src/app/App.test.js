<div className='stepform' style={{ marginTop: '175px' }}>
  <div className="an-hung-page" style={{ padding: '0 10px' }}>
    <div className="bo-hop-dong-container page-eleven">
      <div className="title" style={{ paddingLeft: '16px', paddingBottom: '0px' }}>
        <h4 className='basic-quy-top-padding basic-text-upper'>Nhập thông tin sức khỏe</h4>
        <i className="info-icon" onClick={() => showNotice()}
        ><img src={FE_BASE_URL + "/img/icon/Information-step.svg"} alt="information-icon" className="info-icon"
          /></i>
      </div>
      <div className="customer-informationthe-bh" style={{ paddingLeft: '61px', paddingRight: '61px' }}>
        <div className="customer-informationthe-bh consulting-service">
          <div className="customer-informationthe-bh-bottom__body">
            {/* <div className="title" style={{ paddingRight: '12px' }}>
              <h4 className='basic-quy-top-padding basic-text-upper'>Nhập thông tin sức khỏe</h4>
            </div> */}

            {this.state.insuredLapseList && this.state.insuredLapseList[0].Questionaire &&
              this.state.insuredLapseList[0].Questionaire.map((item, index) => {
                if ((this.state.expandIdLapseList.indexOf("hc-lapse-card-item-" + index) >= 0) /*&& (item.LifeInsure in this.state.insuredLapseList[0].Questionaire)*/) {
                  return (
                    <div className={index === 0 ? "information-card-item border-bottom-zero" : "information-card-item"}>
                      <div className="information-card-item-header" onClick={() => toggleCardInfo(item.LifeInsure, "hc-lapse-card-item-" + index)}>
                        <div className="information-card-item-header-text expand-text" id={"hc-lapse-card-item-" + index}>
                          {formatFullName(item.LifeInsure)}
                        </div>
                        <div className="information-card-item-header-arrow">
                          <img
                            src="img/icon/arrow-down.svg"
                            alt="arrow-down"
                            className="bottom-mar"
                          />
                        </div>
                      </div>
                      {/* <div className="information-card-item-content">
                                <div className="information-card-item-content-image width80percent">
                                    <img src={'data:image/png;base64, ' + this.state.insuredImageList[item.ClientID]} alt="insurance-card" />
                                </div>
                                <div className="information-card-item-content-text"style={{width:'453px',paddingTop:'10px'}}>
                                  <a className="left verybigheight"><div className="left verybigheight" onClick={()=>openDownload(this.state.insuredImageList[item.ClientID], item.FullName)}>Lưu thẻ</div></a>
                                  <Link className="right verybigheight" to={"/lifeassured/" + item.ClientID} onClick={()=>selectedSubMenu('item-12-1', 'Người được bảo hiểm')}><div className="right verybigheight">Quyền lợi bảo hiểm</div></Link>
                                </div>
                              </div> */}

                    </div>
                  )
                } else {
                  return (
                    <div className={index === 0 ? "information-card-item border-bottom-zero" : "information-card-item"} onClick={() => toggleCardInfo(item.LifeInsure, "hc-lapse-card-item-" + index)}>
                      <div className="information-card-item-header">
                        <div className="information-card-item-header-text" id={"hc-lapse-card-item-" + index}>
                          {formatFullName(item.LifeInsure)}
                        </div>
                        <div className="information-card-item-header-arrow">
                          <img src="img/icon/arrow-left-grey.svg" alt="arrow-left" />
                        </div>
                      </div>
                    </div>
                  )
                }

              })}

            {/* <div className="location-container">
                      <div className="location-icon">
                        <img src="img/location-brown.svg" alt="location" />
                      </div>
                      <div className="location-text">Cơ sở y tế bảo lãnh viện phí</div>
                    </div> */}

          </div>
        </div>
      </div>

      <img className="decor-clip" src="../img/mock.svg" alt="" />
      <img className="decor-person" src="../img/person.png" alt="" />

    </div>

  </div>
</div>
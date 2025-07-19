import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {getSession} from '../util/common';
import {FE_BASE_URL, ACCESS_TOKEN} from '../constants';

var logined = false;
if (getSession(ACCESS_TOKEN)) {
  logined = true;
}
function NotFound(props) {

    useEffect(() => {

        return () => {

        }
    }, []);

    return (
      <div className={logined ? "logined" : ""}>
      <main className="notfound-page">
      <div className="container padding-notfound">
        <div className="notfound-content">
          <img src={FE_BASE_URL + "/img/new/404.svg"} alt="404" className='not-found-width'/>
          <h3 className="notfound-heading">Trang Quý khách muốn xem không tồn tại</h3>
          <div className='bottom-btn' style={{position: 'relative'}}>
          <Link to="/" className="btn__content">
            <div className='btn hw-404-button'>
              Quay lại Trang chủ
            </div>
          </Link>
          </div>

        </div>
      </div>
    </main>
    </div>
    )
}

export default NotFound;
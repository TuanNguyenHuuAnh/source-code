import React from 'react';
import { GOOGLE_CLIENT_ID, GOOGLE_AUTH_URL, BACK_PATH } from '../constants';
import { setSession } from '../util/common';

const clientId = GOOGLE_CLIENT_ID;
function LoginGoogle({ parentCallback, path }) {

  const savePath=(path)=> {
    setSession(BACK_PATH, path);
  }
  return (
    <a href={GOOGLE_AUTH_URL} className="social" onClick={()=>savePath(path)}>
      <i><img src="img/icon/gmail.svg" alt="" /></i>
      <p>Google</p>
    </a>
  );
}

export default LoginGoogle;

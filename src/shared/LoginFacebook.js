import React from 'react';
import {FACEBOOK_AUTH_URL } from '../constants';

function LoginFacebook({ parentCallback }) {


  //useBeforeunload(() => "Are you sure to close this tab?");
  return (
    <a href={FACEBOOK_AUTH_URL} className="social">
      <i><img src="img/icon/facebook.svg" alt="" /></i>
      <p>Facebook</p>
    </a>

  );
}

export default LoginFacebook;

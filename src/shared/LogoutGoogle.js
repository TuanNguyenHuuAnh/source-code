import React from 'react';
import { useGoogleLogout } from 'react-google-login';
import { GOOGLE_CLIENT_ID } from '../constants';

const clientId = GOOGLE_CLIENT_ID;

function LogoutGoogle() {
  const onLogoutSuccess = (res) => {
    //console.log('Logged out Success');
  };

  const onFailure = () => {
    //console.log('Handle failure cases');
  };

  const { signOut } = useGoogleLogout({
    clientId,
    onLogoutSuccess,
    onFailure,
  });

  return (
    <button onClick={signOut} className="button">
      <img src="img/icon/gmail.svg" alt="google login" className="icon"></img>

      <span className="buttonText">Sign out</span>
    </button>
  );
}

export default LogoutGoogle;

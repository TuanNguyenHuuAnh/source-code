// import 'antd/dist/antd.css';
// import '../claim.css';
import React, { Component } from "react";
import { getDeviceId, getSession } from "../util/common";

import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN } from "../constants";

class InsParticipation extends Component {
  constructor(props) {
    super(props);

    this.state = {
      jsonInput: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          APIToken: getSession(ACCESS_TOKEN),
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          Project: "mcp",
          UserLogin: getSession(USER_LOGIN),
          ClientID: getSession(CLIENT_ID),
        },
      },
      currentUtility: null,
    };

    this.handlerClickUtility = this.clickOnIcon.bind(this);
  }

  componentDidMount() {
  }


  render() {
    return (
      <>
      </>
    );
  }
}

export default InsParticipation;

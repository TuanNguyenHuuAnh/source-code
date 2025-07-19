import React, { Component } from 'react';
import { ACCESS_TOKEN, CLIENT_ID, CLIENT_PROFILE } from '../constants';
import MainPublic from '../home/MainPublic';
import MainExisted from '../home/MainExisted';
import {setSession, getSession} from '../util/common';

class AppMain extends Component {
	constructor(props) {
		super(props);
		this.state = {
			loading: false
		}

	}

	render() {
		let clientProfile = this.props.clientProfile;
		if (getSession(CLIENT_PROFILE)) {
			clientProfile = JSON.parse(getSession(CLIENT_PROFILE));
		}
		let clientId = null;
		if (clientProfile) {
			clientId = clientProfile[0].ClientID;
		}
		if (getSession(CLIENT_ID) === '') {
			clientId = getSession(CLIENT_ID);
		}

		if (!clientProfile || clientId === null || !this.props.authenticated) {
			return (
				<div>
					<main>

						<section className="sccarousel">
							<MainPublic LoginLoading={this.state.loading} parentCallback={this.props.parentCallbackHiden} enscryptStr={this.props.enscryptStr}/>
						</section>
					</main>
				</div>
			)
		} else if (clientId === '') {
			return (
				<div>
					<MainExisted clientProfile={clientProfile} parentCallback={this.props.parentCallbackHiden} enscryptStr={this.props.enscryptStr}/>
				</div>
			)
		} else {
			return (
				<div>
					<MainExisted clientProfile={clientProfile} parentCallback={this.props.parentCallbackHiden} enscryptStr={this.props.enscryptStr}/>
				</div>
			)
		}
	}
}
export default AppMain;
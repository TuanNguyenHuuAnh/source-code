import React from 'react';
import {Link} from 'react-router-dom';
import {MAGP_FILE_FOLDER_URL} from '../constants';

class ShortcutBar extends React.Component {
    render() {
        const callbackApp = (hideMain) => {
            this.props.parentCallback(hideMain);
        }
        if (!this.props.cmsshortcutBar) {
            return "";
        } else {
            return (
                <section className="scintro">
                    <div className="container">
                        <div className="title">
                            <h1 className="basic-red" style={{fontSize : '36px'}}>Dai-ichi Connect</h1>
                            <p>Giao dịch từ xa - An tâm mọi nhà</p>
                        </div>
                        {}
                        <div className="intro-warpper">
                            <div className="intro">
                                {this.props.accountRole && this.props.accountRole === "Existed" && this.props.cmsshortcutBar && this.props.cmsshortcutBar.map((item, index) => (
                                    <div className="intro__item">
                                        <Link to={"/" + item.pageCode} onClick={() => callbackApp(true)}>
                                            <div className="icon-warpper">
                                                <img src={MAGP_FILE_FOLDER_URL + item.iconUuid} alt=""/>
                                            </div>
                                        </Link>
                                        <p className="basic-bold">{item.title}</p>
                                    </div>

                                ))}

                            </div>
                        </div>
                    </div>
                </section>


            )
        }
    }
}


export default ShortcutBar;
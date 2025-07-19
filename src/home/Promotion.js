
import React from 'react';
import { Link } from 'react-router-dom'
import { LINK_MENU_NAME_ID, LINK_MENU_NAME } from '../constants';
import { setSession } from '../util/common';

class Promotion extends React.Component
{  
  constructor(props) {
    super(props);
  }

    render()
    {
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
        selectedMenu('ah3', 'Điểm thưởng');
      }
      const selectedMenu=(id, menuName)=> {
        setSession(LINK_MENU_NAME, menuName);
        setSession(LINK_MENU_NAME_ID, id);
      }
    return(

      <section className="scpromotion">
          <div className="container">
            <div className="promotion-warpper">
              <h2>Chương trình điểm thưởng</h2>
              <Link to={"/point"} onClick={()=>callbackApp(true)}>
              <button className="promotionvideo">
                <p>trải nghiệm ngay</p>
                <div className="icon-warpper">
                  <i><img src="../img/icon/playbtn.svg" alt="" /></i>
                </div>
              </button>
              </Link>
            </div>
          </div>
        </section>
    )
}
}
export default Promotion;
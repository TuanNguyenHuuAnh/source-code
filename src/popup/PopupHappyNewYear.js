import React, { Component } from 'react';
import { NEW_YEAR_START_DATE, NEW_YEAR_END_DATE, IS_POPUP_VIEWED, CMS_HOMEPAGE, MAGP_FILE_FOLDER_URL, FROM_APP, FE_BASE_URL} from '../constants';
import { cms } from "../util/APIUtils";
import {setSession, getSession} from '../util/common';

let startDate = Date.parse(NEW_YEAR_START_DATE);
let endDate = Date.parse(NEW_YEAR_END_DATE);
class PopupHappyNewYear extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isViewed: (getSession(IS_POPUP_VIEWED) || getSession(FROM_APP)) ? true:false,
            cmspopup: null,
            homepage: null
        };
        this.closeHappyPopupEsc = this.closeHappyPopupEsc.bind(this);
    }

    componentDidMount(){
        document.addEventListener("keydown", this.closeHappyPopupEsc, false);
        setTimeout(this.checkFromApp(), 1000);
        this.GetHomePage();
    }
    checkFromApp() {
      if (getSession(FROM_APP)) {
        this.setState({isViewed: true});
      }
    }
    componentWillUnmount(){
        document.removeEventListener("keydown", this.closeHappyPopupEsc, false);
    }
    closeHappyPopupEsc=(event)=> {
        if (event.keyCode === 27) {
            this.closeHappyPopup(0);
        }
    
    } 

    closeHappyPopup=(index)=> {
      if (document.getElementById('happy-new-year-popup' + index)) {
        document.getElementById('happy-new-year-popup' + index).className = "popup";
      }
      setSession(IS_POPUP_VIEWED, IS_POPUP_VIEWED);
    }

    GetHomePage (){
        let homepage = null;
        var   jsonState = this.state;  
      
        if (getSession(CMS_HOMEPAGE)) {
            this.setState({homepage:  JSON.parse(getSession(CMS_HOMEPAGE))});
            // homepage = JSON.parse(getSession(CMS_HOMEPAGE));
            // this.setState({cmspopup:  this.GetContent(homepage.data,"popup")});
        }
        else
        {
          const homepageRequest ={"Action":"GetHomePage"};           
          cms(homepageRequest)
          .then(response => {
              if(response && response.error===0 && response.data )
                {              
                  setSession(CMS_HOMEPAGE,JSON.stringify(response));
                  this.setState({homepage:  response});
                  // this.setState({cmspopup: this.GetContent(homepage.data,"popup")});
                }
                
            }).catch(error => {
              // console.log(error);
          });  
        }
      }
      GetContent=(jsonState,cmstype)=>{
        var cmsbanner=null;
        if(jsonState){
          
          jsonState.forEach(function(item, index, array) { 
             
                if(cmstype===item.type)    
                {
                 
                  switch(item.type) {
                    case 'banner':
                      {                                                                      
                        cmsbanner=item.data.items;                        
                        break;                  
                      }
                    case 'shortcut':
                      {      
                        cmsbanner=item.data.items;
                        break;           
                      }
                      case 'favoriteProducts':
                        {          
                          cmsbanner=item.data;
                          break;           
                        }
                        case 'shortcutBar':
                          {              
                            cmsbanner=item.data.items;
                            break;           
                          }
                          case 'articleByCategory':
                            {           
                              cmsbanner=item.data.items;
                              break;           
                            }
                            case 'popup':
                                {           
                                  cmsbanner=item.data.items;
                                  break;           
                                }
                    default:
                      {
                        break;
                      }
                  }                                
                }
              })  
          }
          return cmsbanner;    
        }
    render() {
      const closeHappyPopup=(index)=> {
        if (document.getElementById('happy-new-year-popup' + index)) {
          document.getElementById('happy-new-year-popup' + index).className = "popup";
        }
        setSession(IS_POPUP_VIEWED, IS_POPUP_VIEWED);
      }
      let cmspopup = null;
      if(this.state.homepage) {
        cmspopup = this.GetContent(this.state.homepage.data,"popup")
      }
        let date = new Date();
        if (getSession(FROM_APP)) {
          return (<></>)
        }
        return (
          cmspopup && cmspopup.map((item, index) => ( 
            item.imageUuid &&
              <div className={((startDate <= date) && (date <= endDate) && !this.state.isViewed)?"popup show": "popup"} id={"happy-new-year-popup" + index}>
                <div className="popup__card">
                    <div className="happycard">
                    <img src={MAGP_FILE_FOLDER_URL+item.imageUuid} alt="" />
                    <i className="closebtn option-closebtn"><img src={FE_BASE_URL + "/img/icon/close.svg"} alt="" onClick={()=>closeHappyPopup(index)}/></i>  
                    </div>
                </div>
                <div className="popupbg"></div>
              </div>
            ))



        )
    }
}
export default PopupHappyNewYear;
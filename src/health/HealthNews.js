import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { MAGP_FILE_FOLDER_URL,ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, CLIENT_PROFILE, EXPIRED_MESSAGE, CMS_HEALTH_NEWS, AUTHENTICATION, COMPANY_KEY} from '../constants';
import { CPGetClientProfileByCLIID,CPGetPolicyInfoByPOLID,cms, logoutSession } from '../util/APIUtils';
import '../common/Common.css';
import {showMessage, setSession, getSession, getDeviceId} from '../util/common';

class HealthNews extends Component
{    
  constructor(props) {
    super(props);   
    this.state = {homepage:null,feereminder:null,UserProfile:null,  
      jsonInput2: {
        jsonDataInput: {
          Company: COMPANY_KEY,
          Authentication: AUTHENTICATION,
          DeviceId: getDeviceId(),
          APIToken: getSession(ACCESS_TOKEN),
          Project: 'mcp',
          Action: 'PolicyPolDueToDate',
          ClientID: getSession(CLIENT_ID),
          UserID: getSession(USER_LOGIN),
          LifeInsureID: ''
        } 
      }  ,
        loaded: false,
         cmsbanner:null,
         cmsshortcutBar:null,
         cmsproducts:null,
         cmsnews:null,
         cmsshortcut:null,
         clientId:null,
         clientProfile:null,
         isLogin:"",
         accountRole:null,
         postback:false
     
    };   
  }
 
    componentDidMount () {    
     this.GetHealthNewsPage ();
    } 
    GetHealthNewsPage (){
        var   jsonState = this.state;  
        if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
          this.setState({
              UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
              ,isLogin:"logined"
          });                   
        }
      if (!this.state.cmsnews) {

        if (!getSession(CMS_HEALTH_NEWS)) {
          const newsRequest = {
            "Action": "GetArticleByCategoryCode",
            "Data": { "Code": "SVSK" }
          };
          cms(newsRequest)
            .then(response => {
              if (response && response.error === 0 && response.data) {
                jsonState.cmsnews = response.data;
                setSession(CMS_HEALTH_NEWS, JSON.stringify(response.data));
                this.setState(jsonState);
              }
              else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                  pathname: '/home',
                  state: { authenticated: false, hideMain: false }

                })

              }
            }).catch(error => {
              this.props.history.push('/maintainence');
            });
        } else {
          this.setState({cmsnews: JSON.parse(getSession(CMS_HEALTH_NEWS))});
        }

      }
      }

    render(){  
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
      }   

        return (
        
         <main className={this.state.isLogin} >     
             <section className="scbreadcrums">
              <div className="container">
                <div className="breadcrums basic-white">
                  <div className="breadcrums__item">
                    <p>Trang chủ</p>
                    <span>&gt;</span>
                  </div>
                  <div className="breadcrums__item">
                    <p>Sống khỏe</p>
                    <span>&gt;</span>
                  </div>
                  <div className="breadcrums__item">
                    <p>Bản tin sống khỏe</p>
                    <span>&gt;</span>
                  </div>
                </div>
              </div>
            </section>
              <section className="schealth-banner"> 
              <div className="container">
              <h4>Bản tin sống khỏe</h4>
            </div>
              </section>  
            <div className="scnews-group">
              <div className="container">
                <div className="news-group">    
                  <div className="news-group-item">

                  {this.state.cmsnews?
                      (
                        <div className="newscard_big bigcard">
                        <Link to={"/healthnewsitem/"+this.state.cmsnews[0].articleId} >
                          <div className="img-warpper">
                            <img src={MAGP_FILE_FOLDER_URL+this.state.cmsnews[0].featuredImageUuid} alt="" />
                          </div>
                        </Link>
                        <div className="newscard__content">
                        <Link to={"/healthnewsitem/"+this.state.cmsnews[0].articleId} >
                            <h4>{this.state.cmsnews[0].title}</h4>
                          </Link>
                          <div className="newscard__content-bottom">
                            <div className="tag"><p>mới</p></div>                           
                          </div>
                        </div>
                      </div>                  

                      ):("")}
                  
                  </div>
                  <div className="news-group-item">
                      <div className="news-search">
                        <div className="input">
                          <div className="input__content">
                            <input placeholder="Nhập thông tin cần tìm kiếm..." type="search" />
                          </div>
                          <i className="icon"><img src="../img/icon/Search.svg" alt="search" /></i>
                        </div>
                      </div>
                      <div className="news-group-tab-wrapper">                      
                          {this.state.cmsnews && this.state.cmsnews.map((item, index) => ( 
                              index>0?(
                                <Link className="news-group-tab" to={"/healthnewsitem/"+item.articleId} >
                                <div className="news-group-tab">
                                <div className="img-wrapper"><img src={MAGP_FILE_FOLDER_URL+item.featuredImageUuid} alt="" /></div>
                                <div className="news-group-tab__content">
                                    <h4>{item.title}</h4>
                                  <div className="news-group-tab__content-bottom">
                                    <div className="tag"><p>mới</p></div>                                   
                                  </div>
                                </div>
                              </div>
                              </Link>
                              )
                              :("")

                          ))}
                      </div>
                  </div>                   
                </div>
              </div>
            </div>             
          </main> 
        )         
    }

}

export default HealthNews;
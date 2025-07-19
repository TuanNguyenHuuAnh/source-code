import React, { Component } from 'react';
import Shortcut from '../home/Shortcut';
import NEWS from '../home/NEWS';
import HealthNewsSlide from '../health/HealthNewsSlide';
import HealthShortcut from '../health/HealthShortcut';
import DownloadApp from '../home/DownloadApp';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, CLIENT_PROFILE, EXPIRED_MESSAGE, COMPANY_KEY ,CMS_HOMEPAGE, AUTHENTICATION} from '../constants';
import { CPGetPolicyInfoByPOLID,cms, logoutSession } from '../util/APIUtils';
import '../common/Common.css';
import {showMessage, setSession, getSession, getDeviceId} from '../util/common';
class HealthPage extends Component
{    
  constructor(props) {
    super(props);   
    this.state = {homepage:null,feereminder:null,UserProfile:null,
    jsonFeeRemider:{
          URL:'CPGetPolicyInfoByPOLID',
          JsonInput:{
          jsonDataInput:{
              Project:'mcp',
              APIToken:'ac4810ae39294ef2a48c239ef6ded986',
              Authentication:AUTHENTICATION,
              DeviceId:'8e8056d71967b3f6',
              Action:'PolicyPolDueToDate',
              OS:'Samsung_SM-A125F-Android-11',
              UserID:'0000774450',
              Company:'mcp_dlvn'
                  }
              }      
      },
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
         isLogin:null,
         accountRole:null,
         clientProfile:null,
         postback:false
     
    };
    
    
  }  

    componentWillMount () {    
    this.GetHomePage ();
   } 
    componentDidMount () {    
     this.GetHomePage ();
    } 
    GetHomePage (){
        var   jsonState = this.state;  
        if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
            this.setState({
                UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                ,isLogin:"logined"
            });                   
        }
        if(getSession(CMS_HOMEPAGE))
        {
        
           this.setState({
              homepage: JSON.parse(getSession(CMS_HOMEPAGE))
          });      
        }
        else
        {
          const homepageRequest ={"Action":"GetHomePage"};           
          cms(homepageRequest)
          .then(response => {
              if(response && response.error===0 && response.data )
                {              
                  jsonState.homepage = response;  
                  setSession(CMS_HOMEPAGE,JSON.stringify(response));
                  this.setState(jsonState);
                }
              else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                showMessage(EXPIRED_MESSAGE);
                logoutSession();
                this.props.history.push({
                  pathname: '/home',
                  state: { authenticated: false, hideMain: false}
          
                })
          
              }
            }).catch(error => {
              this.props.history.push('/maintainence');
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
        getclientProfile=(clientProfile)=>{     
          var jsonState = this.state;
          jsonState.postback=true;
          jsonState.clientProfile=clientProfile;
          this.setState(clientProfile);
          if (clientProfile && clientProfile[0] ) {          
            jsonState.clientId = clientProfile[0].ClientID;
            jsonState.isLogin="logined";
            this.setState(jsonState);
            if (jsonState.clientId === '') {
                jsonState.accountRole="Potential";              
                this.setState(jsonState);
            }
            else
              {        
                jsonState.accountRole="Existed";
                jsonState.jsonInput2.jsonDataInput.LifeInsureID = jsonState.clientId;
                const apiRequest = Object.assign({}, jsonState.jsonInput2);
                CPGetPolicyInfoByPOLID(apiRequest).then(Res => {   
                    if(Res && Res.Response && Res.Response.ClientProfile )
                    {
                      var jsonStates=this.state;
                      jsonStates.feereminder=Res.Response; 
                      this.setState(jsonStates); 
                    }
                    else if (Response.NewAPIToken === 'invalidtoken' || Response.ErrLog === 'APIToken is invalid') {
                      showMessage(EXPIRED_MESSAGE);
                      logoutSession();
                      this.props.history.push({
                        pathname: '/home',
                        state: { authenticated: false, hideMain: false}
                
                      })
                
                    }
                  }).catch(error => {
                    this.props.history.push('/maintainence');
                });         
            }
          }

        }  

    render(){    
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
      } 
        let homepage=this.state.homepage;     
        let cmsnews=null;
        let cmsshortcut=null;
    
        let clientProfile=this.props.clientProfile;
        let isLogin="";
        let accountRole="";
        let itemList=[];
        let html1="";
        if(!homepage)  
        {
          this.GetHomePage();
          homepage=this.state.homepage;
        }        
        if(this.state.postback===false)
        {
          if (clientProfile && clientProfile[0]) {
            this.getclientProfile(clientProfile);
          }
        }   
        if(homepage)
        {                   
          
            cmsnews=this.GetContent(homepage.data,"articleByCategory");
            cmsshortcut=this.GetContent(homepage.data,"shortcut");          
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
                  </div>
                </div>
              </section>  
              <section className="schealth-banner"> 
              <div className="container">
                  <h4>Sống khỏe</h4>
                </div>
              </section>
              <HealthShortcut cmsshortcut={cmsshortcut} parentCallback={this.props.parentCallback}/>
              <HealthNewsSlide cmsnews={cmsnews} parentCallback={this.props.parentCallback}/>   
          </main> 
        )         
    }

}

export default HealthPage;
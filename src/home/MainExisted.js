import React, { Component } from 'react';
import ShortcutBar from './ShortcutBar';
import Products from '../home/Products';
import Promotion from '../home/Promotion';
import Shortcut from '../home/Shortcut';
import NEWS from '../home/NEWS';
import FeeReminder from '../home/FeeReminder'
import DownloadApp from '../home/DownloadApp';
import { ACCESS_TOKEN, CLIENT_ID, USER_LOGIN, EXPIRED_MESSAGE, COMPANY_KEY, CMS_HOMEPAGE, AUTHENTICATION} from '../constants';
import { CPGetPolicyInfoByPOLID,cms, logoutSession } from '../util/APIUtils';
import '../common/Common.css';
import Banner from './Banner';
import {showMessage, setSession, getSession, getDeviceId} from '../util/common';
class MainExisted extends Component
{    
  constructor(props) {
    super(props);   
    this.state = {homepage:null,feereminder:null,
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
         isLogin:null,
         accountRole:null,
         clientProfile:null,
         postback:false
     
    };    
  }
  //   componentWillMount () {    
  //   //this.GetHomePage ();
  //  } 
    componentDidMount () {    
     this.GetHomePage ();
    } 
    componentDidUpdate () {
      if ((this.props.clientProfile !== this.state.clientProfile) && (this.state.postback===false)) {
        if (this.props.clientProfile && this.props.clientProfile[0]) {
          this.getclientProfile(this.props.clientProfile);
        }
      }
    }
    GetHomePage (){
        var   jsonState = this.state;  
      
        // if(getSession(CMS_HOMEPAGE))
        // {
        //    this.setState({
        //       homepage: JSON.parse(getSession(CMS_HOMEPAGE))
        //   });      
        // }
        // else
        // {
          const homepageRequest ={"Action":"GetHomePage"};           
          cms(homepageRequest)
          .then(response => {
              if(response && response?.data )
                {              
                  jsonState.homepage = response;  
                  setSession(CMS_HOMEPAGE,JSON.stringify(response));
                  this.setState(jsonState);
                }
                
            }).catch(error => {
              this.props.history.push('/maintainence');
          });  
        // }
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
                    //this.props.history.push('/maintainence');
                });        
            }
          }

        }  

    render(){    
        let homepage=this.state.homepage;
        let htmlFeeReminder="";
        let cmsbanner=null;
        let cmsshortcutBar=null;
        let cmsproducts=null;
        let cmsnews=null;
        let cmsshortcut=null;
        // let clientProfile=this.props.clientProfile;

        // if(!homepage)  
        // {
        //   this.GetHomePage();
        //   homepage=this.state.homepage;
        // }        

        // if(this.state.postback===false)
        // {
        //   if (clientProfile && clientProfile[0]) {
        //     this.getclientProfile(clientProfile);
        //   }
        // }     
               
       

        if(homepage)
        {                   
            cmsbanner=this.GetContent(homepage.data,"banner");           
            cmsshortcutBar=this.GetContent(homepage.data,"shortcutBar");
            cmsproducts=this.GetContent(homepage.data,"favoriteProducts");
            cmsnews=this.GetContent(homepage.data,"articleByCategory");
            cmsshortcut=this.GetContent(homepage.data,"shortcut");
            if(this.state.feereminder)
            {
              htmlFeeReminder=<FeeReminder cmdfeereminder={this.state.feereminder} parentCallback={this.props.parentCallback}/>;    
            }
            else
            {
              htmlFeeReminder="";
            }            
        }   
        const props = {
          dots: true,
          speed: 10,
          slidesToShow: 1,
          slidesToScroll: 1,
          autoplay: true,
          autoPlaySpeed:2500
        };
        return (
        
         <main className={this.state.isLogin} >
            <Banner  cmsbanner={cmsbanner} enscryptStr={this.props.enscryptStr}/>
              <ShortcutBar  accountRole={this.state.accountRole} cmsshortcutBar={cmsshortcutBar} parentCallback={this.props.parentCallback}/>              
              {htmlFeeReminder}
              <Products cmsproducts={cmsproducts} parentCallback={this.props.parentCallback} enscryptStr={this.props.enscryptStr}/>
              <Promotion   parentCallback={this.props.parentCallback}/>
              <Shortcut cmsshortcut={cmsshortcut} parentCallback={this.props.parentCallback} />
              <NEWS parentCallback={this.props.parentCallback}/>
              <DownloadApp/>          
                    
          </main> 
        )         
    }

}

export default MainExisted;
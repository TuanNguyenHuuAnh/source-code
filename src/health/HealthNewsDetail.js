import React, { Component } from 'react';
import {  MAGP_FILE_FOLDER_URL,ACCESS_TOKEN,   CLIENT_PROFILE ,CMS_HEALTH_NEWS,EXPIRED_MESSAGE } from '../constants';
import '../common/Common.css';
import {cms, cpCallMAGP,logoutSession } from '../util/APIUtils';
import HealthNewsSlide from '../health/HealthNewsSlide'
import {showMessage, setSession, getSession} from '../util/common';

class HealthNewsDetail extends Component
{          
  constructor(props) {
    super(props);   
    this.state = {UserProfile:null,           
         isLogin:null,
         articleId:null,
         jsonInput:{
           Action:'GetArticleById',
           Data:{Id:40049,isOnlyItem:false}
          }  ,
          jsonRequest: {
            URL:"CMS",
                JsonInput:{
                  Action:"GetArticleById",
                  Data:{Id:40049,isOnlyItem:false}
                 }
            
            },
          jsonResponse:null    ,
          loading:false  ,
          cmsnews:null,
          homepage:null
        
    };
  }  
 
  componentDidMount () {  
    this.LoadPage(); 
  } 
  getHealthNewItem(id){
    //const homepageRequest ={"Action":"GetArticleById","Data":{"Id":id,"isOnlyItem":false}};     
    const homepageRequest = {"URL":"CMS","JsonInput":{"Action":"GetArticleById","Data":{"Id":40049,"isOnlyItem":false}}}
      cpCallMAGP(this.state.jsonRequest)
      .then(response => {
        //console.log(response);
          if(response && response.error===0 && response.data )
            {     
              var jsonState =this.state;         
              jsonState.jsonResponse = response.data;  
              jsonState.loading=true;
              this.setState(jsonState);           
            }
            
        }).catch(error => {
          //this.props.history.push('/maintainence');
      });  

  }
  convertDateToString=(value)=>
      {
        var date = new Date(value); // M-D-YYYY
        var d = date.getDate();
        var m = date.getMonth() + 1;
        var y = date.getFullYear();
        var dateString = (d <= 9 ? '0' + d : d) + '/' + (m <= 9 ? '0' + m : m) + '/' + y;
        return dateString;
      }
    
       LoadPage (){
           var   jsonState = this.state;  
           if (getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
             this.setState({
                 UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
                 ,isLogin:"logined"
             });                   
         }  
         var id = this.props.match.params.id;
         if(id && id!=="")
         {
           //console.log(id);
          if(!this.state.articleId ||this.state.articleId!==Number(id))
             {                      
               
                 jsonState.articleId=Number(id);
                 jsonState.jsonRequest.JsonInput.Data.Id=Number(id);
                 jsonState.loading=false;
                 this.setState(jsonState);
             }
             this.getHealthNewItem(this.state.articleId);
         }             

           if( !this.state.cmsnews)
           {                   
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
      if (!this.state.UserProfile && getSession(ACCESS_TOKEN) && getSession(CLIENT_PROFILE)) {
        this.setState({
            UserProfile: JSON.parse(getSession(CLIENT_PROFILE))
            ,isLogin:"logined"
        });  
      }   
      var id = this.props.match.params.id;
         if(id && id!=="" && this.state.articleId!==Number(id))
         {
          if(!this.state.articleId ||this.state.articleId!==Number(id))
             {                      
                  var jsonState=this.state;
                 jsonState.articleId=Number(id);
                 jsonState.jsonRequest.JsonInput.Data.Id=Number(id);
                 jsonState.loading=false;
                 this.setState(jsonState);
             }
             this.getHealthNewItem(this.state.articleId);
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

                {this.state.jsonResponse?
                  ( 
                    <section className="scblog">
                      <div className="container">
                        <div className="blog">
                          <div className="blog__head">
                            <h1>{this.state.jsonResponse.title}</h1>
                            <div className="bottom">
                              <p className="time">{this.convertDateToString(this.state.jsonResponse.createdAt.substr(0,10))          }</p>                   
                            </div>
                          </div>
                          <div className="blog">                                                      
                            <div  dangerouslySetInnerHTML={{ __html:  this.state.jsonResponse.content }}>       
                            </div>
                          </div>
                        </div>
                      </div>
                    </section>)
                  :("")
                }               
                
                <HealthNewsSlide cmsnews={this.state.cmsnews} parentCallback={this.props.parentCallback}/> 
            
        </main> 
        )         
    }

}

export default HealthNewsDetail;
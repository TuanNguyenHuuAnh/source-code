import React from 'react';
import { Link } from 'react-router-dom';
import { MAGP_FILE_FOLDER_URL } from '../constants';

class HealthNewsSlide extends React.Component
{  
  constructor(props) {
    super(props);
  } 
  render()
    {
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
      } 
      var html ="",cmsnews=null; 
      if(!this.props.cmsnews)
      {
        return "";
      }
      else if(  this.props.cmsnews)
      {   
          return(
            <section className="scnews scnewshealth">
            <div className="container">
              <div className="scnews__body">
                <div className="scnews__body-title">
                  <h3 className="basic-bold">Bản tin sống khỏe</h3>
                  <Link to={"/healthnews/"}> 
                  <a className="simple-brown basic-semibold">
                    Xem tất cả <span className="simple-brown">&gt;</span></a>
                    </Link>
                </div>
                <div className="news-warpper">
                  <div className="news">     
                    { this.props.cmsnews &&  this.props.cmsnews.map((item, index) => ( 
                      index<=3?(<div className="news__item">
                      <div className="newscard">
                        <Link to={"/healthnewsitem/"+item.articleId}  >
                            <div className="img-warpper">
                            <img src={MAGP_FILE_FOLDER_URL+item.featuredImageUuid} alt="" />
                            </div>
                          </Link>
                        <div className="newscard__content">
                        <a >
                          <Link to={"/healthnewsitem/"+item.articleId} >
                          <h4>{item.title}</h4>
                          </Link>
                          </a>
                        <div className="newscard__content-bottom">
                          <div className="tag"><p>mới</p></div>
                          </div>
                        </div>
                      </div>
                    </div>):("")                      

                      ))}
                  </div>
                </div>
              </div>
            </div>
          </section>

            
      )
      }
      else
      {
          return "";

      }
  }
}

export default HealthNewsSlide;
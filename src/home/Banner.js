import React from 'react';
import {MAGP_FILE_FOLDER_URL, ENCRYPTED_DATA, MAPPING_ALT_IMG_BANNER} from '../constants';
import { Carousel } from 'antd';
// import ReactHtmlParser from "react-html-parser";
import parse from 'html-react-parser';
import { cms, enscyptData } from "../util/APIUtils";
import {convertImageUuidToAlt, getSession} from '../util/common';

let enscriptPlug = '';
class Banner extends React.Component
{  	constructor(props) {
        super(props);
        this.state = {
          reload: false,
          articleLink: {}
        }
      this.next = this.next.bind(this);
      this.previous = this.previous.bind(this);
      this.carousel = React.createRef();
      }
      next() {
        this.carousel.next();
      }
      previous() {
      this.carousel.prev();
      }
 
      buildCMSLink() {
        if(this.props.cmsbanner) {
          for (let i = 0; i< this.props.cmsbanner.length; i++) {
            let submitRequest = {
              Action: "GetArticleById",
              Data: {
                Id: this.props.cmsbanner[i].articleId
              }
            }
            cms(submitRequest)
            .then(response => {
                if(response && response.error===0 && response.data )
                  {     
                    let articleLinkCopy = this.state.articleLink;         
                    articleLinkCopy[response.data.id] = response.data.content;

                    this.setState({articleLink: articleLinkCopy});
                  }
                  
              }).catch(error => {
            }); 
          }

        }
      }
      
      componentDidMount() {
        try {
          if(this.props.cmsbanner) {
            this.buildCMSLink();
  
          } else {
            var buildCMSLink = this.buildCMSLink.bind(this);
            setTimeout(buildCMSLink, 1000);
          }
        } catch(error) {
          console.log('Banner error=', error);
        }

        
      }
  render()
    {
      if (this.props.enscryptStr) {
        enscriptPlug = '&payload=' + this.props.enscryptStr;
      } else if (getSession(ENCRYPTED_DATA)) {
        enscriptPlug = '&payload=' + getSession(ENCRYPTED_DATA);
      } else {
        enscriptPlug = '&payload=\"\"';
      }
      const onMouseOut = () => {
       // alert (12);
        document.getElementById("btnprev").setAttribute("style","display:none");
        document.getElementById("btnnext").setAttribute("style","display:none");
      }
      const onMousein = () => {
        //alert (1);
        document.getElementById("btnprev").setAttribute("style","display:block");
        document.getElementById("btnnext").setAttribute("style","display:block");
      }
      const props = {
        dots: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoPlaySpeed:4000
      };
      var html ="",html1 ="",cmsbanner=null;  
      var dothtml =""  ;
      if(this.props.cmsbanner)
      {
        var articleLink = this.state.articleLink;
          this.props.cmsbanner.forEach(function(item, index, array) {

            html+='<div class="back-img"  >';
            html+='<img  src="'+MAGP_FILE_FOLDER_URL+item.imageUuid+'" alt=""/>';
            html+='</div>';    
            html1+='<div class="carousel_dlvn">';
            html1+='<div class="banner-content"  ></div>';
            html1+='<div class="back-img" >';
            if (articleLink[item.articleId]) {
              html1+='<a class="back-img" target="_blank" rel="noreferrer" href="' + (articleLink[item.articleId] + ((item.pageCode === 'SOP')?enscriptPlug:'')) + '">'
            }
            html1+='  <img src="'+MAGP_FILE_FOLDER_URL+item.imageUuid+'" alt="'+ convertImageUuidToAlt(item.imageUuid, MAPPING_ALT_IMG_BANNER)+ '" />';
            if (articleLink[item.articleId]) {
              html1+='</a>';
            }
            html1+='</div>';
            html1+=' </div>';
            dothtml+="<div class='dot'></div>";
          })
        
          return(
            <section className="sccarousel">	
            <div className="custom-carousel swiper" onMouseOut={()=>onMouseOut()} onMouseOver={()=>onMousein()}>
                  <div className="custom-carousel swiper">           
                        <Carousel ref={node => (this.carousel = node)}  {...props}  >
                        {parse(html1)}       
                        </Carousel>
                  </div>  
                  <div className="control">
                    <button className="prev basic-none" id="btnprev"  onClick={this.previous}  >
                    <i>
                      <img src="img/slider/control_left.svg" alt="" />
                    </i>
                    </button>
                    <button className="next basic-none" id="btnnext"  onClick={this.next}>
                    <i>
                      <img src="img/slider/control_right.svg" alt="" />
                    </i>
                    </button>
                  </div>
                  <div className="banner-paging">									
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

export default Banner;
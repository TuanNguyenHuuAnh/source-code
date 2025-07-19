import React from 'react';
import { Link } from 'react-router-dom';
import { MAGP_FILE_FOLDER_URL,PAGE_RUN, PAGE_ADVISORY } from '../constants';

class HealthShortcut extends React.Component
{  
  constructor(props) {
    super(props);
  }
  
  render()
    {
      const callbackApp = (hideMain) => {
        this.props.parentCallback(hideMain);
      } 
      var html ="",cmsshortcut=null;    
      if(!this.props.cmsshortcut)
      {
        return "";
      }
      else 
      {    
        //console.log(this.props.cmsshortcut);
          return(
            <section className="scnews scnews__health">
            <div className="container">
              <div className="scnews__head">
                   
                    <div className="icon-warpper">         
                    {this.props.cmsshortcut  && this.props.cmsshortcut.map((item, index) => ( 
                      (item.pageCode==="PAGE_RUN" ) || (item.pageCode==="PAGE_ADVISORY")?(<a href={item.pageCode==="PAGE_RUN"?PAGE_RUN:PAGE_ADVISORY} target="_blank">
                                                   <div className="icon">
                                                      <i><img src={MAGP_FILE_FOLDER_URL+item.iconUuid} alt="" /></i>
                                                      <p>{item.title.substring(0, 7)} <br/>{item.title.substring(7)}</p>
                                                  </div>
                                                </a>)
                                                :("")
                   
                         
                    ))}
                    </div>
                  </div>
            </div>
          </section>

        
      )
      }
  }
}

export default HealthShortcut;
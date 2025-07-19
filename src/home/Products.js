
import React from 'react';
import { MAGP_FILE_FOLDER_URL, PAGE_ATSH, PAGE_ATHTTD, PAGE_APHTTD, PAGE_ANHT, PAGE_ATDT, PAGE_DGAP, PAGE_KCare, PAGE_ICHIGO, ENCRYPTED_DATA } from '../constants';
import { getSession } from '../util/common';

let enscriptPlug = '';
class Products extends React.Component {
    constructor(props) {
      super(props);
    }
  redirectPage(value, enscriptPlug, url) {
    var redirectLink = "";
    switch (value) {
      case 'PAGE_ATSH':
        {
          redirectLink = PAGE_ATSH;
          break;
        }
      case 'PAGE_ATHTTD':
        {
          redirectLink = PAGE_ATHTTD;
          break;
        }
      case 'PAGE_APHTTD':
        {
          redirectLink = PAGE_APHTTD;
          break;
        }
      case 'PAGE_ANHT':
        {
          redirectLink = PAGE_ANHT;
          break;
        }
      case 'PAGE_ATDT':
        {
          redirectLink = PAGE_ATDT;
          break;
        }
      case 'PAGE_DGAP':
        {
          redirectLink = PAGE_DGAP;
          break;
        }
      case 'SOP':
        {
          redirectLink = url + enscriptPlug;
          break;
        }
      default:
        {
          redirectLink = url?url:"/";
        }

    }
    return redirectLink;
  }

  render() {
    if (this.props.enscryptStr) {
      enscriptPlug = '&payload=' + this.props.enscryptStr;
    } else if (getSession(ENCRYPTED_DATA)) {
      enscriptPlug = '&payload=' + getSession(ENCRYPTED_DATA);
    } else {
      enscriptPlug = '&payload=\"\"';
    }
    let items = null;
    if (this.props.cmsproducts && this.props.cmsproducts.items) {
      items = (this.props.cmsproducts.items.length > 6) ? this.props.cmsproducts.items.slice(0, 6) : this.props.cmsproducts.items;
    }
    var t = items ? items.length - 1 : 0;
    var x = (5 - t) * 100;
    var s = x + 'px';
    const callbackApp = (hideMain) => {
      this.props.parentCallback(hideMain);
    }
    var html = "", cmsProducts = null;
    if (!this.props.cmsproducts) {
      return "";
    }
    else {
      return (
        <section className="scproduct">
          <div className="container">
            <h2>{this.props.cmsproducts.title}</h2>
            <div className="products-warpper" >
              <div className="products">
                {items && items.map((item, index) => (
                  <div className="products__item" key={'product-item' + index}>
                    <a href={this.redirectPage(item.pageCode, enscriptPlug, item.url)} target="_blank">
                      <div className="productcard">
                        <div className="img-warpper">
                          <img src={MAGP_FILE_FOLDER_URL + item.imageUuid} alt="" />
                        </div>
                        <div className="content">
                          <h3 className="basic-semibold">{item.title}</h3>
                        </div>
                      </div>
                    </a>
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


export default Products;
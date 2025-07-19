import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { FE_BASE_URL } from './sdkConstant';
const propTypes = {
  items: PropTypes.array.isRequired,
  onChangePage: PropTypes.func.isRequired,
  initialPage: PropTypes.number,
  pageSize: PropTypes.number,
  totals: PropTypes.number,
  offsetSize: PropTypes.number,
  offset: PropTypes.number
}
const defaultProps = {
  initialPage: 1,
  pageSize: 10
}
class Pagination2 extends Component {

  constructor(props) {
    super(props);
    this.state = {
      pager: {
      },
      startIndex:'',
      endIndex:''
    };
  }


  componentWillMount() {
    // set page if items array isn't empty
    if (this.props.items && this.props.items.length) {
      this.setPage(this.props.initialPage);
    }
  }
  componentDidUpdate(prevProps, prevState) {
    // reset page if items array has changed
    if (this.props.items !== prevProps.items) {
      this.setPage(this.props.initialPage);
    }
  }
  setPage(page) {
    var {totals, offsetSize, items, pageSize, offset } = this.props;
    var pager = this.state.pager;
    if (page < 1 || ((page > pager.totalPages) && (pager.totalPages > 0) )) {
      return;
    }
    if (!totals || totals < 0) {
      totals = items.length;
    }
    // get new pager object for specified page
    pager = this.getPager(totals, items.length, page, pageSize, offsetSize, offset);

    // get new page of items from items array
    var pageOfItems = items.slice(pager.startIndex, pager.endIndex + 1);

    // update state
    this.setState({ pager: pager });

    // call change page function in parent component
    this.props.onChangePage(pageOfItems);
  }
  getPager(totalItems, arraySize, currentPage, pageSize, offsetSize, offset) {
    // default to first page
    currentPage = currentPage || 1;

    // default page size is 10
    pageSize = pageSize || 10;

    // calculate total pages
    var totalPages = Math.ceil(totalItems / pageSize);

    var startPage, endPage;
    if (totalPages <= 10) {
      // less than 10 total pages so show all
      startPage = 1;
      endPage = totalPages;
    } else {
      // more than 10 total pages so calculate start and end pages
      if (currentPage <= 6) {
        startPage = 1;
        endPage = 10;
      } else if (currentPage + 4 >= totalPages) {
        startPage = totalPages - 9;
        endPage = totalPages;
      } else {
        startPage = currentPage - 5;
        endPage = currentPage + 4;
      }
      // console.log('currentPage=' + currentPage + ' startPage=' + startPage);
    }
    // calculate start and end item indexes
    var startIndex = (currentPage - 1) * pageSize;
    var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
    // console.log('startIndex=' + startIndex + ' endIndex=' + endIndex);
    var jsonState = this.state;
    jsonState.startIndex = startIndex;
    jsonState.endIndex = endIndex;
    let nextOffset = offset;
    nextOffset = Math.floor(currentPage*pageSize/offsetSize) + 1;
    // let totalOffset = Math.floor(totalItems/offsetSize) + 1;
    // if (nextOffset > totalOffset) {
    //   nextOffset = totalOffset;
    // }
    this.setState(jsonState);

    // create an array of pages to ng-repeat in the pager control
    var pages = [...Array((endPage + 1) - startPage).keys()].map(i => startPage + i);
    // return object with all pager properties required by the view
    return {
      totalItems: totalItems,
      currentPage: currentPage,
      pageSize: pageSize,
      totalPages: totalPages,
      startPage: startPage,
      endPage: endPage,
      startIndex: startIndex,
      endIndex: endIndex,
      pages: pages,
      nextOffset: nextOffset
    };
  }

  render() {
    var pager = this.state.pager;

    if (!pager.pages || pager.pages.length < 1) {
      // don't display pager if there is only 1 page
      return null;
    }

    const goToPage=(pageNum)=> {
      this.setPage(pageNum);
      let nextOffset = this.state.pager.nextOffset;
      let startIndex = this.state.pager.startIndex;
      let endIndex = this.state.pager.endIndex;
      var {offsetSize, pageSize } = this.props;
      nextOffset = Math.floor(pageNum*pageSize/offsetSize) + 1;
      this.props.callbackLoadOffestPolicies(nextOffset, pageNum);
    }
    
    return (
      <div className="paging-wrapper">
        <div className="paging-detail">
          <p>
            <span className="basic-semibold" style={{color:'#727272'}}>{(this.state.startIndex)+1}-{(this.state.endIndex)+1} trong số </span>
            <span className="basic-semibold" style={{color:'#727272'}}> {this.props.totals}</span>
          </p>
        </div>
        <div className="paging">
          <div className="arrow-left">
            <button className={pager.currentPage === 1 ? "page-number" : ""} onClick={() => goToPage(pager.currentPage - 1)}>
              <img
                src={FE_BASE_URL + "/img/icon/9.1/paging/9.1-paging-icon-arrow.svg"}
                alt=""
              />
            </button>
            <button className={pager.currentPage === 1 ? "page-number" : ""} onClick={() => goToPage(1)}>
              <img
                src={FE_BASE_URL + "/img/icon/9.1/paging/9.1-paging-icon-arrowDouble.svg"}
                alt=""
              />
            </button>
          </div>
          <div className="number" style={{marginTop: '2px'}}>
            {pager.pages.map((page, index) =>
              <button key={index} className={pager.currentPage === page ? "page-number active" : ""} onClick={() => goToPage(page)}>
                <span>{page}</span>
              </button>
            )}
          </div>
          <div className="arrow-right" style={{marginTop: '4px', paddingTop: '3px'}}>
            <button className={pager.currentPage === pager.totalPages ? "page-number" : ""} onClick={() => goToPage(pager.currentPage + 1)}>
              <img
                src={FE_BASE_URL + "/img/icon/9.1/paging/9.1-paging-icon-arrow.svg"}
                alt=""
              />
            </button>
            <button className={pager.currentPage === pager.totalPages ? "page-number" : ""} onClick={() => goToPage(pager.totalPages)}>
              <img
                src={FE_BASE_URL + "/img/icon/9.1/paging/9.1-paging-icon-arrowDouble.svg"}
                alt=""
              />
            </button>
          </div>
        </div>
      </div>
    );
  }
}

Pagination2.propTypes = propTypes;
Pagination2.defaultProps = defaultProps;
export default Pagination2;
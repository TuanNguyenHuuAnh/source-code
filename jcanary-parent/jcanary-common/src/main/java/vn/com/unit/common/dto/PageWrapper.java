/*******************************************************************************
 * Class        PageWrapper
 * Created date 2015/01/09
 * Lasted date  2015/01/09
 * Author       KhoaNA
 * Change log   2015/01/0901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.common.constant.CommonConstant;

/**
 * PageWrapper
 * This class use for paging
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PageWrapper<T> {
    /** Show max page */
    private int maxPage = 5;
    /** Size element T of page */
    private int sizeOfPage = 2;
    /** Current page */
    private int currentPage;
    /** List page */
    private List<PageItem> items;
    /** Total pages */
    private int totalPages;
    /** List element T of page */
    private List<T> data;
    /** Count all data */
    private int countAll;
    /** Show start page */
    private int start;
    /** Show start page */
    private Integer startIndexCurrent;
    /**list page size*/
    private List<Integer> listPageSize;
    
    private int preMaxPage;
    
    private int nextMaxPage;
    
    /**count error*/
    private int errorCount;
    
    public PageWrapper() {
    	data = new ArrayList<>();
    }
    
    public PageWrapper(int currentPage, int sizeOfPage) {
        if( currentPage == CommonConstant.NUMBER_ZERO ) {
            currentPage = CommonConstant.NUMBER_ONE;
        }
        
        this.currentPage = currentPage;
        this.sizeOfPage = sizeOfPage;
        this.preMaxPage = getPreMaxPage();
        this.nextMaxPage = getNextMaxPage();
    }
    
    public PageWrapper(int currentPage, int sizeOfPage,List<Integer> listPageSize) {
        if( currentPage == CommonConstant.NUMBER_ZERO ) {
            currentPage = CommonConstant.NUMBER_ONE;
        }
        
        this.currentPage = currentPage;
        this.sizeOfPage = sizeOfPage;
        this.listPageSize = listPageSize;
              
    }
    

    /**
     * 
     * @author KhoaNA
     * 
     * @param content
     *              
     * @return
     */
    public void setDataAndCount(List<T> data, int countAll) {
        items = new ArrayList<PageItem>();

        this.data = data;
        this.countAll = countAll;
        
        this.totalPages = countAll/this.sizeOfPage;

        int residuals = countAll%this.sizeOfPage;
        if( residuals > 0 ) {
            this.totalPages = this.totalPages + 1;
        }

        if(this.currentPage < this.maxPage) {
            if( this.currentPage%this.maxPage > 0 ) {
                this.start = this.currentPage/this.maxPage + 1;
            } else {
                this.start = this.currentPage/this.maxPage;
            }
        } else {
            int offset = 0;
            if( this.currentPage%this.maxPage > 0 ) {
                offset = this.currentPage/this.maxPage + 1;
            } else {
                offset = this.currentPage/this.maxPage;
            }
            this.start = (offset-1)*maxPage+1;
        }
    }
    
    /**
     * Get items page
     * 
     * @return List<PageItem>
     * @since 01-00
     * @author KhoaNA
     */
    public List<PageItem> getItems() {
        int showPages = this.totalPages - this.start >= this.maxPage ? this.maxPage : this.totalPages - this.start + 1;
        for (int i = 0; i < showPages ; i++) {
            this.items.add(new PageItem(this.start + i, this.start + i == this.currentPage));
        }
        return this.items;
    }
    
    /**
     * nextMaxPageGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getNextMaxPage() {
        // current page temp
//        int currentPage =  this.start + this.maxPage;
        this.nextMaxPage = this.currentPage < this.totalPages ? this.currentPage + 1 : -1;
        return this.nextMaxPage;
    }

    /**
     * preMaxPageGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getPreMaxPage() {
        // current page temp
//        int currentPage = this.start - this.maxPage;
        this.preMaxPage = this.currentPage > 1 ? this.currentPage - 1  : -1;
        return this.preMaxPage;
    }
    
    /**
	 * @return the errorCount
	 */
	public int getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount the errorCount to set
	 */
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	/**
     * totalPagesGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getTotalPages() {
        return this.totalPages;
    }

    /**
     * maxPageGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getMaxPage() {
        return maxPage;
    }

    /**
     * maxPageSet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    /**
     * sizeOfPageGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getSizeOfPage() {
        return sizeOfPage;
    }

    /**
     * sizeOfPageSet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }

    /**
     * currentPageGet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * currentPageSet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get data
     * @return List<T>
     * @author KhoaNA
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Set data
     * @param   data
     *          type List<T>
     * @return
     * @author  KhoaNA
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * itemsSet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public void setItems(List<PageItem> items) {
        this.items = items;
    }

    /**
     * totalPagesSet
     * 
     * @since 01-00
     * @author KhoaNA
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Get start index page
     * @return int
     * @author KhoaNA
     */
    public Integer getStartIndexCurrent() {
    	if(null == startIndexCurrent)
    		startIndexCurrent = (currentPage-1)*sizeOfPage+1;
        return startIndexCurrent;
    }

    /**
     * Get start index page
     * @param startIndexCurrent
     * @author KhoaNA
     */
    public void setStartIndexCurrent(Integer startIndexCurrent) {
		this.startIndexCurrent = startIndexCurrent;
	}

	/**
     * Get countAll
     * @return int
     * @author KhoaNA
     */
    public int getCountAll() {
        return countAll;
    }

    /**
     * Set countAll
     * @param   countAll
     *          type int
     * @return
     * @author  KhoaNA
     */
    public void setCountAll(int countAll) {
        this.countAll = countAll;
    }

	/**
	 * Get listPageSize
	 * @return List<Integer>
	 * @author phunghn
	 */
	public List<Integer> getListPageSize() {
		return listPageSize;
	}

	/**
	 * Set listPageSize
	 * @param   listPageSize
	 *          type List<Integer>
	 * @return
	 * @author  phunghn
	 */
	public void setListPageSize(List<Integer> listPageSize) {
		this.listPageSize = listPageSize;
	}
    
}
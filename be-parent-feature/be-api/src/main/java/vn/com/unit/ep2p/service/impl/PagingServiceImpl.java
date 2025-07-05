/*******************************************************************************
 * Class        ：PagingServiceImpl
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.core.req.dto.PagingReq;
import vn.com.unit.ep2p.service.PagingService;

/**
 * PagingServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PagingServiceImpl implements PagingService{

    /*
     * @Autowired SystemConfig systemConfig;
     */
    
    @Override
    public void setPagingDefault(PagingReq reqPagingDto) {
        // Init page size
        if(null == reqPagingDto.getPageSize()) {
            reqPagingDto.setPageSize(10);//TODO systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        }     
        //Init page index
        if(null == reqPagingDto.getPageIndex()){
            reqPagingDto.setPageIndex(1);
        }
        
        // Init Page size Service
//        Integer iPageSize = null;
        if (null != reqPagingDto.getIsPaging() && reqPagingDto.getIsPaging() == 1) {
//            PageWrapper<Object> pageWrapper = new PageWrapper<>(page, pageSize);
//            int currentPage = pageWrapper.getCurrentPage();
//            startIndex = (currentPage - 1) * pageSize;
        }
    }

}

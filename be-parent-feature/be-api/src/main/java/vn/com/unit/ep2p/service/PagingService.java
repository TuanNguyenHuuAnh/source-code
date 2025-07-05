/*******************************************************************************
 * Class        ：PagingService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.ep2p.core.req.dto.PagingReq;

/**
 * PagingService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface PagingService {

    public void setPagingDefault(PagingReq reqPagingDto);
}

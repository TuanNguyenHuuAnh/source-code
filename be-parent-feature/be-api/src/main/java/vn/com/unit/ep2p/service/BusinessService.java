/*******************************************************************************
 * Class        ：BusinessService
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.BusinessAddReq;
import vn.com.unit.ep2p.dto.req.BusinessUpdateReq;
import vn.com.unit.workflow.dto.JpmBusinessDto;

/**
 * <p>
 * BusinessService
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface BusinessService extends BaseRestService<ObjectDataRes<JpmBusinessDto>, JpmBusinessDto> {


    /**
     * <p>
     * create business
     * </p>
     * 
     * @param reqBusinessAddDto
     *            type {@link BusinessAddReq}
     * @return {@link JpmBusinessDto}
     * @author KhuongTH
     * @throws DetailException
     */
    JpmBusinessDto create(BusinessAddReq reqBusinessAddDto) throws DetailException;

    /**
     * <p>
     * update business information
     * </p>
     * 
     * @param reqBusinessUpdateDto
     *            type {@link BusinessUpdateReq}
     * @author KhuongTH
     * @throws DetailException
     */
    void update(BusinessUpdateReq reqBusinessUpdateDto) throws DetailException;
}

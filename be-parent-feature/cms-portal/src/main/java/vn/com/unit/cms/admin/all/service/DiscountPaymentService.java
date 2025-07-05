/*******************************************************************************
 * Class        ：DiscountPaymentService
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.admin.all.dto.DiscountPaymentTypeDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

/**
 * DiscountPaymentService
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface DiscountPaymentService {

    /**
     * @param id
     * @return
     */
    DiscountPaymentTypeDto getDiscountPaymentTypeEditModel(Integer id);

    /**
     * @return
     */
    DiscountPaymentTypeDto initPaymentTypeAddModel();

    /**
     * @param discountPaymentTypeDto
     * @return
     */
    DiscountPaymentTypeDto saveAddPaymentType(DiscountPaymentTypeDto discountPaymentTypeDto);

    /**
     * @param discountPaymentTypeDto
     * @return
     */
    DiscountPaymentTypeDto saveUpdatePaymentType(DiscountPaymentTypeDto discountPaymentTypeDto);

    /**
     * @param code
     * @return
     */
    int getPaymentCountByCode(String code);

    /**
     * @param page
     * @param searchDto
     * @return
     */
    PageWrapper<DiscountPaymentTypeDto> searchDiscountPaymentTypeList(int page, CommonSearchDto searchDto);

}

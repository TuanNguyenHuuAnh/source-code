/*******************************************************************************
 * Class        ：DiscountPaymentTypeRepository
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.DiscountPaymentType;
import vn.com.unit.db.repository.DbRepository;

/**
 * DiscountPaymentTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface DiscountPaymentTypeRepository extends DbRepository<DiscountPaymentType, Integer>{

    /**
     * @param code
     * @return
     * TODO
     */
    int countByCode(@Param("code")String code);

    /**
     * @return
     */
    Integer findMaxSort();

}

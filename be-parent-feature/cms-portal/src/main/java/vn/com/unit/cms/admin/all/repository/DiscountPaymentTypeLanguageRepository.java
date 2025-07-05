/*******************************************************************************
 * Class        ：DiscountPaymentTypeLanguageRepository
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.DiscountPaymentTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * DiscountPaymentTypeLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface DiscountPaymentTypeLanguageRepository extends DbRepository<DiscountPaymentTypeLanguage, Integer>{

    /**
     * @param paymentTypeId
     * @return
     * TODO implement
     */
    List<DiscountPaymentTypeLanguage> findByPaymentTypeId(@Param("paymentTypeId")Integer paymentTypeId);

}

/*******************************************************************************
 * Class        ：PaymentCardRepository
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;

import vn.com.unit.cms.admin.all.entity.PaymentCard;
import vn.com.unit.db.repository.DbRepository;

/**
 * PaymentCardRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface PaymentCardRepository extends DbRepository<PaymentCard, Integer> {

}

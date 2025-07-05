/*******************************************************************************
 * Class        ：ConstantLanguageRepository
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;

import vn.com.unit.cms.admin.all.entity.ConstantLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * ConstantLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface ConstantLanguageRepository extends DbRepository<ConstantLanguage, Long> {
    
}
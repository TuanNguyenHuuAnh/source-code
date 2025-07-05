/*******************************************************************************
 * Class        ：JobLanguageRepository
 * Created date ：2017/03/08
 * Lasted date  ：2017/03/08
 * Author       ：TranLTH
 * Change log   ：2017/03/08：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;

import vn.com.unit.cms.admin.all.entity.JobLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * JobLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface JobLanguageRepository extends DbRepository<JobLanguage, Long> {

}

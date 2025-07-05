/*******************************************************************************
 * Class        ：ShareHolderManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;

import vn.com.unit.cms.admin.all.entity.InvendorCategory;
import vn.com.unit.db.repository.DbRepository;

public interface InvendorCategoryRepository extends DbRepository<InvendorCategory, Long> {
	
}

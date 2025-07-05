/*******************************************************************************
 * Class        ：ServiceDetailRepository
 * Created date ：2017/05/29
 * Lasted date  ：2017/05/29
 * Author       ：tungns
 * Change log   ：2017/05/29：01-00 tungns create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ServiceDetailDto;
import vn.com.unit.cms.admin.all.entity.ServiceDetail;
import vn.com.unit.db.repository.DbRepository;

/**
 * ServiceDetailRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns
 */
public interface ServiceDetailRepository extends DbRepository<ServiceDetail, Long> {

	/**
	 * @param id
	 * @return
	 */
	List<ServiceDetailDto> findDetailList(@Param("id") Long id, @Param("langCode") String langCode);

	/**
	 * 
	 * @param serviceID
	 * @param groupContent
	 */
	@Modifying
	void deleteDetailList(@Param("serviceID") Long serviceID, @Param("groupContent") String groupContent);

}

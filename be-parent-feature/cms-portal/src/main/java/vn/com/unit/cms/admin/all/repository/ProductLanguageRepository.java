/*******************************************************************************
 * Class        ：ProductLanguageRepository
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
//import jp.xet.springframework.data.mirage.repository.MirageRepository;
import vn.com.unit.cms.admin.all.entity.ProductLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * ProductLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductLanguageRepository extends DbRepository<ProductLanguage, Long> {
	/**
	 * delete ProductLanguage by product id
	 *
	 * @param deleteDate
	 * @param deleteBy
	 * @param productId
	 * @author hand
	 */
	@Modifying
	public void deleteByProductId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("productId") Long productId);

	/**
	 * find all ProductLanguage by productId
	 *
	 * @param productId
	 * @return List<ProductLanguage>
	 * @author hand
	 */
	List<ProductLanguage> findByProductId(@Param("productId") Long productId);

}

/*******************************************************************************
 * Class        ：ProductRepository
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.ProductDetail;
import vn.com.unit.db.repository.DbRepository;

/**
 * ProductRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface ProductDetailRepository extends DbRepository<ProductDetail, Long> {

	/**
	 * findProductDetaiByProductId
	 *
	 * @param productId
	 * @return <ProductDetail>
	 * @author hand
	 */
	List<ProductDetail> findProductDetaiByProductId(@Param("productId") Long productId);

	@Modifying
	void deleteByProductId(@Param("productId") Long productId);

}

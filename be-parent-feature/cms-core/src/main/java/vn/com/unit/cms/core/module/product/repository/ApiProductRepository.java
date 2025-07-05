/*******************************************************************************
 * Class        ：ProductRepository
 * Created date ：2023/08/04
 * Lasted date  ：2023/08/04
 * Author       ：thu.thai
 * Change log   ：2023/08/04 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.product.repository;

import java.util.List;

import vn.com.unit.cms.core.module.product.entity.Product;
import vn.com.unit.db.repository.DbRepository;

public interface ApiProductRepository extends DbRepository<Product, Long>{
	 public List<Product> getAllProduct() ;

}

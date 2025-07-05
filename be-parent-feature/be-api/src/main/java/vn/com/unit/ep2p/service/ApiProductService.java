/*******************************************************************************
 * Class        ：ApiProductService
 * Created date ：2023/08/04
 * Lasted date  ：2023/08/04
 * Author       ：thu.thai
 * Change log   ：2023/08/04 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.List;

import vn.com.unit.cms.core.module.product.entity.Product;

public interface ApiProductService {
	public List<Product> getAllProduct() throws IOException;
}

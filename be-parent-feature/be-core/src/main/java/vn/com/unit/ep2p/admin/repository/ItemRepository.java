/*******************************************************************************
 * Class        ItemRepository
 * Created date 2017/03/28
 * Lasted date  2017/03/28
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/03/2801-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.repository.JcaItemRepository;
import vn.com.unit.ep2p.admin.dto.ItemDto;


/**
 * ItemRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public interface ItemRepository extends JcaItemRepository {

	/**
	 * Find all item by sub_type
	 * 
	 * @param subType
	 * @return listItem : List<Item>
	 */
	public List<ItemDto> findItemBySubType(@Param("subType") String subType);

	/**
	 * Find all item by sub_type
	 * 
	 * @param subType
	 * @return listItem : List<Item>
	 */
	public List<ItemDto> findItemBySubTypeOracle(@Param("subType") String subType);
}

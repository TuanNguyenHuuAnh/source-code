/*******************************************************************************
 * Class        :JcaItemRepository
 * Created date :2020/12/07
 * Lasted date  :2020/12/07
 * Author       :MinhNV
 * Change log   :2020/12/07:01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaItemRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaItemRepository extends DbRepository<JcaItem, Long> {

    /**
     * <p>
     * count all item by condition
     * </p>
     * .
     *
     * @param jcaItemSearchDto
     *            type {@link JcaItemSearchDto}
     * @return int
     * @author MinhNV
     */
    int countJcaItemDtoByCondition(@Param("jcaItemSearchDto") JcaItemSearchDto jcaItemSearchDto);

    /**
     * <p>
     * get list JcaItemDto by condition
     * </p>
     * .
     *
     * @param jcaItemSearchDto
     *            type {@link JcaItemSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List of JcaItemDto
     * @author MinhNV
     */
    Page<JcaItemDto> getJcaItemDtoByCondition(@Param("jcaItemSearchDto") JcaItemSearchDto jcaItemSearchDto,@Param("pageable") Pageable pageable);

    /**
     * <p>
     * get JcaItem by id
     * </p>
     * .
     *
    
     */
    JcaItemDto getJcaItemDtoById(@Param("id") Long id);

    /**
     * <p>
     * get list JcaItem
     * </p>
     * .
     *
     * @return List<JcaItem>
     * @author MinhNV
     */
    List<JcaItem> getItemDtoList();

    /**
     * <p>
     * Check exist item.
     * </p>
     *
     * @param item
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link Boolean}
     * @author taitt
     */
    Boolean checkExitsItemByItemCodeAndCompanyId(@Param("item") String item, @Param("companyId") Long companyId);

    /**
     * Gets the item id by item code and company id.
     *
     * @param item
     *            type {@link String}
     * @param companyId
     *            the company id type Long
     * @return the item id by item code and company id
     * @author tantm
     */
    Long getItemIdByItemCodeAndCompanyId(@Param("item") String item, @Param("companyId") Long companyId);

    /**
     * <p>
     * Count jca item dto by function code.
     * </p>
     *
     * @param functionCode
     *            type {@link String}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaItemDtoByFunctionCode(@Param("functionCode") String functionCode);
}

/*******************************************************************************
 * Class        JcaItemService
 * Created date 2020/12/07
 * Lasted date  2020/12/07
 * Author       MinhNV
 * Change log   2020/12/07 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaItemService.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface JcaItemService extends DbRepositoryService<JcaItem, Long> {

    /** The Constant TABLE_ALIAS_JCA_ITEM. */
    public static final String TABLE_ALIAS_JCA_ITEM = "item";

    /**
     * <p>
     * total item by condition
     * </p>
     * .
     *
     * @param itemSearchDto
     *            type {@link JcaItemSearchDto}
     * @return int
     * @author MinhNV
     */
    public int countJcaItemDtoByCondition(JcaItemSearchDto itemSearchDto);

    /**
     * <p>
     * get all item by condition
     * </p>
     * .
     *
     * @param itemSearchDto
     *            type {@link JcaItemSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List<JcaItemDto>
     * @author MinhNV
     */
    public List<JcaItemDto> getJcaItemDtoByCondition(JcaItemSearchDto itemSearchDto, Pageable pagable);

    /**
     * <p>
     * get item by id
     * </p>
     * .
     *
     * @param id
     *            id of item
     * @return JcaItem
     * @author MinhNV
     */
    public JcaItem getItemById(Long id);

    /**
     * <p>
     * Save JcaItem with create, update
     * </p>
     * .
     *
     * @param jcaItem
     *            type {@link JcaItem}
     * @return JcaItem
     * @author MinhNV
     */
    public JcaItem saveJcaItem(JcaItem jcaItem);

    /**
     * <p>
     * Save JcaItem with JcaItemDto
     * </p>
     * .
     *
     * @param jcaItemDto
     *            type {@link JcaItemDto}
     * @return JcaItem
     * @author MinhNV
     */
    public JcaItem saveJcaItemDto(JcaItemDto jcaItemDto);

    /**
     * <p>
     * Get item information detail by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaItemDto
     * @author MinhNV
     */
    public JcaItemDto getJcaItemDtoById(Long id);

    /**
     * Check exits item by item code and company id.
     *
     * @param itemCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return true, if successful
     * @author taitt
     */
    Boolean checkExitsItemByItemCodeAndCompanyId(String itemCode, Long companyId);

    /**
     * Gets the item id by code and company id.
     *
     * @param functionCode
     *            the function code type Long
     * @param companyId
     *            the company id type Long
     * @return the item id by code and company id
     * @author tantm
     */
    public Long getItemIdByItemCodeAndCompanyId(String functionCode, Long companyId);
    
    /**
     * <p>
     * Deleted jca item by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deletedJcaItemById(Long id);

    /**
     * checkRoleItem.
     *
     * @param itemCode
     *            type {@link String}
     * @return true, if successful
     * @author taitt
     */
    boolean checkRoleItem(String itemCode);

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
    public int countJcaItemDtoByFunctionCode(String functionCode);
}

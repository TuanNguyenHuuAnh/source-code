/*******************************************************************************
 * Class        ItemService
 * Created date 2020/12/07
 * Lasted date  2020/12/07
 * Author       MinhNV
 * Change log   2020/12/07 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaItemSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.ItemAddReq;
import vn.com.unit.ep2p.dto.req.ItemUpdateReq;
import vn.com.unit.ep2p.dto.res.ItemInfoRes;

/**
 * PositionService.
 *
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
public interface ItemService extends BaseRestService<ObjectDataRes<JcaItemDto>, JcaItemDto> {

    /**
     * <p>
     * count item by condition
     * </p>
     * .
     *
     * @param jcaItemSearchDto
     *            type {@link JcaItemSearchDto}
     * @return int
     * @author MinhNV
     */
    int countJcaItemDtoByCondition(JcaItemSearchDto jcaItemSearchDto);

    /**
     * get list item by condition with pagination.
     *
     * @param jcaItemSearchDto
     *            type {@link JcaItemSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List<JcaItemDto>
     * @author MinhNV
     */
    List<JcaItemDto> getJcaItemDtoByCondition(JcaItemSearchDto jcaItemSearchDto, Pageable pagable);

    /**
     * <p>
     * create item
     * </p>
     * .
     *
     * @param reqItemAddDto
     *            type {@link ItemAddReq}
     * @return ItemInfoRes
     * @throws DetailException
     *             the detail exception
     * @author MinhNV
     */
    ItemInfoRes create(ItemAddReq reqItemAddDto) throws DetailException;

    /**
     * <p>
     * update item
     * </p>
     * .
     *
     * @param reqItemUpdateDto
     *            type {@link ItemUpdateReq}
     * @throws DetailException
     *             the detail exception
     * @author MinhNV
     */
    void update(ItemUpdateReq reqItemUpdateDto) throws DetailException;

   
    /**
     * <p>
     * get info an item
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return ItemInfoRes
     * @throws DetailException
     *             the detail exception
     * @author MinhNV
     */
    ItemInfoRes getItemInfoById(Long id) throws DetailException;

    /**
     * <p>
     * Get list enum search.
     * </p>
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author SonND
     */
    List<EnumsParamSearchRes> getListEnumSearch();

}

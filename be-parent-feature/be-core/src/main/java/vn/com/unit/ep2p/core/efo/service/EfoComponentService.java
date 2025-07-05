/*******************************************************************************
 * Class        ：EfoComponentService
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * EfoComponentService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoComponentService {

    /**
     * countComponentDtoByCondition.
     *
     * @param efoComponentSearchDto
     *            type {@link EfoComponentSearchDto}
     * @return {@link int}
     * @author taitt
     */
    int countComponentDtoByCondition(EfoComponentSearchDto efoComponentSearchDto);

    /**
     * getComponentDtoByCondition.
     *
     * @param efoComponentSearchDto
     *            type {@link EfoComponentSearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return {@link List<EfoComponentDto>}
     * @author taitt
     */
    List<EfoComponentDto> getComponentDtoByCondition(EfoComponentSearchDto efoComponentSearchDto, Pageable pagable);

    /**
     * getComponentById.
     *
     * @param id
     *            type {@link Long}
     * @return {@link EfoComponent}
     * @author taitt
     */
    EfoComponent getComponentById(Long id);

    /**
     * saveEfoComponent.
     *
     * @param efoComponent
     *            type {@link EfoComponent}
     * @return {@link EfoComponent}
     * @author taitt
     */
    EfoComponent saveEfoComponent(EfoComponent efoComponent);

    /**
     * saveEfoComponentDto.
     *
     * @param efoComponentDto
     *            type {@link EfoComponentDto}
     * @return {@link EfoComponent}
     * @author taitt
     */
    EfoComponent saveEfoComponentDto(EfoComponentDto efoComponentDto);

    /**
     * getEfoComponentDtoById.
     *
     * @param id
     *            type {@link Long}
     * @return {@link EfoComponentDto}
     * @author taitt
     */
    EfoComponentDto getEfoComponentDtoById(Long id);

    /**
     * saveEfoComponentList.
     *
     * @param componentList
     *            type {@link List<EfoComponent>}
     * @author taitt
     */
    void saveEfoComponentList(List<EfoComponent> componentList,String formName) throws DetailException;

}

/*******************************************************************************
 * Class        EfoCategoryService
 * Created date 2019/12/17
 * Lasted date  2019/12/17
 * Author       TrongNV
 * Change log   2019/12/17 01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;

/**
 * EfoCategoryService.
 *
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
public interface EfoCategoryService {

    /**
     * <p>
     * Total account by condition
     * </p>
     * .
     *
     * @param efoCategorySearchDto
     *            type {@link EfoCategorySearchDto}
     * @return int
     * @author TrongNV
     */
    public int countCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto);
    
    /**
     * <p>
     * Get all category by condition
     * </p>
     * .
     *
     * @param efoCategorySearchDto
     *            type {@link EfoCategorySearchDto}
     * @param pagable
     *            type {@link Pageable}
     * @return List<EfoCategoryDto>
     * @author TrongNV
     */
    public List<EfoCategoryDto> getCategoryDtoByCondition(EfoCategorySearchDto efoCategorySearchDto, Pageable pagable);
    
    /**
     * <p>
     * Get category by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return CategoryDto
     * @author TrongNV
     */
    public EfoCategory getCategoryById(Long id);
    
    /**
     * <p>
     * Save EfoCategory with create, update
     * </p>
     * .
     *
     * @param efoCategory
     *            type {@link EfoCategory}
     * @return EfoCategory
     * @author TrongNV
     */
    public EfoCategory saveEfoCategory(EfoCategory efoCategory);
    
    /**
     * <p>
     * Save EfoCategory with EfoCategoryDto
     * </p>
     * .
     *
     * @param efoCategoryDto
     *            type {@link EfoCategoryDto}
     * @return JcaAccount
     * @author TrongNV
     */
    public EfoCategory saveEfoCategoryDto(EfoCategoryDto efoCategoryDto);
    
    /**
     * <p>
     * Get category information detail by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaAccountDto
     * @author SonND
     */
    public EfoCategoryDto getEfoCategoryDtoById(Long id);
    
    /**
     * <p>
     * Deleted efo category by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deletedEfoCategoryById(Long id);
    

}

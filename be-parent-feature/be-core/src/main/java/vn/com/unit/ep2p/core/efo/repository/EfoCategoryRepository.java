/*******************************************************************************
 * Class        :EfoCategoryRepository
 * Created date :2020/12/17
 * Lasted date  :2020/12/17
 * Author       :TrongNV
 * Change log   :2020/12/17:01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
import vn.com.unit.ep2p.core.efo.dto.EfoCategorySearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoCategory;


/**
 * 
 * EfoCategoryRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
public interface EfoCategoryRepository extends DbRepository<EfoCategory, Long> {
    
    /**
     * 
     * <p> total category by condition </p>
     * 
     * @param efoCategorySearchDto
     * @return int 
     * @author TrongNV
     */
    int countCategoryDtoByCondition(@Param("efoCategorySearchDto") EfoCategorySearchDto efoCategorySearchDto);
	
    /**
     * 
     * <p> get list categoryDto by condition </p>
     * 
     * @param pageable
     * @param efoCategorySearchDto 
     * @return List<EfoCategoryDto>
     * @author TrongNV
     */
    Page<EfoCategoryDto> getCategoryDtoByCondition(@Param("efoCategorySearchDto") EfoCategorySearchDto efoCategorySearchDto,            
            Pageable pageable);
    
    
    /**
     * 
     * <p> get category information detail by id </p>
     * 
     * @param id
     * @return EfoCategoryDto
     * @author TrongNV
     */
    EfoCategoryDto getEfoCategoryDtoById(@Param("id") Long id);

}
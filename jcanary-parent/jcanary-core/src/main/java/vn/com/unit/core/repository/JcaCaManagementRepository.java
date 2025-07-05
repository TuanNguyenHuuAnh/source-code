/*******************************************************************************
 * Class        ：JcaCaManagementRepository
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaCaManagementRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
public interface JcaCaManagementRepository extends DbRepository<JcaCaManagement, Long> {

    /**
     * 
     * <p>
     * count all certificate authority by condition
     * </p>
     * 
     * @param keySearch
     * @param JcaCaManagementSearchDto
     * @return int
     * @author MinhNV
     */
    int countCaManagementDtoByCondition(@Param("searchDto") JcaCaManagementSearchDto searchDto);

    /**
     * 
     * <p>
     * get list JcaCaManagementDto by condition
     * </p>
     * 
     * @param JcaCaManagementSearchDto
     * @param offset
     * @param sizeOfPage
     * @param isPaging
     * @return List of JcaCaManagementDto
     * @author MinhNV
     */
    Page<JcaCaManagementDto> getCaManagementDtoByCondition(@Param("searchDto") JcaCaManagementSearchDto searchDto,Pageable pageable);

    /**
     * 
     * <p>
     * get JcaCaManagementDto by id
     * </p>
     * 
     * @param id
     * @return JcaCaManagementDto
     * @author MinhNV
     */
    JcaCaManagementDto getJcaCaManagementDtoById(@Param("id") Long id);
}

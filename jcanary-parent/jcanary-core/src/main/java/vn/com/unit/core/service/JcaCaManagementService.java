/*******************************************************************************
 * Class        ：JcaCaManagementService
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaCaManagementService
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
public interface JcaCaManagementService extends DbRepositoryService<JcaCaManagement, Long>{

    static final String TABLE_ALIAS_JCA_M_ACCOUNT_CA = "ca";
    
    /**
     * 
     * <p>
     * Total certificate authority by condition
     * </p>
     * 
     * @param keySearch
     * @param JcaCaManagementSearchDto
     * @return int
     * @author minhnv
     */
    public int countCaManagementDtoByCondition(JcaCaManagementSearchDto jcaCaManagementSearchDto);

    /**
     * 
     * <p>
     * Get all certificate authorities by condition
     * </p>
     * 
     * @param keySearch
     * @param JcaCaManagementSearchDto
     * @param pageSize
     * @param startIndex
     * @return List<JcaCaManagementDto>
     * @author minhnv
     */
    public List<JcaCaManagementDto> getCaManagementDtoByCondition(JcaCaManagementSearchDto jcaCaManagementSearchDto,
            Pageable pageable);

    /**
     * <p>
     * Get certificate authority by id
     * </p>
     * 
     * @param id
     * @return JcaCaManagement
     * @author minhnv
     */
    public JcaCaManagement getJcaCaManagementById(Long id);

    /**
     * 
     * <p>
     * Save JcaCaManagement with create, update
     * </p>
     * 
     * @param JcaCaManagement
     * @return JcaCaManagement
     * @author minhnv
     */
    public JcaCaManagement saveJcaCaManagement(JcaCaManagement jcaCaManagement);

    /**
     * 
     * <p>
     * Save JcaCaManagement with JcaCaManagementDto
     * </p>
     * 
     * @param JcaCaManagementDto
     * @return JcaCaManagement
     * @author minhnv
     */
    public JcaCaManagement saveJcaCaManagementDto(JcaCaManagementDto jcaCaManagementDto);

    /**
     * 
     * <p>
     * Get certificate authority information detail by id
     * </p>
     * 
     * @param id
     * @return JcaCaManagementDto
     * @author minhnv
     */
    public JcaCaManagementDto getJcaCaManagementDtoById(Long id);
}

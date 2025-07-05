/*******************************************************************************
 * Class        ：JcaGroupSystemConfigRepository
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.dto.JcaGroupSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaGroupSystemConfig;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaGroupSystemConfigRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaGroupSystemConfigRepository extends DbRepository<JcaGroupSystemConfig, Long>{

    /**
     * countGroupSystemConfigDtoByCondition.
     *
     * @param jcaGroupSystemConfigSearchDto
     *            the jca group system config search dto
     * @return the int
     * @author ngannh
     */
    public int countGroupSystemConfigDtoByCondition(@Param("jcaGroupSystemConfigSearchDto") JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto);

    /**
     * getGroupSystemConfigDtoByCondition.
     *
     * @param jcaGroupSystemConfigSearchDto
     *            the jca group system config search dto
     * @param pageable
     *            the pageable
     * @return the group system config dto by condition
     * @author ngannh
     */
    public Page<JcaGroupSystemConfigDto> getGroupSystemConfigDtoByCondition(@Param("jcaGroupSystemConfigSearchDto") JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto, Pageable pageable);

    /**
     * getJcaGroupSystemConfigDtoById.
     *
     * @param id
     *            the id
     * @return the jca group system config dto by id
     * @author ngannh
     */
    public JcaGroupSystemConfigDto getJcaGroupSystemConfigDtoById(@Param("id")  Long id);
    
    @Modifying
    void mergeSystemSetting(@Param("companyId") Long companyId);

}

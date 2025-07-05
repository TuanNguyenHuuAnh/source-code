/*******************************************************************************
 * Class        ：JcaSystemConfigRepository
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.dto.JcaSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaSystemConfigRepository.
 *
 * @author ngannh
 * @version 01-00
 * @since 01-00
 */
public interface JcaSystemConfigRepository extends DbRepository<JcaSystemConfig, String>{
    
    /**
     * countSystemConfigDtoByCondition.
     *
     * @author ngannh
     * @param jcaSystemConfigSearchDto            the jca system config search dto
     * @return the int
     */
    public int countSystemConfigDtoByCondition(@Param("jcaSystemConfigSearchDto") JcaSystemConfigSearchDto jcaSystemConfigSearchDto);

    /**
     * getSystemConfigDtoByCondition.
     *
     * @author ngannh
     * @param jcaSystemConfigSearchDto            the jca system config search dto
     * @param pageable            the pageable
     * @return the system config dto by condition
     */
    public Page<JcaSystemConfigDto> getSystemConfigDtoByCondition(@Param("jcaSystemConfigSearchDto") JcaSystemConfigSearchDto jcaSystemConfigSearchDto,Pageable pageable);



    /**
     * getJcaSystemConfigDtoByKey.
     *
     * @author ngannh
     * @param settingKey            the setting key
     * @return the jca system config dto by key
     */
    public JcaSystemConfigDto getJcaSystemConfigDtoByKey(@Param("settingKey") String settingKey);

    /**
     * <p>
     * Get value by key.
     * </p>
     *
     * @author tantm
     * @param settingKey            type {@link String}
     * @param companyId            type {@link Long}
     * @return {@link String}
     */
    public String getValueByKey(@Param("settingKey") String settingKey, @Param("companyId") Long companyId);
    
    /**
     * <p>Get value by key degault.</p>
     *
     * @author Tan Tai
     * @param settingKey type {@link String}
     * @return {@link String}
     */
    public String getValueByKeyDefault(@Param("settingKey") String settingKey);

    /**
     * <p>
     * Get list jca system config dto by group code.
     * </p>
     *
     * @author tantm
     * @param groupCode            type {@link String}
     * @param companyId            type {@link Long}
     * @return {@link List<JcaSystemConfigDto>}
     */
    public List<JcaSystemConfigDto> getListJcaSystemConfigDtoByGroupCode(@Param("groupCode") String groupCode, @Param("companyId") Long companyId);

    /**
     * <p>Merge system setting.</p>
     *
     * @author Tan Tai
     * @param companyId type {@link Long}
     */
    @Modifying
    void mergeSystemSetting(@Param("companyId") Long companyId);
    
    /**
     * <p>Find by company id.</p>
     *
     * @author Tan Tai
     * @param companyId type {@link Long}
     * @return {@link List<JcaSystemConfig>}
     */
    List<JcaSystemConfig>  findByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * <p>Get jca system config by company and key.</p>
     *
     * @author Tan Tai
     * @param companyId type {@link Long}
     * @param settingKey type {@link String}
     * @return {@link JcaSystemConfig}
     */
    public JcaSystemConfig getJcaSystemConfigByCompanyAndKey(@Param("companyId") Long companyId,@Param("settingKey") String settingKey);
    
    
}

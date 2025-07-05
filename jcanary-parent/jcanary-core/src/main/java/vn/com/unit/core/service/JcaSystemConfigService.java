/*******************************************************************************
 * Class        ：JcaSystemConfigService
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.dto.JcaSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaSystemConfig;

/**
 * <p>
 * JcaSystemConfigService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaSystemConfigService {
    
    /** The Constant TABLE_ALIAS_JCA_SYSTEM_SETTING. */
    static final String TABLE_ALIAS_JCA_SYSTEM_SETTING = "SETTING";

    /**
     * <p>
     * Count system config dto by condition.
     * </p>
     *
     * @param jcaSystemConfigSearchDto
     *            type {@link JcaSystemConfigSearchDto}
     * @return {@link int}
     * @author tantm
     */
    int countSystemConfigDtoByCondition(JcaSystemConfigSearchDto jcaSystemConfigSearchDto);

    /**
     * <p>
     * Get system config dto by condition.
     * </p>
     *
     * @param jcaSystemConfigSearchDto
     *            type {@link JcaSystemConfigSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaSystemConfigDto>}
     * @author tantm
     */
    List<JcaSystemConfigDto> getSystemConfigDtoByCondition(JcaSystemConfigSearchDto jcaSystemConfigSearchDto,Pageable pageable);

    /**
     * <p>
     * Get system config by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link JcaSystemConfig}
     * @author tantm
     */
    JcaSystemConfig getSystemConfigById(Long id);

    /**
     * <p>
     * Save jca system config.
     * </p>
     *
     * @param jcaSystemConfig
     *            type {@link JcaSystemConfig}
     * @return {@link JcaSystemConfig}
     * @author tantm
     */
    JcaSystemConfig saveJcaSystemConfig(JcaSystemConfig jcaSystemConfig);

    /**
     * <p>
     * Save jca system config dto.
     * </p>
     *
     * @param jcaSystemConfigDto
     *            type {@link JcaSystemConfigDto}
     * @return {@link JcaSystemConfig}
     * @author tantm
     */
    JcaSystemConfig saveJcaSystemConfigDto(JcaSystemConfigDto jcaSystemConfigDto);



    /**
     * <p>
     * Update.
     * </p>
     *
     * @param jcaSystemConfig
     *            type {@link JcaSystemConfig}
     * @author tantm
     */
    void update(JcaSystemConfig jcaSystemConfig);

    /**
     * <p>
     * Get jca system config dto by key.
     * </p>
     *
     * @param settingKey
     *            type {@link String}
     * @return {@link JcaSystemConfigDto}
     * @author tantm
     */
    JcaSystemConfigDto getJcaSystemConfigDtoByKey(String settingKey);
    
    /**
     * <p>
     * Get value by key.
     * </p>
     *
     * @param settingKey
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link String}
     * @author tantm
     */
    String getValueByKey(String settingKey, Long companyId);
    
    /**
     * <p>
     * Get list jca system config dto by group code.
     * </p>
     *
     * @param groupCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaSystemConfigDto>}
     * @author tantm
     */
    List<JcaSystemConfigDto> getListJcaSystemConfigDtoByGroupCode(String groupCode, Long companyId);

    /**
     * mergeSystemSetting
     * @param companyId
     * @author taitt
     */
    void mergeSystemSetting(Long companyId);

    /**
     * findAll
     * @return
     * @author taitt
     */
    List<JcaSystemConfig> findAll();

    /**
     * findAllByCompanyId
     * @param companyId
     * @return
     * @author taitt
     */
    List<JcaSystemConfig> findAllByCompanyId(Long companyId);

    /**
     * findJcaSystemConfigByCompanyAndKey
     * @param companyId
     * @param settingKey
     * @return
     * @author ngannh
     */
    JcaSystemConfig getJcaSystemConfigByCompanyAndKey(Long companyId, String settingKey);

	/**
	 * getValueByKeyDefault
	 * @param settingKey
	 * @return
	 * @author Tan Tai
	 */
	String getValueByKeyDefault(String settingKey);
}

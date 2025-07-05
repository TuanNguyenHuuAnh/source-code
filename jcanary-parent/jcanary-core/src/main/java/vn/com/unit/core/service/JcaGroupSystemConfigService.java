/*******************************************************************************
 * Class        ：jcaGroupSystemConfig
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.dto.JcaGroupSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaGroupSystemConfig;

/**
 * jcaGroupSystemConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaGroupSystemConfigService {
    static final String TABLE_ALIAS_JCA_GROUP_SYSTEM_SETTING = "GROUP_SETTING";

    /**
     * countGroupSystemConfigDtoByCondition
     * @param keySearch
     * @param jcaGroupSystemConfigSearchDto
     * @return
     * @author ngannh
     */
    int countGroupSystemConfigDtoByCondition(JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto);

    /**
     * getGroupSystemConfigDtoByCondition
     * @param keySearch
     * @param jcaGroupSystemConfigSearchDto
     * @param startIndex
     * @param pageSize
     * @param isPaging
     * @return
     * @author ngannh
     */
    List<JcaGroupSystemConfigDto> getGroupSystemConfigDtoByCondition(JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto,Pageable pageable);

    /**
     * getGroupSystemConfigById
     * @param id
     * @return
     * @author ngannh
     */
    JcaGroupSystemConfig getGroupSystemConfigById(Long id);

    /**
     * saveJcaGroupSystemConfig
     * @param jcaGroupSystemConfig
     * @return
     * @author ngannh
     */
    JcaGroupSystemConfig saveJcaGroupSystemConfig(JcaGroupSystemConfig jcaGroupSystemConfig);

    /**
     * saveJcaGroupSystemConfigDto
     * @param jcaGroupSystemConfigDto
     * @return
     * @author ngannh
     */
    JcaGroupSystemConfig saveJcaGroupSystemConfigDto(JcaGroupSystemConfigDto jcaGroupSystemConfigDto);

    /**
     * getJcaGroupSystemConfigDtoById
     * @param id
     * @return
     * @author ngannh
     */
    JcaGroupSystemConfigDto getJcaGroupSystemConfigDtoById(Long id);

    /**
     * update
     * @param jcaGroupSystemConfig
     * @author ngannh
     */
    void update(JcaGroupSystemConfig jcaGroupSystemConfig);

    /**
     * mergeSystemSetting
     * @param companyId
     * @author taitt
     */
    void mergeSystemSetting(Long companyId);

}

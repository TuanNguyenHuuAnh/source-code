/*******************************************************************************
 * Class        ：JcaSystemConfigServiceImpl
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.core.dto.JcaSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.entity.JcaSystemConfigHis;
import vn.com.unit.core.repository.JcaSystemConfigRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;

/**
 * JcaSystemConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaSystemConfigServiceImpl implements JcaSystemConfigService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JcaSystemConfigRepository jcaSystemConfigRepository;
    

    
    @Override
    public int countSystemConfigDtoByCondition(JcaSystemConfigSearchDto jcaSystemConfigSearchDto) {
        return jcaSystemConfigRepository.countSystemConfigDtoByCondition(jcaSystemConfigSearchDto);
    }

    @Override
    public List<JcaSystemConfigDto> getSystemConfigDtoByCondition(JcaSystemConfigSearchDto jcaSystemConfigSearchDto, Pageable pageable) {
        return jcaSystemConfigRepository.getSystemConfigDtoByCondition(jcaSystemConfigSearchDto, pageable).getContent();
    }

    @Override
    public JcaSystemConfig getSystemConfigById(Long id) {
//        return jcaSystemConfigRepository.findOne(id);
        return null;
    }
    @Override
    public JcaSystemConfig getJcaSystemConfigByCompanyAndKey(Long companyId,String settingKey) {
        return jcaSystemConfigRepository.getJcaSystemConfigByCompanyAndKey(companyId,settingKey);
    }
    @Override
    @Transactional
    public JcaSystemConfig saveJcaSystemConfig(JcaSystemConfig jcaSystemConfig) {
        JcaSystemConfigHis jcaSystemConfigHis = objectMapper.convertValue(jcaSystemConfig, JcaSystemConfigHis.class);

        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
//        Long id = jcaSystemConfig.getId();
        JcaSystemConfig oldJcaSystemConfig = this.getJcaSystemConfigByCompanyAndKey(jcaSystemConfig.getCompanyId(), jcaSystemConfig.getSettingKey());

        if (null != oldJcaSystemConfig) {
                jcaSystemConfig.setCreatedDate(oldJcaSystemConfig.getCreatedDate());
                jcaSystemConfig.setCreatedId(oldJcaSystemConfig.getCreatedId());
                jcaSystemConfig.setUpdatedDate(sysDate);
                jcaSystemConfig.setUpdatedId(userId);
                jcaSystemConfigRepository.update(jcaSystemConfig);
        } else {
            jcaSystemConfig.setCreatedDate(sysDate);
            jcaSystemConfig.setCreatedId(userId);
            jcaSystemConfig.setUpdatedDate(sysDate);
            jcaSystemConfig.setUpdatedId(userId);
            jcaSystemConfigRepository.create(jcaSystemConfig);
        }
        return jcaSystemConfig;
    }

    @Override
    @Transactional
    public JcaSystemConfig saveJcaSystemConfigDto(JcaSystemConfigDto jcaSystemConfigDto) {
        JcaSystemConfig jcaSystemConfig = objectMapper.convertValue(jcaSystemConfigDto, JcaSystemConfig.class);

        return this.saveJcaSystemConfig(jcaSystemConfig);
    }

    @Override
    @Transactional
    public void update(JcaSystemConfig jcaSystemConfig) {
        jcaSystemConfigRepository.update(jcaSystemConfig);
    }

    @Override
    public JcaSystemConfigDto getJcaSystemConfigDtoByKey(String settingKey) {

        return jcaSystemConfigRepository.getJcaSystemConfigDtoByKey(settingKey);
    }

    @Override
    public String getValueByKey(String settingKey, Long companyId) {
        return jcaSystemConfigRepository.getValueByKey(settingKey, companyId);
    }
    
    @Override
    public String getValueByKeyDefault(String settingKey) {
        return jcaSystemConfigRepository.getValueByKeyDefault(settingKey);
    }

    @Override
    public List<JcaSystemConfigDto> getListJcaSystemConfigDtoByGroupCode(String groupCode, Long companyId) {
        return jcaSystemConfigRepository.getListJcaSystemConfigDtoByGroupCode(groupCode, companyId);
    }
    
    @Override
    @Transactional
    public void mergeSystemSetting(Long companyId) {
        if(companyId != null) {
            jcaSystemConfigRepository.mergeSystemSetting(companyId);
//            jcaGroupSystemConfigService.mergeSystemSetting(companyId);
        }
    }
    
    @Override
    public List<JcaSystemConfig> findAll() {
        List<String> lstSort = new ArrayList<String>();
        lstSort.add("COMPANY_ID");
        List<JcaSystemConfig> systemSettings = (List<JcaSystemConfig>) jcaSystemConfigRepository.findAll(new Sort(Direction.ASC, lstSort));
        return systemSettings;
    }
    
    @Override
    public List<JcaSystemConfig> findAllByCompanyId(Long companyId) {
        List<JcaSystemConfig> systemSettings = (List<JcaSystemConfig>) jcaSystemConfigRepository.findByCompanyId(companyId);
        return systemSettings;
    }
}

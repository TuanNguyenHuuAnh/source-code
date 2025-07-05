/*******************************************************************************
 * Class        ：JcaGroupSystemConfigServiceImpl
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaGroupSystemConfigDto;
import vn.com.unit.core.dto.JcaGroupSystemConfigSearchDto;
import vn.com.unit.core.entity.JcaGroupSystemConfig;
import vn.com.unit.core.repository.JcaGroupSystemConfigRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaGroupSystemConfigService;

/**
 * JcaGroupSystemConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaGroupSystemConfigServiceImpl implements JcaGroupSystemConfigService {

    @Autowired
    JcaGroupSystemConfigRepository jcaGroupSystemConfigRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public int countGroupSystemConfigDtoByCondition( JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto) {
        return jcaGroupSystemConfigRepository.countGroupSystemConfigDtoByCondition( jcaGroupSystemConfigSearchDto);
    }

    @Override
    public List<JcaGroupSystemConfigDto> getGroupSystemConfigDtoByCondition(JcaGroupSystemConfigSearchDto jcaGroupSystemConfigSearchDto,
            Pageable pageable) {
        return jcaGroupSystemConfigRepository.getGroupSystemConfigDtoByCondition(jcaGroupSystemConfigSearchDto, pageable).getContent();
    }

    @Override
    public JcaGroupSystemConfig getGroupSystemConfigById(Long id) {
        return jcaGroupSystemConfigRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaGroupSystemConfig saveJcaGroupSystemConfig(JcaGroupSystemConfig jcaGroupSystemConfig) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaGroupSystemConfig.getId();
        if (null != id) {
            JcaGroupSystemConfig oldJcaGroupSystemConfig = jcaGroupSystemConfigRepository.findOne(id);
            if (null != oldJcaGroupSystemConfig) {
                jcaGroupSystemConfig.setCreatedDate(oldJcaGroupSystemConfig.getCreatedDate());
                jcaGroupSystemConfig.setCreatedId(oldJcaGroupSystemConfig.getCreatedId());
                jcaGroupSystemConfig.setUpdatedDate(sysDate);
                jcaGroupSystemConfig.setUpdatedId(userId);
                jcaGroupSystemConfigRepository.update(jcaGroupSystemConfig);
            }
        } else {
            jcaGroupSystemConfig.setCreatedDate(sysDate);
            jcaGroupSystemConfig.setCreatedId(userId);
            jcaGroupSystemConfig.setUpdatedDate(sysDate);
            jcaGroupSystemConfig.setUpdatedId(userId);
            jcaGroupSystemConfigRepository.create(jcaGroupSystemConfig);
        }
        return jcaGroupSystemConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaGroupSystemConfig saveJcaGroupSystemConfigDto(JcaGroupSystemConfigDto jcaGroupSystemConfigDto) {
        JcaGroupSystemConfig jcaGroupSystemConfig = objectMapper.convertValue(jcaGroupSystemConfigDto, JcaGroupSystemConfig.class);

        return this.saveJcaGroupSystemConfig(jcaGroupSystemConfig);
    }

    @Override
    public JcaGroupSystemConfigDto getJcaGroupSystemConfigDtoById(Long id) {
        return jcaGroupSystemConfigRepository.getJcaGroupSystemConfigDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JcaGroupSystemConfig jcaGroupSystemConfig) {
        jcaGroupSystemConfigRepository.update(jcaGroupSystemConfig);

    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mergeSystemSetting(Long companyId) {
        if(companyId != null) {
            jcaGroupSystemConfigRepository.mergeSystemSetting(companyId);
        }
    }

}

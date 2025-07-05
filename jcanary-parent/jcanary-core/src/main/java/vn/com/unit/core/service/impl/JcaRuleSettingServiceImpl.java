/*******************************************************************************
 * Class        ：JcaRuleSettingServiceImpl
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：KhoaNA
 * Change log   ：2020/11/13：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.dto.JcaRuleSettingDto;
import vn.com.unit.core.entity.JcaRuleSetting;
import vn.com.unit.core.repository.JcaRuleSettingRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRuleSettingService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRuleSettingServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRuleSettingServiceImpl implements JcaRuleSettingService {

    @Autowired
    private JcaRuleSettingRepository jcaRuleSettingRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public JcaRuleSetting saveJcaRuleSetting(JcaRuleSetting entity) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();

        JcaRuleSetting old = jcaRuleSettingRepository.getJcaRuleSettingByPK(entity.getBusinessId(), entity.getOrgId(),
                entity.getPositionId());
        if (null == old) {
            entity.setCreatedId(userId);
            entity.setCreatedDate(sysDate);
            entity.setUpdatedId(userId);
            entity.setUpdatedDate(sysDate);
            jcaRuleSettingRepository.create(entity);
        } else {
            entity.setCreatedId(old.getCreatedId());
            entity.setCreatedDate(old.getCreatedDate());
            entity.setUpdatedId(userId);
            entity.setUpdatedDate(sysDate);
            jcaRuleSettingRepository.update(entity);
        }
        return entity;
    }

    @Override
    public DbRepository<JcaRuleSetting, Long> initRepo() {
        return jcaRuleSettingRepository;
    }

    @Override
    public List<JcaRuleSettingDto> getListJcaRuleSettingDtoByBusinessId(long businessId) {
        return jcaRuleSettingRepository.getListJcaRuleSettingDtoByBusinessId(businessId);
    }

    @Override
    public void deleteJcaRuleSettingByPK(Long businessId, Long orgId, Long positionId) {
        jcaRuleSettingRepository.deleteJcaRuleSettingByPK(businessId, orgId, positionId);
    }

    @Override
    public void saveListJcaRuleSettingDto(List<JcaRuleSettingDto> listRuleSettingDto) {

        for (JcaRuleSettingDto dto : listRuleSettingDto) {
            JcaRuleSetting entity = objectMapper.convertValue(dto, JcaRuleSetting.class);
            this.saveJcaRuleSetting(entity);
        }

    }

    @Override
    public String validateOrgList(List<JcaRuleSettingDto> listRuleSettingDto, Locale locale) {
        if (listRuleSettingDto != null) {
            // check org
            for (JcaRuleSettingDto dto : listRuleSettingDto) {
                if (dto.getOrgId() == null) {
                    return messageSource.getMessage("message.null.org", null, locale);
                }
                if (dto.getPositionId() == null) {
                    return messageSource.getMessage("message.null.position", null, locale);
                }
            }
            // check dup
            for (int i = 0; i < listRuleSettingDto.size(); i++) {
                for (int j = i + 1; j < listRuleSettingDto.size(); j++) {
                    if (listRuleSettingDto.get(i).getOrgId().equals(listRuleSettingDto.get(j).getOrgId())
                            && listRuleSettingDto.get(i).getPositionId().equals(listRuleSettingDto.get(j).getPositionId())) {
                        return messageSource.getMessage("message.duplicate.org", null, locale);
                    }
                }
            }
        }
        return null;
    }
}

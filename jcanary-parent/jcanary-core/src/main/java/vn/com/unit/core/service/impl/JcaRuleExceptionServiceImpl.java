/*******************************************************************************
 * Class        ：JcaRuleExceptionServiceImpl
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
import vn.com.unit.core.dto.JcaRuleExceptionDto;
import vn.com.unit.core.entity.JcaRuleException;
import vn.com.unit.core.repository.JcaRuleExceptionRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRuleExceptionService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRuleExceptionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRuleExceptionServiceImpl implements JcaRuleExceptionService {

    @Autowired
    private JcaRuleExceptionRepository jcaRuleExceptionRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public JcaRuleException saveJcaRuleException(JcaRuleException entity) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();

        JcaRuleException old = jcaRuleExceptionRepository.getJcaRuleExceptionByPK(entity.getBusinessId(), entity.getOrgId(),
                entity.getAccountId());
        if (null == old) {
            entity.setCreatedId(userId);
            entity.setCreatedDate(sysDate);
            entity.setUpdatedId(userId);
            entity.setUpdatedDate(sysDate);
            jcaRuleExceptionRepository.create(entity);
        } else {
            entity.setCreatedId(old.getCreatedId());
            entity.setCreatedDate(old.getCreatedDate());
            entity.setUpdatedId(userId);
            entity.setUpdatedDate(sysDate);
            jcaRuleExceptionRepository.update(entity);
        }
        return entity;
    }

    @Override
    public DbRepository<JcaRuleException, Long> initRepo() {
        return jcaRuleExceptionRepository;
    }

    @Override
    public List<JcaRuleExceptionDto> getListJcaRuleExceptionDtoByBusinessId(long businessId) {
        return jcaRuleExceptionRepository.getListJcaRuleExceptionDtoByBusinessId(businessId);
    }

    @Override
    public void deleteJcaRuleExceptionByPK(Long businessId, Long orgId, Long positionId) {
        jcaRuleExceptionRepository.deleteJcaRuleExceptionByPK(businessId, orgId, positionId);
    }

    @Override
    public void saveListJcaRuleExceptionDto(List<JcaRuleExceptionDto> listRuleExceptionDto) {

        for (JcaRuleExceptionDto dto : listRuleExceptionDto) {
            JcaRuleException entity = objectMapper.convertValue(dto, JcaRuleException.class);
            this.saveJcaRuleException(entity);
        }

    }

    @Override
    public String validateOrgList(List<JcaRuleExceptionDto> listRuleExceptionDto, Locale locale) {
        if (listRuleExceptionDto != null) {
            // check org
            for (JcaRuleExceptionDto dto : listRuleExceptionDto) {
                if (dto.getOrgId() == null) {
                    return messageSource.getMessage("message.null.org", null, locale);
                }
                if (dto.getAccountId() == null) {
                    return messageSource.getMessage("message.null.account", null, locale);
                }
            }
            // check dup
            for (int i = 0; i < listRuleExceptionDto.size(); i++) {
                for (int j = i + 1; j < listRuleExceptionDto.size(); j++) {
                    if (listRuleExceptionDto.get(i).getOrgId().equals(listRuleExceptionDto.get(j).getOrgId())
                            && listRuleExceptionDto.get(i).getAccountId().equals(listRuleExceptionDto.get(j).getAccountId())) {
                        return messageSource.getMessage("message.duplicate.org", null, locale);
                    }
                }
            }
        }
        return null;
    }
}

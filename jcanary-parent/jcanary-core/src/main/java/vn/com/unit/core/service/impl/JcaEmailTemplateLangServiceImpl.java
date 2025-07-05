/*******************************************************************************
 * Class        :JcaEmailTemplateLangServiceImpl
 * Created date :2021/01/15
 * Lasted date  :2021/01/15
 * Author       :SonND
 * Change log   :2021/01/15 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaEmailTemplateLangDto;
import vn.com.unit.core.entity.JcaEmailTemplateLang;
import vn.com.unit.core.repository.JcaEmailTemplateLangRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailTemplateLangService;

/**
 * JcaEmailTemplateLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaEmailTemplateLangServiceImpl implements JcaEmailTemplateLangService {

    @Autowired
    JcaEmailTemplateLangRepository jcaEmailTemplateLangRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaEmailTemplateLang saveJcaEmailTemplateLang(JcaEmailTemplateLang jcaEmailTemplateLang) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaEmailTemplateLang.getId();
        if(null != id) {
            JcaEmailTemplateLang oldJcaEmailTemplateLang =  jcaEmailTemplateLangRepository.findOne(id);
            if (null != oldJcaEmailTemplateLang) {
                jcaEmailTemplateLang.setCreatedDate(oldJcaEmailTemplateLang.getCreatedDate());
                jcaEmailTemplateLang.setCreatedId(oldJcaEmailTemplateLang.getCreatedId());
                jcaEmailTemplateLang.setUpdatedDate(sysDate);
                jcaEmailTemplateLang.setUpdatedId(userId);
                jcaEmailTemplateLangRepository.update(jcaEmailTemplateLang);
            }
            
        }else {
            jcaEmailTemplateLang.setCreatedDate(sysDate);
            jcaEmailTemplateLang.setCreatedId(userId);
            jcaEmailTemplateLang.setUpdatedDate(sysDate);
            jcaEmailTemplateLang.setUpdatedId(userId);
            jcaEmailTemplateLangRepository.create(jcaEmailTemplateLang);
        }
        return jcaEmailTemplateLang;
    }

    @Override
    public JcaEmailTemplateLang saveJcaEmailTemplateLangDto(JcaEmailTemplateLangDto jcaEmailTemplateLangDto) {
        // convert to jcaEmailTemplate
        JcaEmailTemplateLang jcaEmailTemplateLang = objectMapper.convertValue(jcaEmailTemplateLangDto, JcaEmailTemplateLang.class);
        // save data
        jcaEmailTemplateLang = this.saveJcaEmailTemplateLang(jcaEmailTemplateLang);
        
        // update id
        jcaEmailTemplateLangDto.setId(jcaEmailTemplateLang.getId());
        return jcaEmailTemplateLang;
    }

  
    @Override
    public List<JcaEmailTemplateLangDto> getJcaEmailTemplateLangDtoEmailTemplateId(Long emailTemplateId) {
        return jcaEmailTemplateLangRepository.getJcaEmailTemplateLangDtoByEmailTemplateId(emailTemplateId);
    }

    @Override
    public JcaEmailTemplateLangDto getJcaEmailTemplateLangDtoByEmailTemplateIdAndLangCode(Long emailTemplateId, String langCode) {
        return jcaEmailTemplateLangRepository.getJcaEmailTemplateLangDtoByIdAndLangCode(emailTemplateId, langCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savejcaEmailTemplateLangDtos(Long emailTemplateId, List<JcaEmailTemplateLangDto> jcaEmailTemplateLangDtos) {
        List<JcaEmailTemplateLangDto> listOld = this.getJcaEmailTemplateLangDtoEmailTemplateId(emailTemplateId);
        for (JcaEmailTemplateLangDto jcaEmailTemplateLangDto : jcaEmailTemplateLangDtos) {
            if(CommonCollectionUtil.isNotEmpty(listOld)) {
                JcaEmailTemplateLangDto oldLangDto = listOld.stream().filter(oldLang -> oldLang.getLangCode().equals(jcaEmailTemplateLangDto.getLangCode())).findAny().orElse(null);
                if(null != oldLangDto) {
                    jcaEmailTemplateLangDto.setId(oldLangDto.getId());
                }
            }
            jcaEmailTemplateLangDto.setEmailTemplateId(emailTemplateId);
            this.saveJcaEmailTemplateLangDto(jcaEmailTemplateLangDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaEmailTemplateLangDtosByEmailTemplateId(Long emailTemplateId) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jcaEmailTemplateLangRepository.deleteJcaEmailTemplateLangDtosByEmailTemplateId(emailTemplateId,userId,sysDate);
        
    }
 
}

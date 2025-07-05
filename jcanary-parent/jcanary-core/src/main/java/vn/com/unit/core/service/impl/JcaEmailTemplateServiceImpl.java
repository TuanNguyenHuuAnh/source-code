/*******************************************************************************
 * Class        :JcaEmailTemplateServiceImpl
 * Created date :2020/12/16
 * Lasted date  :2020/12/16
 * Author       :SonND
 * Change log   :2020/12/16 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.repository.JcaEmailTemplateRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaEmailTemplateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaEmailTemplateServiceImpl implements JcaEmailTemplateService {

    @Autowired
    private JcaEmailTemplateRepository jcaEmailTemplateRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaEmailTemplate saveJcaEmailTemplate(JcaEmailTemplate jcaEmailTemplate) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaEmailTemplate.getId();
        if(null != id) {
            JcaEmailTemplate oldJcaEmailTemplate =  jcaEmailTemplateRepository.findOne(id);
            if (null != oldJcaEmailTemplate) {
                jcaEmailTemplate.setCreatedDate(oldJcaEmailTemplate.getCreatedDate());
                jcaEmailTemplate.setCreatedId(oldJcaEmailTemplate.getCreatedId());
                jcaEmailTemplate.setUpdatedDate(sysDate);
                jcaEmailTemplate.setUpdatedId(userId);
                jcaEmailTemplateRepository.update(jcaEmailTemplate);
            }
            
        }else {
            jcaEmailTemplate.setCreatedDate(sysDate);
            jcaEmailTemplate.setCreatedId(userId);
            jcaEmailTemplate.setUpdatedDate(sysDate);
            jcaEmailTemplate.setUpdatedId(userId);
            jcaEmailTemplateRepository.create(jcaEmailTemplate);
        }
        return jcaEmailTemplate;
    }

    @Override
    public JcaEmailTemplate saveJcaEmailTemplateDto(JcaEmailTemplateDto jcaEmailTemplateDto) {
        // unescape template html
        jcaEmailTemplateDto.setTemplateContent(unescapeTemplateHtml(jcaEmailTemplateDto.getTemplateContent()));
        // convert to jcaEmailTemplate
        JcaEmailTemplate jcaEmailTemplate = objectMapper.convertValue(jcaEmailTemplateDto, JcaEmailTemplate.class);
        // save data
        jcaEmailTemplate = this.saveJcaEmailTemplate(jcaEmailTemplate);
        
        // escape template html
         jcaEmailTemplateDto.setTemplateContent(escapeTemplateHtml(jcaEmailTemplateDto.getTemplateContent()));
        // update id
        jcaEmailTemplateDto.setId(jcaEmailTemplate.getId());
        
        return jcaEmailTemplate;
    }

    @Override
    public int countJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto) {
        return jcaEmailTemplateRepository.countJcaEmailTemplateDtoByCondition(jcaEmailTemplateSearchDto);
    }

    @Override
    public List<JcaEmailTemplateDto> getJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto,
            Pageable pageable) {
        return jcaEmailTemplateRepository.getJcaEmailTemplateDtoByCondition(jcaEmailTemplateSearchDto, pageable).getContent();
    }

    @Override
    public JcaEmailTemplateDto getJcaEmailTemplateById(Long id) {
        return jcaEmailTemplateRepository.getJcaEmailTemplateDtoById(id);
    }

    @Override
    public JcaEmailTemplateDto getJcaEmailTemplateDtoById(Long id) {
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateRepository.getJcaEmailTemplateDtoById(id);
        if(null != jcaEmailTemplateDto) {
            jcaEmailTemplateDto.setTemplateContent(escapeTemplateHtml(jcaEmailTemplateDto.getTemplateContent())); 
        }
        return jcaEmailTemplateDto;
    }

    @Override
    public String escapeTemplateHtml(String templateHtml) {
        if(CommonStringUtil.isNotBlank(templateHtml)) {
            int length = templateHtml.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char value = templateHtml.charAt(i);
                sb.append(escapeValue(value));
            }
            return sb.toString();
        }else {
            return templateHtml; 
        }
    }
    
    private String escapeValue(char value) {
        String result = CommonConstant.EMPTY;
        if(compareChar(value, CommonConstant.CHAR_DOUBLE_QUOTATION_MARK)) {
            result = CommonConstant.ESCAPE_QUOT + result;
        }
        else if(compareChar(value, CommonConstant.CHAR_AMPERSAND)) {
            result = CommonConstant.ESCAPE_AMP + result;
        }
        else if(compareChar(value, CommonConstant.CHAR_IS_LESS_THAN)) {
            result = CommonConstant.ESCAPE_LT + result;
        }
        else if(compareChar(value, CommonConstant.CHAR_IS_MORE_THAN)) {
            result = CommonConstant.ESCAPE_GT + result;
        }else {
            result = value + result;
        }
        return result;
    }
    
    private boolean compareChar(char char1, char char2) {
       boolean equal = false;
       int result = Character.compare(char1, char2); 
       if( result == 0) {
           equal = true;
       }
       return  equal;
    }
    

    @Override
    public String unescapeTemplateHtml(String templateHtmlEscape) {
        if(CommonStringUtil.isNotBlank(templateHtmlEscape)) {
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_QUOT, CommonConstant.CHAR_DOUBLE_QUOTATION_MARK+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_AMP, CommonConstant.CHAR_AMPERSAND+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_LT, CommonConstant.CHAR_IS_LESS_THAN+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_GT, CommonConstant.CHAR_IS_MORE_THAN+ CommonConstant.EMPTY);
            return templateHtmlEscape;
        }else {
            return templateHtmlEscape;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaEmailTemplate(Long emailTemplateId) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != emailTemplateId) {
            JcaEmailTemplate oldJcaEmailTemplate =  jcaEmailTemplateRepository.findOne(emailTemplateId);
            if (null != oldJcaEmailTemplate) {
                oldJcaEmailTemplate.setDeletedDate(sysDate);
                oldJcaEmailTemplate.setDeletedId(userId);
                jcaEmailTemplateRepository.update(oldJcaEmailTemplate);
            }
            
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaEmailTemplateDto(Long emailTemplateId) {
         this.deleteJcaEmailTemplate(emailTemplateId);
    }

    @Override
    public DbRepository<JcaEmailTemplate, Long> initRepo() {
        return jcaEmailTemplateRepository;
    }

    @Override
    public JcaEmailTemplateDto findJcaEmailTemplateDtoByCode(String code) {
        return jcaEmailTemplateRepository.findJcaEmailTemplateDtoByCode(code);
    }

    @Override
    public  List<JcaGroupEmailDto> findGourpMail(String id) {
        return jcaEmailTemplateRepository.findGourpMailById(id);
    }
    
    @Override
    public  JcaGroupEmailDto findUserNameAction(String userName) {
        return jcaEmailTemplateRepository.findUserNameActionByUser(userName);
    }
}

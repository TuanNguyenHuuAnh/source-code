package vn.com.unit.ep2p.core.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.quartz.job.email.dto.QuartzJobEmailTemplateDto;
import vn.com.unit.quartz.job.email.service.QuartzJobEmailTemplateService;
@Service
public class QuartzJbEmailTemplateServiceImpl extends AbstractCommonService  implements QuartzJobEmailTemplateService{

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Override
    public QuartzJobEmailTemplateDto getQuartzJobEmailTemplateById(Long id) {
        JcaEmailTemplateDto jcaEmailTemplate = jcaEmailTemplateService.getJcaEmailTemplateDtoById(id);
        QuartzJobEmailTemplateDto result = objectMapper.convertValue(jcaEmailTemplate, QuartzJobEmailTemplateDto.class);
        return result;
    }

}

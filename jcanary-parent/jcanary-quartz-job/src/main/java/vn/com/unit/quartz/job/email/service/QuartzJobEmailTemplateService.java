package vn.com.unit.quartz.job.email.service;

import vn.com.unit.quartz.job.email.dto.QuartzJobEmailTemplateDto;

public interface QuartzJobEmailTemplateService {
    
    public QuartzJobEmailTemplateDto getQuartzJobEmailTemplateById(Long id);
}

package vn.com.unit.ep2p.core.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.common.dto.EmailResultDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.service.JcaEmailService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.quartz.job.email.dto.QuartzEmailDto;
import vn.com.unit.quartz.job.email.dto.QuartzJobEmailResult;
import vn.com.unit.quartz.job.email.service.QuartzJobEmailService;

@Service
public class QuartzJobEmailServiceImpl extends AbstractCommonService implements QuartzJobEmailService{
    
    @Autowired
    private JcaEmailService jcaEmailService;
    
    @Override
    public QuartzJobEmailResult sendEmail(QuartzEmailDto quartzEmailDto) {
        JcaEmailDto jcaEmailDto = objectMapper.convertValue(quartzEmailDto, JcaEmailDto.class);
        EmailResultDto responseEmailDto = jcaEmailService.sendEmail(jcaEmailDto);
        QuartzJobEmailResult result = objectMapper.convertValue(responseEmailDto, QuartzJobEmailResult.class);
        return result;
    }
}

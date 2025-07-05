package vn.com.unit.quartz.job.email.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;
@Getter
@Setter
public class QuartzJobEmailTemplateDto extends AbstractTracking{
    
    private Long id;
    private String templateName;
    private String code;
    private String templateContent;
    private Boolean actived;
    private String subject;
    private Long companyId;
    private String companyName;
    private String createdBy;
    
    private List<QuartzJobEmailTemplateLangDto> jcaEmailTemplateLangDtos;
}

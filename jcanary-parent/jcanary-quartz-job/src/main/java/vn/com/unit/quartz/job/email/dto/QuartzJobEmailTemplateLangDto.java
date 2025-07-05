package vn.com.unit.quartz.job.email.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

@Getter
@Setter
public class QuartzJobEmailTemplateLangDto extends AbstractTracking{
    private Long id;
    private Long emailTemplateId;
    private String langCode;
    private String title;
    private String notification;
}

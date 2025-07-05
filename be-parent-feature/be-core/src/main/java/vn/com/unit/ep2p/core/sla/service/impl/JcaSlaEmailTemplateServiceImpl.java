/*******************************************************************************
 * Class        ：JcaSlaTemplateServiceImpl
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：khadm
 * Change log   ：2021/01/19：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.core.sla.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.sla.email.dto.SlaEmailTemplateDto;
import vn.com.unit.sla.email.service.SlaEmailTemplateService;

/**
 * JcaSlaTemplateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author khadm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaSlaEmailTemplateServiceImpl implements SlaEmailTemplateService {
    public static final String PARAM_START_WITH = "${";
    public static final String PARAM_END_WITH = "}";
    public static final Pattern PARAM_PATTERN = Pattern
            .compile(Pattern.quote(PARAM_START_WITH) + "(.*?)" + Pattern.quote(PARAM_END_WITH));

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    private Logger logger = LoggerFactory.getLogger(JcaSlaEmailTemplateServiceImpl.class);

    @Override
    public SlaEmailTemplateDto getSlaEmailTemplateDtoByTemplateId(Long templateId, Map<String, Object> mapData) {
        SlaEmailTemplateDto result = null;
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateById(templateId);
        if (null != jcaEmailTemplateDto) {
            result = new SlaEmailTemplateDto();
            result.setSubject(jcaEmailTemplateDto.getTemplateSubject());
            String content = replaceParamContent(jcaEmailTemplateDto, mapData);
            result.setEmailContent(content);
        }
        return result;
    }

    /**
     * @author TaiTM
     * @date 2021-04-29
     */
    private String replaceParamContent(JcaEmailTemplateDto jcaEmailTemplateDto, Map<String, Object> mapData) {
        LinkedHashSet<String> hashParam = new LinkedHashSet<String>();

        String content = jcaEmailTemplateDto.getTemplateContent();

        hashParam.addAll(getParams(jcaEmailTemplateDto.getTemplateSubject()));
        hashParam.addAll(getParams(jcaEmailTemplateDto.getTemplateContent()));

        for (String param : hashParam) {
            if (mapData.containsKey(param)) {
                String text = (String) mapData.get(param);
                
                String str = PARAM_START_WITH.concat(param).concat(PARAM_END_WITH);
                while (content.contains(str)) {
                    content = content.replace(str, text);
                }
            }
        }

        return content;
    }

    /**
     * @author TaiTM
     * @date 2021-04-29
     */
    public List<String> getParams(String content) {
        List<String> paramList = new ArrayList<String>();
        if (StringUtils.isNotBlank(content)) {
            Matcher matcher = PARAM_PATTERN.matcher(content);
            while (matcher.find()) {
                String param = matcher.group(1);
                paramList.add(param);
            }
        }
        return paramList;
    }
    
    @Override
    public String replaceParam(String content, Map<String, Object> mapData) {
        LinkedHashSet<String> hashParam = new LinkedHashSet<String>();
        hashParam.addAll(getParams(content));
        for (String param : hashParam) {
            if (mapData.containsKey(param)) {
                String text = (String) mapData.get(param);
                if (text != null) {
                    try {
                        String str = PARAM_START_WITH.concat(param).concat(PARAM_END_WITH);
                        while (content.contains(str)) {
                            content = content.replace(str, text);
                        }
                    } catch (Exception e) {
                        logger.error("######replaceParam######", e);
                    }
                }
            }
        }
        return content;
    }
}

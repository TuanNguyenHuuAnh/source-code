package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.dto.JcaNotiTemplateLangDto;
import vn.com.unit.core.entity.JcaNotiTemplateLang;
import vn.com.unit.core.repository.JcaNotiTemplateLangRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaNotiTemplateLangService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaNotiTemplateLangServiceImpl implements JcaNotiTemplateLangService {

    @Autowired
    private JcaNotiTemplateLangRepository jcaNotiTemplateLangRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<JcaNotiTemplateLangDto> getJcaNotiTemplateLangDtoListByTemplateId(Long templateId) {
        return jcaNotiTemplateLangRepository.getJcaNotiTemplateLangDtoListByTemplateId(templateId);
    }

    @Override
    @Transactional
    public JcaNotiTemplateLang saveJcaNotiTemplateLang(JcaNotiTemplateLang jcaNotiTemplateLang) {
        Long templateId = jcaNotiTemplateLang.getNotiId();
        Long langId = jcaNotiTemplateLang.getLangId();
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = commonService.getSystemDate();
        JcaNotiTemplateLang jcaNotiTemplateLangOld = jcaNotiTemplateLangRepository.getJcaNotiTemplateLangByTemplateIdAndLangId(templateId,
                langId);
        if (null != jcaNotiTemplateLangOld) {
            CommonObjectUtil.copyPropertiesNonNull(jcaNotiTemplateLang, jcaNotiTemplateLangOld);
            jcaNotiTemplateLangOld.setUpdatedDate(sysDate);
            jcaNotiTemplateLangOld.setUpdatedId(userId);
            return jcaNotiTemplateLangRepository.update(jcaNotiTemplateLangOld);
        } else {
            jcaNotiTemplateLang.setCreatedDate(sysDate);
            jcaNotiTemplateLang.setCreatedId(userId);
            jcaNotiTemplateLang.setUpdatedDate(sysDate);
            jcaNotiTemplateLang.setUpdatedId(userId);
            return jcaNotiTemplateLangRepository.create(jcaNotiTemplateLang);
        }
    }

    @Override
    @Transactional
    public JcaNotiTemplateLang saveJcaNotiTemplateLangDto(JcaNotiTemplateLangDto jcaNotiTemplateLangDto) {
        JcaNotiTemplateLang jcaNotiTemplateLang = objectMapper.convertValue(jcaNotiTemplateLangDto, JcaNotiTemplateLang.class);
        return this.saveJcaNotiTemplateLang(jcaNotiTemplateLang);
    }

}

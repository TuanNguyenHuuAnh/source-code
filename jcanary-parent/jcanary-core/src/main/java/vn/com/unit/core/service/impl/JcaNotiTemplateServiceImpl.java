/*******************************************************************************
 * Class        ：JcaNotiTemplateServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.dto.JcaNotiTemplateLangDto;
import vn.com.unit.core.dto.JcaNotiTemplateSearchDto;
import vn.com.unit.core.entity.JcaNotiTemplate;
import vn.com.unit.core.repository.JcaNotiTemplateRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaNotiTemplateLangService;
import vn.com.unit.core.service.JcaNotiTemplateService;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaNotiTemplateServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaNotiTemplateServiceImpl implements JcaNotiTemplateService {

    /** The jca noti template repository. */
    @Autowired
    private JcaNotiTemplateRepository jcaNotiTemplateRepository;

    @Autowired
    private JcaNotiTemplateLangService jcaNotiTemplateLangService;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DbRepository<JcaNotiTemplate, Long> initRepo() {
        return jcaNotiTemplateRepository;
    }

    @Override
    public JcaNotiTemplate saveJcaNotiTemplate(JcaNotiTemplate jcaNotiTemplate) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        Long id = jcaNotiTemplate.getId();
        if (null != id) {
            JcaNotiTemplate jcaNotiTemplateOld = jcaNotiTemplateRepository.findOne(id);
            CommonObjectUtil.copyPropertiesNonNull(jcaNotiTemplate, jcaNotiTemplateOld);
            jcaNotiTemplateOld.setUpdatedDate(sysDate);
            jcaNotiTemplateOld.setUpdatedId(userId);
            jcaNotiTemplateRepository.update(jcaNotiTemplateOld);
        } else {
            jcaNotiTemplate.setCreatedDate(sysDate);
            jcaNotiTemplate.setCreatedId(userId);
            jcaNotiTemplate.setUpdatedDate(sysDate);
            jcaNotiTemplate.setUpdatedId(userId);
            jcaNotiTemplateRepository.create(jcaNotiTemplate);
        }
        return jcaNotiTemplate;
    }

    @Override
    public JcaNotiTemplate saveJcaNotiTemplateDto(JcaNotiTemplateDto notiTemplateDto) {
        JcaNotiTemplate jcaNotiTemplate = objectMapper.convertValue(notiTemplateDto, JcaNotiTemplate.class);
        this.saveJcaNotiTemplate(jcaNotiTemplate);
        Long templateId = jcaNotiTemplate.getId();
        List<JcaNotiTemplateLangDto> notiTemplateLangDtoList = notiTemplateDto.getNotiTemplateLangDtoList();
        if (CommonCollectionUtil.isNotEmpty(notiTemplateLangDtoList)) {
            for (JcaNotiTemplateLangDto jcaNotiTemplateLangDto : notiTemplateLangDtoList) {
                jcaNotiTemplateLangDto.setNotiId(templateId);
                jcaNotiTemplateLangService.saveJcaNotiTemplateLangDto(jcaNotiTemplateLangDto);
            }
        }
        return jcaNotiTemplate;
    }

    @Override
    public JcaNotiTemplateDto getJcaNotiTemplateDtoById(Long templateId) {
        JcaNotiTemplateDto jcaNotiTemplateDto = jcaNotiTemplateRepository.getJcaNotiTemplateDtoById(templateId);
        if (null != jcaNotiTemplateDto) {
            List<JcaNotiTemplateLangDto> notiTemplateLangDtoList = jcaNotiTemplateLangService
                    .getJcaNotiTemplateLangDtoListByTemplateId(templateId);
            jcaNotiTemplateDto.setNotiTemplateLangDtoList(notiTemplateLangDtoList);
        }
        return jcaNotiTemplateDto;
    }

    @Override
    public int countJcaNotiTemplateDtoByCondition(JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto) {
        return jcaNotiTemplateRepository.countJcaNotiTemplateDtoByCondition(jcaNotiTemplateSearchDto);
    }

    @Override
    public List<JcaNotiTemplateDto> getJcaNotiTemplateDtoListByCondition(JcaNotiTemplateSearchDto jcaNotiTemplateSearchDto,
            Pageable pageable) {
        return jcaNotiTemplateRepository.getJcaNotiTemplateDtoListByCondition(jcaNotiTemplateSearchDto, pageable).getContent();
    }

    @Override
    public void deleteJcaNotiTemplate(Long templateId) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        if (null != templateId) {
            JcaNotiTemplate jcaNotiTemplate = jcaNotiTemplateRepository.findOne(templateId);
            jcaNotiTemplate.setDeletedDate(sysDate);
            jcaNotiTemplate.setDeletedId(userId);
            jcaNotiTemplateRepository.update(jcaNotiTemplate);
        }
    }

    @Override
    public void deleteJcaNotiTemplateDto(Long templateId) {
        // TODO Auto-generated method stub
    }

    @Override
    public JcaNotiTemplateDto getJcaNotiTemplateDtoByCodeAndCompnayId(String code, Long companyId) {
        return jcaNotiTemplateRepository.getJcaNotiTemplateDtoByCodeAndCompnayId(code, companyId);
    }

}

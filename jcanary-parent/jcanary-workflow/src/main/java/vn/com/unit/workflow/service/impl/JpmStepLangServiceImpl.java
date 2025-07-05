/*******************************************************************************
* Class        JpmStepLangServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmStepLang;
import vn.com.unit.workflow.repository.JpmStepLangRepository;
import vn.com.unit.workflow.service.JpmLanguageService;
import vn.com.unit.workflow.service.JpmStepLangService;

/**
 * JpmStepLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStepLangServiceImpl implements JpmStepLangService {

    @Autowired
    private JpmStepLangRepository jpmStepLangRepository;
    
    @Autowired
    private JpmLanguageService jpmLanguageService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmStepLangRepository.delete(id);
                res = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStepLang saveJpmStepLang(JpmStepLang jpmStepLang) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmStepLang.setCreatedId(userId);
        jpmStepLang.setCreatedDate(sysDate);
        jpmStepLangRepository.create(jpmStepLang);
        return jpmStepLang;
    }

    @Override
    public JpmStepLang saveJpmStepLangDto(JpmStepLangDto jpmStepLangDto) {
        JpmStepLang jpmStepLang = objectMapper.convertValue(jpmStepLangDto, JpmStepLang.class);

        this.saveJpmStepLang(jpmStepLang);
        return jpmStepLang;
    }

    @Override
    public List<JpmStepLangDto> getStepLangDtosByProcessId(Long processId) {
        return jpmStepLangRepository.getStepLangDtosByProcessId(processId);
    }

    @Override
    public List<JpmStepLangDto> getStepLangDtosByStepId(Long stepId) {
        return jpmStepLangRepository.getStepLangDtosByStepId(stepId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStepLangDtosByStepId(List<JpmStepLangDto> stepLangDtos, Long stepId) {
        // delete old
        this.deleteStepLangByStepId(stepId);

        // get language map
        Map<String, Long> langIdConverter = jpmLanguageService.getLanguageIdConverter();
        
        // save new
        for (JpmStepLangDto stepLangDto : stepLangDtos) {
            stepLangDto.setStepId(stepId);
            stepLangDto.setLangId(langIdConverter.getOrDefault(stepLangDto.getLangCode(), 0L));
            this.saveJpmStepLangDto(stepLangDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStepLangByStepId(Long stepId) {
        jpmStepLangRepository.deleteStepLangByStepId(stepId);
    }

}
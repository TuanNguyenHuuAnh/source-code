/*******************************************************************************
* Class        JpmProcessLangServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmProcessLangDto;
import vn.com.unit.workflow.entity.JpmProcessLang;
import vn.com.unit.workflow.repository.JpmProcessLangRepository;
import vn.com.unit.workflow.service.JpmLanguageService;
import vn.com.unit.workflow.service.JpmProcessLangService;

/**
 * JpmProcessLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessLangServiceImpl implements JpmProcessLangService {

    @Autowired
    private JpmProcessLangRepository jpmProcessLangRepository;
    
    @Autowired
    private JpmLanguageService jpmLanguageService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessLangDto getJpmProcessLangDtoById(Long id) {
        JpmProcessLangDto jpmProcessLangDto = new JpmProcessLangDto();
        if (null != id) {
            JpmProcessLang jpmProcessLang = jpmProcessLangRepository.findOne(id);
            if (null != jpmProcessLang) {
                jpmProcessLangDto = objectMapper.convertValue(jpmProcessLang, JpmProcessLangDto.class);
            }
        }
        return jpmProcessLangDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmProcessLangRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessLang saveJpmProcessLang(JpmProcessLang jpmProcessLang) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmProcessLang.setCreatedId(userId);
        jpmProcessLang.setCreatedDate(sysDate);
        jpmProcessLangRepository.create(jpmProcessLang);
        return jpmProcessLang;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmProcessLang saveJpmProcessLangDto(JpmProcessLangDto jpmProcessLangDto) {
        JpmProcessLang jpmProcessLang = objectMapper.convertValue(jpmProcessLangDto, JpmProcessLang.class);
        this.saveJpmProcessLang(jpmProcessLang);
        return jpmProcessLang;
    }

    @Override
    public List<JpmProcessLangDto> getJpmProcessLangDtosByProcessId(@NotNull Long processId) {
        return jpmProcessLangRepository.getJpmProcessLangDtosByProcessId(processId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProcessLangDtosByProcessId(@NotNull List<JpmProcessLangDto> processLangDtos, @NotNull Long processId) {
        // delete old
        this.deleteJpmProcessLangDtosByProcessId(processId);
        
        // get language map
        Map<String, Long> langIdConverter = jpmLanguageService.getLanguageIdConverter();
        
        // save new
        for (JpmProcessLangDto processLangDto : processLangDtos) {
            processLangDto.setProcessId(processId);
            processLangDto.setLangId(langIdConverter.getOrDefault(processLangDto.getLangCode(), 0L));
            this.saveJpmProcessLangDto(processLangDto);
        }

    }

    @Override
    public void deleteJpmProcessLangDtosByProcessId(Long processId) {
        jpmProcessLangRepository.deleteJpmProcessLangDtosByProcessId(processId);
    }
}
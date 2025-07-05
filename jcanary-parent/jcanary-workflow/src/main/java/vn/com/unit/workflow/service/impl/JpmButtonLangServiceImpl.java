/*******************************************************************************
* Class        JpmButtonLangServiceImpl
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
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButtonLang;
import vn.com.unit.workflow.repository.JpmButtonLangRepository;
import vn.com.unit.workflow.service.JpmButtonLangService;
import vn.com.unit.workflow.service.JpmLanguageService;

/**
 * JpmButtonLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonLangServiceImpl implements JpmButtonLangService {

    @Autowired
    private JpmButtonLangRepository jpmButtonLangRepository;
    
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
                jpmButtonLangRepository.delete(id);
                res = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonLang saveJpmButtonLang(JpmButtonLang jpmButtonLang) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmButtonLang.setCreatedId(userId);
        jpmButtonLang.setCreatedDate(sysDate);
        jpmButtonLangRepository.create(jpmButtonLang);
        return jpmButtonLang;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonLang saveJpmButtonLangDto(JpmButtonLangDto jpmButtonLangDto) {
        JpmButtonLang jpmButtonLang = objectMapper.convertValue(jpmButtonLangDto, JpmButtonLang.class);
        this.saveJpmButtonLang(jpmButtonLang);
        return jpmButtonLang;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveButtonLangsByButtonId(@NotNull List<JpmButtonLangDto> jpmButtonLangDto, @NotNull Long buttonId) {
        // delete old
        this.deleteButtonLangsByButtonId(buttonId);
        
        // get language map
        Map<String, Long> langIdConverter = jpmLanguageService.getLanguageIdConverter();
        
        // save new
        for (JpmButtonLangDto buttonLangDto : jpmButtonLangDto) {
            buttonLangDto.setButtonId(buttonId);
            buttonLangDto.setLangId(langIdConverter.getOrDefault(buttonLangDto.getLangCode(), 0L));
            this.saveJpmButtonLangDto(buttonLangDto);
        }
    }

    @Override
    public List<JpmButtonLangDto> getButtonLangDtosByProcessId(Long processId) {
        return jpmButtonLangRepository.getButtonLangDtosByProcessId(processId);
    }

    @Override
    public List<JpmButtonLangDto> getButtonLangDtosByButtonId(Long buttonId) {
        return jpmButtonLangRepository.getButtonLangDtosByButtonId(buttonId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteButtonLangsByButtonId(Long buttonId) {
        jpmButtonLangRepository.deleteButtonLangsByButtonId(buttonId);

    }

}
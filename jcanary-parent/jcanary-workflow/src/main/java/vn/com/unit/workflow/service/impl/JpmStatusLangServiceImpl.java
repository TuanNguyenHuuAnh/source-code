/*******************************************************************************
* Class        JpmStatusLangServiceImpl
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
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatusLang;
import vn.com.unit.workflow.repository.JpmStatusLangRepository;
import vn.com.unit.workflow.service.JpmLanguageService;
import vn.com.unit.workflow.service.JpmStatusLangService;

/**
 * JpmStatusLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusLangServiceImpl implements JpmStatusLangService {

    @Autowired
    private JpmStatusLangRepository jpmStatusLangRepository;
    
    @Autowired
    private JpmLanguageService jpmLanguageService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStatusLangDto getJpmStatusLangDtoById(Long id) {
        JpmStatusLangDto jpmStatusLangDto = new JpmStatusLangDto();
        if (null != id) {
            JpmStatusLang jpmStatusLang = jpmStatusLangRepository.findOne(id);
            if (null != jpmStatusLang) {
                jpmStatusLangDto = objectMapper.convertValue(jpmStatusLang, JpmStatusLangDto.class);
            }
        }
        return jpmStatusLangDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmStatusLangRepository.delete(id);
                res = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStatusLang saveJpmStatusLang(JpmStatusLang jpmStatusLang) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmStatusLang.setCreatedId(userId);
        jpmStatusLang.setCreatedDate(sysDate);
        jpmStatusLangRepository.create(jpmStatusLang);
        return jpmStatusLang;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStatusLang saveJpmStatusLangDto(JpmStatusLangDto jpmStatusLangDto) {
        JpmStatusLang jpmStatusLang = objectMapper.convertValue(jpmStatusLangDto, JpmStatusLang.class);

        this.saveJpmStatusLang(jpmStatusLang);
        return jpmStatusLang;
    }

    @Override
    public List<JpmStatusLangDto> getStatusLangDtosByProcessId(Long processId) {
        return jpmStatusLangRepository.getStatusLangDtosByProcessId(processId);
    }
    
    @Override
    public List<JpmStatusLangDto> getStatusLangDtosByBusinessCode(String businessCode) {
        return jpmStatusLangRepository.findStatusLangDtosByBusinessCode(businessCode);
    }

    @Override
    public List<JpmStatusLangDto> getStatusLangDtosByStatusId(Long statusId) {
        return jpmStatusLangRepository.getStatusLangDtosByStatusId(statusId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.service.JpmStatusService#savestatusDtosByProcessId(java.util.List, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStatusLangDtosByStatusId(List<JpmStatusLangDto> statusLangDtos, Long statusId) {
        // delete old
        this.deleteStatusLangByStatusId(statusId);

        // get language map
        Map<String, Long> langIdConverter = jpmLanguageService.getLanguageIdConverter();
        
        // save new
        for (JpmStatusLangDto statusLangDto : statusLangDtos) {
            statusLangDto.setStatusId(statusId);
            statusLangDto.setLangId(langIdConverter.getOrDefault(statusLangDto.getLangCode(), 0L));
            this.saveJpmStatusLangDto(statusLangDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStatusLangByStatusId(Long statusId) {
        jpmStatusLangRepository.deleteStatusLangByStatusId(statusId);
    }

}
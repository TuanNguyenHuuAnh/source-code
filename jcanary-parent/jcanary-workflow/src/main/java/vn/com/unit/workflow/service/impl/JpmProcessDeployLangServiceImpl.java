/*******************************************************************************
* Class        JpmProcessDeployLangServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmProcessDeployLangDto;
import vn.com.unit.workflow.entity.JpmProcessDeployLang;
import vn.com.unit.workflow.repository.JpmProcessDeployLangRepository;
import vn.com.unit.workflow.service.JpmProcessDeployLangService;

/**
 * JpmProcessDeployLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessDeployLangServiceImpl implements JpmProcessDeployLangService {

    @Autowired
    private JpmProcessDeployLangRepository jpmProcessDeployLangRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessDeployLangDto getJpmProcessDeployLangDtoById(Long id) {
        JpmProcessDeployLangDto jpmProcessDeployLangDto = new JpmProcessDeployLangDto();
        if (null != id) {
            JpmProcessDeployLang jpmProcessDeployLang = jpmProcessDeployLangRepository.findOne(id);
            if (null != jpmProcessDeployLang) {
                jpmProcessDeployLangDto = objectMapper.convertValue(jpmProcessDeployLang, JpmProcessDeployLangDto.class);
            }
        }
        return jpmProcessDeployLangDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmProcessDeployLangRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    public JpmProcessDeployLang saveJpmProcessDeployLang(JpmProcessDeployLang jpmProcessDeployLang) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmProcessDeployLang.setCreatedId(userId);
        jpmProcessDeployLang.setCreatedDate(sysDate);
        jpmProcessDeployLangRepository.create(jpmProcessDeployLang);
        return jpmProcessDeployLang;
    }

    @Override
    public JpmProcessDeployLang saveJpmProcessDeployLangDto(JpmProcessDeployLangDto jpmProcessDeployLangDto) {
        JpmProcessDeployLang jpmProcessDeployLang = objectMapper.convertValue(jpmProcessDeployLangDto, JpmProcessDeployLang.class);
        return this.saveJpmProcessDeployLang(jpmProcessDeployLang);
    }

    @Override
    public void saveJpmProcessDeployLangDtos(List<JpmProcessDeployLangDto> processDeployLangDtos, Long processDeployId) {
        if (CommonCollectionUtil.isNotEmpty(processDeployLangDtos) && Objects.nonNull(processDeployId)) {
            for (JpmProcessDeployLangDto processDeployLangDto : processDeployLangDtos) {
                processDeployLangDto.setProcessDeployId(processDeployId);
                this.saveJpmProcessDeployLangDto(processDeployLangDto);
            }
        }
    }

    @Override
    public List<JpmProcessDeployLangDto> getJpmProcessDeployLangDtosByProcessDeployId(@NotNull Long processDeployId) {
        return jpmProcessDeployLangRepository.getJpmProcessDeployLangDtosByProcessDeployId(processDeployId);
    }

}
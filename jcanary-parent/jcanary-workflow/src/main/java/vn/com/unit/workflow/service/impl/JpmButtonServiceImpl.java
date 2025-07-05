/*******************************************************************************
* Class        JpmButtonServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.repository.JpmButtonRepository;
import vn.com.unit.workflow.service.JpmButtonLangService;
import vn.com.unit.workflow.service.JpmButtonService;
import vn.com.unit.workflow.service.JpmHiButtonService;

/**
 * JpmButtonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonServiceImpl implements JpmButtonService {

    @Autowired
    private JpmButtonRepository jpmButtonRepository;

    @Autowired
    private JpmButtonLangService jpmButtonLangService;

    @Autowired
    private JpmHiButtonService jpmHiButtonService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmButtonDto getJpmButtonDtoById(Long id) {
        JpmButtonDto jpmButtonDto = new JpmButtonDto();
        if (null != id) {
            JpmButton jpmButton = jpmButtonRepository.findOne(id);
            if (null != jpmButton && 0L == jpmButton.getDeletedId()) {
                jpmButtonDto = objectMapper.convertValue(jpmButton, JpmButtonDto.class);
                jpmButtonDto.setButtonId(id);

                List<JpmButtonLangDto> buttonLangs = jpmButtonLangService.getButtonLangDtosByButtonId(id);
                jpmButtonDto.setButtonLangs(buttonLangs);
            }
        }
        return jpmButtonDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmButton jpmButton = jpmButtonRepository.findOne(id);
            if (null != jpmButton) {
                jpmButton.setDeletedId(userId);
                jpmButton.setDeletedDate(sysDate);
                jpmButtonRepository.update(jpmButton);
                res = true;
            }
            // delete button lang
            jpmButtonLangService.deleteButtonLangsByButtonId(id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButton saveJpmButton(JpmButton jpmButton) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmButton.getId();
        if (null != id) {
            JpmButton oldJpmButton = jpmButtonRepository.findOne(id);
            if (null != oldJpmButton) {
                jpmButton.setCreatedId(oldJpmButton.getCreatedId());
                jpmButton.setCreatedDate(oldJpmButton.getCreatedDate());
                jpmButton.setUpdatedId(userId);
                jpmButton.setUpdatedDate(sysDate);
                jpmButtonRepository.update(jpmButton);
            }
        } else {
            jpmButton.setCreatedId(userId);
            jpmButton.setCreatedDate(sysDate);
            jpmButton.setUpdatedId(userId);
            jpmButton.setUpdatedDate(sysDate);
            jpmButtonRepository.create(jpmButton);
        }
        
        // save history
        jpmHiButtonService.saveJpmHiButton(jpmButton);
        
        return jpmButton;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButton saveJpmButtonDto(JpmButtonDto jpmButtonDto) {
        JpmButton jpmButton = objectMapper.convertValue(jpmButtonDto, JpmButton.class);

        jpmButton.setId(jpmButtonDto.getButtonId());

        this.saveJpmButton(jpmButton);

        Long buttonId = jpmButton.getId();
        jpmButtonDto.setButtonId(buttonId);
        if (DtsCollectionUtil.isNotEmpty(jpmButtonDto.getButtonLangs())) {
            jpmButtonLangService.saveButtonLangsByButtonId(jpmButtonDto.getButtonLangs(), buttonId);
        }
        return jpmButton;
    }

    @Override
    public List<JpmButtonDto> getButtonDtosByProcessId(Long processId) {
        List<JpmButtonDto> buttonDtos = jpmButtonRepository.getButtonDtosByProcessId(processId);
        if (CommonCollectionUtil.isNotEmpty(buttonDtos)) {
            List<JpmButtonLangDto> buttonLangDtos = jpmButtonLangService.getButtonLangDtosByProcessId(processId);
            if (CommonCollectionUtil.isNotEmpty(buttonLangDtos)) {
                Map<Long, List<JpmButtonLangDto>> buttonLangDtosMap = buttonLangDtos.stream()
                        .collect(Collectors.groupingBy(JpmButtonLangDto::getButtonId));
                buttonDtos.forEach(buttonDto -> buttonDto.setButtonLangs(buttonLangDtosMap.get(buttonDto.getButtonId())));
            }
        }
        return buttonDtos;
    }

    @Override
    public JpmButtonDto getJpmButtonDtoByButtonId(Long buttonId) {
        return jpmButtonRepository.getJpmButtonDtoByButtonId(buttonId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, Long> saveButtonDtosByProcessId(@NotNull List<JpmButtonDto> buttonDtos, @NotNull Long processId) {
        Map<Long, Long> resMap = new HashMap<>();
        List<JpmButtonDto> currentButtonDtos = this.getButtonDtosByProcessId(processId);
        Map<String, JpmButtonDto> buttonMap = CommonCollectionUtil.isEmpty(currentButtonDtos) ? new HashMap<>()
                : currentButtonDtos.stream().collect(Collectors.toMap(JpmButtonDto::getButtonCode, buttonDto -> buttonDto));

        for (JpmButtonDto buttonDto : buttonDtos) {
            Long oldButtonId = buttonDto.getButtonId();
            String buttonCode = buttonDto.getButtonCode();
            JpmButtonDto currentButtonDto = buttonMap.remove(buttonCode);
            if (Objects.isNull(currentButtonDto)) {
                buttonDto.setButtonId(null);
            } else {
                buttonDto.setButtonId(currentButtonDto.getButtonId());
            }
            buttonDto.setProcessId(processId);
            this.saveJpmButtonDto(buttonDto);

            if (Objects.nonNull(oldButtonId)) {
                resMap.put(oldButtonId, buttonDto.getButtonId());
            }
        }
        // DELETE
        if (CommonMapUtil.isNotEmpty(buttonMap)) {
            for (Map.Entry<String, JpmButtonDto> entry : buttonMap.entrySet()) {
                Long buttonId = entry.getValue().getButtonId();
                this.deleteById(buttonId);
            }
        }
        return resMap;
    }

}
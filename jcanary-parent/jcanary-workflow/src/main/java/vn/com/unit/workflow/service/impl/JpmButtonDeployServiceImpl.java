/*******************************************************************************
* Class        JpmButtonDeployServiceImpl
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.entity.JpmButtonDeploy;
import vn.com.unit.workflow.repository.JpmButtonDeployRepository;
import vn.com.unit.workflow.service.JpmButtonDeployService;
import vn.com.unit.workflow.service.JpmButtonLangDeployService;

/**
 * JpmButtonDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonDeployServiceImpl implements JpmButtonDeployService {

    @Autowired
    private JpmButtonDeployRepository jpmButtonDeployRepository;

    @Autowired
    private JpmButtonLangDeployService jpmButtonLangDeployService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmButtonDeployDto getJpmButtonDeployDtoById(Long id) {
        JpmButtonDeployDto jpmButtonDeployDto = new JpmButtonDeployDto();
        if (null != id) {
            JpmButtonDeploy jpmButtonDeploy = jpmButtonDeployRepository.findOne(id);
            if (Objects.nonNull(jpmButtonDeploy) && 0L == jpmButtonDeploy.getDeletedId()) {
                jpmButtonDeployDto = objectMapper.convertValue(jpmButtonDeploy, JpmButtonDeployDto.class);
                jpmButtonDeployDto.setButtonDeployId(id);
                
                List<JpmButtonLangDeployDto> buttonLangDeployDtos = jpmButtonLangDeployService.getButtonLangDeployDtosByButtonDeployId(id);
                jpmButtonDeployDto.setButtonLangs(buttonLangDeployDtos);
            }
        }
        return jpmButtonDeployDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmButtonDeploy jpmButtonDeploy = jpmButtonDeployRepository.findOne(id);
            if (null != jpmButtonDeploy) {
                jpmButtonDeploy.setDeletedId(userId);
                jpmButtonDeploy.setDeletedDate(sysDate);
                jpmButtonDeployRepository.update(jpmButtonDeploy);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonDeploy saveJpmButtonDeploy(JpmButtonDeploy jpmButtonDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmButtonDeploy.getId();
        if (null != id) {
            JpmButtonDeploy oldJpmButtonDeploy = jpmButtonDeployRepository.findOne(id);
            if (null != oldJpmButtonDeploy) {
                jpmButtonDeploy.setCreatedId(oldJpmButtonDeploy.getCreatedId());
                jpmButtonDeploy.setCreatedDate(oldJpmButtonDeploy.getCreatedDate());
                jpmButtonDeploy.setUpdatedId(userId);
                jpmButtonDeploy.setUpdatedDate(sysDate);
                jpmButtonDeployRepository.update(jpmButtonDeploy);
            }
        } else {
            jpmButtonDeploy.setCreatedId(userId);
            jpmButtonDeploy.setCreatedDate(sysDate);
            jpmButtonDeploy.setUpdatedId(userId);
            jpmButtonDeploy.setUpdatedDate(sysDate);
            jpmButtonDeployRepository.create(jpmButtonDeploy);
        }
        return jpmButtonDeploy;
    }

    @Override
    public JpmButtonDeploy saveJpmButtonDeployDto(JpmButtonDeployDto jpmButtonDeployDto) {
        JpmButtonDeploy jpmButtonDeploy = objectMapper.convertValue(jpmButtonDeployDto, JpmButtonDeploy.class);
        this.saveJpmButtonDeploy(jpmButtonDeploy);
        
        Long buttonDeployId = jpmButtonDeploy.getId();
        List<JpmButtonLangDeployDto> buttonLangDeployDtos = jpmButtonDeployDto.getButtonLangs();
        jpmButtonLangDeployService.saveJpmButtonLangDeployDtos(buttonLangDeployDtos, buttonDeployId);
        
        return jpmButtonDeploy;
    }

    @Override
    public Map<Long, Long> saveJpmButtonDeployDtos(List<JpmButtonDeployDto> buttonDeployDtos, Long processDeployId) {
        Map<Long, Long> buttonIdMap = new HashMap<>();
        if (CommonCollectionUtil.isNotEmpty(buttonDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmButtonDeployDto buttonDeployDto : buttonDeployDtos) {
                buttonDeployDto.setProcessDeployId(processDeployId);
                JpmButtonDeploy buttonDeploy = this.saveJpmButtonDeployDto(buttonDeployDto);

                Long buttonId = buttonDeploy.getButtonId();
                Long buttonDeployId = buttonDeploy.getId();
                buttonIdMap.put(buttonId, buttonDeployId);
            }
        }
        return buttonIdMap;
    }

    @Override
    public List<JpmButtonDeployDto> getButtonDeployDtosByProcessDeployId(Long processDeployId) {
        List<JpmButtonDeployDto> buttonDeployDtos = jpmButtonDeployRepository.getButtonDeployDtosByProcessDeployId(processDeployId);
        if(CommonCollectionUtil.isNotEmpty(buttonDeployDtos)) {
            List<JpmButtonLangDeployDto> buttonLangDeployDtos = jpmButtonLangDeployService.getButtonLangDeployDtosByProcessDeployId(processDeployId);
            if (CommonCollectionUtil.isNotEmpty(buttonLangDeployDtos)) {
                Map<Long, List<JpmButtonLangDeployDto>> buttonLangDtosMap = buttonLangDeployDtos.stream()
                        .collect(Collectors.groupingBy(JpmButtonLangDeployDto::getButtonDeployId));
                buttonDeployDtos.forEach(buttonDto -> buttonDto.setButtonLangs(buttonLangDtosMap.get(buttonDto.getButtonDeployId())));
            }
        }
        return buttonDeployDtos;
    }

    @Override
    public ActionDto getActionDtoByProcessDeployIdAndButtonDeployId(Long processDeployId, Long buttonId) {
    	String dbType = "MSSQL";
        return jpmButtonDeployRepository.getActionDtoByProcessDeployIdAndButtonDeployId(processDeployId, buttonId, dbType);
    }

	@Override
	public JpmButtonDeployDto getJpmButtonDeployDtoByButtonTextAndProcessDeployId(String buttonText, Long processDeployId) {
		// TODO Auto-generated method stub
		return jpmButtonDeployRepository.getJpmButtonDeployDtoByButtonTextAndProcessDeployId(buttonText, processDeployId);
	}
    
}
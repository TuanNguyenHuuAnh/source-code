/*******************************************************************************
 * Class        AppButtonDeployServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhuongTH
 * Change log   2016/07/1901-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.process.workflow.dto.AppButtonDeployDto;
import vn.com.unit.process.workflow.repository.AppButtonDeployRepository;
import vn.com.unit.process.workflow.service.AppButtonDeployService;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.service.JpmButtonDeployService;

/**
 * AppButtonDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppButtonDeployServiceImpl implements AppButtonDeployService {
	
	@Autowired
	private AppButtonDeployRepository appButtonDeployRepository;
	
	@Autowired
	private JpmButtonDeployService jpmButtonDeployService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public AppButtonDeployDto[] getListButtonDeployDtoByProcessId(Long processId) {
	    List<JpmButtonDeployDto> buttonDeployDtos = jpmButtonDeployService.getButtonDeployDtosByProcessDeployId(processId);
	    if(CommonCollectionUtil.isEmpty(buttonDeployDtos)) return null;
 		return buttonDeployDtos.stream().map(this::convertJpmButtonDtoToAppButtonDto).collect(Collectors.toList()).toArray(new AppButtonDeployDto[0]);
	}

	@Override
	public AppButtonDeployDto getById(Long id) {
	    JpmButtonDeployDto buttonDeployDto = jpmButtonDeployService.getJpmButtonDeployDtoById(id);
		return this.convertJpmButtonDtoToAppButtonDto(buttonDeployDto);
	}

	@Override
	public List<AppButtonDeployDto> getListButtonDeployDtoByStepId(Long stepId) {
		List<AppButtonDeployDto> lstButtonDeloy = new ArrayList<>();
		try {
		    lstButtonDeloy = appButtonDeployRepository.findListButtonDeployDtoByStepId(stepId);
		} catch (Exception e) {
		    
		}
		return lstButtonDeloy;
	}


	@Override
	public List<Select2Dto> getButtonActions(Long processDeployId, String stepCode, String lang) throws Exception {
		List<Select2Dto> list = new ArrayList<>();
		List<JpmButtonForDocDto> buttonList = appButtonDeployRepository.findListButtonForDocDtoByProcessDeployIdAndStepCode(processDeployId, stepCode, lang);
		if(buttonList != null && CollectionUtils.isNotEmpty(buttonList)) {
			for (JpmButtonForDocDto jpmButtonForDocDto : buttonList) {
				if(jpmButtonForDocDto.getId() != null) {
					Select2Dto dto = new Select2Dto();
					String id = jpmButtonForDocDto.getId().toString();
					String name = jpmButtonForDocDto.getButtonText();
					String type = jpmButtonForDocDto.getButtonType();
					dto.setId(id);
					dto.setName(type);
					dto.setText(name);
					list.add(dto);
				}
			}
		}
		return list;
	}

    /**
     * @author KhuongTH
     */
    @Override
    public AppButtonDeployDto findOneButtonByProcessIdAndButtonType(Long processId, String buttonType) {
        return appButtonDeployRepository.findOneButtonByProcessIdAndButtonType(processId, buttonType);
    }
    
    @Override
    public ActionDto findActionDtoByProcessIdAndButtonId(Long processId,Long buttonId){
        return appButtonDeployRepository.findActionDtoByProcessIdAndButtonId(processId, buttonId);
    }
    
    @Override
    public List<Select2Dto> getSurveyButtons(Long stepId, String lang) throws Exception {
        List<Select2Dto> list = new ArrayList<>();
        List<AppButtonDeployDto> buttonList = appButtonDeployRepository.findButtonsByStepId(stepId, lang);
        if (CollectionUtils.isNotEmpty(buttonList)) {
            for (AppButtonDeployDto button : buttonList) {
                if(StringUtils.startsWith(button.getButtonType(), "SURVEY_")) {
                    Select2Dto dto = new Select2Dto();
                    String id = String.valueOf(button.getId());
                    String name = button.getButtonText();
                    dto.setId(id);
                    dto.setName(name);
                    dto.setText(name);
                    list.add(dto);
                }
            }
        }
        return list;
    }
   
    private AppButtonDeployDto convertJpmButtonDtoToAppButtonDto(JpmButtonDeployDto buttonDto) {
        if (null == buttonDto)
            return null;
        AppButtonDeployDto appButtonDto = objectMapper.convertValue(buttonDto, AppButtonDeployDto.class);

        appButtonDto.setId(buttonDto.getButtonDeployId());
        appButtonDto.setListJpmButtonLang(buttonDto.getButtonLangs());
        appButtonDto.setOrders(buttonDto.getDisplayOrder());
        return appButtonDto;
    }
}

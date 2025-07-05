package vn.com.unit.process.workflow.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.process.workflow.dto.AppParamDeployDto;
import vn.com.unit.process.workflow.service.AppParamDeployService;
import vn.com.unit.workflow.dto.JpmParamDeployDto;
import vn.com.unit.workflow.service.JpmParamDeployService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppParamDeployServiceImpl implements AppParamDeployService {

	@Autowired
	private JpmParamDeployService jpmParamDeployService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
    public AppParamDeployDto[] getListParamDeployDtoByProcessId(Long processId) {
        List<JpmParamDeployDto> paramDeployDtos = jpmParamDeployService.getParamDeployDtosByProcessDeployId(processId);
        if (CommonCollectionUtil.isEmpty(paramDeployDtos))
            return null;
        return paramDeployDtos.stream().map(this::convertJpmParamDtoToAppParamDto).collect(Collectors.toList())
                .toArray(new AppParamDeployDto[0]);
    }

	@Override
	public AppParamDeployDto getById(Long id) {
	    JpmParamDeployDto paramDeployDto = jpmParamDeployService.getJpmParamDeployDtoById(id);
		return this.convertJpmParamDtoToAppParamDto(paramDeployDto);
	}
	
	private AppParamDeployDto convertJpmParamDtoToAppParamDto(JpmParamDeployDto paramDto) {
        if (null == paramDto)
            return null;
        AppParamDeployDto appParamDto = objectMapper.convertValue(paramDto, AppParamDeployDto.class);
        appParamDto.setId(paramDto.getParamDeployId());
        appParamDto.setConfigSteps(paramDto.getParamConfigDtos());
        return appParamDto;
    }
	
}

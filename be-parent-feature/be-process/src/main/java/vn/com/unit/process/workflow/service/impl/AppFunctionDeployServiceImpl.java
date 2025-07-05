package vn.com.unit.process.workflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.process.workflow.dto.AppFunctionDeployDto;
import vn.com.unit.process.workflow.repository.AppFunctionDeployRepository;
import vn.com.unit.process.workflow.service.AppFunctionDeployService;
import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.service.JpmPermissionDeployService;


/**
 * <p>AppFunctionDeployServiceImpl</p>.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppFunctionDeployServiceImpl implements AppFunctionDeployService {
	
	@Autowired
	AppFunctionDeployRepository appFunctionDeployRepository;
	
	@Autowired
	private JpmPermissionDeployService jpmPermissionDeployService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public AppFunctionDeployDto[] getListFunctionDeployDtoByProcessId(Long processId){
	    List<JpmPermissionDeployDto> permissionDeployDtos = jpmPermissionDeployService.getPermissionDeployDtosByProcessDeployId(processId);
		if(CommonCollectionUtil.isEmpty(permissionDeployDtos)) return null;
		return permissionDeployDtos.stream().map(this::convertJpmPermissionDtoToAppFunctionDto).collect(Collectors.toList()).toArray(new AppFunctionDeployDto[0]);
	}
	
	@Override
	public AppFunctionDeployDto getById(Long id){
	    JpmPermissionDeployDto permissionDeployDto = jpmPermissionDeployService.getJpmPermissionDeployDtoById(id);
		return this.convertJpmPermissionDtoToAppFunctionDto(permissionDeployDto);
	}

    private AppFunctionDeployDto convertJpmPermissionDtoToAppFunctionDto(JpmPermissionDeployDto permissionDto) {
        if (null == permissionDto)
            return null;
        AppFunctionDeployDto appFunctionDto = objectMapper.convertValue(permissionDto, AppFunctionDeployDto.class);
        appFunctionDto.setId(permissionDto.getPermissionDeployId());
        appFunctionDto.setCode(permissionDto.getPermissionCode());
        appFunctionDto.setName(permissionDto.getPermissionName());
        return appFunctionDto;
    }
}

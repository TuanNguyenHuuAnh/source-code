package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppFunctionDto;
import vn.com.unit.process.workflow.repository.AppFunctionRepository;
import vn.com.unit.process.workflow.service.AppFunctionService;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.service.JpmPermissionService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppFunctionServiceImpl implements AppFunctionService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppFunctionRepository appFunctionRepository;
    
    @Autowired
    private JpmPermissionService jpmPermissionService;
    
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public AppFunctionDto[] getListJpmFunctionByProcessId(Long jpmProcessId) {
        List<JpmPermissionDto> permissionDtos = jpmPermissionService.getPermissionDtosByProcessId(jpmProcessId);
        return permissionDtos.stream().map(this::convertJpmPermissionDtoToAppFunctionDto).collect(Collectors.toList())
                .toArray(new AppFunctionDto[0]);
    }

    @Override
    public AppFunctionDto getById(Long id) {
        JpmPermissionDto permissionDto = jpmPermissionService.getJpmPermissionDtoByPermissionId(id);
        return this.convertJpmPermissionDtoToAppFunctionDto(permissionDto);
    }

    @Override
    public JpmPermission saveJpmFunction(AppFunctionDto functionDto) throws Exception {
        JpmPermissionDto permissionDto = this.convertAppFunctionDtoToJpmPermissionDto(functionDto);
        return jpmPermissionService.saveJpmPermissionDto(permissionDto);
    }

    @Override
    public void deleteJpmFunction(Long functionId) {
        jpmPermissionService.deleteById(functionId);
    }

    @Override
    public boolean validateFunctionWithNameAndProcessId(Long functionId, String functionName, Long processId) {
        return appFunctionRepository.validateFunctionWithNameAndProcessId(functionId, functionName, processId);
    }

    @Override
    public boolean validateJpmFunctionUsing(Long functionId) {
        return appFunctionRepository.validateJpmFunctionUsing(functionId);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<JpmPermission> saveJpmFunctionByProcessId(List<AppFunctionDto> functionDtos, Long processId, Map<Long, Long> convertFunctionId) {
        List<JpmPermission> functions = new ArrayList<>();
        
//        String user = UserProfileUtils.getUserNameLogin();
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        
//        for(AppFunctionDto functionDto : functionDtos){
//            JpmPermission function = modelMapper.map(functionDto, JpmPermission.class);
//            function.setId(null);
//            function.setProcessId(processId);
//            function.setCreatedBy(user);
//            function.setCreatedDate(sysDate);
//            
//            appFunctionRepository.save(function);
//            functions.add(function);
//            
//            //add convert Id
//            if (null != convertFunctionId) {
//                Long key = functionDto.getId();
//                Long value = function.getId();
//                convertFunctionId.put(key, value);
//            }
//        }
        return functions;
    }

    @Override
    public List<AppFunctionDto> getListFunctionDefaultFreeform() {
        return appFunctionRepository.getListFunctionDefaultFreeform(ConstantCore.SYSTEM);
    }

    private AppFunctionDto convertJpmPermissionDtoToAppFunctionDto(JpmPermissionDto permissionDto) {
        if (null == permissionDto)
            return null;
        AppFunctionDto appFunctionDto = objectMapper.convertValue(permissionDto, AppFunctionDto.class);
        appFunctionDto.setId(permissionDto.getPermissionId());
        appFunctionDto.setCode(permissionDto.getPermissionCode());
        appFunctionDto.setName(permissionDto.getPermissionName());
        return appFunctionDto;
    }

    private JpmPermissionDto convertAppFunctionDtoToJpmPermissionDto(AppFunctionDto appFunctionDto) {
        if (null == appFunctionDto)
            return null;
        JpmPermissionDto permissionDto = objectMapper.convertValue(appFunctionDto, JpmPermissionDto.class);
        permissionDto.setPermissionId(appFunctionDto.getId());
        permissionDto.setPermissionCode(appFunctionDto.getCode());
        permissionDto.setPermissionName(appFunctionDto.getName());
        return permissionDto;
    }
}

package vn.com.unit.process.workflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.process.workflow.dto.AppParamDto;
import vn.com.unit.process.workflow.repository.AppParamRepository;
import vn.com.unit.process.workflow.service.AppParamService;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.entity.JpmParam;
import vn.com.unit.workflow.service.JpmParamService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppParamServiceImpl implements AppParamService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppParamRepository appParamRepository;
    
    @Autowired
    private JpmParamService jpmParamService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public AppParamDto[] getListJpmParamByProcessId(Long jpmProcessId) {
        List<JpmParamDto> paramDtos = jpmParamService.getParamDtosByProcessId(jpmProcessId);
        return paramDtos.stream().map(this::convertJpmParamDtoToAppParamDto).collect(Collectors.toList()).toArray(new AppParamDto[0]);
    }

    @Override
    public AppParamDto getById(Long id) {
        JpmParamDto paramDto = jpmParamService.getJpmParamDtoById(id);
        return this.convertJpmParamDtoToAppParamDto(paramDto);
    }

    @Override
    public JpmParam saveJpmParam(AppParamDto paramDto) throws Exception {
        JpmParamDto jpmParamDto = this.convertAppParamDtoToJpmParamDto(paramDto);
        return jpmParamService.saveJpmParamDto(jpmParamDto);
    }

    @Override
    public void deleteJpmParam(Long paramId) {
        jpmParamService.deleteById(paramId);
    }

    @Override
    public boolean validateParamWithNameAndProcessId(Long paramId, String fieldName, Long processId) {
        return appParamRepository.validateParamWithNameAndProcessId(paramId, fieldName, processId);
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<JpmParam> saveJpmParamByProcessId(List<AppParamDto> paramDtoList, Long processId) {
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
//        
//        ModelMapper mapper = new ModelMapper();
//        PropertyMap<AppParamDto, JpmParam> processMapping = new PropertyMap<AppParamDto, JpmParam>() {
//            @Override
//            protected void configure() {
//                map().setId(null);
//                map().setProcessId(processId);
//                map().setCreatedBy(user);
//                map().setCreatedDate(sysDate);
//            }
//        };
//        mapper.addMappings(processMapping);

        List<JpmParam> res = null;
//        if(paramDtoList != null){
//            List<AppParamDto> currentParamDtoList = appParamRepository.getListJpmParamByProcessId(processId);
//            List<JpmParam> paramList = new ArrayList<JpmParam>();
//            
//            Map<String, AppParamDto> mapParam = new HashMap<>();
//            for(AppParamDto paramDto : currentParamDtoList){
//                mapParam.put(paramDto.getFieldName(), paramDto);
//            }
//            
//            for(AppParamDto paramDto : paramDtoList){
//                if(null == mapParam.remove(paramDto.getFieldName())){
//                    JpmParam param = mapper.map(paramDto, JpmParam.class);
//                    
//                    paramList.add(param);
//                }
//            }
//            res = (List<JpmParam>) appParamRepository.save(paramList);
//        }
        return res;
    }

    private AppParamDto convertJpmParamDtoToAppParamDto(JpmParamDto paramDto) {
        if (null == paramDto)
            return null;
        AppParamDto appParamDto = objectMapper.convertValue(paramDto, AppParamDto.class);
        appParamDto.setId(paramDto.getParamId());
        appParamDto.setConfigSteps(paramDto.getParamConfigDtos());
        return appParamDto;
    }

    private JpmParamDto convertAppParamDtoToJpmParamDto(AppParamDto appParamDto) {
        if (null == appParamDto)
            return null;
        JpmParamDto paramDto = objectMapper.convertValue(appParamDto, JpmParamDto.class);
        paramDto.setParamId(appParamDto.getId());
        paramDto.setParamConfigDtos(appParamDto.getConfigSteps());
        return paramDto;
    }
}

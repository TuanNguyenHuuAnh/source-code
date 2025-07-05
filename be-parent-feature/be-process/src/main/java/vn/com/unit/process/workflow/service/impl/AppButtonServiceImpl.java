package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.process.workflow.repository.AppButtonDefaultRepository;
import vn.com.unit.process.workflow.repository.AppButtonRepository;
import vn.com.unit.process.workflow.service.AppButtonService;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.service.JpmButtonService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppButtonServiceImpl implements AppButtonService {


    @Autowired
    private AppButtonRepository appButtonRepository;


    @Autowired
    private AppButtonDefaultRepository appButtonDefaultRepository;
    
    
    @Autowired
    private JpmButtonService jpmButtonService; 
    
    @Autowired
    private ObjectMapper objectMapper; 

    @Override
    public AppButtonDto[] getListJpmButtonByProcessId(Long jpmProcessId) {
        List<JpmButtonDto> buttonDtos = jpmButtonService.getButtonDtosByProcessId(jpmProcessId);
        return buttonDtos.stream().map(this::convertJpmButtonDtoToAppButtonDto).collect(Collectors.toList()).toArray(new AppButtonDto[0]);
    }

    @Override
    public AppButtonDto getById(Long id) {
        JpmButtonDto buttonDto = jpmButtonService.getJpmButtonDtoById(id);
        return this.convertJpmButtonDtoToAppButtonDto(buttonDto);
    }

    @Override
    public JpmButton saveJpmButton(AppButtonDto buttonDto) throws Exception {
        JpmButtonDto jpmButtonDto = this.convertAppButtonDtoToJpmButtonDto(buttonDto);
        return  jpmButtonService.saveJpmButtonDto(jpmButtonDto);
    }

    @Override
    public void deleteJpmButton(Long buttonId) {
        jpmButtonService.deleteById(buttonId);
    }

    @Override
    public boolean validateButtonWithNameAndProcessId(Long buttonId, String buttonName, Long processId) {
        return appButtonRepository.validateButtonWithNameAndProcessId(buttonId, buttonName, processId);
    }

    @Override
    public boolean validateJpmButtonUsing(Long buttonId) {
        return appButtonRepository.validateJpmButtonUsing(buttonId);
    }

    @Override
    public List<AppButtonDto> getListButtonDefault() {
        List<AppButtonDto> buttonDtos = appButtonDefaultRepository.getListButtonDefault();
        
//        for(AppButtonDto buttonDto : buttonDtos){
////            Long buttonId = buttonDto.getId();
////            List<JpmButtonLang> buttonLangs = appButtonDefaultLangRepository.getLisJpmButtonDefaultLangByButtonIds(Arrays.asList(buttonId));
////            
////            buttonDto.setListJpmButtonLang(buttonLangs);
//        }
        
        return buttonDtos;
    }
    
    @Override
    public List<AppButtonDto> getListButtonDefaultByTypeAndKinds(String type, List<String> kinds) {
        return appButtonDefaultRepository.getListButtonDefaultByTypeAndKinds(type, kinds);
    }
    

    @Transactional
	@Override
	public List<JpmButton> saveJpmButtonByProcessId(List<AppButtonDto> buttonDtoList, Long processId, Map<Long, Long> convertButtonId) {
//		Date sysDate = CommonDateUtil.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
		
//		objectMapper mapper = new objectMapper();
//        PropertyMap<AppButtonDto, JpmButton> processMapping = new PropertyMap<AppButtonDto, JpmButton>() {
//            @Override
//            protected void configure() {
//            	map().setId(null);
//                map().setProcessId(processId);
//                map().setCreatedBy(user);
//                map().setCreatedDate(sysDate);
//            }
//        };
//        mapper.addMappings(processMapping);
        
        List<JpmButton> res = new ArrayList<>();
        
//        for(AppButtonDto buttonDto : buttonDtoList){
//            JpmButton button = mapper.map(buttonDto, JpmButton.class);
//            List<JpmButtonLang> buttonLangs = buttonDto.getListJpmButtonLang();
//            appButtonRepository.save(button);
//            
//            // save language
//            Long buttonId = button.getId();
//            for(JpmButtonLang buttonLang : buttonLangs){
//                buttonLang.setId(null);
//                buttonLang.setButtonId(buttonId);
//                buttonLang.setCreatedBy(user);
//                buttonLang.setCreatedDate(sysDate);
//                appButtonLangRepository.save(buttonLang);
//            }
//            res.add(button);
//            
//            // add convert
//            if(null != convertButtonId){
//                Long key = buttonDto.getId();
//                convertButtonId.put(key, buttonId);
//            }
//            
//            //reset id
//            buttonDto.setId(buttonId);
//        }
        
		return res;
	}

    /**
     * @author KhuongTH
     */
    @Override
    public List<AppButtonDto> getListButtonDefaultFreeform(String processType, String processKind) {
        List<AppButtonDto> buttonDtos = new ArrayList<>();
        
        buttonDtos = appButtonRepository.getListButtonDefaultFreeform(processType, processKind);
        
//        for(AppButtonDto buttonDto : buttonDtos){
////            Long buttonId = buttonDto.getId();
////            List<JpmButtonLang> buttonLangs = appButtonDefaultLangRepository.getListJpmButtonLangByButtonId(buttonId);
////            buttonDto.setListJpmButtonLang(buttonLangs);
//        }
        
        return buttonDtos;
    }

    @Override
    public List<Select2Dto> getSelect2DtoByProcess(Long processId, String lang) {
        return appButtonRepository.getSelect2DtoByProcess(processId, lang);
    }

    private AppButtonDto convertJpmButtonDtoToAppButtonDto(JpmButtonDto buttonDto) {
        if (null == buttonDto)
            return null;
        AppButtonDto appButtonDto = objectMapper.convertValue(buttonDto, AppButtonDto.class);

        appButtonDto.setId(buttonDto.getButtonId());
        appButtonDto.setListJpmButtonLang(buttonDto.getButtonLangs());
        appButtonDto.setOrders(buttonDto.getDisplayOrder());
        return appButtonDto;
    }

    private JpmButtonDto convertAppButtonDtoToJpmButtonDto(AppButtonDto appButtonDto) {
        if (null == appButtonDto)
            return null;
        JpmButtonDto buttonDto = objectMapper.convertValue(appButtonDto, JpmButtonDto.class);

        buttonDto.setButtonId(appButtonDto.getId());
        buttonDto.setButtonLangs(appButtonDto.getListJpmButtonLang());
        buttonDto.setDisplayOrder(appButtonDto.getOrders());
        return buttonDto;
    }
}

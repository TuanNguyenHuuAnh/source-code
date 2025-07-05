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
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.process.workflow.repository.AppStatusRepository;
import vn.com.unit.process.workflow.repository.AppStatusSystemDefaultRepository;
import vn.com.unit.process.workflow.service.AppStatusService;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.service.JpmStatusService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStatusServiceImpl implements AppStatusService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppStatusRepository appStatusRepository;

//    @Autowired
//    AppStatusLangRepository appStatusLangRepository;

    @Autowired
    AppLanguageService languageService; 
    
    @Autowired
    AppStatusSystemDefaultRepository appStatusSystemDefaultRepository;
    
//    @Autowired
//    AppStatusSystemDefaultLangRepository appStatusSystemDefaultLangRepository;
    
    @Autowired
    private JpmStatusService jpmStatusService; 
    
    @Autowired
    private ObjectMapper objectMapper; 

    @Override
    public AppStatusDto[] getListJpmStatusByProcessId(Long jpmProcessId) {
        List<JpmStatusDto> statusDtos = jpmStatusService.getStatusDtosByProcessId(jpmProcessId);
        return statusDtos.stream().map(this::convertJpmStatusDtoToAppStatusDto).collect(Collectors.toList()).toArray(new AppStatusDto[0]);
    }

    @Override
    public AppStatusDto getById(Long id) {
        JpmStatusDto statusDto = jpmStatusService.getJpmStatusDtoById(id);
        return this.convertJpmStatusDtoToAppStatusDto(statusDto);
    }

    @Override
    public JpmStatus saveJpmStatus(AppStatusDto statusDto) throws Exception {
        JpmStatusDto jpmStatusDto = this.convertAppStatusDtoToJpmStatusDto(statusDto);
        return jpmStatusService.saveJpmStatusDto(jpmStatusDto);
    }

    @Override
    public void deleteJpmStatus(Long statusId) {
        jpmStatusService.deleteById(statusId);
    }

    @Override
    public boolean validateStatusWithCodeAndProcessId(Long statusId, String statusCode, Long processId) {
        return appStatusRepository.validateStatusWithCodeAndProcessId(statusId, statusCode, processId);
    }

    @Override
    public boolean validateJpmStatusUsing(Long statusId) {
        return appStatusRepository.validateJpmStatusUsing(statusId);
    }

    @Override
    public List<JpmStatus> saveJpmStatus(List<AppStatusDto> listJpmStatus, Long processId) {
//    	Date sysDate = CommonDateUtil.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
        
        // List Create, Update data
     	List<JpmStatus> result = new ArrayList<>();
//     	
//     	if( listJpmStatus != null ) {
//     		List<JpmStatus> jpmStatusObjectList = appStatusRepository.findJpmStatusByProcessId(processId);
//			HashMap<String,JpmStatus> jpmStatusObjectMap = convertListToMap(jpmStatusObjectList);
//			Iterable<JpmStatusSystemDefaultLang> listStatusDefaultLang = appStatusSystemDefaultLangRepository.findAll();
//			Map<String,List<JpmStatusLang>> mapLang = new HashMap<String, List<JpmStatusLang>>();
//			
//			for (AppStatusDto appStatusDto : listJpmStatus) {
//				String code = appStatusDto.getStatusCode();
//				
//				JpmStatus jpmStatusObject = jpmStatusObjectMap.remove(code);
//				if( jpmStatusObject == null ) {
//					jpmStatusObject = modelMapper.map(appStatusDto, JpmStatus.class);
//					jpmStatusObject.setProcessId(processId);
//					boolean isSystem = appStatusDto.getIsSystem();
//					if(isSystem){
//					    //Default System
//					    jpmStatusObject.setCreatedBy(ConstantCore.SYSTEM);
//					}else{
//					    jpmStatusObject.setCreatedBy(user);
//					}
//					jpmStatusObject.setCreatedDate(sysDate);
//					//put new status map.
//					mapLang.put(code, createStatusLangListDefault(jpmStatusObject, listStatusDefaultLang));
//				}
//				
//				result.add(jpmStatusObject);
//			}
//			
//			List<JpmStatus> resultStatusList = (List<JpmStatus>)appStatusRepository.save(result);
//			List<JpmStatusLang> listSaveLanguage = new ArrayList<>();
//			for(JpmStatus stt : resultStatusList){
//				List<JpmStatusLang> languageList = mapLang.remove(stt.getStatusCode());
//				
//				if( languageList != null ) {
//					for(JpmStatusLang lang : languageList){
//						lang.setStatusId(stt.getId());
//						
//						listSaveLanguage.add(lang);
//					}
//				}
//			}
//			if(listSaveLanguage.size()>0){
//				appStatusLangRepository.save(listSaveLanguage);
//			}
//			// Delete list jpmStatus not matched
//			appStatusRepository.delete(jpmStatusObjectMap.values());
//     	}
     	
        return result;
    }
    // create Default status language
//    private List<JpmStatusLang> createStatusLangListDefault(JpmStatus jpmStatus, Iterable<JpmStatusSystemDefaultLang> listStatusDefaultLang){
//        
//        List<JpmStatusLang> resultList = new ArrayList<>();
//        String typeStatus = jpmStatus.getCreatedBy();
//        
//        if(ConstantCore.SYSTEM.equals(typeStatus)){
//            String statusCode = jpmStatus.getStatusCode();
//            for(JpmStatusSystemDefaultLang statusDefaultLang : listStatusDefaultLang){
//                String statusLangCode = statusDefaultLang.getStatusCode();
//                if(statusLangCode != null && statusLangCode.equals(statusCode)){
//                    JpmStatusLang statusLang = modelMapper.map(statusDefaultLang, JpmStatusLang.class);
//                    statusLang.setId(null);
//                    resultList.add(statusLang);
//                }
//            }
//        }else{
//            String name = jpmStatus.getStatusName();
//        	List<LanguageDto> languageList = languageService.getLanguageDtoList();
//            List<String> listCodeLanguage = languageList.stream().map(lang -> lang.getCode()).sorted().collect(Collectors.toList());
//            
//            for(String code : listCodeLanguage){
//            	JpmStatusLang sttLang = new JpmStatusLang();
//            	sttLang.setLangCode(code);
//            	sttLang.setStatusName(name);
//            	resultList.add(sttLang);
//            }
//        }
//        return resultList;
//    }
    
	@Override
	public JpmStatus getByBusinessCodeAndStatusCode(Long processId) {
		return appStatusRepository.getStatusByProcessId(processId);
	}

//    /**
//	 * Convert List<JpmStatus> to Map<String,JpmStatus> with key is code
//	 * 
//	 * @param jpmStatusList
//	 * 			type List<JpmStatus>
//	 * @return HashMap<String,JpmStatus>
//	 * @author KhoaNA
//	 */
//	private HashMap<String,JpmStatus> convertListToMap(List<JpmStatus> jpmStatusList) {
//		HashMap<String, JpmStatus> result = new HashMap<>();
//		
//		if( jpmStatusList != null ) {
//			for (JpmStatus jpmStatus : jpmStatusList) {
//				String key = jpmStatus.getStatusCode();
//				result.put(key, jpmStatus);
//			}
//		}
//		
//		return result;
//	}

    /**
     * @author KhuongTH
     */
    @Override
    public List<JpmStatus> saveJpmStatusByProcessId(List<AppStatusDto> listJpmStatus, Long processId,  Map<Long, Long> convertStatusId) {
        List<JpmStatus> statuses = new ArrayList<>();
        
//        String user = UserProfileUtils.getUserNameLogin();
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        
//        for(AppStatusDto statusDto : listJpmStatus){
//            JpmStatus status = modelMapper.map(statusDto, JpmStatus.class);
//            status.setId(null);
//            status.setProcessId(processId);
//            status.setCreatedBy(ConstantCore.SYSTEM);
//            status.setCreatedDate(sysDate);
//            status.setUpdatedBy(user);
//            status.setUpdatedDate(sysDate);
//
//            appStatusRepository.save(status);
//            statuses.add(status);
//            
//            // save language
//            Long statusId = status.getId();
//            List<JpmStatusLang> statusLangs = statusDto.getListJpmStatusLang();
//            for(JpmStatusLang statusLang : statusLangs){
//                statusLang.setId(null);
//                statusLang.setStatusId(statusId);
//                statusLang.setCreatedBy(user);
//                statusLang.setCreatedDate(sysDate);
//                
//                appStatusLangRepository.save(statusLang);
//            }
//
//            // add convert id
//            if (null != convertStatusId) {
//                Long key = statusDto.getId();
//                convertStatusId.put(key, statusId);
//            }
//            
//            //reset id
//            statusDto.setId(statusId);
//        }
        return statuses;
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<AppStatusDto> getListStatusDefaultFreeform() {
        List<AppStatusDto> statusDtos = new ArrayList<>();
        statusDtos = appStatusRepository.getListStatusDefaultFreeform(ConstantCore.SYSTEM);
        
//        for(AppStatusDto statusDto : statusDtos){
//            Long statusId = statusDto.getId();
////            List<JpmStatusLang> statusLangs = appStatusLangRepository.getListJpmStatusLangByStatusId(statusId);
////            statusDto.setListJpmStatusLang(statusLangs);
//        }
        
        return statusDtos;
    }
    
    private AppStatusDto convertJpmStatusDtoToAppStatusDto(JpmStatusDto statusDto) {
        if (null == statusDto)
            return null;
        AppStatusDto appStatusDto = objectMapper.convertValue(statusDto, AppStatusDto.class);
        appStatusDto.setId(statusDto.getStatusId());
        appStatusDto.setListJpmStatusLang(statusDto.getStatusLangs());
        return appStatusDto;
    }

    private JpmStatusDto convertAppStatusDtoToJpmStatusDto(AppStatusDto appStatusDto) {
        if (null == appStatusDto)
            return null;
        JpmStatusDto statusDto = objectMapper.convertValue(appStatusDto, JpmStatusDto.class);
        statusDto.setStatusId(appStatusDto.getId());
        statusDto.setStatusLangs(appStatusDto.getListJpmStatusLang());
        return statusDto;
    }
}

package vn.com.unit.process.workflow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.process.admin.repository.AppStepLangRepository;
import vn.com.unit.process.workflow.dto.AppButtonForStepDto;
import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.process.workflow.repository.AppStepRepository;
import vn.com.unit.process.workflow.service.AppStepService;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.entity.JpmStep;
import vn.com.unit.workflow.service.JpmButtonForStepService;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStepService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStepServiceImpl implements AppStepService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppStepRepository appStepRepository;

    @Autowired
    AppStepLangRepository appStepLangRepository;

//    @Autowired
//    AppButtonForStepRepository appButtonForStepRepository;

    @Autowired
    private JpmButtonForStepService JpmButtonForStepService;

    @Autowired
    AppLanguageService languageService;

    @Autowired
    private JpmStepService jpmStepService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public AppStepDto getById(Long id) {
        JpmStepDto stepDto = jpmStepService.getJpmStepDtoById(id);
        return this.convertJpmStepDtoToAppStepDto(stepDto);
    }

    @Override
    public JpmStep saveJpmStep(AppStepDto appStepDto) {
        // Date sysDate = CommonDateUtil.getSystemDateTime();
        // String user = UserProfileUtils.getUserNameLogin();
        //
        // JpmStep objectSave = modelMapper.map(appStepDto, JpmStep.class);
        // Long id = objectSave.getId();
        // if (id == null) {
        // objectSave.setCreatedBy(user);
        // objectSave.setCreatedDate(sysDate);
        // } else {
        // JpmStep objectCurrent = appStepRepository.getById(id);
        // // Keep value not change
        // String createdBy = objectCurrent.getCreatedBy();
        // Date createdDate = objectCurrent.getCreatedDate();
        // String stepType = objectCurrent.getStepType();
        //
        // objectSave.setCreatedBy(createdBy);
        // objectSave.setCreatedDate(createdDate);
        // objectSave.setStepType(stepType);
        //
        // // manual handling set data
        // objectSave.setUpdatedBy(user);
        // objectSave.setUpdatedDate(sysDate);
        // }
        //
        // appStepRepository.save(objectSave);
        //
        // // Save list button
        // // Delete existing buttons
        // AppButtonForStepDto[] jpmButtonForStepDtoList = appStepDto.getListJpmButton();
        //
        // List<Long> removedButtonIds = new ArrayList<>();
        // if( jpmButtonForStepDtoList != null ) {
        // removedButtonIds = Arrays.stream(jpmButtonForStepDtoList).filter(dto -> dto.getIsDeleted())
        // .map(dto -> dto.getId()).collect(Collectors.toList());
        // }
        //
        // if (removedButtonIds.size() > 0)
        // appButtonForStepRepository.removeJpmButtonForStepByIds(objectSave.getId(),
        // removedButtonIds, user, sysDate);
        //
        // // Update existing / create new buttons
        // List<JpmButtonForStep> oldButton = new ArrayList<>();
        // if( jpmButtonForStepDtoList != null ) {
        // oldButton = Arrays.stream(jpmButtonForStepDtoList)
        // .filter(dto -> !dto.getIsDeleted())
        // .map(dto -> modelMapper.map(dto, JpmButtonForStep.class))
        // .collect(Collectors.toList());
        // }
        //
        // List<Long> listButtonDbIds = oldButton.stream().map(dto -> dto.getId()).filter(Objects::nonNull).collect(Collectors.toList());
        //
        // List<JpmButtonForStep> listButtonDb = new ArrayList<>();
        // if (listButtonDbIds.size() > 0)
        // listButtonDb = appButtonForStepRepository.getButtonForStepByIds(listButtonDbIds);
        //
        // for (JpmButtonForStep btn : oldButton) {
        // JpmButtonForStep dbButton = listButtonDb.stream().filter(dto -> dto.getId() == btn.getId()).findFirst().orElse(null);
        // if (dbButton == null) {
        // // new button
        // btn.setCreatedBy(user);
        // btn.setCreatedDate(sysDate);
        // btn.setDeletedBy(null);
        // btn.setDeletedDate(null);
        // } else {
        // btn.setCreatedBy(dbButton.getCreatedBy());
        // btn.setCreatedDate(dbButton.getCreatedDate());
        // }
        //
        // btn.setUpdatedBy(user);
        // btn.setUpdatedDate(sysDate);
        // btn.setStepId(objectSave.getId());
        // }
        // appButtonForStepRepository.save(oldButton);
        //
        // id = objectSave.getId();
        // appStepDto.setId(id);
        //
        // // Update language
        // List<Long> existingIds = appStepDto.getListJpmStepLang().stream().map(lang -> lang.getId()).collect(Collectors.toList());
        // existingIds.removeIf(Objects::isNull);
        //
        // List<JpmStepLang> listExistingLang = new ArrayList<>();
        // if (existingIds.size() > 0)
        // listExistingLang = appStepLangRepository.getListJpmStepLangByIds(existingIds);
        //
        // List<JpmStepLang> listLangSaved = new ArrayList<>();
        //
        // for (JpmStepLang stepLang : appStepDto.getListJpmStepLang()) {
        // JpmStepLang finalStepLang = stepLang;
        // List<JpmStepLang> existingLang = listExistingLang.stream()
        // .filter(lang -> Objects.equals(lang.getId(),finalStepLang.getId())).collect(Collectors.toList());
        //
        // if (CollectionUtils.isEmpty(existingLang)) {
        // // Create new
        // stepLang.setStepId(id);
        // stepLang.setCreatedBy(user);
        // stepLang.setCreatedDate(sysDate);
        // } else {
        // // Update
        // existingLang.get(0).setStepName(stepLang.getStepName());
        // stepLang = modelMapper.map(existingLang.get(0), JpmStepLang.class);
        // }
        // stepLang.setUpdatedBy(user);
        // stepLang.setUpdatedDate(sysDate);
        //
        // listLangSaved.add(stepLang);
        // }
        //
        // appStepLangRepository.save(listLangSaved);

        AppButtonForStepDto[] jpmButtonForStepDtoList = appStepDto.getListJpmButton();

        List<Long> removedButtonIds = new ArrayList<>();
        if (jpmButtonForStepDtoList != null) {
            removedButtonIds = Arrays.stream(jpmButtonForStepDtoList).filter(AppButtonForStepDto::getIsDeleted)
                    .map(AppButtonForStepDto::getId).collect(Collectors.toList());
            appStepDto.setListJpmButton(Arrays.stream(jpmButtonForStepDtoList).filter(dto -> !dto.getIsDeleted())
                    .collect(Collectors.toList()).toArray(new AppButtonForStepDto[0]));
        }

        if (CommonCollectionUtil.isNotEmpty(removedButtonIds)) {
            for (Long removedButtonId : removedButtonIds) {
                JpmButtonForStepService.deleteById(removedButtonId);
            }
        }

        JpmStepDto stepDto = this.convertAppStepDtoToJpmStepDto(appStepDto);
        JpmStep jpmStep = jpmStepService.saveJpmStepDto(stepDto);
        appStepDto.setId(jpmStep.getId());
        return jpmStep;
    }

    @Override
    public void deleteJpmStep(Long id) {
        jpmStepService.deleteById(id);
    }

    @Override
    public List<JpmStep> saveJpmStepByProcessId(Map<String, List<AppStepDto>> jpmStepDtoMap, List<JpmStatus> jpmStatusList,
            Long processId) {
        // Date sysDate = CommonDateUtil.getSystemDateTime();
        // String user = UserProfileUtils.getUserNameLogin();
        //
        // HashMap<String,JpmStatus> jpmStatusObjectMap = convertJpmStatusListToMap(jpmStatusList);
        //
        // HashMap<String, List<JpmStepLang>> jpmStepLangMap = new HashMap<>();
        // // List Create, Update data
        List<JpmStep> result = new ArrayList<JpmStep>();
        //
        // if( jpmStepDtoMap != null ) {
        // List<JpmStep> jpmStepObjectList = appStepRepository.findJpmStepByProcessId(processId);
        // HashMap<String,JpmStep> jpmStepObjectMap = convertListToMap(jpmStepObjectList);
        //
        // for (String statusCode : jpmStepDtoMap.keySet()) {
        // List<AppStepDto> jpmStepDtoList = jpmStepDtoMap.get(statusCode);
        // JpmStatus jpmStatusObject = jpmStatusObjectMap.get(statusCode);
        //
        // if( jpmStepDtoList != null ) {
        // for (AppStepDto appStepDto : jpmStepDtoList) {
        // String code = appStepDto.getCode();
        // String name = appStepDto.getName();
        //
        // JpmStep jpmStepObject = jpmStepObjectMap.remove(code);
        // if( jpmStepObject != null ) {
        // jpmStepObject.setName(name);
        // jpmStepObject.setUpdatedBy(user);
        // jpmStepObject.setUpdatedDate(sysDate);
        // } else {
        // jpmStepObject = modelMapper.map(appStepDto, JpmStep.class);
        // jpmStepObject.setProcessId(processId);
        // jpmStepObject.setCreatedBy(user);
        // jpmStepObject.setCreatedDate(sysDate);
        //
        // //create default lang
        // List<JpmStepLang> langList = createListStepLangFormStepName(name);
        // jpmStepLangMap.put(code, langList);
        // }
        //
        // if( jpmStatusObject != null ) {
        // Long jpmStatusId = jpmStatusObject.getId();
        // jpmStepObject.setStatusId(jpmStatusId);
        // }
        //
        // result.add(jpmStepObject);
        // }
        // }
        // }
        //
        // appStepRepository.save(result);
        // //Save lang default
        // List<JpmStepLang> saveLangList = new ArrayList<>();
        // for(JpmStep step : result){
        // String stepCode = step.getCode();
        // Long stepId = step.getId();
        // List<JpmStepLang> subStepList = jpmStepLangMap.remove(stepCode);
        //
        // if( subStepList != null && !subStepList.isEmpty() ) {
        // for(JpmStepLang item : subStepList){
        // item.setStepId(stepId);
        // saveLangList.add(item);
        // }
        // }
        // }
        // if(!saveLangList.isEmpty()){
        // appStepLangRepository.save(saveLangList);
        // }
        //
        // // Delete list jpmStep not matched
        // appStepRepository.delete(jpmStepObjectMap.values());
        // }

        return result;
    }

//    /**
//     * Convert List<JpmStep> to Map<String,JpmStep> with key is code
//     * 
//     * @param jpmStepList
//     *            type List<JpmStep>
//     * @return HashMap<String,JpmStep>
//     * @author KhoaNA
//     */
//    private HashMap<String, JpmStep> convertListToMap(List<JpmStep> jpmStepList) {
//        HashMap<String, JpmStep> result = new HashMap<>();
//        //
//        // if( jpmStepList != null ) {
//        // for (JpmStep jpmStep : jpmStepList) {
//        // String key = jpmStep.getCode();
//        // result.put(key, jpmStep);
//        // }
//        // }
//        //
//        return result;
//    }
//
//    /**
//     * Convert List<JpmStatus> to Map<String,JpmStatus> with key is code
//     * 
//     * @param jpmStatusList
//     *            type List<JpmStatus>
//     * @return HashMap<String,JpmStatus>
//     * @author KhoaNA
//     */
//    private HashMap<String, JpmStatus> convertJpmStatusListToMap(List<JpmStatus> jpmStatusList) {
//        HashMap<String, JpmStatus> result = new HashMap<>();
//
//        if (jpmStatusList != null) {
//            for (JpmStatus jpmStatus : jpmStatusList) {
//                String key = jpmStatus.getStatusCode();
//                result.put(key, jpmStatus);
//            }
//        }
//
//        return result;
//    }

    @Override
    public List<AppStepDto> getJpmStepDtoDetailByProcessId(Long processId, String lang) {
        List<AppStepDto> listJpmStepDto = appStepRepository.findJpmStepDtoDetailByProcessId(processId, lang,
                ConstantDisplayType.J_PRP_STATUS_001.toString());
        // calculate countButton
        String stepCode = null;
        List<AppStepDto> listStepAndButtonByCode = new ArrayList<>();
        Map<String, List<AppStepDto>> mapStep = new HashMap<>();
        for (AppStepDto item : listJpmStepDto) {
            String iCode = item.getCode();
            if (!iCode.equals(stepCode) && stepCode != null) {
                mapStep.put(stepCode, listStepAndButtonByCode);
                listStepAndButtonByCode = new ArrayList<>();
            } else {
                if (stepCode != null) {
                    item.setHidden(true); // set hidden;
                }
            }

            listStepAndButtonByCode.add(item);
            stepCode = iCode;
        }
        mapStep.put(stepCode, listStepAndButtonByCode); // add final step

        for (AppStepDto item : listJpmStepDto) {
            String iCode = item.getCode();
            List<AppStepDto> listButton = mapStep.get(iCode);
            int countButton = listButton.size();

            item.setCountButton(countButton);
        }
        return listJpmStepDto;
    }

    @Override
    public List<JpmStep> getJpmStepByProcessId(Long processId) {
        // return appStepRepository.findJpmStepByProcessId(processId);
        return null;
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<AppStepDto> getListStepDefaultFreeform(String processType, String processKind) {
        List<AppStepDto> stepDtos = new ArrayList<>();
        stepDtos = appStepRepository.getListStepDefaultFreeform(ConstantCore.SYSTEM);

//        for (AppStepDto stepDto : stepDtos) {
//            Long stepId = stepDto.getId();
//            // AppButtonForStepDto[] buttonForSteps = appButtonForStepRepository.getListButtonForStepDefaultFreeform(stepId, processType,
//            // processKind);
//            // List<JpmStepLang> stepLangs = appStepLangRepository.getListJpmStepLangByStepId(stepId);
//            //
//            // stepDto.setListJpmButton(buttonForSteps);
//            // stepDto.setListJpmStepLang(stepLangs);
//        }

        return stepDtos;
    }

    /** === BEGIN PRIVATE === */
    /**
     * create List Step Lang Default EfoForm Step Name When Create New Process
     * 
     * @param stepName
     * @return List<JpmStepLang>
     * 
     * @author KhuongTH
     */
    // private List<JpmStepLang> createListStepLangFormStepName(String stepName){
    // List<JpmStepLang> resList = new ArrayList<JpmStepLang>();
    // Date sysDate = CommonDateUtil.getSystemDateTime();
    // String user = UserProfileUtils.getUserNameLogin();
    //
    // List<LanguageDto> languageList = languageService.getLanguageDtoList();
    // List<String> listCodeLanguage = languageList.stream().map(lang -> lang.getCode()).sorted().collect(Collectors.toList());
    // for(String code : listCodeLanguage){
    // JpmStepLang stepLang = new JpmStepLang();
    // stepLang.setLangCode(code);
    // stepLang.setCreatedBy(user);
    // stepLang.setCreatedDate(sysDate);
    // stepLang.setStepName(stepName);
    //
    // resList.add(stepLang);
    // }
    // return resList;
    // }

    private AppStepDto convertJpmStepDtoToAppStepDto(JpmStepDto stepDto) {
        if (null == stepDto)
            return null;
        AppStepDto appStepDto = objectMapper.convertValue(stepDto, AppStepDto.class);
        appStepDto.setId(stepDto.getStepId());
        appStepDto.setListJpmStepLang(stepDto.getStepLangs());
        appStepDto.setListJpmButton(this.convertJpmButtonForStepDtosToAppButtonForStepDtos(stepDto.getButtonForStepDtos()));
        appStepDto.setName(stepDto.getStepName());
        appStepDto.setCode(stepDto.getStepCode());
        appStepDto.setCommonStatusCode(stepDto.getCommonStatusCode());
        return appStepDto;
    }

    private JpmStepDto convertAppStepDtoToJpmStepDto(AppStepDto appStepDto) {
        if (null == appStepDto)
            return null;
        Map<String, Long> statusCommonConverter = jpmStatusCommonService.getStatusCommonIdConverter();
        JpmStepDto stepDto = objectMapper.convertValue(appStepDto, JpmStepDto.class);
        stepDto.setStepId(appStepDto.getId());
        stepDto.setStepLangs(appStepDto.getListJpmStepLang());
        stepDto.setButtonForStepDtos(this.convertAppButtonForStepDtosToJpmButtonForStepDtos(appStepDto.getListJpmButton()));
        stepDto.setStepName(appStepDto.getName());
        stepDto.setStepCode(appStepDto.getCode());
        stepDto.setCommonStatusCode(appStepDto.getCommonStatusCode());
        stepDto.setCommonStatusId(statusCommonConverter.getOrDefault(appStepDto.getCommonStatusCode(), 0L));
        return stepDto;
    }

    private AppButtonForStepDto[] convertJpmButtonForStepDtosToAppButtonForStepDtos(List<JpmButtonForStepDto> buttonForStepDtos) {
        if (CommonCollectionUtil.isEmpty(buttonForStepDtos))
            return null;
        List<AppButtonForStepDto> appButtonForStepDtos = buttonForStepDtos.stream().map(a -> {
            AppButtonForStepDto appButtonForStepDto = objectMapper.convertValue(a, AppButtonForStepDto.class);
            appButtonForStepDto.setIsAuthenticate(a.isOptionAuthenticate());
            appButtonForStepDto.setIsExportPdf(a.isOptionExportPdf());
            appButtonForStepDto.setIsSave(a.isOptionSaveForm());
            appButtonForStepDto.setIsSaveEform(a.isOptionSaveEform());
            appButtonForStepDto.setIsSign(a.isOptionSigned());
            appButtonForStepDto.setDisplayHistoryApprove(a.isOptionShowHistory());
            appButtonForStepDto.setFunctionId(a.getPermissionId());
            appButtonForStepDto.setFieldSign(a.isOptionFillToEform());
            return appButtonForStepDto;
        }).collect(Collectors.toList());
        return appButtonForStepDtos.toArray(new AppButtonForStepDto[0]);
    }

    private List<JpmButtonForStepDto> convertAppButtonForStepDtosToJpmButtonForStepDtos(AppButtonForStepDto[] appButtonForStepDtos) {
        if (appButtonForStepDtos == null || appButtonForStepDtos.length == 0)
            return null;
        List<JpmButtonForStepDto> buttonForStepDtos = Arrays.stream(appButtonForStepDtos).map(a -> {
            JpmButtonForStepDto buttonForStepDto = objectMapper.convertValue(a, JpmButtonForStepDto.class);
            buttonForStepDto.setOptionAuthenticate(a.getIsAuthenticate());
            buttonForStepDto.setOptionExportPdf(a.getIsExportPdf());
            buttonForStepDto.setOptionSaveForm(a.getIsSave());
            buttonForStepDto.setOptionSaveEform(a.getIsSaveEform());
            buttonForStepDto.setOptionSigned(a.getIsSign());
            buttonForStepDto.setOptionShowHistory(a.getDisplayHistoryApprove());
            buttonForStepDto.setPermissionId(a.getFunctionId());
            buttonForStepDto.setOptionFillToEform(a.getFieldSign());
            return buttonForStepDto;
        }).collect(Collectors.toList());
        return buttonForStepDtos;
    }

    /** === END PRIVATE === */
}

package vn.com.unit.process.admin.sla.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaInfoDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSearchDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation;
import vn.com.unit.ep2p.admin.sla.repository.SlaInfoRepository;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
import vn.com.unit.process.admin.sla.service.SlaInfoService;
import vn.com.unit.process.admin.sla.service.SlaSettingService;
import vn.com.unit.process.admin.sla.service.SlaStepService;
import vn.com.unit.process.workflow.service.AppBusinessService;
import vn.com.unit.process.workflow.service.AppProcessDeployService;
import vn.com.unit.process.workflow.service.AppStepDeployService;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.sla.service.SlaConfigService;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.dto.JpmSlaInfoSearchDto;
import vn.com.unit.workflow.entity.JpmSlaInfo;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.service.impl.JpmSlaInfoServiceImpl;

@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaInfoServiceImpl extends JpmSlaInfoServiceImpl  implements SlaInfoService, AbstractCommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(SlaInfoService.class);

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private SlaInfoRepository infoRepository;

	@Autowired
	private JCommonService comService;

	@Autowired
	private SlaStepService stepService;

	@Autowired
	private AppStepDeployService jpmStepDeployService;
	
	@Autowired
	private AppBusinessService jpmBusinessService;
	
	@Autowired
	private AppProcessDeployService jpmProcessDeployService;
	
	@Autowired
	private CalendarTypeAppService calendarTypeService;
	
	@Autowired
    private TemplateService templateService;
	
	@Autowired
    private SlaSettingService slaSettingService;
	
	@Autowired
	private SlaConfigService slaConfigService;
	
//	@Autowired
//	private JpmSlaInfoService jpmSlaInfoService;
	
//	@Autowired
//    private JpmSlaConfigService jpmSlaConfigService;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Override
	public PageWrapper<JpmSlaInfoDto> search(SlaSearchDto searchDto, int page, int pageSize) throws DetailException {

		// Get listPageSize, sizeOfPage
		List<Integer> listPageSize = systemConfig.getListPage(pageSize);
		int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
		
		PageWrapper<JpmSlaInfoDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);
		if (null == searchDto) {
			searchDto = new SlaSearchDto();
		}
		
		/** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JpmSlaInfo.class, "sla_info");

          /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JpmSlaInfoSearchDto reqSearch = this.buildJpmSlaInfoSearchDto(commonSearch);
        
		int count = this.countBySearchCondition(reqSearch);
		List<JpmSlaInfoDto> result = new ArrayList<>();
		if (count > 0) {
			result = this.getJpmSlaInfoDtoListByCondition(reqSearch, pageableAfterBuild);
		}
		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	private JpmSlaInfoSearchDto buildJpmSlaInfoSearchDto(MultiValueMap<String, String> commonSearch) {
	    JpmSlaInfoSearchDto reqSearch = new JpmSlaInfoSearchDto();
        String name = CommonStringUtil.isNotBlank(commonSearch.getFirst("name")) ? commonSearch.getFirst("name")
                : DtsConstant.EMPTY;
        Long businessId = CommonStringUtil.isNotBlank(commonSearch.getFirst("businessId")) ? Long.valueOf(commonSearch.getFirst("businessId")) : null;
        Long processDeployId = CommonStringUtil.isNotBlank(commonSearch.getFirst("processDeployId")) ? Long.valueOf(commonSearch.getFirst("processDeployId")) : null;
        Long companyId = CommonStringUtil.isNotBlank(commonSearch.getFirst("companyId")) ? Long.valueOf(commonSearch.getFirst("companyId")) : null;

        reqSearch.setCompanyId(companyId);
        reqSearch.setBusinessId(businessId);
        reqSearch.setProcessDeployId(processDeployId);
        reqSearch.setName(name);
        
        return  reqSearch;
    }

    @Override
	public SlaInfoDto findById(Long id, Locale locale) {
		SlaInfoDto slaInfoDto = new SlaInfoDto();
		JpmSlaInfoDto jpmSlaInfoDto = this.getJpmSlaInfoDtoById(id);
		if(null != jpmSlaInfoDto) {
		    slaInfoDto = objectMapper.convertValue(jpmSlaInfoDto, SlaInfoDto.class);
		    List<SlaStepDto> slaStepDtoList = stepService.getListStepByInfoId(id, locale);
		    slaInfoDto.setStepList(slaStepDtoList);
		}

//		SlaInfo slInfo = infoRepository.findOne(id);
//		if (slInfo != null) {
//			NullAwareBeanUtils.copyPropertiesWONull(slInfo, slaInfoDto);
//
//			List<SlaStepDto> slaStepDtoList = stepService.getListStepByInfoId(id, locale);
//			if (slaInfoDto != null) {
//				slaInfoDto.setStepList(slaStepDtoList);
//			}
//		}

		return slaInfoDto;
	}

	@Override
	public boolean checkSlaInfoExist(Long companyId, String name) {
	    JpmSlaInfoSearchDto searchDto = new JpmSlaInfoSearchDto();
	    searchDto.setCompanyId(companyId);
	    searchDto.setName(name);
	    List<JpmSlaInfoDto> jpmSlaInfoDtoList = this.getJpmSlaInfoDtoListBySearchDto(searchDto);
		return CommonCollectionUtil.isNotEmpty(jpmSlaInfoDtoList);
	}

	@Override
	public SlaInfoDto saveSlaInfoDto(SlaInfoDto slaInfoDto) throws Exception {
		JpmSlaInfoDto jpmSlaInfoDto = objectMapper.convertValue(slaInfoDto, JpmSlaInfoDto.class);
		boolean isCreate = null == slaInfoDto.getId();
		this.saveJpmSlaInfoDto(jpmSlaInfoDto);
		Long jpmSlaInfoId = jpmSlaInfoDto.getId();
		Long businessId = slaInfoDto.getBusinessId();
        Long processDeployId = slaInfoDto.getProcessDeployId();
        Long calendarTypeId = slaInfoDto.getSlaCalendarTypeId();
		if(isCreate) {
		    List<JpmStepDeploy> stepDeployDtos = jpmStepDeployService.getJpmStepDeployByProcessId(slaInfoDto.getProcessDeployId());
		    if(CommonCollectionUtil.isNotEmpty(stepDeployDtos)) {
		        for (JpmStepDeploy jpmStepDeploy : stepDeployDtos) {
		            JpmSlaConfigDto slaStepDto = new JpmSlaConfigDto();
		            slaStepDto.setJpmSlaInfoId(jpmSlaInfoId);
	                slaStepDto.setBusinessId(businessId);
                    slaStepDto.setProcessDeployId(processDeployId);
	                slaStepDto.setCalendarTypeId(calendarTypeId);
	                slaStepDto.setStepDeployId(jpmStepDeploy.getId());
	                stepService.saveJpmSlaConfigDto(slaStepDto);
                }
		    }
		} else {
		    List<JpmSlaConfigDto> jpmSlaConfigDtoList = stepService.getJpmSlaConfigDtoByInfoIdAndLang(jpmSlaInfoId, null);
		    if(CommonCollectionUtil.isNotEmpty(jpmSlaConfigDtoList)) {
		       for (JpmSlaConfigDto jpmSlaConfigDto : jpmSlaConfigDtoList) {
		           if(null != jpmSlaConfigDto.getId()) {
		               jpmSlaConfigDto.setCalendarTypeId(calendarTypeId);
	                   stepService.saveJpmSlaConfigDto(jpmSlaConfigDto);
	                   SlaConfig slaConfig = slaConfigService.findOne(jpmSlaConfigDto.getId());
	                   slaConfig.setCalendarTypeId(calendarTypeId);
	                   slaConfigService.update(slaConfig);
		           }
		       }
		    }
		}
		slaInfoDto.setId(jpmSlaInfoId);
		return slaInfoDto;
	}
	
	@Override
	public void deleteSla(Long id, Locale locale) throws Exception {
	    JpmSlaInfo jpmSlaInfo = this.findOne(id);
	    jpmSlaInfo.setDeletedId(UserProfileUtils.getAccountId());
	    jpmSlaInfo.setDeletedDate(comService.getSystemDate());
	    this.update(jpmSlaInfo);
	    List<SlaStepDto> slaStepDtoList = stepService.getListStepByInfoId(id, locale);
	    if(CommonCollectionUtil.isNotEmpty(slaStepDtoList)) {
	        for (SlaStepDto slaStepDto : slaStepDtoList) {
	            stepService.deleteStepSetting(slaStepDto.getJpmSlaConfigId());
            }
	    }
	    
//		String lang = locale.getLanguage().toUpperCase();
//		List<SlaStepDto> lstStep = stepService.getListBySlaInfoId(id, lang);
//		String deletedBy = UserProfileUtils.getUserNameLogin();
//		Date deletedDate = comService.getSystemDateTime();
//
//		if (!CollectionUtils.isEmpty(lstStep)) {
//
//			for (SlaStepDto stepDto : lstStep) {
//				if (stepDto.getId() != null) {
//					stepService.deleteStepSetting(stepDto.getId());
//				}
//			}
//			stepService.deleteByInfoId(id, deletedBy, deletedDate);
//		}
//
//		SlaInfo slaInfo = infoRepository.findOne(id);
//		if (slaInfo != null) {
//			slaInfo.setDeletedBy(deletedBy);
//			slaInfo.setDeletedDate(deletedDate);
//			infoRepository.save(slaInfo);
//		}

	}
	
	@Override
	public Select2ResultDto getBusinessListForSelect(final Long companyId) throws Exception {
		List<Select2Dto> lst = jpmBusinessService.getSelect2DtoListCompanyId(companyId);
		Select2ResultDto obj = new Select2ResultDto();
		obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
	}
	
	@Override
	public Select2ResultDto getProcessListForSelect(final Long businessId, String lang) throws Exception {
		List<Select2Dto> lst = jpmProcessDeployService.getJpmProcessDtoTypeSelect2DtoByBusinessId(businessId, lang);
		Select2ResultDto obj = new Select2ResultDto();
		obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
	}
	
	@Override
	public Select2ResultDto getCalendarTypeListForSelect(final Long companyId) throws Exception {
		List<Select2Dto> lst = calendarTypeService.getCalendarTypeListByYearnCompany(companyId);
		Select2ResultDto obj = new Select2ResultDto();
		obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
	}

	@Override
	public Select2ResultDto getEmailTamplateList(String key, Long companyId) throws Exception {
		Select2ResultDto obj = new Select2ResultDto();
		List<Select2Dto> result = templateService.getTemplateByCompanyId(key, null, companyId);	
		if(result == null) {
			result = new ArrayList<>();
		}
		obj.setTotal(result.size());
        obj.setResults(result);
		return obj;
	}

	@Override
	public SlaConfiguration exportSlaConfiguration(Long companyId, Long processId) throws Exception {
		SlaConfiguration configuration = new SlaConfiguration();
		List<SlaConfiguration.SlaInfomation> slaInfos = getSlaListByCompanyIdAndProcessId(companyId, processId);
		if(slaInfos != null && !CollectionUtils.isEmpty(slaInfos)) {
			SlaConfiguration.SlaInfomation slaInfo = retrieveSlaInfomation(slaInfos.get(0));	
			configuration.setSlaInfo(slaInfo);		
		}	
		return configuration;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean importSlaConfiguration(SlaConfiguration sla) throws Exception {
//		SlaInfomation slaInfo = sla == null ? null : sla.getSlaInfo();
//		boolean validate = Boolean.FALSE;
//		if(slaInfo != null) {
//			Long comId = slaInfo.getCompanyId() == null ? new Long(0) : slaInfo.getCompanyId();
//			String name = (slaInfo.getSlaName() == null || StringUtils.isEmpty(slaInfo.getSlaName())) ? StringUtils.EMPTY : slaInfo.getSlaName();
//			validate = StringUtils.EMPTY.equals(name) ? (boolean)Boolean.FALSE : checkSlaInfoExist(comId, name);
//
//			// If exist
//			if (validate) {
//				// update sla configuration infomation
//				infoRepository.save(slaInfo);
//				List<SlaConfiguration.SlaInfomation.StepInfomation> stepList = (slaInfo.getStepInfomations() == null || CollectionUtils.isEmpty(slaInfo.getStepInfomations())) 
//						? null : slaInfo.getStepInfomations();
//				if(stepList != null) {
//					Iterable<SlaConfiguration.SlaInfomation.StepInfomation> stepIterable = stepList;
//					stepService.save(stepIterable);
//					List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> settingList = stepList.stream()
//				            .flatMap(step -> step.getSettingInfomations().stream()).collect(Collectors.toList());
//					if(settingList == null || CollectionUtils.isEmpty(settingList)) {
//						Iterable<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> settingIterable = settingList;
//						//stepRepository.save(settingIterable);
//						List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation.DetailSettingInfomation> settingAlertList = settingList.stream()
//					            .flatMap(setting -> setting.getDetailInfomations().stream()).collect(Collectors.toList());
//						if(settingList == null || CollectionUtils.isEmpty(settingList)) {
//							
//						}
//					}
//				}
//							
//			} else {}
//		}
//		
//		return validate;
	    return true;
	}
	
	@Override
	public List<SlaInfomation> getSlaListByCompanyIdAndProcessId(Long companyId, Long processId) throws Exception {
		return infoRepository.findListByCompanyIdAndProcessId(companyId, processId);
	}
	
	/**
	 * @param slaInfo
	 * @return
	 * @throws Exception
	 */
	private SlaConfiguration.SlaInfomation retrieveSlaInfomation(SlaConfiguration.SlaInfomation slaInfo) throws Exception {
		Long slaId = (slaInfo != null && slaInfo.getId() != null) ? slaInfo.getId() : null;
		if(slaId != null) {
			List<SlaConfiguration.SlaInfomation.StepInfomation> stepInfos = stepService.getStepListBySlaId(slaId);
			if(stepInfos != null && !CollectionUtils.isEmpty(stepInfos)) {
				stepInfos = retrieveStepInfomationList(stepInfos);
				slaInfo.setStepInfomations(stepInfos);
			}
		}
		return slaInfo;
	}
	
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<SlaConfiguration.SlaInfomation.StepInfomation> retrieveStepInfomationList(List<SlaConfiguration.SlaInfomation.StepInfomation> list) throws Exception {
		List<SlaConfiguration.SlaInfomation.StepInfomation> steplist = new ArrayList<SlaConfiguration.SlaInfomation.StepInfomation>();
		if(list != null && !CollectionUtils.isEmpty(list)) {
			for(SlaConfiguration.SlaInfomation.StepInfomation stepItem : list) {
				Long stepId = stepItem.getId() != null ? stepItem.getId() : null;
				if(stepId != null) {
					List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> settingInfos = slaSettingService.getSettingListBySlaStepId(stepId);
					settingInfos = retrieveSettingInfomationList(settingInfos);
					if(settingInfos != null && !CollectionUtils.isEmpty(settingInfos)) {
						stepItem.setSettingInfomations(settingInfos);
					}
				}
				steplist.add(stepItem);
			}
		}
		list.clear();
		list.addAll(steplist);
		return steplist;
	}
	
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> retrieveSettingInfomationList(List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> list) throws Exception {
		List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> settinglist = new ArrayList<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation>();
		if(list != null && !CollectionUtils.isEmpty(list)) {
			for(SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation settingItem : list) {
				Long settingId = settingItem.getId() != null ? settingItem.getId() : null;
				if(settingId != null) {
					List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation.DetailSettingInfomation> detailInfos = slaSettingService.getDetailListBySettingId(settingId);
					if(detailInfos != null && !CollectionUtils.isEmpty(detailInfos)) {	
						settingItem.setDetailInfomations(detailInfos);
					}
				}
				settinglist.add(settingItem);
			}
		}
		list.clear();
		list.addAll(settinglist);
		return list;
	}

	@Override
	public boolean checkProcessExist(Long processId) {
	    JpmSlaInfoSearchDto searchDto = new JpmSlaInfoSearchDto();
        searchDto.setProcessDeployId(processId);
        List<JpmSlaInfoDto> jpmSlaInfoDtoList = this.getJpmSlaInfoDtoListBySearchDto(searchDto);
        return CommonCollectionUtil.isNotEmpty(jpmSlaInfoDtoList);
	}

	@Override
	
	public Integer cloneSlaByProcessDeployId(Long oldProcessDeployId, Long newProcessDeployId, String processName, String newVersion) {
		/*
		StringBuilder log = new StringBuilder("##cloneSlaByProcessDeployId## - oldProcessDeployId : ");
		log.append(oldProcessDeployId).append("; newProcessDeployId : ").append(newProcessDeployId)
		.append("; newVersion : ").append(newVersion);
		logger.debug(log.toString());
		CloneSlaDto clone = new CloneSlaDto();
		clone.oldProcessDeployId = oldProcessDeployId;
		clone.newProcessDeployId = newProcessDeployId;
		clone.newVersion = StringUtils.isNotBlank(newVersion) ? newVersion.trim() : StringUtils.EMPTY;
		sqlManager.call(CLONE_SLA_PROCEDURE, clone);
		return clone.result;
		*/
//		SlaInfo sla = null;
//		List<SlaStep> newSteps = new ArrayList<>();
//		List<SlaSetting> newSettings = new ArrayList<>();
//		try {
//			StringBuilder log = new StringBuilder("##cloneSlaByProcessDeployId## - oldProcessDeployId : ");
//			log.append(oldProcessDeployId).append("; newProcessDeployId : ").append(newProcessDeployId)
//			.append("; newVersion : ").append(newVersion);
//			logger.debug(log.toString());
//			
//			sla = infoRepository.findOneByOldProcessId(oldProcessDeployId);
//			if(sla != null) {
//				sla.setId(null);
//				StringBuilder name = new StringBuilder(processName);
//				name.append(StringUtils.SPACE);
//				name.append(newVersion.trim());
//				sla.setSlaName(name.toString());
//				sla.setProcessId(newProcessDeployId);
//				sla = infoRepository.save(sla);
//				Long id = sla.getId();
//				List<Select2Dto> dtos = jpmStepDeployService.findStepsByProcessId(newProcessDeployId);
//				Map<String, Long> map =  setStepMap(dtos);
//				List<SlaStep> steps = stepService.findListByOldProcessId(oldProcessDeployId);
//				if(CollectionUtils.isNotEmpty(steps)) {
//					for(SlaStep entry : steps) {
//					    if(map.size() < 1) {
//					        throw new NullPointerException();
//					    }
//					    String code = jpmStepDeployService.findCodeById(entry.getStepId());
//					    if(StringUtils.isNotBlank(code)) {
//					        Long stepId = map.get(code);
//					        entry.setStepId(stepId);
//					    }
//						Long stepId = entry.getId();
//						List<SlaSetting> settings = slaSettingService.getListByOldProcessId(stepId, oldProcessDeployId);
//						entry.setId(null);
//						entry.setSlaInfoId(id);
//						entry.setProcessId(newProcessDeployId);
//						entry = stepService.saveStep(entry);
//						newSteps.add(entry);
//						stepId = entry.getId();
//						this.cloneSettingAlertById(settings, newSettings, stepId, oldProcessDeployId);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("##cloneSlaByProcessDeployId##", e);
//			if(sla != null) {
//				infoRepository.delete(sla);
//			}
//			try {
//				if(CollectionUtils.isNotEmpty(newSteps)) {
//					stepService.deleteList(newSteps);
//				}
//				if(CollectionUtils.isNotEmpty(newSettings)) {
//					slaSettingService.deleteList(newSettings);
//				}
//			} catch (SQLException e1) {
//				logger.error("##cloneSlaByProcessDeployId##", e);
//			}
//			return 0;
//		}
		return 1;
		
	}
	
//	private Map<String, Long> setStepMap(List<Select2Dto> dtos) {
//	    Map<String, Long> map = new HashMap<>();
//	    if(CollectionUtils.isNotEmpty(dtos)) {
//	        for(Select2Dto entry : dtos) {
//	            try {
//	                map.put(entry.getName(), Long.parseLong(entry.getId()));
//                } catch (Exception e) {
//                    logger.error("##setStepMap##", e);
//                }
//	        }
//	    }
//	    return map;
//	}
	
	/*
	private class CloneSlaDto {
        @In
        public Long oldProcessDeployId;
        
        @In
        public Long newProcessDeployId;
        
        @In
        public String newVersion;
        
        @Out
        public int result;
    }
    */

	@Override
	public String getEmailTemplateNameById(Long templateId) {
		try {
			if(templateId != null) {
				return templateService.getNameById(templateId);
			}
		} catch (Exception e) {
			logger.error("#getEmailTemplateNameById", e);
		}
		return StringUtils.EMPTY;
	}

    @Override
    public JCommonService getCommonService() {
        return comService;
    }

	/**
	 * @param settings
	 * @param newSettings
	 * @param stepId
	 * @param oldProcessDeployId
	 * @throws SQLException
	 */
//	private void cloneSettingAlertById(List<SlaSetting> settings, List<SlaSetting> newSettings, Long stepId, Long oldProcessDeployId) throws SQLException {
//		if(CollectionUtils.isNotEmpty(settings)) {
//			for(SlaSetting entry : settings) {
//				Long settingId = entry.getId();
//				List<SlaSettingAlertTo> alerts = slaSettingService.getSettingListByOldProcessId(settingId, oldProcessDeployId);
//				entry.setId(null);
//				entry.setSlaStepId(stepId);
//				Boolean isTransfer = entry.getIsTransfer();
//				if(isTransfer == null) {
//					entry.setIsTransfer(Boolean.FALSE);
//				}
//				entry = slaSettingService.saveOne(entry);
//				newSettings.add(entry);
//				settingId = entry.getId();
//				cloneSettingAlertList(alerts, settingId);
//			}
//		}
//	}
	
	/**
	 * @param alerts
	 * @param settingId
	 * @throws SQLException
	 */
//	private void cloneSettingAlertList(List<SlaSettingAlertTo> alerts, Long settingId) throws SQLException {
//		if(CollectionUtils.isNotEmpty(alerts)) {
//			for(SlaSettingAlertTo entry : alerts) {
//				entry.setSlaSettingId(settingId);
//			}
//			slaSettingService.saveAlertList(alerts);
//		}
//	}

}

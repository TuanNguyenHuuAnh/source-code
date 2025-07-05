package vn.com.unit.ep2p.sam.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jp.sf.amateras.mirage.provider.ConnectionProvider;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.events.repository.EventsMasterDataRepository;
import vn.com.unit.cms.core.module.notify.entity.Notify;
import vn.com.unit.cms.core.module.notify.entity.NotifysApplicableDetail;
import vn.com.unit.cms.core.module.notify.repository.NotifyRepository;
import vn.com.unit.cms.core.module.notify.repository.NotifysApplicableDetailRepository;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesResponse;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesStatusDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityDetailDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityRequest;
import vn.com.unit.cms.core.module.sam.dto.AttachmentDto;
import vn.com.unit.cms.core.module.sam.dto.CategoryDto;
import vn.com.unit.cms.core.module.sam.dto.OrgLocationResponse;
import vn.com.unit.cms.core.module.sam.dto.ParticipantDto;
import vn.com.unit.cms.core.module.sam.entity.SamActivity;
import vn.com.unit.cms.core.module.sam.entity.SamApprovalHistory;
import vn.com.unit.cms.core.module.sam.entity.SamAttachment;
import vn.com.unit.cms.core.module.sam.entity.SamOrganizationLocation;
import vn.com.unit.cms.core.module.sam.entity.SamPlan;
import vn.com.unit.cms.core.module.sam.repository.SamActivityRepository;
import vn.com.unit.cms.core.module.sam.repository.SamApprovalHistoryRepository;
import vn.com.unit.cms.core.module.sam.repository.SamAttachmentRepository;
import vn.com.unit.cms.core.module.sam.repository.SamOrganizationLocationRepository;
import vn.com.unit.cms.core.module.sam.repository.SamPlanRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.ADPDeviceTokenDto;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;
import vn.com.unit.ep2p.admin.dto.RolePermissionDto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.utils.SoapApiUtils;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.sam.service.SamService;
import vn.com.unit.ep2p.service.EventsService;

/**
 * @author ntr.bang
 * Get all activities of login user to show on dash-board screen
 * SR16172 - create date 20/4/2024 - Add API get role permission (getRolesPermission and getAgentTypeAndRoles)
 * Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 * 				 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 * 				 06/06/2024	nt.tinh SR16580 - Lưu trữ & truy xuất các tệp đính kèm từ hoạt động SAM trên AD Portal/mADP
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SamServiceImpl extends SamBaseServiceImpl implements SamService {

	@Autowired
	private SamActivityRepository actRepository;

	@Autowired
	private SamOrganizationLocationRepository orgRepository;

	@Autowired
	private SamPlanRepository planRepository;

	@Autowired
	private SamApprovalHistoryRepository historyRepository;
	
	@Autowired
	private Db2ApiService db2ApiService;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private SamAttachmentRepository attachmentRepository;
	
	@Autowired
	private EventsService eventsService;
	
	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private NotifysApplicableDetailRepository notifyDetailRepository;
	
	@Autowired
	@Qualifier("connectionProvider")
	private ConnectionProvider connectionProvider;
	
	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(SamServiceImpl.class);
	
	private static final String project = "SAM";
	
	private static final String TITLE_CREATE = "[%s] Phê duyệt kế hoạch %s của %s";
	private static final String TITLE_UPDATE = "[%s] %s đã %s kế hoạch %s tổ chức ngày %s";
	private static final String TITLE_CANCEL = "[%s] %s đã hủy kế hoạch %s dự kiến tổ chức ngày %s";
	private static final String LINK_DETAIL = "/sam-change-status/";
	private static final String CONTENT_CREATE = "%s trình duyệt kế hoạch tổ chức %s vào ngày %s. Vui lòng vào SAM để xem chi tiết và phê duyệt.";
	private static final String CONTENT_UPDATE = "Vui lòng vào SAM để xem thông tin kế hoạch.";
	private static final String CONTENT_CANCEL = "Lý do hủy: %s";
	private static final String MESSAGE = "Kế hoạch %s ngày %s %s";

	@Override
	public CmsCommonPagination<ActivitiesResponse> searchActivities(String agentCode, String agentType, String reqDate, String actCode, String planDate, Long statusId
			, Integer page, Integer pageSize, CommonSearchWithPagingDto search) {
		CmsCommonPagination<ActivitiesResponse> resultData = new CmsCommonPagination<ActivitiesResponse>();
		try {
			agentCode = UserProfileUtils.getFaceMask();
			List<ActivitiesResponse> lstResData = new ArrayList<>();
			if (StringUtils.isNotBlank(agentCode)) {
				// Check agent type to query data
				Set<String> zoneSet = new LinkedHashSet<>();
				List<String> zones = new ArrayList<>();
				// Get agent type
				List<String> agentTypes = actRepository.findAllAgentTypeByProject(project);
				Set<String> approvedZoneLst = new LinkedHashSet<>();
				if (agentTypes != null && agentTypes.contains(agentType)) {
					
					// Get agent type and partner code
					List<AgentInfoDto> agentInfoLst = db2ApiService.getAgentInfo(agentCode);
					
					List<String> partnerCodeLst = new ArrayList<>();
					// Loop to process with multiple agent type
					for (AgentInfoDto dto : agentInfoLst) {
						Set<String> zoneByAgentTypeSet = new LinkedHashSet<>();
						List<OrgLocationDto> orgLst = db2ApiService.getOrgLocationInfo(agentCode, dto.getAgentType(), dto.getPartnerCode());
						partnerCodeLst.add(dto.getPartnerCode());
						
						// Get role permission by agent type SR16172 - @author ntr.bang - create date 20/4/2024
						List<String> agentTypeLst = Arrays.asList(dto.getAgentType());
						List<RolePermissionDto> roleLst = actRepository.getRolesPermission(agentTypeLst);
						boolean isApproved = false;
						if (roleLst != null && roleLst.size() > 0) {
							for (RolePermissionDto rolePermissionDto : roleLst) {
								if (rolePermissionDto.getApprove() == 1) {
									isApproved = true;
								}
							}
						}
						
						// Add BU code to list
						for (OrgLocationDto orgDto : orgLst) {
							zoneByAgentTypeSet.add(orgDto.getZoneCode());
							
							// Add approved role to list
							if (isApproved) {
								if (dto.getOrgId().equalsIgnoreCase(orgDto.getZoneId())) {
									approvedZoneLst.add(orgDto.getZone());
								}
							}
						}
						zoneSet.addAll(zoneByAgentTypeSet);
					}
					// Convert set to list
					zones = new ArrayList<>(zoneSet);
					// only agent type are "ZD", "RD", "AD", "ADH" 
					agentCode = "";
				}
				int count = actRepository.countByCondition(reqDate, actCode, planDate, statusId, agentCode, zones);
				Integer offset = page == null ? null : Utility.calculateOffsetSQL(page + 1, pageSize);
				if (count > 0) {
					// Query data from database
					List<ActivitiesDto> lstData = actRepository.searchActivities(
							reqDate, actCode, planDate, statusId,
							offset, pageSize, agentCode, search, zones
							);
					for (ActivitiesDto item : lstData) {
						editOrgName(item);
					}
					
					// Build response data
					// Level 1 : Partner | Zone
					// Level 2 : BU
					lstResData = buildResponseData(lstData, approvedZoneLst);
				}
				resultData.setData(lstResData);
				resultData.setTotalData(count);
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
		
		return resultData;
	}


	@Override
	public List<String> findFiveActivities(String agentCode, Integer number) {
		List<String> resultData = new ArrayList<>();
		try {
			log.info("Begin findFiveActivities(Integer number)");
			log.debug("User login: ", agentCode);

			if (StringUtils.isNotBlank(agentCode)) {
				// Get data from database
				resultData = actRepository.findFiveActivities(number, agentCode);

			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return resultData;
	}

	@Override
	public List<ActivitiesStatusDto> findAllActivitiesStatus() {
		List<ActivitiesStatusDto> resultData = new ArrayList<>();
		try {
			log.info("Begin findAllActivitiesStatus(Integer number)");

			// Get data from database
			resultData = actRepository.findAllActivitiesStatus();

		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return resultData;
	}

	@Override
	public List<ParticipantDto> findAllParticipants() {
		List<ParticipantDto> resultData = new ArrayList<>();
		try {
			log.info("Begin findAllParticipants()");

			// Get data from database
			resultData = actRepository.findAllParticipants();

		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return resultData;
	}

	@Override
	public ActivitityDetailDto getActivityDetail(String agentCode, String agentType, String actCode) {
		ActivitityDetailDto resultData = new ActivitityDetailDto();
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
        String path = systemConfig.getPhysicalPathById(repo, null);
    	boolean flag = true;
		try {
			List<ActivitityDetailDto> lstData = null;
			List<String> agentTypes = actRepository.findAllAgentTypeByProject(project);
			if (agentTypes != null && agentTypes.contains(agentType)) {
				Set<String> zoneSet = new LinkedHashSet<>();
				List<String> zones = new ArrayList<>();
				// Get agent type and partner code
				List<AgentInfoDto> agentInfoLst = db2ApiService.getAgentInfo(agentCode);
				
				List<String> partnerCodeLst = new ArrayList<>();
				// Loop to process with multiple agent type
				for (AgentInfoDto dto : agentInfoLst) {
					Set<String> zoneByAgentTypeSet = new LinkedHashSet<>();
					List<OrgLocationDto> orgLst = db2ApiService.getOrgLocationInfo(agentCode, dto.getAgentType(), dto.getPartnerCode());
					partnerCodeLst.add(dto.getPartnerCode());
					
					// Add BU code to list
					for (OrgLocationDto orgDto : orgLst) {
						zoneByAgentTypeSet.add(orgDto.getZoneCode());
					}
					zoneSet.addAll(zoneByAgentTypeSet);
				}
				// Convert set to list
				zones = new ArrayList<>(zoneSet);
				lstData = actRepository.getActivityDetail(actCode, null, zones);
			} else {
				lstData = actRepository.getActivityDetail(actCode, agentCode, null);
			}

			if(lstData != null && lstData.size() > 0) {
				resultData = lstData.get(0);
				editOrgName(resultData);
				// Lấy hình đính kèm báo cáo
				if (!StringUtils.isEmpty(resultData.getReportAttachmentPhysical())) {
					File file = new File(path + "/" + resultData.getReportAttachmentPhysical());
		            byte[] fileContent = new byte[(int) file.length()];
		            FileInputStream inputStream = null;
		            try {
		                inputStream = new FileInputStream(file);
		                inputStream.read(fileContent);
		            } catch (IOException e) {
		            	flag = false;
		            } finally {
		                // close input stream
		                if (inputStream != null) {
		                    inputStream.close();
		                }
		            }
		            if (flag == true) {
		            	resultData.setReportAttachmentImg(Base64.getEncoder().encodeToString(fileContent));    
		            }
		            else {
		            	resultData.setReportAttachmentImg("");
		            }	
				}
				if(lstData.size() > 1) {
					String buLst = "";
					String buCodeLst = "";
					for (int i = 0; i < lstData.size(); i++) {
						if(i == 0) {
							buLst = lstData.get(i).getBu();
							buCodeLst = lstData.get(i).getBuCode();
						} else {
							buLst = buLst + "," + lstData.get(i).getBu();
							buCodeLst = buCodeLst + "," + lstData.get(i).getBuCode();
						}
					}
					// Set BU, BU_CODE
					resultData.setBu(buLst);
					resultData.setBuCode(buCodeLst);
					resultData.setBuCount(String.valueOf(lstData.size()));
				}
				List<AttachmentDto> attachments = actRepository.findAllAttachments(resultData.getActivityId(), "ACTIVITY");
				resultData.setActivityAttachment(attachments);
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return resultData;
	}
	

	@Override
	public OrgLocationResponse getOrglocation(String agentCode, String agentType) {
		OrgLocationResponse res = new OrgLocationResponse();
		
		try {
			List<OrgLocationDto> orgLocationLst = new ArrayList<>();
			log.info("Begin getOrglocation(String agentCode, String agentType)");
			
			// Get agent type and partner code - fix production issue, case can not show data of VNPST
			List<AgentInfoDto> agentInfoLst = db2ApiService.getAgentInfo(agentCode);
			
			List<String> partnerCodeLst = new ArrayList<>();
			// Loop to process with multiple agent type
			for (AgentInfoDto dto : agentInfoLst) {
				if (!partnerCodeLst.contains(dto.getPartnerCode())) {
					List<OrgLocationDto> orgLst = db2ApiService.getOrgLocationInfo(agentCode, dto.getAgentType(), dto.getPartnerCode());
					partnerCodeLst.add(dto.getPartnerCode());
					// Add to list
					orgLocationLst.addAll(orgLst);
				}
			}
			
			// set to response list
			res.setOrgLocationLst(orgLocationLst);
			// Filter partner list
			List<String> partnerLst = null;
			if(orgLocationLst.size() > 0) {
				Set<String> set = orgLocationLst.stream().map(OrgLocationDto::getPartner).collect(Collectors.toSet());
				partnerLst = new ArrayList<>(set);
			}
			// Set partner list
			res.setPartnerLst(partnerLst);
			
		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return res;
	}


	@Override
	public int createActivity(ActivitityRequest activity, List<MultipartFile> listFile) {
		int result = 0;
		try {
			activity.setAgentCode(UserProfileUtils.getFaceMask());
			// Check to update draft record
			if(activity.getId() != null && activity.getId() > 0) {
				updateDraft(activity, listFile);
				return 1;
			}

			// Create new record when submit/save draft
			// 1. Create data into Activity table (SAM_ACTIVITY)
			SamActivity activityEntity = new SamActivity();
			// Get next value from activity sequence
			Long activityId = actRepository.getActivityNextIdValue();
			log.debug("Activity ID: ", activityId);
			// Generate Activity Code format (A0000000001)
			Long id = activityId != null ? activityId + 1 : 1;
			activityEntity.setActCode(leftPad(String.valueOf(id)));
			activityEntity.setContent(activity.getContent());
			activityEntity.setType(activity.getType());
			activityEntity.setForm(activity.getForm());
			activityEntity.setParticipants(activity.getParticipants());
			activityEntity.setCategoryId(activity.getCategoryId());
			activityEntity.setSubject(activity.getSubject());
			activityEntity.setChannel(activity.getChannel());
			activityEntity.setPartner(activity.getPartner());
			// if save draft then status_id = 1, else status_id = 2
			activityEntity.setStatusId(activity.getNewStatusId());
			activityEntity.setCreatedBy(activity.getAgentCode());
			activityEntity.setCreatedDate(new Date());
			// Save ACTIVITY to database
			activityEntity = actRepository.save(activityEntity);
			// Save Attachment file
			List<String> lstPathFile = null;
			if (listFile != null && listFile.size() > 0) {
				lstPathFile= saveAttachFile(activityEntity, listFile);
			}
			
			// 2. Create data to SAM_ORGANIZATION_LOCATION
			saveMultipleBu(activity, activityEntity.getId());
			
			// 3. Create data to SAM_PLAN
			SamPlan planEntity = new SamPlan();
			planEntity.setPlanDate(convertToDate(activity.getPlanDate(), "MM/dd/yyyy"));
			planEntity.setPersonNumber(activity.getPersonNumberPlan());
			planEntity.setCostAmt(activity.getCostAmtPlan());
			planEntity.setSalesAmt(activity.getSalesAmtPlan());
			planEntity.setActivityId(activityEntity.getId());
			planEntity.setCreatedBy(activity.getAgentCode());
			planEntity.setCreatedDate(new Date());
			// Save PLAN to database
			planRepository.save(planEntity);
			
			// Push notification for ZD
			String title = String.format(TITLE_CREATE, activity.getPartner(), this.getCategoryName(activity.getCategoryId().toString()), activity.getAgentName());
			String contents = String.format(CONTENT_CREATE, activity.getAgentName(), activity.getSubject(), activity.getPlanDate());
			pushNotifyActivity(activity.getZdCode(), title, contents, LINK_DETAIL + activityEntity.getActCode());
			
			// Create WorkFlowId
			if (lstPathFile != null && !lstPathFile.isEmpty()) {
				String keyin = String.format("#AgentCode@%s#ActivityCode@%s#Notes@Plan", activityEntity.getCreatedBy(), activityEntity.getActCode());
				String keyinValue = String.format("%s$%s$Plan$", activityEntity.getCreatedBy(), activityEntity.getActCode());
				try {
					SoapApiUtils.getWorkflowId(lstPathFile, keyin, keyinValue);
					attachmentRepository.updateWfCreated(activityEntity.getId(), "ACTIVITY");
				} catch (Exception e) {
					log.error("iBPS has exception : " + e);
			    }
			}
			
			// Save data to database successfully
			result = 1;
			log.info("Create Activity successfully");
		} catch (Exception e) {
			log.error("Exception: ", e);
			result = -1;
		}
		return result;
	}
	
	/**
	 * save Multiple BU selected
	 * @param activity
	 * @param actId
	 */
	private void saveMultipleBu(ActivitityRequest activity, Long actId) {
		if(StringUtils.isNotBlank(activity.getBu()) && activity.getBu().contains(",")) {
			String[] buLst = activity.getBu().split(",");
			String[] buCodeLst = activity.getBuCode().split(",");
			for (int i = 0; i < buLst.length; i++) {
				log.debug("BU: ", buLst[i]);
				log.debug("BU CODE: ", buCodeLst[i]);
				// Save organization location
				if(StringUtils.isNotBlank(buLst[i]) && !",".equals(buLst[i])
						&& StringUtils.isNotBlank(buCodeLst[i]) && !",".equals(buCodeLst[i])) {
					saveOrgLocation(activity, actId, buLst[i].trim(), buCodeLst[i].trim());
				}
			}
		} else {
			// Save organization location
			saveOrgLocation(activity, actId, activity.getBu(), activity.getBuCode());
		}
	}

	/**
	 * Save BU
	 * @param activity
	 * @param actId
	 * @param bu
	 * @param buCode
	 */
	private void saveOrgLocation(ActivitityRequest activity, Long actId, String bu, String buCode) {
		// Create new entity
		SamOrganizationLocation orgLocationEntity = new SamOrganizationLocation();
		// Set data
		orgLocationEntity.setZone(activity.getZone());
		orgLocationEntity.setZoneCode(activity.getZoneCode());
		orgLocationEntity.setRegional(activity.getRegional());
		orgLocationEntity.setArea(activity.getArea());
		orgLocationEntity.setBu(bu);
		orgLocationEntity.setBuCode(buCode);
		orgLocationEntity.setBuCount(activity.getBuCount());
		orgLocationEntity.setActivityId(actId);
		orgLocationEntity.setCreatedBy(activity.getAgentCode());
		orgLocationEntity.setCreatedDate(new Date());
		// Save ORGANIZATION_LOCATION to database
		orgRepository.save(orgLocationEntity);
	}

	@Override
	public int updateActivity(ActivitityRequest activity, SamActivity entity, List<MultipartFile> listFile) {
		int result = 1;
		try {
			log.debug("Update by Activity ID: ", activity.getId());
			String title = null;
			String contents = null;
			String message = null;
			String categoryName = this.getCategoryName(entity.getCategoryId().toString());;
			
			// 1. Change status
			entity.setStatusId(activity.getNewStatusId());
			
			// If status = waiting approval then set approved data is current date
			if(activity.getNewStatusId() == 3 || activity.getNewStatusId() == 5) {
				entity.setApprovedDate(new Date());
				if (activity.getNewStatusId() == 3) {
					entity.setApprovedBy(activity.getAgentCode());
					title = String.format(TITLE_UPDATE, entity.getPartner(), activity.getAgentName(), "duyệt", categoryName, activity.getPlanDate());
					message = String.format(MESSAGE, categoryName, activity.getPlanDate(), "đã được duyệt");
				} else {
					title = String.format(TITLE_UPDATE, entity.getPartner(), activity.getAgentName(), "từ chối", categoryName, activity.getPlanDate());
					message = String.format(MESSAGE, categoryName, activity.getPlanDate(), "đã bị từ chối");
				}
				contents = CONTENT_UPDATE;
			}
			// If status = cancel
			if(activity.getNewStatusId() == 6) {
				entity.setCancelReason(activity.getCancelReason());
				entity.setCancelDate(new Date());
				title = String.format(TITLE_CANCEL, entity.getPartner(), activity.getAgentName(), categoryName, activity.getPlanDate());
				contents = String.format(CONTENT_CANCEL, activity.getCancelReason());
				message = String.format(MESSAGE, categoryName, activity.getPlanDate(), "đã bị hủy");
			}
			// If status = waiting approval then set approved data is current date
			List<String> lstPathFile = null;
			if(activity.getNewStatusId() == 4) {
				entity.setReportedDate(new Date());
				entity.setUpdatedBy(activity.getAgentCode());
				entity.setUpdatedDate(new Date());
				// Save Attachment file
				if (listFile != null && listFile.size() > 0) {
					lstPathFile = saveAttachFile(entity, listFile);
				}
			}
						
			// Update data to database
			actRepository.update(entity);
			
			// 2. Save Actual Plan
			// Save information of actual plan when SF reported
			if(activity.getNewStatusId() == 4) {
				
				SamPlan planEntity = new SamPlan();
				planEntity.setActualDate(convertToDate(activity.getActualDate(), "MM/dd/yyyy"));
				planEntity.setPersonNumber(activity.getPersonNumberActual());
				planEntity.setCostAmt(activity.getCostAmtActual());
				planEntity.setSalesAmt(activity.getSalesAmtActual());
				planEntity.setActivityId(activity.getId());
				planEntity.setCreatedBy(activity.getAgentCode());
				planEntity.setActualResult(activity.getResult());
				// Save Actual Plan to database
				planRepository.save(planEntity);
			}
			
			// 3. Save history
			SamApprovalHistory hisEntity = new SamApprovalHistory();
			// Set data
			hisEntity.setActivityId(activity.getId());
			hisEntity.setOrgLocationId(activity.getOrgLocationId());
			// Status before change
			hisEntity.setOldStatusId(activity.getOldStatusId());
			hisEntity.setOldStatus(activity.getOldStatus());
			// Status after changed
			hisEntity.setNewStatusId(activity.getNewStatusId());
			hisEntity.setNewStatus(activity.getNewStatus());
			hisEntity.setCreatedBy(activity.getAgentCode());
			// Save status change history to database
			historyRepository.save(hisEntity);
			
			// Push notification
			if(activity.getNewStatusId() == 3 || activity.getNewStatusId() == 5) {
				this.pushNotifyActivity(entity.getCreatedBy(), title, contents, LINK_DETAIL + entity.getActCode());
				this.pushNotifyForMobile(entity.getCreatedBy(), message);
			} else if (activity.getNewStatusId() == 6 && activity.getOldStatusId() == 3) {
				this.pushNotifyActivity(entity.getApprovedBy(), title, contents, LINK_DETAIL + entity.getActCode());
				this.pushNotifyForMobile(entity.getApprovedBy(), message);
			}
			
			// Create WorkFlowId
			if (lstPathFile != null && !lstPathFile.isEmpty()) {
				String keyin = String.format("#AgentCode@%s#ActivityCode@%s#Notes@Report", activity.getAgentCode(), entity.getActCode());
				String keyinValue = String.format("%s$%s$Report$", activity.getAgentCode(), entity.getActCode());
				try {
					SoapApiUtils.getWorkflowId(lstPathFile, keyin, keyinValue);
					attachmentRepository.updateWfCreated(activity.getId(), "REPORT");
				} catch (Exception e) {
					log.error("iBPS has exception : " + e);
			    }
			}
			log.info("Update Activity successfully");
			return result;
		} catch (Exception e) {
			log.error("Exception: ", e);
			result = -1;
		}
		return result;
	}

	@Override
	public SamActivity findById(Long activityId) {
		return actRepository.findOne(activityId);
	}
	
	@Override
	public List<CategoryDto> findAllCategories() {
		List<CategoryDto> resultData = new ArrayList<>();
		try {
			log.info("Begin findAllCategories");
			// Get data from database
			resultData = actRepository.findAllCategories();

		} catch (Exception e) {
			log.error("Exception: ", e);
		}

		return resultData;
	}

	/**
	 * Update data for draft record
	 * @param activity
	 * @return
	 */
	public boolean updateDraft(ActivitityRequest activity, List<MultipartFile> listFile) {
		boolean result = false;
		// 1. Update data to SAM_ACTIVITY
		SamActivity activityEntity = actRepository.findOne(activity.getId());
		// Get next value from activity sequence
		activityEntity.setContent(activity.getContent());
		activityEntity.setType(activity.getType());
		activityEntity.setForm(activity.getForm());
		activityEntity.setParticipants(activity.getParticipants());
		activityEntity.setCategoryId(activity.getCategoryId());
		activityEntity.setSubject(activity.getSubject());
		activityEntity.setChannel(activity.getChannel());
		activityEntity.setPartner(activity.getPartner());
		activityEntity.setStatusId(activity.getNewStatusId());
		activityEntity.setUpdatedBy(activity.getAgentCode());
		activityEntity.setUpdatedDate(new Date());
		// Save ACTIVITY to database
		actRepository.update(activityEntity);
		
		// Save Attachment file
		if (listFile != null && listFile.size() > 0) {
			List<AttachmentDto> attachments = actRepository.findAllAttachments(activity.getId(), null);
			for (AttachmentDto item : attachments) {
				// delete old file
				attachmentRepository.delete(item.getId());
				deleteDraffFile(item.getAttachmentPhysical());
			}
			// add new file
			saveAttachFile(activityEntity, listFile);
		}
		// 2. Update data to SAM_ORGANIZATION_LOCATION
		// Delete all ORG location and create new
		// Delete old location
		List<Long> ids = actRepository.findAllIdsOfOrgLocationByActId(activity.getId());
		if(ids != null && ids.size() > 0) {
			for (Long id : ids) {
				orgRepository.delete(id);
			}
		}
		
		// Create new
		saveMultipleBu(activity, activityEntity.getId());
		
		// 3. Update data to SAM_PLAN
		SamPlan planEntity = planRepository.findOne(activity.getPlanId());
		planEntity.setPlanDate(convertToDate(activity.getPlanDate(), "MM/dd/yyyy"));
		planEntity.setPersonNumber(activity.getPersonNumberPlan());
		planEntity.setCostAmt(activity.getCostAmtPlan());
		planEntity.setSalesAmt(activity.getSalesAmtPlan());
		planEntity.setActivityId(activityEntity.getId());
		planEntity.setUpdatedBy(activity.getAgentCode());
		planEntity.setUpdatedDate(new Date());
		// Save PLAN to database
		planRepository.update(planEntity);
		result = true;
		return result;
	}
	
	private List<String> saveAttachFile(SamActivity activity, List<MultipartFile> listFile) {
		List<String> lstPathFile = new ArrayList<String>();
		SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDate = formatDateExport.format(new Date());
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
		String path = systemConfig.getPhysicalPathById(repo, null);
		int length = path.length();
		path = Paths.get(path, "SAM_Image/" + currentDate.substring(0, 6)).toString();
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Path pathFile = null;
		int indexDot, i = 0;
		SamAttachment attachmentEntity = null;
		String qmlPath = systemConfig.getConfig("QML_PATH", ConstantCore.COMP_CUSTOMER_ID);
		for (MultipartFile file : listFile) {
			i++;
			try {
				byte[] fileCode = file.getBytes();
				indexDot = file.getOriginalFilename().lastIndexOf(".");
				pathFile = Paths.get(path, file.getOriginalFilename().substring(0, indexDot) + "_" + currentDate + file.getOriginalFilename().substring(indexDot));
				Files.write(pathFile, fileCode);
				attachmentEntity = new SamAttachment();
				attachmentEntity.setActId(activity.getId());
				attachmentEntity.setDetailNo(i);
				if (activity.getStatusId() <= 2) {
					attachmentEntity.setType("ACTIVITY");
				} else {
					attachmentEntity.setType("REPORT");
				}
				attachmentEntity.setAttachment(file.getOriginalFilename());
				attachmentEntity.setAttachmentPhysical(pathFile.toString().substring(length + 1));
				lstPathFile.add(qmlPath + attachmentEntity.getAttachmentPhysical().replace("/", "\\"));
				
				attachmentRepository.save(attachmentEntity);
			} catch (IOException e) {
				log.error("Exception ", e);
			}
		}
		
		return lstPathFile;
	}
	
	private void deleteDraffFile(String fileName) {
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
		String path = systemConfig.getPhysicalPathById(repo, null);
		
		try {
			File fileToDelete = new File(Paths.get(path, fileName).toString());

	        // Check if the file exists before attempting to delete
	        if (fileToDelete.exists()) {
	            fileToDelete.delete();
	        }
		} catch (Exception e) {
			log.error("Exception ", e);
		}
	}
	
	@Override
    public List<AgentInfoDto> getAgentTypeAndRoles(String agentCode) {
            List<AgentInfoDto> agentInfoLst = null;
            try {
                    agentInfoLst = db2ApiService.getAgentInfo(agentCode);
            } catch (Exception e) {
                    log.error("Exception: ", e);
            }
            return agentInfoLst;
    }
    
    @Override
    public List<RolePermissionDto> getRolesPermission(String agentCode) {
            List<RolePermissionDto> roleLst = null;
            try {
                    List<AgentInfoDto> agentInfoLst = db2ApiService.getAgentInfo(agentCode);
                    List<String> agentTypeLst = new ArrayList<>();
                    if (agentInfoLst != null && agentInfoLst.size() > 0) {
                            for (AgentInfoDto dto : agentInfoLst) {
                                    agentTypeLst.add(dto.getAgentType());
                            }
                    }
                    if (agentTypeLst.size() > 0) {
                            // Get roles from database
                            roleLst = actRepository.getRolesPermission(agentTypeLst);
                    }
            } catch (Exception e) {
                    log.error("Exception: ", e);
            }
            return roleLst;
    }
    
    /**
     * Push notification when create or update activity
     * @param agentCode
     * @return
     */
    private Long pushNotifyActivity(String agentCode, String title, String contents, String link) {
    	//save notify
		Notify entity = new Notify();
		entity.setNotifyCode(getNotifyCode());
		entity.setNotifyTitle(title);
		entity.setNotifyType(2);
		entity.setContents(contents);
		entity.setLinkNotify(link);
		entity.setSendImmediately(true);
		entity.setActive(true);
		entity.setApplicableObject("ALL");
		entity.setCreateBy("system");
		entity.setSendDate(new Date());
		entity.setSend(true);
		entity.setFc(false);
		entity.setCreateDate(new Date());
		entity.setNotifyType(1);
		entity.setSaveDetail(false);
		notifyRepository.save(entity);
		
		//save notify detail
		NotifysApplicableDetail entityDetail = new NotifysApplicableDetail();
		entityDetail.setNotifyId(entity.getId());
		entityDetail.setAgentCode(new Long(agentCode));
		entityDetail.setReadAlready(false);
		notifyDetailRepository.save(entityDetail);
		
		return entity.getId();
	}
    
    private String getNotifyCode() {
		SimpleDateFormat format = new SimpleDateFormat("yy");
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		return CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT,
				notifyRepository.getMaxNotifyCode(CmsPrefixCodeConstant.PREFIX_CODE_NOT
						+ format.format(new Date()) + formatMM.format(new Date())));
	}
    
    private String getCategoryName(String categoryCode) {
    	List<EventsMasterDataDto> masterData = eventsService.getListMasterData("ACTIVITY_CATEGORY", null, categoryCode);
    	if (masterData != null && masterData.size() > 0) {
    		return masterData.get(0).getName();
    	}
    	return null;
    }
    
    private void editOrgName(Object obj) {
    	if (obj instanceof ActivitityDetailDto) {
    		ActivitityDetailDto detail = (ActivitityDetailDto) obj;
    		List<OrgLocationDto> orgLocation = db2ApiService.getOrgLocationByBu(detail.getBuCode());
        	if (orgLocation.size() > 0) {
        		detail.setZone(orgLocation.get(0).getZone());
        		detail.setRegional(orgLocation.get(0).getRegional());
        		detail.setArea(orgLocation.get(0).getArea());
        	}
    	} else {
    		ActivitiesDto detail = (ActivitiesDto) obj;
    		List<OrgLocationDto> orgLocation = db2ApiService.getOrgLocationByBu(detail.getBuCode());
        	if (orgLocation.size() > 0) {
        		detail.setZone(orgLocation.get(0).getZone());
        	}
    	}
    }
    
    public void pushNotifyForMobile(String agentCode, String message) {
		Connection connection = connectionProvider.getConnection();
		try {
			connection.setAutoCommit(false);
			String query = "INSERT INTO dbo.tbNotification"
					+ " (sAgentId, sDeviceToken, sMessage, nType, project, dtSubmitDate, nActive, dtCreatedDate, IsSend, LinkNotify)"
					+ " VALUES (?,?,?,'20','eApp_AD',GETDATE(),0,GETDATE(),1,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			
			List<ADPDeviceTokenDto> deviceToken = db2ApiService.getDeviceTokenInfo(Arrays.asList(agentCode));
			for (ADPDeviceTokenDto data : deviceToken) {
				pst.setString(1, data.getUserId());
    			pst.setString(2, data.getDeviceToken());
    			pst.setString(3, message);
    			pst.setString(4, "");
    			
    			pst.addBatch();
    			pst.executeBatch();
    			pst.clearBatch();
			}
			
			connection.commit();
		} catch (SQLException e) {
			log.error("Exception: ", e);
		}
	}
    
}

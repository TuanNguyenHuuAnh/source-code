package vn.com.unit.ep2p.sam.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.events.dto.EventsMasterDataDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesResponse;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesStatusDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityDetailDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityRequest;
import vn.com.unit.cms.core.module.sam.dto.CategoryDto;
import vn.com.unit.cms.core.module.sam.dto.OrgLocationResponse;
import vn.com.unit.cms.core.module.sam.dto.ParticipantDto;
import vn.com.unit.cms.core.module.sam.entity.SamActivity;
import vn.com.unit.ep2p.admin.dto.AgentInfoDto;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;
import vn.com.unit.ep2p.admin.dto.RolePermissionDto;

/**
 * @author ntr.bang
 * SR16172 - create date 20/4/2024 - Add API get role permission (getRolesPermission and getAgentTypeAndRoles)
 * Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 * 				 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 */
public interface SamService {

	/**
	 * Search all activities by conditions
	 * @param reqDate format MM/YYYY
	 * @param actCode
	 * @param planDate format DD/MM/YYYY
	 * @param statusId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @return
	 */
    CmsCommonPagination<ActivitiesResponse> searchActivities(String agentCode, String agentType, String reqDate, String actCode, String planDate
    		, Long statusId, Integer page, Integer pageSize, CommonSearchWithPagingDto search);
    
    /**
     * Find number activities code (default number = 5)
     * @param number
     * @return
     */
    List<String> findFiveActivities(String agentCode, Integer number);
    
    /**
     * Find all active activities status
     * @param number
     * @return
     */
    List<ActivitiesStatusDto> findAllActivitiesStatus();
    
    /**
     * Find all participants
     * @param number
     * @return
     */
    List<ParticipantDto> findAllParticipants();
    
    
    /**
     * Get activity detail by activity code
     * @param number
     * @return
     */
    ActivitityDetailDto getActivityDetail(String agentCode, String agentType, String actCode);
    
    /**
     * Create new a activity
     * @param activity
     * @return
     */
    int createActivity(@Param("activity") ActivitityRequest activity, List<MultipartFile> listFile);
    
    /**
     * Update a activity
     * @param activity
     * @return
     */
    int updateActivity(@Param("activity") ActivitityRequest activity, SamActivity entity, List<MultipartFile> listFile);
    
    /**
     * Find activity by ID
     * @param activityId
     * @return
     */
    SamActivity findById(@Param("activity") Long activityId);
    
    /**
     * Find all categories by is_deleted = false
     * @return
     */
    List<CategoryDto> findAllCategories();
    
    /**
     * Find all organization location
     * @return
     */
    OrgLocationResponse getOrglocation(String agentCode, String agentType);

    /**
     * @author ntr.bang
     * SR16172 - create date 20/4/2024
     * Get agent information by agent code
     * @param agentCode
     * @return
     */
    List<AgentInfoDto> getAgentTypeAndRoles(String agentCode);

	/**
	 * @author ntr.bang
	 * SR16172 - create date 20/4/2024
	 * Get All Role Permission of agent
	 * @param agentCode
	 * @return
	 */
    List<RolePermissionDto> getRolesPermission(String agentCode);
}

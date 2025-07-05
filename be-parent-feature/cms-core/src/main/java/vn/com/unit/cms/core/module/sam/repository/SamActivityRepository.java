package vn.com.unit.cms.core.module.sam.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitiesStatusDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityDetailDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityExportDto;
import vn.com.unit.cms.core.module.sam.dto.AttachmentDto;
import vn.com.unit.cms.core.module.sam.dto.CategoryDto;
import vn.com.unit.cms.core.module.sam.dto.ParticipantDto;
import vn.com.unit.cms.core.module.sam.entity.SamActivity;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.RolePermissionDto;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
public interface SamActivityRepository extends DbRepository<SamActivity, Long> {

	/**
	 * Count records by conditions
	 * @param eventDate
	 * @param user
	 * @return
	 */
	int countByCondition(@Param("date") String reqDate, @Param("actCode") String actCode, @Param("planDate") String planDate
			, @Param("statusId") Long statusId, @Param("createBy") String user, @Param("zones") List<String> zones);

	/**
	 * search activities by conditions
	 * @param eventDate
	 * @param page
	 * @param pageSize
	 * @param user
	 * @param search
	 * @return
	 */
	List<ActivitiesDto> searchActivities(@Param("date") String reqDate, @Param("actCode") String actCode, @Param("planDate") String planDate
			, @Param("statusId") Long statusId, @Param("page") Integer page, @Param("pageSize") Integer pageSize
			, @Param("createBy") String user, @Param("search") CommonSearchWithPagingDto search, @Param("zones") List<String> zones);
	
	/**
	 * Find top 5 activities code
	 * @param number
	 * @param user
	 * @return
	 */
	List<String> findFiveActivities(@Param("number") Integer number, @Param("createBy") String user);

	/**
	 * Find all activities status (master data)
	 * @return
	 */
	List<ActivitiesStatusDto> findAllActivitiesStatus();

	/**
	 * Find all activities status (master data)
	 * @return
	 */
	List<ParticipantDto> findAllParticipants();

	/**
	 * Get activity detail by activity code
	 * @return
	 */
	List<ActivitityDetailDto> getActivityDetail(@Param("actCode") String actCode, @Param("agentCode") String agentCode, @Param("zones") List<String> zones);
	
	/**
	 * Get current activity ID
	 * @return
	 */
	Long getActivityNextIdValue();
	
	/**
	 * Get activity detail by activity code
	 * @return
	 */
	List<ActivitityExportDto> exportCsv(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	/**
	 * Find all categories (master data)
	 * @return
	 */
	List<CategoryDto> findAllCategories();
	
	/**
	 * Find all agent type by project (master data)
	 * @return
	 */
	List<String> findAllAgentTypeByProject(@Param("project") String project);
	
	/**
	 * Find all organization location id by activity id
	 * @param actId
	 * @return
	 */
	List<Long> findAllIdsOfOrgLocationByActId(@Param("actId") Long actId);
	
	/**
	 * Get roles by agent type from master data
	 * @param agentTypes
	 * @return
	 */
	List<RolePermissionDto> getRolesPermission(@Param("agentTypes") List<String> agentTypes);

	/**
	 * Find all attachment
	 * @param actId
	 * @return
	 */
	List<AttachmentDto> findAllAttachments(@Param("actId") Long actId, @Param("type") String type);
}
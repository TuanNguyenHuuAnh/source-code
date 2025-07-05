package vn.com.unit.ep2p.admin.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingAlertToDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation;
import vn.com.unit.sla.repository.SlaConfigDetailRepository;

public interface SlaSettingRepository extends SlaConfigDetailRepository {

	/**
	 * @param stepId
	 * @param processId
	 * @param processStepId
	 * @param actived
	 * @return
	 */
	List<SlaSettingDto> findListSettingByStepId(@Param("stepId")Long stepId, @Param("processId")Long processId
			, @Param("processStepId")Long processStepId, @Param("actived")Integer actived);

	/**
	 * @param settingId
	 * @param emailType
	 * @return
	 */
	List<SlaSettingAlertToDto> findListSettingAlertToBySettingIdAndEmailType(@Param("settingId")Long settingId
			, @Param("emailType") String emailType);

	/**
	 * @param id
	 * @param deletedBy
	 * @param deletedDate
	 */
	@Modifying
	void deleteByStepId(@Param("id")Long id,@Param("deletedBy")String deletedBy,@Param("deletedDate")Date deletedDate);
	
	/**
	 * @param stepId
	 * @return
	 */
	List<SettingInfomation> findSettingListBySlaStepId(@Param("stepId") Long stepId);

	/**
	 * @param stepId
	 * @param oldProcessDeployId
	 * @return
	 */
//	List<SlaSetting> findListByOldProcessId(@Param("stepId") Long stepId, @Param("oldProcessDeployId") Long oldProcessDeployId);
	
}

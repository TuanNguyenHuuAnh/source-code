package vn.com.unit.ep2p.admin.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
//import vn.com.unit.sla.repository.SlaNotiAlertToRepository;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingAlertToDto;
import vn.com.unit.sla.repository.SlaConfigAlertToRepository;
//import vn.com.unit.sla.service.SlaConfigAlertToService;

public interface SlaSettingAlertToRepository extends SlaConfigAlertToRepository{

	/**
	 * @param stepId
	 * @return
	 */
	public List<SlaSettingAlertToDto> findListAlertToByStepId(@Param("stepId") Long stepId);
	
	/**
	 * @param id
	 * @param deletedBy
	 * @param deletedDate
	 */
	@Modifying
	public void deleteBySettingId(@Param("id")Long id,@Param("deletedBy")String deletedBy,@Param("deletedDate")Date deletedDate);

	/**
	 * @param id
	 * @return
	 */
	public List<SlaSettingAlertToDto> findListAlertToBySlaStepId(@Param("stepId")Long id);
	
	/**
	 * @param settingId
	 * @return
	 */
	public List<SlaSettingAlertToDto> findListAlertToBySettingId(@Param("settingId") Long settingId);
	
	/**
	 * @param settingId
	 * @return
	 */
	List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation.DetailSettingInfomation> findDetailListBySettingId(
			@Param("settingId") Long settingId);
	
	/**
	 * @param settingId
	 * @param oldProcessDeployId
	 * @return
	 */
//	List<SlaSettingAlertTo> findListByOldProcessId(@Param("settingId") Long settingId, @Param("oldProcessDeployId") Long oldProcessDeployId);
	
}

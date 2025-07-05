package vn.com.unit.ep2p.admin.sla.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration.SlaInfomation.StepInfomation;
import vn.com.unit.workflow.repository.JpmSlaConfigRepository;

public interface SlaStepRepository extends JpmSlaConfigRepository {

	/**
	 * @param slaInfoId
	 * @param lang
	 * @return
	 */
	List<SlaStepDto> findListBySlaInfoId(@Param("slaInfoId") Long slaInfoId, @Param("lang") String lang);

	/**
	 * @param slaStepId
	 * @return
	 */
	SlaStepDto getSlaStepDtoById(@Param("id") Long slaStepId);

	/**
	 * @param id
	 * @param deletedBy
	 * @param deletedDate
	 */
	@Modifying
	void deleteByInfoId(@Param("id") Long id, @Param("deletedBy") String deletedBy,
			@Param("deletedDate") Date deletedDate);
	
	/**
	 * @param slaId
	 * @return
	 */
	List<StepInfomation> findStepListBySlaId(@Param("slaId") Long slaId);
	
	/**
	 * @param oldProcessDeployId
	 * @return
	 */
	//List<SlaStep> findListByOldProcessId(@Param("oldProcessDeployId") Long oldProcessDeployId);

}

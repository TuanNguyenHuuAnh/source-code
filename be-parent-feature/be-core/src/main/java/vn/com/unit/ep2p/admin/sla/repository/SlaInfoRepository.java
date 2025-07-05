package vn.com.unit.ep2p.admin.sla.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaInfoDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSearchDto;
import vn.com.unit.workflow.repository.JpmSlaInfoRepository;

public interface SlaInfoRepository extends JpmSlaInfoRepository {

	/**
	 * @param searchDto
	 * @return
	 */
	int countSlaInfoDtoByCondition(@Param("searchDto")SlaSearchDto searchDto);

	/**
	 * @param startIndex
	 * @param sizeOfPage
	 * @param searchDto
	 * @return
	 */
	List<SlaInfoDto> findSlaInfoDtoByCondition(@Param("offset")int startIndex, @Param("sizeOfPage")int sizeOfPage
			, @Param("searchDto")SlaSearchDto searchDto);

	/**
	 * @param id
	 * @return
	 */
	SlaInfoDto findById(@Param("id")Long id);

	/**
	 * @param slaInfoDto
	 * @return
	 */
//	SlaInfo findBySlaInfoDto(@Param("dto")SlaInfoDto slaInfoDto);
	
	/**
	 * @param companyId
	 * @param name
	 * @return
	 */
	int checkSlaExisted(@Param("companyId")Long companyId, @Param("name")String name);

	/**
	 * @param companyId
	 * @param processId
	 * @return
	 */
	List<SlaConfiguration.SlaInfomation> findListByCompanyIdAndProcessId(@Param("companyId")Long companyId
			, @Param("processId")Long processId);
	
	/**
	 * @param processId
	 * @return
	 */
	int checkProcessExist(@Param("processId")Long processId);
	
	/**
	 * @param oldProcessDeployId
	 * @return
	 */
//	SlaInfo findOneByOldProcessId(@Param("oldProcessDeployId") Long oldProcessDeployId);
	
}

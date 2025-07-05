package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;

@Repository
public interface AppQrtzMScheduleRepository extends DbRepository<QrtzMSchedule, Long> {
	
	/**
	 * @param term
	 * @param companyIdList
	 * @return
	 */
	List<Select2Dto> getListForCombobox(@Param("term") String term, @Param("companyIdList") List<Long> companyIdList);

	/**
	 * @param schedCode
	 * @return
	 */
	QrtzMSchedule getByScheduleId(@Param("schedId") Long schedId, @Param("companyId") Long companyId);

	/**
	 * @param schedCode
	 * @param id
	 * @return
	 */
	Select2Dto findSelect2ByJobIdAndCompanyId(@Param("schedId") Long schedId, @Param("id") Long id);

	/**
	 * @param scheduleCode
	 * @return
	 */
	Object isScheduleInUse(@Param("schedId") Long schedId, @Param("companyId") Long companyId);

	/**
	 * @param schedSearch
	 * @return
	 */
	int countScheduleByCondition(@Param("schedSearch") QrtzMScheduleSearchDto schedSearch);

	/**
	 * @param schedSearch
	 * @param offset
	 * @param sizeOfPage
	 * @return
	 */
	List<QrtzMScheduleDto> getSchedules(@Param("schedSearch") QrtzMScheduleSearchDto schedSearch, @Param("offset") int offset, 
			@Param("sizeOfPage") int sizeOfPage);
	
	/**
	 * @param companyId
	 * @param code
	 * @return
	 */
	int hasCode(@Param("companyId") Long companyId, @Param("code") String code, @Param("id") Long id);
	
	/**
	 * getListSelect2DtoByCompanyId
	 * @param term
	 * @param companyId
	 * @param isPaging
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> getListSelect2DtoByCompanyId(@Param("term")String term, @Param("companyId")Long companyId, @Param("isPaging")boolean isPaging);
}

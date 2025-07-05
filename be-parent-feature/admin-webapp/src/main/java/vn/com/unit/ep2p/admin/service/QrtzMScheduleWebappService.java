package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;

public interface QrtzMScheduleWebappService {

	/**
	 * @param term
	 * @param companyIdList
	 * @return
	 * @throws SQLException
	 */
	List<Select2Dto> getListForCombobox(String term, List<Long> companyIdList) throws SQLException;

	/**
	 * @param schedCode
	 * @return
	 */
	QrtzMSchedule getByScheduleId(Long schedId, Long companyId);

	/**
	 * @param schedCode
	 * @param id
	 * @return
	 */
	Select2Dto getSelect2ByJobIdAndCompanyId(Long schedId, Long id);

	/**
	 * @param id
	 * @return
	 */
	QrtzMSchedule getById(Long id);

	/**
	 * @param scheduleDto
	 * @return
	 * @throws Exception
	 */
	QrtzMSchedule save(QrtzMScheduleDto scheduleDto) throws Exception;

	/**
	 * @param cron
	 * @return
	 */
	Boolean cronCheck(String cron);

	/**
	 * @param scheduleCode
	 * @return
	 */
	Boolean isScheduleInUse(Long scheduleId, Long companyId);

	/**
	 * @param schedSearch
	 * @param pageSize
	 * @param page
	 * @return
	 */
	PageWrapper<QrtzMScheduleDto> getSchedules(QrtzMScheduleSearchDto schedSearch, int pageSize, int page);
	
	/**
	 * @param id
	 */
	void deleteSchedule(Long id);
	
	/**
	 * @param companyId
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	boolean hasCode(Long companyId, String code, Long id) throws SQLException;
	
	/**
	 * getListSelect2DtoByCompanyId
	 * @param term
	 * @param companyId
	 * @param isPaging
	 * @return
	 * @throws SQLException
	 * @author trieuvd
	 */
	List<Select2Dto> getListSelect2DtoByCompanyId(String term, Long companyId, boolean isPaging) throws SQLException;
}

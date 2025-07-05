package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;

public interface QrtzMJobWebappService {

	void deleteJob(Long id) throws Exception;

	QrtzMJob getById(Long id) throws Exception;

	QrtzMJob getByJobId(Long jobId) throws Exception;

	PageWrapper<QrtzMJobDto> getJobs(QrtzMJobSearchDto jobSearch, int pageSize, int page);

	List<Select2Dto> getListForCombobox(String term, List<Long> companyIdList) throws SQLException;

	List<Select2Dto> getListJobClass(String term, Long id) throws SQLException;
	
	String getClassNameByPath(String path) throws Exception;

	public List<Select2Dto> getListJobType() throws Exception;

	public Select2Dto getSelect2ByJobIdAndCompanyId(Long jobId, Long id);

	void initCreateScreen(ModelAndView mav, Long jobId) throws Exception;

	boolean isJobInUse(Long jobId) throws Exception;
	
	void save(QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity, Locale locale) throws Exception;
	
	/**
	 * getByJobGroup
	 * @param jobGroup
	 * @return
	 * @author HungHT
	 */
	QrtzMJob getByJobGroup(Long companyId, String jobGroup) throws Exception;
	
	/**
	 * getEmailTemplateSelection
	 * @param
	 * @return
	 * @author thuydtn
	 */
	List<Select2Dto> getEmailTemplateSelection(Long comId) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Select2Dto getCompanyById(Long id) throws Exception;
	
	/**
	 * @param mav
	 * @param jobEntity
	 * @param jobStoreEntity
	 * @param lstSendStatus
	 * @throws Exception
	 */
	void initScreen(ModelAndView mav, QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity, List<String> lstSendStatus) throws Exception;
	
	/**
	 * @param companyId
	 * @param code
	 * @param id
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

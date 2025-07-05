package vn.com.unit.process.admin.sla.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaInfoDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaSearchDto;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.service.JpmSlaInfoService;

public interface SlaInfoService extends JpmSlaInfoService {

	/**
	 * @param searchDto
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws DetailException 
	 * @throws Exception
	 */
	PageWrapper<JpmSlaInfoDto> search(SlaSearchDto searchDto, int page, int pageSize) throws DetailException;

	/**
	 * @param id
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	SlaInfoDto findById(Long id, Locale locale);
	
	/**
	 * @param companyId
	 * @param name
	 * @return
	 * @throws Exception
	 */
	boolean checkSlaInfoExist(Long companyId, String name);

	/**
	 * @param slaInfoDto
	 * @return
	 * @throws Exception
	 */
	SlaInfoDto saveSlaInfoDto(SlaInfoDto slaInfoDto) throws Exception;

	/**
	 * @param id
	 * @param locale
	 * @throws Exception
	 */
	void deleteSla(Long id, Locale locale) throws Exception;
	
	/**
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Select2ResultDto getBusinessListForSelect(final Long companyId) throws Exception;
	
	/**
	 * @param companyId
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	Select2ResultDto getProcessListForSelect(final Long companyId, String lang) throws Exception;
	
	/**
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Select2ResultDto getCalendarTypeListForSelect(final Long companyId) throws Exception;
	
	/**
	 * @param key
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Select2ResultDto getEmailTamplateList(String key, Long companyId) throws Exception;
	
	/**
	 * @param companyId
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	SlaConfiguration exportSlaConfiguration(Long companyId, Long processId) throws Exception;
	
	/**
	 * @param sla
	 * @return
	 * @throws Exception
	 */
	boolean importSlaConfiguration(SlaConfiguration sla) throws Exception;
	
	/**
	 * @param companyId
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	List<SlaConfiguration.SlaInfomation> getSlaListByCompanyIdAndProcessId(Long companyId, Long processId) throws Exception;
	
	/**
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	boolean checkProcessExist(Long processId);
	
	/**
	 * @param oldProcessDeployId
	 * @param newProcessDeployId
	 * @param newVersion
	 * @return
	 * @throws Exception
	 */
	Integer cloneSlaByProcessDeployId(Long oldProcessDeployId, Long newProcessDeployId, String processName, String newVersion);
	
	/**
	 * @param templateId
	 * @return
	 */
	String getEmailTemplateNameById(Long templateId);

}

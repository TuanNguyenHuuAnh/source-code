package vn.com.unit.ep2p.admin.sla.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.sla.entity.SlaConfig;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;
import vn.com.unit.workflow.entity.JpmSlaConfig;
import vn.com.unit.workflow.service.JpmSlaConfigService;

public interface SlaStepService extends JpmSlaConfigService {
	
	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	SlaConfig findOneById(Long id) throws SQLException;
	
	/**
	 * @param step
	 * @return
	 * @throws SQLException
	 */
	SlaConfig saveStep(JpmSlaConfig step) throws SQLException;
	
	/**
	 * @param step
	 * @return
	 * @throws SQLException
	 */
//	void save(Iterable<SlaConfiguration.SlaInfomation.StepInfomation> steps) throws SQLException;

	/**
	 * @param slaInfoId
	 * @param locale
	 * @return
	 * @throws SQLException
	 */
	public List<SlaStepDto> getListStepByInfoId(Long slaInfoId, Locale locale);
	
	/**
	 * @param slaSteps
	 * @throws Exception
	 */
	public void saveListSlaStep(List<SlaConfig> slaSteps) throws Exception;

	/**
	 * @param slaStepId
	 * @return
	 * @throws Exception
	 */
	public SlaStepDto getSlaStepDtoById(Long slaStepId, String lang) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public void deleteStepSetting(Long id) throws Exception;
	
	/**
	 * @param slaId
	 * @return
	 * @throws Exception
	 */
	List<SlaConfiguration.SlaInfomation.StepInfomation> getStepListBySlaId(Long slaId) throws Exception;
	
	/**
	 * @param slaInfoId
	 * @param lang
	 * @return
	 */
	List<SlaStepDto> getListBySlaInfoId(Long slaInfoId, String lang);
	
	/**
	 * @param id
	 * @param deletedBy
	 * @param deletedDate
	 */
	void deleteByInfoId(Long id, Long deletedBy, Date deletedDate);
	
	/**
	 * @param oldProcessDeployId
	 * @return
	 */
	List<SlaConfig> findListByOldProcessId(Long oldProcessDeployId) throws SQLException;
	
	/**
	 * @param steps
	 * @throws SQLException
	 */
	void deleteList(List<SlaConfig> steps) throws SQLException;
	
	/**
     * <p>
     * Get jpm sla config dto by info id and lang.
     * </p>
     *
     * @author TrieuVD
     * @param slaInfoId
     *            type {@link Long}
     * @param langCode
     *            type {@link String}
     * @return {@link List<JpmSlaConfigDto>}
     */
	public List<JpmSlaConfigDto> getJpmSlaConfigDtoByInfoIdAndLang(Long slaInfoId, String langCode);
	
}

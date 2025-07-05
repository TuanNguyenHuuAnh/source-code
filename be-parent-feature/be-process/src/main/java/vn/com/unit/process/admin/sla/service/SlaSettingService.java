package vn.com.unit.process.admin.sla.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import vn.com.unit.sla.entity.SlaNotiAlertTo;
import vn.com.unit.ep2p.admin.sla.dto.SlaConfiguration;
import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;
import vn.com.unit.sla.entity.SlaConfigDetail;
import vn.com.unit.sla.service.SlaConfigDetailService;

public interface SlaSettingService extends SlaConfigDetailService{

	/**
	 * @param stepId
	 * @return
	 * @throws Exception
	 */
	List<SlaSettingDto> getListSettingByStepId(Long stepId) throws Exception;

	/**
	 * @param slaStepDto
	 * @param locale
	 * @throws Exception
	 */
	void saveSLASetting(SlaStepDto slaStepDto, Locale locale) throws Exception;
	
	/**
	 * @param stepId
	 * @throws Exception
	 */
	void removeSetting(Long stepId) throws Exception;
	
	/**
	 * @param stepId
	 * @return
	 * @throws Exception
	 */
	List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation> getSettingListBySlaStepId(Long stepId) throws Exception;
	
	/**
	 * @param settingId
	 * @return
	 * @throws Exception
	 */
	List<SlaConfiguration.SlaInfomation.StepInfomation.SettingInfomation.DetailSettingInfomation> getDetailListBySettingId(
			Long settingId) throws Exception;

	/**
	 * @param stepId
	 * @param oldProcessDeployId
	 * @return
	 * @throws SQLException
	 */
	List<SlaConfigDetail> getListByOldProcessId(Long stepId, Long oldProcessDeployId) throws SQLException;
	
	/**
	 * @param settingId
	 * @param oldProcessDeployId
	 * @return
	 */
	List<SlaNotiAlertTo> getSettingListByOldProcessId(Long settingId, Long oldProcessDeployId) throws SQLException;
	
	/**
	 * @param alerts
	 * @throws SQLException
	 */
	void saveAlertList(List<SlaNotiAlertTo> alerts) throws SQLException;
	
	/**
	 * @param setting
	 * @return
	 * @throws SQLException
	 */
	//SlaSetting saveOne(SlaSetting setting) throws SQLException;
	
	/**
	 * @param settings
	 * @throws SQLException
	 */
	void deleteList(List<SlaConfigDetail> settings) throws SQLException;
	
	/**
     * <p>
     * Save sla setting dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaSettingDto
     *            type {@link SlaSettingDto}
     * @return {@link SlaSettingDto}
	 * @throws Exception 
     */
	public SlaSettingDto saveSlaSettingDto(SlaSettingDto slaSettingDto) throws Exception;
	
}

/*******************************************************************************
 * Class        ：SlaActionService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.sla.dto.SlaDataResultDto;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.dto.SlaNotiAlertDto;

/**
 * SlaAsyncActionService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaAsyncActionService {

    /**
     * <p>
     * Send notification.
     * </p>
     *
     * @param alertDto
     *            type {@link SlaNotiAlertDto}
     * @param createAlertHisFlag
     *            type {@link boolean}
     * @return {@link SlaDataResultDto}
     * @author TrieuVD
     */
    public SlaDataResultDto sendNotification(SlaNotiAlertDto alertDto, boolean createAlertHisFlag);
    
    /**
     * <p>
     * Send email.
     * </p>
     *
     * @author TrieuVD
     * @param alertDto
     *            type {@link SlaEmailAlertDto}
     * @param createAlertHisFlag
     *            type {@link boolean}
     * @return {@link SlaDataResultDto}
     */
    public SlaDataResultDto sendEmail(SlaEmailAlertDto alertDto, boolean createAlertHisFlag);
    
    /**
     * <p>
     * Update sla noti alert by id list.
     * </p>
     *
     * @author TrieuVD
     * @param status
     *            type {@link Integer}
     * @param idList
     *            type {@link List<Long>}
     */
    public void updateSlaNotiAlertByIdList(Integer status, List<Long> idList);
    
    /**
     * <p>
     * Update sla email alert by id list.
     * </p>
     *
     * @author TrieuVD
     * @param status
     *            type {@link Integer}
     * @param idList
     *            type {@link List<Long>}
     */
    public void updateSlaEmailAlertByIdList(Integer status, List<Long> idList);
    
    /**
     * <p>
     * Creates the sla alert by config.
     * </p>
     *
     * @author TrieuVD
     * @param slaCreateAlertParam
     *            type {@link CreateSlaAlertParam}
     */
    public void createSlaAlertByConfig(CreateSlaAlertParam slaCreateAlertParam);
}

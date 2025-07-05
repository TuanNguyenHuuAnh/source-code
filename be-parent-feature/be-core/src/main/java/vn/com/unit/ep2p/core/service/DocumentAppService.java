/*******************************************************************************
 * Class        ：DocumentAppService
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.res.dto.TaskSlaRes;
import vn.com.unit.ep2p.dto.AccountDetailDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmHiTaskDto;

/**
 * DocumentAppService.
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
@SuppressWarnings("rawtypes")
public interface DocumentAppService<SAVE extends DocumentSaveReq, ACTION extends DocumentActionReq> {


    /**
     * detail.
     *
     * @author taitt
     * @param documentId type {@link Long}
     * @return {@link DocumentAppRes}
     * @throws Exception the exception
     */
    DocumentAppRes detail(Long documentId) throws Exception;

    /**
     * <p>
     * Get list process history document.
     * </p>
     *
     * @author tantm
     * @param documentId            type {@link Long}
     * @return {@link List<JpmHiTaskDto>}
     */
    List<JpmHiTaskDto> getListProcessHistoryDocument(Long documentId);

    /**
     * <p>
     * Inits the document.
     * </p>
     *
     * @author KhuongTH
     * @param formId            type {@link Long}
     * @return {@link DocumentAppRes}
     */
    DocumentAppRes initDocument(Long formId);

	/**
	 * save.
	 *
	 * @author Tan Tai
	 * @param efoDocDto type {@link EfoDocDto}
	 * @return {@link EfoDocDto}
	 * @throws Exception the exception
	 */
	EfoDocDto save(EfoDocDto efoDocDto) throws Exception;
	
	   /**
     * save.
     *
     * @author TaiTM
     * @param SAVE
     * @return SAVE
     * @throws Exception the exception
     */
	SAVE save(SAVE saveDto) throws Exception;
	
	SAVE saveBusiness(SAVE saveDto, EfoDocDto efoDocDto) throws Exception;

	/**
	 * action.
	 *
	 * @author Tan Tai
	 * @param efoDocDto type {@link EfoDocDto}
	 * @return {@link EfoDocDto}
	 * @throws Exception the exception
	 */
	EfoDocDto action(EfoDocDto efoDocDto) throws Exception;
	
    /**
     * action.
     *
     * @author TaiTM
     * @param efoDocDto type {@link EfoDocDto}
     * @return {@link EfoDocDto}
     * @throws Exception the exception
     */
	ACTION action(ACTION action, Locale locale) throws Exception;
	
	ACTION actionBusiness(ACTION action, EfoDocDto efoDocDto, Locale locale) throws Exception;

	/**
	 * getEfoDocDtoById.
	 *
	 * @author Tan Tai
	 * @param documentId type {@link Long}
	 * @return {@link EfoDocDto}
	 */
	EfoDocDto getEfoDocDtoById(Long documentId);

	/**
	 * setValueSaveDocument.
	 *
	 * @author Tan Tai
	 * @param documentSaveReq type {@link DocumentSaveReq}
	 * @return {@link EfoDocDto}
	 * @throws DetailException the detail exception
	 */
	EfoDocDto setValueSaveDocument(DocumentSaveReq documentSaveReq) throws DetailException;

	/**
	 * setValueActionDocument.
	 *
	 * @author Tan Tai
	 * @param documentActionReq type {@link DocumentActionReq}
	 * @return {@link EfoDocDto}
	 * @throws DetailException the detail exception
	 */
	EfoDocDto setValueActionDocument(DocumentActionReq documentActionReq) throws DetailException;
	
    /**
     * <p>
     * Get list sla document.
     * </p>
     *
     * @author TrieuVD
     * @param documentId
     *            type {@link Long}
     * @return {@link List<SlaForTaskRes>}
     */
    public List<TaskSlaRes> getListSlaDocument(Long documentId);
    
    /**
    *
    * @author TaiTM
    * @param documentId
    * @return JpmButtonWrapper<JpmButtonForDocDto>
    */
   public ACTION loadData(Long documentId);
    
    /**
     *
     * @author TaiTM
     * @param documentId
     * @return JpmButtonWrapper<JpmButtonForDocDto>
     */
    public JpmButtonWrapper<JpmButtonForDocDto> handleButtonByBusiness(ACTION data,
            JpmButtonWrapper<JpmButtonForDocDto> jpmButtons);
    
    /**
    *
    * @author TaiTM
    * @param documentActionReq
    * @return json
    * @description tạo ra chuỗi json chứa thông tin của param để truyền vào email
    */
    public String setInputJsonForEmail(ACTION documentActionReq);

	/**
	 * @param docId
	 * @param buttonText
	 * @throws Exception 
	 * @author vunt
	 * @return 
	 */
	EfoDocDto actionNextStep(Long docId, String stepCode, String buttonText) throws Exception;

	/**
	 * @param coreTaskId
	 * @param docId
	 * @param buttonText
	 * @param note
	 * @author vunt
	 */
	void createHistTask(Long docId, String buttonText, String note);

	/**
	 * @param documentActionReq
	 * @return
	 * @throws Exception 
	 */
	public ACTION actionList(ACTION documentActionReq, Locale locale) throws Exception;

	/**
	 * @author vunt
	 * @param documentActionReq
	 * @param efoDocDto
	 * @return
	 */
	ACTION getInfomationByDocId(DocumentActionReq documentActionReq) throws Exception;

	/**
	 * @author Vunt
	 * @param documentId
	 * @return
	 */
	DocumentAppRes detailHistory(Long documentId) throws Exception;

	ACTION saveDataBussiness(ACTION action) throws Exception;

	void setParamBusiness(ACTION documentActionReq, EfoDocDto efoDocDto) throws Exception;

	ACTION actionSaveBusiness(ACTION documentActionReq, EfoDocDto efoDocDto) throws Exception;

	void actionSlaBusiness(ACTION documentActionReq, EfoDocDto efoDocDto) throws Exception;
	
    public void sendMailProcess(ACTION abstractProcessDto, Integer nextStepNo, String nextStatusCode, Integer curStepNo,
            AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale) throws Exception;

	DocumentActionReq actionSkipStep(ACTION documentActionReq, String stepCode, String buttonText, Locale locale)
			throws Exception;
}

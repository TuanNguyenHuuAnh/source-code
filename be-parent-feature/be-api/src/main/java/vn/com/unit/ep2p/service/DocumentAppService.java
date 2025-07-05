/*******************************************************************************
 * Class        ：DocumentAppService
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.res.dto.TaskSlaRes;
import vn.com.unit.workflow.dto.JpmHiTaskDto;

/**
 * DocumentAppService.
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
public interface DocumentAppService {


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
	 * action.
	 *
	 * @author Tan Tai
	 * @param efoDocDto type {@link EfoDocDto}
	 * @return {@link EfoDocDto}
	 * @throws Exception the exception
	 */
	EfoDocDto action(EfoDocDto efoDocDto) throws Exception;

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
	 * @author vunt
	 * @param documentSaveReq
	 * @return
	 */
	EfoDocDto createDocument(DocumentSaveReq documentSaveReq);
}

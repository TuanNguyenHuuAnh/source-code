/*******************************************************************************
 * Class        ：DocumentMainFileService
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import java.util.Locale;

import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.res.dto.DocumentMainFileUploadReq;
import vn.com.unit.storage.dto.FileDownloadResult;

/**
 * DocumentMainFileService.
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
public interface DocumentMainFileService {

    /**
     * <p>
     * Upload and save.
     * </p>
     *
     * @author tantm
     * @param documentSaveReq            type {@link DocumentMainFileUploadReq}
     * @return {@link EfoOzDocMainFileDto}
     * @throws Exception             the exception
     */
    EfoOzDocMainFileDto uploadMainFile(DocumentMainFileUploadReq documentSaveReq) throws Exception;

    /**
     * <p>
     * Save oz doc main file.
     * </p>
     *
     * @author tantm
     * @param mainFileDto            type {@link EfoOzDocMainFileDto}
     * @param actionFlag            type {@link DocumentActionFlag}
     * @param isIncreaseVersion            type {@link boolean}
     * @param isMajor            type {@link boolean}
     * @return {@link EfoOzDocMainFileDto}
     * @throws Exception             the exception
     */
    EfoOzDocMainFileDto saveOzDocMainFile(EfoOzDocMainFileDto mainFileDto, DocumentActionFlag actionFlag, boolean isIncreaseVersion,
            boolean isMajor) throws Exception;


    /**
     * getOZDocMainFileById.
     *
     * @author taitt
     * @param id            type {@link Long}
     * @param docMainFileHistoryId            type {@link Long}
     * @param isOZD            type {@link boolean}
     * @param lang            type {@link Locale}
     * @return {@link FileDownloadResult}
     * @throws DetailException the detail exception
     */
    FileDownloadResult getOZDocMainFileById(Long id, Long docMainFileHistoryId, boolean isOZD, Locale lang)  throws DetailException;

	/**
	 * buildEfoOzDocMainFileDto.
	 *
	 * @author Tan Tai
	 * @param efoDocDto type {@link EfoDocDto}
	 * @return {@link EfoOzDocMainFileDto}
	 */
	EfoOzDocMainFileDto buildEfoOzDocMainFileDto(EfoDocDto efoDocDto);

}

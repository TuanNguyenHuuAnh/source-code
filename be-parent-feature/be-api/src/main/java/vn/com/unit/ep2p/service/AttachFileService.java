/*******************************************************************************
 * Class        ：AttachFileService
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.Locale;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.dto.req.AttachFileUploadReq;
import vn.com.unit.ep2p.dto.res.AttachFileDowloadRes;
import vn.com.unit.ep2p.dto.res.AttachFileUploadRes;

/**
 * AttachFileService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface AttachFileService {

    static final String TABLE_ALIAS_ATTACH_FILE = "attach";

    /**
     * <p>
     * Upload attach file.
     * </p>
     *
     * @param attachFileUploadReq
     *            type {@link AttachFileUploadReq}
     * @return {@link AttachFileUploadRes}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public AttachFileUploadRes uploadAttachFile(AttachFileUploadReq attachFileUploadReq) throws DetailException;
    
    /**
     * <p>
     * Get attach file list.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link ObjectDataRes<JcaAttachFileDto>}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public ObjectDataRes<JcaAttachFileDto> getAttachFileList(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException;
    
    /**
     * <p>
     * Delete attach file.
     * </p>
     *
     * @param attachFileId
     *            type {@link Long}
     * @author TrieuVD
     * @throws DetailException 
     */
    public void deleteAttachFile(Long attachFileId) throws DetailException;
    
    /**
     * <p>
     * Dowload attach file.
     * </p>
     *
     * @param attachFileId
     *            type {@link Long}
     * @return {@link AttachFileDowloadRes}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public AttachFileDowloadRes dowloadAttachFile(Long attachFileId) throws DetailException;
    
    /**
     * <p>
     * Update reference id.
     * </p>
     *
     * @param referenceKey
     *            type {@link String}
     * @param referenceId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author TrieuVD
     */
    public void updateReferenceId(String referenceKey, Long referenceId) throws DetailException;

	public String uploadFileToServer(Long userId, MultipartFile file, Locale language) throws AppException;
}

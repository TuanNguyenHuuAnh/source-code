/*******************************************************************************
 * Class        ：EfoOzDocMainFileService
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：NhanNV
 * Change log   ：2020/11/18：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;

/**
 * EfoOzDocMainFileService.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoOzDocMainFileService {

    /**
     * <p>
     * Save efo oz doc main file.
     * </p>
     *
     * @param efoOzDocMainFile
     *            type {@link EfoOzDocMainFileDto}
     * @param actionFlag
     *            type {@link DocumentActionFlag}
     * @param isIncreaseVersion
     *            type {@link boolean}
     * @param isMajor
     *            type {@link boolean}
     * @return {@link EfoOzDocMainFileDto}
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public EfoOzDocMainFileDto saveEfoOzDocMainFile(EfoOzDocMainFile efoOzDocMainFile, DocumentActionFlag actionFlag,
            boolean isIncreaseVersion, boolean isMajor) throws Exception;

    /**
     * <p>Generate doc file name.</p>
     *
     * @param docId type {@link Long}
     * @param majorVersion type {@link Long}
     * @param minorVersion type {@link Long}
     * @param isFinal type {@link boolean}
     * @param isOzd type {@link boolean}
     * @return {@link String}
     * @author tantm
     */
    String generateDocFileName(Long docId, Long majorVersion, Long minorVersion, boolean isFinal, boolean isOzd);

    /**
     * <p>Get efo oz doc main file dto by id.</p>
     *
     * @param mainFileId type {@link Long}
     * @return {@link EfoOzDocMainFileDto}
     * @author tantm
     */
    public EfoOzDocMainFileDto getEfoOzDocMainFileDtoById(Long mainFileId);

    /**
     * <p>Get one efo oz doc main file.</p>
     *
     * @param id type {@link Long}
     * @return {@link EfoOzDocMainFile}
     * @author tantm
     */
    public EfoOzDocMainFile getEfoOzDocMainFileById(Long id);

    /**
     * <p>
     * Get efo oz doc main file dto by doc id.
     * </p>
     *
     * @param docId
     *            type {@link Long}
     * @return {@link EfoOzDocMainFileDto}
     * @author tantm
     */
    public EfoOzDocMainFileDto getEfoOzDocMainFileDtoByDocId(Long docId);

    /**
     * getEfoOzDocMainFileDtoByVersionId.
     *
     * @param docMainFileVersionId
     *            type {@link Long}
     * @return {@link EfoOzDocMainFileDto}
     * @author taitt
     */
    EfoOzDocMainFileDto getEfoOzDocMainFileDtoByVersionId(Long docMainFileVersionId);

}

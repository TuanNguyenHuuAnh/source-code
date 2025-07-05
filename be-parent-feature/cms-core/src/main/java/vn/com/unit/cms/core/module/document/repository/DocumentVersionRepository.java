/*******************************************************************************
 * Class        ：DocumentVersionRepository
 * Created date ：2017/04/24
 * Lasted date  ：2017/04/24
 * Author       ：thuydtn
 * Change log   ：2017/04/24：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.document.dto.DocumentVersionDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.document.entity.DocumentDetail;
import vn.com.unit.db.repository.DbRepository;

public interface DocumentVersionRepository extends DbRepository<DocumentDetail, Long> {
    /**
     * @param offset
     * @param sizeOfPage
     * @return
     */
    public List<DocumentDetail> findAllActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage);

    public int countActive();

    /**
     * 
     * @param documentId
     * @return
     */
    public DocumentDetail findDocumentDetailByDocumentId(@Param("documentId") Long documentId,
            @Param("version") Long version);

    /**
     * 
     * @param documentId
     * @return
     */
    public Integer findCurrentVersionByDocumentId(@Param("documentId") Long documentId);

    /**
     * 
     * @param documentId
     */
    @Modifying
    public void updateAllToOldVersionByDocumentId(@Param("documentId") Long documentId);

    /**
     * 
     * @param documentId
     * @return
     */
    public List<DocumentVersionDto> findAllVersionObjectByDocumentId(@Param("documentId") Long documentId);

    /**
     * @param shareToken
     * @param documentId
     * @param versionId
     */
    @Modifying
    public void saveShareToken(@Param("shareToken") byte[] shareToken, @Param("timeStamp") String timeStamp,
            @Param("documentId") Long documentId, @Param("versionId") Long versionId);

    /**
     * @param documentId
     * @return
     */
    public String findDownloadUrlByDocumentId(@Param("documentId") Long documentId);
    
    public List<DocumentVersionDto> getListRegulation(@Param("languageCode") String languageCode);
}

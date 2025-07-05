/*******************************************************************************
 * Class        ：AttachFileRepository
 * Created date ：2019/07/26
 * Lasted date  ：2019/07/26
 * Author       ：taitt
 * Change log   ：2019/07/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.entity.JcaAttachFile;
import vn.com.unit.core.repository.JcaAttachFileRepository;

/**
 * AttachFileRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface AttachFileRepository extends JcaAttachFileRepository {

    /**
     * 
     * findAttachFileDtoByReferenceIdAndReference
     * 
     * @param referenceId
     * @param reference
     * @return
     * @author taitt
     */
    List<JcaAttachFileDto> findAttachFileDtoByReferenceIdAndReference(@Param("referenceId") Long referenceId,
            @Param("reference") String reference);

    /**
     * 
     * findAttachFileTempDtoByReferenceIdAndReference
     * 
     * @param tempAttachList
     * @param reference
     * @return
     * @author taitt
     */
    List<JcaAttachFileDto> findAttachFileTempDtoByReferenceIdAndReference(@Param("tempAttachList") List<Long> tempAttachList,
            @Param("reference") String reference);

    /**
     * 
     * findAttachFileByTempFileId
     * 
     * @param tempId
     * @return
     * @author taitt
     */
    JcaAttachFile findAttachFileByTempFileId(@Param("tempId") Long tempId);

    /**
     * 
     * findListAttachFileAPIByListId
     * 
     * @param listIdFile
     * @return
     * @author taitt
     */
//    List<ResAttachFileDto> findListAttachFileAPIByListId(@Param("listId") List<Long> listIdFile);

    /**
     * 
     * updateReferIdByListId
     * 
     * @param attachFile
     * @param listId
     * @return
     * @author taitt
     */
    @Modifying
    void updateReferIdByListId(@Param("referenceId") Long referenceId, @Param("updatedBy") String updatedBy,
            @Param("updatedDate") Date updatedDate, @Param("listId") List<Long> listId);

    List<JcaAttachFileDto> findAttachFileDtoByReferenceIdAndReferenceArchive(@Param("referenceId") Long referenceId,
            @Param("reference") String reference);

    JcaAttachFile findOneArchive(@Param("id") Long id);
}

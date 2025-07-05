/*******************************************************************************
 * Class        ：AttachedFileRepository
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.entity.AttachedFile;

//import vn.com.unit.jcanary.entity.AttachedFile;

/**
 * AttachedFileRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface AssetAttachedFileRepository extends DbRepository<AttachedFile, Long> {

    /**
     * findListAttachedFileByRef
     *
     * @param ref
     * @return
     * @author VinhLT
     */
    List<AttachedFile> findListAttachedFileByRef(@Param("ref") String ref);
}
/*******************************************************************************
 * Class        :JcaMenuPathRepository
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :TaiTM
 * Change log   :2020/12/10:01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.ParentPath;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaMenuPathRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface ParentPathRepository extends DbRepository<ParentPath, Long> {

    /**
     * 
     * <p>
     * Get JcaMenuPath by descendantId
     * </p>
     * 
     * 
     * @param descendantId
     * @return JcaMenuPath
     * @author TaiTM
     */
    public ParentPath getJcaMenuPathByDescendantId(@Param("descendantId") Long descendantId,
            @Param("type") String type);

    /**
     * <p>
     * Delete menu path by descendant id.
     * </p>
     *
     * @param descendantId type {@link Long}
     * @author TaiTM
     */
    @Modifying
    void deleteMenuPathByDescendantId(@Param("descendantId") Long descendantId, @Param("type") String type);

    public ParentPath findOneByPK(@Param("ancestorId") Long ancestorId, @Param("descendantId") Long descendantId,
            @Param("type") String type);

}
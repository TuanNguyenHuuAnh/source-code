/*******************************************************************************
 * Class        :JcaMenuPathRepository
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :SonND
 * Change log   :2020/12/10:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.entity.JcaMenuPath;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaMenuPathRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaMenuPathRepository extends DbRepository<JcaMenuPath, Long> {

    /**
     * 
     * <p>
     * Get JcaMenuPath by descendantId
     * </p>
     * 
     * 
     * @param descendantId
     * @return JcaMenuPath
     * @author SonND
     */
    JcaMenuPath getJcaMenuPathByDescendantId(@Param("descendantId") Long descendantId);

    /**
     * <p>
     * Delete menu path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author SonND
     */
    @Modifying
    void deleteMenuPathByDescendantId(@Param("descendantId")Long descendantId);

    JcaMenuPath findOneByPK(@Param("ancestorId")Long ancestorId, @Param("descendantId")Long descendantId);
    
    /**
     * <p>
     * Get jca menu path dto list default.
     * </p>
     *
     * @author TrieuVD
     * @return {@link List<JcaMenuPathDto>}
     */
    public List<JcaMenuPathDto> getJcaMenuPathDtoListDefault();

}
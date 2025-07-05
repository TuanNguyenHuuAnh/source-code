/*******************************************************************************
 * Class        :JcaMenuPathService
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :TaiTM
 * Change log   :2020/12/10 01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.admin.all.dto.ParentPathDto;
import vn.com.unit.cms.admin.all.entity.ParentPath;

/**
 * JcaMenuPathService.
 *
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface ParentPathService {

    /**
     * <p>
     * Save entity with create, update
     * </p>
     * .
     *
     * @param jcaMenuPath type {@link ParentPath}
     * @return JcaMenuPath
     * @author TaiTM
     */
    public ParentPath saveParentPath(ParentPath jcaMenuPath);

    /**
     * <p>
     * Save jcaMenuPath with jcaMenuPathDto
     * </p>
     * .
     *
     * @param jcaMenuPathDto type {@link ParentPathDto}
     * @return JcaMenuPath
     * @author TaiTM
     */
    public ParentPath saveParentPathDto(ParentPathDto jcaMenuPathDto);

    /**
     * <p>
     * Get jcaMenuPath by descendant id
     * </p>
     * .
     *
     * @param descendantId type {@link Long}
     * @return JcaMenuPath
     * @author TaiTM
     */
    public ParentPath getJcaMenuPathByDescendantId(Long descendantId, String type);

    /**
     * <p>
     * Delete menu path by descendant id.
     * </p>
     *
     * @param descendantId type {@link Long}
     * @author TaiTM
     */
    public void deleteMenuPathByDescendantId(Long descendantId, String type);
}

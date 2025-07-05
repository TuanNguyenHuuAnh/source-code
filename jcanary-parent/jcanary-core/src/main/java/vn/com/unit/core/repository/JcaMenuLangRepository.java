/*******************************************************************************
 * Class        ：JcaMenuLangRepository
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaMenuLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaMenuLangRepository extends DbRepository<JcaMenuLang, Long> {

    /**
     * <p>
     * Find one by PK.
     * </p>
     *
     * @author tantm
     * @param menuId
     *            type {@link Long}
     * @param langId
     *            type {@link Long}
     * @return {@link JcaMenuLang}
     */
    JcaMenuLang findOneByPK(@Param("menuId") Long menuId, @Param("langId") Long langId);

    /**
     * <p>
     * Delete jca menu lang by menu id.
     * </p>
     *
     * @author tantm
     * @param id
     *            type {@link Long}
     * @return {@link int}
     */
    @Modifying
    int deleteJcaMenuLangByMenuId(@Param("menuId") Long id);
    
    /**
     * <p>
     * Get jca menu lang dto list default.
     * </p>
     *
     * @author TrieuVD
     * @return {@link List<JcaMenuLangDto>}
     */
    public List<JcaMenuLangDto> getJcaMenuLangDtoListDefault();
}

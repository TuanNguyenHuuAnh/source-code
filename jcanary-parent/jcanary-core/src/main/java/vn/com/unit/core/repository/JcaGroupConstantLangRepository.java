/*******************************************************************************
 * Class        ：JcaGroupConstantLanguageRepository
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：tantm
 * Change log   ：2020/12/24：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaGroupConstantLang;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaGroupConstantLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaGroupConstantLangRepository extends DbRepository<JcaGroupConstantLang, Long> {

    /**
     * <p>
     * Get jca group constant lang by PK.
     * </p>
     *
     * @param groupId
     *            type {@link Long}
     * @param long1
     *            type {@link String}
     * @return {@link JcaGroupConstantLang}
     * @author tantm
     */
    JcaGroupConstantLang getJcaGroupConstantLangByPK(@Param("groupId")Long groupId, @Param("langId")Long long1);

}

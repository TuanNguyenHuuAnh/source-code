/*******************************************************************************
 * Class        ：AuthorityRepository
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：vinhlt
 * Change log   ：2021/02/01：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.entity.JcaAuth;
import vn.com.unit.db.repository.DbRepository;

/**
 * AuthorityRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public interface JcaAuthRepository extends DbRepository<JcaAuth, Long> {
    int countByToken(@Param("token") String token);
    @Modifying
    void removeToken(@Param("token") String token);
}

/*******************************************************************************
 * Class        :AccountRepository
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.db.repository.DbRepository;
public interface JcaAccountRegisterRepository extends DbRepository<JcaAccountRegister, Long> {

	JcaAccountRegister findByAccountId(@Param("userId") Long userId);
    
}
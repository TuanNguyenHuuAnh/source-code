/*******************************************************************************
 * Class        AccountOrgRepository
 * Created date 2016/07/19
 * Lasted date  2016/07/19
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.core.repository.JcaAccountOrgRepository;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountOrgDelegateDto;
import vn.com.unit.ep2p.admin.entity.AccountSms;

import java.util.Date;
import java.util.List;

/**
 * AccountOrgRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AccountSmsRepository extends DbRepository<AccountSms, Long> {

	AccountSms findByAccountId(@Param("accountId") Long id, @Param("currentDate") Date currentDate, @Param("typeOtp") String typeOtp);
}

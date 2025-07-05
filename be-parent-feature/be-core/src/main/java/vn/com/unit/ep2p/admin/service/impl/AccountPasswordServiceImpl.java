/*******************************************************************************
 * Class        : AccountPasswordServiceImpl
 * Created date : 2018/06/22
 * Lasted date  : 2018/06/22
 * Author       : LongPNT
 * Change log   : 2018/06/22:01-00 LongPNT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.ep2p.admin.entity.AccountPassword;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.repository.AccountPasswordRepository;
import vn.com.unit.ep2p.admin.service.AccountPasswordService;

/**
 * AccountPasswordServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author LongPNT
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountPasswordServiceImpl implements AccountPasswordService {

	/** flag to check account is already logged */
	private static final Long ATTR_ACCOUNT_ALREADY_LOGGED_IN = 1L;

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(AccountPasswordServiceImpl.class);

	/** AccountPasswordRepository */
	@Autowired
	private AccountPasswordRepository accountPasswordRespository;

	@Autowired
	private JcaAccountService jcaAccountService;

	/** systemConfig */
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
    private CommonService comService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean checkWhenLogin(Long accountId) throws NotFoundException {
		if(isFirstLogin(accountId)) {
			updateWhenFirstLogin(accountId);
			return true;
		}
		return false;
	}

	@Override
	public void createNewPassword(Long accountId, String password) throws Exception {
		AccountPassword newAccountPassword = new AccountPassword();
		newAccountPassword.setAccountId(accountId);
		try {
			String enPassword = passwordEncoder.encode(password);
			if (passwordHaveBeenUsed(accountId, enPassword)) {
				logger.info("Password have been used");
				throw new Exception("Password have been used");
			}
			newAccountPassword.setPassword(enPassword);
			newAccountPassword.setEffectedDate(comService.getSystemDateTime());
			newAccountPassword.setCreatedId(UserProfileUtils.getAccountId());
			newAccountPassword.setCreatedDate(comService.getSystemDateTime());
			accountPasswordRespository.save(newAccountPassword);
		} catch (NoSuchAlgorithmException e) {
			logger.info(String.format(e.getMessage()));
		}
	}


	@Override
	public List<String> getListHistoryPassword(Long accountId) {
		// TODO inserted by LongPNT: get numberOfOldPassword from database configuration
		int numberOfOldPassword = Integer.parseInt(systemConfig.getConfig(SystemConfig.NUMBER_PASSWORD_USED) != null ? systemConfig.getConfig(SystemConfig.NUMBER_PASSWORD_USED) : "5");
		
		List<String> listHistoryPassword = new ArrayList<>();
		DatabaseTypeEnum dataType = DatabaseTypeEnum.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
		switch (dataType) {
		case SQLSERVER:
			listHistoryPassword = accountPasswordRespository.getListHistoryPassword(accountId, numberOfOldPassword);
			break;
		case MYSQL:
			listHistoryPassword = accountPasswordRespository.getListHistoryPasswordMySQL(accountId,
					numberOfOldPassword);
			break;
		case ORACLE:
			listHistoryPassword = accountPasswordRespository.getListHistoryPasswordOracle(accountId,
					numberOfOldPassword);
			break;
		default:
			break;
		}
		return listHistoryPassword == null ? new ArrayList<>() : listHistoryPassword;
	}

	@Override
	public boolean isFirstLogin(Long accountId) {
		AccountPassword accPass = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
		if (accPass == null) {
		    return true;
		}
		return false;
	}

	@Override
	public boolean passwordHaveBeenUsed(Long accountId, String password) {
		List<String> listHistoryPassword = getListHistoryPassword(accountId);
		return Stream.of(listHistoryPassword).anyMatch(e -> e.toString().equals(password));
	}


	@Override
	public void updateWhenFirstLogin(Long accountId) {
		JcaAccount account = jcaAccountService.findOne(accountId);
		AccountPassword currentAccountPassword = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
		if(currentAccountPassword != null){
			currentAccountPassword.setEffectedDate(new Date());
			currentAccountPassword.setExpiredDate(new Date(253402214400000L));
			currentAccountPassword.setPassword(account.getPassword());
		} else {
			currentAccountPassword = new AccountPassword();
			currentAccountPassword.setAccountId(accountId);
			currentAccountPassword.setEffectedDate(new Date());
			currentAccountPassword.setExpiredDate(new Date(253402214400000L));
			currentAccountPassword.setPassword(account.getPassword());
			currentAccountPassword.setFirstLogin(ATTR_ACCOUNT_ALREADY_LOGGED_IN);
			currentAccountPassword.setFirstLoginDate(comService.getSystemDateTime());
			currentAccountPassword.setCreatedId(UserProfileUtils.getAccountId());
			currentAccountPassword.setCreatedDate(comService.getSystemDateTime());
		}
		accountPasswordRespository.save(currentAccountPassword);
	}

	@Override
	public boolean checkPassExpired(JcaAccount account){
	    //check if first login ->insert jca_m_account_password
	    try {
	        Long accountId = account.getId();
            Long companyId = account.getCompanyId();
            //check pass expired
            AccountPassword accPass = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
            if(accPass != null){
                long expiredPass = Long.parseLong(systemConfig.getConfig(SystemConfig.EXPIRED_PASSWORD, companyId) != null ? systemConfig.getConfig(SystemConfig.EXPIRED_PASSWORD, companyId) : "0");
                //disable expire password
                if(expiredPass <= 0L){
                    return false;
                }
                LocalDate expiredDate = accPass.getEffectedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(expiredPass);
                LocalDate currentDate = LocalDate.now();
                if(!expiredDate.isAfter(currentDate)){
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Exception ", e);
            return false;
        }
	    return false;
	}
	
	@Override
	public void updateAccountPass(Long accountId, String enPassword) throws NotFoundException{
        AccountPassword accPass = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
        // if(!isFirstLogin(accountId)){
        // accPass = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
        // }
        if (accPass != null) {
            // set expiredDate oldPass
            accPass.setExpiredDate(comService.getSystemDateTime());
            accountPasswordRespository.update(accPass);
        }
        // create new accountPass
        AccountPassword newAccPass = new AccountPassword();
        @SuppressWarnings("deprecation")
		Date expiredDate = new Date(8009, 11, 30);
        newAccPass.setAccountId(accountId);
        newAccPass.setExpiredDate(expiredDate);
        newAccPass.setCreatedId(UserProfileUtils.getAccountId());
        newAccPass.setCreatedDate(comService.getSystemDateTime());
        newAccPass.setEffectedDate(comService.getSystemDateTime());
        newAccPass.setPassword(enPassword);
        accountPasswordRespository.create(newAccPass);
	}
	
	@Override
	public AccountPassword getHistoryPassword(Long accountId){
	    return  accountPasswordRespository.getAccountPasswordByAccountId(accountId);
	}

    @Override
    public void updateExpiredDateAccountPass(Long accountId) {
        if(null != accountId && !isFirstLogin(accountId)) {
            AccountPassword accPass = accountPasswordRespository.getAccountPasswordByAccountId(accountId);
            //set expiredDate oldPass
            accPass.setExpiredDate(comService.getSystemDateTime());
            accountPasswordRespository.save(accPass);
        }
    }

}
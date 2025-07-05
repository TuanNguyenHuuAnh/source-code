/*******************************************************************************
 * Class        ：AccountService
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.BaseRestService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.entity.AccountSms;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.dto.req.*;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.dto.res.ForgotPasswordByAgentCodeRes;
import vn.com.unit.ep2p.dto.res.RegisterAccountRes;

/**
 * AccountService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface ApiAccountService extends BaseRestService<ObjectDataRes<JcaAccountDto>, JcaAccountDto>{
    
    /**
     * countAccountDtoByCondition.
     *
     * @param jcaAccountSearchDto
     *            type {@link JcaAccountSearchDto}
     * @return {@link int}
     * @author taitt
     */
    int countAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto);

    /**
     * getAccountDtoByCondition.
     *
     * @param jcaAccountSearchDto
     *            type {@link JcaAccountSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the account dto by condition
     * @author taitt
     */
    List<JcaAccountDto> getAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto,Pageable pageable);

    /**
     * update.
     *
     * @param reqAccountUpdateDto
     *            type {@link AccountUpdateReq}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void update(AccountUpdateReq reqAccountUpdateDto) throws DetailException;

    /**
     * create.
     *
     * @param reqAccountAddDto
     *            type {@link AccountAddReq}
     * @return {@link AccountInfoRes}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    AccountInfoRes create(AccountAddReq reqAccountAddDto) throws DetailException;
    
    RegisterAccountRes create(RegisterAccountReq registerAccountReq) throws Exception;


    /**
     * getAccountInfoResById.
     *
     * @param id
     *            type {@link Long}
     * @return the account info res by id
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    AccountInfoRes getAccountInfoResById(Long id) throws DetailException;
    
    
    /**
     * <p>
     * Upload avatar.
     * </p>
     *
     * @param imgBase64
     *            type {@link String}
     * @param fileName
     *            type {@link String}
     * @param jcaAccountDto
     *            type {@link JcaAccountDto}
     * @return {@link JcaAccountDto}
     * @throws Exception
     *             the exception
     * @author sonnd
     */
    JcaAccountDto uploadAvatar(String imgBase64,String fileName, JcaAccountDto jcaAccountDto) throws Exception;
    
    /**
     * <p>
     * Change password.
     * </p>
     *
     * @param accountChangePasswordReq
     *            type {@link AccountChangePasswordReq}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    void changePassword(AccountChangePasswordReq accountChangePasswordReq) throws Exception;

    /**
     * getListEnumSearch.
     *
     * @return {@link List<EnumsParamSearchRes>}
     * @author taitt
     */
    List<EnumsParamSearchRes> getListEnumSearch();
    
    /**
     * getAccountInfoResByRegisterAccountReq.
     
     * @return the account info res
     * @throws DetailException
     *             the detail exception
     * @author TaiTM
     */
    public AccountInfoRes getAccountInfoResByRegisterAccountReq(RegisterAccountReq registerAccountReq)
            throws DetailException;
    
    public int countAccountByEmail(String email);
    
    public int countAccountByPhone(String phone);
    
    public AccountInfoRes getAccountInfoResByUsername(String username) throws DetailException;
    
    public ForgotPasswordByAgentCodeRes forgotPasswordByAgentCode(String agentCode) throws Exception;
    
    public AccountSms forgotPassword(ForgotPasswordReq forgotPasswordReq) throws Exception;

    public String renewPasswordByOtp(RenewPasswordReq renewPasswordReq) throws Exception;
    
    public boolean isFirstLogin(Long userId) throws DetailException;

	JcaAccount changePasswordAccountGa(AccountChangePasswordGadReq accountChangePasswordReq) throws DetailException, Exception;

    boolean checkOtp(CheckOtpReq checkOtpReq) throws DetailException;
    
    public String getUserName(String agentCode);

    Db2AgentDto checkAgentExist(String agentCode) throws Exception;

    boolean checkPasswordAgent(JcaAccount jcaAccount, String passwordOld) throws Exception;
    boolean checkPasswordOld(String oldPassword, String password);

    RegisterAccountRes getAccountCandidateByEmail(String email, String fullname, String phone);

	JcaAccountRegister saveJcaAccountRegisterContacted(Long id) throws Exception;

    void loginGad(JcaAccount jcaAccount, String passwordGad) throws Exception;

    void resendOtpGad(JcaAccount jcaAccount) throws Exception;

    boolean checkOtpGad(JcaAccount jcaAccount, String otp) throws Exception;

    void renewPasswordAccountGa(AccountRenewPasswordGadReq accountRenewPasswordGadReq) throws Exception;
}
 
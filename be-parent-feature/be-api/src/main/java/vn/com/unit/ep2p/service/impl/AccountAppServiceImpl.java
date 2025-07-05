/*******************************************************************************
 * Class        ：AccountAppServiceImpl
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：Tan Tai
 * Change log   ：2021/03/11：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.FileDownloadInfoReq;
import vn.com.unit.ep2p.core.res.dto.DownloadFileInfoRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.AccountAppUpdateReq;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.service.AccountAppService;
import vn.com.unit.ep2p.service.ApiAccountService;
import vn.com.unit.ep2p.service.FileDownloadService;

/**
 * AccountAppServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountAppServiceImpl extends AbstractCommonService  implements AccountAppService {

	@Autowired
	FileDownloadService fileDownloadService;
	
	@Autowired
	ApiAccountService apiAccountService;

	@Override	
	public AccountInfoRes getUserProfile() throws DetailException {

		Long accountId = UserProfileUtils.getAccountId();

		JcaAccountDto jcaAccountDto = apiAccountService.detail(accountId);

		AccountInfoRes accountInfoRes = objectMapper.convertValue(jcaAccountDto, AccountInfoRes.class);

		try {
			DownloadFileInfoRes downlloadResult = fileDownloadService.downloadFileLocal(
					new FileDownloadInfoReq(accountInfoRes.getAvatarRepoId(), accountInfoRes.getAvatar()));

			accountInfoRes.setImgBase64(downlloadResult.getBase64());
		} catch (Exception e) {
			e.fillInStackTrace();
		}

		return accountInfoRes;
	}
		
	@Override
	@Transactional
	public void updateUserProfile(AccountAppUpdateReq accountUpdateDtoReq) throws DetailException {
        Long id = UserProfileUtils.getAccountId();

        JcaAccountDto jcaAccountDto = apiAccountService.detail(id);
        if (null != jcaAccountDto) {
            try {
                
                jcaAccountDto.setReceivedEmail(accountUpdateDtoReq.getReceivedEmail());
                jcaAccountDto.setReceivedNotification(accountUpdateDtoReq.getReceivedNotification());
                
                // upload image
                if(CommonStringUtil.isNotBlank(accountUpdateDtoReq.getImgBase64()) && CommonStringUtil.isNotBlank(accountUpdateDtoReq.getFileName())) {
                	apiAccountService.uploadAvatar(accountUpdateDtoReq.getImgBase64(), accountUpdateDtoReq.getFileName(), jcaAccountDto);
                }
                
                apiAccountService.save(jcaAccountDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402803_APPAPI_ACCOUNT_UPDATE_INFO_ERROR);
            }

        }
	}
	
	
}

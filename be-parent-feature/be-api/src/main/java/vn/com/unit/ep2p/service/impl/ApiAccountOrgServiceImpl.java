/*******************************************************************************
 * Class        ：AccountOrgServiceImpl
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：SonND
 * Change log   ：2020/12/17：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountOrgSearchDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.core.enumdef.param.JcaAccountOrgSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountOrgService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.AccountOrgInfoReq;
import vn.com.unit.ep2p.core.req.dto.AccountOrgUpdateInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.res.AccountInfoRes;
import vn.com.unit.ep2p.dto.res.AccountOrgInfoRes;
import vn.com.unit.ep2p.dto.res.OrganizationInfoRes;
import vn.com.unit.ep2p.dto.res.PositionInfoRes;
import vn.com.unit.ep2p.service.AccountOrgService;
import vn.com.unit.ep2p.service.ApiAccountService;
import vn.com.unit.ep2p.service.OrganizationService;
import vn.com.unit.ep2p.service.PositionService;

//@Primary
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiAccountOrgServiceImpl extends AbstractCommonService implements AccountOrgService {

	@Autowired
	private JcaAccountOrgService jcaAccountOrgService;

	@Autowired
	private ApiAccountService apiAccountService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private PositionService positionService;

	@Override
	public ObjectDataRes<JcaAccountOrgDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable)
			throws DetailException {
		ObjectDataRes<JcaAccountOrgDto> resObj = null;
		try {
			/** init pageable */
			Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAccountOrg.class,
					JcaAccountOrgService.TABLE_ALIAS_JCA_ACCOUNT_ORG);
			/** init param search repository */
			JcaAccountOrgSearchDto reqSearch = this.buildJcaAccountOrgSearchDto(commonSearch);

			int totalData = this.countAccountOrgDtoByCondition(reqSearch);
			List<JcaAccountOrgDto> datas = new ArrayList<>();
			if (totalData > 0) {
				datas = this.getAccountOrgDtoByCondition(reqSearch, pageableAfterBuild);
			}
			resObj = new ObjectDataRes<>(totalData, datas);
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022301_APPAPI_ACCOUNT_ORG_LIST_ERROR);
		}
		return resObj;
	}

	private JcaAccountOrgSearchDto buildJcaAccountOrgSearchDto(MultiValueMap<String, String> commonSearch) {
		JcaAccountOrgSearchDto reqSearch = new JcaAccountOrgSearchDto();

		Long accountId = UserProfileUtils.getAccountId();

		String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch")
				: AppApiConstant.EMPTY;
		Boolean actived = null != commonSearch.getFirst("actived") ? Boolean.valueOf(commonSearch.getFirst("actived"))
				: null;
		List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums"))
				? commonSearch.get("multipleSeachEnums")
				: null;

		reqSearch.setAccountId(accountId);
		reqSearch.setActived(actived);

		if (CommonCollectionUtil.isNotEmpty(enumsValues)) {
			for (String enumValue : enumsValues) {
				switch (JcaAccountOrgSearchEnum.valueOf(enumValue)) {
				case ORG_NAME:
					reqSearch.setOrgName(keySearch);
					break;
				case POSITION_NAME:
					reqSearch.setPositionName(keySearch);
					break;

				default:
					reqSearch.setOrgName(keySearch);
					reqSearch.setPositionName(keySearch);
					break;
				}
			}
		} else {
			reqSearch.setOrgName(keySearch);
			reqSearch.setPositionName(keySearch);
		}

		return reqSearch;
	}

	@Override
	public JcaAccountOrgDto save(JcaAccountOrgDto jcaAccountOrgDto) throws DetailException {
		jcaAccountOrgService.saveJcaAccountOrgDto(jcaAccountOrgDto);
		return jcaAccountOrgDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long accountForOrgId) throws DetailException {
		JcaAccountOrgDto jcaAccountOrgDto = jcaAccountOrgService.getJcaAccountOrgDtoById(accountForOrgId);
		if (null != jcaAccountOrgDto) {
			try {
				jcaAccountOrgService.deleteJcaAccountOrgById(accountForOrgId);
			} catch (Exception e) {
				handlerCastException.castException(e,
						AppApiExceptionCodeConstant.E4022304_APPAPI_ACCOUNT_ORG_DELETE_ERROR);
			}
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4022306_APPAPI_ACCOUNT_ORG_NOT_FOUND, true);
		}
	}

	@Override
	public JcaAccountOrgDto detail(Long id) throws DetailException {
		JcaAccountOrgDto jcaAccountOrgDto = jcaAccountOrgService.getJcaAccountOrgDtoById(id);
		if (null != jcaAccountOrgDto) {
			return jcaAccountOrgDto;
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4022306_APPAPI_ACCOUNT_ORG_NOT_FOUND, true);
		}
	}

	private void setUserId(Long userId, JcaAccountOrgDto jcaAccountOrgDto) throws DetailException {
		if (null != userId) {
			AccountInfoRes accountInfoRes = apiAccountService.getAccountInfoResById(userId);
			if (null != accountInfoRes) {
				jcaAccountOrgDto.setUserId(userId);
			} else {
				throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND, true);
			}

		}
	}

	private void setOrgId(Long orgId, JcaAccountOrgDto jcaAccountOrgDto) throws DetailException {
		if (null != orgId) {
			OrganizationInfoRes organizationInfoRes = organizationService.getOrganizationInfoResById(orgId);
			if (null != organizationInfoRes) {
				jcaAccountOrgDto.setOrgId(orgId);
			} else {
				throw new DetailException(AppApiExceptionCodeConstant.E4021802_APPAPI_ORGANIZATION_NOT_FOUND, true);
			}

		}
	}

	private void setPositionId(Long positionId, JcaAccountOrgDto jcaAccountOrgDto) throws DetailException {
		if (null != positionId) {
			PositionInfoRes positionInfoRes = positionService.getPositionInfoById(positionId);
			if (null != positionInfoRes) {
				jcaAccountOrgDto.setPositionId(positionId);
			} else {
				throw new DetailException(AppApiExceptionCodeConstant.E402906_APPAPI_POSITION_NOT_FOUND, true);
			}

		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(AccountOrgUpdateInfoReq accountOrgUpdateInfoReq) throws DetailException {
		Long id = accountOrgUpdateInfoReq.getOrgForAccountId();
		JcaAccountOrgDto jcaAccountOrgDto = jcaAccountOrgService.getJcaAccountOrgDtoById(id);
		if (null != jcaAccountOrgDto) {
			try {
				this.setUserId(accountOrgUpdateInfoReq.getUserId(), jcaAccountOrgDto);
				this.setOrgId(accountOrgUpdateInfoReq.getOrgId(), jcaAccountOrgDto);
				this.setPositionId(accountOrgUpdateInfoReq.getPositionId(), jcaAccountOrgDto);
				if (null != accountOrgUpdateInfoReq.getActived()) {
					jcaAccountOrgDto.setActived(accountOrgUpdateInfoReq.getActived());
				} else {
					accountOrgUpdateInfoReq.setActived(false);
				}
				if (null != accountOrgUpdateInfoReq.getMainFlag()) {
					jcaAccountOrgDto.setMainFlag(accountOrgUpdateInfoReq.getMainFlag());
				} else {
					jcaAccountOrgDto.setMainFlag(false);
				}
				this.save(jcaAccountOrgDto);
			} catch (Exception e) {
				handlerCastException.castException(e,
						AppApiExceptionCodeConstant.E4022303_APPAPI_ACCOUNT_ORG_UPDATE_INFO_ERROR);
			}
		} else {
			throw new DetailException(AppApiExceptionCodeConstant.E4022306_APPAPI_ACCOUNT_ORG_NOT_FOUND, true);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AccountOrgInfoRes create(AccountOrgInfoReq accountOrgInfoReq) throws DetailException {
		JcaAccountOrgDto jcaAccountOrgDto = new JcaAccountOrgDto();
		try {
			this.setUserId(accountOrgInfoReq.getUserId(), jcaAccountOrgDto);
			this.setOrgId(accountOrgInfoReq.getOrgId(), jcaAccountOrgDto);
			this.setPositionId(accountOrgInfoReq.getPositionId(), jcaAccountOrgDto);
			if (null != accountOrgInfoReq.getActived()) {
				jcaAccountOrgDto.setActived(accountOrgInfoReq.getActived());
			} else {
				jcaAccountOrgDto.setActived(false);
			}
			if (null != accountOrgInfoReq.getMainFlag()) {
				jcaAccountOrgDto.setMainFlag(accountOrgInfoReq.getMainFlag());
			} else {
				jcaAccountOrgDto.setMainFlag(false);
			}
			this.save(jcaAccountOrgDto);
//            jcaAccountOrgDto = this.detail(jcaAccountOrgDto.getAccountOrgId()); 
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022302_APPAPI_ACCOUNT_ORG_ADD_ERROR);
		}
		return this.mapperJcaAccountOrgDtoToAccountOrgInfoRes(jcaAccountOrgDto);
	}

	private AccountOrgInfoRes mapperJcaAccountOrgDtoToAccountOrgInfoRes(JcaAccountOrgDto jcaAccountOrgDto) {
		return objectMapper.convertValue(jcaAccountOrgDto, AccountOrgInfoRes.class);
	}

	@Override
	public int countAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto) {
		return jcaAccountOrgService.countJcaAccountOrgDtoByCondition(jcaAccountOrgSearchDto);
	}

	@Override
	public List<JcaAccountOrgDto> getAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto,
			Pageable pageable) {
		return jcaAccountOrgService.getJcaAccountOrgDtoByCondition(jcaAccountOrgSearchDto, pageable);
	}

}

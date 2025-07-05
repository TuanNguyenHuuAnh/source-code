/*******************************************************************************
 * Class        AccountOrgServiceImpl
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountDetailDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountEditDto;
import vn.com.unit.ep2p.admin.repository.AccountOrgRepository;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.OrgInfoService;
import vn.com.unit.ep2p.admin.utils.Utility;
import vn.com.unit.ep2p.constant.CommonConstant;

/**
 * AccountOrgServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountOrgServiceImpl implements AccountOrgService {

	@Autowired
	private AccountOrgRepository accOrgRepository;

	@Autowired
	private CommonService comService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	OrgInfoService orgInfoService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Long> findOrgIdListByAccountId(Long accountId) {
		return accOrgRepository.findOrgIdListByAccountId(accountId);

	}

	@Override
	public List<JcaAccountOrgDto> findAccountOrgDtoByAccountId(Long accountId) {
		return accOrgRepository.findAccountOrgDtoByAccountId(accountId);
	}

	@Override
	public void deleteById(Long id) {
		JcaAccountOrg accountOrg = accOrgRepository.findOne(id);
		if (null != accountOrg) {
			// accountOrg.setDeletedDate(comService.getSystemDateTime());
			// accountOrg.setDeletedId(UserProfileUtils.getAccountId());
			accOrgRepository.save(accountOrg);
		}
	}

	@Override
	public boolean checkStartEndDate(List<JcaAccountOrgDto> listOrgForAccountDto) {
//        for (JcaAccountOrgDto rfad : listOrgForAccountDto) {
//            if (rfad.getEffectedDate() != null && rfad.getExpiredDate() != null) {
//                if (rfad.getEffectedDate().after(rfad.getExpiredDate())) {
//                    return true;
//                }
//            } else {
//                return true;
//            }
//        }
		return false;
	}

	@Override
	public void updateOrgForAccount(JcaAccountEditDto accountDto) throws ParseException {
		Date date = comService.getSystemDateTime();
		Long userId = UserProfileUtils.getAccountId();
		List<JcaAccountOrgDto> listOrgForAccountDto = accountDto.getListOrgForAccount();
		accOrgRepository.deleteByAccountId(accountDto.getId());
		for (JcaAccountOrgDto accountOrgDto : listOrgForAccountDto) {
			JcaAccountOrg accountOrg = objectMapper.convertValue(accountOrgDto, JcaAccountOrg.class);
			accountOrg.setAccountId(accountDto.getId());
			accountOrg.setMainFlag(false);
			accountOrg.setCreatedId(userId);
			accountOrg.setCreatedDate(date);
			accOrgRepository.create(accountOrg);

		}
	}

	@Override
	public List<OrgNode> findOrgByAccountId(Long accountId) {
		List<OrgNode> orgNodes = new ArrayList<OrgNode>();
		JcaAccountDetailDto accountDto = accountService.findAccountDetailDtoById(accountId);
		if (accountDto.getId() != null) {
			Long companyId = accountDto.getCompanyId();
			// orgTree
			List<Long> companyIdList = new ArrayList<>();
			companyIdList.add(companyId);
			orgNodes = orgInfoService.getNodeByCompanyIdList(companyIdList);
		}
		return orgNodes;
	}

	@Override
	public String validateOrgList(List<JcaAccountOrgDto> accountOrgDtos, Locale locale) {
		if (accountOrgDtos != null) {
			// check org
			for (JcaAccountOrgDto accountOrgDto : accountOrgDtos) {
				if (accountOrgDto.getOrgId() == null) {
					return messageSource.getMessage("message.null.org", null, locale);
				}
				if (accountOrgDto.getPositionId() == null) {
					return messageSource.getMessage("message.null.position", null, locale);
				}
			}
			// check dup
			for (int i = 0; i < accountOrgDtos.size(); i++) {
				for (int j = i + 1; j < accountOrgDtos.size(); j++) {
					if (!accountOrgDtos.get(i).getOrgId().equals(CommonConstant.UNKNOWN_ID)
							&& accountOrgDtos.get(i).getOrgId().equals(accountOrgDtos.get(j).getOrgId())) {
						return messageSource.getMessage("message.duplicate.org", null, locale);
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<Select2Dto> findSelect2DtoByAccountId(Long accountId, Long companyId, String keySearch,
			boolean isPaging) {
		return accOrgRepository.findSelect2DtoByAccountId(keySearch, accountId, companyId, isPaging);
	}

	@Override
	public void updateOrgIdByOrgCode(Long orgId, String orgCode, Long companyId) {
		if (orgId != null && orgCode != null) {
			accOrgRepository.updateOrgIdByOrgCode(orgId, orgCode, companyId);
		}
	}

	@Override
	public PageWrapper<AccountListDto> findAccountByOrgId(int page, int pageSize, Long orgId) {
		// Init PageWrapper
		List<Integer> listPageSize = systemConfig.getListPage(pageSize);
		int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);

		PageWrapper<AccountListDto> pageWrapper = new PageWrapper<AccountListDto>(page, sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);

		int count = accOrgRepository.countAccountByOrgId(orgId);

		List<AccountListDto> accountList = new ArrayList<AccountListDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			accountList = accOrgRepository.findAccountByOrgId(offsetSQL, sizeOfPage, orgId);
		}

		pageWrapper.setDataAndCount(accountList, count);
		return pageWrapper;
	}

	/**
	 * @author KhuongTH
	 */
	@Override
	public List<Select2Dto> findSelect2DtoUserOwnsByAccountId(Long accountId, String keySearch, boolean isPaging) {
		return accOrgRepository.findSelect2DtoUserOwnsByAccountId(keySearch, accountId, isPaging);
	}

	@Override
	public void updatePositionIdByPositionCode(Long positionId, String positionCode, Long companyId) {
		if (null != positionCode && StringUtils.isNotBlank(positionCode)) {
			accOrgRepository.updatePositionIdByPositionCode(positionId, positionCode, companyId);
		}
	}

	@Override
	public List<Select2Dto> findSelect2DtoUserOwnsByAccountIdOrId(Long accountId, Long id) {
		List<Select2Dto> lstSelect2Dto = new ArrayList<>();
		if (null != id) {
			JcaOrganization orgInfo = orgInfoService.findOrgInfoById(id);
			Select2Dto selectDto = new Select2Dto();
			selectDto.setId(orgInfo.getId().toString());
			selectDto.setName(orgInfo.getCode());
			selectDto.setText(orgInfo.getName());
			lstSelect2Dto.add(selectDto);
		}
		if (accountId != null) {
			lstSelect2Dto = accOrgRepository.findSelect2DtoUserOwnsToViewsOrgByAccountId(accountId);
		}

		return lstSelect2Dto;
	}

	@Override
	public Select2Dto getPositionByOrgId(Long accountId, Long orgId) {
		return accOrgRepository.getPositionByOrgId(accountId, orgId);
	}

	@Override
	public Select2Dto findPositionMergeByAccountId(Long accountId) {
		return accOrgRepository.findPositionMergeByAccountId(accountId);
	}

	@Override
	public PageWrapper<AccountListDto> findAccountByPositionId(int page, int pageSize, Long orgId) {
		// Init PageWrapper
		List<Integer> listPageSize = systemConfig.getListPage(pageSize);
		int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);

		PageWrapper<AccountListDto> pageWrapper = new PageWrapper<AccountListDto>(page, sizeOfPage);
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);

		int count = accOrgRepository.countAccountByPositionId(orgId);

		List<AccountListDto> accountList = new ArrayList<AccountListDto>();
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			accountList = accOrgRepository.findAccountByPositionId(offsetSQL, sizeOfPage, orgId);
		}

		pageWrapper.setDataAndCount(accountList, count);
		return pageWrapper;
	}

	@Override
	public void deleteJcaAccountOrgByPK(Long id, Long orgId, Long positionId) {
		accOrgRepository.deleteJcaAccountOrgByPK(id, orgId, positionId);
	}
}

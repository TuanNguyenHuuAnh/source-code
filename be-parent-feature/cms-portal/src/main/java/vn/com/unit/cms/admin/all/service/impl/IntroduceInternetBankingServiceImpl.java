/*******************************************************************************
 * Class        ：IntroduceInternetBankingServiceImpl
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingDetailDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingDetailLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingEditDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingSearchDto;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBanking;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingDetail;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBankingLanguage;
import vn.com.unit.cms.admin.all.enumdef.IntroduceInternetBankingTypeEnum;
import vn.com.unit.cms.admin.all.repository.IntroduceInternetBankingRepository;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingDetailService;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingLanguageService;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingService;
import vn.com.unit.cms.core.utils.CmsUtils;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * IntroduceInternetBankingServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IntroduceInternetBankingServiceImpl implements IntroduceInternetBankingService {

	@Autowired
	private LanguageService languageService;

	@Autowired
	private IntroduceInternetBankingRepository introduceInternetBankingRepository;

	@Autowired
	private IntroduceInternetBankingLanguageService introduceInternetBankingLanguageService;

	@Autowired
	private IntroduceInternetBankingDetailService introduceInternetBankingDetailService;

	@Autowired
	private SystemConfig systemConfig;

	/**
	 * initIntroduceInternetBankingEdit
	 *
	 * @param mav
	 * @param string
	 * @author hoangnp
	 */
	@Override
	public void initIntroduceInternetBankingEdit(ModelAndView mav, String string) {
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		mav.addObject("languageList", languageList);

		// introduceTypeList
		List<SelectItem> introduceTypeList = new ArrayList<SelectItem>();
		for (IntroduceInternetBankingTypeEnum en : IntroduceInternetBankingTypeEnum.values()) {
			introduceTypeList.add(new SelectItem(en.getName(), en.toString()));
		}
		mav.addObject("introduceTypeList", introduceTypeList);
	}

	/**
	 * getIntroduceInternetBanking
	 *
	 * @param introduceInternetBankingId
	 * @param string
	 * @return IntroduceInternetBankingEditDto
	 * @author hoangnp
	 */
	@Override
	public IntroduceInternetBankingEditDto getIntroduceInternetBanking(Long introduceInternetBankingId, String string) {

		IntroduceInternetBankingEditDto resultDto = new IntroduceInternetBankingEditDto();

		// languageList
		List<Language> languageList = languageService.findAllActive();

		if (introduceInternetBankingId == null) {
			List<IntroduceInternetBankingDetailLanguageDto> introduceInternetBankingDetailLaguageList = initIntroduceInternetBankingDetaiLanguageList(
					languageList);
			resultDto.setIntroduceInternetBankingDetailLanguageList(introduceInternetBankingDetailLaguageList);
			resultDto.setEnabled(Boolean.TRUE);
			
			return resultDto;
		}

		// set Introduce Internet Banking
		IntroduceInternetBanking entity = introduceInternetBankingRepository.findOne(introduceInternetBankingId);
		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setName(entity.getName());
			resultDto.setEnabled(entity.isEnabled());
			resultDto.setIntroductionType(entity.getIntroductionType());

			for (IntroduceInternetBankingTypeEnum en : IntroduceInternetBankingTypeEnum.values()) {
				if (StringUtils.equals(entity.getIntroductionType(), en.getName())) {
					resultDto.setIntroductionTypeName(en.getValue());
					break;
				}
			}

		}
		// set IntroduceInternetBankingLanguage
		List<IntroduceInternetBankingLanguageDto> introduceInternetBankingLanguageList = getIntroduceInternetBankingLanguageList(
				introduceInternetBankingId, languageList);
		resultDto.setIntroduceInternetBankingLanguageList(introduceInternetBankingLanguageList);

		// set IntroduceInternetBankingDetailLanguage
		List<IntroduceInternetBankingDetailLanguageDto> introduceInternetBankingDetaiLanguageList = getIntroduceInternetBankingDetaiLanguageList(
				introduceInternetBankingId, languageList);
		resultDto.setIntroduceInternetBankingDetailLanguageList(introduceInternetBankingDetaiLanguageList);

		return resultDto;
	}

	/**
	 * getIntroduceInternetBankingDetaiLanguageList
	 *
	 * @param introduceInternetBankingId
	 * @param languageList
	 * @return List<IntroduceInternetBankingDetailLanguageDto>
	 * @author hoangnp
	 */
	private List<IntroduceInternetBankingDetailLanguageDto> getIntroduceInternetBankingDetaiLanguageList(
			Long introduceInternetBankingId, List<Language> languageList) {

		List<IntroduceInternetBankingDetail> introduceInternetBankingDetailList = introduceInternetBankingDetailService
				.findIntroduceInternetBankingDetailById(introduceInternetBankingId);
		if (introduceInternetBankingDetailList.size() == 0) {
			return initIntroduceInternetBankingDetaiLanguageList(languageList);
		}

		List<IntroduceInternetBankingDetailLanguageDto> resultList = new ArrayList<IntroduceInternetBankingDetailLanguageDto>();
		IntroduceInternetBankingDetailLanguageDto detailLanguageDto = null;

		List<IntroduceInternetBankingDetailDto> detailLanguageList = null;
		IntroduceInternetBankingDetailDto detailDto = null;

		// loop language
		for (Language language : languageList) {

			detailLanguageList = new ArrayList<IntroduceInternetBankingDetailDto>();
			detailLanguageDto = new IntroduceInternetBankingDetailLanguageDto();
			detailLanguageDto.setLanguageCode(language.getCode());

			for (IntroduceInternetBankingDetail introduceInternetBankingDetail : introduceInternetBankingDetailList) {
				if (StringUtils.equals(introduceInternetBankingDetail.getLanguageCode(), language.getCode())) {
					detailDto = new IntroduceInternetBankingDetailDto();
					detailDto.setId(introduceInternetBankingDetail.getId());
					detailDto.setIntroduceInternetBankingId(
							introduceInternetBankingDetail.getIntroduceInternetBankingId());
					detailDto.setLanguageCode(introduceInternetBankingDetail.getLanguageCode());
					detailDto.setTitle(introduceInternetBankingDetail.getTitle());
					detailDto.setContent(introduceInternetBankingDetail.getContent());
					detailDto.setGroupContent(introduceInternetBankingDetail.getGroupContent());

					detailLanguageList.add(detailDto);
				}
			}

			detailLanguageDto.setIntroduceInternetBankingDetailList(detailLanguageList);

			resultList.add(detailLanguageDto);
		}

		return resultList;

	}

	/**
	 * getIntroduceInternetBankingLanguageList
	 *
	 * @param introduceInternetBankingId
	 * @param languageList
	 * @return List<IntroduceInternetBankingLanguageDto>
	 * @author hoangnp
	 */
	private List<IntroduceInternetBankingLanguageDto> getIntroduceInternetBankingLanguageList(
			Long introduceInternetBankingId, List<Language> languageList) {

		List<IntroduceInternetBankingLanguageDto> resultList = new ArrayList<IntroduceInternetBankingLanguageDto>();
		// ProductLanguageList
		List<IntroduceInternetBankingLanguage> introduceInternetLanguageList = introduceInternetBankingLanguageService
				.findByIntroduceInternetBankingId(introduceInternetBankingId);

		// loop language
		for (Language language : languageList) {
			// loop introduceInternetLanguageList
			for (IntroduceInternetBankingLanguage entity : introduceInternetLanguageList) {

				if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
					IntroduceInternetBankingLanguageDto introduceInternetLanguageDto = new IntroduceInternetBankingLanguageDto();
					introduceInternetLanguageDto.setId(entity.getId());
					introduceInternetLanguageDto.setLanguageCode(entity.getLanguageCode());
					introduceInternetLanguageDto.setTitle(entity.getTitle());
					introduceInternetLanguageDto.setDescription(entity.getDescription());

					resultList.add(introduceInternetLanguageDto);
					break;
				}
			}
		}
		return resultList;
	}

	/**
	 * initIntroduceInternetBankingDetaiLanguageList
	 *
	 * @param languageList
	 * @return List<IntroduceInternetBankingDetailLanguageDto>
	 * @author hoangnp
	 */
	private List<IntroduceInternetBankingDetailLanguageDto> initIntroduceInternetBankingDetaiLanguageList(
			List<Language> languageList) {
		List<IntroduceInternetBankingDetailLanguageDto> resultList = new ArrayList<IntroduceInternetBankingDetailLanguageDto>();
		IntroduceInternetBankingDetailLanguageDto detaiLanguageDto = null;

		List<IntroduceInternetBankingDetailDto> introduceInternetBankingDetailList = null;

		IntroduceInternetBankingDetailDto detailDto = null;

		// loop language
		for (Language language : languageList) {
			detailDto = new IntroduceInternetBankingDetailDto();
			detailDto.setLanguageCode(language.getCode());
			detailDto.setGroupContent(ConstantCore.STR_ZERO);
			introduceInternetBankingDetailList = new ArrayList<IntroduceInternetBankingDetailDto>();
			introduceInternetBankingDetailList.add(detailDto);

			detaiLanguageDto = new IntroduceInternetBankingDetailLanguageDto();
			detaiLanguageDto.setLanguageCode(language.getCode());
			detaiLanguageDto.setIntroduceInternetBankingDetailList(introduceInternetBankingDetailList);
			resultList.add(detaiLanguageDto);
		}

		return resultList;
	}

	/**
	 * addOrEdit
	 *
	 * @param introduceInternetBankingEdit
	 * @author hoangnp
	 */
	@Override
	@Transactional
	public void addOrEdit(IntroduceInternetBankingEditDto introduceInternetBankingEdit) {
		Long introduceInternetBankingId = introduceInternetBankingEdit.getId();

		// user login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();

		createOrEditIntroduceInternetBanking(introduceInternetBankingEdit, UserProfileUtils.getUserNameLogin());

		createOrEditIntroduceInternetBankingLanguage(introduceInternetBankingEdit, UserProfileUtils.getUserNameLogin());

		createOrEditIntroduceInternetBankingDetail(introduceInternetBankingEdit, UserProfileUtils.getUserNameLogin(),
				introduceInternetBankingId);

	}

	/**
	 * createOrEditIntroduceInternetBankingDetail
	 *
	 * @param introduceInternetBankingEdit
	 * @param username
	 * @param introduceInternetBankingId
	 * @author hoangnp
	 */
	private void createOrEditIntroduceInternetBankingDetail(
			IntroduceInternetBankingEditDto introduceInternetBankingEdit, String username,
			Long introduceInternetBankingId) {
		if (null != introduceInternetBankingId) {
			introduceInternetBankingDetailService.deleteByIntroduceInternetBankingId(introduceInternetBankingId);
		}

		for (IntroduceInternetBankingDetailLanguageDto languageDto : introduceInternetBankingEdit
				.getIntroduceInternetBankingDetailLanguageList()) {

			for (IntroduceInternetBankingDetailDto introduceInternetBankingDetail : languageDto
					.getIntroduceInternetBankingDetailList()) {

				// m_introduce_internet_banking_detail entity
				IntroduceInternetBankingDetail entity = new IntroduceInternetBankingDetail();

				entity.setId(introduceInternetBankingDetail.getId());
				entity.setIntroduceInternetBankingId(introduceInternetBankingEdit.getId());
				entity.setLanguageCode(introduceInternetBankingDetail.getLanguageCode());
				entity.setTitle(introduceInternetBankingDetail.getTitle());
				entity.setContent(introduceInternetBankingDetail.getContent());
				entity.setGroupContent(introduceInternetBankingDetail.getGroupContent());

				introduceInternetBankingDetailService.saveDetail(entity);
			}
		}

		CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.INTRODUCE_INTERNET_BANKING_FOLDER,
				AdminConstant.EDITOR_FOLDER, introduceInternetBankingEdit.getRequestToken()).toString());

	}

	/**
	 * createOrEditIntroduceInternetBankingLanguage
	 *
	 * @param introduceInternetBankingEdit
	 * @param userName
	 * @author hoangnp
	 */
	private void createOrEditIntroduceInternetBankingLanguage(
			IntroduceInternetBankingEditDto introduceInternetBankingEdit, String userName) {
		for (IntroduceInternetBankingLanguageDto languageDto : introduceInternetBankingEdit
				.getIntroduceInternetBankingLanguageList()) {

			// m_introduce_internet_banking_language
			IntroduceInternetBankingLanguage entity = new IntroduceInternetBankingLanguage();

			if (null != languageDto.getId()) {
				entity = introduceInternetBankingLanguageService.findById(languageDto.getId());
				if (null == entity) {
					throw new BusinessException(
							"Not found IntroduceInternetBankingLanguag with id=" + languageDto.getId());
				}

				entity.setUpdateDate(new Date());
				entity.setUpdateBy(userName);
			} else {
				entity.setCreateDate(new Date());
				entity.setCreateBy(userName);
			}

			entity.setIntroduceInternetBankingId(introduceInternetBankingEdit.getId());
			entity.setLanguageCode(languageDto.getLanguageCode());
			entity.setTitle(languageDto.getTitle());
			entity.setDescription(languageDto.getDescription());

			introduceInternetBankingLanguageService.saveIntroduceInternetBankingLanguage(entity);
		}

	}

	/**
	 * createOrEditIntroduceInternetBanking
	 *
	 * @param introduceInternetBankingEdit
	 * @param userProfile
	 * @author hoangnp
	 */
	private void createOrEditIntroduceInternetBanking(IntroduceInternetBankingEditDto introduceInternetBankingEdit,
			String usernameLogin) {

		// m_introduce_internet_banking
		IntroduceInternetBanking entity = new IntroduceInternetBanking();

		// introduce Internet Banking exists Id
		if (null != introduceInternetBankingEdit.getId()) {
			entity = introduceInternetBankingRepository.findOne(introduceInternetBankingEdit.getId());
			if (null == entity) {
				throw new BusinessException(
						"Not found Introduce Internet Banking with id=" + introduceInternetBankingEdit.getId());
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(usernameLogin);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(usernameLogin);
		}

		entity.setCode(introduceInternetBankingEdit.getCode().toUpperCase());
		entity.setName(introduceInternetBankingEdit.getName());
		entity.setEnabled(introduceInternetBankingEdit.isEnabled());
		entity.setIntroductionType(introduceInternetBankingEdit.getIntroductionType());

		introduceInternetBankingRepository.save(entity);

		introduceInternetBankingEdit.setId(entity.getId());
	}

	/**
	 * findByCode
	 *
	 * @param code
	 * @return IntroduceInternetBanking
	 * @author hoangnp
	 */
	@Override
	public IntroduceInternetBanking findByCode(String code) {
		return introduceInternetBankingRepository.findByCode(code);
	}

	/**
	 * search
	 *
	 * @param page
	 * @param introduceInternetBankingSearchDto
	 * @return PageWrapper<IntroduceInternetBankingLanguageSearchDto>
	 * @author hoangnp
	 */
	@Override
    public PageWrapper<IntroduceInternetBankingLanguageSearchDto> search(int page, IntroduceInternetBankingSearchDto searchDto) {
	    if (null == searchDto) {
	        searchDto = new IntroduceInternetBankingSearchDto();
	    }
        int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        int count = introduceInternetBankingRepository.countByIntroduceInternetBankingSearchDto(searchDto);
        if (page > count / sizeOfPage + 1) {
            page = 1;
        }

        PageWrapper<IntroduceInternetBankingLanguageSearchDto> pageWrapper = new PageWrapper<IntroduceInternetBankingLanguageSearchDto>(
                page, sizeOfPage);
        List<IntroduceInternetBankingLanguageSearchDto> result = new ArrayList<IntroduceInternetBankingLanguageSearchDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = introduceInternetBankingRepository.findByIntroduceInternetBankingSearchDto(offsetSQL, sizeOfPage, searchDto);
        }
        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;

    }

	/**
	 * setSearchParm
	 *
	 * @param condition
	 * @author hoangnp
	 */
//	private void setSearchParm(IntroduceInternetBankingSearchDto condition) {
//
//		if (null == condition.getFieldValues()) {
//			condition.setFieldValues(new ArrayList<String>());
//		}
//
//		if (condition.getFieldValues().isEmpty()) {
//			condition.setName(condition.getFieldSearch());
//			condition.setCode(condition.getFieldSearch());
//			// condition.setTitleDetail(condition.getFieldSearch());
//			condition.setTitle(condition.getFieldSearch());
//			condition.setDescription(condition.getFieldSearch());
//
//		} else {
//			for (String field : condition.getFieldValues()) {
//				if (StringUtils.equals(field, IntroduceInternetBankingSearchEnum.CODE.name())) {
//					condition.setCode(condition.getFieldSearch());
//					continue;
//				}
//				if (StringUtils.equals(field, IntroduceInternetBankingSearchEnum.NAME.name())) {
//					condition.setName(condition.getFieldSearch());
//					continue;
//				}
//				if (StringUtils.equals(field, IntroduceInternetBankingSearchEnum.TITLE.name())) {
//					condition.setTitle(condition.getFieldSearch());
//					continue;
//				}
//
//				if (StringUtils.equals(field, IntroduceInternetBankingSearchEnum.DESCRIPTION.name())) {
//					condition.setDescription(condition.getFieldSearch());
//					continue;
//				}
//			}
//
//		}
//
//	}

	/**
	 * deleteIntroduce
	 *
	 * @param id
	 * @author hoangnp
	 */
	@Override
	@Transactional
	public void deleteIntroduce(Long id) {
		// check exits id
		IntroduceInternetBanking introduceInternetBanking = introduceInternetBankingRepository.findOne(id);
		if (null == introduceInternetBanking) {
			throw new BusinessException("Not found introduce with id=" + id);
		}
		// user name login
		String userName = UserProfileUtils.getUserNameLogin();

		// delete introduceLanguage
		introduceInternetBankingLanguageService.deleteByTypeId(id, userName);
		// delete introduceLanguage
		introduceInternetBankingDetailService.deleteByTypeId(id, userName);
		// delete introduce
		introduceInternetBanking.setDeleteDate(new Date());
		introduceInternetBanking.setDeleteBy(userName);
		introduceInternetBankingRepository.save(introduceInternetBanking);
	}

}

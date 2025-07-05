/*******************************************************************************
 * Class        ：ConstantServiceImpl
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.dto.ConstantDto;
import vn.com.unit.cms.admin.all.dto.ConstantLanguageDto;
import vn.com.unit.cms.admin.all.entity.Constant;
import vn.com.unit.cms.admin.all.entity.ConstantLanguage;
import vn.com.unit.cms.admin.all.repository.ConstantLanguageRepository;
import vn.com.unit.cms.admin.all.repository.ConstantRepository;
import vn.com.unit.cms.admin.all.service.ConstantService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
//import vn.com.unit.jcanary.enumdef.ConstDispType;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;

/**
 * ConstantServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ConstantServiceImpl implements ConstantService {
	@Autowired
	private ConstantRepository constantRepository;

	@Autowired
	private ConstantLanguageRepository constantLanguageRepository;

//    @Autowired
//    private ConstantDisplayService constDispService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private JcaConstantService jcaConstantService;

	@Override
	public void initScreenConstantList(ModelAndView mav) {
		// Init career
		try {

			// ${constantDisplay.cat} => ${constantDisplay.kind}
			// #{${constantDisplay.code}} => #{${constantDisplay.code}}
			// constDispService.findByType("M10");
			// => List<JcaConstantDto> statusList =
			// jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
			// "EN");

			// type => groupCode
			// cat => kind
			// code => code

			// catOfficialName => name

			// ConstantDisplay motive =
			// constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
			// emailModel.getMotive().toString());
			// JcaConstantDto motive =
			// jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
			// emailModel.getMotive().toString(), "EN").get(0);

			// List<ConstantDisplay> listBannerPage =
			// constDispService.findByType(ConstDispType.B01);
			// List<JcaConstantDto> listBannerPage =
			// jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
			// "EN");

//            List<ConstantDisplay> typeJob = constDispService.findByType(ConstDispType.JOB);

			List<JcaConstantDto> typeJob = jcaConstantService
					.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.JOB.toString(), "EN");

			mav.addObject("typeJob", typeJob);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// Init language
		try {
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			mav.addObject("languageList", languageList);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
	}

	@Override
	@Transactional
	public void addOrEditConstant(ConstantDto constantDto) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();

		Long constantId = constantDto.getId();
		// Update data m_constant table
		Constant updateConstant = new Constant();
		if (null != constantId) {
			updateConstant = constantRepository.findOne(constantId);
			if (null == updateConstant) {
				throw new BusinessException("Not found Constant by id= " + constantId);
			}
			updateConstant.setUpdateBy(userNameLogin);
			updateConstant.setUpdateDate(new Date());
		} else {
			constantId = Long.parseLong((constantRepository.findMaxIdConstant() + 1) + "");
			updateConstant.setCreateBy(userNameLogin);
			updateConstant.setCreateDate(new Date());
		}
		updateConstant.setCode("JOBTYPE" + constantId);
		updateConstant.setType(constantDto.getType());
		try {
			constantRepository.save(updateConstant);
			constantDto.setId(updateConstant.getId());
			constantDto.setCode(updateConstant.getCode());
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// Update constant language
		addOrEditConstantLanguage(constantDto, userNameLogin);

	}

	/**
	 * addOrEditConstantLanguage
	 *
	 * @param constantDto
	 * @param userNameLogin
	 * @author TranLTH
	 */
	@Transactional
	public void addOrEditConstantLanguage(ConstantDto constantDto, String userNameLogin) {
		// update data m_constant_language table
		List<ConstantLanguageDto> listConstantLanguageDto = constantDto.getConstantLanguageDtos();

		for (ConstantLanguageDto constantLanguageDto : listConstantLanguageDto) {
			ConstantLanguage constantLanguage = new ConstantLanguage();
			if (null != constantLanguageDto.getId()) {
				constantLanguage = constantLanguageRepository.findOne(constantLanguageDto.getId());
				if (null == constantLanguage) {
					throw new BusinessException("Not found Constant Language with id=" + constantLanguageDto.getId());
				}
				constantLanguage.setUpdateDate(new Date());
				constantLanguage.setUpdateBy(userNameLogin);
			} else {
				constantLanguage.setCreateDate(new Date());
				constantLanguage.setCreateBy(userNameLogin);
			}

			constantLanguage.setLanguageCode(constantLanguageDto.getLanguageCode());
			constantLanguage.setConstantCode(constantDto.getCode());
			constantLanguage.setName(constantLanguageDto.getName());
			try {
				constantLanguageRepository.save(constantLanguage);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	@Transactional
	public void delete(Long constantId) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();
		Constant constant = new Constant();
		List<ConstantLanguage> constantLanguages = new ArrayList<ConstantLanguage>();
		if (null != constantId) {
			constant = constantRepository.findOne(constantId);
			constantLanguages = constantRepository.findConstantIdLanguage(constant.getCode());
		}
		// delete constant
		constant.setDeleteDate(new Date());
		constant.setDeleteBy(userNameLogin);

		try {
			constantRepository.save(constant);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		// delete constant language
		if (null != constantLanguages) {
			for (ConstantLanguage constantLanguage : constantLanguages) {
				constantLanguage.setDeleteDate(new Date());
				constantLanguage.setDeleteBy(userNameLogin);
				try {
					constantLanguageRepository.save(constantLanguage);
				} catch (Exception ex) {
					throw new SystemException(ex);
				}
			}
		}
	}

	@Override
	public PageWrapper<ConstantDto> search(int page, ConstantDto constantDto) {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<ConstantDto> pageWrapper = new PageWrapper<ConstantDto>(page, sizeOfPage);
		if (null == constantDto)
			constantDto = new ConstantDto();

		int count = constantRepository.countConstantByCondition(constantDto);

		List<ConstantDto> result = new ArrayList<ConstantDto>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;

			result = constantRepository.findConstantLimitByCondition(startIndex, sizeOfPage, constantDto);
		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public List<ConstantDto> findByType(String type, String languageCode) {
		return constantRepository.findByType(type, languageCode);
	}

	@Override
	public ConstantDto getConstantDto(Long constantId) {

		Constant constant = new Constant();
		ConstantDto constantDto = new ConstantDto();

		if (null == constantId) {
			return constantDto;
		}
		constant.setId(constantId);
		constant = constantRepository.findOne(constantId);

		constantDto.setCode(constant.getCode());
		constantDto.setId(constant.getId());
		constantDto.setType(constant.getType());
		constantDto.setCheckUpdateDelete(constantRepository.checkUpdateDelete(constant.getCode()));

		/** Get information m_constant_language */
		List<ConstantLanguageDto> constantListLanguageDtos = new ArrayList<ConstantLanguageDto>();
		List<ConstantLanguageDto> constantLanguageDtos = getInforConstantLanguage(constant.getCode());
		/** Get language */
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		/** Map language with constant language */
		for (LanguageDto languageDto : languageList) {
			for (ConstantLanguageDto constantLanguageDto : constantLanguageDtos) {
				ConstantLanguageDto resultConstantLanguage = new ConstantLanguageDto();
				if (languageDto.getCode().equals(constantLanguageDto.getLanguageCode())) {
					resultConstantLanguage.setConstantCode(constantLanguageDto.getConstantCode());
					resultConstantLanguage.setId(constantLanguageDto.getId());
					resultConstantLanguage.setLanguageCode(constantLanguageDto.getLanguageCode());
					resultConstantLanguage.setName(constantLanguageDto.getName());
					constantListLanguageDtos.add(resultConstantLanguage);
				}
			}
		}
		constantDto.setConstantLanguageDtos(constantLanguageDtos);
		return constantDto;
	}

	/**
	 * getInforConstantLanguage
	 *
	 * @param constantCode
	 * @return
	 * @author TranLTH
	 */
	public List<ConstantLanguageDto> getInforConstantLanguage(String constantCode) {
		List<ConstantLanguageDto> constantLanguageDtos = new ArrayList<ConstantLanguageDto>();
		List<ConstantLanguage> constantLanguages = constantRepository.findConstantIdLanguage(constantCode);
		for (ConstantLanguage constantLanguage : constantLanguages) {
			ConstantLanguageDto constantLanguageDto = new ConstantLanguageDto();
			constantLanguageDto.setId(constantLanguage.getId());
			constantLanguageDto.setConstantCode(constantLanguage.getConstantCode());
			constantLanguageDto.setLanguageCode(constantLanguage.getLanguageCode());
			constantLanguageDto.setName(constantLanguage.getName());

			constantLanguageDtos.add(constantLanguageDto);
		}
		return constantLanguageDtos;
	}
}
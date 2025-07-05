package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.EmulateLanguageRepository;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.EmulateService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateLanguageDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchResultDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestApplicableDetail;
import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.cms.core.module.emulate.entity.Emulate;
import vn.com.unit.cms.core.module.emulate.entity.EmulateLanguage;
import vn.com.unit.cms.core.module.emulate.repository.ContestApplicableDetailRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestRepository;
import vn.com.unit.cms.core.module.emulate.repository.ContestSummaryRepository;
import vn.com.unit.cms.core.module.emulate.repository.EmulateRepository;
import vn.com.unit.cms.core.utils.CmsDateUtils;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmStatusDeployService;

@Service
public class EmulateServiceImpl extends DocumentWorkflowCommonServiceImpl<EmulateEditDto, EmulateEditDto>
		implements EmulateService {

	@Autowired
	private EmulateRepository emulateRepository;

	@Autowired
	private EmulateLanguageRepository emulateLanguageRepository;

	@Autowired
	private ContestSummaryRepository contestSummaryRepository;

	@Autowired
	private ContestRepository contestDetailRepository;

	@Autowired
	private ContestApplicableDetailRepository contestApplicableDetailRepository;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Autowired
	private SystemConfig appSystemConfig;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private JpmStatusDeployService jpmStatusDeployService;

	public EmulateEditDto getData(Long id, Locale locale) {
		EmulateEditDto resultDto = new EmulateEditDto();

		if (id == null) {
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			resultDto.setEnabled(true);
			resultDto.setCustomerTypeId(9L);
			return resultDto;
		}

		// set FaqsCategory
		Emulate entity = emulateRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (entity == null || entity.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setEnabled(entity.isEnabled());
			resultDto.setSort(entity.getSort());
			resultDto.setCustomerTypeId(entity.getCustomerTypeId());
			resultDto.setCreateBy(entity.getCreateBy());
			resultDto.setUpdateDate(entity.getUpdateDate());
			resultDto.setDocId(entity.getDocId());

			resultDto.setStartDate(entity.getStartDate());
			resultDto.setEndDate(entity.getEndDate());
			resultDto.setPostedDate(entity.getPostedDate());
			resultDto.setExpirationDate(entity.getExpirationDate());

			resultDto.setMemoCode(entity.getMemoCode());
			resultDto.setHot(entity.isHot());
			resultDto.setLinkDms(entity.isLinkDms());
			resultDto.setSubjectsApplicable(entity.getSubjectsApplicable());
			resultDto.setTerritory(entity.getTerritory());
			resultDto.setArea(entity.getArea());
			resultDto.setRegion(entity.getRegion());
			resultDto.setAgentType(entity.getAgentType());
			resultDto.setOffice(entity.getOffice());

			JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(resultDto.getDocId(), locale.toString());
			if (status != null) {
				resultDto.setStatusName(status.getStatusName());
			}
		}

		List<EmulateLanguageDto> listLanguage = getLanguageList(id);
		resultDto.setEmulateLanguageDto(listLanguage);

		return resultDto;
	}

	private List<EmulateLanguageDto> getLanguageList(Long emulateId) {
		List<EmulateLanguageDto> listLang = emulateLanguageRepository.findListLanguage(emulateId, null);

		for (EmulateLanguageDto dto : listLang) {
			dto.setContent(CmsUtils.converByteToStringUTF8(dto.getContentByte()));
		}
		return listLang;
	}

	@Override
	public EmulateEditDto getEdit(Long id, String customerAlias, Locale locale) {
		return getData(id, locale);
	}

	@Override
	public List<EmulateSearchResultDto> getListByCondition(EmulateSearchDto searchDto, Pageable pageable) {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
			searchDto.setUsername(UserProfileUtils.getUserNameLogin());
		}
		return emulateRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(EmulateSearchDto searchDto) {
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
//            searchDto.setUsername(UserProfileUtils.getUserNameLogin());
//        }
		return emulateRepository.countList(searchDto);
	}

	@Override
	public SystemConfig getSystemConfig() {
		return appSystemConfig;
	}

	@Override
	public CmsCommonService getCmsCommonService() {
		return cmsCommonService;
	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;
	}
	
	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(EmulateSearchDto searchDto, Locale locale) {

		List<CommonSearchFilterDto> list = EmulateService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto>  rs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
				rs.add(filter);
			}
		}
		return rs;
	}

	@Override
	public EmulateEditDto getEditDtoById(Long id, Locale locale) {
		return getData(id, locale);
	}

	@Override
	public void saveOrUpdate(EmulateEditDto editDto, Locale locale) throws Exception {
		createOrEditData(editDto, locale);
		createOrEditLanguage(editDto);

		if (editDto.getRequestToken() != null) {
			CmsUtils.moveTempSubFolderToUpload(
					Paths.get(AdminConstant.EMULATE_FOLDER, AdminConstant.EDITOR_FOLDER, editDto.getRequestToken())
							.toString());
		} else {
			CmsUtils.moveTempSubFolderToUpload(
					Paths.get(AdminConstant.EMULATE_FOLDER, AdminConstant.EDITOR_FOLDER).toString());
		}
	}

	private void createOrEditData(EmulateEditDto editDto, Locale locale) throws IOException {

		String usernameAction = UserProfileUtils.getUserNameLogin();
		Emulate entity = new Emulate();

		// news exists id
		if (null != editDto.getId()) {
			entity = emulateRepository.findOne(editDto.getId());

			// dữ liệu ko tồn tại hoặc đã bị xóa
			if (entity == null || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			if (entity.getUpdateDate() != null
					&& !formatDate.format(entity.getUpdateDate()).equals(formatDate.format(editDto.getUpdateDate()))) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(usernameAction);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(usernameAction);
		}

		try {
			entity.setCode(editDto.getCode());
			entity.setMemoCode(editDto.getMemoCode());
			entity.setEnabled(editDto.isEnabled());

			entity.setCustomerTypeId(editDto.getCustomerTypeId());
			entity.setExpirationDate(editDto.getExpirationDate());
			entity.setPostedDate(editDto.getPostedDate());
			entity.setHot(editDto.isHot());

			entity.setStartDate(editDto.getStartDate());
			entity.setEndDate(editDto.getEndDate());

			entity.setDocId(editDto.getDocId());

			long subDate = CmsDateUtils.dateSubDate(editDto.getEndDate(), editDto.getStartDate());

			/**
			 * Ngày kết thúc – Ngày bắt đầu > 6 tháng: Thi đua năm Ngược lại: Thi đua tháng
			 */
			if (subDate > 6) {
				entity.setType(1);
			}

			if (StringUtils.isBlank(entity.getCode())) {
				entity.setCode(CommonUtil.getNextCode(CmsPrefixCodeConstant.PREFIX_CODE_EMULATE,
						cmsCommonService.getMaxCode("M_EMULATE", CmsPrefixCodeConstant.PREFIX_CODE_EMULATE)));
			}

			entity.setLinkDms(editDto.isLinkDms());
			entity.setSubjectsApplicable(editDto.getSubjectsApplicable());
			entity.setFc(editDto.isFc());
			entity.setTerritory(editDto.getTerritory());
			entity.setArea(editDto.getArea());
			entity.setRegion(editDto.getRegion());
			entity.setAgentType(editDto.getAgentType());
			entity.setOffice(editDto.getOffice());

			// set note = null
			emulateRepository.save(entity);
		} catch (Exception e) {
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
		editDto.setId(entity.getId());
		editDto.setCode(entity.getCode());
	}

	private boolean createOrEditLanguage(EmulateEditDto editDto) throws IOException {
		try {
			String usernameAction = UserProfileUtils.getUserNameLogin();

			for (EmulateLanguageDto cLanguageDto : editDto.getEmulateLanguageDto()) {
				// m_news_language entity
				EmulateLanguage entity = new EmulateLanguage();

				if (null != cLanguageDto.getId()) {
					entity = emulateLanguageRepository.findOne(cLanguageDto.getId());
					if (null == entity) {
						throw new BusinessException("Not found NewsLanguag with id=" + cLanguageDto.getId());
					}
					entity.setUpdateDate(new Date());
					entity.setUpdateBy(usernameAction);
				} else {
					entity.setCreateDate(new Date());
					entity.setCreateBy(usernameAction);
				}

				entity.setMEmulateId(editDto.getId());
				entity.setLanguageCode(cLanguageDto.getLanguageCode());
				entity.setTitle(cLanguageDto.getTitle());
				entity.setShortContent(cLanguageDto.getShortContent());
				entity.setContent(CmsUtils.convertStringToByteUTF8(cLanguageDto.getContent()));
				entity.setLinkAlias(cLanguageDto.getLinkAlias());
				entity.setKeyWord(cLanguageDto.getKeyword());
				entity.setDescriptionKeyword(cLanguageDto.getKeywordDescription());
				entity.setImgUrl(cLanguageDto.getImgUrl());

				// physical file template
				String physicalImgUrlTmp = cLanguageDto.getPhysicalImgUrl();
				// upload images
				if (StringUtils.isNotEmpty(physicalImgUrlTmp)) {
					String newPhiscalNameUrl = CmsUtils.moveTempToUploadFolder(physicalImgUrlTmp,
							AdminConstant.EMULATE_FOLDER);
					entity.setPhysicalImgUrl(newPhiscalNameUrl);
				} else {
					entity.setPhysicalImgUrl(null);
				}

				emulateLanguageRepository.save(entity);
			}

			if (editDto.getRequestToken() != null) {
				CmsUtils.moveTempSubFolderToUpload(
						Paths.get(AdminConstant.EMULATE_EDITOR_FOLDER, editDto.getRequestToken()).toString());
			} else {
				CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.EMULATE_EDITOR_FOLDER).toString());
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		ContestSummary contest = findBannerById(id);
		deleteContest(contest);

		String userName = UserProfileUtils.getUserNameLogin();
		List<ContestDto> detail = findDeatilByMemo(contest.getMemoNo());
		if(detail != null) {
			for(ContestDto ls: detail) {
				ls.setDeletedDate(new Date());
				ls.setDeletedBy(userName);
				contestDetailRepository.save(detail);
				
				ContestApplicableDetail applicaDetail = findApplicaDetailById(ls.getId());
				if (applicaDetail != null) {
					applicaDetail.setDeleteDate(new Date());
					applicaDetail.setDeleteBy(userName);
					contestApplicableDetailRepository.save(applicaDetail);
				}
			}
		}
		}

	@Override
	public void deleteContest(ContestSummary contest) {
		// user name login
		String userName = UserProfileUtils.getUserNameLogin();
		contest.setDeletedDate(new Date());
		contest.setDeletedBy(userName);
		contestSummaryRepository.save(contest);
	}

	public ContestSummary findBannerById(Long id) {
		return contestSummaryRepository.findOne(id);
	}

	public List<ContestDto> findDeatilByMemo(String memo) {
		return contestDetailRepository.findByMemo(memo);
	}

	public ContestApplicableDetail findApplicaDetailById(Long id) {
		return contestApplicableDetailRepository.findAplicaById(id);
	}

	@Override
	public List<EmulateSearchResultDto> getListForSort(EmulateSearchDto searchDto) {
		return emulateRepository.findListSorting(searchDto);
	}

	@Override
	public void updateSortAll(EmulateSearchDto searchDto) {
		for (SortOrderDto dto : searchDto.getSortOderList()) {
			emulateRepository.updateSortAll(dto);
		}
	}

	@Override
	public DocumentActionReq actionBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto, Locale locale)
			throws Exception {
		EmulateEditDto editDto = (EmulateEditDto) documentActionReq;

		saveOrUpdate(editDto, locale);

		editDto.setDataId(editDto.getId());

		return editDto;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getEnumColumnForExport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}


	@Override
	public List<EmulateSearchDto> getType() {
		return emulateRepository.findByContestType();
	}
}

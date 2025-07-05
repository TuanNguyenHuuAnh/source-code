/*******************************************************************************
 * Class        ：ProductConsultingInforServiceImpl
 * Created date ：2017/09/01
 * Lasted date  ：2017/09/01
 * Author       ：hand
 * Change log   ：2017/09/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.dto.ExportProductConsultReportDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingCommentDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingInforSearchDto;
import vn.com.unit.cms.admin.all.dto.ProductConsultingUpdateItemDto;
import vn.com.unit.cms.admin.all.entity.ProductConsultingInfor;
import vn.com.unit.cms.admin.all.entity.ProductConsultingUpdateItem;
import vn.com.unit.cms.admin.all.enumdef.ExportProductConsultExportEnum;
import vn.com.unit.cms.admin.all.enumdef.GenderEnum;
import vn.com.unit.cms.admin.all.enumdef.ProductConsultingCommentEnum;
import vn.com.unit.cms.admin.all.enumdef.ProductConsultingStatusEnum;
import vn.com.unit.cms.admin.all.repository.ProductConsultingInforRepository;
import vn.com.unit.cms.admin.all.repository.ProductConsultingItemRepository;
import vn.com.unit.cms.admin.all.service.ProductConsultingInforService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.cms.admin.all.constant.CommonConstant;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * ProductConsultingInforServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProductConsultingInforServiceImpl implements ProductConsultingInforService {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	ProductConsultingInforRepository consultingInforRepository;

	@Autowired
	ServletContext servletContext;

	@Autowired
	ProductConsultingItemRepository productConsultingItemRepository;

	@Autowired
	MessageSource msg;

	private static final Logger logger = LoggerFactory.getLogger(ProductConsultingInforServiceImpl.class);

	/**
	 * find ProductConsultingInfor List
	 *
	 * @param conditon
	 * @param page
	 * @return PageWrapper<ProductConsultingInforSearchDto>
	 * @author hand
	 */
	@Override
	public PageWrapper<ProductConsultingInforSearchDto> findList(ProductConsultingInforSearchDto dto, int page) {
		if (null == dto)
			dto = new ProductConsultingInforSearchDto();
		int sizeOfPage = dto.getPageSize() != null ? dto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

		PageWrapper<ProductConsultingInforSearchDto> pageWrapper = new PageWrapper<ProductConsultingInforSearchDto>(
				page, sizeOfPage);

		int count = consultingInforRepository.countProductConsultingInforList(dto);
		List<ProductConsultingInforSearchDto> result = null;
		if (count > 0) {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			result = consultingInforRepository.findProductConsultingInforList(offsetSQL, sizeOfPage, dto);
		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	/**
	 * getProductConsultingInforDto
	 *
	 * @param id
	 * @param languageCode
	 * @return ProductConsultingInforSearchDto
	 * @author hand
	 */
	@Override
	public ProductConsultingInforSearchDto getProductConsultingInforDto(Long id, Locale locale) {

		ProductConsultingInforSearchDto result = consultingInforRepository.findProductConsultingInforDto(id,
				locale.toString());

		if (result == null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (!StringUtils.isEmpty(result.getGender())) {
			for (GenderEnum en : GenderEnum.values()) {
				if (StringUtils.equals(en.genderValue().toString(), result.getGender())) {
					result.setGender(en.genderName());
					break;
				}
			}
		}
		List<ProductConsultingUpdateItemDto> updateHistory = productConsultingItemRepository
				.findByProductConsultingId(id);
		result.setUpdateHistory(updateHistory);
		result.setProcessingStatus(result.getProcessingStatus());
		return result;
	}

	/**
	 * findListProductByCustomerId
	 *
	 * @param customerId
	 * @param languageCode
	 * @return productListJsonStr
	 * @author hand
	 */
	@Override
	public String findProductListJsonByCustomerId(Long customerId, String languageCode) {
		List<SelectItem> productListSelect = consultingInforRepository.findProductSelectByCustomerId(customerId,
				languageCode);
		return CommonJsonUtil.convertObjectToJsonString(productListSelect);
	}

	@Override
	public void exportExcel(ProductConsultingInforSearchDto searchDto, HttpServletResponse res, Locale locale) {
		try {
			/* change template */
			String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_PRODUCTCONSULT;
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
					+ CmsCommonConstant.TYPE_EXCEL;
			String datePattern = "dd/MM/yyyy";

			List<ExportProductConsultReportDto> lstData = consultingInforRepository.exportExcelWithCondition(searchDto);
			List<ItemColsExcelDto> cols = new ArrayList<>();
			// start fill data to workbook
			ImportExcelUtil.setListColumnExcel(ExportProductConsultExportEnum.class, cols);
			ExportExcelUtil<ExportProductConsultReportDto> exportExcel = new ExportExcelUtil<>();
			// do export
			exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportProductConsultReportDto.class, cols,
					datePattern, res, templateName);

		} catch (Exception e) {
			logger.error("Exception ", e);
		}
	}

	@Override
	public List<ProductConsultingUpdateItemDto> getUpdateHistory(Long ptoductConsultingId) {
		List<ProductConsultingUpdateItemDto> updateHistory = productConsultingItemRepository
				.findByProductConsultingId(ptoductConsultingId);
		return updateHistory;
	}

	@Override
	public List<ProductConsultingCommentDto> getCommentOptions() {
		List<ProductConsultingCommentDto> commentOptions = new ArrayList<ProductConsultingCommentDto>();
		for (ProductConsultingCommentEnum commentEnum : ProductConsultingCommentEnum.class.getEnumConstants()) {
			ProductConsultingCommentDto commentOption = new ProductConsultingCommentDto();
			commentOption.setCommentTitle(commentEnum.getCommentTitle());
			commentOption.setCommentValue(commentEnum.getCommentValue());
			commentOptions.add(commentOption);
		}
		return commentOptions;
	}

	@Override
	@Transactional
	public ProductConsultingInforSearchDto updateProductConsultingToProcessing(ProductConsultingInforSearchDto model,
			Long productConsutingId, Locale locale) {
		ProductConsultingInfor entity = consultingInforRepository.findOne(productConsutingId);
		ProductConsultingInforSearchDto dto = processUpdateProductConsulting(entity, model, productConsutingId, locale,
				ProductConsultingStatusEnum.PRODUCT_CONSULTING_STATUS_PROCESSING);
		return dto;
	}

	@Override
	@Transactional
	public ProductConsultingInforSearchDto updateProductConsultingDone(ProductConsultingInforSearchDto model,
			Long productConsutingId, Locale locale) {
		ProductConsultingInfor entity = consultingInforRepository.findOne(productConsutingId);
		ProductConsultingInforSearchDto dto = processUpdateProductConsulting(entity, model, productConsutingId, locale,
				ProductConsultingStatusEnum.PRODUCT_CONSULTING__DONE);
		return dto;
	}

	@Override
	@Transactional
	public ProductConsultingInforSearchDto updateProductConsultingReject(ProductConsultingInforSearchDto model,
			Long productConsutingId, Locale locale) {
		ProductConsultingInfor entity = consultingInforRepository.findOne(productConsutingId);
		ProductConsultingInforSearchDto dto = processUpdateProductConsulting(entity, model, productConsutingId, locale,
				ProductConsultingStatusEnum.PRODUCT_CONSULTING__REJECTED);
		return dto;
	}

	private ProductConsultingInforSearchDto processUpdateProductConsulting(ProductConsultingInfor entity,
			ProductConsultingInforSearchDto model, Long productConsutingId, Locale locale,
			ProductConsultingStatusEnum processingStatus) {
		ProductConsultingInforSearchDto consultingDto = new ProductConsultingInforSearchDto();
		try {
			entity.setProcessingStatus(processingStatus.getStatusName());
			entity.setProcessedUser(UserProfileUtils.getFullName());
			entity.setComment(model.getComment());
			entity.setCommentCode(model.getCommentCode());
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(UserProfileUtils.getUserNameLogin());

			// update History
			ProductConsultingUpdateItem updateHistoryItem = new ProductConsultingUpdateItem();
			updateHistoryItem.setProductConsultingInforId(productConsutingId);
			updateHistoryItem.setCommentName(model.getComment());
			updateHistoryItem.setCommentCode(model.getCommentCode());
			updateHistoryItem.setProcessedUser(UserProfileUtils.getFullName());
			updateHistoryItem.setStatus(processingStatus.getStatusName());
			updateHistoryItem.setCreateDate(new Date());
			updateHistoryItem.setCreateBy(UserProfileUtils.getUserNameLogin());
			productConsultingItemRepository.save(updateHistoryItem);

			entity = consultingInforRepository.save(entity);
			consultingDto = new ProductConsultingInforSearchDto(entity);

			List<ProductConsultingUpdateItemDto> updateHistory = productConsultingItemRepository
					.findByProductConsultingId(productConsutingId);
			consultingDto.setUpdateHistory(updateHistory);
		} catch (Exception e) {
			logger.error("updateProductConsultingToProcessing: " + e.getMessage());
			throw new BusinessException(msg.getMessage(ConstantCore.MESSAGE_ERROR_PROCESSING, null, locale));
		}
		return consultingDto;
	}

}

package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.OrderEditDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductManagementEditDto;
import vn.com.unit.cms.core.module.order.dto.OrderLanguageSearchDto;
import vn.com.unit.cms.core.module.order.dto.OrderSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.cms.core.module.report.dto.ReportGroupDetailSearchDto;
import vn.com.unit.ep2p.admin.dto.AuthorityDetailDto;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

public interface OrderService extends
        CmsCommonSearchFillterService<OrderSearchDto, OrderLanguageSearchDto, OrderEditDto> {
	public List<OrderLanguageSearchDto> getListForExportGeneral();
	public List<OrderLanguageSearchDto> getListForExportDetail();
}

package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductManagementEditDto;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

public interface ProductManagementService extends
        CmsCommonSearchFillterService<ProductSearchDto, ProductLanguageSearchDto, ProductManagementEditDto> {

}

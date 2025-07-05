package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

public interface DocumentCategoryService extends
        CmsCommonSearchFillterService<DocumentCategorySearchDto, DocumentCategorySearchResultDto, DocumentCategoryEditDto> {

    public List<MenuNode> getListTree(String lang, Long rootId, Long idIgnore);
}

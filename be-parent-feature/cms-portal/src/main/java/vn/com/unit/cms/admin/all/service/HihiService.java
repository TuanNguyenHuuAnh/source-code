package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.HihiEditDto;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

public interface HihiService extends
        CmsCommonSearchFillterService<DocumentCategorySearchDto, DocumentCategorySearchResultDto, HihiEditDto> {

//    public List<MenuNode> getListTree(String lang, Long rootId, Long idIgnore);
}

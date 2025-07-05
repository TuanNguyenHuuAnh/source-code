/*******************************************************************************
 * Class        ：IntroduceManagementRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryParentCodeDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryViewDto;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface DocumentCategoryRepository extends DbRepository<DocumentCategory, Long> {

    /**
     *
     * @param searchDto
     * @return
     * @author TaiTM
     */
    public int countList(@Param("searchDto") DocumentCategorySearchDto searchDto);

    /**
     * @param searchDto
     * @param pageable
     * @return
     * @author TaiTM
     */
    public Page<DocumentCategorySearchResultDto> findListSearch(@Param("searchDto") DocumentCategorySearchDto searchDto,
            Pageable pageable);
    

    public List<DocumentCategoryEditDto> findListForTree(@Param("languageCode") String lang, @Param("idIgnore") Long idIgnore, @Param("channel") String channel);
    
    public List<DocumentCategorySearchResultDto> findListForSort(
            @Param("searchDto") DocumentCategorySearchDto searchDto);
    
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);
    
    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @return
     */
    public List<DocumentCategory> findRootActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("customerTypeId") String customerTypeIdText);

    public List<DocumentCategory> findAllRootActive(@Param("customerTypeId") String customerTypeIdText);

    public List<DocumentCategory> findAllActiveChildrenCategory(@Param("parentIds") List<Long> parentIds,
            @Param("customerTypeId") String customerTypeIdText);

    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param condition
     * @return
     */
    public List<DocumentCategory> findRootActiveByConditions(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("condition") DocumentCategorySearchDto condition,
            @Param("languageCode") String languageCode);

    /**
     * 
     * @param condition
     * @param languageCode
     * @return
     */
    public List<DocumentCategory> findRootForPageByConditions(@Param("condition") DocumentCategorySearchDto condition,
            @Param("languageCode") String languageCode, @Param("languageCode") String customerTypeIdText);

    /**
     * 
     * @param condition
     * @return
     */
    public int countRootActiveByConditions(@Param("condition") DocumentCategorySearchDto condition,
            @Param("languageCode") String languageCode);

    public int countRootActive(@Param("customerTypeId") String customerTypeIdText);

    public DocumentCategory findDetailById(@Param("id") Long id);

    public List<DocumentCategory> findRootListForSelection(@Param("customerTypeId") String customerTypeIdText);

    /**
     * 
     * @param parentIds
     * @return
     */
    public List<DocumentCategory> findAllActiveChildrenCategoryForSelection(@Param("parentIds") List<Long> parentIds,
            @Param("customerTypeId") String customerTypeIdText);

    /**
     * @param code
     * @return count
     */
    public int countByCode(@Param("code") String code);

    /**
     * @param id
     * @return
     */
    public DocumentCategoryEditDto findCategoryViewDetail(@Param("id") Long id);

    /**
     * 
     * @return
     */
    public List<DocumentCategoryParentCodeDto> findAllActiveCodes();

    /**
     * 
     * @param parentId
     * @return
     */
    public Long findMaxSort(@Param("parentId") Long parentId);

    /**
     * 
     * @param typeId
     * @return
     */
    public Long findMaxSortInType(@Param("typeId") Long typeId);

    /**
     * 
     * @param typeCode
     * @param languageCode
     * @param processStatus
     * @return
     */
    public List<DocumentCategoryViewDto> findViewDtoByTypeCode(@Param("typeCode") String typeCode,
            @Param("languageCode") String languageCode, @Param("processStatus") String processStatus);

    /**
     * @param id
     * @param languageCode
     * @return
     */
    public DocumentCategoryViewDto findViewDtoById(@Param("id") Long id, @Param("languageCode") String languageCode);

    /**
     * 
     * @param customerTypeId
     * @param typeCode
     * @param languageCode
     * @param processStatus
     * @return
     */
    public List<DocumentCategoryViewDto> findViewDtoForCustomerByTypeId(
            @Param("customerTypeId") String customerTypeIdText, @Param("typeCode") String typeCode,
            @Param("languageCode") String languageCode, @Param("processStatus") String processStatus);

    /**
     * find root node's id
     * 
     * @param categoryCode
     * @return
     */
    public List<Long> findIdListByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * find root node's id
     * 
     * @param categoryCode
     * @return
     */
    public Long findParentIdByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * find code by id
     * 
     * @param categoryId
     * @return
     */
    public String findCodeByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * find root node's id by id
     * 
     * @param categoryId
     * @return
     */
    public Long findParentIdByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * find code list by code
     * 
     * @param categoryId
     * @return
     */
    public List<DocumentCategoryEditDto> findDocumentCategoryDtoListByCategoryCode(
            @Param("condition") DocumentCategorySearchDto condition, @Param("languageCode") String languageCode);

    /**
     * find Dto by id
     * 
     * @param categoryId
     * @return
     */
    public DocumentCategoryEditDto findDocumentCategoryDtoById(@Param("categoryId") Long categoryId);

    /**
     * countByLinkAliasAndParentIdInTypeWithExceptId
     * 
     * @param linkAlias
     * @param exceptId
     * @param parentId
     * @param typeId
     * @return
     */
    public int countByLinkAliasAndParentIdInTypeWithExceptId(@Param("linkAlias") String linkAlias,
            @Param("exceptId") Long exceptId, @Param("parentId") Long parentId, @Param("typeId") Long typeId);

    /**
     * findActiveByCondition
     * 
     * @param searchDto
     * @return
     */
    public List<DocumentCategory> findByTypeAndParent(@Param("typeId") Long typeId, @Param("parentId") Long parentId,
            @Param("customerTypeId") String customerTypeIdText);

    /**
     * findSelectionByTypeAndParent
     * 
     * @param typeId
     * @param parentId
     * @return
     */
    public List<DocumentCategory> findSelectionByTypeAndParent(@Param("typeId") Long typeId,
            @Param("parentId") Long parentId, @Param("customerTypeId") String customerTypeIdText);

    /**
     * getMaxCode
     *
     * @author nhutnn
     */
    String getMaxCode();
    
    DocumentCategory findByCode(@Param("code") String code);

}

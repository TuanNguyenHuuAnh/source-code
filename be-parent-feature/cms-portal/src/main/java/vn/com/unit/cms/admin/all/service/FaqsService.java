/*******************************************************************************
 * Class        ：FaqsService
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.faqs.dto.FaqsEditDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * FaqsService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface FaqsService extends DocumentWorkflowCommonService<FaqsEditDto, FaqsEditDto>,
        CmsCommonSearchFillterService<FaqsSearchDto, FaqsSearchResultDto, FaqsEditDto> {

    /**
     * Find faqs by code
     * 
     * @param faqsCode
     * @return Faqs
     * @author TaiTM
     */
    public Faqs findFaqsByCode(String faqsCode);

    /**
     * getFaqsDto
     *
     * @param faqsId
     * @param languageCode
     * @param action       boolean: true is edit, false is detail
     * @return FaqsEditDto
     * @author TaiTM
     */
    public FaqsEditDto getEditDto(Long faqsId, Locale locale, boolean action, String customerAlias);

    /**
     * addOrEdit Faqs
     *
     * @param editDto
     * @param action  true is saveDraft, false is sendRequest
     * @author TaiTM
     * @param request
     * @throws Exception
     */
    public boolean addOrEdit(FaqsEditDto editDto, boolean action, Locale locale, HttpServletRequest request)
            throws Exception;

    /**
     * delete Faqs
     *
     * @param faqs
     * @author TaiTM
     */
    public void deleteFaqs(Faqs faqs);

    /**
     * delete Faqs by category id
     *
     * @param typeId
     * @author TaiTM
     */
    public void deleteFaqsByCategoryId(Long typeId);
    
    /**
     * findById
     *
     * @param id
     * @return
     * @author TaiTM
     */
    public Faqs findById(Long id);

    public Boolean updateSortAll(List<SortOrderDto> sortOderList);

    public List<FaqsSearchResultDto> findAllFaqsForSort(String lang, FaqsSearchDto searchDto);

    FaqsLanguage findByAliasCategoryIdAndTypeId(String linkAlias, String languageCode, Long customerId, Long categoryId,
            Long typeId, List<Long> categoryProductListId, List<Long> categorySubProductListId,
            List<Long> productListId);

    public String getCateFaq(Long id, String lang);

    public Faqs getlsDocument(Long id);

}

/*******************************************************************************
 * Class        :FormRepository
 * Created date :2019/04/17
 * Lasted date  :2019/04/17
 * Author       :HungHT
 * Change log   :2019/04/17:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.repository.EfoFormRepository;
import vn.com.unit.ep2p.dto.ResBoardListDto;
import vn.com.unit.ep2p.dto.ResBoardListServicesDto;
import vn.com.unit.ep2p.dto.ResDocumentDetailDto;

/**
 * FormRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface FormRepository extends EfoFormRepository{

    /**
     * countFormList
     * 
     * @param search
     * @return
     * @author HungHT
     */
    int countFormList(@Param("search") EfoFormSearchDto search);

    /**
     * getFormList
     * 
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<EfoFormDto> getFormList(@Param("companyList") List<Long> companyList, @Param("formId") Long formId, @Param("languageCode") String langCode, @Param("processTypeIgnores") List<String> processTypeIgnores);

    /**
     * findById
     * 
     * @param id
     * @return
     * @author HungHT
     */
    EfoFormDto findById(@Param("id") Long id);

    /**
     * getListForm
     * 
     * @param keySearch
     * @param companyId
     * @param accountId
     * @param isPaging
     * @return
     * @author HungHT
     */
    List<Select2Dto> getFormListByCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("accountId") Long accountId, @Param("isPaging") boolean isPaging, @Param("lang") String lang);

    /**
     * getStatusListByCompanyId
     * 
     * @param keySearch
     * @param companyId
     * @param formId
     * @param isPaging
     * @param lang
     * @return
     * @author KhoaNA
     */
    List<Select2Dto> getStatusListByCompanyIdAndFormId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("processId") Long processId, @Param("isPaging") boolean isPaging, @Param("lang") String lang);

    /**
     * findByCompanyIdAndFileName
     * 
     * @param fileNameList
     * @param companyId
     * @return
     * @author HungHT
     */
    List<EfoFormDto> findByCompanyIdAndFileName(@Param("companyId") Long companyId, @Param("fileNameList") List<String> fileNameList);

    /**
     * findByCompanyIdAndName
     * 
     * @param companyId
     * @param name
     * @param currentFormId
     * @return
     * @author HungHT
     */
    EfoFormDto findByCompanyIdAndName(@Param("companyId") Long companyId, @Param("name") String name,
            @Param("currentFormId") Long currentFormId);

    /**
     * findMaxDisplayOrderByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    Long findMaxDisplayOrderByCompanyId(@Param("companyId") Long companyId);

    /**
     * checkExistBusinessCode
     * 
     * @param businessCode
     * @return
     * @author HungHT
     */
    Boolean checkExistBusinessCode(@Param("businessCode") String businessCode);

    /**
     * checkExistProcessCode
     * 
     * @param processCode
     * @return
     * @author HungHT
     */
    Boolean checkExistProcessCode(@Param("processCode") String processCode);

    /**
     * checkExistItem
     * 
     * @param item
     * @return
     * @author HungHT
     */
    Boolean checkExistItem(@Param("item") String item, @Param("companyId") Long companyId);

    /**
     * getSvcBoardByCompanyIdAndCategoryId
     * 
     * @param companyId
     * @param categoryId
     * @return
     * @author HungHT
     */
    List<EfoFormDto> getSvcBoardByCompanyIdAndCategoryId(@Param("companyId") Long companyId, @Param("categoryId") Long categoryId,
            @Param("languageCode") String langCode);

    /**
     * getListCompanyId
     * 
     * @return
     * @author TaiTT
     */
    List<Long> getListCompanyId();

    /**
     * getFormListByListComPanyId
     * 
     * @param companyIdList
     * @param langCode
     * @return
     * @author TaiTT
     */
    List<ResBoardListDto> getDocumentFormListByListCompanyId(@Param("companyIdList") List<Long> companyId,
            @Param("accountId") Long accountId, @Param("languageCode") String langCode, @Param("pageSize") Integer pageSize,
            @Param("page") int startIndex);

    /**
     * 
     */
    Long countDocumentFormListByListCompanyId(@Param("companyIdList") List<Long> companyId, @Param("accountId") Long accountId);

    /***
     * 
     * getMyTemplateFormListByListCompanyId
     * 
     * @param companyId
     * @param langCode
     * @param pageSize
     * @param startIndex
     * @return
     * @author taitt
     */
    List<ResBoardListDto> getMyTemplateFormListByListCompanyId(@Param("companyIdList") List<Long> companyId,
            @Param("accountId") Long accountId, @Param("languageCode") String langCode, @Param("pageSize") Integer pageSize,
            @Param("page") int startIndex,@Param("processTypeIgnores")List<String> processTypeIgnores);

    /**
     * 
     * getMytaskFormListByListCompanyId
     * 
     * @param companyList
     * @param groupIdList
     * @param userId
     * @param langCode
     * @param pageSize
     * @param startIndex
     * @return
     * @author taitt
     */
    List<ResBoardListDto> getMytaskFormListByListCompanyId(@Param("companyList") List<Long> companyList, @Param("accountId") Long accountId,
            @Param("actUserId") String actUserId, @Param("languageCode") String langCode, @Param("pageSize") Integer pageSize,
            @Param("page") int startIndex);

    Long getCountDocumentForm(@Param("companyList") List<Long> companyList);

    /**
     * getServiceList
     * 
     * @param companyId
     * @param categoryId
     * @param langCode
     * @return
     * @author TaiTT
     */
    List<ResBoardListServicesDto> getServiceList(@Param("companyId") Long companyId, @Param("categoryId") Long categoryId,
            @Param("accountId") Long accountId, @Param("langCode") String langCode, @Param("pageSize") Integer pageSize,
            @Param("page") int startIndex);

    /**
     *  only Apply with Mobile because not create service by config = 'pc'
     * findTemplateSerivceDtoByCondition
     * 
     * @param companyId
     * @param categoryId
     * @param langCode
     * @param pageSize
     * @param startIndex
     * @return
     * @author taitt
     */
    List<ResBoardListServicesDto> findTemplateSerivceDtoByCondition(@Param("pageSize") Integer pageSize, @Param("page") int startIndex,
            @Param("companyId") Long companyId, @Param("categoryId") Long categoryId, @Param("accountId") Long accountId,
            @Param("langCode") String langCode,@Param("processTypeIgnores")List<String> processTypeIgnores);

    /**
     * getDocumentList
     * 
     * @param companyId
     * @param categoryId
     * @param langCode
     * @return
     * @author TaiTT
     */
    List<ResDocumentDetailDto> getDocumentList(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("categoryId") Long categoryId, @Param("companyList") List<Long> companyList, @Param("languageCode") String langCode,
            @Param("pageSize") Integer pageSize, @Param("page") int startIndex);

    /**
     * findByCompanId
     * 
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findByCompanId(@Param("companyId") Long companyId);

    /**
     * 
     * getBusinessIdByFormId
     * 
     * @param formId
     * @return
     * @author KhuongTH
     */
    Long getBusinessIdByFormId(@Param("formId") Long formId);

    /**
     * getFormIdByBusinessId
     *
     * @param businessId
     * @return Long
     * @author KhuongTH
     */
    Long getFormIdByBusinessId(@Param("businessId") Long businessId);
    
    /**
     * 
     * @param businessId
     * @return
     * @author Taitt
     */
    EfoFormDto getFormDtoByBusinessId(@Param("businessId") Long businessId,@Param("lang") String lang);

    String getFunctionCodeByFormId(@Param("companyId") Long companyId, @Param("categoryId") Long categoryId, @Param("formId") Long formId);

    Long countListMyTemplateDetailDtoByConditionAPI(@Param("companyIdList") List<Long> companyIdList, @Param("accountId") Long accountId,@Param("processTypeIgnores")List<String> processTypeIgnores);

    /**
     * @param keySearch
     * @param companyId
     * @param categoryId
     * @param langCode
     * @param processTypeIgnores
     * @param isPaging
     * @return
     */
    List<Select2Dto> findFromNameForCombobox(@Param("keySearch") String keySearch, @Param("companyId") Long companyId, 
    		@Param("categoryId") Long categoryId, @Param("languageCode") String langCode, 
    		@Param("processTypeIgnores") List<String> processTypeIgnores, @Param("isPaging") boolean isPaging);
    
    /**
     * @param docType
     * @return
     */
    List<Long> findIdsByDocType(@Param("companyId") Long companyId, @Param("docType") String docType);
    
    
    /**
     * @param keySearch
     * @param companyId
     * @param userName
     * @param isPaging
     * @return
     */
    List<Select2Dto> findListByCompanyIdAndUserName(@Param("keySearch") String keySearch
            , @Param("companyId") Long companyId, @Param("userName") String userName
            , @Param("processTypeIgnores") List<String> processTypeIgnores
            , @Param("isPaging") boolean isPaging, @Param("lang") String lang);
    
    /**
     * 
     * findListByCompanyIdAndUserName
     * @param keySearch
     * @param companyId
     * @param userName
     * @param processTypeIgnores
     * @param isPaging
     * @param lang
     * @return
     * @author taitt
     */
    List<Select2Dto> findListByCompanyIdAndUserNameWithPaging(@Param("keySearch") String keySearch
            , @Param("companyId") Long companyId, @Param("userName") String userName
            , @Param("processTypeIgnores") List<Integer> processTypeIgnores
            , @Param("lang") String lang, @Param("page") int startIndex, @Param("pageSize") Integer pageSize,@Param("isPaging")int isPaging);
    
    Long countListByCompanyIdAndUserNameWithPaging(@Param("keySearch") String keySearch
    , @Param("companyId") Long companyId, @Param("userName") String userName
    , @Param("processTypeIgnores") List<Integer> processTypeIgnores
    , @Param("lang") String lang, @Param("page") int startIndex, @Param("pageSize") Integer pageSize,@Param("isPaging")int isPaging);
    
    EfoFormDto getFormDtoById(@Param("id")Long id, @Param("lang")String lang);
    
    /**
     * getSelect2DtoForUser
     * @param keySearch
     * @param companyId
     * @param accountId
     * @param processTypeIgnores
     * @param isPaging
     * @param lang
     * @return
     * @author trieuvd
     */
    List<Select2Dto> getSelect2DtoForUser(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("accountId") Long accountId, @Param("processTypeIgnores") List<String> processTypeIgnores, 
            @Param("isPaging") boolean isPaging, @Param("lang") String lang);
}
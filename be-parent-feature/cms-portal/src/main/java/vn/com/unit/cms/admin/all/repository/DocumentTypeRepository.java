/*******************************************************************************
 * Class        ：DocumentTypeRepository
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：thuydtn
 * Change log   ：2017/04/18：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.DocumentTypeDto;
import vn.com.unit.cms.admin.all.dto.DocumentTypeLanguageDto;
import vn.com.unit.cms.admin.all.dto.DocumentTypeLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.DocumentType;
import vn.com.unit.cms.admin.all.entity.DocumentTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * DocumentTypeRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface DocumentTypeRepository extends DbRepository<DocumentType, Long> {

    /**
     * find all DocumentType not delete
     *
     * @return
     * @author thuydtn
     */
    List<DocumentType> findAllActive();

    /**
     * find only fields: id, name
     * 
     * @return
     */
    List<DocumentTypeLanguageDto> findAllActiveForSelection(@Param("customerTypeId") Long customerTypeId, 
    		@Param("languageCode") String languageCode,
    		@Param("status") Integer status);

    /**
     * count all record TypeLanguage
     *
     * @param searchDto
     * @return int
     * @author thuydtn
     */
    int countByTypeSearchDto(@Param("searchCond") DocumentTypeSearchDto searchDto, @Param("languageCode") String languageCode);

    /**
     * find all TypeLanguage by DocumentTypeSearchDto
     *
     * @param offsetSQL
     * @param sizeOfPage
     * @param searchDto
     *            DocumentTypeSearchDto
     * @return
     * @author thuydtn
     */
    List<DocumentTypeLanguageSearchDto> findByTypeSearchDto(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage, @Param("searchCond") DocumentTypeSearchDto searchDto,
            @Param("languageCode") String languageCode);

    /**
     * find DocumentType by code
     *
     * @param code
     * @return DocumentType
     * @author thuydtn
     */
    DocumentType findByCode(@Param("code") String code);

    /**
     * get max sort
     *
     * @return
     * @author thuydtn
     */
    Long findMaxSort();

    /**
     * @param typeCode
     * @param languageCode
     * @return
     */
    String findTypeIntroduce(@Param("typeCode") String typeCode, @Param("languageCode") String languageCode);

    /**
     * getMaxCode
     *
     * @author nhutnn
     */
    String getMaxCode();

    /** findByTypeId
     *
     * @param typeId
     * @author nhutnn
     */
    List<DocumentType> findByTypeIdAndIdExpect(@Param("typeId") Long typeId, @Param("idExpect") Long idExpect);
    
    /** findAllDocumentTypeAndNotIn
     *
     * @param languageCode
     * @param faqsTypeEditDto
     * @author nhutnn
     */
    List<DocumentTypeDto> findAllDocumentTypeAndNotIn(@Param("languageCode") String languageCode , @Param("typeDto") DocumentTypeDto documentTypeDto );
    
    /** findLangByLinkAlias
     *
     * @param linkAlias
     * @param languageCode
     * @param customerId
     * @author nhutnn
     */
    DocumentTypeLanguage findLangByLinkAlias(@Param("linkAlias") String linkAlias, @Param("languageCode") String languageCode, @Param("customerId") Long customerId);
    
    List<DocumentTypeLanguageSearchDto> exportFileExcel(@Param("searchCond") DocumentTypeSearchDto searchDto, @Param("languageCode") String languageCode);
}

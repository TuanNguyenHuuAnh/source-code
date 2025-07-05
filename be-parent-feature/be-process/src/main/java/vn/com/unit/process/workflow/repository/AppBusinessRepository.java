/*******************************************************************************
 * Class        :JpmBusinessRepository
 * Created date :2019/06/17
 * Lasted date  :2019/06/17
 * Author       :KhoaNA
 * Change log   :2019/06/17:01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppBusinessSearchDto;
import vn.com.unit.workflow.repository.JpmBusinessRepository;

/**
 * JpmBusinessRepository
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppBusinessRepository extends JpmBusinessRepository {

	/**
     * count JpmBusiness by searchDto and deleteBy is null
     * 
     * @param searchDto
     * 			type JpmBusinessSearchDto
     * @return int
     * @author KhoaNA
     */
    int countJpmBusinessByCondition(@Param("searchDto") AppBusinessSearchDto searchDto);

    /**
     * find JpmBusiness by searchDto and deleteBy is null
     * 
     * @param startIndex
     * 			type int
     * @param sizeOfPage
     * 			type int
     * @param searchDto
     * 			type JpmBusinessSearchDto
     * @return List<JpmBusinessDto>
     * @author KhoaNA
     */
    List<AppBusinessDto> findJpmBusinessDtoByCondition(@Param("offset") int startIndex,
        @Param("sizeOfPage") int sizeOfPage, @Param("searchDto") AppBusinessSearchDto searchDto);

    /**
     * Find JpmBusiness by code, companyId and deleteBy is null
     * @param code
     * 			type String
     * @param companyId
     * 			type Long
     * @return JpmBusinessDto
     * @author KhoaNA
     */
    AppBusinessDto findOneJpmBusinessByCodeAndCompanyId(@Param("code") String code,
                                                     @Param("companyId") Long companyId);
    
	/**
     * Find JpmBusiness by deleteBy is null
     * @param id
     * @return JpmBusinessDto
     * @author KhoaNA
     */
    AppBusinessDto findOneJpmBusinessById(@Param("id") Long id);
    
    /**
     * Find JpmBusiness list by keySearch, companyId, companyAdmin and isPaging
     * 
     * @param keySearch
     * 			type String
     * @param companyId
     * 			type Long
     * @param companyAdmin
     * 			type boolean
     * @param isPaging
     * 			type boolean
     * @return List<JpmBusinessDto>
     * @author KhoaNA
     */
    List<AppBusinessDto> findJpmBusinessByKeySearchAndCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
    					@Param("companyAdmin") boolean companyAdmin, @Param("isPaging") boolean isPaging);
    
    /**
     * 
     * findJpmBusinessByKeySearchAndCompanyId
     * @param keySearch
     * @param companyId
     * @param companyAdmin
     * @param isPaging
     * @return
     * @author taitt
     */
    List<AppBusinessDto> findJpmBusinessByKeySearchAndCompanyIdByPaging(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
			@Param("companyAdmin") boolean companyAdmin,@Param("processTypeIgnores") List<String> processTypeIgnores
			,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize,@Param("isPaging")int isPaging);
    
    /**
     * 
     * countJpmBusinessByKeySearchAndCompanyId
     * @param keySearch
     * @param companyId
     * @param companyAdmin
     * @return
     * @author taitt
     */
    Long countJpmBusinessByKeySearchAndCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
			@Param("companyAdmin") boolean companyAdmin,@Param("processTypeIgnores")List<String> processTypeIgnores);
    /**
     * Find JpmBusiness list by  companyId
     * 
     * @param companyId
     * 			type Long
     * @return List<JpmBusinessDto>
     * @author KhuongTH
     */
    List<AppBusinessDto> findJpmBusinessDtoListByCompanyId(@Param("companyId") Long companyId);

    /**
     * findSelect2DtoHasAuthorityByCompanyId
     *
     * @param companyId
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    List<Select2Dto> findSelect2DtoHasAuthorityByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * findSelect2DtoUnregistered
     * @param companyId
     * @param processTypes
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findSelect2DtoUnregisteredCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId, @Param("processTypes") List<Integer> processTypes, @Param("isPaging") boolean isPaging);
}

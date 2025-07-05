/*******************************************************************************
 * Class        ：DocumentRepository
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchResultDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface DocumentRepository extends DbRepository<Document, Long> {

	/**
	 * @param condition
	 * @return
	 */
	public int countList(@Param("searchDto") DocumentSearchDto searchDto);

	/**
	 * @param offset
	 * @param sizeOfPage
	 * @return
	 */
	public Page<DocumentSearchResultDto> findListSearch(@Param("searchDto") DocumentSearchDto searchDto,
			Pageable pageable);

	public List<DocumentSearchResultDto> findListForSort(@Param("searchDto") DocumentSearchDto searchDto);

	/**
	 * @param offset
	 * @param sizeOfPage
	 * @param condition
	 * @return
	 */
	public List<DocumentEditDto> findActiveByConditions(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("condition") DocumentSearchDto condition,
			@Param("languageCode") String languageCode);

	/**
	 * @param code
	 * @return
	 */
	public int countByCode(@Param("code") String code);

	/**
	 * @param documentId
	 * @return
	 */
	public Integer findCurrentVersionByDocumentId(@Param("documentId") Long documentId);

	/**
	 * countByLinkAliasInCategory
	 * 
	 * @param linkAlias
	 * @param categoryId
	 * @return
	 */
	public int countByLinkAliasInTypeWithExceptId(@Param("languageCode") String languageCode,
			@Param("customerTypeId") Long customerTypeId, @Param("linkAlias") String linkAlias,
			@Param("typeId") Long typeId, @Param("exceptId") Long exceptId);

	public List<DocumentEditDto> exportExcelDocument(@Param("condition") DocumentSearchDto condition,
			@Param("languageCode") String languageCode);

	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 22/05/2019
	 * @param documentTypeId
	 * @param lstStatus
	 * 
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("documentTypeId") Long documentTypeId,
			@Param("lstStatus") List<Long> lstStatus);

	public String findGenCode(@Param("categoryLangId") Long categoryLangId);


    List<String> getListRoleNameByUserName(@Param("userNameLogin") String userNameLogin);

    Integer isUserHasRoleMaker(@Param("username") String username);
    
    public Document getDocumentByCode(@Param("code") String code);
	public String getCategoryName(@Param("id")Long id, @Param("lang") String lang);
	
	public String getbutton(@Param("id")Long id, @Param("idp") Long idp);

	public List<JcaGroupEmailDto> lstEmailCcByCondition(@Param("roleCode")String roleCode, @Param("groupCode")String groupCode, @Param("channel")String channel);

	public List<JcaGroupEmailDto> findGourpMailByTaskId(@Param("jpmTaskId")Long jpmTaskId);

	public List<JcaGroupEmailDto> getListRoleNameByUserNameAndProcessDeployId(@Param("userNameLogin")String userNameLogin,
			@Param("processDeployId")Long processDeployId, @Param("permissonCode")String permissonCode, @Param("channel")String channel);

}

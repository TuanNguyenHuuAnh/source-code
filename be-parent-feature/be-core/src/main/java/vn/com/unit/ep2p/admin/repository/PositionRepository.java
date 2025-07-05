/*******************************************************************************
 * Class        PositionRepository
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.repository.JcaPositionRepository;
import vn.com.unit.ep2p.admin.dto.PositionNode;
import vn.com.unit.ep2p.admin.dto.PositionSearchDto;
import vn.com.unit.common.dto.Select2Dto;

/**
 * PositionRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface PositionRepository extends JcaPositionRepository{

	/**
     * find positionDto by id
     * @param id
     *          type Long
     * @return PositionDto
     * @author KhoaNA
     */
	JcaPositionDto findPositionDtoById(@Param("id") Long id);
	
	/**
     * find position by code
     * @param name
     *          type String
     * @param id
     * 			type Long
     * @return Position
     * @author KhoaNA
     */
	JcaPosition findPositionByCode(@Param("code") String code, @Param("id") Long id);
	
	/**
     * count position by condition
     * @param searchDto
     *          type PositionSearchDto
     * @return int
     * @author KhoaNA
     */
	int countPositionDtoByCondition(@Param("searchDto") PositionSearchDto searchDto);
	
	/**
     * find positionDto list by condition
     * @param offset
     *          type int
     * @param sizeOfPage
     *          type int
     * @param searchDto
     *          type PositionSearchDto
     * @return List<PositionDto>
     * @author KhoaNA
     */
	List<JcaPositionDto> findPositionDtoByCondition(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("searchDto") PositionSearchDto searchDto);
	
	/**
     * find positionDto
     * @return List<PositionDto>
     * @author KhoaNA
     */
	List<JcaPositionDto> findPositionDtoList();
	
	/**
	 * findByCompany
	 * @param companyId
	 * @param companyAdmin
	 * @return List PositionDto
	 * @author trieuvd
	 */
	List<JcaPositionDto> findByCompany(@Param("companyId") Long companyId);
	
	List<JcaPositionDto> findPositionDtoListByCompanyId(@Param("companyId") Long companyId);
	
	/**
	 * getByCodeAndCompanyId
	 * @param code
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	JcaPosition getByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId, @Param("positionId") Long positionId);
	
	/**
	 * findSelect2DtoByKeyAndCompanyId
	 * @param keySearch
	 * @param companyId
	 * @param companyAdmin
	 * @param isPaging
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> findSelect2DtoByKeyAndCompanyId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("companyAdmin") boolean companyAdmin, @Param("isPaging") boolean isPaging);

	List<PositionNode> findNodeByOrgParent(@Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin, @Param("id")Long id);

	int countByParentId(@Param("parentId") Long parentId);

	Integer getMaxPositionSort(@Param("parentId")Long parentId);
}

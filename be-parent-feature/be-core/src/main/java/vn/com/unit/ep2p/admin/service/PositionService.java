/*******************************************************************************
 * Class        PositionService
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.service.JcaPositionService;
import vn.com.unit.ep2p.admin.dto.PositionNode;
import vn.com.unit.ep2p.admin.dto.PositionSearchDto;

/**
 * PositionService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface PositionService extends JcaPositionService{
	
	/**
     * get positionDto list
     *
     * @param  languageCode
     * 			type String
     * @return PositionDto
     * @author KhoaNA
     */
    public List<JcaPositionDto> getPositionDtoList();
    
    /**
     * get positionDto by id
     *
     * @param  id
     * 			type Long
     * @return PositionDto
     * @author KhoaNA
     */
    public JcaPositionDto getPositionDtoById(Long id);
    
    /**
     * get position by code
     *
     * @param  code
     * 			type String
     * @param  id
     * 			type Long
     * @return Position
     * @author KhoaNA
     */
    public JcaPosition getPositionByCode(String code, Long id);
    
    /**
     * save positionDto
     *
     * @param  positionDto
     * 			type PositionDto
     * @return
     * @author KhoaNA
     */
    public void savePositionDto(JcaPositionDto positionDto);
    
    /**
     * search positionDto
     *
     * @param page
     * 			type int
     * @param  searchDto
     * 			type PositionSearchDto
     * @param pageSize
     * 			type int
     * @return PageWrapper<PositionDto>
     * @author KhoaNA
     */
    public PageWrapper<JcaPositionDto> searchByCondition(int page, PositionSearchDto searchDto, int pageSize);
    
    /**
     * delete position
     *
     * @param  id
     * 			type Long
     * @return
     * @author KhoaNA
     */
    public void deletePositionById(Long id);
    
    /**
     * findPositionDtoByCompany
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<JcaPositionDto> findPositionDtoByCompany(Long companyId);
    
    public List<JcaPositionDto> getPositionDtoListByCompanyId(Long companyId);
    
    /**
     * getByCodeAndCompanyId
     * @param code
     * @param companyId
     * @param positionId
     * @return
     * @author trieuvd
     */
    public JcaPosition getByCodeAndCompanyId(String code, Long companyId, Long positionId);
    
    /**
     * getSelect2DtoListByTermAndCompanyId
     * @param keySearch
     * @param companyId
     * @param isAdmin
     * @param isPaging
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> getSelect2DtoListByTermAndCompanyId(String keySearch, Long companyId, Boolean isAdmin, Boolean isPaging);

	public PositionNode buildPosition(Long posId);

	public List<PositionNode> findPositionByParent(Long id);

	public Integer getMaxPositionSort(Long positionParentId);
	
	public JcaPositionDto buildPosModel(long orgId, boolean editable);

}

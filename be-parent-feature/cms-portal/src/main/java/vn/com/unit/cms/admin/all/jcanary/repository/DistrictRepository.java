/*******************************************************************************
 * Class        DistrictRepository
 * Created date 2017/02/20
 * Lasted date  2017/02/20
 * Author       TranLTH
 * Change log   2017/02/2001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.District;
import vn.com.unit.cms.admin.all.jcanary.entity.DistrictLanguage;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;

//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.DistrictSearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.District;
//import vn.com.unit.jcanary.entity.DistrictLanguage;

/**
 * DistrictRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface DistrictRepository extends DbRepository<District, Long> {
    /**
     * findDistrictListByCondition
     *
     * @param districtDto
     * @param languageCode
     * @return List<District>
     * @author TranLTH
     */
    public List<DistrictDto> findDistrictListByCondition(@Param("districtDto") DistrictDto districtDto, @Param("languageCode") String languageCode);
    public List<DistrictDto> findDistrictListByConditionOracle(@Param("districtDto") DistrictDto districtDto, @Param("languageCode") String languageCode);
    /**
     * getDistrictDtoById
     *
     * @param districtId
     * @return DistrictDto
     * @author TranLTH
     */
    public DistrictDto findDistrictDtoById(@Param("districtId") Long districtId);
    public DistrictDto findDistrictDtoByIdOracle(@Param("districtId") Long districtId);
    /**
     * countDistrictByCondition
     *
     * @param districtSearchDto
     * @return int
     * @author TranLTH
     */
    public int countDistrictByCondition( @Param("districtSearchDto")DistrictSearchDto districtSearchDto);
    /**
     * findDistrictLimitByCondition
     *
     * @param startIndex
     * @param sizeOfPage
     * @param districtSearchDto
     * @return List<DistrictDto>
     * @author TranLTH
     */
    public List<DistrictDto> findDistrictLimitByConditionMYSQL(
    		@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("districtSearchDto") DistrictSearchDto districtSearchDto);
    
    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param districtSearchDto
     * @return
     */
    public List<DistrictDto> findDistrictLimitByConditionSQLServer(
    		@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("districtSearchDto") DistrictSearchDto districtSearchDto);
    /**
     * findDistrictIdLanguage
     *
     * @param districtId
     * @return List<DistrictLanguage>
     * @author TranLTH
     */
    public List<DistrictLanguage> findDistrictIdLanguage(@Param("districtId") Long districtId);
    /**
     * findDistrictLimitByConditionOracle
     *
     * @param offset
     * @param sizeOfPage
     * @param districtSearchDto
     * @return
     * @author phatvt
     */
    public List<DistrictDto> findDistrictLimitByConditionOracle(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("districtSearchDto") DistrictSearchDto districtSearchDto);
    
    public int countDistrictByConditionForOracle( @Param("districtSearchDto")DistrictSearchDto districtSearchDto);
	
    
	/**
	 * @param cityId
	 * @param languageCode
	 * @return List<Select2Dto>
	 * @author LongPNT
	 */
	public List<Select2Dto> findAllDistrictListByCityIdOracle(@Param("cityId") Long cityId, @Param("languageCode") String languageCode);
	
	/**
	 * @param cityId
	 * @param languageCode
	 * @return List<Select2Dto>
	 * @author LongPNT
	 */
	public List<Select2Dto> findAllDistrictListByCityId(@Param("cityId") Long cityId, @Param("languageCode") String languageCode);
	
	/** findAllDistrictListByCityIdByType
	 *
	 * @param cityId
	 * @param languageCode
	 * @param dtype
	 * @return
	 * @author hangnkm
	 */
	public List<Select2Dto> findAllDistrictListByCityIdByType(@Param("cityId") Long cityId, @Param("languageCode") String languageCode, @Param("dType") List<String> dtype);
	
	/**
	 * findAllDistrictForSelect2
	 * 
	 * @param term
	 * @return List<Select2Dto>
	 * @author longpnt
	 */
	public List<Select2Dto> findAllDistrictForSelect2(@Param("term") String term);
	
	
	/** findAllDistrictByType
	 *
	 * @param dtype
	 * @param languageCode
	 * @return
	 * @author hangnkm
	 */
	public List<DistrictDto> findAllDistrictByType(@Param("dType") String dtype, @Param("languageCode") String languageCode);

	
}

/*******************************************************************************
 * Class        BranchRepository
 * Created date 2017/03/10
 * Lasted date  2017/03/10
 * Author       TranLTH
 * Change log   2017/03/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchSelectionDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Branch;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.dto.BranchSearchDto;
//import vn.com.unit.jcanary.dto.BranchSelectionDto;
//import vn.com.unit.jcanary.entity.Branch;

/**
 * BranchRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface BranchRepository extends DbRepository<Branch, Long> {
    /**
     * findBranchListByCondition
     *
     * @param branchDto
     * @return
     * @author TranLTH
     */
    List<BranchDto> findBranchListByCondition(@Param("branchDto") BranchDto branchDto);

    /**
     * getBranchDtoById
     *
     * @param branchId
     * @return
     * @author TranLTH
     */
    BranchDto findBranchDtoById(@Param("branchId") Long branchId);

    /**
     * countBranchByCondition
     *
     * @param branchSearchDto
     * @return int
     * @author TranLTH
     */
    public int countBranchByCondition(@Param("branchSearchDto") BranchSearchDto branchSearchDto);

    /**
     * findBranchLimitByCondition
     *
     * @param startIndex
     * @param sizeOfPage
     * @param branchDto
     * @return
     * @author TranLTH
     */
    public List<BranchDto> findBranchLimitByConditionSQLServer(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("branchSearchDto") BranchSearchDto branchSearchDto);

    public List<BranchDto> findBranchLimitByConditionMYSQL(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("branchSearchDto") BranchSearchDto branchSearchDto);

    /**
     * findByCode
     *
     * @param code
     * @return
     * @author TranLTH
     */
    BranchDto findByCode(@Param("code") String code);

    List<BranchSelectionDto> findListForSelection(@Param("languageCode") String languageCode);

    /**
     * findBranchListByTypeAndCity
     * 
     * @param locationType
     * @param cityName
     * @param keyword
     * @return List<String>
     * @author hand
     */
    List<BranchDto> findBranchListByTypeAndCity(@Param("locationType") String locationType,
            @Param("cityName") String cityName, @Param("keyword") String keyword);
    /**
     * findBranchLimitByConditionOracle
     *
     * @param offset
     * @param sizeOfPage
     * @param branchSearchDto
     * @return
     * @author phatvt
     */
    public List<BranchDto> findBranchLimitByConditionOracle(@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, @Param("branchSearchDto") BranchSearchDto branchSearchDto);
    
    /**
     * @param languageCode
     * @return
     */
    List<BranchSelectionDto> findListForSelection(@Param("languageCode") String languageCode, @Param("typeSelect")  List<String> typeSelect);
    
    List<String> findCityList();
}
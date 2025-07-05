/**
 * @author TaiTM
 * @date 2021-03-16
 */
package vn.com.unit.ep2p.core.ers.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.req.dto.ZipCodeDtoRes;

/**
 * @author TaiTM
 * @date 2021-03-16
 */
public interface Select2DataRepository extends DbRepository<Object, Long> {

    /**
     * findProvinceList
     *
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> findProvinceList();

    /**
     * findAllDistrictByCondition
     *
     * @param province
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> findAllDistrictByCondition(@Param("province") String province);

    /**
     * findAllWardByCondition
     *
     * @param province
     * @param district
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> findAllWardByCondition(@Param("province") String province,
            @Param("district") String district);

    /**
     * findZipcodeDto
     *
     * @param zipcode
     * @return
     * @author TaiTM
     */
    public ZipCodeDtoRes findZipcodeDto(@Param("zipcode") String zipcode);

    /**
     * findBankList
     *
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> findBankList(@Param("lang") String lang);

    /**
     * findAllBankBranchByCondition
     *
     * @param province
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> findAllBankBranchByCondition(@Param("province") String province,
            @Param("lang") String lang);

    /**
     * findConstantData
     *
     * @param groupCode
     * @param kind
     * @param lang
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> findConstantData(@Param("groupCode") String groupCode, @Param("kind") String kind, @Param("code") String code,
            @Param("lang") String lang);

    /**
     * findRegionBancasList
     * @return
     * @author TuyenNX
     */
    public List<Select2Dto> findRegionBancasList();
    
    public List<Select2Dto> findRMManagerBancas();

    public List<Select2Dto> findManagerByRegionBancas(@Param("regionManagerCode") String regionManagerCode);

    public List<Select2Dto> findClassInfoNoStartBancas();

    public List<Select2Dto> findRMAMManagerBancas();
    
    public List<Select2Dto> findSecurityQuestion(@Param("lang") String lang);
    
    public List<Select2Dto> findListItem(@Param("companyId") Long lang);
    
    public List<Select2Dto> findListStatusForProcess(@Param("businessCode") String businessCode,
            @Param("languageCode") String languageCode, @Param("companyId") Long lang);
    
    public List<Select2Dto> findListFaqsCategory(@Param("lang") String lang);
    
    public List<Select2Dto> findListDocumentCategory(@Param("lang") String lang, @Param("channel") String channel);
    
    public List<Select2Dto> findListBanner( @Param("bannerDevice") String bannerDevice,
            @Param("bannerType") String bannerType);

	public List<Select2Dto> getListConstantByKindOrderBy(String groupCode, String kind, String lang);
}

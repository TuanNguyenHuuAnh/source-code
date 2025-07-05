/**
 * @author TaiTM
 * @date 2021-03-16
 */
package vn.com.unit.ep2p.core.ers.service;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * @author TaiTM
 * @date 2021-03-16
 */
public interface Select2DataService {

    /**
     * getProvinceList
     *
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> getProvinceList();

    /**
     * getAllDistrictByCity
     *
     * @param proivnce
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> getAllDistrictByProvince(String proivnce);

    /**
     * getAllWardByDistrict
     *
     * @param district
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> getAllWardByDistrict(String province, String district);

    /**
     * getAllBankBranchByProvince
     *
     * @param proivnce
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> getAllBankBranchByProvince(String proivnce, String lang);

    /**
     * getJob
     *
     * @param groupCode
     * @param kind
     * @param lang
     * @return List<Select2Dto>
     * @author TaiTM
     */
    public List<Select2Dto> getConstantData(String groupCode, String kind, String lang);
    public List<Select2Dto> getConstantData(String groupCode, String kind, String code, String lang);

    /**
     * getRegionBancasList
     * @return
     * @author TuyenNX
     */
    public List<Select2Dto> getRegionBancasList();
    
    /**
     * getRMManagerByRegionBancas
     * 
     * @return
     * @author TaiTM
     */
    public List<Select2Dto> getRMManagerBancas();

    public List<Select2Dto> getManagerByRegionBancas(String regionManagerCode);

    public List<Select2Dto> getClassInfoNoStartBancas();

    public List<Select2Dto> getRMAMManagerBancas();
    
    public List<Select2Dto> getSecurityQuestion(String lang);
    
    public List<Select2Dto> getListItem(Long companyId);
    
    public List<Select2Dto> getListStatusForProcess(String businessCode, String languageCode, Long companyId);
    
    public List<Select2Dto> getListFaqsCategory(String languageCode);
    
    public List<Select2Dto> getListDocumentCategory(String languageCode, String channel);
    
	public List<Select2Dto> getListBanner( String bannerDevice, String banenrType);

	public List<Select2Dto> getListConstantByKindOrderBy(String groupCode, String kind, String language);

}

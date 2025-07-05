package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.repository.JcaItemRepository;
import vn.com.unit.ep2p.admin.dto.ItemManagementSearchDto;
import vn.com.unit.common.dto.Select2Dto;

/**
 * Created by quangnd on 7/30/2018.
 */
public interface ItemManagementRepository extends JcaItemRepository {

    JcaItemDto findItemByFunctionCode(@Param("searchDto") ItemManagementSearchDto searchDto);

    Integer getMaxDisplayOrder();

    JcaItemDto findByFunctionCodeAndCompanyId(@Param("functionCode") String functionCode,
            @Param("companyId") Long companyId);

    JcaItemDto findById(@Param("id") Long id);

    /**
     * findByBusinessId
     * 
     * @param keySearch
     * @param companyId
     * @param businessId
     * @param isPaging
     * @param functionCodeAsId
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> findByBusinessId(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
            @Param("businessId") Long businessId, @Param("isPaging") boolean isPaging,
            @Param("functionCodeAsId") boolean functionCodeAsId);

    /**
     * getBusinessSelect2ByTermAndCompanyId
     * 
     * @param term
     * @param companyId
     * @param companyIds
     * @param companyAdmin
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> getBusinessSelect2ByTermAndCompanyId(@Param("term") String term,
            @Param("companyId") Long companyId, @Param("companyIds") List<Long> companyIds,
            @Param("companyAdmin") boolean companyAdmin);

    /**
     * getSelect2ByCondition
     * 
     * @param keySearch
     * @param companyId
     * @param functionTypes
     * @param subType
     * @param isPaging
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> getSelect2ByCondition(@Param("keySearch") String keySearch,
            @Param("companyId") Long companyId, @Param("functionTypes") List<String> functionTypes,
            @Param("subType") String subType, @Param("isPaging") boolean isPaging);

    /**
     * findByFunctionCodeFunctionTypeAndSubType
     * 
     * @param functionCode
     * @param functionType
     * @param subType
     * @param companyId
     * @return
     * @author trieuvd
     */
    public JcaItemDto findByFunctionCodeFunctionTypeAndSubType(@Param("functionCode") String functionCode,
            @Param("functionType") String functionType, @Param("subType") String subType,
            @Param("companyId") Long companyId);

    /**
     * findByProcessIdFunctionTypeAndSubType
     * 
     * @param processId
     * @param functionType
     * @param subType
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<JcaItemDto> findByProcessIdFunctionTypeAndSubType(@Param("processId") Long processId,
            @Param("functionType") String functionType, @Param("subType") String subType,
            @Param("companyId") Long companyId);
}

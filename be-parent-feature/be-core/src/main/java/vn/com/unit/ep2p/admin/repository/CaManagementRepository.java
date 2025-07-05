/*******************************************************************************
 * Class        :CaManagementRepository
 * Created date :2019/08/26
 * Lasted date  :2019/08/26
 * Author       :HungHT
 * Change log   :2019/08/26:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.repository.JcaCaManagementRepository;

/**
 * CaManagementRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface CaManagementRepository extends JcaCaManagementRepository {

	/**
     * countCaManagementList
     * @param search
     * @return
     * @author HungHT
     */
    int countCaManagementList(@Param("search") JcaCaManagementSearchDto search);

	/**
     * getCaManagementList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<JcaCaManagementDto> getCaManagementList(@Param("search") JcaCaManagementSearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    JcaCaManagementDto findById(@Param("id") Long id);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    JcaCaManagementDto findByProperties1(@Param("properties1") String properties1);

    /**
     * findByAccountId
     * @param accountId
     * @return CaManagementDto
     * @author KhuongTH
     */
    JcaCaManagementDto findByAccountId(@Param("accountId") Long accountId);
    
    /**
     * getDefaultByCompanyId
     * @param companyId
     * @return CaManagementDto
     * @author KhuongTH
     */
    JcaCaManagementDto getDefaultByCompanyId(@Param("companyId") Long companyId);
    
    /**
     * updateDefaultByCompanyId
     * @param companyId
     * @param id
     * @author trieuvd
     */
    @Modifying
    void updateDefaultByCompanyId(@Param("companyId") Long companyId, @Param("id") Long id);
}
/*******************************************************************************
 * Class        ：ContactRepository
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：NhanNV
 * Change log   ：2019/11/12：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.ep2p.dto.JcaContactLangDto;

/**
 * ContactRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface JcaContactRepository {
//	extends DbRepository<JcaContact, Long>{

    /**
     * getByCompanyAndLang
     * @param companyId
     * @param lang
     * @return
     * @author NhanNV
     */
    List<JcaContactLangDto> getByCompanyIdAndLang(@Param("companyId")Long companyId, @Param("lang")String lang);
}

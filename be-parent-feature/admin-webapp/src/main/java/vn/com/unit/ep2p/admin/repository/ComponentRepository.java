/*******************************************************************************
 * Class        :ComponentRepository
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.ep2p.admin.dto.ComponentSearchDto;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.repository.EfoComponentRepository;

/**
 * ComponentRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface ComponentRepository extends EfoComponentRepository {

	/**
     * countComponentList
     * @param search
     * @return
     * @author HungHT
     */
    int countComponentList(@Param("search") ComponentSearchDto search);

	/**
     * getComponentList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<EfoComponentDto> getComponentList(@Param("search") ComponentSearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    EfoComponentDto findById(@Param("id") Long id);

    /**
     * deleteByFormId
     * 
     * @param formId
     * @param user
     * @param date
     * @author HungHT
     */
    @Modifying
    void deleteByFormId(@Param("formId") Long formId, @Param("user") String user, @Param("date") Date date);
    
    /**
     * getListcomponentByBusinessId
     * @param businessId
     * @return List<ComponentDto>
     * @author KhuongTH
     */
    public List<EfoComponentDto> getListcomponentByBusinessId(@Param("businessId") Long businessId);
}
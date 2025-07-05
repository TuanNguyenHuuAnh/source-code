/*******************************************************************************
 * Class        TemplateRepository
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.dto.Popup;
import vn.com.unit.ep2p.dto.PopupDto;
import vn.com.unit.ep2p.dto.PopupSearchDto;
import vn.com.unit.ep2p.dto.PopupSearchReqDto;

import java.util.List;

/**
 * TemplateRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public interface PopupRepository extends DbRepository<Popup, Long> {
    /**
     * getTemplate
     *
     * @param term
     * @return
     * @author phatvt
     */
    List<Select2Dto>getPopup(@Param("term") String term, @Param("fileFormat") String fileFormat);
    
    /**
     * countPopupByCondition
     *
     * @param searchDto
     * @return
     * @author phatvt
     */
    public int countPopupByCondition( @Param("searchDto") PopupSearchReqDto searchDto);
    /**
     * findAllPopupListByCondition
     *
     * @param offset
     * @param sizeOfPage
     * @param searchDto
     * @return
     * @author phatvt
     */
    public List<PopupDto> findAllPopupListByCondition(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("searchDto") PopupSearchReqDto searchDto,@Param("langCode") String langCode);
    
    @Modifying
    public void deletePopup(@Param("PopupId") Long PopupId, @Param("user") String user);
    
    /**
     * getPopupByCompanyId
     * @param term
     * @param fileFormat
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto>getPopupByCompanyId(@Param("term") String term, @Param("fileFormat") String fileFormat, @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging);

    /**
     * @param names
     * @return
     */
    List<Select2Dto> findPopupswithoutWorkflow(@Param("names") List<String> names);
    

    /**
     * @param PopupId
     * @return
     */
    String findNameById(@Param("PopupId") Long PopupId);
    
    /**
     * @return
     */
    List<Select2Dto> findAllSelection();
    
    /**
     * @param code
     * @return
     */
    PopupDto findByCode(@Param("code") String code);
    
    /**
     * 
     * findPopupDtoById
     * @param id
     * @return
     * @author taitt
     */
    List<PopupDto> findPopupDtoById(@Param("id") Long id);
    
}

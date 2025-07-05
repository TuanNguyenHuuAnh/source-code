/*******************************************************************************
 * Class        FormService
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       NhanNV
 * Change log   2019/04/12 01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.entity.JcaItem;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;

/**
 * FormService.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoFormService {

    static final String TABLE_ALIAS_EFO_FORM = "EFO_FORM";
    
    /**
     * Get Form by form id.
     * 
     * @param id
     *            ID of form
     * @return Form type entity
     * @author NhanNV
     */
    EfoForm getFormById(Long id);

    /**
     * getEfoFormByAccountIdForFormsTemplate.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return List for EfoForm object
     * @author taitt
     */
    List<EfoFormDto> getEfoFormDtoByCondition(EfoFormSearchDto efoFormSearchDto, Pageable pageable);

    /**
     * countTotalFormByAccountIdForFormsTemplate.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @return int for total item list
     * @author taitt
     */
    int countEfoFormByCondition(EfoFormSearchDto efoFormSearchDto);

    /**
     * getSelect2DtoWithEfoFormByCompanyId.
     *
     * @param keySearch
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<Select2Dto>}
     * @author taitt
     */
    List<Select2Dto> getSelect2DtoWithEfoFormByCompanyId(String keySearch, Long companyId);

    /**
     * saveEfoForm
     * @param efoForm
     * @return
     * @author taitt
     */
    EfoForm saveEfoForm(EfoForm efoForm);

    /**
     * saveEfoFormDto
     * @param efoFormDto
     * @return
     * @author taitt
     */
    EfoForm saveEfoFormDto(EfoFormDto efoFormDto);

    /**
     * getEfoFormDtoById
     * @param id
     * @return
     * @author taitt
     */
    EfoFormDto getEfoFormDtoById(Long id);


    /**
     * getEfoFormDtoByCompanyIdAndFileName
     * @param companyId
     * @param fileNameList
     * @return
     * @author taitt
     */
    List<EfoFormDto> getEfoFormDtoByCompanyIdAndFileName(Long companyId, List<String> fileNameList);

    /**
     * getEfoFormDtoByCompanyIdAndEfoFormName
     * @param companyId
     * @param efoFormName
     * @param currentFormId
     * @return
     * @author taitt
     */
    EfoFormDto getEfoFormDtoByCompanyIdAndEfoFormName(Long companyId, String efoFormName, Long currentFormId);

    /**
     * createItem
     * @param itemCode
     * @param itemName
     * @param suffix
     * @param businessId
     * @param description
     * @param companyId
     * @param subType
     * @return
     * @throws DetailException
     * @author taitt
     */
    JcaItem createItem(String itemCode, String itemName, String suffix, Long businessId, String description, Long companyId, String subType)
            throws DetailException;

    /**
     * findMaxDisplayOrderByCompanyId
     * @param companyId
     * @return
     * @author taitt
     */
    Long findMaxDisplayOrderByCompanyId(Long companyId);

}

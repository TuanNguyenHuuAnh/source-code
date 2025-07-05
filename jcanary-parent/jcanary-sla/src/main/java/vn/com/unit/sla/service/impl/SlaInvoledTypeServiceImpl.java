/*******************************************************************************
 * Class        ：SlaInvoledTypeServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.sla.dto.SlaInvoledTypeDto;
import vn.com.unit.sla.repository.SlaInvoledTypeRepository;
import vn.com.unit.sla.service.SlaInvoledTypeService;

/**
 * <p>
 * SlaInvoledTypeServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaInvoledTypeServiceImpl implements SlaInvoledTypeService{
    
    @Autowired
    private SlaInvoledTypeRepository slaInvoledTypeRepository;

    @Override
    public List<Select2Dto> getSelect2DtoListByLang(String lang) {
        List<Select2Dto> resultList = new ArrayList<>();
        List<SlaInvoledTypeDto> involedTypeDtoList = slaInvoledTypeRepository.getSlaInvoledTypeDtoListByLang(lang);
        if(CommonCollectionUtil.isNotEmpty(involedTypeDtoList)) {
            resultList = involedTypeDtoList.stream().map(f -> new Select2Dto(f.getCode(), f.getName(), f.getName())).collect(Collectors.toList());
        }
        return resultList;
    }

}

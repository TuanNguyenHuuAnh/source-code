/*******************************************************************************
 * Class        ：EfoComponentServiceImpl
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;
import vn.com.unit.ep2p.core.efo.repository.EfoComponentRepository;
import vn.com.unit.ep2p.core.efo.service.EfoComponentService;

/**
 * EfoComponentServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoComponentServiceImpl implements EfoComponentService{

    @Autowired
    private EfoComponentRepository efoComponentRepository;
    
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public int countComponentDtoByCondition(EfoComponentSearchDto efoComponentSearchDto) {
        return efoComponentRepository.countEfoComponentDtoByCondition(efoComponentSearchDto);
    }

    @Override    
    public List<EfoComponentDto> getComponentDtoByCondition(EfoComponentSearchDto efoComponentSearchDto, Pageable pagable) {
        return efoComponentRepository.getEfoComponentDtoByCondition(efoComponentSearchDto, pagable).getContent();
    }

    @Override    
    public EfoComponent getComponentById(Long id) {
        return efoComponentRepository.findOne(id);
    }

    @Override    
    public EfoComponent saveEfoComponent(EfoComponent efoComponent) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = efoComponent.getId();
        if(null != id) {
            EfoComponent oldEfoComponent =  efoComponentRepository.findOne(id);
            if (null !=oldEfoComponent) {
                efoComponent.setCreatedDate(oldEfoComponent.getCreatedDate());
                efoComponent.setCreatedId(oldEfoComponent.getCreatedId());
                efoComponent.setUpdatedDate(sysDate);
                efoComponent.setUpdatedId(userId);
                efoComponentRepository.update(efoComponent);
            }
            
        }else {
            efoComponent.setCreatedDate(sysDate);
            efoComponent.setCreatedId(userId);
            efoComponent.setUpdatedDate(sysDate);
            efoComponent.setUpdatedId(userId);
            efoComponentRepository.create(efoComponent);
        }
        return efoComponent;
    }
    
    @Override    
    public EfoComponent saveEfoComponentDto(EfoComponentDto efoComponentDto) {
        EfoComponent efoComponent = objectMapper.convertValue(efoComponentDto, EfoComponent.class);
        efoComponent.setId(efoComponentDto.getComponentId());
        
        // save data
        efoComponent = this.saveEfoComponent(efoComponent);
        
        // update id
        efoComponentDto.setComponentId(efoComponent.getId());
        return efoComponent;
    }

    @Override    
    public EfoComponentDto getEfoComponentDtoById(Long id) {
        return efoComponentRepository.getEfoComponentDtoById(id);
    }
    
    
    @Override    
    public void saveEfoComponentList(List<EfoComponent> componentList,String formName) throws DetailException{
        try {
            for (EfoComponent component : componentList) {
                if (null != component.getId()) {
                    efoComponentRepository.update(component);
                }else {
                    efoComponentRepository.create(component);
                }
            }
        }catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301807_CORE_REGISTER_FORM_SAVE_COMPONENT, new String[] { formName },true);    
        }

    }
}

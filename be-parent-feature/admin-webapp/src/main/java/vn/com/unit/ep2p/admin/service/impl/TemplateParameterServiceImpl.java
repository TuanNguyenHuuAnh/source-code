/*******************************************************************************
 * Class        ：TemplateParameterServiceImpl
 * Created date ：2020/02/03
 * Lasted date  ：2020/02/03
 * Author       ：trieuvd
 * Change log   ：2020/02/03：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.repository.TemplateParameterRepository;
import vn.com.unit.ep2p.admin.service.TemplateParameterService;
import vn.com.unit.ep2p.dto.TemplateParameterDto;

/**
 * TemplateParameterServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
@Service
@Transactional
public class TemplateParameterServiceImpl implements TemplateParameterService{

    @Autowired
    TemplateParameterRepository templateParameterRepository;
    
    @Override
    public List<String> getListParameter() {
        List<String> result = new ArrayList<String>();
        List<TemplateParameterDto> listParameterDto = templateParameterRepository.findByCompany(null);
        if(!CollectionUtils.isEmpty(listParameterDto)) {
            result = listParameterDto.stream().map(m -> m.getParameterFullName()).collect(Collectors.toList());
        }
        return result;
    }
    
    @Override
    public List<String> getListParameterByCompanyId(Long companyId) {
        List<String> result = new ArrayList<String>();
        List<TemplateParameterDto> listParameterDto = templateParameterRepository.findByCompany(companyId);
        if(!CollectionUtils.isEmpty(listParameterDto)) {
            result = listParameterDto.stream().map(m -> m.getParameterFullName()).collect(Collectors.toList());
        }
        return result;
    }

}

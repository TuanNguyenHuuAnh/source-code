
package vn.com.unit.ep2p.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganizationPath;
import vn.com.unit.core.service.impl.JcaOrganizationPathServiceImpl;
import vn.com.unit.ep2p.admin.service.OrganizationPathService;

@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrganizationPathServiceImpl extends JcaOrganizationPathServiceImpl implements OrganizationPathService {

    @Autowired
    private ObjectMapper objectMapper;
    
    public JcaOrganizationPathDto save(JcaOrganizationPathDto jcaOrganizationPathDto) {
        JcaOrganizationPath jcaOrganizationPath = saveJcaOrganizationPathDto(jcaOrganizationPathDto);
        return objectMapper.convertValue(jcaOrganizationPath, JcaOrganizationPathDto.class);
    }

}

/*******************************************************************************
 * Class        :JcaOrganizationPathServiceImpl
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganizationPath;
import vn.com.unit.core.repository.JcaOrganizationPathRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaOrganizationPathService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaOrganizationPathServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaOrganizationPathServiceImpl implements JcaOrganizationPathService {

    @Autowired
    private JcaOrganizationPathRepository jcaOrganizationPathRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JCommonService commonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaOrganizationPath saveJcaOrganizationPath(JcaOrganizationPath jcaOrganizationPath) {
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

        jcaOrganizationPath.setCreatedDate(systemDate);
        jcaOrganizationPath.setCreatedId(userId);
        jcaOrganizationPathRepository.create(jcaOrganizationPath);
        return jcaOrganizationPath;
    }

    @Override
    public JcaOrganizationPathDto getJcaOrganizationPathDtoByDescendantId(Long descendantId) {
        return jcaOrganizationPathRepository.getJcaOrganizationPathDtoByDescendantId(descendantId);
    }

    @Override
    public JcaOrganizationPath saveJcaOrganizationPathDto(JcaOrganizationPathDto jcaOrganizationPathDto) {
        JcaOrganizationPath jcaOrganizationPath = objectMapper.convertValue(jcaOrganizationPathDto, JcaOrganizationPath.class);
        this.saveJcaOrganizationPath(jcaOrganizationPath);
        return jcaOrganizationPath;
    }

    @Override
    public JcaOrganizationPathDto getJcaOrganizationPathDtoById(Long id) {
        return jcaOrganizationPathRepository.getJcaOrganizationPathDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrganizationPathByDescendantId(Long descendantId) {
        jcaOrganizationPathRepository.deleteOrganizationPathByDescendantId(descendantId);
    }

    @Override
    public DbRepository<JcaOrganizationPath, Long> initRepo() {
        return jcaOrganizationPathRepository;
    }

}

/*******************************************************************************
 * Class        :JcaMenuPathServiceImpl
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :SonND
 * Change log   :2020/12/10 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.entity.JcaMenuPath;
import vn.com.unit.core.repository.JcaMenuPathRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaMenuPathService;

/**
 * JcaMenuPathServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaMenuPathServiceImpl implements JcaMenuPathService {


    @Autowired
    JcaMenuPathRepository jcaMenuPathRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaMenuPath saveJcaMenuPath(JcaMenuPath jcaMenuPath) {
        Date systemDate = CommonDateUtil.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        JcaMenuPath oldJcaMenuPath = jcaMenuPathRepository.findOneByPK(jcaMenuPath.getAncestorId(), jcaMenuPath.getDescendantId());
        if (null != oldJcaMenuPath) {
            jcaMenuPath.setCreatedDate(oldJcaMenuPath.getCreatedDate());
            jcaMenuPath.setCreatedId(oldJcaMenuPath.getCreatedId());
            jcaMenuPathRepository.update(jcaMenuPath);
        } else {
            jcaMenuPath.setCreatedDate(systemDate);
            jcaMenuPath.setCreatedId(userId);
            jcaMenuPathRepository.create(jcaMenuPath);
        }
        return jcaMenuPath;
    }

    @Override
    public JcaMenuPath getJcaMenuPathById(Long id) {
        return jcaMenuPathRepository.findOne(id);
    }

    @Override
    public JcaMenuPath getJcaMenuPathByDescendantId(Long descendantId) {
        return jcaMenuPathRepository.getJcaMenuPathByDescendantId(descendantId);
    }

    @Override
    public JcaMenuPath saveJcaMenuPathDto(JcaMenuPathDto jcaMenuPathDto) {
        JcaMenuPath jcaMenuPath = objectMapper.convertValue(jcaMenuPathDto, JcaMenuPath.class);
        this.saveJcaMenuPath(jcaMenuPath);
        return jcaMenuPath;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuPathByDescendantId(Long descendantId) {
        jcaMenuPathRepository.deleteMenuPathByDescendantId(descendantId);
    }

    @Override
    public List<JcaMenuPathDto> getJcaMenuPathDtoListDefault() {
        return jcaMenuPathRepository.getJcaMenuPathDtoListDefault();
    }

}

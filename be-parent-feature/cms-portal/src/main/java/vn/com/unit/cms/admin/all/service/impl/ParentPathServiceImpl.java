/*******************************************************************************
 * Class        :JcaMenuPathServiceImpl
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :TaiTM
 * Change log   :2020/12/10 01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.admin.all.dto.ParentPathDto;
import vn.com.unit.cms.admin.all.entity.ParentPath;
import vn.com.unit.cms.admin.all.repository.ParentPathRepository;
import vn.com.unit.cms.admin.all.service.ParentPathService;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;

/**
 * JcaMenuPathServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ParentPathServiceImpl implements ParentPathService {

    @Autowired
    public ParentPathRepository parentPathRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParentPath saveParentPath(ParentPath jcaMenuPath) {
        Date systemDate = CommonDateUtil.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        ParentPath oldJcaMenuPath = parentPathRepository.findOneByPK(jcaMenuPath.getAncestorId(),
                jcaMenuPath.getDescendantId(), jcaMenuPath.getType());
        if (null != oldJcaMenuPath) {
            jcaMenuPath.setCreatedDate(oldJcaMenuPath.getCreatedDate());
            jcaMenuPath.setCreatedId(oldJcaMenuPath.getCreatedId());
            parentPathRepository.update(jcaMenuPath);
        } else {
            jcaMenuPath.setCreatedDate(systemDate);
            jcaMenuPath.setCreatedId(userId);
            parentPathRepository.create(jcaMenuPath);
        }
        return jcaMenuPath;
    }

    @Override
    public ParentPath getJcaMenuPathByDescendantId(Long descendantId, String type) {
        return parentPathRepository.getJcaMenuPathByDescendantId(descendantId, type);
    }

    @Override
    public ParentPath saveParentPathDto(ParentPathDto jcaMenuPathDto) {
        ParentPath jcaMenuPath = objectMapper.convertValue(jcaMenuPathDto, ParentPath.class);
        this.saveParentPath(jcaMenuPath);
        return jcaMenuPath;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuPathByDescendantId(Long descendantId, String type) {
        parentPathRepository.deleteMenuPathByDescendantId(descendantId, type);
    }
}

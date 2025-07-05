/*******************************************************************************
 * Class        FormServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       NhanNV
 * Change log   2016/06/21 01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.efo.dto.EfoFormAuthorityDto;
import vn.com.unit.ep2p.core.efo.entity.EfoFormAuthority;
import vn.com.unit.ep2p.core.efo.repository.EfoFormAuthorityRepository;
import vn.com.unit.ep2p.core.efo.service.EfoFormAuthorityService;

/**
 * FormServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoFormAuthorityServiceImpl implements EfoFormAuthorityService {

    @Autowired
    private EfoFormAuthorityRepository efoFormAuthotiryRepository;

    @Override
    public List<EfoFormAuthorityDto> getEfoFormAuthorityDtosByBusinessIdAndRoleId(Long businessId, Long roleId) {
        return efoFormAuthotiryRepository.getEfoFormAuthorityDtosByBusinessIdAndRoleId(businessId, roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEfoFormAuthorityDtosByRoleId(List<EfoFormAuthorityDto> efoFormAuthorityDtos, Long businessId, Long roleId) {
        if (CommonCollectionUtil.isEmpty(efoFormAuthorityDtos) || Objects.isNull(businessId) || Objects.isNull(roleId)) {
            return false;
        }

        // delete old data
        efoFormAuthotiryRepository.deleteByBusinessIdAndRoleId(businessId, roleId);

        Long user = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        for (EfoFormAuthorityDto efoFormAuthorityDto : efoFormAuthorityDtos) {
            if (!efoFormAuthorityDto.isAccessFlag())
                continue;
            EfoFormAuthority efoFormAuthority = new EfoFormAuthority();
            efoFormAuthority.setFormId(efoFormAuthorityDto.getFormId());
            efoFormAuthority.setRoleId(roleId);
            efoFormAuthority.setCreatedId(user);
            efoFormAuthority.setCreatedDate(sysDate);

            efoFormAuthotiryRepository.create(efoFormAuthority);
        }
        
        return true;
    }

}

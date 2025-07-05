
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.InterestRateTitleDto;
import vn.com.unit.cms.admin.all.entity.InterestRateTitle;
import vn.com.unit.cms.admin.all.repository.InterestRateTitleRepository;
import vn.com.unit.cms.admin.all.service.InterestRateTitleService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
//import vn.com.unit.jcanary.utils.NullAwareBeanUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InterestRateTitleServiceImpl implements InterestRateTitleService {

    @Autowired
    InterestRateTitleRepository interestRateTitleRepository;

    @Override
    public List<InterestRateTitleDto> findInterestRateTitleByType(String type, Long customerTypeId) {
        return interestRateTitleRepository.findInterestRateTitleByType(type, customerTypeId);
    }

    @Override
    public List<InterestRateTitleDto> findInterestRateTitleByDto(InterestRateTitleDto interestRateTitleDto) {
        return interestRateTitleRepository.findInterestRateTitleByDto(interestRateTitleDto);
    }

    @Override
    public void deleteByListId(List<Long> lstId) {
        String userName = UserProfileUtils.getUserNameLogin();
        interestRateTitleRepository.deleteByListId(new Date(), userName, lstId);
    }

    @Override
    public void saveInterestRateTitle(InterestRateTitleDto interestRateTitleDto) throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(interestRateTitleDto.getDatas())) {
                List<InterestRateTitleDto> datas = new ArrayList<>(interestRateTitleDto.getDatas());
                for (InterestRateTitleDto data : datas) {
                    data.setInterestRateType(interestRateTitleDto.getInterestRateType());

                    InterestRateTitle entity = new InterestRateTitle();
                    if (data.getId() == null) {
                        entity.setCustomerTypeId(interestRateTitleDto.getCustomerTypeId());
                        entity.setCreateBy(UserProfileUtils.getUserNameLogin());
                        entity.setCreateDate(new Date());
                    } else {
                        entity = interestRateTitleRepository.findOne(data.getId());
                        entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
                        entity.setUpdateDate(new Date());
                    }

                    NullAwareBeanUtils.copyPropertiesWONull(data, entity);
                    interestRateTitleRepository.save(entity);
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }
}

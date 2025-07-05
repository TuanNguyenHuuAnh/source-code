/*******************************************************************************
 * Class        ：JpmSlaInfoServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;
import vn.com.unit.workflow.dto.JpmSlaInfoSearchDto;
import vn.com.unit.workflow.entity.JpmSlaInfo;
import vn.com.unit.workflow.repository.JpmSlaInfoRepository;
import vn.com.unit.workflow.service.JpmSlaConfigService;
import vn.com.unit.workflow.service.JpmSlaInfoService;

/**
 * <p>
 * JpmSlaInfoServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmSlaInfoServiceImpl implements JpmSlaInfoService {

    @Autowired
    private JpmSlaInfoRepository jpmSlaInfoRepository;

    @Autowired
    private JpmSlaConfigService jpmSlaConfigService;
    
    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DbRepository<JpmSlaInfo, Long> initRepo() {
        return jpmSlaInfoRepository;
    }

    @Override
    public JpmSlaInfoDto getJpmSlaInfoDtoById(Long id) {
        return jpmSlaInfoRepository.getJpmSlaInfoDtoById(id);
    }

    @Override
    @Transactional
    public JpmSlaInfo saveJpmSlaInfo(JpmSlaInfo jpmSlaInfo) {
        Long id = jpmSlaInfo.getId();
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = commonService.getSystemDate();
        if (null != id) {
            jpmSlaInfo.setUpdatedId(userId);
            jpmSlaInfo.setUpdatedDate(sysDate);
            JpmSlaInfo jpmSlaInfoOld = jpmSlaInfoRepository.findOne(id);
            CommonObjectUtil.copyPropertiesNonNull(jpmSlaInfo, jpmSlaInfoOld);
            jpmSlaInfoRepository.update(jpmSlaInfoOld);
        } else {
            jpmSlaInfo.setCreatedId(userId);
            jpmSlaInfo.setCreatedDate(sysDate);
            jpmSlaInfo.setUpdatedId(userId);
            jpmSlaInfo.setUpdatedDate(sysDate);
            jpmSlaInfoRepository.create(jpmSlaInfo);
        }
        return jpmSlaInfo;
    }

    @Override
    @Transactional
    public JpmSlaInfoDto saveJpmSlaInfoDto(JpmSlaInfoDto jpmSlaInfoDto) {
        JpmSlaInfo jpmSlaInfo = objectMapper.convertValue(jpmSlaInfoDto, JpmSlaInfo.class);
        jpmSlaInfoDto.setId(this.saveJpmSlaInfo(jpmSlaInfo).getId());
        return jpmSlaInfoDto;
    }

    @Override
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListBySearchDto(JpmSlaInfoSearchDto searchDto) {
        return jpmSlaInfoRepository.getJpmSlaInfoDtoListBySearchDto(searchDto);
    }

    @Override
    public int countBySearchCondition(JpmSlaInfoSearchDto searchDto) {
        return jpmSlaInfoRepository.countBySearchCondition(searchDto);
    }

    @Override
    public List<JpmSlaInfoDto> getJpmSlaInfoDtoListByCondition(JpmSlaInfoSearchDto searchDto, Pageable pageable) {
        return jpmSlaInfoRepository.getJpmSlaInfoDtoListByCondition(searchDto, pageable);
    }

    @Override
    public void cloneJpmSla(Long oldProcessDeployId, Long processDeployId) throws DetailException {
        JpmSlaInfoDto jpmSlaInfoDto = jpmSlaInfoRepository.getJpmSlaInfoDtoByProcessDeployId(oldProcessDeployId);
        if (null != jpmSlaInfoDto) {
            Long oldSlaInfoId = jpmSlaInfoDto.getId();
            jpmSlaInfoDto.setId(null);
            jpmSlaInfoDto.setProcessDeployId(processDeployId);
            Long slaInfoId = this.saveJpmSlaInfoDto(jpmSlaInfoDto).getId();
            jpmSlaConfigService.cloneJpmSlaConfig(oldSlaInfoId, slaInfoId);
        }
    }

}

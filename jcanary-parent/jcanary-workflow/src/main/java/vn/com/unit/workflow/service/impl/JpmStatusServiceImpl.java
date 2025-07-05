/*******************************************************************************
* Class        JpmStatusServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.repository.JpmStatusRepository;
import vn.com.unit.workflow.service.JpmHiStatusService;
import vn.com.unit.workflow.service.JpmStatusLangService;
import vn.com.unit.workflow.service.JpmStatusService;

/**
 * JpmStatusServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusServiceImpl implements JpmStatusService {

    @Autowired
    private JpmStatusRepository jpmStatusRepository;

    @Autowired
    private JpmStatusLangService jpmStatusLangService;
    
    @Autowired
    private JpmHiStatusService jpmHiStatusService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStatusDto getJpmStatusDtoById(Long id) {
        JpmStatusDto jpmStatusDto = new JpmStatusDto();
        if (null != id) {
            JpmStatus jpmStatus = jpmStatusRepository.findOne(id);
            if (null != jpmStatus && 0 == jpmStatus.getDeletedId()) {
                jpmStatusDto = objectMapper.convertValue(jpmStatus, JpmStatusDto.class);
                jpmStatusDto.setStatusId(id);
                
                List<JpmStatusLangDto> statusLangDtos = jpmStatusLangService.getStatusLangDtosByStatusId(id);
                jpmStatusDto.setStatusLangs(statusLangDtos);
            }
        }
        return jpmStatusDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmStatus jpmStatus = jpmStatusRepository.findOne(id);
            if (null != jpmStatus) {
                jpmStatus.setDeletedId(userId);
                jpmStatus.setDeletedDate(sysDate);
                jpmStatusRepository.update(jpmStatus);
                res = true;
            }
            jpmStatusLangService.deleteStatusLangByStatusId(id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStatus saveJpmStatus(JpmStatus jpmStatus) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmStatus.getId();
        if (null != id) {
            JpmStatus oldJpmStatus = jpmStatusRepository.findOne(id);
            if (null != oldJpmStatus) {
                jpmStatus.setCreatedId(oldJpmStatus.getCreatedId());
                jpmStatus.setCreatedDate(oldJpmStatus.getCreatedDate());
                jpmStatus.setUpdatedId(userId);
                jpmStatus.setUpdatedDate(sysDate);
                jpmStatusRepository.update(jpmStatus);
            }
        } else {
            jpmStatus.setCreatedId(userId);
            jpmStatus.setCreatedDate(sysDate);
            jpmStatus.setUpdatedId(userId);
            jpmStatus.setUpdatedDate(sysDate);
            jpmStatusRepository.create(jpmStatus);
        }
        
        // save history
        jpmHiStatusService.saveJpmHiStatus(jpmStatus);
        
        return jpmStatus;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmStatus saveJpmStatusDto(JpmStatusDto jpmStatusDto) {
        JpmStatus jpmStatus = objectMapper.convertValue(jpmStatusDto, JpmStatus.class);
        if(Objects.nonNull(jpmStatusDto.getStatusId())) {
            jpmStatus.setId(jpmStatusDto.getStatusId());
        }

        this.saveJpmStatus(jpmStatus);
        
        Long statusId = jpmStatus.getId();        
        jpmStatusDto.setStatusId(statusId);
        if(DtsCollectionUtil.isNotEmpty(jpmStatusDto.getStatusLangs())){
            jpmStatusLangService.saveStatusLangDtosByStatusId(jpmStatusDto.getStatusLangs(), statusId);

        }
        return jpmStatus;
    }

    @Override
    public List<JpmStatusDto> getStatusDtosByProcessId(Long processId) {
        List<JpmStatusDto> statusDtos = jpmStatusRepository.getStatusDtosByProcessId(processId);
        if (CommonCollectionUtil.isNotEmpty(statusDtos)) {
            List<JpmStatusLangDto> statusLangDtos = jpmStatusLangService.getStatusLangDtosByProcessId(processId);
            if (CommonCollectionUtil.isNotEmpty(statusLangDtos)) {
                Map<Long, List<JpmStatusLangDto>> statusLangDtosMap = statusLangDtos.stream()
                        .collect(Collectors.groupingBy(JpmStatusLangDto::getStatusId));
                statusDtos.forEach(statusDto -> statusDto.setStatusLangs(statusLangDtosMap.get(statusDto.getStatusId())));
            }
        }
        return statusDtos;
    }
    
    @Override
    public List<JpmStatusDto> getStatusDtosByBusinessCode(String businessCode) {
        List<JpmStatusDto> statusDtos = jpmStatusRepository.findStatusDtosByBusinessCode(businessCode);
        if (CommonCollectionUtil.isNotEmpty(statusDtos)) {
            List<JpmStatusLangDto> statusLangDtos = jpmStatusLangService.getStatusLangDtosByBusinessCode(businessCode);
            if (CommonCollectionUtil.isNotEmpty(statusLangDtos)) {
                Map<Long, List<JpmStatusLangDto>> statusLangDtosMap = statusLangDtos.stream()
                        .collect(Collectors.groupingBy(JpmStatusLangDto::getStatusId));
                statusDtos
                        .forEach(statusDto -> statusDto.setStatusLangs(statusLangDtosMap.get(statusDto.getStatusId())));
            }
        }
        return statusDtos;
    }

    @Override
    public JpmStatusDto getStatusDtoByStatusId(Long statusId) {
        return jpmStatusRepository.getStatusDtoByStatusId(statusId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, Long> saveStatusDtosByProcessId(List<JpmStatusDto> statusDtos, Long processId) {
        Map<Long, Long> resMap = new HashMap<>();
        List<JpmStatusDto> currentStatusDtos = this.getStatusDtosByProcessId(processId);
        Map<String, JpmStatusDto> statusMap = CommonCollectionUtil.isEmpty(currentStatusDtos) ? new HashMap<>()
                : currentStatusDtos.stream().collect(Collectors.toMap(JpmStatusDto::getStatusCode, status -> status));

        for (JpmStatusDto statusDto : statusDtos) {
            Long oldStatusId = statusDto.getStatusId();
            String statusCode = statusDto.getStatusCode();
            JpmStatusDto currentStatusDto = statusMap.remove(statusCode);
            if (Objects.isNull(currentStatusDto)) {
                statusDto.setStatusId(null);
            } else {
                statusDto.setStatusId(currentStatusDto.getStatusId());
            }
            statusDto.setProcessId(processId);
            //save
            this.saveJpmStatusDto(statusDto);

            if (Objects.nonNull(oldStatusId)) {
                resMap.put(oldStatusId, statusDto.getStatusId());
            }
        }
        
        //delete
        if (CommonMapUtil.isNotEmpty(statusMap)) {
            for(Map.Entry<String, JpmStatusDto> entry : statusMap.entrySet()) {
                Long id = entry.getValue().getStatusId();
                this.deleteById(id);
            }
        }
        return resMap;
       
    }

}
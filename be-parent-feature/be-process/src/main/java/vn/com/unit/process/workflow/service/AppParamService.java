package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.process.workflow.dto.AppParamDto;
import vn.com.unit.workflow.entity.JpmParam;

public interface AppParamService {
    AppParamDto[] getListJpmParamByProcessId(Long jpmProcessId);
    AppParamDto getById(Long id);
    JpmParam saveJpmParam(AppParamDto paramDto) throws Exception;
    void deleteJpmParam(Long paramId);
    boolean validateParamWithNameAndProcessId(Long paramId, String fieldName, Long processId);
    List<JpmParam> saveJpmParamByProcessId(List<AppParamDto> paramDtoList, Long processId);
}

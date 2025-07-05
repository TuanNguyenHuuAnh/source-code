package vn.com.unit.process.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.workflow.service.AppProcessDmnDeployService;
import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.service.JpmProcessDmnDeployService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppProcessDmnDeployServiceImpl implements AppProcessDmnDeployService {

    @Autowired
    private JpmProcessDmnDeployService jpmProcessDmnDeployService;

    @Override
    public List<JpmProcessDmnDeployDto> getJpmProcessDmnDeployDtosByProcessDeployId(Long processDeployId) {
        
        return jpmProcessDmnDeployService.getJpmProcessDmnDeployDtosByProcessDeployId(processDeployId);
    }
}

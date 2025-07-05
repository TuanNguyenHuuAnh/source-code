package vn.com.unit.process.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.process.admin.repository.AppStepLangRepository;
import vn.com.unit.process.admin.service.AppStepLangService;
import vn.com.unit.workflow.entity.JpmStepLang;

/**
 * Created by QuangND on 8/1/2019.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStepLangServiceImpl implements AppStepLangService {

    @Autowired
    AppStepLangRepository appStepLangRepository;

    @Override
    public List<JpmStepLang> getListJpmStepLang(Long stepId, List<String> listCodeLanguage) {
        return appStepLangRepository.getListJpmStepLangByStepIdAndListLang(stepId,listCodeLanguage);
    }

    @Override
    public List<JpmStepLang> getListJpmStepLangByProcessId(Long jpmProcessId) {
        return appStepLangRepository.getListJpmStepLangByProcessId(jpmProcessId);
    }
}

package vn.com.unit.process.admin.service;

import java.util.List;

import vn.com.unit.workflow.entity.JpmStepLang;

public interface AppStepLangService {

    public List<JpmStepLang> getListJpmStepLang(Long stepId, List<String> listCodeLanguage);

    public List<JpmStepLang> getListJpmStepLangByProcessId(Long jpmProcessId);
}

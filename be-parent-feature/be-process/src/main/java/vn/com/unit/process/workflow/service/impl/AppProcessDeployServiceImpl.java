/*******************************************************************************
 * Class        AppProcessDeployServiceImpl
 * Created date 2019/07/04
 * Lasted date  2019/07/04
 * Author       KhuongTH
 * Change log   2019/07/04 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
//import vn.com.unit.ep2p.admin.repository.AppProcessDeployLangRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDeploySearchDto;
import vn.com.unit.process.workflow.enumdef.JpmProcessDeploySearchEnum;
import vn.com.unit.process.workflow.repository.AppProcessDeployLangRepository;
import vn.com.unit.process.workflow.repository.AppProcessDeployRepository;
import vn.com.unit.process.workflow.service.AppProcessDeployService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeploySearchDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.LanguageMapDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.entity.JpmProcessDeployLang;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmProcessService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppProcessDeployServiceImpl implements AppProcessDeployService, AbstractCommonService {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private AppProcessDeployRepository appProcessDeployRepository;

    @Autowired
    private AppProcessDeployLangRepository appProcessDeployLangRepository;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;

//    private static final String DBTYPE = "DBTYPE";
    
    private static final Logger logger = LoggerFactory.getLogger(AppProcessDeployServiceImpl.class);

    @Override
    public PageWrapper<AppProcessDeployDto> search(int page, int pageSize, AppProcessDeploySearchDto searchDto) throws DetailException {
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<AppProcessDeployDto> pageWrapper = new PageWrapper<>(page, sizeOfPage, listPageSize);
        if (null == searchDto) {
            searchDto = new AppProcessDeploySearchDto();
        }

        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);

        Date fromDate = searchDto.getFromDate();
        if (null != fromDate) {
            fromDate = CommonDateUtil.removeTime(fromDate);
            searchDto.setFromDate(fromDate);
        }
        Date toDate = searchDto.getToDate();
        if (null != toDate) {
            toDate = CommonDateUtil.setMaxTime(toDate);
            searchDto.setToDate(toDate);
        }

        Pageable pageable = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JpmBusiness.class,
                JpmProcessService.TABLE_ALIAS_JPM_PROCESS);

        JpmProcessDeploySearchDto searchReq = this.buildJpmProcessDeploySearchDto(searchDto);
        int count = jpmProcessDeployService.countBySearchCondition(searchReq);
        List<AppProcessDeployDto> result = new ArrayList<>();
        if (count > 0) {
            List<JpmProcessDeployDto> processDeployDtos = jpmProcessDeployService.getProcessDeployDtosByCondition(searchReq, pageable);
            result = processDeployDtos.stream().map(this::convertJpmProcessDtoToAppProcessDto).collect(Collectors.toList());
        }

        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private JpmProcessDeploySearchDto buildJpmProcessDeploySearchDto(AppProcessDeploySearchDto commonSearch) {
        JpmProcessDeploySearchDto processSearchDto = new JpmProcessDeploySearchDto();

        String keySearch = commonSearch.getFieldSearch();
        Long companyId = commonSearch.getCompanyId();
        Long businessId = commonSearch.getBusinessId();
        Long processId = commonSearch.getProcessId();
        Date fromDate = commonSearch.getFromDate();
        Date toDate = commonSearch.getToDate();

        processSearchDto.setCompanyId(companyId);
        processSearchDto.setBusinessId(businessId);
        processSearchDto.setKeySearch(keySearch);
        processSearchDto.setProcessId(processId);
        processSearchDto.setFromDate(Objects.nonNull(fromDate) ? CommonDateUtil.removeTime(fromDate) : null);
        processSearchDto.setToDate(Objects.nonNull(toDate) ? CommonDateUtil.setMaxTime(toDate) : null);
        if (CommonCollectionUtil.isNotEmpty(commonSearch.getFieldValues())) {
            for (String field : commonSearch.getFieldValues()) {
                switch (JpmProcessDeploySearchEnum.valueOf(field)) {
                case PROCESS_CODE:
                    processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
                    break;
                case PROCESS_NAME:
                    processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                default:
                    processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
                    processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
                    break;
                }
            }
        } else {
            processSearchDto.setProcessCode(CommonStringUtil.trimToNull(keySearch));
            processSearchDto.setProcessName(CommonStringUtil.trimToNull(keySearch));
        }
        return processSearchDto;
    }

    @Override
    public AppProcessDeployDto getJpmProcessDeployDtoById(Long id) {
        JpmProcessDeployDto processDeployDto = jpmProcessDeployService.getJpmProcessDeployDtoById(id);
        return this.convertJpmProcessDtoToAppProcessDto(processDeployDto);
    }

    @Override
    public AppProcessDeployDto getJpmProcessDeployByCodeAndCompanyId(String code, Long companyId) {
        return appProcessDeployRepository.findOneJpmProcessDeployByCodeAndCompanyId(code, companyId);
    }

    @Override
    public boolean deletedById(Long id) {
        boolean checkDelete = true;

        int count = appProcessDeployRepository.countCheckDeleteJpmProcessDeployById(id);
        if (count > 0) {
            checkDelete = false;
        } else {
            checkDelete = jpmProcessDeployService.deleteById(id);
        }

        return checkDelete;
    }

    @Override
    public AppProcessDeployDto getByFormId(Long businessId, Long processId) {
        return appProcessDeployRepository.getJpmProcessByBusinessId(businessId, processId);
    }

    @Override
    public List<AppProcessDeployDto> findJpmProcessDtoByBusinessId(Long businessId, String lang) {
        return appProcessDeployRepository.findJpmProcessDtoListByBusinessId(businessId, lang);
    }

    @Override
    public List<Select2Dto> getJpmProcessDtoTypeSelect2DtoByBusinessId(Long businessId, String lang) {
        List<Select2Dto> result = new ArrayList<>();

        try {
            List<AppProcessDeployDto> lstProcess = appProcessDeployRepository.findJpmProcessDtoListByBusinessId(businessId, lang);

            if (CommonCollectionUtil.isNotEmpty(lstProcess)) {
                for (AppProcessDeployDto appProcessDeployDto : lstProcess) {
                    Select2Dto select2Dto = new Select2Dto();

                    String id = String.valueOf(appProcessDeployDto.getId());
                    select2Dto.setId(id);

                    String name = appProcessDeployDto.getName();
                    Long majorVersion = appProcessDeployDto.getMajorVersion();
                    Long minorVersion = appProcessDeployDto.getMinorVersion();

                    String text = name.concat(ConstantCore.HYPHEN).concat(ConstantCore.VERSION).concat(String.valueOf(majorVersion))
                            .concat(ConstantCore.DOT).concat(String.valueOf(minorVersion));
                    select2Dto.setText(text);

                    select2Dto.setName(name);

                    result.add(select2Dto);
                }
            }
        } catch (Exception ex) {
            logger.error("Error:Exception: ", ex);
        }
        return result;
    }

    @Override
    public AppProcessDeployDto getJpmProcessDeployForDocument(Long companyId, Long businessId, Long processDeployId) {
        AppProcessDeployDto result = null;

        if (processDeployId != null) {
            result = appProcessDeployRepository.findOneJpmProcessDeployById(processDeployId);
        } else {
            Date sysDate = CommonDateUtil.getSystemDateTime();
            result = appProcessDeployRepository.findJpmProcessDeployByCompanyIdAndBusinessIdAndMaxEffectiveDate(companyId, businessId,
                    sysDate);
        }
        return result;
    }

    @Override
    public List<Select2Dto> getJpmProcessDeployListByFormId(String keySearch, Long formId, boolean isPaging, String lang) {
        List<Select2Dto> lst = new ArrayList<>();
        if (formId != null) {
            List<AppProcessDeployDto> jpmProcessDeployDtoList = appProcessDeployRepository
                    .findJpmProcessDeployListByCompanyIdAndFormId(keySearch, formId, isPaging, lang);

            if (jpmProcessDeployDtoList != null && !jpmProcessDeployDtoList.isEmpty()) {
                for (AppProcessDeployDto appProcessDeployDto : jpmProcessDeployDtoList) {
                    String id = String.valueOf(appProcessDeployDto.getId());

                    String name = appProcessDeployDto.getName();
                    Long majorVersion = appProcessDeployDto.getMajorVersion();
                    Long minorVersion = appProcessDeployDto.getMinorVersion();

                    String text = name.concat(ConstantCore.HYPHEN).concat(ConstantCore.VERSION).concat(String.valueOf(majorVersion))
                            .concat(ConstantCore.DOT).concat(String.valueOf(minorVersion));

                    Select2Dto objectSelect = new Select2Dto(id, text, name);
                    lst.add(objectSelect);
                }
            }
        }
        return lst;
    }

    @Override
    public List<AppProcessDeployDto> findJpmProcessDtoListByCompanyId(Long companyId) throws Exception {
        return appProcessDeployRepository.findJpmProcessDtoListByCompanyId(companyId);
    }

    @Override
    public JpmProcessImportExportDto exportJpmProcessDeploy(Long processDeployId) {
        JpmProcessImportExportDto resObj = null;
        resObj = jpmProcessDeployService.exportProcess(processDeployId);
        if (Objects.isNull(resObj)) {
            return null;
        } else {
            // get file
            FileDownloadParam fileDownloadParam = new FileDownloadParam();
            fileDownloadParam.setFilePath(resObj.getBpmnFilePath());
            fileDownloadParam.setRepositoryId(resObj.getBpmnRepoId());

            try {
                FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
                String bpmnFile = new String(fileDownloadResult.getFileByteArray());
                resObj.setFileBpmn(CommonBase64Util.encode(bpmnFile));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return resObj;
    }

    @Override
    public List<AppProcessDeployDto> findJpmProcessDeployDtoListByProcessId(Long processId) {
        DatabaseTypeEnum dataType = DatabaseTypeEnum.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));


        List<AppProcessDeployDto> results = new ArrayList<>();

        results = appProcessDeployRepository.findJpmProcessDeployDtoListByProcessIdSQL(processId);
        
//        switch (dataType) {
//        case SQLSERVER:
//            results = appProcessDeployRepository.findJpmProcessDeployDtoListByProcessIdSQL(processId);
//            break;
//        case MYSQL:
//            // TODO
//            break;
//        case ORACLE:
//            results = appProcessDeployRepository.findJpmProcessDeployDtoListByProcessId(processId);
//            break;
//        default:
//            break;
//        }
        
        return results;
    }

    @Override
    public String getLangByIdAndLangCode(Long processId, String lang) {
        JpmProcessDeployLang langDto = null;// appProcessDeployLangRepository.getByProcessDeployIdAndLang(processId, lang);
        return null != langDto ? langDto.getProcessName() : null;
    }

    @Override
    public List<LanguageMapDto> getProcessNameListById(Long processId) throws SQLException {
        return appProcessDeployLangRepository.findProcessNameListById(processId);
    }

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }

    private AppProcessDeployDto convertJpmProcessDtoToAppProcessDto(JpmProcessDeployDto processDto) {
        if (processDto == null)
            return null;
        AppProcessDeployDto appProcessDto = objectMapper.convertValue(processDto, AppProcessDeployDto.class);
        appProcessDto.setId(processDto.getProcessDeployId());
        appProcessDto.setCode(processDto.getProcessCode());
        appProcessDto.setName(processDto.getProcessName());
        appProcessDto.setIsShowWorkflow(processDto.isShowWorkflow());
        appProcessDto.setjRepositoryId(processDto.getBpmnRepoId());
        appProcessDto.setProcessDefinitionid(processDto.getProcessDefinitionId());
        appProcessDto.setFileNameBpmn(processDto.getBpmnFileName());
        appProcessDto.setFilePathBpmn(processDto.getBpmnFilePath());

        return appProcessDto;
    }

}

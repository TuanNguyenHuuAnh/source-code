/*******************************************************************************
 * Class        ：ProcessDeployServiceImpl
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：KhuongTH
 * Change log   ：2020/12/14：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsCollectionUtil;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.service.ProcessDeployService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeploySearchDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.entity.JpmProcessDeploy;
import vn.com.unit.workflow.service.JpmProcessDeployService;

/**
 * <p>
 * ProcessDeployServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessDeployServiceImpl extends AbstractCommonService implements ProcessDeployService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public JpmProcessDeployDto save(JpmProcessDeployDto objectDto) throws DetailException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) throws DetailException {
        try {
            boolean deleted = jpmProcessDeployService.deleteById(id);
            if (!deleted) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021906_APPAPI_PROCESS_DEPLOY_NOT_FOUND, true);
            }
        } catch (DetailException e) {
            throw e;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021904_APPAPI_PROCESS_DEPLOY_DELETE_ERROR, true);
        }
    }

    @Override
    public JpmProcessDeployDto detail(Long id) throws DetailException {
        JpmProcessDeployDto processDto = jpmProcessDeployService.getJpmProcessDeployDtoById(id);
        if (null == processDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021906_APPAPI_PROCESS_DEPLOY_NOT_FOUND, true);
        }
        return processDto;
    }

    @Override
    public ObjectDataRes<JpmProcessDeployDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JpmProcessDeployDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JpmProcessDeploy.class,
                    JpmProcessDeployService.TABLE_ALIAS_JPM_PROCESS_DEPLOY);
            /** init param search repository */
            JpmProcessDeploySearchDto searchDto = this.buildJpmProcessDeploySearchDto(commonSearch);

            int totalData = jpmProcessDeployService.countBySearchCondition(searchDto);
            List<JpmProcessDeployDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = jpmProcessDeployService.getProcessDeployDtosByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021901_APPAPI_PROCESS_DEPLOY_LIST_ERROR);
        }
        return resObj;
    }

    @Override
    public JpmProcessImportExportDto exportProcessDeploy(Long processDeployId) throws DetailException {
        JpmProcessImportExportDto resObj = null;
        resObj = jpmProcessDeployService.exportProcess(processDeployId);
        if (Objects.isNull(resObj)) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021906_APPAPI_PROCESS_DEPLOY_NOT_FOUND, true);
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
                logger.error("Exception ", e);
                throw new DetailException(AppApiExceptionCodeConstant.E4021906_APPAPI_PROCESS_DEPLOY_NOT_FOUND, true);
            }
            
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     * @author taitt
     */
    private JpmProcessDeploySearchDto buildJpmProcessDeploySearchDto(MultiValueMap<String, String> commonSearch) {
        JpmProcessDeploySearchDto processSearchDto = new JpmProcessDeploySearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long businessId = null != commonSearch.getFirst("businessId") ? Long.valueOf(commonSearch.getFirst("businessId")) : null;
        Long processId = null != commonSearch.getFirst("processId") ? Long.valueOf(commonSearch.getFirst("processId")) : null;

        processSearchDto.setCompanyId(companyId);
        processSearchDto.setBusinessId(businessId);
        processSearchDto.setKeySearch(keySearch);
        processSearchDto.setProcessCode(CommonStringUtil.isNotBlank(keySearch) ? keySearch.trim() : null);
        processSearchDto.setProcessName(CommonStringUtil.isNotBlank(keySearch) ? keySearch.trim() : null);
        processSearchDto.setProcessId(processId);
        return processSearchDto;
    }
    
    
    @Override
    public List<JpmProcessDeployDto> getJpmProcessDeployDtoByFormId(Long formId) throws DetailException {
        List<JpmProcessDeployDto> processDto = jpmProcessDeployService.getJpmProcessDeployByFormId(formId);
        if (DtsCollectionUtil.isEmpty(processDto)) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021906_APPAPI_PROCESS_DEPLOY_NOT_FOUND, true);
        }
        return processDto;
    }
}

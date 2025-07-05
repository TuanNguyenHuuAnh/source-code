/*******************************************************************************
 * Class        ：JpmSvcBoardServiceImpl
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import jp.sf.amateras.mirage.exception.BreakIterationException;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonNullAwareBeanUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.res.JpmSvcBoardRes;
//import vn.com.unit.ep2p.service.ConstantService;
import vn.com.unit.ep2p.service.FormService;
import vn.com.unit.ep2p.service.JpmSvcBoardService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.service.FileStorageService;

/**
 * JpmSvcBoardServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmSvcBoardServiceImpl extends AbstractCommonService implements JpmSvcBoardService {

    @Autowired
    private FormService formService;

//    @Autowired
//    private ConstantService constantService;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ObjectDataRes<JpmSvcBoardRes> svcBoardList(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception {
        ObjectDataRes<JpmSvcBoardRes> resObjs = new ObjectDataRes<>();

        try {
            ObjectDataRes<EfoFormDto> objSearchRes = formService.search(requestParams, pageable);

            List<JpmSvcBoardRes> jpmSvcs = new ArrayList<>();
            
            if (CommonCollectionUtil.isNotEmpty(objSearchRes.getDatas()) ) {
                objSearchRes.getDatas().stream().forEach(item -> {
                    try {
                       JpmSvcBoardRes jpmSvc = new JpmSvcBoardRes();
                       CommonNullAwareBeanUtil.copyPropertiesWONull(item, jpmSvc);
                       jpmSvcs.add(jpmSvc);
                   } catch (Exception e) {
                       throw new BreakIterationException();
                   }
               });
            }
            resObjs = new ObjectDataRes<>(objSearchRes.getTotalData(), jpmSvcs);
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4024901_APPAPI_JPM_SVC_BOARD_LIST_ERROR);
        }
        if (CommonCollectionUtil.isNotEmpty(resObjs.getDatas())) {
            List<JpmSvcBoardRes> jpmSvcBoardResList = resObjs.getDatas();
            for (JpmSvcBoardRes resObj : jpmSvcBoardResList) {
                if(resObj.getIconRepoId() != null) {
                    FileDownloadParam param = new FileDownloadParam();
                    param.setFilePath(resObj.getIconFilePath());
                    param.setRepositoryId(resObj.getIconRepoId());
                    
                    FileDownloadResult downloadResultDto;
                    try {
                        downloadResultDto = fileStorageService.download(param);
                        byte[] byteImage = downloadResultDto.getFileByteArray();
                        String imageBase64 = CommonBase64Util.encode(byteImage);
                        
                        resObj.setImgBase64(imageBase64);
                        resObj.setImageName(downloadResultDto.getFileName());
                    } catch (Exception e) {
                        throw new DetailException(AppApiExceptionCodeConstant.E4024902_APPAPI_JPM_SVC_BOARD_IMAGE_FORM_ERROR);
                    }
                }
            }
        }

        // TODO get constant display form type

        return resObjs;
    }
}

/*******************************************************************************
 * Class        ：DocumentMainFileRest
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
//import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.res.dto.DocumentMainFileUploadReq;
//import vn.com.unit.ep2p.core.service.DocumentMainFileService;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * 
 * DocumentMainFileRest
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

//@RestController
//@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
//@Api(tags = { AppApiConstant.API_APP_DOCUMENT_MAIN_FILE_DESCR })
public class DocumentMainFileRest2bk extends AbstractRest {

//    @Autowired
//    private DocumentMainFileService documentMainFileService;

//    @PostMapping(AppApiConstant.API_APP_DOCUMENT_MAIN_FILE)
//    @ApiOperation("Create document main file")
//    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 401, message = "Unauthorized"),
//            @ApiResponse(code = 402, message = "Forbidden"), @ApiResponse(code = 402802, message = "Error process add document"),
//            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse uploadMainFile(
            @ApiParam(name = "body", value = "Document main file information to add new") @RequestBody DocumentMainFileUploadReq documentSaveReq) {
        long start = System.currentTimeMillis();
        try {
            // 1.Validate
            // documentAppService.validate();

            // 2. Save document
            //EfoOzDocMainFileDto resDoto = documentMainFileService.uploadAndSave(documentSaveReq);
            String usePath = System.getProperty("user.home") + "/Desktop";
            File folder = new File(usePath+"/KhuongTH-20210126");
            folder.mkdir();
            File file = new File(usePath, "/KhuongTH-20210126/".concat(documentSaveReq.getFileName()));
            try {
                Files.write(file.toPath(), CommonBase64Util.decodeToByte(documentSaveReq.getFileBase64()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return this.successHandler.handlerSuccess("OK", start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

}

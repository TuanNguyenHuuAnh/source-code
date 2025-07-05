/*******************************************************************************
 * Class        ：AttachedFileControllerInterface
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ReturnObject;
import vn.com.unit.ep2p.core.dto.AttachedFileDto;
import vn.com.unit.ep2p.core.entity.AttachedFile;
import vn.com.unit.ep2p.core.service.AttachedFileService;
import vn.com.unit.imp.excel.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.constant.Message;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.constant.UrlConst;
//import vn.com.unit.jcanary.constant.ViewConstant;
//import vn.com.unit.jcanary.dto.AttachedFileDto;
//import vn.com.unit.jcanary.dto.ReturnObject;
//import vn.com.unit.jcanary.entity.AttachedFile;
//import vn.com.unit.jcanary.service.AttachedFileService;

/**
 * AttachedFileControllerInterface
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface AttachedFileControllerInterface {

    static final Logger logger = LoggerFactory.getLogger(AttachedFileControllerInterface.class);

    /**
     * getAttachedFileService
     *
     * @return
     * @author VinhLT
     */
    AttachedFileService getAttachedFileService();

    /**
     * getPermisionItem
     *
     * @return
     * @author VinhLT
     */
    String getPermisionItem();

    /**
     * uploadFile
     *
     * @param attachedFileDto
     * @param model
     * @return
     * @author VinhLT
     */
    @RequestMapping(value = UrlConst.URL_ATTACHED_FILE_UPLOAD, method = { RequestMethod.POST })
    default public String uploadFile(@ModelAttribute(value = "attachedFileDto") AttachedFileDto attachedFileDto,
            Model model) {
        if (!UserProfileUtils.hasRole(getPermisionItem())
                && !UserProfileUtils.hasRole(getPermisionItem().concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(getPermisionItem().concat(ConstantCore.COLON_EDIT))) {
            return ViewConstant.ACCESS_DENIED_MODELANDVIEW;
        }
        try {
            List<AttachedFile> lstAttachedFile = getAttachedFileService().processAttachedFile(attachedFileDto);
            model.addAttribute("lstAttachedFile", lstAttachedFile);
            model.addAttribute("itemPermission", getPermisionItem());
            model.addAttribute("isShowActionAttachedFile", true);

        } catch (Exception e) {
            logger.error(UrlConst.URL_ATTACHED_FILE_UPLOAD, e);
        }
        return "/views/ASSET_MANAGEMENT_MODULE/attached-file-module/attached-file-table.html"; // "attached.file.table"
    }

    /**
     * downloadFile
     *
     * @param attachedFileDto
     * @param response
     * @author VinhLT
     */
    @RequestMapping(value = UrlConst.URL_ATTACHED_FILE_DOWNLOAD, method = RequestMethod.GET)
    default public void downloadFile(@RequestParam(value = "id") Long id, HttpServletResponse response) {
        try {
            getAttachedFileService().downloadAttachedFile(id, response);

        } catch (Exception e) {
            logger.error(UrlConst.URL_ATTACHED_FILE_DOWNLOAD, e);
        }
    }

    /**
     * deleteFile
     *
     * @param id
     * @param response
     * @author VinhLT
     */
    @RequestMapping(value = UrlConst.URL_ATTACHED_FILE_DELETE, method = RequestMethod.POST)
    @ResponseBody
    default public ReturnObject deleteFile(@RequestParam(value = "id") Long id, HttpServletResponse response) {
        ReturnObject returnObject = new ReturnObject();
        try {
            if (!UserProfileUtils.hasRole(getPermisionItem())
                    && !UserProfileUtils.hasRole(getPermisionItem().concat(ConstantCore.COLON_DISP))
                    && !UserProfileUtils.hasRole(getPermisionItem().concat(ConstantCore.COLON_EDIT))) {
                throw new Exception("Not have permission");
            }
            getAttachedFileService().deleteFileById(id);
            returnObject.setMessage(Message.SUCCESS, "Delete file successfull");
        } catch (Exception e) {
            logger.error(UrlConst.URL_ATTACHED_FILE_DELETE, e);
            returnObject.setMessage(Message.ERROR, "Delete file fail");
        }
        return returnObject;
    }
}

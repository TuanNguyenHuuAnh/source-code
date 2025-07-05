/*******************************************************************************
 * Class        ：MockupController
 * Created date ：2017/03/23
 * Lasted date  ：2017/03/23
 * Author       ：trieunh <trieunh@unit.com.vn>
 * Change log   ：2017/03/23：01-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.controller;

//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.utils.CommonStringUtil;

/**
 * MockupController
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
@Controller(value = "")
public class MockupController {
//    @Autowired
//    private ServletContext servletContext;

	// /mockup?link=index.html
    @RequestMapping(value = "/mockup", method = RequestMethod.GET)
    public ModelAndView viewMockup(@RequestParam(name = "link", required = false) String link) {
    	
    	if (CommonStringUtil.isEmpty(link)) {
    		link = "index";
    	}
    	
        ModelAndView mav = new ModelAndView("/mockup/" + link.replace(".html", "") + ".html");

//        switch (link) {
//        case "SDL001":
//            // case "SDA001":
//            String viewName = UrlConst.REDIRECT.concat("/document/list");
//            mav.setViewName(viewName);
//            break;
//        default:
//            break;
//        }

        return mav;
    }

//    @RequestMapping(value = "/mockup/dowload-excel", method = RequestMethod.GET)
//    public void downloadFile(@RequestParam(name = "file", required = false) String file, HttpServletRequest request,
//            HttpServletResponse response, Locale locale) {
//        // start export excel
//        String filePath = servletContext.getRealPath(RptConstant.REAL_PATH_TEMPLATE_EXCEL).concat(ConstantCore.SLASH)
//                .concat(file);
//        File fileExcel = new File(filePath);
//        try {
//            response.setContentType(RptConstant.CONTENT_TYPE_EXCEL);
//            response.setHeader("Content-disposition", "attachment; filename=\"" + file);
//            FileCopyUtils.copy(FileUtils.readFileToByteArray(fileExcel), response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @PostMapping(value = "/mockup/export-excel")
//    public void exportExcel(@RequestParam("token") String token, HttpServletRequest req, HttpServletResponse res,
//            @RequestParam("templateName") String templateName, Locale locale) throws Exception {
//
//        Utils.addCookieForExport(token, req, res);
//
//        // String templateName = ExcelConst.IMPORT_AGENT_STRUCTURE;
//
//        String template = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
//                + CommonConstant.TYPE_EXCEL;
//        String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
//
//        List<AbstractDTO> lstData = new ArrayList<AbstractDTO>();
//
//        List<ItemColsExcelDto> cols = new ArrayList<>();
//
//        ExportExcelUtil<AbstractDTO> exportExcel = new ExportExcelUtil<>();
//
//        // do export
//        exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, AbstractDTO.class, cols, datePattern, "A5", res,
//                templateName);
//    }
}

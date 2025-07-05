package vn.com.unit.ep2p.rest.cms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSearchResultDto;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSuperDto;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.ApiLibraryMaterialService;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.res.ImportExcelAbstractRest;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;

@RestController
@RequestMapping(CmsApiConstant.BASE_API_URL + CmsApiConstant.API_LIBRARY_MARTERIAL)
@Api(tags = { CmsApiConstant.API_LIBRARY_MATERIAL_DESCR })
public class LibraryMaterialsRest  extends AbstractRest{
	
    @Autowired
    private ApiLibraryMaterialService apiLibraryMaterialService;
	
    @Autowired
    ServletContext servletContext;
    
    static final Logger logger = LoggerFactory.getLogger(ImportExcelAbstractRest.class);

	@GetMapping(AppApiConstant.API_CATEROGY_LIBRARY_MATERIAL_BY_ID)// get title va ten file dowload
    @ApiOperation("Api provides a list library masterial on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), 
    		@ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), 
            @ApiResponse(code = 403, message = "Forbidden"), })
    public ResponseEntity getListByIdCategory(HttpServletRequest request 
    	   ,@RequestParam(value = "searchKey", required = false) String searchKey 
    	   ,@RequestParam(value = "categoryId") Long categoryId	   
    	   ,@RequestParam(defaultValue = "1") Integer page 
    	   ,@RequestParam(defaultValue = "10") Integer size 
    	   ,@RequestParam(defaultValue = "0") Integer modeView)  {  
        long start = System.currentTimeMillis(); 
        Locale locale = LangugeUtil.getLanguageFromHeader(request);  
        
        	List<LibraryMaterialSearchResultDto> datas = new ArrayList<>();  
        	if(!StringUtils.isEmpty(searchKey)) {  
        		searchKey = FileUtil.deAccent(searchKey).toLowerCase().replace(" ", "-");  
        	}  
        	int count = apiLibraryMaterialService.countLibraryMaterialByIdCategory(searchKey,categoryId, modeView, locale.getLanguage());  
            if (count > 0) { 
                datas = apiLibraryMaterialService.searchLibraryMaterialByIdCategory(searchKey,categoryId, page, size, locale.getLanguage(), modeView);  
            } 
            ObjectDataRes<LibraryMaterialSearchResultDto> resObj = new ObjectDataRes<>(count, datas); 
            // EXPORT
            String templateName = resObj.getDatas().get(0).getFileName();
            String fileServer = resObj.getDatas().get(0).getPhysicalFileName();
            
            ResponseEntity res = null; 
            try {                
                ExportExcelUtil exportExcel = new ExportExcelUtil<>(); 
                Workbook workbook = exportExcel.getXSSFWorkbook(fileServer); 
                ByteArrayOutputStream out = new ByteArrayOutputStream(); 
                SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss"); 
                String currentDate = formatDateExport.format(new Date()); 
                workbook.write(out); 
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray()); 
                HttpHeaders headers = new HttpHeaders(); 
                headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME 
                		+ templateName.replace(CommonConstant.TYPE_EXCEL, "") + "_" + currentDate + CommonConstant.TYPE_EXCEL + "\"");               
                res = ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(CommonConstant.CONTENT_TYPE_EXCEL)) 
                		.body(new InputStreamResource(in)); 
            } catch (Exception e) { 
                logger.error("##downloadTemplate##", e); 
            }         
           return res;
        
    } 
	
	@GetMapping(AppApiConstant.API_CATEROGY_LIBRARY_MATERIAL_BY_TYPE_SUPER) // get tat ca danh muc cha
    @ApiOperation("Api provides a list library masterial on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
    public DtsApiResponse getListByTypeCategory(HttpServletRequest request
            ,@RequestParam(defaultValue = "") String categoryType)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	Long parentID = 1L;
        	List<LibraryMaterialSuperDto> datas = apiLibraryMaterialService.getListSuperCategory(parentID ,locale, categoryType);
            
            ObjectDataRes<LibraryMaterialSuperDto> resObj = new ObjectDataRes<>(0, datas);
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	
	@GetMapping(AppApiConstant.API_CATEROGY_LIBRARY_MATERIAL_GA) // get tat ca danh muc GA
    @ApiOperation("Api provides a list library masterial on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
	 public DtsApiResponse getListByTypeSubCategoryGa(HttpServletRequest request
	            ,@RequestParam(value = "keySearch", required = false, defaultValue = "") String keySearch
	            ,@RequestParam(value = "page", required = false, defaultValue = "0") Integer page
	            ,@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize)  {
	        long start = System.currentTimeMillis();
	        Locale locale = LangugeUtil.getLanguageFromHeader(request);
	        try {
	        	Long parentID = 1L;
	        	ObjectDataRes<LibraryMaterialSuperDto> resObj = apiLibraryMaterialService.getListSubDocGa(locale, page, pageSize, keySearch);    
	            return this.successHandler.handlerSuccess(resObj, start, null, null);
	        } catch (Exception ex) {
	            return this.errorHandler.handlerException(ex, start, null, null);
	        }
	    }
	
	@GetMapping(AppApiConstant.API_CATEROGY_LIBRARY_MATERIAL_BY_TYPE_SUB) // get danh muc con theo danh muc cha
    @ApiOperation("Api provides a list library masterial on systems")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
    public DtsApiResponse getListByTypeSubCategory(HttpServletRequest request,@RequestParam(value = "id") Long id
            ,@RequestParam(value = "keySearch", required = false, defaultValue = "") String keySearch
            ,@RequestParam(value = "page", required = false, defaultValue = "0") Integer page
            ,@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize
            ,@RequestParam(defaultValue = "") String categoryType )  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	Long parentID = 1L;
        	List<LibraryMaterialSuperDto> datas = new ArrayList<>();
        	ObjectDataRes<LibraryMaterialSuperDto> resObj = new ObjectDataRes<>();
        	    datas = apiLibraryMaterialService.getListSuperCategory(parentID ,locale, categoryType);
        	    datas.stream().forEach(x ->System.out.println(x.getId()) );
                resObj = new ObjectDataRes<>();
                if(datas !=null) {
                    for(LibraryMaterialSuperDto item: datas) {
                        if(item.getId().equals(id)) {
                            resObj = apiLibraryMaterialService.getListSubDoc(id ,locale, page, pageSize, keySearch);

                        }
                    }
                }
            return this.successHandler.handlerSuccess(resObj, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
	@GetMapping(AppApiConstant.API_CATEROGY_BY_CODE)
    @ApiOperation("Api provides a get cate id by constant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success"), @ApiResponse(code = 500, message = "Internal Server Error"),
    @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"), })
    public DtsApiResponse getCategoryByConstant(HttpServletRequest request, String constantCode)  {
        long start = System.currentTimeMillis();
        Locale locale = LangugeUtil.getLanguageFromHeader(request);
        try {
        	LibraryMaterialSuperDto data = apiLibraryMaterialService.getCategoryByConstant(constantCode ,locale);
            
            return this.successHandler.handlerSuccess(data, start, null, null);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start, null, null);
        }
    }
}

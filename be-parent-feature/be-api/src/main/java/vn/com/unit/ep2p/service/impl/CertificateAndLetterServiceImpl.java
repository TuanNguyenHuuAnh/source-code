package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import fr.opensagres.poi.xwpf.converter.core.Color;
import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateAnotherPagingParam;
import vn.com.unit.cms.core.module.agent.dto.CertificateAnotherSearchDto;
import vn.com.unit.cms.core.module.agent.dto.CertificateDto;
import vn.com.unit.cms.core.module.agent.dto.CertificatePagingParam;
import vn.com.unit.cms.core.module.agent.dto.CertificateSearchDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceLetterDto;
import vn.com.unit.cms.core.module.agent.dto.IntroduceLetterPagingParam;
import vn.com.unit.cms.core.module.agent.dto.IntroduceSearchDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentPagingParam;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentSearchDto;
import vn.com.unit.cms.core.module.agent.dto.LetterAgentTerPram;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.enumdef.CertificateExportEnum;
import vn.com.unit.ep2p.enumdef.IntroduceLetterExportEnum;
import vn.com.unit.ep2p.enumdef.LetterAgentExportEnum;
import vn.com.unit.ep2p.service.CertificateAndLetterService;
import vn.com.unit.ep2p.service.ParseJsonToParamSearchService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.JcaRepositoryService;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class CertificateAndLetterServiceImpl implements CertificateAndLetterService {

	private static final String DS_SP_GET_LIST_CERTIFICATION_OF_AGENT = "RPT_ODS.DS_SP_GET_LIST_CERTIFICATION_OF_AGENT";
	private static final String DS_SP_LETTER_AGENT = "RPT_ODS.DS_SP_MOVEMENT_LETTER_AGENT";
	private static final String DS_SP_LETTER_INTRODUCE = "RPT_ODS.DS_SP_LETTER_INTRODUCE";
	private static final String DS_SP_GET_LIST_CERTIFICATION_OF_AGENT_ANOTHER = "RPT_ODS.DS_SP_GET_LIST_CERTIFICATION_OF_AGENT_ANOTHER";
	private static final String DS_SP_TER_LETTER_AGENT = "RPT_ODS.DS_SP_TER_LETTER_AGENT";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	ParseJsonToParamSearchService parseJsonToParamSearchService;

	@Autowired
	private JcaRepositoryService jcaRepositoryService;
	
	@Override
	public CmsCommonPagination<LetterAgentDto> getListLetterAgentByCondition(LetterAgentSearchDto dto) {
	    CmsCommonPagination<LetterAgentDto> resultData = new CmsCommonPagination<LetterAgentDto>();
		LetterAgentPagingParam param = new LetterAgentPagingParam();
		dto.setFunctionCode("LETTER_AGENT");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "LETTER_AGENT");
		param.agentCode = dto.getAgentCode();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(DS_SP_LETTER_AGENT, param);
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
		    resultData.setData(param.data);
	        resultData.setTotalData(param.totalRows);
        }
		return resultData;
	}

	@Override
	public CmsCommonPagination<IntroduceLetterDto> getListIntroduceLetterByCondition(IntroduceSearchDto dto) {
		IntroduceLetterPagingParam param = new IntroduceLetterPagingParam();
		CmsCommonPagination<IntroduceLetterDto> resultData = new CmsCommonPagination<IntroduceLetterDto>();
		dto.setFunctionCode("LETTER_INTRODUCE");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
    	String search = "";
        if( ObjectUtils.isEmpty(dto.getEffectiveDate())) {
        	search = " AND '"+dto.getNgayHieuLuc() +"' = VARCHAR_FORMAT( ASSIGN_DATE,'YYYYMM')";
        }
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "LETTER_INTRODUCE");
		param.agentCode = dto.getAgentCode();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.sort = common.getSort();
        param.search = search +" "+common.getSearch();
		sqlManagerDb2Service.call(DS_SP_LETTER_INTRODUCE, param);
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			param.data.forEach(e -> {
				e.setPolicyNo(e.getSoHdbh());
                if(StringUtils.isNotBlank(e.getSoHdbh())){
                    e.setSoHdbh(formatPolicyNumber(9, e.getSoHdbh()));
                }
			});
		    resultData.setData(param.data);
	        resultData.setTotalData(param.totalRows);
        }
		return resultData;
	}
	private String formatPolicyNumber(int digits, String policyNumber){
		if(StringUtils.isEmpty(policyNumber)) {
			return "";
		}
        return IntStream.range(0, digits - policyNumber.length()).mapToObj(i -> "0").collect(Collectors.joining("")).concat(policyNumber);
    }

	@Override
	public ObjectDataRes<CertificateDto> getListCertificateByCondition(CertificateSearchDto dto) {
		dto.setFunctionCode("CERTIFICATE");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "CERTIFICATE");
		CertificatePagingParam param = new CertificatePagingParam();
		param.agentCode=dto.getAgentCode();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(DS_SP_GET_LIST_CERTIFICATION_OF_AGENT, param);
		List<CertificateDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			total = param.total;
		}
		ObjectDataRes<CertificateDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportExcelLetterAgent(LetterAgentSearchDto dto, HttpServletResponse response, Locale locale) {
        ResponseEntity res = null;
		try {
			dto.setPage(0);
			dto.setPageSize(0);
			CmsCommonPagination<LetterAgentDto> common = getListLetterAgentByCondition(dto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            //String templateName = "Presentation.xlsx";
            String templateName = "Letter_Agent.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A5";
            
            List<LetterAgentDto> lstdata = common.getData();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
			ImportExcelUtil.setListColumnExcel(LetterAgentExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, LetterAgentDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
                logger.error("##doExport##", e);
            }

          } catch (Exception e) {
              logger.error("##exportExcelLetterAgent##", e);
        }
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResponseEntity exportExcelIntroduceLetter(IntroduceSearchDto dto, HttpServletResponse response,
			Locale locale) {
		ResponseEntity res = null;
		try {
			dto.setPage(0);
			dto.setPageSize(0);
			CmsCommonPagination<IntroduceLetterDto> common = getListIntroduceLetterByCondition(dto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Introduce_Letter.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A5";
            
            List<IntroduceLetterDto> lstdata = common.getData();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
			ImportExcelUtil.setListColumnExcel(IntroduceLetterExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, IntroduceLetterDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
				throw new Exception(e.getMessage());
			}
          } catch (Exception e) {

        }
		return res;
	}

	@Override
	public ObjectDataRes<CertificateDto> getListCertificateAnotherByCondition(CertificateAnotherSearchDto dto) {
		CertificateAnotherPagingParam param = new CertificateAnotherPagingParam();
		dto.setFunctionCode("CERTIFICATE_ANOTHER");
    	ObjectMapper mapper = new ObjectMapper();
    	String stringJsonParam ="";
    	try {
			stringJsonParam = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
		CommonSearchWithPagingDto common = parseJsonToParamSearchService.getSearchCondition(stringJsonParam, "CERTIFICATE_ANOTHER");
		param.agentCode=dto.getAgentCode();
		param.page=dto.getPage();
		param.pageSize=dto.getPageSize();
		param.sort = common.getSort();
		param.search = common.getSearch();
		sqlManagerDb2Service.call(DS_SP_GET_LIST_CERTIFICATION_OF_AGENT_ANOTHER, param);
		List<CertificateDto> data = new ArrayList<>();
		int total = 0;
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
			data = param.data;
			total = param.total;
		}
		ObjectDataRes<CertificateDto> resObj = new ObjectDataRes<>(total, data);
		return resObj;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity exportListCertificateByCondition(CertificateSearchDto dto, HttpServletResponse response,
			Locale locale) {
		ResponseEntity res = null;
		try {
			dto.setPage(0);
			dto.setPageSize(0);
			ObjectDataRes<CertificateDto> common = getListCertificateByCondition(dto);
            String datePattern = getSystemConfig().getConfig(SystemConfig.DATE_PATTERN);
            datePattern = "dd/MM/yyyy";
            String templateName = "Certificate.xlsx";
    		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_EXCEL + "/" + templateName);
    		String startRow = "A5";
            
            List<CertificateDto> lstdata = common.getDatas();
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
			ImportExcelUtil.setListColumnExcel(CertificateExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null;// setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null;// setMapColDefaultValue();

            // do export
            try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbook(templatePath);) {
				Map<String, CellStyle> mapColStyle = null;// setMapColStyle(xssfWorkbook);
				String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
				String path = systemConfig.getPhysicalPathById(repo, null); //path up service
            	res =  exportExcel.doExportExcelHeaderWithColFormatRestUpService(xssfWorkbook, 0, null, lstdata, CertificateDto.class
            			, cols, datePattern, startRow, mapColFormat, mapColStyle,
						setMapColDefaultValue, null, true, templateName, true,path);
            } catch (Exception e) {
				throw new Exception(e.getMessage());
			}
          } catch (Exception e) {

        }
		return res;
	}

	@Override
	public String getFileByBase64(FileDto file) throws IOException {
		
//		String base64 = file.getBase64();
		 byte[] bytes = file.getBase64();
				   //  Base64.getEncoder().encode(base64.getBytes(StandardCharsets.UTF_8));
//		 base64 = new String(bytes);
		try {
//			logger.error("base64-------", base64);
			JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
			logger.error("repo-------", repo.getPhysicalPath());
			String path = Paths.get(repo.getPhysicalPath(), file.getFileType()+ "/" + file.getFileName()).toString();
			logger.error("path-------", path);
			File fileOut = new File(path);
			logger.error("fileOut-------", fileOut);

			writeFileFromBase64(bytes, fileOut, file.getTemplateType(), file.getLetterCategory(), file.getFileType());

			return file.getFileType()+ "//" + file.getFileName();
		} catch (DetailException e) {

			logger.error("DetailException-------", e);
		}

		return new String(bytes);
	}

    private void writeFileFromBase64(byte[] base64, File fileOut, String templateType, String letterCategory, String fileType) throws DetailException {
        String path = fileOut.getParent();
        File direct = new File(path);
        if (!direct.isDirectory()) {
            direct.mkdirs();
        }
        try {
			manipulatePdf(base64, fileOut.getPath(), templateType, letterCategory, fileType);
		} catch (IOException e1) {
			logger.error("IOException-------", e1);
		} catch (DocumentException e1) {
			logger.error("DocumentException-------", e1);
		}
//        try (FileOutputStream fs = new FileOutputStream(fileOut)) {
//            fs.write(Base64.getDecoder().decode(base64));
//            fs.flush();
//        } catch (IOException e) {
//            throw new DetailException("writeFileFromBase64:" + e.getMessage());
//        }

//    	OutputStream fos;
//		try {
//			fos = new BufferedOutputStream(new FileOutputStream(fileOut));
//    	    try {
//				fos.write(base64);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				logger.error("Exception ", e);
//			}
//        	finally {
//        		try {
//					fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					logger.error("Exception ", e);
//				}
//        	}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			logger.error("Exception ", e);
//		}
    }

	@Override
	public CmsCommonPagination<LetterAgentDto> getListLetterAgentTer(LetterAgentSearchDto searchDto) {
		LetterAgentTerPram param = new LetterAgentTerPram();
		param.agentCode = searchDto.getAgentCode();
		sqlManagerDb2Service.call(DS_SP_TER_LETTER_AGENT, param);
	    CmsCommonPagination<LetterAgentDto> resultData = new CmsCommonPagination<LetterAgentDto>();
		if (CommonCollectionUtil.isNotEmpty(param.data)) {
		    resultData.setData(param.data);
		}
		return resultData;
	}
	public void manipulatePdf(byte[] src, String dest, String templateType, String letterCategory, String fileType) throws IOException, DocumentException {
	    PdfReader reader = new PdfReader(src);
	    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	    PdfContentByte under = stamper.getUnderContent(1);
	    Font f = new Font(FontFamily.TIMES_ROMAN, 9);

	    String fontName = servletContext.getRealPath(CommonConstant.REAL_PATH_FONT) + File.separator;
	    fontName = Paths.get(fontName, "vuArial.ttf").toString();
	    f = new Font(BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 9);
		f.setSize(9);
		f.setStyle(Font.NORMAL);
	    Phrase p = new Phrase("", f);
	    ColumnText.showTextAligned(under, Element.ALIGN_LEFT, p, 600, 400, 0);
	    PdfContentByte over = stamper.getOverContent(1);
	    over.saveState();
	    PdfGState gs1 = new PdfGState();
	    gs1.setFillOpacity(0.6f);
	    over.setGState(gs1);
//		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF) + File.separator;
//		templatePath = Paths.get(templatePath, "icon-D.png").toString();
//	    File fi = new File(templatePath);
//	    byte[] base64 = Files.readAllBytes(fi.toPath());
//	    Image i = Image.getInstance(base64);
//	    i.scaleAbsolute(25, 25);
//	    i.setAbsolutePosition(30, 25);
//	    over.addImage(i);
//	    p = new Phrase("Copyright © Dai-ichi Life Việt Nam", f);
//	    ColumnText.showTextAligned(over, Element.ALIGN_BOTTOM, p, 58, 30, 0);
	    String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF) + File.separator;
		templatePath = Paths.get(templatePath, "icon-D.png").toString();
	    File fi = new File(templatePath);
	    Rectangle pagesize = reader.getPageSize(1);
	    byte[] base64 = Files.readAllBytes(fi.toPath());
	    Image i = Image.getInstance(base64);
	    i.scaleAbsolute(25, 25);
	    if (StringUtils.isEmpty(fileType) || !"ConfirmMELetter".equalsIgnoreCase(fileType)) {

		    String[] templateTypeString = {"DOC_7", "DOC_8", "DOC_9", "DOC_10"};
		    if (StringUtils.isNotEmpty(letterCategory) && Arrays.stream(templateTypeString).anyMatch(letterCategory::equals)) {
		    	BaseColor white = new BaseColor(255, 255, 255);
		    	f.setColor(white);
			    gs1.setFillOpacity(0.7f);
			    over.setGState(gs1);
		    	i.setAbsolutePosition(30, 25);
			    over.addImage(i);
			    p = new Phrase("Copyright © Dai-ichi Life Việt Nam", f);
			    ColumnText.showTextAligned(over, Element.ALIGN_BOTTOM, p, 58, 30, 0);
		    }
		    else if ("CERTIFICATE".equalsIgnoreCase(templateType)) {
			    i.setAbsolutePosition(30, 25);
			    over.addImage(i);
			    p = new Phrase("Copyright © Dai-ichi Life Việt Nam", f);
			    ColumnText.showTextAligned(over, Element.ALIGN_BOTTOM, p, 58, 30, 0);
		    } else {
			    i.setAbsolutePosition(pagesize.getWidth() - 48, 35);
			    over.addImage(i);
			    p = new Phrase("Copyright © Dai-ichi Life Việt Nam", f);
			    ColumnText.showTextAligned(over, Element.ALIGN_BOTTOM, p, pagesize.getWidth() - 160, 25, 0);
		    }
	    }
	    over.restoreState();
	    stamper.close();
	    reader.close();
	}
}

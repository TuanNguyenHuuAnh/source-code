package vn.com.unit.ep2p.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import vn.com.unit.ep2p.core.ers.dto.ImageProviderDto;
import vn.com.unit.imp.excel.constant.CommonConstant;

/**
 * ConvertDocxUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenNX
 */
public class ConvertDocxUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertDocxUtils.class);
    
    public byte[] covertFile(String exportType
            , String pathFile, String fileName, Map<String, Object> param, List<ImageProviderDto> paramImage) throws Exception{
        byte[] retData = null;
        try {
            File docxSourceFile = new File(pathFile, fileName);
            File tempFileDocx = File.createTempFile(fileName.replace(".docx", ""), ".docx");
            try (InputStream docxInputStream = new FileInputStream(docxSourceFile);) {
                IXDocReport ixDocReport = XDocReportRegistry.getRegistry().loadReport(docxInputStream, TemplateEngineKind.Velocity);
                FieldsMetadata metadata = new FieldsMetadata();
                IContext context = ixDocReport.createContext();
                putParamToIcontext(context, param);
                putParamImage(metadata, context, paramImage);
                ixDocReport.setFieldsMetadata(metadata);
                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFileDocx)) {
                    ixDocReport.process(context, fileOutputStream);
                    XDocReportRegistry.getRegistry().unregisterReport(ixDocReport);
                }
            }
            switch (exportType) {
            case "WORD_CASH":
                try (FileInputStream fileInputStream = new FileInputStream(tempFileDocx)) {
                    retData = IOUtils.toByteArray(fileInputStream);
                }
                break;
            case "WORD":
                try (FileInputStream fileInputStream = new FileInputStream(tempFileDocx)) { 
                    retData = IOUtils.toByteArray(fileInputStream); 
                }
                break;
            case "PDF":
                ZipSecureFile.setMinInflateRatio(0);
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    IXDocReport reportPdf = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(tempFileDocx),
                            TemplateEngineKind.Velocity);
                    PdfOptions pdfOptions = PdfOptions.create();
                    Options convertOptions = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF).subOptions(pdfOptions);
                    reportPdf.convert(reportPdf.createContext(), convertOptions, byteArrayOutputStream);
                    byteArrayOutputStream.flush();
                    retData = byteArrayOutputStream.toByteArray();
                }
                break;
            case "HTML":
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    IXDocReport reportPdf = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(tempFileDocx),
                            TemplateEngineKind.Velocity);
                    Options convertOptions = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.XWPF);
                    reportPdf.convert(reportPdf.createContext(), convertOptions, byteArrayOutputStream);
                    retData = byteArrayOutputStream.toByteArray();
                }
                break;
            }
            Files.delete(tempFileDocx.toPath());
            return retData;
        } catch (Exception e) {
            logger.error("Convert to PDF", e);
            throw e;
        }
    }

    public ResponseEntity<Resource> exportForRest(String exportType
            , String pathFile, String fileName, String fileNameNew
            , Map<String, Object> param, List<ImageProviderDto> paramImage) throws Exception{
        try {
            byte[] file = covertFile(exportType, pathFile, fileName, param, paramImage);
            if(file != null) {
                String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"; 
                String suffix = ".docx";
                switch (exportType) {
                case "PDF":
                    contentType = CommonConstant.CONTENT_TYPE_PDF;
                    suffix = ".pdf";
                    break;
                case "HTML":
                    
                    break;
                }
                ByteArrayInputStream in = new ByteArrayInputStream(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add(CommonConstant.CONTENT_DISPOSITION, CommonConstant.ATTCHMENT_FILENAME + fileNameNew + suffix);
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(new InputStreamResource(in));
            }
        } catch (Exception e) {
            throw e;
        }                               
        return null;
    }
    
    private void putParamToIcontext(IContext context, Map<String, Object> param) {
        SimpleDateFormat sdfCommon = new SimpleDateFormat("dd/MM/yyyy");
        if(param != null) {
            for (String i : param.keySet()) {
                Object value = param.get(i);
                if(value == null) {
                    value = "";
                } else {
                    if(value instanceof Date) {
                        value = sdfCommon.format(value);
                    }
                }
                context.put(i, value);
            }
        }
    }

    private void putParamImage(FieldsMetadata metadata, IContext context, List<ImageProviderDto> paramImage) {
        if(CollectionUtils.isNotEmpty(paramImage)) {
            for(ImageProviderDto item: paramImage) {
                try {
                    File file = new File(item.getPathFile());
                    if(file.exists()) {
                        metadata.addFieldAsImage(item.getName());
                        IImageProvider fileImage = new FileImageProvider(file, true);
                        fileImage.setSize(item.getWidth(), item.getHeight());
                        context.put(item.getName(), fileImage);
                    }
                } catch (Exception e) {
                    logger.error("Error get file", e);
                }
            }
        }
    }
}

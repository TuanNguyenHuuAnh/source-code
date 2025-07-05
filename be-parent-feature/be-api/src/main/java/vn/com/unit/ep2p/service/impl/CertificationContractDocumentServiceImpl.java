package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.agent.dto.ContractDocumentDto;
import vn.com.unit.cms.core.module.agent.dto.FileDto;
import vn.com.unit.cms.core.module.db2.Db2Repository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.service.CertificationContractDocumentService;
import vn.com.unit.imp.excel.constant.CommonConstant;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.service.JcaRepositoryService;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class CertificationContractDocumentServiceImpl implements CertificationContractDocumentService {
	@Autowired
	Db2Repository db2Repository;
	@Autowired
	private JcaRepositoryService jcaRepositoryService;
	@Autowired
	private ServletContext servletContext;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<ContractDocumentDto> getListTermsAndConditions(List<String> docIds) {
		List<ContractDocumentDto> lstData = db2Repository.getContractDocuments(docIds);
		return lstData;
	}
	
	@Override
	public String getFileByBase64(FileDto file) throws IOException {
		byte[] bytes = file.getBase64();
		String fileType = "ConfirmDocuments";
		try {
			JcaRepositoryDto repo = jcaRepositoryService.getJcaRepositoryDto("REPO_MAIN", null);
			logger.error("repo-------", repo.getPhysicalPath());
			String path = Paths.get(repo.getPhysicalPath(), fileType + "/" + file.getFileName()).toString();
			logger.error("path-------", path);
			File fileOut = new File(path);
			logger.error("fileOut-------", fileOut);

			writeFileFromBase64(bytes, fileOut);

			return fileType + "//" + file.getFileName();
		} catch (DetailException e) {
			logger.error("DetailException-------", e);
		}

		return new String(bytes);
	}

    private void writeFileFromBase64(byte[] base64, File fileOut) throws DetailException {
        String path = fileOut.getParent();
        File direct = new File(path);
        if (!direct.isDirectory()) {
            direct.mkdirs();
        }
        try {
			manipulatePdf(base64, fileOut.getPath());
		} catch (IOException e1) {
			logger.error("IOException-------", e1);
		} catch (DocumentException e1) {
			logger.error("DocumentException-------", e1);
		}
    }
    
    private void manipulatePdf(byte[] src, String dest) throws IOException, DocumentException {
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

	    over.restoreState();
	    stamper.close();
	    reader.close();
	}
}

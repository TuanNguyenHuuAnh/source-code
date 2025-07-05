/*******************************************************************************
 * Class        ：CommonPdfUltil
 * Created date ：2020/06/09
 * Lasted date  ：2020/06/09
 * Author       ：DaiTrieu
 * Change log   ：2020/06/09：01-00 DaiTrieu create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.Approver2Dto;
import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.common.utils.LocationTextExtractionStrategyWithPosition.Coordinate;


/**
 * <p>
 * CommonPdfUltil
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public class CommonPdfUltil {
	
	/** The Constant SIZE_A4. */
	public static final String SIZE_A4 = "A4";
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CommonPdfUltil.class);

	/**
     * <p>
     * Get page size.
     * </p>
     *
     * @param fileName
     *            type {@link String}
     * @param bytes
     *            type {@link byte[]}
     * @return {@link String}
     * @author tantm
     */
	public static String getPageSize(String fileName, byte[] bytes) {
		String result = StringUtils.EMPTY;
		try {
			PdfReader reader = new PdfReader(bytes);
			Rectangle pagesize = reader.getPageSize(reader.getNumberOfPages());
			double pdfWith =  Math.floor(pagesize.getWidth());
			if (DebugLogger.isDebugEnabled()) {
				DebugLogger.debug("[PT Tracking] | [PdfUltils_getPageSize] | [%d] | [fileName: %s] | [pdfWith: %f]",
						Thread.currentThread().getId(), fileName, pdfWith);
			}

			if (pdfWith <= PageSize.A4.getWidth() && pdfWith > PageSize.A5.getWidth()) {
				result = SIZE_A4;
			}

			reader.close();
		} catch (Exception e) {
			logger.error("[getPageSize]", e);
		}

		if (DebugLogger.isDebugEnabled()) {
			DebugLogger.debug("[PT Tracking] | [PdfUltils_getPageSize] | [%d] | [fileName: %s] | [pageSize: %s]",
					Thread.currentThread().getId(), fileName, result);
		}
		
		return result;
	}
	
	/**
     * <p>
     * Scale by rectangle.
     * </p>
     *
     * @param fileName
     *            type {@link String}
     * @param fileBytes
     *            type {@link byte[]}
     * @param rectangle
     *            type {@link Rectangle}
     * @return {@link byte[]}
     * @author tantm
     */
	public static byte[] scaleByRectangle(String fileName, byte[] fileBytes, Rectangle rectangle) {

		boolean isDebugEnabled = DebugLogger.isDebugEnabled();
		if (isDebugEnabled) {
			DebugLogger.debug(
					"[PT Tracking] | [PdfUltils_ScaleByRectangle] | [%d] | [fileName: %s] | [rectangle: %f x %f]",
					Thread.currentThread().getId(), fileName, rectangle.getWidth(), rectangle.getHeight());
		}

		byte[] outBytes = null;
		try {

			Document doc = new Document(rectangle);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			PdfWriter writer = PdfWriter.getInstance(doc, outputStream);

			doc.open();

			PdfContentByte cb = writer.getDirectContent();

			PdfReader reader = new PdfReader(fileBytes);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				PdfImportedPage page = writer.getImportedPage(reader, i);

				Rectangle pagesize = reader.getPageSizeWithRotation(i);
				float oWidth = pagesize.getWidth();
				float oHeight = pagesize.getHeight();
				float scale = getScale(oWidth, oHeight, rectangle);
				float scaledWidth = oWidth * scale;
				float scaledHeight = oHeight * scale;
				int rotation = pagesize.getRotation();

				doc.newPage();

				AffineTransform transform = new AffineTransform(scale, 0, 0, scale, 0, 0);
				switch (rotation) {
				case 0:
					cb.addTemplate(page, transform);
					break;
				case 90:
					AffineTransform rotate90 = new AffineTransform(0, -1f, 1f, 0, 0, scaledHeight);
					rotate90.concatenate(transform);
					cb.addTemplate(page, rotate90);
					break;
				case 180:
					AffineTransform rotate180 = new AffineTransform(-1f, 0, 0, -1f, scaledWidth, scaledHeight);
					rotate180.concatenate(transform);
					cb.addTemplate(page, rotate180);
					break;
				case 270:
					AffineTransform rotate270 = new AffineTransform(0, 1f, -1f, 0, scaledWidth, 0);
					rotate270.concatenate(transform);
					cb.addTemplate(page, rotate270);
					break;
				default:
					cb.addTemplate(page, scale, 0, 0, scale, 0, 0);
				}
			}

			doc.close();
			reader.close();
			writer.close();

			outBytes = outputStream.toByteArray();

			if (isDebugEnabled) {
				DebugLogger.debug(
						"[PT Tracking] | [PdfUltils_ScaleByRectangle_success] | [%d] | [fileName: %s] | [rectangle: %f x %f]",
						Thread.currentThread().getId(), fileName, rectangle.getWidth(), rectangle.getHeight());
			}

		} catch (Exception e) {
			logger.error("[PdfUltils_ScaleByRectangle]", e);
		}

		return outBytes;
	}
	
	/**
     * <p>
     * Get scale.
     * </p>
     *
     * @param width
     *            type {@link float}
     * @param height
     *            type {@link float}
     * @param rectangle
     *            type {@link Rectangle}
     * @return {@link float}
     * @author tantm
     */
	private static float getScale(float width, float height, Rectangle rectangle) {
	    float scaleX = rectangle.getWidth() / width;
	    float scaleY = rectangle.getHeight() / height;
	    return Math.min(scaleX, scaleY);
	}
	
    /**
     * <p>
     * Add hyperlink at footer.
     * </p>
     *
     * @param byteArray
     *            type {@link byte[]}
     * @param docCode
     *            type {@link String}
     * @param docUrl
     *            type {@link String}
     * @param uploadDate
     *            type {@link Date}
     * @param resourceLoader
     *            type {@link ResourceLoader}
     * @return {@link byte[]}
     * @author tantm
     */
    public static byte[] addHyperlinkAtFooter(byte[] byteArray, String docCode, String docUrl, Date uploadDate,
            ResourceLoader resourceLoader) {
        File file = null;
        String uploadDateStr = CommonDateUtil.formatDateToString(uploadDate, CommonConstant.DDMMYYYY_SLASH);
        try {
            // Create temp file
            file = File.createTempFile("temp", ".pdf");

            // Read pdf file
            PdfContentByte content;
            PdfReader pdfReader = new PdfReader(byteArray);
            PdfReader.unethicalreading = true;

            PdfAction action = new PdfAction(docUrl);

            // Create stamper
            PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(file));

            // Init font and size
            int fontSize = 9;
            Rectangle linkLocation = null;
            PdfAnnotation link = null;
            FontFactory.register(resourceLoader.getResource("resources/Calibri_Font/calibri.ttf").getFile().getAbsolutePath());
            Font font = FontFactory.getFont("Calibri", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, fontSize);
            BaseFont baseFont = font.getBaseFont();
            BaseColor baseColor = new BaseColor(165, 165, 165);
            PdfBorderArray border = new PdfBorderArray(0, 0, 0);

            Rectangle pagesize = pdfReader.getPageSize(pdfReader.getNumberOfPages());
            float pdfWidth = pagesize.getWidth();
            float pdfHeight = pagesize.getHeight();
            float ulx = 18;
            float uly = pdfHeight - 11 - baseFont.getAscentPoint(docCode, fontSize);

            // Loop page from pdf file
            for (int pageNr = 1; pageNr <= pdfReader.getNumberOfPages(); pageNr++) {
                // Add upper left text
                content = stamper.getOverContent(pageNr);
                content.setFontAndSize(font.getBaseFont(), fontSize);
                content.setColorFill(baseColor);
                content.beginText();
                content.showTextAligned(Element.ALIGN_LEFT, docCode, ulx, uly, 0f);
                content.showTextAligned(Element.ALIGN_RIGHT, uploadDateStr, pdfWidth - ulx, uly, 0f);
                content.endText();

                // Add hyperlink
                linkLocation = new Rectangle(ulx - 2, uly - 2, ulx - 2 + baseFont.getWidthPoint(docCode, fontSize),
                        uly - 2 + baseFont.getAscentPoint(docCode, fontSize));
                link = PdfAnnotation.createLink(stamper.getWriter(), linkLocation, PdfAnnotation.HIGHLIGHT_INVERT, action);
                link.setBorder(border);
                link.setDefaultAppearanceString(content);
                stamper.addAnnotation(link, pageNr);
            }
            stamper.close();
            byte[] fileByte = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return fileByte;
        } catch (IOException | DocumentException e) {
            logger.error("[addHyperlinkAtFooter] Error", e);
            return null;
        } finally {
            if (null != file && file.exists()) {
                file.delete();
            }
        }
    }
    
    /**
     * <p>
     * Add sign box to pdf.
     * </p>
     *
     * @param byteArrayFilePdf
     *            type {@link byte[]}
     * @param keySearch
     *            type {@link String}
     * @param signLeft
     *            type {@link boolean}
     * @param approverDto
     *            type {@link Approver2Dto}
     * @param byteArraySignatureImage
     *            type {@link byte[]}
     * @param byteArrayCheckImage
     *            type {@link byte[]}
     * @param fontPath
     *            type {@link String}
     * @return {@link byte[]}
     * @author tantm
     */
    public static byte[] addSignBoxToPdf(byte[] byteArrayFilePdf, String keySearch, boolean signLeft, Approver2Dto approverDto,
            byte[] byteArraySignatureImage, byte[] byteArrayCheckImage, String fontPath) {
        File file = null;
        float widthSignBox = 220;
        float heightSignBox = 110;
        float padding = 0;
        float widthSignatureImage = 80;
        float heightSignatureImage = 60;
        float checkImageSize = 18;
        float widthApproveTime = 110;
        float llx = 0;
        float lly = 195;
        float margin = 20;

        try {
            // Create temp file
            file = File.createTempFile("temp", ".pdf");
            file.deleteOnExit();
            // Read pdf file
            PdfReader pdfReader = new PdfReader(byteArrayFilePdf);
            float pageWidth = pdfReader.getPageSize(1).getWidth();
            Coordinate result = null;

            // Find by key search
            if (StringUtils.isNoneBlank(keySearch)) {
                PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
                LocationTextExtractionStrategyWithPosition strategy = new LocationTextExtractionStrategyWithPosition(null);
                parser.processContent(1, strategy, Collections.emptyMap()).getResultantText();
                result = strategy.calcCoordinateOfKeySearch(keySearch);
            }

            // Check result
            if (null != result && result.getLlx() > 0 && result.getLly() > 0) {
                lly = result.getLly();
                Rectangle box = new Rectangle(result.getLlx(), result.getLly(), result.getUrx(), result.getUry());
                llx = result.getLlx() + (box.getWidth() / 2) - (widthSignBox / 2) + 8;
            } else {
                // Calculate for case find key search not found.
                if (signLeft) {
                    llx = (pageWidth / 2) - (pageWidth / 4) - (widthSignBox / 2) + margin;
                } else {
                    llx = (pageWidth / 2) + (pageWidth / 4) - (widthSignBox / 2) - margin;
                }
            }

            float urx = llx + widthSignBox;
            float ury = lly - heightSignBox;

            // Create stamper
            PdfStamper stamper = new PdfStamper(pdfReader, new FileOutputStream(file));
            PdfContentByte pdfContentByte = stamper.getOverContent(1);

            Rectangle signBox = new Rectangle(llx, lly, urx, ury);
            PdfTemplate signTemplate = PdfTemplate.createTemplate(stamper.getWriter(), signBox.getWidth(), signBox.getHeight());
            // appearance.setColorStroke(baseColorGreen);
            signTemplate.rectangle(0, 0, signBox.getWidth(), signBox.getHeight());
            // appearance.stroke();

            Rectangle recBox = new Rectangle(0, 0, signBox.getWidth(), signBox.getHeight());
            // org.setBorderColor(baseColorRed);

            FontFactory.register(fontPath);
            Font fontBold = FontFactory.getFont("Calibri Light", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
            fontBold.setColor(BaseColor.BLACK);
            fontBold.setStyle(Font.BOLD);

            Font fontItalic = FontFactory.getFont("Calibri Light", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
            fontItalic.setColor(BaseColor.BLACK);
            fontItalic.setStyle(Font.ITALIC);

            ColumnText colText = new ColumnText(signTemplate);
            colText.setSimpleColumn(recBox);
            PdfPTable tblSign = new PdfPTable(1);
            tblSign.setWidthPercentage(100);

            // Title name
            PdfPCell cellSign = new PdfPCell(new Phrase(approverDto.getTitleName(), fontBold));
            cellSign.setBorder(0);
            cellSign.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSign.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellSign.setPadding(padding);
            tblSign.addCell(cellSign);

            // Signature image
            Image signatureImage = Image.getInstance(byteArraySignatureImage);
            float proportion = 0;
            if (signatureImage.getWidth() > widthSignatureImage) {
                proportion = signatureImage.getWidth() / widthSignatureImage;
                signatureImage.scaleAbsolute(widthSignatureImage, signatureImage.getHeight() / proportion);
            } else if (signatureImage.getHeight() > heightSignatureImage) {
                proportion = signatureImage.getHeight() / heightSignatureImage;
                signatureImage.scaleAbsolute(signatureImage.getWidth() / proportion, heightSignatureImage);
            }
            cellSign = new PdfPCell(signatureImage);
            cellSign.setBorder(0);
            cellSign.setFixedHeight(heightSignatureImage + 2 * padding);
            cellSign.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSign.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellSign.setPadding(padding);
            tblSign.addCell(cellSign);

            // Approve name
            cellSign = new PdfPCell(new Phrase(approverDto.getApproverName(), fontBold));
            cellSign.setBorder(0);
            cellSign.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSign.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellSign.setPadding(0);
            tblSign.addCell(cellSign);

            colText.addElement(tblSign);
            Phrase phraseSigned = new Phrase("Đã ký: " + approverDto.getApproveTime(), fontItalic);
            widthApproveTime = ColumnText.getWidth(phraseSigned) + 2 * padding;

            // Check digital sign
            if ("1".equals(approverDto.getIsSigned())) {
                tblSign = new PdfPTable(2);
                float[] cln = { checkImageSize + 2 * padding, widthApproveTime };
                tblSign.setWidthPercentage(cln, signBox);

                // Signature image
                Image checkImage = Image.getInstance(byteArrayCheckImage);
                checkImage.scaleAbsolute(checkImageSize, checkImageSize);
                cellSign = new PdfPCell(checkImage);
                cellSign.setBorder(0);
                cellSign.setFixedHeight(checkImageSize);
                cellSign.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSign.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSign.setPadding(0);
                tblSign.addCell(cellSign);
            } else {
                tblSign = new PdfPTable(1);
                float[] cln = { widthApproveTime };
                tblSign.setWidthPercentage(cln, signBox);
            }

            cellSign = new PdfPCell(phraseSigned);
            cellSign.setBorder(0);
            cellSign.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSign.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellSign.setPadding(0);
            cellSign.setFixedHeight(checkImageSize);
            tblSign.addCell(cellSign);

            colText.addElement(tblSign);
            colText.go();
            pdfContentByte.addTemplate(signTemplate, llx, lly);
            stamper.close();
            byte[] fileByte = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return fileByte;
        } catch (Exception e) {
            logger.error("[addSignBoxToPdf] Error", e);
        }
        return null;
    }

    /**
     * <p>
     * The main method.
     * </p>
     *
     * @param args
     *            the arguments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static void main(String[] args) throws IOException {
        byte[] byteArrayPdf = Files
                .readAllBytes(Paths.get("C:\\TVD_DATA\\UNIT_CORP\\BLABLA\\SACOM\\sacom_test\\sac.pdf"));
        Approver2Dto approverDto = new Approver2Dto();
        approverDto.setTitleName("GIÁM ĐỐC TRUNG TÂM PHÁT TRIỂN ỨNG DỤNG");
        approverDto.setApproverName("Giả Quốc Bảo");
        approverDto.setApproveTime("30-12-2020 06:00:01");
        approverDto.setIsSigned("0");
        byte[] byteArraySignatureImage = Files.readAllBytes(
                Paths.get("C:\\TVD_DATA\\UNIT_CORP\\BLABLA\\SACOM\\sacom_test\\sign1.png"));
        byte[] byteArrayCheckImage = Files.readAllBytes(
                Paths.get("C:\\TVD_DATA\\UNIT_CORP\\BLABLA\\SACOM\\sacom_test\\check.jpg"));
        String fontPath = "C:\\TVD_DATA\\UNIT_CORP\\BLABLA\\SACOM\\sacom_test\\calibril.ttf";
        byte[] byteArray = addSignBoxToPdf(byteArrayPdf, "BÊN NHẬN" , false, approverDto, byteArraySignatureImage, byteArrayCheckImage, fontPath);
        
        //
        Approver2Dto approver2 = new Approver2Dto();
        approver2.setTitleName("GIÁM ĐỐC KHỐI CÔNG NGHỆ THÔNG TIN");
        approver2.setApproverName("Trần Thái Bình");
        approver2.setApproveTime("30-12-2020 07:00:01");
        approver2.setIsSigned("1");
        byte[] byteArraySignatureImage2 = Files.readAllBytes(
                Paths.get("C:\\TVD_DATA\\UNIT_CORP\\BLABLA\\SACOM\\sacom_test\\sign1.png"));
        
        byteArray = addSignBoxToPdf(byteArray, "BÊN GIAO" , true, approver2, byteArraySignatureImage2, byteArrayCheckImage, fontPath);
        
        File filetmp = File.createTempFile("temp", ".pdf");
        FileUtils.writeByteArrayToFile(filetmp, byteArray);
        System.out.println(filetmp.getAbsolutePath());

    }
}

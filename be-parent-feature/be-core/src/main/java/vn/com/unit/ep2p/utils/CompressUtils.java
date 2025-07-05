package vn.com.unit.ep2p.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressUtils {

    private static float compQualDefault = 0.85f;
    private static float compQualMax = 1f;

    /** logger */
    private final static Logger logger = LoggerFactory.getLogger(CompressUtils.class);

    public static void compressImg(String pathFileName, boolean isReplace) {
        try {
            File imageFile = new File(pathFileName);
            String[] strs = pathFileName.split("\\.");
            String extend = strs[strs.length - 1];
            String compressedImageFile = pathFileName.replace("." + extend, "_compressed." + extend);

            InputStream is = new FileInputStream(imageFile);
            if (FileReaderUtils.isImgFile(pathFileName)) {
                float quality = 0.5f;

                // create a BufferedImage as the result of decoding the supplied InputStream
                BufferedImage image = ImageIO.read(is);

                // get all image writers for JPG format
                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extend);

                if (!writers.hasNext()) {
                    return;
                }

                ImageWriter writer = (ImageWriter) writers.next();
                ImageWriteParam param = writer.getDefaultWriteParam();

                // compress to a given quality
                if (param.canWriteCompressed()) {
                    OutputStream os = new FileOutputStream(compressedImageFile);
                    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
                    writer.setOutput(ios);

                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(quality);

                    // appends a complete image stream containing a single image and
                    // associated stream and image metadata and thumbnails to the output
                    writer.write(null, new IIOImage(image, null, null), param);

                    ios.close();
                    os.close();

                    writer.dispose();
                } else {
                    File compressed = new File(compressedImageFile);
                    FileUtils.copyFile(imageFile, compressed);
                }
            }

            // close all streams
            is.close();

            if (isReplace) {
                File compressed = new File(compressedImageFile);
                FileUtils.copyFile(compressed, imageFile);
                compressed.delete();
            }
        } catch (Exception e) {
            logger.error("#####compressImg######", e);
        }
    }
    
    public static void shrinkPDFToImg(String inputFile, String outputFile) {
        try {
            PDDocument pdDocument = new PDDocument();
            PDDocument oDocument = PDDocument.load(new File(inputFile));
            PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
            int numberOfPages = oDocument.getNumberOfPages();
            PDPage page = null;

            for (int i = 0; i < numberOfPages; i++) {
                page = new PDPage(PDRectangle.LETTER);
                // BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 180, ImageType.RGB);
                PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
                PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
                float newHeight = PDRectangle.LETTER.getHeight();
                float newWidth = PDRectangle.LETTER.getWidth();
                contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
                contentStream.close();

                pdDocument.addPage(page);
            }

            pdDocument.save(outputFile);
            pdDocument.close();

        } catch (IOException e) {
            logger.error("Exception ", e);
        }
    }

    /**
     * @param inputFile
     * @param compQual:  [0, 100]: độ giảm tương ứng
     * @param inputFile: [true/false]: có hình ảnh png hay không
     */
    public static void shrinkPDF(String inputFile, float compQual, boolean tiff) {
        try {
            if (compQual < 0) {
                compQual = compQualDefault;
            } else if (compQual > 100) {
                compQual = compQualMax;
            } else {
                compQual = compQual / 100;
            }

            final RandomAccessBufferedFileInputStream rabfis = new RandomAccessBufferedFileInputStream(inputFile);
            final PDFParser parser = new PDFParser(rabfis);
            parser.parse();
            final PDDocument doc = parser.getPDDocument();
            final PDPageTree pages = doc.getPages();
            final ImageWriter imgWriter;
            final ImageWriteParam iwp;
            if (tiff) {
                final Iterator<ImageWriter> tiffWriters = ImageIO.getImageWritersBySuffix("png");
                imgWriter = tiffWriters.next();
                iwp = imgWriter.getDefaultWriteParam();
                // iwp.setCompressionMode(ImageWriteParam.MODE_DISABLED);
            } else {
                final Iterator<ImageWriter> jpgWriters = ImageIO.getImageWritersByFormatName("jpeg");
                imgWriter = jpgWriters.next();
                iwp = imgWriter.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(compQual);
            }
            for (PDPage p : pages) {
                scanResources(p.getResources(), doc, imgWriter, iwp, tiff);
            }

            doc.save(new File(inputFile));
        } catch (Exception e) {
            logger.error("Exception ", e);
        }
    }

    private static void scanResources(final PDResources rList, final PDDocument doc, final ImageWriter imgWriter,
            final ImageWriteParam iwp, boolean tiff) throws FileNotFoundException, IOException {
        Iterable<COSName> xNames = rList.getXObjectNames();
        for (COSName xName : xNames) {
            final PDXObject xObj = rList.getXObject(xName);
            if (xObj instanceof PDFormXObject)
                scanResources(((PDFormXObject) xObj).getResources(), doc, imgWriter, iwp, tiff);
            if (!(xObj instanceof PDImageXObject))
                continue;
            final PDImageXObject img = (PDImageXObject) xObj;
            System.out.println("Compressing image: " + xName.getName());
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imgWriter.setOutput(ImageIO.createImageOutputStream(baos));
            BufferedImage bi = img.getImage();
            IIOImage iioi;
            if (bi.getTransparency() == BufferedImage.OPAQUE) {
                iioi = new IIOImage(bi, null, null);
            } else if (bi.getTransparency() == BufferedImage.TRANSLUCENT) {
                iioi = new IIOImage(img.getOpaqueImage(), null, null);
            } else {
                iioi = new IIOImage(img.getOpaqueImage(), null, null);
            }
            imgWriter.write(null, iioi, iwp);
            final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            final PDImageXObject imgNew;
            if (tiff)
                imgNew = LosslessFactory.createFromImage(doc, img.getImage());
            else
                imgNew = JPEGFactory.createFromStream(doc, bais);
            rList.put(xName, imgNew);
        }
    }
}

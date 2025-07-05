package vn.com.unit.ep2p.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressJPEGFile {

	private static final float COMPRESS_QUALITY = 0.5f;
	private static final String FORMAT_IMAGE = "jpg";
	private static final Logger logger = LoggerFactory.getLogger(CompressJPEGFile.class);

	public static String process(String folderPath, String fileName) throws Exception {
		// document: https://memorynotfound.com/compress-images-java-example/
		String outputName = "";

		File inputFile = new File(folderPath + fileName);
		outputName = fileName.substring(0, fileName.lastIndexOf('.')) + System.nanoTime() + "." + FORMAT_IMAGE;
		File outputFile = new File(folderPath + outputName);
		BufferedImage image = ImageIO.read(inputFile);

		try (OutputStream out = new FileOutputStream(outputFile);) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName(FORMAT_IMAGE).next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(out);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();
			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(COMPRESS_QUALITY);
			}

			writer.write(null, new IIOImage(image, null, null), param);

			ios.close();
			writer.dispose();
			if(!inputFile.delete()){
				logger.error("source not delete");
			}

			return outputName;
		}

	}
}

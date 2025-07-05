package vn.com.unit.ep2p.utils;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.VideoAttributes;
import it.sauronsoftware.jave.VideoSize;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertVideo {
	
	private static final Logger logger = LoggerFactory.getLogger(ConvertVideo.class);
	
	public static String convert(String folderPath, String fileName){
		//document: http://www.sauronsoftware.it/projects/jave/manual.php
		
		//convert to flv:
		//	codec = flv
		//	format = flv
		//convert to mp4:
		//	codec = mpeg4
		//	format = mp4

		String extensao= fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()).toLowerCase();
		String newExtent = ".flv";
		String codec;
		String format;
		String targetStr = "";
		if(extensao.equals("avi") || extensao.equals("wmv") || extensao.equals("wma")){
			codec = "flv";
			format = "flv";
		}else{
			return targetStr;
		}
		
		File source = new File(folderPath + fileName);
		targetStr = fileName.substring(0, fileName.lastIndexOf('.'))+(new Date()).getTime() + newExtent;
		File target = new File(folderPath + targetStr);
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");					//Audio encoder
		audio.setBitRate(new Integer(128000));			//128 kbit/s
		audio.setChannels(new Integer(2));				//1: mono, 2: stereo
		audio.setSamplingRate(new Integer(22050));		//22 KHz
		VideoAttributes video = new VideoAttributes();
		video.setCodec(codec);
//		video.setTag("DIVX");
		video.setBitRate(new Integer(1600000));			//1600 kbit/s
		video.setFrameRate(new Integer(24));			//24 fps
		video.setSize(new VideoSize(600, 300));			//width x height
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat(format);
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
		Encoder encoder = new Encoder();

		try {
			encoder.encode(source, target, attrs);
			if(!source.delete()){
				logger.error("source not delete");
			}
			
		} catch (IllegalArgumentException e) {
			logger.error("_convert_", e);
		} catch (InputFormatException e) {
			logger.error("_convert_", e);
		} catch (EncoderException e) {
			logger.error("_convert_", e);
		}
		
		return targetStr;
	}
}

package vn.com.unit.common.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.dts.utils.DtsStringUtil;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static int sum(int a, int b) {
		return a + b;
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		String[] charList = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "g",
				"h", "i", "j", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "y", "A", "B", "C",
				"D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X",
				"Y" };
		int randMax = charList.length;
		String randString = "";
		for (int pos = 0; pos < length; ++pos) {
			Random random = new Random();
			int randNumber = random.nextInt();
			if (randNumber >= 0) {
				randNumber = randNumber % randMax;
			} else if (randNumber == Integer.MIN_VALUE) {
				randNumber = ((randNumber + 1) * -1) % randMax;
			} else {
				randNumber = (randNumber * -1) % randMax;
			}
			randString = randString + charList[randNumber];
		}
		return randString;
	}

	public static URI appendUri(String uri, String appendQuery) throws URISyntaxException {
		URI oldUri = new URI(uri);

		String newQuery = oldUri.getQuery();
		if (newQuery == null) {
			newQuery = appendQuery;
		} else {
			newQuery += "&" + appendQuery;
		}

		URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(), oldUri.getPath(), newQuery,
				oldUri.getFragment());

		return newUri;
	}

	/**
	 * @param dataComp1
	 * @param dataComp2
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] genMd5Hash(String dataComp1, String dataComp2)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytesOfMessage = (dataComp1.concat(dataComp2)).getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytesOfMessage);
		byte[] hashTokenBytes = md.digest();

		return hashTokenBytes;
	}

	public static String byteToHex(byte b) {
		int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	public static String genMd5HashHexString(String dataComp1, String dataComp2)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] hashBytes = genMd5Hash(dataComp1, dataComp2);
		return bytesToHexString(hashBytes);
	}

	public static String bytesToHexString(byte[] byteArray) {
		String hexString = "";
		for (int i = 0; i < byteArray.length; ++i) {
			hexString = hexString + byteToHex(byteArray[i]);
		}
		return hexString;
	}

	/**
	 * @return
	 */
	public static String randomStringWithTimeStamp() {
		String randString = randomString(20);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()).replace(".", "");
		return randString.concat(timeStamp);
	}

	/**
	 * Convert from Unicode dÃƒÂ¡Ã‚Â»Ã‚Â±ng sÃƒÂ¡Ã‚ÂºÃ‚Âµn sang Unicode
	 * tÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¢ hÃƒÂ¡Ã‚Â»Ã‚Â£p.
	 * 
	 * @param str
	 * @return String
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public static String convertUnicodeToComposite(String str) {
		str = str.replaceAll("\u1EBB", "\u0065\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â»
		str = str.replaceAll("\u00E9", "\u0065\u0301"); // ÃƒÆ’Ã‚Â©
		str = str.replaceAll("\u00E8", "\u0065\u0300"); // ÃƒÆ’Ã‚Â¨
		str = str.replaceAll("\u1EB9", "\u0065\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¹
		str = str.replaceAll("\u1EBD", "\u0065\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Â½
		str = str.replaceAll("\u1EC3", "\u00EA\u0309"); // ÃƒÂ¡Ã‚Â»Ã†â€™
		str = str.replaceAll("\u1EBF", "\u00EA\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¿
		str = str.replaceAll("\u1EC1", "\u00EA\u0300"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u1EC7", "\u00EA\u0323"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¡
		str = str.replaceAll("\u1EC5", "\u00EA\u0303"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¦
		str = str.replaceAll("\u1EF7", "\u0079\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â·
		str = str.replaceAll("\u00FD", "\u0079\u0301"); // ÃƒÆ’Ã‚Â½
		str = str.replaceAll("\u1EF3", "\u0079\u0300"); // ÃƒÂ¡Ã‚Â»Ã‚Â³
		str = str.replaceAll("\u1EF5", "\u0079\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Âµ
		str = str.replaceAll("\u1EF9", "\u0079\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â¹
		str = str.replaceAll("\u1EE7", "\u0075\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â§
		str = str.replaceAll("\u00FA", "\u0075\u0301"); // ÃƒÆ’Ã‚Âº
		str = str.replaceAll("\u00F9", "\u0075\u0300"); // ÃƒÆ’Ã‚Â¹
		str = str.replaceAll("\u1EE5", "\u0075\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â¥
		str = str.replaceAll("\u0169", "\u0075\u0303"); // Ãƒâ€¦Ã‚Â©
		str = str.replaceAll("\u1EED", "\u01B0\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â­
		str = str.replaceAll("\u1EE9", "\u01B0\u0301"); // ÃƒÂ¡Ã‚Â»Ã‚Â©
		str = str.replaceAll("\u1EEB", "\u01B0\u0300"); // ÃƒÂ¡Ã‚Â»Ã‚Â«
		str = str.replaceAll("\u1EF1", "\u01B0\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â±
		str = str.replaceAll("\u1EEF", "\u01B0\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â¯
		str = str.replaceAll("\u1EC9", "\u0069\u0309"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â°
		str = str.replaceAll("\u00ED", "\u0069\u0301"); // ÃƒÆ’Ã‚Â­
		str = str.replaceAll("\u00EC", "\u0069\u0300"); // ÃƒÆ’Ã‚Â¬
		str = str.replaceAll("\u1ECB", "\u0069\u0323"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¹
		str = str.replaceAll("\u0129", "\u0069\u0303"); // Ãƒâ€žÃ‚Â©
		str = str.replaceAll("\u1ECF", "\u006F\u0309"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u00F3", "\u006F\u0301"); // ÃƒÆ’Ã‚Â³
		str = str.replaceAll("\u00F2", "\u006F\u0300"); // ÃƒÆ’Ã‚Â²
		str = str.replaceAll("\u1ECD", "\u006F\u0323"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u00F5", "\u006F\u0303"); // ÃƒÆ’Ã‚Âµ
		str = str.replaceAll("\u1EDF", "\u01A1\u0309"); // ÃƒÂ¡Ã‚Â»Ã…Â¸
		str = str.replaceAll("\u1EDB", "\u01A1\u0301"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Âº
		str = str.replaceAll("\u1EDD", "\u01A1\u0300"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u1EE3", "\u01A1\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â£
		str = str.replaceAll("\u1EE1", "\u01A1\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â¡
		str = str.replaceAll("\u1ED5", "\u00F4\u0309"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¢
		str = str.replaceAll("\u1ED1", "\u00F4\u0301"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ
		str = str.replaceAll("\u1ED3", "\u00F4\u0300"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å“
		str = str.replaceAll("\u1ED9", "\u00F4\u0323"); // ÃƒÂ¡Ã‚Â»Ã¢â€žÂ¢
		str = str.replaceAll("\u1ED7", "\u00F4\u0303"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â€�
		str = str.replaceAll("\u1EA3", "\u0061\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â£
		str = str.replaceAll("\u00E1", "\u0061\u0301"); // ÃƒÆ’Ã‚Â¡
		str = str.replaceAll("\u00E0", "\u0061\u0300"); // ÃƒÆ’Ã‚Â 
		str = str.replaceAll("\u1EA1", "\u0061\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¡
		str = str.replaceAll("\u00E3", "\u0061\u0303"); // ÃƒÆ’Ã‚Â£
		str = str.replaceAll("\u1EB3", "\u0103\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â³
		str = str.replaceAll("\u1EAF", "\u0103\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¯
		str = str.replaceAll("\u1EB1", "\u0103\u0300"); // ÃƒÂ¡Ã‚ÂºÃ‚Â±
		str = str.replaceAll("\u1EB7", "\u0103\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â·
		str = str.replaceAll("\u1EB5", "\u0103\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Âµ
		str = str.replaceAll("\u1EA9", "\u00E2\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â©
		str = str.replaceAll("\u1EA5", "\u00E2\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¥
		str = str.replaceAll("\u1EA7", "\u00E2\u0300"); // ÃƒÂ¡Ã‚ÂºÃ‚Â§
		str = str.replaceAll("\u1EAD", "\u00E2\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â­
		str = str.replaceAll("\u1EAB", "\u00E2\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Â«
		str = str.replaceAll("\u1EBA", "\u0045\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Âº
		str = str.replaceAll("\u00C9", "\u0045\u0301"); // ÃƒÆ’Ã¢â‚¬Â°
		str = str.replaceAll("\u00C8", "\u0045\u0300"); // ÃƒÆ’Ã‹â€ 
		str = str.replaceAll("\u1EB8", "\u0045\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¸
		str = str.replaceAll("\u1EBC", "\u0045\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¼
		str = str.replaceAll("\u1EC2", "\u00CA\u0309"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å¡
		str = str.replaceAll("\u1EBE", "\u00CA\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¾
		str = str.replaceAll("\u1EC0", "\u00CA\u0300"); // ÃƒÂ¡Ã‚Â»Ã¢â€šÂ¬
		str = str.replaceAll("\u1EC6", "\u00CA\u0323"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â 
		str = str.replaceAll("\u1EC4", "\u00CA\u0303"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å¾
		str = str.replaceAll("\u1EF6", "\u0059\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â¶
		str = str.replaceAll("\u00DD", "\u0059\u0301"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u1EF2", "\u0059\u0300"); // ÃƒÂ¡Ã‚Â»Ã‚Â²
		str = str.replaceAll("\u1EF4", "\u0059\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â´
		str = str.replaceAll("\u1EF8", "\u0059\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â¸
		str = str.replaceAll("\u1EE6", "\u0055\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â¦
		str = str.replaceAll("\u00DA", "\u0055\u0301"); // ÃƒÆ’Ã…Â¡
		str = str.replaceAll("\u00D9", "\u0055\u0300"); // ÃƒÆ’Ã¢â€žÂ¢
		str = str.replaceAll("\u1EE4", "\u0055\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â¤
		str = str.replaceAll("\u0168", "\u0055\u0303"); // Ãƒâ€¦Ã‚Â¨
		str = str.replaceAll("\u1EEC", "\u01AF\u0309"); // ÃƒÂ¡Ã‚Â»Ã‚Â¬
		str = str.replaceAll("\u1EE8", "\u01AF\u0301"); // ÃƒÂ¡Ã‚Â»Ã‚Â¨
		str = str.replaceAll("\u1EEA", "\u01AF\u0300"); // ÃƒÂ¡Ã‚Â»Ã‚Âª
		str = str.replaceAll("\u1EF0", "\u01AF\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â°
		str = str.replaceAll("\u1EEE", "\u01AF\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â®
		str = str.replaceAll("\u1EC8", "\u0049\u0309"); // ÃƒÂ¡Ã‚Â»Ã‹â€ 
		str = str.replaceAll("\u00CD", "\u0049\u0301"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u00CC", "\u0049\u0300"); // ÃƒÆ’Ã…â€™
		str = str.replaceAll("\u1ECA", "\u0049\u0323"); // ÃƒÂ¡Ã‚Â»Ã…Â 
		str = str.replaceAll("\u0128", "\u0049\u0303"); // Ãƒâ€žÃ‚Â¨
		str = str.replaceAll("\u1ECE", "\u004F\u0309"); // ÃƒÂ¡Ã‚Â»Ã…Â½
		str = str.replaceAll("\u00D3", "\u004F\u0301"); // ÃƒÆ’Ã¢â‚¬Å“
		str = str.replaceAll("\u00D2", "\u004F\u0300"); // ÃƒÆ’Ã¢â‚¬â„¢
		str = str.replaceAll("\u1ECC", "\u004F\u0323"); // ÃƒÂ¡Ã‚Â»Ã…â€™
		str = str.replaceAll("\u00D5", "\u004F\u0303"); // ÃƒÆ’Ã¢â‚¬Â¢
		str = str.replaceAll("\u1EDE", "\u01A0\u0309"); // ÃƒÂ¡Ã‚Â»Ã…Â¾
		str = str.replaceAll("\u1EDA", "\u01A0\u0301"); // ÃƒÂ¡Ã‚Â»Ã…Â¡
		str = str.replaceAll("\u1EDC", "\u01A0\u0300"); // ÃƒÂ¡Ã‚Â»Ã…â€œ
		str = str.replaceAll("\u1EE2", "\u01A0\u0323"); // ÃƒÂ¡Ã‚Â»Ã‚Â¢
		str = str.replaceAll("\u1EE0", "\u01A0\u0303"); // ÃƒÂ¡Ã‚Â»Ã‚Â 
		str = str.replaceAll("\u1ED4", "\u00D4\u0309"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬ï¿½
		str = str.replaceAll("\u1ED0", "\u00D4\u0301"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u1ED2", "\u00D4\u0300"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â„¢
		str = str.replaceAll("\u1ED8", "\u00D4\u0323"); // ÃƒÂ¡Ã‚Â»Ã‹Å“
		str = str.replaceAll("\u1ED6", "\u00D4\u0303"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â€œ
		str = str.replaceAll("\u1EA2", "\u0041\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¢
		str = str.replaceAll("\u00C1", "\u0041\u0301"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u00C0", "\u0041\u0300"); // ÃƒÆ’Ã¢â€šÂ¬
		str = str.replaceAll("\u1EA0", "\u0041\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â 
		str = str.replaceAll("\u00C3", "\u0041\u0303"); // ÃƒÆ’Ã†â€™
		str = str.replaceAll("\u1EB2", "\u0102\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â²
		str = str.replaceAll("\u1EAE", "\u0102\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â®
		str = str.replaceAll("\u1EB0", "\u0102\u0300"); // ÃƒÂ¡Ã‚ÂºÃ‚Â°
		str = str.replaceAll("\u1EB6", "\u0102\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¶
		str = str.replaceAll("\u1EB4", "\u0102\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Â´
		str = str.replaceAll("\u1EA8", "\u00C2\u0309"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¨
		str = str.replaceAll("\u1EA4", "\u00C2\u0301"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¤
		str = str.replaceAll("\u1EA6", "\u00C2\u0300"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¦
		str = str.replaceAll("\u1EAC", "\u00C2\u0323"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¬
		str = str.replaceAll("\u1EAA", "\u00C2\u0303"); // ÃƒÂ¡Ã‚ÂºÃ‚Âª
		return str;
	}

	/**
	 * Convert from Unicode tÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¢ hÃƒÂ¡Ã‚Â»Ã‚Â£p to Unicode
	 * dÃƒÂ¡Ã‚Â»Ã‚Â±ng sÃƒÂ¡Ã‚ÂºÃ‚Âµn
	 * 
	 * @param str
	 * @return String
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public static String convertComposite2Unicode(String str) {
		str = str.replaceAll("\u0065\u0309", "\u1EBB"); // ÃƒÂ¡Ã‚ÂºÃ‚Â»
		str = str.replaceAll("\u0065\u0301", "\u00E9"); // ÃƒÆ’Ã‚Â©
		str = str.replaceAll("\u0065\u0300", "\u00E8"); // ÃƒÆ’Ã‚Â¨
		str = str.replaceAll("\u0065\u0323", "\u1EB9"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¹
		str = str.replaceAll("\u0065\u0303", "\u1EBD"); // ÃƒÂ¡Ã‚ÂºÃ‚Â½
		str = str.replaceAll("\u00EA\u0309", "\u1EC3"); // ÃƒÂ¡Ã‚Â»Ã†â€™
		str = str.replaceAll("\u00EA\u0301", "\u1EBF"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¿
		str = str.replaceAll("\u00EA\u0300", "\u1EC1"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u00EA\u0323", "\u1EC7"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¡
		str = str.replaceAll("\u00EA\u0303", "\u1EC5"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¦
		str = str.replaceAll("\u0079\u0309", "\u1EF7"); // ÃƒÂ¡Ã‚Â»Ã‚Â·
		str = str.replaceAll("\u0079\u0301", "\u00FD"); // ÃƒÆ’Ã‚Â½
		str = str.replaceAll("\u0079\u0300", "\u1EF3"); // ÃƒÂ¡Ã‚Â»Ã‚Â³
		str = str.replaceAll("\u0079\u0323", "\u1EF5"); // ÃƒÂ¡Ã‚Â»Ã‚Âµ
		str = str.replaceAll("\u0079\u0303", "\u1EF9"); // ÃƒÂ¡Ã‚Â»Ã‚Â¹
		str = str.replaceAll("\u0075\u0309", "\u1EE7"); // ÃƒÂ¡Ã‚Â»Ã‚Â§
		str = str.replaceAll("\u0075\u0301", "\u00FA"); // ÃƒÆ’Ã‚Âº
		str = str.replaceAll("\u0075\u0300", "\u00F9"); // ÃƒÆ’Ã‚Â¹
		str = str.replaceAll("\u0075\u0323", "\u1EE5"); // ÃƒÂ¡Ã‚Â»Ã‚Â¥
		str = str.replaceAll("\u0075\u0303", "\u0169"); // Ãƒâ€¦Ã‚Â©
		str = str.replaceAll("\u01B0\u0309", "\u1EED"); // ÃƒÂ¡Ã‚Â»Ã‚Â­
		str = str.replaceAll("\u01B0\u0301", "\u1EE9"); // ÃƒÂ¡Ã‚Â»Ã‚Â©
		str = str.replaceAll("\u01B0\u0300", "\u1EEB"); // ÃƒÂ¡Ã‚Â»Ã‚Â«
		str = str.replaceAll("\u01B0\u0323", "\u1EF1"); // ÃƒÂ¡Ã‚Â»Ã‚Â±
		str = str.replaceAll("\u01B0\u0303", "\u1EEF"); // ÃƒÂ¡Ã‚Â»Ã‚Â¯
		str = str.replaceAll("\u0069\u0309", "\u1EC9"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â°
		str = str.replaceAll("\u0069\u0301", "\u00ED"); // ÃƒÆ’Ã‚Â­
		str = str.replaceAll("\u0069\u0300", "\u00EC"); // ÃƒÆ’Ã‚Â¬
		str = str.replaceAll("\u0069\u0323", "\u1ECB"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¹
		str = str.replaceAll("\u0069\u0303", "\u0129"); // Ãƒâ€žÃ‚Â©
		str = str.replaceAll("\u006F\u0309", "\u1ECF"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u006F\u0301", "\u00F3"); // ÃƒÆ’Ã‚Â³
		str = str.replaceAll("\u006F\u0300", "\u00F2"); // ÃƒÆ’Ã‚Â²
		str = str.replaceAll("\u006F\u0323", "\u1ECD"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u006F\u0303", "\u00F5"); // ÃƒÆ’Ã‚Âµ
		str = str.replaceAll("\u01A1\u0309", "\u1EDF"); // ÃƒÂ¡Ã‚Â»Ã…Â¸
		str = str.replaceAll("\u01A1\u0301", "\u1EDB"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Âº
		str = str.replaceAll("\u01A1\u0300", "\u1EDD"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u01A1\u0323", "\u1EE3"); // ÃƒÂ¡Ã‚Â»Ã‚Â£
		str = str.replaceAll("\u01A1\u0303", "\u1EE1"); // ÃƒÂ¡Ã‚Â»Ã‚Â¡
		str = str.replaceAll("\u00F4\u0309", "\u1ED5"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â¢
		str = str.replaceAll("\u00F4\u0301", "\u1ED1"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Ëœ
		str = str.replaceAll("\u00F4\u0300", "\u1ED3"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å“
		str = str.replaceAll("\u00F4\u0323", "\u1ED9"); // ÃƒÂ¡Ã‚Â»Ã¢â€žÂ¢
		str = str.replaceAll("\u00F4\u0303", "\u1ED7"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â€�
		str = str.replaceAll("\u0061\u0309", "\u1EA3"); // ÃƒÂ¡Ã‚ÂºÃ‚Â£
		str = str.replaceAll("\u0061\u0301", "\u00E1"); // ÃƒÆ’Ã‚Â¡
		str = str.replaceAll("\u0061\u0300", "\u00E0"); // ÃƒÆ’Ã‚Â 
		str = str.replaceAll("\u0061\u0323", "\u1EA1"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¡
		str = str.replaceAll("\u0061\u0303", "\u00E3"); // ÃƒÆ’Ã‚Â£
		str = str.replaceAll("\u0103\u0309", "\u1EB3"); // ÃƒÂ¡Ã‚ÂºÃ‚Â³
		str = str.replaceAll("\u0103\u0301", "\u1EAF"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¯
		str = str.replaceAll("\u0103\u0300", "\u1EB1"); // ÃƒÂ¡Ã‚ÂºÃ‚Â±
		str = str.replaceAll("\u0103\u0323", "\u1EB7"); // ÃƒÂ¡Ã‚ÂºÃ‚Â·
		str = str.replaceAll("\u0103\u0303", "\u1EB5"); // ÃƒÂ¡Ã‚ÂºÃ‚Âµ
		str = str.replaceAll("\u00E2\u0309", "\u1EA9"); // ÃƒÂ¡Ã‚ÂºÃ‚Â©
		str = str.replaceAll("\u00E2\u0301", "\u1EA5"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¥
		str = str.replaceAll("\u00E2\u0300", "\u1EA7"); // ÃƒÂ¡Ã‚ÂºÃ‚Â§
		str = str.replaceAll("\u00E2\u0323", "\u1EAD"); // ÃƒÂ¡Ã‚ÂºÃ‚Â­
		str = str.replaceAll("\u00E2\u0303", "\u1EAB"); // ÃƒÂ¡Ã‚ÂºÃ‚Â«
		str = str.replaceAll("\u0045\u0309", "\u1EBA"); // ÃƒÂ¡Ã‚ÂºÃ‚Âº
		str = str.replaceAll("\u0045\u0301", "\u00C9"); // ÃƒÆ’Ã¢â‚¬Â°
		str = str.replaceAll("\u0045\u0300", "\u00C8"); // ÃƒÆ’Ã‹â€ 
		str = str.replaceAll("\u0045\u0323", "\u1EB8"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¸
		str = str.replaceAll("\u0045\u0303", "\u1EBC"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¼
		str = str.replaceAll("\u00CA\u0309", "\u1EC2"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å¡
		str = str.replaceAll("\u00CA\u0301", "\u1EBE"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¾
		str = str.replaceAll("\u00CA\u0300", "\u1EC0"); // ÃƒÂ¡Ã‚Â»Ã¢â€šÂ¬
		str = str.replaceAll("\u00CA\u0323", "\u1EC6"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Â 
		str = str.replaceAll("\u00CA\u0303", "\u1EC4"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬Å¾
		str = str.replaceAll("\u0059\u0309", "\u1EF6"); // ÃƒÂ¡Ã‚Â»Ã‚Â¶
		str = str.replaceAll("\u0059\u0301", "\u00DD"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u0059\u0300", "\u1EF2"); // ÃƒÂ¡Ã‚Â»Ã‚Â²
		str = str.replaceAll("\u0059\u0323", "\u1EF4"); // ÃƒÂ¡Ã‚Â»Ã‚Â´
		str = str.replaceAll("\u0059\u0303", "\u1EF8"); // ÃƒÂ¡Ã‚Â»Ã‚Â¸
		str = str.replaceAll("\u0055\u0309", "\u1EE6"); // ÃƒÂ¡Ã‚Â»Ã‚Â¦
		str = str.replaceAll("\u0055\u0301", "\u00DA"); // ÃƒÆ’Ã…Â¡
		str = str.replaceAll("\u0055\u0300", "\u00D9"); // ÃƒÆ’Ã¢â€žÂ¢
		str = str.replaceAll("\u0055\u0323", "\u1EE4"); // ÃƒÂ¡Ã‚Â»Ã‚Â¤
		str = str.replaceAll("\u0055\u0303", "\u0168"); // Ãƒâ€¦Ã‚Â¨
		str = str.replaceAll("\u01AF\u0309", "\u1EEC"); // ÃƒÂ¡Ã‚Â»Ã‚Â¬
		str = str.replaceAll("\u01AF\u0301", "\u1EE8"); // ÃƒÂ¡Ã‚Â»Ã‚Â¨
		str = str.replaceAll("\u01AF\u0300", "\u1EEA"); // ÃƒÂ¡Ã‚Â»Ã‚Âª
		str = str.replaceAll("\u01AF\u0323", "\u1EF0"); // ÃƒÂ¡Ã‚Â»Ã‚Â°
		str = str.replaceAll("\u01AF\u0303", "\u1EEE"); // ÃƒÂ¡Ã‚Â»Ã‚Â®
		str = str.replaceAll("\u0049\u0309", "\u1EC8"); // ÃƒÂ¡Ã‚Â»Ã‹â€ 
		str = str.replaceAll("\u0049\u0301", "\u00CD"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u0049\u0300", "\u00CC"); // ÃƒÆ’Ã…â€™
		str = str.replaceAll("\u0049\u0323", "\u1ECA"); // ÃƒÂ¡Ã‚Â»Ã…Â 
		str = str.replaceAll("\u0049\u0303", "\u0128"); // Ãƒâ€žÃ‚Â¨
		str = str.replaceAll("\u004F\u0309", "\u1ECE"); // ÃƒÂ¡Ã‚Â»Ã…Â½
		str = str.replaceAll("\u004F\u0301", "\u00D3"); // ÃƒÆ’Ã¢â‚¬Å“
		str = str.replaceAll("\u004F\u0300", "\u00D2"); // ÃƒÆ’Ã¢â‚¬â„¢
		str = str.replaceAll("\u004F\u0323", "\u1ECC"); // ÃƒÂ¡Ã‚Â»Ã…â€™
		str = str.replaceAll("\u004F\u0303", "\u00D5"); // ÃƒÆ’Ã¢â‚¬Â¢
		str = str.replaceAll("\u01A0\u0309", "\u1EDE"); // ÃƒÂ¡Ã‚Â»Ã…Â¾
		str = str.replaceAll("\u01A0\u0301", "\u1EDA"); // ÃƒÂ¡Ã‚Â»Ã…Â¡
		str = str.replaceAll("\u01A0\u0300", "\u1EDC"); // ÃƒÂ¡Ã‚Â»Ã…â€œ
		str = str.replaceAll("\u01A0\u0323", "\u1EE2"); // ÃƒÂ¡Ã‚Â»Ã‚Â¢
		str = str.replaceAll("\u01A0\u0303", "\u1EE0"); // ÃƒÂ¡Ã‚Â»Ã‚Â 
		str = str.replaceAll("\u00D4\u0309", "\u1ED4"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬ï¿½
		str = str.replaceAll("\u00D4\u0301", "\u1ED0"); // ÃƒÂ¡Ã‚Â»Ã¯Â¿Â½
		str = str.replaceAll("\u00D4\u0300", "\u1ED2"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â„¢
		str = str.replaceAll("\u00D4\u0323", "\u1ED8"); // ÃƒÂ¡Ã‚Â»Ã‹Å“
		str = str.replaceAll("\u00D4\u0303", "\u1ED6"); // ÃƒÂ¡Ã‚Â»Ã¢â‚¬â€œ
		str = str.replaceAll("\u0041\u0309", "\u1EA2"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¢
		str = str.replaceAll("\u0041\u0301", "\u00C1"); // ÃƒÆ’Ã¯Â¿Â½
		str = str.replaceAll("\u0041\u0300", "\u00C0"); // ÃƒÆ’Ã¢â€šÂ¬
		str = str.replaceAll("\u0041\u0323", "\u1EA0"); // ÃƒÂ¡Ã‚ÂºÃ‚Â 
		str = str.replaceAll("\u0041\u0303", "\u00C3"); // ÃƒÆ’Ã†â€™
		str = str.replaceAll("\u0102\u0309", "\u1EB2"); // ÃƒÂ¡Ã‚ÂºÃ‚Â²
		str = str.replaceAll("\u0102\u0301", "\u1EAE"); // ÃƒÂ¡Ã‚ÂºÃ‚Â®
		str = str.replaceAll("\u0102\u0300", "\u1EB0"); // ÃƒÂ¡Ã‚ÂºÃ‚Â°
		str = str.replaceAll("\u0102\u0323", "\u1EB6"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¶
		str = str.replaceAll("\u0102\u0303", "\u1EB4"); // ÃƒÂ¡Ã‚ÂºÃ‚Â´
		str = str.replaceAll("\u00C2\u0309", "\u1EA8"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¨
		str = str.replaceAll("\u00C2\u0301", "\u1EA4"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¤
		str = str.replaceAll("\u00C2\u0300", "\u1EA6"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¦
		str = str.replaceAll("\u00C2\u0323", "\u1EAC"); // ÃƒÂ¡Ã‚ÂºÃ‚Â¬
		str = str.replaceAll("\u00C2\u0303", "\u1EAA"); // ÃƒÂ¡Ã‚ÂºÃ‚Âª
		return str;
	}

	// Defining Character Array you can change accordingly
	private static final char[] chars = { '1', 'A', 'a', 'B', 'b', 'C', 'c', '2', 'D', 'd', 'E', 'e', 'F', 'f', '3',
			'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', '4', 'M', 'm', 'N', 'n', 'O', 'o', '5', 'P',
			'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', '6', '7', 'U', 'u', 'V', 'v', 'U', 'u', 'W', 'w', '8', 'X',
			'x', 'Y', 'y', 'Z', 'z', '9' };

	public static final Color[] COLORS = { Color.red, Color.black, Color.blue };

	public static String generateCaptchaTextMethod1() {
		Random rdm = new Random();
		int rl = rdm.nextInt(); // Random numbers are generated.
		String hash1 = Integer.toHexString(rl); // Random numbers are converted
												// to Hexa Decimal.

		return hash1;
	}

	public static String generateCaptchaTextMethod2(int captchaLength) {
		String randomStrValue = "";

		final int LENGTH = 6; // Character Length

		StringBuffer sb = new StringBuffer();

		int index = 0;
		for (int i = 0; i < LENGTH; i++) {
			// Getting Random Number with in range(ie: 60 total character
			// present)
			index = (int) (Math.random() * (chars.length - 1));
			sb.append(chars[index]); // Appending the character using
										// StringBuffer
		}

		randomStrValue = String.valueOf(sb); // Assigning the Generated Password
												// to String variable

		return randomStrValue;
	}

	public static String captchaTextToCaptchaImage(String value) throws IOException {
		/*
		 * if (value != null && !value.isEmpty()) { BufferedImage image = null; try {
		 * image = ImageIO.read(CommonUtil.class.getResource("/images/background.jpg"));
		 * // Image
		 * 
		 * } catch (IOException e) {
		 * 
		 * System.out.println(e.getMessage()); e.printStackTrace(); }
		 * 
		 * Graphics g = image.getGraphics();
		 * 
		 * g.setFont(g.getFont().deriveFont(30f));
		 * 
		 * char[] c = value.toCharArray();
		 * 
		 * int x = 20; int y = 50;
		 * 
		 * for (int i = 0; i < c.length; i++) { x = x + 30; g.setColor(colors[(int)
		 * (Math.random() * 3)]); g.drawString(String.valueOf(c[i]), x, y); }
		 * 
		 * g.dispose();
		 * 
		 * try { //ImageIO.write(image, "png", new File("Output.png")); // Output
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 * ImageIO.write(image, "png", baos); baos.flush(); byte[] imageInByte =
		 * baos.toByteArray(); System.out.println("Captcha Generated Successfully!!!");
		 * 
		 * return imageInByte; // Image
		 * 
		 * } catch (IOException e) { System.out.println(e.getMessage());
		 * e.printStackTrace(); } }
		 */
		return value;
	}

	public static Boolean isHostServiceOnline(String serviceUrl) {
		HttpURLConnection connection = null;
		Boolean isOnline = false;
		try {
			URL u = new URL(serviceUrl);
			connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(1000);
			connection.setRequestMethod("HEAD");
			int code = connection.getResponseCode();
			if (code == 200)// success
			{
				isOnline = true;
			}
		} catch (IOException e) {
			return isOnline;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return isOnline;
	}

	/**
	 * cleanXSS
	 * 
	 * @param value
	 * @return
	 * @author HungHT
	 */
	public static String cleanXSS(String value) {
		if (value != null) {
			value = value.replaceAll("\\s{2,}", " ").trim();
			value = value.replaceAll("%3C", "<");
			value = value.replaceAll("%2F", "/");
			value = value.replaceAll("%3E", ">");
			value = value.replaceAll("%3D", "=");
			// You'll need to remove the spaces from the html entities below
			if (value.matches("<.*?on.*?>.*?")) {
				value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			}
			/* value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;"); */
			if (value.matches(".*?=.*?")) {
				value = value.replaceAll("'", "&#39;");
			}
			value = value.replaceAll("eval\\((.*)\\)", "");
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			value = value.replaceAll("<.*?script.*?>", "");
		}

		return value;
	}

	/**
	 * getCodeRandom
	 *
	 * @param length
	 * @return randomCode
	 * @author nhutnn
	 */
	public static String getCodeRandom(int length) {
		StringBuffer code = new StringBuffer(String.valueOf(System.currentTimeMillis()));
		code.append(CommonUtil.randomString(length).toUpperCase());
		return code.length() > length ? code.substring(0, length) : code.toString();
	}

	/**
	 * getNextCode
	 *
	 * @param prefix
	 * @param maxCode
	 * @return nextCode
	 * @author nhutnn
	 */
	public static String getNextCode(String prefix, String maxCode) {
//		int number = 6;
		int number = 4;

		if (StringUtils.isNotBlank(prefix)) {
			SimpleDateFormat format = new SimpleDateFormat("yy");
			prefix = prefix + format.format(new Date()) + ".";
		}

		StringBuffer nextCode = new StringBuffer(prefix);
		if (maxCode == null) {
//			return nextCode.append("000001").toString();
			return nextCode.append("00001").toString();

		}
		try {
			maxCode = maxCode.replaceAll(prefix.toString(), "");
			return nextCode.append(DtsStringUtil.leftPad(String.valueOf(Long.valueOf(maxCode) + 1), number, "0"))
					.toString();
		} catch (Exception e) {
//			return nextCode.append("000001").toString();
			return nextCode.append("0001").toString();

		}
	}

	/**
	 * getNextBannerCode
	 *
	 * @param prefix
	 * @param maxCode
	 * @return nextBannerCode
	 * @author longdch
	 */
	public static String getNextBannerCode(String prefix, String maxCode) {
		int number = 4;

		if (StringUtils.isNotBlank(prefix)) {
			SimpleDateFormat format = new SimpleDateFormat("yy");
			SimpleDateFormat formatMM = new SimpleDateFormat("MM");
			
			prefix = prefix + format.format(new Date()) + formatMM.format(new Date()) +  ".";
		}
		
		StringBuffer nextCode = new StringBuffer(prefix);
		if (maxCode == null) {
			return nextCode.append("0001").toString();
		}
		try {
			maxCode = maxCode.replaceAll(prefix.toString(), "");
			return nextCode.append(DtsStringUtil.leftPad(String.valueOf(Long.valueOf(maxCode) + 1), number, "0"))
					.toString();
		} catch (Exception e) {
			return nextCode.append("0001").toString();
		}
	}

	/**
	 * Convert XMLGregorianCalendar to date
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar xmlCal = null;
		try {
			GregorianCalendar gre = new GregorianCalendar();
			gre.setTime(date);
			xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gre);
		} catch (DatatypeConfigurationException e) {
			logger.error("_XMLGregorianCalendar_", e);
		}
		return xmlCal;
	}

	/**
	 * Marshal from object to xml
	 * 
	 * @param sourceObject
	 * @param sourceClass
	 * @return xmlString
	 * @author trieunh
	 * @throws FactoryConfigurationError
	 * @throws XMLStreamException
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static <T> String marshalOnjectToXML(T sourceObject, Class<T> sourceClass)
			throws XMLStreamException, FactoryConfigurationError, JAXBException, UnsupportedEncodingException {
		String xmlResult = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(out);
		JAXBContext jaxbContext = JAXBContext.newInstance(sourceClass);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(sourceObject, writer);
		xmlResult = out.toString("UTF-8");
		return xmlResult;
	}

	/**
	 * Unmarshal xml to java object
	 * 
	 * @param xmlSource
	 * @param desClass
	 * @return T class
	 * @author trieunh
	 * @throws XMLStreamException
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshalXMLToObject(String xmlSource, Class<T> desClass)
			throws XMLStreamException, JAXBException {
		T desObject = null;
		XMLInputFactory xif = XMLInputFactory.newFactory();
		XMLStreamReader xmlReader = xif.createXMLStreamReader(new StringReader(xmlSource));
		JAXBContext jaxbContext = JAXBContext.newInstance(desClass);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		desObject = (T) unmarshaller.unmarshal(xmlReader);
		return desObject;
	}

	/**
	 * Calculate offset SQL
	 *
	 * @param page
	 * @param sizeOfPage
	 * @return int
	 * @author KhoaNA
	 */
	public static int calculateOffsetSQL(int page, int sizeOfPage) {
		if (page <= 0) {
			page = 1;
		}

		return (page - 1) * sizeOfPage;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static MultiValueMap<String, String> convert(Object obj, ObjectMapper objectMapper) {
		MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
		Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<Map<String, String>>() {
		});
		parameters.setAll(maps);
		return parameters;
	}

	public static int verifyOverflowStartIndex(int currentPage, int sizeOfPage) {
		long result = (long) (currentPage - 1) * sizeOfPage;
		if (result > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (result < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) result;
	}

	/**
	 * add addition to arguments String
	 *
	 * @param addition
	 * @param arguments
	 * @return String
	 * @author hand
	 */
	public static String additionString(String addition, String... arguments) {
		if (arguments == null || addition == null) {
			return CommonStringUtil.EMPTY;
		}
		StringBuilder result = new StringBuilder(CommonStringUtil.EMPTY);
		for (String i : arguments) {
			if (!CommonStringUtil.isBlank(i)) {
				result.append(i);
				result.append(addition);
			}
		}
		String resultStr = result.toString();
		if (CommonStringUtil.isBlank(resultStr) || resultStr.length() < addition.length()) {
			return resultStr;
		}
		return resultStr.substring(0, resultStr.length() - addition.length());
	}
	
	/**
	 * getNextBannerCode
	 *
	 * @param prefix
	 * @param maxCode
	 * @return nextOrderCode
	 * @author tinhnt
	 */
	public static String getNextOrderCode(String prefix, String maxCode) {
		int number = 6;

		if (StringUtils.isNotBlank(prefix)) {
			SimpleDateFormat format = new SimpleDateFormat("yy");
			
			prefix = prefix + (Integer.parseInt(format.format(new Date())) + 1) + ".";
		}
		
		StringBuffer nextCode = new StringBuffer(prefix);
		if (maxCode == null) {
			return nextCode.append("000001").toString();
		}
		try {
			maxCode = maxCode.replaceAll(prefix.toString(), "");
			return nextCode.append(DtsStringUtil.leftPad(String.valueOf(Long.valueOf(maxCode) + 1), number, "0"))
					.toString();
		} catch (Exception e) {
			return nextCode.append("000001").toString();
		}
	}
}

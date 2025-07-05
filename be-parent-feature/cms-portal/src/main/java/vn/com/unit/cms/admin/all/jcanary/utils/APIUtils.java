package vn.com.unit.cms.admin.all.jcanary.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.ep2p.core.utils.Utility;

//import vn.com.unit.util.Utility;

public class APIUtils {
	
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(APIUtils.class);

	public static String callApiGet(String urlParam) {
		String result = StringUtils.EMPTY;
		HttpURLConnection conn = null;
		try {
			
			String[] urlArr = Utility.getConfigureProperty("url.api.back.end").split(",");
			
			for (String urlStr : urlArr) {
				URL url = new URL(urlStr.trim() + urlParam);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					logger.error("callApiGet: HTTP error code " + conn.getResponseCode());
				}
			}
			

			/*
			 * BufferedReader br = new BufferedReader(new
			 * InputStreamReader((conn.getInputStream()))); String output; while
			 * ((output = br.readLine()) != null) { System.out.println(output); }
			 */

		} catch (IOException e) {
			logger.error("callApiGet: " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return result;
	}

}

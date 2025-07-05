/***************************************************************
 * @author vunt					
 * @date Apr 5, 2021	
 * @project mbal-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Charsets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.dto.AccountApiDto;
public class APIUtils {
	private static final Logger logger = LoggerFactory.getLogger(APIUtils.class);
	
	public static void syncAccountAPi(SystemConfig systemConfig) {
		String apiUrl = systemConfig.getConfig(SystemConfig.LOGIN_API_URL)==null
				?"http://localhost:8080/testmysql":systemConfig.getConfig(SystemConfig.LOGIN_API_URL);
        //call api
		@SuppressWarnings("unused")
		List<AccountApiDto> lstObj = new ArrayList<>();
		HttpURLConnection connection = null;
        try {
        	
        	URL u = new URL(apiUrl);
			connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(1000);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
    	    if (connection.getResponseCode() != 200) {
    	      throw new RuntimeException("Unable to get data for URL " + apiUrl);
    	    }
			byte[] b = new byte[2048];
    	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	    DataInputStream d = new DataInputStream((FilterInputStream) connection.getContent());
    	    int c;
    	    while ((c = d.read(b, 0, b.length)) != -1) bos.write(b, 0, c);
    	    String return_ = new String(bos.toByteArray(), Charsets.UTF_8);
    	    logger.info("Calling URL API: {} returns: {}", apiUrl, return_);
    	    Gson gson = new Gson();
    	    //return collection
    	    Type type = new TypeToken<List<Object>>(){}.getType();
    	    lstObj = gson.fromJson(return_, type);
    	    
        } catch (IOException e) {
        	logger.error("IOException", e);
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
    }
	
	@SuppressWarnings("finally")
	public static AccountApiDto profileLogin(String username, String password, SystemConfig systemConfig) {
		String apiUrl = systemConfig.getConfig(SystemConfig.LOGIN_API_URL)==null
				?"http://localhost:8080/testmysql":systemConfig.getConfig(SystemConfig.LOGIN_API_URL);
        //call api
		String charSet = "UTF-8";
		HttpURLConnection connection = null;
		AccountApiDto profile = new AccountApiDto();
        try {
        	Map<String,Object> params = new LinkedHashMap<>();
            params.put("username",username);
            params.put("password", password);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), charSet));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), charSet));
            }
            byte[] postDataBytes = postData.toString().getBytes(charSet);

        	URL u = new URL(apiUrl);
			connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(1000);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			connection.setDoOutput(true);
			connection.getOutputStream().write(postDataBytes);
    	    if (connection.getResponseCode() != 200) {
    	      throw new RuntimeException("Unable to get data for URL " + apiUrl);
    	    }
			byte[] b = new byte[2048];
    	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	    DataInputStream d = new DataInputStream((FilterInputStream) connection.getContent());
    	    int c;
    	    while ((c = d.read(b, 0, b.length)) != -1) bos.write(b, 0, c);
    	    String return_ = new String(bos.toByteArray(), Charsets.UTF_8);
    	    logger.info("Calling URL API: {} returns: {}", apiUrl, return_);
    	    Gson gson = new Gson();
    	    //return collection
    	    /*Type type = new TypeToken<List<AccountApiDto>>(){}.getType();
    	    List<AccountApiDto> contactList = gson.fromJson(return_, type);
    	    */
    	    //return object
    	    profile = gson.fromJson(return_, AccountApiDto.class);
        } catch (IOException e) {
        	logger.error("IOException", e);
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
            return profile;
		}
    }

}

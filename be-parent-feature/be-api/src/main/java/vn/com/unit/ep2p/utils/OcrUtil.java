package vn.com.unit.ep2p.utils;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import fr.opensagres.xdocreport.document.json.JSONObject;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.ConstantCore;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.service.LoggingService;
import vn.com.unit.ep2p.log.entity.LogApiExternal;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OcrUtil<T> {
    private SystemConfig systemConfig;
	
	private RestTemplate restTemplate;
	
	private final String URL_CONFIG_SETTING = "OCR_API_URL";
	
	private final String CLIENT_NAME_CONFIG_SETTING = "OCR_CLIENT_USERNAME";
	
	private final String CLIENT_KEY_CONFIG_SETTING = "OCR_CLIENT_SECRET_KEY";
	
	private String baseUrl;
	
	private HttpHeaders headers;
	
	private ObjectMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static LoggingService loggingService;
	
	@Autowired
	private OcrUtil(SystemConfig systemConfig, LoggingService loggingService) throws Exception {
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) {
				return true;
			}
		};
		SSLContext sslContext = SSLContexts.custom()
											.loadTrustMaterial(null, acceptingTrustStrategy)
											.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom()
													.setSSLSocketFactory(csf)
													.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		
		this.restTemplate = new RestTemplate(requestFactory);
		this.restTemplate.setMessageConverters(messageConverters);
		
		this.headers = new HttpHeaders();
		this.headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		this.mapper = new ObjectMapper();
		
		// set systemConfig
		this.systemConfig = systemConfig;
		OcrUtil.loggingService = loggingService;
		
		// set base url
		this.baseUrl = this.systemConfig.getConfig(this.URL_CONFIG_SETTING, ConstantCore.COMP_CUSTOMER_ID);
		
		this.login();
	}
	
	private void login() {
		// get login credentials
		String clientUsername = this.systemConfig.getConfig(this.CLIENT_NAME_CONFIG_SETTING, ConstantCore.COMP_CUSTOMER_ID);
		String clientSecretKey = this.systemConfig.getConfig(this.CLIENT_KEY_CONFIG_SETTING, ConstantCore.COMP_CUSTOMER_ID);
		
		this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Object> mulMapLogIn = new LinkedMultiValueMap<>();
		mulMapLogIn.put("username", Arrays.asList(clientUsername));
		mulMapLogIn.put("password", Arrays.asList(clientSecretKey));
		
		HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(mulMapLogIn, headers);
		
		try {
			ResponseEntity<String> result = this.restTemplate.exchange(
				this.baseUrl + "token",
				HttpMethod.POST,
				req,
				String.class
			);
			
			Map<?, ?> map = this.mapper.readValue(result.getBody(), Map.class);
			this.headers.add("Authorization", "Bearer " + map.get("access_token").toString());
		} catch (Exception e) {
			this.logger.error("OcrUtil error: " + e.toString());
		}
	}
	
	public JSONObject callApi(
		String api,
		Map<Object, Object> params,
		HttpMethod method, Object object,
		MediaType mediaType, Class<T> responseType
	) {
		try {
			long startTime = System.currentTimeMillis();
			String url = this.baseUrl + api + mapToUrlParams(params);
			HttpEntity<Object> req = null;
			if (object != null) {
				req = new HttpEntity<>(object, this.headers);
			}
			else {
				req = new HttpEntity<>(this.headers);
			}
			
			this.headers.setContentType(mediaType);
			ResponseEntity<T> result = this.restTemplate.exchange(url, method, req, responseType);
			
			JSONObject jsonObject = new JSONObject(result.getBody().toString());
			long endTime = System.currentTimeMillis();
			long totalActionTime = endTime - startTime;
			
			LogApiExternal logApiEx = new LogApiExternal();
			logApiEx.setUrl(url);
			logApiEx.setJsonInput(req.toString());
			logApiEx.setCreatedDate(new Date());
			logApiEx.setStatus(result.getStatusCode().toString());
			logApiEx.setTats(totalActionTime);
			logApiEx.setUsername(UserProfileUtils.getUserNameLogin());
			logApiEx.setResponseJson(result.getBody().toString());
			loggingService.saveLogApiExternal(logApiEx);
			
			return jsonObject;
		} catch (Exception e) {
			this.logger.error("OcrUtil error: " + e.toString());
			return null;
		}
	}
	
	private String mapToUrlParams(Map<Object, Object> params) {
		if (params == null) {
			return "";
		}
		
		StringBuilder result = new StringBuilder("?");
		for (Map.Entry<Object, Object> entry : params.entrySet()) {
			result.append(entry.getKey().toString()).append("=").append(entry.getValue()).append("&");
		}
		
		return result.substring(0, result.length() - 1);
	}
}
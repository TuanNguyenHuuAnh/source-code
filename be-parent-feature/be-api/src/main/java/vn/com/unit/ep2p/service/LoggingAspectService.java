package vn.com.unit.ep2p.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.MBeanServer;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringUtils;
import ua_parser.Client;
import ua_parser.Device;
import ua_parser.Parser;
import vn.com.unit.cms.core.module.usersLogin.entity.UserLogin;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.web.rest.common.DtsApiError;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.service.LoggingService;
import vn.com.unit.ep2p.dto.res.AccountLoginRes;
import vn.com.unit.ep2p.log.entity.LogAPI;
import vn.com.unit.ep2p.log.entity.LogDB;

/**
 * LoggingAspect
 *
 */
@Aspect
@Component
public class LoggingAspectService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LoggingService logService;
	
	@Autowired
	private AccountService accService;

	@Autowired
	private UserLoginService userLoginService;

	MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut("execution(* vn.com.unit.ep2p..*Rest.*(..))")
	public void RestPointCut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}

	@Pointcut("execution(* jp.sf.amateras.mirage.SqlManager.call*(..))")
	public void CallDBPointCut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}

	@Pointcut("execution(* vn.com.unit..*Repository.*(..)) "
			+ "&& !execution(* vn.com.unit.cms.core.module.userLogin.repository..*Repository.*(..))"
			+ "&& !execution(* vn.com.unit.cms.core.module.logApi.repository..*Repository.*(..))")
	public void CallSqlPointCut() {
		// Method is empty as this is just a Pointcut, the implementations are in the
		// advices.
	}	
	
	@SuppressWarnings("unchecked")
	private String getRequestBody(HttpServletRequest request) throws IOException {	
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException ex) {
			throw ex;
		}
		return stringBuilder.toString();
	}

	private String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

    private static String formatVersion(String family, String major, String minor, String patch) {
        StringBuilder version = new StringBuilder(family);
        if (major != null) version.append(" ").append(major);
        if (minor != null) version.append(".").append(minor);
        if (patch != null) version.append(".").append(patch);
        return version.toString();
    }

    @AfterReturning(pointcut = "execution(* vn.com.unit.ep2p.rest.authen.AuthenRest.loggingOut(..)) && args(request)", returning = "response")
    public void updateLogout(HttpServletRequest request, DtsApiResponse response) {
    	if (response.getStatusCode() == 200) {
			String authHeader = request.getHeader("Authorization");
			if(StringUtils.isNotBlank(authHeader)){
				String token = authHeader.replace("Bearer ", "");
				UserLogin userDto = new UserLogin();
				userDto = userLoginService.getUserLoginIdByAccessToken(token);
				if (userDto.getId() != null) {
					userDto.setLogoutDate(new Date());
					userLoginService.saveUserLogin(userDto);
				}
			}
    	}
    }

	@SuppressWarnings("unused")
	@Around("RestPointCut()")
	public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
		TrackingContext.clearTempDB();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String urlPath = request.getRequestURI();
		String clientIp = getClientIpAddress(request);
		String userAgentString = request.getHeader("User-Agent");
				
		String deviceName = null;
		String browser = null;
		String deviceLogin = null;
		String os = null;
		
		if (StringUtils.isNotEmpty(userAgentString)) {
			if (userAgentString.startsWith("m") || userAgentString.startsWith("App") || userAgentString.contains("okhttp") || userAgentString.contains("Dalvik") 
	        		|| userAgentString.contains("CFNetwork") || userAgentString.toLowerCase().contains("mobi")) {
	        	deviceName = "Mobile";
	        } else {
	        	deviceName = "Web";
	        }
		}

		Object[] args = joinPoint.getArgs();
		String json = null;
		try {
			if (args != null && args.length > 1) {
				json = objectMapper.writeValueAsString(args[1]);	
			}
		} catch (Exception e) {
			logger.error("Convert to json error: ", e);
		}
        String requestBody = (String) request.getAttribute("requestBody");
		if (requestBody == null) {
			requestBody = getRequestBody(request);
			request.setAttribute("requestBody", requestBody);
		}

		Object result = null;
		String exception = null;
		long startTime = System.currentTimeMillis();
		try {
			result = joinPoint.proceed();
		} catch (Throwable ex) {
			exception = ex.getMessage();
		} finally {
			long endTime = System.currentTimeMillis();
			String methodName = joinPoint.getSignature().getName();
			long totalActionTime = endTime - startTime;
			if ("download".equalsIgnoreCase(methodName)) {
				return result;
			}

			String responseBody = null;
			try {
				if (result instanceof ResponseEntity) {
					ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
					if (!(responseEntity.getBody() instanceof InputStreamResource)) {
						responseBody = objectMapper.writeValueAsString(result);
					}
				} else {
					responseBody = objectMapper.writeValueAsString(result);
				}
			} catch (Exception e) {
				logger.error("Convert to json error: ", e);
			}

			DtsApiResponse response = new DtsApiResponse();
			ModelMapper mapper = new ModelMapper();
			if (result != null) {
				response = mapper.map(result, DtsApiResponse.class);
			}
			int code = response.getStatusCode();
			String channel = null;
			if (urlPath.contains("/login") && !urlPath.contains("jwt")) {
				Parser uaParser = new Parser();
		        try {
		            Client client = uaParser.parse(userAgentString);
		            Device device = client.device;

		            if (client != null) {
		            	browser = formatVersion(client.userAgent.family, client.userAgent.major, client.userAgent.minor, client.userAgent.patch);
			            os = formatVersion(client.os.family, client.os.major, client.os.minor, client.os.patch);
			            deviceLogin = client.device.family;
		            }
		        } catch (Exception e) {
		        	logger.error("Parser error: ", e);
		        }
		        String userName = request.getParameter("username");
		        if (StringUtils.isNotEmpty(requestBody)) {
		        	ObjectMapper objMapper = new ObjectMapper();
		        	JsonNode rootNode = objMapper.readTree(requestBody);
		        	if (rootNode != null) {
		        		userName = rootNode.path("username").asText();
		        	}
		        }
		        String accessToken = null;
		        if (StringUtils.isNotEmpty(responseBody)) {
		        	ObjectMapper objMapper = new ObjectMapper();
		        	JsonNode rootNode = objMapper.readTree(responseBody);
		        	if (rootNode != null) {
			        	JsonNode dataNode = rootNode.get("data");
			        	if (dataNode != null) {
				        	accessToken = dataNode.path("accessToken").asText();
			        	}
		        	}
		        }
		        
				AccountLoginRes account = new AccountLoginRes();
				JcaAccount acc = new JcaAccount();
				if (code == 200) {
					if (response.getData() instanceof AccountLoginRes) {
						account = mapper.map(response.getData(), AccountLoginRes.class);
					}
					acc = accService.findByUserName(account.getUsername() != null ? account.getUsername() : userName, 2L);
				}
				String accountId = null;
				if (account != null && account.getAccountId() != null) {
					accountId = account.getAccountId();
				} else if (acc != null && acc.getId() != null) {
					accountId = acc.getId().toString();
				}
				
				UserLogin userDto = new UserLogin();
				Date loginDate = new Date();
				String token = account.getAccessToken() != null ? account.getAccessToken() : accessToken;
				if (token != null && token.contains("Bearer")) {
					token = token.replace("Bearer ", "");
				}
				userDto.setAccessToken(token);
				userDto.setUserID(accountId);
				userDto.setUsername(account.getUsername() != null ? account.getUsername() : userName);
				userDto.setLoginDate(loginDate);
				userDto.setLoginType(deviceName);
				userDto.setLoginStatus(code == 200 ? "Succeeded" : "Failed");
				userDto.setClientIp(clientIp);
				userDto.setRemoteHost(request.getRemoteHost());
				userDto.setBrowser(browser);
				userDto.setOs(os);
				userDto.setDevice(deviceLogin);
				if (account.getChannel() != null) {
					channel = account.getChannel();
				} else if (acc != null && acc.getChannel() != null) {
					channel = acc.getChannel();
				}
				userDto.setChannel(channel);
				userDto.setUserAgent(userAgentString);
				userLoginService.saveUserLogin(userDto);
			}
		
			String error = null;
			if (response != null && response.getError() != null) {
				DtsApiError dtsApiError = response.getError();
				error = dtsApiError.toString();
			}
			String detail = null;
			if (!StringUtils.isEmpty(response.getDescription())) {
				detail = response.getStatusMessages() + " - " + response.getDescription();
			} else {
				detail = response.getStatusMessages();
			}
			
			String username = UserProfileUtils.getUserNameLogin();
			// Save logEntry
			LogAPI logEntry = new LogAPI();
			logEntry.setUrlPath(urlPath);
			logEntry.setSourceIp(clientIp);
			logEntry.setEventName(methodName);
			logEntry.setRequestJson(requestBody);
			//logEntry.setResponseJson(responseBody);
			logEntry.setTats(totalActionTime);
			logEntry.setCreatedDate(new Date());
			logEntry.setMessage(detail);
			//logEntry.setStatus(code);
			logEntry.setReqParams(json);
			logEntry.setDevice(deviceName);
			logEntry.setUserAgent(userAgentString);
			logEntry.setUserAccount(username);
			logEntry.setLogLevel("INFO");
			logEntry.setOutcome("Success");
			if (exception != null || error != null) {
				logEntry.setLogLevel("ERROR");
				logEntry.setOutcome("Fail");
			}
			logEntry.setSystem("Daiichi Success");
			if ("AD".equals(channel)) {
				logEntry.setSystem("AD Portal");	
			}
			
			try {
				logService.saveLogApi(logEntry);
			} catch (Exception e) {
				logger.info("Save log API error: ", e);
			}
			
			List<LogDB> tempDB = TrackingContext.getTemp();
			if (!tempDB.isEmpty() && tempDB != null && tempDB.size() > 1) {
				tempDB.sort((log1, log2) -> log1.getCreatedDate().compareTo(log2.getCreatedDate()));
			}
			for (int i = 0; i < tempDB.size(); i++) {
				LogDB logDb = tempDB.get(i);
				if (logDb.getStoreName().contains("RPT_ODS")
					|| logDb.getStoreName().contains("STG_DS")
					|| logDb.getStoreName().contains("STG_DMS")
					|| logDb.getStoreName().contains("STG_HCMS")) {
					logDb.setApiID(logEntry.getId());
					try {
						logService.saveLogDb(logDb);
					} catch (Exception e) {
						logger.info("Save log DB2 error: ", e);
					}	
				}
			}
			TrackingContext.clearTempDB();
		}
		return result;
	}

	@Around("CallDBPointCut()")
	public Object logCallDB(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		String storeName = args[0].toString();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(args[1]);
		} catch (Exception e) {
			logger.error("Convert to json error: ", e);
		}
		Object result = null;
		String exception = null;
		long startTime = System.currentTimeMillis();
		try {
			result = joinPoint.proceed();
		} catch (Throwable ex) {
			exception = ex.getMessage();
		} finally {
			long endTime = System.currentTimeMillis();
			long totalActionTime = endTime - startTime;

			// Save DB
			LogDB logDb = new LogDB();
			logDb.setTats(totalActionTime);
			logDb.setStoreName(storeName);
			logDb.setParam(json);
			logDb.setException(exception);
			logDb.setCreatedDate(new Date());
				
			TrackingContext.getTemp().add(logDb);
		}
		return result;
	}
		
	@Around("CallSqlPointCut()")
	public Object logCallSql(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		int lastDotIndex = className.lastIndexOf(".");
		String fullName = String.join("_", className.substring(lastDotIndex + 1), methodName);
		String argsString = null;
		if (args.length > 0) {
			try {
			argsString = Arrays.stream(joinPoint.getArgs()).map(arg -> args != null ? arg.toString() : null).collect(Collectors.joining(", "));
			} catch(Exception e) {
				
			}
		}
		Object result = null;
		String exception = null;
		long startTime = System.currentTimeMillis();
		try {
			result = joinPoint.proceed();
		} catch (Throwable ex) {
			exception = ex.getMessage();
		} finally {
			long endTime = System.currentTimeMillis();
			long totalActionTime = endTime - startTime;
			
			// Save DB2
			LogDB logDb = new LogDB();
			logDb.setTats(totalActionTime);
			logDb.setStoreName(fullName);
			logDb.setParam(argsString);
			logDb.setException(exception);
			logDb.setCreatedDate(new Date());
				
			TrackingContext.getTemp().add(logDb);
		}
		return result;
	}

}

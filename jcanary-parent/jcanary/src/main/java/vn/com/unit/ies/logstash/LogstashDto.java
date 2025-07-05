// 2021-06-18 - LocLT - Write Logstash

package vn.com.unit.ies.logstash;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.utils.DtsDateFormatUtil;

@Getter
@Setter
public class LogstashDto {
	private String serviceName = "";
	private String serviceDomain = "";
	private String ipSource = "";
	private String ipDest = "";
	private String url = "";
	private String level = "";
	private String method = "";
	private String logHeader = "";
	private String requestContent = "";
	private String responseContent = "";
	private String responseCode = "";
	private String responseMessage = "";
	private String transactionID = "";
	private String takeTime = "";
	private String dateTime = "";
	private String others = "";

	public LogstashDto() {
		super();
	}

	// 2021-06-18 - LocLT - Write Logstash
	@SuppressWarnings("unchecked")
	public LogstashDto(Exception ex, HttpServletRequest request, String level, Object response, Integer codeStatus,
			String message, String transactionId, Long took, String others) {

		ObjectMapper mapper = new ObjectMapper();

//		ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);

		// this.setServiceName(ex.getStackTrace()[1].getMethodName());

		// ex.getStackTrace()[1].getClassName()
		// nead for all and check

		// this.setServiceDomain(ex.getStackTrace()[1].getClassName());

		String classTag = "";
		String methodTag = "";

		List<StackTraceElement> listStackTrace = new ArrayList<>();

		listStackTrace.addAll(Arrays.asList(Thread.currentThread().getStackTrace()));

		if (listStackTrace != null) {
			for (int i = 0; i < listStackTrace.size(); i++) {
				StackTraceElement stackTrace = listStackTrace.get(i);
				try {
					Class<?> clazz = Class.forName(stackTrace.getClassName());

					if (null != clazz) {
						try {
							Api classApiAnnotation = clazz.getAnnotation(Api.class);
							classTag = classApiAnnotation.tags() != null ? String.join(", ", classApiAnnotation.tags())
									: "";
						} catch (Exception e) {
						}

						try {
							List<Method> listMethod = Arrays.asList(clazz.getDeclaredMethods());

							for (Method method : listMethod) {
								if (method.getName().equals(stackTrace.getMethodName())) {
									ApiOperation methodApiOperationAnnotation = method
											.getAnnotation(ApiOperation.class);
									methodTag = methodApiOperationAnnotation.value();
								}
							}
						} catch (Exception e) { // NoSuchMethodException | SecurityException
						}
					}
				} catch (Exception e) {
				}

				if ((classTag != null && classTag != "") || (methodTag != null && methodTag != "")) {
					break;
				}
			}
		}

//		if (ex != null) {
//			try {
//				Class<?> clazz = Class.forName(ex.getStackTrace()[1].getClassName());
//
//				if (null != clazz) {
//					try {
//						Api classApiAnnotation = clazz.getAnnotation(Api.class);
//						classTag = classApiAnnotation.tags() != null ? String.join(", ", classApiAnnotation.tags())
//								: "";
//					} catch (Exception e) {
//					}
//
//					try {
//						Method method = clazz.getDeclaredMethod(ex.getStackTrace()[1].getMethodName());
//						ApiOperation methodApiOperationAnnotation = method.getAnnotation(ApiOperation.class);
//						methodTag = methodApiOperationAnnotation.value();
//					} catch (Exception e) { // NoSuchMethodException | SecurityException
//					}
//
//				}
//
//			} catch (Exception e) { // ClassNotFoundException
//			}
//		} else {
//			try {
//				List<StackTraceElement> listStackTrace = Arrays.asList(Thread.currentThread().getStackTrace());
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}

		this.setServiceDomain(classTag);
		this.setServiceName(methodTag);

		String clientIp = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
		clientIp = clientIp.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : clientIp;
		this.setIpSource(clientIp);

		String hostIp = request.getLocalAddr();
		hostIp = hostIp.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : hostIp;

		this.setIpDest(hostIp);

		// request.getRequestURL()
		this.setUrl(request.getRequestURI());

		this.setLevel(level != null ? level : "");

		this.setMethod(request.getMethod());

		Enumeration<String> listHeaderName = request.getHeaderNames();

		Map<String, String> mapHeader = new HashMap<>();

		if (listHeaderName != null) {
			while (listHeaderName.hasMoreElements()) {
				String headerName = listHeaderName.nextElement();
				mapHeader.put(headerName, request.getHeader(headerName));
			}
		}

		String listHeader = "";

		try {
			listHeader = mapper.writeValueAsString(mapHeader);
		} catch (JsonProcessingException e) {

		}

		this.setLogHeader(listHeader);

		String queryStringOrPostFormData = "";

		try {
			queryStringOrPostFormData = request.getParameterMap() != null
					&& ObjectUtils.isNotEmpty(request.getParameterMap())
							? mapper.writeValueAsString(request.getParameterMap())
							: "";
		} catch (Exception e) { // JsonProcessingException

		}

		String bodyRequest = "";

		try {
//			request.getReader();
//			request.getInputStream();
			bodyRequest = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

//			request.getQueryString();
//			request.getParameter("id");
		} catch (Exception e) {
		}

		Map<String, String> mapPathVariable = new TreeMap<>();
		try {
//			Map<Object, Object> pathVariables = (Map<Object, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);        
			mapPathVariable.putAll(new TreeMap<>(
					(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)));
		} catch (Exception e) {
		}

		Map<String, Object> mapRequestContent = new HashMap<>();
		mapRequestContent.put("queryStringOrPostFormData", queryStringOrPostFormData);
		mapRequestContent.put("bodyRequest", bodyRequest);
		mapRequestContent.put("mapPathVariable", mapPathVariable);

		String requestContent = "";

		try {
			requestContent = mapper.writeValueAsString(mapRequestContent);
		} catch (Exception e) {
		}

//		Map<String, Object> mapAttribute = new HashMap<>();

//		if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {}

//		try {
////			listParam += request.getReader().lines().collect(Collectors.joining());
//			listParam += new String(cachingRequest.getContentAsByteArray());
//		} catch (Exception e) {
//		}

		this.setRequestContent(requestContent);

		// new DtsApiResponse(expCode.getValue(), DtsConstant.ERROR, message,
		// hiddenDesc, took)

		String responseContent = "";
		try {
			responseContent = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {

		}

		this.setResponseContent(responseContent);

		this.setResponseCode(String.valueOf(codeStatus));

		this.setResponseMessage(message != null ? message : "");

		this.setTransactionID(transactionId != null ? transactionId : "");

		this.setTakeTime(String.valueOf(took));

		this.setDateTime(DtsDateFormatUtil.fullTimeZone());

		this.setOthers(others);
	}

	// 2021-06-18 - LocLT - Write Logstash
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			String rs = this.toString();

			rs.replace("LogstashDto [", "{");
			rs = rs.substring(0, rs.length() - 1) + "}";

			rs.replaceAll(", ", ", \"");
			rs.replaceAll("=", "\"");

			return rs;
		}
	}

	// 2021-06-18 - LocLT - Write Logstash
	@Override
	public String toString() {
		return "LogstashDto [serviceName=" + serviceName + ", serviceDomain=" + serviceDomain + ", ipSource=" + ipSource
				+ ", ipDest=" + ipDest + ", url=" + url + ", level=" + level + ", method=" + method + ", logHeader="
				+ logHeader + ", requestContent=" + requestContent + ", responseContent=" + responseContent
				+ ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", transactionID="
				+ transactionID + ", takeTime=" + takeTime + ", dateTime=" + dateTime + ", others=" + others + "]";
	}
}

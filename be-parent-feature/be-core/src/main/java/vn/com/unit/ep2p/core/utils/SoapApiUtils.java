package vn.com.unit.ep2p.core.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.service.LoggingService;
import vn.com.unit.ep2p.log.entity.LogApiExternal;

@Component
public class SoapApiUtils {
	private static JcaSystemConfigService jcaSystemConfigService;
	private static LoggingService loggingService;
    
    @Autowired
    public SoapApiUtils(JcaSystemConfigService jcaSystemConfigService, LoggingService loggingService) {
    	SoapApiUtils.jcaSystemConfigService = jcaSystemConfigService;
    	SoapApiUtils.loggingService = loggingService;
    }
	
	
	public static String getWorkflowId(List<String> listFile, String keyin, String keyinValue) throws Exception {
		long startTime = System.currentTimeMillis();
		LogApiExternal logApiEx = new LogApiExternal();
		String url = jcaSystemConfigService.getValueByKey("NEWSCANWS_BASE_URL", ConstantCore.COMP_CUSTOMER_ID);
		try {
			logApiEx.setUrl(url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","text/xml; charset=utf-8"); 
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:scan=\"http://scan.daiichi\">\r\n" + 
	                 	 "   <soapenv:Header/>\r\n" + 
	                 	 "   <soapenv:Body>\r\n" + 
	                 	 "      <scan:getWorkflowIdWithIllCode>\r\n" + 
	                 	 "         <scan:sProcessType>BSA</scan:sProcessType>\r\n" +
	                 	 "         <scan:sWorkflowId></scan:sWorkflowId>\r\n" +
	                 	 "         <scan:sWorkStep></scan:sWorkStep>\r\n" +
	                 	 "         <scan:sCSOfficeCode></scan:sCSOfficeCode>\r\n" +
	                 	 "         <scan:sCSOfficeName></scan:sCSOfficeName>\r\n" +
	                 	 "         <scan:sKeyin>%s</scan:sKeyin>\r\n";
			for (String file : listFile) {
				xml += "         <scan:arrFileListDoc>".concat(file).concat("#1#SAMDocument</scan:arrFileListDoc>\r\n");
			}
			xml +=  	 "         <scan:sUserIdWF>ADPUser</scan:sUserIdWF>\r\n" +
	                	 "         <scan:sPasswordWF></scan:sPasswordWF>\r\n" +
	                 	 "         <scan:sAuthenticateCode></scan:sAuthenticateCode>\r\n" +
	                 	 "         <scan:sUserWindow></scan:sUserWindow>\r\n" +
	                 	 "         <scan:sKeyinValue>%s</scan:sKeyinValue>\r\n" +
	                 	 "         <scan:sIndexValue>rdbBSA_SAMDocument$1$1</scan:sIndexValue>\r\n";
			xml += 	 	 "         <scan:sImagePath>".concat(listFile.get(0)).concat("</scan:sImagePath>\r\n");
			xml +=  	 "         <scan:sDocId>BSA</scan:sDocId>\r\n" +
	                 	 "         <scan:sDateSearch>%s</scan:sDateSearch>\r\n" +
	                 	 "         <scan:sIllustrationID></scan:sIllustrationID>\r\n" +
	                 	 "      </scan:getWorkflowIdWithIllCode>\r\n" + 
	                 	 "   </soapenv:Body>\r\n" + 
	                 	 "</soapenv:Envelope>";
			SimpleDateFormat formatDateExport = new SimpleDateFormat("dd/MM/yyyy");
			xml = String.format(xml, keyin, keyinValue, formatDateExport.format(new Date()));
			logApiEx.setJsonInput(xml);
			
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
			con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    response.append(inputLine);
			}
			in.close();
			String finalvalue= response.toString();
			
			long endTime = System.currentTimeMillis();
			long totalActionTime = endTime - startTime;
			logApiEx.setCreatedDate(new Date());
			logApiEx.setStatus(responseStatus);
			logApiEx.setTats(totalActionTime);
			logApiEx.setUsername(UserProfileUtils.getFaceMask());
			logApiEx.setResponseJson(finalvalue);
			loggingService.saveLogApiExternal(logApiEx);
			
			return finalvalue;
	    }
	    catch (Exception e) {
            long endTime = System.currentTimeMillis();
			long totalActionTime = endTime - startTime;
            logApiEx.setCreatedDate(new Date());
			logApiEx.setStatus("ERROR");
			logApiEx.setTats(totalActionTime);
			logApiEx.setUsername(UserProfileUtils.getFaceMask());
			logApiEx.setResponseJson(e.toString());
			loggingService.saveLogApiExternal(logApiEx);
			throw  new Exception("Tạo workflow thất bại");
	    }
	}
}

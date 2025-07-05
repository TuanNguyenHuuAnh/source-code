package vn.com.unit.ep2p.core.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSAPUtils {
    private static String SOAP_ENDPOINT_URL = "http://d-po.mbageas.life:50100/XISOAPAdapter/MessageServlet?senderParty=&senderService=bc_authen_qas&receiverParty=&receiverService=&interface=SIOS_CHECK_AUTHEN&interfaceNamespace=urn:mbageas:other:fiori";
    private static String SOAP_ACTION = "http://sap.com/xi/WebService/soap1.1";
    private static String SOAP_USER = "EREC01";
    private static String SOAP_PASS = "Mbal@1234";

    private static String res = "";
    
    private static final String RES_SUCCESS = "<RESPONSE_CODE>000</RESPONSE_CODE>";
    public static final String PARAM_START_WITH = "<RESPONSE_CODE>";
    public static final String PARAM_END_WITH = "</RESPONSE_CODE>";
    
    private static final Logger logger = LoggerFactory.getLogger(LoginSAPUtils.class);

//    public static void main(String[] args) {
//        String uername = "rec01";
//        String password = "Abc@1234";
//        loginSoapWebService(uername, password, SOAP_ENDPOINT_URL, SOAP_ACTION, SOAP_USER, SOAP_PASS);
//    }

    // REQUEST
    private static SOAPMessage createSOAPRequest(String username, String password) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        createSoapEnvelope(soapMessage, username, password);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", SOAP_ACTION);
        String userAndPassword = String.format("%s:%s", SOAP_USER, SOAP_PASS);
        String basicAuth = Base64.getEncoder().encodeToString(userAndPassword.getBytes());
        headers.addHeader("Authorization", "Basic " + basicAuth);
        soapMessage.saveChanges();
        /* Print the request message, just for debugging purposes */
        // System.out.println("Request SOAP Message:");
        // soapMessage.writeTo(System.out);
        System.out.println("\n");
        return soapMessage;
    }

    /*
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
     * xmlns:urn="urn:mbageas:other:fiori"> <soapenv:Header/> <soapenv:Body>
     * <urn:MT_CHECK_AUTHEN_INPUT> <I_USER>rec01</I_USER> <I_PASS>Abc@1234</I_PASS>
     * </urn:MT_CHECK_AUTHEN_INPUT> </soapenv:Body> </soapenv:Envelope>
     */
    // BODY REQUSEST
    private static void createSoapEnvelope(SOAPMessage soapMessage, String username, String password)
            throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("urn", "urn:mbageas:other:fiori");
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPBodyElement element = soapBody.addBodyElement(envelope.createName("MT_CHECK_AUTHEN_INPUT", "urn", ""));
        element.addChildElement("I_USER").addTextNode(username);
        element.addChildElement("I_PASS").addTextNode(password);
    }

    // RESPONSE
    public static String loginSoapWebService(String username, String password, String url, String host, String user,
            String pass) {
        try {
            res = "";
            createParam(url, host, user, pass);
            
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(username, password), SOAP_ENDPOINT_URL);
            
            // Print the SOAP Response
            //System.out.println("Response SOAP Message:");
            // soapResponse.writeTo(System.out);
            OutputStream fileOut = new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    char ch = (char) b;
                    res = res + ch;
                }
            };
            soapResponse.writeTo(fileOut);
            soapConnection.close();

            logger.debug("RES_API", res);
            System.out.println(res);

            if (res.contains(RES_SUCCESS)) {
                return "000";
            } else {
                int indexStart = res.indexOf(PARAM_START_WITH);
                int indexEnd = res.indexOf(PARAM_END_WITH);
                String str = res.substring(indexStart, indexEnd).replace(PARAM_START_WITH, "");
                return str;
            }
        } catch (Exception e) {
            System.err.println(
                    "\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            logger.error("Exception ", e);
        }
        return "009"; // Other ERROR
    }
    
    private static void createParam(String url, String host, String user, String pass) {
        if (StringUtils.isNotBlank(url)) {
            SOAP_ENDPOINT_URL = url;
        }
        if (StringUtils.isNotBlank(host)) {
            SOAP_ACTION = host;
        }
        if (StringUtils.isNotBlank(user)) {
            SOAP_USER = user;
        }
        if (StringUtils.isNotBlank(pass)) {
            SOAP_PASS = pass;
        }
    }
}
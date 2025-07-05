/*******************************************************************************
 * Class        ：SOAPApiServiceImpl
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.api.dto.ResSOAPApiDto;
import vn.com.unit.common.api.service.SOAPApiService;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.api.dto.AbstractApiExternalDto;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;
import vn.com.unit.dts.api.enumdef.APIStatus;

/**
 * SOAPApiServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Component(value = CommonConstant.BEAN_SOAP_API_EXTERNAL)
@Scope(value = "singleton")
public class SOAPApiServiceImpl implements SOAPApiService{

    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> E callAPI(T object,String soapEndpointUrl, Object requestObj, Class<E> responseClass)
            throws Exception {
        
        E result = null;
        String msgRequest = CommonStringUtil.EMPTY;
        String msgResponse = CommonStringUtil.EMPTY;
        String status = APIStatus.FAIL.toString();
        
        
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        
        try {
            URL endpoint = new URL(null, soapEndpointUrl, new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    // Connection settings
                    connection.setConnectTimeout(CommonConstant.API_EXTERNAL_TIMEOUT); // 2 min
                    connection.setReadTimeout(CommonConstant.API_EXTERNAL_TIMEOUT); // 2 min
                    return (connection);
                }
            });
            
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call((SOAPMessage) requestObj, endpoint);
            ((ResSOAPApiDto) result).setSoapResponse(soapResponse);
            msgResponse = this.printSoapMessage(soapResponse);
            
            // remove data tag
            msgResponse = msgResponse.replaceAll(CommonConstant.DATA_TAG_REGEX, CommonConstant.EMPTY);
            
            // Convert to Object
            JAXBContext jaxbContext = JAXBContext.newInstance(responseClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object unmarshalObj = jaxbUnmarshaller.unmarshal(soapResponse.getSOAPBody().extractContentAsDocument());
            ((ResSOAPApiDto) result).setUnmarshalObj(unmarshalObj);
            
            //fault
            if(null != soapResponse.getSOAPBody().getFault()) {
                Object fault = jaxbUnmarshaller.unmarshal(((SOAPBody) soapResponse.getSOAPBody().getFault()).extractContentAsDocument());
                ((ResSOAPApiDto) result).setFault(fault);
            }
            
            // Set status
//            result.setCorrelationId(logApi.getCorrelationId());
            status = APIStatus.SUCCESS.toString();
            result.setStatus(status);

            // Close connect
            soapConnection.close();
        } catch (SOAPException e) {
//            logger.error("[callSOAPService][SOAPException] msgRequest: [" + msgRequest + "]. ID: " + logApi.getId(), e);
            throw e;
        } catch (JAXBException e) {
//            logger.error("[callSOAPService][JAXBException] msgRequest: [" + msgRequest + "], msgResponse: [" + msgResponse + "]. ID: "
//                    + logApi.getId(), e);
            throw e;
        } catch (MalformedURLException e) {
//            logger.error("[callSOAPService][MalformedURLException] msgRequest: [" + msgRequest + "], msgResponse: [" + msgResponse + "]. ID: "
//                    + logApi.getId(), e);
            throw e;
        } catch (Exception e) {
//            logger.error("[callSOAPService][Exception]. ID: " + logApi.getId(), e);
            throw e;
        } finally {
            
        }
        
        return result;
    }

    /**
     * 
     * printSoapMessage
     * @param soapMessage
     * @return
     * @author taitt
     */
    public String printSoapMessage(SOAPMessage soapMessage) {
        String message = null;
        try {
            if (null != soapMessage) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                // Format it
                // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                // transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                Source soapContent = soapMessage.getSOAPPart().getContent();

                ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
                StreamResult result = new StreamResult(streamOut);
                transformer.transform(soapContent, result);
                message = streamOut.toString();
            }
        } catch (Exception e) {
            
        }
        return message;
    }

    @Override
    public String printSOAPElement(SOAPElement soapElement) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        DOMSource source = new DOMSource(soapElement);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);
        String strObject = result.getWriter().toString().replaceAll(CommonConstant.REPLACE_CHARACTER_SOAP, CommonConstant.EMPTY).trim();
        return strObject;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.dts.api.service.AbstractApiExternalService#callAPIDownloadFile(vn.com.unit.dts.api.dto.AbstractApiExternalDto, java.lang.String, java.lang.Object)
     */
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIDownloadFile(T object, String url,
            Object requestObj) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.dts.api.service.AbstractApiExternalService#callAPIUploadFile(vn.com.unit.dts.api.dto.AbstractApiExternalDto, java.lang.String, org.springframework.util.MultiValueMap)
     */
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIUploadFile(T object, String url,
            MultiValueMap<String, Object> requestObj) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}

/*******************************************************************************
 * Class        ：BpmnUtil
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：KhuongTH
 * Change log   ：2021/01/13：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;

import vn.com.unit.common.constant.CommonConstant;

/**
 * <p>
 * BpmnUtil
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class BpmnUtil {

    public static BpmnModel convertInputStreamToBpmnModel(InputStream inputStream) throws UnsupportedEncodingException, XMLStreamException {
        BpmnModel bpmnModel = null;
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader inr = new InputStreamReader(inputStream, CommonConstant.UTF_8);
        XMLStreamReader xtr = xif.createXMLStreamReader(inr);
        BpmnXMLConverter converter = new BpmnXMLConverter();
        bpmnModel = converter.convertToBpmnModel(xtr);
        return bpmnModel;
    }
}

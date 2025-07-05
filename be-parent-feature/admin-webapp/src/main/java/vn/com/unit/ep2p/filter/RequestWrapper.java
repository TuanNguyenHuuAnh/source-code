/**
 * 
 */
package vn.com.unit.ep2p.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * @author longpnt
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    
    public String[] getParameterValues(String parameter) {
      String[] values = super.getParameterValues(parameter);
      if (StringUtils.isNotBlank(parameter) && parameter.toLowerCase().contains(ConstantCore.CONST_CONTENT)) {
          return values;
      }
      if (values==null)  {
                  return null;
          }
      int count = values.length;
      String[] encodedValues = new String[count];
      for (int i = 0; i < count; i++) {
                 encodedValues[i] = CommonUtil.cleanXSS(values[i]);
       }
      return encodedValues;
    }
    
    public String getParameter(String parameter) {
          String value = super.getParameter(parameter);
          if (value == null) {
                 return null;
                  }
          return CommonUtil.cleanXSS(value);
    }
    
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return CommonUtil.cleanXSS(value);
    }
}
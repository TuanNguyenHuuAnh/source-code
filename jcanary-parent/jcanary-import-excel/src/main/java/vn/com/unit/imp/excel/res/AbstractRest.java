/*******************************************************************************
 * Class        ：AbstractRest
 * Created date ：2020/12/04
 * Lasted date  ：2020/12/04
 * Author       ：taitt
 * Change log   ：2020/12/04：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.res;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;

/**
 * AbstractRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractRest {

    @Autowired
    protected ErrorHandler errorHandler;

    @Autowired
    protected SuccessHandler successHandler;
    
    @Autowired
    protected ObjectMapper objectMapper;
}

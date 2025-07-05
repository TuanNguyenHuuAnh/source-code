/*******************************************************************************
 * Class        ：HomeRest
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：taitt
 * Change log   ：2020/11/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.rest.AbstractRest;

/**
 * HomeRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@RestController
@RequestMapping("/")
public class HomeRest extends AbstractRest {
    
    
    @GetMapping("/")
    public DtsApiResponse index() throws JsonProcessingException {
        long start = System.currentTimeMillis();
        return this.successHandler.handlerSuccess("Welcome to mbal EnterPrise Api " , start);
    }   

}

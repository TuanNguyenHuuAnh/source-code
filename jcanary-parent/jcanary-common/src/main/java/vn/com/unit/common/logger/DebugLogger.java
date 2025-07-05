/*******************************************************************************
 * Class        ：DebugLogger
 * Created date ：2020/07/29
 * Lasted date  ：2020/07/29
 * Author       ：HungHT
 * Change log   ：2020/07/29：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.common.logger;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import vn.com.unit.dts.calc.CalcElapsedTime;



/**
 * DebugLogger
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class DebugLogger extends CalcElapsedTime {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(DebugLogger.class);

    /**
     * debug
     * 
     * @param format
     *          type String
     * @param args
     *          type Object
     * @author HungHT
     */
    @SuppressWarnings("resource")
    public static void debug(String format, Object... args) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        logger.debug(formatter.format(format, args).toString());
    }

    /**
     * isDebugEnabled
     * 
     * @return boolean
     * @author HungHT
     */
    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * convertObjectToJSON
     * 
     * @param obj 
     *          type Object
     * @return String
     * @author HungHT
     */
    public static String convertObjectToJSON(Object obj) {
        String resultJson = null;
        ObjectWriter mapper = new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writer();
        try {
            resultJson = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("[convertObjectToJSON] Convert failed.", e);
        }
        return resultJson;
    }
}

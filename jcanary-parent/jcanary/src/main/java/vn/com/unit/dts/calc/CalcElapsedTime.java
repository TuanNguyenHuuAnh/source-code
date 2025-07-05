/*******************************************************************************
 * Class        ：CalcElapsedTime
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：taitt
 * Change log   ：2020/11/11：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.calc;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.utils.DtsDateFormatUtil;
import vn.com.unit.dts.utils.DtsDateUtil;

/**
 * CalcElapsedTime
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class CalcElapsedTime {

    protected Date startTime;
    protected Date endTime;
    protected long elapsedTime;
    
    public CalcElapsedTime() {
        super();
        this.startTime = new Date();
    }

    /**
     * Get setEndTime
     * 
     * @author HungHT
     */
    public void setEndTime() {
        this.endTime = new Date();
        long start = this.startTime.getTime();
        long end = this.endTime.getTime();
        this.elapsedTime = end - start;
    }

    /**
     * getStart
     * 
     * @return String
     * @author HungHT
     */
    public String getStart() {
        return DtsDateFormatUtil.format(this.getStartTime(), DtsDateUtil.YYYYMMDDHHMMSSFFF);
    }

    /**
     * getEnd
     * 
     * @return String
     * @author HungHT
     */
    public String getEnd() {
        return DtsDateFormatUtil.format(this.getEndTime(), DtsDateUtil.YYYYMMDDHHMMSSFFF);
    }
}

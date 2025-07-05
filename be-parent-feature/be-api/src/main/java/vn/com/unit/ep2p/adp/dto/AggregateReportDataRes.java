/*******************************************************************************
 * Class        ：ObjectDataRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.adp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ObjectDataRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateReportDataRes implements Serializable {
    private static final long serialVersionUID = 7789953518927387027L;

    private ArrayList<String> headers;
	private List<AggregateReportRowDetailDto> dataRows;
}

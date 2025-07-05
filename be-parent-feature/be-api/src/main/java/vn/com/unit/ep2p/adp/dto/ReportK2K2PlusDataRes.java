/*******************************************************************************
 * Class        ：ObjectDataRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.adp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ReportK2K2PlusDataRes<T> implements Serializable {
    private static final long serialVersionUID = 7789953518927387027L;

    protected Integer totalData;
    private BigDecimal totalEpp;
	private BigDecimal totalApp;
	private BigDecimal totalTp;
	private BigDecimal totalEp;
    protected List<T> datas;

}

/*******************************************************************************
 * Class        ：ObjectDataRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.res;

import java.io.Serializable;
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
public class ObjectDataRes<T> implements Serializable {
    private static final long serialVersionUID = 7789953518927387027L;

    protected Integer totalData;
    protected List<T> datas;

//    public ObjectDataRes(int totalDataOfTClass,List<T> datasOfTClass){
//        this.totalData = totalDataOfTClass;
//        this.datas = datasOfTClass;
//    }
}

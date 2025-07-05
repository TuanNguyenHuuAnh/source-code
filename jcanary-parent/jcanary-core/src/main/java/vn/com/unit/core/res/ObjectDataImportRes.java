/*******************************************************************************
 * Class        ：ObjectDataRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.res;

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
public class ObjectDataImportRes<T> {

    protected int totalData;
    protected List<T> datas;
    protected boolean isError;
    public ObjectDataImportRes(int totalData, List<T> datas) {
        super();
        this.totalData = totalData;
        this.datas = datas;
    }
    
    public void setIsError(Boolean isError) {
        this.isError = isError;
    }
}

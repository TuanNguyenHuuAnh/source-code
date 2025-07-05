/*******************************************************************************
 * Class        ：FileUploadParam
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：tantm
 * Change log   ：2020/11/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * FileUploadParam
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public abstract class AbstractUploadParam {

    private byte[] fileByteArray;
    private String fileName;
    private String rename;
    private String key;
    private int typeRule;
    private Date dateRule;
    private String subFilePath;
    private Long companyId;

}

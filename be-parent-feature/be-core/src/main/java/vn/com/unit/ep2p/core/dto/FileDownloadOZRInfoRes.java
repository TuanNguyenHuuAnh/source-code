/*******************************************************************************
 * Class        ：FileDownloadOZRInfoRes
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.api.dto.ResRESTApi;

/**
 * FileDownloadOZRInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadOZRInfoRes extends ResRESTApi{

    private byte[] fileByteArray;
}

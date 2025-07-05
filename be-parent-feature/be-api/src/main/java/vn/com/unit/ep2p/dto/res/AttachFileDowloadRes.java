/*******************************************************************************
 * Class        ：AttachFileDowloadRes
 * Created date ：2021/02/02
 * Lasted date  ：2021/02/02
 * Author       ：TrieuVD
 * Change log   ：2021/02/02：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

/**
 * AttachFileDowloadRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class AttachFileDowloadRes {
    private String fileName;
    private String fileType;
    private byte[] fileByte;
}

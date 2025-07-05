/*******************************************************************************
 * Class        ：AttachFileRes
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractUploadResult;

/**
 * AttachFileRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class AttachFileRes extends AbstractUploadResult {

    private Long attachFileId;
}

/*******************************************************************************
 * Class        ：AttachFileUploadRes
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * AttachFileUploadRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class AttachFileUploadRes {

    private boolean status;
    private String errorMesage;
    private Long companyId;
    private Long referenceId;
    private String referenceKey;
    private String attachType;
    private List<AttachFileRes> resultList;
}

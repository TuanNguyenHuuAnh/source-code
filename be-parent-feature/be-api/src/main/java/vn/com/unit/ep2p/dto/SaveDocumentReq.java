/*******************************************************************************
 * Class        ：SaveDocumentReq
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：taitt
 * Change log   ：2020/11/16：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SaveDocumentReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDocumentReq {

    private  String     documentId;
    private  String   documentName;
    private  String   description;
    private  String   priority;
    private  String   dueDate;
    private  Long     deparmentId;
    private  Long     majorVersion;
    private  Long     minorVersion;
    
    private  Long     serviceId;
    private  Long     businessId;


    
    
}

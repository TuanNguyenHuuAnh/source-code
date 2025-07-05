/*******************************************************************************
 * Class        ：DashBoardDocumentRes
 * Created date ：2021/01/26
 * Lasted date  ：2021/01/26
 * Author       ：taitt
 * Change log   ：2021/01/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DashBoardDocumentRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardDocumentRes {

    private int totalIn;
    private int totalFinished;
    private int totalDraft;
    private int totalProcess;
}

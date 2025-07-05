/*******************************************************************************
 * Class        ：WebSocketService
 * Created date ：2019/12/14
 * Lasted date  ：2019/12/14
 * Author       ：trieuvd
 * Change log   ：2019/12/14：01-00 trieuvd create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;


/**
 * WebSocketService
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
public interface WebSocketService {
    
    /**
     * countNotificationAndSendToUser
     * @param accountId
     * @author trieuvd
     */
    void countNotificationAndSendToUser(Long accountId);
    
    /**
     * sendMessageToUser
     * @param accountId
     * @param message
     * @author trieuvd
     */
    void sendMessageToUser(Long accountId, String message);
}

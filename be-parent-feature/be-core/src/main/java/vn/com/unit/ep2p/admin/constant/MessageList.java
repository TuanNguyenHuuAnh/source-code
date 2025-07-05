/*******************************************************************************
 * Class        ：MessageList
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：KhoaNA
 * Change log   ：2017/02/14：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageList
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class MessageList {
    /** List message */
    private List<Message> messages;
    
    /** Status */
    private String status;
    
    private boolean accessDenied;

    /**
     * Constructor default
     * 
     * @author KhoaNA
     */
    public MessageList() {
        this.messages = new ArrayList<Message>();
    }

    /**
     * Constructor with params
     * @param status
     * @author KhoaNA
     */
    public MessageList(String status) {
        this.messages = new ArrayList<Message>();
        this.status = status;
    }
    
    /**
     * Add messages to list
     * @since 01-00
     * @author KhoaNA
     * @param content 
     * @return
     */
    public void add(String content) {
        Message message = new Message(status, content);
        messages.add(message);
    }

    /**
     * Add messages to list
     * @since 01-00
     * @author KhoaNA
     * @param status type String
     * @param content type String
     * @return
     */
    public void add(String status, String content) {
        Message message = new Message(status, content);
        messages.add(message);
    }
    
    /**
     * Add MessageList to list
     * 
     * @since 01-00
     * @author KhoaNA
     * 
     * @param status
     *          status of message
     * @param content
     *          content message
     * @param locale
     *          locale
     * @return
     */
    public void add(MessageList messageList) {
        if( messageList != null ) {
            List<Message> msgLst = messageList.getMessages();
            
            if( msgLst != null && !msgLst.isEmpty() ) {
                for (Message msg : msgLst) {
                    String status = msg.getStatus();
                    String content = msg.getContent();
                    
                    Message message = new Message(status, content);
                    messages.add(message);
                }
            }
        }
    }

    /**
     * Get messages
     * @return List<Message>
     * @author KhoaNA
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Set messages
     * @param   messages
     *          type List<Message>
     * @return
     * @author  KhoaNA
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Get status
     * @return String
     * @author KhoaNA
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Valid empty
     * 
     * @return
     * @author KhoaNA
     */
    public boolean isEmpty() {
        return messages.isEmpty();
    }
    
    /** haveMessageWithStatus
     *
     * @param status
     * @return
     * @author Phucdq
     */
    public boolean haveMessageWithStatus(String status) {
        return messages.stream().anyMatch(element -> status.equals(element.getStatus()));
    }

    /**
     * Get accessDenied
     * @return boolean
     * @author hand
     */
    public boolean isAccessDenied() {
        return accessDenied;
    }

    /**
     * Set accessDenied
     * @param   accessDenied
     *          type boolean
     * @return
     * @author  hand
     */
    public void setAccessDenied(boolean accessDenied) {
        this.accessDenied = accessDenied;
    }
}

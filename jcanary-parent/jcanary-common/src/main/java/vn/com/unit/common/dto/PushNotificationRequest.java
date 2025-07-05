/*******************************************************************************
 * Class        ：PushNotificationRequest
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * PushNotificationRequest
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class PushNotificationRequest {
    private String from;
    private String to;
    private String functionCode;
    private String title;
    private String message;
    private Map<String, String> data;
    private List<Long> accountIdList;
    private List<String> tokenList;
}

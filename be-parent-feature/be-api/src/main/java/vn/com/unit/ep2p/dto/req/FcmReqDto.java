/*******************************************************************************
 * Class        ：FcmReqDto
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：TrieuVD
 * Change log   ：2020/12/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * FcmReqDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Data
public class FcmReqDto {
    
    private String title;
    private String message;
    private String topic;
    private String token;
    private List<String> multipleTokens = Lists.newLinkedList();
    
    private Map<String, String> data;
}
